/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    private final AddLinkUI addLinkUI;
    private final IndefiniteTaskUI indefiniteTaskUI;
    private final Logger logger;

    public LinkParserImpl(AddLinkUI addLinkUI, IndefiniteTaskUI indefiniteTaskUI, Logger logger) {
        this.addLinkUI = addLinkUI;
        this.indefiniteTaskUI = indefiniteTaskUI;
        this.logger = logger;
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
    
    private void makeListFromText(String para,LinkParserResultImpl res){
        String linksText = addLinkUI.getLinksText();
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
