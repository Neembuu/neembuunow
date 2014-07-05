package twitter4j;

import java.io.Serializable;

public class GeoLocation
  implements Serializable
{
  private static final long serialVersionUID = -4847567157651889935L;
  protected double latitude;
  protected double longitude;
  
  GeoLocation() {}
  
  public GeoLocation(double paramDouble1, double paramDouble2)
  {
    this.latitude = paramDouble1;
    this.longitude = paramDouble2;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    for (;;)
    {
      return bool;
      if (!(paramObject instanceof GeoLocation))
      {
        bool = false;
      }
      else
      {
        GeoLocation localGeoLocation = (GeoLocation)paramObject;
        if (Double.compare(localGeoLocation.getLatitude(), this.latitude) != 0) {
          bool = false;
        } else if (Double.compare(localGeoLocation.getLongitude(), this.longitude) != 0) {
          bool = false;
        }
      }
    }
  }
  
  public double getLatitude()
  {
    return this.latitude;
  }
  
  public double getLongitude()
  {
    return this.longitude;
  }
  
  public int hashCode()
  {
    long l1 = Double.doubleToLongBits(this.latitude);
    int i = (int)(l1 ^ l1 >>> 32);
    long l2 = Double.doubleToLongBits(this.longitude);
    return i * 31 + (int)(l2 ^ l2 >>> 32);
  }
  
  public String toString()
  {
    return "GeoLocation{latitude=" + this.latitude + ", longitude=" + this.longitude + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.GeoLocation
 * JD-Core Version:    0.7.0.1
 */