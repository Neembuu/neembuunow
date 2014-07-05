package jp.adlantis.android;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import jp.adlantis.android.utils.ADLStringUtils;
import jp.adlantis.android.utils.AdlantisUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdlantisAd
  extends HashMap<String, Object>
  implements Map<String, Object>, Serializable
{
  public static final int ADTYPE_BANNER = 1;
  public static final int ADTYPE_TEXT = 2;
  private static final long IMPRESSION_COUNT_INTERVAL_NANOSECONDS = 2000000000L;
  private static final String LOG_TAG = "AdlantisAd";
  private static final long NANOSECONDS_IN_SECOND = 1000000000L;
  private static final long serialVersionUID = -94783938293849L;
  protected Handler _impressionHandler;
  protected boolean _isBeingViewed;
  protected boolean _sendImpressionCountFailed = false;
  protected boolean _sendingCountExpand;
  protected boolean _sendingImpressionCount;
  protected boolean _sentCountExpand;
  protected boolean _sentImpressionCount;
  protected long _viewStartTime;
  protected long _viewedTime;
  
  public AdlantisAd(HashMap<String, Object> paramHashMap)
  {
    super(paramHashMap);
  }
  
  public AdlantisAd(JSONObject paramJSONObject)
  {
    this(AdlantisUtils.jsonObjectToHashMap(paramJSONObject));
  }
  
  public static AdlantisAd[] adsFromJSONInputStream(InputStream paramInputStream)
  {
    return adsFromJSONString(AdlantisUtils.convertInputToString(paramInputStream));
  }
  
  public static AdlantisAd[] adsFromJSONString(String paramString)
  {
    AdlantisAd[] arrayOfAdlantisAd = null;
    try
    {
      JSONArray localJSONArray = extractJSONAdArray(paramString);
      if (localJSONArray != null)
      {
        arrayOfAdlantisAd = new AdlantisAd[localJSONArray.length()];
        for (int i = 0; i < localJSONArray.length(); i++) {
          arrayOfAdlantisAd[i] = new AdlantisAd(localJSONArray.getJSONObject(i));
        }
      }
      Log.i("AdlantisAd", "Adlantis: no ads received (this is not an error)");
      return arrayOfAdlantisAd;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        logError("exception parsing JSON data " + localException);
      }
    }
  }
  
  private Uri buildURIFrom(Context paramContext, String paramString)
  {
    if (paramString == null) {}
    Uri localUri1;
    for (Uri localUri2 = null;; localUri2 = AdManager.getInstance().getAdNetworkConnection().defaultRequestBuilder(paramContext, localUri1).build())
    {
      return localUri2;
      localUri1 = Uri.parse(paramString);
    }
  }
  
  private Uri buildURIFromProperty(Context paramContext, String paramString)
  {
    return buildURIFrom(paramContext, (String)get(paramString));
  }
  
  private int currentOrientation(View paramView)
  {
    return paramView.getResources().getConfiguration().orientation;
  }
  
  public static String errorMessageFromJSONString(String paramString)
  {
    Object localObject = null;
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString).getJSONObject("adlantis_ads");
      if (localJSONObject != null)
      {
        String str = localJSONObject.getJSONObject("error").getString("description");
        localObject = str;
      }
    }
    catch (JSONException localJSONException)
    {
      label36:
      break label36;
    }
    return localObject;
  }
  
  private static JSONArray extractJSONAdArray(String paramString)
  {
    try
    {
      localJSONArray1 = new JSONArray(paramString);
      if (localJSONArray1 == null)
      {
        for (;;)
        {
          try
          {
            localJSONObject1 = new JSONObject(paramString);
          }
          catch (JSONException localJSONException2)
          {
            JSONObject localJSONObject1;
            JSONObject localJSONObject3;
            JSONArray localJSONArray3;
            localJSONArray2 = localJSONArray1;
            continue;
            JSONObject localJSONObject2 = localJSONObject1;
            continue;
          }
          try
          {
            localJSONObject3 = localJSONObject1.getJSONObject("adlantis_ads");
            localJSONObject2 = localJSONObject3;
            if (localJSONObject2 == null) {
              continue;
            }
          }
          catch (JSONException localJSONException1)
          {
            localJSONObject2 = localJSONObject1;
          }
        }
        localJSONArray3 = localJSONObject2.getJSONArray("ads");
        localJSONArray2 = localJSONArray3;
        return localJSONArray2;
      }
    }
    catch (JSONException localJSONException3)
    {
      for (;;)
      {
        JSONArray localJSONArray1 = null;
        continue;
        JSONArray localJSONArray2 = localJSONArray1;
      }
    }
  }
  
  private boolean hasHighResolutionDisplay(Context paramContext)
  {
    return AdlantisUtils.hasHighResolutionDisplay(paramContext);
  }
  
  private static String iphone_orientationKey(int paramInt)
  {
    if (orientationIsLandscape(paramInt)) {}
    for (String str = "iphone_landscape";; str = "iphone_portrait") {
      return str;
    }
  }
  
  protected static void logDebug(String paramString)
  {
    Log.d("AdlantisAd", paramString);
  }
  
  protected static void logError(String paramString)
  {
    Log.e("AdlantisAd", paramString);
  }
  
  protected static void logWarn(String paramString)
  {
    Log.w("AdlantisAd", paramString);
  }
  
  private static boolean orientationIsLandscape(int paramInt)
  {
    if (paramInt == 2) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  private static String orientationKey(int paramInt)
  {
    if (orientationIsLandscape(paramInt)) {}
    for (String str = "landscape";; str = "portrait") {
      return str;
    }
  }
  
  private void setSendImpressionCountFailed(boolean paramBoolean)
  {
    this._sendImpressionCountFailed = paramBoolean;
    this._sendingImpressionCount = false;
  }
  
  private void setSendingImpressionCount(boolean paramBoolean)
  {
    this._sendingImpressionCount = paramBoolean;
  }
  
  private void setSentImpressionCount(boolean paramBoolean)
  {
    this._sentImpressionCount = paramBoolean;
    this._sendingImpressionCount = false;
  }
  
  public int adType()
  {
    if ("sp_banner".compareTo((String)get("type")) == 0) {}
    for (int i = 1;; i = 2) {
      return i;
    }
  }
  
  public String altTextString(int paramInt)
  {
    String str = null;
    Map localMap = bannerInfoForOrientation(paramInt);
    if (localMap != null)
    {
      str = (String)localMap.get("alt");
      if (str != null) {
        str = Uri.decode(str);
      }
    }
    return str;
  }
  
  public String altTextString(View paramView)
  {
    String str = null;
    Map localMap = bannerInfoForCurrentOrientation(paramView);
    if (localMap != null)
    {
      str = (String)localMap.get("alt");
      if (str != null) {
        str = Uri.decode(str);
      }
    }
    return str;
  }
  
  public Map<?, ?> bannerInfoForCurrentOrientation(View paramView)
  {
    Map localMap = null;
    if (adType() == 1) {
      localMap = bannerInfoForOrientation(currentOrientation(paramView));
    }
    return localMap;
  }
  
  public Map<?, ?> bannerInfoForOrientation(int paramInt)
  {
    Object localObject;
    if (adType() == 1)
    {
      localObject = (Map)get(orientationKey(paramInt));
      if (localObject == null) {
        localObject = get(iphone_orientationKey(paramInt));
      }
      if (!(localObject instanceof Map)) {}
    }
    for (Map localMap = (Map)localObject;; localMap = null) {
      return localMap;
    }
  }
  
  public String bannerURLForCurrentOrientation(View paramView)
  {
    return bannerURLForOrientation(currentOrientation(paramView), paramView.getContext());
  }
  
  public String bannerURLForOrientation(int paramInt, Context paramContext)
  {
    return bannerURLForOrientation(paramInt, hasHighResolutionDisplay(paramContext));
  }
  
  public String bannerURLForOrientation(int paramInt, boolean paramBoolean)
  {
    String str = null;
    if (adType() == 1)
    {
      Map localMap = bannerInfoForOrientation(paramInt);
      if (localMap != null)
      {
        if (paramBoolean) {
          str = (String)localMap.get("src_2x");
        }
        if (str == null) {
          str = (String)localMap.get("src");
        }
      }
    }
    return str;
  }
  
  protected void clearImpressionHandler()
  {
    this._impressionHandler = null;
  }
  
  public Uri countExpandUri(Context paramContext)
  {
    return buildURIFromProperty(paramContext, "count_expand");
  }
  
  public Uri countImpressionUri(Context paramContext)
  {
    return buildURIFromProperty(paramContext, "count_impression");
  }
  
  protected void doSendImpressionCountThread()
  {
    new Thread()
    {
      public void run()
      {
        boolean bool = AdlantisAd.this.sendRequestForProperty("count_impression", "sendImpressionCount");
        AdlantisAd.this.setSendingImpressionCount(false);
        if (bool) {
          AdlantisAd.this.setSentImpressionCount(true);
        }
        for (;;)
        {
          return;
          AdlantisAd.this.setSendImpressionCountFailed(true);
        }
      }
    }.start();
  }
  
  public boolean hasAdForOrientation(int paramInt)
  {
    int i = 1;
    if (adType() == 2) {}
    for (;;)
    {
      return i;
      int j;
      if (adType() == i)
      {
        if (bannerInfoForOrientation(paramInt) == null) {
          j = 0;
        }
      }
      else {
        j = 0;
      }
    }
  }
  
  public HashMap<String, Object> hashMapRepresentation()
  {
    return new HashMap(this);
  }
  
  protected AbstractHttpClient httpClientFactory()
  {
    return new DefaultHttpClient();
  }
  
  public String iconURL(View paramView)
  {
    return iconURL(hasHighResolutionDisplay(paramView.getContext()));
  }
  
  public String iconURL(boolean paramBoolean)
  {
    String str1 = null;
    String str2;
    if (adType() == 2)
    {
      Map localMap = (Map)get("iphone_icon");
      if (localMap != null)
      {
        if (paramBoolean) {
          str1 = (String)localMap.get("src_2x");
        }
        if (str1 == null) {
          str2 = (String)localMap.get("src");
        }
      }
    }
    for (;;)
    {
      return str2;
      str2 = str1;
      continue;
      str2 = null;
    }
  }
  
  public String imageURL(int paramInt, boolean paramBoolean)
  {
    String str = null;
    int i = adType();
    if (i == 2) {
      str = iconURL(paramBoolean);
    }
    for (;;)
    {
      return str;
      if (i == 1) {
        str = bannerURLForOrientation(paramInt, paramBoolean);
      }
    }
  }
  
  public String imageURL(View paramView)
  {
    return bannerURLForOrientation(currentOrientation(paramView), hasHighResolutionDisplay(paramView.getContext()));
  }
  
  protected long impressionCountIntervalMilliseconds()
  {
    return TimeUnit.NANOSECONDS.toMillis(impressionCountIntervalNanoseconds());
  }
  
  protected long impressionCountIntervalNanoseconds()
  {
    return 2000000000L;
  }
  
  protected boolean impressionCountIntervalPassed()
  {
    if (viewedTime() >= impressionCountIntervalNanoseconds()) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  boolean isRedirectingUrl(String paramString)
  {
    if ((ADLStringUtils.isHttpUrl(tapUrlString())) && (paramString.indexOf("url=") != -1)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public boolean isWebLink()
  {
    return "web".equals(linkType());
  }
  
  public String linkType()
  {
    return (String)get("link_type");
  }
  
  protected void sendImpressionCount()
  {
    if (!shouldSendImpressionCount()) {}
    for (;;)
    {
      return;
      setSendingImpressionCount(true);
      doSendImpressionCountThread();
    }
  }
  
  protected boolean sendRequestForProperty(String paramString1, String paramString2)
  {
    boolean bool = false;
    try
    {
      String str = buildURIFromProperty(null, paramString1).toString();
      if (str != null)
      {
        HttpGet localHttpGet = new HttpGet(str);
        int i = httpClientFactory().execute(localHttpGet).getStatusLine().getStatusCode();
        if ((i >= 200) && (i < 400)) {
          bool = true;
        } else {
          logError(paramString2 + " status=" + i);
        }
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
      logError(paramString2 + " exception=" + localMalformedURLException.toString());
    }
    catch (IOException localIOException)
    {
      logError(paramString2 + " exception=" + localIOException.toString());
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      logError(paramString2 + " OutOfMemoryError=" + localOutOfMemoryError.toString());
    }
    return bool;
  }
  
  public boolean shouldHandleRedirect()
  {
    if ((isRedirectingUrl(tapUrlString())) && (("appstore".equals(linkType())) || ("itunes".equals(linkType())))) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  protected boolean shouldSendImpressionCount()
  {
    if ((!this._sentImpressionCount) && (!this._sendingImpressionCount) && (!this._sendImpressionCountFailed)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public String tapUriRedirect()
  {
    Uri.Builder localBuilder = Uri.parse(tapUrlString()).buildUpon();
    localBuilder.appendQueryParameter("adlDoRedirect", "1");
    return localBuilder.toString();
  }
  
  public String tapUrlString()
  {
    return (String)get("href");
  }
  
  public String textAdString()
  {
    return Uri.decode((String)get("string"));
  }
  
  public String urlString()
  {
    return tapUrlString();
  }
  
  protected long viewedTime()
  {
    if (this._isBeingViewed)
    {
      long l = System.nanoTime();
      this._viewedTime += l - this._viewStartTime;
      this._viewStartTime = l;
    }
    return this._viewedTime;
  }
  
  public void viewingEnded()
  {
    if (!this._isBeingViewed) {
      logWarn("viewingEnded() called without matching viewingStarted()");
    }
    this._isBeingViewed = false;
    clearImpressionHandler();
    if (impressionCountIntervalPassed()) {
      sendImpressionCount();
    }
  }
  
  public void viewingStarted()
  {
    this._viewStartTime = System.nanoTime();
    this._isBeingViewed = true;
    if (shouldSendImpressionCount())
    {
      this._impressionHandler = new Handler(Looper.getMainLooper());
      Runnable local1 = new Runnable()
      {
        public void run()
        {
          if (AdlantisAd.this.impressionCountIntervalPassed()) {
            AdlantisAd.this.sendImpressionCount();
          }
        }
      };
      this._impressionHandler.postDelayed(local1, impressionCountIntervalMilliseconds());
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdlantisAd
 * JD-Core Version:    0.7.0.1
 */