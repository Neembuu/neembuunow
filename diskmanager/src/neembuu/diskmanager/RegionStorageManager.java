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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

/**
 * Call from a single download thread, and dump all data here,
 * just as it is downloaded and forget.
 * For optimizing reads by caching write, and also committing
 * writes in another thread after buffering to reduce disk usage.
 * Do not rely on bounds {@link Range#starting() } and {@link Range#ending()  }
 * bounds specified by this, as they WILL be different from those of 
 * {@code  neembuu.vfs.readmanager.DownloadedRegion }.
 * However a fancy disk manager might look nice in advanced controls.
 * Not thread safe. 
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public interface RegionStorageManager extends LoggerCreator, SeekableByteChannelCreator{
    long startingOffset();
    int write(ByteBuffer src) throws IOException ;
    int write(ByteBuffer src,long absoulteFileOffset)throws IOException;
    int read(ByteBuffer src,long absoulteFileOffset)throws IOException;
    
    void transferTo_ReOpenIfRequired(WritableByteChannel wbc)throws IOException;
    
    public long endingByFileSize()throws IOException;
    
    void close()throws Exception;    
}
