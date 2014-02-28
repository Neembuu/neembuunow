/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl;

import neembuu.release1.defaultImpl.linkgroup.LinkGrouperImpl;
import neembuu.release1.api.linkgroup.LinkGroup;
import java.util.LinkedList;
import java.util.List;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkgroup.LinkGroupMakers;
import neembuu.release1.api.linkgroup.LinkGrouperResults;
import neembuu.release1.api.linkparser.LinkParserResult;
import static neembuu.release1.defaultImpl.LinkOrganizerImplTest.makeList_TrialLinkHandler;
import neembuu.release1.defaultImpl.linkgroup.DefaultLinkGroupMaker;
import neembuu.release1.defaultImpl.linkgroup.SplitsLinkGroupMaker;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinkGrouperTest {
    public static void main(String[] args) {        
        final LinkedList<TrialLinkHandler> trialLinkHandlers = makeList_TrialLinkHandler();
        
        List<LinkGroup> linkPackages = new LinkedList<LinkGroup>();
        
        LinkGrouperImpl grouperImpl = new LinkGrouperImpl();
        LinkGrouperResults results =  grouperImpl.group(new LinkParserResult() {
            @Override public List<TrialLinkHandler> getFailedLinks() { return null; }
            @Override public List<TrialLinkHandler> results() { return trialLinkHandlers; }
        });
        
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
}
