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

package neembuu.release1.httpclient.utils;

import java.io.IOException;
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
     * @param httpClient
     * @return the length
     */
    public static long calculateLength(String url,DefaultHttpClient httpClient) {
        try {
            //DefaultHttpClient httpClient = NHttpClient.getInstance();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            long length = response.getEntity().getContentLength();
            System.out.println("Length: " + length);
            httpGet.abort();
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
    public static String getContentType(String url,DefaultHttpClient httpClient) {
        try {
            //DefaultHttpClient httpClient = NHttpClient.getInstance();
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
    public static String getData(String url,DefaultHttpClient httpClient) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        return EntityUtils.toString(httpResponse.getEntity());
    }
    
    /**
     * Get the content of a page.
     * @param url url from which to read
     * @param httpContext the httpContext in which to make the request
     * @return the String content of the page
     * @throws Exception 
     */
    public static String getData(String url, HttpContext httpContext,DefaultHttpClient httpClient) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet, httpContext);
        return EntityUtils.toString(httpResponse.getEntity());
    }
    
    
    
}
