package net.nend.android;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

final class NendHelper
{
  private static boolean mDebuggable = false;
  private static boolean mDev = false;
  
  static void disableDebug()
  {
    mDebuggable = false;
    mDev = false;
  }
  
  private static boolean hasImplicitIntent(Context paramContext, Intent paramIntent)
  {
    if (paramContext.getPackageManager().queryIntentActivities(paramIntent, 65536).size() > 0) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  static boolean isConnected(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnected())) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  static boolean isDebuggable()
  {
    return mDebuggable;
  }
  
  static boolean isDev()
  {
    return mDev;
  }
  
  static String md5String(String paramString)
  {
    Object localObject = new byte[16];
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      localObject = arrayOfByte;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      for (;;)
      {
        int i;
        NendLog.e("nend_SDK", localNoSuchAlgorithmException.getMessage(), localNoSuchAlgorithmException);
      }
    }
    StringBuffer localStringBuffer = new StringBuffer();
    for (i = 0; i < localObject.length; i++) {
      localStringBuffer.append(Integer.toString(256 + (0xFF & localObject[i]), 16).substring(1));
    }
    return localStringBuffer.toString();
  }
  
  static void setDebuggable(Context paramContext)
  {
    try
    {
      ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
      if (localApplicationInfo.metaData != null)
      {
        mDebuggable = localApplicationInfo.metaData.getBoolean("NendDebuggable", false);
        mDev = localApplicationInfo.metaData.getBoolean("NendDev", false);
      }
      label48:
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label48;
    }
  }
  
  static void startBrowser(View paramView, String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
    if (hasImplicitIntent(paramView.getContext(), localIntent))
    {
      localIntent.setFlags(268435456);
      paramView.getContext().startActivity(localIntent);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendHelper
 * JD-Core Version:    0.7.0.1
 */