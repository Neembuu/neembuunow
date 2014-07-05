package jp.co.cayto.appc.sdk.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.HashMap;
import jp.co.cayto.appc.sdk.android.common.AppController;

public class AppCWebActivity
  extends Activity
{
  private static final String APPC_EC_URL_ = "https://app-c.net/EC/";
  private static final String GIVEAPP_URL = "http://android.giveapp.jp/PrDetailTest/";
  private AppController mAppController;
  private String mCurrentUrl;
  private PackageManager mPackageManager;
  private String mPrType;
  private ProgressDialog mProgressDialog;
  private boolean mReturnUrlFlg;
  private String mUrl;
  private WebView mWebView;
  
  @JavascriptInterface
  public void DisplayAgreement()
  {
    Intent localIntent = new Intent(this, AppCAgreementActivity.class);
    localIntent.putExtra("redirect_class", "");
    startActivity(localIntent);
  }
  
  @JavascriptInterface
  public void backApp()
  {
    finish();
  }
  
  @JavascriptInterface
  public String checkInstalledApps(String paramString)
  {
    try
    {
      if (this.mPackageManager == null) {
        this.mPackageManager = getPackageManager();
      }
      this.mPackageManager.getApplicationInfo(paramString, 0);
      str = "1";
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        String str = "0";
      }
    }
    return str;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    this.mPrType = getIntent().getExtras().getString("pr_type");
    StringBuilder localStringBuilder = new StringBuilder("http://android.giveapp.jp/PrDetailTest/");
    localStringBuilder.append("?media_pn=").append(getPackageName());
    localStringBuilder.append("&version=").append("2.4");
    localStringBuilder.append("&pr_type=").append(this.mPrType);
    localStringBuilder.append("&utm_source=").append("appc");
    localStringBuilder.append("&utm_medium=").append("android");
    localStringBuilder.append("&utm_term=").append(this.mPrType);
    localStringBuilder.append("&utm_content=").append("textlink");
    localStringBuilder.append("&utm_campaign=").append("appc2.4");
    this.mUrl = localStringBuilder.toString();
    this.mCurrentUrl = "";
    this.mReturnUrlFlg = false;
  }
  
  public void onDestroy()
  {
    ((ViewGroup)findViewById(16908290)).removeAllViews();
    this.mWebView.stopLoading();
    this.mWebView.setWebChromeClient(null);
    this.mWebView.setWebViewClient(null);
    unregisterForContextMenu(this.mWebView);
    this.mWebView.removeAllViews();
    this.mWebView.destroy();
    this.mPrType = null;
    this.mUrl = null;
    this.mCurrentUrl = null;
    this.mWebView = null;
    this.mAppController = null;
    this.mPackageManager = null;
    this.mProgressDialog = null;
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    if (paramKeyEvent.getAction() == 0) {}
    switch (paramKeyEvent.getKeyCode())
    {
    default: 
      bool = super.onKeyDown(paramInt, paramKeyEvent);
    }
    for (;;)
    {
      return bool;
      if (TextUtils.isEmpty(this.mCurrentUrl)) {
        break;
      }
      if (this.mCurrentUrl.equals(this.mUrl))
      {
        this.mWebView.clearHistory();
        break;
      }
      if (this.mCurrentUrl.startsWith("https://app-c.net/EC/?media_"))
      {
        this.mWebView.clearHistory();
        this.mWebView.loadUrl(this.mUrl);
      }
      else if ((!this.mCurrentUrl.startsWith("https://app-c.net/EC/process/API/fix/")) && (!this.mCurrentUrl.startsWith("https://app-c.net/EC/error/")) && (!this.mReturnUrlFlg))
      {
        if (!this.mWebView.canGoBack()) {
          break;
        }
        this.mWebView.goBack();
      }
    }
  }
  
  public void onStart()
  {
    super.onStart();
    if (this.mWebView == null)
    {
      this.mWebView = new WebView(this);
      setContentView(this.mWebView);
      this.mWebView.getSettings().setJavaScriptEnabled(true);
      this.mWebView.addJavascriptInterface(this, "SDK");
      this.mWebView.setWebViewClient(new CustomWebViewClient());
      this.mWebView.setWebChromeClient(new CustomWebChromeClient());
      this.mWebView.setInitialScale(100);
      this.mWebView.setVerticalScrollbarOverlay(true);
      WebSettings localWebSettings = this.mWebView.getSettings();
      localWebSettings.setJavaScriptEnabled(true);
      localWebSettings.setPluginsEnabled(true);
      localWebSettings.setCacheMode(2);
      localWebSettings.setSavePassword(true);
      localWebSettings.setSaveFormData(true);
      this.mWebView.setScrollBarStyle(0);
    }
    this.mWebView.loadUrl(this.mUrl);
    this.mWebView.requestFocus();
  }
  
  @JavascriptInterface
  public void registCpiByStaffReview(String paramString1, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("target_package", paramString1);
    localHashMap.put("redirect_url", paramString2);
    if (this.mAppController == null) {
      this.mAppController = AppController.createIncetance(getApplicationContext());
    }
    this.mAppController.registCPIMoveMarket(this, localHashMap, this.mPrType);
  }
  
  class CustomWebChromeClient
    extends WebChromeClient
  {
    CustomWebChromeClient() {}
    
    public void onProgressChanged(WebView paramWebView, int paramInt)
    {
      if ((paramInt > 75) && (AppCWebActivity.this.mProgressDialog != null)) {}
      try
      {
        AppCWebActivity.this.mProgressDialog.dismiss();
        label26:
        AppCWebActivity.this.mProgressDialog = null;
        return;
      }
      catch (Exception localException)
      {
        break label26;
      }
    }
  }
  
  class CustomWebViewClient
    extends WebViewClient
  {
    public CustomWebViewClient()
    {
      AppCWebActivity.this.mProgressDialog = null;
    }
    
    public void onPageFinished(WebView paramWebView, String paramString)
    {
      AppCWebActivity localAppCWebActivity;
      boolean bool;
      if (!TextUtils.isEmpty(AppCWebActivity.this.mCurrentUrl))
      {
        localAppCWebActivity = AppCWebActivity.this;
        if ((AppCWebActivity.this.mCurrentUrl.startsWith("https://app-c.net/EC/")) || (!paramString.startsWith("https://app-c.net/EC/"))) {
          break label90;
        }
        bool = true;
      }
      for (;;)
      {
        localAppCWebActivity.mReturnUrlFlg = bool;
        AppCWebActivity.this.mCurrentUrl = paramString;
        if (AppCWebActivity.this.mProgressDialog != null) {}
        try
        {
          AppCWebActivity.this.mProgressDialog.dismiss();
          label81:
          AppCWebActivity.this.mProgressDialog = null;
          return;
          label90:
          bool = false;
        }
        catch (Exception localException)
        {
          break label81;
        }
      }
    }
    
    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      if (AppCWebActivity.this.mProgressDialog != null) {}
      try
      {
        AppCWebActivity.this.mProgressDialog.dismiss();
        label20:
        AppCWebActivity.this.mProgressDialog = new ProgressDialog(paramWebView.getContext());
        AppCWebActivity.this.mProgressDialog.setIndeterminate(true);
        AppCWebActivity.this.mProgressDialog.setCancelable(true);
        AppCWebActivity.this.mProgressDialog.setProgressStyle(0);
        AppCWebActivity.this.mProgressDialog.show();
        return;
      }
      catch (Exception localException)
      {
        break label20;
      }
    }
    
    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      if ((paramString.startsWith("http:")) || (paramString.startsWith("https:"))) {}
      for (boolean bool = false;; bool = true)
      {
        return bool;
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
        AppCWebActivity.this.startActivity(localIntent);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.AppCWebActivity
 * JD-Core Version:    0.7.0.1
 */