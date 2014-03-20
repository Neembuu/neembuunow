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

import neembuu.release1.defaultImpl.splitImpl.SplitMergeNeembuuFileCreator;
import neembuu.release1.defaultImpl.file.SimpleNeembuuFileCreator;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.ui.ExpandableUIContainer;
import neembuu.release1.api.ui.linkpanel.Graph;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.linkpanel.OpenableEUI;
import neembuu.release1.api.ui.linkpanel.Progress;
import neembuu.release1.api.ui.access.AddRemoveFromFileSystem;
import neembuu.release1.api.ui.access.RemoveFromUI;
import neembuu.release1.api.ui.actions.ChangeDownloadModeAction;
import neembuu.release1.api.ui.actions.ConnectionActions;
import neembuu.release1.api.ui.actions.ExpandAction;
import neembuu.release1.ui.actions.ChangeDownloadModeActionImpl;
import neembuu.release1.ui.actions.ConnectionActionsImpl;
import neembuu.release1.ui.actions.ExpandActionImpl;
import neembuu.release1.ui.actions.LinkActionsImpl;
import neembuu.release1.ui.actions.OpenActionImpl;
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
        
        Graph graph = new GraphImpl(lp.graphUIA);
        Progress progress =  new ProgressImpl(lp.progressUIA,graph,ProgressImpl.Mode.OverallProgressUI);
        
        lp.init(graph, progress, VariantsImpl.makeDummy());
        
        final OpenableEUI openableEUI = lp.expandableUI;
        
        ConnectionActions connectionActions = new ConnectionActionsImpl(lp.lowerControlsUIA);
        ChangeDownloadModeAction changeDownloadModeAction = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);

        final RemoveFromUI removeFromUI = new RemoveFromUI() {
            @Override public void remove() {  luic1.removeUI(openableEUI); } };
        
        final LinkActionsImpl linkActionsImpl;
        
        final ExpandAction expandAction = new ExpandActionImpl(lp.expandActionUIA,ExpandActionImpl.Mode.SingleLinkType);
        
        SimpleNeembuuFileCreator fileCreator = new SimpleNeembuuFileCreator(
                trialLinkHandler, addRemoveFromFileSystem);
        OpenActionImpl openActionImpl = new OpenActionImpl(realFileProvider, mainComponent);
        linkActionsImpl = new LinkActionsImpl(
                lp.closeActionUIA, removeFromUI,mainComponent, fileCreator, openActionImpl);
        
        linkActionsImpl.getReAdd().addCallBack(openActionImpl);
        linkActionsImpl.getReAdd().addCallBack(new ReAddActionCallBackImpl(
                lp.closeActionUIA, lp.lowerControlsUIA, changeDownloadModeAction,true));
        
        lp.initActions(expandAction, openActionImpl, linkActionsImpl.getClose(), 
                linkActionsImpl.getDelete(), linkActionsImpl.getReAdd(), 
                linkActionsImpl.getSave(), connectionActions, changeDownloadModeAction, null);
        
        lp.closeActionUIA.fileNameLabel().setText(trialLinkHandler.tempDisplayName());
        linkActionsImpl.getClose().initializeUIAsClosed();
        return openableEUI;
    }
    
    
    public static OpenableEUI makeSplitLinkUI(final ExpandableUIContainer luic1, 
            final MainComponent mainComponent,
            RealFileProvider realFileProvider,
            AddRemoveFromFileSystem addRemoveFromFileSystem,
            TrialLinkGroup trialLinkGroup) {
        GenericLinkPanel lp = new GenericLinkPanel();
        final OpenableEUI openableEUI = lp.expandableUI;
        
        Graph graph = new GraphImpl(lp.graphUIA);
        Progress progress =  new ProgressImpl(lp.progressUIA,graph,ProgressImpl.Mode.OverallProgressUI);
        
        lp.init(graph, progress, VariantsImpl.makeDummy());
        
        ConnectionActions connectionActions = new ConnectionActionsImpl(lp.lowerControlsUIA);
        ChangeDownloadModeAction changeDownloadModeAction = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);

        final RemoveFromUI removeFromUI = new RemoveFromUI() {
            @Override public void remove() {  luic1.removeUI(openableEUI); } };
        
        final LinkActionsImpl linkActionsImpl;
        
        final ExpandAction expandAction = new ExpandActionImpl(lp.expandActionUIA,ExpandActionImpl.Mode.SplitLinkType);
        
        SplitMergeNeembuuFileCreator fileCreator = new SplitMergeNeembuuFileCreator(
                trialLinkGroup, addRemoveFromFileSystem);
        OpenActionImpl openActionImpl = new OpenActionImpl(realFileProvider, mainComponent);
        linkActionsImpl = new LinkActionsImpl(lp.closeActionUIA, removeFromUI, mainComponent, fileCreator, openActionImpl);
        
        linkActionsImpl.getReAdd().addCallBack(openActionImpl);
        linkActionsImpl.getReAdd().addCallBack(new ReAddActionCallBackImpl(
                lp.closeActionUIA, lp.lowerControlsUIA, changeDownloadModeAction,true));
        
        lp.initActions(expandAction, openActionImpl, linkActionsImpl.getClose(), 
                linkActionsImpl.getDelete(), linkActionsImpl.getReAdd(), 
                linkActionsImpl.getSave(), connectionActions, changeDownloadModeAction, null);
        
        
        lp.closeActionUIA.fileNameLabel().setText(trialLinkGroup.tempDisplayName());
        
        return openableEUI;
    }
    
}
