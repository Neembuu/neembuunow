package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import java.util.Locale;

class AdfurikunAdCropsTask
  extends AsyncTask<Void, Void, String>
{
  private static final String ADCROPS_COUNT = "40";
  private static final String ADCROPS_EMPTY = "";
  private static final String ADCROPS_FORMAT = "json";
  private static final String ADCROPS_LOC = "1";
  private static final String ADCROPS_PAGE = "1";
  private static final String ADCROPS_PARAM_COUNT = "_count=";
  private static final String ADCROPS_PARAM_FORMAT = "_format=";
  private static final String ADCROPS_PARAM_I = "_i=";
  private static final String ADCROPS_PARAM_LANG = "_lang=";
  private static final String ADCROPS_PARAM_LOC = "_loc=";
  private static final String ADCROPS_PARAM_LOCALE = "_locale=";
  private static final String ADCROPS_PARAM_PAGE = "_page=";
  private static final String ADCROPS_PARAM_PL = "_pl=";
  private static final String ADCROPS_PARAM_SITE = "_site=";
  private static final String ADCROPS_URL = "http://t.adcrops.net/ad/p/txt?";
  private static final String ADCROPS_USER_AGENT = "8CHK.A/Android/2.2.0/4.2.2/IS03/SHI03/ja/";
  private Context mContext;
  private int mErr;
  private OnLoadListener mLoadListener;
  private AdfurikunLogUtil mLog;
  private String mURL;
  
  public AdfurikunAdCropsTask(OnLoadListener paramOnLoadListener, Context paramContext, String paramString, AdfurikunLogUtil paramAdfurikunLogUtil)
  {
    this.mLoadListener = paramOnLoadListener;
    this.mContext = paramContext;
    this.mLog = paramAdfurikunLogUtil;
    this.mErr = AdfurikunConstants.WEBAPI_NOERR;
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("http://t.adcrops.net/ad/p/txt?");
    localStringBuffer.append("_lang=");
    localStringBuffer.append(Locale.getDefault().getLanguage());
    localStringBuffer.append("&");
    localStringBuffer.append("_loc=");
    localStringBuffer.append("1");
    localStringBuffer.append("&");
    localStringBuffer.append("_site=");
    localStringBuffer.append(paramString);
    localStringBuffer.append("&");
    localStringBuffer.append("_page=");
    localStringBuffer.append("1");
    localStringBuffer.append("&");
    localStringBuffer.append("_count=");
    localStringBuffer.append("40");
    localStringBuffer.append("&");
    localStringBuffer.append("_format=");
    localStringBuffer.append("json");
    localStringBuffer.append("&");
    localStringBuffer.append("_locale=");
    localStringBuffer.append(Locale.getDefault());
    localStringBuffer.append("&");
    localStringBuffer.append("_pl=");
    localStringBuffer.append("");
    localStringBuffer.append("&");
    localStringBuffer.append("_i=");
    localStringBuffer.append("");
    this.mURL = localStringBuffer.toString();
  }
  
  protected String doInBackground(Void... paramVarArgs)
  {
    publishProgress(new Void[0]);
    String str = "";
    NetworkInfo localNetworkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
    AdfurikunApiAccessUtil.WebAPIResult localWebAPIResult;
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnected()))
    {
      localWebAPIResult = AdfurikunApiAccessUtil.callWebAPI(this.mURL, this.mLog, "8CHK.A/Android/2.2.0/4.2.2/IS03/SHI03/ja/");
      if (localWebAPIResult.return_code == 200) {
        str = localWebAPIResult.message;
      }
    }
    for (;;)
    {
      publishProgress(new Void[0]);
      return str;
      this.mErr = localWebAPIResult.return_code;
      continue;
      this.mErr = AdfurikunConstants.WEBAPI_CONNECTEDERR;
    }
  }
  
  protected void onPostExecute(String paramString)
  {
    super.onPostExecute(paramString);
    if (this.mLoadListener != null) {
      this.mLoadListener.onLoadFinish(this.mErr, paramString);
    }
  }
  
  public static abstract interface OnLoadListener
  {
    public abstract void onLoadFinish(int paramInt, String paramString);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunAdCropsTask
 * JD-Core Version:    0.7.0.1
 */