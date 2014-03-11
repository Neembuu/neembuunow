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

package neembuu.release1.ui.linkpanel;

import java.io.File;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.OpenableEUI;
import neembuu.release1.api.ui.access.RemoveFromUI;
import neembuu.release1.api.ui.actions.ChangeDownloadModeAction;
import neembuu.release1.api.ui.actions.ConnectionActions;
import neembuu.release1.api.ui.actions.ExpandAction;
import neembuu.release1.defaultImpl.LinkOrganizerImplTest;
import neembuu.release1.ui.MainPanel;
import neembuu.release1.ui.actions.ChangeDownloadModeActionImpl;
import neembuu.release1.ui.actions.ConnectionActionsImpl;
import neembuu.release1.ui.actions.ExpandActionImpl;
import neembuu.release1.ui.actions.LinkActionsImpl;
import neembuu.release1.ui.actions.ReAddActionCallBackImpl;
import neembuu.release1.ui.linkcontainer.LinksContainer;

/**
 *
 * @author Shashank Tulsyan
 */
public class TestGenericLinkPanel {
    MainPanel mp;
    LinksContainer lc;
    MainComponent mainComponent;

    public TestGenericLinkPanel(MainPanel mp, LinksContainer lc, MainComponent mainComponent) {
        this.mp = mp;
        this.lc = lc;
        this.mainComponent = mainComponent;
    }
    
    
    public OpenableEUI singleLink(){
        GenericLinkPanel lp = new GenericLinkPanel();
        final OpenableEUI openableEUI = lp.expandableUI;
        
        ConnectionActions connectionActions = new ConnectionActionsImpl(lp.lowerControlsUIA);
        ChangeDownloadModeAction changeDownloadModeAction = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);

        final RemoveFromUI removeFromUI = new RemoveFromUI() {
            @Override public void remove() {  lc.removeUI(openableEUI); } };
        
        final LinkActionsImpl linkActionsImpl;
        
        LinkOrganizerImplTest.TrialLinkHandlerDummy  trialLinkHandlerDummy =
                LinkOrganizerImplTest.make("XYZ EP - 09, When Aliens came to planet earth.rmvb");
        
        linkActionsImpl = new LinkActionsImpl(
                lp.closeActionUIA, removeFromUI, realFileProvider, 
                mainComponent, null, trialLinkHandlerDummy);
        
        
        linkActionsImpl.getReAdd().setCallBack(new ReAddActionCallBackImpl(lp.closeActionUIA, lp.lowerControlsUIA));
        
        ExpandAction expandAction = new ExpandActionImpl(lp.expandActionUIA);
        
        lp.initActions(expandAction, linkActionsImpl.getOpen(), linkActionsImpl.getClose(), 
                linkActionsImpl.getDelete(), linkActionsImpl.getReAdd(), 
                linkActionsImpl.getSave(), connectionActions, changeDownloadModeAction);
        
        lp.closeActionUIA.fileNameLabel().setText(trialLinkHandlerDummy.tempDisplayName());
        
        lp.lowerControlsUIA.progress().init(new FakeFileBeingDownloaded());
        
        return openableEUI;
    }
    
    RealFileProvider realFileProvider = new RealFileProvider() {
        @Override  public File getRealFile(VirtualFile vf) {
            return new File("j:\\neembuu\\realfiles\\test120k.rmvb"); 
        } };
    
    
}
