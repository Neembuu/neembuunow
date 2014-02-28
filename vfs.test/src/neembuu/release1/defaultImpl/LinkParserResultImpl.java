/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
