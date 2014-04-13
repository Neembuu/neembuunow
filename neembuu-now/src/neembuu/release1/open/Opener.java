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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingUtilities;
import jpfm.SystemUtils;
import neembuu.release1.Application;
import neembuu.release1.api.open.Open;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.pismo.PismoInstaller;
import neembuu.release1.pismo.lin;

/**
 *
 * @author Shashank Tulsyan
 */
public final class Opener {
    
    private final Map<String,Open> openHandles = new HashMap<>();
    
    private MainComponent mainComponent;

    public void initMainComponent(MainComponent mainComponent) {
        if(this.mainComponent!=null){throw new IllegalStateException("Already initialized"); }
        this.mainComponent = mainComponent;
    }
    
    public static final Opener I = new Opener();
    
    public void closeAll(){
        for(Open o : openHandles.values()){
            o.close();
        }
    }
    
    public Open open(File f){
        String fileToOpen = f.getAbsolutePath();
        Open x = openHandles.get(fileToOpen);
        if(x!=null){ x.close(); }
        
        if(SystemUtils.IS_OS_WINDOWS){
            return openWin(f);
        }else if(SystemUtils.IS_OS_MAC){
            return fallBackOpen(f);
        }else {
            return openLin(f);
        }
    }
    
    private Open openWin(File f) {
        Path vlc = Application.getResource(Application.Resource.Installation,"vlc");
        vlc = vlc.resolve("vlc-1.1.11-win32").resolve("vlc.exe");
        if(Files.exists(vlc)){ return make(vlc, f);}
        else { 
            vlc = Paths.get(System.getenv("ProgramFiles"),"VideoLAN","VLC","vlc.exe");
            if(Files.exists(vlc)){ return make(vlc, f);}
            else {
                vlc = Paths.get("C:","Program Files (x86)","VideoLAN","VLC","vlc.exe");
                if(Files.exists(vlc)){ return make(vlc, f);}
                else { 
                    vlc = Paths.get("C:","Program Files","VideoLAN","VLC","vlc.exe");
                    if(Files.exists(vlc)){ return make(vlc, f);}
                    else { 
                        return fallBackOpen(f);
                    }
                }
            }
        }
    }
    
    private Open openLin(File f) {
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
    
    private Open fallBackOpen(File f){
        try{
            java.awt.Desktop.getDesktop().open(f);
            return DummyOpen.I();
        }catch(Exception ioe){
            try{
                java.awt.Desktop.getDesktop().open(f.getParentFile());
            }catch(Exception ioe1){
                // could not open file :(
                String fn = "";
                try{ fn=f.getAbsolutePath();}catch(Exception a){/*null pointer*/}
                mainComponent.newMessage().error().setTitle("Could not open file")
                        .setMessage(fn).setTimeout(10000).showNonBlocking();
                ioe.printStackTrace();
                ioe1.printStackTrace();
            }
        }return DummyOpen.I();
    }
    
    private static final class DummyOpen implements Open { 
        public static DummyOpen I(){return new DummyOpen();}
        @Override public boolean isOpen() { return false;/*throw new UnsupportedOperationException("Not supported yet.");*/}
        @Override public void close() { }
    }
    
    private Open make(Path vlc, File toOpen){
        String fileToOpen = toOpen.getAbsolutePath();
        ProcessBuilder pb = new ProcessBuilder(vlc.toAbsolutePath().toString(),fileToOpen);
        Process p = null;
        try{
            p = pb.start();
        }catch(Exception a){
            return fallBackOpen(toOpen);
        }
        OpenImpl oi = new OpenImpl(p, fileToOpen);
        openHandles.put(fileToOpen, oi);
        return oi;
    }
    
    private final class OpenImpl implements Open { 
        private final Process p;
        private final String path;

        public OpenImpl(Process p, String path) { this.p = p; this.path = path; }
        
        @Override public boolean isOpen() { 
            try { p.exitValue(); return false; } 
            catch (Exception e) { return true; } 
        }
        
        @Override public void close() { 
            try{ p.destroy(); }catch(Exception a){} 
            Open o = openHandles.remove(path);
            if(o!=this && o!=null){
                new Exception("closed file but removed wrong handle, therefore killing it").printStackTrace();
                o.close();
            }
        }
    }

}
