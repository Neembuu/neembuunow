/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl.linkgroup;

import java.util.List;
import neembuu.diskmanager.Session;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.linkhandler.TrialLinkHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public final class BasicLinkGroup implements LinkGroup{
    private final List<TrialLinkHandler> absorbedLinks;
    private final String displayName;
    private final Session s;

    public BasicLinkGroup(List<TrialLinkHandler> getAbsorbedLinks, String displayName, Session s) {
        this.absorbedLinks = getAbsorbedLinks;
        this.displayName = displayName;
        this.s = s;
    }
    

    @Override public List<TrialLinkHandler> getAbsorbedLinks() {
        return absorbedLinks;
    }

    @Override public String displayName() {
        return displayName;
    }

    @Override public Session getSession() {
        return s;
    }
    
}
