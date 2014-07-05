package jp.adlantis.android;

public class GreeAdManager
  extends AdManager
{
  public String byline()
  {
    return "Ads by GREE";
  }
  
  AdNetworkConnection createConnection()
  {
    return new GreeAdConnection();
  }
  
  public String sdkDescription()
  {
    return "GREE Ad SDK";
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.GreeAdManager
 * JD-Core Version:    0.7.0.1
 */