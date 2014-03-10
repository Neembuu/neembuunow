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
package neembuu.vfs.file;

import jpfm.DirectoryStream;
import jpfm.volume.CascadableAbstractFile;
import jpfm.volume.OpenCloseListener;
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
    void closeCompletely()throws Exception;
    
    void addOpenCloseListener(OpenCloseListener closeListener);
    void removeOpenCloseListener(OpenCloseListener closeListener);
    
    void setAutoCompleteEnabled(boolean autoCompleteEnabled);
}
