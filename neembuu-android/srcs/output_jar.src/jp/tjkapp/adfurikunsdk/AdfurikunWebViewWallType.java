package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Message;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebView.WebViewTransport;
import android.webkit.WebViewClient;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Random;

class AdfurikunWebViewWallType
  extends WebView
{
  private static final String ADCROPS_JS = "<!doctype html><html lang=\"ja\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no\"><meta name=\"apple-mobile-web-app-capable\" content=\"yes\"><title>おすすめアプリ</title><script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script><link rel=\"stylesheet\" href=\"./adf_wall_base.css\"><link rel=\"stylesheet\" href=\"./adf_wall_1.css\" id=\"wall_css\"><script type=\"text/javascript\"> var json_str = '[ADFR_CPIJS_JSONDATA]'; $(document) .ready( function() { var css_list = [ \"./adf_wall_1.css\", \"./adf_wall_2.css\", \"./adf_wall_3.css\", \"./adf_wall_4.css\" ]; var randnum = Math.floor(Math.random() * 100) % 4; $(\"#wall_css\").attr(\"href\", css_list[randnum]); try { var json_data = $.parseJSON(json_str); var wall = json_data.wall; var ads = json_data.ads; if (ads.length == 0 || ads[0].length == 0) { throw \"empty data\"; } var cnt = 0; for ( var i = 0; i < ads.length; i++) { var ad = ads[i]; if (ad.rest_count <= 50) { continue; } var link = ad.link_url; /* typeでリンクURLを変更してApp側でキャッチ。判別にしています。 */ if (ad.type == 1) { link = \"adfurikun_appurl:\" + link; } else if (ad.type == 2) { link = \"adfurikun_weburl:\" + link; } var ad_image = ad.image_icon_72; /* Androidは画像サイズ変える必要あり。 */ var html = '<article><a href=\"' + link + '\"><img src=\"' + ad_image + '\" alt=\"' + ad.title + '\" class=\"app_icon\"></a>'; html += '<h1>' + ad.title + '</h1></article>'; $(\".app_list\").append(html); cnt++; } for ( var j = cnt; j < 14; j++) { $(\".app_list\") .append( \"<article><a><img src='http://d1bqhgjuxdf1ml.cloudfront.net/spacer.png' class='app_icon spacer'></a><h1></h1></article>\"); } /* これが認識されるか確認, img src=\"\"で送ってる。 */ $(\"#wall_beacon\").attr(\"src\", wall.beacon_url); $(\"#wall_beacon\").hide(); } catch (e) { /* エラー発生 */ location.href = 'adfurikun_notfound:'; } });</script></head><body> <header class=\"site_header\"> <h1>おすすめアプリ</h1> </header> <div class='app_list'></div> <img src=\"\" width=\"1\" height=\"1\" id=\"wall_beacon\"></body></html>";
  private static final String ADCROPS_LIST_JS = "<!doctype html><html lang=\"ja\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no\"><meta name=\"apple-mobile-web-app-capable\" content=\"yes\"><title>おすすめアプリ</title><script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script><link rel=\"stylesheet\" href=\"./adf_list_base.css\"><link rel=\"stylesheet\" href=\"./adf_list_1.css\" id=\"wall_css\"><script type=\"text/javascript\"> var json_str = '[ADFR_CPIJS_JSONDATA]'; $(document) .ready( function() { var css_list = [ \"./adf_list_1.css\", \"./adf_list_2.css\", \"./adf_list_3.css\", \"./adf_list_4.css\" ]; var randnum = Math.floor(Math.random() * 100) % 4; $(\"#wall_css\").attr(\"href\", css_list[randnum]); try { var json_data = $.parseJSON(json_str); var wall = json_data.wall; var ads = json_data.ads; if (ads.length == 0 || ads[0].length == 0) { throw \"empty data\"; } var cnt = 0; for ( var i = 0; i < ads.length; i++) { var ad = ads[i]; if (ad.rest_count <= 50) { continue; } var link = ad.link_url; if (ad.type == 1) { link = \"adfurikun_appurl:\" + link; } else if (ad.type == 2) { link = \"adfurikun_weburl:\" + link; } link = '<a href=\"' + link + '\" '; var ad_image = ad.image_icon_72; var html = '<article>' + link + '><h1 class=\"app_name\"><span class=\"free\">無料</span>' + ad.title + '</h1></a>'; html += '<div class=\"app_box clearfix\">' + link + '><img src=\"' + ad_image + '\" alt=\"' +ad.title + '\" class=\"app_icon\"></a>'; html += '<div class=\"app_info\"><p>' + ad.description + '</p>'; if (ad.type == 2) { html += link + 'class=\"dl_button\">詳細へ</a></div>\\n</div>\\n</article>'; } else { html += link + 'class=\"dl_button\">インストール</a></div>\\n</div>\\n</article>'; } $(\".app_list\").append(html); cnt++; } $(\"#wall_beacon\").attr(\"src\", wall.beacon_url); $(\"#wall_beacon\").hide(); } catch (e) { location.href = 'adfurikun_notfound:'; } });</script></head><body> <header class=\"site_header\"> <h1>おすすめアプリ</h1> </header> <div class='app_list'></div> <img src=\"\" width=\"1\" height=\"1\" id=\"wall_beacon\"></body></html>";
  private static final String APP_URL = "file:///android_asset/adfurikun/adfurikun_appurl:";
  private static final String ASSETS_BASE_URL = "file:///android_asset/adfurikun/";
  private static final String BASE_URL = "about:blank";
  private static final String NOTFOUND = "file:///android_asset/adfurikun/adfurikun_notfound:";
  private static final String NOTFOUND_HTML = "<!doctype html> <html lang=\"ja\"> <head> <meta charset=\"UTF-8\"> <meta name=\"viewport\" content=\"width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no\"> <title>NotFound</title> <style> html,body,div,span,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,abbr,address,cite,code,del,dfn,em,img,ins,kbd,q,samp,small,strong,sub,sup,var,b,i,dl,dt,dd,ol,ul,li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,figcaption,figure,footer,header,hgroup,menu,nav,section,summary,time,mark,audio,video{margin:0;padding:0;border:0;outline:0;font-size:100%;vertical-align:baseline;background:transparent}body{line-height:1}article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section{display:block}nav ul{list-style:none}blockquote,q{quotes:none}blockquote:before,blockquote:after,q:before,q:after{content:'';content:none}a{margin:0;padding:0;font-size:100%;vertical-align:baseline;background:transparent}mark{background-color:#ff9;color:#000;font-style:italic;font-weight:bold}del{text-decoration:line-through}abbr[title],dfn[title]{border-bottom:1px dotted;cursor:help}table{border-collapse:collapse;border-spacing:0}hr{display:block;height:1px;border:0;border-top:1px solid #cccccc;margin:1em 0;padding:0}input,select{vertical-align:middle}li{list-style:none}img{vertical-align:bottom}html{overflow-y:scroll}body{font:12px/1.2 \"ヒラギノ角ゴ Pro W3\", \"Hiragino Kaku Gothic Pro\", \"メイリオ\", Meiryo, \"MS UI Gotic\", \"MS PGothic\" ,Osaka, sans-serif}.clearfix:after{content:\".\";display:block;height:0;clear:both;visibility:hidden}.clearfix{display:inline-table;min-height:1%}* html .clearfix{height:1%}.clearfix{display:block}*{margin:0;padding:0}a{background:none;text-decoration:none}#notfound{text-align:center;padding:15px;font-size:14px;background:#f1f3ee}#notfound p{margin-top:5px;line-height:1.5}#notfound h1{background:URL(\"images/notfound.png\") no-repeat;background-size:100%;width:160px;height:140px;margin:0 auto;text-indent:-9999px} </style> </head> <body> <section id=\"notfound\"> <h1>NotFound</h1> [ERROR_CODE] <p>インターネットに接続できません。<br> 通信状況を確認してください。 </p> </section> </body> </html>";
  private static final String WEB_URL = "file:///android_asset/adfurikun/adfurikun_weburl:";
  private AdfurikunAdCropsTask mAdCropsTask;
  private AdfurikunInfo.AdInfoForWebView mAdInfoForWebView;
  private String mAppID;
  private boolean mIsLoading;
  private boolean mIsOneShotMode;
  private AdfurikunLogUtil mLog;
  private OnActionListener mOnActionListener;
  private OnProgressListener mOnProgressListener;
  private String mUserAgent;
  private WebViewClient mWebViewClient = new WebViewClient()
  {
    public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
      if (AdfurikunWebViewWallType.this.mOnProgressListener != null) {
        AdfurikunWebViewWallType.this.mOnProgressListener.stopProgress();
      }
    }
    
    public void onPageStarted(WebView paramAnonymousWebView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
    {
      if (!AdfurikunWebViewWallType.this.checkLoadPage(paramAnonymousWebView, paramAnonymousString, true)) {
        AdfurikunWebViewWallType.this.startProgress();
      }
    }
    
    public void onReceivedError(WebView paramAnonymousWebView, int paramAnonymousInt, String paramAnonymousString1, String paramAnonymousString2)
    {
      if (AdfurikunWebViewWallType.this.mOnProgressListener != null) {
        AdfurikunWebViewWallType.this.mOnProgressListener.dismissProgress();
      }
      AdfurikunWebViewWallType.this.loadErrPage(AdfurikunConstants.WALL_ERR_TYPE_CONNECTED, paramAnonymousInt);
    }
    
    public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      if (!AdfurikunWebViewWallType.this.checkLoadPage(paramAnonymousWebView, paramAnonymousString, false)) {
        paramAnonymousWebView.loadUrl(paramAnonymousString);
      }
      return true;
    }
  };
  private Random random;
  
  public AdfurikunWebViewWallType(Context paramContext, String paramString, AdfurikunLogUtil paramAdfurikunLogUtil)
  {
    super(paramContext);
    this.mLog = paramAdfurikunLogUtil;
    initialize(paramString);
  }
  
  private void cancelTask()
  {
    this.mIsLoading = false;
    if (this.mAdCropsTask != null)
    {
      this.mAdCropsTask.cancel(true);
      this.mAdCropsTask = null;
    }
  }
  
  private boolean checkLoadPage(WebView paramWebView, String paramString, boolean paramBoolean)
  {
    boolean bool = false;
    if (paramString != null) {
      if (paramString.startsWith("file:///android_asset/adfurikun/adfurikun_weburl:"))
      {
        paramString = paramString.substring("file:///android_asset/adfurikun/adfurikun_weburl:".length(), paramString.length());
        if (!paramString.equals("about:blank")) {
          break label94;
        }
      }
    }
    for (;;)
    {
      return bool;
      if (paramString.startsWith("file:///android_asset/adfurikun/adfurikun_appurl:"))
      {
        paramString = paramString.substring("file:///android_asset/adfurikun/adfurikun_appurl:".length(), paramString.length());
        break;
      }
      if (!"file:///android_asset/adfurikun/adfurikun_notfound:".equals("url")) {
        break;
      }
      loadErrPage(AdfurikunConstants.WALL_ERR_TYPE_NOTFOUND, AdfurikunConstants.WEBAPI_EXCEPTIONERR);
      bool = true;
      continue;
      label94:
      if (!paramString.startsWith("file://")) {
        if (this.mAdInfoForWebView != null)
        {
          String str = this.mAdInfoForWebView.adnetwork_key;
          int i = this.mAdInfoForWebView.wall_type;
          if ((!str.equals(AdfurikunConstants.ADNETWORKKEY_DEFAULT)) && (i != AdfurikunConstants.WALL_TYPE_HTML) && (i != AdfurikunConstants.WALL_TYPE_ID) && (i == AdfurikunConstants.WALL_TYPE_URL) && (this.mAdInfoForWebView.html.length() > 0) && (this.mAdInfoForWebView.html.equals(paramString))) {}
        }
        else
        {
          if (paramBoolean) {
            paramWebView.stopLoading();
          }
          if (paramWebView.canGoBack()) {
            paramWebView.goBack();
          }
          new AdfurikunRecTask(getContext(), this.mAppID, Locale.getDefault().getLanguage(), this.mAdInfoForWebView.user_ad_id, this.mLog, this.mUserAgent, 0).execute(new Void[0]);
          Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
          localIntent.setFlags(268435456);
          getContext().startActivity(localIntent);
          if (this.mOnProgressListener != null) {
            this.mOnProgressListener.dismissProgress();
          }
          bool = true;
        }
      }
    }
  }
  
  private void initialize(String paramString)
  {
    this.random = new Random();
    this.mOnProgressListener = null;
    this.mOnActionListener = null;
    this.mIsLoading = false;
    this.mAdCropsTask = null;
    this.mAppID = paramString;
    this.mAdInfoForWebView = null;
    this.mIsOneShotMode = false;
    setBackgroundColor(0);
    WebSettings localWebSettings = getSettings();
    this.mUserAgent = localWebSettings.getUserAgentString();
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setSavePassword(false);
    localWebSettings.setSaveFormData(false);
    localWebSettings.setSupportZoom(false);
    localWebSettings.setLoadWithOverviewMode(true);
    localWebSettings.setSupportMultipleWindows(false);
    localWebSettings.setAllowFileAccess(true);
    localWebSettings.setCacheMode(2);
    localWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    localWebSettings.setDomStorageEnabled(true);
    localWebSettings.setDatabaseEnabled(true);
    localWebSettings.setDatabasePath(getContext().getDir("localstorage", 0).getPath());
    Class localClass;
    if (Build.VERSION.SDK_INT >= 16) {
      localClass = localWebSettings.getClass();
    }
    try
    {
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = Boolean.TYPE;
      Method localMethod = localClass.getMethod("setAllowUniversalAccessFromFileURLs", arrayOfClass);
      if (localMethod != null)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Boolean.valueOf(true);
        localMethod.invoke(localWebSettings, arrayOfObject);
      }
      label202:
      setScrollBarStyle(0);
      setVerticalScrollbarOverlay(true);
      setVerticalScrollBarEnabled(false);
      setHorizontalScrollBarEnabled(false);
      onResume();
      setWebViewClient(this.mWebViewClient);
      setWebChromeClient(new WebChromeClient()
      {
        public void onCloseWindow(WebView paramAnonymousWebView)
        {
          super.onCloseWindow(paramAnonymousWebView);
          if (AdfurikunWebViewWallType.this.mOnActionListener != null) {
            AdfurikunWebViewWallType.this.mOnActionListener.windowClose();
          }
        }
        
        public void onConsoleMessage(String paramAnonymousString1, int paramAnonymousInt, String paramAnonymousString2)
        {
          AdfurikunWebViewWallType.this.mLog.debug(AdfurikunConstants.TAG_NAME, "[ConsoleMessage]");
          AdfurikunWebViewWallType.this.mLog.debug(AdfurikunConstants.TAG_NAME, " ---- " + paramAnonymousString1);
          AdfurikunWebViewWallType.this.mLog.debug(AdfurikunConstants.TAG_NAME, " ---- From line " + paramAnonymousInt + " of " + paramAnonymousString2);
        }
        
        public boolean onConsoleMessage(ConsoleMessage paramAnonymousConsoleMessage)
        {
          onConsoleMessage(paramAnonymousConsoleMessage.message(), paramAnonymousConsoleMessage.lineNumber(), paramAnonymousConsoleMessage.sourceId());
          return true;
        }
        
        public boolean onCreateWindow(WebView paramAnonymousWebView, boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, Message paramAnonymousMessage)
        {
          WebView localWebView = new WebView(AdfurikunWebViewWallType.this.getContext());
          localWebView.getSettings().setJavaScriptEnabled(true);
          localWebView.setWebViewClient(AdfurikunWebViewWallType.this.mWebViewClient);
          ((WebView.WebViewTransport)paramAnonymousMessage.obj).setWebView(localWebView);
          paramAnonymousMessage.sendToTarget();
          return false;
        }
        
        public boolean onJsAlert(WebView paramAnonymousWebView, String paramAnonymousString1, String paramAnonymousString2, JsResult paramAnonymousJsResult)
        {
          return super.onJsAlert(paramAnonymousWebView, paramAnonymousString1, paramAnonymousString2, paramAnonymousJsResult);
        }
        
        public void onProgressChanged(WebView paramAnonymousWebView, int paramAnonymousInt)
        {
          super.onProgressChanged(paramAnonymousWebView, paramAnonymousInt);
          if (AdfurikunWebViewWallType.this.mOnProgressListener != null) {
            AdfurikunWebViewWallType.this.mOnProgressListener.setProgress(paramAnonymousInt);
          }
        }
      });
      return;
    }
    catch (Exception localException)
    {
      break label202;
    }
  }
  
  private void loadAdCrops(String paramString)
  {
    if ((paramString.length() > 0) && (!this.mIsLoading))
    {
      cancelTask();
      this.mAdCropsTask = new AdfurikunAdCropsTask(new AdfurikunAdCropsTask.OnLoadListener()
      {
        public void onLoadFinish(int paramAnonymousInt, String paramAnonymousString)
        {
          AdfurikunWebViewWallType.this.mIsLoading = false;
          String str2;
          if ((paramAnonymousInt == AdfurikunConstants.WEBAPI_NOERR) && (paramAnonymousString.length() > 0))
          {
            String str1 = paramAnonymousString.replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "").replace("\\n", "\\\\n");
            if (AdfurikunWebViewWallType.this.random.nextInt(2) == 0)
            {
              str2 = "<!doctype html><html lang=\"ja\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no\"><meta name=\"apple-mobile-web-app-capable\" content=\"yes\"><title>おすすめアプリ</title><script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script><link rel=\"stylesheet\" href=\"./adf_wall_base.css\"><link rel=\"stylesheet\" href=\"./adf_wall_1.css\" id=\"wall_css\"><script type=\"text/javascript\"> var json_str = '[ADFR_CPIJS_JSONDATA]'; $(document) .ready( function() { var css_list = [ \"./adf_wall_1.css\", \"./adf_wall_2.css\", \"./adf_wall_3.css\", \"./adf_wall_4.css\" ]; var randnum = Math.floor(Math.random() * 100) % 4; $(\"#wall_css\").attr(\"href\", css_list[randnum]); try { var json_data = $.parseJSON(json_str); var wall = json_data.wall; var ads = json_data.ads; if (ads.length == 0 || ads[0].length == 0) { throw \"empty data\"; } var cnt = 0; for ( var i = 0; i < ads.length; i++) { var ad = ads[i]; if (ad.rest_count <= 50) { continue; } var link = ad.link_url; /* typeでリンクURLを変更してApp側でキャッチ。判別にしています。 */ if (ad.type == 1) { link = \"adfurikun_appurl:\" + link; } else if (ad.type == 2) { link = \"adfurikun_weburl:\" + link; } var ad_image = ad.image_icon_72; /* Androidは画像サイズ変える必要あり。 */ var html = '<article><a href=\"' + link + '\"><img src=\"' + ad_image + '\" alt=\"' + ad.title + '\" class=\"app_icon\"></a>'; html += '<h1>' + ad.title + '</h1></article>'; $(\".app_list\").append(html); cnt++; } for ( var j = cnt; j < 14; j++) { $(\".app_list\") .append( \"<article><a><img src='http://d1bqhgjuxdf1ml.cloudfront.net/spacer.png' class='app_icon spacer'></a><h1></h1></article>\"); } /* これが認識されるか確認, img src=\"\"で送ってる。 */ $(\"#wall_beacon\").attr(\"src\", wall.beacon_url); $(\"#wall_beacon\").hide(); } catch (e) { /* エラー発生 */ location.href = 'adfurikun_notfound:'; } });</script></head><body> <header class=\"site_header\"> <h1>おすすめアプリ</h1> </header> <div class='app_list'></div> <img src=\"\" width=\"1\" height=\"1\" id=\"wall_beacon\"></body></html>";
              String str3 = str2.replace("[ADFR_CPIJS_JSONDATA]", str1);
              AdfurikunWebViewWallType.this.loadDataWithBaseURL("file:///android_asset/adfurikun/", str3, "text/html", "UTF-8", null);
              AdfurikunWebViewWallType.this.recImpression();
            }
          }
          for (;;)
          {
            return;
            str2 = "<!doctype html><html lang=\"ja\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no\"><meta name=\"apple-mobile-web-app-capable\" content=\"yes\"><title>おすすめアプリ</title><script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script><link rel=\"stylesheet\" href=\"./adf_list_base.css\"><link rel=\"stylesheet\" href=\"./adf_list_1.css\" id=\"wall_css\"><script type=\"text/javascript\"> var json_str = '[ADFR_CPIJS_JSONDATA]'; $(document) .ready( function() { var css_list = [ \"./adf_list_1.css\", \"./adf_list_2.css\", \"./adf_list_3.css\", \"./adf_list_4.css\" ]; var randnum = Math.floor(Math.random() * 100) % 4; $(\"#wall_css\").attr(\"href\", css_list[randnum]); try { var json_data = $.parseJSON(json_str); var wall = json_data.wall; var ads = json_data.ads; if (ads.length == 0 || ads[0].length == 0) { throw \"empty data\"; } var cnt = 0; for ( var i = 0; i < ads.length; i++) { var ad = ads[i]; if (ad.rest_count <= 50) { continue; } var link = ad.link_url; if (ad.type == 1) { link = \"adfurikun_appurl:\" + link; } else if (ad.type == 2) { link = \"adfurikun_weburl:\" + link; } link = '<a href=\"' + link + '\" '; var ad_image = ad.image_icon_72; var html = '<article>' + link + '><h1 class=\"app_name\"><span class=\"free\">無料</span>' + ad.title + '</h1></a>'; html += '<div class=\"app_box clearfix\">' + link + '><img src=\"' + ad_image + '\" alt=\"' +ad.title + '\" class=\"app_icon\"></a>'; html += '<div class=\"app_info\"><p>' + ad.description + '</p>'; if (ad.type == 2) { html += link + 'class=\"dl_button\">詳細へ</a></div>\\n</div>\\n</article>'; } else { html += link + 'class=\"dl_button\">インストール</a></div>\\n</div>\\n</article>'; } $(\".app_list\").append(html); cnt++; } $(\"#wall_beacon\").attr(\"src\", wall.beacon_url); $(\"#wall_beacon\").hide(); } catch (e) { location.href = 'adfurikun_notfound:'; } });</script></head><body> <header class=\"site_header\"> <h1>おすすめアプリ</h1> </header> <div class='app_list'></div> <img src=\"\" width=\"1\" height=\"1\" id=\"wall_beacon\"></body></html>";
            break;
            AdfurikunWebViewWallType.this.loadErrPage(AdfurikunConstants.WALL_ERR_TYPE_API, paramAnonymousInt);
          }
        }
      }, getContext(), paramString, this.mLog);
      this.mIsLoading = true;
      this.mAdCropsTask.execute(new Void[0]);
    }
  }
  
  private void startProgress()
  {
    if (this.mOnProgressListener != null) {
      this.mOnProgressListener.startProgress();
    }
  }
  
  public boolean canGoBack()
  {
    return false;
  }
  
  public void destroy()
  {
    super.destroy();
    cancelTask();
  }
  
  public AdfurikunInfo.AdInfoForWebView getAdInfoForWebView()
  {
    return this.mAdInfoForWebView;
  }
  
  public String getUserAgent()
  {
    return this.mUserAgent;
  }
  
  protected void loadErrPage(int paramInt1, int paramInt2)
  {
    String str = "";
    if (paramInt2 == AdfurikunConstants.WEBAPI_CONNECTEDERR) {
      paramInt2 = 404;
    }
    if ((paramInt2 < 200) || (paramInt2 >= 400)) {
      str = Integer.toString(paramInt2);
    }
    loadDataWithBaseURL("file:///android_asset/adfurikun/", "<!doctype html> <html lang=\"ja\"> <head> <meta charset=\"UTF-8\"> <meta name=\"viewport\" content=\"width=device-width,minimum-scale=1,maximum-scale=1,user-scalable=no\"> <title>NotFound</title> <style> html,body,div,span,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,abbr,address,cite,code,del,dfn,em,img,ins,kbd,q,samp,small,strong,sub,sup,var,b,i,dl,dt,dd,ol,ul,li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,figcaption,figure,footer,header,hgroup,menu,nav,section,summary,time,mark,audio,video{margin:0;padding:0;border:0;outline:0;font-size:100%;vertical-align:baseline;background:transparent}body{line-height:1}article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section{display:block}nav ul{list-style:none}blockquote,q{quotes:none}blockquote:before,blockquote:after,q:before,q:after{content:'';content:none}a{margin:0;padding:0;font-size:100%;vertical-align:baseline;background:transparent}mark{background-color:#ff9;color:#000;font-style:italic;font-weight:bold}del{text-decoration:line-through}abbr[title],dfn[title]{border-bottom:1px dotted;cursor:help}table{border-collapse:collapse;border-spacing:0}hr{display:block;height:1px;border:0;border-top:1px solid #cccccc;margin:1em 0;padding:0}input,select{vertical-align:middle}li{list-style:none}img{vertical-align:bottom}html{overflow-y:scroll}body{font:12px/1.2 \"ヒラギノ角ゴ Pro W3\", \"Hiragino Kaku Gothic Pro\", \"メイリオ\", Meiryo, \"MS UI Gotic\", \"MS PGothic\" ,Osaka, sans-serif}.clearfix:after{content:\".\";display:block;height:0;clear:both;visibility:hidden}.clearfix{display:inline-table;min-height:1%}* html .clearfix{height:1%}.clearfix{display:block}*{margin:0;padding:0}a{background:none;text-decoration:none}#notfound{text-align:center;padding:15px;font-size:14px;background:#f1f3ee}#notfound p{margin-top:5px;line-height:1.5}#notfound h1{background:URL(\"images/notfound.png\") no-repeat;background-size:100%;width:160px;height:140px;margin:0 auto;text-indent:-9999px} </style> </head> <body> <section id=\"notfound\"> <h1>NotFound</h1> [ERROR_CODE] <p>インターネットに接続できません。<br> 通信状況を確認してください。 </p> </section> </body> </html>".replace("[ERROR_CODE]", str), "text/html", "UTF-8", null);
  }
  
  protected void recImpression()
  {
    if (this.mAdInfoForWebView != null) {
      new AdfurikunRecTask(getContext(), this.mAppID, Locale.getDefault().getLanguage(), this.mAdInfoForWebView.user_ad_id, this.mLog, this.mUserAgent, 1).execute(new Void[0]);
    }
  }
  
  protected void setAdInfo(AdfurikunInfo.AdInfoForWebView paramAdInfoForWebView)
  {
    this.mAdInfoForWebView = paramAdInfoForWebView;
    WebSettings localWebSettings;
    String str1;
    int i;
    if (this.mAdInfoForWebView != null)
    {
      startProgress();
      localWebSettings = getSettings();
      if (!this.mAdInfoForWebView.adnetwork_key.equals(AdfurikunConstants.ADNETWORKKEY_YDN)) {
        break label262;
      }
      localWebSettings.setUserAgentString(this.mUserAgent + " YJAd-ANDROID/");
      str1 = this.mAdInfoForWebView.adnetwork_key;
      i = this.mAdInfoForWebView.wall_type;
      if ((!str1.equals(AdfurikunConstants.ADNETWORKKEY_DEFAULT)) && (i != AdfurikunConstants.WALL_TYPE_HTML)) {
        break label296;
      }
      String str2 = new StringBuilder(String.valueOf(getContext().getApplicationContext().getCacheDir().getPath())).append(AdfurikunConstants.ADFURIKUN_FOLDER).append(this.mAppID).append("/").toString() + this.mAdInfoForWebView.adnetwork_key + "_" + this.mAdInfoForWebView.user_ad_id + ".html";
      File localFile = new File(str2);
      if (!localFile.exists())
      {
        AdfurikunApiAccessUtil.saveStringFile(str2, this.mAdInfoForWebView.html);
        localFile = new File(str2);
      }
      if (!localFile.exists()) {
        break label273;
      }
      loadUrl(Uri.fromFile(localFile).toString());
      label253:
      recImpression();
    }
    for (;;)
    {
      invalidate();
      return;
      label262:
      localWebSettings.setUserAgentString(this.mUserAgent);
      break;
      label273:
      loadDataWithBaseURL("about:blank", this.mAdInfoForWebView.html, "text/html", "UTF-8", null);
      break label253;
      label296:
      if (i == AdfurikunConstants.WALL_TYPE_ID)
      {
        if (str1.equals(AdfurikunConstants.ADNETWORKKEY_ADCROPS)) {
          loadAdCrops(this.mAdInfoForWebView.html);
        } else {
          loadErrPage(AdfurikunConstants.WALL_ERR_TYPE_API, AdfurikunConstants.WEBAPI_EXCEPTIONERR);
        }
      }
      else if (i == AdfurikunConstants.WALL_TYPE_URL)
      {
        loadUrl(this.mAdInfoForWebView.html);
        recImpression();
      }
      else
      {
        loadErrPage(AdfurikunConstants.WALL_ERR_TYPE_API, AdfurikunConstants.WEBAPI_EXCEPTIONERR);
      }
    }
  }
  
  public void setAdfurikunAppKey(String paramString)
  {
    this.mAppID = paramString;
  }
  
  public void setOnActionListener(OnActionListener paramOnActionListener)
  {
    this.mOnActionListener = paramOnActionListener;
  }
  
  public void setOnProgressListener(OnProgressListener paramOnProgressListener)
  {
    this.mOnProgressListener = paramOnProgressListener;
  }
  
  public void setOneShotMode(boolean paramBoolean)
  {
    if (this.mIsOneShotMode != paramBoolean)
    {
      this.mIsOneShotMode = paramBoolean;
      if (!this.mIsOneShotMode) {
        break label25;
      }
      onPause();
    }
    for (;;)
    {
      return;
      label25:
      onResume();
    }
  }
  
  public static abstract interface OnActionListener
  {
    public abstract void windowClose();
  }
  
  public static abstract interface OnProgressListener
  {
    public abstract void dismissProgress();
    
    public abstract void errorClose();
    
    public abstract void setProgress(int paramInt);
    
    public abstract void startProgress();
    
    public abstract void stopProgress();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunWebViewWallType
 * JD-Core Version:    0.7.0.1
 */