package com.google.ads.mediation.imobile;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import jp.co.imobile.android.AdRequestResult;
import jp.co.imobile.android.AdView;
import jp.co.imobile.android.AdViewRequestListener;
import jp.co.imobile.android.AdViewRunMode;
import jp.co.imobile.android.AdViewStateListener;
import jp.co.imobile.android.SupportAdSize;

public final class IMobileAdapter
  implements MediationBannerAdapter<IMobileExtras, IMobileServerParameters>, MediationInterstitialAdapter<IMobileExtras, IMobileServerParameters>
{
  private static final AdSize[] IMOBILE_SUPPORT_SIZES = ;
  private AdView adView;
  private MediationBannerListener bannerListener;
  private FrameLayout wrappedAdView;
  
  private static final AdSize[] IMobileSupportAdSizes()
  {
    SupportAdSize[] arrayOfSupportAdSize = SupportAdSize.values();
    AdSize[] arrayOfAdSize = new AdSize[arrayOfSupportAdSize.length];
    for (int i = 0;; i++)
    {
      if (i >= arrayOfSupportAdSize.length) {
        return arrayOfAdSize;
      }
      SupportAdSize localSupportAdSize = arrayOfSupportAdSize[i];
      arrayOfAdSize[i] = new AdSize(localSupportAdSize.getWidth(), localSupportAdSize.getHeight());
    }
  }
  
  public void destroy()
  {
    if (this.adView != null)
    {
      this.adView.stop();
      this.adView = null;
    }
    this.bannerListener = null;
  }
  
  public Class<IMobileExtras> getAdditionalParametersType()
  {
    return IMobileExtras.class;
  }
  
  public View getBannerView()
  {
    return this.wrappedAdView;
  }
  
  public Class<IMobileServerParameters> getServerParametersType()
  {
    return IMobileServerParameters.class;
  }
  
  public void requestBannerAd(MediationBannerListener paramMediationBannerListener, Activity paramActivity, IMobileServerParameters paramIMobileServerParameters, AdSize paramAdSize, MediationAdRequest paramMediationAdRequest, IMobileExtras paramIMobileExtras)
  {
    this.bannerListener = paramMediationBannerListener;
    AdSize localAdSize = paramAdSize.findBestSize(IMOBILE_SUPPORT_SIZES);
    if (localAdSize == null) {
      this.bannerListener.onFailedToReceiveAd(this, AdRequest.ErrorCode.NO_FILL);
    }
    for (;;)
    {
      return;
      this.adView = AdView.create(paramActivity, paramIMobileServerParameters.publisherId, paramIMobileServerParameters.mediaId, paramIMobileServerParameters.spotId);
      this.adView.setRunState(AdViewRunMode.FULL_MANUAL);
      IMobileInternalBannerListener localIMobileInternalBannerListener = new IMobileInternalBannerListener(null);
      this.adView.setOnRequestListener(localIMobileInternalBannerListener);
      IMobileInternalStateListener localIMobileInternalStateListener = new IMobileInternalStateListener(null);
      this.adView.setOnViewStateListener(localIMobileInternalStateListener);
      FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(localAdSize.getWidthInPixels(paramActivity), localAdSize.getHeightInPixels(paramActivity));
      this.wrappedAdView = new FrameLayout(paramActivity);
      this.wrappedAdView.setLayoutParams(localLayoutParams);
      this.wrappedAdView.addView(this.adView);
      this.adView.start();
    }
  }
  
  public void requestInterstitialAd(MediationInterstitialListener paramMediationInterstitialListener, Activity paramActivity, IMobileServerParameters paramIMobileServerParameters, MediationAdRequest paramMediationAdRequest, IMobileExtras paramIMobileExtras)
  {
    paramMediationInterstitialListener.onFailedToReceiveAd(this, AdRequest.ErrorCode.INVALID_REQUEST);
  }
  
  public void showInterstitial() {}
  
  private class IMobileInternalBannerListener
    implements AdViewRequestListener
  {
    private IMobileInternalBannerListener() {}
    
    public void onCompleted(AdRequestResult paramAdRequestResult, AdView paramAdView)
    {
      switch (paramAdRequestResult.getResult())
      {
      default: 
        IMobileAdapter.this.bannerListener.onFailedToReceiveAd(IMobileAdapter.this, AdRequest.ErrorCode.INTERNAL_ERROR);
      }
      for (;;)
      {
        return;
        IMobileAdapter.this.bannerListener.onReceivedAd(IMobileAdapter.this);
        continue;
        IMobileAdapter.this.bannerListener.onClick(IMobileAdapter.this);
        IMobileAdapter.this.bannerListener.onPresentScreen(IMobileAdapter.this);
        IMobileAdapter.this.bannerListener.onLeaveApplication(IMobileAdapter.this);
      }
    }
    
    public void onFailed(AdRequestResult paramAdRequestResult, AdView paramAdView)
    {
      switch (paramAdRequestResult.getResult())
      {
      case FAIL_CLICK_HOUSE_AD: 
      case NOT_FOUND_AD: 
      case PARAM_ERROR: 
      default: 
        IMobileAdapter.this.bannerListener.onFailedToReceiveAd(IMobileAdapter.this, AdRequest.ErrorCode.INTERNAL_ERROR);
      }
      for (;;)
      {
        return;
        IMobileAdapter.this.bannerListener.onFailedToReceiveAd(IMobileAdapter.this, AdRequest.ErrorCode.NETWORK_ERROR);
        continue;
        IMobileAdapter.this.bannerListener.onFailedToReceiveAd(IMobileAdapter.this, AdRequest.ErrorCode.NO_FILL);
        continue;
        IMobileAdapter.this.bannerListener.onFailedToReceiveAd(IMobileAdapter.this, AdRequest.ErrorCode.INVALID_REQUEST);
      }
    }
  }
  
  private class IMobileInternalStateListener
    implements AdViewStateListener
  {
    private IMobileInternalStateListener() {}
    
    public void onDismissAdScreen(AdView paramAdView)
    {
      IMobileAdapter.this.bannerListener.onDismissScreen(IMobileAdapter.this);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.mediation.imobile.IMobileAdapter
 * JD-Core Version:    0.7.0.1
 */