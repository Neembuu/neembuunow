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

package neembuu.release1.defaultImpl.external;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.LinkHandlerProvider;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.log.LoggerUtil;

/**
 *
 * @author Shashank Tulsyan
 */
public class ExternalLinkHandlersProvider implements LinkHandlerProvider{
    
    private final ArrayList<LinkHandlersProviderWrapper> l = new ArrayList<>();
    
    private static final Logger L = LoggerUtil.getLogger(ExternalLinkHandlersProvider.class.getName());

    @Override public TrialLinkHandler tryHandling(String url) {
        for (LinkHandlersProviderWrapper lhpw : l) {
            if(lhpw.canHandle(url)){
                return lhpw.singleton().tryHandling(url);
            }
        }
        
        return null;
    }

    @Override public LinkHandler getLinkHandler(TrialLinkHandler trialLinkHandler) throws Exception {
        for (LinkHandlersProviderWrapper lhpw : l) {
            LinkHandler lh=null;
            try{
                lh = lhpw.singleton().getLinkHandler(trialLinkHandler);
            }catch(Exception a){
                L.log(Level.INFO,"Error in getting link handler",a);
            }
            if(lh!=null){
                return lh;
            }
        }return null;
    }
    
}
