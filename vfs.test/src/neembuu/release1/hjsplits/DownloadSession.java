/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.hjsplits;

import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public class DownloadSession {
    SeekableConnectionFile file;

    public SeekableConnectionFile getSeekableConnectionFile(){
        return file;
    }
    
    public String getFinalFileName(){
        return "";
    }
    
    public void enableOpenButton(boolean f){
        
    }
    
    
}
