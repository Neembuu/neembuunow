package jp.co.asbit.pvstar.ads;

import android.app.Activity;
import android.util.Log;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.customevent.CustomEventBanner;
import com.google.ads.mediation.customevent.CustomEventBannerListener;
import jp.adlantis.android.AdRequestListener;
import jp.adlantis.android.AdRequestNotifier;
import jp.adlantis.android.AdlantisView;

public class Adlantis
  implements CustomEventBanner
{
  private static final String TAG = "Adlantis";
  private AdRequestListener adlantisRequestListener;
  private AdlantisView adlantisView;
  
  public void destroy()
  {
    if (this.adlantisView != null)
    {
      this.adlantisView.clearAds();
      if (this.adlantisRequestListener != null) {
        this.adlantisView.removeRequestListener(this.adlantisRequestListener);
      }
      this.adlantisView = null;
    }
  }
  
  public void requestBannerAd(final CustomEventBannerListener paramCustomEventBannerListener, Activity paramActivity, String paramString1, String paramString2, AdSize paramAdSize, MediationAdRequest paramMediationAdRequest, Object paramObject)
  {
    Log.d("Adlantis", "Adlantis");
    this.adlantisView = new AdlantisView(paramActivity.getApplicationContext());
    this.adlantisView.setPublisherID("MTkwNzI%3D%0A");
    this.adlantisView.setAdFetchInterval(0L);
    if (this.adlantisRequestListener == null) {
      this.adlantisRequestListener = new AdRequestListener()
      {
        public void onFailedToReceiveAd(AdRequestNotifier paramAnonymousAdRequestNotifier)
        {
          paramCustomEventBannerListener.onFailedToReceiveAd();
        }
        
        public void onReceiveAd(AdRequestNotifier paramAnonymousAdRequestNotifier)
        {
          paramCustomEventBannerListener.onReceivedAd(Adlantis.this.adlantisView);
        }
        
        public void onTouchAd(AdRequestNotifier paramAnonymousAdRequestNotifier)
        {
          paramCustomEventBannerListener.onClick();
          paramCustomEventBannerListener.onPresentScreen();
          paramCustomEventBannerListener.onLeaveApplication();
        }
      };
    }
    this.adlantisView.addRequestListener(this.adlantisRequestListener);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.ads.Adlantis
 * JD-Core Version:    0.7.0.1
 */