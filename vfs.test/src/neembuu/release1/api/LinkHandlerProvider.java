/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api;

import neembuu.release1.api.LinkHandler;

/**
 *
 * @author Shashank Tulsyan
 */
public interface LinkHandlerProvider {
    boolean canHandle(String url);
    
    LinkHandler getLinkHandler(String url);
    
    
}
