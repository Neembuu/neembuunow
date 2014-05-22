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
import java.util.HashMap;
import java.util.logging.Logger;
import neembuu.release1.api.log.LoggerUtil;

/**
 *
 * @author Shashank Tulsyan
 */
public class MainCommandsListener {
    private static final Logger logger = LoggerUtil.getLogger(MainCommandsListener.class.getName());
    
    private final HashMap<String,FileCommands> commandsListeners 
            = new HashMap<String, FileCommands>();
    
    public void register(String extension, FileCommands commandsListener){
        Object r = commandsListeners.get(extension);
        if(r!=null){
            throw new IllegalStateException("A listener "+r
                    + " is already associated with this extension "+extension);
        }
        commandsListeners.put(extension, commandsListener);
    }
    
    public boolean handleFile(Path file,String extension) {
        if(extension != null && extension.length()!=0){
            FileCommands cl = commandsListeners.get(extension);
            if(cl!=null){
                boolean res = cl.handleFile(file,extension);
                if(res)return true;
            }
        }
        
        //return tryAll(file, extension);
        
        return false;
    }
    
    private boolean tryAll(Path file,String extension){
        for(FileCommands cls : commandsListeners.values()){
            boolean res = cls.handleFile(file,extension);
            if(res)return true;
        }
        return false;
    }
}
