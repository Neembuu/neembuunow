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
import neembuu.release1.api.ui.access.MinimalistFileSystem;
import neembuu.release1.api.ui.access.RemoveFromUI;
import neembuu.release1.api.ui.actions.ChangeDownloadModeAction;
import neembuu.release1.api.ui.actions.ReAddAction;
import neembuu.release1.api.ui.linkpanel.ProgressProvider;
import neembuu.release1.defaultImpl.multiVariant.MultiVariantFileCreator;
import neembuu.release1.ui.actions.ChangeDownloadModeActionImpl;
import neembuu.release1.ui.actions.ConnectionActionsImpl;
import neembuu.release1.ui.actions.ExpandActionImpl;
import neembuu.release1.ui.actions.LinkActionsImpl;
import neembuu.release1.ui.actions.MultiVariantOpenAction;
import neembuu.release1.ui.actions.OpenActionImpl;
import neembuu.release1.ui.actions.ReAddActionCallBackImpl;
import neembuu.release1.ui.actions.SaveAction_forVariants;
import neembuu.vfs.progresscontrol.DownloadSpeedProvider;

/**
 *
 * @author Shashank Tulsyan
 */
public final class GLPFactory {
    
    private final ExpandableUIContainer luic1;
    private final MainComponent mainComponent;
    private final RealFileProvider realFileProvider;
    private final MinimalistFileSystem root;
    private final TrialLinkGroup linkGroup;
    private final DownloadSpeedProvider downloadSpeedProvider;

    public GLPFactory(ExpandableUIContainer luic1, MainComponent mainComponent, RealFileProvider realFileProvider, MinimalistFileSystem root, TrialLinkGroup linkGroup, DownloadSpeedProvider downloadSpeedProvider) {
        this.luic1 = luic1;
        this.mainComponent = mainComponent;
        this.realFileProvider = realFileProvider;
        this.root = root;
        this.linkGroup = linkGroup;
        this.downloadSpeedProvider = downloadSpeedProvider;
    }
    
    public static OpenableEUI make(ExpandableUIContainer luic1, 
            MainComponent mainComponent, RealFileProvider realFileProvider,
            MinimalistFileSystem root,TrialLinkGroup linkGroup, 
            DownloadSpeedProvider downloadSpeedProvider){
        int size = linkGroup.getAbsorbedLinks().size();
        if(size==0)return null;
        
        GLPFactory factory = new GLPFactory(luic1, mainComponent, 
                realFileProvider, root, linkGroup, downloadSpeedProvider);
        
        OpenableEUI openableEUI;
        if(size > 1){
            openableEUI = factory.makeSplitLinkUI();
        }else {
            if(linkGroup.getAbsorbedLinks().get(0).containsMultipleLinks()){
                openableEUI = factory.makeMultiVariantUI();
            }else {
                openableEUI = factory.makeSingleLinkUI();
            }
        }
        return openableEUI;
    }
    
    public OpenableEUI makeSingleLinkUI() {
        TrialLinkHandler trialLinkHandler = linkGroup.getAbsorbedLinks().get(0);
        GenericLinkPanel lp = new GenericLinkPanel();        
        ProgressImpl progress = makeProgress(lp);
        ChangeDownloadModeAction changeDownloadModeAction = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);
        SimpleNeembuuFileCreator fileCreator = new SimpleNeembuuFileCreator(trialLinkHandler, root);
        
        OpenActionImpl openActionImpl = new OpenActionImpl(realFileProvider, mainComponent);
        
        LinkActionsImpl linkActionsImpl = new LinkActionsImpl(
                lp.closeActionUIA, defaultRemoveFromUI(lp.openableEUI, luic1),
                mainComponent, fileCreator, openActionImpl);
        
        addCallbacks(linkActionsImpl, openActionImpl, lp, progress, changeDownloadModeAction, null);
        
        lp.initActions(new ExpandActionImpl(lp.expandActionUIA,ExpandActionImpl.Mode.SingleLinkType), 
                openActionImpl, linkActionsImpl.getClose(), 
                linkActionsImpl.getDelete(), linkActionsImpl.getReAdd(), 
                linkActionsImpl.getSave(), new ConnectionActionsImpl(progress.progressProvider),
                changeDownloadModeAction);
        
