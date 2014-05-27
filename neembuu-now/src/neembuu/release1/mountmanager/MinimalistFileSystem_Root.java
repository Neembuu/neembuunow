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

import java.nio.file.StandardOpenOption;
import jpfm.DirectoryStream;
import jpfm.FileAttributesProvider;
import jpfm.JPfmMutableContainable;
import jpfm.fs.BasicCascadableProvider;
import jpfm.fs.SimpleReadOnlyFileSystem;
import jpfm.mount.BasicCascadeMount;
import jpfm.util.UniversallyValidFileName;
import jpfm.volume.vector.VectorRootDirectory;
import neembuu.diskmanager.DiskManager;
import neembuu.diskmanager.Session;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.file.OnlineFile;
import neembuu.release1.api.ui.access.MinimalistFileSystem;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.file.AskResume;
import neembuu.vfs.file.SeekableConnectionFile;
import neembuu.vfs.file.SeekableConnectionFileParams;
import neembuu.vfs.file.TroubleHandler;
import neembuu.vfs.progresscontrol.ThrottleFactory;
import neembuu.vfs.readmanager.impl.SeekableConnectionFileImplBuilder;

/**
 *
 * @author Shashank Tulsyan
 */
public class MinimalistFileSystem_Root implements MinimalistFileSystem {

    private final VectorRootDirectory volume;
    private final TroubleHandler troubleHandler;
    private final DiskManager diskManager;
    private final RealFileProvider realFileProvider;
    private final SimpleReadOnlyFileSystem fs;

    public MinimalistFileSystem_Root(VectorRootDirectory volume, TroubleHandler troubleHandler, DiskManager diskManager, RealFileProvider realFileProvider, SimpleReadOnlyFileSystem fs) {
        this.volume = volume;
        this.troubleHandler = troubleHandler;
        this.diskManager = diskManager;
        this.realFileProvider = realFileProvider;
        this.fs = fs;
    }
    
    
    @Override
    public SeekableConnectionFile create(final OnlineFile f,Session s)throws Exception {
        String fileName = getSuitableFileName(f.getName());
        
        SeekableConnectionFile connectionFile
                = SeekableConnectionFileImplBuilder.build(new SeekableConnectionFileParams.Builder()
                        .setFileSize(f.getFileSize())
                        .setSession(s)
                        .setParent(volume)
                        .setNewConnectionProvider(f.getConnectionProvider())
                        .setTroubleHandler(troubleHandler)
                        .setFileName(fileName)
                        .setAskResume(new AskResumeImpl(f.getConnectionProvider()))
                        .setThrottleFactory(ThrottleFactory.General.SINGLETON)
                        .build()
                );
        
        return connectionFile;
    }

    @Override public RealFileProvider getRealFileProvider() {
        return realFileProvider;
    }

    @Override
    public void remove(FileAttributesProvider v) {
        volume.remove(v);
        if(v instanceof JPfmMutableContainable){
            ((JPfmMutableContainable)v).setParent(null);
        }
    }

    @Override
    public void add(FileAttributesProvider v) {
        if(volume.contains(v)){
            throw new IllegalStateException("Already present");
        }
        volume.add(v);
        if(v instanceof JPfmMutableContainable){
            ((JPfmMutableContainable)v).setParent(volume);
        }
    }
    
    @Override public String getSuitableFileName(String filename){
        return getSuitableFileName(filename, volume);
    }
    
    private String getSuitableFileName(String filename, DirectoryStream parent){
//        if(filename.length()>50){
//            filename = filename.substring(0,50)
//                    + /*extension*/ filename.substring(filename.length()-4);
//        }
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

    @Override
    public BasicCascadeMount cascadeMount(BasicCascadableProvider basicCascadable)  {
        return fs.cascadeMount(basicCascadable);
    }
    
    
    private static final class AskResumeImpl implements AskResume {
        private final NewConnectionProvider ncp;

        public AskResumeImpl(NewConnectionProvider ncp) {
            this.ncp = ncp;
        }
        
        @Override public boolean resume() {
            if (ncp.estimateCreationTime(1) >= Integer.MAX_VALUE) {
                return false;
            }
            // for rapidshare type of links clean the
            // download directory and start fresh

            // retain stuff for others
            return true;
        }
    };
}
