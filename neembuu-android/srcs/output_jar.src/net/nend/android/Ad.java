package net.nend.android;

abstract interface Ad
  extends AdParameter
{
  public abstract void cancelRequest();
  
  public abstract String getUid();
  
  public abstract boolean isRequestable();
  
  public abstract void removeListener();
  
  public abstract boolean requestAd();
  
  public abstract void setListener(AdListener paramAdListener);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.Ad
 * JD-Core Version:    0.7.0.1
 */