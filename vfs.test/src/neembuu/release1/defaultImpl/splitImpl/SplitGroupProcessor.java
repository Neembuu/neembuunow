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

package neembuu.release1.defaultImpl.splitImpl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import jpfm.DirectoryStream;
import jpfm.FileAttributesProvider;
import jpfm.fs.splitfs.CascadableSplitFS;
import jpfm.mount.BasicCascadeMount;
import neembuu.release1.api.ui.access.MinimalistFileSystem;
import neembuu.vfs.file.ConstrainUtility;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public class SplitGroupProcessor  {

    
    public List<SeekableConnectionFile> canHandle(List<SeekableConnectionFile> virtualFiles) {
        final ArrayList<SeekableConnectionFile> handleAble = new ArrayList<SeekableConnectionFile>();
        if (virtualFiles.size() < 1) return null;
        
        for (SeekableConnectionFile vf : virtualFiles) {
            int index = -2;
            
            String n = vf.getName();
            try {
                index = Integer.parseInt(n.substring(n.length() - 3));
            } catch (Exception a) {
                continue;
            }
            if (vf.getDownloadConstrainHandler().index() < 0) {
                vf.getDownloadConstrainHandler().setIndex(index);
            }else if(vf.getDownloadConstrainHandler().index() != index){
                throw new IllegalStateException("Illegally contrained files, expected index="+index+ " found="+
                        vf.getDownloadConstrainHandler().index());
            }   
            handleAble.add(vf);
            if (index < 0) { return null; }
        }
        return handleAble;
    }

    public SplitGroupSession handle(List<SeekableConnectionFile> splitVirtualFiles,
            MinimalistFileSystem addRemoveFromFileSystem
            ) {
        
        List<SeekableConnectionFile> sessions_ = canHandle(splitVirtualFiles);
        if(sessions_.size()!=splitVirtualFiles.size()){
            throw new IllegalStateException("Number of splits expected ="
                    + sessions_.size()+ " found="+splitVirtualFiles.size());
        }
        Set<Integer> indices = new LinkedHashSet<Integer>();
        
        ArrayList<SeekableConnectionFile> scfs = new ArrayList<SeekableConnectionFile>();
        for(SeekableConnectionFile vf : splitVirtualFiles){
            if(!indices.add(vf.getDownloadConstrainHandler().index())){
                throw new IllegalStateException("Trying to constraint multiple files with same index="
                        + vf.getDownloadConstrainHandler().index());
            }
            scfs.add(vf);
        }

        Set<FileAttributesProvider> files = new LinkedHashSet<FileAttributesProvider>();
        synchronized (splitVirtualFiles) {
            for (SeekableConnectionFile vf : splitVirtualFiles) {
                files.add(vf);
            }
        }

        String name = splitVirtualFiles.get(0).getName();
        name = name.substring(0, name.lastIndexOf("."));

        CascadableSplitFS.CascadableSplitFSProvider cascadableSplitFS = new CascadableSplitFS.CascadableSplitFSProvider(files, name);
        
        BasicCascadeMount bcm = addRemoveFromFileSystem.cascadeMount(cascadableSplitFS);
        
        ConstrainUtility.constrain((Iterable)scfs);
        
        
        // remove splited files .001 , .002 ... from virtual folder
        // to avoid confusion
        synchronized (splitVirtualFiles) {
            for (SeekableConnectionFile vf : splitVirtualFiles) {
                addRemoveFromFileSystem.remove(vf);
                vf.setParent((DirectoryStream)bcm.getFileSytem().getRootAttributes());
                // the file does not exist, therefore the open button should be
                // disabled
                //vf.getUI().deactivateOpenButton(true);
            }
        }
        SplitGroupSession sgs = new SplitGroupSession(bcm, splitVirtualFiles);
        return sgs;
    }
    
}
