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

package neembuu.release1.ui;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.ui.VirtualFileUI;
import neembuu.release1.api.ui.ExpandableUIContainer;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.ui.HeightProperty;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.access.AddRemoveFromFileSystem;
import neembuu.release1.api.ui.access.RemoveFromUI;
import neembuu.release1.api.ui.actions.ChangeDownloadModeAction;
import neembuu.release1.api.ui.actions.ConnectionActions;
import neembuu.release1.api.ui.actions.ExpandAction;
import neembuu.release1.api.ui.actions.ReAddAction;

import neembuu.release1.ui.actions.ConnectionActionsImpl;
import neembuu.release1.ui.actions.ChangeDownloadModeActionImpl;
import neembuu.release1.ui.actions.ExpandActionImpl;
import neembuu.release1.ui.actions.LinkActionsImpl;
import neembuu.release1.ui.actions.ReAddActionCallBackImpl;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SingleFileLinkUI implements VirtualFileUI{
    
    private final LinkPanel lp;
    
    //private VirtualFile virtualFile;

    private JComponent contraint;
    
    private TrialLinkHandler trialLinkHandler;
    
    private final LinkActionsImpl linkActionsImpl;
    
    public SingleFileLinkUI(
            final ExpandableUIContainer luic1, 
            final MainComponent mainComponent,
            RealFileProvider realFileProvider,
            AddRemoveFromFileSystem addRemoveFromFileSystem,
            TrialLinkHandler trialLinkHandler) {
        lp = new LinkPanel();

        
        ConnectionActions connectionActions = new ConnectionActionsImpl(lp.lowerControlsUIA);
        ChangeDownloadModeAction changeDownloadModeAction = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);

        final RemoveFromUI removeFromUI = new RemoveFromUI() {
            @Override public void remove() { 
                luic1.removeUI(SingleFileLinkUI.this); } };
        
        linkActionsImpl = new LinkActionsImpl(
                lp.closeActionUIA, removeFromUI, realFileProvider, 
                mainComponent, addRemoveFromFileSystem, trialLinkHandler);
        
        
        linkActionsImpl.getReAdd().setCallBack(new ReAddActionCallBackImpl(lp.closeActionUIA, lp.lowerControlsUIA));
        
        ExpandAction expandAction = new ExpandActionImpl(lp.expandActionUIA);
        
        lp.initActions(expandAction, linkActionsImpl.getOpen(), linkActionsImpl.getClose(), 
                linkActionsImpl.getDelete(), linkActionsImpl.getReAdd(), 
                linkActionsImpl.getSave(), connectionActions, changeDownloadModeAction);
        
        lp.closeActionUIA.fileNameLabel().setText(trialLinkHandler.tempDisplayName());
        
        /*try{*/
            linkActionsImpl.getClose().closeOnlyUI();
        /*}catch(NullPointerException ignore){
            //ignore.printStackTrace();
        }*/
    }

    public void reAddOpen(){
        linkActionsImpl.getReAdd().actionPerformed(null);
    }
    
    private VirtualFile virtualFile = null;
    
    void init(VirtualFile virtualFile){
        this.virtualFile = virtualFile;

        //lp.setFile(virtualFile);
    }

    @Override
    public VirtualFile getVirtualFile() {
        return virtualFile;
    }

    @Override
    public JComponent getJComponent() {
        return lp;
    }

    /*@Override
    public void setContraintComponent(JComponent contraint) {
        this.contraint = contraint;
    }

    @Override
    public JComponent getContraintComponent() {
        return contraint;
    }*/

    @Override
    public HeightProperty heightProperty() {
        return lp.heightProperty;
    }
    
}
