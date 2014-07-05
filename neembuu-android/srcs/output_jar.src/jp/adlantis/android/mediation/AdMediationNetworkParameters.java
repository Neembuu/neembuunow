package jp.adlantis.android.mediation;

import android.util.Log;
import java.io.InputStream;
import java.util.Map;
import jp.adlantis.android.utils.AdlantisUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class AdMediationNetworkParameters
{
  private static final String LOG_TAG = "AdMediationNetworkParameters";
  private Map<String, ?> parameters;
  
  public AdMediationNetworkParameters(Map<String, ?> paramMap)
  {
    this.parameters = paramMap;
  }
  
  public AdMediationNetworkParameters(JSONObject paramJSONObject)
  {
    this(AdlantisUtils.jsonObjectToHashMap(paramJSONObject));
  }
  
  public static AdMediationNetworkParameters[] networksFromJSONInputStream(InputStream paramInputStream)
  {
    return networksFromJSONString(AdlantisUtils.convertInputToString(paramInputStream));
  }
  
  public static AdMediationNetworkParameters[] networksFromJSONString(String paramString)
  {
    AdMediationNetworkParameters[] arrayOfAdMediationNetworkParameters = null;
    try
    {
      JSONArray localJSONArray = new JSONObject(paramString).getJSONArray("networks");
      if (localJSONArray != null)
      {
        arrayOfAdMediationNetworkParameters = new AdMediationNetworkParameters[localJSONArray.length()];
        for (int i = 0; i < localJSONArray.length(); i++) {
          arrayOfAdMediationNetworkParameters[i] = new AdMediationNetworkParameters(localJSONArray.getJSONObject(i));
        }
      }
      Log.i("AdMediationNetworkParameters", "Adlantis: no networks received (this is not an error)");
      label71:
      return arrayOfAdMediationNetworkParameters;
    }
    catch (Exception localException)
    {
      break label71;
    }
  }
  
  public String getCountRequestUrl()
  {
    return (String)this.parameters.get("count_req");
  }
  
  public String getCountTapUrl()
  {
    return (String)this.parameters.get("count_tap");
  }
  
  public String getNetworkName()
  {
    return (String)this.parameters.get("name");
  }
  
  public String getParameter(String paramString)
  {
    Map localMap = (Map)this.parameters.get("info");
    if (localMap == null) {}
    for (String str = null;; str = (String)localMap.get(paramString)) {
      return str;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.mediation.AdMediationNetworkParameters
 * JD-Core Version:    0.7.0.1
 */