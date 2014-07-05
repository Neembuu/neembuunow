package com.amoad.amoadsdk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import java.util.concurrent.ExecutionException;

public class AMoAdSdkPopUp
{
  static final String STATUS_CODE_SUCCESS = "0";
  static String Url = null;
  static String clickUrl = null;
  
  /* Error */
  private static String getJsonTextStatus(String paramString, Activity paramActivity)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 35
    //   3: iconst_0
    //   4: invokevirtual 41	android/app/Activity:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   7: astore_2
    //   8: aload_2
    //   9: ldc 43
    //   11: iconst_0
    //   12: invokeinterface 49 3 0
    //   17: istore_3
    //   18: aconst_null
    //   19: astore 4
    //   21: invokestatic 55	com/amoad/amoadsdk/Util:getAppKey	()Ljava/lang/String;
    //   24: astore 15
    //   26: aload_1
    //   27: invokevirtual 59	android/app/Activity:getPackageManager	()Landroid/content/pm/PackageManager;
    //   30: astore 16
    //   32: aload 16
    //   34: aload_1
    //   35: invokevirtual 62	android/app/Activity:getPackageName	()Ljava/lang/String;
    //   38: iconst_0
    //   39: invokevirtual 68	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   42: getfield 74	android/content/pm/PackageInfo:versionCode	I
    //   45: istore 18
    //   47: new 76	org/apache/http/impl/client/DefaultHttpClient
    //   50: dup
    //   51: invokespecial 77	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   54: astore 19
    //   56: aload 19
    //   58: invokeinterface 83 1 0
    //   63: astore 20
    //   65: aload 20
    //   67: sipush 5000
    //   70: invokestatic 89	org/apache/http/params/HttpConnectionParams:setSoTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   73: aload 20
    //   75: sipush 5000
    //   78: invokestatic 92	org/apache/http/params/HttpConnectionParams:setConnectionTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   81: aload 19
    //   83: new 94	org/apache/http/client/methods/HttpGet
    //   86: dup
    //   87: new 96	java/lang/StringBuilder
    //   90: dup
    //   91: getstatic 17	com/amoad/amoadsdk/AMoAdSdkPopUp:Url	Ljava/lang/String;
    //   94: invokestatic 102	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   97: invokespecial 105	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   100: ldc 107
    //   102: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: aload_0
    //   106: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   109: ldc 113
    //   111: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   114: aload 15
    //   116: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   119: ldc 115
    //   121: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: iload_3
    //   125: invokevirtual 118	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   128: ldc 120
    //   130: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   133: iload 18
    //   135: invokestatic 123	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   138: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   141: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   144: invokespecial 127	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
    //   147: invokeinterface 131 2 0
    //   152: astore 21
    //   154: aload 21
    //   156: invokeinterface 137 1 0
    //   161: invokeinterface 143 1 0
    //   166: istore 22
    //   168: aload 21
    //   170: invokeinterface 147 1 0
    //   175: invokeinterface 153 1 0
    //   180: astore 23
    //   182: new 155	java/io/InputStreamReader
    //   185: dup
    //   186: aload 23
    //   188: ldc 157
    //   190: invokespecial 160	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   193: astore 24
    //   195: new 162	java/io/BufferedReader
    //   198: dup
    //   199: aload 24
    //   201: invokespecial 165	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   204: astore 25
    //   206: new 167	java/lang/StringBuffer
    //   209: dup
    //   210: invokespecial 168	java/lang/StringBuffer:<init>	()V
    //   213: astore 26
    //   215: aload 25
    //   217: invokevirtual 171	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   220: astore 29
    //   222: aload 29
    //   224: ifnonnull +252 -> 476
    //   227: new 173	org/json/JSONObject
    //   230: dup
    //   231: aload 26
    //   233: invokevirtual 174	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   236: invokespecial 175	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   239: astore 30
    //   241: aload 30
    //   243: ldc 177
    //   245: invokevirtual 181	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   248: astore 31
    //   250: aload_2
    //   251: invokeinterface 185 1 0
    //   256: astore 32
    //   258: aload 31
    //   260: ldc 11
    //   262: invokevirtual 189	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   265: ifeq +150 -> 415
    //   268: aload 32
    //   270: new 96	java/lang/StringBuilder
    //   273: dup
    //   274: aload_0
    //   275: invokestatic 102	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   278: invokespecial 105	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   281: ldc 191
    //   283: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   286: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   289: aload 30
    //   291: ldc 193
    //   293: invokevirtual 181	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   296: invokeinterface 199 3 0
    //   301: pop
    //   302: aload 32
    //   304: new 96	java/lang/StringBuilder
    //   307: dup
    //   308: aload_0
    //   309: invokestatic 102	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   312: invokespecial 105	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   315: ldc 201
    //   317: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   320: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   323: aload 30
    //   325: ldc 201
    //   327: invokevirtual 181	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   330: invokeinterface 199 3 0
    //   335: pop
    //   336: aload 32
    //   338: new 96	java/lang/StringBuilder
    //   341: dup
    //   342: aload_0
    //   343: invokestatic 102	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   346: invokespecial 105	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   349: ldc 203
    //   351: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   354: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   357: new 96	java/lang/StringBuilder
    //   360: dup
    //   361: getstatic 19	com/amoad/amoadsdk/AMoAdSdkPopUp:clickUrl	Ljava/lang/String;
    //   364: invokestatic 102	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   367: invokespecial 105	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   370: ldc 107
    //   372: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   375: aload_0
    //   376: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   379: ldc 113
    //   381: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   384: aload 15
    //   386: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   389: ldc 205
    //   391: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   394: aload 30
    //   396: ldc 193
    //   398: invokevirtual 181	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   401: invokevirtual 111	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   404: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   407: invokeinterface 199 3 0
    //   412: pop
    //   413: iconst_0
    //   414: istore_3
    //   415: iload 22
    //   417: sipush 200
    //   420: if_icmpeq +10 -> 430
    //   423: iload 22
    //   425: invokestatic 123	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   428: astore 31
    //   430: aload 32
    //   432: ldc 43
    //   434: iload_3
    //   435: iconst_1
    //   436: iadd
    //   437: invokeinterface 209 3 0
    //   442: pop
    //   443: aload 32
    //   445: invokeinterface 213 1 0
    //   450: pop
    //   451: aload 25
    //   453: ifnull +8 -> 461
    //   456: aload 25
    //   458: invokevirtual 216	java/io/BufferedReader:close	()V
    //   461: aload 31
    //   463: astore 6
    //   465: aload 6
    //   467: areturn
    //   468: astore 17
    //   470: iconst_0
    //   471: istore 18
    //   473: goto -426 -> 47
    //   476: aload 26
    //   478: aload 29
    //   480: invokevirtual 219	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   483: pop
    //   484: goto -269 -> 215
    //   487: astore 28
    //   489: aload 25
    //   491: astore 4
    //   493: aload 4
    //   495: ifnull +8 -> 503
    //   498: aload 4
    //   500: invokevirtual 216	java/io/BufferedReader:close	()V
    //   503: aconst_null
    //   504: astore 6
    //   506: goto -41 -> 465
    //   509: astore 7
    //   511: ldc 221
    //   513: ldc 223
    //   515: aload 7
    //   517: invokestatic 229	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   520: pop
    //   521: goto -18 -> 503
    //   524: astore 12
    //   526: aload 4
    //   528: ifnull +8 -> 536
    //   531: aload 4
    //   533: invokevirtual 216	java/io/BufferedReader:close	()V
    //   536: aconst_null
    //   537: astore 6
    //   539: goto -74 -> 465
    //   542: astore 13
    //   544: ldc 221
    //   546: ldc 223
    //   548: aload 13
    //   550: invokestatic 229	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   553: pop
    //   554: goto -18 -> 536
    //   557: astore 9
    //   559: aload 4
    //   561: ifnull +8 -> 569
    //   564: aload 4
    //   566: invokevirtual 216	java/io/BufferedReader:close	()V
    //   569: aload 9
    //   571: athrow
    //   572: astore 10
    //   574: ldc 221
    //   576: ldc 223
    //   578: aload 10
    //   580: invokestatic 229	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   583: pop
    //   584: goto -15 -> 569
    //   587: astore 35
    //   589: ldc 221
    //   591: ldc 223
    //   593: aload 35
    //   595: invokestatic 229	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   598: pop
    //   599: goto -138 -> 461
    //   602: astore 9
    //   604: aload 25
    //   606: astore 4
    //   608: goto -49 -> 559
    //   611: astore 27
    //   613: aload 25
    //   615: astore 4
    //   617: goto -91 -> 526
    //   620: astore 5
    //   622: goto -129 -> 493
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	625	0	paramString	String
    //   0	625	1	paramActivity	Activity
    //   7	244	2	localSharedPreferences	SharedPreferences
    //   17	420	3	i	int
    //   19	597	4	localObject1	Object
    //   620	1	5	localJSONException1	org.json.JSONException
    //   463	75	6	str1	String
    //   509	7	7	localException1	Exception
    //   557	13	9	localObject2	Object
    //   602	1	9	localObject3	Object
    //   572	7	10	localException2	Exception
    //   524	1	12	localException3	Exception
    //   542	7	13	localException4	Exception
    //   24	361	15	str2	String
    //   30	3	16	localPackageManager	android.content.pm.PackageManager
    //   468	1	17	localNameNotFoundException	android.content.pm.PackageManager.NameNotFoundException
    //   45	427	18	j	int
    //   54	28	19	localDefaultHttpClient	org.apache.http.impl.client.DefaultHttpClient
    //   63	11	20	localHttpParams	org.apache.http.params.HttpParams
    //   152	17	21	localHttpResponse	org.apache.http.HttpResponse
    //   166	258	22	k	int
    //   180	7	23	localInputStream	java.io.InputStream
    //   193	7	24	localInputStreamReader	java.io.InputStreamReader
    //   204	410	25	localBufferedReader	java.io.BufferedReader
    //   213	264	26	localStringBuffer	java.lang.StringBuffer
    //   611	1	27	localException5	Exception
    //   487	1	28	localJSONException2	org.json.JSONException
    //   220	259	29	str3	String
    //   239	156	30	localJSONObject	org.json.JSONObject
    //   248	214	31	str4	String
    //   256	188	32	localEditor	SharedPreferences.Editor
    //   587	7	35	localException6	Exception
    // Exception table:
    //   from	to	target	type
    //   32	47	468	android/content/pm/PackageManager$NameNotFoundException
    //   206	451	487	org/json/JSONException
    //   476	484	487	org/json/JSONException
    //   498	503	509	java/lang/Exception
    //   21	32	524	java/lang/Exception
    //   32	47	524	java/lang/Exception
    //   47	206	524	java/lang/Exception
    //   531	536	542	java/lang/Exception
    //   21	32	557	finally
    //   32	47	557	finally
    //   47	206	557	finally
    //   564	569	572	java/lang/Exception
    //   456	461	587	java/lang/Exception
    //   206	451	602	finally
    //   476	484	602	finally
    //   206	451	611	java/lang/Exception
    //   476	484	611	java/lang/Exception
    //   21	32	620	org/json/JSONException
    //   32	47	620	org/json/JSONException
    //   47	206	620	org/json/JSONException
  }
  
  public static String getStatusClickPopup(Activity paramActivity, String paramString)
  {
    SharedPreferences localSharedPreferences = paramActivity.getSharedPreferences("popup_info", 0);
    String str = localSharedPreferences.getString(paramString + "_click_status", "9999");
    SharedPreferences.Editor localEditor = localSharedPreferences.edit();
    localEditor.putString(paramString + "_click_status", "9999");
    localEditor.commit();
    return str;
  }
  
  public static void startPopUp(Activity paramActivity, String paramString)
  {
    Util.startInitializeForOther(paramActivity, AMoAdSdkWallActivity.class);
    Url = Util.getBasePopupUrl();
    clickUrl = Util.getBaseTrPoClickUrl();
    AMoAdSdkPopUp localAMoAdSdkPopUp = new AMoAdSdkPopUp();
    localAMoAdSdkPopUp.getClass();
    PopupTextInfoTask localPopupTextInfoTask = new PopupTextInfoTask(localAMoAdSdkPopUp, paramActivity, paramString);
    String str = null;
    try
    {
      str = (String)localPopupTextInfoTask.execute(new String[0]).get();
      if ((str != null) && (str.equals("0")))
      {
        Intent localIntent = new Intent(paramActivity, AMoAdSdkPopUpActivity.class);
        localIntent.putExtra("triggerkey", paramString);
        paramActivity.startActivity(localIntent);
      }
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        localInterruptedException.printStackTrace();
      }
    }
    catch (ExecutionException localExecutionException)
    {
      for (;;)
      {
        localExecutionException.printStackTrace();
      }
    }
  }
  
  public static void startPopUpForUnity(Activity paramActivity, String paramString)
  {
    Util.startInitializeForOther(paramActivity, AMoAdSdkWallActivity.class);
    Url = Util.getBasePopupUrl();
    clickUrl = Util.getBaseTrPoClickUrl();
    AMoAdSdkPopUp localAMoAdSdkPopUp = new AMoAdSdkPopUp();
    localAMoAdSdkPopUp.getClass();
    PopupTextInfoTask localPopupTextInfoTask = new PopupTextInfoTask(localAMoAdSdkPopUp, paramActivity, paramString);
    String str = null;
    try
    {
      str = (String)localPopupTextInfoTask.execute(new String[0]).get();
      if ((str != null) && (str.equals("0")))
      {
        Intent localIntent = new Intent(paramActivity, AMoAdSdkPopUpForUnityActivity.class);
        localIntent.putExtra("triggerkey", paramString);
        paramActivity.startActivity(localIntent);
      }
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;)
      {
        localInterruptedException.printStackTrace();
      }
    }
    catch (ExecutionException localExecutionException)
    {
      for (;;)
      {
        localExecutionException.printStackTrace();
      }
    }
  }
  
  private class PopupTextInfoTask
    extends AsyncTask<String, Void, String>
  {
    private Activity activity = null;
    private String triggerKey = null;
    
    public PopupTextInfoTask(Activity paramActivity, String paramString)
    {
      this.activity = paramActivity;
      this.triggerKey = paramString;
    }
    
    protected String doInBackground(String... paramVarArgs)
    {
      try
      {
        String str2 = AMoAdSdkPopUp.getJsonTextStatus(this.triggerKey, this.activity);
        str1 = str2;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          String str1 = null;
        }
      }
      return str1;
    }
    
    protected void onPostExecute(String paramString) {}
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.AMoAdSdkPopUp
 * JD-Core Version:    0.7.0.1
 */