/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api;

import neembuu.release1.api.ui.LinkUI;
import neembuu.rangearray.UIRangeArrayAccess;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public interface VirtualFile {
    UIRangeArrayAccess getRegions();
    SeekableConnectionFile getConnectionFile();
    VirtualFilesParams getVirtualFilesParams();
    boolean tryUpdating(String newUrl);
    
    void setUI(LinkUI linkUI) throws IllegalStateException;
    
    LinkUI getUI() throws IllegalStateException;
}
