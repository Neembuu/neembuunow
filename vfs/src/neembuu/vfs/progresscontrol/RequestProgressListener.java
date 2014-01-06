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
