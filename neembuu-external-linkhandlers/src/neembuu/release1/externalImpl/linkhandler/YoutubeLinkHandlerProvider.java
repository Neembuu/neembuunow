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
package neembuu.release1.externalImpl.linkhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.file.OnlineFile;
import neembuu.release1.api.file.PropertyProvider;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.LinkHandlerProvider;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.captcha.Captcha;
import neembuu.release1.defaultImpl.file.BasicOnlineFile;
import neembuu.release1.defaultImpl.file.BasicPropertyProvider;
import neembuu.release1.httpclient.NHttpClient;
import neembuu.release1.httpclient.utils.NHttpClientUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import davidepastore.StringUtils;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.defaultImpl.external.ELHProvider;
import neembuu.release1.defaultImpl.linkhandler.BasicLinkHandler;
import neembuu.release1.defaultImpl.linkhandler.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author davidepastore
 */
@ELHProvider(
        checkingRegex = "https?://(www.youtube.com/watch\\?v=|youtu.be/)([\\w\\-\\_]*)(&(amp;)?[\\w\\?=]*)?",
        minimumReleaseVerReq = 1398604095683L
)
public class YoutubeLinkHandlerProvider implements LinkHandlerProvider {
    private static final Logger LOGGER = LoggerUtil.getLogger(YoutubeLinkHandlerProvider.class.getName()); // all logs go into an html file
    
    private final String K_CHALLENGE_URL = "https://www.google.com/recaptcha/api/challenge?k=";
    private final String K_CHALLENGE_CODE = "6LcVessSAAAAAH73irTtpZYKknjeBvN3nuUzJ2G3";
    
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
    
    private void xzz(){}
    
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
        
        String contentType = NHttpClientUtils.getContentType(url,NHttpClient.getNewInstance());
        
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
            ex.printStackTrace();
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
            DefaultHttpClient httpClient = NHttpClient.getNewInstance();
            HttpPost httpPost = new HttpPost("http://www.clipconverter.cc/check.php");
            
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            formparams.add(new BasicNameValuePair("mediaurl", url));
            
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            
            final String responseString = EntityUtils.toString(httpResponse.getEntity());
            
            JSONObject jSonObject = new JSONObject(responseString);
            //System.out.println(jSonObject);
            
            if(jSonObject.has("redirect")){
                int count = retryCount;
                
                //If captcha is incorrect, add a count
                if(!handleCaptcha(jSonObject)){
                    count++;
                }
                
                return clipConverterExtraction(tlh, count);
            }
            
            JSONArray jSonArray = jSonObject.getJSONArray("url");
            
            System.out.println("urls: " + jSonArray);
            
            //Set the group name as the name of the video
            String  nameOfVideo = jSonObject.getString("filename");
            //normalize name of video
            
            //nameOfVideo = jpfm.util.UniversallyValidFileName.makeUniversallyValidFileName(nameOfVideo);
            linkHandlerBuilder.setGroupName(nameOfVideo);
            
            
            // Davide you cannot create a this.fileName field
            // this.filename = jSonObject.getString("filename") + ".mp4";
            // The same YoutubeLinkHandler object will be used for hanlding
            // all Youtube links. We "do" it in different threads in 
            // neembuu.release1.ui.actions.LinkActionsImpl line 128
            // void reAddAction(boolean anotherThread) 
            
            long c_duration = -1;
            
