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

package neembuu.release1.defaultImpl.linkhandler;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.file.OnlineFile;
import neembuu.release1.api.file.PropertyProvider;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.LinkHandlerProvider;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.defaultImpl.file.BasicOnlineFile;
import neembuu.release1.defaultImpl.file.BasicPropertyProvider;
import neembuu.release1.httpclient.NHttpClient;
import neembuu.release1.httpclient.utils.NHttpClientUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * I'm using the method explained <a href="http://jetcracker.wordpress.com/2013/10/29/vimeo-direct-download-link-java/">here</a>.
 * @author davidepastore
 */
public class VimeoLinkHandlerProvider implements LinkHandlerProvider {
    
    private static final Logger LOGGER = LoggerUtil.getLogger();
    private final DefaultHttpClient httpClient = NHttpClient.getNewInstance();
    
    private String dataConfigUrl;
    private String title;
    
    @Override
    public TrialLinkHandler tryHandling(final String url) {
        return new VIMEO_TLH(url);
    }
    
    @Override
    public LinkHandler getLinkHandler(TrialLinkHandler tlh) throws Exception {
        if( !(tlh instanceof VIMEO_TLH) || !tlh.canHandle()){return null;}
        BasicLinkHandler.Builder linkHandlerBuilder = extraction(tlh);
        return linkHandlerBuilder.build();
    }
    
    /**
     * Find data url.
     * @param vimeoUrl The vimeo url.
     */
    private void findData(String vimeoUrl){
        try {
            //Find data config url
            final String response = NHttpClientUtils.getData(vimeoUrl, httpClient);
            
            Document doc = Jsoup.parse(response);
            
            //Find title
            title = doc.select("meta[property=og:title]").attr("content");
            
            //Find data-config-url
            dataConfigUrl = doc.select("div.player").attr("data-config-url");
            dataConfigUrl = dataConfigUrl.replaceAll("&amp;", "&");
            System.out.println("Dataconfigurl: " + dataConfigUrl);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        String url = tlh.getReferenceLinkString();
        BasicLinkHandler.Builder linkHandlerBuilder = BasicLinkHandler.Builder.create();
        
        try {
            findData(url);
            final String responseString = NHttpClientUtils.getData(dataConfigUrl, httpClient);
            
            JSONObject jSonObject = new JSONObject(responseString);
            //System.out.println(jSonObject);
            
            //Set the group name as the name of the video
            linkHandlerBuilder.setGroupName(title);
            
            long duration = jSonObject.getJSONObject("video").getLong("duration") * 1000;
            long cDuration = duration;
            jSonObject = jSonObject.getJSONObject("request").getJSONObject("files").getJSONObject("h264");
            
            JSONObject hdJsonObject = jSonObject.getJSONObject("hd");
            JSONObject sdJsonObject = jSonObject.getJSONObject("sd");
            
            createFileBuilder(hdJsonObject.getString("url"), title, "HD", duration, linkHandlerBuilder);
            createFileBuilder(sdJsonObject.getString("url"), title, "SD", duration, linkHandlerBuilder);
            final String extension = "mp4";
            
            for(OnlineFile of : linkHandlerBuilder.getFiles()){
                long dur = of.getPropertyProvider().getLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS);
                if(dur < 0 && cDuration > 0 && 
                        of.getPropertyProvider() instanceof BasicPropertyProvider){
                    ((BasicPropertyProvider)of.getPropertyProvider())
                            .putLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS, cDuration);
                }
            }
            
        } catch (Exception ex) {
            int retryLimit = ((VIMEO_TLH)tlh).retryLimit;
            ex.printStackTrace();
            System.out.println("retry no. = "+retryCount);
            if(retryCount > retryLimit) throw ex;
            return extraction(tlh,retryCount+1);
        }

        return linkHandlerBuilder;
    }
    
    
    private void createFileBuilder(String url, String title, String quality, long duration, BasicLinkHandler.Builder linkHandlerBuilder){
        long length = NHttpClientUtils.calculateLength(url, httpClient);

        BasicOnlineFile.Builder fileBuilder = linkHandlerBuilder
                .createFile();

        fileBuilder.setName(title + " " + quality + ".mp4")
            .putLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS, duration)
            .putStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION, quality)
            .setUrl(url)
            .setSize(length).next();
    }
    
    /**
     * Print all the url (debug purpose).
     * @param urls ArrayList<String> with all the urls.
     */
    private void printUrls(ArrayList<String> urls) {
        System.out.println("\n***** START PRINTING YOUTUBE URLS *****");
        for (String url : urls) {
            System.out.println(url);
        }
        System.out.println("***** END PRINTING YOUTUBE URLS *****\n");
    }
    
       
    static final class VIMEO_TLH implements TrialLinkHandler {

        private final String url;
        private int retryLimit = 5;

        public void setRetryLimit(int retryLimit) { this.retryLimit = retryLimit; }

        VIMEO_TLH(String url) { 
            this.url = Utils.getRidOfHttps(url); 
        }

        /**
         * 
         * @param url
         * @return 
         */
        @Override public boolean canHandle() {
            boolean result = url.matches("https?://(vimeo.com/)([0-9]+)");
            LOGGER.log(Level.INFO, "Vimeo can handle this? {0}", result);
            return result;
        }

        @Override public String getErrorMessage() { return canHandle() ? null : "Cannot handle"; }
        @Override public boolean containsMultipleLinks() { return true; }
        @Override public String tempDisplayName() { return url; }
        @Override public String getReferenceLinkString() { return url; }
    };
    
}
