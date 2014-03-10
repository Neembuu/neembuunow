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

package neembuu.vfs.readmanager;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Comparator;
import neembuu.vfs.file.DownloadCompletedListener;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import neembuu.rangearray.RangeArrayParams;
import neembuu.vfs.progresscontrol.DownloadSpeedProvider;
import neembuu.vfs.progresscontrol.RequestSpeedProvider;
import neembuu.rangearray.RangeArray;
import jpfm.util.ProfileableReadRequest;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpfm.JPfmError;
import jpfm.annotations.NonBlocking;
import jpfm.operations.readwrite.Completer;
import jpfm.operations.readwrite.ReadRequest;
import jpfm.operations.readwrite.SimpleReadRequest;
//import jpfm.util.ReadUtils;
import jpfm.util.SplittedRequestHandler;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.vfs.file.DownloadConstrainHandler;
import net.jcip.annotations.NotThreadSafe;
import static jpfm.util.ReadUtils.*;
import neembuu.vfs.file.TroubleHandler;
import neembuu.vfs.readmanager.WaitForExpansionOrCreateNewConnectionPolicy.Result;

/**
 * ReadQueueManager handles read request for a given 
 * Each file has a separate ReadQueueManager. 
 * ReadQueueManager dispatches read requests to different connections.
 * It also requests for creation of new connections if data is being 
 * requested on a region currently not available.
 * The design gets slightly complicated by the fact that requests often 
 * span over more than one connection.
 * <br/>For example : <br/>
 * Region available : 500000--->599999 600000--->700000<br/>
 * and requested region = 599990--->600010. <br/>
 * For this particular case, the request would be split into 2 parts, 
 * 599990--->599999 600000--->600010
 * and ensure thread safety and atomicity guarantees . <br/>
 * Users of this need not bother themselves about this complication, 
 * but must understand that
 * it is safer and would save time to simply use this class for this 
 * purpose rather than writing 
 * custom code for this same purpose from scratch.
 * @author Shashank Tulsyan
 */
