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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import javax.swing.JFrame;
import jpfm.SystemUtils;
import neembuu.release1.Application;
import static neembuu.release1.Application.Resource.*;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.ui.MainComponentImpl;

/**
 *
 * @author Shashank Tulsyan
 */
public final class PismoInstaller {
    
    final MainComponent mainComponent;
    final boolean uninstall;
    final InstallerCallbackListener listener;

    public PismoInstaller(MainComponent mainComponent, boolean uninstall, InstallerCallbackListener listener) {
        this.mainComponent = mainComponent;
        this.uninstall = uninstall;
        this.listener = listener;
    }    
    
    public static void tryInstallingPismoFileMount(MainComponent mainComp, boolean uninstall, InstallerCallbackListener listener) throws Exception {
        PismoInstaller pi = new PismoInstaller(mainComp, uninstall, listener);
        if (SystemUtils.IS_OS_WINDOWS && false){
            new win(pi).install();
        }
        if (SystemUtils.IS_OS_MAC) {
            //pismoInstallerDirectory = pismoInstallerDirectory.resolve("mac");
            throw new IllegalStateException("Macintosh support in not included with this package");
        }else { // assume linux
            new lin(pi).install();
        }
    }
    
    public static final class CheckResults { 
        public final String out_n_err;
        public final boolean success;
        public CheckResults(String out_n_err, boolean success) {this.out_n_err = out_n_err;this.success = success;}
    }

    public static CheckResults check(InputStream is, PismoInstaller pi)throws IOException{
        String out = streamToString(is);
        out+="\n";

        System.out.println(out);
        boolean success = check(out, pi);
        
        return new CheckResults(out, success);
    }
    
    public static String streamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s = null;
        StringBuilder sb = new StringBuilder();
        READLINE_LOOP: while ((s = br.readLine()) != null) {
            System.out.println(s);
            sb.append(s);
            sb.append("\n");
        }
        String ret = sb.toString();
        System.out.println(ret);
        return ret;
    }
    
    public static boolean check(String out,PismoInstaller pi){
        boolean success = false;
        if(!pi.uninstall ){
            success = out.contains("complete") && !out.contains("Uninstall complete"); 
        }else{
            success = out.contains("Uninstall complete"); 
        }return success;
    }
    
    public static Path installLogFileName(boolean uninstall){
        Path logs = Application.getResource(Logs);
        final String installStr = uninstall?"uninstall":"install";
        return logs.resolve("pfm_" + installStr + ".log.txt");
    }
    
    public static void main(String[] args) throws Exception{
        final JFrame jf = new JFrame("test-neembuu-pfm-install");
        /*SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                jf.setVisible(true);
            }
        });*/ 
        final MainComponent mci = new MainComponentImpl(jf);
        Application.setMainComponent(mci);
        InstallerCallbackListener icl = new InstallerCallbackListener() {

            @Override
            public void informUserAboutInstallation() {
                mci.newMessage().setTitle("Can install?").setTimeout(1000).show();
            }

            @Override
            public void installationTakingTooLong(int c) {
                mci.newMessage().setTitle("Tool long").setMessage("time="+c).setTimeout(1000).show();
            }

            @Override
            public void installationSuccessful() {
                mci.newMessage().setTitle("success").setTimeout(3000).show();
            }

            @Override
            public void installationFailed(String stdOut) {
                mci.newMessage().setTitle("failed").setMessage(stdOut).show();
            }
        };
        
        tryInstallingPismoFileMount(mci,false,icl);
        System.out.println("done installing");
        tryInstallingPismoFileMount(mci,true,icl);
        
        System.exit(0);
    }
 
}

