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
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComboBox;
import jpfm.VolumeVisibility;
import jpfm.fs.SimpleReadOnlyFileSystem;
import jpfm.mount.Mount;
import jpfm.mount.MountFlags;
import jpfm.mount.MountParams;
import jpfm.mount.MountParamsBuilder;
import jpfm.mount.Mounts;
import jpfm.volume.CommonFileAttributesProvider;
import jpfm.volume.vector.VectorRootDirectory;
import neembuu.diskmanager.DiskManager;
import neembuu.diskmanager.DiskManagerParams;
import neembuu.diskmanager.DiskManagers;
import neembuu.release1.UnprofessionalTroubleHandler;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.linkgroup.LinkGrouperResults;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkparser.LinkParserResult;
import neembuu.release1.api.ui.linkpanel.Graph;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.linkpanel.OpenableEUI;
import neembuu.release1.api.ui.access.MinimalistFileSystem;
import neembuu.release1.api.ui.access.RemoveFromUI;
import neembuu.release1.api.ui.actions.ChangeDownloadModeAction;
import neembuu.release1.api.ui.actions.ConnectionActions;
import neembuu.release1.api.ui.actions.ExpandAction;
import neembuu.release1.api.ui.actions.OpenAction;
import neembuu.release1.api.ui.actions.VariantSelectionAction;
import neembuu.release1.api.ui.linkpanel.VariantSelector;
import neembuu.release1.api.ui.linkpanel.VariantSelector.Selectable;
import neembuu.release1.defaultImpl.DummyMultiVariantTrialLinkHandler;
import neembuu.release1.defaultImpl.LinkOrganizerImplTest;
import neembuu.release1.defaultImpl.file.SimpleNeembuuFileCreator;
import neembuu.release1.defaultImpl.linkgroup.LinkGrouperImpl;
import neembuu.release1.defaultImpl.multiVariant.MultiVariantFileCreator;
import neembuu.release1.defaultImpl.splitImpl.SplitMergeNeembuuFileCreator;
import neembuu.release1.mountmanager.AddRemoveFromFileSystem_Root;
import neembuu.release1.ui.MainPanel;
import neembuu.release1.ui.actions.ChangeDownloadModeActionImpl;
import neembuu.release1.ui.actions.ConnectionActionsImpl;
import neembuu.release1.ui.actions.ExpandActionImpl;
import neembuu.release1.ui.actions.LinkActionsImpl;
import neembuu.release1.ui.actions.MultiVariantOpenAction;
import neembuu.release1.ui.actions.OpenActionImpl;
import neembuu.release1.ui.actions.ReAddActionCallBackImpl;
import neembuu.release1.ui.actions.SaveAction_forVariants;
import neembuu.release1.ui.linkcontainer.LinksContainer;
import neembuu.vfs.progresscontrol.DownloadSpeedProvider;

/**
 *
 * @author Shashank Tulsyan
 */
public class TestGenericLinkPanel {
    private final MainPanel mp;
    private final LinksContainer luic1;
    private final MainComponent mainComponent;
    
    private final String fn = "test120k.rmvb", srcSplit = "J:\\neembuu\\realfiles\\test120k.rmvb";

    public TestGenericLinkPanel(MainPanel mp, LinksContainer lc, MainComponent mainComponent) {
        this.mp = mp;
        this.luic1 = lc;
        this.mainComponent = mainComponent;
    }
    
    
    public OpenableEUI singleLink(){
        final GenericLinkPanel lp = new GenericLinkPanel();
        final OpenableEUI openableEUI = lp.openableEUI;
        
        Graph graph = new GraphImpl(lp.graphUIA);
        ProgressImpl progress =  new ProgressImpl(lp.progressUIA,graph,ProgressImpl.Mode.OverallProgressUI);
        
        lp.init(graph, progress.progressProvider, VariantsImpl.makeDummy());
        
        ConnectionActions connectionActions = new ConnectionActionsImpl(progress.progressProvider);
        ChangeDownloadModeAction changeDownloadModeAction = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);

