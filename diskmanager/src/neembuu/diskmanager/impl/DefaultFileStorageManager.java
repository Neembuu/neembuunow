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
package neembuu.diskmanager.impl;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import neembuu.diskmanager.DiskManagerParams;
import neembuu.diskmanager.FileStorageManager;
import neembuu.diskmanager.FileStorageManagerParams;
import neembuu.diskmanager.LoggerCreateSPI.Type;
import neembuu.diskmanager.NioUtils;
import neembuu.diskmanager.Nomenclature;
import neembuu.diskmanager.Nomenclature.RegionStorage;
import neembuu.diskmanager.RegionStorageManager;
import neembuu.diskmanager.ResumeStateCallback;
import neembuu.diskmanager.impl.channel.ChannelList;

/**
 *
 * @author Shashank Tulsyan <shashaanktulsyan@gmail.com>
 */
public final class DefaultFileStorageManager implements FileStorageManager{
    final String store ;
    private final DiskManagerParams dmp;
    private final FileStorageManagerParams fsmp;
    private final LinkedList<DefaultRegionStorageManager> drsms
            = new LinkedList<DefaultRegionStorageManager>();
    
    private final ChannelList channels 
            = new ChannelList();
    
    private volatile boolean open = false;
    
    private final Path sessionPath;
    private final CloseCallback closeCallback;
    
    public DefaultFileStorageManager(DiskManagerParams dmp,FileStorageManagerParams fsmp,
            Path basePath, CloseCallback closeCallback)  throws IOException{
        this.dmp = dmp;this.fsmp = fsmp;this.sessionPath = basePath;
        this.closeCallback = closeCallback;
        store = fileStoragePath(basePath,dmp,fsmp.getFileName(), true);
                 
        List<RegionStorageManager> l = analyzePreviousState();
        LinkedList<java.util.logging.LogRecord> logLater = new LinkedList<LogRecord>();
        consultAndResumeState(l,logLater);
        
        if(l!=null){
            synchronized (drsms){
                drsms.addAll((List)l);
            }    
        }
        
        open = true;
        
        if(logLater.isEmpty())return;
        Logger l1 = createLogger(DefaultFileStorageManager.class.getName());
        for(LogRecord lr : logLater){l1.log(lr);}
        for (int i = 0; i < l1.getHandlers().length; i++) {
            l1.getHandlers()[i].close();
        }
    }
    
    private void consultAndResumeState(List<RegionStorageManager> l,List<java.util.logging.LogRecord> logLater){
        if(l!=null){ // first consult diskmanager's callback
            ResumeStateCallback mainCallback = dmp.getResumeStateCallback();
            if(mainCallback!=null){
                if(!mainCallback.resumeState(l)){
                    for(RegionStorageManager rsm : l){
                        logLater.add(new LogRecord(Level.INFO, "(maincallback)going to delete "+rsm));
                        closeAndLog(rsm, logLater);
                    }
                    emptyDirectory(store,logLater);
                    l  = null;
                }
            }
        }if(l!=null) {
            if(!fsmp.getResumeStateCallback().resumeState(l)){
                for(RegionStorageManager rsm : l){
                    logLater.add(new LogRecord(Level.INFO, "(internalcallback)going to delete "+rsm));
                    closeAndLog(rsm, logLater);
                }
                emptyDirectory(store,logLater);
                l  = null;
            }
        }
    }
    
    private static void closeAndLog(RegionStorageManager rsm,List<java.util.logging.LogRecord> logLater){
        try{
            rsm.close();
        }catch(Exception a){
            LogRecord lr = new LogRecord(Level.SEVERE,"");
            lr.setThrown(a);
            logLater.add(lr);
        }
    }
    
    static String fileStoragePath(Path basePath, DiskManagerParams dmp, String fileName, boolean create)throws IOException{
        Path store_path = basePath.resolve(
                dmp.getNomenclature().getFileStorage().makeName(fileName));
        if(Files.exists(store_path)){
            if(!Files.isDirectory(store_path)){
                throw new IOException("Storage location should be a directory. Given = "+store_path);
            }
        }else if(create) Files.createDirectory(store_path);
        return store_path.toAbsolutePath().toString();
    }
    
