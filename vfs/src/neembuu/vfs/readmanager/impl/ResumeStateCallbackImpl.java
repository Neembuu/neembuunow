/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.vfs.readmanager.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import neembuu.config.GlobalTestSettings;
import neembuu.diskmanager.RegionStorageManager;
import neembuu.diskmanager.ResumeStateCallback;
import neembuu.rangearray.Range;
import neembuu.vfs.progresscontrol.ThrottleFactory;
import neembuu.vfs.readmanager.ReadQueueManager;

/**
 *
 * @author Shashank Tulsyan
 */
final class ResumeStateCallbackImpl implements ResumeStateCallback{
    
    private final ReadQueueManager readQueueManager;
    private final SeekableConnectionFileImpl seekableHttpFile;
    private final ThrottleFactory throttleFactory;
    
    private static final boolean DEBUG = false;

    ResumeStateCallbackImpl(ReadQueueManager readQueueManager, SeekableConnectionFileImpl seekableHttpFile, ThrottleFactory throttleFactory) {
        this.readQueueManager = readQueueManager;
        this.seekableHttpFile = seekableHttpFile;
        this.throttleFactory = throttleFactory;
    }
    
    
    @Override
    public boolean resumeState(
            List<RegionStorageManager> previouslyDownloadedData) {
        if(previouslyDownloadedData.size()>0){
            if(seekableHttpFile.getParams().getAskResume().resume()){
                resumeStateConfirmed(previouslyDownloadedData);
                return true;
            }
        }
        return false;
        
    }
    
    private void resumeStateConfirmed(
            List<RegionStorageManager> previouslyDownloadedData) {
       for(RegionStorageManager regionStorageManager : previouslyDownloadedData) {
           resumeStateForRegion(regionStorageManager);
       }
    }
    
    private void resumeStateForRegion(RegionStorageManager regionStorageManager){
       BasicRegionHandler channel = null;
        try{
            Range region = readQueueManager.getRegionHandlers().addElement(
                    regionStorageManager.starting(),
                    regionStorageManager.ending(), null);
            channel = new BasicRegionHandler(
                    seekableHttpFile,
                    region,
                    regionStorageManager,
                    throttleFactory.createNewThrottle(),
                    regionStorageManager.ending()
                );
            
            Range newRegion = readQueueManager.getRegionHandlers().setProperty(region, channel);
            if(newRegion!=region){
                throw new UnsupportedOperationException("This RangeArray "+
                        readQueueManager.getRegionHandlers()
                        + "doesn\'t edit the actual entry, abnormal condition");
            }
            if(DEBUG)Logger.getLogger(BasicRegionHandler.class.getName())
                    .log(Level.INFO, "added={0}", channel);
            /*channel.startConnection((int)
                    RangeUtils.getSize(region),false);*/
        }catch(Exception any){
            Logger.getLogger(BasicRegionHandler.class.getName())
                    .log(Level.INFO,"",any);
        }
    }
}