        final RemoveFromUI removeFromUI = new RemoveFromUI() {
            @Override public void remove() {  luic1.removeUI(openableEUI); } };
        
        final LinkActionsImpl linkActionsImpl;
        
        LinkOrganizerImplTest.TrialLinkHandlerDummy  trialLinkHandlerDummy =
                LinkOrganizerImplTest.make("XYZ EP - 09, When Aliens came to planet earth.rmvb");
        
        SimpleNeembuuFileCreator fileCreator = new SimpleNeembuuFileCreator(
                trialLinkHandlerDummy, null);
        OpenAction openAction = new OpenAction() {
            @Override public void actionPerformed() {
                if(Math.random()>0.5d){
                    lp.closeActionUIA.openButton().setCaption("420p");
                }else {
                    lp.closeActionUIA.openButton().setCaption(null);
                }}};
        linkActionsImpl = new LinkActionsImpl(
                lp.closeActionUIA, removeFromUI, mainComponent, fileCreator, openAction);
        
        linkActionsImpl.getReAdd().addCallBack(new ReAddActionCallBackImpl(
                lp.closeActionUIA, progress.progressProvider,changeDownloadModeAction,true));
        
        ExpandAction expandAction = new ExpandActionImpl(lp.expandActionUIA,ExpandActionImpl.Mode.MultiVariantType);
        
        lp.initActions(expandAction, openAction, linkActionsImpl.getClose(), 
                linkActionsImpl.getDelete(), linkActionsImpl.getReAdd(), 
                linkActionsImpl.getSave(), connectionActions, changeDownloadModeAction);
        
        lp.closeActionUIA.fileNameLabel().setText(trialLinkHandlerDummy.tempDisplayName());
        
        progress.progressProvider.progress().init(new FakeFileBeingDownloaded());
        
