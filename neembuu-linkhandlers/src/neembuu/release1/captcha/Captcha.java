/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.captcha;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import davidepastore.StringUtils;
import neembuu.release1.httpclient.NHttpClient;
import neembuu.release1.httpclient.utils.NHttpClientUtils;
import org.apache.http.protocol.HttpContext;

/**
 * This class allows you to search within a string, the k challenge, the c challenge, 
 * the captcha image and displays it to the user to obtain in this way the input.
 * @author davidepastore
 */
public class Captcha{
    
    private String string;
    private String formTitle;
    
    /* Google */
    private String googleRegK = "http://www\\.google\\.com/recaptcha/api/challenge\\?k=";
    private String googleRegC = "challenge.*?:.*?'(.*?)',";
    private String googleURL = "http://www.google.com/recaptcha/api/image?c=";
    
    /* Recaptcha */
    private String recaptchaRegK = "http://www\\.api\\.recaptcha\\.net/challenge\\?k=";
    
    private Pattern regex;
    private Matcher regexMatcher;
    private int start;
    private int end;
    private String kCaptchaUrl;
    private String cCaptchaUrl;
    private HttpContext httpContext;
    private URL imageURL;
    
    /**
     * Every how many milliseconds to check if the form of captcha has been closed.
     */
    private final static long WAIT_TIME = 1000;
    
    /**
     * Basic constructor without setting string. If you already have the K challenge link,
     * you can use this and then set the formTitle with <b>setFormTitle()</b>.
     */
    public Captcha(){
    }
    
    /**
     * Constructor
     * @param string The string into which you want to find the captcha urls.
     */
    public Captcha(String string){
        this.string = string;
    }
    
    /**
     * Constructor
     * @param string The string into which you want to find the captcha urls.
     * @param formTitle The form title.
     */
    public Captcha(String string, String formTitle){
        this.string = string;
        this.formTitle = formTitle;
    }
    
    /**
     * Find the K Challenge URL.
     * @return The K Challenge URL as a String.
     * @throws Exception 
     */
    public String findKCaptchaUrl() throws Exception{
        kCaptchaUrl = null;
        
        //FileUtils.saveInFile("Recaptcha.html", string);
        
        //For google
        regex = Pattern.compile(googleRegK);
        regexMatcher = regex.matcher(string);
        
        if(regexMatcher.find()){
            //NULogger.getLogger().info("Google K recaptcha found!");
            start = regexMatcher.start();
            string = string.substring(start);
            end = string.indexOf("\"");
            kCaptchaUrl = string.substring(0, end);
//            NULogger.getLogger().log(Level.INFO, "kCaptchaUrl: {0}", kCaptchaUrl);
            return kCaptchaUrl;
        }
        
        //For recaptcha
        regex = Pattern.compile(recaptchaRegK);
        regexMatcher = regex.matcher(string);
        
        if(regexMatcher.find()){
            //NULogger.getLogger().info("Recaptcha K recaptcha found!");
            start = regexMatcher.start();
            string = string.substring(start);
            end = string.indexOf("\"");
            kCaptchaUrl = string.substring(0, end);
//            NULogger.getLogger().log(Level.INFO, "kCaptchaUrl: {0}", kCaptchaUrl);
            return kCaptchaUrl;
        }
        
        return null;
    }

    /**
     * Find the C Challenge URL.
     * @return The C Challenge URL as a String.
     * @throws Exception 
     */
    public String findCCaptchaUrl() throws Exception{
        return findCCaptchaUrlFromK(kCaptchaUrl);
    }
    
    /**
     * Find the C Challenge URL from the given K Challenge URL.
     * @param kCaptchaUrl The K Challenge URL as a String.
     * @return The C Challenge URL as a String..
     * @throws IOException 
     */
    public String findCCaptchaUrlFromK(String kCaptchaUrl) throws IOException, Exception{
        cCaptchaUrl = null;
        
        if(kCaptchaUrl == null){
            return null;
        }
        
        String body =  NHttpClientUtils.getData(kCaptchaUrl,httpContext,NHttpClient.getNewInstance());
        
        //CommonUploaderTasks.saveInFile("Recaptcha.html", body);
        
        regex = Pattern.compile(googleRegC);
        regexMatcher = regex.matcher(body);
        
        if(regexMatcher.find()){
//            NULogger.getLogger().info("Google C recaptcha found!");
            cCaptchaUrl = body.substring(regexMatcher.start(), regexMatcher.end());
            cCaptchaUrl = StringUtils.stringBetweenTwoStrings(cCaptchaUrl, "'", "'");
//            NULogger.getLogger().log(Level.INFO, "cCaptchaUrl: {0}", cCaptchaUrl);
            return cCaptchaUrl;
        }
        
//        NULogger.getLogger().log(Level.INFO, "kCaptchaUrl: {0}", kCaptchaUrl);
        
        return cCaptchaUrl;
    }
    
    /**
     * Find the captcha image URL.
     * @return The captcha image URL as an URL.
     * @throws IOException
     * @throws Exception 
     */
    public URL findCaptchaImageURL() throws IOException, Exception{
        imageURL = new URL(googleURL+cCaptchaUrl);
        return imageURL;
    }

    /**
     * Return the captcha string (entered by user).
     * @return The captcha string (entered by user).
     * @throws InterruptedException 
     */
    public String getCaptchaString() throws InterruptedException {
        CaptchaForm captchaForm;
        captchaForm = new CaptchaForm(imageURL, formTitle, httpContext);
        captchaForm.setVisible(true);
//        NULogger.getLogger().info("Captcha Form opened.");
        
        //Is this the better method?
        while(!captchaForm.isClosing){
            Thread.sleep(WAIT_TIME);
        }
//        NULogger.getLogger().log(Level.INFO, "Captcha Form closed. I read: {0}", captchaForm.captchaString);
        return captchaForm.captchaString;
    }
    
    /**
     * Get C Captcha Url.
     * @return The C Captcha Url.
     */
    public String getCCaptchaUrl(){
        return cCaptchaUrl;
    }
    
    /**
     * Set the title of the form.
     * @param title The title of the form.
     */
    public void setFormTitle(String title){
        this.formTitle = title;
    }
    
    /**
     * Set the image URL.
     * @param imageUrl The image URL (URL type).
     */
    public void setImageURL(URL imageUrl){
        this.imageURL = imageUrl;
}
    
    /**
     * Set the image URL.
     * @param imageUrl The image URL (String type).
     */
    public void setImageURL(String imageUrl) throws MalformedURLException{
        this.setImageURL(new URL(imageUrl));
    }

    /**
     * Set the HttpContext
     * @param httpContext 
     */
    public void setHttpContext(HttpContext httpContext) {
        this.httpContext = httpContext;
    }
}
