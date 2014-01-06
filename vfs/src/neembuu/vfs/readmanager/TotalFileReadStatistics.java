package neembuu.vfs.readmanager;

import neembuu.rangearray.UnsyncRangeArrayCopy;
import neembuu.vfs.progresscontrol.DownloadSpeedProvider;
import neembuu.vfs.progresscontrol.RequestSpeedProvider;

/**
 * @author Shashank Tulsyan
 */
public interface TotalFileReadStatistics {
    long totalDataRequestedSoFar();
    int totalNumberOfRequestsMade();
    /**
     * @return immutable copy of read request state
     */
    UnsyncRangeArrayCopy<ReadRequestState> getReadRequestStates();
    
    DownloadSpeedProvider getTotalAverageDownloadSpeedProvider();
    RequestSpeedProvider getTotalAverageRequestSpeedProvider();
    
    long getNewHandlerCreationTime(long offset);
}
