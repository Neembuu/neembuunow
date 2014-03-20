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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkhandler.TrialLinkHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class DefaultLinkGroup implements TrialLinkGroup { 

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
    public List<TrialLinkHandler> getFailedLinks() {
        return Collections.emptyList();
    }

    @Override
    public List<TrialLinkHandler> getAbsorbedLinks() {
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

    @Override
    public String tempDisplayName() {
        return tlh.tempDisplayName();
    }
}
