package jp.adlantis.android;

import android.view.View;

public class AdViewAdapter
  implements AdRequestNotifier
{
  protected View adView;
  protected AdRequestListeners listeners = new AdRequestListeners();
  
  AdViewAdapter(View paramView)
  {
    this.adView = paramView;
  }
  
  public View adView()
  {
    return this.adView;
  }
  
  public void addRequestListener(AdRequestListener paramAdRequestListener)
  {
    this.listeners.addRequestListener(paramAdRequestListener);
  }
  
  public void clearAds() {}
  
  public void connect() {}
  
  public void removeRequestListener(AdRequestListener paramAdRequestListener)
  {
    this.listeners.removeRequestListener(paramAdRequestListener);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdViewAdapter
 * JD-Core Version:    0.7.0.1
 */