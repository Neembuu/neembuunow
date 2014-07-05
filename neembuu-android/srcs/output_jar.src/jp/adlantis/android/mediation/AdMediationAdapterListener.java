package jp.adlantis.android.mediation;

import android.view.View;

public abstract interface AdMediationAdapterListener
{
  public abstract void onDismissScreen(AdMediationAdapter paramAdMediationAdapter);
  
  public abstract void onFailedToReceiveAd(AdMediationAdapter paramAdMediationAdapter);
  
  public abstract void onLeaveApplication(AdMediationAdapter paramAdMediationAdapter);
  
  public abstract void onPresentScreen(AdMediationAdapter paramAdMediationAdapter);
  
  public abstract void onReceivedAd(AdMediationAdapter paramAdMediationAdapter, View paramView);
  
  public abstract void onTouchAd(AdMediationAdapter paramAdMediationAdapter);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.mediation.AdMediationAdapterListener
 * JD-Core Version:    0.7.0.1
 */