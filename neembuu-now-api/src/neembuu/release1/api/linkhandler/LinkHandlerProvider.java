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

package neembuu.release1.api.linkhandler;

/**
 *
 * @author Shashank Tulsyan
 */
public interface LinkHandlerProvider {
    TrialLinkHandler tryHandling(String url);
    
    /**
     * @param trialLinkHandler the same that was obtained from {@link #tryHandling(java.lang.String) }.
     * Sometimes TrialLinkHandlers normalize a link. For example youtu.be/?aaaa type url changed to
     * youtube.com/watch?aaaa type.
     * @return null if this handler is not suited to handle this trialLink.
     * Otherwise a working LinkHandler instance is returned.
     * @throws Exception if this LinkHandlerProvider is the correct LinkHandlerProvider, but 
     * due to some problem in Internet connection or something similar, has failed.
     * If this LinkHandlerProvider thinks that this TrialLink should be checked 
     * by some other LinkHandlerProvider is should return null.
     */
    LinkHandler getLinkHandler(TrialLinkHandler trialLinkHandler)throws Exception;
}
