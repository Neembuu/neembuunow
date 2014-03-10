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
