package twitter4j;

import java.util.EventObject;

public final class RateLimitStatusEvent
  extends EventObject
{
  private static final long serialVersionUID = -2332507741769177298L;
  private boolean isAccountRateLimitStatus;
  private RateLimitStatus rateLimitStatus;
  
  RateLimitStatusEvent(Object paramObject, RateLimitStatus paramRateLimitStatus, boolean paramBoolean)
  {
    super(paramObject);
    this.rateLimitStatus = paramRateLimitStatus;
    this.isAccountRateLimitStatus = paramBoolean;
  }
  
  public RateLimitStatus getRateLimitStatus()
  {
    return this.rateLimitStatus;
  }
  
  public boolean isAccountRateLimitStatus()
  {
    return this.isAccountRateLimitStatus;
  }
  
  public boolean isIPRateLimitStatus()
  {
    if (!this.isAccountRateLimitStatus) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.RateLimitStatusEvent
 * JD-Core Version:    0.7.0.1
 */