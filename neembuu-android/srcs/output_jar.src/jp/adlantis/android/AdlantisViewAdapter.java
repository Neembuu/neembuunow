package jp.adlantis.android;

import android.view.View;
import android.view.ViewParent;

public class AdlantisViewAdapter
  extends AdViewAdapter
{
  public AdlantisViewAdapter(View paramView)
  {
    super(paramView);
    ((AdlantisAdViewContainer)this.adView).addRequestListener(new AdRequestListener()
    {
      public void onFailedToReceiveAd(AdRequestNotifier paramAnonymousAdRequestNotifier)
      {
        AdlantisViewAdapter.this.listeners.notifyListenersFailedToReceiveAd(AdlantisViewAdapter.this.adlantisView());
      }
      
      public void onReceiveAd(AdRequestNotifier paramAnonymousAdRequestNotifier)
      {
        AdlantisViewAdapter.this.listeners.notifyListenersAdReceived(AdlantisViewAdapter.this.adlantisView());
      }
      
      public void onTouchAd(AdRequestNotifier paramAnonymousAdRequestNotifier)
      {
        AdlantisViewAdapter.this.listeners.notifyListenersAdTouched(AdlantisViewAdapter.this.adlantisView());
      }
    });
  }
  
  protected AdlantisView adlantisView()
  {
    ViewParent localViewParent = this.adView.getParent();
    if ((localViewParent instanceof AdlantisView)) {}
    for (AdlantisView localAdlantisView = (AdlantisView)localViewParent;; localAdlantisView = null) {
      return localAdlantisView;
    }
  }
  
  public void clearAds()
  {
    ((AdlantisAdViewContainer)this.adView).clearAds();
  }
  
  public void connect()
  {
    ((AdlantisAdViewContainer)this.adView).connect();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdlantisViewAdapter
 * JD-Core Version:    0.7.0.1
 */