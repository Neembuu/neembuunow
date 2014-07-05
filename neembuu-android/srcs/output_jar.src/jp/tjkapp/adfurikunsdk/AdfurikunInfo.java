package jp.tjkapp.adfurikunsdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

class AdfurikunInfo
{
  public String bg_color = "ffffff";
  public long cycle_time = 30L;
  public ArrayList<AdInfo> infoArray = new ArrayList();
  private Random random = new Random();
  public boolean ta_off = false;
  private JSONObject weight_total = new JSONObject();
  
  public AdInfoForWebView getOtherAdHtml(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString3;
    if (!this.weight_total.has(str)) {
      str = AdfurikunConstants.DEFAULT_LOCALE;
    }
    int i = this.infoArray.size();
    int j = this.random.nextInt(i);
    int k = j;
    if (k >= i) {}
    label207:
    for (int m = 0;; m++)
    {
      AdInfoForWebView localAdInfoForWebView;
      if (m >= j) {
        localAdInfoForWebView = getRandomAdHtml(paramString3);
      }
      for (;;)
      {
        return localAdInfoForWebView;
        AdInfo localAdInfo1 = (AdInfo)this.infoArray.get(k);
        if ((localAdInfo1.weight.has(str)) && ((!localAdInfo1.adnetwork_key.equals(paramString1)) || (!localAdInfo1.user_ad_id.equals(paramString2))))
        {
          localAdInfoForWebView = new AdInfoForWebView(localAdInfo1);
        }
        else
        {
          k++;
          break;
          AdInfo localAdInfo2 = (AdInfo)this.infoArray.get(m);
          if ((!localAdInfo2.weight.has(str)) || ((localAdInfo2.adnetwork_key.equals(paramString1)) && (localAdInfo2.user_ad_id.equals(paramString2)))) {
            break label207;
          }
          localAdInfoForWebView = new AdInfoForWebView(localAdInfo2);
        }
      }
    }
  }
  
  public AdInfoForWebView getRandomAdHtml(String paramString)
  {
    int i = 0;
    String str = paramString;
    if (!this.weight_total.has(str)) {
      str = AdfurikunConstants.DEFAULT_LOCALE;
    }
    if (this.weight_total.has(str)) {}
    try
    {
      int i2 = this.weight_total.getInt(str);
      i = i2;
    }
    catch (JSONException localJSONException2)
    {
      label43:
      int j;
      int k;
      int m;
      int n;
      break label43;
    }
    if (i > 0)
    {
      j = this.random.nextInt(i);
      k = 0;
      m = this.infoArray.size();
    }
    for (n = 0;; n++)
    {
      if (n >= m) {}
      AdInfo localAdInfo;
      for (AdInfoForWebView localAdInfoForWebView = null;; localAdInfoForWebView = new AdInfoForWebView(localAdInfo))
      {
        return localAdInfoForWebView;
        localAdInfo = (AdInfo)this.infoArray.get(n);
        if (localAdInfo.weight.has(str)) {}
        try
        {
          int i1 = localAdInfo.weight.getInt(str);
          k += i1;
        }
        catch (JSONException localJSONException1)
        {
          label129:
          break label129;
        }
        if (k - 1 < j) {
          break;
        }
      }
    }
  }
  
  public void initCalc()
  {
    int i = this.infoArray.size();
    int j = 0;
    if (j >= i) {
      return;
    }
    AdInfo localAdInfo = (AdInfo)this.infoArray.get(j);
    Iterator localIterator = localAdInfo.weight.keys();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        j++;
        break;
      }
      String str = (String)localIterator.next();
      try
      {
        int k = localAdInfo.weight.getInt(str);
        if (this.weight_total.has(str)) {
          k += this.weight_total.getInt(str);
        }
        this.weight_total.put(str, k);
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
  }
  
  public static class AdInfo
  {
    public String adnetwork_key = "";
    public String html = "";
    public String is_text = "";
    public int tapchk_off_flg = 0;
    public String user_ad_id = "";
    public int wall_type = AdfurikunConstants.WALL_TYPE_NONE;
    public JSONObject weight = null;
  }
  
  public static class AdInfoForWebView
  {
    public String adnetwork_key;
    public String html;
    public String is_text;
    public int tapchk_off_flg;
    public String user_ad_id;
    public int wall_type;
    
    public AdInfoForWebView(AdfurikunInfo.AdInfo paramAdInfo)
    {
      this.adnetwork_key = paramAdInfo.adnetwork_key;
      this.user_ad_id = paramAdInfo.user_ad_id;
      this.html = paramAdInfo.html;
      this.is_text = paramAdInfo.is_text;
      this.wall_type = paramAdInfo.wall_type;
      this.tapchk_off_flg = paramAdInfo.tapchk_off_flg;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunInfo
 * JD-Core Version:    0.7.0.1
 */