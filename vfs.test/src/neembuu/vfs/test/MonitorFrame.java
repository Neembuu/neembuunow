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

package neembuu.vfs.test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import jpfm.DirectoryStream;
import jpfm.FileAttributesProvider;
import neembuu.util.logging.LoggerUtil;
import neembuu.vfs.file.MonitoredHttpFile;

/**
 *
 * @author Shashank Tulsyan
 */
public class MonitorFrame implements ActionListener{

    final JFrame frame;
    JPanel content;
    
    public static final boolean DEBUG = true;

    private static final Logger LOGGER = LoggerUtil.getLogger();
    
    private final MountManagerService managerService;

    public MonitorFrame(MountManagerService managerService, FrameProvider fp) {
        this.managerService = managerService;
        frame = fp.getJFrame();
        
        createAndShowFUI();
    }
    
    void createAndShowFUI(){
        try{
            SwingUtilities.invokeAndWait(new Runnable() {
                    @Override public void run() { createGUI(); } });
        }catch(Exception idontCare){
            idontCare.printStackTrace();
        }
    }


    public void createGUI(){
        //frame = new JFrame("Monitored Neembuu Virtual Volume (containing real files) ");
        
        frame.setMaximumSize(new Dimension(428,380+100));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        frame.addWindowListener(new CloseHandler());
        content = new JPanel(new FlowLayout());
        JButton n = new JButton("Unmount");
        n.setActionCommand("unmount");
        n.addActionListener(this);
        n.setBounds(10, 10, 300, 100);
        
        JButton showOpenId = new JButton("Show open ids");
        showOpenId.setActionCommand("showOpenId");
        showOpenId.addActionListener(this);
        showOpenId.setBounds(10, 230, 300, 100);
        content.add(showOpenId);
        content.add(n);
        JButton printPendingOps = new JButton("Print pending ops");
        printPendingOps.setActionCommand("printPendingOps");
        printPendingOps.addActionListener(this);
        content.add(printPendingOps);
        JButton addFile = new JButton("Add files");
        addFile.setActionCommand("addFile");
        addFile.addActionListener(this);
        content.add(addFile);
        JButton gcChecker = new JButton("GC Check");
        gcChecker.setActionCommand("gcChecker");
        gcChecker.addActionListener(this);
        content.add(gcChecker);
        addToCon((DirectoryStream)managerService.volume(),false);
        content.setPreferredSize(new Dimension(600,5000));
        JScrollPane scrollPane = new JScrollPane(content);
        frame.setContentPane(scrollPane);
        frame.setPreferredSize(new Dimension(650,600+100));
        frame.pack();
        frame.setVisible(true);
    }

    private void addToCon(DirectoryStream ds,boolean inner){
        for(FileAttributesProvider ff : ds){
            if( ff instanceof MonitoredHttpFile )
                content.add( ((MonitoredHttpFile)ff).getFilePanel());
            else if(ff instanceof MonitoredRealFile && !inner)
                content.add( ((MonitoredRealFile)ff).getFilePanel());
            else if(ff instanceof MonitoredAbstractFile && !inner){
                content.add( ((MonitoredAbstractFile)ff).getFilePanel());
            }else {
                LOGGER.log(Level.INFO, "cannot add {0} to display frame ", ff);
                if(ff instanceof DirectoryStream){
                    addToCon((DirectoryStream)ff,true);
                }
            }
        }
    }
    
    private String[]showAddFilesDialog(){
        final LinkedList<String> filetoMountList = new LinkedList<String>();

        AddTestLinks atl = new AddTestLinks(frame, true, filetoMountList);
        atl.setVisible(true);

        String[]filesToMount_ = (String[])filetoMountList.toArray(new String[]{});
        return filesToMount_;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("unmount")){
            try{ managerService.unMount();}catch(Exception a){LOGGER.log(Level.INFO," ",a);}
        }
        if(e.getActionCommand().equalsIgnoreCase("showOpenId")){
            managerService.showOpenIds();
        }
        if(e.getActionCommand().equals("printPendingOps")){
            managerService.printPendingOps(); return;
        }
        if(e.getActionCommand().equals("gcChecker")){
            System.out.println("NumberOfReadInstancesInMemory="+jpfm.operations.ReadImpl.numberOfReadInstancesInMemory);
            return;
        }
        if(e.getActionCommand().equals("addFile")){
            addFile();
        }
    }
    
    private void addFile(){
        String[]newFilesToMount = showAddFilesDialog();
        for(String s:newFilesToMount){
            frame.setVisible(false);
            if(s.startsWith("http://") || s.startsWith("https://")){
                System.out.println("Adding file="+s);
                try{
                    String fileName = s.substring(s.lastIndexOf('/')+1);
                    String url = s;
                    content.add(managerService.addNewFile(fileName, url));
                }catch(Exception a){
                    LOGGER.log(Level.INFO," ",a);
                }
            }
        }
        frame.setVisible(true);
    }

    private final class CloseHandler extends WindowAdapter {
        @Override public void windowClosing(WindowEvent e) {
            try{ managerService.unMount(); } catch(Exception a){
                LOGGER.log(Level.INFO," ",a);
            }System.exit(0);
        }

    }
}