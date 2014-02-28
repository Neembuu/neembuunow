/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.ui.access;

import neembuu.release1.api.File;
import neembuu.release1.api.VirtualFile;

/**
 *
 * @author Shashank Tulsyan
 */
public interface AddRemoveFromFileSystem {
    void remove(VirtualFile vf);
    void add(VirtualFile vf);
    
    VirtualFile create(File f);
}
