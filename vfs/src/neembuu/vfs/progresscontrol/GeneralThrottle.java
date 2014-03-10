/*
 *  Copyright (C) 2014 Shashank Tulsyan
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
package neembuu.vfs.progresscontrol;

import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.config.GlobalTestSettings;
import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.util.logging.LoggerUtil;
import neembuu.vfs.annotations.DownloadThread;
import neembuu.vfs.annotations.ReadHandlerThread;
import neembuu.vfs.annotations.ReadQueueManagerThread;
import neembuu.vfs.connection.NewConnectionParams;
import neembuu.vfs.readmanager.ReadRequestState;

/**
 *
 * @author Shashank Tulsyan
 */
final class GeneralThrottle implements Throttle {

    /////// speed calculation variables //////
    final SpeedObv downloadSpeedObv = new SpeedObv(100,0);
    final SpeedObv requestSpeedObv = new SpeedObv(50,0);

    // download speed throttle variables
    volatile double sleepPerByteDownloaded = 0;
    volatile boolean throttleEnabled = true;
    long lstTime = System.currentTimeMillis();
    float a = 0; //adjustment factor
    boolean dir = true; // under throttle
    int dc = downloadSpeedObv.getL();
    
    long lstRqObvMilliTime = 0;
    
    private volatile long maxReadOffset;
    private volatile int numberOfSuccessiveExternallRequests = 0;
    private volatile int numberOfRequestsMadeAlongThis = 0;
    private long requestsJumpedInstance = 0;
    private volatile long reBirthTime;
    private volatile NewConnectionParams cp;
    
    private volatile long throttleWakeUpTime = System.currentTimeMillis();
    
    //private final AtomicInteger noOfPendingRq = new AtomicInteger(0); // because 
    // checking length of a concurrent queue is not efficient
    
    private static final boolean THROTTLE_LOGGER_ENABLED = true;
    /**
     * Not throttling if there is no gap between last requested offset and last downloaded offset
     * or when the gap is very small.
     * gap should be evaluated in terms of time and not size
     * design assumption : gap should be at least 4 seconds
     */
    private static final int MINIMUM_NUMBER_OF_SECONDS_IN_GAP = 4;
    private ThrottleState throttleState = ThrottleState.NOT_THROTTLING;
    
    GeneralThrottle() {
        
    }
    
    @Override
    public final NewConnectionParams getConnectionParams() {
        return cp;
    }

    private void updateThrottleState(ThrottleState newState) {
        if (throttleState != newState) {                    
            throttleState = newState;
        }

    }

    
    private double prv_wipb=0; 
    
    @Override
    public final void wait(double waitIntervalPerByte, int numberOfBytesDownloaded) {
        if(cp==null)return;
        if(!THROTTLE_LOGGER_ENABLED)return;//
        if(!canDie())return;
        // we shall not throttle connections for links
        // that allow only 1 connection, because there is 
        // no point
        double millisec = (long)(waitIntervalPerByte*numberOfBytesDownloaded*1000);
        int nanosec = (int)
                ((long)millisec*1000000)%1000000;
        if(nanosec<0)nanosec=0;//sometimes -ve due to round off error
        long s,e;
        long maxWait = cp.getReadRequestState().getTotalFileReadStatistics().getNewHandlerCreationTime(
                cp.getReadRequestState().ending()+1)*2;
        if(maxWait<3000)maxWait = 3000; maxWait = Math.min(maxWait, 10000);
        try{
            if(prv_wipb!=waitIntervalPerByte){
                logThrottleState(119,"waitIPB="+waitIntervalPerByte+" nobd= "+numberOfBytesDownloaded
                        +" waiting="+millisec+" n="+nanosec+" prv="+prv_wipb);
                prv_wipb = waitIntervalPerByte;
            }
            if(((long)millisec)==0){return;}
            
            throttleWakeUpTime = (long)(System.currentTimeMillis() + millisec);

            if(Double.isInfinite(waitIntervalPerByte)){
                s = System.currentTimeMillis();
                synchronized(cp.getThrottlingLock()){
                    cp.getThrottlingLock().wait((long)maxWait);
                }e = System.currentTimeMillis();
                if(e-s>maxWait-100){
                    if(canDie())
                        throw new RuntimeException("Killing to avoid waiting for infinite time");
                    else return;
                }
            }else {
                synchronized(cp.getThrottlingLock()){
                    cp.getThrottlingLock().wait((long)millisec, nanosec);
                }
            }
        }catch(InterruptedException ie){
            logThrottleState(127, null,ie);
        }
    }
    
