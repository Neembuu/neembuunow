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

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.api.ui.AddLinkUI;
import neembuu.util.Throwables;

/**
 *
 * @author Shashank Tulsyan
 */
public class FlashGotDownloadCommand implements FileCommands{

    private static final Logger logger = LoggerUtil.getLogger(FlashGotDownloadCommand.class.getName());
    
    private static final String ValidExtension = "flashgotjson";
    private final AddLinkUI addLinkUI;

    public FlashGotDownloadCommand(AddLinkUI addLinkUI) {
        this.addLinkUI = addLinkUI;
    }
    
    @Override public boolean handleFile(Path file,String extension) {
        if(! ValidExtension.equalsIgnoreCase(extension))return false;
        
        try{
            final FlashGotTemplate fgt = new FlashGotTemplate(file);
            Throwables.start(new Runnable() {
                @Override public void run() {
                    handle(fgt);
                }},"handleFile");
            return true;
        }catch(Exception jsone){
            jsone.printStackTrace();
            logger.log(Level.SEVERE,"Could not read",jsone);
            return false;
        }
    }
    
    private void handle(FlashGotTemplate fgt){
        
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
