package com.amoad.amoadsdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class Util
{
  public static final String APPENDIX_PARAM_KEY = "&ax=";
  public static final String APPEND_FREQ_KEY = "&freq=";
  public static final String APP_KEY_PARAM_KEY = "&ak=";
  public static final String BASE_POPUP_URL_DEV = "";
  public static final String BASE_POPUP_URL_REAL = "http://tr.applipromotion.com/deliver/imp/popup";
  public static final String BASE_POPUP_URL_TEST = "http://tr.applipromotion.com/deliver/imp/popup";
  public static final String BASE_TRIGGER_URL_DEV = "";
  public static final String BASE_TRIGGER_URL_REAL = "http://tr.applipromotion.com/deliver/imp/image";
  public static final String BASE_TRIGGER_URL_TEST = "http://tr.applipromotion.com/deliver/imp/image";
  public static final String BASE_TRPO_CLICK_URL_DEV = "";
  public static final String BASE_TRPO_CLICK_URL_REAL = "http://tr.applipromotion.com/deliver/click";
  public static final String BASE_TRPO_CLICK_URL_TEST = "http://tr.applipromotion.com/deliver/click";
  public static final String BASE_URL_DEV = "http://　/v2/";
  public static final String BASE_URL_REAL = "http://ad.applipromotion.com/v2/";
  public static final String BASE_URL_TEST = "http://www.applipromotion.com/ad/v2/";
  public static final String CLICK_APP_KEY_PARAM_KEY = "&cak=";
  public static final String CREATIVE_NAME_PARAM_KEY = "&cn=";
  private static final Pattern HTTPS_MARKET_URL_PATTERN = Pattern.compile("^https://market.android.com/details\\?id=(.*)", 2);
  private static final Pattern HTTPS_PLAY_URL_PATTERN = Pattern.compile("^https://play.google.com/.*details\\?id=(.*)", 2);
  public static final String LOGO_FLG_PARAM_KEY = "&logo=";
  public static final String LOG_TAG = "AMoAdSdk";
  public static final String OS_PARAM_KEY_VALUE = "?os=android";
  public static final String SCREEN_ID_PARAM_KEY = "&screenId=";
  public static final String UUID_PARAM_KEY = "&uuid=";
  protected static final Pattern XAPP_API_URL_PATTERN;
  protected static final Pattern XAPP_CLICK_URL_PATTERN;
  private static Activity activity;
  private static String appKey;
  private static Bundle bundle;
  private static Boolean conversion;
  private static String conversionAppendix;
  private static String countryName;
  private static String debugMode;
  static final Object obj;
  private static String screen;
  private static String wallAppendix;
  protected static WebView webView = null;
  protected static WebView webViewSplash = null;
  
  static
  {
    appKey = null;
    screen = "auto";
    debugMode = null;
    conversion = null;
    countryName = "JP";
    wallAppendix = null;
    conversionAppendix = null;
    activity = null;
    bundle = null;
    obj = new Object();
    XAPP_CLICK_URL_PATTERN = Pattern.compile("^xapp://appKey=([0-9a-z]{16})(/appendix=(.*?))?/name=(.+)/link=(.*)$", 2);
    XAPP_API_URL_PATTERN = Pattern.compile("^xapp://([0-9a-z]+)/(.*)$", 2);
  }
  
  private static void appendAppKey(StringBuilder paramStringBuilder)
  {
    appendUrl(paramStringBuilder, "&ak=", getAppKey());
  }
  
  private static void appendAppendix(StringBuilder paramStringBuilder, String paramString)
  {
    appendUrl(paramStringBuilder, "&ax=", paramString);
  }
  
  private static void appendClickAppKey(StringBuilder paramStringBuilder, String paramString)
  {
    appendUrl(paramStringBuilder, "&cak=", paramString);
  }
  
  private static void appendConversionAppendix(StringBuilder paramStringBuilder)
  {
    appendAppendix(paramStringBuilder, getConversionAppendix());
  }
  
  private static void appendCreativeName(StringBuilder paramStringBuilder, String paramString)
  {
    appendUrl(paramStringBuilder, "&cn=", paramString);
  }
  
  private static void appendFreq(StringBuilder paramStringBuilder, String paramString)
  {
    appendUrl(paramStringBuilder, "&freq=", paramString);
  }
  
  private static void appendLogoFlg(StringBuilder paramStringBuilder)
  {
    appendUrl(paramStringBuilder, "&logo=", "true");
  }
  
  private static void appendParam(StringBuilder paramStringBuilder, HashMap<String, String> paramHashMap)
  {
    if ((paramHashMap == null) || (paramHashMap.size() == 0)) {}
    for (;;)
    {
      return;
      Iterator localIterator = paramHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        appendUrl(paramStringBuilder, "&" + str + "=", (String)paramHashMap.get(str));
      }
    }
  }
  
  private static void appendScreenId(StringBuilder paramStringBuilder)
  {
    appendUrl(paramStringBuilder, "&screenId=", getScreenId().toString());
  }
  
  private static void appendUUID(StringBuilder paramStringBuilder)
  {
    appendUrl(paramStringBuilder, "&uuid=", Config.getUuid());
  }
  
  private static void appendUUID(StringBuilder paramStringBuilder, Activity paramActivity)
  {
    appendUrl(paramStringBuilder, "&uuid=", Config.getUuid());
  }
  
  private static void appendUrl(StringBuilder paramStringBuilder, String paramString1, String paramString2)
  {
    if ((paramString2 != null) && (paramString2.length() > 0)) {
      paramStringBuilder.append(paramString1).append(paramString2);
    }
  }
  
  private static void appendWallAppendix(StringBuilder paramStringBuilder)
  {
    appendAppendix(paramStringBuilder, getWallAppendix());
  }
  
  public static int createRandom(int paramInt)
  {
    return (int)(Math.random() * paramInt);
  }
  
  public static String getAppKey()
  {
    if (appKey == null) {
      setupMetadata();
    }
    return appKey;
  }
  
  public static String getBasePopupUrl()
  {
    String str;
    if ("TRUE".equals(debugMode)) {
      str = "http://tr.applipromotion.com/deliver/imp/popup";
    }
    for (;;)
    {
      return str;
      if ("STG".equals(debugMode)) {
        str = "";
      } else {
        str = "http://tr.applipromotion.com/deliver/imp/popup";
      }
    }
  }
  
  public static String getBaseTrPoClickUrl()
  {
    String str;
    if ("TRUE".equals(debugMode)) {
      str = "http://tr.applipromotion.com/deliver/click";
    }
    for (;;)
    {
      return str;
      if ("STG".equals(debugMode)) {
        str = "";
      } else {
        str = "http://tr.applipromotion.com/deliver/click";
      }
    }
  }
  
  public static String getBaseTriggerUrl()
  {
    String str;
    if ("TRUE".equals(debugMode)) {
      str = "http://tr.applipromotion.com/deliver/imp/image";
    }
    for (;;)
    {
      return str;
      if ("STG".equals(debugMode)) {
        str = "";
      } else {
        str = "http://tr.applipromotion.com/deliver/imp/image";
      }
    }
  }
  
  private static String getBaseUrl()
  {
    String str;
    if ("TRUE".equals(debugMode)) {
      str = "http://www.applipromotion.com/ad/v2/";
    }
    for (;;)
    {
      return str;
      if ("STG".equals(debugMode)) {
        str = "http://　/v2/";
      } else {
        str = "http://ad.applipromotion.com/v2/";
      }
    }
  }
  
  private static Bundle getBundle(Activity paramActivity, Class<?> paramClass)
  {
    Bundle localBundle = null;
    if (paramActivity != null) {}
    try
    {
      localBundle = new Intent(paramActivity, paramClass).resolveActivityInfo(paramActivity.getPackageManager(), 128).metaData;
      return localBundle;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        Log.v("AMoAdSdk", "MainfestファイルからMETAデータの取得でエラー。。" + localThrowable.getMessage());
      }
    }
  }
  
  public static String getClickURL(String paramString1, String paramString2, String paramString3)
  {
    StringBuilder localStringBuilder = new StringBuilder(getBaseUrl()).append("click").append("?os=android");
    appendAppKey(localStringBuilder);
    appendUUID(localStringBuilder);
    appendClickAppKey(localStringBuilder, paramString1);
    appendCreativeName(localStringBuilder, paramString2);
    appendAppendix(localStringBuilder, paramString3);
    Log.d("AMoAdSdk", "ClickURL: " + localStringBuilder);
    return localStringBuilder.toString();
  }
  
  private static File getContextFile(Activity paramActivity, String paramString)
  {
    File localFile = paramActivity.getBaseContext().getFilesDir();
    if (!localFile.exists()) {
      localFile.mkdirs();
    }
    return new File(localFile, paramString);
  }
  
  private static String getConversionAppendix()
  {
    if (conversionAppendix == null) {
      setupMetadata();
    }
    return conversionAppendix;
  }
  
  public static String getConversionURL(Activity paramActivity)
  {
    StringBuilder localStringBuilder = new StringBuilder(getBaseUrl()).append("conversion").append("?os=android");
    appendAppKey(localStringBuilder);
    appendUUID(localStringBuilder, paramActivity);
    appendConversionAppendix(localStringBuilder);
    Log.d("AMoAdSdk", "ConversionURL: " + localStringBuilder);
    return localStringBuilder.toString();
  }
  
  public static int getDipToPix(int paramInt, Activity paramActivity)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    paramActivity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    return (int)(paramInt * localDisplayMetrics.density);
  }
  
  public static int getExecCount(Activity paramActivity)
  {
    String str = readFromContextFile(paramActivity, "AMoAdSdk_ExecCount");
    if ((str == null) || (str.length() <= 0)) {}
    for (int i = 0;; i = new Integer(str).intValue()) {
      return i;
    }
  }
  
  public static Integer getScreenId()
  {
    return getScreenId(screen);
  }
  
  public static Integer getScreenId(String paramString)
  {
    if (paramString == null) {
      setupMetadata();
    }
    Integer localInteger;
    if ((paramString != null) && ("landscape".equals(paramString))) {
      localInteger = Integer.valueOf(1);
    }
    for (;;)
    {
      return localInteger;
      if ((paramString != null) && ("portrait".equals(paramString)))
      {
        localInteger = Integer.valueOf(2);
      }
      else
      {
        Configuration localConfiguration = activity.getResources().getConfiguration();
        if (localConfiguration == null) {
          localInteger = Integer.valueOf(2);
        } else {
          switch (localConfiguration.orientation)
          {
          default: 
            localInteger = Integer.valueOf(2);
            break;
          case 2: 
            localInteger = Integer.valueOf(1);
            break;
          case 1: 
            localInteger = Integer.valueOf(2);
          }
        }
      }
    }
  }
  
  public static int getShowCount(Activity paramActivity)
  {
    int i = 0;
    if (!new SimpleDateFormat("yyyyMMdd").format(new Date()).equals(readFromContextFile(paramActivity, "AMoAdSdk_ShowDate"))) {}
    for (;;)
    {
      return i;
      String str = readFromContextFile(paramActivity, "AMoAdSdk_ShowCount");
      if ((str != null) && (str.length() > 0)) {
        i = new Integer(str).intValue();
      }
    }
  }
  
  private static String getUpperStr(Bundle paramBundle, String paramString)
  {
    if ((paramBundle != null) && (paramBundle.containsKey(paramString))) {}
    for (String str = paramBundle.get(paramString).toString().toUpperCase();; str = null) {
      return str;
    }
  }
  
  private static String getWallAppendix()
  {
    if (wallAppendix == null) {
      setupMetadata();
    }
    return wallAppendix;
  }
  
  public static String getWallURL(DESTINATIONS paramDESTINATIONS, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder(getBaseUrl()).append(paramDESTINATIONS).append("?os=android");
    appendAppKey(localStringBuilder);
    appendUUID(localStringBuilder);
    appendLogoFlg(localStringBuilder);
    appendWallAppendix(localStringBuilder);
    if ((paramString != null) && (paramString != "")) {
      appendFreq(localStringBuilder, paramString);
    }
    Log.d("AMoAdSdk", "WallURL: " + localStringBuilder);
    return localStringBuilder.toString();
  }
  
  public static boolean hasRedirect(String paramString)
  {
    boolean bool = false;
    SimpleHttpResponse localSimpleHttpResponse = sendHeadRequest(paramString);
    switch (localSimpleHttpResponse.code)
    {
    default: 
      break;
    }
    for (;;)
    {
      return bool;
      Iterator localIterator = localSimpleHttpResponse.header.entrySet().iterator();
      if (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        String str = (String)localEntry.getKey();
        List localList = (List)localEntry.getValue();
        if ((!"location".equals(str.toLowerCase())) || (localList.size() <= 0)) {
          break;
        }
        bool = true;
      }
    }
  }
  
  public static boolean isAlive(Context paramContext)
  {
    boolean bool = false;
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable())) {
        if ((localNetworkInfo.isConnected()) || (localNetworkInfo.isConnectedOrConnecting())) {
          break label49;
        }
      }
    }
    label49:
    for (bool = false;; bool = true) {
      return bool;
    }
  }
  
  public static boolean isFirstOnToday(Activity paramActivity)
  {
    if (new SimpleDateFormat("yyyyMMdd").format(new Date()).equals(readFromContextFile(paramActivity, "AMoAdSdk_AccessDate"))) {}
    for (boolean bool = false;; bool = true) {
      return bool;
    }
  }
  
  public static boolean isSameDay(Date paramDate1, Date paramDate2)
  {
    if ((paramDate1 == null) || (paramDate2 == null)) {}
    for (boolean bool = false;; bool = new SimpleDateFormat("yyyyMMdd").format(paramDate1).equals(new SimpleDateFormat("yyyyMMdd").format(paramDate2))) {
      return bool;
    }
  }
  
  public static UrlParseResult parseHttpsMarketUrl(String paramString)
  {
    Matcher localMatcher = HTTPS_MARKET_URL_PATTERN.matcher(paramString);
    if (localMatcher.matches()) {}
    for (UrlParseResult localUrlParseResult = UrlParseResult.build(true).$(Key.marketUrl, "market://details?id=" + localMatcher.group(1));; localUrlParseResult = UrlParseResult.build(false)) {
      return localUrlParseResult;
    }
  }
  
  public static UrlParseResult parseHttpsPlayUrl(String paramString)
  {
    Matcher localMatcher = HTTPS_PLAY_URL_PATTERN.matcher(paramString);
    if (localMatcher.matches()) {}
    for (UrlParseResult localUrlParseResult = UrlParseResult.build(true).$(Key.marketUrl, "market://details?id=" + localMatcher.group(1));; localUrlParseResult = UrlParseResult.build(false)) {
      return localUrlParseResult;
    }
  }
  
  public static UrlParseResult parseXAppApi(String paramString)
  {
    Matcher localMatcher = XAPP_API_URL_PATTERN.matcher(paramString);
    UrlParseResult localUrlParseResult;
    String[] arrayOfString1;
    int j;
    if (localMatcher.matches())
    {
      Command localCommand = Command.create(localMatcher.group(1));
      if (localCommand != Command.Unknown)
      {
        localUrlParseResult = UrlParseResult.build(true).$(Key.command, localCommand);
        arrayOfString1 = localMatcher.group(2).split("&");
        int i = arrayOfString1.length;
        j = 0;
        if (j < i) {}
      }
    }
    for (;;)
    {
      return localUrlParseResult;
      String[] arrayOfString2 = arrayOfString1[j].split("=");
      if (arrayOfString2.length == 2) {
        localUrlParseResult.$(Key.create(arrayOfString2[0]), arrayOfString2[1]);
      }
      j++;
      break;
      localUrlParseResult = UrlParseResult.build(false);
    }
  }
  
  public static UrlParseResult parseXappClickUrl(String paramString)
  {
    Matcher localMatcher = XAPP_CLICK_URL_PATTERN.matcher(paramString);
    if (localMatcher.matches()) {}
    for (UrlParseResult localUrlParseResult = UrlParseResult.build(true).$(Key.appKey, localMatcher.group(1)).$(Key.appendix, localMatcher.group(3)).$(Key.name, localMatcher.group(4)).$(Key.link, localMatcher.group(5));; localUrlParseResult = UrlParseResult.build(false)) {
      return localUrlParseResult;
    }
  }
  
  /* Error */
  public static String readFromContextFile(Activity paramActivity, String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 597	java/io/BufferedReader
    //   5: dup
    //   6: new 599	java/io/InputStreamReader
    //   9: dup
    //   10: new 601	java/io/FileInputStream
    //   13: dup
    //   14: aload_0
    //   15: aload_1
    //   16: invokestatic 603	com/amoad/amoadsdk/Util:getContextFile	(Landroid/app/Activity;Ljava/lang/String;)Ljava/io/File;
    //   19: invokespecial 606	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   22: invokespecial 609	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   25: invokespecial 612	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   28: astore_3
    //   29: aload_3
    //   30: invokevirtual 615	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   33: astore 5
    //   35: ldc 58
    //   37: aload 5
    //   39: invokestatic 317	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   42: pop
    //   43: aload_3
    //   44: invokevirtual 618	java/io/BufferedReader:close	()V
    //   47: aload_3
    //   48: ifnull +7 -> 55
    //   51: aload_3
    //   52: invokevirtual 618	java/io/BufferedReader:close	()V
    //   55: aload 5
    //   57: areturn
    //   58: astore 19
    //   60: ldc_w 620
    //   63: ldc_w 622
    //   66: aload 19
    //   68: invokestatic 626	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   71: pop
    //   72: goto -17 -> 55
    //   75: astore 22
    //   77: aload_2
    //   78: ifnull +7 -> 85
    //   81: aload_2
    //   82: invokevirtual 618	java/io/BufferedReader:close	()V
    //   85: aconst_null
    //   86: astore 5
    //   88: goto -33 -> 55
    //   91: astore 6
    //   93: ldc_w 620
    //   96: ldc_w 622
    //   99: aload 6
    //   101: invokestatic 626	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   104: pop
    //   105: goto -20 -> 85
    //   108: astore 8
    //   110: ldc 58
    //   112: aload 8
    //   114: invokevirtual 627	java/io/IOException:getMessage	()Ljava/lang/String;
    //   117: aload 8
    //   119: invokestatic 626	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   122: pop
    //   123: aload_2
    //   124: ifnull -39 -> 85
    //   127: aload_2
    //   128: invokevirtual 618	java/io/BufferedReader:close	()V
    //   131: goto -46 -> 85
    //   134: astore 13
    //   136: ldc_w 620
    //   139: ldc_w 622
    //   142: aload 13
    //   144: invokestatic 626	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   147: pop
    //   148: goto -63 -> 85
    //   151: astore 21
    //   153: aload_2
    //   154: ifnull -69 -> 85
    //   157: aload_2
    //   158: invokevirtual 618	java/io/BufferedReader:close	()V
    //   161: goto -76 -> 85
    //   164: astore 16
    //   166: ldc_w 620
    //   169: ldc_w 622
    //   172: aload 16
    //   174: invokestatic 626	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   177: pop
    //   178: goto -93 -> 85
    //   181: astore 9
    //   183: aload_2
    //   184: ifnull +7 -> 191
    //   187: aload_2
    //   188: invokevirtual 618	java/io/BufferedReader:close	()V
    //   191: aload 9
    //   193: athrow
    //   194: astore 10
    //   196: ldc_w 620
    //   199: ldc_w 622
    //   202: aload 10
    //   204: invokestatic 626	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   207: pop
    //   208: goto -17 -> 191
    //   211: astore 9
    //   213: aload_3
    //   214: astore_2
    //   215: goto -32 -> 183
    //   218: astore 15
    //   220: aload_3
    //   221: astore_2
    //   222: goto -69 -> 153
    //   225: astore 8
    //   227: aload_3
    //   228: astore_2
    //   229: goto -119 -> 110
    //   232: astore 4
    //   234: aload_3
    //   235: astore_2
    //   236: goto -159 -> 77
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	239	0	paramActivity	Activity
    //   0	239	1	paramString	String
    //   1	235	2	localObject1	Object
    //   28	207	3	localBufferedReader	java.io.BufferedReader
    //   232	1	4	localFileNotFoundException1	java.io.FileNotFoundException
    //   33	54	5	str	String
    //   91	9	6	localException1	java.lang.Exception
    //   108	10	8	localIOException1	java.io.IOException
    //   225	1	8	localIOException2	java.io.IOException
    //   181	11	9	localObject2	Object
    //   211	1	9	localObject3	Object
    //   194	9	10	localException2	java.lang.Exception
    //   134	9	13	localException3	java.lang.Exception
    //   218	1	15	localNullPointerException1	java.lang.NullPointerException
    //   164	9	16	localException4	java.lang.Exception
    //   58	9	19	localException5	java.lang.Exception
    //   151	1	21	localNullPointerException2	java.lang.NullPointerException
    //   75	1	22	localFileNotFoundException2	java.io.FileNotFoundException
    // Exception table:
    //   from	to	target	type
    //   51	55	58	java/lang/Exception
    //   2	29	75	java/io/FileNotFoundException
    //   81	85	91	java/lang/Exception
    //   2	29	108	java/io/IOException
    //   127	131	134	java/lang/Exception
    //   2	29	151	java/lang/NullPointerException
    //   157	161	164	java/lang/Exception
    //   2	29	181	finally
    //   110	123	181	finally
    //   187	191	194	java/lang/Exception
    //   29	47	211	finally
    //   29	47	218	java/lang/NullPointerException
    //   29	47	225	java/io/IOException
    //   29	47	232	java/io/FileNotFoundException
  }
  
  public static void saveAccessDate(Activity paramActivity)
  {
    saveToContextFile(paramActivity, "AMoAdSdk_AccessDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
  }
  
  public static void saveConversion(Activity paramActivity)
  {
    saveToContextFile(paramActivity, "AMoAdSdk_ConversionDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
  }
  
  /* Error */
  public static void saveToContextFile(Activity paramActivity, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new 638	java/io/PrintWriter
    //   5: dup
    //   6: new 640	java/io/OutputStreamWriter
    //   9: dup
    //   10: new 642	java/io/FileOutputStream
    //   13: dup
    //   14: aload_0
    //   15: aload_1
    //   16: invokestatic 603	com/amoad/amoadsdk/Util:getContextFile	(Landroid/app/Activity;Ljava/lang/String;)Ljava/io/File;
    //   19: invokespecial 643	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   22: invokespecial 646	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;)V
    //   25: invokespecial 649	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   28: astore 4
    //   30: aload 4
    //   32: aload_2
    //   33: invokevirtual 652	java/io/PrintWriter:print	(Ljava/lang/String;)V
    //   36: aload 4
    //   38: invokevirtual 653	java/io/PrintWriter:close	()V
    //   41: aload 4
    //   43: ifnull +8 -> 51
    //   46: aload 4
    //   48: invokevirtual 653	java/io/PrintWriter:close	()V
    //   51: return
    //   52: astore 5
    //   54: ldc 58
    //   56: aload 5
    //   58: invokevirtual 627	java/io/IOException:getMessage	()Ljava/lang/String;
    //   61: aload 5
    //   63: invokestatic 626	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   66: pop
    //   67: aload_3
    //   68: ifnull -17 -> 51
    //   71: aload_3
    //   72: invokevirtual 653	java/io/PrintWriter:close	()V
    //   75: goto -24 -> 51
    //   78: astore 6
    //   80: aload_3
    //   81: ifnull +7 -> 88
    //   84: aload_3
    //   85: invokevirtual 653	java/io/PrintWriter:close	()V
    //   88: aload 6
    //   90: athrow
    //   91: astore 6
    //   93: aload 4
    //   95: astore_3
    //   96: goto -16 -> 80
    //   99: astore 5
    //   101: aload 4
    //   103: astore_3
    //   104: goto -50 -> 54
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	107	0	paramActivity	Activity
    //   0	107	1	paramString1	String
    //   0	107	2	paramString2	String
    //   1	103	3	localObject1	Object
    //   28	74	4	localPrintWriter	java.io.PrintWriter
    //   52	10	5	localIOException1	java.io.IOException
    //   99	1	5	localIOException2	java.io.IOException
    //   78	11	6	localObject2	Object
    //   91	1	6	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   2	30	52	java/io/IOException
    //   2	30	78	finally
    //   54	67	78	finally
    //   30	41	91	finally
    //   30	41	99	java/io/IOException
  }
  
  static boolean sendClick(String paramString1, String paramString2, String paramString3)
  {
    return sendRequestBool(getClickURL(paramString1, paramString2, paramString3));
  }
  
  static boolean sendConversion(Activity paramActivity)
  {
    boolean bool = false;
    if (useConversion(paramActivity, AMoAdSdkWallActivity.class, false).booleanValue()) {
      bool = sendRequestBool(getConversionURL(paramActivity));
    }
    Log.v("AMoAdSdk", "sendConversion=========retWall==========" + bool);
    if (bool) {
      saveConversion(paramActivity);
    }
    return bool;
  }
  
  public static SimpleHttpResponse sendHeadRequest(String paramString)
  {
    Log.d("AMoAdSdk", paramString);
    HttpURLConnection localHttpURLConnection = null;
    try
    {
      localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      localHttpURLConnection.setInstanceFollowRedirects(true);
      localHttpURLConnection.setRequestProperty("Accept-Language", "ja;q=0.7,en;q=0.3");
      localHttpURLConnection.connect();
      localSimpleHttpResponse = new SimpleHttpResponse(localHttpURLConnection);
      return localSimpleHttpResponse;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        if (localHttpURLConnection != null) {
          localHttpURLConnection.disconnect();
        }
        Log.d("AMoAdSdk", "リクエストヘッダーのみ受信は[" + paramString + "]への接続で例外" + localThrowable.getMessage());
        SimpleHttpResponse localSimpleHttpResponse = new SimpleHttpResponse(null);
      }
    }
  }
  
  public static SimpleHttpResponse sendRequest(String paramString)
  {
    Log.d("AMoAdSdk", paramString);
    HttpURLConnection localHttpURLConnection = null;
    try
    {
      localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      localHttpURLConnection.setInstanceFollowRedirects(true);
      localHttpURLConnection.setRequestProperty("Accept-Language", "ja;q=0.7,en;q=0.3");
      localHttpURLConnection.setRequestMethod("GET");
      localHttpURLConnection.connect();
      localSimpleHttpResponse = new SimpleHttpResponse(localHttpURLConnection, true);
      return localSimpleHttpResponse;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        if (localHttpURLConnection != null) {
          localHttpURLConnection.disconnect();
        }
        Log.d("AMoAdSdk", "コンテンツ含めて受信は[" + paramString + "]への接続で例外" + localThrowable.getMessage());
        SimpleHttpResponse localSimpleHttpResponse = new SimpleHttpResponse(null);
      }
    }
  }
  
  private static boolean sendRequestBool(String paramString)
  {
    boolean bool = true;
    try
    {
      Log.v("AMoAdSdk", paramString);
      if (webView == null)
      {
        webView = new WebView(activity);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVisibility(4);
        webView.clearCache(true);
      }
      webView.stopLoading();
      webView.loadUrl(paramString);
      return bool;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        Log.v("AMoAdSdk", "HTTPリクエスト失敗。url=" + paramString, localThrowable);
        bool = false;
      }
    }
  }
  
  public static void setExecCount(Activity paramActivity, int paramInt)
  {
    saveToContextFile(paramActivity, "AMoAdSdk_ExecCount", paramInt);
  }
  
  public static void setShowCount(Activity paramActivity, int paramInt)
  {
    saveToContextFile(paramActivity, "AMoAdSdk_ShowDate", new SimpleDateFormat("yyyyMMdd").format(new Date()));
    saveToContextFile(paramActivity, "AMoAdSdk_ShowCount", paramInt);
  }
  
  private static void setupMetadata()
  {
    setupMetadata(bundle, activity);
  }
  
  private static void setupMetadata(Bundle paramBundle, Activity paramActivity)
  {
    try
    {
      bundle = paramBundle;
      activity = paramActivity;
      appKey = paramBundle.getString("app_key");
      screen = paramBundle.getString("screen");
      debugMode = getUpperStr(paramBundle, "debug");
      conversion = Boolean.valueOf(paramBundle.getBoolean("conversion", false));
      wallAppendix = paramBundle.getString("wall_appendix");
      conversionAppendix = paramBundle.getString("conversion_appendix");
      countryName = paramBundle.getString("country_name");
      if ((countryName == null) || ("".equals(countryName))) {
        countryName = "JP";
      }
      Log.v("AMoAdSdk", "appKey:" + appKey);
      Log.v("AMoAdSdk", "debug mode:" + debugMode);
      Log.v("AMoAdSdk", "conversion:" + conversion);
      Log.v("AMoAdSdk", "screen:" + screen);
      Log.v("AMoAdSdk", "wallAppendix:" + wallAppendix);
      Log.v("AMoAdSdk", "conversionAppendix:" + conversionAppendix);
      Log.v("AMoAdSdk", "countryName:" + countryName);
      return;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        if (appKey == null) {
          appKey = "0000000000000000";
        }
        if (screen == null) {
          screen = "auto";
        }
        if (conversion == null) {
          conversion = Boolean.valueOf(false);
        }
        if (wallAppendix == null) {
          wallAppendix = "";
        }
        if (conversionAppendix == null) {
          conversionAppendix = "";
        }
        if (countryName == null) {
          countryName = "JP";
        }
        Log.v("AMoAdSdk", "setupMetadata error", localThrowable);
      }
    }
  }
  
  static void startInitialize(Activity paramActivity)
  {
    try
    {
      Bundle localBundle = paramActivity.getPackageManager().getActivityInfo(paramActivity.getComponentName(), 128).metaData;
      setupMetadata(localBundle, paramActivity);
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        Log.e("AMoAdSdk", "onCreate Error! write AndroidManifest.xml.", localNameNotFoundException);
      }
    }
  }
  
  static void startInitializeForOther(Activity paramActivity, Class<?> paramClass)
  {
    setupMetadata(getBundle(paramActivity, paramClass), paramActivity);
  }
  
  private static String toSHA256DigestString(String paramString)
    throws NoSuchAlgorithmException
  {
    byte[] arrayOfByte = MessageDigest.getInstance("SHA-256").digest(paramString.getBytes());
    StringBuilder localStringBuilder = new StringBuilder();
    int i = arrayOfByte.length;
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return localStringBuilder.toString().toUpperCase();
      }
      int k = arrayOfByte[j];
      String str = "0" + Integer.toHexString(k);
      localStringBuilder.append(str.substring(-2 + str.length()));
    }
  }
  
  private static Boolean useConversion(Activity paramActivity, Class<?> paramClass, boolean paramBoolean)
  {
    Bundle localBundle = getBundle(paramActivity, paramClass);
    setupMetadata(localBundle, paramActivity);
    boolean bool = localBundle.getBoolean("conversion", false);
    if ((bool) && (!paramBoolean)) {
      if (!wasConversion(paramActivity)) {
        break label46;
      }
    }
    label46:
    for (bool = false;; bool = true) {
      return Boolean.valueOf(bool);
    }
  }
  
  public static boolean wasConversion(Activity paramActivity)
  {
    if (readFromContextFile(paramActivity, "AMoAdSdk_ConversionDate") != null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public static enum DESTINATIONS
  {
    pre,  wall,  adw;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.Util
 * JD-Core Version:    0.7.0.1
 */