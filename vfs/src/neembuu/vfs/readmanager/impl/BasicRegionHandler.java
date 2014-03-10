/*
 *  Copyright (C) 2010 Shashank Tulsyan
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.vfs.readmanager.impl;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import static jpfm.util.ReadUtils.*;
import neembuu.vfs.connection.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpfm.JPfmError;
import jpfm.annotations.PartiallyCompleting;
import jpfm.operations.AlreadyCompleteException;
import jpfm.operations.Read;
import jpfm.operations.readwrite.Completer;
import jpfm.operations.readwrite.ReadRequest;
import jpfm.util.ProfileableReadRequest;
import jpfm.util.SplittedRequestHandler.SplittedRequest;
import neembuu.config.GlobalTestSettings;
import neembuu.diskmanager.RegionStorageManager;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArray;
import neembuu.vfs.annotations.DownloadThread;
import neembuu.vfs.annotations.MainDirectionDeciderThread;
import neembuu.vfs.annotations.ReadHandlerThread;
import neembuu.vfs.annotations.ReadQueueManagerThread;
import neembuu.vfs.connection.NewConnectionParams;
import neembuu.vfs.connection.TransientConnectionListener;
import neembuu.vfs.progresscontrol.Throttle;
import neembuu.vfs.progresscontrol.ThrottleStatistics;
import neembuu.vfs.readmanager.DownloadDataChannel;
import neembuu.vfs.readmanager.DownloadedRegion;
import neembuu.vfs.readmanager.RegionHandler;
import neembuu.vfs.readmanager.TotalFileReadStatistics;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.NotThreadSafe;

/**
 *
 * @author Shashank Tulsyan
 */
