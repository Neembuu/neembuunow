
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

package neembuu.release1.defaultImpl;

import java.security.KeyStore;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import neembuu.config.GlobalTestSettings;
import neembuu.release1.api.File;
import neembuu.release1.api.LinkHandler;
import neembuu.release1.log.LoggerUtil;
import neembuu.release1.api.LinkHandlerProvider;
import neembuu.release1.util.NeembuuHttpClient;
import neembuu.vfs.connection.NewConnectionProvider;
import neembuu.vfs.connection.sampleImpl.DownloadManager;
import org.apache.http.HttpConnection;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

/**
 *
 * @author Shashank Tulsyan
 */
public class DirectLinkHandlerProvider implements LinkHandlerProvider {    
    private static final Logger LOGGER = LoggerUtil.getLogger();
    
    static final class DirectLinkHandler implements LinkHandler{
        private final String fileName; 
        private final long fileSize;
        private final String url;

        public DirectLinkHandler(String fileName, long fileSize, String url) {
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
    
    
    private DefaultHttpClient newClient(){
        DefaultHttpClient client = new DefaultHttpClient();
        GlobalTestSettings.ProxySettings proxySettings 
                        = GlobalTestSettings.getGlobalProxySettings();
        HttpContext context = new BasicHttpContext();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        
        schemeRegistry.register(new Scheme("http", new PlainSocketFactory(), 80));
        
        try{
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            schemeRegistry.register(new Scheme("https", new SSLSocketFactory(keyStore), 8080));
        }catch(Exception a){
            a.printStackTrace(System.err);
        }

        context.setAttribute(
                ClientContext.SCHEME_REGISTRY,
                schemeRegistry);
        context.setAttribute(
                ClientContext.AUTHSCHEME_REGISTRY,
                new BasicScheme()/*file.httpClient.getAuthSchemes()*/);
        
        
        context.setAttribute(
                ClientContext.COOKIESPEC_REGISTRY,
                client.getCookieSpecs()/*file.httpClient.getCookieSpecs()*/
                );

        BasicCookieStore basicCookieStore = new BasicCookieStore();

        context.setAttribute(
                ClientContext.COOKIE_STORE,
                basicCookieStore/*file.httpClient.getCookieStore()*/);
        context.setAttribute(
                ClientContext.CREDS_PROVIDER,
                new BasicCredentialsProvider()/*file.httpClient.getCredentialsProvider()*/);

        HttpConnection hc = new DefaultHttpClientConnection();
        context.setAttribute(
                ExecutionContext.HTTP_CONNECTION,
                hc);

        //System.out.println(file.httpClient.getParams().getParameter("http.useragent"));
        HttpParams httpParams = new BasicHttpParams();
        
        if(proxySettings!=null){
            AuthState as = new AuthState();
            as.setCredentials(new UsernamePasswordCredentials(proxySettings.userName, proxySettings.password));
            as.setAuthScope(AuthScope.ANY);
            as.setAuthScheme(new BasicScheme());
            httpParams.setParameter(ClientContext.PROXY_AUTH_STATE,as);
            httpParams.setParameter("http.proxy_host", new HttpHost(proxySettings.host, proxySettings.port));
        }
        
        client = new DefaultHttpClient(new SingleClientConnManager(
                httpParams/*file.httpClient.getParams()*/,
                schemeRegistry),
                httpParams/*file.httpClient.getParams()*/);
        
        
        if(proxySettings!=null){
            client.getCredentialsProvider().setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(proxySettings.userName, proxySettings.password));
        }
        
        return client;
    }
    
    public final DirectLinkHandler getDirectLinkHandler(String url){
        
        String fileName = url.substring(url.lastIndexOf('/')+1);
        
        try{
            DefaultHttpClient httpClient = NeembuuHttpClient.getInstance();
            HttpGet request = new HttpGet(url);

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
            
            
            long length = response.getEntity().getContentLength();
            request.abort();
            LOGGER.log(Level.INFO, "File size found = {0}", length);
            if(length<0){
                LOGGER.info("length < 0 , not setting");
            }
            else {
                
                return new DirectLinkHandler(fileName,length,url);
            }
        }catch(Exception any){
            LOGGER.log(Level.INFO,"Can\'t get filesize",any);
        }
        return new DirectLinkHandler(fileName,-1,url);
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
    public boolean canHandle(String url) {
        return url.startsWith("http://");
    }

    @Override
    public LinkHandler getLinkHandler(String url) {
        return getDirectLinkHandler(url);
    }
    
    
}

