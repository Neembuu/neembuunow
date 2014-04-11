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

package neembuu.release1.defaultImpl;

import neembuu.release1.defaultImpl.linkgroup.LinkGrouperImpl;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import java.util.LinkedList;
import java.util.List;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkgroup.LinkGrouperResults;
import neembuu.release1.api.linkparser.LinkParserResult;
import static neembuu.release1.defaultImpl.LinkOrganizerImplTest.makeList_TrialLinkHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinkGrouperTest {
    public static void main(String[] args) {        
        final LinkedList<TrialLinkHandler> trialLinkHandlers = makeList_TrialLinkHandler();
        
        List<TrialLinkGroup> linkPackages = new LinkedList<TrialLinkGroup>();
        
        LinkGrouperImpl grouperImpl = new LinkGrouperImpl();
        LinkGrouperResults results =  grouperImpl.group(new LinkParserResult() {
            @Override public List<TrialLinkHandler> getFailedLinks() { return null; }
            @Override public List<TrialLinkHandler> results() { return trialLinkHandlers; }
            @Override public List<String> getFailedLines() { return null; }
        });
        
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
}
