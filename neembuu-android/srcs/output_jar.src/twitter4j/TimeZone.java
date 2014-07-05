package twitter4j;

import java.io.Serializable;

public abstract interface TimeZone
  extends Serializable
{
  public abstract String getName();
  
  public abstract String tzinfoName();
  
  public abstract int utcOffset();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.TimeZone
 * JD-Core Version:    0.7.0.1
 */