            for (int i = 0; i < jSonArray.length(); i++) {
                jSonObject = (JSONObject) jSonArray.get(i);
                String fileName = jSonObject.getString("text");
                System.out.println("Filename: " + fileName);
                
                final String extension = jSonObject.getString("filetype").toLowerCase();
                fileName = StringUtils.stringBetweenTwoStrings(fileName, ">", "<");
                fileName = fileName + "." + extension;
                
                String singleUrl = jSonObject.getString("url");
                //singleUrl = singleUrl.substring(0, singleUrl.indexOf("#"));
                //did some changes, but this doesn't help :(
                System.out.println("Before normalization URL: " + singleUrl);
                long length = tryFindingSize(singleUrl);
                singleUrl = Utils.normalize(singleUrl);
                System.out.println("Normalized URL: " + singleUrl);
                
                
                if(length==0){
                    length = NHttpClientUtils.calculateLength(singleUrl,httpClient);
                }
                //System.out.println("Length: " + length);
                
                if(length <= 0){ continue; /*skip this url*/ }
                
                BasicOnlineFile.Builder fileBuilder = linkHandlerBuilder
                        .createFile();
                
                try{ // finding video/audio length
                    String dur = StringUtils.stringBetweenTwoStrings(singleUrl, "dur=", "&");
                    long duration = (int)(Double.parseDouble(dur)*1000);
                    if(c_duration < 0 ){ c_duration = duration; }
                    fileBuilder.putLongPropertyValue(PropertyProvider.LongProperty.MEDIA_DURATION_IN_MILLISECONDS, duration);
                    System.out.println("dur="+dur);
                }catch(Exception a){
                    // ignore
                }
                
                try{ // finding the quality short name
                    String type = fileName.substring(fileName.indexOf("(")+1);
                    type = type.substring(0,type.indexOf(")"));
                    fileBuilder.putStringPropertyValue(PropertyProvider.StringProperty.VARIANT_DESCRIPTION, type);
                    System.out.println("type="+type);
                }catch(Exception a){
                    a.printStackTrace();
                }
                
                fileName = nameOfVideo + " " +fileName;
                
                fileBuilder.setName(fileName)
                    .setUrl(singleUrl)
                    .setSize(length).next();
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
            int retryLimit = ((YT_TLH)tlh).retryLimit;
            ex.printStackTrace();
            System.out.println("retry no. = "+retryCount);
            if(retryCount > retryLimit) throw ex;
            return clipConverterExtraction(tlh,retryCount+1);
        }

        return linkHandlerBuilder;
    }
    
    private long tryFindingSize(String rawURL){
        try{
            String s = "size=";
            String sz = rawURL.substring(rawURL.indexOf(s)+s.length());
            if(sz.contains("#")){
                sz = sz.substring(0,sz.indexOf("#"));
            }
            long size = Long.parseLong(sz);
            return size;
        }catch(Exception a){
            /*size not found ignore*/
            a.printStackTrace();
        }
        return 0;
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


    /**
     * Handle the captcha string.
     * @param jSonObject The JSONObject with the redirect url.
     * @return Returns true if the captcha is correct, false otherwise.
     */
    private boolean handleCaptcha(JSONObject jSonObject) {
        try {
            System.out.println("Handling captcha.");
            
            final String redirect = jSonObject.getString("redirect");
            final String url = "http://www.clipconverter.cc" + redirect;
            final DefaultHttpClient httpClient = NHttpClient.getNewInstance();
            
            //Get the captcha code
            Captcha captcha = new Captcha();
            captcha.setFormTitle("Captcha for Youtube.com");
            if (captcha.findCCaptchaUrlFromK(K_CHALLENGE_URL + K_CHALLENGE_CODE) != null) {
                captcha.findCaptchaImageURL();
                final String captchaString = captcha.getCaptchaString();

                HttpPost httpPost = new HttpPost(url);
                List<NameValuePair> formparams = new ArrayList<>();
                formparams.add(new BasicNameValuePair("recaptcha_challenge_field", captcha.getCCaptchaUrl()));
                formparams.add(new BasicNameValuePair("recaptcha_response_field", captchaString));

                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
                httpPost.setEntity(entity);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                final String responseString = EntityUtils.toString(httpResponse.getEntity());
                return !responseString.contains("Invalid captcha!");
            } else {
                throw new Exception("Captcha generic error");
            }
        } catch (JSONException ex) {
            //ex.printStackTrace();
            LOGGER.log(Level.INFO,"error",ex);
        } catch (Exception ex) {
            //ex.printStackTrace();
            LOGGER.log(Level.INFO,"error",ex);
        }
        
        return false;
        
    }
    
       
    static final class YT_TLH implements TrialLinkHandler {

        private final String url;
        private int retryLimit = 5;

        public void setRetryLimit(int retryLimit) { this.retryLimit = retryLimit; }

        YT_TLH(String url) { 
            // normalize the url here
            this.url = Utils.normalize(url);
        }

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
        @Override public String getReferenceLinkString() { 
            return url; 
        }
    };
}
