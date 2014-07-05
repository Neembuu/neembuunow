package jp.adlantis.android;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import jp.adlantis.android.utils.AdlantisUtils;

public abstract class AdNetworkConnection
{
  protected String _conversionTagHost;
  protected String _conversionTagTestHost;
  private HashMap<String, String> _defaultParamMap;
  protected String _host;
  private int _testAdRequestUrlIndex;
  private String[] _testAdRequestUrls;
  
  private Uri adRequestURI_internal(AdManager paramAdManager, Context paramContext, Map<String, String> paramMap)
  {
    Uri.Builder localBuilder = defaultRequestBuilder(paramContext, null);
    localBuilder.scheme("http");
    localBuilder.authority(getHost());
    localBuilder.path("/sp/load_app_ads");
    localBuilder.appendQueryParameter("callbackid", "0");
    localBuilder.appendQueryParameter("zid", paramAdManager.getPublisherID());
    localBuilder.appendQueryParameter("adl_app_flg", "1");
    if (paramAdManager.keywords() != null) {
      localBuilder.appendQueryParameter("keywords", paramAdManager.keywords());
    }
    if (paramMap != null) {
      AdlantisUtils.setUriParamsFromMap(localBuilder, paramMap);
    }
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
    localBuilder.appendQueryParameter("displaySize", localDisplayMetrics.widthPixels + "x" + localDisplayMetrics.heightPixels);
    localBuilder.appendQueryParameter("displayDensity", Float.toString(localDisplayMetrics.density));
    return localBuilder.build();
  }
  
  private Uri.Builder appendTargetingParameters(Uri.Builder paramBuilder)
  {
    AdService.TargetingParams localTargetingParams = AdManager.getInstance().getTargetingParam();
    String str1 = localTargetingParams.getCountry();
    if (str1 != null) {
      paramBuilder.appendQueryParameter("country", str1);
    }
    String str2 = localTargetingParams.getLocale();
    if (str2 != null) {
      paramBuilder.appendQueryParameter("locale", str2);
    }
    Location localLocation = localTargetingParams.getLocation();
    if (localLocation != null)
    {
      paramBuilder.appendQueryParameter("lat", Double.toString(localLocation.getLatitude()));
      paramBuilder.appendQueryParameter("lng", Double.toString(localLocation.getLongitude()));
    }
    return paramBuilder;
  }
  
  private HashMap<String, String> defaultParameters(Context paramContext)
  {
    try
    {
      HashMap localHashMap;
      if (this._defaultParamMap != null)
      {
        localHashMap = this._defaultParamMap;
      }
      else
      {
        this._defaultParamMap = new HashMap();
        if (paramContext != null) {
          this._defaultParamMap.put("appIdentifier", paramContext.getPackageName());
        }
        this._defaultParamMap.put("deviceClass", "android");
        String str1 = Build.VERSION.RELEASE;
        if (str1 != null) {
          this._defaultParamMap.put("deviceOsVersionFull", str1);
        }
        try
        {
          String str5 = NumberFormat.getNumberInstance().parse(str1).toString();
          this._defaultParamMap.put("deviceOsVersion", str5);
          String str2 = Build.MODEL;
          if (str2 != null)
          {
            if (str2.compareTo("sdk") == 0) {
              str2 = "simulator";
            }
            this._defaultParamMap.put("deviceFamily", str2);
          }
          if (Build.BRAND != null) {
            this._defaultParamMap.put("deviceBrand", Build.BRAND);
          }
          if (Build.DEVICE != null) {
            this._defaultParamMap.put("deviceName", Build.DEVICE);
          }
          String str3 = md5_uniqueID(paramContext);
          if (str3 != null) {
            this._defaultParamMap.put("udid", str3);
          }
          String str4 = GreeDsp.getUUID();
          if (str4 != null) {
            this._defaultParamMap.put("uuid", str4);
          }
          this._defaultParamMap.put("sdkVersion", sdkVersion());
          this._defaultParamMap.put("sdkBuild", sdkBuild());
          this._defaultParamMap.put("adlProtocolVersion", "3");
          localHashMap = this._defaultParamMap;
        }
        catch (ParseException localParseException)
        {
          for (;;)
          {
            localParseException.printStackTrace();
          }
        }
      }
      return localHashMap;
    }
    finally {}
  }
  
