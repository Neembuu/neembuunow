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
import java.util.LinkedList;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import neembuu.release1.Main;
import neembuu.release1.api.file.NeembuuFile;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.file.NeembuuFileCreator;
import neembuu.release1.api.ui.access.CloseActionUIA;
import neembuu.release1.api.ui.access.RemoveFromUI;
import neembuu.release1.api.ui.actions.CloseAction;
import neembuu.release1.api.ui.actions.DeleteAction;
import neembuu.release1.api.ui.actions.OpenAction;
import neembuu.release1.api.ui.actions.ReAddAction;
import neembuu.release1.api.ui.actions.ReAddAction.CallBack;
import neembuu.release1.api.ui.actions.SaveAction;
import neembuu.release1.ui.Colors;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinkActionsImpl {
    private final CloseActionUIA ui;
    private final RemoveFromUI removeFromUI;
    private final MainComponent mainComponent;
    
    private volatile NeembuuFile connectionFile;
    
    private final NeembuuFileCreator neembuuFileCreator;
    private final OpenAction openAction;

    private final DeleteAction delete = new DeleteAction() {@Override public void actionPerformed() {
            delete();
        }};;
    private final SaveAction save = new SaveAction() {@Override public void actionPerformed() {
            saveFileClicked();
        }};;
    
    private final CloseAction close = new CloseAction() {
        @Override public void actionPerformed() {
            closeAction(true,false);}
        @Override public void initializeUIAsClosed() {
            closeAction(true,true);
        }};;
    private final ReAddAction addAndPlay = new ReAddAction() {
        
        @Override public void actionPerformed(boolean anotherThread) {
            reAddAction(anotherThread);
        }@Override public void addCallBack(CallBack callBack) {
            LinkActionsImpl.this.callBacks.add(callBack);
        }@Override public void removeCallBack(CallBack callBack) { 
            LinkActionsImpl.this.callBacks.remove(callBack);
        }};

    private final LinkedList<CallBack> callBacks = new LinkedList<CallBack>();
    
    public LinkActionsImpl(CloseActionUIA ui, RemoveFromUI removeFromUI, 
            MainComponent mainComponent, NeembuuFileCreator neembuuFileCreator, OpenAction oa) {
        this.ui = ui;this.removeFromUI = removeFromUI;openAction = oa;
        this.mainComponent = mainComponent; this.neembuuFileCreator = neembuuFileCreator;}
    
    public DeleteAction getDelete() {return delete;}
    public CloseAction getClose() {return close;}

    public ReAddAction getReAdd() {return addAndPlay;}
    public SaveAction getSave() {return save;}

    
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
            ui.contract();ui.openButton().setVisible(false);
            if(!onlyUI)closeActionProcess(false,false);
        }else /*open*/{
            if(!createNewVirtualFile()){ //undo UI changes
                closeAction(true, onlyUI);
                ui.repaint();return;}
            ui.border().setColor(Colors.BORDER);ui.repaint();
            ui.fileNameLabel().setForeground(Color.BLACK);
            ui.fileNameLabel().setText(connectionFile.getMinimumFileInfo().getName());
            ui.openButton().setVisible(true);
            
            
            connectionFile.addToFileSystem();
            
            openAction.actionPerformed();
            if(onlyUI){ throw new IllegalStateException("Will never happen"); }
        }
    }
    
    void closeActionProcess(boolean ignoreError,boolean calledFromDelete){
        if(connectionFile==null){ // virtual file has not been created so it cannot be closed
            return;
        }
        try{
            //addRemoveFromFileSystem.remove(connectionFile);
            connectionFile.removeFromFileSystem();
        }catch(Exception a){
            if(!ignoreError)
                Main.getLOGGER().log(Level.SEVERE, "Error in removing from filesystem, "
                    + "file might have not been added in the FS so it cannot be removed",a);
        }
        try{
            connectionFile.closeCompletely();
            //vf = null; file cannot be deleted if this is done.
        }catch(Exception a){
            if(!ignoreError && !calledFromDelete)
                Main.getLOGGER().log(Level.SEVERE, "Error in completely closing file",a);
        }
    }
    

    
    void deleteAction(){
        NeembuuFile v = connectionFile; //closeActionProcess sets v = null, so making a copy
        closeActionProcess(false,true);
        try{
            if(v==null){
                throw new UnsupportedOperationException("File removed, but could not find and delete temporary data");
            }else {
                //connectionFile.getFileStorageManager().completeSession(null, connectionFile.getFileSize());
                connectionFile.deleteSession();
            }
        }catch(Exception a){
            Main.getLOGGER().log(Level.SEVERE, "Could not delete file",a);
            JOptionPane.showMessageDialog(mainComponent.getJFrame(), a.getMessage(),"Could not delete file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    void saveFileClicked(){
        SaveActionImpl sai = new SaveActionImpl(connectionFile, mainComponent);
        sai.actionPerformed();
    }
        
    private boolean createNewVirtualFile(){        
        if(connectionFile!=null){
            closeActionProcess(true,false);
        }
        
        try{
            connectionFile = neembuuFileCreator.create();
        }catch(Exception a){
            JOptionPane.showMessageDialog(mainComponent.getJFrame(), 
                    "Sorry! There is nothing you \n"
                    + "can do about it.\n"
                    + "Reason : "+a.getMessage(), 
                    "Could not make virtual file", JOptionPane.ERROR_MESSAGE);
            a.printStackTrace();
            return false;
        }
        
        if(callBacks.isEmpty()){
            throw new IllegalStateException("Creation call back not set");
        }for (CallBack callBack : callBacks) {
            callBack.doneCreation(connectionFile);
        }
        
        return true;
    }
    
    
    
}
