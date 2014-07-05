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
import mediba.ad.sdk.android.openx.MasAdListener;
import mediba.ad.sdk.android.openx.MasAdView;

public class MedibaAdAdapter
  implements AdMediationAdapter
{
  private MasAdView adView = null;
  private ViewGroup adViewHolder = null;
  private AdMediationAdapterListener listener = null;
  
  public void destroy()
  {
    if (this.adView != null)
    {
      this.adView.setAdListener(null);
      this.adView.stop();
      this.adView.destroy();
    }
    this.adView = null;
    this.adViewHolder = null;
  }
  
  public View requestAd(AdMediationAdapterListener paramAdMediationAdapterListener, Activity paramActivity, AdMediationNetworkParameters paramAdMediationNetworkParameters)
  {
    this.listener = paramAdMediationAdapterListener;
    String str = paramAdMediationNetworkParameters.getParameter("sid");
    this.adView = new MasAdView(paramActivity);
    this.adView.setSid(str);
    this.adView.setAdListener(new MedibaAdListener(null));
    this.adView.start();
    this.adViewHolder = new RelativeLayout(paramActivity)
    {
      public boolean onInterceptTouchEvent(MotionEvent paramAnonymousMotionEvent)
      {
        if (paramAnonymousMotionEvent.getAction() == 1) {
          MedibaAdAdapter.this.listener.onTouchAd(MedibaAdAdapter.this);
        }
        return false;
      }
    };
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams.addRule(13);
    this.adViewHolder.addView(this.adView, localLayoutParams);
    return null;
  }
  
  private class MedibaAdListener
    extends MasAdListener
  {
    private MedibaAdListener() {}
    
    public void onFailedToReceiveAd()
    {
      if (MedibaAdAdapter.this.listener != null) {
        MedibaAdAdapter.this.listener.onFailedToReceiveAd(MedibaAdAdapter.this);
      }
    }
    
    public void onFailedToReceiveRefreshedAd()
    {
      if (MedibaAdAdapter.this.listener != null) {
        MedibaAdAdapter.this.listener.onFailedToReceiveAd(MedibaAdAdapter.this);
      }
    }
    
    public void onInternalBrowserClose()
    {
      if (MedibaAdAdapter.this.listener != null) {
        MedibaAdAdapter.this.listener.onDismissScreen(MedibaAdAdapter.this);
      }
    }
    
    public void onInternalBrowserOpen()
    {
      if (MedibaAdAdapter.this.listener != null) {
        MedibaAdAdapter.this.listener.onPresentScreen(MedibaAdAdapter.this);
      }
    }
    
    public void onReceiveAd()
    {
      if (MedibaAdAdapter.this.listener != null)
      {
        MedibaAdAdapter.this.listener.onReceivedAd(MedibaAdAdapter.this, MedibaAdAdapter.this.adViewHolder);
        MedibaAdAdapter.this.adView.stop();
      }
    }
    
    public void onReceiveRefreshedAd() {}
    
    public void onVideoPlayerEnd()
    {
      if (MedibaAdAdapter.this.listener != null) {
        MedibaAdAdapter.this.listener.onDismissScreen(MedibaAdAdapter.this);
      }
    }
    
    public void onVideoPlayerStart()
    {
      if (MedibaAdAdapter.this.listener != null) {
        MedibaAdAdapter.this.listener.onPresentScreen(MedibaAdAdapter.this);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.mediation.adapters.MedibaAdAdapter
 * JD-Core Version:    0.7.0.1
 */