package jp.adlantis.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.io.IOException;
import java.net.MalformedURLException;
import jp.adlantis.android.utils.ADLStringUtils;
import jp.adlantis.android.utils.AsyncImageLoader;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.HttpContext;

public class AdManager
{
  private static String DEBUG_TASK = "AdManager";
  private long _adDisplayInterval = 10000L;
  private long _adFetchInterval = 60000L;
  private AdNetworkConnection _adNetworkConnection = createConnection();
  private AsyncImageLoader _asyncImageLoader = new AsyncImageLoader();
  private boolean _connectionChangeReceiverRegistered = false;
  private String _conversionTag = null;
  private boolean _conversionTagSent = false;
  AdServiceManager adServiceManager = new AdServiceManager();
  private AdService.TargetingParams targetingParams = new AdService.TargetingParams();
  
  protected AdManager()
  {
    addService(new ADLAdService(null));
  }
  
  public static AdManager getInstance()
  {
    return AdManagerHolder.INSTANCE;
  }
  
  private void handleHttpClickRequest(String paramString, final AdManagerRedirectUrlProcessedCallback paramAdManagerRedirectUrlProcessedCallback)
  {
    final String str = this._adNetworkConnection.buildCompleteHttpUri(null, paramString);
    Log.d(DEBUG_TASK, "handleHttpClickRequest=" + str);
    new Thread()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAdManagerRedirectUrlProcessedCallback != null) {
          paramAdManagerRedirectUrlProcessedCallback.redirectProcessed((Uri)paramAnonymousMessage.obj);
        }
      }
    }
    {
      public boolean isRedirectRequested(HttpResponse paramAnonymousHttpResponse, HttpContext paramAnonymousHttpContext)
      {
        String str = null;
        int i = paramAnonymousHttpResponse.getStatusLine().getStatusCode();
        if ((i >= 300) && (i < 400))
        {
          Header[] arrayOfHeader = paramAnonymousHttpResponse.getHeaders("Location");
          if (arrayOfHeader.length > 0)
          {
            str = arrayOfHeader[0].getValue();
            Log.d(AdManager.DEBUG_TASK, "location=" + str);
          }
        }
        if (str == null)
        {
          HttpUriRequest localHttpUriRequest = (HttpUriRequest)paramAnonymousHttpContext.getAttribute("http.request");
          HttpHost localHttpHost = (HttpHost)paramAnonymousHttpContext.getAttribute("http.target_host");
          str = localHttpHost.toURI() + localHttpUriRequest.getURI();
        }
        Message localMessage = this.val$handler.obtainMessage(0, Uri.parse(str));
        this.val$handler.sendMessage(localMessage);
        return false;
      }
    }
    {
      public void run()
      {
        try
        {
          AbstractHttpClient localAbstractHttpClient = NetworkRequest.httpClientFactory();
          localAbstractHttpClient.setRedirectHandler(this.val$redirectHandler);
          localAbstractHttpClient.execute(new HttpGet(str));
          return;
        }
        catch (MalformedURLException localMalformedURLException)
        {
          for (;;)
          {
            Log.e(AdManager.DEBUG_TASK, localMalformedURLException.toString());
          }
        }
        catch (IOException localIOException)
        {
          for (;;)
          {
            Log.e(AdManager.DEBUG_TASK, localIOException.toString());
          }
        }
      }
    }.start();
  }
  
  public static boolean isGreeSdk()
  {
    return GreeApiDelegator.greePlatformAvailable();
  }
  
  public static boolean isNetworkAvailable(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (localConnectivityManager == null) {}
    for (;;)
    {
      boolean bool1 = false;
      label17:
      return bool1;
      try
      {
        NetworkInfo[] arrayOfNetworkInfo = localConnectivityManager.getAllNetworkInfo();
        if (arrayOfNetworkInfo == null) {
          continue;
        }
        for (int i = 0; i < arrayOfNetworkInfo.length; i++)
        {
          boolean bool2 = arrayOfNetworkInfo[i].isConnected();
          if (bool2)
          {
            bool1 = true;
            break label17;
          }
        }
      }
      catch (Exception localException)
      {
        Log.e(DEBUG_TASK, localException.toString());
      }
    }
  }
  
  public static void main(String[] paramArrayOfString) {}
  
  private void sendConversionTagInternal(final Context paramContext, final String paramString, final boolean paramBoolean)
  {
    registerConnectionChangeReceiver(paramContext);
    if (!paramString.equals(this._conversionTag))
    {
      this._conversionTag = paramString;
      this._conversionTagSent = false;
    }
    if (!this._conversionTagSent) {
      new Thread()
      {
        public void run()
        {
          try
          {
            AbstractHttpClient localAbstractHttpClient = NetworkRequest.httpClientFactory();
            String str = AdManager.this._adNetworkConnection.conversionTagRequestUri(paramContext, paramString, paramBoolean).toString();
            Log.d(AdManager.DEBUG_TASK, "sendConversionTag url=" + str);
            int i = localAbstractHttpClient.execute(new HttpGet(str)).getStatusLine().getStatusCode();
            if ((i >= 300) && (i < 501)) {
              AdManager.access$202(AdManager.this, true);
            }
            return;
          }
          catch (MalformedURLException localMalformedURLException)
          {
            for (;;)
            {
              Log.e(AdManager.DEBUG_TASK, "sendConversionTag exception=" + localMalformedURLException.toString());
            }
          }
          catch (IOException localIOException)
          {
            for (;;)
            {
              Log.e(AdManager.DEBUG_TASK, "sendConversionTag exception=" + localIOException.toString());
            }
          }
        }
      }.start();
    }
  }
  
  protected static void setInstance(AdManager paramAdManager)
  {
    AdManagerHolder.INSTANCE = paramAdManager;
  }
  
  public long adDisplayInterval()
  {
    return this._adDisplayInterval;
  }
  
  public long adFetchInterval()
  {
    return this._adFetchInterval;
  }
  
  public String adTapUrlCompleteString(AdlantisAd paramAdlantisAd)
  {
    String str = paramAdlantisAd.tapUrlString();
    if ((paramAdlantisAd.isWebLink()) && (ADLStringUtils.isHttpUrl(str))) {
      str = this._adNetworkConnection.buildCompleteHttpUri(null, str);
    }
    return str;
  }
  
  public void addService(AdService paramAdService)
  {
    this.adServiceManager.addService(paramAdService);
  }
  
  public AsyncImageLoader asyncImageLoader()
  {
    return this._asyncImageLoader;
  }
  
  public String byline()
  {
    return "Ads by AdLantis";
  }
  
  AdNetworkConnection createConnection()
  {
    return new AdLantisConnection();
  }
  
  public AdService getActiveAdService(Context paramContext)
  {
    return this.adServiceManager.getActiveAdService(paramContext);
  }
  
  public AdNetworkConnection getAdNetworkConnection()
  {
    return this._adNetworkConnection;
  }
  
  public ADLAdService getAdlAdService()
  {
    return this.adServiceManager.getAdlAdService();
  }
  
  public InternationalAdService getInternationalAdService()
  {
    return this.adServiceManager.getInternationalAdService();
  }
  
  public String getPublisherID()
  {
    return this.adServiceManager.getAdlAdService().getPublisherId();
  }
  
  public AdService.TargetingParams getTargetingParam()
  {
    return this.targetingParams;
  }
  
  public String gitSha()
  {
    return "6eb3350";
  }
  
  public void handleClickRequest(AdlantisAd paramAdlantisAd, final AdManagerRedirectUrlProcessedCallback paramAdManagerRedirectUrlProcessedCallback)
  {
    if (paramAdManagerRedirectUrlProcessedCallback != null)
    {
      if (!paramAdlantisAd.shouldHandleRedirect()) {
        break label21;
      }
      handleHttpClickRequest(paramAdlantisAd.tapUriRedirect(), paramAdManagerRedirectUrlProcessedCallback);
    }
    for (;;)
    {
      return;
      label21:
      Handler local5 = new Handler(Looper.getMainLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          if (paramAdManagerRedirectUrlProcessedCallback != null) {
            paramAdManagerRedirectUrlProcessedCallback.redirectProcessed((Uri)paramAnonymousMessage.obj);
          }
        }
      };
      local5.sendMessage(local5.obtainMessage(0, Uri.parse(adTapUrlCompleteString(paramAdlantisAd))));
    }
  }
  
  boolean hasTestAdRequestUrls()
  {
    return this._adNetworkConnection.hasTestAdRequestUrls();
  }
  
  public String keywords()
  {
    return this.targetingParams.getKeywords();
  }
  
  protected void onNetworkAvailable(Context paramContext)
  {
    if ((this._conversionTag != null) && (!this._conversionTagSent)) {
      sendConversionTag(paramContext, this._conversionTag);
    }
  }
  
  public String publisherIDMetadataKey()
  {
    return this._adNetworkConnection.publisherIDMetadataKey();
  }
  
  protected void registerConnectionChangeReceiver(Context paramContext)
  {
    if (!this._connectionChangeReceiverRegistered)
    {
      IntentFilter localIntentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
      paramContext.getApplicationContext().registerReceiver(new ConnectionChangeReceiver(null), localIntentFilter);
      this._connectionChangeReceiverRegistered = true;
    }
  }
  
  public String sdkBuild()
  {
    return "1716";
  }
  
  public String sdkDescription()
  {
    return "AdLantis SDK";
  }
  
  public String sdkFullVersion()
  {
    return sdkDescription() + " " + sdkVersion() + " (" + gitSha() + ")";
  }
  
  public String sdkVersion()
  {
    return "1.4.0";
  }
  
  public void sendConversionTag(Context paramContext, String paramString)
  {
    sendConversionTagInternal(paramContext, paramString, false);
  }
  
  public void sendConversionTagTest(Context paramContext, String paramString)
  {
    sendConversionTagInternal(paramContext, paramString, true);
  }
  
  public void setAdDisplayInterval(long paramLong)
  {
    this._adDisplayInterval = paramLong;
  }
  
  public void setAdFetchInterval(long paramLong)
  {
    this._adFetchInterval = paramLong;
  }
  
  public void setAdNetworkConnection(AdNetworkConnection paramAdNetworkConnection)
  {
    this._adNetworkConnection = paramAdNetworkConnection;
  }
  
  public void setCountry(String paramString)
  {
    this.targetingParams.setCountry(paramString);
  }
  
  public void setGapPublisherID(String paramString)
  {
    setPublisherID(paramString);
  }
  
  public void setHost(String paramString)
  {
    this._adNetworkConnection.setHost(paramString);
  }
  
  public void setKeywords(String paramString)
  {
    this.targetingParams.setKeywords(paramString);
  }
  
  public void setLocation(Location paramLocation)
  {
    this.targetingParams.setLocation(paramLocation);
  }
  
  public void setPublisherID(String paramString)
  {
    this.adServiceManager.getAdlAdService().setPublisherId(paramString);
  }
  
  public void setTestAdRequestUrls(String[] paramArrayOfString)
  {
    Log.d(DEBUG_TASK, "setting test AdRequestUrls");
    this._adNetworkConnection.setTestAdRequestUrls(paramArrayOfString);
  }
  
  private class ConnectionChangeReceiver
    extends BroadcastReceiver
  {
    private ConnectionChangeReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (AdManager.isNetworkAvailable(paramContext)) {
        AdManager.this.onNetworkAvailable(paramContext);
      }
    }
  }
  
  public static abstract interface AdManagerRedirectUrlProcessedCallback
  {
    public abstract void redirectProcessed(Uri paramUri);
  }
  
  protected static class AdManagerHolder
  {
    protected static AdManager INSTANCE;
    
    static
    {
      if (AdManager.isGreeSdk()) {}
      for (Object localObject = new GreeAdManager();; localObject = new AdManager())
      {
        INSTANCE = (AdManager)localObject;
        return;
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdManager
 * JD-Core Version:    0.7.0.1
 */