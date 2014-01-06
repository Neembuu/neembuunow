/*
 * Copyright (C) 2011 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.vfs.readmanager;

import neembuu.vfs.progresscontrol.ThrottleStatistics;


/**
 * This should be used to throttle the speed of the connection according 
 * to the situation and throttling policy. <br/>
 * For example  :<br/> <ul>
 * <li>when {@link ReadRequestState#requestDownloadGap() } is zero
 * no throttling must be done.</li>
 * <li>when {@link ReadRequestState#pendingRequestsPresentOutside() } 
 * or {@link ReadRequestState#requestsPresentAlongThisConnection() }
 * is true the connection must not be closed as requests are still being
 * made on it.</li>
 * <li>When difference between {@link ReadRequestState#lastRequestTime() } 
 * and <b>System.currentTimeMillis()</b> is large the connection must be terminated.
 * </li>
 * </ul>
 * @author Shashank Tulsyan
 */
public interface ReadRequestState{
    
    /**
     * @return The offset at which this connection was started
     */
    long starting();
    
    /**
     * The last absolute offset till which download has been done.
     * Ending is inclusive, that means total number of bytes downloaded is {@link #ending()} - {@link #starting} +1 .
     * @return  Last offset downloaded
     */
    long ending();

    /**
     * Authority limit is the file offset till which this connection is exclusively responsible
     * for satisfying read requests. Authority limit may be greater than what this connection 
     * has already downloaded. This situation basically means that there are pending requests
     * in the connection. When download speed is higher than request speed, then authority limit
     * would be found to be equal to {@link #ending()}.
     * @return
     */
    long authorityLimit();

    long fileSize();
    
    String getFileName();
    
    TotalFileReadStatistics getTotalFileReadStatistics();
    
    boolean isAlive();
    
    ThrottleStatistics getThrottleStatistics();
    
    int numberOfPendingRequests();
    
    boolean hasPendingRequests();
    
    String tryGettingPendingRequestsList();
    
    boolean isMainDirectionOfDownload();
}
