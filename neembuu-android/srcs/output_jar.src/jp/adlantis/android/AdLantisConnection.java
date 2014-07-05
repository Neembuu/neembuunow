package jp.adlantis.android;

public class AdLantisConnection
  extends AdNetworkConnection
{
  public AdLantisConnection()
  {
    this._host = "sp.ad.adlantis.jp";
    this._conversionTagHost = "sp.conv.adlantis.jp";
    this._conversionTagTestHost = "sp.www.adlantis.jp";
  }
  
  public String publisherIDMetadataKey()
  {
    return "Adlantis_Publisher_ID";
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdLantisConnection
 * JD-Core Version:    0.7.0.1
 */