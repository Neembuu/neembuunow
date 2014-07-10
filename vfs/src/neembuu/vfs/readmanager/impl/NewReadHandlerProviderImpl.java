/*
 *  Copyright (C) 2010 Shashank Tulsyan
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

package neembuu.vfs.readmanager.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import static java.nio.file.StandardOpenOption.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.diskmanager.*;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeUtils;
import neembuu.vfs.connection.checks.SeekingAbility;
import neembuu.vfs.file.TroubleHandler;
import neembuu.vfs.progresscontrol.ThrottleFactory;
import neembuu.vfs.readmanager.NewReadHandlerProvider;
import neembuu.vfs.readmanager.rqm.ReadQueueManager;
import neembuu.vfs.readmanager.RegionHandler;

/**
 * Provide alternate implementation
 * @author Shashank Tulsyan
 */
final class NewReadHandlerProviderImpl 
        implements NewReadHandlerProvider/*, ResumeStateCallback*/ {
    
    private final SeekableConnectionFileImpl seekableHttpFile;
    private final ThrottleFactory throttleFactory; 
    private final ReadQueueManager readQueueManager;
    private final FileStorageManager fsm ;
    private static final boolean DEBUG = false;
    private final ResumeStateCallbackImpl rsc;
    
    public NewReadHandlerProviderImpl(
            SeekableConnectionFileImpl seekableHttpFile,Session s
            /*DiskManager diskManager*/,ThrottleFactory throttleFactory) {
        this.seekableHttpFile = seekableHttpFile;
        this.throttleFactory = throttleFactory;
        
        readQueueManager = new ReadQueueManager(this);
        
        rsc = new ResumeStateCallbackImpl(readQueueManager, seekableHttpFile, throttleFactory);
        
        
        FileStorageManagerParams fsmp = new FileStorageManagerParams.Builder()
                .setFileName(seekableHttpFile.getName())
                .setResumeStateCallback(rsc)
                .setEstimatedFileSize(seekableHttpFile.getFileSize())
                .build();
        fsm = /*diskManager*/s.makeNewFileStorageManager(fsmp);
        readQueueManager.initLogger();
        
        String failureReason = checkOrUpdateFileSize();
        if(failureReason!=null){throw new IllegalStateException(failureReason); }
        
        // file name cannot be checked, because different file name
        // results in creation of a different FileStorageManager.
        // If filename is different it is assumed to be a different file.
    }
    
    private String checkOrUpdateFileSize(){
        try{
            SeekableByteChannel sbc = fsm.getOrCreateResource("filesize",WRITE,READ,CREATE);
            if(sbc.size()==0 || sbc.size() > 1024 ){//empty file or file nonsense data
                fixFileSizeMetaData(sbc); return null;
            }else { 
                ByteBuffer bb = ByteBuffer.allocate((int)(sbc.size()));
                int r = sbc.read(bb);
                if(r<sbc.size()){return "Could not read filesize meta data";}
                try{
                    long sz = Long.parseLong(new String(bb.array()));
                    long old = seekableHttpFile.getFileSize();
                    if(sz!=old){
                        return "Filesize changed. Earlier when you played this file\n"
                                + "filesize was = "+old+" this time it is ="+sz+"\n"
                                + "The link might have expired.\n"
                                + "You can try updating the link using the \"Edit links\" option.";
                    }
                }catch(Exception a){
                    a.printStackTrace();
                    fixFileSizeMetaData(sbc); return null;
                }
            }
        }catch(Exception a){
            a.printStackTrace();
        }return null;
    }
    
    private void fixFileSizeMetaData(SeekableByteChannel sbc)throws IOException{
        sbc.position(0);
        sbc.write(ByteBuffer.wrap(Long.toString(
                seekableHttpFile.getFileSize()).getBytes()));
        sbc.close(); 
    }
    
    @Override
    public final FileStorageManager getFileStorageManager() {
        return fsm;
    }
    
    @Override
    public String getName(){
        return seekableHttpFile.getName();
    }

    

    @Override
    public TroubleHandler getTroubleHandler() {
        return seekableHttpFile.getTroubleHandler();
    }

    private Logger rQMLogger = null;
    
    @Override
    public final Logger getReadQueueManagerThreadLogger(){
        if(rQMLogger==null){
            rQMLogger = fsm.createLogger("ReadQueueManagerThread");
        }
        return rQMLogger;
    }    

    @Override
    public SeekingAbility seekingAbility() {
        return seekableHttpFile.getNewConnectionProvider().seekingAbility();
    }
    
    @Override
    public final long getNewHandlerCreationTime(long offset) {
        return seekableHttpFile.getNewConnectionProvider().estimateCreationTime(offset);
    }

    @Override
    public final ReadQueueManager getReadQueueManager() {
        return readQueueManager;
    }
    
    @Override
    public RegionHandler provideHandlerFor(
            long start,
            long end) {
        BasicRegionHandler channel = null;
        try{
            Range region = readQueueManager.getRegionHandlers().addElement(start, start/*end*/, null);
            
            channel = new BasicRegionHandler(
                    seekableHttpFile,
                    region,
                    fsm.getRegionStorageManagerFor(start),
                    throttleFactory.createNewThrottle(),
                    end
                    );
            
            Range newRegion = readQueueManager.getRegionHandlers().setProperty(region, channel);
            //LOGGER.info("setProperty=>"+readQueueManager.getRegionHandlers().get(0).getProperty().toString());
            if(newRegion!=region){
                throw new UnsupportedOperationException("This RangeArray "+
                        readQueueManager.getRegionHandlers()
                        + "doesn\'t edit the actual entry, abnormal condition");
            }
            
            if(DEBUG)Logger.getLogger(BasicRegionHandler.class.getName())
                    .log(Level.INFO, "added={0}", channel);
            channel.startConnection((int)
                    RangeUtils.getSize(region),false);
        }catch(Exception any){
            Logger.getLogger(BasicRegionHandler.class.getName())
                    .log(Level.INFO,"",any);
            return null;
        }
        return channel;//
    }

    @Override
    public final boolean isOpen() {
        return seekableHttpFile.isOpen();
    }

   
    
    
    
}
