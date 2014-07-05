package twitter4j;

import java.io.Serializable;

public abstract interface Friendship
  extends Serializable
{
  public abstract long getId();
  
  public abstract String getName();
  
  public abstract String getScreenName();
  
  public abstract boolean isFollowedBy();
  
  public abstract boolean isFollowing();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.Friendship
 * JD-Core Version:    0.7.0.1
 */