/*
 * Copyright (C) 2014 davidepastore
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.release1.externalImpl.linkhandler;

import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.file.OnlineFile;
import neembuu.release1.api.file.PropertyProvider;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.LinkHandlerProvider;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.defaultImpl.external.ELHProvider;
import neembuu.release1.defaultImpl.file.BasicOnlineFile;
import neembuu.release1.defaultImpl.file.BasicPropertyProvider;
import neembuu.release1.defaultImpl.linkhandler.BasicLinkHandler;
import neembuu.release1.defaultImpl.linkhandler.Utils;
import neembuu.release1.httpclient.NHttpClient;
import neembuu.release1.httpclient.utils.NHttpClientUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * I'm using the method explained <a href="https://github.com/rg3/youtube-dl/blob/master/youtube_dl/extractor/dailymotion.py">here</a>.
 * @author davidepastore
 */
@ELHProvider( checkingRegex = DailymotionLinkHandlerProvider.REG_EXP,isBroken = true)
public class DailymotionLinkHandlerProvider implements LinkHandlerProvider {
    
    private static final Logger LOGGER = LoggerUtil.getLogger(DailymotionLinkHandlerProvider.class.getName());
    private final DefaultHttpClient httpClient = NHttpClient.getNewInstance();
    private HttpContext httpContext;
    
    static final String REG_EXP = "https?://(www\\.)?dailymotion\\.com/(embed/)?video/([a-z0-9\\-_]+)|swf(/video)?/[a-zA-Z0-9]+";
    
    private String title;
    
    //Video constants
    private static final String STREAM_H264_URL = "stream_h264_url";
    private static final String STREAM_H264_LD_URL = "stream_h264_ld_url";
    private static final String STREAM_H264_HQ_URL = "stream_h264_hq_url";
    private static final String STREAM_H264_HD_URL = "stream_h264_hd_url";
    private static final String STREAM_H264_HD1080_URL = "stream_h264_hd1080_url";
    
    @Override
    public TrialLinkHandler tryHandling(final String url) {
        return new DM_TLH(url);
    }
    
    @Override
    public LinkHandler getLinkHandler(TrialLinkHandler tlh) throws Exception {
        if( !(tlh instanceof DM_TLH) || !tlh.canHandle()){return null;}
        BasicLinkHandler.Builder linkHandlerBuilder = extraction(tlh);
        return linkHandlerBuilder.build();
    }
    
    /**
     * Set the cookies to allow to watch more videos and to force to use english.
     */
    private void setCookies(){
        httpContext = new BasicHttpContext();
        CookieStore cookieStore = new BasicCookieStore();
        
        //Add the cookies value
        cookieStore.addCookie(new BasicClientCookie("family_filter", "off"));
        cookieStore.addCookie(new BasicClientCookie("ff", "off"));
        cookieStore.addCookie(new BasicClientCookie("lang", "en_US"));
        
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        
    }
    
