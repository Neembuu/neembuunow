/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Shashank Tulsyan
 */
public final class LinkHandlerProviders {
    
    private static final Set<LinkHandlerProvider> providers =
        (Set<LinkHandlerProvider>)Collections.newSetFromMap(new /*Weak*/HashMap());    
    
    public static void registerProvider(LinkHandlerProvider fnasp){
        synchronized (providers){
            // weak reference to allow gcing?
            if(fnasp==null) throw new NullPointerException();
            providers.add(fnasp);
        }
    }

    public static LinkHandlerProvider getWhichCanHandleOrDefault(String url){
        synchronized (providers){
            for(LinkHandlerProvider fnasp : providers){
                if(fnasp.canHandle(url)){
                    return fnasp;
                }
            }
            return providers.iterator().next();
        }
    }
    
}
