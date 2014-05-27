/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.externalImpl.linkhandler;

/**
 *
 * @author Shashank Tulsyan
 */
public class Generator {
    public static void main(String[] args) throws Exception{        
        GenerateExternalLinkHandlerEntry 
                entry = new GenerateExternalLinkHandlerEntry(
                        DailymotionLinkHandlerProvider.class
                        //YoutubeLinkHandlerProvider.class
                );
        
        
        
        entry.getExternalLinkHandlerEntry();
    }
}
