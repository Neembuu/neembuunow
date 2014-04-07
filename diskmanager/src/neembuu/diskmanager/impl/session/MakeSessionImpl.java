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

package neembuu.diskmanager.impl.session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.LogRecord;
import neembuu.diskmanager.DiskManagerParams;
import neembuu.diskmanager.MakeSession;
import neembuu.diskmanager.NioUtils;
import neembuu.diskmanager.Nomenclature.SessionName;
import neembuu.diskmanager.Nomenclature.SessionNameMutable;
import neembuu.diskmanager.Session;
import neembuu.diskmanager.impl.JavaStringHashAlgo;

/**
 *
 * @author Shashank Tulsyan
 */
public class MakeSessionImpl implements MakeSession{
    private final String type;
    private final Object lock;
    private final DiskManagerParams dmp;
    
    private final DefaultSession oldSession;

    public MakeSessionImpl(String type, Object lock, DiskManagerParams dmp) {
        this.type = type; this.lock = lock; this.dmp = dmp; this.oldSession = null;
    }
    
    public MakeSessionImpl(DefaultSession oldSession, Object lock, DiskManagerParams dmp) {
        this.type = oldSession.getType(); this.lock = lock; this.dmp = dmp;
        this.oldSession = oldSession;
    }

    
    private SeekableByteChannel channel = null;
    
    private Path tempPath  = null;
    private Path tempMetaDataPath = null;
    private Path finalMetaDataPath = null;
    
    @Override
    public SeekableByteChannel metaData() throws IOException {
        if(channel!=null)return channel;

        initTempPath();
        
        channel = FileChannel.open(makeMetaDataPath(), StandardOpenOption.READ,
                StandardOpenOption.WRITE,StandardOpenOption.CREATE);
        
        return channel;
    }
    
    private Path makeMetaDataPath(){
        tempMetaDataPath = tempPath.resolve("temp_"+dmp.getNomenclature().sessionMetaDataFileName());
        finalMetaDataPath = tempPath.resolve(dmp.getNomenclature().sessionMetaDataFileName());
        return tempMetaDataPath;
    }
    
    private void initTempPath()throws IOException{
        if(tempPath!=null){
            return;
        }if(oldSession!=null){
            tempPath = oldSession.getSessionPath();
            return;
        }
        Path p = Paths.get(dmp.getBaseStoragePath());
        tempPath = createTempPath(p);
    }
    
    private static Path createTempPath(Path p)throws IOException{
        String tempFolderName = "t"+Math.random();
        Path randomFolder = p.resolve(tempFolderName);
        
        if(!Files.exists(randomFolder)){
            return Files.createDirectory(randomFolder);
        }else{
            return createTempPath(p);
        }
    }

    
    
    @Override
    public void cancel() throws IOException{
        synchronized (lock){
            Exception x=new Exception();
            try{channel.close();}catch(Exception a){x =a;}
            try{ NioUtils.deleteDirectory(tempPath);}catch(IOException a){a.addSuppressed(x); throw a;}
        }
    }

