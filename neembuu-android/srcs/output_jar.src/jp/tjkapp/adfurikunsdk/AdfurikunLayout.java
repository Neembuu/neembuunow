package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import java.util.Random;

public class AdfurikunLayout
  extends AdfurikunBase
{
  public static final int TRANSITION_FADEIN_FADEOUT = 4;
  private static final int TRANSITION_MAX = 5;
  public static final int TRANSITION_NOTHING = -1;
  public static final int TRANSITION_RANDOM = -2;
  public static final int TRANSITION_SLIDE_FROM_BOTTOM = 3;
  public static final int TRANSITION_SLIDE_FROM_LEFT = 1;
  public static final int TRANSITION_SLIDE_FROM_RIGHT = 0;
  public static final int TRANSITION_SLIDE_FROM_TOP = 2;
  private boolean mIsEnable;
  private boolean mIsStop;
  private Random mRandom;
  private int mTransitionType = -1;
  private final Runnable updateThread = new Runnable()
  {
    public void run()
    {
      AdfurikunLayout.this.nextAd();
    }
  };
  
  public AdfurikunLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public AdfurikunLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
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
  
  private Animation getFadeInAnimation()
  {
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
    localAlphaAnimation.setDuration(800L);
    return localAlphaAnimation;
  }
  
  private Animation getFadeOutAnimation()
  {
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(1.0F, 0.0F);
    localAlphaAnimation.setDuration(400L);
    return localAlphaAnimation;
  }
  
  private Animation getPushDownInAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(2, 0.0F, 2, 0.0F, 2, -1.0F, 2, 0.0F);
    localTranslateAnimation.setDuration(300L);
    return localTranslateAnimation;
  }
  
  private Animation getPushDownOutAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(2, 0.0F, 2, 0.0F, 2, 0.0F, 2, 1.0F);
    localTranslateAnimation.setDuration(300L);
    return localTranslateAnimation;
  }
  
  private Animation getPushLeftInAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(2, 1.0F, 2, 0.0F, 2, 0.0F, 2, 0.0F);
    localTranslateAnimation.setDuration(300L);
    return localTranslateAnimation;
  }
  
  private Animation getPushLeftOutAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(2, 0.0F, 2, -1.0F, 2, 0.0F, 2, 0.0F);
    localTranslateAnimation.setDuration(300L);
    return localTranslateAnimation;
  }
  
  private Animation getPushRightInAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(2, -1.0F, 2, 0.0F, 2, 0.0F, 2, 0.0F);
    localTranslateAnimation.setDuration(300L);
    return localTranslateAnimation;
  }
  
  private Animation getPushRightOutAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(2, 0.0F, 2, 1.0F, 2, 0.0F, 2, 0.0F);
    localTranslateAnimation.setDuration(300L);
    return localTranslateAnimation;
  }
  
  private Animation getPushUpInAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(2, 0.0F, 2, 0.0F, 2, 1.0F, 2, 0.0F);
    localTranslateAnimation.setDuration(300L);
    return localTranslateAnimation;
  }
  
  private Animation getPushUpOutAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(2, 0.0F, 2, 0.0F, 2, 0.0F, 2, -1.0F);
    localTranslateAnimation.setDuration(300L);
    return localTranslateAnimation;
  }
  
  private void setTransition()
  {
    Animation localAnimation1;
    Animation localAnimation2;
    if (!this.mTaOff)
    {
      int i = this.mTransitionType;
      if (i == -2) {
        i = this.mRandom.nextInt(5);
      }
      localAnimation1 = null;
      localAnimation2 = null;
      switch (i)
      {
      }
    }
    for (;;)
    {
      setInAnimation(localAnimation1);
      setOutAnimation(localAnimation2);
      return;
      localAnimation1 = getPushLeftInAnimation();
      localAnimation2 = getPushLeftOutAnimation();
      continue;
      localAnimation1 = getPushRightInAnimation();
      localAnimation2 = getPushRightOutAnimation();
      continue;
      localAnimation1 = getPushDownInAnimation();
      localAnimation2 = getPushDownOutAnimation();
      continue;
      localAnimation1 = getPushUpInAnimation();
      localAnimation2 = getPushUpOutAnimation();
      continue;
      localAnimation1 = getFadeInAnimation();
      localAnimation2 = getFadeOutAnimation();
    }
  }
  
  public void destroy()
  {
    this.mIsEnable = false;
    this.handler.removeCallbacks(this.updateThread);
    super.destroy();
  }
  
  protected void initialize(Context paramContext)
  {
    super.initialize(paramContext);
    this.mIsEnable = false;
    this.mIsStop = true;
    this.mRandom = new Random();
  }
  
  public void nextAd()
  {
    if (!this.mIsStop) {
      super.nextAd();
    }
  }
  
  public void onPause()
  {
    super.onPause();
    this.mIsEnable = false;
    this.handler.removeCallbacks(this.updateThread);
    this.handler.removeCallbacks(this.retryThread);
    this.handler.removeCallbacks(this.retryThread2);
  }
  
  public void onResume()
  {
    super.onResume();
    this.mIsEnable = true;
    nextAd();
  }
  
  protected void pushSubView(AdfurikunInfo.AdInfoForWebView paramAdInfoForWebView)
  {
    this.handler.removeCallbacks(this.updateThread);
    if (this.mTransitionType == -2) {
      setTransition();
    }
    super.pushSubView(paramAdInfoForWebView);
    if (!this.mIsStop) {
      this.handler.postDelayed(this.updateThread, 1000L * this.mAdfurikunInfo.cycle_time);
    }
  }
  
  protected void randomAdfurikun()
  {
    if ((this.mIsEnable) && (!this.mIsStop)) {
      super.randomAdfurikun();
    }
  }
  
  public void restartRotateAd()
  {
    if (this.mIsStop) {
      startRotateAd();
    }
  }
  
  public void setTransitionType(int paramInt)
  {
    this.mTransitionType = paramInt;
    if (this.mTransitionType >= 5) {
      this.mTransitionType = -1;
    }
    setTransition();
  }
  
  public void startRotateAd()
  {
    this.mIsStop = false;
    for (int i = 0;; i++)
    {
      if (i >= 2)
      {
        nextAd();
        return;
      }
      AdfurikunWebView localAdfurikunWebView = (AdfurikunWebView)getChildAt(i);
      if (localAdfurikunWebView != null) {
        localAdfurikunWebView.setOneShotMode(false);
      }
    }
  }
  
  public void stopRotateAd()
  {
    if (!this.mIsStop)
    {
      this.mIsStop = true;
      this.handler.removeCallbacks(this.updateThread);
      this.handler.removeCallbacks(this.retryThread);
      this.handler.removeCallbacks(this.retryThread2);
      cancelTask();
    }
    for (int i = 0;; i++)
    {
      if (i >= 2) {
        return;
      }
      AdfurikunWebView localAdfurikunWebView = (AdfurikunWebView)getChildAt(i);
      if (localAdfurikunWebView != null) {
        localAdfurikunWebView.setOneShotMode(true);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunLayout
 * JD-Core Version:    0.7.0.1
 */