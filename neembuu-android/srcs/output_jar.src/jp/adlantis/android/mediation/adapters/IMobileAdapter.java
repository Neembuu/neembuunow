package jp.adlantis.android.mediation.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import jp.adlantis.android.mediation.AdMediationAdapter;
import jp.adlantis.android.mediation.AdMediationAdapterListener;
import jp.adlantis.android.mediation.AdMediationNetworkParameters;
import jp.co.imobile.android.AdRequestResult;
import jp.co.imobile.android.AdView;
import jp.co.imobile.android.AdViewRequestListener;

public class IMobileAdapter
  implements AdMediationAdapter, AdViewRequestListener
{
  private AdView adView = null;
  private ViewGroup adViewHolder = null;
  private AdMediationAdapterListener listener = null;
  
  public void destroy()
  {
    if (this.adView != null)
    {
      this.adView.setOnRequestListener(null);
      this.adView.stop();
    }
    this.adView = null;
    this.adViewHolder = null;
  }
  
  public void onCompleted(AdRequestResult paramAdRequestResult, AdView paramAdView)
  {
    if (this.listener != null)
    {
      this.listener.onReceivedAd(this, this.adViewHolder);
      this.adView.stop();
    }
  }
  
  public void onFailed(AdRequestResult paramAdRequestResult, AdView paramAdView)
  {
    if (this.listener != null) {
      this.listener.onFailedToReceiveAd(this);
    }
  }
  
  public View requestAd(AdMediationAdapterListener paramAdMediationAdapterListener, Activity paramActivity, AdMediationNetworkParameters paramAdMediationNetworkParameters)
  {
    this.listener = paramAdMediationAdapterListener;
    String str1 = paramAdMediationNetworkParameters.getParameter("media_id");
    String str2 = paramAdMediationNetworkParameters.getParameter("spot_id");
    this.adView = AdView.create(paramActivity, Integer.parseInt(str1), Integer.parseInt(str2));
    this.adView.setOnRequestListener(this);
    this.adView.start();
    this.adViewHolder = new RelativeLayout(paramActivity)
    {
      public boolean onInterceptTouchEvent(MotionEvent paramAnonymousMotionEvent)
      {
        if (paramAnonymousMotionEvent.getAction() == 1) {
          IMobileAdapter.this.listener.onTouchAd(IMobileAdapter.this);
        }
        return false;
      }
    };
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams.addRule(13);
    this.adViewHolder.addView(this.adView, localLayoutParams);
    return this.adViewHolder;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.mediation.adapters.IMobileAdapter
 * JD-Core Version:    0.7.0.1
 */