/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.release1.httpclient;

import java.security.KeyStore;
import neembuu.config.GlobalTestSettings;
import org.apache.http.HttpConnection;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.UsernamePasswordCredentials;
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
            httpClient = new DefaultHttpClient();
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

            httpClient = new DefaultHttpClient(new SingleClientConnManager(
                    httpParams/*file.httpClient.getParams()*/,
                    schemeRegistry),
                    httpParams/*file.httpClient.getParams()*/);


            if(proxySettings!=null){
                httpClient.getCredentialsProvider().setCredentials(
                    AuthScope.ANY,
                    new UsernamePasswordCredentials(proxySettings.userName, proxySettings.password));
            }
        
        
        }
        return httpClient;
    }
    
}
