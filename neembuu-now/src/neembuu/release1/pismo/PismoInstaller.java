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

package neembuu.release1.pismo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import jpfm.MachineUtils;
import jpfm.SystemUtils;
import neembuu.release1.Application;
import neembuu.release1.Main;


/**
 *
 * @author Shashank Tulsyan
 */
public final class PismoInstaller {
    
    public static void tryInstallingPismoFileMount(boolean uninstall, InstallerCallbackListener listener) throws Exception {
        char s = File.separatorChar;
        
        String pismoInstallerDirectory = Application.getHome() + s;
        if (SystemUtils.IS_OS_WINDOWS){
            
        }
        if (SystemUtils.IS_OS_MAC) {
            throw new IllegalStateException("Macintosh support in not included with this package");
        }
        if (SystemUtils.IS_OS_LINUX){
            throw new IllegalStateException("Linux support in not included with this package");
        }

        listener.informUserAboutInstallation();
        
        String path = 
                MachineUtils.getRuntimeSystemArchitecture() == MachineUtils.MachineType.AMD64 ?
                    Application.getResource("tools\\Windows\\elevate\\Elevate64.exe").getAbsolutePath() : Application.getResource("tools\\Windows\\elevate\\Elevate32.exe").getAbsolutePath();

        File f = Application.getResource(uninstall?"uninstall_logs.txt":"install_logs.txt");
        if(f.exists()){
            f.delete();
        }
        f = Application.getResource("done.txt");
        if(f.exists()){
            f.delete();
        }
        
        Process process = null;
        try {
            String[]command = new String[] { path, "-wait", 
                ///*installer.getAbsolutePath()*/"F:\\neembuu\\ReleasePlans\\pfmap-170\\pfm-170-jdownloader-win\\pfminst.exe", parameter };
                pismoInstallerDirectory+(uninstall?"Uninstall.bat":"Install.bat")};
                //"F:\\neembuu\\ReleasePlans\\Install.bat" };
            ProcessBuilder probuilder = new ProcessBuilder(command);
            
            process = probuilder.start();
            System.out.println(Arrays.toString(command));
        } catch (Throwable e) {
            Main.getLOGGER().log(Level.INFO,"Error running pismo installer/uninstaller",e);
        }
        if(process==null){
            Main.getLOGGER().log(Level.INFO,"Error running pismo installer/uninstaller");
            return;
        }
        
        int totalWaitTime = 0;
        while(!f.exists()){
            try{
                Thread.sleep(100);
                totalWaitTime += 100;
                if(totalWaitTime>10000){
                    listener.installationTakingTooLong(totalWaitTime/1000);
                }
            }catch(Exception a){
            }
        }
        
        f = Application.getResource(uninstall?"uninstall_logs.txt":"install_logs.txt");
        InputStream is = new FileInputStream(f) ;
        
        
        String out = streamToString(Main.getLOGGER(),is /*process.getInputStream()*/);
        out+="\n";
        //out+=streamToString(Main.getLOGGER(), process.getErrorStream());
        System.out.println(out);
        boolean success = false;
        if(!uninstall ){
            success = out.contains("complete") && !out.contains("Uninstall complete"); 
        }else{
            success = out.contains("Uninstall complete"); 
        }
        
        if (!success) {
            listener.installationFailed();
            throw new IllegalStateException("Installation failed, check log for details");
        }

        listener.installationSuccessful();
    }

