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
package neembuu.release1.pismo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jpfm.MachineUtils;
import neembuu.diskmanager.NioUtils;
import neembuu.release1.Application;
import static neembuu.release1.Application.Resource.Home;
import static neembuu.release1.Application.Resource.Installation;
import static neembuu.release1.Application.Resource.Logs;
import neembuu.release1.Main;
import static neembuu.release1.pismo.PismoInstaller.installLogFileName;

/**
 *
 * @author Shashank Tulsyan
 */
public final class win implements InstallService{

    private final PismoInstaller pi;

    win(PismoInstaller pismoInstaller) {
        this.pi = pismoInstaller;
    }
    
    boolean done = false;

    @Override
    public boolean done() {
        return done;
    }
    
    @Override public void install() throws Exception {
        Path pfm_setup = Application.getResource(Installation,"pfm","pfm-170-win");
        pi.listener.informUserAboutInstallation();

        Path base = Application.getResource(Installation,"elevate");
        
        String path = 
                MachineUtils.getRuntimeSystemArchitecture() == MachineUtils.MachineType.AMD64 ?
                    base.resolve("Elevate64.exe").toAbsolutePath().toString() : base.resolve("Elevate32.exe").toAbsolutePath().toString();

        Path pfm_temp = Application.getResource(Home,"pfm_install_temp");
        Path pfm_logs = Application.getResource(Logs);
        if(Files.exists(pfm_temp)){
            if(Files.isDirectory(pfm_temp)){
                NioUtils.deleteDirectory(pfm_temp);
            }else {
                Files.delete(pfm_temp);
            }
        }
        Files.createDirectory(pfm_temp);
                
        Path batchFile = makeWindowsBatchFile(pfm_temp, pfm_setup, pfm_logs, pi.uninstall);
        
        Process process = null;
        try {
            String[]command = new String[] { path, "-wait", batchFile.toAbsolutePath().toString()};

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
        while(!Files.exists(pfm_logs.resolve("done.txt"))){
            try{
                Thread.sleep(100);
                totalWaitTime += 100;
                if(totalWaitTime>10000){
                    pi.listener.installationTakingTooLong(totalWaitTime/1000);
                }
            }catch(Exception a){
            }
        }
        
        Path install_logs = installLogFileName(pi.uninstall);
        InputStream is = new FileInputStream(install_logs.toFile()) ;
        PismoInstaller.CheckResults cr = PismoInstaller.check(is, pi);
        if(cr.success)
            pi.listener.installationSuccessful();
        else { 
            pi.listener.installationFailed(cr.out_n_err);
        }
    }
   
    
    private static Path makeWindowsBatchFile(
            Path dest, Path pfm, Path logs, boolean uninstall) throws IOException {
        /*
         @echo off
         %0\..\tools\pfmap\win\pfminst install >> %0\..\install_logs.txt
         echo "done" >> %~dp0\done.txt
         */
        final String installStr = uninstall ? "uninstall" : "install";
        List<String> lines = new ArrayList<>();
        lines.add(n(pfm.resolve("pfminst.exe"))
                + " "
                + installStr
                + " >> " + n(installLogFileName(uninstall).toAbsolutePath())
        );
        // single > since we want to overwrite the file
        lines.add("echo \"done\" > " + n(logs.resolve("done.txt")) );
        lines.add("@echo off");

        dest = dest.resolve(installStr + ".bat");

        return Files.write(dest, lines, Charset.forName("US-ASCII"), WRITE, CREATE, TRUNCATE_EXISTING);
    }
    
    private static String n(Path p){
        return "\""+p.toAbsolutePath()+"\"";
    }
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
