package jp.adlantis.android;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Map;
import jp.adlantis.android.utils.ADLAssetUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

public class AdRequest
  extends NetworkRequest
  implements AdRequestNotifier
{
  protected static String DEBUG_TASK = "AdRequest";
  protected AdlantisAdsModel adModel;
  protected AdRequestListeners listeners = new AdRequestListeners();
  
  public AdRequest(AdlantisAdsModel paramAdlantisAdsModel)
  {
    this.adModel = paramAdlantisAdsModel;
  }
  
  protected Uri adRequestUri(Context paramContext, Map<String, String> paramMap)
  {
    return getAdNetworkConnection().adRequestUri(adManager(), paramContext, paramMap);
  }
  
  public void addRequestListener(AdRequestListener paramAdRequestListener)
  {
    this.listeners.addRequestListener(paramAdRequestListener);
  }
  
  public void addRequestListeners(AdRequestListeners paramAdRequestListeners)
  {
    this.listeners.addRequestListeners(paramAdRequestListeners.listeners);
  }
  
  public AdlantisAd[] adsForRequestUri(Context paramContext, Uri paramUri)
    throws IOException, ClientProtocolException
  {
    return AdlantisAd.adsFromJSONInputStream(inputStreamForUri(paramContext, paramUri));
  }
  
  public void connect(final Context paramContext, final Map<String, String> paramMap, final AdRequestManagerCallback paramAdRequestManagerCallback)
  {
    if (Looper.getMainLooper() == null) {
      log_e("Looper.getMainLooper() == null connect() failed.");
    }
    new Thread()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAdRequestManagerCallback != null) {
          paramAdRequestManagerCallback.adsLoaded();
        }
      }
    }
    {
      public void run()
      {
        AdRequest.this.doAdRequest(paramContext, paramMap);
        Message localMessage = this.val$adLoadedHandler.obtainMessage(0, this);
        this.val$adLoadedHandler.sendMessage(localMessage);
      }
    }.start();
  }
  
  public boolean doAdRequest(Context paramContext, Map<String, String> paramMap)
  {
    bool1 = false;
    try
    {
      AdlantisAd[] arrayOfAdlantisAd = adsForRequestUri(paramContext, adRequestUri(paramContext, paramMap));
      if (arrayOfAdlantisAd != null)
      {
        if (arrayOfAdlantisAd.length > 0) {
          bool1 = true;
        }
        getAdsModel().setAds(arrayOfAdlantisAd);
        log_d(arrayOfAdlantisAd.length + " ads loaded");
      }
      bool2 = bool1;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      for (;;)
      {
        bool2 = bool1;
        log_e(localMalformedURLException.toString());
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        boolean bool2 = bool1;
        log_e(localIOException.toString());
        continue;
        notifyListenersFailedToReceiveAd(null);
      }
    }
    if (bool2)
    {
      notifyListenersAdReceived(null);
      return bool2;
    }
  }
  
  protected AdlantisAdsModel getAdsModel()
  {
    return this.adModel;
  }
  
  protected InputStream inputStreamForHttpUri(Uri paramUri, String paramString1, String paramString2)
    throws IOException, ClientProtocolException
  {
    HttpRequestInterceptor local1 = new HttpRequestInterceptor()
    {
      public void process(HttpRequest paramAnonymousHttpRequest, HttpContext paramAnonymousHttpContext)
        throws HttpException, IOException
      {
        AuthState localAuthState = (AuthState)paramAnonymousHttpContext.getAttribute("http.auth.target-scope");
        CredentialsProvider localCredentialsProvider = (CredentialsProvider)paramAnonymousHttpContext.getAttribute("http.auth.credentials-provider");
        HttpHost localHttpHost = (HttpHost)paramAnonymousHttpContext.getAttribute("http.target_host");
        if (localAuthState.getAuthScheme() == null)
        {
          Credentials localCredentials = localCredentialsProvider.getCredentials(new AuthScope(localHttpHost.getHostName(), localHttpHost.getPort()));
          if (localCredentials != null)
          {
            localAuthState.setAuthScheme(new BasicScheme());
            localAuthState.setCredentials(localCredentials);
          }
        }
      }
    };
    HttpHost localHttpHost = new HttpHost(paramUri.getHost(), paramUri.getPort(), paramUri.getScheme());
    AbstractHttpClient localAbstractHttpClient = httpClientFactory();
    localAbstractHttpClient.addRequestInterceptor(local1, 0);
    localAbstractHttpClient.getCredentialsProvider().setCredentials(new AuthScope(localHttpHost.getHostName(), localHttpHost.getPort()), new UsernamePasswordCredentials(paramString1, paramString2));
    String str = paramUri.toString();
    log_d(str);
    return localAbstractHttpClient.execute(new HttpGet(str)).getEntity().getContent();
  }
  
  protected InputStream inputStreamForUri(Context paramContext, Uri paramUri)
    throws IOException, ClientProtocolException
  {
    InputStream localInputStream = null;
    if (isHttp(paramUri)) {}
    for (localInputStream = inputStreamForHttpUri(paramUri, "", "");; localInputStream = ADLAssetUtils.inputStreamFromAssetUri(paramContext, paramUri)) {
      do
      {
        return localInputStream;
      } while ((!isFile(paramUri)) || (!ADLAssetUtils.isAssetUri(paramUri)));
    }
  }
  
  public boolean isFile(Uri paramUri)
  {
    return paramUri.getScheme().equals("file");
  }
  
  public boolean isHttp(Uri paramUri)
  {
    if ((paramUri.getScheme().equals("http")) || (paramUri.getScheme().equals("https"))) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public void notifyListenersAdReceived(AdRequestNotifier paramAdRequestNotifier)
  {
    this.listeners.notifyListenersAdReceived(paramAdRequestNotifier);
  }
  
  public void notifyListenersFailedToReceiveAd(AdRequestNotifier paramAdRequestNotifier)
  {
    this.listeners.notifyListenersFailedToReceiveAd(paramAdRequestNotifier);
  }
  
  public void removeRequestListener(AdRequestListener paramAdRequestListener)
  {
    this.listeners.removeRequestListener(paramAdRequestListener);
  }
  
  public static abstract interface AdRequestManagerCallback
  {
    public abstract void adsLoaded();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdRequest
 * JD-Core Version:    0.7.0.1
 */