/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.defaultImpl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.StringUtils;
import neembuu.release1.api.File;
import neembuu.release1.api.LinkHandler;
import neembuu.release1.api.LinkHandlerProvider;
import neembuu.release1.log.LoggerUtil;
import neembuu.release1.httpclient.utils.NHttpClientUtils;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.connection.sampleImpl.DownloadManager;
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
            String responseString = NHttpClientUtils.getData(url);
            ArrayList<String> urls= findTextData(responseString);
            long length = -1;
            String url_direct = "";
            for (int i = 0; i < urls.size(); i++) {
                url_direct = urls.get(i);
                length = NHttpClientUtils.calculateLength(url_direct); //the first quality
                if(length>-1){
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
     * Print all the url (debug purpose):
     * @param urls ArrayList<String> with all the urls.
     */
    private void printUrls(ArrayList<String> urls) {
        System.out.println("\n***** START PRINTING YOUTUBE URLS *****");
        for (String url : urls) {
            System.out.println(url);
        }
        
//        http://r7---sn-nx5cvox-hpae.googlevideo.com/videoplayback?id=b7e6515fcf7ce2c7&upn=pE_LJRLbXHA&ms=au&sparams=gcr,id,ip,ipbits,itag,ratebypass,source,upn,expire&mt=1389635120&mv=m&sver=3&expire=1389658372&itag=18&ratebypass=yes&ipbits=0&fexp=935616,912301,906946,932275,914005,916623,938630,936910,936913,907231,907240,921090&key=yt5&ip=87.21.255.9&source=youtube&gcr=it&signature=BB1F40DD101C64D5BD561FD6B2435FFD62920671.3075BA0DAF7CFE73A35EBDFDDBDDDF2DC2DB03E4
//        http://r7---sn-nx5cvox-hpae.googlevideo.com/videoplayback?ratebypass=yes&fexp=935621,921404,938625,943700,938630,936910,936913,907231,907240,921090&&key=yt5&ip=87.21.255.9&upn=T_H8pQ43vPE&gcr=it&mt=1389634410&id=b7e6515fcf7ce2c7&expire=1389658372&sver=3&ipbits=0&ms=au&mv=m&sparams=gcr,id,ip,ipbits,itag,ratebypass,source,upn,expire&source=youtube&signature=42648F98D3E266283C6288CFE628D3A42CF9FEF42.41FE549CD274FD6D17031A3531D9C203A49EB98EE&itag=22
        
        // Deleted &fallback_host=tc.v8.cache8.googlevideo.com
        // Deleted &quality=hd720
        // Deleted &type=video/mp4;+codecs=%22avc1.64001F,+mp4a.40.2%22&
        
        
        
        System.out.println("***** END PRINTING YOUTUBE URLS *****\n");
    }
    
}
