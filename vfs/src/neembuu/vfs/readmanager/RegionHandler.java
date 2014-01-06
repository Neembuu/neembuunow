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

import java.util.List;
import jpfm.annotations.NonBlocking;
import jpfm.operations.readwrite.ReadRequest;
import neembuu.rangearray.RangeArray;
import neembuu.vfs.annotations.MainDirectionDeciderThread;
import neembuu.vfs.annotations.ReadQueueManagerThread;

/**
 *
 * @author Shashank Tulsyan
 */
public interface RegionHandler extends DownloadedRegion{
    
    /**
     * This method receives calls from the ReadQueueManager.
     * If the request spans over more than one RegionHandler, then it is split into suitable parts.
     * It is not required to check whether the readRequest lies
     * within the bounds of this RegionHandler. <br/>
     * Bounds of connection lie between {@link #starting() } and {@link #authorityLimit() }.
     * Region already downloaded by this connection is {@link #starting() } to {@link #ending() }.<br/>
     * {@link #ending() } is lesser than or equal to {@link #authorityLimit() } . <br/>
     * <br/><br/>
     * There are three possible situations : <ul>
     * <li> When the readRequest lies between {@link #ending()} & {@link #authorityLimit() }
     * it means that the data is still being downloaded, and will be available soon.  <br/>
     * There are different ways of handling this, viz : blocking, non-blocking and partially completing.<br/>
     * The recommended approach is a "partially completing". Since read requests cannot be immediately fulfilled 
     * what we do is, we <b>simply return</b>.
     * The time for the data to be available depends of the request buffer size and download speed.
     * Experimentally, for particular test file test120k.rmvb, using vlc (which shall be the default
     * media player for neembuu ), the time was found to be around 0.1 seconds. 
     * Thread.sleep should NOT be used, as it will also change the behavior of this function to Blocking. 
     * <small>This function should always be non-blocking</small></li><br/>
     * <li>When the readRequest lies anywhere between {@link #starting() } & {@link #ending() }
     * the data can simply filled from the temporary files 
     * in which download data is being saved. </li><br/>
     * <li>If the connection is closed, and request has been made ahead of available data 
     * a new connection must be automatically made. In the meantime, this thread should not be blocked.
     * Connection should be made in another thread.</li> </ul>
     * @param readRequest 
     */
    @NonBlocking
    @ReadQueueManagerThread
    void handleRead(ReadRequest readRequest);
    
    @ReadQueueManagerThread
    void addNewZeroRequestObservation(long creationTime);
    
    void expandAuthorityLimitCarefully(RangeArray rangeArray, long newAuthorityLimit);
    
    /**
     * Unused. But would be called when virtual file is opened.
     */
    void open();
    /**
     * Close is called whenever the virtual file is closed.
     */
    void close();
    
    /**
     * Called when virtual filesystem in unmounted, and thus all resources 
     * must be closed for good.
     */
    void closeCompletely()throws Exception;
    
    String[]getPendingOperationsAsString();
    void fillInOperationsPendingSince(List<ReadRequest> rrs, long millisec);
    
    @MainDirectionDeciderThread
    void setMainDirectionOfDownload(boolean mainDirection);
}
