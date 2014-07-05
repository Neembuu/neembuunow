package jp.adlantis.android.mediation.adapters;

import android.app.Activity;
import android.view.View;
import jp.adlantis.android.mediation.AdMediationAdapter;
import jp.adlantis.android.mediation.AdMediationAdapterListener;
import jp.adlantis.android.mediation.AdMediationNetworkParameters;
import net.nend.android.NendAdListener;
import net.nend.android.NendAdView;

public class NendAdapter
  implements AdMediationAdapter, NendAdListener
{
  private NendAdView adView = null;
  private AdMediationAdapterListener listener = null;
  
  public void destroy()
  {
    if (this.adView != null) {
      this.adView.setListener(null);
    }
    this.adView = null;
  }
  
  public void onClick(NendAdView paramNendAdView)
  {
    if (this.listener != null) {
      this.listener.onTouchAd(this);
    }
  }
  
  public void onFailedToReceiveAd(NendAdView paramNendAdView)
  {
    if (this.listener != null) {
      this.listener.onFailedToReceiveAd(this);
    }
  }
  
  public void onReceiveAd(NendAdView paramNendAdView)
  {
    if (this.listener != null)
    {
      this.listener.onReceivedAd(this, this.adView);
      this.adView.pause();
    }
  }
  
  public View requestAd(AdMediationAdapterListener paramAdMediationAdapterListener, Activity paramActivity, AdMediationNetworkParameters paramAdMediationNetworkParameters)
  {
    this.listener = paramAdMediationAdapterListener;
    String str1 = paramAdMediationNetworkParameters.getParameter("spot_id");
    String str2 = paramAdMediationNetworkParameters.getParameter("api_key");
    this.adView = new NendAdView(paramActivity, Integer.parseInt(str1), str2);
    this.adView.setListener(this);
    this.adView.loadAd();
    return null;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.mediation.adapters.NendAdapter
 * JD-Core Version:    0.7.0.1
 */