package jp.adlantis.android;

import java.util.Vector;
import jp.adlantis.android.mediation.AdMediationNetworkParameters;
import jp.adlantis.android.model.OnChangeListener;
import jp.adlantis.android.model.SimpleObservable;

public class AdlantisAdsModel
  extends SimpleObservable<AdlantisAdsModel>
{
  private AdlantisAd[] _ads = null;
  private String errorMessage;
  private AdMediationNetworkParameters[] networkParameters = null;
  
  public AdlantisAdsModel() {}
  
  public AdlantisAdsModel(AdlantisAd[] paramArrayOfAdlantisAd)
  {
    this._ads = paramArrayOfAdlantisAd;
  }
  
  private AdlantisAd[] filteredAdsForOrientation(int paramInt)
  {
    Vector localVector = filteredAdsForOrientationVector(paramInt);
    AdlantisAd[] arrayOfAdlantisAd = new AdlantisAd[localVector.size()];
    localVector.copyInto(arrayOfAdlantisAd);
    return arrayOfAdlantisAd;
  }
  
  public int adCount()
  {
    if (this._ads == null) {}
    for (int i = 0;; i = this._ads.length) {
      return i;
    }
  }
  
  public int adCountForOrientation(int paramInt)
  {
    return filteredAdsForOrientation(paramInt).length;
  }
  
  /**
   * @deprecated
   */
  public AdlantisAd[] adsForOrientation(int paramInt)
  {
    if (paramInt == 2) {}
    for (;;)
    {
      try
      {
        AdlantisAd[] arrayOfAdlantisAd2 = filteredAdsForOrientation(2);
        localObject2 = arrayOfAdlantisAd2;
        return localObject2;
      }
      finally {}
      AdlantisAd[] arrayOfAdlantisAd1 = filteredAdsForOrientation(1);
      Object localObject2 = arrayOfAdlantisAd1;
    }
  }
  
  public void clearAds()
  {
    setAds(null);
  }
  
  /**
   * @deprecated
   */
  public Vector<AdlantisAd> filteredAdsForOrientationVector(int paramInt)
  {
    try
    {
      Vector localVector = new Vector();
      if (this._ads != null) {
        for (int i = 0; i < this._ads.length; i++) {
          if (this._ads[i].hasAdForOrientation(paramInt)) {
            localVector.addElement(this._ads[i]);
          }
        }
      }
      return localVector;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public AdlantisAd[] getAds()
  {
    return this._ads;
  }
  
  public String getErrorMessage()
  {
    return this.errorMessage;
  }
  
  public AdMediationNetworkParameters[] getNetworkParameters()
  {
    return this.networkParameters;
  }
  
  protected void notifyListeners()
  {
    notifyListeners(this);
  }
  
  public void setAds(AdlantisAd[] paramArrayOfAdlantisAd)
  {
    try
    {
      this._ads = paramArrayOfAdlantisAd;
      notifyListeners();
      return;
    }
    finally {}
  }
  
  public void setErrorMessage(String paramString)
  {
    this.errorMessage = paramString;
  }
  
  public void setNetworkParameters(AdMediationNetworkParameters[] paramArrayOfAdMediationNetworkParameters)
  {
    this.networkParameters = paramArrayOfAdMediationNetworkParameters;
  }
  
  public static abstract interface AdlantisAdsModelListener
    extends OnChangeListener<AdlantisAdsModel>
  {}
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdlantisAdsModel
 * JD-Core Version:    0.7.0.1
 */