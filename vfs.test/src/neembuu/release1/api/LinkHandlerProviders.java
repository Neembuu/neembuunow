/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.defaultImpl.DirectLinkHandlerProvider;
import neembuu.release1.log.LoggerUtil;

/**
 *
 * @author Shashank Tulsyan
 */
public final class LinkHandlerProviders {
    
    private static final Logger LOGGER = LoggerUtil.getLogger();
    
    private static final Set<LinkHandlerProvider> providers =
        (Set<LinkHandlerProvider>)Collections.newSetFromMap(new /*Weak*/HashMap());
    
    private static LinkHandlerProvider defaultLinkProvider;
    
    public static void registerProvider(LinkHandlerProvider fnasp){
        synchronized (providers){
            // weak reference to allow gcing?
            if(fnasp==null) throw new NullPointerException();
            providers.add(fnasp);
        }
    }
    
    public static void registerDefaultProvider(LinkHandlerProvider fnasp) {
        if(fnasp==null){
            throw new NullPointerException();
        }
        defaultLinkProvider = fnasp;
    }

    public static LinkHandlerProvider getWhichCanHandleOrDefault(String url){
//        printProviders();
        synchronized (providers){
            for(LinkHandlerProvider fnasp : providers){
                if(fnasp.canHandle(url)){
                    System.out.println(fnasp.getClass().getSimpleName() + " is handling " + url);
                    LOGGER.log(Level.INFO, "{0} is handling {1}", new Object[]{fnasp.getClass().getSimpleName(), url});
                    return fnasp;
                }
            }
            
            //Check if the default provider can handle this link
            if(defaultLinkProvider.canHandle(url)){
                return defaultLinkProvider;
            }
            
            return providers.iterator().next();
        }
    }
    
    /**
     * To check the order.
     */
    public static void printProviders(){
        for(int i = 0; i < providers.size(); i++){
            System.out.println(providers.toArray()[i].getClass().getSimpleName());
        }
        
    }
    
}
