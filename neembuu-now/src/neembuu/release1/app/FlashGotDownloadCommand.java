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

package neembuu.release1.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.linkgroup.LinkGroupMakers;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkhandler.LinkHandlerProviders;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.api.ui.LinkGroupUICreator;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.defaultImpl.linkgroup.Utils;
import neembuu.util.Throwables;

/**
 *
 * @author Shashank Tulsyan
 */
public class FlashGotDownloadCommand implements FileCommands{

    private static final Logger logger = LoggerUtil.getLogger(FlashGotDownloadCommand.class.getName());
    
    private static final String ValidExtension = "flashgotjson";
    
    private final LinkGroupUICreator lguic;
    private final MainComponent mc;

    public FlashGotDownloadCommand(LinkGroupUICreator lguic, MainComponent mc) {
        this.lguic = lguic;
        this.mc = mc;
    }
    
    @Override public boolean handleFile(final Path file,String extension,final long creationTime) {
        if(! ValidExtension.equalsIgnoreCase(extension))return false;
        
        try{
            final FlashGotTemplate fgt = new FlashGotTemplate(file);
            try{Files.delete(file);}catch(IOException a){/**/} 
            Throwables.start(new Runnable() {
                @Override public void run() {
                    tryHandle(fgt,file,creationTime);
                }},"handleFile");
            return true;
        }catch(Exception jsone){
            reportFailure(jsone, creationTime);
            return false;
        }
    }
    
    private void tryHandle(FlashGotTemplate fgt,final Path file,long creationTime){
        boolean success = false;
        try{
            try{
                handle(fgt);
                success = true;
            }catch(Exception a){
                reportFailure(a, creationTime);
            }
            if(!success){
                // :P file has already been deleted
            }
        }catch(Exception a){a.printStackTrace(System.err);}
    }
    
    private void reportFailure(Exception reason,long creationTime){
        long delta = System.currentTimeMillis() - creationTime;
        delta = Math.abs(delta);
        if(delta < 5*60*1000){ // 5mins
            mc.newMessage().setTitle("Could not add link send from flashgot")
                    .setMessage("Reason : "+reason.getMessage())
                    .setTimeout(5000)
                    .showNonBlocking();
        }
        logger.log(Level.SEVERE,"Could not handle command",reason);
    }
    
    private void handle(FlashGotTemplate fgt)throws Exception{
        TrialLinkHandler trialLinkHandler = LinkHandlerProviders.getWhichCanHandleOrDefault(fgt.getURL());
        
        TrialLinkGroup res = LinkGroupMakers.tryMaking(trialLinkHandler);
        LinkGroup lg = LinkGroupMakers.make(res);
        // Utils.saveDisplayName(lg.getSession(), fgt.getFNAME());
        /**
         * TODO : Write other session specific data like cookies and stuff, 
         * and also use it when running.
         * Send name parameter somehow
         */
        lguic.createUIFor(Collections.singletonList(lg),true);
    }

    
    private String checkVersion(Path file){
        String version = file.getFileName().toString();
        try{
            version = version.substring(0,version.lastIndexOf('.'));
            version = version.substring(0,version.lastIndexOf('.'));
        }catch(Exception a){ version = ""; }
        return version;
    }

    @Override
    public String defaultExtension() {
        return ValidExtension;
    }
        
}
