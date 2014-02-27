/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.ui;

import javax.swing.JComponent;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.ui.VirtualFileUI;
import neembuu.release1.api.ui.ExpandableUIContainer;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.ui.HeightProperty;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.access.AddRemoveFromFileSystem;
import neembuu.release1.api.ui.access.RemoveFromUI;
import neembuu.release1.api.ui.actions.ChangeDownloadModeAction;
import neembuu.release1.api.ui.actions.ConnectionActions;
import neembuu.release1.api.ui.actions.ExpandAction;

import neembuu.release1.ui.actions.ConnectionActionsImpl;
import neembuu.release1.ui.actions.ChangeDownloadModeActionImpl;
import neembuu.release1.ui.actions.ExpandActionImpl;
import neembuu.release1.ui.actions.LinkActionsImpl;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SingleFileLinkUI implements VirtualFileUI{
    
    private final LinkPanel lp;
    
    private VirtualFile virtualFile;

    private JComponent contraint;
    
    
    public SingleFileLinkUI(
            final ExpandableUIContainer luic1, 
            final MainComponent mainComponent,
            RealFileProvider realFileProvider,
            AddRemoveFromFileSystem addRemoveFromFileSystem) {
        lp = new LinkPanel();

        
        ConnectionActions connectionActions = new ConnectionActionsImpl(lp.lowerControlsUIA);
        ChangeDownloadModeAction changeDownloadModeAction = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);

        final RemoveFromUI removeFromUI = new RemoveFromUI() {
            @Override public void remove() { 
                luic1.removeUI(SingleFileLinkUI.this); } };
        
        LinkActionsImpl linkActionsImpl = new LinkActionsImpl(
                lp.closeActionUIA, removeFromUI, realFileProvider, 
                mainComponent, virtualFile, addRemoveFromFileSystem);
        
        ExpandAction expandAction = new ExpandActionImpl(lp.expandActionUIA);
        
        lp.initActions(expandAction, linkActionsImpl.getOpen(), linkActionsImpl.getClose(), 
                linkActionsImpl.getDelete(), linkActionsImpl.getReAdd(), 
                linkActionsImpl.getSave(), connectionActions, changeDownloadModeAction);
    }
    
    
    public void init(VirtualFile virtualFile){
        this.virtualFile = virtualFile;
        lp.setFile();
    }

    @Override
    public VirtualFile getVirtualFile() {
        return virtualFile;
    }

    @Override
    public JComponent getJComponent() {
        return lp;
    }

    @Override
    public void setContraintComponent(JComponent contraint) {
        this.contraint = contraint;
    }

    @Override
    public JComponent getContraintComponent() {
        return contraint;
    }

    @Override
    public HeightProperty heightProperty() {
        return lp.heightProperty;
    }
    
}
