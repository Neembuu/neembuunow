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

import java.nio.file.Path;
import neembuu.release1.app.Application;
import static neembuu.release1.app.Application.Resource.Installation;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.Message;

/**
 *
 * @author Shashank Tulsyan
 */
public final class lin implements InstallService {
    
    private final PismoInstaller pi;

    public lin(PismoInstaller pi) {
        this.pi = pi;
    }
    
    boolean done = false;

    @Override
    public boolean done() {
        return done;
    }
    
    @Override public void install() throws Exception {
        Path pfm_setup = Application.getResource(Installation,"pfm","pfm-170-lin.run");
        installImpl(pfm_setup);
    }
    
    void installImpl(Path pfm_setup)throws Exception{
        String password = pi.mainComponent.newMessage()
                .setTitle("Please enter super-user password")
                .setMessage("Neembuu needs super user access to install Pismo file mount.\n"
                        + "It needs to run the following setup file \n"
                        + "\n"
                        + pfm_setup.toAbsolutePath()+"\n"
                        + "\n"
                        + "Also note that gcc should be installed on your system\n"
                        + "Sometimes when none of the precompiled binaries\n"
                        + "match your system, the setup tries to make compatible\n"
                        + "binaries from the source code. For this gcc must be installed.\n"
                        + "Neembuu might attempt to install gcc, by executing :\n"
                        + "sudo apt-get install gcc\n"
                        + "\n"
                        + "Please enter super-user password")
                .askPassword();
        
        if(password==null){
            System.out.println("User did not provide super user password");
            System.exit(-1);
        }
        
        String output = "";
        output+=executeAsRoot(password, "chmod +x "+pfm_setup);
        output+=fix_gcc(password);
        
        String install = pi.uninstall?"uninstall":"install";
        String pfm_install = executeAsRoot(password, pfm_setup+" "+install);
        
        boolean success = PismoInstaller.check(pfm_install, pi);
        
        if(success){
            pi.listener.installationSuccessful();
        }else { 
            pi.listener.installationFailed(output+"\n"+pfm_install);
        }
    }
    
    private boolean checkGccExists(){
        String[]cmd_gcc = {"gcc","--version"};
        ProcessBuilder gcc_pb1 = new ProcessBuilder(cmd_gcc);    
        gcc_pb1.inheritIO();
        boolean gccAbsent = false;
        try{
            Process gcc_p = gcc_pb1.start();
            gcc_p.waitFor();
            if(gcc_p.exitValue()!=0){
                gccAbsent = true;
            }
            System.out.println("gcc version check error code="+gcc_p.exitValue());
        }catch(Exception a){
            gccAbsent = true;
        }return gccAbsent;
    }
    
    private String fix_gcc(String password){
        boolean gccAbsent = checkGccExists();
        if(!gccAbsent)return "";
        String s1 = executeAsRoot(password, "apt-get clean");
        String s2 = executeAsRoot(password, "apt-get -y install gcc");
        return s1+"\n"+s2;
    }
    
    public String executeAsRoot(String password, String cmd){
        return executeAsRoot(password, cmd, pi.mainComponent);
    }
    
    public static String executeAsRoot(String password, String cmd, MainComponent mainComponent ){
        Message m = mainComponent.newMessage().setTitle(cmd)
                .setMessage(cmd).editable()
                .showNonBlocking();
        
        System.out.println("Now executing "+cmd);
        String[] cmd_array = {"/bin/bash","-c","sudo -S "+cmd};
        ProcessBuilder pb = new ProcessBuilder(cmd_array);
        
        System.out.println("cmd="+"/bin/bash -c sudo -S "+cmd);
        String st =  "";
        try{
            Process p = pb.start();
            
            p.getOutputStream().write(password.getBytes());
            p.getOutputStream().write('\n');
            p.getOutputStream().flush();
            /*for (int i = 0; i < 100; i++) {
                try{
                    p.getOutputStream().write('Y');p.getOutputStream().flush();
                    //p.getOutputStream().write('\n');p.getOutputStream().flush();
                }catch(IOException a){
                    a.printStackTrace();
                }
            }*/
            
            StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR",m);
            StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT",m);
            outputGobbler.start();
            errorGobbler.start();
            
            p.waitFor();
            
            st = errorGobbler.getAsString()+"\n"+outputGobbler.getAsString();

            System.out.println("gcc version="+p.exitValue());
        }catch(Exception a){
            a.printStackTrace();
            st = a.getMessage();
        }
        System.out.println("Hiding"+cmd);
        m.close();
        System.out.println("Done executing "+cmd);
        return st;
    }
}
