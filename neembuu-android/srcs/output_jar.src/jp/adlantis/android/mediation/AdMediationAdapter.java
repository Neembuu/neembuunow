package jp.adlantis.android.mediation;

import android.app.Activity;
import android.view.View;

public abstract interface AdMediationAdapter
{
  public abstract void destroy();
  
  public abstract View requestAd(AdMediationAdapterListener paramAdMediationAdapterListener, Activity paramActivity, AdMediationNetworkParameters paramAdMediationNetworkParameters);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.mediation.AdMediationAdapter
 * JD-Core Version:    0.7.0.1
 */