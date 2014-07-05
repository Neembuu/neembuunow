package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import java.io.File;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

class AdfurikunGetInfoTask
  extends AsyncTask<Void, Void, AdfurikunInfo>
{
  private String mAppID;
  private Context mContext;
  private int mErr;
  private boolean mIsMain;
  private OnLoadListener mLoadListener;
  private String mLocale;
  private AdfurikunLogUtil mLog;
  private String mUserAgent;
  
  public AdfurikunGetInfoTask(OnLoadListener paramOnLoadListener, Context paramContext, String paramString1, String paramString2, AdfurikunLogUtil paramAdfurikunLogUtil, String paramString3, boolean paramBoolean)
  {
    this.mLoadListener = paramOnLoadListener;
    this.mContext = paramContext;
    this.mAppID = paramString1;
    this.mLocale = paramString2;
    this.mLog = paramAdfurikunLogUtil;
    this.mUserAgent = paramString3;
    this.mIsMain = paramBoolean;
    this.mErr = AdfurikunConstants.WEBAPI_NOERR;
  }
  
  private void saveData(String paramString)
  {
    if (this.mContext != null)
    {
      long l = new Date().getTime();
      SharedPreferences.Editor localEditor = this.mContext.getSharedPreferences(AdfurikunConstants.PREF_FILE, 3).edit();
      localEditor.putLong(AdfurikunConstants.PREFKEY_AD_LAST_TIME + this.mAppID, l);
      localEditor.commit();
      File localFile = this.mContext.getCacheDir();
      if (localFile != null) {
        AdfurikunApiAccessUtil.saveStringFile(new StringBuilder(String.valueOf(localFile.getPath())).append(AdfurikunConstants.ADFURIKUN_FOLDER).append(this.mAppID).append("/").toString() + AdfurikunConstants.GETINFO_FILE, paramString);
      }
    }
  }
  
  protected AdfurikunInfo doInBackground(Void... paramVarArgs)
  {
    publishProgress(new Void[0]);
    String str1 = "";
    NetworkInfo localNetworkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
    AdfurikunApiAccessUtil.WebAPIResult localWebAPIResult;
    String str3;
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnected()))
    {
      String str2 = this.mContext.getSharedPreferences(AdfurikunConstants.PREF_FILE, 3).getString(AdfurikunConstants.PREFKEY_DEVICE_ID, "");
      localWebAPIResult = AdfurikunApiAccessUtil.getInfo(this.mAppID, str2, this.mLocale, this.mLog, this.mUserAgent, this.mIsMain);
      if (localWebAPIResult.return_code == 200) {
        str3 = "";
      }
    }
    for (;;)
    {
      try
      {
        JSONObject localJSONObject1 = new JSONObject(localWebAPIResult.message);
        if (localJSONObject1.has("result")) {
          str3 = localJSONObject1.getString("result");
        }
        if (str3.equals("ok"))
        {
          saveData(localWebAPIResult.message);
          str1 = localWebAPIResult.message;
          this.mErr = AdfurikunConstants.WEBAPI_NOERR;
          publishProgress(new Void[0]);
          return AdfurikunApiAccessUtil.stringToInfo(this.mContext, this.mAppID, str1, this.mLog, false);
        }
        if ((!str3.equals("error")) || (!localJSONObject1.has("values"))) {
          continue;
        }
        JSONObject localJSONObject2 = new JSONObject(localJSONObject1.getString("values"));
        if (!localJSONObject2.has("message")) {
          continue;
        }
        this.mLog.debug_e(AdfurikunConstants.TAG_NAME, "error=" + localJSONObject2.getString("message"));
        continue;
      }
      catch (JSONException localJSONException)
      {
        this.mLog.debug_e(AdfurikunConstants.TAG_NAME, "JSONException");
        this.mLog.debug_e(AdfurikunConstants.TAG_NAME, localJSONException);
        continue;
      }
      this.mErr = localWebAPIResult.return_code;
      continue;
      this.mErr = AdfurikunConstants.WEBAPI_CONNECTEDERR;
    }
  }
  
  protected void onPostExecute(AdfurikunInfo paramAdfurikunInfo)
  {
    super.onPostExecute(paramAdfurikunInfo);
    if (this.mLoadListener != null) {
      this.mLoadListener.onLoadFinish(this.mErr, paramAdfurikunInfo);
    }
  }
  
  public static abstract interface OnLoadListener
  {
    public abstract void onLoadFinish(int paramInt, AdfurikunInfo paramAdfurikunInfo);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunGetInfoTask
 * JD-Core Version:    0.7.0.1
 */