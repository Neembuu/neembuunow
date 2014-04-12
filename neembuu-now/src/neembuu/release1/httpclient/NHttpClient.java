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
package neembuu.release1.httpclient;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import neembuu.config.GlobalTestSettings;
import org.apache.http.HttpConnection;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.openide.util.Exceptions;

/**
 * Neembuu HttpClient.
 * @author davidepastore
 */
public class NHttpClient {
    
    private static DefaultHttpClient httpClient = null;
    
    private NHttpClient(){
        
    }
    
    /**
     * Get the instance
     * @return HttpClient instance.
     */
    public static DefaultHttpClient getInstance(){
        //Initializes the instance.
        if(httpClient == null){
            try {
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
                
                
                /* Allow all hostname */
                //SSL http://javaskeleton.blogspot.it/2010/07/avoiding-peer-not-authenticated-with.html
                SSLContext ctx;
                ctx = SSLContext.getInstance("TLS");
                 
                X509TrustManager tm = new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                };
                ctx.init(null, new TrustManager[]{tm}, null);
                SSLSocketFactory ssf = new SSLSocketFactory(ctx);
                
                SchemeRegistry registry = new SchemeRegistry();
                registry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
                registry.register(new Scheme("https", 443, ssf));
                ClientConnectionManager ccm = new PoolingClientConnectionManager(registry);
                
                httpClient = new DefaultHttpClient(ccm);
                
                context.setAttribute(
                        ClientContext.SCHEME_REGISTRY,
                        schemeRegistry);
                
                context.setAttribute(
                        ClientContext.AUTHSCHEME_REGISTRY,
                        new BasicScheme()/*file.httpClient.getAuthSchemes()*/
                );
                
                context.setAttribute(
                        ClientContext.COOKIESPEC_REGISTRY,
                        httpClient.getCookieSpecs()/*file.httpClient.getCookieSpecs()*/
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
                
                httpClient.setParams(httpParams /*file.httpClient.getParams()*/);
                
                
                if(proxySettings != null){
                    httpClient.getCredentialsProvider().setCredentials(
                            AuthScope.ANY,
                            new UsernamePasswordCredentials(proxySettings.userName, proxySettings.password));
                }
            } catch (NoSuchAlgorithmException ex) {
                Exceptions.printStackTrace(ex);
            } catch (KeyManagementException ex) {
                Exceptions.printStackTrace(ex);
            }
        
        
        }
        return httpClient;
    }
    
}
