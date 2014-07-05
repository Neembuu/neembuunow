package com.amoad;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Settings
{
  private static final String HASH_ALGORITHUM = "SHA-1";
  private static final String PREF_KEY_FQ = "fq";
  private static final String PREF_SETTINGS = "settings";
  private static final String TAG = "Frequency";
  private static final Boolean mDebug = Boolean.valueOf(false);
  private Context mContext;
  private String mDomain;
  private String mFileName;
  private String mModel;
  private SharedPreferences.Editor mPrefEditorSettings;
  private SharedPreferences mPrefSettings;
  
  Settings(Context paramContext, String paramString1, String paramString2)
  {
    this.mContext = paramContext;
    this.mDomain = paramString1;
    this.mModel = paramString2;
    this.mFileName = getSettingsFileName();
    this.mPrefSettings = this.mContext.getSharedPreferences(this.mFileName, 0);
    if (mDebug.booleanValue()) {
      Log.d("Frequency", "mContext:" + this.mContext + " mDomain:" + this.mDomain + " mModel:" + this.mModel + " mFileName:" + this.mFileName);
    }
  }
  
  private String getHash(String paramString1, String paramString2)
  {
    String str = null;
    if ((paramString1 == null) || (paramString2 == null)) {}
    for (;;)
    {
      return str;
      StringBuffer localStringBuffer;
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance(paramString2);
        localMessageDigest.reset();
        localMessageDigest.update(paramString1.getBytes());
        byte[] arrayOfByte = localMessageDigest.digest();
        localStringBuffer = new StringBuffer();
        int i = arrayOfByte.length;
        for (int j = 0; j < i; j++)
        {
          localStringBuffer.append(Integer.toHexString(0xF & arrayOfByte[j] >> 4));
          localStringBuffer.append(Integer.toHexString(0xF & arrayOfByte[j]));
        }
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}
      str = localStringBuffer.toString();
    }
  }
  
  private String getSettingsFileName()
  {
    return getHash(this.mDomain + this.mModel + "settings", "SHA-1");
  }
  
  String getFrequency()
  {
    String str = this.mPrefSettings.getString("fq", "");
    if (mDebug.booleanValue()) {
      Log.d("Frequency", "return fq:" + str);
    }
    return str;
  }
  
  void setFrequency(String paramString)
  {
    if (this.mPrefSettings != null)
    {
      if (mDebug.booleanValue()) {
        Log.d("Frequency", "set fq:" + paramString);
      }
      this.mPrefEditorSettings = this.mPrefSettings.edit();
      this.mPrefEditorSettings.putString("fq", paramString);
      this.mPrefEditorSettings.commit();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.Settings
 * JD-Core Version:    0.7.0.1
 */