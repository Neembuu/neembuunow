/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.api;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.StringUtils;
import neembuu.release1.log.LoggerUtil;
import neembuu.release1.util.NeembuuHttpClient;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.connection.sampleImpl.DownloadManager;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
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

    @Override
    public boolean canHandle(String url) {
        LOGGER.log(Level.INFO,"Youtube can handle this? ", url.startsWith("http://www.youtube.com/ or https " ));
        // we need to make use of regex here
        return url.startsWith("http://www.youtube.com/") || url.startsWith("https://www.youtube.com/");
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
        
        String contentType = NeembuuHttpClient.getContentType(url);
        
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
                System.out.println("fmtUrlPair[1]: "+ fmtUrlPair[1] +"\nfmtUrlPair[0]: " + fmtUrlPair[0]);
                
                finalUrls.add(fmtUrlPair[1]);
                    
            }
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        
        //Setting filename
        grabTitle(text, finalUrls.get(0)); //the first quality
        
        return finalUrls;
    }
    
    
    private LinkHandler getYoutubeLinkHandler(String url) {
        try {
            DefaultHttpClient httpClient = NeembuuHttpClient.getInstance();
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);
            String responseString = EntityUtils.toString(response.getEntity());
            ArrayList<String> urls= findTextData(responseString);
            long length = NeembuuHttpClient.calculateLength(urls.get(0)); //the first quality
            return new YoutubeLinkHandlerProvider.YoutubeLinkHandler(filename, length, urls.get(0));
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }
    
}
