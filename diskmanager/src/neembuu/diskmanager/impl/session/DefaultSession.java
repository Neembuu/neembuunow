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
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import neembuu.diskmanager.DiskManagerParams;
import neembuu.diskmanager.FileStorageManager;
import neembuu.diskmanager.FileStorageManagerParams;
import neembuu.diskmanager.LoggerCreateSPI;
import neembuu.diskmanager.NioUtils;
import neembuu.diskmanager.Session;
import neembuu.diskmanager.impl.CloseCallback;
import neembuu.diskmanager.impl.DefaultFileStorageManager;
import neembuu.diskmanager.impl.JavaStringHashAlgo;
import neembuu.diskmanager.impl.Utils;
import neembuu.diskmanager.impl.channel.ChannelList;

/**
 *
 * @author Shashank Tulsyan
 */
public class DefaultSession implements Session {

    private final String type;
    private final DiskManagerParams dmp;
    private final Path sessionPath;
    private boolean restoredCorrectly = false;
    
    private final ChannelList resources = new ChannelList();
    private final List<FileStorageManager> fsms = new LinkedList<FileStorageManager>();

    public DefaultSession(String type, Path p, DiskManagerParams dmp) {
        this.type = type; this.dmp = dmp;this.sessionPath = p;
        restoredCorrectly = isValid(p, type, dmp);
    }
    
    static boolean isValid(Path p, String type, DiskManagerParams dmp){
        if(!Files.exists(p.resolve(dmp.getNomenclature().sessionMetaDataFileName()))){
            return false;
        }
        try{
            String sessionFileName = p.getFileName().toString();
            String metaHash = dmp.getNomenclature().getSessionName(sessionFileName).getMetaHash();
            //boolean typeValid = checkType(p, type); if(!typeValid)return false;
            boolean hashValid = hashCheck(p, metaHash,dmp); 
            return hashValid;
        }catch(Exception a){ /*ignore*/ }
        return false;
    }

    public Path getSessionPath() {
        return sessionPath;
    }
    
    /*private static boolean checkType(Path p,String type){
        try{
            Path typeFile = p.resolve("type");
            if(Files.exists(typeFile)){
                List<String> lines = Files.readAllLines(p, StandardCharsets.US_ASCII);
                for (String line : lines) {
                    if(line.equalsIgnoreCase(type))return true;
                }
            }
        }catch(Exception a){}return false;
    }*/
    
    private static boolean hashCheck(Path p,String metaHash,DiskManagerParams dmp){
        try{
            Path metaDataFile = p.resolve(dmp.getNomenclature().sessionMetaDataFileName());
            if(Files.exists(metaDataFile)){
                try ( FileChannel fc = FileChannel.open(metaDataFile, StandardOpenOption.READ) ) {
                    ByteBuffer bb = JavaStringHashAlgo.makeByteBuffer(fc);
                    String hasCodeStr = JavaStringHashAlgo.makeHash(bb).getAsString();
                    if(metaHash.startsWith(hasCodeStr)){
                        return true;
                    }
                }catch(Exception a){
                    
                }
            }
        }catch(Exception a){
            
        }return false;
    }
     
    @Override public FileStorageManager makeNewFileStorageManager(FileStorageManagerParams fsmp) {
        final FileStorageManager fsm;
        try{
            fsm = new DefaultFileStorageManager(dmp,fsmp,sessionPath, 
                    new CloseCallback<DefaultFileStorageManager>() { 
                        @Override public void closed(DefaultFileStorageManager e) { fsms.remove(e); }}
            );
            fsms.add(fsm);
        }catch(IOException ioe){
            throw new RuntimeException(ioe);
        }
        return fsm;
    }

    @Override public String getType() {
        return type;
    }

    @Override
    public SeekableByteChannel openMetaData()throws IOException {
        /*if(!restoredCorrectly()){
            throw new IllegalStateException("Invalid session");
        }*/
        return resources.addAndGet(FileChannel.open(
                sessionPath.resolve(dmp.getNomenclature().sessionMetaDataFileName()), StandardOpenOption.READ));
    }

    @Override
    public boolean restoredCorrectly() {
        return restoredCorrectly;
    }

    @Override
    public SeekableByteChannel getOrCreateResource(String nm, OpenOption... openOptions) throws Exception{
        return resources.addAndGet(FileChannel.open(sessionPath.resolve(nm), openOptions));
    }

    @Override
    public Logger createLogger(String nm) {
        nm  = dmp.getNomenclature().getLogStorage().make(nm);
        return Utils.createLogger(nm, dmp, this, LoggerCreateSPI.Type.Session);
    }

    @Override
    public void delete() throws Exception {
        close();
        NioUtils.deleteDirectory(sessionPath);
    }
    
    @Override
    public void close()throws Exception{
        synchronized (resources){
            resources.close();
        }
        for (FileStorageManager fsm : fsms) {
            fsm.close();
        }
    }
    
}
