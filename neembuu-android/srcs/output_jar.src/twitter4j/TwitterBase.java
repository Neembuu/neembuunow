package twitter4j;

import twitter4j.auth.Authorization;
import twitter4j.conf.Configuration;

public abstract interface TwitterBase
{
  public abstract void addRateLimitStatusListener(RateLimitStatusListener paramRateLimitStatusListener);
  
  public abstract Authorization getAuthorization();
  
  public abstract Configuration getConfiguration();
  
  public abstract long getId()
    throws TwitterException, IllegalStateException;
  
  public abstract String getScreenName()
    throws TwitterException, IllegalStateException;
  
  public abstract void shutdown();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.TwitterBase
 * JD-Core Version:    0.7.0.1
 */