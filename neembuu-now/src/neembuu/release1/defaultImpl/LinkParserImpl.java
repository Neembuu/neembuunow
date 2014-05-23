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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.IndefiniteTask;
import neembuu.release1.api.linkhandler.LinkHandlerProviders;
import neembuu.release1.api.linkparser.LinkParserResult;
import neembuu.release1.api.ui.AddLinkUI;
import neembuu.release1.api.ui.IndefiniteTaskUI;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinkParserImpl {
    private final IndefiniteTaskUI indefiniteTaskUI;
    private static final Logger logger = Logger.getLogger(LinkParserImpl.class.getName());

    public LinkParserImpl(IndefiniteTaskUI indefiniteTaskUI) {
        this.indefiniteTaskUI = indefiniteTaskUI;
    }
    
    public LinkParserResult process(String para){
        LinkParserResultImpl res = new LinkParserResultImpl();
        
        IndefiniteTask analyzingLinks_task = indefiniteTaskUI.showIndefiniteProgress("Analyzing links");
        IndefiniteTask lastErrorMessage = null;
        try{
            makeListFromText(para,res);
            lastErrorMessage =  iterateOverEachLink(res,lastErrorMessage);
        }catch(Exception a){
            logger.log(Level.INFO, "Problem in adding link", a);
        }
        if(lastErrorMessage!=null){
            lastErrorMessage.done();
        }
        analyzingLinks_task.done();
        
        return res;
    }
    
    private void makeListFromText(String linksText,LinkParserResultImpl res){
        logger.info("Splitting links\n" + linksText);

        res.lnks = linksText.split("\n");

        LinkedList<String> a = new LinkedList<String>();
        for (int i = 0; i < res.lnks.length; i++) {
            res.lnks[i] = res.lnks[i].trim();
            if(res.lnks[i].length()==0){
                continue;
            }
            a.add(res.lnks[i]);
        }
        res.lnks = a.toArray(new String[a.size()]);
    }
    
    private IndefiniteTask iterateOverEachLink(LinkParserResultImpl res,IndefiniteTask lastErrorMessage){
        for (String lnk : res.lnks) {
            logger.info("handling link=" + lnk);
            try {
                URL url = new URL(lnk);
                TrialLinkHandler trialLinkHandler = LinkHandlerProviders.getWhichCanHandleOrDefault(lnk);
                if (!trialLinkHandler.canHandle()) {
                    if (lastErrorMessage != null) {
                        lastErrorMessage.done();
                    }
                    lastErrorMessage = indefiniteTaskUI.showIndefiniteProgress("Could not use a link");
                    logger.log(Level.INFO, "Failure for " + lnk + " reason " + trialLinkHandler.getErrorMessage());
                    res.failedLinks.add(trialLinkHandler);
                } else {
                    res.result.add(trialLinkHandler);
                }
            } catch (MalformedURLException any) {
                res.failedLines.add(lnk);
                if (lastErrorMessage != null) {
                    lastErrorMessage.done();
                }
                lastErrorMessage = indefiniteTaskUI.showIndefiniteProgress("Format of a link is incorrect");
                logger.log(Level.INFO, "Problem in adding link " + lnk, any);
            }
        }
        return lastErrorMessage;
    }
}
