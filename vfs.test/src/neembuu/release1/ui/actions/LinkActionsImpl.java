/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui.actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import neembuu.release1.Main;
import neembuu.release1.api.File;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.LinkHandlerProviders;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.access.AddRemoveFromFileSystem;
import neembuu.release1.api.ui.access.CloseActionUIA;
import neembuu.release1.api.ui.access.RemoveFromUI;
import neembuu.release1.api.ui.actions.CloseAction;
import neembuu.release1.api.ui.actions.DeleteAction;
import neembuu.release1.api.ui.actions.OpenAction;
import neembuu.release1.api.ui.actions.ReAddAction;
import neembuu.release1.api.ui.actions.SaveAction;
import neembuu.release1.ui.Colors;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinkActionsImpl {
    private final CloseActionUIA ui;
    private final RemoveFromUI removeFromUI;
    private final RealFileProvider realFileProvider;
    private final MainComponent mainComponent;
    private final TrialLinkHandler trialLinkHandler;
    //private final VirtualFile vf;
    private final AddRemoveFromFileSystem addRemoveFromFileSystem;


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

    public LinkActionsImpl(CloseActionUIA ui, RemoveFromUI removeFromUI, RealFileProvider realFileProvider, MainComponent mainComponent,AddRemoveFromFileSystem addRemoveFromFileSystem, TrialLinkHandler trialLinkHandler) {
        this.ui = ui;
        this.removeFromUI = removeFromUI;
        this.realFileProvider = realFileProvider;
        this.mainComponent = mainComponent;
        this.addRemoveFromFileSystem = addRemoveFromFileSystem;
        this.trialLinkHandler = trialLinkHandler;
    }
    
    public DeleteAction getDelete() {
        return delete;
    }

    
    public CloseAction getClose() {
        return close;
    }

    
    public OpenAction getOpen() {
        return open;
    }

    
    public ReAddAction getReAdd() {
        return addAndPlay;
    }

    
    public SaveAction getSave() {
        return save;
    }

    
    public void delete(){
        int x;
        x = JOptionPane.showConfirmDialog(mainComponent.getJFrame(),"Are you sure you want to delete this file","Delete",JOptionPane.YES_NO_OPTION);
        if(x == JOptionPane.YES_OPTION){
            deleteAction();
        }
        removeFromUI.remove();
        //singleFileLinkUI.getLinkUIContainer().removeLinkUI(singleFileLinkUI);
    }
        
    void closeAction(boolean closeOrOpen){
        ui.rightControlsPanel().setVisible(!closeOrOpen);
        ui.overlay().setVisible(closeOrOpen);
        if(closeOrOpen){
            ui.border().setColor(Color.WHITE);
            ui.fileNameLabel().setForeground(Colors.BORDER);
            ui.contract();
            ui.openButton().setVisible(false);
            closeActionProcess();
        }else /*open*/{
            createNew();
            ui.border().setColor(Colors.BORDER);
            ui.repaint();
            ui.fileNameLabel().setForeground(Color.BLACK);
            ui.fileNameLabel().setText(vf.getConnectionFile().getName());
            ui.openButton().setVisible(true);
            
            addRemoveFromFileSystem.add(vf);
            openVirtualFile();
        }
    }
    
    void closeActionProcess(){
        addRemoveFromFileSystem.remove(vf);
        try{
            vf.getConnectionFile().closeCompletely();
        }catch(Exception a){
            Main.getLOGGER().log(Level.SEVERE, "Erorr in completely closing file",a);
        }
    }
    
    void saveAction(File outputFilePath){
        closeActionProcess();

        try{
            vf.getConnectionFile().getFileStorageManager().completeSession(outputFilePath, vf.getConnectionFile().getFileSize());
        }catch(Exception a){
            Main.getLOGGER().log(Level.SEVERE, "Could not save file",a);
            JOptionPane.showMessageDialog(mainComponent.getJFrame(), a.getMessage(),"Could not save file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    void deleteAction(){
        closeActionProcess();
        try{
            if(vf==null){
                throw new UnsupportedOperationException();
            }else {
                vf.getConnectionFile().getFileStorageManager().completeSession(null, vf.getConnectionFile().getFileSize());
            }
        }catch(Exception a){
            Main.getLOGGER().log(Level.SEVERE, "Could not delete file",a);
            JOptionPane.showMessageDialog(mainComponent.getJFrame(), a.getMessage(),"Could not save file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    void saveFileClicked(){
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setSelectedFile(new File(System.getProperty("java.home")+File.separator+
                vf.getConnectionFile().getName()));
        fileChooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
        int retVal = fileChooser.showSaveDialog(mainComponent.getJFrame());
        if(retVal == javax.swing.JFileChooser.APPROVE_OPTION){
            saveAction(fileChooser.getSelectedFile().getAbsoluteFile());
        }else {

        }
    }
    
    
    public void openVirtualFile(){
        try{
            File f = realFileProvider.getRealFile(vf);
            java.awt.Desktop.getDesktop().open(f);

        }catch(Exception a){
            JOptionPane.showMessageDialog(null,a.getMessage(),"Could not open file",JOptionPane.ERROR_MESSAGE);
            Main.getLOGGER().log(Level.SEVERE,"Could not open file",a);
        }
    }
    
    
    private void createNew(){
        LinkHandler linkHandler = 
            LinkHandlerProviders.getHandler(trialLinkHandler.getReferenceLinkString());
        if(linkHandler==null){
            JOptionPane.showMessageDialog(mainComponent.getJFrame(), 
                    "It seems this website is not\n"
                    + "supported anymore by Neembuu now.", 
                    "Could not handle this link", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(linkHandler.getFiles().size() > 1) {
            Main.getLOGGER().info("LinkHandler "+linkHandler+" has more than one file when"
                    + " it was expected to have only one. Using only first file.");
        }
        
        File f = linkHandler.getFiles().get(0);
        
        VirtualFile vf 
    }
}
