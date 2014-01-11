/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.newlink;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import neembuu.release1.Main;
import neembuu.release1.api.LinkHandler;
import neembuu.release1.api.Link;
import neembuu.release1.api.LinkHandlerProvider;
import neembuu.release1.api.LinkHandlerProviders;
import neembuu.release1.api.VirtualFile;
import neembuu.release1.api.VirtualFilesParams;
import neembuu.release1.defaultImpl.OneToOneVirtualFileProvider;
import neembuu.release1.defaultImpl.SimplyOpenTheVideoFile;
import neembuu.release1.defaultImpl.SplitGroupProcessor;
import neembuu.release1.ui.Constraint;
import neembuu.release1.ui.MainPanel;
import neembuu.release1.ui.SingleFileLinkUI;

/**
 *
 * @author Shashank Tulsyan
 */
public class AddLinkAction implements Runnable {
    
    private final MainPanel mp;
    private final OneToOneVirtualFileProvider svfp
                = new OneToOneVirtualFileProvider();
    private Main main;
    
    public AddLinkAction(MainPanel mp) {
        this.mp = mp;
    }
    
    public void setMain(Main m){
        this.main = m;
    }
    
    boolean open = false; 
    
    public void open(boolean open){
        this.open = open;
    }
    
    @Override
    public void run() {
        String[] lnks = {};
        final LinkedList<Link> result = new LinkedList<Link>();
        try{
            
            String linksText = mp.getLinksText();
            mp.showIndefiniteProgress(true, "Analyzing links");
            Main.getLOGGER().info("Splitting links\n" + linksText);

            lnks = linksText.split("\n");
            
            LinkedList<String> a = new LinkedList<String>();
            for (int i = 0; i < lnks.length; i++) {
                lnks[i] = lnks[i].trim();
                if(lnks[i].length()==0){
                    continue;
                }
                a.add(lnks[i]);
            }
            lnks = a.toArray(new String[a.size()]);

            for (int i = 0; i < lnks.length; i++) {
                Main.getLOGGER().info("link=" + lnks[i]);
                try {
                    URL url = new URL(lnks[i]);
                    if (url.getProtocol().equals("http") || url.getProtocol().equals("https")) {
                        LinkHandlerProvider fnasp = 
                            LinkHandlerProviders.getWhichCanHandleOrDefault(lnks[i]);
                        if(fnasp==null){
                            mp.showIndefiniteProgress(true, "Could not use a link");
                            Main.getLOGGER().log(Level.INFO, "Could not find handler for"+lnks[i]);
                        }else {
                            LinkHandler fnas = fnasp.getLinkHandler(lnks[i]);
                            Main.getLOGGER().log(Level.INFO,"Added="+fnas.getGroupName()
                                    +" "+fnas.getGroupSize()+" l="+lnks[i]);
                            if(!fnas.foundSize()){
                                Main.getLOGGER().log(Level.INFO, "Could not find link size "+lnks[i]);
                            }else{
                                result.add(new LinksImpl(fnas, lnks[i]));
                                lnks[i] = null;
                            }
                        }
                    } else {
                        mp.showIndefiniteProgress(true, "Only http/https links allowed.");
                    }
                } catch (MalformedURLException any) {
                    mp.showIndefiniteProgress(true, "Format of a link is incorrect");
                    Main.getLOGGER().log(Level.INFO, "Problem in adding link "+lnks[i], any);
                }
            }
            
        }catch(Exception a){
            Main.getLOGGER().log(Level.INFO, "Problem in adding link", a);
        }
        
        mp.showIndefiniteProgress(false, "");
        mp.addLinksPanelEnable(true);
        
        
        String c = "";
        c = lnks[0]; int cnt = 0 ;
        for (int i = 1; i < lnks.length; i++) {
            if(lnks[i]!=null){
                c+="\n"+lnks[i];
                cnt++;
            }
        }
        mp.setLinksText(c);
        if(cnt==0){
            mp.addLinksPanelShow(false);
            mp.addLinkProgressSet("");
        }else{
            mp.addLinkProgressSet("There are links which could not be added due to some error");
        }
        
        if(!result.isEmpty()){
            mp.showIndefiniteProgress(true, "Making files");
            doLinks(result);
        }
    }

    
    
    private void doLinks(List<Link> l){
        SplitGroupProcessor splitGroupProcessor = new SplitGroupProcessor();
        SimplyOpenTheVideoFile simplyOpenTheVideoFile = new SimplyOpenTheVideoFile();
                
        List<VirtualFile> virtualFiles = new ArrayList<VirtualFile>();
        for(Link l1 : l){
            try{
                virtualFiles.add(makeVirtualFile(l1));
            }catch(Exception a){
                JOptionPane.showMessageDialog(mp, a.getMessage(), "Could not create file for "+l1.getLink(), JOptionPane.ERROR_MESSAGE);
                Main.getLOGGER().log(Level.INFO, "Problem in creating virtual file "+ l1.getLink(), a);
            }
        }
        
        List<VirtualFile> splits = splitGroupProcessor.canHandle(virtualFiles);
        if(splits!=null && splits.size()>0 ){
            if(handleSplits(splitGroupProcessor, splits)){
                virtualFiles.removeAll(splits);
            }
            
            splitGroupProcessor.openSuitableFile(splits, main.getMountManager());
        }
        for (VirtualFile virtualFile : virtualFiles) {
            main.getNui().getLinksContainer().addLinkUI(virtualFile.getVirtualFilesParams().getLinkUI(),0);
        }
        
        simplyOpenTheVideoFile.openSuitableFile(virtualFiles, main.getMountManager() );
        
        // ConstrainUtility.constrain((DirectoryStream)main.getMountManager().getFileSystem().getRootAttributes()); 
        // this makes each file aware of existence of other filein the same virtual
        // directory. This way if user has added two files but is watching
        // only one, the other files will cease downloading
        mp.showIndefiniteProgress(false, "");
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
                virtualFile.getVirtualFilesParams().getLinkUI().setContraintComponent(new Constraint());
            }
            main.getNui().getLinksContainer().addLinkUI(virtualFile.getVirtualFilesParams().getLinkUI(),i);
            i++;
        }
        
        
        
        return true;
    }
    
    private VirtualFile makeVirtualFile(Link l)throws Exception{
        // use virtual file providers
        String fileName = l.getLinkHandler().getGroupName();
        
        fileName = main.getMountManager().getSuitableFileName(fileName);
        SingleFileLinkUI singleFileLinkUI = new SingleFileLinkUI(main.getNui(), main.getMountManager());
        
        VirtualFilesParams vfp = VirtualFilesParams.Builder.create()
                .setDiskManager(main.getDiskManager())
                .setLink(l)
                .setFileName(fileName)
                .setLinkHandler(l.getLinkHandler())
                .setTroubleHandler(main.getTroubleHandler())
                .setLinkUI(singleFileLinkUI)
                .build();
        VirtualFile vf = svfp.create(vfp).get(0);
        main.getMountManager().addFile(vf);
        singleFileLinkUI.init(vf);
        
        return vf;
    }
}
