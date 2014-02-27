/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl.linkgroup;

import java.util.List;
import neembuu.release1.api.TrialLinkHandler;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.linkgroup.LinkGroupMakers;
import neembuu.release1.api.linkgroup.LinkGrouperResults;
import neembuu.release1.api.linkparser.LinkParserResult;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinkGrouperImpl {
    static {
        LinkGroupMakers.registerDefaultMaker(new DefaultLinkGroupMaker());
        LinkGroupMakers.registerMaker(new SplitsLinkGroupMaker());
    }
    
    public LinkGrouperResults group(LinkParserResult linkParserResult){
        LinkGrouperResultsImpl results = new LinkGrouperResultsImpl();
                
        Outer_loop:
        for (TrialLinkHandler trialLinkHandler : linkParserResult.results()) {
            System.out.println("handling="+trialLinkHandler.tempDisplayName());
            Inner_loop:
            for (LinkGroup linkPackage : results.complete_linkPackages) {
                if(linkPackage.absorb(trialLinkHandler)){
                    continue Outer_loop;
                }
            }
            LinkGroup res = LinkGroupMakers.tryMaking(trialLinkHandler);
            
            if(res==null){
                results.unhandleAbleLinks.add(trialLinkHandler);
            } else {
                results.complete_linkPackages.add(res);
            }
        }
        
        checkFailure(results,linkParserResult);
        
        return results;
    }
    
    private void checkFailure(LinkGrouperResultsImpl results,LinkParserResult linkParserResult){
        Outer_loop:
        for(LinkGroup lp : results.complete_linkPackages){
            if(!lp.complete()){
                results.incomplete_linkPackages.add(lp);
            }
        }
    }
}
