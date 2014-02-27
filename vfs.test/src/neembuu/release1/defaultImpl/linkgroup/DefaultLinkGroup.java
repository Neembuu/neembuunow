/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl.linkgroup;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.TrialLinkHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class DefaultLinkGroup implements LinkGroup { 

    @Override
    public boolean absorb(TrialLinkHandler other) {
        return false;
    }
    
    @Override
    public String toString() {
        String ret = tlh.tempDisplayName() +"{\n";
        ret += "\t"+tlh.tempDisplayName()+"\n";
        ret+= "}\n";
        return ret;

    }

    @Override
    public boolean complete() {
        return true;
    }

    @Override
    public List<TrialLinkHandler> getLinks() {
        return Collections.singletonList(tlh);
    }
    
    private final TrialLinkHandler tlh;

    DefaultLinkGroup(TrialLinkHandler tlh) {
        this.tlh = tlh;
    }
    
    static DefaultLinkGroup tryHandle(TrialLinkHandler tlh){
        DefaultLinkGroup result = new DefaultLinkGroup(tlh);
        return result;
    }

    
}
