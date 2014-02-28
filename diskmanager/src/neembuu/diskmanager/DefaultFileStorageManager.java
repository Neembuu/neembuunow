/*
 * Copyright (c) 2011 Shashank Tulsyan <shashaanktulsyan@gmail.com>. 
 * 
 * This is part of free software: you can redistribute it and/or modify
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
 * along with this.  If not, see <http ://www.gnu.org/licenses/>.
 */
package neembuu.diskmanager;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import neembuu.util.logging.LoggerUtil;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
final class DefaultFileStorageManager implements FileStorageManager{
    final String store ;
    private final DefaultDiskManager defaultDiskManager;
    private final FileStorageManagerParams fsmp;
    private final Logger readQMThLogger;
    private final LinkedList<DefaultRegionStorageManager> drsms
            = new LinkedList<DefaultRegionStorageManager>();
    
    public DefaultFileStorageManager(
            DefaultDiskManager defaultDiskManager,
            FileStorageManagerParams fsmp) 
            throws IOException{
        this.defaultDiskManager = defaultDiskManager;
        this.fsmp = fsmp;
        
        store = fileStoragePath(defaultDiskManager, fsmp.getFileName(), true);
                 
        LinkedList<java.util.logging.LogRecord>
               logLater = new LinkedList<LogRecord>();

        List<RegionStorageManager> l = analyzePreviousState();
        if(l!=null){
            // first consult diskmanager's callback
            ResumeStateCallback mainCallback = defaultDiskManager.getDiskManagerParams().getResumeStateCallback();
            if(mainCallback!=null){
                if(!mainCallback.resumeState(l)){
                    for(RegionStorageManager rsm : l){
                        logLater.add(new LogRecord(Level.INFO, "(maincallback)going to delete "+rsm));
                        rsm.close();
                    }
                    emptyDirectory(store,logLater);
                    l  = null;
                }
            }
        }
        if(l!=null) {
            if(!fsmp.getResumeStateCallback().resumeState(l)){
                for(RegionStorageManager rsm : l){
                    logLater.add(new LogRecord(Level.INFO, "(internalcallback)going to delete "+rsm));
                    rsm.close();
                }
                emptyDirectory(store,logLater);
                l  = null;
            }
        }

        
        if(l!=null){
            synchronized (drsms){
                drsms.addAll((List)l);
            }    
        }
        
        readQMThLogger = LoggerUtil.getLightWeightHtmlLogger("ReadQueueManagerThread",store,defaultDiskManager.getDiskManagerParams().getMaxReadQueueManagerThreadLogSize());
        for(LogRecord lr : logLater){
            readQMThLogger.log(lr);
        }
    }
    
    static String fileStoragePath(DiskManager defaultDiskManager, String fileName, boolean create)throws IOException{
        String store_path = defaultDiskManager.getDiskManagerParams().getBaseStoragePath() + File.separator +  fileName + "_neembuu_download_data";
        if(new File(store_path).exists()){
            if(new java.io.File(store_path).isFile()){
                throw new IOException("Storage location should be a directory. Given = "+store_path);
            }
        }else if(create) new File(store_path).mkdir();
        return store_path;
    }
    
    @Override
    public Logger getReadQueueManagerThreadLogger() {
        return readQMThLogger;
    }
    
    static void emptyDirectory(String store,LinkedList<java.util.logging.LogRecord> logLater){  
        try{
            File[]f=new File(store).listFiles();
            if(f==null)return;
            for (int i = 0; i < f.length; i++) {
                try{
                    java.nio.file.Files.delete(f[i].toPath());
                }catch(java.nio.file.FileSystemException fse){
                    //ignore
                }
                //if(!f[i].delete())LOGGER.log(Level.INFO,"Could not delete {0}",f[i]);
            }
        }catch(Exception any){
            LogRecord lr = new LogRecord(Level.INFO,"");
            lr.setThrown(any);
            logLater.add(lr);
        }        
    }
    
