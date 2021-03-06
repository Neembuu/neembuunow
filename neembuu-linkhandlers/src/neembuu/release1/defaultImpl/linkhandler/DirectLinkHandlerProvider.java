
/*
 * Copyright (C) 2011 Shashank Tulsyan
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

package neembuu.release1.defaultImpl.linkhandler;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.release1.api.linkhandler.TrialLinkHandler;
import neembuu.release1.api.linkhandler.LinkHandler;
import neembuu.release1.api.linkhandler.LinkHandlerProvider;
import neembuu.release1.api.log.LoggerUtil;
import neembuu.release1.httpclient.NHttpClient;
import neembuu.util.CombineExceptions;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author Shashank Tulsyan
 */
public class DirectLinkHandlerProvider implements LinkHandlerProvider {    
    private static final Logger LOGGER = LoggerUtil.getLogger(DirectLinkHandlerProvider.class.getName());
    
    private static final int RETRY_COUNT = 5;
    
    private LinkHandler getDirectLinkHandler(String url, int attempt)throws Exception{
        
        String fileName = url.substring(url.lastIndexOf('/')+1);
        
        try{
            DefaultHttpClient httpClient = NHttpClient.getNewInstance();
            HttpGet request = new HttpGet(url);
            System.out.println("url="+url);
            HttpResponse response = httpClient.execute(request);

            //resp.setHeader("Content-Disposition", "attachment; filename=" + fileName );
            //resp.setHeader("Content-Type", "attachment; filename=" + fileName );
            try{
                String tmp = response.getHeaders("Content-Disposition")[0].getValue();
                int i = tmp.lastIndexOf("filename=");
                if(i > 0){
                    fileName = tmp.substring(i+9);
                    fileName = trimLeadingAndTrailingInvertedComma(fileName);
                }
            }catch(Exception a){
                String tmp = response.getHeaders("Content-Type")[0].getValue();
                try{
                    int i = tmp.lastIndexOf("filename=");
                    if(i > 0){
                        fileName = tmp.substring(i+9);
                        fileName = trimLeadingAndTrailingInvertedComma(fileName);
                    }
                }catch(Exception a2){
                    LOGGER.log(Level.INFO,"Can\'t get filename from Content-Disposition header",a);
                    LOGGER.log(Level.INFO,"Can\'t get filename from Content-Type header",a2);
                    fileName = url.substring(url.lastIndexOf('/')+1);
                }
            }
            
            if(fileName.length() ==0 ){
                fileName = Utils.getNameFromURL(url);
            }
            
            long length = response.getEntity().getContentLength();
            request.abort();
            LOGGER.log(Level.INFO, "File size found = {0}", length);
            if(length<0){
                LOGGER.info("length < 0 , not setting");
            }
            else {
                return BasicLinkHandler.Builder.create().setGroupName(fileName)
                        .createFile()
                                .setName(fileName).setUrl(url).setSize(length)
                        .next()
                        .build();
            }
        }catch(Exception any){
            LOGGER.log(Level.INFO,"Can\'t get filesize",any);
            throw any;
        }
        return BasicLinkHandler.Builder.create().addFile(url, fileName, -1).setGroupName(fileName).build();
    }
    
    private static String trimLeadingAndTrailingInvertedComma(String a){
        if(a.charAt(0)=='\"'){
            a = a.substring(1);
        }
        if(a.charAt(a.length()-1)=='\"'){
            a = a.substring(0,a.length()-1);
        }
        return a;
    }

    @Override
    public TrialLinkHandler tryHandling(final String url) {
        return new TrialLinkHandler() {

            @Override public boolean canHandle() {
                return url.startsWith("http://") || url.startsWith("https://"); 
            }

            @Override public String getErrorMessage() {
                return !canHandle()?"Only http/https links allowed.":null;
            }

            @Override public boolean containsMultipleLinks() {
                return false;
            }

            @Override public String tempDisplayName(){
                return getReferenceLinkString(); //return url;//.substring(url.lastIndexOf('/')+1);
            }

            @Override public String getReferenceLinkString() {
                return Utils.normalize(url);
            }
        };
    }

    @Override
    public LinkHandler getLinkHandler(TrialLinkHandler trialLinkHandler) throws Exception{
        LinkHandler lh = null;
        ArrayList<Exception> es = new ArrayList<>();
        for (int i = 0; i < RETRY_COUNT; i++) {
            try{
                lh = getDirectLinkHandler(trialLinkHandler.getReferenceLinkString(),i);
            }catch(Exception a){
                es.add(a);
                lh = null;
            }
            if(lh!=null){
                return lh;
            }
        }
        
        //es.add(new Exception("Eww!"));
        
        throw CombineExceptions.combine(es);
    }

        
}

