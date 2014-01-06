package neembuu.vfs.file;

import jpfm.volume.OpenCloseListener;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.vfs.readmanager.TotalFileReadStatistics;

/**
 * This is an interface to Neembuu files for user interface.
 * This contains functions that UI classes might be interested in.
 * @author Shashank Tulsyan
 */
public interface FileBeingDownloaded {

    UIRangeArrayAccess getRegionHandlers();
    
    TotalFileReadStatistics getTotalFileReadStatistics();
    
    void addOpenCloseListener(OpenCloseListener closeListener);
    void removeOpenCloseListener(OpenCloseListener closeListener);
    
    void setAutoCompleteEnabled(boolean autoCompleteEnabled);
    boolean isAutoCompleteEnabled();
    
    void close();
}
