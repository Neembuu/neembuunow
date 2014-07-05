package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

abstract class AdfurikunBase
  extends FrameLayout
{
  protected ConnectivityManager cm;
  protected Handler handler = new Handler();
  protected AdfurikunInfo mAdfurikunInfo;
  protected String mAppID;
  private int mBgColor;
  private float mDebugFontSize;
  private boolean mFirstTime;
  protected AdfurikunGetInfoTask mGetInfoTask;
  private Animation mInAnimation;
  protected boolean mIsIntersAd = false;
  protected boolean mIsLoading;
  protected boolean mIsLog;
  protected AdfurikunLogUtil mLog;
  private Animation mOutAnimation;
  private int mRetryWait;
  protected boolean mTaOff;
  private String mUserAgent;
  private int mWhichChild = 0;
  protected final Runnable retryThread = new Runnable()
  {
    public void run()
    {
      AdfurikunBase.this.loadFromNetwork(AdfurikunBase.this.getContext(), AdfurikunConstants.LOAD_MAIN_RETRY);
    }
  };
  protected final Runnable retryThread2 = new Runnable()
  {
    public void run()
    {
      AdfurikunBase.this.loadFromNetwork(AdfurikunBase.this.getContext(), AdfurikunConstants.LOAD_SUB_RETRY);
    }
  };
  
  public AdfurikunBase(Context paramContext)
  {
    super(paramContext);
    initialize(paramContext);
  }
  
  public AdfurikunBase(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initialize(paramContext);
  }
  
  private void adfurikunInit()
  {
    if ((this.mAdfurikunInfo == null) || (this.mAdfurikunInfo.bg_color.length() > 0)) {}
    for (;;)
    {
      try
      {
        this.mBgColor = Integer.parseInt(this.mAdfurikunInfo.bg_color, 16);
        this.mBgColor = (0xFF000000 | this.mBgColor);
        setBackgroundColor(this.mBgColor);
        this.mTaOff = this.mAdfurikunInfo.ta_off;
        if (this.mAdfurikunInfo.infoArray.size() <= 0) {
          break label102;
        }
        randomAdfurikun();
        return;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        this.mBgColor = -16777216;
        continue;
      }
      this.mBgColor = 0;
      continue;
      label102:
      this.mLog.debug(AdfurikunConstants.TAG_NAME, ">>>>>>>>>>>>>>>>>nolist!!");
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
    int i = -1 + getChildCount();
    for (;;)
    {
      if (i < 0) {
        return;
      }
      AdfurikunWebView localAdfurikunWebView = (AdfurikunWebView)getChildAt(i);
      if (localAdfurikunWebView != null) {}
      try
      {
        localAdfurikunWebView.setVisibility(8);
        removeViewAt(i);
        localAdfurikunWebView.stopLoading();
        localAdfurikunWebView.setWebViewClient(null);
        localAdfurikunWebView.clearCache(true);
        localAdfurikunWebView.clearHistory();
        localAdfurikunWebView.destroy();
        label59:
        i--;
      }
      catch (Exception localException)
      {
        break label59;
      }
    }
  }
  
  private boolean isEmptyBitmap(Bitmap paramBitmap)
  {
    int i;
    int j;
    int k;
    int m;
    int n;
    int i1;
    label52:
    int i2;
    int i3;
    int i4;
    label100:
    int i5;
    boolean bool;
    int i8;
    int i9;
    int i10;
    if (paramBitmap != null)
    {
      int i7;
      try
      {
        i = 0xFFFFFF & this.mBgColor;
        j = paramBitmap.getWidth();
        k = paramBitmap.getHeight();
        if (j <= k) {
          break label230;
        }
        m = j;
        arrayOfInt = new int[m];
        n = k / 16;
        i1 = 0;
        if (i1 >= k)
        {
          i6 = j / 16;
          i7 = 0;
          break label219;
        }
        paramBitmap.getPixels(arrayOfInt, 0, j, 0, i1, j, 1);
        i2 = i;
        i3 = 0;
        i4 = 0;
      }
      catch (OutOfMemoryError localOutOfMemoryError)
      {
        int[] arrayOfInt;
        int i6;
        int i11;
        int i12;
        bool = false;
        break label228;
      }
      i5 = 0xFFFFFF & arrayOfInt[i4];
      if (i5 == i2) {
        break label264;
      }
      i3++;
      if (i3 <= 2) {
        break label264;
      }
      bool = false;
      break label228;
      label158:
      label203:
      label219:
      while (i7 < j)
      {
        paramBitmap.getPixels(arrayOfInt, 0, 1, i7, 0, 1, k);
        i8 = i;
        i9 = 0;
        i10 = 0;
        break label284;
        i11 = arrayOfInt[i10];
        i12 = i11 & 0xFFFFFF;
        if (i12 != i8)
        {
          i9++;
          if (i9 > 2)
          {
            bool = false;
            break;
          }
        }
        i8 = i12;
        i10++;
        break label284;
        i7 += i6;
      }
    }
    else
    {
      bool = true;
    }
    for (;;)
    {
      label228:
      return bool;
      label230:
      m = k;
      break;
      for (;;)
      {
        if (i4 < j) {
          break label272;
        }
        if ((i2 == i) || (i3 + 1 <= 2)) {
          break label274;
        }
        bool = false;
        break;
        label264:
        i2 = i5;
        i4++;
      }
      label272:
      break label100;
      label274:
      i1 += n;
      break label52;
      label284:
      if (i10 < k) {
        break label158;
      }
      if ((i8 == i) || (i9 + 1 <= 2)) {
        break label203;
      }
      bool = false;
    }
  }
  
  private void loadAdfurikunData()
  {
    Context localContext = getContext();
    long l = localContext.getSharedPreferences(AdfurikunConstants.PREF_FILE, 3).getLong(AdfurikunConstants.PREFKEY_AD_LAST_TIME + this.mAppID, -1L);
    if (l == -1L) {
      loadFromNetwork(localContext, AdfurikunConstants.LOAD_MAIN_INIT);
    }
    for (;;)
    {
      return;
      if (new Date().getTime() - l >= AdfurikunConstants.REGETINFO_TIME)
      {
        loadFromNetwork(localContext, AdfurikunConstants.LOAD_MAIN_INIT);
      }
      else if (this.mAdfurikunInfo == null)
      {
        this.mAdfurikunInfo = loadFromCache(localContext);
        if (this.mAdfurikunInfo == null) {
          loadFromNetwork(localContext, AdfurikunConstants.LOAD_MAIN_INIT);
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
  
  private AdfurikunInfo loadFromCache(Context paramContext)
  {
    String str = AdfurikunApiAccessUtil.loadStringFile(new StringBuilder(String.valueOf(paramContext.getApplicationContext().getCacheDir().getPath())).append(AdfurikunConstants.ADFURIKUN_FOLDER).append(this.mAppID).append("/").toString() + AdfurikunConstants.GETINFO_FILE);
    return AdfurikunApiAccessUtil.stringToInfo(paramContext, this.mAppID, str, this.mLog, true);
  }
  
  private void loadFromNetwork(final Context paramContext, final int paramInt)
  {
    if ((this.mAppID.length() > 0) && (!this.mIsLoading))
    {
      cancelTask();
      if ((paramInt != AdfurikunConstants.LOAD_MAIN_INIT) && (paramInt != AdfurikunConstants.LOAD_MAIN_RETRY)) {
        break label99;
      }
    }
    label99:
    for (boolean bool = true;; bool = false)
    {
      this.mGetInfoTask = new AdfurikunGetInfoTask(new AdfurikunGetInfoTask.OnLoadListener()
      {
        public void onLoadFinish(int paramAnonymousInt, AdfurikunInfo paramAnonymousAdfurikunInfo)
        {
          AdfurikunBase.this.mIsLoading = false;
          AdfurikunBase.this.mAdfurikunInfo = paramAnonymousAdfurikunInfo;
          if ((paramAnonymousInt == AdfurikunConstants.WEBAPI_NOERR) && (AdfurikunBase.this.mAdfurikunInfo != null)) {
            AdfurikunBase.this.adfurikunInit();
          }
          for (;;)
          {
            return;
            if (paramAnonymousInt == AdfurikunConstants.WEBAPI_EXCEPTIONERR)
            {
              if ((paramInt == AdfurikunConstants.LOAD_MAIN_INIT) || (paramInt == AdfurikunConstants.LOAD_SUB_INIT)) {
                AdfurikunBase.this.mRetryWait = AdfurikunConstants.RETRY_TIME_SHORT;
              }
              for (;;)
              {
                if (AdfurikunBase.this.mRetryWait < AdfurikunConstants.RETRY_STOP_TIME_SHORT)
                {
                  if ((paramInt == AdfurikunConstants.LOAD_MAIN_INIT) || (paramInt == AdfurikunConstants.LOAD_MAIN_RETRY))
                  {
                    AdfurikunBase.this.handler.postDelayed(AdfurikunBase.this.retryThread, AdfurikunBase.this.mRetryWait);
                    break;
                    AdfurikunBase localAdfurikunBase2 = AdfurikunBase.this;
                    localAdfurikunBase2.mRetryWait += AdfurikunConstants.RETRY_TIME_SHORT;
                    continue;
                  }
                  AdfurikunBase.this.handler.postDelayed(AdfurikunBase.this.retryThread2, AdfurikunBase.this.mRetryWait);
                  break;
                }
              }
              if (((paramInt == AdfurikunConstants.LOAD_MAIN_INIT) || (paramInt == AdfurikunConstants.LOAD_MAIN_RETRY)) && (AdfurikunApiAccessUtil.isUseSubServer()))
              {
                AdfurikunBase.this.loadFromNetwork(paramContext, AdfurikunConstants.LOAD_SUB_INIT);
              }
              else
              {
                AdfurikunBase.this.mAdfurikunInfo = AdfurikunBase.this.loadFromCache(paramContext);
                AdfurikunBase.this.adfurikunInit();
              }
            }
            else if ((paramAnonymousInt >= 201) || (paramAnonymousInt == AdfurikunConstants.WEBAPI_CONNECTEDERR) || (AdfurikunBase.this.mAdfurikunInfo == null))
            {
              if ((paramInt == AdfurikunConstants.LOAD_MAIN_INIT) || (paramInt == AdfurikunConstants.LOAD_SUB_INIT)) {
                AdfurikunBase.this.mRetryWait = AdfurikunConstants.RETRY_TIME;
              }
              for (;;)
              {
                if (AdfurikunBase.this.mRetryWait < AdfurikunConstants.RETRY_STOP_TIME)
                {
                  if ((paramInt == AdfurikunConstants.LOAD_MAIN_INIT) || (paramInt == AdfurikunConstants.LOAD_MAIN_RETRY))
                  {
                    AdfurikunBase.this.handler.postDelayed(AdfurikunBase.this.retryThread, AdfurikunBase.this.mRetryWait);
                    break;
                    AdfurikunBase localAdfurikunBase1 = AdfurikunBase.this;
                    localAdfurikunBase1.mRetryWait = (2 * localAdfurikunBase1.mRetryWait);
                    continue;
                  }
                  AdfurikunBase.this.handler.postDelayed(AdfurikunBase.this.retryThread2, AdfurikunBase.this.mRetryWait);
                  break;
                }
              }
              if (((paramInt == AdfurikunConstants.LOAD_MAIN_INIT) || (paramInt == AdfurikunConstants.LOAD_MAIN_RETRY)) && (AdfurikunApiAccessUtil.isUseSubServer()))
              {
                AdfurikunBase.this.loadFromNetwork(paramContext, AdfurikunConstants.LOAD_SUB_INIT);
              }
              else
              {
                AdfurikunBase.this.mAdfurikunInfo = AdfurikunBase.this.loadFromCache(paramContext);
                AdfurikunBase.this.adfurikunInit();
              }
            }
          }
        }
      }, getContext(), this.mAppID, Locale.getDefault().getLanguage(), this.mLog, this.mUserAgent, bool);
      this.mIsLoading = true;
      this.mGetInfoTask.execute(new Void[0]);
      return;
    }
  }
  
  private void showOnly(int paramInt)
  {
    int i = getChildCount();
    View localView;
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return;
      }
      localView = getChildAt(j);
      if (j != paramInt) {
        break;
      }
      if ((!this.mFirstTime) && (this.mInAnimation != null)) {
        localView.startAnimation(this.mInAnimation);
      }
      localView.setVisibility(0);
      this.mFirstTime = false;
    }
    if ((!this.mFirstTime) && (this.mOutAnimation != null) && (localView.getVisibility() == 0)) {
      localView.startAnimation(this.mOutAnimation);
    }
    for (;;)
    {
      localView.setVisibility(4);
      break;
      if (localView.getAnimation() == this.mInAnimation) {
        localView.clearAnimation();
      }
    }
  }
  
  public void destroy()
  {
    this.handler.removeCallbacks(this.retryThread);
    this.handler.removeCallbacks(this.retryThread2);
    cancelTask();
    clearSubView();
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
    if (this.mIsLog)
    {
      AdfurikunWebView localAdfurikunWebView = (AdfurikunWebView)getChildAt(getDisplayedChild());
      if ((localAdfurikunWebView != null) && (localAdfurikunWebView.getVisibility() == 0))
      {
        AdfurikunInfo.AdInfoForWebView localAdInfoForWebView = localAdfurikunWebView.getAdInfoForWebView();
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
  }
  
  protected int getDisplayedChild()
  {
    return this.mWhichChild;
  }
  
  protected void initialize(Context paramContext)
  {
    setClickable(true);
    this.mLog = new AdfurikunLogUtil();
    this.mAdfurikunInfo = null;
    this.mGetInfoTask = null;
    this.mIsLoading = false;
    this.mTaOff = false;
    this.mRetryWait = AdfurikunConstants.RETRY_TIME;
    this.mUserAgent = "";
    this.mBgColor = -16777216;
    this.mFirstTime = true;
    this.cm = ((ConnectivityManager)paramContext.getSystemService("connectivity"));
    if (AdfurikunConstants.DETAIL_LOG) {
      this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "BASEURL=" + AdfurikunApiAccessUtil.getGetInfoBaseUrl());
    }
    this.mIsLog = false;
    this.mAppID = null;
    try
    {
      ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
      if (localApplicationInfo.metaData != null)
      {
        this.mIsLog = localApplicationInfo.metaData.getBoolean("adfurikun_test", false);
        this.mAppID = localApplicationInfo.metaData.getString("adfurikun_appkey");
      }
      i = paramContext.getSharedPreferences(AdfurikunConstants.PREF_FILE, 3).getInt(AdfurikunConstants.PREFKEY_TESTMODE, AdfurikunConstants.PREF_TESTMODE_NOSETTING);
      if (i == AdfurikunConstants.PREF_TESTMODE_NOSETTING)
      {
        this.mLog.setIsDebugable(this.mIsLog);
        if ((this.mAppID != null) && (this.mAppID.length() > 0))
        {
          this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "adfurikun_appkey[" + this.mAppID + "]");
          localLayoutParams = new FrameLayout.LayoutParams(-1, -2);
          j = 0;
          if (j < 2) {
            break label411;
          }
          Display localDisplay = ((WindowManager)getContext().getSystemService("window")).getDefaultDisplay();
          DisplayMetrics localDisplayMetrics = new DisplayMetrics();
          localDisplay.getMetrics(localDisplayMetrics);
          this.mDebugFontSize = (16.0F * localDisplayMetrics.density);
          if (Build.VERSION.SDK_INT >= 11) {
            setLayerType(1, null);
          }
          return;
        }
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        int i;
        FrameLayout.LayoutParams localLayoutParams;
        int j;
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
        label411:
        AdfurikunWebView localAdfurikunWebView = new AdfurikunWebView(getContext(), this.mAppID, this.mLog);
        localAdfurikunWebView.setVisibility(4);
        addView(localAdfurikunWebView, localLayoutParams);
        this.mUserAgent = localAdfurikunWebView.getUserAgent();
        j++;
      }
    }
  }
  
  protected boolean isEmptyAd(AdfurikunWebView paramAdfurikunWebView)
  {
    Object localObject = null;
    int i = getWidth();
    int j = getHeight();
    try
    {
      Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_4444);
      localObject = localBitmap;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      label27:
      Canvas localCanvas;
      boolean bool;
      break label27;
    }
    if (localObject != null)
    {
      localCanvas = new Canvas(localObject);
      paramAdfurikunWebView.layout(0, 0, i, j);
      paramAdfurikunWebView.draw(localCanvas);
    }
    bool = isEmptyBitmap(localObject);
    if (localObject != null) {
      localObject.recycle();
    }
    return bool;
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
    for (int i = 0;; i++)
    {
      if (i >= 2) {
        return;
      }
      AdfurikunWebView localAdfurikunWebView = (AdfurikunWebView)getChildAt(i);
      if (localAdfurikunWebView != null) {
        localAdfurikunWebView.onPause();
      }
    }
  }
  
  public void onResume()
  {
    for (int i = 0;; i++)
    {
      if (i >= 2) {
        return;
      }
      AdfurikunWebView localAdfurikunWebView = (AdfurikunWebView)getChildAt(i);
      if (localAdfurikunWebView != null) {
        localAdfurikunWebView.onResume();
      }
    }
  }
  
  protected void pushSubView(AdfurikunInfo.AdInfoForWebView paramAdInfoForWebView)
  {
    NetworkInfo localNetworkInfo = this.cm.getActiveNetworkInfo();
    AdfurikunWebView localAdfurikunWebView1;
    AdfurikunWebView localAdfurikunWebView2;
    AdfurikunInfo.AdInfoForWebView localAdInfoForWebView1;
    AdfurikunInfo.AdInfoForWebView localAdInfoForWebView2;
    label243:
    String str1;
    label310:
    label342:
    String str2;
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnected()) && (paramAdInfoForWebView != null))
    {
      this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "---------------------------------------------------------");
      if (!AdfurikunConstants.DETAIL_LOG) {
        break label393;
      }
      this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "[next_ad]adnetwork_key=" + paramAdInfoForWebView.adnetwork_key + ", user_ad_id=" + paramAdInfoForWebView.user_ad_id);
      int i = getDisplayedChild();
      localAdfurikunWebView1 = (AdfurikunWebView)getChildAt(i);
      int j = i + 1;
      if (j >= getChildCount()) {
        j = 0;
      }
      localAdfurikunWebView2 = (AdfurikunWebView)getChildAt(j);
      if ((localAdfurikunWebView1 != null) && (localAdfurikunWebView2 != null))
      {
        boolean bool = false;
        localAdInfoForWebView1 = localAdfurikunWebView1.getAdInfoForWebView();
        localAdInfoForWebView2 = localAdfurikunWebView2.getAdInfoForWebView();
        if ((localAdInfoForWebView2 != null) && (localAdInfoForWebView1 != null) && (!localAdInfoForWebView2.adnetwork_key.equals(AdfurikunConstants.ADNETWORKKEY_ADLANTIS)))
        {
          bool = isEmptyAd(localAdfurikunWebView2);
          if (bool)
          {
            if (!AdfurikunConstants.DETAIL_LOG) {
              break label426;
            }
            this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "[empty_ad]adnetwork_key=" + localAdInfoForWebView2.adnetwork_key + ", user_ad_id=" + localAdInfoForWebView2.user_ad_id);
          }
        }
        if (!bool) {
          break label494;
        }
        localAdfurikunWebView2.setAdInfo(paramAdInfoForWebView);
        if (localAdInfoForWebView1 != null)
        {
          if (!AdfurikunConstants.DETAIL_LOG) {
            break label460;
          }
          this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "[continues_ad]adnetwork_key=" + localAdInfoForWebView1.adnetwork_key + ", user_ad_id=" + localAdInfoForWebView1.user_ad_id);
        }
        this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "---------------------------------------------------------");
        if ((localAdInfoForWebView1 == null) && (localAdInfoForWebView2 == null))
        {
          if (paramAdInfoForWebView != null) {
            break label609;
          }
          str1 = "";
          if (paramAdInfoForWebView != null) {
            break label618;
          }
          str2 = "";
          label351:
          if ((str1.length() <= 0) || (str2.length() <= 0)) {
            break label627;
          }
        }
      }
    }
    label393:
    label426:
    label460:
    label494:
    label627:
    for (AdfurikunInfo.AdInfoForWebView localAdInfoForWebView3 = this.mAdfurikunInfo.getOtherAdHtml(str1, str2, Locale.getDefault().getLanguage());; localAdInfoForWebView3 = this.mAdfurikunInfo.getRandomAdHtml(Locale.getDefault().getLanguage()))
    {
      pushSubView(localAdInfoForWebView3);
      return;
      this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "[next_ad]adnetwork_key=" + paramAdInfoForWebView.adnetwork_key);
      break;
      this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "[empty_ad]adnetwork_key=" + localAdInfoForWebView2.adnetwork_key);
      break label243;
      this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "[continues_ad]adnetwork_key=" + localAdInfoForWebView1.adnetwork_key);
      break label310;
      showNext();
      localAdfurikunWebView1.setAdInfo(paramAdInfoForWebView);
      if (localAdInfoForWebView2 == null) {
        break label310;
      }
      if (AdfurikunConstants.DETAIL_LOG) {
        this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "[show_ad]adnetwork_key=" + localAdInfoForWebView2.adnetwork_key + ", user_ad_id=" + localAdInfoForWebView2.user_ad_id);
      }
      for (;;)
      {
        if (this.mIsIntersAd) {
          break label607;
        }
        localAdfurikunWebView2.recImpression();
        break;
        this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "[show_ad]adnetwork_key=" + localAdInfoForWebView2.adnetwork_key);
      }
      break label310;
      str1 = paramAdInfoForWebView.adnetwork_key;
      break label342;
      str2 = paramAdInfoForWebView.user_ad_id;
      break label351;
    }
  }
  
  protected void randomAdfurikun()
  {
    if ((this.mAdfurikunInfo != null) && (this.mAdfurikunInfo.infoArray.size() > 0)) {
      pushSubView(this.mAdfurikunInfo.getRandomAdHtml(Locale.getDefault().getLanguage()));
    }
  }
  
  public void setAdfurikunAppKey(String paramString)
  {
    this.mAppID = paramString;
    this.mLog.debug_i(AdfurikunConstants.TAG_NAME, "adfurikun_appkey[" + this.mAppID + "]");
    for (int i = 0;; i++)
    {
      if (i >= 2) {
        return;
      }
      AdfurikunWebView localAdfurikunWebView = (AdfurikunWebView)getChildAt(i);
      if (localAdfurikunWebView != null) {
        localAdfurikunWebView.setAdfurikunAppKey(this.mAppID);
      }
    }
  }
  
  protected void setDisplayedChild(int paramInt)
  {
    int i = 0;
    this.mWhichChild = paramInt;
    if (paramInt >= getChildCount()) {
      this.mWhichChild = 0;
    }
    for (;;)
    {
      if (getFocusedChild() != null) {
        i = 1;
      }
      showOnly(this.mWhichChild);
      if (i != 0) {
        requestFocus(2);
      }
      return;
      if (paramInt < 0) {
        this.mWhichChild = (-1 + getChildCount());
      }
    }
  }
  
  protected void setInAnimation(Animation paramAnimation)
  {
    this.mInAnimation = paramAnimation;
  }
  
  protected void setOutAnimation(Animation paramAnimation)
  {
    this.mOutAnimation = paramAnimation;
  }
  
  protected void showNext()
  {
    setDisplayedChild(1 + this.mWhichChild);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunBase
 * JD-Core Version:    0.7.0.1
 */