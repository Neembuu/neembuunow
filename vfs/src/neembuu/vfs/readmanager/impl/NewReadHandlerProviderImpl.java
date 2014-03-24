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

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import neembuu.config.GlobalTestSettings;
import neembuu.diskmanager.*;
import neembuu.rangearray.Range;
import neembuu.rangearray.RangeUtils;
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
            SeekableConnectionFileImpl seekableHttpFile,
            DiskManager diskManager,ThrottleFactory throttleFactory) {
        this.seekableHttpFile = seekableHttpFile;
        this.throttleFactory = throttleFactory;
        
        readQueueManager = new ReadQueueManager(this);
        
        rsc = new ResumeStateCallbackImpl(readQueueManager, seekableHttpFile, throttleFactory);
        
        
        FileStorageManagerParams fsmp = new FileStorageManagerParams.Builder()
                .setFileName(seekableHttpFile.getName())
                .setResumeStateCallback(rsc)
                .build();
        fsm = diskManager.makeNewFileStorageManager(fsmp);
        readQueueManager.initLogger();
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

    @Override
    public final Logger getReadQueueManagerThreadLogger(){
        return fsm.getReadQueueManagerThreadLogger();
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
