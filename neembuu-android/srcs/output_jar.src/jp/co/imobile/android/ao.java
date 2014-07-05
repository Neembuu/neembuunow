package jp.co.imobile.android;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.util.Locale;

final class ao
  implements bp
{
  private static String c = null;
  private static String d = null;
  private final Context a;
  private final DisplayMetrics b;
  private boolean e;
  
  private ao(Context paramContext)
  {
    this.a = paramContext;
    this.b = new DisplayMetrics();
    ((WindowManager)this.a.getSystemService("window")).getDefaultDisplay().getMetrics(this.b);
    this.e = false;
  }
  
  ao(Context paramContext, byte paramByte)
  {
    this(paramContext);
  }
  
  static String c()
  {
    return Locale.getDefault().getLanguage();
  }
  
  static String e()
  {
    return Build.VERSION.RELEASE;
  }
  
  static String f()
  {
    return Build.BRAND;
  }
  
  static String g()
  {
    return Build.DEVICE;
  }
  
  static String h()
  {
    return Build.MODEL;
  }
  
  private ConnectivityManager n()
  {
    ConnectivityManager localConnectivityManager;
    if (this.a.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == -1) {
      localConnectivityManager = null;
    }
    for (;;)
    {
      return localConnectivityManager;
      localConnectivityManager = (ConnectivityManager)this.a.getSystemService("connectivity");
      if (localConnectivityManager == null) {
        localConnectivityManager = null;
      }
    }
  }
  
  private final String o()
  {
    try
    {
      String str2 = Settings.Secure.getString(this.a.getApplicationContext().getContentResolver(), "android_id");
      localObject = str2;
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        arrayOfString1 = new String[2];
        arrayOfString1[0] = "ex:";
        arrayOfString1[1] = localException1.toString();
        cj.b("fail get android id(Setting.Secure)", this, arrayOfString1);
        Object localObject = null;
      }
    }
    if (localObject == null) {}
    try
    {
      String str1 = Settings.System.getString(this.a.getApplicationContext().getContentResolver(), "android_id");
      localObject = str1;
    }
    catch (Exception localException2)
    {
      for (;;)
      {
        String[] arrayOfString1;
        String[] arrayOfString2 = new String[2];
        arrayOfString2[0] = "ex:";
        arrayOfString2[1] = localException2.toString();
        cj.b("fail get android id(Setting.SYSTEM)", this, arrayOfString2);
      }
    }
    return localObject;
  }
  
  final void a(boolean paramBoolean)
  {
    this.e = paramBoolean;
  }
  
  final boolean a()
  {
    return this.e;
  }
  
  final boolean b()
  {
    ConnectivityManager localConnectivityManager = n();
    boolean bool;
    if (localConnectivityManager == null) {
      bool = true;
    }
    for (;;)
    {
      return bool;
      NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
      if (localNetworkInfo != null) {
        bool = localNetworkInfo.isConnectedOrConnecting();
      } else {
        bool = false;
      }
    }
  }
  
  final String d()
  {
    if (c == null) {
      c = this.a.getPackageName();
    }
    return c;
  }
  
  public final String getLogContents()
  {
    return "";
  }
  
  public final String getLogTag()
  {
    return "(IM)DeviceHelper:";
  }
  
  final String i()
  {
    String str = null;
    ConnectivityManager localConnectivityManager = n();
    if (localConnectivityManager == null) {}
    for (;;)
    {
      return str;
      NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
      if (localNetworkInfo != null) {
        str = localNetworkInfo.getTypeName().toLowerCase(Locale.getDefault());
      }
    }
  }
  
  final float j()
  {
    return this.b.density;
  }
  
  final int k()
  {
    return this.b.heightPixels;
  }
  
  final int l()
  {
    return this.b.widthPixels;
  }
  
  final String m()
  {
    String str;
    if (d == null)
    {
      str = o();
      if (!ci.a(str)) {
        break label29;
      }
    }
    label29:
    for (d = ci.b(str);; d = "") {
      return d;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.ao
 * JD-Core Version:    0.7.0.1
 */