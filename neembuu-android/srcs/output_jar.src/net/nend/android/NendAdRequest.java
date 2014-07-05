package net.nend.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri.Builder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.util.Locale;

final class NendAdRequest
{
  private final String mApiKey;
  private final String mDomain;
  private final String mPath;
  private final String mProtocol;
  private final int mSpotId;
  
  static
  {
    if (!NendAdRequest.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  NendAdRequest(Context paramContext, int paramInt, String paramString)
  {
    if (paramContext == null) {
      throw new NullPointerException("Context is null.");
    }
    if (paramInt <= 0) {
      throw new IllegalArgumentException("Spot id is invalid. spot id : " + paramInt);
    }
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new IllegalArgumentException("Api key is invalid. api key : " + paramString);
    }
    this.mSpotId = paramInt;
    this.mApiKey = paramString;
    String str1 = "http";
    String str2 = "ad1.nend.net";
    Object localObject1 = "na.php";
    for (;;)
    {
      try
      {
        ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
        if (localApplicationInfo.metaData != null)
        {
          if (localApplicationInfo.metaData.getString(NendConstants.MetaData.ADSCHEME.getName()) != null) {
            str1 = localApplicationInfo.metaData.getString(NendConstants.MetaData.ADSCHEME.getName());
          }
          if (localApplicationInfo.metaData.getString(NendConstants.MetaData.ADAUTHORITY.getName()) != null) {
            str2 = localApplicationInfo.metaData.getString(NendConstants.MetaData.ADAUTHORITY.getName());
          }
          if (localApplicationInfo.metaData.getString(NendConstants.MetaData.ADPATH.getName()) != null)
          {
            String str3 = localApplicationInfo.metaData.getString(NendConstants.MetaData.ADPATH.getName());
            localObject1 = str3;
          }
        }
        return;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        if (!$assertionsDisabled) {
          throw new AssertionError();
        }
      }
      finally
      {
        this.mProtocol = str1;
        this.mDomain = str2;
        this.mPath = ((String)localObject1);
      }
      NendLog.d(NendStatus.ERR_UNEXPECTED, localNameNotFoundException);
      this.mProtocol = str1;
      this.mDomain = str2;
      this.mPath = ((String)localObject1);
    }
  }
  
  private String getDevice()
  {
    return Build.DEVICE;
  }
  
  private String getLocale()
  {
    return Locale.getDefault().toString();
  }
  
  private String getModel()
  {
    return Build.MODEL;
  }
  
  private String getOS()
  {
    return "android";
  }
  
  private String getSDKVersion()
  {
    return "2.1.0";
  }
  
  private String getVersion()
  {
    return Build.VERSION.RELEASE;
  }
  
  String getRequestUrl(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new IllegalArgumentException("UID is invalid. uid : " + paramString);
    }
    return new Uri.Builder().scheme(this.mProtocol).authority(this.mDomain).path(this.mPath).appendQueryParameter("apikey", this.mApiKey).appendQueryParameter("spot", String.valueOf(this.mSpotId)).appendQueryParameter("uid", paramString).appendQueryParameter("os", getOS()).appendQueryParameter("version", getVersion()).appendQueryParameter("model", getModel()).appendQueryParameter("device", getDevice()).appendQueryParameter("localize", getLocale()).appendQueryParameter("sdkver", getSDKVersion()).toString();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendAdRequest
 * JD-Core Version:    0.7.0.1
 */