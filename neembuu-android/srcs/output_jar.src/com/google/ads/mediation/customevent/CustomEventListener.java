package com.google.ads.mediation.customevent;

public abstract interface CustomEventListener
{
  public abstract void onDismissScreen();
  
  public abstract void onFailedToReceiveAd();
  
  public abstract void onLeaveApplication();
  
  public abstract void onPresentScreen();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.mediation.customevent.CustomEventListener
 * JD-Core Version:    0.7.0.1
 */