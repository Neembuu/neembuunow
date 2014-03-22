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
package neembuu.release1.ui.actions;

import java.util.LinkedList;
import java.util.List;
import neembuu.release1.Main;
import neembuu.release1.api.IndefiniteTask;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkgroup.LinkGrouperResults;
import neembuu.release1.api.linkparser.LinkParserResult;
import neembuu.release1.api.ui.AddLinkUI;
import neembuu.release1.api.ui.IndefiniteTaskUI;
import neembuu.release1.api.ui.ExpandableUIContainer;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.linkpanel.OpenableEUI;
import neembuu.release1.api.ui.access.MinimalistFileSystem;
import neembuu.release1.defaultImpl.linkgroup.LinkGrouperImpl;
import neembuu.release1.defaultImpl.LinkParserImpl;
import neembuu.release1.ui.linkcontainer.LinksContainer;
import neembuu.release1.ui.MainPanel;
import neembuu.release1.ui.NeembuuUI;
import neembuu.release1.ui.linkpanel.GLPFactory;
import neembuu.vfs.progresscontrol.DownloadSpeedProvider;

/**
 *
 * @author Shashank Tulsyan
 */
public class AddLinkAction implements Runnable {
    
    private Main main;
    private final IndefiniteTaskUI indefiniteTaskUI;
    private final ExpandableUIContainer luic1;
    private final MainComponent mainComponent;
    private RealFileProvider realFileProvider;
    private MinimalistFileSystem addRemoveFromFileSystem;
    private final AddLinkUI addLinkUI;
    
    public AddLinkAction(NeembuuUI nui,MainPanel mp) {
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
        for(TrialLinkGroup lg : results.complete_linkPackages()){
            System.out.println(lg);
        }
        System.out.println("+++incomplete+++");
        for(TrialLinkGroup lg : results.incomplete_linkPackages()){
            System.out.println(lg);
        }
        System.out.println("+++unhandlable+++");
        for(TrialLinkHandler trialLinkHandler : results.unhandleAbleLinks()){
            System.out.println("Could not handle the link -> "+trialLinkHandler);
        }
    }
    
    private void createUIFor(LinkGrouperResults results){
        for(TrialLinkGroup linkGroup :  results.complete_linkPackages()){
            
            OpenableEUI openableEUI = GLPFactory.make(
                luic1, mainComponent, realFileProvider, 
                addRemoveFromFileSystem,linkGroup,new DownloadSpeedProvider(){
                    @Override public double getDownloadSpeed_KiBps(){return 256;}});
            
            if(openableEUI==null){return;}
            ((LinksContainer)luic1).addUI(openableEUI, 0);
            if(open){
                openableEUI.open();
            }
        }
    }
    
    private String makeResidualParagraph(LinkGrouperResults grouperResults, LinkParserResult parserResult){
        List<String> links = new LinkedList<String>();

        for(TrialLinkHandler tlh : parserResult.getFailedLinks()){
            links.add(tlh.getReferenceLinkString());
        }
        for (TrialLinkHandler tlh : grouperResults.unhandleAbleLinks()) {
            links.add(tlh.getReferenceLinkString());
        }
        for(TrialLinkGroup lg: grouperResults.incomplete_linkPackages()){
            for (TrialLinkHandler tlh : lg.getFailedLinks()) {
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
    
}
