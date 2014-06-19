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
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributeView;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.util.Throwables;

/**
 *
 * @author Shashank Tulsyan
 */
public class DirectoryWatcherServiceImpl implements DirectoryWatcherService {

    private static final Logger logger = LoggerUtil.getLogger(DirectoryWatcherServiceImpl.class.getName());

    private final MainCommandsListener mcl;

    private final Path commandsDir;
    
    private volatile Thread thread = null;
    
    public DirectoryWatcherServiceImpl(MainCommandsListener mcl) {
        this.mcl = mcl;
        commandsDir = Application.getResource("commands");
    }

    @Override public void startService() {
        Throwables.start(new Runnable(){
            @Override
            public void run() {
                startServiceImpl();
            }
        },DirectoryWatcherServiceImpl.class.toString());
    }
    
    @Override public void stopService(){
        Thread localCopy = thread;
        thread = null;
        if(localCopy!=null){
            try{
                localCopy.interrupt();
            }catch(Exception a){
                //ignore
            }
        }
        
    }

    private void startServiceImpl() {
        if (!ensureDirectoryExists(commandsDir)) {
            return;
        }
        try (WatchService watcher = commandsDir.getFileSystem().newWatchService()) {
            commandsDir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

            while(thread == Thread.currentThread()) {
                try{
                    WatchKey watckKey = null;
                    try{
                        watckKey = watcher.take();
                    }catch(InterruptedException ie){
                        continue;
                    }

                    List<WatchEvent<?>> events = watckKey.pollEvents();
                    for (WatchEvent event : events) {
                        logger.log(Level.INFO, "{0}: {1}", new Object[]{event.kind(), event.context().toString()});
                        if(event.kind()==ENTRY_MODIFY){
                            handle(watcher, true);
                        }
                    }
                }catch(Exception a){
                    logger.log(Level.INFO, "WatchService loop error", a);
                }
            }
        } catch (IOException ioe) {
            logger.log(Level.INFO, "WatchService died", ioe);
        } 
    }

    private boolean ensureDirectoryExists(Path pth) {
        if (Files.isDirectory(pth)) {
            return true;
        }
        try {
            Files.createDirectories(pth);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    @Override public void forceRescan(long time) {
        logger.info("An instance of neembuu was started, something must have happened.\n"
                + "Checking the commands directory.");
        Throwables.start(new Runnable(){
            @Override public void run() {
                rescanDirectory();
            }
        },null,true);
    }
    
    private void handle(Object pathObj,boolean isRelative){
        logger.log(Level.FINE, "handling,{0}", pathObj);
        if(!(pathObj instanceof Path)){return;}
        
        Path path = (Path)pathObj;
        if(isRelative){
            path = commandsDir.resolve(path);
        }else {
            path = path.toAbsolutePath();
        }
        
        if(!Files.exists(path))return;
        
        if(!Files.isRegularFile(path))return;
        
        if(!Files.isReadable(path))return;
        
        long creationTime = getCreationTime(path);
        
        if(isTooOld(creationTime)){
            try{Files.delete(path);}catch(IOException ioe){ioe.printStackTrace();}
            return;
        }
        
        String extesion = path.getFileName().toString();
        try{
            extesion = extesion.substring(extesion.lastIndexOf('.')+1);
        }catch(Exception a){
            extesion = "";
        }
        
        boolean res = mcl.handleFile(path,extesion,creationTime);
        if(!res){
            logger.log(Level.FINE, "Could not handle file = {0}", path);
        }
    }
    
    private void rescanDirectory() {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(commandsDir)){
            for(Path p : ds){
                handle(p, false);    
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.log(Level.SEVERE,"could not scan directory",ex);
        }

    }
    
    private long getCreationTime(Path f){
        String nm = f.getFileName().toString();
        try{
            long t = Long.parseLong(nm);
            return t;
        }catch(Exception a){
            
        }
        
        try{
            BasicFileAttributeView view = Files.getFileAttributeView(f, BasicFileAttributeView.class);
            return view.readAttributes().creationTime().toMillis();
        }catch(IOException ioe){
            
        }
        
        return System.currentTimeMillis();
    }
    
    private boolean isTooOld(long t){
        long maxGapAllowed = 15*60*1000; // 15mins
        if(System.currentTimeMillis() - t > maxGapAllowed){
            return true;
        }return false;
    }

}
