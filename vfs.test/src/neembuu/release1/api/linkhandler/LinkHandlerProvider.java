/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.linkhandler;

/**
 *
 * @author Shashank Tulsyan
 */
public interface LinkHandlerProvider {
    TrialLinkHandler tryHandling(String url);
    
    LinkHandler getLinkHandler(String url);
}