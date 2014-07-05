package jp.adlantis.android;

import android.app.Activity;
import android.widget.RelativeLayout.LayoutParams;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.customevent.CustomEventBanner;
import com.google.ads.mediation.customevent.CustomEventBannerListener;

public class AdlantisCustomEventBanner
  implements CustomEventBanner, AdRequestListener
{
  private AdlantisView adView;
  private CustomEventBannerListener bannerListener;
  
  public void destroy() {}
  
  public void onFailedToReceiveAd(AdRequestNotifier paramAdRequestNotifier)
  {
    this.bannerListener.onFailedToReceiveAd();
  }
  
  public void onReceiveAd(AdRequestNotifier paramAdRequestNotifier)
  {
    this.bannerListener.onReceivedAd(this.adView);
  }
  
  public void onTouchAd(AdRequestNotifier paramAdRequestNotifier)
  {
    this.bannerListener.onClick();
  }
  
  public void requestBannerAd(CustomEventBannerListener paramCustomEventBannerListener, Activity paramActivity, String paramString1, String paramString2, AdSize paramAdSize, MediationAdRequest paramMediationAdRequest, Object paramObject)
  {
    this.bannerListener = paramCustomEventBannerListener;
    this.adView = new AdlantisView(paramActivity);
    this.adView.setAdFetchInterval(0L);
    this.adView.addRequestListener(this);
    this.adView.setLayoutParams(new RelativeLayout.LayoutParams(-2, paramAdSize.getHeightInPixels(paramActivity)));
    this.adView.setPublisherID(paramString2);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdlantisCustomEventBanner
 * JD-Core Version:    0.7.0.1
 */