    public final Session createTestSession() throws IOException {
        Path baseStoragePath = Paths.get(dmp.getBaseStoragePath());
        Session r = new DefaultSession(type, baseStoragePath, dmp);
        return r;
    }

    
    @Override
    public void createNew(final CreatNewCallback cnc) {
        if(channel==null){
            throw new IllegalArgumentException("No session data written");
        }
        try{
            channel.position(0);
            final ByteBuffer bb = JavaStringHashAlgo.makeByteBuffer(channel);
            channel.close();
            
            final JavaStringHashAlgo.Hash hash = JavaStringHashAlgo.makeHash(bb);
            
            Path baseStoragePath = Paths.get(dmp.getBaseStoragePath());
            
            final AtomicReference<Path> alreadyExists = new AtomicReference<>();
            final SessionNameMutable hashName = new SessionNameMutable();
            hashName.setMetaHash(hash.getAsString()).setType(type);
            
            checkIfExists(dmp,baseStoragePath, hashName, alreadyExists, bb);
            
            if(alreadyExists.get()!=null){
               Session r = new DefaultSession(type, alreadyExists.get(), dmp);
               cnc.alreadyExists(r);  return;
            } else { 
                if(hashName.getVariant()!=null){
                    fixName(dmp,baseStoragePath, hashName, 0, 4);
                }String name_Str = dmp.getNomenclature().sessionFileName(hashName);
                
                if(oldSession!=null){
                    oldSession.close();
                }
                
                Path sessionDirectory = Paths.get(dmp.getBaseStoragePath(),name_Str);
                Files.deleteIfExists(finalMetaDataPath);// in case this session is
                // being modified, the old copy of session meta data needs to be deleted
                Files.move(tempMetaDataPath, finalMetaDataPath, StandardCopyOption.ATOMIC_MOVE);
                Files.move(tempPath, sessionDirectory, StandardCopyOption.ATOMIC_MOVE);
                
                Session r = new DefaultSession(type, sessionDirectory, dmp);
                cnc.done(r); return;
            }
            
        }catch(Exception a){cnc.failed(a);}
        cnc.failed(null);
    }
    
    private void checkIfExists(final DiskManagerParams dmp,final Path  basePath, final SessionNameMutable hashName,
            final AtomicReference<Path> alreadyExists,final ByteBuffer bb)throws IOException{
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(basePath, new DirectoryStream.Filter<Path>() {
            @Override public boolean accept(Path dir) throws IOException {
                if(!Files.isDirectory(dir)) return false;
                String dirname = dir.getFileName().toString();
                SessionName thisDir = dmp.getNomenclature().getSessionName(dirname);
                
                if(thisDir.getMetaHash().equalsIgnoreCase(hashName.getMetaHash()) ){
                    hashName.setVariant("");
                    if(equalsFile(bb, dir)){alreadyExists.set(dir);return true;}
                }
                return false;
            }
        })) {
            for (Path path : ds) {
                break;
            }
        }
    }
    
    private void fixName(final DiskManagerParams dmp,Path p,
            final SessionNameMutable name, int attempt,final int limit)throws IOException{
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(p, new DirectoryStream.Filter<Path>() {
            @Override public boolean accept(Path dir) throws IOException {
                String nm = dir.getFileName().toString();
                SessionName sn = dmp.getNomenclature().getSessionName(nm);
                return sn.getMetaHash().equalsIgnoreCase(name.getMetaHash());
            }
        })) {
            for (Path path : ds) {
                
                
                name.setVariant(name.getVariant()+((int)(Math.random()*10)));
                if(attempt>limit){
                    throw new UnsupportedOperationException("Either there are too many files with same name or this code is broken");
                }
                fixName(dmp,p, name, attempt+1,limit);
                break;
            }
        }
    }
    
    private boolean equalsFile(ByteBuffer bb, Path dir)throws IOException{
        Path metaDataFile = dir.resolve(dmp.getNomenclature().sessionMetaDataFileName());
        if(!Files.exists(metaDataFile)){return false;}
        FileChannel fc = FileChannel.open(metaDataFile, StandardOpenOption.READ);
        ByteBuffer bb_ =  JavaStringHashAlgo.makeByteBuffer(fc);
        return bb_.equals(bb);
    }
    
    
    @Deprecated private LinkedList<java.util.logging.LogRecord> deleteFileStorage(String fileName) {
        LinkedList<java.util.logging.LogRecord>
               logLater = new LinkedList<LogRecord>();
        /*String fileStorePath;
        try {
            fileStorePath = DefaultFileStorageManager.fileStoragePath(tempPath,dmp,fileName, false);
        } catch (IOException e) {
            LogRecord lr = new LogRecord(Level.INFO,"");
            lr.setThrown(e);
            logLater.add(lr);
            return logLater;
        }
        DefaultFileStorageManager.emptyDirectory(fileStorePath, logLater);*/
        return logLater;
    }
}