  public Uri adRequestUri(AdManager paramAdManager, Context paramContext, Map<String, String> paramMap)
  {
    Uri localUri;
    if ((this._testAdRequestUrls != null) && (this._testAdRequestUrls.length > 0))
    {
      adRequestURI_internal(paramAdManager, paramContext, paramMap);
      localUri = Uri.parse(this._testAdRequestUrls[this._testAdRequestUrlIndex]);
      this._testAdRequestUrlIndex = ((1 + this._testAdRequestUrlIndex) % this._testAdRequestUrls.length);
    }
    for (;;)
    {
      return localUri;
      localUri = adRequestURI_internal(paramAdManager, paramContext, paramMap);
    }
  }
  
  public String androidId(Context paramContext)
  {
    return Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
  }
  
  public Uri.Builder appendParameters(Uri.Builder paramBuilder)
  {
    return paramBuilder;
  }
  
  public String buildCompleteHttpUri(Context paramContext, String paramString)
  {
    return defaultRequestBuilder(paramContext, Uri.parse(paramString)).build().toString();
  }
  
  public Uri conversionTagRequestUri(Context paramContext, String paramString, boolean paramBoolean)
  {
    Uri.Builder localBuilder = defaultRequestBuilder(paramContext, null);
    localBuilder.scheme("http");
    if (paramBoolean)
    {
      localBuilder.authority(getConversionTagTestHost());
      localBuilder.path("/ctt");
    }
    for (;;)
    {
      localBuilder.appendQueryParameter("tid", paramString);
      localBuilder.appendQueryParameter("output", "js");
      return localBuilder.build();
      localBuilder.authority(getConversionTagHost());
      localBuilder.path("/sp/conv");
    }
  }
  
  public Uri.Builder defaultRequestBuilder(Context paramContext, Uri paramUri)
  {
    if (paramUri != null) {}
    for (Uri.Builder localBuilder = paramUri.buildUpon();; localBuilder = new Uri.Builder())
    {
      AdlantisUtils.setUriParamsFromMap(localBuilder, defaultParameters(paramContext));
      return appendParameters(appendTargetingParameters(localBuilder));
    }
  }
  
  protected String deviceId(Context paramContext)
  {
    try
    {
      String str2 = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
      str1 = str2;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        String str1 = null;
      }
    }
    return str1;
  }
  
  public String getConversionTagHost()
  {
    return this._conversionTagHost;
  }
  
  public String getConversionTagTestHost()
  {
    return this._conversionTagTestHost;
  }
  
  public String getHost()
  {
    return this._host;
  }
  
  public int getPort()
  {
    return 80;
  }
  
  boolean hasTestAdRequestUrls()
  {
    if (this._testAdRequestUrls != null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public String md5_uniqueID(Context paramContext)
  {
    String str1 = null;
    if (paramContext == null) {}
    for (;;)
    {
      return str1;
      String str2 = uniqueID(paramContext);
      if (str2 != null) {
        str1 = AdlantisUtils.md5(str2);
      }
    }
  }
  
  public abstract String publisherIDMetadataKey();
  
  public String sdkBuild()
  {
    return AdManager.getInstance().sdkBuild();
  }
  
  public String sdkVersion()
  {
    return AdManager.getInstance().sdkVersion();
  }
  
  public void setHost(String paramString)
  {
    this._host = paramString;
  }
  
  public void setTestAdRequestUrls(String[] paramArrayOfString)
  {
    this._testAdRequestUrls = paramArrayOfString;
  }
  
  public String uniqueID(Context paramContext)
  {
    String str = androidId(paramContext);
    if (str == null) {
      str = deviceId(paramContext);
    }
    return str;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdNetworkConnection
 * JD-Core Version:    0.7.0.1
 */