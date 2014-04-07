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

import neembuu.release1.defaultImpl.file.split.SplitMergeNeembuuFileCreator;
import neembuu.release1.defaultImpl.file.SimpleNeembuuFileCreator;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.ui.ExpandableUIContainer;
import neembuu.release1.api.ui.LinkGroupUICreator;
import neembuu.release1.api.ui.linkpanel.Graph;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.linkpanel.OpenableEUI;
import neembuu.release1.api.ui.access.MinimalistFileSystem;
import neembuu.release1.api.ui.access.RemoveFromUI;
import neembuu.release1.api.ui.actions.ChangeDownloadModeAction;
import neembuu.release1.api.ui.actions.CloseAction;
import neembuu.release1.api.ui.actions.ReAddAction;
import neembuu.release1.api.ui.linkpanel.ProgressProvider;
import neembuu.release1.defaultImpl.file.multiVariant.MultiVariantFileCreator;
import neembuu.release1.ui.actions.ChangeDownloadModeActionImpl;
import neembuu.release1.ui.actions.ConnectionActionsImpl;
import neembuu.release1.ui.actions.EditLinksActionImpl;
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
public final class Link_UI_Factory {
    
    private final ExpandableUIContainer luic1;
    private final MainComponent mainComp;
    private final RealFileProvider realFileProvider;
    private final MinimalistFileSystem root;
    private final LinkGroup linkGroup;
    private final DownloadSpeedProvider downloadSpeedProvider;
    private final LinkGroupUICreator linkUIMaker;

    public Link_UI_Factory(ExpandableUIContainer luic1, MainComponent mainComponent, RealFileProvider realFileProvider, MinimalistFileSystem root, LinkGroup linkGroup, DownloadSpeedProvider downloadSpeedProvider,LinkGroupUICreator linkGroupUICreator) {
        this.luic1 = luic1;
        this.mainComp = mainComponent;
        this.realFileProvider = realFileProvider;
        this.root = root;
        this.linkGroup = linkGroup;
        this.downloadSpeedProvider = downloadSpeedProvider;
        this.linkUIMaker = linkGroupUICreator;
    }
    
    public static OpenableEUI make(ExpandableUIContainer luic1, 
            MainComponent mainComponent, RealFileProvider realFileProvider,
            MinimalistFileSystem root,LinkGroup linkGroup, 
            DownloadSpeedProvider downloadSpeedProvider,LinkGroupUICreator linkGroupUICreator){
        int size = linkGroup.getAbsorbedLinks().size();
        if(size==0)return null;
        
        Link_UI_Factory factory = new Link_UI_Factory(luic1, mainComponent, realFileProvider, 
                root, linkGroup, downloadSpeedProvider, linkGroupUICreator);
        
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
        GenericLinkPanel lp = new GenericLinkPanel();        
        ProgressImpl progress = makeProgress(lp);
        ChangeDownloadModeAction changeDownloadMode = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);
        RemoveFromUI remover = defaultRemoveFromUI(lp.openableEUI, luic1);
        
        SimpleNeembuuFileCreator fileCreator = new SimpleNeembuuFileCreator(linkGroup, root);
        
        OpenActionImpl open = new OpenActionImpl(realFileProvider, mainComp);
        LinkActionsImpl actions = new LinkActionsImpl(linkGroup.getSession(),
                lp.closeActionUIA, remover, mainComp, fileCreator, open);
        
        addCallbacks(actions, open, lp, progress, changeDownloadMode, null, true);
        
        lp.initActions(new ExpandActionImpl(lp.expandActionUIA,ExpandActionImpl.Mode.SingleLinkType), 
                open, actions.getClose(), actions.getDelete(), 
                actions.getReAdd(), actions.getSave(), editLinks(remover), 
                new ConnectionActionsImpl(progress.progressProvider),changeDownloadMode);
        
