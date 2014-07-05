package jp.tjkapp.adfurikunsdk;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.customevent.CustomEventBanner;
import com.google.ads.mediation.customevent.CustomEventBannerListener;

public class AdfurikunAdMob
  implements CustomEventBanner
{
  private static AdfurikunAdMob mAdfurikunAdMob;
  private static AdfurikunAdMobLayout mAdfurikunAdMobLayout;
  
  private void removeParent()
  {
    if (mAdfurikunAdMobLayout != null)
    {
      ViewGroup localViewGroup = (ViewGroup)mAdfurikunAdMobLayout.getParent();
      if (localViewGroup != null) {
        localViewGroup.removeView(mAdfurikunAdMobLayout);
      }
    }
  }
  
  public void destroy()
  {
    if ((mAdfurikunAdMob == this) && (mAdfurikunAdMobLayout != null) && (mAdfurikunAdMobLayout != null))
    {
      mAdfurikunAdMobLayout.destroy();
      mAdfurikunAdMobLayout = null;
    }
  }
  
  public void requestBannerAd(CustomEventBannerListener paramCustomEventBannerListener, Activity paramActivity, String paramString1, String paramString2, AdSize paramAdSize, MediationAdRequest paramMediationAdRequest, Object paramObject)
  {
    AdSize[] arrayOfAdSize = new AdSize[5];
    arrayOfAdSize[0] = AdSize.BANNER;
    arrayOfAdSize[1] = AdSize.IAB_BANNER;
    arrayOfAdSize[2] = AdSize.IAB_LEADERBOARD;
    arrayOfAdSize[3] = AdSize.IAB_MRECT;
    arrayOfAdSize[4] = AdSize.IAB_WIDE_SKYSCRAPER;
    if (paramAdSize.findBestSize(arrayOfAdSize) == null) {
      paramCustomEventBannerListener.onFailedToReceiveAd();
    }
    for (;;)
    {
      return;
      if (mAdfurikunAdMobLayout == null) {
        mAdfurikunAdMobLayout = new AdfurikunAdMobLayout(paramActivity);
      }
      mAdfurikunAdMobLayout.setCustomEventBannerListener(paramCustomEventBannerListener);
      if ((paramString2 != null) && (paramString2.length() > 0)) {
        mAdfurikunAdMobLayout.setAdfurikunAppKey(paramString2);
      }
      mAdfurikunAdMobLayout.nextAd();
      mAdfurikunAdMob = this;
      removeParent();
      paramCustomEventBannerListener.onReceivedAd(mAdfurikunAdMobLayout);
    }
  }
  
  private class AdfurikunAdMobLayout
    extends AdfurikunBase
  {
    public AdfurikunAdMobLayout(Context paramContext)
    {
      super();
    }
    
    public AdfurikunAdMobLayout(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
    }
    
    protected void initialize(Context paramContext)
    {
      super.initialize(paramContext);
      for (int i = 0;; i++)
      {
        if (i >= 2) {
          return;
        }
        AdfurikunWebView localAdfurikunWebView = (AdfurikunWebView)getChildAt(i);
        if (localAdfurikunWebView != null) {
          localAdfurikunWebView.setOneShotMode(false);
        }
      }
    }
    
    public void setCustomEventBannerListener(CustomEventBannerListener paramCustomEventBannerListener)
    {
      for (int i = 0;; i++)
      {
        if (i >= 2) {
          return;
        }
        AdfurikunWebView localAdfurikunWebView = (AdfurikunWebView)getChildAt(i);
        if (localAdfurikunWebView != null) {
          localAdfurikunWebView.setCustomEventBannerListener(paramCustomEventBannerListener);
        }
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunAdMob
 * JD-Core Version:    0.7.0.1
 */