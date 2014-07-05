package com.amoad.amoadsdk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import java.lang.ref.WeakReference;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class AMoAdSdkTrigger
{
  static final String STATUS_CODE_SUCCESS = "0";
  static String Url = null;
  static String clickUrl = null;
  
  private String creHtmlBody(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer("<HTML>");
    localStringBuffer.append("<HEAD>");
    localStringBuffer.append("</HEAD>");
    localStringBuffer.append("<BODY  style='margin: 0; padding: 0'>");
    localStringBuffer.append("<img width=\"100%\" height=\"100%\" src=\"" + paramString + "\">");
    localStringBuffer.append("</BODY>");
    localStringBuffer.append("</HTML>");
    return localStringBuffer.toString();
  }
  
  /* Error */
  private String[] getJsonTextImageInfo(String paramString, Activity paramActivity)
  {
    // Byte code:
    //   0: iconst_5
    //   1: anewarray 91	java/lang/String
    //   4: astore_3
    //   5: aload_3
    //   6: iconst_0
    //   7: ldc 93
    //   9: aastore
    //   10: aload_3
    //   11: iconst_1
    //   12: ldc 93
    //   14: aastore
    //   15: aload_3
    //   16: iconst_2
    //   17: ldc 93
    //   19: aastore
    //   20: aload_3
    //   21: iconst_3
    //   22: ldc 93
    //   24: aastore
    //   25: aload_3
    //   26: iconst_4
    //   27: ldc 93
    //   29: aastore
    //   30: aconst_null
    //   31: astore 4
    //   33: invokestatic 98	com/amoad/amoadsdk/Util:getAppKey	()Ljava/lang/String;
    //   36: astore 14
    //   38: aload_2
    //   39: invokevirtual 104	android/app/Activity:getPackageManager	()Landroid/content/pm/PackageManager;
    //   42: astore 15
    //   44: aload 15
    //   46: aload_2
    //   47: invokevirtual 107	android/app/Activity:getPackageName	()Ljava/lang/String;
    //   50: iconst_0
    //   51: invokevirtual 113	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   54: getfield 119	android/content/pm/PackageInfo:versionCode	I
    //   57: istore 17
    //   59: new 121	org/apache/http/impl/client/DefaultHttpClient
    //   62: dup
    //   63: invokespecial 122	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   66: astore 18
    //   68: aload 18
    //   70: invokeinterface 128 1 0
    //   75: astore 19
    //   77: aload 19
    //   79: sipush 10000
    //   82: invokestatic 134	org/apache/http/params/HttpConnectionParams:setConnectionTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   85: aload 19
    //   87: sipush 10000
    //   90: invokestatic 137	org/apache/http/params/HttpConnectionParams:setSoTimeout	(Lorg/apache/http/params/HttpParams;I)V
    //   93: aload 18
    //   95: new 139	org/apache/http/client/methods/HttpGet
    //   98: dup
    //   99: new 66	java/lang/StringBuilder
    //   102: dup
    //   103: getstatic 24	com/amoad/amoadsdk/AMoAdSdkTrigger:Url	Ljava/lang/String;
    //   106: invokestatic 143	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   109: invokespecial 69	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   112: ldc 145
    //   114: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: aload_1
    //   118: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: ldc 147
    //   123: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: aload 14
    //   128: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: ldc 149
    //   133: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: iload 17
    //   138: invokestatic 152	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   141: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   144: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   147: invokespecial 153	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
    //   150: invokeinterface 157 2 0
    //   155: astore 20
    //   157: aload 20
    //   159: invokeinterface 163 1 0
    //   164: invokeinterface 169 1 0
    //   169: istore 21
    //   171: iload 21
    //   173: sipush 200
    //   176: if_icmpeq +44 -> 220
    //   179: aload_3
    //   180: iconst_0
    //   181: iload 21
    //   183: invokestatic 152	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   186: aastore
    //   187: iconst_0
    //   188: ifeq +7 -> 195
    //   191: aconst_null
    //   192: invokevirtual 174	java/io/BufferedReader:close	()V
    //   195: aload_3
    //   196: areturn
    //   197: astore 16
    //   199: iconst_0
    //   200: istore 17
    //   202: goto -143 -> 59
    //   205: astore 41
    //   207: ldc 176
    //   209: ldc 178
    //   211: aload 41
    //   213: invokestatic 184	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   216: pop
    //   217: goto -22 -> 195
    //   220: aload 20
    //   222: invokeinterface 188 1 0
    //   227: invokeinterface 194 1 0
    //   232: astore 22
    //   234: new 196	java/io/InputStreamReader
    //   237: dup
    //   238: aload 22
    //   240: ldc 198
    //   242: invokespecial 201	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   245: astore 23
    //   247: new 171	java/io/BufferedReader
    //   250: dup
    //   251: aload 23
    //   253: invokespecial 204	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   256: astore 24
    //   258: new 49	java/lang/StringBuffer
    //   261: dup
    //   262: invokespecial 205	java/lang/StringBuffer:<init>	()V
    //   265: astore 25
    //   267: aload 24
    //   269: invokevirtual 208	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   272: astore 28
    //   274: aload 28
    //   276: ifnonnull +258 -> 534
    //   279: new 210	org/json/JSONObject
    //   282: dup
    //   283: aload 25
    //   285: invokevirtual 83	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   288: invokespecial 211	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   291: astore 29
    //   293: aload 29
    //   295: ldc 213
    //   297: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   300: astore 30
    //   302: aload 30
    //   304: ldc 18
    //   306: invokevirtual 220	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   309: ifeq +207 -> 516
    //   312: aload_2
    //   313: ldc 222
    //   315: iconst_0
    //   316: invokevirtual 226	android/app/Activity:getSharedPreferences	(Ljava/lang/String;I)Landroid/content/SharedPreferences;
    //   319: invokeinterface 232 1 0
    //   324: astore 33
    //   326: aload 33
    //   328: aload_1
    //   329: aload 29
    //   331: ldc 234
    //   333: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   336: invokeinterface 240 3 0
    //   341: pop
    //   342: aload 33
    //   344: new 66	java/lang/StringBuilder
    //   347: dup
    //   348: aload_1
    //   349: invokestatic 143	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   352: invokespecial 69	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   355: ldc 242
    //   357: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   360: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   363: aload 29
    //   365: ldc 244
    //   367: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   370: invokeinterface 240 3 0
    //   375: pop
    //   376: aload 33
    //   378: new 66	java/lang/StringBuilder
    //   381: dup
    //   382: aload_1
    //   383: invokestatic 143	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   386: invokespecial 69	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   389: ldc 246
    //   391: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   394: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   397: aload 29
    //   399: ldc 248
    //   401: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   404: invokeinterface 240 3 0
    //   409: pop
    //   410: aload 33
    //   412: new 66	java/lang/StringBuilder
    //   415: dup
    //   416: aload_1
    //   417: invokestatic 143	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   420: invokespecial 69	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   423: ldc 250
    //   425: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   428: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   431: aload 29
    //   433: ldc 252
    //   435: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   438: invokeinterface 240 3 0
    //   443: pop
    //   444: aload 33
    //   446: new 66	java/lang/StringBuilder
    //   449: dup
    //   450: aload_1
    //   451: invokestatic 143	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   454: invokespecial 69	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   457: ldc 254
    //   459: invokevirtual 72	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   462: invokevirtual 78	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   465: aload 29
    //   467: ldc 213
    //   469: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   472: invokeinterface 240 3 0
    //   477: pop
    //   478: aload 33
    //   480: invokeinterface 258 1 0
    //   485: pop
    //   486: aload_3
    //   487: iconst_1
    //   488: aload 29
    //   490: ldc 252
    //   492: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   495: aastore
    //   496: aload_3
    //   497: iconst_2
    //   498: aload 29
    //   500: ldc 248
    //   502: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   505: aastore
    //   506: aload_3
    //   507: iconst_3
    //   508: aload 29
    //   510: ldc 244
    //   512: invokevirtual 216	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   515: aastore
    //   516: aload_3
    //   517: iconst_0
    //   518: aload 30
    //   520: aastore
    //   521: aload 24
    //   523: ifnull +8 -> 531
    //   526: aload 24
    //   528: invokevirtual 174	java/io/BufferedReader:close	()V
    //   531: goto -336 -> 195
    //   534: aload 25
    //   536: aload 28
    //   538: invokevirtual 60	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   541: pop
    //   542: goto -275 -> 267
    //   545: astore 27
    //   547: aload 24
    //   549: astore 4
    //   551: aload 4
    //   553: ifnull -358 -> 195
    //   556: aload 4
    //   558: invokevirtual 174	java/io/BufferedReader:close	()V
    //   561: goto -366 -> 195
    //   564: astore 6
    //   566: ldc 176
    //   568: ldc 178
    //   570: aload 6
    //   572: invokestatic 184	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   575: pop
    //   576: goto -381 -> 195
    //   579: astore 11
    //   581: aload 4
    //   583: ifnull -388 -> 195
    //   586: aload 4
    //   588: invokevirtual 174	java/io/BufferedReader:close	()V
    //   591: goto -396 -> 195
    //   594: astore 12
    //   596: ldc 176
    //   598: ldc 178
    //   600: aload 12
    //   602: invokestatic 184	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   605: pop
    //   606: goto -411 -> 195
    //   609: astore 8
    //   611: aload 4
    //   613: ifnull +8 -> 621
    //   616: aload 4
    //   618: invokevirtual 174	java/io/BufferedReader:close	()V
    //   621: aload 8
    //   623: athrow
    //   624: astore 9
    //   626: ldc 176
    //   628: ldc 178
    //   630: aload 9
    //   632: invokestatic 184	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   635: pop
    //   636: goto -15 -> 621
    //   639: astore 31
    //   641: ldc 176
    //   643: ldc 178
    //   645: aload 31
    //   647: invokestatic 184	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   650: pop
    //   651: goto -120 -> 531
    //   654: astore 8
    //   656: aload 24
    //   658: astore 4
    //   660: goto -49 -> 611
    //   663: astore 26
    //   665: aload 24
    //   667: astore 4
    //   669: goto -88 -> 581
    //   672: astore 5
    //   674: goto -123 -> 551
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	677	0	this	AMoAdSdkTrigger
    //   0	677	1	paramString	String
    //   0	677	2	paramActivity	Activity
    //   4	513	3	arrayOfString	String[]
    //   31	637	4	localObject1	Object
    //   672	1	5	localJSONException1	org.json.JSONException
    //   564	7	6	localException1	Exception
    //   609	13	8	localObject2	Object
    //   654	1	8	localObject3	Object
    //   624	7	9	localException2	Exception
    //   579	1	11	localException3	Exception
    //   594	7	12	localException4	Exception
    //   36	91	14	str1	String
    //   42	3	15	localPackageManager	android.content.pm.PackageManager
    //   197	1	16	localNameNotFoundException	android.content.pm.PackageManager.NameNotFoundException
    //   57	144	17	i	int
    //   66	28	18	localDefaultHttpClient	DefaultHttpClient
    //   75	11	19	localHttpParams	org.apache.http.params.HttpParams
    //   155	66	20	localHttpResponse	HttpResponse
    //   169	13	21	j	int
    //   232	7	22	localInputStream	java.io.InputStream
    //   245	7	23	localInputStreamReader	java.io.InputStreamReader
    //   256	410	24	localBufferedReader	java.io.BufferedReader
    //   265	270	25	localStringBuffer	StringBuffer
    //   663	1	26	localException5	Exception
    //   545	1	27	localJSONException2	org.json.JSONException
    //   272	265	28	str2	String
    //   291	218	29	localJSONObject	org.json.JSONObject
    //   300	219	30	str3	String
    //   639	7	31	localException6	Exception
    //   324	155	33	localEditor	SharedPreferences.Editor
    //   205	7	41	localException7	Exception
    // Exception table:
    //   from	to	target	type
    //   44	59	197	android/content/pm/PackageManager$NameNotFoundException
    //   191	195	205	java/lang/Exception
    //   258	521	545	org/json/JSONException
    //   534	542	545	org/json/JSONException
    //   556	561	564	java/lang/Exception
    //   33	44	579	java/lang/Exception
    //   44	59	579	java/lang/Exception
    //   59	187	579	java/lang/Exception
    //   220	258	579	java/lang/Exception
    //   586	591	594	java/lang/Exception
    //   33	44	609	finally
    //   44	59	609	finally
    //   59	187	609	finally
    //   220	258	609	finally
    //   616	621	624	java/lang/Exception
    //   526	531	639	java/lang/Exception
    //   258	521	654	finally
    //   534	542	654	finally
    //   258	521	663	java/lang/Exception
    //   534	542	663	java/lang/Exception
    //   33	44	672	org/json/JSONException
    //   44	59	672	org/json/JSONException
    //   59	187	672	org/json/JSONException
    //   220	258	672	org/json/JSONException
  }
  
  private void setWebview(WebView paramWebView, final Activity paramActivity, final String paramString)
  {
    paramWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    paramWebView.setScrollBarStyle(33554432);
    paramWebView.getSettings().setJavaScriptEnabled(true);
    paramWebView.setVerticalScrollBarEnabled(false);
    paramWebView.setHorizontalScrollBarEnabled(false);
    paramWebView.setFocusable(false);
    paramWebView.clearCache(true);
    paramWebView.getSettings().setCacheMode(2);
    paramWebView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        switch (paramAnonymousMotionEvent.getAction())
        {
        }
        for (;;)
        {
          return true;
          try
          {
            new AMoAdSdkTrigger.ClickResultSetSendTask(AMoAdSdkTrigger.this, paramActivity, paramString).execute(new String[0]);
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
      }
    });
    paramWebView.setWebViewClient(new WebViewClient()
    {
      public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
      }
      
      public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
      {
        paramAnonymousWebView.loadUrl(paramAnonymousString);
        return false;
      }
    });
  }
  
  public void showTrigger(Activity paramActivity, String paramString, int paramInt1, int paramInt2)
  {
    Util.startInitializeForOther(paramActivity, AMoAdSdkWallActivity.class);
    Url = Util.getBaseTriggerUrl();
    clickUrl = Util.getBaseTrPoClickUrl();
    int i = Util.getDipToPix(paramInt1, paramActivity);
    int j = Util.getDipToPix(paramInt2, paramActivity);
    AMoAdSdkTrigger localAMoAdSdkTrigger = new AMoAdSdkTrigger();
    localAMoAdSdkTrigger.getClass();
    new WebviewSetImageDownloadTask(localAMoAdSdkTrigger, paramActivity, paramString, i, j).execute(new String[0]);
  }
  
  public void showTriggerForUnity(Activity paramActivity, String paramString, int paramInt1, int paramInt2)
  {
    Util.startInitializeForOther(paramActivity, AMoAdSdkWallActivity.class);
    Url = Util.getBaseTriggerUrl();
    clickUrl = Util.getBaseTrPoClickUrl();
    int i = Util.getDipToPix(paramInt1, paramActivity);
    int j = Util.getDipToPix(paramInt2, paramActivity);
    AMoAdSdkTrigger localAMoAdSdkTrigger = new AMoAdSdkTrigger();
    localAMoAdSdkTrigger.getClass();
    new WebviewSetImageDownloadTask(localAMoAdSdkTrigger, paramActivity, paramString, i, j).execute(new String[0]);
  }
  
  private class ClickResultSetSendTask
    extends AsyncTask<String, Void, String>
  {
    private Activity activity = null;
    private String appKey = "";
    private String creativeID = "";
    private String triggerKey = "";
    
    public ClickResultSetSendTask(Activity paramActivity, String paramString)
    {
      this.activity = paramActivity;
      this.triggerKey = paramString;
    }
    
    protected String doInBackground(String... paramVarArgs)
    {
      this.creativeID = this.activity.getSharedPreferences("trigger_info", 0).getString(this.triggerKey, "");
      this.appKey = Util.getAppKey();
      try
      {
        String str2 = String.valueOf(new DefaultHttpClient().execute(new HttpGet(AMoAdSdkTrigger.clickUrl + "?triggerKey=" + this.triggerKey + "&appKey=" + this.appKey + "&tCreativeId=" + this.creativeID)).getStatusLine().getStatusCode());
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
    
    protected void onPostExecute(String paramString)
    {
      SharedPreferences.Editor localEditor = this.activity.getSharedPreferences("trigger_info", 0).edit();
      localEditor.putString(this.triggerKey + "_click_status", paramString);
      localEditor.commit();
      Intent localIntent = new Intent(this.activity, AMoAdSdkWallActivity.class);
      this.activity.startActivity(localIntent);
    }
  }
  
  private class WebviewSetImageDownloadTask
    extends AsyncTask<String, Void, String[]>
  {
    private Activity activity = null;
    private FrameLayout layout = null;
    private int marginLeft = 0;
    private int marginTop = 0;
    private String triggerKey = "";
    private final WeakReference<WebView> webviewRef;
    
    public WebviewSetImageDownloadTask(Activity paramActivity, String paramString, int paramInt1, int paramInt2)
    {
      this.activity = paramActivity;
      this.triggerKey = paramString;
      this.webviewRef = new WeakReference(new WebView(this.activity));
      this.marginLeft = paramInt1;
      this.marginTop = paramInt2;
      if (this.layout == null)
      {
        this.layout = new FrameLayout(this.activity);
        this.activity.addContentView(this.layout, new ViewGroup.LayoutParams(-1, -1));
        this.layout.setFocusable(true);
        this.layout.setFocusableInTouchMode(true);
      }
      this.layout.addView((View)this.webviewRef.get(), new FrameLayout.LayoutParams(-2, -2, 0));
    }
    
    protected String[] doInBackground(String... paramVarArgs)
    {
      try
      {
        String[] arrayOfString2 = AMoAdSdkTrigger.this.getJsonTextImageInfo(this.triggerKey, this.activity);
        arrayOfString1 = arrayOfString2;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          String[] arrayOfString1 = null;
        }
      }
      return arrayOfString1;
    }
    
    protected void onPostExecute(String[] paramArrayOfString)
    {
      if ((paramArrayOfString[0] != null) && (paramArrayOfString[0].equals("0")))
      {
        FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)((WebView)this.webviewRef.get()).getLayoutParams();
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        this.activity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        localLayoutParams.height = ((int)(localDisplayMetrics.density / 2.0F * Integer.parseInt(paramArrayOfString[2])));
        localLayoutParams.width = ((int)(localDisplayMetrics.density / 2.0F * Integer.parseInt(paramArrayOfString[3])));
        localLayoutParams.setMargins(this.marginLeft, this.marginTop, 0, 0);
        ((WebView)this.webviewRef.get()).setLayoutParams(localLayoutParams);
        AMoAdSdkTrigger.this.setWebview((WebView)this.webviewRef.get(), this.activity, this.triggerKey);
        ((WebView)this.webviewRef.get()).loadDataWithBaseURL(null, AMoAdSdkTrigger.this.creHtmlBody(paramArrayOfString[1]), "text/html", "utf-8", null);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.AMoAdSdkTrigger
 * JD-Core Version:    0.7.0.1
 */