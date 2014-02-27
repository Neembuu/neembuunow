/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl.linkgroup;

import java.util.LinkedList;
import java.util.List;
import neembuu.release1.api.TrialLinkHandler;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.linkgroup.LinkGrouperResults;
import neembuu.release1.api.linkparser.LinkParserResult;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinkGrouperResultsImpl implements LinkGrouperResults{
    final List<LinkGroup> incomplete_linkPackages = new LinkedList<LinkGroup>();
    final List<LinkGroup> complete_linkPackages = new LinkedList<LinkGroup>();
    final List<TrialLinkHandler> unhandleAbleLinks = new LinkedList<TrialLinkHandler>();
    
    @Override
    public List<LinkGroup> incomplete_linkPackages() {
        return incomplete_linkPackages;
    }

    @Override
    public List<LinkGroup> complete_linkPackages() {
        return complete_linkPackages;
    }

    @Override
    public List<TrialLinkHandler> unhandleAbleLinks() {
        return unhandleAbleLinks;
    }

}
