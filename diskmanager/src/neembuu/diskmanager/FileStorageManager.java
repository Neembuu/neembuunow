/*
 * Copyright (c) 2011 Shashank Tulsyan <shashaanktulsyan@gmail.com>. 
 * 
 * This is part of free software: you can redistribute it and/or modify
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
 * along with this.  If not, see <http ://www.gnu.org/licenses/>.
 */
package neembuu.diskmanager;

import java.io.File;
import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.util.logging.Logger;

/**
 * The function of {@link FileStorageManager} is to store downloaded data appropriately.
 * It also read from recently downloaded data in order to satisfy read requests.
 * It can also resumes a corrupted download state.<br/> 
 * Other optional function is, it can copy recently downloaded data in RAM, 
 * and supply it directly from RAM when a read
 * request is made on {@link RegionStorageManager#read(java.nio.ByteBuffer, long) }. This
 * would improve read speeds. High read speeds are often unachievable without this optimization 
 * because the size of read requests is small and thus number of read requests is large, 
 * and the overhead of virtual filesystem becomes significant.
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public interface FileStorageManager {
    DiskManager getDiskManager();
    RegionStorageManager getRegionStorageManagerFor(long startingOffset)throws IOException;
    
    /**
     * Watch as you download is highly prone to race-conditions, once in a million chance errors
     * which are literally impossible to find. For this purpose we log rigorously.
     * To ensure readability of logs we write them in different files. It is the job of 
     * DiskManager to make a logger and save it's data to a file. The limit of log size is also
     * decided by {@link FileStorageManager}
     * @return 
     */
    Logger getReadQueueManagerThreadLogger();
        
    void copyIfCompleteTo(SeekableByteChannel output, long fileSize)throws Exception;
    
    void deleteSession()throws Exception;
    
    void close() throws Exception;
}
