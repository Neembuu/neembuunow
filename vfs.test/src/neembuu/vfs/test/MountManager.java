/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.vfs.test;

import java.io.File;
import java.nio.channels.SeekableByteChannel;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import jpfm.DirectoryStream;
import jpfm.FileAttributesProvider;
import jpfm.FileType;
import jpfm.JPfm;
import jpfm.SystemUtils;
import jpfm.VolumeVisibility;
import jpfm.fs.FSUtils;
import jpfm.mount.Mount;
import jpfm.mount.MountFlags;
import jpfm.mount.MountParams;
import jpfm.mount.MountParamsBuilder;
import jpfm.mount.Mounts;
import jpfm.volume.CommonFileAttributesProvider;
import jpfm.volume.VeryBigFile;
import jpfm.volume.vector.VectorDirectory;
import jpfm.volume.vector.VectorNode;
import jpfm.volume.vector.VectorRootDirectory;
import neembuu.vfs.NeembuuVirtualFileSystem;
import neembuu.vfs.file.ConstrainUtility;
import neembuu.vfs.file.MonitoredHttpFile;
import neembuu.vfs.file.SeekableConnectionFile;
import neembuu.vfs.file.TroubleHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class MountManager {
    private final String[]filesToMount;
    private final String mountLocation;
    private final String heapLocation;

    //NeembuuVirtualFileSystem_old fileSystem;
    private NeembuuVirtualFileSystem fileSystem;
    private final VectorRootDirectory volume = new VectorRootDirectory(10, 3,CommonFileAttributesProvider.DEFAULT);
    
    Mount mount;
    final JPfm.Manager jpfmManager;
    
    final FrameProvider fp;

    private static final Logger LOGGER = Logger.getLogger(MountManager.class.getName());;
    
    private final TroubleHandler troubleHandler;
    
    
    public MountManager(String[] filesToMount, String mountLocation, String heapLocation,FrameProvider fp) {
        this.filesToMount = filesToMount;
        this.mountLocation = mountLocation;
        this.heapLocation = heapLocation;
        this.jpfmManager = makeManager();
        this.fp = fp;
        
        troubleHandler = new SimpleTroubleHandler(fp);
        createVolume();
    }
    
    private JPfm.Manager makeManager(){
        JPfm.Manager manager = null;
        
        try{
            manager =  JPfm.setDefaultManager(
                /*new JPfm.DefaultManager.LibraryLoader() {
                    @Override
                    public boolean loadLibrary(Logger logger) {
                        try {
                            if(SystemUtils.IS_OS_WINDOWS){
                                if(MachineUtils.is64Bit(MachineUtils.getRuntimeSystemArchitecture())){
                                    System.load("f:\\neembuu\\nbvfs_native\\jpfm\\VS_project_files\\x64\\Release\\jpfm.dll");
                                    logger.info("using f:\\neembuu\\nbvfs_native\\jpfm\\VS_project_files\\x64\\Release\\jpfm.dll");
                                }else {
                                    System.load("f:\\neembuu\\nbvfs_native\\jpfm\\VS_project_files\\Release\\jpfm.dll");
                                    logger.info("using f:\\neembuu\\nbvfs_native\\jpfm\\VS_project_files\\Release\\jpfm.dll");
                                }
                            }
                            else{ 
                                System.load("/Volumes/Files and Workspace/neembuu/nbvfs_native/jpfm_mac/jpfm_xcode/build/Debug/libjpfm.dylib");
                            }
                            return true;
                        } catch (Exception e) {
                            return false;
                        }
                    }
                },
                true*/
            );
        }catch(Exception a){
            javax.swing.JOptionPane.showMessageDialog(fp.getJFrame(),
                    "Could not initialize Java pismo file mount\n"+
                    "Pismo file mount might not be installed.\n"+
                    "Actual error message="+a.getMessage(),
                    "Error in initialize JPfm",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                    //,GlobalTestSettings.ONION_EMOTIONS.I_AM_DEAD);
            System.exit(-1);
        }
        return manager;
    }
    
    public void mount(){
        final MountFlags mountFlags = new MountFlags.Builder()
                .setForceUnbuffered()
                //.setWorldRead()
                //.setSystemVisible()
                .build();
        try{
            mount = Mounts.mount(new MountParamsBuilder()
                    .set(MountParams.ParamType.LISTENER, new MountListenerImpl(mountLocation, fp))
                    .set(MountParams.ParamType.MOUNT_LOCATION, mountLocation)//.toString())
                    .set(MountParams.ParamType.FILE_SYSTEM, fileSystem)
                    .set(MountParams.ParamType.EXIT_ON_UNMOUNT, false)
                    .set(MountParams.ParamType.VOLUME_VISIBILITY, VolumeVisibility.GLOBAL)
                    .set(MountParams.ParamType.MOUNT_FLAGS, mountFlags)
                    .build());
        }catch(Exception any){
            LOGGER.log(Level.INFO," ",any);
            System.exit(1);
        }
    }
    
    private void createVolume() {
        fileSystem = new NeembuuVirtualFileSystem(volume);        
        final VectorDirectory realfiles = new VectorDirectory("realfiles", volume);

        if(SystemUtils.IS_OS_WINDOWS){
            VeryBigFile veryBigFile = new VeryBigFile(volume);
            volume.add(veryBigFile);
        }

        for(String s : filesToMount){
            if(s.startsWith("http://") || s.startsWith("https://")){
                System.out.println("Adding file="+s);
                try{
                    MonitoredHttpFile mhttpfile = 
                        new MonitoredHttpFile_Builder_Test()
                            .setFileSize(-1)
                            .setStoragePath(heapLocation)
                            .setParent(volume)
                            .setUrl(s)
                            .setFileName(s.substring(s.lastIndexOf('/')+1))
                            .setTroubleHandler(troubleHandler)
                            .build();
                    decideDirectory(mhttpfile);                    
                }catch(Exception a){
                    LOGGER.log(Level.INFO," ",a);
                }
            }else {
                File ff = new File(s);
                if(!ff.exists()){
                    LOGGER.log(Level.INFO, "ignoring : file not found={0}", s);
                    continue;}
                try{
                    ThrottledRealFile file1 = new ThrottledRealFile(ff.getAbsolutePath(),ThrottledRealFile.INVALID_CPS,realfiles);
                    realfiles.add(file1);
                }catch(Exception a){LOGGER.log(Level.INFO," ",a);}
            }
        }
        
        try{
            for(FileAttributesProvider fap : volume){
                if(fap instanceof DirectoryStream){
                    ConstrainUtility.constrain((DirectoryStream)fap);
                }
            }
        }catch(Exception a){
            a.printStackTrace(System.err);
        }
        
        String basicPath = null;
        if(SystemUtils.IS_OS_WINDOWS){
            basicPath = "J:\\neembuu\\realfiles\\";
        }else if(SystemUtils.IS_OS_LINUX){
            basicPath = "/media/j/neembuu/realfiles/";
        }else {// assume mac, as mac may be marked as either
            //mac or darwin
            basicPath = "/Volumes/MIDS/neembuu/realfiles/";
        }

        try{
            File[]files = new File(basicPath).listFiles();
            for (int i = 0; i < files.length; i++) {
                String nextPath = files[i].getAbsolutePath();
                try{
                    ThrottledRealFile file1 = new ThrottledRealFile(nextPath,ThrottledRealFile.INVALID_CPS,realfiles);
                    realfiles.add(file1);
                }catch(Exception e){
                    LOGGER.log(Level.FINE,"could not put file",e);
                }
            }
        }catch(Exception any){
            LOGGER.log(Level.FINE,"could not put file",any);
        }
        
        if(realfiles.size()>0){
            volume.add(realfiles);
        }
    }
    
    private void decideDirectory(MonitoredHttpFile mhttpfile){
        try{
            int indx = -1;
            String n = mhttpfile.getName();
            indx = Integer.parseInt(n.substring(n.length()-3));
            n = n.substring(0,n.length()-4);
            VectorNode suitableParent = findNode(n);
            suitableParent.add(mhttpfile);
            mhttpfile.setParent(suitableParent);
            mhttpfile.getDownloadConstrainHandler().setIndex(indx);
            return;
        }catch(Exception a){
            
        }
        volume.add(mhttpfile);
    }
    
    private VectorNode findNode(String name){
        for(FileAttributesProvider fap : volume){
            if(fap.getName().equals(name) && fap.getFileType()==FileType.FOLDER){
                return (VectorNode)fap;
            }
        }
        VectorDirectory vd = new VectorDirectory(name, volume);
        volume.add(vd);
        return vd;
    }
    
    private SeekableConnectionFile findSCF(String name){
        for(FileAttributesProvider fap : volume){
            System.out.println(fap+" finding "+name);
            if(fap.getName().equals(name) && fap.getFileType()==FileType.FILE){
                return (SeekableConnectionFile)fap;
            }
        }
        return null;
    }
    
    
    
    ////////////////////////EXPORTED METHODS/////////////////////
    void unMount()throws Exception{
        Thread [] t = new Thread[mount.getThreadGroup(jpfmManager).activeCount()];
        mount.getThreadGroup(jpfmManager).enumerate(t);
        System.out.println("++thread list++");
        for (int i = 0; i < t.length; i++) {
            Thread thread = t[i];
            System.out.println(thread);
        }System.out.println("--thread list--");
        
        mount.unMount();
        mount = null;
        System.out.println("unmounted");
    }
    
    private JPanel addFile(String fileName,String s)throws Exception{
        MonitoredHttpFile mhttpfile = 
            new MonitoredHttpFile_Builder_Test()
                .setFileSize(-1)
                .setStoragePath(heapLocation)
                .setParent(volume)
                .setUrl(s)
                .setTroubleHandler(troubleHandler)
                .setFileName(fileName)
                .build();

        volume.add(mhttpfile);
        return mhttpfile.getFilePanel();
    }
    
    private SeekableByteChannel get(String fn){
        return new SeekableByteChannel_from_SeekableConnectionFile(findSCF(fn));
    }
    
    ////////////////////////EXPORTING INTERFACE/////////////////////
    final MountManagerService mountManagerService = new MountManagerService() {
        @Override public VectorRootDirectory volume() { return volume; }
        @Override public void unMount() throws Exception { MountManager.this.unMount(); }
        @Override public void printPendingOps() { FSUtils.printIncompleteOperationsBasic(fileSystem); }
        @Override public JPanel addNewFile(String fileName, String url)throws Exception { return addFile(fileName, url); }
        @Override public SeekableByteChannel get(String fn) { return MountManager.this.get(fn); }

        @Override public void showOpenIds() {
            Iterator<FileAttributesProvider> it = volume.iterator();
            System.out.println("++open id of all++");
            while(it.hasNext()){
                FileAttributesProvider  next = it.next();
                System.out.print(next.getName());
                System.out.print(" ");
                System.out.println(next.getFileDescriptor());
                System.out.print(" ");
                System.out.print(" class="+next.getClass());
            }
            System.out.println("--open id of all--");
        }
        
    };
}
