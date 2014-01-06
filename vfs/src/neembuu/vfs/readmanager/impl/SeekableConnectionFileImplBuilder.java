/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.vfs.readmanager.impl;

import neembuu.vfs.file.SeekableConnectionFile;
import neembuu.vfs.file.SeekableConnectionFileParams;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SeekableConnectionFileImplBuilder {
    public static SeekableConnectionFile
            build(SeekableConnectionFileParams scfp)throws Exception{
        SeekableConnectionFileImpl scfi = null; 
        
            scfi = 
                new SeekableConnectionFileImpl(scfp);
        
        return scfi;
    }
}
