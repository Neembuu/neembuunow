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

import neembuu.diskmanager.DiskManager;
import neembuu.diskmanager.Session;
import neembuu.release1.api.linkhandler.TrialLinkHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public interface LinkGroupMaker {
    TrialLinkGroup tryMaking(TrialLinkHandler tlh);
    
    LinkGroup make(TrialLinkGroup tlg,DiskManager dm)throws Exception;
    
    LinkGroup restore(Session s)throws Exception;
    
    LinkGroup modify(Session s,TrialLinkGroup tlg, DiskManager dm)throws Exception;
    
}
