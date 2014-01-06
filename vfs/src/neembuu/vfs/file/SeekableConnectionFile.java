package neembuu.vfs.file;

import jpfm.DirectoryStream;
import jpfm.volume.CascadableAbstractFile;
import neembuu.diskmanager.FileStorageManager;

/**
 *
 * @author Shashank Tulsyan
 */
public interface SeekableConnectionFile 
    extends FileBeingDownloaded,
            CascadableAbstractFile{
    DownloadConstrainHandler getDownloadConstrainHandler();
    void setParent(DirectoryStream parent);
    void addDownloadCompletedListener(DownloadCompletedListener dcl);
    void removeDownloadCompletedListener(DownloadCompletedListener dcl);
    
    TroubleHandler getTroubleHandler();
    SeekableConnectionFileParams getParams();
    FileStorageManager getFileStorageManager();
    
    /**
     * Called when file has been unmounted and so all resources must be closed
     * for good.
     */
    void closeCompletely();
    
    void addRequestPatternListener(RequestPatternListener rpl);
    
    void removeRequestPatternListener(RequestPatternListener rpl);
    
}
