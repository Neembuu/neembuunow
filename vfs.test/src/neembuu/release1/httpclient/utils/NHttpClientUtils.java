/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.httpclient.utils;

import java.io.IOException;
import neembuu.release1.httpclient.NHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.openide.util.Exceptions;

/**
 * Utils class for NHttpClient.
 * @author davidepastore
 */
public class NHttpClientUtils {

    /**
     * Calculate the length
     * @param url
     * @return the length
     */
    public static long calculateLength(String url) {
        try {
            DefaultHttpClient httpClient = NHttpClient.getInstance();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            long length = response.getEntity().getContentLength();
            System.out.println("Length: " + length);
            EntityUtils.consume(response.getEntity());
            return length;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return -1;
    }

    /**
     * Get the content type of a url.
     * @param url
     * @return the content type.
     */
    public static String getContentType(String url) {
        try {
            DefaultHttpClient httpClient = NHttpClient.getInstance();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            String contentType = response.getEntity().getContentType().getValue();
            System.out.println("Content Type: " + contentType);
            EntityUtils.consume(response.getEntity());
            return contentType;
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }
    
    
    /**
     * Get the content of a page.
     * @param url url from which to read
     * @return the String content of the page
     * @throws Exception 
     */
    public static String getData(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = NHttpClient.getInstance().execute(httpGet);
        return EntityUtils.toString(httpResponse.getEntity());
    }
    
    /**
     * Get the content of a page.
     * @param url url from which to read
     * @param httpContext the httpContext in which to make the request
     * @return the String content of the page
     * @throws Exception 
     */
    public static String getData(String url, HttpContext httpContext) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = NHttpClient.getInstance().execute(httpGet, httpContext);
        return EntityUtils.toString(httpResponse.getEntity());
    }
    
    
    
}
