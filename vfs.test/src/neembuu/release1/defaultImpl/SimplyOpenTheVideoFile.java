/*
 * Copyright (C) 2012 Shashank Tulsyan
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
package neembuu.release1.defaultImpl;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

import jpfm.DirectoryStream;
import jpfm.FileAttributesProvider;
import jpfm.FileType;
import neembuu.release1.Main;
import neembuu.release1.MountManager;
import neembuu.release1.api.LinkGroupProcessor;
import neembuu.release1.api.VirtualFile;
/**
 * 
 * @author Shashank Tulsyan
 */
public class SimplyOpenTheVideoFile implements LinkGroupProcessor {

    private static String[] knownExtension = { "avi", "mpg", "mpeg", "webm", "mp4", "mp3", "rmvb", "mkv", "flv", "wma", "wmv", "ogg", "ogm", "flac" };

    @Override
    public List<VirtualFile> canHandle(List<VirtualFile> files) {
        return files;
    }

    @Override
    public void handle(List<VirtualFile> files, MountManager mountManager) {
        DirectoryStream root = (DirectoryStream) mountManager.getFileSystem().getRootAttributes();

        boolean foundSomething = findAndOpenVideo(root, mountManager);
    }

    public static boolean findAndOpenVideo(DirectoryStream ds, MountManager mountManager) {
        String pathBase = getMountLocation(mountManager);
        pathBase = path(ds,(DirectoryStream)mountManager.getFileSystem().getRootAttributes(),pathBase);
        if(pathBase==null){
            Main.getLOGGER().info("Could not find ds="+ds);
            return false;
        }
        return findAndOpenVideo(ds, pathBase , mountManager);
    }
    
    private static String path(FileAttributesProvider toFindPathOf, DirectoryStream src, String base){
        if(toFindPathOf == src){
            return base;
        }
        for (FileAttributesProvider fap : src) {
            if (fap == toFindPathOf) {
                return base + File.separator + fap.getName();
            } else if (fap instanceof DirectoryStream) {
                return path(toFindPathOf, (DirectoryStream)fap, base);
            }
        }
        return null;
    }
    
    private static boolean findAndOpenVideo(DirectoryStream ds, String path, MountManager mountManager) {
        Main.getLOGGER().info(" inside " + ds);
        for (FileAttributesProvider fap : ds) {
            if (fap.getFileType() == FileType.FOLDER) {
                return findAndOpenVideo((DirectoryStream) fap, path + File.separatorChar + fap.getName(), mountManager);
            } else if(openFile(fap, path)){
                return true;
            }
        }
        try{
            Main.getLOGGER().log(Level.SEVERE,"Could not open any file, trying to open folder instead");
            File f = new File(path, ds.getName());
            java.awt.Desktop.getDesktop().open(f);
            return true;
        }catch(Exception a){
            Main.getLOGGER().log(Level.SEVERE,"Could not even open the folder",a);
        }
            
        return false;
    }

    private static boolean openFile(FileAttributesProvider fap,String path){
        if (canBeOpenedInMediaPlayer(fap.getName())) {
            try {
                File f = new File(path, fap.getName());
                if(!tryOpeningUsingVLC(f)){
                    java.awt.Desktop.getDesktop().open(f);
                }
                return true;
            } catch (Exception a) {
                Main.getLOGGER().log(Level.SEVERE,"Could not open the file",a);
            }
        } else {
            Main.getLOGGER().info("cannot open " + fap.getName());
        }
        return false;
    }
    
    public static boolean tryOpeningUsingVLC(File f){
        String vlcLoc = " ";//NeembuuExtension.getInstance().getVlcLocation();
        if(vlcLoc!=null){
            try{
                ProcessBuilder pb = new ProcessBuilder(vlcLoc,f.getAbsolutePath());
                Process p = pb.start();
                return true;
            }catch(Exception any){
                return false;
            }
        }
        return false;
    }
    
    private static boolean canBeOpenedInMediaPlayer(String name) {
        name = name.toLowerCase();
        for (int i = 0; i < knownExtension.length; i++) {
            if (name.endsWith(knownExtension[i])) { return true; }
        }
        return false;
    }

    @Override
    public void openSuitableFile(List<VirtualFile> virtualFiles, MountManager mountManager) {
        DirectoryStream root = (DirectoryStream)mountManager.getFileSystem().getRootAttributes();
        String basePath = getMountLocation(mountManager);
        for (VirtualFile virtualFile : virtualFiles) {
            String path = path(virtualFile.getConnectionFile().getParent(), root, basePath);
            openFile(virtualFile.getConnectionFile(), path);
        }
    }
    
    private static String getMountLocation(MountManager mountManager){
        return mountManager.getMount().getMountLocation().toString();
    }
    
}
