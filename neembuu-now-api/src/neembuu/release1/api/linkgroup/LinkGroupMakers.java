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

package neembuu.release1.api.linkgroup;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import neembuu.release1.api.linkhandler.TrialLinkHandler;

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

    public static TrialLinkGroup tryMaking(TrialLinkHandler tlh){
        TrialLinkGroup linkGroup = null;
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
