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

package neembuu.release1.open;

import neembuu.release1.api.open.Opener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import jpfm.SystemUtils;
import neembuu.release1.app.Application;
import neembuu.release1.api.open.Open;
import neembuu.release1.api.open.OpenerAccess;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.pismo.lin;

/**
 *
 * @author Shashank Tulsyan
 */
public final class OpenerImpl implements Opener {
    
    private final Map<String,Open> openHandles = new HashMap<>();
    
    private final MainComponent mainComponent;

    public OpenerImpl(MainComponent mainComponent) {
        this.mainComponent = mainComponent;
    }

    private final OpenerAccess openerA = new OpenerAccess() {

        @Override public void closeAll() {
            OpenerImpl.this.closeAllImpl();
        }

        @Override public Open openHandles_remove(String filePath) {
            return OpenerImpl.this.openHandles.remove(filePath);
        }
    };

    private void closeAllImpl(){
        // this complexity is to avoid concurrent modification exception
        // closing all instances, includes ones which on close
        // remove themselves from the list of open handles
        Open[]openHandlerArr = openHandles.values().toArray(new Open[0]);
        for(Open o : openHandlerArr){
            o.closeAll();
        }
        
        // closing zombie open handles which for some odd reason didn't remove
        // their copies
        for(Open o : openHandles.values()){
            System.out.println("Closing zombie open handle -> "+o);
            try{
                o.closeAll();
            }catch(Exception a){
                a.printStackTrace();
            }
        }
        
        openHandles.clear();
        
    }

    public final OpenerAccess getOpenerAccess() {
        return openerA;
    }
    
    @Override
    public Open open(String fileToOpen){
        Open x = openHandles.get(fileToOpen);
        if(x!=null){ x.close(); }
        
        if(SystemUtils.IS_OS_WINDOWS){
            return openWin(fileToOpen);
        }else if(SystemUtils.IS_OS_MAC){
            return fallBackOpen(fileToOpen);
        }else {
            return openLin(fileToOpen);
        }
    }
    
    @Override
    public Open openFolder(String f)throws Exception{
        if(!new File(f).isDirectory())throw new IllegalArgumentException(f+" is not a directory");
        
        if(SystemUtils.IS_OS_WINDOWS){
            return openFolderWin(f);
        }else if(SystemUtils.IS_OS_MAC){
            return fallbackOpenFolder(f);
        }else {
            return fallbackOpenFolder(f);
        }
    }
    
    private Open openFolderWin(String fileToOpen)throws Exception{        
        ProcessBuilder pb = new ProcessBuilder("explorer.exe",fileToOpen);
        Process p = null;
        try{
            p = pb.start();
        }catch(Exception a){
            throw a;
        }
        OpenImplWindowFolder oi = new OpenImplWindowFolder(p, fileToOpen, openerA);
        openHandles.put(fileToOpen, oi);
        return oi;
    }
    
    private Open fallbackOpenFolder(String f)throws Exception{
        java.awt.Desktop.getDesktop().open(new File(f));
        return DummyOpen.I();
    }
    
    private Open openWin(String absoluteFilePath) {
        Path vlc = Application.getResource(Application.Resource.Installation,"vlc");
        vlc = vlc.resolve("vlc-1.1.11-win32").resolve("vlc.exe");
        if(Files.exists(vlc)){ return make(vlc, absoluteFilePath);}
        else { 
            vlc = Paths.get(System.getenv("ProgramFiles"),"VideoLAN","VLC","vlc.exe");
            if(Files.exists(vlc)){ return make(vlc, absoluteFilePath);}
            else {
                vlc = Paths.get("C:","Program Files (x86)","VideoLAN","VLC","vlc.exe");
                if(Files.exists(vlc)){ return make(vlc, absoluteFilePath);}
                else { 
                    vlc = Paths.get("C:","Program Files","VideoLAN","VLC","vlc.exe");
                    if(Files.exists(vlc)){ return make(vlc, absoluteFilePath);}
                    else { 
                        return fallBackOpen(absoluteFilePath);
                    }
                }
            }
        }
    }
    
    private Open openLin(String f) {
        Path vlc = Paths.get("/usr/bin/vlc");
        if(Files.exists(vlc)){
            return make(vlc, f);
        }else {
            new Thread("Install VLC thread") {
                @Override public void run() {
                    askLinUserToInstallVLC();
                }
            }.start();
        }return fallBackOpen(f);
    }
    
    private void askLinUserToInstallVLC(){
        boolean u = mainComponent.newMessage().setTitle("VLC not found")
            .setMessage("VLC is one of the most suited video players\n"
                    + "for use with Neembuu. Other players might also work"
                    + "very well.\n"
                    + "Should Neembuu attempt to install vlc?")
            .ask();
        if(!u)return;
        
        String cmd = "sudo apt-get -y install vlc";
        
        String password = mainComponent.newMessage().setTitle("Please enter super user root password")
            .setMessage("To install VLC, the following command will be executed\n"
                    + cmd)
            .askPassword();
        
        if(password==null)return;
        
        String out = lin.executeAsRoot(password, cmd, mainComponent);
    }
    
    private Open fallBackOpen(String absoulteFilePath){
        try{
            java.awt.Desktop.getDesktop().open(new File(absoulteFilePath));
            return DummyOpen.I();
        }catch(Exception ioe){
            try{
                java.awt.Desktop.getDesktop().open(new File(absoulteFilePath).getParentFile());
            }catch(Exception ioe1){
                mainComponent.newMessage().error().setTitle("Could not open file")
                        .setMessage(absoulteFilePath).setTimeout(10000).showNonBlocking();
                ioe.printStackTrace();
                ioe1.printStackTrace();
            }
        }return DummyOpen.I();
    }
    
    private Open make(Path vlc, String fileToOpen){
        vlc = vlc.toAbsolutePath();
        ProcessBuilder pb = new ProcessBuilder(vlc.toString(),fileToOpen);
        Process p = null;
        try{
            p = pb.start();
        }catch(Exception a){
            return fallBackOpen(fileToOpen);
        }
        OpenImpl oi = new OpenImpl(p, fileToOpen, openerA);
        openHandles.put(fileToOpen, oi);
        return oi;
    }
   
}
