package jp.adlantis.android.mediation;

import android.content.Context;
import android.util.Log;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import jp.adlantis.android.AdRequest;
import jp.adlantis.android.AdlantisAd;
import jp.adlantis.android.AdlantisAdsModel;
import jp.adlantis.android.NetworkRequest;
import jp.adlantis.android.utils.AdlantisUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;

public class AdMediationRequest
  extends AdRequest
{
  public AdMediationRequest(AdlantisAdsModel paramAdlantisAdsModel)
  {
    super(paramAdlantisAdsModel);
  }
  
  public static boolean sendGetRequest(String paramString)
  {
    boolean bool = false;
    try
    {
      int i = NetworkRequest.httpClientFactory().execute(new HttpGet(paramString)).getStatusLine().getStatusCode();
      if ((i >= 200) && (i < 400)) {
        bool = true;
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Log.e(DEBUG_TASK, "sendGetRequest exception=" + localException.toString());
      }
    }
    return bool;
  }
  
  public boolean doAdRequest(Context paramContext, Map<String, String> paramMap)
  {
    boolean bool = false;
    try
    {
      String str1 = AdlantisUtils.convertInputToString(inputStreamForUri(paramContext, adRequestUri(paramContext, paramMap)));
      AdlantisAd[] arrayOfAdlantisAd = AdlantisAd.adsFromJSONString(str1);
      if (arrayOfAdlantisAd == null) {
        arrayOfAdlantisAd = new AdlantisAd[0];
      }
      getAdsModel().setAds(arrayOfAdlantisAd);
      log_d(arrayOfAdlantisAd.length + " ads loaded");
      if (arrayOfAdlantisAd.length > 0) {
        bool = true;
      }
      if (bool) {
        getAdsModel().setNetworkParameters(null);
      }
      for (;;)
      {
        String str2 = AdlantisAd.errorMessageFromJSONString(str1);
        getAdsModel().setErrorMessage(str2);
        if (!bool) {
          break label203;
        }
        notifyListenersAdReceived(null);
        return bool;
        AdMediationNetworkParameters[] arrayOfAdMediationNetworkParameters = AdMediationNetworkParameters.networksFromJSONString(str1);
        getAdsModel().setNetworkParameters(arrayOfAdMediationNetworkParameters);
        if (arrayOfAdMediationNetworkParameters == null) {
          break;
        }
        log_d(arrayOfAdMediationNetworkParameters.length + " networks loaded");
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
      for (;;)
      {
        log_e(localMalformedURLException.toString());
        continue;
        log_d("no networks loaded");
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        log_e(localIOException.toString());
        continue;
        label203:
        notifyListenersFailedToReceiveAd(null);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.mediation.AdMediationRequest
 * JD-Core Version:    0.7.0.1
 */