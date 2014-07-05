package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

class AdfurikunRecTask
  extends AsyncTask<Void, Void, Void>
{
  public static final int RECTYPE_CLICK = 0;
  public static final int RECTYPE_IMPRESSION = 1;
  private String mAppID;
  private Context mContext;
  private int mErr;
  private String mLocale;
  private AdfurikunLogUtil mLog;
  private int mRecType;
  private String mUserAdId;
  private String mUserAgent;
  
  public AdfurikunRecTask(Context paramContext, String paramString1, String paramString2, String paramString3, AdfurikunLogUtil paramAdfurikunLogUtil, String paramString4, int paramInt)
  {
    this.mContext = paramContext;
    this.mAppID = paramString1;
    this.mLocale = paramString2;
    this.mUserAdId = paramString3;
    this.mLog = paramAdfurikunLogUtil;
    this.mUserAgent = paramString4;
    this.mRecType = paramInt;
    this.mErr = AdfurikunConstants.WEBAPI_NOERR;
  }
  
  protected Void doInBackground(Void... paramVarArgs)
  {
    publishProgress(new Void[0]);
    NetworkInfo localNetworkInfo = ((ConnectivityManager)this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
    String str;
    AdfurikunApiAccessUtil.WebAPIResult localWebAPIResult;
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnected()))
    {
      str = this.mContext.getSharedPreferences(AdfurikunConstants.PREF_FILE, 3).getString(AdfurikunConstants.PREFKEY_DEVICE_ID, "");
      if (this.mRecType == 0)
      {
        localWebAPIResult = AdfurikunApiAccessUtil.recClick(this.mAppID, this.mUserAdId, str, this.mLocale, this.mLog, this.mUserAgent);
        if (localWebAPIResult.return_code == 200) {}
      }
    }
    for (this.mErr = localWebAPIResult.return_code;; this.mErr = AdfurikunConstants.WEBAPI_CONNECTEDERR)
    {
      publishProgress(new Void[0]);
      return null;
      localWebAPIResult = AdfurikunApiAccessUtil.recImpression(this.mAppID, this.mUserAdId, str, this.mLocale, this.mLog, this.mUserAgent);
      break;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunRecTask
 * JD-Core Version:    0.7.0.1
 */