package jp.co.asbit.pvstar.ads;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import com.amoad.AMoAdView;
import com.amoad.AdCallback;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.customevent.CustomEventBanner;
import com.google.ads.mediation.customevent.CustomEventBannerListener;

public class AMoAd
  implements CustomEventBanner
{
  private static final String TAG = "AMoAd";
  private AMoAdView amoad;
  
  public void destroy()
  {
    if (this.amoad != null)
    {
      this.amoad.setCallback(null);
      this.amoad = null;
    }
  }
  
  public void requestBannerAd(final CustomEventBannerListener paramCustomEventBannerListener, Activity paramActivity, String paramString1, String paramString2, AdSize paramAdSize, MediationAdRequest paramMediationAdRequest, Object paramObject)
  {
    Log.d("AMoAd", "AMoAd");
    if (this.amoad == null)
    {
      this.amoad = new AMoAdView(paramActivity.getApplicationContext());
      this.amoad.setSid("62056d310111552ca466d4e2d0d56f76cb1d482c6a9942678ace3273e2363ea8");
      this.amoad.stopRotation();
      this.amoad.setCallback(new AdCallback()
      {
        public void didFailToReceiveAdWithError()
        {
          paramCustomEventBannerListener.onFailedToReceiveAd();
        }
        
        public void didReceiveAd()
        {
          paramCustomEventBannerListener.onReceivedAd(AMoAd.this.amoad);
        }
        
        public void didReceiveEmptyAd()
        {
          paramCustomEventBannerListener.onFailedToReceiveAd();
        }
      });
    }
    ViewGroup localViewGroup = (ViewGroup)this.amoad.getParent();
    if (localViewGroup != null) {
      localViewGroup.removeView(this.amoad);
    }
    this.amoad.requestFreshAd();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.ads.AMoAd
 * JD-Core Version:    0.7.0.1
 */