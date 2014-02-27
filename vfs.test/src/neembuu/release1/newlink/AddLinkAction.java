/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.newlink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import neembuu.release1.Main;
import neembuu.release1.api.IndefiniteTask;
import neembuu.release1.api.ReferenceLink;
import neembuu.release1.api.LinkPackage;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.TrialLinkHandler;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.VirtualFilesParams;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.linkgroup.LinkGrouperResults;
import neembuu.release1.api.linkparser.LinkParserResult;
import neembuu.release1.api.ui.AddLinkUI;
import neembuu.release1.api.ui.IndefiniteTaskUI;
import neembuu.release1.api.ui.ExpandableUIContainer;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.access.AddRemoveFromFileSystem;
import neembuu.release1.defaultImpl.linkgroup.LinkGrouperImpl;
import neembuu.release1.defaultImpl.LinkParserImpl;
import neembuu.release1.defaultImpl.OneToOneVirtualFileProvider;
import neembuu.release1.defaultImpl.SimplyOpenTheVideoFile;
import neembuu.release1.defaultImpl.SplitGroupProcessor;
import neembuu.release1.defaultImpl.single.SingleLinkPackage;
import neembuu.release1.ui.Constraint;
import neembuu.release1.ui.MainPanel;
import neembuu.release1.ui.NeembuuUI;

/**
 *
 * @author Shashank Tulsyan
 */
public class AddLinkAction implements Runnable {
    
    private Main main;
    private final OneToOneVirtualFileProvider svfp
                = new OneToOneVirtualFileProvider();

    private final IndefiniteTaskUI indefiniteTaskUI;
    private final ExpandableUIContainer luic1;
    private final MainComponent mainComponent;
    private RealFileProvider realFileProvider;
    private AddRemoveFromFileSystem addRemoveFromFileSystem;
    private final AddLinkUI addLinkUI;
    
    public AddLinkAction(NeembuuUI nui,MainPanel mp) {
        //this.mp = mp;
        
        addLinkUI = mp.getAddLinkUI();
        
        indefiniteTaskUI = nui.getIndefiniteTaskUI();
        luic1 = nui.getLinksContainer();
        mainComponent = nui.getMainComponent();
        
    }
    
    public void setMain(Main m){
        this.main = m;
        realFileProvider = m.getMountManager().getRealFileProvider();
        addRemoveFromFileSystem = m.getMountManager().getAddRemoveFromFileSystem();
    }
    
    boolean open = false; 
    
    public void open(boolean open){
        this.open = open;
    }
    
    @Override
    public void run() {
        addLinkUI.addLinksPanelEnable(true);
        LinkParserImpl linkParserImpl = new LinkParserImpl(addLinkUI, indefiniteTaskUI, main.getLOGGER());
        
        LinkParserResult linkParserResult = linkParserImpl.process(addLinkUI.getLinksText());

        LinkGrouperResults grouperResults = null;
        
        if(!linkParserResult.results().isEmpty()){
            IndefiniteTask makingFiles = indefiniteTaskUI.showIndefiniteProgress("Making files");
            grouperResults = groupLinks(linkParserResult);
            makingFiles.done();
            
            saveLinks(grouperResults);
            
            createUIFor(grouperResults);
        }
        
    }

    
    private LinkGrouperResults groupLinks(LinkParserResult linkParserResult){
        LinkGrouperImpl linkGrouper = new LinkGrouperImpl();
        
        LinkGrouperResults results = linkGrouper.group(linkParserResult);
        
        addLinkUI.setLinksText(makeResidualParagraph(results,linkParserResult));
        if(addLinkUI.getLinksText()!=null && addLinkUI.getLinksText().length()>0){
            addLinkUI.addLinkProgressSet("There are links which could not be added due to some error");
        }else{
            addLinkUI.addLinksPanelShow(false);
            addLinkUI.addLinkProgressSet("");
        }
        
        return results;
    }
    
    private void saveLinks(LinkGrouperResults results){
        System.out.println("+++Saving feature yet to be implemented+++");
        System.out.println("+++done+++");
        for(LinkGroup lg : results.complete_linkPackages()){
            System.out.println(lg);
        }
        System.out.println("+++incomplete+++");
        for(LinkGroup lg : results.incomplete_linkPackages()){
            System.out.println(lg);
        }
        System.out.println("+++unhandlable+++");
        for(TrialLinkHandler trialLinkHandler : results.unhandleAbleLinks()){
            System.out.println("Could not handle the link -> "+trialLinkHandler);
        }
    }
    
    private void createUIFor(LinkGrouperResults results){
        for(LinkGroup linkGroup :  results.complete_linkPackages()){
            int size = linkGroup.getLinks().size();
            if(size > 1){
                createUIForMultiple(linkGroup);
            }else {
                createUIForSingle(linkGroup);
            }
        }
    }
    
    private void createUIForMultiple(LinkGroup linkGroup){
        
    }
    
