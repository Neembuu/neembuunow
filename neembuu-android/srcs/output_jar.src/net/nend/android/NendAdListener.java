package net.nend.android;

import java.util.EventListener;

public abstract interface NendAdListener
  extends EventListener
{
  public abstract void onClick(NendAdView paramNendAdView);
  
  public abstract void onDismissScreen(NendAdView paramNendAdView);
  
  public abstract void onFailedToReceiveAd(NendAdView paramNendAdView);
  
  public abstract void onReceiveAd(NendAdView paramNendAdView);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendAdListener
 * JD-Core Version:    0.7.0.1
 */