        lp.closeActionUIA.fileNameLabel().setText(linkGroup.displayName());
        actions.getClose().initializeUIAsClosed();
        return lp.openableEUI;
    }
    
    
    public OpenableEUI makeSplitLinkUI() {
        GenericLinkPanel lp = new GenericLinkPanel();
        ProgressImpl progress = makeProgress(lp);
        ChangeDownloadModeAction cdma = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);
        RemoveFromUI remover = defaultRemoveFromUI(lp.openableEUI, luic1);

        SplitMergeNeembuuFileCreator fileCreator = new SplitMergeNeembuuFileCreator(linkGroup, root);
        
        SaveAction_forVariants save = new SaveAction_forVariants(mainComp, lp.progressUIA);
        OpenActionImpl open = new OpenActionImpl(realFileProvider, mainComp);
        LinkActionsImpl actions = new LinkActionsImpl(linkGroup.getSession(),
                lp.closeActionUIA, remover, mainComp, fileCreator, open);
        
        addCallbacks(actions, open, lp, progress, cdma, null, true);
        actions.getReAdd().addCallBack(new VariantProgressProvider(lp.progressUIA,save,null,null,true));
                
        lp.initActions(new ExpandActionImpl(lp.expandActionUIA,ExpandActionImpl.Mode.SplitLinkType),
                open, actions.getClose(),actions.getDelete(), actions.getReAdd(), save, 
                editLinks(remover), new ConnectionActionsImpl(progress.progressProvider), cdma);
        
        lp.closeActionUIA.fileNameLabel().setText(linkGroup.displayName());
        actions.getClose().initializeUIAsClosed();
        
        return lp.openableEUI;
    }
    
    public OpenableEUI makeMultiVariantUI() {
        GenericLinkPanel lp = new GenericLinkPanel();
        SaveAction_forVariants save = new SaveAction_forVariants(mainComp, lp.progressUIA);
        VariantProgressProvider vpp = makeProgressForVariant(lp, save);
        ChangeDownloadModeAction changeDownloadModeAction = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);
        RemoveFromUI remover = defaultRemoveFromUI(lp.openableEUI, luic1);
        
        MultiVariantFileCreator fileCreator = new MultiVariantFileCreator(linkGroup,root);
        
        MultiVariantOpenAction open = new MultiVariantOpenAction(realFileProvider, mainComp, downloadSpeedProvider);
        LinkActionsImpl actions = new LinkActionsImpl(linkGroup.getSession(),
                lp.closeActionUIA, remover,mainComp, fileCreator,open);
        
        addCallbacks(actions, open, lp, null, changeDownloadModeAction, vpp, false);
        actions.getReAdd().addCallBack(vpp);
                
        lp.initActions(new ExpandActionImpl(lp.expandActionUIA,ExpandActionImpl.Mode.MultiVariantType),
                open, actions.getClose(), actions.getDelete(), actions.getReAdd(), save, 
                editLinks(remover), new ConnectionActionsImpl(vpp), changeDownloadModeAction);
        
        lp.closeActionUIA.fileNameLabel().setText(linkGroup.displayName());
        actions.getClose().initializeUIAsClosed();

        return lp.openableEUI;
    }

    private EditLinksActionImpl editLinks(RemoveFromUI remover){
        EditLinksActionImpl elai = new EditLinksActionImpl(linkUIMaker, linkGroup, mainComp, remover);
        return elai;
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
            ProgressProvider vpi, boolean initalizeProgress){
        linkActionsImpl.getReAdd().addCallBack(openActionImpl);
        linkActionsImpl.getReAdd().addCallBack(new ReAddActionCallBackImpl(lp.closeActionUIA, 
                vpi==null?progress.progressProvider:vpi, 
                changeDownloadModeAction,initalizeProgress));
    }
    
    private static RemoveFromUI defaultRemoveFromUI(
            final OpenableEUI openableEUI,
            final ExpandableUIContainer luic1){
        return new RemoveFromUI() {
            @Override public void remove() {  luic1.removeUI(openableEUI); } };
    }
}
