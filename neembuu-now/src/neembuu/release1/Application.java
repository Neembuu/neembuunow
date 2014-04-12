/*
 *  Copyright (C) 2014 Shashank Tulsyan
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
package neembuu.release1;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpfm.SystemUtils;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.Message;
import neembuu.release1.ui.MainComponentImpl;

/**
 *
 * @author Shashank Tulsyan
 */
public class Application {
    
    public enum Resource {
        Home, Installation, Logs, ExternalPlugins, TempStorage, VirtualFolderMountLocation
    }
    
    public enum Runtime {
        Jar, Development
    }
    
    public static long releaseTime() {
        return 1397223376463L; 
        //returning as a System.currentTime() long value
    }

    private static MainComponent mainComponent;

    public static void setMainComponent(MainComponent mainComponent) {
        if(Application.mainComponent!=null ){
            throw new IllegalStateException("Already initialized");
        }Application.mainComponent = mainComponent;
    }
    
    public static Path getResource(final String... relative) {
        return getResource(Resource.Home, relative);
    }
    
    public static Path getResource(Resource s, final String... relative) {
        Path base;
        switch (s) {
            case TempStorage:                   base = getTempStorage(); break;
            case Home:                          base = getHome(); break;
            case VirtualFolderMountLocation:    base = getVF(); break;    
            case Installation:                  base = getInstallationRoot(); break;
            case ExternalPlugins:               base = getPath(getHome(),"external_plugins");break;
            case Logs:                          base = getPath(getHome(),"logs");break;
            default:                            throw new AssertionError();
        }
        return Paths.get(base.toAbsolutePath().toString(), relative).toAbsolutePath();
    }
    
    private static Path getVF(){
        Path vf = getHome().resolve("NeembuuVirtualFolder");
        if(Files.exists(vf))return vf;
        if (SystemUtils.IS_OS_WINDOWS){
            try{
                Files.createFile(vf);
            }catch(Exception a){
                throw new RuntimeException(a);
            }
        }else { 
            try{
                Files.createDirectory(vf);
            }catch(Exception a){
                throw new RuntimeException(a);
            }
        }return vf;
    }
    
    private static Path getTempStorage(){
        // folder is neembuu-now
        return getPath(Paths.get(System.getProperty("java.io.tmpdir")),"neembuu-now","release1");
    }
    
    private static Path getHome(){
        // folder is (dot)neembuu-now
        return getPath(Paths.get(System.getProperty("user.home")), ".neembuu-now","release1");
    }
    
    private static Path getPath(Path base, String... elements) {
        Message m = mainComponent.newMessage().error().setTitle("Application directory issue");
        
        Path base_path = base; 
        for (String path : elements) {
            base_path = i(base_path, path, m);
        }
        
        return base_path;
    }
    
    private static Path i(Path base, String sub, Message m){
        Path p = base.resolve(sub);
        ensureExists(p, m);
        return p;
    }
    
    private static void ensureExists(Path dir, Message m){
        if(Files.exists(dir)){
            if(!Files.isDirectory(dir)){
                m.setMessage("Application directory folder has been replaced by a file\n"
                        + "Neembuu cannot start without fixing this.\n"
                        + "Can Neembuu try to fix it?");
                boolean ok = m.ask();
                if(ok){
                    try{
                        Files.delete(dir);
                        Files.createDirectory(dir);
                    }catch(IOException aaa){
                        aaa.printStackTrace();
                        m.setMessage("Could not delete the file, please manually delete the file\n"
                                + "and restart NeembuuNow. File path : \n"
                                 + dir.toAbsolutePath().toString());
                        m.show();
                        System.exit(0);
                    }
                }else  {
                    System.exit(0);
                }
            }
        }else {
            try{
                Files.createDirectory(dir);
            }catch(IOException ioe){
                ioe.printStackTrace();
                m.setMessage("Could not create directory\n"
                        + dir.toAbsolutePath().toString()+"\n"
                        + "Reason:\n"
                        + ioe.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        setMainComponent(new MainComponentImpl(new javax.swing.JFrame()));
        System.out.println(getResource());
    }

    public static Runtime getRuntime() {
        if(runtime==null)getInstallationRoot();
        return runtime;
    }
    
    private static Path installationRoot = null;
    private static Runtime runtime = null;
    
    private static Path getInstallationRoot() {
        if(installationRoot!=null)return installationRoot;
        try {
            // todo :
            InputStream prof = null;
            CodeSource src = Main.class.getProtectionDomain().getCodeSource();
            //boolean assume = false;
            if (src == null) {
                System.err.println("Asumming because code source is null");
                throw new NullPointerException();
                //assume = true;
            } else {
                Main.class.getProtectionDomain().getCodeSource();
                if (src.getLocation().toString().endsWith("classes/")) {
                    String urlpth = src.getLocation().toString();
                    urlpth = urlpth.substring(0, urlpth.lastIndexOf('/'));
                    urlpth = urlpth.substring(0, urlpth.lastIndexOf('/'));
                    urlpth = urlpth.substring(0, urlpth.lastIndexOf('/'));
                    urlpth = urlpth.substring(0, urlpth.lastIndexOf('/'));
                    urlpth = urlpth.substring(0, urlpth.lastIndexOf('/') + 1);
                    urlpth = urlpth + "release1_development_environment/";
                    Logger.getGlobal().log(Level.INFO, "Running in development mode, using properties = " + urlpth);
                    runtime = Runtime.Development;
                    return installationRoot=Paths.get(new URL(urlpth).toURI());
                } else if (src.getLocation().toString().endsWith(".jar")) {
                    String urlpth = src.getLocation().toString();
                    urlpth = urlpth.substring(0, urlpth.lastIndexOf('/') + 1);
                    //urlpth = urlpth + "neembuu.properties";
                    Logger.getGlobal().log(Level.INFO, "Running from jar, using properties = " + urlpth);
                    runtime = Runtime.Jar;
                    return installationRoot=Paths.get(new URL(urlpth).toURI());
                } else {
                    Logger.getGlobal().log(Level.INFO, "Asumming because code source=" + src.getLocation().toString());
                    throw new NullPointerException();
                    //assume = true;
                }
            }

        } catch (Exception a) {
            Logger.getGlobal().log(Level.SEVERE, "Error initializing environment", a);
        }

        Logger.getGlobal().log(Level.INFO, "Could not initialize environment");
        throw new NullPointerException();
    }
}
