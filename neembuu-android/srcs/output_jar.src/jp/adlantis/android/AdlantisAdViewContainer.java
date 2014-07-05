package jp.adlantis.android;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import android.widget.ViewFlipper;
import java.util.HashMap;
import java.util.Map;
import jp.adlantis.android.mediation.AdMediationManager;
import jp.adlantis.android.mediation.AdMediationRequest;
import jp.adlantis.android.utils.ADLDebugUtils;
import jp.adlantis.android.utils.AdlantisUtils;

public class AdlantisAdViewContainer
  extends RelativeLayout
  implements AdRequestNotifier
{
  static int _animationDuration = 500;
  static final int touchHighlightAnimationDuration = 150;
  private AdlantisAdView[] _adViews;
  AdlantisAdsModel _adsModel = new AdlantisAdsModel();
  private boolean _buttonPressed;
  private int _currentAdIndex = 0;
  private boolean _detachingFromWindow;
  private Handler _handler = new Handler(Looper.getMainLooper());
  private boolean _handlingUserEvent;
  private long _idNotSpecifiedWarningInterval = 5000L;
  private boolean _inOnWindowVisibilityChanged;
  private boolean _layoutComplete;
  private int _onWindowVisibilityChangedVisibility;
  private int _previousAdCount = 0;
  private ProgressBar _processIndicator;
  private ViewFlipper _rootViewFlipper;
  private View _touchHighlight;
  protected String lastUsedPublisherID;
  protected AdRequestListeners listeners = new AdRequestListeners();
  private long mAdFetchInterval = adManager().adFetchInterval();
  protected AdRequest mCurrentAdRequest;
  private Runnable mRotateAdTask = new Runnable()
  {
    public void run()
    {
      AdlantisAdViewContainer.this.showNextAd();
      long l = SystemClock.uptimeMillis();
      AdlantisAdViewContainer.this._handler.postAtTime(this, l + AdlantisAdViewContainer.this.adDisplayInterval());
    }
  };
  private Runnable mUpdateAdsTask = new Runnable()
  {
    public void run()
    {
      AdlantisAdViewContainer.this.connect();
    }
  };
  
  public AdlantisAdViewContainer(Context paramContext)
  {
    super(paramContext);
    commonInit();
  }
  
  public AdlantisAdViewContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    String str = paramAttributeSet.getAttributeValue("http://schemas.android.com/apk/res/jp.adlantis.android", "publisherID");
    if (str != null) {
      setPublisherID(str);
    }
    commonInit();
  }
  
  private void adCountChanged()
  {
    int i = adCountForCurrentOrientation();
    if (this._rootViewFlipper != null) {
      if ((i > 0) && (this._previousAdCount == 0))
      {
        this._rootViewFlipper.setVisibility(0);
        this._rootViewFlipper.startAnimation(fadeInAnimation());
      }
    }
    for (;;)
    {
      this._previousAdCount = i;
      return;
      if ((i == 0) && (this._previousAdCount > 0))
      {
        this._rootViewFlipper.startAnimation(fadeOutAnimation());
        this._rootViewFlipper.setVisibility(4);
        continue;
        Log.w(getClass().getSimpleName(), "adCountChanged called when _rootViewFlipper is not available");
      }
    }
  }
  
  private long adDisplayInterval()
  {
    return adManager().adDisplayInterval();
  }
  
  private long adFetchInterval()
  {
    return this.mAdFetchInterval;
  }
  
  public static int animationDuration()
  {
    return _animationDuration;
  }
  
  private void commonInit()
  {
    setupLayout();
    setupModelListener();
    String str;
    if (getPublisherID() == null)
    {
      str = packagePublisherID();
      if (str != null)
      {
        if (!AdManager.isGreeSdk()) {
          break label40;
        }
        setGapPublisherID(str);
      }
    }
    for (;;)
    {
      showToastIfPublisherIdNotSpecified();
      return;
      label40:
      setPublisherID(str);
      continue;
      if (hasAdsForCurrentOrientation()) {
        startTimers();
      } else {
        connect();
      }
    }
  }
  
  private void connectIfPublisherIDChanged()
  {
    if (publisherIdChanged()) {
      connect();
    }
  }
  
  private AdlantisAd currentAd()
  {
    AdlantisAd localAdlantisAd = null;
    AdlantisAd[] arrayOfAdlantisAd = adsForCurrentOrientation();
    if ((arrayOfAdlantisAd != null) && (arrayOfAdlantisAd.length > 0) && (this._currentAdIndex < arrayOfAdlantisAd.length)) {
      localAdlantisAd = arrayOfAdlantisAd[this._currentAdIndex];
    }
    return localAdlantisAd;
  }
  
  static int defaultBackgroundColor()
  {
    return ViewSettings.defaultBackgroundColor();
  }
  
  static Animation fadeInAnimation()
  {
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
    localAlphaAnimation.setDuration(animationDuration());
    return localAlphaAnimation;
  }
  
  static Animation fadeOutAnimation()
  {
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(1.0F, 0.0F);
    localAlphaAnimation.setDuration(animationDuration());
    return localAlphaAnimation;
  }
  
  private void handleUserTouchUp()
  {
    AdlantisAd localAdlantisAd = currentAd();
    if ((localAdlantisAd == null) || (this._handlingUserEvent)) {}
    for (;;)
    {
      return;
      this._handlingUserEvent = true;
      this._processIndicator.setVisibility(0);
      localAdlantisAd.sendImpressionCount();
      if (localAdlantisAd.tapUrlString() != null) {
        handleClickRequest(localAdlantisAd);
      } else {
        this._handlingUserEvent = false;
      }
    }
  }
  
  static Animation inFromLeftAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(2, -1.0F, 2, 0.0F, 2, 0.0F, 2, 0.0F);
    localTranslateAnimation.setDuration(animationDuration());
    return localTranslateAnimation;
  }
  
  static Animation inFromRightAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(2, 1.0F, 2, 0.0F, 2, 0.0F, 2, 0.0F);
    localTranslateAnimation.setDuration(animationDuration());
    return localTranslateAnimation;
  }
  
  private boolean inView(MotionEvent paramMotionEvent)
  {
    boolean bool1 = true;
    float f1 = paramMotionEvent.getX();
    float f2 = paramMotionEvent.getY();
    float f3 = getHeight();
    float f4 = getWidth();
    boolean bool2;
    if ((f1 >= 0.0F) && (f1 <= f4) && (f2 >= 0.0F) && (f2 <= f3))
    {
      bool2 = bool1;
      if (!bool2) {
        break label83;
      }
      if (paramMotionEvent.getEdgeFlags() != 0) {
        break label78;
      }
    }
    for (;;)
    {
      return bool1;
      bool2 = false;
      break;
      label78:
      bool1 = false;
      continue;
      label83:
      bool1 = bool2;
    }
  }
  
  private boolean openUri(Uri paramUri)
  {
    boolean bool = false;
    Intent localIntent = new Intent("android.intent.action.VIEW", paramUri);
    localIntent.addFlags(268435456);
    try
    {
      getContext().startActivity(localIntent);
      bool = true;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      for (;;)
      {
        Log.e(getClass().getSimpleName(), "activity not found for url=" + paramUri + " exception=" + localActivityNotFoundException);
      }
    }
    return bool;
  }
  
  static Animation outToLeftAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(2, 0.0F, 2, -1.0F, 2, 0.0F, 2, 0.0F);
    localTranslateAnimation.setDuration(animationDuration());
    return localTranslateAnimation;
  }
  
  static Animation outToRightAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(2, 0.0F, 2, 1.0F, 2, 0.0F, 2, 0.0F);
    localTranslateAnimation.setDuration(animationDuration());
    return localTranslateAnimation;
  }
  
  private String packagePublisherID()
  {
    try
    {
      ApplicationInfo localApplicationInfo = getContext().getPackageManager().getApplicationInfo(getContext().getPackageName(), 128);
      if ((localApplicationInfo != null) && (localApplicationInfo.metaData != null))
      {
        if (adManager().hasTestAdRequestUrls())
        {
          String str4 = (String)localApplicationInfo.metaData.get("Adlantis_adRequestUrl");
          String[] arrayOfString = new String[1];
          arrayOfString[0] = str4;
          if (str4 != null) {
            adManager().setTestAdRequestUrls(arrayOfString);
          }
        }
        String str2 = (String)localApplicationInfo.metaData.get("Adlantis_keywords");
        if (str2 != null) {
          adManager().setKeywords(str2);
        }
        String str3 = (String)localApplicationInfo.metaData.get("Adlantis_host");
        if (str3 != null) {
          adManager().setHost(str3);
        }
        str1 = (String)localApplicationInfo.metaData.get(AdManager.getInstance().publisherIDMetadataKey());
        return str1;
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        logD("packagePublisherID" + localException);
        String str1 = null;
      }
    }
  }
  
  private void setAdByIndex(int paramInt)
  {
    logD("setAdByIndex=" + paramInt + " this=" + this);
    if (this._rootViewFlipper == null) {
      logD("setAdByIndex _rootViewFlipper == null");
    }
    int i;
    do
    {
      return;
      i = adCountForCurrentOrientation();
    } while (i == 0);
    if (paramInt >= i) {
      paramInt = 0;
    }
    if (this._rootViewFlipper.getCurrentView() == this._adViews[0]) {}
    for (AdlantisAdView localAdlantisAdView = this._adViews[1];; localAdlantisAdView = this._adViews[0])
    {
      localAdlantisAdView.setAdByIndex(paramInt);
      logD("Animation: " + getClass().getSimpleName() + ".adCountChanged _rootViewFlipper.showNext view = " + this);
      this._rootViewFlipper.showNext();
      break;
    }
  }
  
  public static void setAnimationDuration(int paramInt)
  {
    _animationDuration = paramInt;
  }
  
  private void setButtonState(boolean paramBoolean)
  {
    if (paramBoolean != this._buttonPressed)
    {
      if (!paramBoolean) {
        break label42;
      }
      this._touchHighlight.setVisibility(0);
      this._touchHighlight.startAnimation(touchFadeIn());
      setPressed(true);
    }
    for (;;)
    {
      this._buttonPressed = paramBoolean;
      return;
      label42:
      this._touchHighlight.startAnimation(touchFadeOut());
      setPressed(false);
    }
  }
  
  private void setupLayout()
  {
    setClickable(true);
    this._rootViewFlipper = createRootViewFlipper();
    addView(this._rootViewFlipper, rootViewFlipperLayoutParams());
    View localView = createTouchHighlight();
    this._touchHighlight = localView;
    addView(localView);
    this._processIndicator = createProgressBar();
    addView(this._processIndicator);
    this._layoutComplete = true;
    logD("setupLayout setting _layoutComplete = true");
    setAnimationType(AnimationType.FADE);
    if (hasAdsForCurrentOrientation())
    {
      logD("setupLayout calling setAdByIndex");
      this._currentAdIndex = 0;
      setAdByIndex(0);
    }
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams.addRule(13);
    setLayoutParams(localLayoutParams);
  }
  
  private void startTimers()
  {
    if (hasAdsForCurrentOrientation())
    {
      this._handler.removeCallbacks(this.mRotateAdTask);
      this._handler.postDelayed(this.mRotateAdTask, adDisplayInterval());
    }
    this._handler.removeCallbacks(this.mUpdateAdsTask);
    if (this.mAdFetchInterval > 0L) {
      this._handler.postDelayed(this.mUpdateAdsTask, adFetchInterval());
    }
  }
  
  private void stopTimers()
  {
    if (this._handler != null)
    {
      this._handler.removeCallbacks(this.mRotateAdTask);
      this._handler.removeCallbacks(this.mUpdateAdsTask);
    }
  }
  
  private Animation touchFadeIn()
  {
    Animation localAnimation = fadeInAnimation();
    localAnimation.setDuration(150L);
    localAnimation.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation) {}
      
      public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
      
      public void onAnimationStart(Animation paramAnonymousAnimation) {}
    });
    return localAnimation;
  }
  
  private Animation touchFadeOut()
  {
    Animation localAnimation = fadeOutAnimation();
    localAnimation.setDuration(150L);
    localAnimation.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        AdlantisAdViewContainer.this._touchHighlight.setVisibility(4);
      }
      
      public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
      
      public void onAnimationStart(Animation paramAnonymousAnimation) {}
    });
    return localAnimation;
  }
  
  private void viewVisibilityChanged()
  {
    if (shouldRunAdTimers()) {
      startTimers();
    }
    for (;;)
    {
      return;
      stopTimers();
    }
  }
  
  protected int adCountForCurrentOrientation()
  {
    return getModel().adCountForOrientation(orientation());
  }
  
  protected AdManager adManager()
  {
    return AdManager.getInstance();
  }
  
  public void addRequestListener(AdRequestListener paramAdRequestListener)
  {
    this.listeners.addRequestListener(paramAdRequestListener);
  }
  
  public Map<String, String> additionalParametersForAdRequest()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("orientation", orientationString());
    return localHashMap;
  }
  
  protected AdlantisAd[] adsForCurrentOrientation()
  {
    return getModel().adsForOrientation(orientation());
  }
  
  protected void adsWereLoaded()
  {
    adCountChanged();
    if (hasAdsForCurrentOrientation())
    {
      this._currentAdIndex = 0;
      if (!this._layoutComplete) {
        break label36;
      }
      setAdByIndex(this._currentAdIndex);
    }
    for (;;)
    {
      startTimers();
      return;
      label36:
      logD("adsWereLoaded() layout not complete!!!");
    }
  }
  
  public void clearAds()
  {
    this._handler.removeCallbacks(this.mRotateAdTask);
    getModel().clearAds();
    adCountChanged();
  }
  
  public void connect()
  {
    if (getPublisherID() != null)
    {
      logD("connect view =" + this);
      this.lastUsedPublisherID = getPublisherID();
      createAdRequest().connect(getContext(), additionalParametersForAdRequest(), new AdRequest.AdRequestManagerCallback()
      {
        public void adsLoaded()
        {
          if (AdManager.isGreeSdk()) {}
          for (;;)
          {
            return;
            AdlantisAdViewContainer localAdlantisAdViewContainer = AdlantisAdViewContainer.this;
            if ((AdlantisAdViewContainer.this._adsModel.getNetworkParameters() != null) && (AdlantisAdViewContainer.this._adsModel.getNetworkParameters().length > 0))
            {
              AdlantisAdViewContainer.this.logD("start ad mediation...");
              AdMediationManager.getInstance().requestAd((Activity)localAdlantisAdViewContainer.getContext(), localAdlantisAdViewContainer, AdlantisAdViewContainer.this._adsModel.getNetworkParameters());
            }
            else
            {
              AdMediationManager.getInstance().destroy();
              if (localAdlantisAdViewContainer.getChildCount() == 0) {
                AdlantisAdViewContainer.this.setupLayout();
              }
            }
          }
        }
      });
    }
    for (;;)
    {
      return;
      Log.e(getClass().getSimpleName(), getClass().getSimpleName() + ": can't connect because publisherID hasn't been set.");
    }
  }
  
  public AdRequest createAdRequest()
  {
    this.mCurrentAdRequest = new AdMediationRequest(getModel());
    logD("createAdRequest adRequest = " + this.mCurrentAdRequest);
    this.mCurrentAdRequest.addRequestListener(new AdRequestListener()
    {
      public void onFailedToReceiveAd(AdRequestNotifier paramAnonymousAdRequestNotifier)
      {
        AdlantisAdViewContainer.this.logD("onFailedToReceiveAd adRequest = " + AdlantisAdViewContainer.this.mCurrentAdRequest);
        AdlantisAdViewContainer.this.listeners.notifyListenersFailedToReceiveAd(paramAnonymousAdRequestNotifier);
        AdlantisAdViewContainer.this.mCurrentAdRequest = null;
      }
      
      public void onReceiveAd(AdRequestNotifier paramAnonymousAdRequestNotifier)
      {
        AdlantisAdViewContainer.this.logD("onReceiveAd adRequest = " + AdlantisAdViewContainer.this.mCurrentAdRequest);
        AdlantisAdViewContainer.this.listeners.notifyListenersAdReceived(paramAnonymousAdRequestNotifier);
        AdlantisAdViewContainer.this.mCurrentAdRequest = null;
      }
      
      public void onTouchAd(AdRequestNotifier paramAnonymousAdRequestNotifier) {}
    });
    return this.mCurrentAdRequest;
  }
  
  protected AdlantisAdView createAdlantisAdView()
  {
    AdlantisAdView localAdlantisAdView = new AdlantisAdView(getContext());
    localAdlantisAdView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    localAdlantisAdView.setAdsModel(getModel());
    return localAdlantisAdView;
  }
  
  protected ProgressBar createProgressBar()
  {
    ProgressBar localProgressBar = new ProgressBar(getContext());
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams.addRule(13);
    localProgressBar.setLayoutParams(localLayoutParams);
    localProgressBar.setIndeterminate(true);
    localProgressBar.setVisibility(4);
    return localProgressBar;
  }
  
  protected ViewFlipper createRootViewFlipper()
  {
    AdlantisViewFlipper localAdlantisViewFlipper = new AdlantisViewFlipper(getContext());
    localAdlantisViewFlipper.setBackgroundColor(defaultBackgroundColor());
    if (!hasAdsForCurrentOrientation()) {
      localAdlantisViewFlipper.setVisibility(4);
    }
    this._adViews = new AdlantisAdView[2];
    for (int i = 0; i < this._adViews.length; i++)
    {
      this._adViews[i] = createAdlantisAdView();
      localAdlantisViewFlipper.addView(this._adViews[i]);
    }
    return localAdlantisViewFlipper;
  }
  
  protected View createTouchHighlight()
  {
    View localView = new View(getContext());
    localView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    localView.setBackgroundColor(1728053247);
    localView.setVisibility(4);
    return localView;
  }
  
  protected void dumpLayoutGeometry()
  {
    ADLDebugUtils.dumpSubviewLayout(this, 0);
  }
  
  protected AdlantisAdsModel getModel()
  {
    return this._adsModel;
  }
  
  public String getPublisherID()
  {
    return adManager().getPublisherID();
  }
  
  protected int getWindowCurrentVisibility()
  {
    if (this._inOnWindowVisibilityChanged) {}
    for (int i = this._onWindowVisibilityChangedVisibility;; i = getWindowVisibility()) {
      return i;
    }
  }
  
  protected void handleClickRequest(AdlantisAd paramAdlantisAd)
  {
    adManager().handleClickRequest(paramAdlantisAd, new AdManager.AdManagerRedirectUrlProcessedCallback()
    {
      public void redirectProcessed(Uri paramAnonymousUri)
      {
        boolean bool = AdlantisAdViewContainer.this.openUri(paramAnonymousUri);
        AdlantisAdViewContainer.this._processIndicator.setVisibility(4);
        AdlantisAdViewContainer.access$602(AdlantisAdViewContainer.this, false);
        if (!bool) {
          AdlantisAdViewContainer.this.startTimers();
        }
      }
    });
    logD("onTouchAd AdRequestNotifier = " + this);
    this.listeners.notifyListenersAdTouched(this);
  }
  
  protected boolean hasAdsForCurrentOrientation()
  {
    if (adCountForCurrentOrientation() > 0) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  protected boolean isAttachedToWindow()
  {
    if ((!this._detachingFromWindow) && (getWindowToken() != null) && (getWindowToken().pingBinder())) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  boolean isDoingAdRequest()
  {
    if (this.mCurrentAdRequest != null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  protected void logD(String paramString)
  {
    Log.d(getClass().getSimpleName(), paramString);
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    viewVisibilityChanged();
  }
  
  protected void onDetachedFromWindow()
  {
    this._detachingFromWindow = true;
    super.onDetachedFromWindow();
    viewVisibilityChanged();
    this._detachingFromWindow = false;
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this._rootViewFlipper.setLayoutParams(rootViewFlipperLayoutParams());
    postRequestLayout();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = false;
    if (currentAd() == null) {
      return bool;
    }
    switch (paramMotionEvent.getAction())
    {
    }
    for (;;)
    {
      bool = true;
      break;
      stopTimers();
      setButtonState(true);
      continue;
      setButtonState(inView(paramMotionEvent));
      continue;
      setButtonState(false);
      startTimers();
      continue;
      setButtonState(false);
      if (inView(paramMotionEvent)) {
        handleUserTouchUp();
      } else {
        startTimers();
      }
    }
  }
  
  protected void onWindowVisibilityChanged(int paramInt)
  {
    this._inOnWindowVisibilityChanged = true;
    this._onWindowVisibilityChangedVisibility = paramInt;
    super.onWindowVisibilityChanged(paramInt);
    viewVisibilityChanged();
    this._inOnWindowVisibilityChanged = false;
  }
  
  protected int orientation()
  {
    return AdlantisUtils.orientation(this);
  }
  
  protected String orientationString()
  {
    return AdlantisUtils.orientationToString(orientation());
  }
  
  protected void postRequestLayout()
  {
    post(new Runnable()
    {
      public void run()
      {
        AdlantisAdViewContainer.this.requestLayout();
      }
    });
  }
  
  public boolean publisherIdChanged()
  {
    String str = getPublisherID();
    if ((str != this.lastUsedPublisherID) || ((str != null) && (!str.equals(this.lastUsedPublisherID)))) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public void removeRequestListener(AdRequestListener paramAdRequestListener)
  {
    this.listeners.removeRequestListener(paramAdRequestListener);
  }
  
  protected RelativeLayout.LayoutParams rootViewFlipperLayoutParams()
  {
    Rect localRect = AdlantisUtils.adSizeForOrientation(orientation());
    float f = AdlantisUtils.displayDensity(getContext());
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams((int)(f * localRect.width()), (int)(f * localRect.height()));
    localLayoutParams.addRule(13);
    return localLayoutParams;
  }
  
  protected void setAdFetchInterval(long paramLong)
  {
    this.mAdFetchInterval = paramLong;
    startTimers();
  }
  
  public void setAnimationType(AnimationType paramAnimationType)
  {
    if (this._rootViewFlipper == null) {}
    for (;;)
    {
      return;
      switch (11.$SwitchMap$jp$adlantis$android$AdlantisAdViewContainer$AnimationType[paramAnimationType.ordinal()])
      {
      default: 
        break;
      case 1: 
        this._rootViewFlipper.setInAnimation(null);
        this._rootViewFlipper.setOutAnimation(null);
        break;
      case 2: 
        this._rootViewFlipper.setInAnimation(fadeInAnimation());
        this._rootViewFlipper.setOutAnimation(fadeOutAnimation());
        break;
      case 3: 
        this._rootViewFlipper.setInAnimation(inFromRightAnimation());
        this._rootViewFlipper.setOutAnimation(outToLeftAnimation());
        break;
      case 4: 
        this._rootViewFlipper.setInAnimation(inFromLeftAnimation());
        this._rootViewFlipper.setOutAnimation(outToRightAnimation());
      }
    }
  }
  
  public void setGapPublisherID(String paramString)
  {
    adManager().setGapPublisherID(paramString);
    connectIfPublisherIDChanged();
  }
  
  public void setKeywords(String paramString)
  {
    adManager().setKeywords(paramString);
  }
  
  public void setPublisherID(String paramString)
  {
    adManager().setPublisherID(paramString);
    connectIfPublisherIDChanged();
  }
  
  public void setVisibility(int paramInt)
  {
    super.setVisibility(paramInt);
    viewVisibilityChanged();
  }
  
  public void setupModelListener()
  {
    getModel().addListener(new AdlantisAdsModel.AdlantisAdsModelListener()
    {
      public void onChange(AdlantisAdsModel paramAnonymousAdlantisAdsModel)
      {
        AdlantisAdViewContainer.this._handler.post(new Runnable()
        {
          public void run()
          {
            AdlantisAdViewContainer.this.adsWereLoaded();
          }
        });
      }
    });
  }
  
  protected boolean shouldRunAdTimers()
  {
    if ((getParent() != null) && (getVisibility() == 0) && (isAttachedToWindow()) && (getWindowCurrentVisibility() == 0)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public void showNextAd()
  {
    int i = adCountForCurrentOrientation();
    if (i > 1)
    {
      this._currentAdIndex = ((1 + this._currentAdIndex) % i);
      setAdByIndex(this._currentAdIndex);
    }
  }
  
  public void showToastIfPublisherIdNotSpecified()
  {
    if (getPublisherID() == null)
    {
      Runnable local5 = new Runnable()
      {
        public void run()
        {
          if (AdlantisAdViewContainer.this.getPublisherID() == null)
          {
            Toast.makeText(AdlantisAdViewContainer.this.getContext(), "AdlantisView publisher id not set", 1).show();
            Log.e(getClass().getSimpleName(), getClass().getSimpleName() + ": can't display ads because publisherID hasn't been set.");
          }
          for (;;)
          {
            return;
            if (!AdlantisAdViewContainer.this.isDoingAdRequest()) {
              AdlantisAdViewContainer.this.connectIfPublisherIDChanged();
            }
          }
        }
      };
      this._handler.postDelayed(local5, this._idNotSpecifiedWarningInterval);
    }
  }
  
  public static enum AnimationType
  {
    static
    {
      FADE = new AnimationType("FADE", 1);
      SLIDE_FROM_RIGHT = new AnimationType("SLIDE_FROM_RIGHT", 2);
      SLIDE_FROM_LEFT = new AnimationType("SLIDE_FROM_LEFT", 3);
      AnimationType[] arrayOfAnimationType = new AnimationType[4];
      arrayOfAnimationType[0] = NONE;
      arrayOfAnimationType[1] = FADE;
      arrayOfAnimationType[2] = SLIDE_FROM_RIGHT;
      arrayOfAnimationType[3] = SLIDE_FROM_LEFT;
      $VALUES = arrayOfAnimationType;
    }
    
    private AnimationType() {}
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdlantisAdViewContainer
 * JD-Core Version:    0.7.0.1
 */