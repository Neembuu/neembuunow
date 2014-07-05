package com.amoad;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

class SharedPref
{
  private static final String PREFS_NAME = "com_amoad_AMoAdView";
  private final String BUG = "bug";
  private final String FIRST_TIME = "first_time";
  private final String LOG_ENABLE = "log_enable";
  private Context mContext;
  private Boolean mIsFirstTime = Boolean.valueOf(true);
  private String mLog = "";
  private Boolean mLogEnable = Boolean.valueOf(false);
  
  SharedPref(Context paramContext)
  {
    this.mContext = paramContext;
    SharedPreferences localSharedPreferences = this.mContext.getSharedPreferences("com_amoad_AMoAdView", 0);
    this.mLogEnable = Boolean.valueOf(localSharedPreferences.getBoolean("log_enable", false));
    this.mIsFirstTime = Boolean.valueOf(localSharedPreferences.getBoolean("first_time", true));
    this.mLog = localSharedPreferences.getString("bug", "");
  }
  
  protected void clearLog()
  {
    this.mLog = "";
    SharedPreferences.Editor localEditor = this.mContext.getSharedPreferences("com_amoad_AMoAdView", 0).edit();
    localEditor.remove("bug");
    localEditor.commit();
  }
  
  protected String getLog()
  {
    return this.mLog;
  }
  
  protected Boolean getLogEnable()
  {
    return this.mLogEnable;
  }
  
  protected Boolean isFirstTime()
  {
    return this.mIsFirstTime;
  }
  
  protected void setIsFirstTime(Boolean paramBoolean)
  {
    this.mIsFirstTime = paramBoolean;
    SharedPreferences.Editor localEditor = this.mContext.getSharedPreferences("com_amoad_AMoAdView", 0).edit();
    localEditor.putBoolean("first_time", this.mIsFirstTime.booleanValue());
    localEditor.commit();
  }
  
  protected void setLog(String paramString)
  {
    this.mLog = paramString;
    SharedPreferences.Editor localEditor = this.mContext.getSharedPreferences("com_amoad_AMoAdView", 0).edit();
    localEditor.putString("bug", this.mLog);
    localEditor.commit();
  }
  
  protected boolean setLogEnable(Boolean paramBoolean)
  {
    this.mLogEnable = paramBoolean;
    SharedPreferences.Editor localEditor = this.mContext.getSharedPreferences("com_amoad_AMoAdView", 0).edit();
    localEditor.putBoolean("log_enable", this.mLogEnable.booleanValue());
    localEditor.commit();
    return paramBoolean.booleanValue();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.SharedPref
 * JD-Core Version:    0.7.0.1
 */