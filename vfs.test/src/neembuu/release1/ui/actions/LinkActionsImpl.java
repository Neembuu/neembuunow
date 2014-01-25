/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui.actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import neembuu.release1.Main;
import neembuu.release1.api.ui.access.CloseActionUIA;
import neembuu.release1.api.ui.actions.CloseAction;
import neembuu.release1.api.ui.actions.DeleteAction;
import neembuu.release1.api.ui.actions.OpenAction;
import neembuu.release1.api.ui.actions.ReAddAction;
import neembuu.release1.api.ui.actions.SaveAction;
import neembuu.release1.ui.Colors;
import neembuu.release1.ui.SingleFileLinkUI;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinkActionsImpl {
    private SingleFileLinkUI singleFileLinkUI;
    private CloseActionUIA ui;

    private final DeleteAction delete = new DeleteAction() {@Override public void actionPerformed(ActionEvent e) {
                delete();
        }};;
    private final SaveAction save = new SaveAction() {@Override public void actionPerformed(ActionEvent e) {
                saveFileClicked();
        }};;
    private final OpenAction open = new OpenAction() {@Override public void actionPerformed(ActionEvent e) {
                openVirtualFile();
        }};;
    private final CloseAction close = new CloseAction() {@Override public void actionPerformed(ActionEvent e) {
                closeAction(true);
        }};;
    private final ReAddAction addAndPlay = new ReAddAction() {@Override public void actionPerformed(ActionEvent e) {
                closeAction(false);
        }};;

    public void init(CloseActionUIA ui,SingleFileLinkUI singleFileLinkUI) {
        this.singleFileLinkUI = singleFileLinkUI;
        this.ui = ui;
    }
    
    
    
    
    public ActionListener getDelete() {
        return delete;
    }

    
    public ActionListener getClose() {
        return close;
    }

    
    public ActionListener getOpen() {
        return open;
    }

    
    public ActionListener getAddAndPlay() {
        return addAndPlay;
    }

    
    public ActionListener getSave() {
        return save;
    }

    
    public void delete(){
        int x;
        x = JOptionPane.showConfirmDialog(singleFileLinkUI.getNeembuuUI().getFrame(),"Are you sure you want to delete this file","Delete",JOptionPane.YES_NO_OPTION);
        if(x == JOptionPane.YES_OPTION){
            saveAction(null);
        }
        singleFileLinkUI.getLinkUIContainer().removeLinkUI(singleFileLinkUI);
    }
        
    void closeAction(boolean closeOrOpen){
        ui.rightControlsPanel().getPanel().setVisible(!closeOrOpen);
        ui.overlay().setVisible(closeOrOpen);
        if(closeOrOpen){
            ui.border().setColor(Color.WHITE);
            ui.fileNameLabel().setForeground(Colors.BORDER);
            ui.contract();
            ui.openButton().setVisible(false);
            closeActionProcess();
        }else {
            ui.border().setColor(Colors.BORDER);
            ui.repaint();
            ui.fileNameLabel().setForeground(Color.BLACK);
            ui.fileNameLabel().setText(singleFileLinkUI.getVirtualFile().getConnectionFile().getName());
            ui.openButton().setVisible(true);
            singleFileLinkUI.getMountManager().addFile(singleFileLinkUI.getVirtualFile());
            openVirtualFile();
        }
    }
    
    void closeActionProcess(){
        singleFileLinkUI.getMountManager().removeFile(singleFileLinkUI.getVirtualFile());
        try{
            singleFileLinkUI.getVirtualFile().getConnectionFile().closeCompletely();
        }catch(Exception a){
            Main.getLOGGER().log(Level.SEVERE, "Erorr in completely closing file",a);
        }
    }
    
    void saveAction(File outputFilePath){
        closeActionProcess();

        try{
            singleFileLinkUI.getVirtualFile().getConnectionFile().getFileStorageManager().completeSession(outputFilePath, singleFileLinkUI.getVirtualFile().getConnectionFile().getFileSize());
        }catch(Exception a){
            Main.getLOGGER().log(Level.SEVERE, "Could not save file",a);
            JOptionPane.showMessageDialog(singleFileLinkUI.getNeembuuUI().getFrame(), a.getMessage(),"Could not save file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    void saveFileClicked(){
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setSelectedFile(new File(System.getProperty("java.home")+File.separator+
                singleFileLinkUI.getVirtualFile().getConnectionFile().getName()));
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        int retVal = fileChooser.showSaveDialog(singleFileLinkUI.getNeembuuUI().getFrame());
        if(retVal == javax.swing.JFileChooser.APPROVE_OPTION){
            saveAction(fileChooser.getSelectedFile().getAbsoluteFile());
        }else {

        }
    }
    
    
    public void openVirtualFile(){
        try{
            File f = getAsRealFile();
            java.awt.Desktop.getDesktop().open(f);

        }catch(Exception a){
            JOptionPane.showMessageDialog(null,a.getMessage(),"Could not open file",JOptionPane.ERROR_MESSAGE);
            Main.getLOGGER().log(Level.SEVERE,"Could not open file",a);
        }
    }

    
    private File getAsRealFile(){
        return new File(singleFileLinkUI.getMountManager().getMount().getMountLocation().getAsFile(),
                    singleFileLinkUI.getVirtualFile().getConnectionFile().getName());
    }
}
