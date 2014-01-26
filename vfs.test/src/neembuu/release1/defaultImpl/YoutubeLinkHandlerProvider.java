/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.defaultImpl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.StringUtils;
import neembuu.release1.api.File;
import neembuu.release1.api.LinkHandler;
import neembuu.release1.api.LinkHandlerProvider;
import neembuu.release1.httpclient.NHttpClient;
import neembuu.release1.log.LoggerUtil;
import neembuu.release1.httpclient.utils.NHttpClientUtils;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.connection.sampleImpl.DownloadManager;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openide.util.Exceptions;

/**
 *
 * @author davidepastore
 */
public class YoutubeLinkHandlerProvider implements LinkHandlerProvider {
    private static final Logger LOGGER = LoggerUtil.getLogger();
    
    private String filename = "";
    
    static final class YoutubeLinkHandler implements LinkHandler{
        private final String fileName; 
        private final long fileSize;
        private final String url;

        public YoutubeLinkHandler(String fileName, long fileSize, String url) {
            this.fileName = fileName;
            this.fileSize = fileSize;
            this.url = url;
        }

        @Override
        public List<File> getFiles() {
            LinkedList<File> ll = new LinkedList<File>();
            File f = new File() {

                @Override
                public NewConnectionProvider getConnectionProvider() {
                    NewConnectionProvider newConnectionProvider =
                        new DownloadManager(url);
                        //new JD_DownloadManager(url);
                    return newConnectionProvider;
                }

                @Override
                public String fileName() {
                    return fileName;
                }

                @Override
                public long fileSize() {
                    return fileSize;
                }
            };
            ll.add(f);
            return ll;
        }

        @Override
        public long getGroupSize() {
            return fileSize;
        }        
        
        @Override
        public String getGroupName(){
            return fileName;
        }

        @Override
        public boolean foundName() {
            return fileName!=null;
        }

        @Override
        public boolean foundSize() {
            return fileSize > -1;
        }
    }

    /**
     * Inspired by: <a href="http://stackoverflow.com/questions/3717115/regular-expression-for-youtube-links">Stack Overflow</a>
     * @param url
     * @return 
     */
    @Override
    public boolean canHandle(String url) {
        boolean result = url.matches("https?://(www.youtube.com/watch\\?v=|youtu.be/)([\\w\\-\\_]*)(&(amp;)?[\\w\\?=]*)?");
        LOGGER.log(Level.INFO,"Youtube can handle this? ", result);
        
        return result;
    }

    @Override
    public LinkHandler getLinkHandler(String url) {
        return getYoutubeLinkHandler(url);
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

        this.filename = grabbedTitle; // complete file name without path 
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
    
    
    private LinkHandler getYoutubeLinkHandler(String url) {
        try {
            //String responseString = NHttpClientUtils.getData(url);
            //ArrayList<String> urls= findTextData(responseString);
            ArrayList<String> urls = clipConverterExtraction(url);
            
            long length = -1;
            String url_direct = "";
            for (int i = 0; i < urls.size(); i++) {
                url_direct = urls.get(i);
                length = NHttpClientUtils.calculateLength(url_direct); //the first quality
                if(length > -1){
                    break;
                }
            }
            
            return new YoutubeLinkHandlerProvider.YoutubeLinkHandler(filename, length, url_direct);
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }
    
    /**
     * Use cliconverter.cc service to get the urls.
     * @param url the youtube url.
     * @return An ArrayList<String> with all the urls found for this video.
     */
    private ArrayList<String> clipConverterExtraction(String url){
        ArrayList<String> urls = new ArrayList<String>();
        boolean exception = false;
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
            JSONArray jSonArray = jSonObject.getJSONArray("url");
            
            //Set the filename
            this.filename = jSonObject.getString("filename") + ".mp4";
            
            
            for (int i = 0; i < jSonArray.length(); i++) {
                jSonObject = (JSONObject) jSonArray.get(i);

                if(jSonObject.getString("text").contains("720p")){
                    System.out.print("720p = ");
                }
                
                if(jSonObject.getString("text").contains("480p")){
                    System.out.print("480p = ");
                }
                
                if(jSonObject.getString("text").contains("360p")){
                    System.out.print("360p = ");
                }
                
                if(jSonObject.getString("text").contains("3GP")){
                    System.out.print("3GP = ");
                }
                
                String singleUrl = jSonObject.getString("url");
                singleUrl = StringUtils.stringUntilString(singleUrl, "#");
                
                urls.add(singleUrl);
                System.out.println(singleUrl);
            }
            
        } catch (UnsupportedEncodingException ex) {
            Exceptions.printStackTrace(ex);
            exception = true;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            exception = true;
        } catch (JSONException ex) {
            Exceptions.printStackTrace(ex);
            exception = true;
        }
        if(exception){
            return clipConverterExtraction(url);
        }
        return urls;
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
    
}