    @Override
    @DownloadThread
    public double getWaitIntervalPerByte() {
        if(cp==null){
            logThrottleState(164, "cp is null");return 0;
        }//never happens, but still in 
        // verge race conditions of initialization this happens
        if(!THROTTLE_LOGGER_ENABLED)return 0;
        if(cp.getReadRequestState().isMainDirectionOfDownload()){
            updateThrottleState(ThrottleState.NOT_THROTTLING_MAIN_DIRECTION);
            logThrottleState(170, ThrottleState.NOT_THROTTLING_MAIN_DIRECTION.toString());
            return 0;
        }
        
        double r = requestSpeedObv.getSpeed_Bps();
        if (r == 0) {
            r = 1; // 1 bps is negligible and will not cause Math exceptions
        }
        // not throttling if there is no gap between last requested offset and last downloaded offset
        // or when the gap is very small.
        // gap should be evaluated in terms of time and not size
        // design assumtion : gap should be atleast 4 seconds
        // gap might be negative as well.
        if (requestDownloadGap() < r * MINIMUM_NUMBER_OF_SECONDS_IN_GAP) {

            // assume gap not sufficient
            // a policy will actually control this
            updateThrottleState(ThrottleState.NOT_THROTTLING);
            logThrottleState(167, "not throttling since gap not sufficient");
            return 0;
        }

        if (pendingRequestsPresentOutside()) {
            // implies requested region is > downloaded region
            // download region must catch up by disabling throttling
            updateThrottleState(ThrottleState.NOT_THROTTLING);
            logThrottleState(176, "implies requested region is > downloaded region download region must catch up by disabling throttling");
            return 0;
        }

        dieIfRequestsSufficientlyShiftedOrExternalPendingRequestPresent();
        /*if (requestsPresentAlongThisConnection()) {
            logThrottleState(210, "not throttling since requests present");
            updateThrottleState(ThrottleState.NOT_THROTTLING);
            return 0;
        }*/ // no throttling if requests present
        // UPDATE : policy change as we are already checking for request download gap above.

        long closestAliveConEnding = closestAliveRegionEnding();
        long start = cp.getReadRequestState().starting();
        long end = cp.getReadRequestState().ending();

        if (closestAliveConEnding == start) {
            logThrottleState(218, "throttling & this is the closest alive region");
            /*updateThrottleState(ThrottleState.NOT_THROTTLING);
            return 0;*/
            updateThrottleState(ThrottleState.LIBERAL_THROTTLING);
            return calculateSleepInterval(/*contemporaryAliveConnectionGap*/)/2;
        }else if(true){
            updateThrottleState(ThrottleState.NORMAL_THROTTLING);
            return calculateSleepInterval(/*contemporaryAliveConnectionGap*/);
        }
        // this means this connection is the only alive connection
        // so no speed crisis. let us ignore it

        double contemporaryAliveConnectionGap = 0;

        if (closestAliveConEnding > start) {
            contemporaryAliveConnectionGap = closestAliveConEnding - end;
            if (contemporaryAliveConnectionGap < 0) {
                throw new IllegalStateException("Incorrect implementation of closestAliveRegionEnding() provided");
            }

            contemporaryAliveConnectionGap /= cp.getReadRequestState().fileSize(); // changing into percent

            if (contemporaryAliveConnectionGap < 0.01) {
                logThrottleState(241, "viberation request or user has forwarded the video slightly (vlc's shift + -> )");
                updateThrottleState(ThrottleState.NOT_THROTTLING);
                return 0; // do not throttle
                // viberation request or user has forwarded the video slightly (vlc's shift + -> )
                // this might be the viberation's
            } else {
                // user has probably forwarded the video very much (vlc's ctrl + -> )
                logThrottleState(247, "user has probably forwarded the video very much (vlc's ctrl + -> )");
                updateThrottleState(ThrottleState.NORMAL_THROTTLING);
                return calculateSleepInterval(/*contemporaryAliveConnectionGap*/);
            }
        } else {
            contemporaryAliveConnectionGap = start - closestAliveConEnding;

            contemporaryAliveConnectionGap /= cp.getReadRequestState().fileSize(); // changing into percent
            // viberations travel in forward direction.
            // no new connection is created for viberating forward requests
            // so if there is a request prior to an existing one, it means,
            // that the previous offset request is starving.
            // We need to throttle this, as no matter how much this region
            // downloads, no new requests will be made here, until and unless
            // the previous requests are satisfied.
            if (contemporaryAliveConnectionGap < 0.05) {
                logThrottleState(249, "user has reversed the video slightly (vlc's shift + <- )");
                updateThrottleState(ThrottleState.NOT_THROTTLING);
                return 0; // do not throttle
                // viberation request or user has reversed the video slightly (vlc's shift + <- )
            } else {
                // user has probably reversed the video very much (vlc's ctrl + <- )
                updateThrottleState(ThrottleState.NORMAL_THROTTLING);
                return calculateSleepInterval(/*contemporaryAliveConnectionGap*/);
            }

        }
    }

