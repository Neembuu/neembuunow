/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api;

import neembuu.release1.api.ui.VirtualFileUI;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public interface VirtualFile {
    SeekableConnectionFile getConnectionFile();   
    ReferenceLink getReferenceLink();
    VirtualFileUI getUI();
}
