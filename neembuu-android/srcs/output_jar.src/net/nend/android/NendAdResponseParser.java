package net.nend.android;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.SparseArray;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class NendAdResponseParser
{
  private static final String RESPONSE_ENCODING = "UTF-8";
  private final PackageManager mPackageManager;
  
  static
  {
    if (!NendAdResponseParser.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  NendAdResponseParser(Context paramContext)
  {
    if (paramContext == null) {
      throw new NullPointerException(NendStatus.ERR_INVALID_CONTEXT.getMsg());
    }
    this.mPackageManager = paramContext.getPackageManager();
  }
  
  private AdParameter getAppTargetingAd(JSONObject paramJSONObject)
    throws JSONException, NendException
  {
    JSONArray localJSONArray1 = paramJSONObject.getJSONArray("targeting_ads");
    int i = 0;
    int j = localJSONArray1.length();
    int k;
    label47:
    NendAdResponse.Builder localBuilder;
    if (i < j)
    {
      JSONObject localJSONObject = localJSONArray1.getJSONObject(i);
      JSONArray localJSONArray2 = localJSONObject.getJSONArray("conditions");
      k = 0;
      int m = localJSONArray2.length();
      if (k < m) {
        if (isTarget(localJSONArray2.getJSONArray(k)))
        {
          localBuilder = new NendAdResponse.Builder().setViewType(AdParameter.ViewType.ADVIEW).setImageUrl(localJSONObject.getString("image_url")).setClickUrl(localJSONObject.getString("click_url"));
          if (!paramJSONObject.isNull("reload")) {
            localBuilder.setReloadIntervalInSeconds(paramJSONObject.getInt("reload"));
          }
        }
      }
    }
    for (Object localObject = localBuilder.build();; localObject = getNormalAd(paramJSONObject))
    {
      return localObject;
      k++;
      break label47;
      i++;
      break;
      if (paramJSONObject.isNull("default_ad")) {
        throw new NendException(NendStatus.ERR_OUT_OF_STOCK);
      }
    }
  }
  
  private AdParameter getNormalAd(JSONObject paramJSONObject)
    throws JSONException
  {
    JSONObject localJSONObject = paramJSONObject.getJSONObject("default_ad");
    NendAdResponse.Builder localBuilder = new NendAdResponse.Builder().setViewType(AdParameter.ViewType.ADVIEW).setImageUrl(localJSONObject.getString("image_url")).setClickUrl(localJSONObject.getString("click_url"));
    if (!paramJSONObject.isNull("reload")) {
      localBuilder.setReloadIntervalInSeconds(paramJSONObject.getInt("reload"));
    }
    return localBuilder.build();
  }
  
  private AdParameter getWebViewAd(JSONObject paramJSONObject)
    throws JSONException
  {
    return new NendAdResponse.Builder().setViewType(AdParameter.ViewType.WEBVIEW).setWebViewUrl(paramJSONObject.getString("web_view_url")).build();
  }
  
  private boolean isTarget(JSONArray paramJSONArray)
    throws JSONException
  {
    boolean bool = false;
    assert (paramJSONArray != null);
    int i = 0;
    int j = paramJSONArray.length();
    JSONObject localJSONObject;
    int k;
    for (;;)
    {
      if (i < j)
      {
        localJSONObject = paramJSONArray.getJSONObject(i);
        k = localJSONObject.getInt("logical_operator");
        if (k == 1) {
          try
          {
            this.mPackageManager.getPackageInfo(localJSONObject.getString("url_scheme"), 1);
            i++;
          }
          catch (PackageManager.NameNotFoundException localNameNotFoundException2) {}
        }
      }
    }
    for (;;)
    {
      return bool;
      if (k == 2)
      {
        try
        {
          this.mPackageManager.getPackageInfo(localJSONObject.getString("url_scheme"), 1);
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException1) {}
        break;
        bool = true;
      }
    }
  }
  
  AdParameter parseResponse(String paramString)
  {
    if (paramString != null) {}
    try
    {
      if (paramString.length() == 0) {
        throw new IllegalArgumentException(NendStatus.ERR_INVALID_RESPONSE.getMsg());
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      if (!$assertionsDisabled)
      {
        throw new AssertionError();
        localJSONObject = new JSONObject(URLDecoder.decode(paramString, "UTF-8"));
        if (localJSONObject.getInt("status_code") == NendStatus.SUCCESS.getCode()) {
          break label140;
        }
        throw new NendException(NendStatus.ERR_INVALID_AD_STATUS, "Ad status : " + localJSONObject.getInt("status_code") + ", Message : " + localJSONObject.getString("message"));
      }
    }
    catch (JSONException localJSONException)
    {
      NendLog.w(NendStatus.ERR_FAILED_TO_PARSE, localJSONException);
      localObject = null;
      return localObject;
      ResponseType localResponseType = ResponseType.valueOf(localJSONObject.getInt("response_type"));
      switch (1.$SwitchMap$net$nend$android$NendAdResponseParser$ResponseType[localResponseType.ordinal()])
      {
      default: 
        throw new NendException(NendStatus.ERR_INVALID_RESPONSE_TYPE);
      }
    }
    catch (NendException localNendException)
    {
      for (;;)
      {
        JSONObject localJSONObject;
        NendLog.w(NendStatus.ERR_FAILED_TO_PARSE, localNendException);
        continue;
        Object localObject = getNormalAd(localJSONObject);
        continue;
        localObject = getWebViewAd(localJSONObject);
        continue;
        AdParameter localAdParameter = getAppTargetingAd(localJSONObject);
        localObject = localAdParameter;
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;)
      {
        label140:
        NendLog.w(NendStatus.ERR_FAILED_TO_PARSE, localIllegalArgumentException);
      }
    }
  }
  
  private static enum ResponseType
  {
    private static final SparseArray<ResponseType> intToEnum;
    private int type;
    
    static
    {
      NORMAL = new ResponseType("NORMAL", 1, 1);
      WEB_VIEW = new ResponseType("WEB_VIEW", 2, 2);
      APP_TARGETING = new ResponseType("APP_TARGETING", 3, 3);
      ResponseType[] arrayOfResponseType1 = new ResponseType[4];
      arrayOfResponseType1[0] = UNSUPPORTED;
      arrayOfResponseType1[1] = NORMAL;
      arrayOfResponseType1[2] = WEB_VIEW;
      arrayOfResponseType1[3] = APP_TARGETING;
      $VALUES = arrayOfResponseType1;
      intToEnum = new SparseArray();
      for (ResponseType localResponseType : values()) {
        intToEnum.put(localResponseType.type, localResponseType);
      }
    }
    
    private ResponseType(int paramInt)
    {
      this.type = paramInt;
    }
    
    private static ResponseType valueOf(int paramInt)
    {
      return (ResponseType)intToEnum.get(paramInt, UNSUPPORTED);
    }
  }
  
  private static final class JsonParam
  {
    private static final String CLICK_URL = "click_url";
    private static final String CONDITIONS = "conditions";
    private static final String DEFAULT_AD = "default_ad";
    private static final String IMAGE_URL = "image_url";
    private static final String LOGICAL_OPERATOR = "logical_operator";
    private static final String MESSAGE = "message";
    private static final String PACKAGE_NAME = "url_scheme";
    private static final String RELOAD = "reload";
    private static final String RESPONSE_TYPE = "response_type";
    private static final String STATUS_CODE = "status_code";
    private static final String TARGETING_ADS = "targeting_ads";
    private static final String WEB_VIEW_URL = "web_view_url";
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendAdResponseParser
 * JD-Core Version:    0.7.0.1
 */