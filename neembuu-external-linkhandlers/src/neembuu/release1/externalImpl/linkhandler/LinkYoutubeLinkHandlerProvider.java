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

import davidepastore.StringUtils;
import java.net.URLEncoder;
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
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Youtube link handler provider based on 
 * @author davidepastore
 */
@ELHProvider(checkingRegex = YoutubeLinkHandlerProvider.REG_EXP)
public class LinkYoutubeLinkHandlerProvider implements LinkHandlerProvider {
    
    private static final Logger LOGGER = LoggerUtil.getLogger(LinkYoutubeLinkHandlerProvider.class.getName()); // all logs go into an html file
    
    @Override
    public TrialLinkHandler tryHandling(final String url) {
        return new YoutubeLinkHandlerProvider.YT_TLH(url);
    }
    
    @Override
    public LinkHandler getLinkHandler(TrialLinkHandler tlh) throws Exception {
        if( !(tlh instanceof YoutubeLinkHandlerProvider.YT_TLH) || !tlh.canHandle()){return null;}
        BasicLinkHandler.Builder linkHandlerBuilder = linkYoutubeExtraction(tlh);
        return linkHandlerBuilder.build();
    }
    
    /**
     * Use http://www.linkyoutube.com service to get the urls.
     * @param url the youtube url.
     * @return A BasicLinkHandler.Builder with all the urls found for this video.
     */
    private BasicLinkHandler.Builder linkYoutubeExtraction(TrialLinkHandler tlh)throws Exception{
        return linkYoutubeExtraction(tlh, 0);
    }
    
    
    private BasicLinkHandler.Builder linkYoutubeExtraction(TrialLinkHandler tlh, int retryCount)throws Exception{
        String url = tlh.getReferenceLinkString();
        BasicLinkHandler.Builder linkHandlerBuilder = BasicLinkHandler.Builder.create();
        
        try {
            DefaultHttpClient httpClient = NHttpClient.getNewInstance();
            String requestUrl = "http://www.linkyoutube.com/watch/index.php?video=" + URLEncoder.encode(url, "UTF-8");
            
            final String responseString = NHttpClientUtils.getData(requestUrl, httpClient);
            
            //Set the group name as the name of the video
            String nameOfVideo = getVideoName(url);
            
            String fileName = "text";
            
            linkHandlerBuilder.setGroupName(nameOfVideo);
            
            long c_duration = -1;
            
            Document doc = Jsoup.parse(responseString);
            
            Elements elements = doc.select("#download_links a");
            
            for (Element element : elements) {
                String singleUrl = element.attr("href");
                fileName = element.text();
                if(!singleUrl.equals("#")){
                    
                    long length = NHttpClientUtils.calculateLength(singleUrl, httpClient);
                    singleUrl = Utils.normalize(singleUrl);
                    LOGGER.log(Level.INFO,"Normalized URL: " + singleUrl);


                    if(length==0){
                        length = NHttpClientUtils.calculateLength(singleUrl,httpClient);
                    }
                    
                    //LOGGER.log(Level.INFO,"Length: " + length);
                
                    if(length <= 0){ continue; /*skip this url*/ }

                    BasicOnlineFile.Builder fileBuilder = linkHandlerBuilder
                            .createFile();

                    try{ // finding video/audio length
                        String dur = StringUtils.stringBetweenTwoStrings(singleUrl, "dur=", "&");
                        long duration = (int)(Double.parseDouble(dur)*1000);
                        if(c_duration < 0 ){ c_duration = duration; }
                        fileBuilder.putLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS, duration);
                        LOGGER.log(Level.INFO,"dur="+dur);
                    }catch(NumberFormatException a){
                        // ignore
                    }

                    try{ // finding the quality short name
                        String type = fileName.substring(fileName.indexOf("(")+1);
                        type = type.substring(0, type.indexOf(")"));
                        fileBuilder.putStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION, type);
                        LOGGER.log(Level.INFO,"type="+type);
                    }catch(Exception a){
                        a.printStackTrace();
                    }

                    fileName = nameOfVideo + " " +fileName;

                    fileBuilder.setName(fileName)
                        .setUrl(singleUrl)
                        .setSize(length).next();
                    }
            }
            
            for(OnlineFile of : linkHandlerBuilder.getFiles()){
                long dur = of.getPropertyProvider().getLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS);
                if(dur < 0 && c_duration > 0 && 
                        of.getPropertyProvider() instanceof BasicPropertyProvider){
                    ((BasicPropertyProvider)of.getPropertyProvider())
                            .putLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS,c_duration);
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return linkYoutubeExtraction(tlh);
        }

        return linkHandlerBuilder;
    }
    
    /**
     * Returns the name of the video.
     * @param url The url of the video.
     * @return Returns the title of the video.
     */
    private String getVideoName(String url) throws Exception{
        final String responseString = NHttpClientUtils.getData(url, NHttpClient.getNewInstance());
        Document doc = Jsoup.parse(responseString);
        return doc.select("meta[name=title]").attr("content");
    }
    
}
