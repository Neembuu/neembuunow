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

package neembuu.release1.defaultImpl.linkhandler;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.file.OnlineFile;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.SeekabilityCheckingService;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.Message;
import neembuu.vfs.connection.checks.CanSeek;
import neembuu.vfs.connection.checks.SeekingAbility;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SeekabilityCheckingServiceImpl implements SeekabilityCheckingService{
    
    private static final Logger LOGGER = Logger.getLogger(SeekabilityCheckingServiceImpl.class.getName());
    
    private final MainComponent mc;

    public SeekabilityCheckingServiceImpl(MainComponent mc) {
        this.mc = mc;
    }
    
    

    @Override public void handle(final LinkHandler lh) {
        List<OnlineFile> files = lh.getFiles();
        if(files!=null){
            for (final OnlineFile file : files) {
                try{
                    file.getConnectionProvider().seekingAbility().addListener(new SeekingAbility.Listener() {
                        @Override public void stateChanged(CanSeek before, CanSeek now,long triggeringOffset) {
                            stateChangedImpl(lh, file, before, now, triggeringOffset);
                        }
                    });
                }catch(Exception e){
                    LOGGER.log(Level.SEVERE, "could not handle "+file, e);
                    //ignore
                }
            }
        }
    }
    
    private void stateChangedImpl(LinkHandler lh,OnlineFile of,CanSeek before, CanSeek now,long triggeringOffset){
        LOGGER.log(Level.INFO, "Seekability state changed \n"
                + "lh={0} of={1} before={2} now={3} offset={4}", new Object[]{lh,of,before,now,triggeringOffset});
        if(before==now)return;
        if(now==CanSeek.NO){
            mc.newMessage()
                .setTimeout(20000)
                .setTitle("Sorry for the trouble")
                .setEmotion(Message.Emotion.EMBARRASSED)
                .setMessage("Forwading will not work in the file\n"
                        + ""+of.getName()+"\n"
                        + "the source website has blocked forwarding facility.\n"
                        + "It seems you recently tried to forward the video to\n"+
                         Math.round((triggeringOffset*100d)/of.getFileSize())+"%"
                )
                .showNonBlocking();
        }
    }
    
}
