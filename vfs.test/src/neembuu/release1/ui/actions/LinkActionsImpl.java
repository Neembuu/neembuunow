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

package neembuu.release1.ui.actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import neembuu.release1.Main;
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
    
    private VirtualFile vf;
    
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
    private final CloseAction close = new CloseAction() {
        @Override public void actionPerformed(ActionEvent e) {
            closeAction(true,false);}
        @Override public void closeOnlyUI() {
            closeAction(true,true);
        }};;
    private final ReAddAction addAndPlay = new ReAddAction() {
        @Override public void actionPerformed(ActionEvent e,boolean anotherThread) {
            reAddAction(anotherThread);
        }@Override public void setCallBack(ReAddAction.CallBack callBack) {
            if(LinkActionsImpl.this.callBack==null)LinkActionsImpl.this.callBack = callBack;
            else throw new IllegalStateException("Callback already set to "+LinkActionsImpl.this.callBack);
        }
    };

    ReAddAction.CallBack callBack = null;
    
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
            removeFromUI.remove();
        }
        //singleFileLinkUI.getLinkUIContainer().removeLinkUI(singleFileLinkUI);
    }
        
    void reAddAction(boolean anotherThread){
        if(!anotherThread)closeAction(false, false);
        else {
            ui.overlay().setVisible(false); // to prevent clicks on re-add 2 twice
            new Thread("ReAddAction{"+ui.fileNameLabel().getText()+"}"){
                @Override public void run() { closeAction(false, false);  }
            }.start();
        }
    }
    
    void closeAction(boolean closeOrOpen,boolean onlyUI){
        ui.rightControlsPanel().setVisible(!closeOrOpen);
        ui.overlay().setVisible(closeOrOpen);
        if(closeOrOpen){
            ui.border().setColor(Color.WHITE);
            ui.fileNameLabel().setForeground(Colors.BORDER);
            ui.contract();
            ui.openButton().setVisible(false);
            if(!onlyUI)closeActionProcess(false);
        }else /*open*/{
            createNewVirtualFile();
            ui.border().setColor(Colors.BORDER);
            ui.repaint();
            ui.fileNameLabel().setForeground(Color.BLACK);
            ui.fileNameLabel().setText(vf.getConnectionFile().getName());
            ui.openButton().setVisible(true);
            
            addRemoveFromFileSystem.add(vf);
            openVirtualFile();
            if(onlyUI){ throw new IllegalStateException("Will never happen"); }
        }
    }
    
    void closeActionProcess(boolean ignoreError){
        if(vf==null){ // virtual file has not been created so it cannot be closed
            return;
        }
        try{
            addRemoveFromFileSystem.remove(vf);
        }catch(Exception a){
            if(!ignoreError)
                Main.getLOGGER().log(Level.SEVERE, "Error in removing from filesystem, "
                    + "file might have not been added in the FS so it cannot be removed",a);
        }
        try{
            vf.getConnectionFile().closeCompletely();
            //vf = null; file cannot be deleted if this is done.
        }catch(Exception a){
            if(!ignoreError)Main.getLOGGER().log(Level.SEVERE, "Error in completely closing file",a);
        }
    }
    
    void saveAction(java.io.File outputFilePath){
        closeActionProcess(false);

        try{
            vf.getConnectionFile().getFileStorageManager().completeSession(outputFilePath, vf.getConnectionFile().getFileSize());
        }catch(Exception a){
            Main.getLOGGER().log(Level.SEVERE, "Could not save file",a);
            JOptionPane.showMessageDialog(mainComponent.getJFrame(), a.getMessage(),"Could not save file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    void deleteAction(){
        VirtualFile v = vf; //closeActionProcess sets v = null, so making a copy
        closeActionProcess(false);
        try{
            if(v==null){
                throw new UnsupportedOperationException("File removed, but could not find and delete temporary data");
            }else {
                v.getConnectionFile().getFileStorageManager().completeSession(null, v.getConnectionFile().getFileSize());
            }
        }catch(Exception a){
            Main.getLOGGER().log(Level.SEVERE, "Could not delete file",a);
            JOptionPane.showMessageDialog(mainComponent.getJFrame(), a.getMessage(),"Could not delete file", JOptionPane.ERROR_MESSAGE);
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
            if(!vf.canGetRealFile()){
                JOptionPane.showMessageDialog(mainComponent.getJFrame(),"This file is cannot be opened directly.","Cannot open file",JOptionPane.ERROR_MESSAGE);
                return;
            }
            java.io.File f = vf.getRealFile(); //File f = realFileProvider.getRealFile(vf);
            java.awt.Desktop.getDesktop().open(f);

        }catch(Exception a){
            JOptionPane.showMessageDialog(mainComponent.getJFrame(),a.getMessage(),"Could not open file",JOptionPane.ERROR_MESSAGE);
            Main.getLOGGER().log(Level.SEVERE,"Could not open file",a);
        }
    }
    
        
    private void createNewVirtualFile(){
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
        
        neembuu.release1.api.File f = linkHandler.getFiles().get(0);
        
        if(vf!=null){
            closeActionProcess(true);
        }
        
        try{
            vf = addRemoveFromFileSystem.create(f);
        }catch(Exception a){
            JOptionPane.showMessageDialog(mainComponent.getJFrame(), 
                    "Sorry! There is nothing you \n"
                    + "can do about it.\n"
                    + "Reason : "+a.getMessage(), 
                    "Could not make virtual file", JOptionPane.ERROR_MESSAGE);
            a.printStackTrace();
        }
        
        if(callBack==null){
            throw new IllegalStateException("Creation call back not set");
        }callBack.doneCreation(vf);
    }
    
    
    
}
