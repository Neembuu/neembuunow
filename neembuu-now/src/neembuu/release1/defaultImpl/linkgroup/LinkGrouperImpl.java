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

package neembuu.release1.defaultImpl.linkgroup;

import java.util.List;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkgroup.LinkGroupMakers;
import neembuu.release1.api.linkgroup.LinkGrouperResults;
import neembuu.release1.api.linkparser.LinkParserResult;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinkGrouperImpl {
    
    public LinkGrouperResults group(LinkParserResult linkParserResult){
        LinkGrouperResults results = group(linkParserResult.results());
        return results;
    }
    
    public LinkGrouperResults group(List<TrialLinkHandler> linkParserResult_results){
        LinkGrouperResultsImpl results = new LinkGrouperResultsImpl();
                
        Outer_loop:
        for (TrialLinkHandler trialLinkHandler : linkParserResult_results) {
            System.out.println("handling="+trialLinkHandler.tempDisplayName());
            Inner_loop:
            for (TrialLinkGroup linkPackage : results.complete_linkPackages) {
                if(linkPackage.absorb(trialLinkHandler)){
                    continue Outer_loop;
                }
            }
            TrialLinkGroup res = LinkGroupMakers.tryMaking(trialLinkHandler);
            
            if(res==null){
                results.unhandleAbleLinks.add(trialLinkHandler);
            } else {
                results.complete_linkPackages.add(res);
            }
        }
        checkFailure(results);
        return results;
    }
    
    private void checkFailure(LinkGrouperResultsImpl results){
        Outer_loop:
        for(TrialLinkGroup lp : results.complete_linkPackages){
            if(!lp.complete()){
                results.incomplete_linkPackages.add(lp);
            }
        }
    }
}
