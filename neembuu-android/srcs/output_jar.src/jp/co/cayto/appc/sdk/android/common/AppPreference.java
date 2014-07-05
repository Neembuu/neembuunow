package jp.co.cayto.appc.sdk.android.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class AppPreference
{
  private static boolean _isOptIn(Context paramContext)
  {
    String str1 = getPrefs(paramContext, "log_permission", null);
    String str2 = getPrefs(paramContext, "gid", null);
    if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2))) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public static String getAppLabel(Context paramContext)
  {
    String str = "";
    try
    {
      str = (String)paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 1).applicationInfo.loadLabel(paramContext.getPackageManager());
      label29:
      if (str == null) {
        str = "";
      }
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label29;
    }
  }
  
  public static String getAppVersion(Context paramContext)
  {
    String str = "";
    try
    {
      str = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 1).versionName;
      label19:
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label19;
    }
  }
  
  public static int getAppVersionCode(Context paramContext)
  {
    int i = 0;
    try
    {
      i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 1).versionCode;
      label18:
      return i;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label18;
    }
  }
  
  public static String getGid(Context paramContext)
  {
    return getPrefs(paramContext, "gid", null);
  }
  
  public static String getLocale(Context paramContext)
  {
    return Locale.getDefault().toString();
  }
  
  public static String getMediaKey(Context paramContext)
  {
    String str = loadManifestMetaData(paramContext, "appc_media_key", "");
    if (TextUtils.isEmpty(str)) {
      Log.e("appC", "mediaKey empty!");
    }
    return str;
  }
  
  public static String getPermission(Context paramContext)
  {
    return getPrefs(paramContext, "log_permission", null);
  }
  
  public static String getPrefs(Context paramContext, String paramString1, String paramString2)
  {
    Object localObject = null;
    try
    {
      String str = paramContext.getSharedPreferences("APPC_CPISDK_INF_X1_" + paramContext.getPackageName(), 3).getString(paramString1, null);
      localObject = str;
    }
    catch (Exception localException)
    {
      label38:
      break label38;
    }
    if (localObject != null) {}
    for (;;)
    {
      return localObject;
      localObject = paramString2;
    }
  }
  
  public static boolean isPermission(Context paramContext)
  {
    String str = getPermission(paramContext);
    if ((!TextUtils.isEmpty(str)) && (str.equals("1"))) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public static int loadManifestMetaData(Context paramContext, String paramString, int paramInt)
  {
    Object localObject = null;
    try
    {
      Integer localInteger = Integer.valueOf(paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128).metaData.getInt(paramString));
      localObject = localInteger;
    }
    catch (Exception localException)
    {
      label31:
      break label31;
    }
    if ((localObject == null) || (localObject.intValue() == 0)) {}
    for (;;)
    {
      return paramInt;
      paramInt = localObject.intValue();
    }
  }
  
  public static String loadManifestMetaData(Context paramContext, String paramString1, String paramString2)
  {
    Object localObject = null;
    try
    {
      String str = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128).metaData.getString(paramString1);
      localObject = str;
    }
    catch (Exception localException)
    {
      label28:
      break label28;
    }
    if (localObject == null) {}
    for (;;)
    {
      return paramString2;
      paramString2 = localObject;
    }
  }
  
  public static void removeGid(Context paramContext)
  {
    setPrefs(paramContext, "gid", "");
  }
  
  public static void setGid(Context paramContext, String paramString)
  {
    if (!TextUtils.isEmpty(paramString)) {
      setPrefs(paramContext, "gid", paramString);
    }
  }
  
  @SuppressLint({"SimpleDateFormat"})
  public static void setPermissionOff(Context paramContext)
  {
    setPrefs(paramContext, "log_permission", "0");
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    localSimpleDateFormat.format(new Date());
    setPrefs(paramContext, "update_datetime", localSimpleDateFormat.format(new Date()));
  }
  
  @SuppressLint({"SimpleDateFormat"})
  public static void setPermissionOn(Context paramContext)
  {
    setPrefs(paramContext, "log_permission", "1");
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    localSimpleDateFormat.format(new Date());
    setPrefs(paramContext, "update_datetime", localSimpleDateFormat.format(new Date()));
  }
  
  /* Error */
  /**
   * @deprecated
   */
  public static void setPrefs(Context paramContext, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: aload_0
    //   4: new 103	java/lang/StringBuilder
    //   7: dup
    //   8: ldc 105
    //   10: invokespecial 108	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   13: aload_0
    //   14: invokevirtual 40	android/content/Context:getPackageName	()Ljava/lang/String;
    //   17: invokevirtual 112	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   20: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   23: iconst_3
    //   24: invokevirtual 117	android/content/Context:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   27: invokeinterface 193 1 0
    //   32: astore 5
    //   34: aload 5
    //   36: aload_1
    //   37: aload_2
    //   38: invokeinterface 199 3 0
    //   43: pop
    //   44: aload 5
    //   46: invokeinterface 203 1 0
    //   51: pop
    //   52: ldc 2
    //   54: monitorexit
    //   55: return
    //   56: astore 4
    //   58: ldc 2
    //   60: monitorexit
    //   61: aload 4
    //   63: athrow
    //   64: astore_3
    //   65: goto -13 -> 52
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	68	0	paramContext	Context
    //   0	68	1	paramString1	String
    //   0	68	2	paramString2	String
    //   64	1	3	localException	Exception
    //   56	6	4	localObject	Object
    //   32	13	5	localEditor	android.content.SharedPreferences.Editor
    // Exception table:
    //   from	to	target	type
    //   3	52	56	finally
    //   3	52	64	java/lang/Exception
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.common.AppPreference
 * JD-Core Version:    0.7.0.1
 */