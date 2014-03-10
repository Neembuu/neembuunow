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
package neembuu.release1.mountmanager;

import java.io.File;
import jpfm.DirectoryStream;
import jpfm.FileAttributesProvider;
import jpfm.util.UniversallyValidFileName;
import jpfm.volume.vector.VectorRootDirectory;
import neembuu.diskmanager.DiskManager;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.ui.access.AddRemoveFromFileSystem;
import neembuu.vfs.file.FileBeingDownloaded;
import neembuu.vfs.file.SeekableConnectionFile;
import neembuu.vfs.file.TroubleHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class AddRemoveFromFileSystem_Root implements AddRemoveFromFileSystem {

    private final VectorRootDirectory volume;
    private final TroubleHandler troubleHandler;
    private final DiskManager diskManager;
    private final RealFileProvider realFileProvider;

    public AddRemoveFromFileSystem_Root(VectorRootDirectory volume, TroubleHandler troubleHandler, DiskManager diskManager,RealFileProvider realFileProvider) {
        this.volume = volume;
        this.troubleHandler = troubleHandler;
        this.diskManager = diskManager;
        this.realFileProvider = realFileProvider;
    }
    
    
    @Override
    public VirtualFile create(neembuu.release1.api.File f)throws Exception {
        return createImpl(f);
    }

    @Override
    public void remove(VirtualFile v) {
        volume.remove(v.getConnectionFile());
        v.getConnectionFile().setParent(null);
    }

    @Override
    public void add(VirtualFile v) {
        if(volume.contains(v.getConnectionFile())){
            throw new IllegalStateException("Already present");
        }
        volume.add(v.getConnectionFile());
        v.getConnectionFile().setParent(volume);
    }
    
    public String getSuitableFileName(String filename, DirectoryStream parent){
        if(filename.length()>50){
            filename = filename.substring(0,50)
                    + /*extension*/ filename.substring(filename.length()-4);
        }
        filename = UniversallyValidFileName.makeUniversallyValidFileName(filename);
        filename = checkConflict(filename,parent);
        return filename;
    }
    
    private String checkConflict(String filename,DirectoryStream parent){
        for(FileAttributesProvider fap : parent){
            if(fap.getName().equalsIgnoreCase(filename)){
                filename="2_"+filename;
                return checkConflict(filename,parent);
            }
        }
        return filename;
    }
    
    private VirtualFile createImpl(neembuu.release1.api.File f)throws Exception{
        String fileName = getSuitableFileName(f.fileName(), volume);
        final SeekableConnectionFile connectionFile
                = SeekableConnectionFile_1to1.create(
                        f.fileName(),
                        f.fileSize(),
                        f.getConnectionProvider(),
                        diskManager,
                        troubleHandler, 
                        volume);        
        VirtualFile vf = new VirtualFile() {

            @Override
            public FileBeingDownloaded getFileUIAccess() {
                return connectionFile;
            }
            
            @Override
            public SeekableConnectionFile getConnectionFile() {
                return connectionFile;
            }

            @Override
            public boolean canGetRealFile() {
                return true;
            }

            @Override
            public java.io.File getRealFile() {
                return realFileProvider.getRealFile(this);
            }
        };
        return vf;
    }
}
