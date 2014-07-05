package jp.adlantis.android;

public abstract interface AdRequestListener
{
  public abstract void onFailedToReceiveAd(AdRequestNotifier paramAdRequestNotifier);
  
  public abstract void onReceiveAd(AdRequestNotifier paramAdRequestNotifier);
  
  public abstract void onTouchAd(AdRequestNotifier paramAdRequestNotifier);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdRequestListener
 * JD-Core Version:    0.7.0.1
 */