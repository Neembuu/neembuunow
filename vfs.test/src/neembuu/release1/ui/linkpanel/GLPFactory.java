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

import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.ui.ExpandableUIContainer;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.OpenableEUI;
import neembuu.release1.api.ui.access.AddRemoveFromFileSystem;
import neembuu.release1.api.ui.access.RemoveFromUI;
import neembuu.release1.api.ui.actions.ChangeDownloadModeAction;
import neembuu.release1.api.ui.actions.ConnectionActions;
import neembuu.release1.api.ui.actions.ExpandAction;
import neembuu.release1.ui.actions.ChangeDownloadModeActionImpl;
import neembuu.release1.ui.actions.ConnectionActionsImpl;
import neembuu.release1.ui.actions.ExpandActionImpl;
import neembuu.release1.ui.actions.LinkActionsImpl;
import neembuu.release1.ui.actions.ReAddActionCallBackImpl;

/**
 *
 * @author Shashank Tulsyan
 */
public final class GLPFactory {
    
    public static OpenableEUI makeSingleLinkUI(final ExpandableUIContainer luic1, 
            final MainComponent mainComponent,
            RealFileProvider realFileProvider,
            AddRemoveFromFileSystem addRemoveFromFileSystem,
            TrialLinkHandler trialLinkHandler) {
        
        GenericLinkPanel lp = new GenericLinkPanel();
        final OpenableEUI openableEUI = lp.expandableUI;
        
        ConnectionActions connectionActions = new ConnectionActionsImpl(lp.lowerControlsUIA);
        ChangeDownloadModeAction changeDownloadModeAction = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);

        final RemoveFromUI removeFromUI = new RemoveFromUI() {
            @Override public void remove() {  luic1.removeUI(openableEUI); } };
        
        final LinkActionsImpl linkActionsImpl;
        
        linkActionsImpl = new LinkActionsImpl(
                lp.closeActionUIA, removeFromUI, realFileProvider, 
                mainComponent, addRemoveFromFileSystem, trialLinkHandler);
        
        
        linkActionsImpl.getReAdd().setCallBack(new ReAddActionCallBackImpl(lp.closeActionUIA, lp.lowerControlsUIA));
        
        ExpandAction expandAction = new ExpandActionImpl(lp.expandActionUIA);
        
        lp.initActions(expandAction, linkActionsImpl.getOpen(), linkActionsImpl.getClose(), 
                linkActionsImpl.getDelete(), linkActionsImpl.getReAdd(), 
                linkActionsImpl.getSave(), connectionActions, changeDownloadModeAction);
        
        lp.closeActionUIA.fileNameLabel().setText(trialLinkHandler.tempDisplayName());
        
        return openableEUI;
    }
    
}
