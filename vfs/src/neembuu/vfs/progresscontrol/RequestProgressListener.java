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

import java.util.EventListener;
import neembuu.vfs.annotations.ReadHandlerThread;
import neembuu.vfs.annotations.ReadQueueManagerThread;

/**
 *
 * @author Shashank Tuslyan
 */
public interface RequestProgressListener extends EventListener{
    
    /**
     * 
     * @param downloadedRegionStart
     * @param downloadedRegionEnd
     * @param requestFileOffset negative if the request originated in some other connection 
     * @param requestSize
     * @param creationTime 
     */
    @ReadQueueManagerThread
    public void requestCreated(
        long downloadedRegionStart,
        long downloadedRegionEnd,
        long requestFileOffset,
        int requestSize,
        long creationTime);
    
    @ReadHandlerThread
    public void requestCompleted(
        long downloadedRegionStart,
        long downloadedRegionEnd,
        long requestFileOffset,
        int requestSize,
        long completionTime);
    
}
