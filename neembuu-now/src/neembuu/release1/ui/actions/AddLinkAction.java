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

import neembuu.release1.api.ui.LinkGroupUICreator;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import neembuu.release1.api.IndefiniteTask;
import neembuu.release1.api.RealFileProvider;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.linkgroup.LinkGroupMakers;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkgroup.LinkGrouperResults;
import neembuu.release1.api.linkparser.LinkParserResult;
import neembuu.release1.api.settings.Settings;
import neembuu.release1.api.ui.AddLinkUI;
import neembuu.release1.api.ui.IndefiniteTaskUI;
import neembuu.release1.api.ui.ExpandableUIContainer;
import neembuu.release1.api.ui.MainComponent;
import neembuu.release1.api.ui.linkpanel.OpenableEUI;
import neembuu.release1.api.ui.access.MinimalistFileSystem;
import neembuu.release1.defaultImpl.linkgroup.LinkGrouperImpl;
import neembuu.release1.defaultImpl.LinkParserImpl;
import neembuu.release1.ui.linkcontainer.LinksContainer;
import neembuu.release1.ui.linkpanel.Link_UI_Factory;
import neembuu.vfs.progresscontrol.DownloadSpeedProvider;

/**
 *
 * @author Shashank Tulsyan
 */
public class AddLinkAction implements Runnable, LinkGroupUICreator {
    
    private final IndefiniteTaskUI indefiniteTaskUI;
    private final ExpandableUIContainer luic1;
    private final MainComponent mainComponent;
    private RealFileProvider realFileProvider;
    private MinimalistFileSystem minimalistFileSystem;
    private final AddLinkUI addLinkUI;
    private final Settings settings;
    
    public AddLinkAction(IndefiniteTaskUI indefiniteTaskUI,
            ExpandableUIContainer luic1,
            MainComponent mainComponent,
            AddLinkUI addLinkUI,Settings settings) {
        this.addLinkUI = addLinkUI;
        this.settings = settings;
        this.indefiniteTaskUI = indefiniteTaskUI;
        this.luic1 = luic1;
        this.mainComponent = mainComponent;
        
    }
    
    public void initialize(RealFileProvider realFileProvider, MinimalistFileSystem minimalistFileSystem){
        this.realFileProvider = realFileProvider;
        this.minimalistFileSystem = minimalistFileSystem;
    }
    
    boolean open = false; 
    
    public void open(boolean open){
        this.open = open;
    }
    
    @Override
    public void run() {
        final String linkParagraph = addLinkUI.getLinksText();
        addLinkUI.setLinksText(null);//links would be re-entered in a short while
        // if there are any failures. But before reshowing the UI
        // old links must be removed, to prevent the operation being repeated
        // if user pressed (+) twice
        addLinkUI.addLinksPanelEnable(true);
        LinkParserImpl linkParserImpl = new LinkParserImpl(indefiniteTaskUI);
        
        LinkParserResult linkParserResult = linkParserImpl.process(linkParagraph);

        LinkGrouperResults grouperResults = null;
        
        if(!linkParserResult.results().isEmpty()){
            IndefiniteTask makingFiles = indefiniteTaskUI.showIndefiniteProgress("Making files");
            grouperResults = groupLinks(linkParserResult);
            makingFiles.done();
            
            List<LinkGroup> sessions = saveLinks(grouperResults,linkParserResult);
            printState(grouperResults);
            
            createUIFor(sessions,this.open);
        }
        
    }

    private void residualLinks(LinkGrouperResults results,
            LinkParserResult linkParserResult, List<TrialLinkGroup> failedSessionInit){
        addLinkUI.setLinksText(makeResidualParagraph(results,linkParserResult,failedSessionInit));
        if(addLinkUI.getLinksText()!=null && addLinkUI.getLinksText().length()>0){
            addLinkUI.addLinkProgressSet("There are links which could not be added due to some error");
        }else{
            addLinkUI.addLinksPanelShow(false);
            addLinkUI.addLinkProgressSet("");
        }
    }
    
    private LinkGrouperResults groupLinks(LinkParserResult linkParserResult){
        LinkGrouperImpl linkGrouper = new LinkGrouperImpl();
        LinkGrouperResults results = linkGrouper.group(linkParserResult);
        return results;
    }
    
    private List<LinkGroup> saveLinks(LinkGrouperResults results,LinkParserResult linkParserResult){
        List<LinkGroup> lgs = new ArrayList<>();
        List<TrialLinkGroup> failed = new ArrayList<>();
        for (TrialLinkGroup tlg : results.complete_linkPackages()) {
            try{
                LinkGroup lg = LinkGroupMakers.make(tlg);
                if(lg==null){throw new NullPointerException();}
                lgs.add(lg);
            }catch(Exception a){
                a.printStackTrace();
                failed.add(tlg);
            }
        }
        
        
        residualLinks(results,linkParserResult,failed);
        
        return lgs;
    }
    
    private void printState(LinkGrouperResults results){
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
    
    @Override
    //@ThreadSafe
    public void createUIFor(List<LinkGroup> sessions,boolean shouldOpen){
        for(LinkGroup linkGroup :  sessions){
            
            OpenableEUI openableEUI = Link_UI_Factory.make(
                luic1, mainComponent, realFileProvider, 
                minimalistFileSystem,linkGroup,new DownloadSpeedProvider(){
                    @Override public double getDownloadSpeed_KiBps(){return 56;}},
                this,settings,indefiniteTaskUI);
            
            if(openableEUI==null){return;}
            ((LinksContainer)luic1).addUI(openableEUI, 0);
            if(shouldOpen){
                openableEUI.open();
            }
        }
    }
    
    private String makeResidualParagraph(LinkGrouperResults grouperResults, 
            LinkParserResult parserResult,List<TrialLinkGroup> failedSessionInit){
        List<String> links = new LinkedList<String>();

        for(String line : parserResult.getFailedLines() ){
            links.add(line);
        }
        
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
        for (TrialLinkGroup lg: failedSessionInit) {
            for (TrialLinkHandler tlh : lg.getAbsorbedLinks()) {
                links.add(tlh.getReferenceLinkString());
            }
        }
        
        links = removeDuplicatesFromResidualPara(links);
        
        String c = ""; int cnt=0;
        for(String l : links){
            if(cnt==0){
                c=l;
            }else {
                c+="\n"+l; 
            }cnt++;
        }
        
        return c;
    }
    
    private List<String> removeDuplicatesFromResidualPara(List<String> links){
        List<String> links_non_dup = new LinkedList<>();
        for (String link : links) {
            boolean found = false;
            for (String link_non_dup : links_non_dup) {
                if(link_non_dup.trim().equals(link.trim())){
                    found = true;
                }
            }
            if(!found){
                links_non_dup.add(link);
            }
        }
        return links_non_dup;
    }
    
}
