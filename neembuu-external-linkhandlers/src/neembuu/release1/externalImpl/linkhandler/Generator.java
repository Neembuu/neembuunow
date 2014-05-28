/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.externalImpl.linkhandler;

import neembuu.release1.defaultImpl.external.ExternalLinkHandlerEntry;
import org.json.JSONObject;

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
        
        
        ExternalLinkHandlerEntry elhe = entry.getExternalLinkHandlerEntry();
        JSONObject jsono = new JSONObject(elhe);
        System.out.println(jsono);
    }
}
