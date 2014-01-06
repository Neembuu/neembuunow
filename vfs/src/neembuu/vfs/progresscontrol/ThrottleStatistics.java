/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.vfs.progresscontrol;

/**
 *
 * @author Shashank Tulsyan
 */
public interface ThrottleStatistics
        extends 
            DownloadSpeedProvider,
            RequestSpeedProvider,
            ThrottleStateProvider{
    
    /**
     * This is used during {@link ThrottleState#THROTTLE_TILL_DEAD } state.
     * That is when requests are not being made along this connection and download bandwidth speed
     * is actually required somewhere else.
     * Please see {@link neembuu.http.ThrottledSocket } .
     * @return The time instance ( java.util.Date ) when the last read request was made along this channel.
     */
    long lastRequestTime();

    /**
     * There are read requests beyond the region already downloaded by this connection,
     * is such cases throttling is entirely disabled. <br/>
     * When this happens {@link #requestDownloadGap() } will return a negative value. <br/>
     * This is slightly faster than {@link #requestDownloadGap() } .
     * @return true iff there are read requests beyond the region already downloaded by this connection.
     */
    boolean pendingRequestsPresentOutside();

    /**
     * In the current implementation it is used to check if the connection should be throttled or not.
     * If the gap is too small, throttling is not done. First we build a certain amount of gap, depending
     * upon request speed and average request size, and only then we throttle, this is for a better watching
     * experience. Please see {@link neembuu.http.ThrottledSocket } .
     * @return this download channel.ending() - endingOffset of last request
     */
    long requestDownloadGap();

    /**
     * When user is watching a video over this connection this returns true.
     * If user has shifted to some other region then it returns false.
     * After sometime user might again start watching video over this connection,
     * then this returns true again.
     * If user is not watching video anywhere, this returns false.
     * @return
     */
    boolean requestsPresentAlongThisConnection();
    
    long closestAliveRegionEnding();
    
    /**
     * @return bytes per second
     */
    double averageDownloadSpeed_Bps();
}
