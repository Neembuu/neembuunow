package jp.adlantis.android;

import android.content.Context;
import android.telephony.TelephonyManager;
import java.util.Locale;

public class AdServiceManager
{
  ADLAdService adlAdService;
  InternationalAdService internationalAdService;
  
  private String guessCountry(Context paramContext)
  {
    Object localObject;
    if (getTargetingParams().getCountry() != null) {
      localObject = getTargetingParams().getCountry();
    }
    for (;;)
    {
      return localObject;
      localObject = GreeApiDelegator.getUserCountry();
      if (localObject == null)
      {
        TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
        if (localTelephonyManager != null)
        {
          String str = localTelephonyManager.getSimCountryIso();
          if ((str != null) && (!"".equals(str)))
          {
            localObject = str;
          }
          else
          {
            localObject = localTelephonyManager.getNetworkCountryIso();
            if ((localObject != null) && (!"".equals(localObject))) {}
          }
        }
        else
        {
          Locale localLocale = Locale.getDefault();
          if (localLocale != null) {
            localObject = localLocale.getCountry();
          } else {
            localObject = "JP";
          }
        }
      }
    }
  }
  
  public void addService(AdService paramAdService)
  {
    if ((paramAdService instanceof ADLAdService)) {
      setAdlAdService((ADLAdService)paramAdService);
    }
    for (;;)
    {
      return;
      setInternationalAdService((InternationalAdService)paramAdService);
    }
  }
  
  public AdService getActiveAdService(Context paramContext)
  {
    Object localObject;
    if (!isGreeSdk()) {
      localObject = getAdlAdService();
    }
    for (;;)
    {
      return localObject;
      if (getInternationalAdService() == null)
      {
        localObject = getAdlAdService();
      }
      else
      {
        String str = guessCountry(paramContext);
        if (("JP".equalsIgnoreCase(str)) || ("JPN".equalsIgnoreCase(str))) {
          localObject = getAdlAdService();
        } else {
          localObject = getInternationalAdService();
        }
      }
    }
  }
  
  ADLAdService getAdlAdService()
  {
    return this.adlAdService;
  }
  
  InternationalAdService getInternationalAdService()
  {
    return this.internationalAdService;
  }
  
  AdService.TargetingParams getTargetingParams()
  {
    return AdManager.getInstance().getTargetingParam();
  }
  
  boolean isGreeSdk()
  {
    return AdManager.isGreeSdk();
  }
  
  void setAdlAdService(ADLAdService paramADLAdService)
  {
    this.adlAdService = paramADLAdService;
  }
  
  void setInternationalAdService(InternationalAdService paramInternationalAdService)
  {
    this.internationalAdService = paramInternationalAdService;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdServiceManager
 * JD-Core Version:    0.7.0.1
 */