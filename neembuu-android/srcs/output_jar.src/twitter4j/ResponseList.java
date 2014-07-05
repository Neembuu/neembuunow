package twitter4j;

import java.util.List;

public abstract interface ResponseList<T>
  extends TwitterResponse, List<T>
{
  public abstract RateLimitStatus getRateLimitStatus();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.ResponseList
 * JD-Core Version:    0.7.0.1
 */