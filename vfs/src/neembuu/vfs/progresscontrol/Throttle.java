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

import neembuu.vfs.annotations.DownloadThread;
import neembuu.vfs.connection.NewConnectionParams;

/**
 * 
 * @author Shashank Tulsyan
 */
public interface Throttle 
        extends 
            ThrottleStatistics,
            DownloadProgressListener, 
            RequestProgressListener{
    
    @DownloadThread
    void initialize(NewConnectionParams cp);
    
    @DownloadThread
    void markDead(NewConnectionParams cp);
    
    NewConnectionParams getConnectionParams();
    
    /**
     * @throws RuntimeException if the connection must be killed.
     * @return 
     */
    @DownloadThread
    double getWaitIntervalPerByte();
    
    /**
     * Waits on the throttling lock {@link NewConnectionParams#getThrottlingLock() }.
     * The return value of {@link #getWaitIntervalPerByte() } must be passed to it,
     * along with number of bytes downloaded right now, that is the size of the download read buffer.
     * @param waitIntervalPerByte value returned by {@link #getWaitIntervalPerByte() }
     * @param numberOfBytesDownloaded size of the download buffer
     */
    @DownloadThread
    void wait(double waitIntervalPerByte, int numberOfBytesDownloaded);
}
