package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.ClipDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

class AdfurikunWallAdLayout
  extends FrameLayout
{
  protected ConnectivityManager cm;
  private Handler handler = new Handler();
  private final Runnable hideProgress = new Runnable()
  {
    public void run()
    {
      AdfurikunWallAdLayout.this.hideProgress();
    }
  };
  protected AdfurikunInfo mAdfurikunInfo;
  private AdfurikunWebViewWallType mAdfurikunWebViewWallType;
  protected String mAppID;
  private float mDebugFontSize;
  protected AdfurikunGetInfoTask mGetInfoTask;
  private ProgressBar mHorizontalProgressBar;
  protected boolean mIsLoading;
  protected boolean mIsLog;
  protected AdfurikunLogUtil mLog;
  private AdfurikunWebViewWallType.OnProgressListener mOnProgressListener = new AdfurikunWebViewWallType.OnProgressListener()
  {
    public void dismissProgress()
    {
      AdfurikunWallAdLayout.this.handler.removeCallbacks(AdfurikunWallAdLayout.this.hideProgress);
      AdfurikunWallAdLayout.this.hideProgress();
    }
    
    public void errorClose() {}
    
    public void setProgress(int paramAnonymousInt)
    {
      if (AdfurikunWallAdLayout.this.mHorizontalProgressBar != null)
      {
        AdfurikunWallAdLayout.this.mHorizontalProgressBar.setProgress(paramAnonymousInt);
        if (paramAnonymousInt >= 100)
        {
          AdfurikunWallAdLayout.this.handler.removeCallbacks(AdfurikunWallAdLayout.this.hideProgress);
          AdfurikunWallAdLayout.this.handler.postDelayed(AdfurikunWallAdLayout.this.hideProgress, 100L);
        }
      }
    }
    
    public void startProgress()
    {
      AdfurikunWallAdLayout.this.handler.removeCallbacks(AdfurikunWallAdLayout.this.hideProgress);
      if (AdfurikunWallAdLayout.this.mHorizontalProgressBar != null)
      {
        AdfurikunWallAdLayout.this.mHorizontalProgressBar.setMax(100);
        AdfurikunWallAdLayout.this.mHorizontalProgressBar.setProgress(0);
        AdfurikunWallAdLayout.this.mHorizontalProgressBar.setVisibility(0);
      }
      if (AdfurikunWallAdLayout.this.mProgressBar != null) {
        AdfurikunWallAdLayout.this.mProgressBar.setVisibility(0);
      }
    }
    
    public void stopProgress()
    {
      if (AdfurikunWallAdLayout.this.mProgressBar != null) {
        AdfurikunWallAdLayout.this.mProgressBar.setVisibility(8);
      }
    }
  };
  private ProgressBar mProgressBar;
  private String mUserAgent;
  
  public AdfurikunWallAdLayout(Context paramContext, String paramString, AdfurikunWebViewWallType.OnActionListener paramOnActionListener)
  {
    super(paramContext);
    initialize(paramContext, paramString, paramOnActionListener);
  }
  
  private void adfurikunInit()
  {
    if (this.mAdfurikunInfo != null)
    {
      if (this.mAdfurikunInfo.infoArray.size() <= 0) {
        break label25;
      }
      randomAdfurikun();
    }
    for (;;)
    {
      return;
      label25:
      this.mLog.debug(AdfurikunConstants.TAG_NAME, ">>>>>>>>>>>>>>>>>nolist!!");
      loadErrPage(AdfurikunConstants.WALL_ERR_TYPE_GETINFO, AdfurikunConstants.WEBAPI_EXCEPTIONERR);
    }
  }
  
  private void cancelTask()
  {
    this.mIsLoading = false;
    if (this.mGetInfoTask != null)
    {
      this.mGetInfoTask.cancel(true);
      this.mGetInfoTask = null;
    }
  }
  
  private void clearSubView()
  {
    if (this.mAdfurikunWebViewWallType != null) {}
    try
    {
      this.mAdfurikunWebViewWallType.setVisibility(8);
      removeView(this.mAdfurikunWebViewWallType);
      this.mAdfurikunWebViewWallType.stopLoading();
      this.mAdfurikunWebViewWallType.setWebViewClient(null);
      this.mAdfurikunWebViewWallType.clearCache(true);
      this.mAdfurikunWebViewWallType.clearHistory();
      this.mAdfurikunWebViewWallType.destroy();
      label61:
      this.mAdfurikunWebViewWallType = null;
      return;
    }
    catch (Exception localException)
    {
      break label61;
    }
  }
  
  private void hideProgress()
  {
    if (this.mHorizontalProgressBar != null) {
      this.mHorizontalProgressBar.setVisibility(8);
    }
  }
  
  private void loadAdfurikunData()
  {
    long l = getContext().getSharedPreferences(AdfurikunConstants.PREF_FILE, 3).getLong(AdfurikunConstants.PREFKEY_AD_LAST_TIME + this.mAppID, -1L);
    if (l == -1L) {
      loadFromNetwork();
    }
    for (;;)
    {
      return;
      if (new Date().getTime() - l >= AdfurikunConstants.REGETINFO_TIME)
      {
        loadFromNetwork();
      }
      else if (this.mAdfurikunInfo == null)
      {
        this.mAdfurikunInfo = loadFromCache();
        if (this.mAdfurikunInfo == null) {
          loadFromNetwork();
        } else {
          adfurikunInit();
        }
      }
      else
      {
        randomAdfurikun();
      }
    }
  }
  
  private void loadErrPage(int paramInt1, int paramInt2)
  {
    if (this.mAdfurikunWebViewWallType != null) {
      this.mAdfurikunWebViewWallType.loadErrPage(paramInt1, paramInt2);
    }
  }
  
  private AdfurikunInfo loadFromCache()
  {
    Context localContext = getContext();
    String str = AdfurikunApiAccessUtil.loadStringFile(new StringBuilder(String.valueOf(localContext.getApplicationContext().getCacheDir().getPath())).append(AdfurikunConstants.ADFURIKUN_FOLDER).append(this.mAppID).append("/").toString() + AdfurikunConstants.GETINFO_FILE);
    return AdfurikunApiAccessUtil.stringToInfo(localContext, this.mAppID, str, this.mLog, true);
  }
  
  private void loadFromNetwork()
  {
    if ((this.mAppID.length() > 0) && (!this.mIsLoading))
    {
      cancelTask();
      this.mGetInfoTask = new AdfurikunGetInfoTask(new AdfurikunGetInfoTask.OnLoadListener()
      {
        public void onLoadFinish(int paramAnonymousInt, AdfurikunInfo paramAnonymousAdfurikunInfo)
        {
          AdfurikunWallAdLayout.this.mIsLoading = false;
          AdfurikunWallAdLayout.this.mAdfurikunInfo = paramAnonymousAdfurikunInfo;
          if ((paramAnonymousInt == AdfurikunConstants.WEBAPI_NOERR) && (AdfurikunWallAdLayout.this.mAdfurikunInfo != null)) {
            AdfurikunWallAdLayout.this.adfurikunInit();
          }
          for (;;)
          {
            return;
            AdfurikunWallAdLayout.this.mAdfurikunInfo = AdfurikunWallAdLayout.this.loadFromCache();
            if (AdfurikunWallAdLayout.this.mAdfurikunInfo != null) {
              AdfurikunWallAdLayout.this.adfurikunInit();
            } else {
              AdfurikunWallAdLayout.this.loadErrPage(AdfurikunConstants.WALL_ERR_TYPE_GETINFO, paramAnonymousInt);
            }
          }
        }
      }, getContext(), this.mAppID, Locale.getDefault().getLanguage(), this.mLog, this.mUserAgent, true);
      this.mIsLoading = true;
      this.mGetInfoTask.execute(new Void[0]);
    }
  }
  
  public void destroy()
  {
    this.handler.removeCallbacks(this.hideProgress);
    cancelTask();
    clearSubView();
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
    if ((this.mIsLog) && (this.mAdfurikunWebViewWallType != null) && (this.mAdfurikunWebViewWallType.getVisibility() == 0))
    {
      AdfurikunInfo.AdInfoForWebView localAdInfoForWebView = this.mAdfurikunWebViewWallType.getAdInfoForWebView();
      if (localAdInfoForWebView != null)
      {
        String str = localAdInfoForWebView.adnetwork_key;
        Paint localPaint = new Paint();
        localPaint.setAntiAlias(true);
        localPaint.setTextSize(this.mDebugFontSize);
        Paint.FontMetrics localFontMetrics = localPaint.getFontMetrics();
        float f1 = localFontMetrics.bottom - localFontMetrics.top;
        float f2 = localPaint.measureText(str);
        localPaint.setColor(-256);
        paramCanvas.drawRect(0.0F, 0.0F, f2 + this.mDebugFontSize + this.mDebugFontSize, f1, localPaint);
        localPaint.setColor(-16777216);
        paramCanvas.drawText(str, this.mDebugFontSize, f1 - localFontMetrics.descent, localPaint);
      }
    }
  }
  
  protected boolean goBack()
  {
    if ((this.mAdfurikunWebViewWallType != null) && (this.mAdfurikunWebViewWallType.canGoBack())) {
      this.mAdfurikunWebViewWallType.goBack();
    }
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  protected void initialize(Context paramContext, String paramString, AdfurikunWebViewWallType.OnActionListener paramOnActionListener)
  {
    setClickable(true);
    this.mLog = new AdfurikunLogUtil();
    this.mAdfurikunInfo = null;
    this.mGetInfoTask = null;
    this.mIsLoading = false;
    this.mUserAgent = "";
    this.cm = ((ConnectivityManager)paramContext.getSystemService("connectivity"));
    if (AdfurikunConstants.DETAIL_LOG) {
      this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "BASEURL=" + AdfurikunApiAccessUtil.getGetInfoBaseUrl());
    }
    this.mIsLog = false;
    this.mAppID = paramString;
    try
    {
      ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
      if (localApplicationInfo.metaData != null) {
        this.mIsLog = localApplicationInfo.metaData.getBoolean("adfurikun_test", false);
      }
      i = paramContext.getSharedPreferences(AdfurikunConstants.PREF_FILE, 3).getInt(AdfurikunConstants.PREFKEY_TESTMODE, AdfurikunConstants.PREF_TESTMODE_NOSETTING);
      if (i == AdfurikunConstants.PREF_TESTMODE_NOSETTING)
      {
        this.mLog.setIsDebugable(this.mIsLog);
        if ((this.mAppID != null) && (this.mAppID.length() > 0))
        {
          this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "adfurikun_appkey[" + this.mAppID + "]");
          float f = getResources().getDisplayMetrics().density;
          this.mDebugFontSize = (16.0F * f);
          int j = (int)(0.5F + 8.0F * f);
          FrameLayout.LayoutParams localLayoutParams1 = new FrameLayout.LayoutParams(-1, -1);
          LinearLayout localLinearLayout = new LinearLayout(paramContext);
          localLinearLayout.setOrientation(1);
          addView(localLinearLayout, localLayoutParams1);
          ProgressBar localProgressBar1 = new ProgressBar(paramContext, null, 16842872);
          this.mHorizontalProgressBar = localProgressBar1;
          this.mHorizontalProgressBar.setBackgroundDrawable(AdfurikunWallAd.createGradient(-3684409, -5197648));
          ClipDrawable localClipDrawable = new ClipDrawable(AdfurikunWallAd.createGradient(-10924, -1467136), 3, 1);
          this.mHorizontalProgressBar.setProgressDrawable(localClipDrawable);
          FrameLayout.LayoutParams localLayoutParams2 = new FrameLayout.LayoutParams(-1, j);
          localLinearLayout.addView(this.mHorizontalProgressBar, localLayoutParams2);
          AdfurikunWebViewWallType localAdfurikunWebViewWallType = new AdfurikunWebViewWallType(paramContext, this.mAppID, this.mLog);
          this.mAdfurikunWebViewWallType = localAdfurikunWebViewWallType;
          this.mAdfurikunWebViewWallType.setOnProgressListener(this.mOnProgressListener);
          this.mAdfurikunWebViewWallType.setOnActionListener(paramOnActionListener);
          localLinearLayout.addView(this.mAdfurikunWebViewWallType, localLayoutParams1);
          this.mUserAgent = this.mAdfurikunWebViewWallType.getUserAgent();
          ProgressBar localProgressBar2 = new ProgressBar(paramContext, null, 16842874);
          this.mProgressBar = localProgressBar2;
          FrameLayout.LayoutParams localLayoutParams3 = new FrameLayout.LayoutParams(-2, -2);
          localLayoutParams3.gravity = 17;
          addView(this.mProgressBar, localLayoutParams3);
          if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
          }
          NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
          if ((localNetworkInfo == null) || (!localNetworkInfo.isConnected())) {
            break label615;
          }
          nextAd();
          return;
        }
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        int i;
        this.mLog.debug_e(AdfurikunConstants.TAG_NAME, localNameNotFoundException);
        continue;
        this.mAppID = "XXXXXXXXXXXXXXXXXXXXXXXX";
        continue;
        if (i == AdfurikunConstants.PREF_TESTMODE_TEST) {}
        for (this.mIsLog = true;; this.mIsLog = false)
        {
          this.mLog.setIsDebugable(this.mIsLog);
          break;
        }
        label615:
        loadErrPage(AdfurikunConstants.WALL_ERR_TYPE_CONNECTED, AdfurikunConstants.WEBAPI_CONNECTEDERR);
      }
    }
  }
  
  public boolean isLoadFinished()
  {
    if (this.mAdfurikunInfo == null) {}
    for (boolean bool = false;; bool = true) {
      return bool;
    }
  }
  
  public void nextAd()
  {
    loadAdfurikunData();
  }
  
  public void onPause()
  {
    if (this.mAdfurikunWebViewWallType != null) {
      this.mAdfurikunWebViewWallType.onPause();
    }
  }
  
  public void onResume()
  {
    if (this.mAdfurikunWebViewWallType != null) {
      this.mAdfurikunWebViewWallType.onResume();
    }
  }
  
  protected void pushSubView(AdfurikunInfo.AdInfoForWebView paramAdInfoForWebView)
  {
    NetworkInfo localNetworkInfo = this.cm.getActiveNetworkInfo();
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnected())) {
      if (paramAdInfoForWebView != null) {
        if (this.mAdfurikunWebViewWallType != null) {
          this.mAdfurikunWebViewWallType.setAdInfo(paramAdInfoForWebView);
        }
      }
    }
    for (;;)
    {
      return;
      loadErrPage(AdfurikunConstants.WALL_ERR_TYPE_GETINFO, AdfurikunConstants.WEBAPI_EXCEPTIONERR);
      continue;
      loadErrPage(AdfurikunConstants.WALL_ERR_TYPE_CONNECTED, AdfurikunConstants.WEBAPI_CONNECTEDERR);
    }
  }
  
  protected void randomAdfurikun()
  {
    if ((this.mAdfurikunInfo != null) && (this.mAdfurikunInfo.infoArray.size() > 0)) {
      pushSubView(this.mAdfurikunInfo.getRandomAdHtml(Locale.getDefault().getLanguage()));
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunWallAdLayout
 * JD-Core Version:    0.7.0.1
 */