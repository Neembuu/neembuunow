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

package neembuu.vfs.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import jpfm.SystemUtils;

/**
 *
 * @author Shashank Tulsyan
 */
public final class Main {
    
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    
    public static MountManagerService mountManagerService;
    
    public static void main(String[]args) throws Exception {
        System.err.println(SystemUtils.OS_ARCH);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception lookandfeelexception) {
            LOGGER.log(Level.INFO," ",lookandfeelexception);
        } 
        
        
        if(args!=null && args.length > 0){
            if(args[0].equals("cascadeMount")){
                System.setProperty("neembuu.vfs.test.MoniorFrame.resumepolicy", 
                    "resumeFromPreviousState");
            }
        }else {
            System.setProperty("neembuu.vfs.test.MoniorFrame.resumepolicy", 
                    "emptyDirectory");
        }

        final String[]filesToMount;
        String mountLocation;
        String heapLocation;
        
        if(SystemUtils.IS_OS_WINDOWS){
            mountLocation = "j:\\neembuu\\virtual\\monitored.nbvfs";
            heapLocation = "J:\\neembuu\\heap\\";
            filesToMount=new String[]{
                "http://neembuu.com/test_videos/test120k.rmvb",
                //"http://neembuu.com/test_videos/bigbuckbunny(200v+64a)kbps.mkv"
            };
        }else if(SystemUtils.IS_OS_LINUX){
            mountLocation = "/media/j/neembuu/virtual/monitored14/";
            heapLocation  = "/media/j/neembuu/heap/";
            filesToMount=new String[]{
                "http://neembuu.com/test_videos/test120k.rmvb",
                "/media/j/Videos/Requiem_for_a_Duel.mkv"
            };
        }else {
            System.out.println("someother os, probably mac :"+ SystemUtils.OS_NAME);
            mountLocation = "/Volumes/MIDS/neembuu/virtual/monitored/" ;
            heapLocation = "/Volumes/MIDS/neembuu/heap/";
            filesToMount=new String[]{
                "http://neembuu.com/test_videos/test120k.rmvb",
                "/Volumes/MIDS/Videos/Requiem_for_a_Duel.mkv"
            };
        }
        
        FrameProvider fp = fp();
        
        MountManager mm = new MountManager(filesToMount, mountLocation, heapLocation, fp);
        
        MonitorFrame frame = new MonitorFrame(mm.mountManagerService,fp);
        
                
        if(args!=null && args.length > 0){
            if(args[0].equals("cascadeMount")){
                mountManagerService = mm.mountManagerService;
            }
        }else { mm.mount();}
    }
    
    static FrameProvider fp (){ return new FrameProvider() {
        private final JFrame frame = new JFrame("Monitored Neembuu Virtual Volume (containing real files) ");
            @Override public JFrame getJFrame() { return frame; }};}

}
