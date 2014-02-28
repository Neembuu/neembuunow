/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkparser.LinkParserResult;
import org.junit.Test;

/**
 *
 * @author Shashank Tulsyan
 */
public class LinkOrganizerImplTest {
    
    static LinkedList<TrialLinkHandler> makeList_TrialLinkHandler(){
        LinkedList<TrialLinkHandler> trialLinkHandlers = new LinkedList<TrialLinkHandler>();
        
        trialLinkHandlers.add(make("v1.avi"));
        trialLinkHandlers.add(make("v2.avi"));
        
        trialLinkHandlers.add(make("video.avi.002"));
        trialLinkHandlers.add(make("video2.avi.002"));
        trialLinkHandlers.add(make("video.avi.001"));
        trialLinkHandlers.add(make("video2.avi.001"));
        trialLinkHandlers.add(make("video.avi.003"));
        trialLinkHandlers.add(make("video2.avi.003"));
        
        trialLinkHandlers.add(make("video4.avi.002"));
        trialLinkHandlers.add(make("video4.avi.003"));
        trialLinkHandlers.add(make("video4.avi.004"));
        
        
        trialLinkHandlers.add(make("video3.avi.001"));
        trialLinkHandlers.add(make("video3.avi.002"));
        trialLinkHandlers.add(make("video3.avi.004"));
        
        trialLinkHandlers.add(make("Some youtube video").multi(true));
        
        trialLinkHandlers.add(make(null).displayName("http://r1---maa03s08.googlevideo.com/videoplayback?clen=32642783&source=youtube&ms=au&mv=m&ipbits=0&sparams=algorithm%2Cburst%2Cclen%2Cdur%2Cfactor%2Cgir%2Cid%2Cip%2Cipbits%2Citag%2Clmt%2Cpcm2fr%2Csource%2Cupn%2Cexpire&id=68e581d32193acd6&key=yt5&signature=BF91D43D18C8FFFE77AE8CABCDB1B70879EC7132.3C25CEB615C92F13E755122B6EE4A7DC8DB23A7F&gir=yes&algorithm=throttle-factor&mt=1390633122&sver=3&pcm2fr=yes&dur=144.450&upn=lBDN5QkqIVs&fexp=935632%2C935502%2C905615%2C913571%2C900228%2C916612%2C910836%2C936910%2C936913%2C907231&ip=117.198.43.238&itag=136&expire=1390653885&lmt=1390454666306008&burst=40&factor=1.25&ratebypass=yes&cmbypass=yes&ir=1&rr=46"));
        return trialLinkHandlers;
    }
    
    static TrialLinkHandlerDummy make(String name){
        TrialLinkHandlerDummy dummy = new TrialLinkHandlerDummy();
        dummy.displayName = name;
        return dummy;
    }
    
    
    private static final class LinkParserResultDummy implements LinkParserResult {
        List<TrialLinkHandler> results;
        @Override public List<TrialLinkHandler> results() { return results; }
        @Override public List<TrialLinkHandler> getFailedLinks() {return null; }
    };
    
    private static final class TrialLinkHandlerDummy implements TrialLinkHandler{
        boolean canHandle;
        boolean multi = false;
        String displayName, url;
        
        TrialLinkHandlerDummy canHandle(boolean b){canHandle = b; return this;}
        TrialLinkHandlerDummy multi(boolean b){multi = b; return this;}
        TrialLinkHandlerDummy displayName(String n){displayName = n; return this;}
        TrialLinkHandlerDummy url(String u){url = u; return this;}
        
        @Override public boolean canHandle() { return true; }
        @Override public String getErrorMessage() { return null; }
        @Override public boolean containsMultipleLinks() {return multi; }
        @Override public String tempDisplayName() { return displayName; }
        @Override public String getReferenceLinkString() { return url; }
        //@Override public LinkHandlerProvider getLinkHandlerProvider() { return null; }
    }
}