@NotThreadSafe
public final class ReadQueueManager 
            implements TotalFileReadStatistics{
    private final RangeArray<RegionHandler> handlers;
    
    private final NewReadHandlerProvider provider;
    private Logger rqmLogger;
    /*static final boolean DEBUG_READ_QUEUE_MANAGER 
            = GlobalTestSettings.getValue("DEBUG_READ_QUEUE_MANAGER");
    private static final boolean DEBUG_SPLITTED_REQUESTS 
            = GlobalTestSettings.getValue("DEBUG_SPLITTED_REQUESTS");*/

    private volatile boolean disabled = false;
    private volatile long openTime = 0;
    private volatile boolean complete = false;
    
    private volatile long totalDataRequested = 0;
    private volatile int totalNumberOfRequestsMade = 0;
    
    private volatile long lastExternalRequestTime = 0;
    private volatile long lastInternalRequestTime = 0;
    
    private volatile boolean autoCompleteEnabled = true;
    
    private MainDirectionThread mainDirectionThread = new MainDirectionThread();
    
    private final TotalDownloadSpeed totalDownloadSpeed = new TotalDownloadSpeed();
    private final TotalRequestSpeed totalRequestSpeed = new TotalRequestSpeed();
    
    private final LinkedList<DownloadConstrainHandler> constraintedWith = 
            new LinkedList<DownloadConstrainHandler>();
    
    private final WaitForExpansionOrCreateNewConnectionPolicy 
            waitForExpansionOrCreateNewConnectionPolicy;
    
    private final DCH dch_in = new DCH();
    
    private final LinkedList<DownloadCompletedListener> dcls = new LinkedList<DownloadCompletedListener>();

    public ReadQueueManager(NewReadHandlerProvider provider) {
        this.provider = provider;
        this.waitForExpansionOrCreateNewConnectionPolicy = DefaultNewConnectionPolicy.SINGLETON;
        handlers = RangeArrayFactory.newDefaultRangeArray(new RangeArrayParams.Builder().setEntriesNeverDissolve().build());
    }

    public final DownloadConstrainHandler getDownloadConstrainHandler() {
        return dch_in;
    }
    
    private void constraintWithImpl(DownloadConstrainHandler ch){
        /*if(mainDirectionThread.thread_state.get()!=DEAD){
            throw new IllegalStateException("This is in operation. Constraining should be done before mounting the filesystem.");
        }*/
        synchronized (constraintedWith){
            constraintedWith.add(ch);
            Comparator<DownloadConstrainHandler> c = new Comparator<DownloadConstrainHandler> () {

                @Override
                public int compare(DownloadConstrainHandler o1, DownloadConstrainHandler o2) {
                    return o1.index()-o2.index();
                }
            };

            Collections.sort(constraintedWith,c);
        }
    }
    
    private void fullyUnconstrainImpl(){
        synchronized (constraintedWith){
            Iterator<DownloadConstrainHandler> it = constraintedWith.iterator();
            while(it.hasNext()){it.next();it.remove();}
        }
    }

    public void setAutoCompleteEnabled(boolean autoCompleteEnabled) {
        this.autoCompleteEnabled = autoCompleteEnabled;
    }

    public boolean isAutoCompleteEnabled() {
        return autoCompleteEnabled;
    }
    
    public void initLogger(){
        synchronized (handlers.getModLock()){
            if(rqmLogger!=null)throw new IllegalStateException("Already initialized");
            rqmLogger = provider.getReadQueueManagerThreadLogger();
        }
    }
    
    public final RangeArray<RegionHandler> getRegionHandlers() {
        return (RangeArray)handlers;
    }

    private RegionHandler provideNew(long start, int size){
        return provideNew((long)start,(long)start+size-1);
    }
    
    private RegionHandler provideNew(long start, long end){
        RegionHandler  toret = (RegionHandler)provider.provideHandlerFor(start,end);
        return toret;
    }

    public final void open(){
        rqmLogger.log(Level.INFO,"",new Throwable());
        openTime = System.currentTimeMillis();
        synchronized (handlers.getModLock()){
            for(Range<RegionHandler> range : handlers){
                range.getProperty().open();
            }
        }
        
        mainDirectionThread.open();
    }
    
    public final void close(){
        //no need to kill all threads as they die by themselves
        //after a certain period of inactivity.
        rqmLogger.log(Level.INFO,"",new Throwable());
        synchronized (handlers.getModLock()){
            try{
                for(Range<RegionHandler> range : handlers){
                    rqmLogger.info(range.toString()+" closed");
                    range.getProperty().close();
                }
            }catch(ClassCastException cce){
                cce.printStackTrace(System.err);
            }
        }openTime = 0; totalDataRequested = 0;
        mainDirectionThread.close();
    }
    
    public final void closeCompletely()throws Exception {
        try{
            close();
        }catch(Exception ignore){
            //already closed
        }
        synchronized(handlers.getModLock()){
            for(Range<RegionHandler> handler : handlers){
                try{
                    handler.getProperty().closeCompletely();
                }catch(Exception a){
                    a.printStackTrace(System.err);
                }
            }
        }
        provider.getFileStorageManager().close();
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public static enum ReadRequestSplitType {
        SPECIAL_REQUEST,
        LINEAR_READ__NEW_CONNECTION_BEFORE_ALL_EXISTING_CONNECTIONS,
        LINEAR_READ__ZERO_FILL_READ_REQUEST__NEW_CONNECTION_BEFORE_ALL_EXISTING_CONNECTIONS,
        LINEAR_READ__COMPLETELY_INSIDE_A_DOWNLOADED_REGION,
        SPLITTED_READ__START__NEW_CONNECTION_BEFORE_ALL_EXISTING_CONNECTIONS,
        SPLITTED_READ__START__ZERO_FILL_READ_REQUEST__NEW_CONNECTION_BEFORE_ALL_EXISTING_CONNECTIONS,
        SPLITTED_READ__START_OR_INTERMIDIATE__COMPLETELY_INSIDE,
        LINEAR_READ__CROSSES_AUTHORITY_LIMIT,
        SPLITTED_READ__LAST_SPLIT,
        SPLITTED_READ__START_LIES_BETWEEN_TWO_DOWNLOADED_REGIONS__POLICY_FORCED_A_NEW_CONNECTION,
        SPLITTED_READ__START_LIES_BETWEEN_TWO_DOWNLOADED_REGIONS__POLICY_FORCED_ZERO_FILL_READ_REQUEST,
        SPLITTED_READ__START_LIES_BETWEEN_TWO_DOWNLOADED_REGIONS,
        LINEAR_READ___LIES_BETWEEN_TWO_DOWNLOADED_REGIONS__POLICY_FORCED_A_NEW_CONNECTION,
        LINEAR_READ___LIES_BETWEEN_TWO_DOWNLOADED_REGIONS__POLICY_FORCED_ZERO_FILL_READ_REQUEST,
        LINEAR_READ___LIES_BETWEEN_TWO_DOWNLOADED_REGIONS
    }
        
    @NonBlocking(usesJava1_7NIOClasses=false,usesOneThreadPerContinuousChannel=true,usesOneThreadPerRequest=false)
    public final void read(ReadRequest read){
        lastInternalRequestTime = read.getCreationTime();
        readImpl(read);
        // this is done so that we don't hold multiple number of locks
        // if this was done inside the readImpl method, it would have 
        // to be in synchronized block to handlers
        // then we know that when zero requests are handler, lock
        // to handlers of the other RQM would have to be held.
        // Holding multiple locks risks deadlock. Current design is safer
        handleConstrainedRQMs(read.getCreationTime());
    }
    
    private void readImpl(ReadRequest read){    
        mainDirectionThread.mrs = read.getFileOffset();
        mainDirectionThread.mre = read.getFileOffset() + read.getByteBuffer().capacity() - 1;
        
        // <editor-fold defaultstate="collapsed" desc="comment">
        // this is to prevent calls to expandAuthorityLimitCarefully
        // while other connections are expanding and new connecitons are
        // being created.
        // also re-requested read requests will be called from
        // another thread, they need to be handled synchronously
        // Writing seeking logic for a dynamic element is difficult
        // this will result is certain amount of wastage 
        // we will find out later how much it is.</editor-fold>
        synchronized(handlers.getModLock()){ 
            totalDataRequested+=read.getByteBuffer().capacity();
            totalNumberOfRequestsMade++; // single threaded hence safe
            final ProfileableReadRequest pfread = 
                    new ProfileableReadRequestImpl(
                        read, 
                        totalNumberOfRequestsMade, 
                        totalDataRequested);
            if(read instanceof SpecialReadRequest){
                pfread.addProperty(ReadRequestSplitType.SPECIAL_REQUEST);
            }
            
            long requestStart = pfread.getFileOffset();
            long i_th_splitRequestStart = requestStart;
            long requestEnd   = pfread.getFileOffset()+ pfread.getByteBuffer().capacity() - 1;
            SplittedRequestHandler splittedRequestHandler = null;
            int[]indices= handlers.getIndexPair(requestStart,requestEnd);
            
            rqmLogger.log(Level.INFO, "Handlers\t{0}", handlersToString() );
            
            // <editor-fold defaultstate="collapsed" desc="all split cases diagram">
            /*
             * For a range array
             * neembuu.common.RangeArray{
             * index=0 0->49 
             * index=1 251->300
             *  ....
             * }
             * and symbolically
             * Index                     0               1               2               3               4
             * Offset value          A------>B       C------>D       E------>F       G------>H       I------>J
             *
             * where A=0, B=49, C=251 and D=300 .... similarly others
             *
             * s = starting of request
             * e = ending of request (inclusive)
             * x1 = indices[0] and x2 = indices[1]
             *
             * x1!=x2 means that a request spans over more than one read handler
             *
             * All possible cases for starting
             * -------------------------------
             * Problem i_th_splitRequestStart = s & i =1
             * Index                     0               1               2               3               4
             * Offset value          A------>B       C---s-->D       E------>F       G------>H       I------>J
             *                                           |______....
             * Solution (case_start_1)
             * Index                     0               1               2               3               4
             * Offset value          A------>B       C---s-->D       E------>F       G------>H       I------>J
             *                                           |__________|___....   (s---->E-1)
             *
             * Problem i_th_splitRequestStart = s & i =1
             * Index                     0               1               2               3               4
             * Offset value          A------>B       C------>D  s    E------>F       G------>H       I------>J
             *                                                  |______....
             * Solution (case_start_2)
             * Index                     0               1               2               3               4
             * Offset value          A------>B       C------>D  s    E------>F       G------>H       I------>J
             *                                                  |____|__....   (s---->E-1)
             *
             * All possible cases for intermediate (case_interm_1)
             * ------------------------------------
             * i_th_splitRequestStart = E & indices[1] > i > indices[0]
             * requestCurrentStart = E
             * Solution
             * Index                     0               1               2               3               4
             * Offset value          A------>B       C------>D       E------>F       G------>H       I------>J
             *                                                       |______________| (E--->G-1)
             * Connection might be dead. In that case the channel will automatically try to make a new connection.
             *
             * All possible cases for ending
             * -----------------------------
             * i_th_splitRequestStart = e & i = indices[1]
             * Solution (case_end_1)
             * Index                     0               1               2               3               4
             * Offset value          A------>B       C------>D       E------>F       G---e-->H       I------>J
             *                                                                       |___|  (from G to end )
             * Index                     0               1               2               3               4
             * Offset value          A------>B       C------>D       E------>F       G------>H    e  I------>J
             *                                                                       |____________| (from G to e which is lying outside H)
             */
            // </editor-fold>


            //<editor-fold defaultstate="collapsed" desc="If a splited request">
            if(indices[0]!=indices[1]){
                // verfiable by diagram
                //rqmLogger.log(Level.INFO,"Rare case of splitted request offset={0}-->{1}",new Object[]{requestStart,requestEnd});
                rqmLogger.log(Level.INFO,"Rare case of splitted request offset={0}",requestStart);
                splittedRequestHandler = SplittedRequestHandler.createSplittedRequestHandler(pfread,rqmLogger);
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="ELSE{Special handling for requests completely inside a part}">
            else {
                // special handling for requests completely inside a part
                // only handling creation of new connection if required.
                // otherwise case 0 handles this
                if(indices[0]==-1) { // special case
                    rqmLogger.log(Level.INFO,
                            "Requesting new connection before existing connections (unsplitted). File offset={0}",requestStart);

                    final long newConnectionCreationTime = provider.getNewHandlerCreationTime(pfread.getFileOffset());
                    if(newConnectionCreationTime >= Integer.MAX_VALUE){
                        rqmLogger.log(Level.INFO,"Creation of new connection not possible");
                        pfread.addProperty(ReadRequestSplitType.LINEAR_READ__ZERO_FILL_READ_REQUEST__NEW_CONNECTION_BEFORE_ALL_EXISTING_CONNECTIONS);
                        pfread.addProperty(pfread.getFileOffset());
                        pfread.complete(JPfmError.SUCCESS);
                    }else {
                        RegionHandler handler;
                        // indices[0]==indices[1] ==> that indices[1] need not be incremented
                        // after creation of a new connection
                        ////////////////////////////////////////////////

                        RegionHandler newRegionHandler = provideNew(pfread.getFileOffset(),pfread.getByteBuffer().capacity());
                        handler = newRegionHandler;
                        //expandAuthorityLimitCarefully not required as new connection's
                        // authority limit set by constructor
                            pfread.addProperty(ReadRequestSplitType.LINEAR_READ__NEW_CONNECTION_BEFORE_ALL_EXISTING_CONNECTIONS);
                            pfread.addProperty(handler.starting());
                            handler.handleRead(pfread);                    
                    }
                    sendZeroRequestObservation(1,pfread.getCreationTime(),false);// new insertion was done at zero, so send zero after that
                    return;
                }
                
                rqmLogger.log(Level.INFO, "Simple linear request\t{0} in \t{1}",
                        new Object[]{pfread,handlers.get(indices[0])});
                Range<RegionHandler> supplier =  handlers.get(indices[0]);
                if(supplier.ending() > requestEnd){
                    pfread.addProperty(ReadRequestSplitType.LINEAR_READ__COMPLETELY_INSIDE_A_DOWNLOADED_REGION).addProperty(supplier.starting());
                    supplier.getProperty().handleRead(pfread);
                    sendZeroRequestObservation(0,indices[0]-1,pfread.getCreationTime(),false);
                    sendZeroRequestObservation(indices[1]+1,pfread.getCreationTime(),false);
                    return ;
                }//else handled by CASE.L523
                // this simple linear read requests is for completely internal requests only.
                // written separately so that we can concentrate on optimization if desired.
                
            }//</editor-fold>

            if(disabled){pfread.complete(JPfmError.SUCCESS);return;}

            //<editor-fold defaultstate="collapsed" desc="DEBUG_READ_QUEUE_MANAGER">
            rqmLogger.log(Level.INFO,
                    "region={2}-->{3} interatorOver={0}->{1}",
                    new Object[]{indices[0],indices[1],
                        requestStart,requestEnd});//</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="REQUEST_ITERATION">
            
            sendZeroRequestObservation(0, indices[0]-1, pfread.getCreationTime(),false);
            sendZeroRequestObservation(indices[1]+1,pfread.getCreationTime(),false);
            
            REQUEST_ITERATION:
            for (int i = indices[0]; i <= indices[1]; i++) {
                if(i==-1){ // indices[0] is equal to -1 and indices[1] is 0 or greater
                    // first read request on the file to be made ever
                    rqmLogger.log(Level.INFO,"Requesting new connection before existing connections (splitted). File offset={0}",requestStart);
                    ProfileableReadRequest splitread = splittedRequestHandler.split(
                            // we can be sure that i+1 exists
                            handlers.get(i+1).starting() -1  // -1 because limits are inclusive
                            );
                    
                    final long newConnectionCreationTime = provider.getNewHandlerCreationTime(i_th_splitRequestStart);
                    if(newConnectionCreationTime >= Integer.MAX_VALUE){
                        rqmLogger.log(Level.INFO,"Creation of new connection not possible");
                        splitread.addProperty(ReadRequestSplitType.SPLITTED_READ__START__ZERO_FILL_READ_REQUEST__NEW_CONNECTION_BEFORE_ALL_EXISTING_CONNECTIONS).addProperty(pfread.getFileOffset());
                        splitread.complete(JPfmError.SUCCESS);
                    }else {
                        RegionHandler handler;
                        // indices are now shifted because of addition of an element
                        ////////////////////////////////////////////////
                        // fixing indices
                        RegionHandler newRegionHandler = provideNew(
                                pfread.getFileOffset(),handlers.get(i+1).starting() - 1);
                        if(newRegionHandler==null){
                            handleRegionCreationFailed(pfread,pfread.getFileOffset(),handlers.get(i+1).starting()-1);
                            break REQUEST_ITERATION;
                        }
                        //read.getFileOffset()+read.getByteBuffer().capacity()-1));
                        // indices[0]==indices[1] ==> that indices[1] need not be incremented


                        indices[1]++; i++;
                        handler = newRegionHandler;
                        // authority expansion not required for new connection created
                        // constructor updates authority limit
                        splitread.addProperty(ReadRequestSplitType.SPLITTED_READ__START__NEW_CONNECTION_BEFORE_ALL_EXISTING_CONNECTIONS).addProperty(handler.starting());
                        handler.handleRead(splitread);
                    }
                    //updating start for next loop/////////////////////////
                    /*******/i_th_splitRequestStart = handlers.get(i+1).starting() ;
                    /*******/ continue REQUEST_ITERATION; // to make sure we don 't go in some other case
                    //////////////////////////////////////////////////////
                }
                
                
                RegionHandler nextDataProvider = null;
                try{
                    nextDataProvider = handlers.get(i).getProperty();
                }catch(ClassCastException cce){
                    rqmLogger.log(Level.INFO, "", cce);
                    rqmLogger.log(Level.INFO, "handlerati={0}", handlers.get(i) );
                }
                rqmLogger.log(Level.FINE,"locking over range element {0}",nextDataProvider);
                
                int futureAuthorityExpansionScope = 0;// <<<< we changed this from 1 to 0
                if(i_th_splitRequestStart <= nextDataProvider.authorityLimit()+ futureAuthorityExpansionScope){
                    // <editor-fold defaultstate="collapsed" desc="dia">
                    // start case 1
                    // x = requestCurrentStart
                    // D = authority limit
                    //   * Index                     0               1               2
                    //   * Offset value          A------>B       C--x1-->D       E------>F// </editor-fold>
                    if(i<indices[1]){
                        // <editor-fold defaultstate="collapsed" desc="dia">
                        // one and only intermediate case solution : case_interm_1
                        // also solution for case_start1 and case start2
                        //   * Index                     0               1               2               3
                        //   * Offset value          A------>B       C--x1-->D       E---x2->F   x2  G--x2-->H
                        //
                        //         above diagram showing a few possible locations (3) of x2 out of many possibilities// </editor-fold>
                        ProfileableReadRequest splitread = splittedRequestHandler.split(
                                // we can be sure that i+1 exists
                                handlers.get(i+1).starting() -1  // -1 because limits are inclusive
                                );
                        if(endingOffset(splitread) > nextDataProvider.authorityLimit()){
                            nextDataProvider.expandAuthorityLimitCarefully(handlers,endingOffset(splitread));
                        }
                        splitread.addProperty(ReadRequestSplitType.SPLITTED_READ__START_OR_INTERMIDIATE__COMPLETELY_INSIDE).addProperty(nextDataProvider.starting());
                        nextDataProvider.handleRead(splitread);
                        //updating start for next loop/////////////////////////
                        /*******/i_th_splitRequestStart = handlers.get(i+1).starting() ;
                        /*******/ continue REQUEST_ITERATION; // to make sure we don 't go in some other case
                        //////////////////////////////////////////////////////
                    }else {
                        // <editor-fold defaultstate="collapsed" desc="dia">
                        // i == indices[1] since i is not less than indices[1] and cannot be greater
                        // this is last available entry from which data can be extracted
                        // for this read request. therefore this is the last step in loop.
                        //   * Index                     0               1               2               3
                        //   * Offset value          A------>B       C--x1-->D  x2   E------>F       G------>H
                        // OR
                        //   * Index                     0               1                 2               3
                        //   * Offset value          A------>B       C--x1--x2--D       E------>F       G------>H// </editor-fold>
                        if(i==indices[0]){ // i = startIndex = endindex
                            rqmLogger.log(Level.INFO,"case 0={0}",requestStart);
                            // <editor-fold defaultstate="collapsed" desc="dia">
                            // there is only one entry so no need to split
                            // case 0: handled here
                            // x1 = requestStart
                            //   * Index                     0                1               2
                            //   * Offset value          A------>B       C-x1--x2->D       E------>F
                            //request spans over atleast one more connection
                            /// request in a single connection// </editor-fold>
                            if(requestEnd > nextDataProvider.authorityLimit()){
                                nextDataProvider.expandAuthorityLimitCarefully(handlers, requestEnd);
                            }
                            pfread.addProperty(ReadRequestSplitType.LINEAR_READ__CROSSES_AUTHORITY_LIMIT).addProperty(nextDataProvider.starting());
                            nextDataProvider.handleRead(pfread);// seems safe from deadlock and other thread abandones exclusive access to this from time to time.
                            // we are done
                            break REQUEST_ITERATION;
                        }else {
                            // <editor-fold defaultstate="collapsed" desc="dia">
                            // last splitted request
                            // handling both possibilities
                            // C = x1 it does not matter is x2 is inside of outside
                            //   * Index                     0               1               2               3
                            //   * Offset value          A------>B       C=x1-->D  x2   E------>F       G------>H
                            // OR
                            //   * Index                     0               1                 2               3
                            //   * Offset value          A------>B       C=x1--x2--D       E------>F       G------>H// </editor-fold>
                            // we can be sure that splitted request handler is not null
                            
                            if(splittedRequestHandler.isSplittingCompleted()){
                                break REQUEST_ITERATION;
                            }
                            
                            ProfileableReadRequest lastReadRequest = splittedRequestHandler.split(requestEnd);
                            if(requestEnd > nextDataProvider.authorityLimit()){
                                nextDataProvider.expandAuthorityLimitCarefully(handlers, requestEnd);
                            }
                            lastReadRequest.addProperty(ReadRequestSplitType.SPLITTED_READ__LAST_SPLIT).addProperty(nextDataProvider.starting());
                            nextDataProvider.handleRead(lastReadRequest);
                            // seems safe from deadlock and other thread abandones exclusive access to this
                            // from time to time.
                            //we are done
                            break REQUEST_ITERATION;
                        }
                    }
                } else {
                    // <editor-fold defaultstate="collapsed" desc="comment and dia">
                    // x1 = requestCurrentStart
                    // D = authority limit
                    //   * Index                     0               1               2
                    //   * Offset value          A------>B       C------>D  x1   E------>F
                    // this can happen only with the first element in the iterator
                    // or the last element in the iterator iff there is only one element in the iterator
                    // because in all other cases requestCurrentStarting will
                    // be equal to start of an existing entry
                    
                    // we have 2 choices, either to wait for 200->300 to expand
                    // or start a new connection at 350.
                    // the choice ideally would depend on what would be faster
                    // </editor-fold>
                    RegionHandler handler = null;
                    long newConnectionLimit_prevConLim;
                    if(i<indices[1]){
                        // <editor-fold defaultstate="collapsed" desc="dia">
                        // request spans over more than one entry
                        // this is 2nd possible starting of a request
                        // splitting required
                        // x1 = requestCurrentStart = requestStart
                        // D = authority limit
                        //   * Index                     0               1               2
                        //   * Offset value          A------>B       C------>D  x1   E---x2->F
                        // we do not need to lock over the next element because we are refering only to
                        // starting of that element which remains constant throughout the lifetime of the
                        // connection// </editor-fold>
                        newConnectionLimit_prevConLim = handlers.get(i+1).starting() - 1;
                    }else{
                        // <editor-fold defaultstate="collapsed" desc="dia">
                        // this is also the way a connection can start
                        // the only thing is that in this we do not require splitting
                        // This actually the most common thing which happens when we seek a video
                        // x1 = requestCurrentStart = requestStart
                        // D = authority limit
                        //   * Index                     0               1                  2
                        //   * Offset value          A------>B       C------>D  x1  x2  E------>F// </editor-fold>
                        newConnectionLimit_prevConLim = i_th_splitRequestStart + pfread.getByteBuffer().capacity() -1 ; // -1 because limits are inclusive
                    }
                    
                    RegionHandler immediatePreviousRegion = handlers.get(i).getProperty();
                    final long newConnectionCreationTime = provider.getNewHandlerCreationTime(i_th_splitRequestStart);
                    final Result newConnectionDecision = waitForExpansionOrCreateNewConnectionPolicy.result(
                            i_th_splitRequestStart,
                            newConnectionCreationTime,
                            immediatePreviousRegion.getThrottleStatistics().getDownloadSpeed_KiBps(),
                            i_th_splitRequestStart - immediatePreviousRegion.ending(),
                            immediatePreviousRegion.fileSize());
                    if(newConnectionDecision==Result.ZERO_FILL_THIS_READ_REQUEST){
                        // rapidshare type of links
                        handler = immediatePreviousRegion; // same as for expansion but authority limit not increased
                        rqmLogger.log(Level.FINE, "Zero filling as creation of new connection difficult at {0}, time required {1}",new Object[]{ i_th_splitRequestStart,newConnectionCreationTime});
                    }else if(newConnectionDecision==Result.NEW_CONNECTION_SHOULD_BE_CREATED){
                        rqmLogger.log(Level.FINE, "Creation of new connection required at {0}", i_th_splitRequestStart);
                        RegionHandler newRegionHandler = provideNew(i_th_splitRequestStart,newConnectionLimit_prevConLim);
                        if(newRegionHandler==null){
                            handleRegionCreationFailed(pfread,i_th_splitRequestStart,newConnectionLimit_prevConLim);
                            break REQUEST_ITERATION;
                        }
                        sendZeroRequestObservation(indices[0],indices[0],pfread.getCreationTime(),false); 
                        // sending zero because new element after this is handling read, this is not
                        
                        
                        // indices are now shifted because of addition of an element
                        // fixing indices
                        indices[1]++; i++;
                        handler = newRegionHandler;
                    }else if(newConnectionDecision==Result.WAIT_FOR_EXPANSION){
                        handler = immediatePreviousRegion;
                        immediatePreviousRegion.
                                expandAuthorityLimitCarefully(handlers, newConnectionLimit_prevConLim);
                    }else {
                        throw new UnsupportedOperationException("A new policy, not implemented, was applied on this situation "+newConnectionDecision);
                    }
                    
                    if(i<indices[1]){
                        // <editor-fold defaultstate="collapsed" desc="dia">
                        // request spans over more than one entry
                        // splitting required
                        // x1 = requestCurrentStart = requestStart
                        // D = authority limit
                        //   * Index                     0               1               2
                        //   * Offset value          A------>B       C------>D  x1   E---x2->F
                        // request spans over more than one entry
                        // 
                        // also we are sure that request handler is not null// </editor-fold>
                        ProfileableReadRequest splittedFirstReadRequest = splittedRequestHandler.split(newConnectionLimit_prevConLim);
                        //updating start for next loop/////////////////////////
                        /*******/i_th_splitRequestStart = newConnectionLimit_prevConLim + 1; // this is also equal to starting of next request
                        //////////////////////////////////////////////////////
                        if(endingOffset(splittedFirstReadRequest) > handler.authorityLimit()){
                            handler.expandAuthorityLimitCarefully(handlers,endingOffset(splittedFirstReadRequest));
                        }
                        
                        if(newConnectionDecision==Result.NEW_CONNECTION_SHOULD_BE_CREATED)
                            splittedFirstReadRequest.addProperty(ReadRequestSplitType.SPLITTED_READ__START_LIES_BETWEEN_TWO_DOWNLOADED_REGIONS__POLICY_FORCED_A_NEW_CONNECTION).addProperty(handler.starting());
                        else if(newConnectionDecision==Result.WAIT_FOR_EXPANSION)
                            splittedFirstReadRequest.addProperty(ReadRequestSplitType.SPLITTED_READ__START_LIES_BETWEEN_TWO_DOWNLOADED_REGIONS).addProperty(handler.starting());
                        
                        if(newConnectionDecision==Result.ZERO_FILL_THIS_READ_REQUEST){
                            splittedFirstReadRequest.addProperty(ReadRequestSplitType.SPLITTED_READ__START_LIES_BETWEEN_TWO_DOWNLOADED_REGIONS__POLICY_FORCED_A_NEW_CONNECTION).addProperty(handler.starting());
                            splittedFirstReadRequest.complete(JPfmError.SUCCESS);
                        }else
                            handler.handleRead(splittedFirstReadRequest);
                        //notify about arrival of new request
                    }else {
                        // <editor-fold defaultstate="collapsed" desc="dia">
                        // we have a single request
                        // x1 = requestCurrentStart = requestStart
                        // D = authority limit
                        //   * Index                     0               1                  2
                        //   * Offset value          A------>B       C------>D  x1  x2  E------>F// </editor-fold>
                        // Authority expansion shouldn't be required as the connection was created
                        // just a few lines above. But for peace of mind, we ignore this redundancy.
                        if(endingOffset(pfread) > handler.authorityLimit() && newConnectionDecision!=Result.ZERO_FILL_THIS_READ_REQUEST){
                            handler.expandAuthorityLimitCarefully(handlers,endingOffset(pfread));
                        }
                        if(newConnectionDecision==Result.NEW_CONNECTION_SHOULD_BE_CREATED)
                            pfread.addProperty(ReadRequestSplitType.LINEAR_READ___LIES_BETWEEN_TWO_DOWNLOADED_REGIONS__POLICY_FORCED_A_NEW_CONNECTION).addProperty(handler.starting());
                        else if(newConnectionDecision==Result.WAIT_FOR_EXPANSION)
                            pfread.addProperty(ReadRequestSplitType.LINEAR_READ___LIES_BETWEEN_TWO_DOWNLOADED_REGIONS).addProperty(handler.starting());
                        
                        if(newConnectionDecision==Result.ZERO_FILL_THIS_READ_REQUEST){
                            pfread.addProperty(ReadRequestSplitType.LINEAR_READ___LIES_BETWEEN_TWO_DOWNLOADED_REGIONS__POLICY_FORCED_ZERO_FILL_READ_REQUEST).addProperty(handler.starting());
                            pfread.complete(JPfmError.SUCCESS);
                        }else
                            handler.handleRead(pfread);
                        //notify about arrival of new request
                        break REQUEST_ITERATION;// we are done
                    }
                }
            }
            //</editor-fold>
            
            //<editor-fold defaultstate="collapsed" desc="checking if splitting was done correctly">
            if(splittedRequestHandler!=null){
                rqmLogger.info(splittedRequestHandler.toString());
                if(!splittedRequestHandler.isSplittingCompleted()){
                    throw new IllegalStateException("Implementation error");
                }
            }
            //</editor-fold>
        }
    }
    
    private String handlersToString(){
        RangeArray rangeArray = handlers;
        StringBuilder br=new StringBuilder(100);
        //br.append(rangeArrayDisplayName);
        br.append("{\n");
        Iterator<Range> it = rangeArray.iterator();
        int j=0;
        while(it.hasNext()){
            //long start,long start,DownloadManager.Downloader d,boolean ver
            Range next = it.next();
            br.append("[");br.append(j);br.append("] ");
            br.append(regionHandlerToString(next));
            br.append("\n");
            j++;
        }br.append("}");
        return br.toString();
    }
    
    private String regionHandlerToString(Range range){
        if(range.getProperty() instanceof RegionHandler){
            RegionHandler rh = (RegionHandler)range.getProperty();
            String basic = rh.getClass().getSimpleName()+"{"+
                    rh.starting()+"-->"+rh.ending()+
                    " ,authl="+rh.authorityLimit()+" isAlive="+rh.isAlive()+ 
                    " ,size="+(rh.ending() - rh.starting() +1 )
                    +" ,isMain="+rh.isMainDirectionOfDownload()
                    + "}";
            
            String[]pendingRq = rh.getPendingOperationsAsString();
            for (int i = 0; i < pendingRq.length; i++) {
                basic = basic +"\n\t\t" + pendingRq[i];
            }
            return basic;
        }
        return range.getProperty().getClass().getSimpleName()+"{"+range.starting()+"-->"+range.ending()+"}";
    }
    
    private void sendZeroRequestObservation(int ele_s/*inclusive*/, long creationTime,boolean x){
        synchronized (handlers.getModLock()){
            sendZeroRequestObservation(ele_s, handlers.size()-1,creationTime,x);
        }
    }
    
    // invoked from other RQMs only
    private void receiveExternalZeroRequestObservationImpl(DownloadConstrainHandler src,long creationTime){
        if(src==this.dch_in){throw new IllegalArgumentException("Should be invoked from an external RQM");}
        
        synchronized (handlers.getModLock()){
            sendZeroRequestObservation(0, creationTime,true);
        }
    }
    
    private void sendZeroRequestObservation(int ele_s/*inclusive*/, int ele_e/*inclusive*/, long creationTime,boolean x){
        synchronized (handlers.getModLock()){
            // lock held handlers.getModLock()
            if(ele_s>=handlers.size())return;
            for (int i = ele_s; i <= ele_e; i++) {
                rqmLogger.log(Level.INFO,"sendingzero"+(x?" external =":" =")
                        + handlers.get(i));
                try{
                    (handlers.get(i).getProperty()).addNewZeroRequestObservation(creationTime);
                }catch(ClassCastException cce){
                    rqmLogger.log(Level.SEVERE, handlers.get(i).toString(),cce);
                    throw new RuntimeException(cce);
                    //ignore
                }
            }
        }
        // lock still held
    }
    
    private void handleConstrainedRQMs(long creationTime){
        checkMessagesImpl(); // check for self
        for(DownloadConstrainHandler dch : constraintedWith){
            //rqmLogger.log(Level.INFO,"sending_external_zero="+dch);
            dch.receiveExternalZeroRequestObservation(this.dch_in,creationTime);
            dch.checkMessages();// check for others
        }
    }
    
    private void checkMessagesImpl(){
        if(mainDirectionThread.requiredConnectionAtZero.compareAndSet(true, false)){
            // make a fake read request
            SpecialReadRequest rr = new SpecialReadRequest();
            rqmLogger.log(Level.SEVERE,"Received a special request to create conneciton at zero. Sending fake request "+rr);
            read(rr);
        }else {
            // nothing happend
        }
    }
    
    private static final class SpecialReadRequest implements ReadRequest{
        ReadRequest rr = new SimpleReadRequest(ByteBuffer.allocate(1), 0);

        @Override
        public void setCompleter(Completer completehandler) {
            rr.setCompleter(completehandler);
        }

        @Override
        public boolean isCompleted() {
            return rr.isCompleted();
        }

        @Override
        public void handleUnexpectedCompletion(Exception exception) {
            rr.handleUnexpectedCompletion(exception);
        }

        @Override
        public long getFileOffset() {
            return rr.getFileOffset();
        }

        @Override
        public JPfmError getError() throws IllegalStateException {
            return rr.getError();
        }

        @Override
        public long getCreationTime() {
            return rr.getCreationTime();
        }

        @Override
        public long getCompletionTime() {
            return rr.getCompletionTime();
        }

        @Override
        public ByteBuffer getByteBuffer() {
            return rr.getByteBuffer();
        }

        @Override
        public void complete(JPfmError error) throws IllegalArgumentException, IllegalStateException {
            rr.complete(error);
        }

        @Override
        public void complete(JPfmError error, int actualRead, Completer completer) throws IllegalArgumentException, IllegalStateException {
            rr.complete(error, actualRead, completer);
        }

        @Override
        public boolean canComplete(Completer completehandler) {
            return rr.canComplete(completehandler);
        }
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="waitstop_notused">
    // private long startTime = 0 , endTime = 0 ;
    //    private void waitStart(){
    //        startTime = System.nanoTime();
    //        if(DEBUG)LOGGER.log(Level.INFO, "waiting");
    //    }
    //    private void waitStop(){
    //        endTime = System.nanoTime();
    //        if(DEBUG)LOGGER.log(Level.INFO, "done waiting = "+(endTime - startTime)/1000000 /*millisecs*/ );
    //    }
    //</editor-fold>    
    
    private static final class DefaultNewConnectionPolicy 
                implements WaitForExpansionOrCreateNewConnectionPolicy {
        
        private DefaultNewConnectionPolicy (){}
        
        private static final DefaultNewConnectionPolicy 
                SINGLETON
                    = new DefaultNewConnectionPolicy();

        @Override
        public final String toString() {
            return  "1. Assumes that there is no harm in creating many connection.\n"+
                    "2. Finds what would take lesser time, new connection creation or downloading till requirement\n"+
                    "3. Zero fill in case of infinite connection time\n"+
                    "4. Makes slightly biased decision :\n"+
                    "\tif creation of a new connection is better, but the advantage is less"+
                    "\tthen new connection is not created.";
        }

        @Override
        public Result result(
                long offset, // ignored in our case, might be useful in case of bittorrent
                long estimatedTimeTakenForCreatingANewConnection,
                double previousConnectionSpeed_inKiBps,
                long distanceToCover,
                long fileSize) {            
            double estimateTimeForExpansion = (
                    distanceToCover*1.0d
                    /
                    (previousConnectionSpeed_inKiBps*1024)
                )*1000; //convert to milliseconds
            /*System.err.println("offset="+offset+" estimateTimeForExpansion="+estimateTimeForExpansion
                    +" estimatedTimeTakenForCreatingANewConnection="+estimatedTimeTakenForCreatingANewConnection+""
                    + " d="+distanceToCover+" speed="+previousConnectionSpeed_inKiBps);*/
            if(estimatedTimeTakenForCreatingANewConnection<0){
                throw new IllegalArgumentException("Estimated time taken for creating new connection cannot be negative");
            }
            estimatedTimeTakenForCreatingANewConnection = Math.min(estimatedTimeTakenForCreatingANewConnection, Integer.MAX_VALUE);
            if(
                    estimateTimeForExpansion - estimatedTimeTakenForCreatingANewConnection
                    <
                    1000
                ){
                // expansion will take less time or will not take too long comparatively
                // too long is defined as 1000ms
                
                if(estimateTimeForExpansion>7000 && estimatedTimeTakenForCreatingANewConnection<10000){
                    return Result.NEW_CONNECTION_SHOULD_BE_CREATED;
                } // is expansion will take more 
                // than x seconds do not expand. This is to avoid excessive downloading.
                
                if(estimatedTimeTakenForCreatingANewConnection==Integer.MAX_VALUE){
                    if(estimateTimeForExpansion<=7000)
                        return Result.WAIT_FOR_EXPANSION;//expand
                    return Result.ZERO_FILL_THIS_READ_REQUEST;
                }
                return Result.WAIT_FOR_EXPANSION;//expand
            }
            
            return Result.NEW_CONNECTION_SHOULD_BE_CREATED;//make new connection
        }
        
    }
        
    @Override
    public final long totalDataRequestedSoFar() {
        return totalDataRequested;
    }

    @Override
    public final int totalNumberOfRequestsMade() {
        return totalNumberOfRequestsMade;
    }
        
    private final class TotalDownloadSpeed 
            implements 
                DownloadSpeedProvider{
        
        @Override
        public double getDownloadSpeed_KiBps() {
            //if(openTime==0)return 0;
            
            double totalDownloadSpeed=0;
            
            UnsyncRangeArrayCopy<RegionHandler> regionHandlers
                    = handlers.tryToGetUnsynchronizedCopy();
            for (int i = 0; i < regionHandlers.size(); i++) {
                RegionHandler regionHandler = regionHandlers.get(i).getProperty();
                if(regionHandler!=null)
                    if(regionHandler.isAlive())
                        totalDownloadSpeed+=
                                regionHandlers.get(i).getProperty()
                                .getThrottleStatistics().averageDownloadSpeed_Bps();
            }
            
            return (totalDownloadSpeed  /1024);
        }

    }
    
    private final class TotalRequestSpeed implements RequestSpeedProvider {
        @Override
        public final double getRequestSpeed_KiBps() {
            if(openTime==0)return 0;
            return totalDataRequested*1000
                    /
                   ((System.currentTimeMillis() - openTime)*1024);
        }
    }

    @Override
    public long getNewHandlerCreationTime(long offset) {
        return provider.getNewHandlerCreationTime(offset);
    }
    
    @Override
    public DownloadSpeedProvider getTotalAverageDownloadSpeedProvider() {
        return totalDownloadSpeed;
    }

    @Override
    public RequestSpeedProvider getTotalAverageRequestSpeedProvider() {
        return totalRequestSpeed;
    }

    @Override
    public final UnsyncRangeArrayCopy<ReadRequestState> getReadRequestStates() {
        return handlers.tryToGetUnsynchronizedCopy();
    }
    
    private void handleRegionCreationFailed(ReadRequest readRequest, long start, long end){
        rqmLogger.log(Level.SEVERE, "Zero setting {0} and completing as region'{'{1}-->{2}'}' could not be created. Reason mentioned in earlier log message", new Object[]{readRequest, start, end});
        readRequest.complete(JPfmError.SUCCESS);
    }
    
    public void addDownloadCompletedListener(DownloadCompletedListener dcl) {
        synchronized (dcls){
            dcls.add(dcl);
        }
    }

    public void removeDownloadCompletedListener(DownloadCompletedListener dcl) {
        synchronized (dcls){
            dcls.remove(dcl);
        }
    }
    
    private static int DEAD = 0, STARTING = 1, WORKING = 2, STOPPING = 3;
    private final class MainDirectionThread implements Runnable {
        private final AtomicInteger thread_state = new AtomicInteger(DEAD); 
        // main direction read handler
        private RegionHandler mdrh = null;
        // most recently request start and end
        private volatile long mrs = -1, mre = -1;
        
        private final Object sleepLock = new Object();
        
        int retry = 0;
        long lastCheckTime = 0;
        
        boolean filesPriorToMeCompleted = true;
        private final AtomicBoolean requiredConnectionAtZero = new AtomicBoolean(false);
        int externalMainCnt = 0;
        
        public MainDirectionThread() {
            
        }
        
        private void open(){
            rqmLogger.log(Level.INFO,"opening MainDirectionThread "+_(),new Throwable());
            synchronized (sleepLock){
                sleepLock.notifyAll();
            }
            
            if(thread_state.compareAndSet(DEAD, STARTING)){
                retry = 0;
                startThread();
            }else {
                if(retry>10){
                    throw new IllegalStateException("Incorrectly opening. Current state = "+_());
                }
                try{
                    Thread.sleep(150);
                }catch(Exception a){
                    
                }
                open();retry++;
            }
        }
        
        private void close(){
            rqmLogger.log(Level.INFO,"closing MainDirectionThread "+_(),new Throwable());
            synchronized (sleepLock){
                sleepLock.notifyAll();
            }
            
            
            if(thread_state.compareAndSet(WORKING,STOPPING)){
                retry = 0;
            }else {
                if(thread_state.get()==DEAD){
                    throw new IllegalStateException("Already dead");
                }
                if(retry>3){
                    throw new IllegalStateException("Incorrectly closing. Current state = "+_());
                }
                try{
                    Thread.sleep(150);
                }catch(Exception a){
                    
                }
                close();retry++;
            }
        }
        
        private void startThread(){
            Thread t = new Thread(
                    this,
                    MainDirectionThread.class.getSimpleName()+"{"+ReadQueueManager.this.provider.getName()+"}");
            t.setDaemon(true);
            t.start();
        }
        
        @Override
        public void run() {
            rqmLogger.info("MainDirectionThread just started "+_());
            if(!thread_state.compareAndSet(STARTING, WORKING)){
                throw new IllegalStateException("Thread was not set to starting");
            }
            
            while(!thread_state.compareAndSet(STOPPING,DEAD)){
                try{
                    IF:
                    if(!autoCompleteEnabled){
                        changeMainDirc(906,null);
                    }else if(lastInternalRequestTime > lastExternalRequestTime){
                        if(!complete)
                            iterateAndFindInternalMain(false);
                        else {
                            checkExternal();
                            if(externalMainCnt==0){
                                rqmLogger.info("Completed but No external main");
                            }                            
                            if(filesPriorToMeCompleted){
                                rqmLogger.info("Completed and prior files completed");
                                break IF;
                            }else {
                                rqmLogger.info("Completed but a prior file has not completed yet");
                            }
                        }
                    }else {
                        changeMainDirc(945,null);
                        if(!complete){
                            checkExternal();
                            if(filesPriorToMeCompleted && externalMainCnt==0){
                                rqmLogger.info("Prior files completed and not even 1 external main, finding main here");
                                iterateAndFindInternalMain(true); break IF;
                            }else {
                                rqmLogger.info("A prior file has not completed yet");
                            }
                        }
                    }
                    long sle = 0;
                    if(!complete){sle = 150;}else {sle = 1000;}
                    synchronized (sleepLock){
                        sleepLock.wait(sle);
                    }
                    findRequestsPendingSinceALongTime();
                    checkAndSetComplete_2();
                }catch(Exception a){
                    rqmLogger.log(Level.SEVERE,"",a);
                }
            }
            changeMainDirc(939,null);
            rqmLogger.info("MainDirectionThread dead "+_());
        }
        
        private void checkExternal(){
            int ind = 0; externalMainCnt = 0; filesPriorToMeCompleted = true;
            FOR:
            for(DownloadConstrainHandler dch : constraintedWith){
                if(dch.hasAMainConnection())externalMainCnt++;
            }
            boolean dch_in_is_first_split = true; 
            FOR:
            for(DownloadConstrainHandler dch : constraintedWith){
                if(dch.index() < dch_in.index()){
                    dch_in_is_first_split = false; 
                    break;
                }
            }
            if(dch_in_is_first_split){
                filesPriorToMeCompleted = true; 
                return;
            }
            
            FOR:
            for(DownloadConstrainHandler dch : constraintedWith){
                if(dch.index() < dch_in.index){
                    if(!dch.isComplete()){
                        //if(!dch.hasAMainConnection()){
                            filesPriorToMeCompleted = false;
                            rqmLogger.info("File not completed "+dch);
                            break FOR;
                        //}
                    }
                }
                ind++;
            }
        }
        
        
        private void findRequestsPendingSinceALongTime(){
            if(System.currentTimeMillis()< lastCheckTime + 
                    Math.min(
                            TroubleHandler.DEFAULT_CHECKING_INTERAL_MILLISECONDS,
                            provider.getTroubleHandler().preferredCheckingInterval())){
                return;
            }
            lastCheckTime = System.currentTimeMillis();
            UnsyncRangeArrayCopy<RegionHandler> uh 
                        = handlers.tryToGetUnsynchronizedCopy();
            long atlestMillisec = Math.min(
                    TroubleHandler.DEFAULT_PENDING_ATLEAST_FOR_MILLISECONDS,
                    provider.getTroubleHandler().pendingAtleastFor());
            LinkedList<ReadRequest> pendingRrs = new LinkedList<ReadRequest>();
            // concept of main should be in favour of those connection that are starving
            for (int i = 0; i < uh.size(); i++) {
                RegionHandler rh = uh.get(i).getProperty();
                if(rh.hasPendingRequests()){
                    rh.fillInOperationsPendingSince(pendingRrs, atlestMillisec);
                }
            }
            if(!pendingRrs.isEmpty())
                provider.getTroubleHandler().readRequestsPendingSinceALongTime(pendingRrs,atlestMillisec);
        }
        
        private void iterateAndFindInternalMain(boolean priorCompleted){
            UnsyncRangeArrayCopy<RegionHandler> uh 
                        = handlers.tryToGetUnsynchronizedCopy();
            //Logic is 
            //where the most recent requests are present that is the main connection.
            //is that connection has collided we try the next one.
            //if the request start and end don't lie in the same connection.
            //it means it is a complex splitted request, don't set main conn in this case
            // to prevent starvation of splits
            // UPDATE : we are checking only ending now. Because that is a more common case
            // UPDATE : we are now checking starting as well, and comparing against authority limit
            RegionHandler found = null;
            boolean reachedEnd = false;
            
            // concept of main should be in favour of those connection that are starving
            for (int i = 0; i < uh.size(); i++) {
                RegionHandler rh = uh.get(i).getProperty();
                if(rh==null)continue;
                
                if(uh.size()>i+1 && uh.get(i).ending()+1==uh.get(i+1).starting()){
                    // collided
                }else if(i==uh.size()-1 && rh.ending()>=uh.get(i).getProperty().fileSize()-1){
                    //collided
                }else if(rh.hasPendingRequests()){
                    found = rh; changeMainDirc(962,found); return;
                }
            }
            
            OUTER_LOOP:
            for (int i = 0; i < uh.size(); i++) {
                if(mrs<=uh.get(i).getProperty().authorityLimit()+1 && mre>=uh.get(i).starting()){
                    INNER_LOOP:
                    for (int j = i; j < uh.size(); j++) {
                        if(uh.size()>j+1 && uh.get(j).ending()+1==uh.get(j+1).starting()){
                            // collided
                        }else if(j==uh.size()-1 && uh.get(j).ending()>=uh.get(j).getProperty().fileSize()-1){
                            reachedEnd = true;
                        }else {
                            found = uh.get(j).getProperty();
                            if(found.ending()+1>=found.fileSize()){
                                reachedEnd = true;
                                found = null;
                                break OUTER_LOOP;
                            }
                            break OUTER_LOOP;
                        }
                    }
                }
            }

            if(reachedEnd || priorCompleted){
                found = checkAndSetComplete_1();
            }

            changeMainDirc(998,found);
        }
        
        private RegionHandler checkAndSetComplete_1(){
            UnsyncRangeArrayCopy<RegionHandler> uh 
                        = handlers.tryToGetUnsynchronizedCopy();
            RegionHandler found = null;
            
            // The first main connection that we make should 
            // start from zero. If there is no connection at zero
            // we cannot set it to main. So the first thing to
            // ensure is that a region must exist at zero.
            if(uh.size()==0 || uh.get(0).starting()>0){
                // the problem is there is no connection starting from offset 0
                // we cannot create one, we can only set main

                // read request must be atleast along 1 of them files
                // handleConstrainedRQMs does this for us
                // one the file will sense this flag and do the appropriate
                rqmLogger.log(Level.INFO,"We require a connection from offset 0");
                if(!requiredConnectionAtZero.compareAndSet(false, true)){
                    rqmLogger.log(Level.INFO,"Request for connection was already send");
                }
                return null; // we shall wait for zero region to be created.
                // then we can set it as main :) using the code below
            }
            
            OUTER_LOOP:
            for (int i = 0; i < uh.size(); i++) {
                INNER_LOOP:
                for (int j = i; j < uh.size(); j++) {
                    if(uh.size()>j+1 && uh.get(j).ending()+1==uh.get(j+1).starting()){
                       // collided
                    }else if(j==uh.size()-1 && uh.get(j).ending()>=uh.get(j).getProperty().fileSize()-1){
                        //collided
                    }else {
                        // setting first gap as main, we are filling gaps now
                        found = uh.get(j).getProperty();
                        rqmLogger.log(Level.INFO,"MainDirectionThread filling gap "+found);
                        if(found.ending()+1>=found.fileSize()){
                            rqmLogger.log(Level.INFO,"Looks like entire file downloaded cannot set main");    
                            found = null;
                            complete();
                        }
                        break OUTER_LOOP;
                    }
                }
            }
            return found;
        }
        
        private void checkAndSetComplete_2(){
            UnsyncRangeArrayCopy<RegionHandler> uh 
                        = handlers.tryToGetUnsynchronizedCopy();
            int i =0;
            if(uh.size()==0 || uh.get(0).starting()>0)return;//there must be an entry starting from zero
            OUTER_LOOP:
            /*for (int i = 0; i < uh.size(); i++) {
                INNER_LOOP:*/
                for (int j = i; j < uh.size(); j++) {
                    if(uh.size()>j+1 && uh.get(j).ending()+1==uh.get(j+1).starting()){
                       // collided
                    }else if(j==uh.size()-1 && uh.get(j).ending()>=uh.get(j).getProperty().fileSize()-1){
                        //collided
                        rqmLogger.log(Level.INFO,"Looks like entire file downloaded cannot set main");
                        complete();
                    }else {
                        break OUTER_LOOP;
                    }
                }
            //}
        }
        
        private void complete(){
            if(!complete){
                rqmLogger.log(Level.INFO,"File Completed");
                complete = true;
                synchronized (dcls){
                    for(DownloadCompletedListener dcl : dcls){
                        dcl.downloadCompleted();
                    }
                }
            }
            changeMainDirc(1146, null);
        }
        
        private void changeMainDirc(int l,RegionHandler rh){
            if(rh!=mdrh){
                rqmLogger.info("changing main to "+rh+" l="+l);
            }
            
            if(mdrh!=null){
                mdrh.setMainDirectionOfDownload(false);
            }
            mdrh = rh;
            if(rh!=null){
                rh.setMainDirectionOfDownload(true);
            }
        }
        
        private String _(){
            switch (thread_state.get()) {
                case 0:
                    return "DEAD";
                case 1:
                    return "STARTING";
                case 2:
                    return "WORKING";
                case 3:
                    return "STOPPING";
                default:
                    throw new AssertionError();
            }
        }
    }
    
    private final class DCH implements DownloadConstrainHandler{
        private int index = -2;

        @Override
        public void checkMessages() {
            ReadQueueManager.this.checkMessagesImpl();
        }
        
        @Override
        public boolean hasAMainConnection() {
            return ReadQueueManager.this.mainDirectionThread.mdrh != null;
        }
        
        @Override
        public String toString() {
            return DCH.class.getName()+"{"+ReadQueueManager.this.provider.getName()+"}";
        }
        
        @Override
        public void receiveExternalZeroRequestObservation(DownloadConstrainHandler src, long creationTime) {
            lastExternalRequestTime = creationTime;
            receiveExternalZeroRequestObservationImpl(src, creationTime);
        }
        
        @Override
        public void constraintWith(DownloadConstrainHandler dch) {
            constraintWithImpl(dch);
        }

        @Override
        public void unConstrain() {
            fullyUnconstrainImpl();
        }

        @Override
        public boolean isComplete() {
            return complete;
        }

        @Override
        public boolean hasPendingRequests() {
            UnsyncRangeArrayCopy<RegionHandler> uhandlers
                    = handlers.tryToGetUnsynchronizedCopy();
            for (int i = 0; i < uhandlers.size(); i++) {
                if(uhandlers.get(i).getProperty().hasPendingRequests())
                    return true;
            }return false;
        }

        @Override
        public int index() {
            return index;
        }

        @Override
        public void setIndex(int index) {
            if(this.index!=-2)throw new IllegalStateException("Already initialized to "+this.index);
            this.index = index;
        }
        
        @Override
        public boolean isAutoCompleteEnabled() {
            return ReadQueueManager.this.isAutoCompleteEnabled();
        }
    }
    
}