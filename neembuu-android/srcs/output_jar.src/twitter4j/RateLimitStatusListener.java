package twitter4j;

public abstract interface RateLimitStatusListener
{
  public abstract void onRateLimitReached(RateLimitStatusEvent paramRateLimitStatusEvent);
  
  public abstract void onRateLimitStatus(RateLimitStatusEvent paramRateLimitStatusEvent);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.RateLimitStatusListener
 * JD-Core Version:    0.7.0.1
 */