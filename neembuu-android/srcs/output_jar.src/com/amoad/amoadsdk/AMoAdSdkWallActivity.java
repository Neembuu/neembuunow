package com.amoad.amoadsdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AMoAdSdkWallActivity
  extends Activity
{
  private static final int ERROR_DIALOG_ID = 2;
  private static final int LOADING_DIALOG_ID = 1;
  private Handler handlerWallAccess = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      Log.d("AMoAdSdk", "handlerWallAccess#handleMessage");
    }
  };
  private WebView hiddenWebView = null;
  private LinearLayout layoutBody = null;
  private int loadResourceCount = 0;
  private Toast loadingToast = null;
  private WebView wallWebView = null;
  
  private void closeWallFromPre()
  {
    if (this.loadingToast != null)
    {
      this.loadingToast.cancel();
      this.loadingToast = null;
    }
    Intent localIntent = getIntent();
    localIntent.putExtra("DESTINATIONS", "pre");
    startActivity(localIntent);
    finish();
  }
  
  private void hideErrorDialog()
  {
    try
    {
      dismissDialog(2);
      label5:
      return;
    }
    catch (Throwable localThrowable)
    {
      break label5;
    }
  }
  
  private void hideLoadingDialog()
  {
    try
    {
      if (this.loadingToast != null)
      {
        this.loadingToast.cancel();
        this.loadingToast = null;
      }
      dismissDialog(1);
      label24:
      return;
    }
    catch (Throwable localThrowable)
    {
      break label24;
    }
  }
  
  private void showErrorDialog()
  {
    try
    {
      showDialog(2);
      label5:
      return;
    }
    catch (Throwable localThrowable)
    {
      break label5;
    }
  }
  
  private void showLoadingDialog()
  {
    try
    {
      showDialog(1);
      label5:
      return;
    }
    catch (Throwable localThrowable)
    {
      break label5;
    }
  }
  
  public void callAPI(Command paramCommand, SyntaxSugar.M<Key, Object> paramM)
  {
    switch (paramCommand)
    {
    }
    for (;;)
    {
      return;
      onBackPressed();
      continue;
      closeWallFromPre();
      continue;
      loadWall(paramM.asInteger(Key.interval, Integer.valueOf(0)).intValue());
      continue;
      String str = paramM.asString(Key.type);
      boolean bool3 = paramM.asBoolean(Key.show, Boolean.valueOf(false)).booleanValue();
      boolean bool4 = paramM.asBoolean(Key.hide, Boolean.valueOf(false)).booleanValue();
      if ("loading".equals(str))
      {
        if (bool3) {
          showLoadingDialog();
        } else if (bool4) {
          hideLoadingDialog();
        }
      }
      else if ("error".equals(str)) {
        if (bool3)
        {
          showErrorDialog();
        }
        else if (bool4)
        {
          hideErrorDialog();
          continue;
          if ("loading".equals(paramM.asString(Key.type)))
          {
            boolean bool1 = paramM.asBoolean(Key.show, Boolean.valueOf(false)).booleanValue();
            boolean bool2 = paramM.asBoolean(Key.hide, Boolean.valueOf(false)).booleanValue();
            if (bool1)
            {
              DisplayMetrics localDisplayMetrics = new DisplayMetrics();
              getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
              this.loadingToast = new Toast(this);
              this.loadingToast.setView(new ProgressBar(this, null, 16843399));
              this.loadingToast.setDuration(1);
              this.loadingToast.setGravity(85, (int)(2.0F * localDisplayMetrics.scaledDensity), (int)(15.0F * localDisplayMetrics.scaledDensity));
              this.loadingToast.show();
            }
            else if ((bool2) && (this.loadingToast != null))
            {
              this.loadingToast.cancel();
              this.loadingToast = null;
            }
          }
          else
          {
            Toast.makeText(this, paramM.asString(Key.text), 1).show();
            continue;
            resizeWall();
          }
        }
      }
    }
  }
  
  void closeWall()
  {
    Log.v("AMoAdSdk", "closeWall");
    if (this.loadingToast != null)
    {
      this.loadingToast.cancel();
      this.loadingToast = null;
    }
    finish();
  }
  
  void hideWebView()
  {
    try
    {
      if (this.wallWebView.isShown())
      {
        this.layoutBody.removeView(this.wallWebView);
        this.wallWebView.setVisibility(4);
      }
      label29:
      return;
    }
    catch (Throwable localThrowable)
    {
      break label29;
    }
  }
  
  @SuppressLint({"SimpleDateFormat"})
  void loadWall(int paramInt)
  {
    Log.v("AMoAdSdk", "loadWall");
    final String str = getIntent().getStringExtra("DESTINATIONS");
    this.handlerWallAccess.postDelayed(new Runnable()
    {
      public void run()
      {
        Log.d("AMoAdSdk", "loadWall#delay");
        AMoAdSdkWallActivity.this.showLoadingDialog();
        if ((str == null) || (str.equals("")))
        {
          SharedPreferences localSharedPreferences = AMoAdSdkWallActivity.this.getSharedPreferences("wall_activity_info", 0);
          int i = localSharedPreferences.getInt("freq_count", 0);
          String str1 = localSharedPreferences.getString("freq_date", "");
          Date localDate = new Date(System.currentTimeMillis());
          String str2 = new SimpleDateFormat("yyyyMMdd").format(localDate);
          SharedPreferences.Editor localEditor = localSharedPreferences.edit();
          if (!str2.equals(str1))
          {
            localEditor.putString("freq_date", str2);
            i = 0;
          }
          AMoAdSdkWallActivity.this.wallWebView.loadUrl(Util.getWallURL(Util.DESTINATIONS.adw, String.valueOf(i)));
          localEditor.putInt("freq_count", i + 1);
          localEditor.commit();
        }
        for (;;)
        {
          return;
          AMoAdSdkWallActivity.this.wallWebView.loadUrl(Util.getWallURL(Util.DESTINATIONS.wall, ""));
        }
      }
    }, paramInt);
  }
  
  public void onBackPressed()
  {
    closeWall();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    Log.d("AMoAdSdk", "onConfigurationChanged");
    super.onConfigurationChanged(paramConfiguration);
    resizeWall();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("AMoAdSdk", "onCreate");
    Config.initialize(this);
    requestWindowFeature(1);
    this.layoutBody = new LinearLayout(this);
    this.layoutBody.setLayoutParams(new ViewGroup.LayoutParams(-1, 300));
    this.layoutBody.setOrientation(1);
    this.layoutBody.setBackgroundColor(15921906);
    Util.startInitialize(this);
    setContentView(this.layoutBody);
    this.wallWebView = new WebView(this);
    this.wallWebView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    this.wallWebView.setVisibility(0);
    this.wallWebView.getSettings().setJavaScriptEnabled(true);
    this.wallWebView.setScrollBarStyle(0);
    this.wallWebView.clearCache(true);
    this.wallWebView.setWebViewClient(new WallWebViewClient());
    this.hiddenWebView = new WebView(this);
    this.hiddenWebView.getSettings().setJavaScriptEnabled(true);
    this.hiddenWebView.setWebViewClient(new HiddenWebViewClient());
    this.hiddenWebView.setVisibility(4);
    if (Util.isAlive(getApplicationContext()))
    {
      resizeWall();
      loadWall(0);
    }
    for (;;)
    {
      return;
      Log.v("AMoAdSdk", "通信障害のため終了");
      finish();
    }
  }
  
  protected Dialog onCreateDialog(int paramInt)
  {
    AlertDialog localAlertDialog = null;
    if (paramInt == 2)
    {
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
      localBuilder.setTitle("通信エラー");
      localBuilder.setMessage("3G回線が圏外になっているか、Wi-Fiネットワークに接続していない可能性があります。\n");
      localBuilder.setCancelable(true);
      localBuilder.setPositiveButton("再接続", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          Log.v("AMoAdSdk", "clickPositiveButton");
          AMoAdSdkWallActivity.this.loadWall(100);
        }
      });
      localBuilder.setNegativeButton("戻る", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          Log.v("AMoAdSdk", "clickNegativeButton");
          AMoAdSdkWallActivity.this.onBackPressed();
        }
      });
      localAlertDialog = localBuilder.create();
    }
    for (;;)
    {
      return localAlertDialog;
      if (paramInt == 1)
      {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        this.loadingToast = new Toast(this);
        this.loadingToast.setView(new ProgressBar(this));
        this.loadingToast.setDuration(1);
        this.loadingToast.setGravity(85, (int)(2.0F * localDisplayMetrics.scaledDensity), (int)(15.0F * localDisplayMetrics.scaledDensity));
        this.loadingToast.show();
        localAlertDialog = new AlertDialog.Builder(this).setView(null).create();
        localAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
          public void onCancel(DialogInterface paramAnonymousDialogInterface)
          {
            AMoAdSdkWallActivity.this.closeWall();
          }
        });
      }
    }
  }
  
  protected void onRestart()
  {
    super.onRestart();
    this.wallWebView.loadUrl("javascript:closeConfirm();");
  }
  
  protected void onResume()
  {
    super.onResume();
  }
  
  public void resizeWall()
  {
    DeviceInfo localDeviceInfo = new DeviceInfo(this);
    int i = (int)(0.0D * localDeviceInfo.raitoY);
    int j = (int)(15.0D * localDeviceInfo.raitoY);
    int k = -4 + ((int)localDeviceInfo.clientHeight - i - j);
    ViewGroup.LayoutParams localLayoutParams = this.layoutBody.getLayoutParams();
    localLayoutParams.width = -1;
    localLayoutParams.height = k;
  }
  
  void showWebView()
  {
    try
    {
      if (!this.wallWebView.isShown())
      {
        this.layoutBody.addView(this.wallWebView);
        this.wallWebView.setVisibility(0);
      }
      label29:
      return;
    }
    catch (Throwable localThrowable)
    {
      break label29;
    }
  }
  
  public class HiddenWebViewClient
    extends WebViewClient
  {
    public HiddenWebViewClient() {}
    
    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      Log.d("AMoAdSdk", "shouldOverrideUrlLoading2: url=" + paramString);
      if (paramString.startsWith("market://details?id="))
      {
        Log.v("AMoAdSdk", " => Market");
        AMoAdSdkWallActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
      }
      for (;;)
      {
        return true;
        UrlParseResult localUrlParseResult1 = Util.parseHttpsMarketUrl(paramString);
        if (localUrlParseResult1.valid)
        {
          Log.v("AMoAdSdk", " => Market(PC)");
          AMoAdSdkWallActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(localUrlParseResult1.asString(Key.marketUrl))));
        }
        else
        {
          UrlParseResult localUrlParseResult2 = Util.parseHttpsPlayUrl(paramString);
          if (localUrlParseResult2.valid)
          {
            Log.v("AMoAdSdk", " => Play(PC)");
            AMoAdSdkWallActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(localUrlParseResult2.asString(Key.marketUrl))));
          }
          else
          {
            Log.v("AMoAdSdk", " => 外部ブラウザ");
            AMoAdSdkWallActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
          }
        }
      }
    }
  }
  
  private class WallWebViewClient
    extends WebViewClient
  {
    public WallWebViewClient() {}
    
    public void onLoadResource(WebView paramWebView, String paramString)
    {
      super.onLoadResource(paramWebView, paramString);
      AMoAdSdkWallActivity localAMoAdSdkWallActivity = AMoAdSdkWallActivity.this;
      localAMoAdSdkWallActivity.loadResourceCount = (1 + localAMoAdSdkWallActivity.loadResourceCount);
      if (AMoAdSdkWallActivity.this.loadResourceCount == 5) {
        AMoAdSdkWallActivity.this.handlerWallAccess.postDelayed(new Runnable()
        {
          public void run()
          {
            Log.d("AMoAdSdk", "onPageStarted#delay");
            AMoAdSdkWallActivity.this.showWebView();
            AMoAdSdkWallActivity.this.hideLoadingDialog();
          }
        }, 1000L);
      }
    }
    
    public void onPageFinished(WebView paramWebView, String paramString)
    {
      Log.v("AMoAdSdk", "onPageFinished");
      super.onPageFinished(paramWebView, paramString);
      AMoAdSdkWallActivity.this.showWebView();
      AMoAdSdkWallActivity.this.hideLoadingDialog();
      Util.saveAccessDate(AMoAdSdkWallActivity.this);
    }
    
    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      Log.v("AMoAdSdk", "onPageStarted");
      super.onPageStarted(paramWebView, paramString, paramBitmap);
      AMoAdSdkWallActivity.this.loadResourceCount = 0;
    }
    
    public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
    {
      Log.d("AMoAdSdk", "onReceivedError");
      super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
      AMoAdSdkWallActivity.this.showWebView();
      AMoAdSdkWallActivity.this.hideLoadingDialog();
      AMoAdSdkWallActivity.this.showErrorDialog();
    }
    
    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      Log.v("AMoAdSdk", "shouldOverrideUrlLoading: url=" + paramString);
      boolean bool;
      if (paramString.startsWith("xapp://"))
      {
        UrlParseResult localUrlParseResult1 = Util.parseXappClickUrl(paramString);
        if (localUrlParseResult1.valid)
        {
          Log.v("AMoAdSdk", "*** CLICK ***");
          AMoAdSdkWallActivity.this.hiddenWebView.loadUrl(localUrlParseResult1.asString(Key.link));
          Util.sendClick(localUrlParseResult1.asString(Key.appKey), localUrlParseResult1.asString(Key.name), localUrlParseResult1.asString(Key.appendix));
          bool = true;
        }
      }
      for (;;)
      {
        return bool;
        UrlParseResult localUrlParseResult2 = Util.parseXAppApi(paramString);
        if (localUrlParseResult2.valid)
        {
          Log.v("AMoAdSdk", "*** API ***");
          AMoAdSdkWallActivity.this.callAPI((Command)localUrlParseResult2.get(Key.command), localUrlParseResult2);
          bool = true;
        }
        else if (paramString.startsWith("javascript:"))
        {
          bool = false;
        }
        else
        {
          AMoAdSdkWallActivity.this.hiddenWebView.loadUrl(paramString);
          bool = true;
        }
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.AMoAdSdkWallActivity
 * JD-Core Version:    0.7.0.1
 */