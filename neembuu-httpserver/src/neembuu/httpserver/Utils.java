/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.httpserver;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.message.BasicHeader;

/**
 *
 * @author Shashank Tulsyan
 */
public class Utils {
    
//    GET /test_videos/BigBuckBunny_320x180.mp4 HTTP/1.1
//    Host: neembuu.com
//    User-Agent: VLC/2.1.3 LibVLC/2.1.3
//    Range: bytes=0-
//    Connection: close
//    Icy-MetaData: 1
//
//    HTTP/1.1 206 Partial Content
//    Date: Tue, 08 Jul 2014 12:41:40 GMT
//    Server: Apache/2.2.3 (CentOS)
//    Last-Modified: Sun, 23 Mar 2014 16:11:44 GMT
//    Accept-Ranges: bytes
//    Content-Length: 64657027
//    Cache-Control: max-age=2592000
//    Expires: Thu, 07 Aug 2014 12:41:40 GMT
//    X-Content-Type-Options: nosniff
//    Content-Range: bytes 0-64657026/64657027
//    Connection: close
//    Content-Type: video/mp4
    
//    GET /test_videos/BigBuckBunny_320x180.mp4 HTTP/1.1
//    Host: neembuu.com
//    User-Agent: VLC/2.1.3 LibVLC/2.1.3
//    Range: bytes=64312833-
//    Connection: close
//    Icy-MetaData: 1

//    HTTP/1.1 206 Partial Content
//    Date: Tue, 08 Jul 2014 12:41:41 GMT
//    Server: Apache/2.2.3 (CentOS)
//    Last-Modified: Sun, 23 Mar 2014 16:11:44 GMT
//    Accept-Ranges: bytes
//    Content-Length: 344194
//    Cache-Control: max-age=2592000
//    Expires: Thu, 07 Aug 2014 12:41:41 GMT
//    X-Content-Type-Options: nosniff
//    Content-Range: bytes 64312833-64657026/64657027
//    Connection: close
//    Content-Type: video/mp4
    
    public static long standardOffsetResponse(final HttpRequest request,
                final HttpResponse response, long fileSize){
        long offset = findOffset(request);
        long contentLength = fileSize - offset;
        System.out.println("offset="+offset);
        response.addHeader(new BasicHeader("Accept-Ranges", "bytes"));
        response.addHeader(new BasicHeader("Content-Length",Long.toString(contentLength)));
        response.addHeader(new BasicHeader("Content-Range", "bytes " +offset+"-"
                +(fileSize-1)+"/"+fileSize
        ));
        response.addHeader(new BasicHeader("Connection","close"));
        //response.setStatusCode(HttpStatus.SC_OK);
        response.setStatusCode(HttpStatus.SC_PARTIAL_CONTENT);
        return offset;
    }
    
    private static long findOffset(final HttpRequest request){
        for(Header h : request.getAllHeaders()){
            if(h.getName().trim().equalsIgnoreCase("Range")){
                try{
                    String val = h.getValue();
                    int i =0;
                    SMALL_LOOP:
                    for (; i < val.length(); i++) {
                        if(Character.isDigit(val.charAt(i))){
                            break SMALL_LOOP;
                        }
                    }
                    val = val.substring(i,val.indexOf('-'));
                    return Long.parseLong(val);
                }catch(Exception a){
                    a.printStackTrace();
                }
            }
        }return 0;
    }
}