    private double calculateSleepInterval() {
        double r = requestSpeedObv.getSpeed_Bps();
        if(r < 1024){return Double.POSITIVE_INFINITY;}

        logThrottleState(299, "throttleInverse=" + sleepPerByteDownloaded);
        
        double d = downloadSpeedObv.getSpeed_Bps();
        
        //if(!throttleEnabled)return;
        if(d<r){
            if(a<0.99f){
                if(dir==false){
                    if(dc>0)dc--;
                    else { 
                        dir = true; 
                        dc = downloadSpeedObv.getL();
                        logThrottleState(260, "dir=underthrottle");
                        //controlThrottle.directionLable.setText("under throttle");
                    }
                }else dc = downloadSpeedObv.getL();
                
                if(dir==true){
                    a+=0.00004f*r*r/(d*d);
                    logThrottleState(267, "a="+a);
                }
            }
        }else if(d>r){
            if(a>0.0f){
                if(dir==true){
                    if(dc>0)dc--;
                    else { 
                        dir = false; 
                        dc = downloadSpeedObv.getL();
                        logThrottleState(277, "dir=over throttle");
                    }
                }else dc = downloadSpeedObv.getL();
                
                
                if(dir==false){
                    a-=0.00004f*d*d/(r*r);
                    logThrottleState(284, "a="+a);
                }
            }
        }
        
        sleepPerByteDownloaded = (1/r)*(1-a);
        
        return sleepPerByteDownloaded;
    }

