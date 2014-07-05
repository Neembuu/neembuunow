package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Message;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebView.WebViewTransport;
import android.webkit.WebViewClient;
import com.google.ads.mediation.customevent.CustomEventBannerListener;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Locale;

class AdfurikunWebView
  extends WebView
{
  private final String BASE_URL = "about:blank";
  private AdfurikunInfo.AdInfoForWebView mAdInfoForWebView;
  private String mAppID;
  private CustomEventBannerListener mCustomEventBannerListener;
  private boolean mIsDataWith;
  private boolean mIsOneShotMode;
  private AdfurikunLogUtil mLog;
  private String mUserAgent;
  private WebViewClient mWebViewClient = new WebViewClient()
  {
    public void onPageFinished(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      super.onPageFinished(paramAnonymousWebView, paramAnonymousString);
    }
    
    public void onPageStarted(WebView paramAnonymousWebView, String paramAnonymousString, Bitmap paramAnonymousBitmap)
    {
      AdfurikunWebView.this.checkLoadPage(paramAnonymousWebView, paramAnonymousString, true);
    }
    
    public boolean shouldOverrideUrlLoading(WebView paramAnonymousWebView, String paramAnonymousString)
    {
      if (!AdfurikunWebView.this.checkLoadPage(paramAnonymousWebView, paramAnonymousString, false)) {
        paramAnonymousWebView.loadUrl(paramAnonymousString);
      }
      return true;
    }
  };
  
  public AdfurikunWebView(Context paramContext, String paramString, AdfurikunLogUtil paramAdfurikunLogUtil)
  {
    super(paramContext);
    this.mLog = paramAdfurikunLogUtil;
    this.mCustomEventBannerListener = null;
    initialize(paramString);
  }
  
  private boolean checkLoadPage(WebView paramWebView, String paramString, boolean paramBoolean)
  {
    boolean bool = false;
    if (this.mIsDataWith) {
      if (!paramString.equals("about:blank")) {
        break label31;
      }
    }
    for (;;)
    {
      return bool;
      if (!paramString.startsWith("file://"))
      {
        label31:
        if (paramBoolean) {
          paramWebView.stopLoading();
        }
        new AdfurikunRecTask(getContext(), this.mAppID, Locale.getDefault().getLanguage(), this.mAdInfoForWebView.user_ad_id, this.mLog, this.mUserAgent, 0).execute(new Void[0]);
        if (this.mCustomEventBannerListener != null)
        {
          this.mCustomEventBannerListener.onClick();
          this.mCustomEventBannerListener.onPresentScreen();
          this.mCustomEventBannerListener.onLeaveApplication();
        }
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
        localIntent.setFlags(268435456);
        getContext().startActivity(localIntent);
        bool = true;
      }
    }
  }
  
  private void initialize(String paramString)
  {
    this.mAppID = paramString;
    this.mAdInfoForWebView = null;
    this.mIsDataWith = false;
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
      label174:
      setScrollBarStyle(0);
      setVerticalScrollbarOverlay(true);
      setVerticalScrollBarEnabled(false);
      setHorizontalScrollBarEnabled(false);
      onResume();
      setWebViewClient(this.mWebViewClient);
      setWebChromeClient(new WebChromeClient()
      {
        public void onConsoleMessage(String paramAnonymousString1, int paramAnonymousInt, String paramAnonymousString2)
        {
          AdfurikunWebView.this.mLog.debug(AdfurikunConstants.TAG_NAME, "[ConsoleMessage]");
          AdfurikunWebView.this.mLog.debug(AdfurikunConstants.TAG_NAME, " ---- " + paramAnonymousString1);
          AdfurikunWebView.this.mLog.debug(AdfurikunConstants.TAG_NAME, " ---- From line " + paramAnonymousInt + " of " + paramAnonymousString2);
        }
        
        public boolean onConsoleMessage(ConsoleMessage paramAnonymousConsoleMessage)
        {
          onConsoleMessage(paramAnonymousConsoleMessage.message(), paramAnonymousConsoleMessage.lineNumber(), paramAnonymousConsoleMessage.sourceId());
          return true;
        }
        
        public boolean onCreateWindow(WebView paramAnonymousWebView, boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2, Message paramAnonymousMessage)
        {
          WebView localWebView = new WebView(AdfurikunWebView.this.getContext());
          localWebView.getSettings().setJavaScriptEnabled(true);
          localWebView.setWebViewClient(AdfurikunWebView.this.mWebViewClient);
          ((WebView.WebViewTransport)paramAnonymousMessage.obj).setWebView(localWebView);
          paramAnonymousMessage.sendToTarget();
          return false;
        }
      });
      return;
    }
    catch (Exception localException)
    {
      break label174;
    }
  }
  
  public AdfurikunInfo.AdInfoForWebView getAdInfoForWebView()
  {
    return this.mAdInfoForWebView;
  }
  
  public String getUserAgent()
  {
    return this.mUserAgent;
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
    if (this.mAdInfoForWebView != null)
    {
      localWebSettings = getSettings();
      if (!this.mAdInfoForWebView.adnetwork_key.equals(AdfurikunConstants.ADNETWORKKEY_YDN)) {
        break label220;
      }
      localWebSettings.setUserAgentString(this.mUserAgent + " YJAd-ANDROID/");
      String str = new StringBuilder(String.valueOf(getContext().getApplicationContext().getCacheDir().getPath())).append(AdfurikunConstants.ADFURIKUN_FOLDER).append(this.mAppID).append("/").toString() + this.mAdInfoForWebView.adnetwork_key + "_" + this.mAdInfoForWebView.user_ad_id + ".html";
      File localFile = new File(str);
      if (!localFile.exists())
      {
        AdfurikunApiAccessUtil.saveStringFile(str, this.mAdInfoForWebView.html);
        localFile = new File(str);
      }
      if (!localFile.exists()) {
        break label231;
      }
      this.mIsDataWith = false;
      loadUrl(Uri.fromFile(localFile).toString());
    }
    for (;;)
    {
      invalidate();
      return;
      label220:
      localWebSettings.setUserAgentString(this.mUserAgent);
      break;
      label231:
      this.mIsDataWith = true;
      loadDataWithBaseURL("about:blank", this.mAdInfoForWebView.html, "text/html", "UTF-8", null);
    }
  }
  
  public void setAdfurikunAppKey(String paramString)
  {
    this.mAppID = paramString;
  }
  
  public void setCustomEventBannerListener(CustomEventBannerListener paramCustomEventBannerListener)
  {
    this.mCustomEventBannerListener = paramCustomEventBannerListener;
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
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunWebView
 * JD-Core Version:    0.7.0.1
 */