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

import java.util.LinkedList;
import java.util.List;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkgroup.TrialLinkGroup;
import neembuu.release1.api.linkgroup.LinkGrouperResults;
import neembuu.release1.api.linkparser.LinkParserResult;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinkGrouperResultsImpl implements LinkGrouperResults{
    final List<TrialLinkGroup> incomplete_linkPackages = new LinkedList<TrialLinkGroup>();
    final List<TrialLinkGroup> complete_linkPackages = new LinkedList<TrialLinkGroup>();
    final List<TrialLinkHandler> unhandleAbleLinks = new LinkedList<TrialLinkHandler>();
    
    @Override
    public List<TrialLinkGroup> incomplete_linkPackages() {
        return incomplete_linkPackages;
    }

    @Override
    public List<TrialLinkGroup> complete_linkPackages() {
        return complete_linkPackages;
    }

    @Override
    public List<TrialLinkHandler> unhandleAbleLinks() {
        return unhandleAbleLinks;
    }

}
