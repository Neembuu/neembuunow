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

import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkgroup.LinkGroupMaker;
import neembuu.release1.defaultImpl.linkgroup.SplitsLinkGroup.SplitPart;

/**
 *
 * @author Shashank Tulsyan
 */
public class SplitsLinkGroupMaker implements LinkGroupMaker{

    @Override
    public TrialLinkGroup tryMaking(TrialLinkHandler tlh) {
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
