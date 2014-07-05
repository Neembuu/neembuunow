package twitter4j;

import java.io.Serializable;

public abstract interface Location
  extends Serializable
{
  public abstract String getCountryCode();
  
  public abstract String getCountryName();
  
  public abstract String getName();
  
  public abstract int getPlaceCode();
  
  public abstract String getPlaceName();
  
  public abstract String getURL();
  
  public abstract int getWoeid();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.Location
 * JD-Core Version:    0.7.0.1
 */