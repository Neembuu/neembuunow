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
