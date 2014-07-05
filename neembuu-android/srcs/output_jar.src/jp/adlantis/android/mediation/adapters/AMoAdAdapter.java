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
import jp.co.cyberagent.AMoAdView;
import jp.co.cyberagent.AdCallback;

public class AMoAdAdapter
  implements AdMediationAdapter, AdCallback
{
  private AMoAdView adView = null;
  private ViewGroup adViewHolder = null;
  private AdMediationAdapterListener listener = null;
  
  public void destroy()
  {
    if (this.adView != null) {
      this.adView.setCallback(null);
    }
    this.adView = null;
    this.adViewHolder = null;
  }
  
  public void didFailToReceiveAdWithError()
  {
    if (this.listener != null) {
      this.listener.onFailedToReceiveAd(this);
    }
  }
  
  public void didReceiveAd()
  {
    if (this.listener != null) {
      this.listener.onReceivedAd(this, this.adViewHolder);
    }
  }
  
  public void didReceiveEmptyAd()
  {
    if (this.listener != null) {
      this.listener.onFailedToReceiveAd(this);
    }
  }
  
  public View requestAd(AdMediationAdapterListener paramAdMediationAdapterListener, Activity paramActivity, AdMediationNetworkParameters paramAdMediationNetworkParameters)
  {
    this.listener = paramAdMediationAdapterListener;
    String str = paramAdMediationNetworkParameters.getParameter("ad_sid");
    this.adView = new AMoAdView(paramActivity);
    this.adView.setCallback(this);
    this.adView.setContentSizeIdentifier(2);
    this.adView.setSid(str);
    this.adView.stopRotation();
    this.adView.requestFreshAd();
    this.adViewHolder = new RelativeLayout(paramActivity)
    {
      public boolean onInterceptTouchEvent(MotionEvent paramAnonymousMotionEvent)
      {
        if (paramAnonymousMotionEvent.getAction() == 0) {
          AMoAdAdapter.this.listener.onTouchAd(AMoAdAdapter.this);
        }
        return false;
      }
    };
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams.addRule(13);
    this.adViewHolder.addView(this.adView, localLayoutParams);
    return null;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.mediation.adapters.AMoAdAdapter
 * JD-Core Version:    0.7.0.1
 */