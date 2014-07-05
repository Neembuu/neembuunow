package jp.adlantis.android;

import android.content.Context;
import android.location.Location;
import android.view.View;
import java.util.Locale;

public abstract class AdService
{
  protected TargetingParams targetingParam = null;
  
  public abstract AdViewAdapter adViewAdapter(Context paramContext);
  
  public abstract View createAdView(Context paramContext);
  
  public TargetingParams getTargetingParam()
  {
    return this.targetingParam;
  }
  
  public abstract void pause();
  
  public abstract void resume();
  
  public void setTargetingParam(TargetingParams paramTargetingParams)
  {
    this.targetingParam = paramTargetingParams;
  }
  
  public static final class TargetingParams
  {
    private String country;
    private String keywords;
    private Location location;
    
    public String getCountry()
    {
      return this.country;
    }
    
    public String getKeywords()
    {
      return this.keywords;
    }
    
    public String getLocale()
    {
      Locale localLocale = Locale.getDefault();
      if (localLocale != null) {}
      for (String str = localLocale.getLanguage();; str = "") {
        return str;
      }
    }
    
    public Location getLocation()
    {
      return this.location;
    }
    
    public void setCountry(String paramString)
    {
      if (paramString != null) {}
      for (String str = paramString.toLowerCase();; str = null)
      {
        this.country = str;
        return;
      }
    }
    
    public void setKeywords(String paramString)
    {
      this.keywords = paramString;
    }
    
    public void setLocation(Location paramLocation)
    {
      this.location = paramLocation;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdService
 * JD-Core Version:    0.7.0.1
 */