    /*
     * -------------- Install Output -------------- Adding install reference for
     * file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\pfminst.exe".
     * Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\ptdllrun1.exe"
     * to "C:\Windows\system32\ptdllrun1.exe". NOTE: Keeping existing file
     * "C:\Windows\system32\ptdllrun1.exe", duplicate of
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\ptdllrun1.exe".
     * Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\ptdllrun1.exe"
     * to "C:\Windows\SysWOW64\ptdllrun1.exe". NOTE: Keeping existing file
     * "C:\Windows\SysWOW64\ptdllrun1.exe", duplicate of
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\ptdllrun1.exe".
     * 
     * Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\pfm.exe"
     * to "C:\Windows\pfm.exe". Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\pfmfs.sys"
     * to "C:\Windows\system32\Drivers\pfmfs_640.sys". Configuring driver
     * "pfmfs_640". Loading driver "pfmfs_640" via Service Manager. Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\pfmstat.exe"
     * to "C:\Windows\pfmstat.exe". Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\pfmhost.exe"
     * to "C:\Windows\pfmhost.exe". Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\pfmsyshost.exe"
     * to "C:\Windows\pfmsyshost.exe". Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\pfmapi.dll"
     * to "C:\Windows\system32\pfmapi_640.dll". Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\pfmapi.dll"
     * to "C:\Windows\SysWOW64\pfmapi_640.dll". Registering DLL
     * "pfmapi_640.dll". Running command
     * ""C:\Windows\SysWOW64\ptdllrun1.exe" -i pfmapi_640.dll". Registering DLL
     * "pfmapi_640.dll". Running command
     * ""C:\Windows\system32\ptdllrun1.exe" -i pfmapi_640.dll". Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\pfmisofs.dll"
     * to "C:\Windows\system32\pfmisofs.dll". Registering formatter
     * "C:\Windows\system32\pfmisofs.dll". Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\pfmpfolderfs.dll"
     * to "C:\Windows\system32\pfmpfolderfs.dll". Registering formatter
     * "C:\Windows\system32\pfmpfolderfs.dll". Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\pfmramfs.dll"
     * to "C:\Windows\system32\pfmramfs.dll". Registering formatter
     * "C:\Windows\system32\pfmramfs.dll". Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\pfmzipfs.dll"
     * to "C:\Windows\system32\pfmzipfs.dll". Registering formatter
     * "C:\Windows\system32\pfmzipfs.dll". Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\pfmhost.exe"
     * to "C:\Windows\SysWOW64\pfmhost.exe". Copying file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\pfmsyshost.exe"
     * to "C:\Windows\SysWOW64\pfmsyshost.exe". Core install complete.
     * 
     * 
     * ----------------------------------- UnInstall Output for a full install
     * ----------------------------------- Removing install reference for file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\pfminst.exe".
     * NOTE: Starting installed uninstaller "C:\Windows\pfm.exe". Running
     * command ""C:\Windows\pfm.exe" uninstall -f". Removing install reference
     * for file "C:\Windows\pfm.exe". Unconfiguring driver "pfmfs_640". Deleting
     * file "C:\Windows\system32\Drivers\pfmfs_640.sys". Deleting file
     * "C:\Windows\pfm.exe". Deleting file "C:\Windows\system32\pfmhost.exe".
     * Deleting file "C:\Windows\pfmhost.exe". Deleting file
     * "C:\Windows\system32\pfmsyshost.exe". Deleting file
     * "C:\Windows\pfmsyshost.exe". Deleting file "C:\Windows\pfmstat.exe".
     * Unregistering system DLL "pfmapi_640.dll". Running command
     * ""C:\Windows\system32\ptdllrun1.exe" -u pfmapi_640.dll". Deleting file
     * "C:\Windows\system32\pfmapi_640.dll". Unregistering system DLL
     * "pfmapi_640.dll". Running command
     * ""C:\Windows\SysWOW64\ptdllrun1.exe" -u pfmapi_640.dll". Deleting file
     * "C:\Windows\SysWOW64\pfmapi_640.dll". Unregistering formatter
     * "C:\Windows\system32\pfmisofs.dll". Deleting file
     * "C:\Windows\system32\pfmisofs.dll". Unregistering formatter
     * "C:\Windows\system32\pfmpfolderfs.dll". Deleting file
     * "C:\Windows\system32\pfmpfolderfs.dll". Unregistering formatter
     * "C:\Windows\system32\pfmramfs.dll". Deleting file
     * "C:\Windows\system32\pfmramfs.dll". Unregistering formatter
     * "C:\Windows\system32\pfmzipfs.dll". Deleting file
     * "C:\Windows\system32\pfmzipfs.dll". Deleting file
     * "C:\Windows\SysWOW64\pfmhost.exe". Deleting file
     * "C:\Windows\SysWOW64\pfmsyshost.exe". Uninstall complete. NOTE: Installed
     * uninstaller "C:\Windows\pfm.exe" finished. Deleting file
     * "C:\Windows\pfm.exe". Deleting file "C:\Windows\system32\pfmhost.exe".
     * Deleting file "C:\Windows\pfmhost.exe". Deleting file
     * "C:\Windows\system32\pfmsyshost.exe". Deleting file
     * "C:\Windows\pfmsyshost.exe". Deleting file "C:\Windows\pfmstat.exe".
     * Deleting file "C:\Windows\SysWOW64\pfmhost.exe". Deleting file
     * "C:\Windows\SysWOW64\pfmsyshost.exe". Uninstall complete.
     * 
     * -------------------------------------- UnInstall Output for a partial
     * install -------------------------------------- Removing install reference
     * for file
     * "J:\ProgramsAndSoftware\PismoFileMount\pfmap-162-win_extracted_exe\x64\pfminst.exe".
     * NOTE: Reference removed but other references remain. Not removing shared
     * files. C:\Users\Shashank
     * Tulsyan\.jd_home\libs\neembuu\pfmap\win\x64\pfminst.exe Uninstall
     * complete.
     */
    private static String streamToString(Logger l, InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s = null;
        StringBuilder sb = new StringBuilder();
        READLINE_LOOP: while ((s = br.readLine()) != null) {
            System.out.println(s);
            sb.append(s);
            sb.append("\n");
        }
        String ret = sb.toString();
        l.info(ret);
        return ret;
    }
    
    
    public static void main(String[] args) throws Exception{
        InstallerCallbackListener icl = new InstallerCallbackListener() {

            @Override
            public void informUserAboutInstallation() {
                JOptionPane.showMessageDialog(null, "can install?");
            }

            @Override
            public void installationTakingTooLong(int c) {
                JOptionPane.showMessageDialog(null, "too long "+c);
            }

            @Override
            public void installationSuccessful() {
                JOptionPane.showMessageDialog(null, "success");
            }

            @Override
            public void installationFailed() {
                JOptionPane.showMessageDialog(null, "failed");
            }
        };
        tryInstallingPismoFileMount(false,icl);
        System.out.println("done installing");
        tryInstallingPismoFileMount(true,icl);
    }
 
}

