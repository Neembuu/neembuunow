package twitter4j.api;

import twitter4j.GeoLocation;
import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Trends;
import twitter4j.TwitterException;

public abstract interface TrendsResources
{
  public abstract ResponseList<Location> getAvailableTrends()
    throws TwitterException;
  
  public abstract ResponseList<Location> getAvailableTrends(GeoLocation paramGeoLocation)
    throws TwitterException;
  
  public abstract ResponseList<Location> getClosestTrends(GeoLocation paramGeoLocation)
    throws TwitterException;
  
  public abstract Trends getLocationTrends(int paramInt)
    throws TwitterException;
  
  public abstract Trends getPlaceTrends(int paramInt)
    throws TwitterException;
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.api.TrendsResources
 * JD-Core Version:    0.7.0.1
 */