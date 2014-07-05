package jp.adlantis.android.mediation.adapters;

import android.app.Activity;
import android.view.View;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import jp.adlantis.android.mediation.AdMediationAdapter;
import jp.adlantis.android.mediation.AdMediationAdapterListener;
import jp.adlantis.android.mediation.AdMediationNetworkParameters;

public class AdMobAdapter
  implements AdMediationAdapter, AdListener
{
  private AdView adView = null;
  private AdMediationAdapterListener listener = null;
  
  public void destroy()
  {
    if (this.adView != null)
    {
      this.adView.setAdListener(null);
      this.adView.setOnTouchListener(null);
      this.adView.stopLoading();
      this.adView.destroy();
    }
    this.adView = null;
  }
  
  public void onDismissScreen(Ad paramAd)
  {
    if (this.listener != null) {
      this.listener.onDismissScreen(this);
    }
  }
  
  public void onFailedToReceiveAd(Ad paramAd, AdRequest.ErrorCode paramErrorCode)
  {
    if (this.listener != null) {
      this.listener.onFailedToReceiveAd(this);
    }
  }
  
  public void onLeaveApplication(Ad paramAd)
  {
    if (this.listener != null) {
      this.listener.onLeaveApplication(this);
    }
  }
  
  public void onPresentScreen(Ad paramAd)
  {
    if (this.listener != null) {
      this.listener.onTouchAd(this);
    }
  }
  
  public void onReceiveAd(Ad paramAd)
  {
    if (this.listener != null)
    {
      this.adView.stopLoading();
      this.listener.onReceivedAd(this, this.adView);
    }
  }
  
  public View requestAd(AdMediationAdapterListener paramAdMediationAdapterListener, Activity paramActivity, AdMediationNetworkParameters paramAdMediationNetworkParameters)
  {
    this.listener = paramAdMediationAdapterListener;
    String str = paramAdMediationNetworkParameters.getParameter("ad_unit_id");
    this.adView = new AdView(paramActivity, AdSize.SMART_BANNER, str);
    this.adView.setAdListener(this);
    AdRequest localAdRequest = new AdRequest();
    this.adView.loadAd(localAdRequest);
    return null;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.mediation.adapters.AdMobAdapter
 * JD-Core Version:    0.7.0.1
 */