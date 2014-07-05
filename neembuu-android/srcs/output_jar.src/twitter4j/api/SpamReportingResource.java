package twitter4j.api;

import twitter4j.TwitterException;
import twitter4j.User;

public abstract interface SpamReportingResource
{
  public abstract User reportSpam(long paramLong)
    throws TwitterException;
  
  public abstract User reportSpam(String paramString)
    throws TwitterException;
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.api.SpamReportingResource
 * JD-Core Version:    0.7.0.1
 */