public final class BasicRegionHandler 
        implements 
            Completer,
            RegionHandler, 
            Runnable//,
            //Range<BasicRegionHandler> 
                {
    private volatile Thread dispatchThread = null;
    private volatile boolean mainDirection = false;
    /**
     * inclusive limit for which this read handler is registered
     */
    private volatile long authorityLimit = 0;
    private final Logger readHandlerLogger ;
    private final Logger downloadThreadLogger ;
    private static final boolean DEBUG_REGION_REGISTRATION 
            = GlobalTestSettings.getValue("DEBUG_REGION_REGISTRATION");
    protected final Object throttlingLock = new Object();
    private final Range<DownloadedRegion> range;    
    static long MAX_WAIT = 10000;
    static int DEAD = 0, STARTING = 1, WORKING = 2, STOPPING = 3;
    final AtomicInteger aliveThreadCount = new AtomicInteger(0);
    
    final RegionStorageManager regionStorageManager;
    
        /*package private*/ final SeekableConnectionFileImpl file;

    //private final Map<String, Object> properties = new ConcurrentHashMap<String, Object>();
    
    private String throttleStateLabel = null;
    /**
     * When a RegionHandler range array element is created, it has to be created to zero size.
     * But zero sized elements cannot be a part of RangeArray. So we set the size as one and postpone
     * setting the size correctly. When first byte[] of data is available, we set RAEendRepaired as true
     * to indicate this.
     * Volatile since accessible from 2 thread namely, read thread and download thread.
     */
    private volatile boolean RAEendRepaired;

    //requestCreationTimes, requestSatisfactionTimes
    volatile double smallAverageDownloadSpeed = 0; // unit is bytes/millisecond for both
    volatile double smallAverageRequestSpeed = Integer.MAX_VALUE/10; //avoid infinite throttling
    
    @GuardedBy("newConnectionLock")
    private volatile Connection myConnection = null;    
    //private final Object newConnectionCreationLock = new Object();
    private final TransientConnectionListenerImpl newConnectionLock = new TransientConnectionListenerImpl();

    private final Throttle throttle;
    
    BasicRegionHandler(
            SeekableConnectionFileImpl file,
            Range requestRange,
            RegionStorageManager rsm,
            Throttle t,
            long authorityLimit) throws IOException {
        this.range = requestRange;
        atleastTill = requestRange.starting();
        
        this.regionStorageManager = rsm;
        this.authorityLimit = authorityLimit;//requestRange.ending();// + 1;        
        this.file = file;//endOffset.set(offset);

        this.throttle = t;

        if(this.range.ending()>this.range.starting()){RAEendRepaired = true;}
        else RAEendRepaired = false;
        
        readHandlerLogger = rsm.getReadHandlerThreadLogger();
        downloadThreadLogger = rsm.getDownloadThreadLogger();
    }

    /*final void setThrottle(Throttle throttle){
        this.throttle = throttle;
    }*/
    
    @Override
    public final long starting() {
        return range.starting();
    }

    @Override
    public final long ending() {
        return range.ending();
    }
    
    @Override
    public boolean isMainDirectionOfDownload(){
        return mainDirection;
    }
    
    @MainDirectionDeciderThread
    @Override
    public void setMainDirectionOfDownload(boolean mainDirection){
        this.mainDirection = mainDirection;
        if(mainDirection){
            if(!isAlive()){
                // We don't want complex
                // race condition between threads deciding who
                // shall start the connection MainDirectionDeciderThread
                // or ReadHandlerDispatchThread or ReadQueueManagerThread. 
                // if(!newConnectionLock.tryNewConnection())return; is synchronized
                // and handles calls from 2 threads, so should be able to handle from 3
                // as well. If it has issues, we need to fix the thread, as it is a fundamental
                // mistake and might arise later even if only 2 threads call it.
                try{
                    long connectionOffset = ending()+1;
                    startConnection(connectionOffset, 1, false,true);
                }catch(Exception e){
                    readHandlerLogger.log(Level.SEVERE,"MainDirectionThread could not start new connection",e);
                }
            }else {
                synchronized (throttlingLock){
                    throttlingLock.notifyAll(); // notify about mainess
                    // throttling should fail when this is main
                }
            }
        }
    }
    
    /**
     * TODO :
     * For gui to set package private
     * @return 
     */
    //final ReadQueueManager.DispatchThread getDispatchThread(){
      //  return dispatchThread;
    //}

    /**
     * Authority limit is the file offset till which this connection is exclusively responsible
     * for satisfying read requests. The authority limit is greater than or equal to {@link #ending() }.
     * Ending(inclusive) is the last offset till which data has been already downloaded and is available for reading. <br/>
     * Authority limit is used to determine whether a given connection should be used to handle a read request
     * or another connection should be created. <br/>
     * <u>Example :</u><br/>
     * Suppose download data in the region 500-->600 is available and this connection is alive.<br/>
     * This means, starting=500, ending = 600, size = 99, authorityLimit = 600  <br/>
     * A read request is made 601--->611 (this means request file offset = 601, and buffer size = 10) <br/>
     * This means, starting=500, ending = 600, size = 99, authorityLimit = 611 and one pending request (601-->611) <br/>
     * Now another request is made 612--->620. <br/>
     * Another connection might have been made for this request if this additional variable was not present.
     * Indeed, the significance of authority limit is as simply as this.<br/>
     * Now, starting=500, ending = 600, size = 99, authorityLimit = 621 and 2 pending requests (601-->611 & 612-->620) <br/>
     * Let us say another request is made, 650--->661.
     * There are 2 possibilities : <ul>
     * <li>Give the responsibility of this request to 601--->611 connection</li>
     * <li>A new connection is made</li>
     * </ul>
     * This is basically a matter of policy.
     * Ideally this should depend of what will be faster.
     * The current design makes a new connection, because this case would be a more commonly observed.
     * A better design would be to create a pluggable policy interface who's implementation
     * which makes accurate prediction based on buffer size, download speed, connection latency,
     * payload of making a new connection, maximum simultaneous connections allowed to name a few parameters.
     * Also the policy should be changeable. For example, for avi files it is known the case of giving responsibility to an
     * exiting nearby connection would be better. Another way of handling avi files would be to create a large buffer
     * gap between last request made and available data.
     * ////////////////// TODO
     * @see RegionHandler#expandAuthorityLimitCarefully(neembuu.common.RangeArray, long) 
     * @return 
     */
    @Override
    public final long authorityLimit() {
        if(authorityLimit<this.ending()){
            return this.ending();
            //throw new RuntimeException("AuthorityLimit was not updated. Ending="+ending()+" authorityLimit="+authorityLimit );
        }
        return authorityLimit;
    }
    
    protected final void notifySleepingConnectionAboutArrivalOfRequests(){
        synchronized (throttlingLock){
            throttlingLock.notifyAll();
        }
    }

    /**
     * The download thread might have downloaded a region corresponding to which a pending request
     * might be present. The read request dispatch thread does not keep checking for progress, instead it
     * waits for a small amount of time and check again. To make this more event listener based, what should be
     * done is, every time a packet has been downloaded, this function should be invoked by the download thread.
     * Whether the read request dispatch thread must wake and start working is decided by this function.
     * @param availabletill 
     */
    @GuardedBy("newConnectionLock")
    protected final void notifyRegionAvailability(long availabletill){
        //if(DEBUG)LOGGER.log(Level.INFO, "notifying availability till={0} for={1}", new Object[]{availabletill, this});
        if(dispatchThread==null){
            readHandlerLogger.log(Level.INFO, "skipped availability notification={0}", availabletill);
            return;
        }
        if(atleastTill==starting())return; // return if no notification requested or reseted
        if(myPendingReadRequests.isEmpty())return;        
        if(availabletill>atleastTill){
            if(DEBUG_REGION_REGISTRATION)readHandlerLogger.log(Level.INFO, "reporting availability till={0} for={1}", new Object[]{availabletill, this});
            //safe as we are using it just for waking, lock will surely be free
            synchronized(myLock){
                myLock.notifyAll();
            }
            atleastTill=starting(); //reset
        }
    }
    
    private volatile long atleastTill;
    
    private final void registryForAvailabilityNotification(long atleastTill){
        if(DEBUG_REGION_REGISTRATION)readHandlerLogger.log(Level.INFO, "registering atleast till={0} for={1}", new Object[]{atleastTill, this});
        /*if(this.atleastTill > atleastTill){
            throw new IllegalArgumentException("implementation error, check algo "+"previous atleastTill="+this.atleastTill+" new="+atleastTill);
        }*/
        this.atleastTill = atleastTill;//atomic no synching required
        // Timeout in wait will prevent bad things from happening, so a stale value in atleastTill
        // does not matter anyway
    }
    
    protected final void notifyConnectionSuccess(){
        long totalWait = 0;
        while(dispatchThread==null){
            try{
                // this will hardly take time
                // the reason is that it takes only a few java statements to be executed 
                // for dispatchThread value to be set to appropriate value
                Thread.sleep(100);
                totalWait+=100;
                if(totalWait>500){
                    startNewThreadIfRequired();
                }
            }catch(InterruptedException a){
                
            }
        }
        synchronized(myLock){
            myLock.notifyAll();
        }
    }

    /**
     * Please see {@link #authorityLimit() } first. <br/>
     * This increase the authority limit without changing {@link #ending()}. <br/>
     * {@link RegionHandler#expandEndingAndAuthorityLimitCarefully(neembuu.common.RangeArray, long)  }
     * is called when new content has been downloaded.<br/>
     * {@link RegionHandler#expandAuthorityLimitCarefully(neembuu.common.RangeArray, long)  }
     * is called by ReadQueueManager when a request has been made on this region
     * such that a portion of the request lies inside this connection and a part of the request 
     * lies outside. <br/>
     * Example : data available = 500->600 and request = 590->610 <br/>
     * This is the present case. However a better design might require to expand authority limit at other cases.
     * Example : data available = 500->600 and request = 650->670 <br/>
     * The request lies outside, but instead of creating a new connection, it might have been found
     * by calculation that simply waiting for the presently alive connection 500->600 to reach till there
     * is more efficient. Also see {@link #authorityLimit() } and it further discusses this case.
     * @see #authorityLimit() 
     * @param rangeArray
     * @param newAuthorityLimit 
     */
    public final void expandAuthorityLimitCarefully(RangeArray rangeArray, long newAuthorityLimit){
        assert(newAuthorityLimit>=this.ending());
        assert(newAuthorityLimit>authorityLimit);

        synchronized(rangeArray.getModLock()){ ///safe since authority limit is
            // not published through the gui
            // the difference between ending and authoritylimit is so small
            // that this is unlikely to be visible to the user even if we painted 
            // it on the range array progress bar/component
            if(newAuthorityLimit < this.ending())
                throw new IllegalArgumentException("Attempting contraction instead of expansion.\n" +
                        "For new end = "+newAuthorityLimit +
                        "\nElement = "+this);
            if(newAuthorityLimit == this.ending() ){
                authorityLimit = newAuthorityLimit;
            }
            Range next = null;
            //synchronized(rangeArray.getModLock()){
                int myindex = rangeArray.indexOf(this.starting());
                if(myindex + 1 < rangeArray.size() )
                    next = rangeArray.get(myindex+1);
                if(next !=null ){
                    if(newAuthorityLimit >= next.starting()){
                        newAuthorityLimit = next.starting() - 1;
                        throw new IllegalArgumentException(
                            "Use expandCarefullyAlongWithAuthorityLimit instead, and check the return value. "
                            + "newAuthorityLimit >= next.starting() ."
                            + "newAuthorityLimit="+newAuthorityLimit+" next.starting()="+next.starting());
                    }                    
                }
                authorityLimit = newAuthorityLimit;
            //}
        }
    }

    /**
     * This is used to expand authority limit 
     * and also increase {@link #ending()}. <br/>
     * {@link RegionHandler#expandEndingAndAuthorityLimitCarefully(neembuu.common.RangeArray, long)  }
     * is called when new data packet has been downloaded, and thus ending and authority limit
     * needs to be increased as this region now has more data <br/>
     * {@link RegionHandler#expandAuthorityLimitCarefully(neembuu.common.RangeArray, long)  }
     * is called when responsibility of a request has been given to this 
     * although data is not available in this region. <br/>
     * Every time a new packet of data has been downloaded, the thread which is downloading data, must 
     * call this function. <br/><br/>
     * The authority limit cannot be arbitrarily expanded.
     * <br/>Consider :<br/>
     * 500->600 800->900 <br/>
     * This means data is available between 500 to 600 and 800 to 900.<br/>
     * 500->600 can be expanded till only 799.<br/>
     * Now if another connection was made in between because it was required to do so.<br/>
     * 500->600 650->660 800->900 <br/>
     * 500->600 can be expanded till only 649.<br/>
     * However it is difficult for the downloading thread to immediately terminate when the connection has downloaded
     * data more than what it was supposed to, and thus we have 2 copies of the same data. That is :<br/>
     * 500->655 650->660 800->900 <br/>
     * This entry can't exist in the RangeArray, and it makes the logic to split requests lying between
     * two regions unnecessarily complex. However it is perfectly fine for the region to have data till 655 but show 
     * it has data till only 649 (according to this example).<br/><br/>
     * Every time a new packet of data has been downloaded, the thread which is downloading data, must 
     * call this function. If another connection was touched the value returned by this function
     * would be lesser than what was passed as argument. In such a case either 2 of the things may be done :
     * <ol>
     * <li>Terminate the connection <br/>
     * This is done either when <ul>
     *      <li>No requests are being made on this connection, and thus it has to be closed anyway</li>
     *      <li>Requests are being made on this connection, but the next region, with which this connection 
     *           has collided  <ul>
     *              <li>has already downloaded a lot of data </li>
     *              <li>is alive, that is downloading is still going on</li>
     *           </ul>
     *      </li>
     * </ul>
     * <li>Delete the next region with which this connection is colliding<br/>
     * This is done when <ul>
     *      <li>the requests are being made on this connection, and the next region is very thin (has very little data)
     *      and is already dead.</li>
     *      <li><small><u>A rare case :</u> The download data source is a filehost like rapidshare, and user is using a free account, 
     *          and thus connection if once closed cannot be opened again. This is rare case and will happen
     *          when a link from host like mediafire was available, but is not longer available. </small>
     *      </li>
     *      </ul>
     * </li>
     * </ol>
     * 
     * </pre>
     * @param rangeArray RangeArray containing all connections
     * @param newEnd
     * @return 
     */
    protected final long expandEndingAndAuthorityLimitCarefully(RangeArray rangeArray,long newEnd){
        long actualNewEnd = 
                rangeArray.expandCarefully(range, newEnd);
        // locking not required since this is done in expandCarefully.
        
        authorityLimit =
                authorityLimit > actualNewEnd ?
                    authorityLimit:
                    actualNewEnd;// + 1;
        return actualNewEnd;
    }

    @Override
    public final TotalFileReadStatistics getTotalFileReadStatistics() {
        return file.getReadQueueManager();
    }

    @Override
    public final ThrottleStatistics getThrottleStatistics() {
        return throttle;
    }
    
   
    
    private final class TransientConnectionListenerImpl implements TransientConnectionListener {
        
        volatile int retryCount = 0;
        
        @GuardedBy("this")
        private volatile boolean working = false;
        
        TransientConnectionListenerImpl() {
        }

        @Override
        public void describeState(String state) {
            // report to gui
        }

        @Override
        public void reportNumberOfRetriesMadeSoFar(int numberOfretries) {
            retryCount = numberOfretries;
        }
        
        @GuardedBy("newConnectionLock")
        public synchronized boolean tryNewConnection(){
            if(working)return false;
            if(!isAlive()){
                if(!working){
                    working = true;
                    return true;
                }
            }
            return false;
        }

        @Override
        public void successful(Connection c,NewConnectionParams ncp) {
            if(c==null){
                IllegalArgumentException exp = new IllegalArgumentException("Connection supplied was null, report failure instead");
                failed(exp,ncp);
                throw exp;
            }//@GuardedBy("newConnectionLock")
            synchronized(this){
                myConnection = c;
                if(!working)throw new IllegalStateException("Connection already completed earlier");
                working = false;                
            }
            notifyConnectionSuccess();            
        }

        @Override
        public void failed(Throwable reason,NewConnectionParams ncp) {
            synchronized(newConnectionLock){
                myConnection = null; 
                working = false ; 
            }
            
            file.getTroubleHandler().cannotCreateANewConnection(ncp,retryCount);
        }
    }

    @GuardedBy("newConnectionLock")
    public final synchronized Connection getConnection() {
        return myConnection;
    }
    
    

    public final void setThrottleStateLabel(String throttleStateLabel) {
        this.throttleStateLabel = throttleStateLabel;
    }

    public final String getThrottleStateLabel() {
        return throttleStateLabel;
    }
    
    @PartiallyCompleting
    @ReadHandlerThread
    public final void read(ReadRequest read) throws Exception {
        //partially completing
        try{
            long lastReadOffset = read.getFileOffset() + read.getByteBuffer().capacity() - 1;
            if(!RAEendRepaired){
                registryForAvailabilityNotification(ending());
                return;
                // we cannot handle anything unless and until 
                // the fakeness of end is fixed. Also we don't have even a single byte of 
                // data that is why we cannot satisfy requests right now.
                // The read thread is surely going to sleep, and the criteria 
                // of data being available in this thread is not going to be satfisied.
                // The thread will be woken up by data arrival notification.
            } 
            if(lastReadOffset > ending()/*end*/ /*|| !RAEendRepaired*/ ){
                readHandlerLogger.log(
                        Level.INFO, "buffer not filled yet req.={0}+>{1} avail={2}", new Object[]{read.getFileOffset(), read.getByteBuffer().capacity(), this});

                /*if(newConnectionLock.working.get()){
                    // working on new connection, return
                    return;
                }*/

                //if(!isAlive()){
                    try{
                        startConnection(
                            this.ending()+1,
                            (int)(read.getFileOffset() + read.getByteBuffer().capacity() - 1  - ending()),
                            true,false
                        );
                    }catch(Exception a){
                        readHandlerLogger.log(Level.INFO, "could not create new connection ", a);
                    }
               //}
                return;
            }
            if(read.getFileOffset() - this.starting()  < 0){
                System.err.println("java 312 : negative error="+read.getFileOffset()+" "+this.starting());
            }

            try{
            //while(notFilled(offset, directByteBuffer.capacity(), totalRead)){
            //synchronized(storeChannel){
                //average delay = 0.243 ms
                //System.out.println(regionStorageManager);
                regionStorageManager.read(read.getByteBuffer(),read.getFileOffset());
            //}

            }catch(Exception any){
                // TODO : handle EOF .. probably EOF already handled by request 
                // trimming done right at the jpfm SimpleFS level.
                // If the connection is down use PartialFileFS instead.
                readHandlerLogger.log(Level.INFO,read.toString()+" this="+this,any);
            }
            try{
                completedSuccessfully(read,this);
                readHandlerLogger.log(Level.INFO,"completed="+read.toString());
            }catch(AlreadyCompleteException e){
                if(read instanceof SplittedRequest)
                    readHandlerLogger.log(Level.INFO,"Looks like some split failed to complete and thus request was already forcefully completed",e);
                else 
                    readHandlerLogger.log(Level.SEVERE,"impl mistake,(un)lucky race condition",e);

            }
            
            throttle.requestCompleted(starting(), ending(), read.getFileOffset(), requestSize(read), read.getCompletionTime());
            // report no matter what
            
        }catch(Exception any){
            readHandlerLogger.log(Level.SEVERE,"forefully completing request ",any);
            if(read instanceof ProfileableReadRequest){
                ((ProfileableReadRequest)read).addProperty(any);
            }
            System.err.println(read);
            if(!read.isCompleted()){
                // short reads are treated as error on windows
                // so let 's fill region with zeros and forget it
                read.complete(JPfmError.SUCCESS, 0, null);
            }
        }
        if(read.isCompleted()){
            readHandlerLogger.log(
                    Level.INFO, "completed=\t{0}\t{1}\t{2}", 
                        new Object[]{
                            read.getFileOffset(),
                            "",//ContentPeek.generatePeekString(read),
                            read.getError().toString()});
        }
    }

    private boolean notFilled(long offset, int capacity,int totalRead){
        long end = capacity + offset - 1;
        if(this.ending() < end){
            // not filled even by available region
            if(offset+totalRead - 1 > this.ending()){
                return false;
            }else{ 
                if(this.RAEendRepaired){
                    return true; // not filled as the rest in in some other existing region
                } else {
                    return false;// not filled as buffer not filled yet
                    //we should retry
                }
            }
        }
        return false;
    }

    @Override
    public final boolean isAlive(){
        synchronized (newConnectionLock){
            if(myConnection==null){
                return false;
            }
            return myConnection.isAlive();
        }
    }
   
    // called by run method of ReadQueueManager
    void startConnection(int minimumBuffer, boolean fromRead) throws IOException{
        startConnection(this.starting(),minimumBuffer,fromRead,false);
    }
    
    @MainDirectionDeciderThread
    @ReadHandlerThread
    @ReadQueueManagerThread // called from NewReadHandlerProviderImpl but call originates from RQMThread
    private void startConnection(long offset,final int minimumBuffer,final boolean fromRead,final boolean fromMainDir) throws IOException { 
        if(!newConnectionLock.tryNewConnection())return;
        // the throttle's must be informed of a read request are beyond downloaded region
        // otherwise incorrect throttle condition would be imposed on this new connection.
        if(!myPendingReadRequests.isEmpty()){
            long maxReadEnd = 0; int effectiveReadSize=0;
            for(ReadRequest rr : myPendingReadRequests){
                maxReadEnd = Math.max(endingOffset(rr),maxReadEnd);
            }
            effectiveReadSize = (int)Math.max(0, maxReadEnd - ending() + 1);
            throttle.requestCreated(starting(), ending(), ending(), effectiveReadSize, System.currentTimeMillis());
        }else {
            throttle.requestCreated(starting(), ending(), ending()+1, minimumBuffer, System.currentTimeMillis());
        }
        if(fromRead){
            readHandlerLogger.log(Level.INFO, "attempting to resume connection {0} con={1}", new Object[]{
                this, 
                this.myConnection!=null?this.myConnection:" no my connection right now"});
            readHandlerLogger.log(Level.INFO, "startConnection", new Throwable());
        }
        if(fromMainDir){
            readHandlerLogger.log(Level.INFO, "startConnectionMainDirectionThread", new Throwable());
        }
        
        //logger is thread safe
        readHandlerLogger.log(Level.INFO, "requested offset="+offset+" this-"+this/*,new Throwable()*/);
        //LOGGER.log(Level.INFO, "offset="+offset+" this-"+this);

        if(RAEendRepaired){
            long adjustedOffset = ending()+1;
            if(adjustedOffset!=offset){
                readHandlerLogger.log(Level.INFO, "changing adjusted offset="+adjustedOffset+" this-"+this/*,new Throwable()*/);
            }
            offset = adjustedOffset;
        }else {
            long adjustedOffset = starting();
            if(adjustedOffset!=offset){
                readHandlerLogger.log(Level.INFO, "changing adjusted offset="+adjustedOffset+" this-"+this/*,new Throwable()*/);
            }
            offset = adjustedOffset;
        }
        
        if(newConnectionLock.retryCount>9){
            
        }
        
        //startConnection(this.starting(),minimumBuffer);
        DownloadDataChannelToImpl newDataChannel = new DownloadDataChannelToImpl(minimumBuffer);

        file.getNewConnectionProvider().provideNewConnection(
                new NewConnectionParams.Builder()
                .setOffset(offset)
                .setDownloadThreadLogger(downloadThreadLogger)
                .setMinimumSizeRequired(minimumBuffer)
                .setDownloadDataChannel(newDataChannel)
                .setReadRequestState(this)
                .setTransientConnectionListener(newConnectionLock)
                .setThrottlingLock(throttlingLock)
                .setThrottle(throttle)
                .build()
            );
    }

    
    
    @Override
    public final long fileSize() {
        return file.getFileSize();
    }

    @Override
    public String getFileName() {
        return file.getName();
    }
    
    @Override
    public String toString() {
        return "BasicRegionHandler{"+
                range.starting()+"-->"+range.ending()+
                " ,authl="+authorityLimit()+" isAlive="+isAlive()+" ,isMain="+mainDirection+"}";
    }

    @Override
    public int getBytesFilledTillNow(ReadRequest pendingRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void completeNow(ReadRequest pendingRequest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void open() {
        // starting will be done ONLY AND ONLY just in time of requirement
    }
    
    @Override
    public void close() {
        // thread could have been killed here, but after a certain time
        // threads die by themselves anyway 
        /*if(thread_state.get()==STOPPING)return;
        stop();*/// we are sending an message to stop
        forcefullyCompleteAllPendingRequests(); // this will automatically kill connection
        // after sometime.
    }

    @Override
    public void closeCompletely() throws Exception{
        forcefullyCompleteAllPendingRequests();
        stop();
        if(getConnection()!=null){
            if(getConnection().isAlive())
                getConnection().abort();
        }
        // close diskmanager related resources
        // is handled by DiskManager internals
        // when invoked from ReadQueueManager
    }
    
    @Override
    public StackTraceElement[] getStackTrace() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public final int numberOfPendingRequests() {
        return myPendingReadRequests.size();
    }

    @Override
    public boolean hasPendingRequests() {
        return !myPendingReadRequests.isEmpty();
    }

    @Override
    public String tryGettingPendingRequestsList() {
        StringBuilder sb = new StringBuilder(2000);
        for(ReadRequest rr : myPendingReadRequests){
            sb.append(rr.toString()); sb.append("\n");
        }
        return sb.toString();
    }
    
    @NotThreadSafe
    private final class DownloadDataChannelToImpl //extends OutputStream 
            implements  DownloadDataChannel {
        private long lsttime = System.nanoTime();
        @GuardedBy("newConnectionLock")
        private volatile boolean isOpen = true;
        private volatile boolean ate = false;
        
        private final int minimumBuffer;//useless as we always send an notify
        // when a new region is available

        public DownloadDataChannelToImpl(int minimumBuffer) {
            this.minimumBuffer = minimumBuffer;
        }
        
        @Override
        public boolean isAutoHandleThrottlingEnabled(){
            return ate;
        }
        
        @Override
        public void setAutoHandleThrottlingEnabled(boolean ate){
            this.ate = ate;
        }
      
        private void check(){
            synchronized(newConnectionLock){
                //isAlive also locks over newConnectionLock
                if(!isAlive()){
                    throw new IllegalStateException("Incorrect implementation of Connection : Attempting to write for a dead connection");
                }
                if(!isOpen)
                    throw new IllegalStateException("Channel closed");
            }
        }

        @Override
        public final void close(){
            synchronized(newConnectionLock){
                if(!isOpen){
                    throw new IllegalStateException("Already closed");
                }isOpen = false;
            }
        }
        
        @Override
        public final boolean isOpen() {
            synchronized(newConnectionLock){
                return isOpen;
            }
        }

        @Override
        @DownloadThread
        public final int write(ByteBuffer toWrite)throws  IOException {

            check();
            // thread safe hence forth
            // as connection creation, anniliation cannot take place
            // when a write is being commited.
      
            //if(getSize()>minimumBuffer){System.out.println("buffer filled");}
            int done = 0;
            
            long desiredEnd = -1;

            // assuming that writting in one single stroke works
            if(RAEendRepaired){
                done += regionStorageManager.write(
                        toWrite,ending()+1);// ending is inclusive that is
                // why we write a +1
                desiredEnd = ending()+toWrite.limit(); // len
            }else{
                done += regionStorageManager.write(toWrite,starting());
                //if(done > 1 ){
                    desiredEnd = starting()+toWrite.limit()/*len*/-1;
                    RAEendRepaired = true;
                //}
            }
            if(toWrite.hasRemaining()){
                throw new RuntimeException("The write channel did not commit the entire write request. Total length="+toWrite.limit()/*len*/+" committed="+done);
            }

            if(ate){
                throttle.downloadProgressed(starting(), ending(), toWrite.capacity());
                double waitInterval = throttle.getWaitIntervalPerByte();
                throttle.wait(waitInterval, done);
            }
            
            //LOGGER.log(Level.INFO, "cur={0}des={1}", new Object[]{ending(), desiredEnd});            
            if(
                    expandEndingAndAuthorityLimitCarefully(file.getReadQueueManager().getRegionHandlers(), desiredEnd)
                    < desiredEnd
                ){ //downloading
                // we leaked to a region already downloaded
                // there is no point in downloading anymore
                downloadThreadLogger.log(Level.INFO, "we leaked into another region {0} killing ",BasicRegionHandler.this);
                //synchronized (newConnectionLock){
                    myConnection.abort();
                    /*System.err.println("we leaked into another region killing "+BasicRegionHandler.this);
                    if(true)throw new IllegalStateException
                            ("we leaked into another region killing "+BasicRegionHandler.this);*/
                //}
            }

            // update after expanding
            notifyRegionAvailability(ending());
            //repaintProgress();
            return toWrite.capacity();
        }       
    }

    
    
    /*@Override
    public void addRequestSpeedListener(RequestSpeedListener progressListener) {
        throttle.addRequestSpeedListener(progressListener);
    }

    @Override
    public void removeRequestSpeedListener(RequestSpeedListener progressListener) {
        throttle.removeRequestSpeedListener(progressListener);
    }

    @Override
    public void addDownloadSpeedListener(DownloadSpeedListener progressListener) {
        throttle.addDownloadSpeedListener(progressListener);
    }

    @Override
    public void removeDownloadSpeedListener(DownloadSpeedListener progressListener) {
        throttle.removeDownloadSpeedListener(progressListener);
    }*/
    
    @Override
    public final void addNewZeroRequestObservation(long creationTime){
        throttle.requestCreated(starting(), ending(), -1, 0, creationTime);
    }
    
    long totalThreadCreatedWait = 0;
    
    @Override
    @ReadQueueManagerThread
    public final void handleRead(ReadRequest readRequest){
        throttle.requestCreated(starting(), ending(), readRequest.getFileOffset(), requestSize(readRequest), readRequest.getCreationTime());
        startNewThreadIfRequired();
        if(thread_state.get()==STARTING){
            try{
                Thread.sleep(10);//waiting for new thread to start
                totalThreadCreatedWait+=10;
                if(totalThreadCreatedWait>1000){throw new RuntimeException("Dispatch thread not starting");}
                // keeping it low for keeping latency low as low and possible 
                // for a seek operation
                // thread starting takes like 1 to 3 milliseconds
            }catch(InterruptedException ie){
                
            }handleRead(readRequest);// nested call in hope that new thread will be created by then
            return;
        }
        if(thread_state.get()==WORKING){
            myPendingReadRequests.add(readRequest);
            //synchronized(myLock){myLock.notifyAll();}
        }
    }
    
    private void startNewThreadIfRequired(){
        if(thread_state.compareAndSet(DEAD, STARTING)){
            int totalSleep = 0;
            while(aliveThreadCount.get()>0){
                try{Thread.sleep(10);}catch(InterruptedException ie){} 
                totalSleep+=10;
                if(totalSleep>1000)
                    throw new RuntimeException("Failure in thread model, multiple concurrent consumer thread");
            }
            totalThreadCreatedWait = 0;
            dispatchThread = new Thread(this,
                    BasicRegionHandler.class.getSimpleName()+"{"+Long.toString(starting())+"}{"
                    +file.getName()+"}");
            dispatchThread.setDaemon(true);
            readHandlerLogger.log(Level.INFO,dispatchThread.getName(),new Throwable());
            dispatchThread.start();
        }
    }
    
    final void stop(){
        Thread dispatchThread_cpy = dispatchThread;
        int totalSleep = 0;
        while(thread_state.get()==STARTING){
            try{
                Thread.sleep(10);
                totalSleep+=10;
            }catch(Exception a){
                
            }if(totalSleep>600){throw new RuntimeException("Cannot stop the thread"); }
        }
        if(thread_state.compareAndSet(WORKING, STOPPING)){
            //try{
                if(dispatchThread_cpy!=null){
                    dispatchThread_cpy.interrupt();
                }
                //interrupting not essential as thread dies by itself
                //anyway
            //}catch(Exception e){
                
            //}
            //throw new IllegalStateException("state="+_(thread_state.get()));
        }
    }

    final void forcefullyCompleteAllPendingRequests(){
        try{
            Iterator<ReadRequest> it = myPendingReadRequests.iterator();
            while(it.hasNext()){
                ReadRequest rrs = it.next();
                if(!rrs.isCompleted())rrs.complete(JPfmError.SUCCESS);
                rrs.complete(JPfmError.SUCCESS);
                it.remove();
            }
        }catch(Exception ace){
            ace.printStackTrace(System.err);
            //ignore
        }
    }

    private final ConcurrentLinkedQueue<ReadRequest> myPendingReadRequests = new ConcurrentLinkedQueue<ReadRequest>(){
        @Override
        public final boolean add(ReadRequest e) {
            if(!e.canComplete(null)){
                throw new IllegalStateException("A completer is set for the given job. This thread is not authorized to complete the request anymore");
            }
            return super.add(e);
        }

    };

    private final Object myLock = new Object();
    //private int requestCount = 0; //local request count
    public ConcurrentLinkedQueue<ReadRequest> getPendingRequests(){
        return myPendingReadRequests;
    }

    @Override
    public void fillInOperationsPendingSince(List<ReadRequest> rrs, long millisec) {
        Iterator<ReadRequest> it = myPendingReadRequests.iterator();
        while(it.hasNext()){
            ReadRequest rr = it.next();
            if(!rr.isCompleted()){
                if(System.currentTimeMillis() - rr.getCreationTime() > millisec ){
                    rrs.add(rr);
                }
            }
        }
    }
    
    @Override
    public String[]getPendingOperationsAsString(){
        LinkedList<String> lst = new LinkedList<String>();
        Iterator<ReadRequest> it = myPendingReadRequests.iterator();
        while(it.hasNext()){
            lst.add(it.next().toString());
        }return lst.toArray(new String[lst.size()]);
    }
    
    public void printPendingOps(PrintStream pw){
        pw.println("++++++++++++++++++++");
        Iterator<ReadRequest> it = myPendingReadRequests.iterator();
        while(it.hasNext()){
            pw.println(it.next());
        }
        pw.println("--------------------");
    }

    private final static boolean DEBUG_READ_QUEUE_MANAGER = GlobalTestSettings.getValue("DEBUG_READ_QUEUE_MANAGER");
    
    private int iterateOverUndispatchedRequests(Iterator<ReadRequest> it){
        int requestsIteratedOver = 0;
        while(it.hasNext()){
            //LOGGER.log(Level.INFO, "time taken/iteration={0} ns", (newtimeStart - timeStart) );
            // we assume (incorrectly) that the given read
            // requests lies completely in one region,
            // while to satisfy it, we might need to
            // read from more than one region
            ReadRequest read = it.next();requestsIteratedOver++;
            try{
                if(((Read)read).getCompleter()==null){
                    read.setCompleter(this);
                }
            }catch(ClassCastException cce){
                //ignore
            }
            if(read.isCompleted()){
                // this actually never happens
                if(DEBUG_READ_QUEUE_MANAGER)readHandlerLogger.log(Level.SEVERE, "Unexpected behaviour : PartiallyCompleting "
                        + "implementation completed without method invokation. The partially "
                        + "completing implementation should not keep copy of"
                        + "read requests. ReadRequest={0}",read);
                
                throttle.requestCompleted(starting(), ending(), read.getFileOffset(), requestSize(read), read.getCompletionTime());// report no matter what
                it.remove();continue;
            }
            try{
                read(read);
                ((ProfileableReadRequest)read).getTraversedCount().incrementAndGet();
                if(read.isCompleted()){
                    
                    throttle.requestCompleted(starting(), ending(), read.getFileOffset(), requestSize(read), read.getCompletionTime());
                    // report no matter what
                    it.remove();
                }continue;
            }catch(Exception any){
                if(DEBUG_READ_QUEUE_MANAGER)readHandlerLogger.log(Level.INFO, "Error while reading", any);
                if(!read.isCompleted())read.handleUnexpectedCompletion(any);
            }
        }
        return requestsIteratedOver;
    }

    private int sleepAccordingToPolicy(){
        // implement event based sleeping waking mechanism
        // go in sleep.
        // a wake will be fired when channel realizes that required limit was reached.
        long oldtime = 0;
        try{

            //<editor-fold defaultstate="collapsed" desc="IF{connection is not alive or is just being created}">
            if(!isAlive()){
                long min_end = findMinimumReadRequestEnding();
                if(min_end>ending()){
                    if(DEBUG_SLEEP_ALGOS)oldtime=enteringSleep("Connection");
                    // waiting for connection to be created
                    synchronized(myLock){
                        long waitInterval = 150;
                        if(newConnectionSleepCount>=1)waitInterval = 300;
                        if(newConnectionSleepCount>=5)waitInterval = 600;
                        myLock.wait(waitInterval); // in case an update was just missed
                        // we have a finite sleep time
                        // notficiation automatically send when connected
                        newConnectionSleepCount++;
                    }if(DEBUG_SLEEP_ALGOS)exitSleep(oldtime);
                    return newConnectionSleepCount;
                }else if(min_end==-1){// there are no pending requests
                    // beyond no no need to sleep
                    // This happens when connection collides with another
                    // and dies
                    /*synchronized(myLock){
                        myLock.wait(100);
                    }*/
                }
                //return newConnectionSleepCount;
            }//</editor-fold>

            newConnectionSleepCount=0; // reset
            int maxWait = 3000;
            waitTillRequestsArriveOrMax(oldtime,maxWait); 

        }catch(InterruptedException exception){
            //LOGGER.log(Level.INFO, "RQM", exception);
        }catch(Exception any){
            readHandlerLogger.log(Level.INFO, "RQM", any);
        }return newConnectionSleepCount;
    }

    void waitTillRequestsArriveOrMax(long oldtime,int maxWait)throws InterruptedException{           
        //<editor-fold defaultstate="collapsed" desc="IF{there aren't any pending ReadRequests}">
        long waitStart = System.currentTimeMillis();
        if(myPendingReadRequests.isEmpty()){
            // this usually means downloadSpeed > RequestSpeed
            // or requests have shifted to some other connection
            // and this connection is simply cruising
            if(DEBUG_SLEEP_ALGOS)oldtime=enteringSleep(" is empty ");
            
            while(myPendingReadRequests.isEmpty()){
                synchronized(myLock){
                    if(DEBUG_SLEEP_ALGOS)System.err.println("retrying sleep for is empty "+starting()+"-->"+ending());
                    myLock.wait(30); // in case an update was just missed
                    if(System.currentTimeMillis() - waitStart > maxWait){
                        return;
                    }
                }
            }
            if(DEBUG_SLEEP_ALGOS)exitSleep(oldtime);
        }else {
            // this usually means downloadSpeed < RequestSpeed
            //speedLeastGapWaiter();
            notifyWhenDownloaded_Waiter();
        }
            //</editor-fold>
    }

    private final AtomicInteger thread_state = new AtomicInteger(DEAD); 
    // 0->dead
    // 1->starting
    // 2->working
    // 3->stopping
    
    private int newConnectionSleepCount = 0;
    
    @Override
    public final void run() {
        readHandlerLogger.info(_(thread_state.get())+toString());
        aliveThreadCount.incrementAndGet();
        
        try{
            // thread is in state of 
            if(!thread_state.compareAndSet(STARTING, WORKING)){
                throw new IllegalStateException("Thread was not in state of just starting, instead was in "+_(thread_state.get()) );
            }readHandlerLogger.info(_(thread_state.get())+toString());
            long totalWait = 0; long lastTime = System.currentTimeMillis();
            while(!thread_state.compareAndSet(STOPPING,DEAD)){ // while thread               
                Iterator<ReadRequest> it = myPendingReadRequests.iterator();
                
                int requestsIteratedOver = iterateOverUndispatchedRequests(it);
                newConnectionSleepCount = sleepAccordingToPolicy();

                if(requestsIteratedOver==0){
                    //no requests :( we are waiting, but we shall not wait more than
                    //a certain limit
                    totalWait+=(System.currentTimeMillis()-lastTime);
                }else {
                    //reset
                    totalWait=0;lastTime = System.currentTimeMillis();
                }

                if(totalWait>=MAX_WAIT){
                    if(!myPendingReadRequests.isEmpty()){
                        startConnection(0, true);
                        continue;
                    }
                    if(thread_state.get()!=STOPPING){
                        if(!thread_state.compareAndSet(WORKING,STOPPING)){
                            throw new IllegalStateException("Thread was not in correct state. Expected=WORKING was in "+_(thread_state.get()));
                        }else readHandlerLogger.info(_(thread_state.get())+toString());
                    }
                }
            }    readHandlerLogger.info(_(thread_state.get())+toString());
        }catch(Exception any){
            readHandlerLogger.log(Level.SEVERE,"Region read dispath thread died because of an exception.",any);
            thread_state.set(DEAD);//atomic
            //startNewThreadIfRequired();
        }// thread marked dead and is dead 
        aliveThreadCount.decrementAndGet();
        
        if(!myPendingReadRequests.isEmpty())startNewThreadIfRequired();
    }

    private static final boolean DEBUG_SLEEP_ALGOS = false;

    private long enteringSleep(String message){
        System.err.println("Sleeping for "+message+ starting()+"-->"+ending());
        return System.nanoTime();
        //LOGGER.log(Level.SEVERE, "Sleeping for {0} id={1}", new Object[]{message, previousId});
    }

    private void exitSleep(long oldTime){            
        System.err.println("Done sleeping for "+starting()+"-->"+ending()+" slept for="+((System.nanoTime() - oldTime)/1000000) );
        //LOGGER.log(Level.SEVERE, "Done sleeping for id={0}", previousId);
    }

    private long findMinimumReadRequestEnding(){
        long min_end = -1;
        for(ReadRequest r : myPendingReadRequests){
            if(min_end==-1)min_end = endingOffset(r); // initialize
            if(r.isCompleted())continue;
            min_end = Math.min(endingOffset(r),min_end);
        }return min_end;
    }

    private void notifyWhenDownloaded_Waiter()throws InterruptedException{
        if(DEBUG_SLEEP_ALGOS)System.err.println("+NWDW+");
        if(DEBUG_SLEEP_ALGOS)System.err.println("region="+this);
        long min_end = findMinimumReadRequestEnding();
        if(min_end==-1){
            if(DEBUG_SLEEP_ALGOS)System.err.println("-1 -NWDW-"); 
            return;
        }
        if(min_end<=ending()){
            if(DEBUG_SLEEP_ALGOS)System.err.println("available -NWDW-"); 
            return;
        } // we already have sufficient data to complete 
            // the request with minimum ending value, this means we can complete atleast one 
            // of the unknown number of pending requests
        long oldtime = 0;
        if(DEBUG_SLEEP_ALGOS)
            oldtime=enteringSleep("NWDW");
        registryForAvailabilityNotification(min_end);
        // we might need to wake up the waiting throttle
        // because request goes beyond availability
        synchronized (throttlingLock){
            throttlingLock.notifyAll();
        }
        synchronized(myLock){
            myLock.wait(500);
        }
        if(DEBUG_SLEEP_ALGOS)exitSleep(oldtime);
        if(DEBUG_SLEEP_ALGOS)System.err.println("-NWDW-");
    }

    // not used. notification mechanism is working nicely for now
    private void speedLeastGapWaiter()throws InterruptedException{
        long leastGap = 0;
        Iterator<ReadRequest> it_ = myPendingReadRequests.iterator();
        ReadRequest waitercauser = null;
        while(it_.hasNext()){
            // <editor-fold defaultstate="collapsed" desc="dia">
            // x2 = requestEnding
            // D = myReadHandler.ending()
            //   * Index                     0               1                  2
            //   * Offset value          A------>B       C---x2->D x2       E------>F// </editor-fold>
            ReadRequest readRequest = it_.next();
            leastGap = Math.max(leastGap, ending() - endingOffset(readRequest));
            if(leastGap > 0){
                waitercauser = readRequest;
                break;
            }
        }
        if(leastGap < 0){
            int waitInternval = (int)Math.min(
                        (-leastGap)
                        /
                        throttle.getDownloadSpeed_KiBps()
                        ,1000
                    ) ;
            synchronized(myLock){
                System.out.print("RQM:622 wait="+waitInternval+" ");
                //waitStart();
                if(waitercauser!=null) System.out.println("RQM:624 c="+waitercauser+" si="+(waitercauser.getByteBuffer().capacity()/waitInternval));
                myLock.wait(waitInternval);
            }
            //waitStop();
        }//else if(DEBUG)System.out.println("RQM:628 not waiting");
    }
    
    static String _(int x){
        switch(x){
            case 0 : return "DEAD";
            case 1 : return "STARTING";
            case 2 : return "WORKING";
            case 3 : return "STOPPING";
            default : return null;
        }
    }
        
}