    /**
     * Find JSON.
     * @param dailyMotionUrl The DailyMotion url.
     */
    private String findJson(String dailyMotionUrl){
        String response = "";
        try {
            final String id = dailyMotionUrl.replaceAll(REG_EXP, "$3");
        
            //System.out.println("The id of the video is: " + id);

            response = NHttpClientUtils.getData("http://www.dailymotion.com/embed/video/" + id, httpClient);
            String regExp = "(?m)var info = \\{(.*?)\\},$";

            if(true)throw new UnsupportedOperationException("Please do not use appwork stuff here."
                    + " Having dependency on libraries which are highly unstable will make us cry in future.");
            //probably make a regex utility here in neembuu
            //and we shall share that utility in NU and NN
            //response = new org.appwork.utils.Regex(response, regExp).getMatch(0);

            response = "{" + response + "}";
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
    
    /**
     * Extract the urls.
     * @param url the vimeo url.
     * @return An ArrayList<String> with all the urls found for this video.
     */
    private BasicLinkHandler.Builder extraction(TrialLinkHandler tlh)throws Exception{
        return extraction(tlh, 0);
    }
    
    private BasicLinkHandler.Builder extraction(TrialLinkHandler tlh, int retryCount)throws Exception{
        BasicLinkHandler.Builder linkHandlerBuilder = BasicLinkHandler.Builder.create();
        setCookies();
        try {
            String jSonString = findJson(tlh.getReferenceLinkString());
            
            JSONObject jSonObject = new JSONObject(jSonString);
            //System.out.println("JSON Object: " + jSonObject);

            title = jSonObject.getString("title");
            long duration = jSonObject.getLong("duration") * 1000;
            long cDuration = duration;
            
            //Set the group name as the name of the video
            linkHandlerBuilder.setGroupName(title);
            
            //Add all the videos for the link handler builder
            if(!jSonObject.isNull(STREAM_H264_LD_URL)){
                createFileBuilder(jSonObject.getString(STREAM_H264_LD_URL), title, "LD", duration, linkHandlerBuilder);
            }
            
            if(!jSonObject.isNull(STREAM_H264_URL)){
                createFileBuilder(jSonObject.getString(STREAM_H264_URL), title, "SD", duration, linkHandlerBuilder);
            }
            
            if(!jSonObject.isNull(STREAM_H264_HQ_URL)){
                createFileBuilder(jSonObject.getString(STREAM_H264_HQ_URL), title, "HQ", duration, linkHandlerBuilder);
            }
            
            if(!jSonObject.isNull(STREAM_H264_HD_URL)){
                createFileBuilder(jSonObject.getString(STREAM_H264_HD_URL), title, "HD", duration, linkHandlerBuilder);
            }
            
            if(!jSonObject.isNull(STREAM_H264_HD1080_URL)){
                createFileBuilder(jSonObject.getString(STREAM_H264_HD1080_URL), title, "HD", duration, linkHandlerBuilder);
            }
            
            for(OnlineFile of : linkHandlerBuilder.getFiles()){
                long dur = of.getPropertyProvider().getLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS);
                if(dur < 0 && cDuration > 0 && 
                        of.getPropertyProvider() instanceof BasicPropertyProvider){
                    ((BasicPropertyProvider)of.getPropertyProvider())
                            .putLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS, cDuration);
                }
            }
            
            
        } catch (JSONException ex) {
            int retryLimit = ((DM_TLH)tlh).retryLimit;
            ex.printStackTrace();
            System.out.println("retry no. = "+retryCount);
            if(retryCount > retryLimit) throw ex;
            return extraction(tlh,retryCount+1);
        }
        
        return linkHandlerBuilder;
    }
    
    
    private void createFileBuilder(String url, String title, String quality, long duration, BasicLinkHandler.Builder linkHandlerBuilder){
        long length;
        try {
            length = NHttpClientUtils.calculateLength(url, httpClient);
        } catch (Exception e) {
            return;
        }
        

        BasicOnlineFile.Builder fileBuilder = linkHandlerBuilder
                .createFile();

        fileBuilder.setName(title + " " + quality + ".mp4")
            .putLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS, duration)
            .putStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION, quality)
            .setUrl(url)
            .setSize(length).next();
    }
    
       
    static final class DM_TLH implements TrialLinkHandler {

        private final String url;
        private int retryLimit = 5;

        public void setRetryLimit(int retryLimit) { this.retryLimit = retryLimit; }

        DM_TLH(String url) { 
            this.url = Utils.normalize(url);
        }

        /**
         * 
         * @param url
         * @return 
         */
        @Override public boolean canHandle() {
            boolean result = url.matches(REG_EXP);
            LOGGER.log(Level.INFO, "DailyMotion can handle this? {0}", result);
            return result;
        }

        @Override public String getErrorMessage() { return canHandle() ? null : "Cannot handle"; }
        @Override public boolean containsMultipleLinks() { return true; }
        @Override public String tempDisplayName() { return url; }
        @Override public String getReferenceLinkString() { return url; }
    };
    
}
