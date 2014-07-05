package com.google.ads.mediation.nend;

import android.app.Activity;
import android.view.View;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import net.nend.android.NendAdListener;
import net.nend.android.NendAdView;

public final class NendAdapter
  implements MediationBannerAdapter<NendAdapterExtras, NendAdapterServerParameters>, MediationInterstitialAdapter<NendAdapterExtras, NendAdapterServerParameters>, NendAdListener
{
  public static final String VERSION = "1.0.0";
  private MediationBannerListener mListener;
  private NendAdView mNendAdView;
  
  public void destroy()
  {
    this.mNendAdView = null;
    this.mListener = null;
  }
  
  public Class<NendAdapterExtras> getAdditionalParametersType()
  {
    return NendAdapterExtras.class;
  }
  
  public View getBannerView()
  {
    return this.mNendAdView;
  }
  
  public Class<NendAdapterServerParameters> getServerParametersType()
  {
    return NendAdapterServerParameters.class;
  }
  
  public void onClick(NendAdView paramNendAdView)
  {
    if (this.mListener != null)
    {
      this.mListener.onClick(this);
      this.mListener.onPresentScreen(this);
      this.mListener.onLeaveApplication(this);
    }
  }
  
  public void onDismissScreen(NendAdView paramNendAdView)
  {
    if (this.mListener != null) {
      this.mListener.onDismissScreen(this);
    }
  }
  
  public void onFailedToReceiveAd(NendAdView paramNendAdView)
  {
    if (this.mListener != null) {
      this.mListener.onFailedToReceiveAd(this, AdRequest.ErrorCode.INTERNAL_ERROR);
    }
  }
  
  public void onReceiveAd(NendAdView paramNendAdView)
  {
    if (this.mListener != null) {
      this.mListener.onReceivedAd(this);
    }
  }
  
  public void requestBannerAd(MediationBannerListener paramMediationBannerListener, Activity paramActivity, NendAdapterServerParameters paramNendAdapterServerParameters, AdSize paramAdSize, MediationAdRequest paramMediationAdRequest, NendAdapterExtras paramNendAdapterExtras)
  {
    if (!paramAdSize.isSizeAppropriate(320, 50)) {
      paramMediationBannerListener.onFailedToReceiveAd(this, AdRequest.ErrorCode.NO_FILL);
    }
    for (;;)
    {
      return;
      this.mListener = paramMediationBannerListener;
      this.mNendAdView = new NendAdView(paramActivity, Integer.parseInt(paramNendAdapterServerParameters.spotIdStr), paramNendAdapterServerParameters.apiKey);
      this.mNendAdView.pause();
      this.mNendAdView.setListener(this);
      this.mNendAdView.loadAd();
    }
  }
  
  public void requestInterstitialAd(MediationInterstitialListener paramMediationInterstitialListener, Activity paramActivity, NendAdapterServerParameters paramNendAdapterServerParameters, MediationAdRequest paramMediationAdRequest, NendAdapterExtras paramNendAdapterExtras)
  {
    paramMediationInterstitialListener.onFailedToReceiveAd(this, AdRequest.ErrorCode.INVALID_REQUEST);
  }
  
  public void showInterstitial()
  {
    throw new UnsupportedOperationException();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.mediation.nend.NendAdapter
 * JD-Core Version:    0.7.0.1
 */