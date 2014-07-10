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

import neembuu.vfs.readmanager.rqm.ReadQueueManager;
import java.util.logging.Logger;
import neembuu.diskmanager.FileStorageManager;
import neembuu.vfs.connection.checks.SeekingAbility;
import neembuu.vfs.file.TroubleHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public interface NewReadHandlerProvider  {
    /**
     * Opens a new connection at the given region.
     * This method is called only when a new connection is needed.
     * The connection starts from region's {@link RangeArrayElement#starting() }
     * and continues on even beyond the requests regions end.
     * When the connection must be closed, is decided dynamically according to 
     * the situation. The <b>Range</b> http header should therefore include only the starting
     * and not the ending, as ending is not known for sure.
     * @param region the file offset from which this RegionHandler must be able to
     * provide data
     * @return 
     */
    RegionHandler provideHandlerFor(
            long start, long end);
    
    /**
     * {@link NewReadHandlerProvider#CANNOT_SEEK_TO_THIS_OFFSET} if 
     * connection cannot be created at the given offset because
     * seeking in this download link is not allowed.
     * @return milliseconds
     */
    long getNewHandlerCreationTime(long offset);
    
    SeekingAbility seekingAbility();
    
    boolean isOpen();
    
    //RequestHandlingApproach getRequestHandlingApproach();
    
    long CANNOT_SEEK_TO_THIS_OFFSET = Long.MAX_VALUE;
    
    ReadQueueManager getReadQueueManager();
    
    Logger getReadQueueManagerThreadLogger();
    
    String getName();
    
    TroubleHandler getTroubleHandler(); 
    
    FileStorageManager getFileStorageManager();
}
