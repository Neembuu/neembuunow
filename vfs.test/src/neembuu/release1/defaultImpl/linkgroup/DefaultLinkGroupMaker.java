/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl.linkgroup;

import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkgroup.LinkGroup;
import neembuu.release1.api.linkgroup.LinkGroupMaker;

/**
 *
 * @author Shashank Tulsyan
 */
public class DefaultLinkGroupMaker implements LinkGroupMaker {
    @Override
    public LinkGroup tryMaking(TrialLinkHandler tlh){
        DefaultLinkGroup result = new DefaultLinkGroup(tlh);
        return result;
    }
}
