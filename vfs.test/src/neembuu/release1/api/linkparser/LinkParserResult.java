/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.linkparser;

import java.util.List;
import neembuu.release1.api.TrialLinkHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public interface LinkParserResult {       
    List<TrialLinkHandler> getFailedLinks();
    
    List<TrialLinkHandler> results();
}
