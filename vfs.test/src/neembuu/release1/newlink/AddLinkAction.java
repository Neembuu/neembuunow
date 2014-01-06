/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.newlink;

import java.net.MalformedURLException;
import java.net.URL;
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
import neembuu.release1.defaultImpl.SimpleVirtualFileProvider;
import neembuu.release1.ui.LinkPanel;
import neembuu.release1.ui.MainPanel;

/**
 *
 * @author Shashank Tulsyan
 */
public class AddLinkAction implements Runnable {
    
    private final MainPanel mp;
    private final SimpleVirtualFileProvider svfp
                = new SimpleVirtualFileProvider();
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
                        mp.showIndefiniteProgress(true, "Only http links allowed.");
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
        
        for(Link l1 : l){
            try{
                doLink(l1);
            }catch(Exception a){
                JOptionPane.showMessageDialog(mp, a.getMessage(), "Could not create file for "+l1.getLink(), JOptionPane.ERROR_MESSAGE);
                Main.getLOGGER().log(Level.INFO, "Problem in creating virtual file "+ l1.getLink(), a);
            }
        }
        mp.showIndefiniteProgress(false, "");
    }
    
    private void doLink(Link l)throws Exception{
        // use virtual file providers
        String fileName = l.getLinkHandler().getGroupName();
        
        fileName = main.getMountManager().getSuitableFileName(fileName);
        
        VirtualFilesParams vfp = VirtualFilesParams.Builder.create()
                .setDiskManager(main.getDiskManager())
                .setLink(l)
                .setFileName(fileName)
                .setLinkHandler(l.getLinkHandler())
                .setTroubleHandler(main.getTroubleHandler())
                .build();
        VirtualFile vf = svfp.create(vfp).get(0);
        main.getMountManager().addFile(vf);
        LinkPanel lp = new LinkPanel();
        lp.setFile(vf,main);
        
        main.getNui().getLinksContainer().addLinkPanel(lp);
        
        
        
        
        if(open){
            lp.openVirtualFile();
        }
    }
}
