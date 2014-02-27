/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.linkgroup;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import neembuu.release1.api.TrialLinkHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public final class LinkGroupMakers {
    private static final Set<LinkGroupMaker> makers =
        (Set<LinkGroupMaker>)Collections.newSetFromMap(new /*Weak*/HashMap());
    
    private static LinkGroupMaker defaultLinkGroupMaker;
    
    public static void registerMaker(LinkGroupMaker maker){
        synchronized (makers){
            // weak reference to allow gcing?
            if(maker==null) throw new NullPointerException();
            makers.add(maker);
        }
    }
    
    public static void registerDefaultMaker(LinkGroupMaker maker) {
        if(maker==null){
            throw new NullPointerException();
        }
        if(defaultLinkGroupMaker!=null){
            throw new IllegalStateException("Default already registered = "+defaultLinkGroupMaker);
        }
        defaultLinkGroupMaker = maker;
    }

    public static LinkGroup tryMaking(TrialLinkHandler tlh){
        LinkGroup linkGroup = null;
        synchronized (makers){
            for(LinkGroupMaker maker : makers){
                linkGroup =  maker.tryMaking(tlh);
                if(linkGroup!=null){ 
                    return linkGroup;
                }
            }
        }return defaultLinkGroupMaker.tryMaking(tlh);
    }
    
    
}
