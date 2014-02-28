/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api.linkhandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public static TrialLinkHandler getWhichCanHandleOrDefault(String url){
//        printProviders();
        TrialLinkHandler trialLinkHandler;
        synchronized (providers){
            for(LinkHandlerProvider fnasp : providers){
                trialLinkHandler = fnasp.tryHandling(url);
                if(trialLinkHandler.canHandle()){
                    System.out.println(fnasp.getClass().getSimpleName() + " is handling " + url);
                    LOGGER.log(Level.INFO, "{0} is handling {1}", new Object[]{fnasp.getClass().getSimpleName(), url});
                    return trialLinkHandler;
                }
            }
            
            //Check if the default provider can handle this link
            trialLinkHandler = defaultLinkProvider.tryHandling(url);
            if(trialLinkHandler.canHandle()){
                return trialLinkHandler;
            }
            
            return new FailureTrialLinkHandler(url);
            //return providers.iterator().next().tryHandling(url);
        }
    }
    
    
    public static LinkHandler getHandler(String url){
//        printProviders();
        TrialLinkHandler trialLinkHandler;
        synchronized (providers){
            for(LinkHandlerProvider fnasp : providers){
                trialLinkHandler = fnasp.tryHandling(url);
                if(trialLinkHandler.canHandle()){
                    System.out.println(fnasp.getClass().getSimpleName() + " is handling " + url);
                    LOGGER.log(Level.INFO, "{0} is handling {1}", new Object[]{fnasp.getClass().getSimpleName(), url});
                    return fnasp.getLinkHandler(url);
                }
            }
            
            //Check if the default provider can handle this link
            trialLinkHandler = defaultLinkProvider.tryHandling(url);
            if(trialLinkHandler.canHandle()){
                return defaultLinkProvider.getLinkHandler(url);
            }
            
            return null;
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
    
    private static final class FailureTrialLinkHandler implements TrialLinkHandler {
        private final String url;
        public FailureTrialLinkHandler(String url) { this.url = url; }
        @Override public boolean canHandle() {  return false; }
        @Override public String getErrorMessage() { return "No suitable handler found"; }
        @Override public boolean containsMultipleLinks() { throw new UnsupportedOperationException("Not supported yet."); }
        @Override public String tempDisplayName() { throw new UnsupportedOperationException("Not supported yet.");  }
        @Override public String getReferenceLinkString() { return url;  }
        //@Override public LinkHandlerProvider getLinkHandlerProvider() { throw new UnsupportedOperationException("Not supported yet.");  }        
    }
    
}
