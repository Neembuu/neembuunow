/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.TrialLinkHandler;
import neembuu.release1.api.linkparser.LinkParserResult;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinkOrganizerImpl {
    
    Logger logger;
    
    List<a> l = new LinkedList<a>();
    
    void organize(LinkParserResult linkParserResult){
        if(linkParserResult.results().isEmpty()){
            return;
        }
        
        for(TrialLinkHandler trialLinkHandler : linkParserResult.results()){
            if(!trialLinkHandler.canHandle()){
                logger.log(Level.SEVERE, "unexpected, cannot handle link "+trialLinkHandler.tempDisplayName());
                // ask user confirmation and
                // delete this package from disk if anything in disk
                continue;
            }
            
            
        }
    }
    
    /*private void handleSingle(trialLinkHandler){
        
    }*/
    
    private static final class a {
        List<a> l;
        Object stuff;
    }
}
