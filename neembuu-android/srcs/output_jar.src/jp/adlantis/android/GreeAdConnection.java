package jp.adlantis.android;

import android.net.Uri.Builder;

public class GreeAdConnection
  extends AdNetworkConnection
{
  public GreeAdConnection()
  {
    this._host = "sp.ad.gap.adlantis.jp";
    this._conversionTagHost = "sp.conv.gap.adlantis.jp";
    this._conversionTagTestHost = "sp.gap.developer.gree.co.jp";
  }
  
  public Uri.Builder appendParameters(Uri.Builder paramBuilder)
  {
    String str1 = GreeApiDelegator.getUserId();
    String str2 = null;
    if (str1 != null) {
      str2 = GreeApiDelegator.getSha1DigestInString(str1);
    }
    if (str2 != null) {
      paramBuilder.appendQueryParameter("luid", "gree:" + str2);
    }
    paramBuilder.appendQueryParameter("partner", "gap");
    return paramBuilder;
  }
  
  public String publisherIDMetadataKey()
  {
    return "GAP_Publisher_ID";
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.GreeAdConnection
 * JD-Core Version:    0.7.0.1
 */