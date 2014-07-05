package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class AdfurikunApiAccessUtil
{
  private static final String GETINFO_URL_AMAZON = "http://d830x8j3o1b2k.cloudfront.net/";
  private static final String GETINFO_URL_DEVELOPMENT = "http://115.30.5.174/";
  private static final String GETINFO_URL_PRODUCTION = "https://adfurikun.jp/";
  private static final String GETINFO_URL_STAGING = "http://115.30.27.96/";
  private static final String REC_URL_AMAZON = "http://d2cjo8xlt6fbwy.cloudfront.net/";
  private static final String REC_URL_DEVELOPMENT = "http://115.30.5.174/";
  private static final String REC_URL_PRODUCTION = "http://api.adfurikun.jp/";
  private static final String REC_URL_STAGING = "http://115.30.27.96/";
  private static int SERVER_TYPE = 4;
  private static final int SERVER_TYPE_AMAZON = 4;
  private static final int SERVER_TYPE_DEVELOPMENT = 0;
  private static final int SERVER_TYPE_PRODUCTION = 1;
  private static final int SERVER_TYPE_STAGING = 3;
  private static final String WEBAPI_GETINFO = "adfurikun/api/getinfo/";
  private static final String WEBAPI_HOUSEAD_CLICK = "adfurikun/api/rec-click";
  private static final String WEBAPI_HOUSEAD_IMPRESSION = "adfurikun/api/rec-impression";
  private static final String WEBAPI_KEY_ADNETWORKKEY = "adnetwork_key";
  private static final String WEBAPI_KEY_BG_COLOR = "bg_color";
  private static final String WEBAPI_KEY_CYCLE_TIME = "cycle_time";
  private static final String WEBAPI_KEY_DEVICE_ID = "device_id";
  private static final String WEBAPI_KEY_HTML = "html";
  private static final String WEBAPI_KEY_IS_TEXT = "is_text";
  private static final String WEBAPI_KEY_SETTINGS = "settings";
  private static final String WEBAPI_KEY_TRANSITION_OFF = "ta_off";
  private static final String WEBAPI_KEY_USER_AD_ID = "user_ad_id";
  private static final String WEBAPI_KEY_WEIGHT = "weight";
  private static final String WEBAPI_OPTION_APP_ID = "app_id/";
  private static final String WEBAPI_OPTION_APP_ID_AMAZON = "app_id=";
  private static final String WEBAPI_OPTION_DEVICE_ID = "device_id/";
  private static final String WEBAPI_OPTION_DEVICE_ID_AMAZON = "device_id=";
  private static final String WEBAPI_OPTION_LOCALE = "locale/";
  private static final String WEBAPI_OPTION_LOCALE_AMAZON = "locale=";
  private static final String WEBAPI_OPTION_USERAD_ID = "user_ad_id/";
  private static final String WEBAPI_OPTION_USERAD_ID_AMAZON = "user_ad_id=";
  private static final String WEBAPI_OPTION_VERSION = "ver/";
  private static final String WEBAPI_TAPCHK_OFF_FLG = "tapchk_off_flg";
  private static final String WEBAPI_WALL_TYPE = "wall_type";
  
  public static WebAPIResult callWebAPI(String paramString1, AdfurikunLogUtil paramAdfurikunLogUtil, String paramString2)
  {
    WebAPIResult localWebAPIResult = new WebAPIResult();
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    try
    {
      HttpGet localHttpGet = new HttpGet(paramString1);
      localHttpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
      HttpParams localHttpParams = localDefaultHttpClient.getParams();
      HttpConnectionParams.setConnectionTimeout(localHttpParams, 5000);
      HttpConnectionParams.setSoTimeout(localHttpParams, 5000);
      if ((paramString2 != null) && (paramString2.length() > 0)) {
        localHttpParams.setParameter("http.useragent", paramString2);
      }
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpGet);
      localWebAPIResult.return_code = localHttpResponse.getStatusLine().getStatusCode();
      if (AdfurikunConstants.DETAIL_LOG) {
        paramAdfurikunLogUtil.debug(AdfurikunConstants.TAG_NAME, "return_code=" + localWebAPIResult.return_code);
      }
      if (localWebAPIResult.return_code == 200)
      {
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        localHttpResponse.getEntity().writeTo(localByteArrayOutputStream);
        localWebAPIResult.message = localByteArrayOutputStream.toString();
      }
      for (;;)
      {
        return localWebAPIResult;
        if (localWebAPIResult.return_code != 404) {
          break label304;
        }
        if (!AdfurikunConstants.DETAIL_LOG) {
          break;
        }
        paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, "url not found:" + paramString1);
      }
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      for (;;)
      {
        localWebAPIResult.return_code = AdfurikunConstants.WEBAPI_EXCEPTIONERR;
        paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, "ClientProtocolException");
        paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, localClientProtocolException);
        continue;
        paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, "url not found");
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;)
      {
        localWebAPIResult.return_code = AdfurikunConstants.WEBAPI_EXCEPTIONERR;
        paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, "IllegalArgumentException");
        paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, localIllegalArgumentException);
        continue;
        if (localWebAPIResult.return_code != 408) {
          break;
        }
        paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, "SC_REQUEST_TIMEOUT");
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        label304:
        localWebAPIResult.return_code = AdfurikunConstants.WEBAPI_EXCEPTIONERR;
        paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, "IOException");
        paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, localIOException);
        continue;
        if (localWebAPIResult.return_code == 400) {
          paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, "SC_BAD_REQUEST");
        } else {
          paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, "取得時エラー発生");
        }
      }
    }
  }
  
  private static void getAdInfo(Context paramContext, String paramString1, AdfurikunInfo paramAdfurikunInfo, String paramString2, AdfurikunLogUtil paramAdfurikunLogUtil, boolean paramBoolean)
  {
    String str1;
    JSONArray localJSONArray;
    int j;
    try
    {
      str1 = paramContext.getApplicationContext().getCacheDir().getPath() + AdfurikunConstants.ADFURIKUN_FOLDER + paramString1 + "/";
      if ((paramString2 != null) && (paramString2.length() > 0))
      {
        localJSONArray = new JSONArray(paramString2);
        int i = localJSONArray.length();
        paramAdfurikunLogUtil.debug_i(AdfurikunConstants.TAG_NAME, "---------------------------------------------------------");
        if (AdfurikunConstants.DETAIL_LOG)
        {
          paramAdfurikunLogUtil.debug_i(AdfurikunConstants.TAG_NAME, "[adnetwork_key][user_ad_id]weight");
          break label746;
          if (j < i) {
            break label165;
          }
          paramAdfurikunLogUtil.debug_i(AdfurikunConstants.TAG_NAME, "---------------------------------------------------------");
        }
      }
      else
      {
        paramAdfurikunInfo.initCalc();
        break label752;
      }
      paramAdfurikunLogUtil.debug_i(AdfurikunConstants.TAG_NAME, "[adnetwork_key]weight");
    }
    catch (JSONException localJSONException)
    {
      paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, "JSONException");
      paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, localJSONException);
      break label752;
    }
    label165:
    String str2 = localJSONArray.getString(j);
    JSONObject localJSONObject;
    AdfurikunInfo.AdInfo localAdInfo;
    label214:
    int m;
    label293:
    label373:
    String str3;
    if ((str2 != null) && (str2.length() > 0))
    {
      localJSONObject = new JSONObject(str2);
      Iterator localIterator = localJSONObject.keys();
      localAdInfo = new AdfurikunInfo.AdInfo();
      boolean bool2;
      do
      {
        for (;;)
        {
          if (!localIterator.hasNext())
          {
            if (!AdfurikunConstants.DETAIL_LOG) {
              break label694;
            }
            paramAdfurikunLogUtil.debug_i(AdfurikunConstants.TAG_NAME, "[" + localAdInfo.adnetwork_key + "]" + "[" + localAdInfo.user_ad_id + "]" + localAdInfo.weight);
            String str4 = str1 + localAdInfo.adnetwork_key + "_" + localAdInfo.user_ad_id + ".html";
            m = 0;
            if (!paramBoolean) {
              break label740;
            }
            File localFile = new File(str4);
            if ((localFile != null) && (!localFile.exists())) {
              m = 1;
            }
            if (m != 0) {
              saveStringFile(str1 + localAdInfo.adnetwork_key + "_" + localAdInfo.user_ad_id + ".html", localAdInfo.html);
            }
            paramAdfurikunInfo.infoArray.add(localAdInfo);
            break label753;
          }
          str3 = (String)localIterator.next();
          if ("weight".equals(str3))
          {
            localAdInfo.weight = new JSONObject(localJSONObject.getString(str3));
          }
          else if ("adnetwork_key".equals(str3))
          {
            localAdInfo.adnetwork_key = localJSONObject.getString(str3);
          }
          else if ("user_ad_id".equals(str3))
          {
            localAdInfo.user_ad_id = localJSONObject.getString(str3);
          }
          else if ("html".equals(str3))
          {
            localAdInfo.html = new String(Base64.decode(localJSONObject.getString(str3), 0));
          }
          else if ("is_text".equals(str3))
          {
            localAdInfo.is_text = localJSONObject.getString(str3);
          }
          else
          {
            boolean bool1 = "wall_type".equals(str3);
            if (!bool1) {
              break;
            }
            try
            {
              localAdInfo.wall_type = Integer.parseInt(localJSONObject.getString(str3));
            }
            catch (NumberFormatException localNumberFormatException2)
            {
              localAdInfo.wall_type = AdfurikunConstants.WALL_TYPE_NONE;
            }
          }
        }
        bool2 = "tapchk_off_flg".equals(str3);
      } while (!bool2);
    }
    for (;;)
    {
      try
      {
        if (Integer.parseInt(localJSONObject.getString(str3)) != 1) {
          break label759;
        }
        k = 1;
        localAdInfo.tapchk_off_flg = k;
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        localAdInfo.tapchk_off_flg = 0;
      }
      break label214;
      label694:
      paramAdfurikunLogUtil.debug_i(AdfurikunConstants.TAG_NAME, "[" + localAdInfo.adnetwork_key + "]" + localAdInfo.weight);
      break label293;
      label740:
      m = 1;
      break label373;
      label746:
      j = 0;
      break;
      label752:
      return;
      label753:
      j++;
      break;
      label759:
      int k = 0;
    }
  }
  
  public static String getGetInfoBaseUrl()
  {
    String str = "http://115.30.5.174/";
    switch (SERVER_TYPE)
    {
    }
    for (;;)
    {
      return str;
      str = "http://115.30.5.174/";
      continue;
      str = "https://adfurikun.jp/";
      continue;
      str = "http://115.30.27.96/";
      continue;
      str = "http://d830x8j3o1b2k.cloudfront.net/";
    }
  }
  
  public static WebAPIResult getInfo(String paramString1, String paramString2, String paramString3, AdfurikunLogUtil paramAdfurikunLogUtil, String paramString4, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (paramBoolean)
    {
      localStringBuffer.append(getGetInfoBaseUrl());
      if (SERVER_TYPE == 4) {
        i = 1;
      }
      localStringBuffer.append("adfurikun/api/getinfo/");
      localStringBuffer.append("app_id/");
      localStringBuffer.append(paramString1);
      localStringBuffer.append("/");
      localStringBuffer.append("locale/");
      localStringBuffer.append(paramString3);
      localStringBuffer.append("/");
      localStringBuffer.append("ver/");
      localStringBuffer.append(AdfurikunConstants.ADFURIKUN_VERSION);
      if (AdfurikunConstants.DETAIL_LOG) {
        paramAdfurikunLogUtil.debug(AdfurikunConstants.TAG_NAME, ">>>>>>>>>>>>>>>>>getInfo()");
      }
      if (paramString2.length() > 0)
      {
        if (i == 0) {
          break label243;
        }
        localStringBuffer.append("?");
        localStringBuffer.append("device_id=");
        localStringBuffer.append(paramString2);
      }
    }
    for (;;)
    {
      if (AdfurikunConstants.DETAIL_LOG)
      {
        paramAdfurikunLogUtil.debug(AdfurikunConstants.TAG_NAME, ">>>>>>>>>>>>>>>>>app_id:" + paramString1);
        paramAdfurikunLogUtil.debug(AdfurikunConstants.TAG_NAME, ">>>>>>>>>>>>>>>>>locale:" + paramString3);
      }
      return callWebAPI(localStringBuffer.toString(), paramAdfurikunLogUtil, paramString4);
      localStringBuffer.append(getSubServer());
      break;
      label243:
      localStringBuffer.append("/");
      localStringBuffer.append("device_id/");
      localStringBuffer.append(paramString2);
    }
  }
  
  private static String getRecClickBaseUrl()
  {
    String str = "http://115.30.5.174/";
    switch (SERVER_TYPE)
    {
    }
    for (;;)
    {
      return str;
      str = "http://115.30.5.174/";
      continue;
      str = "http://api.adfurikun.jp/";
      continue;
      str = "http://115.30.27.96/";
      continue;
      str = "http://api.adfurikun.jp/";
    }
  }
  
  private static String getRecImpressionBaseUrl()
  {
    String str = "http://115.30.5.174/";
    switch (SERVER_TYPE)
    {
    }
    for (;;)
    {
      return str;
      str = "http://115.30.5.174/";
      continue;
      str = "http://api.adfurikun.jp/";
      continue;
      str = "http://115.30.27.96/";
      continue;
      str = "http://d2cjo8xlt6fbwy.cloudfront.net/";
    }
  }
  
  private static String getSubServer()
  {
    return "https://adfurikun.jp/";
  }
  
  public static boolean isUseSubServer()
  {
    if (SERVER_TYPE == 4) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  /* Error */
  public static String loadStringFile(String paramString)
  {
    // Byte code:
    //   0: ldc_w 438
    //   3: astore_1
    //   4: new 277	java/io/File
    //   7: dup
    //   8: aload_0
    //   9: invokespecial 348	java/io/File:<init>	(Ljava/lang/String;)V
    //   12: astore_2
    //   13: aload_2
    //   14: invokevirtual 351	java/io/File:exists	()Z
    //   17: ifeq +77 -> 94
    //   20: aconst_null
    //   21: astore_3
    //   22: new 440	java/io/FileInputStream
    //   25: dup
    //   26: aload_2
    //   27: invokespecial 443	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   30: astore 4
    //   32: new 445	java/io/BufferedReader
    //   35: dup
    //   36: new 447	java/io/InputStreamReader
    //   39: dup
    //   40: aload 4
    //   42: ldc_w 449
    //   45: invokespecial 452	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   48: invokespecial 455	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   51: astore 5
    //   53: new 405	java/lang/StringBuffer
    //   56: dup
    //   57: invokespecial 406	java/lang/StringBuffer:<init>	()V
    //   60: astore 6
    //   62: aload 5
    //   64: invokevirtual 458	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   67: astore 15
    //   69: aload 15
    //   71: ifnonnull +25 -> 96
    //   74: aload 6
    //   76: invokevirtual 423	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   79: astore 17
    //   81: aload 17
    //   83: astore_1
    //   84: aload 5
    //   86: ifnull +8 -> 94
    //   89: aload 5
    //   91: invokevirtual 461	java/io/BufferedReader:close	()V
    //   94: aload_1
    //   95: areturn
    //   96: aload 6
    //   98: aload 15
    //   100: invokevirtual 411	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   103: pop
    //   104: goto -42 -> 62
    //   107: astore 13
    //   109: aload 5
    //   111: astore_3
    //   112: aload_3
    //   113: ifnull -19 -> 94
    //   116: aload_3
    //   117: invokevirtual 461	java/io/BufferedReader:close	()V
    //   120: goto -26 -> 94
    //   123: astore 14
    //   125: goto -31 -> 94
    //   128: astore 24
    //   130: aload_3
    //   131: ifnull -37 -> 94
    //   134: aload_3
    //   135: invokevirtual 461	java/io/BufferedReader:close	()V
    //   138: goto -44 -> 94
    //   141: astore 8
    //   143: goto -49 -> 94
    //   146: astore 23
    //   148: aload_3
    //   149: ifnull -55 -> 94
    //   152: aload_3
    //   153: invokevirtual 461	java/io/BufferedReader:close	()V
    //   156: goto -62 -> 94
    //   159: astore 10
    //   161: goto -67 -> 94
    //   164: astore 11
    //   166: aload_3
    //   167: ifnull +7 -> 174
    //   170: aload_3
    //   171: invokevirtual 461	java/io/BufferedReader:close	()V
    //   174: aload 11
    //   176: athrow
    //   177: astore 12
    //   179: goto -5 -> 174
    //   182: astore 18
    //   184: goto -90 -> 94
    //   187: astore 11
    //   189: goto -23 -> 166
    //   192: astore 11
    //   194: aload 5
    //   196: astore_3
    //   197: goto -31 -> 166
    //   200: astore 21
    //   202: goto -54 -> 148
    //   205: astore 9
    //   207: aload 5
    //   209: astore_3
    //   210: goto -62 -> 148
    //   213: astore 20
    //   215: goto -85 -> 130
    //   218: astore 7
    //   220: aload 5
    //   222: astore_3
    //   223: goto -93 -> 130
    //   226: astore 22
    //   228: goto -116 -> 112
    //   231: astore 19
    //   233: goto -121 -> 112
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	236	0	paramString	String
    //   3	92	1	localObject1	Object
    //   12	15	2	localFile	File
    //   21	202	3	localObject2	Object
    //   30	11	4	localFileInputStream	java.io.FileInputStream
    //   51	170	5	localBufferedReader	java.io.BufferedReader
    //   60	37	6	localStringBuffer	StringBuffer
    //   218	1	7	localUnsupportedEncodingException1	java.io.UnsupportedEncodingException
    //   141	1	8	localIOException1	IOException
    //   205	1	9	localIOException2	IOException
    //   159	1	10	localIOException3	IOException
    //   164	11	11	localObject3	Object
    //   187	1	11	localObject4	Object
    //   192	1	11	localObject5	Object
    //   177	1	12	localIOException4	IOException
    //   107	1	13	localFileNotFoundException1	java.io.FileNotFoundException
    //   123	1	14	localIOException5	IOException
    //   67	32	15	str1	String
    //   79	3	17	str2	String
    //   182	1	18	localIOException6	IOException
    //   231	1	19	localFileNotFoundException2	java.io.FileNotFoundException
    //   213	1	20	localUnsupportedEncodingException2	java.io.UnsupportedEncodingException
    //   200	1	21	localIOException7	IOException
    //   226	1	22	localFileNotFoundException3	java.io.FileNotFoundException
    //   146	1	23	localIOException8	IOException
    //   128	1	24	localUnsupportedEncodingException3	java.io.UnsupportedEncodingException
    // Exception table:
    //   from	to	target	type
    //   53	81	107	java/io/FileNotFoundException
    //   96	104	107	java/io/FileNotFoundException
    //   116	120	123	java/io/IOException
    //   22	32	128	java/io/UnsupportedEncodingException
    //   134	138	141	java/io/IOException
    //   22	32	146	java/io/IOException
    //   152	156	159	java/io/IOException
    //   22	32	164	finally
    //   170	174	177	java/io/IOException
    //   89	94	182	java/io/IOException
    //   32	53	187	finally
    //   53	81	192	finally
    //   96	104	192	finally
    //   32	53	200	java/io/IOException
    //   53	81	205	java/io/IOException
    //   96	104	205	java/io/IOException
    //   32	53	213	java/io/UnsupportedEncodingException
    //   53	81	218	java/io/UnsupportedEncodingException
    //   96	104	218	java/io/UnsupportedEncodingException
    //   22	32	226	java/io/FileNotFoundException
    //   32	53	231	java/io/FileNotFoundException
  }
  
  public static WebAPIResult recClick(String paramString1, String paramString2, String paramString3, String paramString4, AdfurikunLogUtil paramAdfurikunLogUtil, String paramString5)
  {
    if (AdfurikunConstants.DETAIL_LOG) {
      paramAdfurikunLogUtil.debug(AdfurikunConstants.TAG_NAME, ">>>>>>>>>>>>>>>>>recClick()");
    }
    int i = 0;
    String str = getRecClickBaseUrl();
    if (str.equals("http://d2cjo8xlt6fbwy.cloudfront.net/")) {
      i = 1;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(str);
    localStringBuffer.append("adfurikun/api/rec-click");
    if (i != 0)
    {
      localStringBuffer.append("?");
      localStringBuffer.append("app_id=");
      localStringBuffer.append(paramString1);
      localStringBuffer.append("&");
      localStringBuffer.append("locale=");
      localStringBuffer.append(paramString4);
      localStringBuffer.append("&");
      localStringBuffer.append("user_ad_id=");
      localStringBuffer.append(paramString2);
      if (paramString3.length() > 0)
      {
        localStringBuffer.append("&");
        localStringBuffer.append("device_id=");
        localStringBuffer.append(paramString3);
      }
    }
    for (;;)
    {
      if (AdfurikunConstants.DETAIL_LOG)
      {
        paramAdfurikunLogUtil.debug(AdfurikunConstants.TAG_NAME, ">>>>>>>>>>>>>>>>>app_id:" + paramString1);
        paramAdfurikunLogUtil.debug(AdfurikunConstants.TAG_NAME, ">>>>>>>>>>>>>>>>>user_ad_id:" + paramString2);
      }
      return callWebAPI(localStringBuffer.toString(), paramAdfurikunLogUtil, paramString5);
      localStringBuffer.append("/");
      localStringBuffer.append("app_id/");
      localStringBuffer.append(paramString1);
      localStringBuffer.append("/");
      localStringBuffer.append("locale/");
      localStringBuffer.append(paramString4);
      localStringBuffer.append("/");
      localStringBuffer.append("user_ad_id/");
      localStringBuffer.append(paramString2);
      if (paramString3.length() > 0)
      {
        localStringBuffer.append("/");
        localStringBuffer.append("device_id/");
        localStringBuffer.append(paramString3);
      }
    }
  }
  
  public static WebAPIResult recImpression(String paramString1, String paramString2, String paramString3, String paramString4, AdfurikunLogUtil paramAdfurikunLogUtil, String paramString5)
  {
    if (AdfurikunConstants.DETAIL_LOG) {
      paramAdfurikunLogUtil.debug(AdfurikunConstants.TAG_NAME, ">>>>>>>>>>>>>>>>>recImpression()");
    }
    int i = 0;
    String str = getRecImpressionBaseUrl();
    if (str.equals("http://d2cjo8xlt6fbwy.cloudfront.net/")) {
      i = 1;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(str);
    localStringBuffer.append("adfurikun/api/rec-impression");
    if (i != 0)
    {
      localStringBuffer.append("?");
      localStringBuffer.append("app_id=");
      localStringBuffer.append(paramString1);
      localStringBuffer.append("&");
      localStringBuffer.append("locale=");
      localStringBuffer.append(paramString4);
      localStringBuffer.append("&");
      localStringBuffer.append("user_ad_id=");
      localStringBuffer.append(paramString2);
      if (paramString3.length() > 0)
      {
        localStringBuffer.append("&");
        localStringBuffer.append("device_id=");
        localStringBuffer.append(paramString3);
      }
    }
    for (;;)
    {
      if (AdfurikunConstants.DETAIL_LOG)
      {
        paramAdfurikunLogUtil.debug(AdfurikunConstants.TAG_NAME, ">>>>>>>>>>>>>>>>>app_id:" + paramString1);
        paramAdfurikunLogUtil.debug(AdfurikunConstants.TAG_NAME, ">>>>>>>>>>>>>>>>>user_ad_id:" + paramString2);
      }
      return callWebAPI(localStringBuffer.toString(), paramAdfurikunLogUtil, paramString5);
      localStringBuffer.append("/");
      localStringBuffer.append("app_id/");
      localStringBuffer.append(paramString1);
      localStringBuffer.append("/");
      localStringBuffer.append("locale/");
      localStringBuffer.append(paramString4);
      localStringBuffer.append("/");
      localStringBuffer.append("user_ad_id/");
      localStringBuffer.append(paramString2);
      if (paramString3.length() > 0)
      {
        localStringBuffer.append("/");
        localStringBuffer.append("device_id/");
        localStringBuffer.append(paramString3);
      }
    }
  }
  
  /* Error */
  public static void saveStringFile(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 161	java/lang/String:length	()I
    //   4: ifle +79 -> 83
    //   7: new 277	java/io/File
    //   10: dup
    //   11: aload_0
    //   12: invokespecial 348	java/io/File:<init>	(Ljava/lang/String;)V
    //   15: astore_2
    //   16: aload_2
    //   17: invokevirtual 479	java/io/File:getParentFile	()Ljava/io/File;
    //   20: astore_3
    //   21: aload_3
    //   22: invokevirtual 351	java/io/File:exists	()Z
    //   25: ifne +8 -> 33
    //   28: aload_3
    //   29: invokevirtual 482	java/io/File:mkdirs	()Z
    //   32: pop
    //   33: aconst_null
    //   34: astore 4
    //   36: new 484	java/io/FileOutputStream
    //   39: dup
    //   40: aload_2
    //   41: invokespecial 485	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   44: astore 5
    //   46: new 487	java/io/PrintWriter
    //   49: dup
    //   50: new 489	java/io/OutputStreamWriter
    //   53: dup
    //   54: aload 5
    //   56: ldc_w 449
    //   59: invokespecial 492	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   62: invokespecial 495	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   65: astore 6
    //   67: aload 6
    //   69: aload_1
    //   70: invokevirtual 498	java/io/PrintWriter:write	(Ljava/lang/String;)V
    //   73: aload 6
    //   75: ifnull +8 -> 83
    //   78: aload 6
    //   80: invokevirtual 499	java/io/PrintWriter:close	()V
    //   83: return
    //   84: astore 13
    //   86: aload 4
    //   88: ifnull -5 -> 83
    //   91: aload 4
    //   93: invokevirtual 499	java/io/PrintWriter:close	()V
    //   96: goto -13 -> 83
    //   99: astore 12
    //   101: aload 4
    //   103: ifnull -20 -> 83
    //   106: aload 4
    //   108: invokevirtual 499	java/io/PrintWriter:close	()V
    //   111: goto -28 -> 83
    //   114: astore 9
    //   116: aload 4
    //   118: ifnull +8 -> 126
    //   121: aload 4
    //   123: invokevirtual 499	java/io/PrintWriter:close	()V
    //   126: aload 9
    //   128: athrow
    //   129: astore 9
    //   131: goto -15 -> 116
    //   134: astore 9
    //   136: aload 6
    //   138: astore 4
    //   140: goto -24 -> 116
    //   143: astore 11
    //   145: goto -44 -> 101
    //   148: astore 8
    //   150: aload 6
    //   152: astore 4
    //   154: goto -53 -> 101
    //   157: astore 10
    //   159: goto -73 -> 86
    //   162: astore 7
    //   164: aload 6
    //   166: astore 4
    //   168: goto -82 -> 86
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	171	0	paramString1	String
    //   0	171	1	paramString2	String
    //   15	26	2	localFile1	File
    //   20	9	3	localFile2	File
    //   34	133	4	localObject1	Object
    //   44	11	5	localFileOutputStream	java.io.FileOutputStream
    //   65	100	6	localPrintWriter	java.io.PrintWriter
    //   162	1	7	localFileNotFoundException1	java.io.FileNotFoundException
    //   148	1	8	localUnsupportedEncodingException1	java.io.UnsupportedEncodingException
    //   114	13	9	localObject2	Object
    //   129	1	9	localObject3	Object
    //   134	1	9	localObject4	Object
    //   157	1	10	localFileNotFoundException2	java.io.FileNotFoundException
    //   143	1	11	localUnsupportedEncodingException2	java.io.UnsupportedEncodingException
    //   99	1	12	localUnsupportedEncodingException3	java.io.UnsupportedEncodingException
    //   84	1	13	localFileNotFoundException3	java.io.FileNotFoundException
    // Exception table:
    //   from	to	target	type
    //   36	46	84	java/io/FileNotFoundException
    //   36	46	99	java/io/UnsupportedEncodingException
    //   36	46	114	finally
    //   46	67	129	finally
    //   67	73	134	finally
    //   46	67	143	java/io/UnsupportedEncodingException
    //   67	73	148	java/io/UnsupportedEncodingException
    //   46	67	157	java/io/FileNotFoundException
    //   67	73	162	java/io/FileNotFoundException
  }
  
  public static AdfurikunInfo stringToInfo(Context paramContext, String paramString1, String paramString2, AdfurikunLogUtil paramAdfurikunLogUtil, boolean paramBoolean)
  {
    AdfurikunInfo localAdfurikunInfo;
    JSONObject localJSONObject;
    String str1;
    if (paramString2.length() > 0)
    {
      localAdfurikunInfo = new AdfurikunInfo();
      for (;;)
      {
        try
        {
          localJSONObject = new JSONObject(paramString2);
          Iterator localIterator = localJSONObject.keys();
          if (!localIterator.hasNext()) {
            break label389;
          }
          str1 = (String)localIterator.next();
          if ("cycle_time".equals(str1))
          {
            localAdfurikunInfo.cycle_time = localJSONObject.getLong(str1);
            if (!AdfurikunConstants.DETAIL_LOG) {
              continue;
            }
            paramAdfurikunLogUtil.debug_i(AdfurikunConstants.TAG_NAME, "cycle_time[" + localAdfurikunInfo.cycle_time + "]");
            continue;
          }
          if (!"bg_color".equals(str1)) {
            break;
          }
        }
        catch (JSONException localJSONException)
        {
          paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, "JSONException");
          paramAdfurikunLogUtil.debug_e(AdfurikunConstants.TAG_NAME, localJSONException);
        }
        localAdfurikunInfo.bg_color = localJSONObject.getString(str1);
        if (AdfurikunConstants.DETAIL_LOG) {
          paramAdfurikunLogUtil.debug_i(AdfurikunConstants.TAG_NAME, "bg_color[" + localAdfurikunInfo.bg_color + "]");
        }
      }
      if ("ta_off".equals(str1)) {
        if (localJSONObject.getInt(str1) != 1) {
          break label392;
        }
      }
    }
    label389:
    label392:
    for (boolean bool = true;; bool = false)
    {
      localAdfurikunInfo.ta_off = bool;
      if (!AdfurikunConstants.DETAIL_LOG) {
        break;
      }
      paramAdfurikunLogUtil.debug_i(AdfurikunConstants.TAG_NAME, "ta_off[" + localAdfurikunInfo.ta_off + "]");
      break;
      if ("device_id".equals(str1))
      {
        String str2 = localJSONObject.getString(str1);
        if (str2.length() <= 0) {
          break;
        }
        SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(AdfurikunConstants.PREF_FILE, 3).edit();
        localEditor.putString(AdfurikunConstants.PREFKEY_DEVICE_ID, str2);
        localEditor.commit();
        break;
      }
      if (!"settings".equals(str1)) {
        break;
      }
      getAdInfo(paramContext, paramString1, localAdfurikunInfo, localJSONObject.getString(str1), paramAdfurikunLogUtil, paramBoolean);
      break;
      localAdfurikunInfo = null;
      return localAdfurikunInfo;
    }
  }
  
  public static class WebAPIResult
  {
    public JSONArray array = null;
    public String message = "";
    public int return_code = AdfurikunConstants.WEBAPI_EXCEPTIONERR;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunApiAccessUtil
 * JD-Core Version:    0.7.0.1
 */