        lp.closeActionUIA.fileNameLabel().setText(trialLinkHandler.tempDisplayName());
        linkActionsImpl.getClose().initializeUIAsClosed();
        return lp.openableEUI;
    }
    
    
    public OpenableEUI makeSplitLinkUI() {
        GenericLinkPanel lp = new GenericLinkPanel();
        ProgressImpl progress = makeProgress(lp);
        ChangeDownloadModeAction changeDownloadModeAction = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);
        SaveAction_forVariants saveAction_forVariants = new SaveAction_forVariants(mainComponent, lp.progressUIA);
        OpenActionImpl openActionImpl = new OpenActionImpl(realFileProvider, mainComponent);
        
        SplitMergeNeembuuFileCreator fileCreator = new SplitMergeNeembuuFileCreator(linkGroup, root);
        
        LinkActionsImpl linkActionsImpl = new LinkActionsImpl(lp.closeActionUIA, 
                defaultRemoveFromUI(lp.openableEUI, luic1), mainComponent, 
                fileCreator, openActionImpl);
        
        addCallbacks(linkActionsImpl, openActionImpl, lp, progress, changeDownloadModeAction, null);
        linkActionsImpl.getReAdd().addCallBack(new VariantProgressProvider(lp.progressUIA,saveAction_forVariants,null,null,true));
                
        lp.initActions(new ExpandActionImpl(lp.expandActionUIA,ExpandActionImpl.Mode.SplitLinkType),
                openActionImpl, linkActionsImpl.getClose(),linkActionsImpl.getDelete(), 
                linkActionsImpl.getReAdd(), saveAction_forVariants, 
                new ConnectionActionsImpl(progress.progressProvider), changeDownloadModeAction);
        
        lp.closeActionUIA.fileNameLabel().setText(linkGroup.tempDisplayName());
        linkActionsImpl.getClose().initializeUIAsClosed();
        
        return lp.openableEUI;
    }
    
    public OpenableEUI makeMultiVariantUI() {
        GenericLinkPanel lp = new GenericLinkPanel();
        SaveAction_forVariants saveForVariants = new SaveAction_forVariants(mainComponent, lp.progressUIA);
        VariantProgressProvider vpp = makeProgressForVariant(lp, saveForVariants);
        ChangeDownloadModeAction changeDownloadModeAction = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);
        MultiVariantOpenAction openAction = new MultiVariantOpenAction(realFileProvider, mainComponent, downloadSpeedProvider);
        
        MultiVariantFileCreator fileCreator = new MultiVariantFileCreator(linkGroup,root);
        
        LinkActionsImpl linkActionsImpl = new LinkActionsImpl(lp.closeActionUIA, 
                defaultRemoveFromUI(lp.openableEUI, luic1), mainComponent, 
                fileCreator,openAction);
        
        addCallbacks(linkActionsImpl, openAction, lp, null, changeDownloadModeAction, vpp);
        linkActionsImpl.getReAdd().addCallBack(vpp);
                
        lp.initActions(new ExpandActionImpl(lp.expandActionUIA,ExpandActionImpl.Mode.MultiVariantType),
                openAction, linkActionsImpl.getClose(), linkActionsImpl.getDelete(), 
                linkActionsImpl.getReAdd(), saveForVariants, 
                new ConnectionActionsImpl(vpp), changeDownloadModeAction);
        
        lp.closeActionUIA.fileNameLabel().setText(linkGroup.tempDisplayName());
        linkActionsImpl.getClose().initializeUIAsClosed();

        return lp.openableEUI;
    }

    private static ProgressImpl makeProgress(GenericLinkPanel lp){
        Graph graph = new GraphImpl(lp.graphUIA);
        ProgressImpl progress =  new ProgressImpl(
                lp.progressUIA,graph,ProgressImpl.Mode.OverallProgressUI);
        lp.init(graph, progress.progressProvider, VariantsImpl.makeDummy());
        return progress;
    }
    
    private static VariantProgressProvider makeProgressForVariant(GenericLinkPanel lp,
            SaveAction_forVariants saveAction4Variants){
        Graph graph = new GraphImpl(lp.graphUIA);
        VariantProgressProvider vpp = new VariantProgressProvider(
                lp.progressUIA,saveAction4Variants,graph,lp.closeActionUIA,false);
        lp.init(graph, vpp, VariantsImpl.makeDummy());
        return vpp;
    }
    
    private static void addCallbacks(LinkActionsImpl linkActionsImpl,
            ReAddAction.CallBack openActionImpl, GenericLinkPanel lp,
            ProgressImpl progress, ChangeDownloadModeAction changeDownloadModeAction,
            ProgressProvider vpi){
        linkActionsImpl.getReAdd().addCallBack(openActionImpl);
        linkActionsImpl.getReAdd().addCallBack(new ReAddActionCallBackImpl(lp.closeActionUIA, 
                vpi==null?progress.progressProvider:vpi, 
                changeDownloadModeAction,true));
    }
    
    private static RemoveFromUI defaultRemoveFromUI(
            final OpenableEUI openableEUI,
            final ExpandableUIContainer luic1){
        return new RemoveFromUI() {
            @Override public void remove() {  luic1.removeUI(openableEUI); } };
    }
}
