/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl;

import jpfm.volume.vector.VectorDirectory;
import neembuu.release1.Main;
import neembuu.release1.api.File;
import neembuu.release1.api.LinkPackage;
import neembuu.release1.api.ReferenceLink;
import neembuu.release1.defaultImpl.single.SeekableConnectionFile_1to1;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public final class LinkPackages {
    
    Main main;
    

    public LinkPackages() {
        
    }
    
    private void init(){
        
    }
    
    private LinkPackage makeLinkPackageForSingle(ReferenceLink l)throws Exception{
        if(l.getLinkHandler().getFiles().isEmpty()){
            throw new NullPointerException("List is empty, no files found"); 
        }
        if(l.getLinkHandler().getFiles().size() > 1){
            // multiple files per link, example youtube
        }else {
            // 1 file per link
            
        }
    }
    
    private LinkPackage makeMultiLinkPackage(ReferenceLink l)throws Exception{
        String folderName = l.getLinkHandler().getGroupName();
        
        folderName = main.getMountManager().getSuitableFileName(folderName);
        
        VectorDirectory vd = new VectorDirectory(folderName, main.getMountManager().getRootDirectory());
        main.getMountManager().getRootDirectory().add(vd);
        
        for (File f : l.getLinkHandler().getFiles()) {
            SeekableConnectionFile seekableConnectionFile 
                = SeekableConnectionFile_1to1.create(
                        l, main.getDiskManager(), main.getTroubleHandler(), 
                        f.fileName(), vd);
            vd.add(seekableConnectionFile);
        }
        
        
    }
    
    private LinkPackage makeLinkPackageForSingle(ReferenceLink l)throws Exception{
        // use virtual file providers
        String fileName = l.getLinkHandler().getGroupName();
        
        fileName = main.getMountManager().getSuitableFileName(fileName);

        
        SeekableConnectionFile seekableConnectionFile 
                = SeekableConnectionFile_1to1.create(
                        l, main.getDiskManager(), main.getTroubleHandler(), 
                        fileName, null);

        main.getMountManager().addFile(vf);
        //singleFileLinkUI.init(vf);
        
        
        
        return vf;
    }
}
