package twitter4j;

import java.io.Serializable;

public abstract interface Trend
  extends Serializable
{
  public abstract String getName();
  
  public abstract String getQuery();
  
  public abstract String getURL();
  
  public abstract String getUrl();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.Trend
 * JD-Core Version:    0.7.0.1
 */