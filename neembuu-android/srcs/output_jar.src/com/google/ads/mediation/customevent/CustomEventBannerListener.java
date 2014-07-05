package com.google.ads.mediation.customevent;

import android.view.View;

public abstract interface CustomEventBannerListener
  extends CustomEventListener
{
  public abstract void onClick();
  
  public abstract void onReceivedAd(View paramView);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.mediation.customevent.CustomEventBannerListener
 * JD-Core Version:    0.7.0.1
 */