    private void createUIForSingle(LinkGroup linkGroup){
        SingleLinkPackage singleLinkPackage = new SingleLinkPackage();
    }
    
    private String makeResidualParagraph(LinkGrouperResults grouperResults, LinkParserResult parserResult){
        List<String> links = new LinkedList<String>();

        for(TrialLinkHandler tlh : parserResult.getFailedLinks()){
            links.add(tlh.getReferenceLinkString());
        }
        for (TrialLinkHandler tlh : grouperResults.unhandleAbleLinks()) {
            links.add(tlh.getReferenceLinkString());
        }
        for(LinkGroup lg: grouperResults.incomplete_linkPackages()){
            for (TrialLinkHandler tlh : lg.getLinks()) {
                links.add(tlh.getReferenceLinkString());
            }
        }
        
        
        String c = ""; int cnt=0;
        for(String l : links){
            if(cnt==0){
                c=l;
            }
            c+="\n"+l; cnt++;
        }
        
        return c;
    }
    
    
    
    private void doLinks_old(List<ReferenceLink> l){
        SplitGroupProcessor splitGroupProcessor = new SplitGroupProcessor();
        SimplyOpenTheVideoFile simplyOpenTheVideoFile = new SimplyOpenTheVideoFile();
                
        List<VirtualFile> virtualFiles = new ArrayList<VirtualFile>();
        List<LinkPackage> linkPackages = new ArrayList<LinkPackage>();
        for(ReferenceLink l1 : l){
            try{
                virtualFiles.add(makeVirtualFile(l1));
            }catch(Exception a){
                JOptionPane.showMessageDialog(mainComponent.getJFrame(), a.getMessage(), "Could not create file for "+l1.getReferenceLinkString(), JOptionPane.ERROR_MESSAGE);
                Main.getLOGGER().log(Level.INFO, "Problem in creating virtual file "+ l1.getReferenceLinkString(), a);
            }
        }
        
        List<VirtualFile> splits = splitGroupProcessor.canHandle(virtualFiles);
        if(splits!=null && splits.size()>0 ){
            if(handleSplits(splitGroupProcessor, splits)){
                
                virtualFiles.removeAll(splits);
            }
            if(this.open){
                splitGroupProcessor.openSuitableFile(splits, main.getMountManager());
            }
        }
        
        initializeSingleUI(virtualFiles);
        
        for (VirtualFile virtualFile : virtualFiles) {
            main.getNui().getLinksContainer().addUI(virtualFile.getUI(),0);
        }
        if(this.open){
            simplyOpenTheVideoFile.openSuitableFile(virtualFiles, main.getMountManager() );
        }
        
        // ConstrainUtility.constrain((DirectoryStream)main.getMountManager().getFileSystem().getRootAttributes()); 
        // this makes each file aware of existence of other filein the same virtual
        // directory. This way if user has added two files but is watching
        // only one, the other files will cease downloading
    }
    
    private boolean handleSplits(SplitGroupProcessor splitGroupProcessor, List<VirtualFile> splits){
        try{
            splitGroupProcessor.handle(splits, main.getMountManager());
        }catch(Exception a){
            Main.getLOGGER().log(Level.INFO, "Could not handle splits", a);
            return false;
        }
        splitGroupProcessor.openSuitableFile(splits, main.getMountManager());

        Collections.sort(splits, new Comparator<VirtualFile>() {
            @Override
            public int compare(VirtualFile o1, VirtualFile o2) {
                return o1.getConnectionFile().getDownloadConstrainHandler().index()
                        - o2.getConnectionFile().getDownloadConstrainHandler().index();
            }
        });
        int i = 0;
        for (VirtualFile virtualFile : splits) {
            if(i<splits.size() -1 ){
                virtualFile.getUI().setContraintComponent(new Constraint());
            }
            main.getNui().getLinksContainer().addUI(virtualFile.getUI(),i);
            i++;
        }
        
        
        
        return true;
    }
    
    private VirtualFile makeVirtualFile(ReferenceLink l)throws Exception{
        // use virtual file providers
        String fileName = l.getLinkHandler().getGroupName();
        
        fileName = main.getMountManager().getSuitableFileName(fileName);
        if(fileName.length()>50){
            fileName = fileName.substring(0,50);
        }
        //SingleFileLinkUI singleFileLinkUI = new SingleFileLinkUI(main.getNui(), main.getMountManager());
        
        VirtualFilesParams vfp = VirtualFilesParams.Builder.create()
                .setDiskManager(main.getDiskManager())
                .setReferenceLink(l)
                .setFileName(fileName)
                .setTroubleHandler(main.getTroubleHandler())
                //.setLinkUI(singleFileLinkUI)
                .build();
        VirtualFile vf = svfp.create(vfp).get(0);
        main.getMountManager().addFile(vf);
        //singleFileLinkUI.init(vf);
        
        return vf;
    }
    
}
