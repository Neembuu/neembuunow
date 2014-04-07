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

package neembuu.release1.defaultImpl;

import java.util.LinkedList;
import java.util.List;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkparser.LinkParserResult;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinkParserResultImpl implements LinkParserResult{
    
    final LinkedList<String> failedLines = new LinkedList<String>();
    
    final LinkedList<TrialLinkHandler> result = new LinkedList<TrialLinkHandler>();
    
    final LinkedList<TrialLinkHandler> failedLinks = new LinkedList<TrialLinkHandler>();
    
    String[] lnks;
    String c = null;

    LinkParserResultImpl() {
    }

    @Override
    public List<TrialLinkHandler> results() {
        return result;
    }

    @Override
    public List<TrialLinkHandler> getFailedLinks() {
        return failedLinks;
    }

    @Override
    public List<String> getFailedLines() {
        return failedLines;
    }
    
}