    static void emptyDirectory(String store,List<java.util.logging.LogRecord> logLater){  
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
        List<RegionStorageManager> handlers = new LinkedList<RegionStorageManager>();
        try{
            File[]fs = new File(store).listFiles();
            LinkedList<File> files = new LinkedList();
            for (File f : fs) {
                if (dmp.getNomenclature().getRegionStorage().getStartingOffset(f.getName()) 
                        == RegionStorage.INVALID_REGION) {/*ignore*/  }
                else { files.add(f); }
            }
            Collections.sort(files,new RegionFileComparator(dmp.getNomenclature()));
            fs=new File[files.size()];fs=files.toArray(fs);
            for (File nextFile : fs) {
                String nm = nextFile.getName();
                long startingOffset = dmp.getNomenclature().getRegionStorage().getStartingOffset(nm);
                RegionStorageManager regionStorageManager = new DefaultRegionStorageManager(
                        dmp, this,
                        startingOffset,
                        nextFile.length(),
                        fsmp.getEstimatedFileSize(),
                        nextFile.getAbsolutePath());
                handlers.add(regionStorageManager);
            }
        }catch(Exception any){
            Logger.getGlobal().log(Level.INFO,"",any);//readQMThLogger.log(Level.INFO,"",any);
            return null;
        }return handlers;
    }

    private static final class RegionFileComparator implements Comparator<File>{
        private final Nomenclature n;
        public RegionFileComparator(Nomenclature n) {
            this.n = n;
        }
        @Override public int compare(File o1, File o2) {
            return (int)(
                    -n.getRegionStorage().getStartingOffset(o1.getName()) 
                    + n.getRegionStorage().getStartingOffset(o2.getName())
            );
        }
    };

    @Override public Logger createLogger(String nm) {
        nm  = dmp.getNomenclature().getLogStorage().make(nm);
        return Utils.createLogger(nm, dmp, this, Type.File);
    }

    @Override public boolean isOpen() {
        return open;
    }

    @Override public SeekableByteChannel getOrCreateResource(String nm,OpenOption... openOptions) throws Exception{
        synchronized(drsms){
            return channels.addAndGet(FileChannel.open(Paths.get(store,nm),openOptions));
        }
    }
    
    @Override public final RegionStorageManager getRegionStorageManagerFor(long startingOffset)throws IOException {
        synchronized (drsms){
            DefaultRegionStorageManager drsm = new DefaultRegionStorageManager(
                    dmp, this, startingOffset,fsmp.getEstimatedFileSize());
            drsms.add(drsm);
            return drsm;
        }
    }
    
    @Override public final void copyIfCompleteTo(SeekableByteChannel output, long fileSize)throws Exception{
        synchronized (drsms){
            Collections.sort(drsms, new Comparator<RegionStorageManager>() {
                @Override public int compare(RegionStorageManager o1, RegionStorageManager o2) {
                    return (int)(o1.startingOffset()- o2.startingOffset());  }});
            if(output!=null){
                saveFileTo(output,fileSize); }
        }
    }

    @Override public void delete() throws Exception{
        synchronized (drsms){
            close();
            for (RegionStorageManager rsm : drsms) {
                rsm.close();
            }
            
            File[]files=new File(store).listFiles();
            if(files==null)return;
            //file close first
            deleteFiles(files);
        }
    }

    @Override public void close() throws Exception {
        synchronized (drsms){
            open = false;
            for (RegionStorageManager rsm : drsms) {
                System.err.println("DefaultFileStorageManager line 263 closing : "+fsmp.getFileName()+" @ "+rsm);
                rsm.close();
            }
        }
        channels.close();
        if(closeCallback!=null){
            closeCallback.closed(this);
        }
        //DefaultRegionStorageManager.closeLogger(readQMThLogger);
    }
    
    private void saveFileTo(SeekableByteChannel output,long fileSize)throws Exception{
        long t = 0; long s=0,e=0;
        for (int i = 0; i < drsms.size(); i++) {
            s = drsms.get(i).startingOffset(); e = drsms.get(i).endingByFileSize();

            if(i+1 < drsms.size()){
                e = Math.min(e, drsms.get(i+1).startingOffset()-1);
            }

            t+= e - s + 1 ;
        }
        if(t!=fileSize){
            Exception a = new IllegalStateException("Download incomplete or corrupt. Total downloaded="+t+" Expected filesize="+fileSize);
            //a.printStackTrace(System.err);// warning
            throw a;
        }

        for (RegionStorageManager rsm : drsms) {
            rsm.transferTo_ReOpenIfRequired(output.position(rsm.startingOffset()));
        }
    }
    
    private void deleteFiles(File[]f)throws Exception{
        boolean canDeleteFolder = true;
        for (int i = 0; i < f.length; i++) {
            try{
                java.nio.file.Files.delete(f[i].toPath());
            }catch(java.nio.file.FileSystemException fse){
                fse.printStackTrace(System.err);
                canDeleteFolder = false;
            }
        }
        if(canDeleteFolder){
            NioUtils.deleteDirectory(Paths.get(store));//java.nio.file.Files.delete(new File(store).toPath());
        }
    }
}