    private void dieIfRequestsSufficientlyShiftedOrExternalPendingRequestPresent() {
        if(requestDownloadGap() < requestSpeedObv.getSpeed_Bps() * MINIMUM_NUMBER_OF_SECONDS_IN_GAP){
            return;
        }
        
        final long totalRequested = cp.getReadRequestState().getTotalFileReadStatistics().totalDataRequestedSoFar();
        if (totalRequested == 0) {
            logThrottleState(271, "total request=0");
            return;
        }

        if(numberOfSuccessiveExternallRequests<=5)
            requestsJumpedInstance = System.currentTimeMillis();
        
        /*if(System.currentTimeMillis() > requestsJumpedInstance + 7000){
            throw new RuntimeException("Requests jumped. Time elapsed");
        }*/
        
        if (numberOfSuccessiveExternallRequests > 5
                && ((double)numberOfSuccessiveExternallRequests) > ((double)numberOfRequestsMadeAlongThis/20d) ) {
            if(canDie())
                throw new RuntimeException("Requests jumped. Total successive outside="+numberOfSuccessiveExternallRequests+" total along="+numberOfRequestsMadeAlongThis);
        } else {
            logThrottleState(322, "total effective number of external requests="+numberOfSuccessiveExternallRequests);
        }
        
        //we are not starving
        // check if some external region is suffering from starvation
        if(System.currentTimeMillis() - lstRqObvMilliTime > 5000){
            UnsyncRangeArrayCopy<ReadRequestState> elements = 
                    cp.getReadRequestState().getTotalFileReadStatistics().getReadRequestStates();

            for (int i = 0; i < elements.size(); i++) {
                ReadRequestState rrs = elements.get(i).getProperty();
                if(i+1<elements.size()){
                    if(rrs.ending()==elements.get(i+1).starting()-1){
                        // The conneciton is complete thus it cannot starve
                        continue;
                    }
                }
                if(rrs.hasPendingRequests()){
                    long extRqt = rrs.getThrottleStatistics().lastRequestTime();
                    if(extRqt - lstRqObvMilliTime > 5000 && canDie()){
                        RuntimeException re = new RuntimeException("Killing to prevent starvation of "+
                                rrs.toString()+"{\n"
                                +rrs.tryGettingPendingRequestsList()+"}");
                        logThrottleState(298, re.getMessage());
                        throw re;
                    }
                }
            }
        }
    }

    private long lastLogTime = System.currentTimeMillis();
    private int lastLineLogged = 0;
    private void logThrottleState(int line, String message) {
        logThrottleState(line, message, null);
    }
    private void logThrottleState(int line, String message,Exception a) {
        if (THROTTLE_LOGGER_ENABLED) {
            if(cp!=null){
                if(lastLineLogged!=line){// || System.currentTimeMillis()>lastLogTime+200){
                    lastLineLogged = line;
                    lastLogTime = System.currentTimeMillis();
                    Logger l = cp.getDownloadThreadLogger();
                    l.log(Level.INFO, line + ":" + message,a);
                }else {
                    /*System.err.println("tns-o="+cp.getOffset()+" l="+line + ":" + message);
                    if(a!=null)a.printStackTrace(System.err);*/
                }
            }else {
                /*System.err.println("cpn-l="+line + ":" + message);
                if(a!=null)a.printStackTrace(System.err);*/
            }
        }
    }
    
    @Override
    public final double getDownloadSpeed_KiBps() {
        if(cp==null)return 0;
        return downloadSpeedObv.getSpeed_KiBps();
    }

    @Override
    public final double getRequestSpeed_KiBps() {
        //if(cp==null)return 0;
        return requestSpeedObv.getSpeed_KiBps();
    }

    @Override
    public ThrottleState getThrottleState() {
        return throttleState;
    }
    
    @Override
    @DownloadThread
    public final void downloadProgressed(long start, long oldEnd, long dx) {
        try{
            downloadSpeedObv.progressed((int)dx);
        }catch(ArithmeticException e){
            logThrottleState(366, "download speed calculation error",e);
        }
    }

    
    @Override
    @ReadQueueManagerThread
    public final void requestCreated(
            long downloadedRegionStart,
            long downloadedRegionEnd,
            long requestFileOffset,
            int requestSize,
            long creationTime) {
        long effectiveReadSize = 0;
        if(requestFileOffset>=0){
            lstRqObvMilliTime = System.currentTimeMillis();
            numberOfRequestsMadeAlongThis++;
            effectiveReadSize = requestFileOffset+requestSize-1 - maxReadOffset;

            if(effectiveReadSize<0)effectiveReadSize = 0;
            else maxReadOffset+=effectiveReadSize; 
            numberOfSuccessiveExternallRequests = 0;
        }else {
            numberOfSuccessiveExternallRequests++;
        }
        requestSpeedObv.progressed((int)(effectiveReadSize));
        
        // a request has been made beyond available region,
        // that too while download thread is sleeping by throttling
        // we need to wake it. The throttling extent might be infinity,
        // so in such a case it will never wake up.
        final NewConnectionParams ncp;
        if(cp==null){logThrottleState(409, "cp is null");return;}
        else {ncp = cp; if(ncp==null)return;/*might happen multithreaded environment*/}
        if(throttleWakeUpTime > System.currentTimeMillis()){
            if(maxReadOffset > ncp.getReadRequestState().ending()){
                synchronized (ncp.getThrottlingLock()){
                    ncp.getThrottlingLock().notifyAll();
                }
            }
        }
    }

