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

package neembuu.vfs.readmanager.rqm;

import neembuu.vfs.file.DownloadCompletedListener;
import java.util.LinkedList;
import java.util.List;
import neembuu.rangearray.RangeArrayParams;
import neembuu.rangearray.RangeArray;
import jpfm.util.ProfileableReadRequest;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpfm.JPfmError;
import jpfm.annotations.NonBlocking;
import jpfm.operations.readwrite.ReadRequest;
import jpfm.util.SplittedRequestHandler;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeArrayFactory;
import neembuu.vfs.file.DownloadConstrainHandler;
import net.jcip.annotations.NotThreadSafe;
import static jpfm.util.ReadUtils.*;
import neembuu.vfs.file.TroubleHandler;
import neembuu.vfs.readmanager.NewReadHandlerProvider;
import neembuu.vfs.readmanager.RegionHandler;
import neembuu.vfs.readmanager.TotalFileReadStatistics;
import neembuu.vfs.readmanager.WaitForExpansionOrCreateNewConnectionPolicy;
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
public final class ReadQueueManager {
    private final RangeArray<RegionHandler> handlers;
    
    private final NewReadHandlerProvider provider;
    private Logger rqmLogger;

    private volatile boolean autoCompleteEnabled = true;
    
    private final MainDirectionThread mainDirectionThread;
    private final TotalFileReadStatisticsImpl totalReadStatistics;
    private final WaitForExpansionOrCreateNewConnectionPolicy 
            waitForExpansionOrCreateNewConnectionPolicy;
    
    private final DCH myDch;
    
    private final LinkedList<DownloadCompletedListener> dcls = new LinkedList<DownloadCompletedListener>();

    public ReadQueueManager(NewReadHandlerProvider provider) {
        this.provider = provider;
        this.waitForExpansionOrCreateNewConnectionPolicy = DefaultNewConnectionPolicy.SINGLETON;
        handlers = RangeArrayFactory.newDefaultRangeArray(new RangeArrayParams.Builder().setEntriesNeverDissolve().build());
        totalReadStatistics = new TotalFileReadStatisticsImpl(provider, handlers);
        myDch = new DCH(rQM_Access);
        mainDirectionThread =  new MainDirectionThread(MDT_RQMAccess);
    }

    public TotalFileReadStatistics getTotalFileReadStatistics() {
        return totalReadStatistics;
    }
    
    public final DownloadConstrainHandler getDownloadConstrainHandler() {
        return myDch;
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
        totalReadStatistics.markOpenTime();
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
        }totalReadStatistics.close();
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

    private void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    private boolean isDisabled() {
        return disabled;
    }
        
    @NonBlocking(usesJava1_7NIOClasses=false,usesOneThreadPerContinuousChannel=true,usesOneThreadPerRequest=false)
    public final void read(ReadRequest read){
        mainDirectionThread.reportMostRecentRequest(read);
        
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
            totalReadStatistics.newDataRequested(read);
            final ProfileableReadRequest pfread =  new ProfileableReadRequestImpl(read,totalReadStatistics);
            
            long requestStart = pfread.getFileOffset();
            long i_th_splitRequestStart = requestStart;
            long requestEnd   = pfread.getFileOffset()+ pfread.getByteBuffer().capacity() - 1;
            SplittedRequestHandler splittedRequestHandler = null;
            int[]indices= handlers.getIndexPair(requestStart,requestEnd);
            
            rqmLogger.log(Level.INFO, "Handlers\t{0}", Utils.handlersToString(handlers.iterator()) );
            
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
                    myDch.sendZeroRequestObservation(1,pfread.getCreationTime(),false);// new insertion was done at zero, so send zero after that
                    return;
                }
                
                rqmLogger.log(Level.INFO, "Simple linear request\t{0} in \t{1}",
                        new Object[]{pfread,handlers.get(indices[0])});
                Range<RegionHandler> supplier =  handlers.get(indices[0]);
                if(supplier.ending() > requestEnd){
                    pfread.addProperty(ReadRequestSplitType.LINEAR_READ__COMPLETELY_INSIDE_A_DOWNLOADED_REGION).addProperty(supplier.starting());
                    supplier.getProperty().handleRead(pfread);
                    myDch.sendZeroRequestObservation(0,indices[0]-1,pfread.getCreationTime(),false);
                    myDch.sendZeroRequestObservation(indices[1]+1,pfread.getCreationTime(),false);
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
            
            myDch.sendZeroRequestObservation(0, indices[0]-1, pfread.getCreationTime(),false);
            myDch.sendZeroRequestObservation(indices[1]+1,pfread.getCreationTime(),false);
            
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
                        myDch.sendZeroRequestObservation(indices[0],indices[0],pfread.getCreationTime(),false); 
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
    
    private void handleConstrainedRQMs(long creationTime){
        myDch.checkMessages(); // check for self
        for(DownloadConstrainHandler dch : myDch.constraintedWith()){
            //rqmLogger.log(Level.INFO,"sending_external_zero="+dch);
            dch.receiveExternalZeroRequestObservation(this.myDch,creationTime);
            dch.checkMessages();// check for others
        }
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
    
    private final MainDirectionThread_RQMAccess MDT_RQMAccess = new MainDirectionThread_RQMAccess() {
        @Override public List<DownloadConstrainHandler> constraintedWith() { 
            return myDch.constraintedWith(); }

        @Override public boolean message_requiredConnectionAtZero() {
            return myDch.message_requiredConnectionAtZero();}
        
        @Override public Logger rqmLogger() { return rqmLogger; }
        @Override public String provider_getName() { return provider.getName(); }
        @Override public long lastExternalRequestTime() { return myDch.lastExternalRequestTime(); }
        @Override public boolean autoCompleteEnabled() { return autoCompleteEnabled; }
        @Override public int myDch_index() { return myDch.index();}
        @Override public TroubleHandler provider_getTroubleHandler() {
            return provider.getTroubleHandler();}
        
        @Override public UnsyncRangeArrayCopy<RegionHandler> handlers_tryToGetUnsynchronizedCopy() {
            return handlers.tryToGetUnsynchronizedCopy();}
        
        @Override public void notifyDownloadComplete() {
            synchronized (dcls) {
                for (DownloadCompletedListener dcl : dcls) {
                    dcl.downloadCompleted(); }}}
    };
    
    private final DCH_RQM_Access rQM_Access = new DCH_RQM_Access() {
        @Override public RangeArray<RegionHandler> handlers() { return handlers; }
        @Override public boolean isComplete() { return mainDirectionThread.isComplete(); }
        @Override public String provider_getName() { return provider.getName(); }
        @Override public Logger rqmLogger() { return rqmLogger; }
        @Override public boolean hasAMainConnection() { return mainDirectionThread.hasAMainConnection(); }
        @Override public void read(ReadRequest readRequest) { ReadQueueManager.this.read(readRequest); }
    };
    
    @Deprecated
    private volatile boolean disabled = false;
    
}