    private List<RegionStorageManager> analyzePreviousState(){
        List<RegionStorageManager>
                        handlers = new LinkedList<RegionStorageManager>();
        try{
            File[]fs = new File(store).listFiles();
            LinkedList<File> files = new LinkedList();
            for (int i = 0; i < fs.length; i++) {
                if(!fs[i].getName().endsWith(".partial")){
                    try{
                        java.nio.file.Files.delete(fs[i].toPath());
                    }catch(Exception a){
                        a.printStackTrace(System.err);
                    }
                }
                else if(fs[i].getName().indexOf("_0x")<0){}
                else {files.add(fs[i]);}
            }
            Collections.sort(files,new Comparator<File>(){
                @Override
                public int compare(File o1, File o2) {
                    String f1name = o1.getName();
                    f1name = f1name.substring(f1name.indexOf("_0x")+3,f1name.lastIndexOf("."));
                    String f2name = o2.getName();
                    f2name = f2name.substring(f2name.indexOf("_0x")+3,f2name.lastIndexOf("."));
                    
                    return (int)(-Long.parseLong(f1name,16) + Long.parseLong(f2name,16));
                }
                
            });fs=new File[files.size()];fs=files.toArray(fs);
            for (int i = 0; i < fs.length; i++) {
                File nextFile = fs[i];
                String nm = nextFile.getName();
                long startingOffset = Long.parseLong(nm.substring(nm.indexOf("_0x")+3,nm.lastIndexOf('.')),16);

                RegionStorageManager regionStorageManager  = null;
                regionStorageManager = new DefaultRegionStorageManager(
                        this, 
                        startingOffset,
                        nextFile.length(),
                        nextFile.getAbsolutePath());
                handlers.add(regionStorageManager);
            }
        }catch(Exception any){
            Logger.getGlobal().log(Level.INFO,"",any);
            //readQMThLogger.log(Level.INFO,"",any);
            return null;
        }
        return handlers;
    }

    @Override
    public final RegionStorageManager getRegionStorageManagerFor(long startingOffset)throws IOException {
        synchronized (drsms){
            DefaultRegionStorageManager drsm = new DefaultRegionStorageManager(this, startingOffset);
            drsms.add(drsm);
            return drsm;
        }
    }

    @Override
    public final DiskManager getDiskManager() {
        return defaultDiskManager;
    }
    
    @Override
    public final void completeSession(File outputFile, long fileSize)throws Exception{
        synchronized (drsms){
            Collections.sort(drsms, new Comparator<RegionStorageManager>() {
                @Override
                public int compare(RegionStorageManager o1, RegionStorageManager o2) {
                    return (int)(o1.starting() - o2.starting());
                }
            });
            
            if(outputFile!=null){
                long t = 0; long s=0,e=0;
                for (int i = 0; i < drsms.size(); i++) {
                    s = drsms.get(i).starting(); e = drsms.get(i).endingByFileSize();

                    if(i+1 < drsms.size()){
                        e = Math.min(e, drsms.get(i+1).starting()-1);
                    }

                    t+= e - s + 1 ;
                }
                if(t!=fileSize){
                    Exception a = new IllegalStateException("Download incomplete or corrupt. Total downloaded="+t+" Expected filesize="+fileSize);
                    //a.printStackTrace(System.err);// warning
                    throw a;
                }

                
                FileChannel fc = //new RandomAccessFile(outputFile, "w").getChannel();
                        FileChannel.open(outputFile.toPath(),
                            StandardOpenOption.WRITE,StandardOpenOption.CREATE
                        );
                for (RegionStorageManager rsm : drsms) {
                    rsm.tranferTo(fc.position(rsm.starting()));
                }
                fc.force(true);
                fc.close();
            }
            
            
            for (RegionStorageManager rsm : drsms) {
                rsm.close();
            }

            DefaultRegionStorageManager.closeLogger(readQMThLogger);
            
            File[]f=new File(store).listFiles();
            if(f==null)return;
            
            boolean canDeleteFolder = true;
            for (int i = 0; i < f.length; i++) {
                try{
                    java.nio.file.Files.delete(f[i].toPath());
                }catch(java.nio.file.FileSystemException fse){
                    fse.printStackTrace(System.err);
                    canDeleteFolder = false;
                }
            }
            if(canDeleteFolder)
                java.nio.file.Files.delete(new File(store).toPath());
            
        }
    }

    @Override
    public void close() throws Exception {
        synchronized (drsms){
            for (RegionStorageManager rsm : drsms) {
                rsm.close();
            }
        }
        DefaultRegionStorageManager.closeLogger(readQMThLogger);
    }
    
    
}
