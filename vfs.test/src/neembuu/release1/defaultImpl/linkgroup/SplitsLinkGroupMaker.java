/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl.linkgroup;

import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.linkgroup.LinkGroupMaker;
import neembuu.release1.defaultImpl.linkgroup.SplitsLinkGroup.SplitPart;

/**
 *
 * @author Shashank Tulsyan
 */
public class SplitsLinkGroupMaker implements LinkGroupMaker{

    @Override
    public LinkGroup tryMaking(TrialLinkHandler tlh) {
        return tryHandle(tlh);
    }
    
    static SplitsLinkGroup tryHandle(TrialLinkHandler tlh){
        SplitsLinkGroup result = new SplitsLinkGroup();
        if( tlh.containsMultipleLinks() ){
            return null;
        }
        String n = tlh.tempDisplayName();
        try {
            SplitPart sp = new SplitPart();
            result.name = n.substring(0,n.length() - 4);
            sp.splitName = n;
            sp.index = Integer.parseInt(n.substring(n.length() - 3));
            result.splitParts.add(sp);
            sp.th = tlh;
        } catch (Exception a) {
            //a.printStackTrace(System.out);
            return null;
        }
        return result;
    }
    
}