    @Override
    @ReadHandlerThread
    public final void requestCompleted(
            long downloadedRegionStart,
            long downloadedRegionEnd,
            long requestFileOffset,
            int requestSize,
            long completionTime) {
    }

    @Override
    public final long lastRequestTime() {
        return lstRqObvMilliTime;
    }

    @Override
    public final boolean requestsPresentAlongThisConnection() {
        if(requestDownloadGap() < 0) {
            logThrottleState(472, "request download gap="+requestDownloadGap());
            return true;
        }
        //theory ...
        // since we receive all requests of every region
        // a certain number, say 5 req. outside this means no requests present here
        // also we know average time between requests, 
        // so say, twice average request time means also means, 
        // no requests present 
        int noOfPendingRequest = cp.getReadRequestState().numberOfPendingRequests();
        if(/*noOfPendingRq.get()*/noOfPendingRequest>0){
            logThrottleState(472, "noOfPendingRq="+/*noOfPendingRq.get()*/noOfPendingRequest);
            return true;
        }
            
            
        return false;
    }

    @Override
    public final boolean pendingRequestsPresentOutside() {
        return maxReadOffset > cp.getReadRequestState().ending();
    } 

    @Override
    public final long closestAliveRegionEnding() {
        long start = cp.getReadRequestState().starting(); 
        
        UnsyncRangeArrayCopy<ReadRequestState> elements = 
                cp.getReadRequestState().getTotalFileReadStatistics().getReadRequestStates();
        
        int indexOfElement = -1;
        
        for (int i = 0; i < elements.size(); i++) {
            if(elements.get(i).starting() == start){
                indexOfElement = i;break;
            }
        }
        
        // first check previos entries
        for(int i = indexOfElement - 1; i>=0; i--){
            if(elements.get(i).getProperty().isAlive())
                return elements.get(i).ending();
        }
            
        // check entries beyond this connection
        for(int i = indexOfElement + 1; i<elements.size(); i++){
            if(elements.get(i).getProperty().isAlive())
                return elements.get(i).ending();
        }
        
        return start;
    }
    
    @Override
    public final long requestDownloadGap(){
        return cp.getReadRequestState().ending() - maxReadOffset ;
    }

    @Override
    public final double averageDownloadSpeed_Bps(){
        return (1000*(cp.getReadRequestState().ending()-cp.getOffset()))
                /
                (System.currentTimeMillis()-reBirthTime);
    }
    
    @Override
    @DownloadThread
    public final void initialize(NewConnectionParams cp) {
        reBirthTime = System.currentTimeMillis();
        numberOfRequestsMadeAlongThis = 0;
        numberOfSuccessiveExternallRequests = 0;
        requestsJumpedInstance = System.currentTimeMillis();
        this.cp = cp;
        //ed.updateName(toString());
    }

    private boolean canDie(){
        NewConnectionParams ncp;
        if(cp!=null){ncp=cp;  if(ncp==null)return true;}
        else return true;
        // it doesn't take infinite time to make new connection
        return ncp.getReadRequestState().getTotalFileReadStatistics().getNewHandlerCreationTime(ncp.getReadRequestState().ending()+1)
                < Integer.MAX_VALUE;
    }
    
    @Override
    @DownloadThread
    public final void markDead(NewConnectionParams cp) {
        if(this.cp!=cp)throw new IllegalArgumentException("this.NewConnectionParams!=NewConnectionParams");
        logThrottleState(540, "markedDead");
        this.cp  = null;
        //ed.updateName(toString()+"_dead");
    }

    @Override
    public String toString() {
        if(cp==null)
            return super.toString();
        return GeneralThrottle.class.getSimpleName()+"_"+throttleState+"_offset="+cp.getOffset()+"_";
    }    
}
