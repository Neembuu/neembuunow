/*
 *  Copyright (C) 2014 Davide Pastore
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package neembuu.release1.defaultImpl.linkhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.StringUtils;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.LinkHandlerProvider;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.httpclient.NHttpClient;
import neembuu.release1.log.LoggerUtil;
import neembuu.release1.httpclient.utils.NHttpClientUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openide.util.Exceptions;

/**
 *
 * @author davidepastore
 */
public class YoutubeLinkHandlerProvider implements LinkHandlerProvider {
    private static final Logger LOGGER = LoggerUtil.getLogger();
    
    @Override
    public TrialLinkHandler tryHandling(final String url) {
        return new YT_TLH(url);
    }

    @Override
    public LinkHandler getLinkHandler(TrialLinkHandler tlh) throws Exception {
        if( !(tlh instanceof YT_TLH) || !tlh.canHandle()){return null;}
        BasicLinkHandler.Builder linkHandlerBuilder = clipConverterExtraction(tlh);
        return linkHandlerBuilder.build();
    }
    
    /**
     * Grab the title.
     * @param text 
     * @param url
     */
    private void grabTitle(String text, String url) {
        
        String grabbedTitle; /*= text.replaceFirst("(.*)<meta name=\"title\" content=", "").trim();
        // change html characters to their UTF8 counterpart
        grabbedTitle = (grabbedTitle);
        grabbedTitle = grabbedTitle.replaceFirst("^\"", "").replaceFirst("\">$", "");

        // http://msdn.microsoft.com/en-us/library/windows/desktop/aa365247%28v=vs.85%29.aspx
        // 
        grabbedTitle = grabbedTitle.replaceAll("<", "");
        grabbedTitle = grabbedTitle.replaceAll(">", "");
        grabbedTitle = grabbedTitle.replaceAll(":", "");
        grabbedTitle = grabbedTitle.replaceAll("/", " ");
        grabbedTitle = grabbedTitle.replaceAll("\\\\", " ");
        grabbedTitle = grabbedTitle.replaceAll("|", "");
        grabbedTitle = grabbedTitle.replaceAll("\\?", "");
        grabbedTitle = grabbedTitle.replaceAll("\\*", "");
        grabbedTitle = grabbedTitle.replaceAll("/", " ");
        grabbedTitle = grabbedTitle.replaceAll("\"", " ");
        grabbedTitle = grabbedTitle.replaceAll("%", "");
        */
        grabbedTitle = StringUtils.stringBetweenTwoStrings(text, "<title>", " - YouTube");
        
        String contentType = NHttpClientUtils.getContentType(url);
        
        if(contentType.equals("video/webm")){
            grabbedTitle += ".webm";
        }
        
        
        System.out.println("Title: " + grabbedTitle);

        throw new IllegalStateException("Legacy code");
        //this.filename = grabbedTitle; // complete file name without path 
    }
    
    
    /**
     * Find text data.
     * @param text the text.
     * @return a list of the urls.
     */
    private ArrayList<String> findTextData(String text){
        ArrayList<String> finalUrls = new ArrayList<String>();
        try {
            String encodedUrl = StringUtils.stringBetweenTwoStrings(text, "\"url_encoded_fmt_stream_map\": \"", "\"");
            LOGGER.log(Level.INFO, "encoded url: {0}", encodedUrl);
            System.out.println("encoded url: " + encodedUrl);
            
            encodedUrl = encodedUrl.replaceFirst("\".*", "");
            
            encodedUrl = encodedUrl.replaceFirst("\".*", "");
            encodedUrl = encodedUrl.replace("%25","%");
            encodedUrl = encodedUrl.replace("\\u0026", "&");
            encodedUrl = encodedUrl.replace("\\", "");
            String[] urls = encodedUrl.split(",");
            
            for(int i = 0; i< urls.length; i++){
                String[] fmtUrlPair = urls[i].split("url=http", 2);
                fmtUrlPair[1] = "url=http"+fmtUrlPair[1]+"&"+fmtUrlPair[0];

                fmtUrlPair[0] = fmtUrlPair[1].substring(fmtUrlPair[1].indexOf("itag=")+5, fmtUrlPair[1].indexOf("itag=")+5+1+(fmtUrlPair[1].matches(".*itag=[0-9]{2}.*")?1:0)+(fmtUrlPair[1].matches(".*itag=[0-9]{3}.*")?1:0));
                fmtUrlPair[1] = fmtUrlPair[1].replaceFirst("url=http%3A%2F%2F", "http://");
                fmtUrlPair[1] = fmtUrlPair[1].replaceAll("%3F","?").replaceAll("%2F", "/").replaceAll("%3B",";").replaceAll("%2C",",").replaceAll("%3D","=").replaceAll("%26", "&").replaceAll("%252C", "%2C").replaceAll("sig=", "signature=").replaceAll("&s=", "&signature=").replaceAll("\\?s=", "?signature=");
                
                
                // remove duplicated &itag=xy
                if (StringUtils.countString(fmtUrlPair[1], "itag=") == 2){
//                    System.out.println("Deleting itag!");
                    fmtUrlPair[1] = fmtUrlPair[1].replaceFirst("itag=[0-9]{1,3}", "");
                }

                //System.out.println("url[" + i + "]: " + urls[i]);
                LOGGER.log(Level.INFO, "fmtUrlPair[1]: {0}\nfmtUrlPair[0]: {1}", new Object[]{fmtUrlPair[1], fmtUrlPair[0]});
//                System.out.println("fmtUrlPair[1]: "+ fmtUrlPair[1] +"\nfmtUrlPair[0]: " + fmtUrlPair[0]);
                
                finalUrls.add(fmtUrlPair[1]);
                    
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        
        printUrls(finalUrls);
        
        //Setting filename
        grabTitle(text, finalUrls.get(0)); //the first quality
        
        return finalUrls;
    }
    
    /**
     * Use cliconverter.cc service to get the urls.
     * @param url the youtube url.
     * @return An ArrayList<String> with all the urls found for this video.
     */
    private BasicLinkHandler.Builder clipConverterExtraction(TrialLinkHandler tlh)throws Exception{
        return clipConverterExtraction(tlh, 0);
    }
    
    private BasicLinkHandler.Builder clipConverterExtraction(TrialLinkHandler tlh, int retryCount)throws Exception{
        String url = tlh.getReferenceLinkString();
        BasicLinkHandler.Builder linkHandlerBuilder = BasicLinkHandler.Builder.create();
        
        try {
            DefaultHttpClient httpClient = NHttpClient.getInstance();
            HttpPost httpPost = new HttpPost("http://www.clipconverter.cc/check.php");
            
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("mediaurl", url));
            
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            
            String responseString = EntityUtils.toString(httpResponse.getEntity());
            
            JSONObject jSonObject = new JSONObject(responseString);
            //System.out.println(jSonObject);
            JSONArray jSonArray = jSonObject.getJSONArray("url");
            
            //Set the group name as the name of the video
            linkHandlerBuilder.setGroupName(jSonObject.getString("filename"));
            
            
            // Davide you cannot create a this.fileName field
            // this.filename = jSonObject.getString("filename") + ".mp4";
            // The same YoutubeLinkHandler object will be used for hanlding
            // all Youtube links. We "do" it in different threads in 
            // neembuu.release1.ui.actions.LinkActionsImpl line 128
            // void reAddAction(boolean anotherThread) 
            
            for (int i = 0; i < jSonArray.length(); i++) {
                jSonObject = (JSONObject) jSonArray.get(i);
                String fileName = jSonObject.getString("text");
                String type = jSonObject.getString("filetype").toLowerCase();
                fileName = StringUtils.stringBetweenTwoStrings(fileName,"> ", "<");
                fileName = fileName+"."+type;
                
                String singleUrl = jSonObject.getString("url");

                long length = NHttpClientUtils.calculateLength(singleUrl);
                if(length<=0){ continue; /*skip this url*/ }
                
                /*System.out.println("{\n\turl="+singleUrl);
                System.out.println("\tsize="+length);
                System.out.println("\tfileName="+fileName+"\n}");*/
                
                linkHandlerBuilder.createFile()
                        .setName(fileName)
                        .setUrl(singleUrl)
                        .setSize(i).next();
            }
            
        } catch (Exception ex) {
            int retryLimit = ((YT_TLH)tlh).retryLimit;
            System.out.println("retry no. = "+retryCount);
            if(retryCount > retryLimit) throw ex;
            return clipConverterExtraction(tlh,retryCount+1);
        }

        return linkHandlerBuilder;
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
    
    
    static final class YT_TLH implements TrialLinkHandler {

        private final String url;
        private int retryLimit = 5;

        public void setRetryLimit(int retryLimit) { this.retryLimit = retryLimit; }

        YT_TLH(String url) { this.url = url; }

        /**
         * Inspired by: <a href="http://stackoverflow.com/questions/3717115/regular-expression-for-youtube-links">Stack Overflow</a>
         * @param url
         * @return 
         */
        @Override public boolean canHandle() {
            boolean result = url.matches("https?://(www.youtube.com/watch\\?v=|youtu.be/)([\\w\\-\\_]*)(&(amp;)?[\\w\\?=]*)?");
            LOGGER.log(Level.INFO, "Youtube can handle this? ", result);
            return result;
        }

        @Override public String getErrorMessage() { return canHandle() ? null : "Cannot handle"; }
        @Override public boolean containsMultipleLinks() { return true; }
        @Override public String tempDisplayName() { return url; }
        @Override public String getReferenceLinkString() { return url; }
    };
}
