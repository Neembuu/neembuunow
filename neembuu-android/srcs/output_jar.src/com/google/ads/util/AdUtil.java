package com.google.ads.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.location.Location;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.google.ads.AdActivity;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.nio.CharBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class AdUtil
{
  public static final int a = a(Build.VERSION.SDK);
  private static Boolean b = null;
  private static String c = null;
  private static String d;
  private static String e = null;
  private static AudioManager f;
  private static boolean g = true;
  private static boolean h = false;
  private static String i = null;
  
  public static int a()
  {
    if (a >= 9) {}
    for (int j = 6;; j = 0) {
      return j;
    }
  }
  
  public static int a(Context paramContext, int paramInt)
  {
    return (int)TypedValue.applyDimension(1, paramInt, paramContext.getResources().getDisplayMetrics());
  }
  
  public static int a(Context paramContext, DisplayMetrics paramDisplayMetrics)
  {
    if (a >= 4) {}
    for (int j = e.a(paramContext, paramDisplayMetrics);; j = paramDisplayMetrics.heightPixels) {
      return j;
    }
  }
  
  public static int a(String paramString)
  {
    try
    {
      int k = Integer.parseInt(paramString);
      j = k;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        b.e("The Android SDK version couldn't be parsed to an int: " + Build.VERSION.SDK);
        b.e("Defaulting to Android SDK version 3.");
        int j = 3;
      }
    }
    return j;
  }
  
  public static DisplayMetrics a(Activity paramActivity)
  {
    DisplayMetrics localDisplayMetrics;
    if (paramActivity.getWindowManager() == null) {
      localDisplayMetrics = null;
    }
    for (;;)
    {
      return localDisplayMetrics;
      localDisplayMetrics = new DisplayMetrics();
      paramActivity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    }
  }
  
  public static String a(Context paramContext)
  {
    String str2;
    String str3;
    if (c == null)
    {
      str2 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
      if ((str2 == null) || (c()))
      {
        str3 = b("emulator");
        if (str3 != null) {
          break label48;
        }
      }
    }
    for (String str1 = null;; str1 = c)
    {
      return str1;
      str3 = b(str2);
      break;
      label48:
      c = str3.toUpperCase(Locale.US);
    }
  }
  
  public static String a(Readable paramReadable)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    CharBuffer localCharBuffer = CharBuffer.allocate(2048);
    for (;;)
    {
      int j = paramReadable.read(localCharBuffer);
      if (j == -1) {
        break;
      }
      localCharBuffer.flip();
      localStringBuilder.append(localCharBuffer, 0, j);
    }
    return localStringBuilder.toString();
  }
  
  public static String a(Map<String, Object> paramMap)
  {
    Object localObject = null;
    try
    {
      String str = b(paramMap).toString();
      localObject = str;
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        b.d("JsonException in serialization: ", localJSONException);
      }
    }
    return localObject;
  }
  
  public static HashMap<String, Object> a(Location paramLocation)
  {
    Object localObject;
    if (paramLocation == null) {
      localObject = null;
    }
    for (;;)
    {
      return localObject;
      localObject = new HashMap();
      ((HashMap)localObject).put("time", Long.valueOf(1000L * paramLocation.getTime()));
      ((HashMap)localObject).put("lat", Long.valueOf((10000000.0D * paramLocation.getLatitude())));
      ((HashMap)localObject).put("long", Long.valueOf((10000000.0D * paramLocation.getLongitude())));
      ((HashMap)localObject).put("radius", Long.valueOf((1000.0F * paramLocation.getAccuracy())));
    }
  }
  
  public static JSONArray a(Set<Object> paramSet)
    throws JSONException
  {
    JSONArray localJSONArray1 = new JSONArray();
    if ((paramSet == null) || (paramSet.isEmpty())) {}
    for (JSONArray localJSONArray2 = localJSONArray1;; localJSONArray2 = localJSONArray1)
    {
      return localJSONArray2;
      Iterator localIterator = paramSet.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = localIterator.next();
        if (((localObject instanceof String)) || ((localObject instanceof Integer)) || ((localObject instanceof Double)) || ((localObject instanceof Long)) || ((localObject instanceof Float))) {
          localJSONArray1.put(localObject);
        } else if ((localObject instanceof Map)) {
          try
          {
            localJSONArray1.put(b((Map)localObject));
          }
          catch (ClassCastException localClassCastException2)
          {
            b.d("Unknown map type in json serialization: ", localClassCastException2);
          }
        } else if ((localObject instanceof Set)) {
          try
          {
            localJSONArray1.put(a((Set)localObject));
          }
          catch (ClassCastException localClassCastException1)
          {
            b.d("Unknown map type in json serialization: ", localClassCastException1);
          }
        } else {
          b.e("Unknown value in json serialization: " + localObject);
        }
      }
    }
  }
  
  public static void a(WebView paramWebView)
  {
    String str = i(paramWebView.getContext().getApplicationContext());
    paramWebView.getSettings().setUserAgentString(str);
  }
  
  public static void a(HttpURLConnection paramHttpURLConnection, Context paramContext)
  {
    paramHttpURLConnection.setRequestProperty("User-Agent", i(paramContext));
  }
  
  public static void a(boolean paramBoolean)
  {
    g = paramBoolean;
  }
  
  public static boolean a(int paramInt1, int paramInt2, String paramString)
  {
    if ((paramInt1 & paramInt2) == 0) {
      b.b("The android:configChanges value of the com.google.ads.AdActivity must include " + paramString + ".");
    }
    for (boolean bool = false;; bool = true) {
      return bool;
    }
  }
  
  public static boolean a(Context paramContext, String paramString)
  {
    boolean bool = false;
    try
    {
      paramContext.getPackageManager().getPackageInfo(paramString, 0);
      bool = true;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      label14:
      break label14;
    }
    return bool;
  }
  
  public static boolean a(Intent paramIntent, Context paramContext)
  {
    if (paramContext.getPackageManager().resolveActivity(paramIntent, 65536) != null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public static boolean a(Uri paramUri)
  {
    boolean bool = false;
    if (paramUri == null) {}
    for (;;)
    {
      return bool;
      String str = paramUri.getScheme();
      if (("http".equalsIgnoreCase(str)) || ("https".equalsIgnoreCase(str))) {
        bool = true;
      }
    }
  }
  
  static boolean a(d paramd)
  {
    if (paramd == null) {
      paramd = d.d;
    }
    if ((paramd.equals(d.e)) || (paramd.equals(d.f))) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public static int b()
  {
    if (a >= 9) {}
    for (int j = 7;; j = 1) {
      return j;
    }
  }
  
  public static int b(Context paramContext, DisplayMetrics paramDisplayMetrics)
  {
    if (a >= 4) {}
    for (int j = e.b(paramContext, paramDisplayMetrics);; j = paramDisplayMetrics.widthPixels) {
      return j;
    }
  }
  
  public static String b(String paramString)
  {
    localObject = null;
    if ((paramString != null) && (paramString.length() > 0)) {}
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes(), 0, paramString.length());
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = new BigInteger(1, localMessageDigest.digest());
      String str = String.format(localLocale, "%032X", arrayOfObject);
      localObject = str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      for (;;)
      {
        localObject = paramString.substring(0, 32);
      }
    }
    return localObject;
  }
  
  public static HashMap<String, String> b(Uri paramUri)
  {
    Object localObject = null;
    if (paramUri == null) {}
    for (;;)
    {
      return localObject;
      HashMap localHashMap = new HashMap();
      String str1 = paramUri.getEncodedQuery();
      if (str1 != null)
      {
        String[] arrayOfString = str1.split("&");
        int j = arrayOfString.length;
        int k = 0;
        if (k < j)
        {
          String str2 = arrayOfString[k];
          int m = str2.indexOf("=");
          if (m < 0) {
            localHashMap.put(Uri.decode(str2), null);
          }
          for (;;)
          {
            k++;
            break;
            localHashMap.put(Uri.decode(str2.substring(0, m)), Uri.decode(str2.substring(m + 1, str2.length())));
          }
        }
      }
      localObject = localHashMap;
    }
  }
  
  public static JSONObject b(Map<String, Object> paramMap)
    throws JSONException
  {
    JSONObject localJSONObject1 = new JSONObject();
    if ((paramMap == null) || (paramMap.isEmpty())) {}
    for (JSONObject localJSONObject2 = localJSONObject1;; localJSONObject2 = localJSONObject1)
    {
      return localJSONObject2;
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        Object localObject = paramMap.get(str);
        if (((localObject instanceof String)) || ((localObject instanceof Integer)) || ((localObject instanceof Double)) || ((localObject instanceof Long)) || ((localObject instanceof Float))) {
          localJSONObject1.put(str, localObject);
        } else if ((localObject instanceof Map)) {
          try
          {
            localJSONObject1.put(str, b((Map)localObject));
          }
          catch (ClassCastException localClassCastException2)
          {
            b.d("Unknown map type in json serialization: ", localClassCastException2);
          }
        } else if ((localObject instanceof Set)) {
          try
          {
            localJSONObject1.put(str, a((Set)localObject));
          }
          catch (ClassCastException localClassCastException1)
          {
            b.d("Unknown map type in json serialization: ", localClassCastException1);
          }
        } else {
          b.e("Unknown value in json serialization: " + localObject);
        }
      }
    }
  }
  
  public static boolean b(Context paramContext)
  {
    boolean bool = false;
    PackageManager localPackageManager = paramContext.getPackageManager();
    String str = paramContext.getPackageName();
    if (localPackageManager.checkPermission("android.permission.INTERNET", str) == -1) {
      b.b("INTERNET permissions must be enabled in AndroidManifest.xml.");
    }
    for (;;)
    {
      return bool;
      if (localPackageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE", str) == -1) {
        b.b("ACCESS_NETWORK_STATE permissions must be enabled in AndroidManifest.xml.");
      } else {
        bool = true;
      }
    }
  }
  
  public static boolean c()
  {
    return a(null);
  }
  
  public static boolean c(Context paramContext)
  {
    boolean bool;
    if (b != null)
    {
      bool = b.booleanValue();
      return bool;
    }
    ResolveInfo localResolveInfo = paramContext.getPackageManager().resolveActivity(new Intent(paramContext, AdActivity.class), 65536);
    b = Boolean.valueOf(true);
    if ((localResolveInfo == null) || (localResolveInfo.activityInfo == null))
    {
      b.b("Could not find com.google.ads.AdActivity, please make sure it is registered in AndroidManifest.xml.");
      b = Boolean.valueOf(false);
    }
    for (;;)
    {
      bool = b.booleanValue();
      break;
      if (!a(localResolveInfo.activityInfo.configChanges, 16, "keyboard")) {
        b = Boolean.valueOf(false);
      }
      if (!a(localResolveInfo.activityInfo.configChanges, 32, "keyboardHidden")) {
        b = Boolean.valueOf(false);
      }
      if (!a(localResolveInfo.activityInfo.configChanges, 128, "orientation")) {
        b = Boolean.valueOf(false);
      }
      if (!a(localResolveInfo.activityInfo.configChanges, 256, "screenLayout")) {
        b = Boolean.valueOf(false);
      }
      if (!a(localResolveInfo.activityInfo.configChanges, 512, "uiMode")) {
        b = Boolean.valueOf(false);
      }
      if (!a(localResolveInfo.activityInfo.configChanges, 1024, "screenSize")) {
        b = Boolean.valueOf(false);
      }
      if (!a(localResolveInfo.activityInfo.configChanges, 2048, "smallestScreenSize")) {
        b = Boolean.valueOf(false);
      }
    }
  }
  
  public static String d(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    String str;
    if (localNetworkInfo == null) {
      str = null;
    }
    for (;;)
    {
      return str;
      switch (localNetworkInfo.getType())
      {
      default: 
        str = "unknown";
        break;
      case 0: 
        str = "ed";
        break;
      case 1: 
        str = "wi";
      }
    }
  }
  
  public static boolean d()
  {
    return g;
  }
  
  public static String e(Context paramContext)
  {
    if (d == null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      PackageManager localPackageManager = paramContext.getPackageManager();
      List localList1 = localPackageManager.queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("geo:0,0?q=donuts")), 65536);
      if ((localList1 == null) || (localList1.isEmpty())) {
        localStringBuilder.append("m");
      }
      List localList2 = localPackageManager.queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse("market://search?q=pname:com.google")), 65536);
      if ((localList2 == null) || (localList2.isEmpty()))
      {
        if (localStringBuilder.length() > 0) {
          localStringBuilder.append(",");
        }
        localStringBuilder.append("a");
      }
      d = localStringBuilder.toString();
    }
    return d;
  }
  
  public static String f(Context paramContext)
  {
    String str = null;
    if (e != null) {
      str = e;
    }
    for (;;)
    {
      return str;
      try
      {
        PackageManager localPackageManager = paramContext.getPackageManager();
        ResolveInfo localResolveInfo = localPackageManager.resolveActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.google.ads")), 65536);
        if (localResolveInfo != null)
        {
          ActivityInfo localActivityInfo = localResolveInfo.activityInfo;
          if (localActivityInfo != null)
          {
            PackageInfo localPackageInfo = localPackageManager.getPackageInfo(localActivityInfo.packageName, 0);
            if (localPackageInfo != null)
            {
              e = localPackageInfo.versionCode + "." + localActivityInfo.packageName;
              str = e;
            }
          }
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    }
  }
  
  public static a g(Context paramContext)
  {
    if (f == null) {
      f = (AudioManager)paramContext.getSystemService("audio");
    }
    int j = f.getMode();
    a locala;
    if (c()) {
      locala = a.e;
    }
    for (;;)
    {
      return locala;
      if ((f.isMusicActive()) || (f.isSpeakerphoneOn()) || (j == 2) || (j == 1))
      {
        locala = a.d;
      }
      else
      {
        int k = f.getRingerMode();
        if ((k == 0) || (k == 1)) {
          locala = a.d;
        } else {
          locala = a.b;
        }
      }
    }
  }
  
  public static void h(Context paramContext)
  {
    if (h) {}
    for (;;)
    {
      return;
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("android.intent.action.USER_PRESENT");
      localIntentFilter.addAction("android.intent.action.SCREEN_OFF");
      paramContext.registerReceiver(new UserActivityReceiver(), localIntentFilter);
      h = true;
    }
  }
  
  public static String i(Context paramContext)
  {
    if (i == null)
    {
      String str1 = new WebView(paramContext).getSettings().getUserAgentString();
      if ((str1 == null) || (str1.length() == 0) || (str1.equals("Java0")))
      {
        String str2 = System.getProperty("os.name", "Linux");
        String str3 = "Android " + Build.VERSION.RELEASE;
        Locale localLocale = Locale.getDefault();
        String str4 = localLocale.getLanguage().toLowerCase(Locale.US);
        if (str4.length() == 0) {
          str4 = "en";
        }
        String str5 = localLocale.getCountry().toLowerCase(Locale.US);
        if (str5.length() > 0) {
          str4 = str4 + "-" + str5;
        }
        String str6 = Build.MODEL + " Build/" + Build.ID;
        str1 = "Mozilla/5.0 (" + str2 + "; U; " + str3 + "; " + str4 + "; " + str6 + ") AppleWebKit/0.0 (KHTML, like " + "Gecko) Version/0.0 Mobile Safari/0.0";
      }
      i = str1 + " (Mobile; " + "afma-sdk-a-v" + "6.4.1" + ")";
    }
    return i;
  }
  
  public static class UserActivityReceiver
    extends BroadcastReceiver
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent.getAction().equals("android.intent.action.USER_PRESENT")) {
        AdUtil.a(true);
      }
      for (;;)
      {
        return;
        if (paramIntent.getAction().equals("android.intent.action.SCREEN_OFF")) {
          AdUtil.a(false);
        }
      }
    }
  }
  
  public static enum a
  {
    static
    {
      a[] arrayOfa = new a[6];
      arrayOfa[0] = a;
      arrayOfa[1] = b;
      arrayOfa[2] = c;
      arrayOfa[3] = d;
      arrayOfa[4] = e;
      arrayOfa[5] = f;
      g = arrayOfa;
    }
    
    private a() {}
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.util.AdUtil
 * JD-Core Version:    0.7.0.1
 */