        return openableEUI;
    }
    
    
    public OpenableEUI splitLink(){
        GenericLinkPanel lp = new GenericLinkPanel();
        final OpenableEUI openableEUI = lp.openableEUI;
        
        Graph graph = new GraphImpl(lp.graphUIA);
        ProgressImpl progress =  new ProgressImpl(lp.progressUIA,graph,ProgressImpl.Mode.OverallProgressUI);

        lp.init(graph, progress.progressProvider,VariantsImpl.makeDummy());
        
        ConnectionActions connectionActions = new ConnectionActionsImpl(progress.progressProvider);
        ChangeDownloadModeAction changeDownloadModeAction = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);

        final RemoveFromUI removeFromUI = new RemoveFromUI() {
            @Override public void remove() {  luic1.removeUI(openableEUI); } };
        
        final LinkActionsImpl linkActionsImpl;
        
        final ExpandAction expandAction = new ExpandActionImpl(lp.expandActionUIA,ExpandActionImpl.Mode.SplitLinkType);
        SplitMergeNeembuuFileCreator fileCreator = createSplitTypeForTest();
        
        SaveAction_forVariants saveAction_forVariants = new SaveAction_forVariants(mainComponent, lp.progressUIA);
        OpenActionImpl openActionImpl = new OpenActionImpl(realFileProviderForSplit, mainComponent);
        linkActionsImpl = new LinkActionsImpl(
                lp.closeActionUIA, removeFromUI, mainComponent, fileCreator, openActionImpl);
        
        linkActionsImpl.getReAdd().addCallBack(openActionImpl);
        linkActionsImpl.getReAdd().addCallBack(new ReAddActionCallBackImpl(
                lp.closeActionUIA, progress.progressProvider, changeDownloadModeAction,true));
        linkActionsImpl.getReAdd().addCallBack(new VariantProgressProvider(lp.progressUIA,saveAction_forVariants,null,null,true));
                
        lp.initActions(expandAction, openActionImpl, linkActionsImpl.getClose(), 
                linkActionsImpl.getDelete(), linkActionsImpl.getReAdd(), 
                saveAction_forVariants/*plugging in customization : D*/, 
                connectionActions, changeDownloadModeAction);
        
        lp.closeActionUIA.fileNameLabel().setText("<Test merge link>");
        //linkActionsImpl.getClose().actionPerformed(null);// replicating user close (X) pressed action
        linkActionsImpl.getReAdd().actionPerformed(true);// replicating a user click on (+) re-add button
        
        return openableEUI;
    }
    
    
    public OpenableEUI multiVariantTypeLink(){
        GenericLinkPanel lp = new GenericLinkPanel();
        final OpenableEUI openableEUI = lp.openableEUI;
        
        Graph graph = new GraphImpl(lp.graphUIA);

        SaveAction_forVariants saveAction_forVariants = new SaveAction_forVariants(mainComponent, lp.progressUIA);
        VariantProgressProvider vpi = 
            new VariantProgressProvider(lp.progressUIA,saveAction_forVariants,graph,lp.closeActionUIA,false);
                
        lp.init(graph, vpi, VariantsImpl.makeDummy());
        
        ConnectionActions connectionActions = new ConnectionActionsImpl(vpi);
        ChangeDownloadModeAction changeDownloadModeAction = new ChangeDownloadModeActionImpl(lp.changeDownloadModeUIA);

        final RemoveFromUI removeFromUI = new RemoveFromUI() {
            @Override public void remove() {  luic1.removeUI(openableEUI); } };
        
        final LinkActionsImpl linkActionsImpl;
        
        final ExpandAction expandAction = new ExpandActionImpl(lp.expandActionUIA,ExpandActionImpl.Mode.MultiVariantType);
        
        MultiVariantFileCreator fileCreator = createMultiVariantTypeForTest();
        
        MultiVariantOpenAction openAction = new MultiVariantOpenAction(realFileProviderMultiVariant, mainComponent, 
            new DownloadSpeedProvider(){@Override public double getDownloadSpeed_KiBps(){return 256;}});

        linkActionsImpl = new LinkActionsImpl(
                lp.closeActionUIA, removeFromUI, mainComponent, fileCreator,openAction);
        
        linkActionsImpl.getReAdd().addCallBack(openAction);
        linkActionsImpl.getReAdd().addCallBack(new ReAddActionCallBackImpl(
                lp.closeActionUIA, vpi, changeDownloadModeAction,false));
        linkActionsImpl.getReAdd().addCallBack(vpi);
                
        lp.initActions(expandAction, openAction, linkActionsImpl.getClose(), 
                linkActionsImpl.getDelete(), linkActionsImpl.getReAdd(), 
                saveAction_forVariants/*plugging in customization : D*/, 
                connectionActions, changeDownloadModeAction);
        
        lp.closeActionUIA.fileNameLabel().setText("<Test merge link>");
        //linkActionsImpl.getClose().actionPerformed(null);// replicating user close (X) pressed action
        linkActionsImpl.getReAdd().actionPerformed(true);// replicating a user click on (+) re-add button

        return openableEUI;
    }
    
    final RealFileProvider realFileProviderMultiVariant = new RealFileProvider() {
        @Override  public File getRealFile(String... x) {
            String pth = mountLocation;
            for (String stringPath : x) {
                pth+=(File.separatorChar+stringPath);
            }return new File(pth);
        } };
    
    
    final RealFileProvider realFileProviderForSplit = new RealFileProvider() {
        @Override  public File getRealFile(String... x) {
            // the name of the virtual folder is comming same as the resultant merge
            return new File(mountLocation+"\\test120k.rmvb\\test120k.rmvb"); 
        } };
    
    private MultiVariantFileCreator createMultiVariantTypeForTest(){

        final LinkedList<TrialLinkHandler> tlh = new LinkedList<TrialLinkHandler>();
        tlh.add(new DummyMultiVariantTrialLinkHandler());
        
        LinkGrouperImpl grouperImpl = new LinkGrouperImpl();
        LinkGrouperResults results =  grouperImpl.group(new LinkParserResult() {
            @Override public List<TrialLinkHandler> getFailedLinks() { return null; }
            @Override public List<TrialLinkHandler> results() { return tlh; }
        });

        return new MultiVariantFileCreator(results.complete_linkPackages().get(0), arffs);
    }
    
    MinimalistFileSystem arffs = createFSforTest();
    
    private SplitMergeNeembuuFileCreator createSplitTypeForTest(){
         
        TrialLinkGroup trialLinkGroup = createSplitLinksForTest();        
    
        SplitMergeNeembuuFileCreator fileCreator = new SplitMergeNeembuuFileCreator(
                trialLinkGroup, arffs);
        return fileCreator;
    }
    
    private TrialLinkGroup createSplitLinksForTest(){
        final LinkedList<TrialLinkHandler> tlh = new LinkedList<TrialLinkHandler>();
        tlh.add(LinkOrganizerImplTest.make(fn+".001").fakeSpeedTarget_inKiBps(50).fileSource(srcSplit+".001"));
        tlh.add(LinkOrganizerImplTest.make(fn+".002").fakeSpeedTarget_inKiBps(50).fileSource(srcSplit+".002"));
        tlh.add(LinkOrganizerImplTest.make(fn+".003").fakeSpeedTarget_inKiBps(50).fileSource(srcSplit+".003"));
        tlh.add(LinkOrganizerImplTest.make(fn+".004").fakeSpeedTarget_inKiBps(50).fileSource(srcSplit+".004"));
        
        LinkGrouperImpl grouperImpl = new LinkGrouperImpl();
        LinkGrouperResults results =  grouperImpl.group(new LinkParserResult() {
            @Override public List<TrialLinkHandler> getFailedLinks() { return null; }
            @Override public List<TrialLinkHandler> results() { return tlh; }
        });
        
        return results.complete_linkPackages().get(0);
    }
    
    private final String mountLocation = "J:\\neembuu\\virtual\\mountloc";
    
    private MinimalistFileSystem createFSforTest(){
        
        UnprofessionalTroubleHandler troubleHandler = new UnprofessionalTroubleHandler(mainComponent,null);

        String basePath = null;
        try{
            basePath = System.getProperty("java.io.tmpdir")+File.separator+"neembuu-release1";
            System.err.println("BasePath="+basePath);
            Files.createDirectory(Paths.get(basePath));
        }catch(FileAlreadyExistsException faee){
            System.out.println("Temp location already exists");
        }catch(IOException a){ throw new RuntimeException(a); }
        
        DiskManager diskManager = DiskManagers.getDefaultManager(new DiskManagerParams.Builder()
            .setBaseStoragePath(basePath)
            .build()
        );
        
        VectorRootDirectory volume = new VectorRootDirectory(10, 3,CommonFileAttributesProvider.DEFAULT);
        SimpleReadOnlyFileSystem fs = new SimpleReadOnlyFileSystem(volume);
        try{
            Mount mount = Mounts.mount(new MountParamsBuilder()
                    //.set(MountParams.ParamType.LISTENER, this)
                    .set(MountParams.ParamType.MOUNT_LOCATION, mountLocation)//.toString())
                    .set(MountParams.ParamType.FILE_SYSTEM, fs)
                    .set(MountParams.ParamType.EXIT_ON_UNMOUNT, false)
                    .set(MountParams.ParamType.VOLUME_VISIBILITY, VolumeVisibility.GLOBAL)
                    .set(MountParams.ParamType.MOUNT_FLAGS, new MountFlags.Builder().build())
                    .build());
        }catch(Exception a){
            a.printStackTrace(); System.exit(-1);
        }
        
        return new AddRemoveFromFileSystem_Root(volume, troubleHandler, diskManager, realFileProviderForSplit, fs);
    }
    
}
