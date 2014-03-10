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

package neembuu.release1.defaultImpl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import jpfm.DirectoryStream;
import jpfm.FileAttributesProvider;
import jpfm.fs.splitfs.CascadableSplitFS;
import jpfm.mount.BasicCascadeMount;
import neembuu.release1.Main;
import neembuu.release1.mountmanager.MountManager;
import neembuu.release1.api.postprocessor.PackageProcessor;
import neembuu.release1.api.VirtualFile;
import neembuu.vfs.file.ConstrainUtility;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public class SplitGroupProcessor implements PackageProcessor {

    @Override
    public List<VirtualFile> canHandle(List<VirtualFile> virtualFiles) {
        final ArrayList<VirtualFile> handleAble = new ArrayList<VirtualFile>();
        if (virtualFiles.size() < 1) return null;
        
        for (VirtualFile vf : virtualFiles) {
            int index = -2;
            
            String n = vf.getConnectionFile().getName();
            try {
                index = Integer.parseInt(n.substring(n.length() - 3));
            } catch (Exception a) {
                continue;
            }
            if (vf.getConnectionFile().getDownloadConstrainHandler().index() < 0) {
                vf.getConnectionFile().getDownloadConstrainHandler().setIndex(index);
            }else if(vf.getConnectionFile().getDownloadConstrainHandler().index() != index){
                throw new IllegalStateException("Illegally contrained files, expected index="+index+ " found="+
                        vf.getConnectionFile().getDownloadConstrainHandler().index());
            }   
            handleAble.add(vf);
            if (index < 0) { return null; }
        }
        return handleAble;
    }

    @Override
    public void handle(List<VirtualFile> splitVirtualFiles, MountManager mountManager) {
        List<VirtualFile> sessions_ = canHandle(splitVirtualFiles);
        if(sessions_.size()!=splitVirtualFiles.size()){
            throw new IllegalStateException("Number of splits expected ="
                    + sessions_.size()+ " found="+splitVirtualFiles.size());
        }
        Set<Integer> indices = new LinkedHashSet<Integer>();
        
        ArrayList<SeekableConnectionFile> scfs = new ArrayList<SeekableConnectionFile>();
        for(VirtualFile vf : splitVirtualFiles){
            if(!indices.add(vf.getConnectionFile().getDownloadConstrainHandler().index())){
                throw new IllegalStateException("Trying to constraint multiple files with same index="
                        + vf.getConnectionFile().getDownloadConstrainHandler().index());
            }
            scfs.add(vf.getConnectionFile());
        }

        Set<FileAttributesProvider> files = new LinkedHashSet<FileAttributesProvider>();
        synchronized (splitVirtualFiles) {
            for (VirtualFile vf : splitVirtualFiles) {
                files.add(vf.getConnectionFile());
            }
        }

        String name = splitVirtualFiles.get(0).getConnectionFile().getName();
        name = name.substring(0, name.lastIndexOf("."));

        CascadableSplitFS.CascadableSplitFSProvider cascadableSplitFS = new CascadableSplitFS.CascadableSplitFSProvider(files, name);
        
        BasicCascadeMount bcm = mountManager.getFileSystem().cascadeMount(cascadableSplitFS);
        
        ConstrainUtility.constrain((Iterable)scfs);
        // remove splited files .001 , .002 ... from virtual folder
        // to avoid confusion
        
        
        synchronized (splitVirtualFiles) {
            for (VirtualFile vf : splitVirtualFiles) {
                mountManager.getRootDirectory().remove(vf.getConnectionFile());
                vf.getConnectionFile().setParent((DirectoryStream)bcm.getFileSytem().getRootAttributes());
                // the file does not exist, therefore the open button should be
                // disabled
                //vf.getUI().deactivateOpenButton(true);
            }
        }
    }

    @Override
    public void openSuitableFile(List<VirtualFile> virtualFiles, MountManager mountManager) {
        String name = virtualFiles.get(0).getConnectionFile().getName();
        name = name.substring(0, name.lastIndexOf("."));

        DirectoryStream root = (DirectoryStream)mountManager.getFileSystem().getRootAttributes();
        DirectoryStream cascadedFolder = null;
        for(FileAttributesProvider fap : root){
            if(fap.getName().equals(name) && fap instanceof DirectoryStream ){
                cascadedFolder = (DirectoryStream)fap;
            }
        }
        
        if(cascadedFolder==null){
            return;
        }
        
        SimplyOpenTheVideoFile.findAndOpenVideo(cascadedFolder, mountManager);
    }
    
    
}
