package neembuu.vfs.progresscontrol;

import java.util.EventListener;

/**
 *
 * @author Shashank Tuslyan
 */
public interface DownloadProgressListener extends EventListener{
    /**
     * 
     * @param start start of connection
     * @param oldEnd old end of connection
     * @param dx number of bytes downloaded or requested 
     * in time dt
     */
    public void downloadProgressed(
            long start,
            long oldEnd,
            long dx);
}
