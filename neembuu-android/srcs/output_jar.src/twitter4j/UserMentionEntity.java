package twitter4j;

import java.io.Serializable;

public abstract interface UserMentionEntity
  extends TweetEntity, Serializable
{
  public abstract int getEnd();
  
  public abstract long getId();
  
  public abstract String getName();
  
  public abstract String getScreenName();
  
  public abstract int getStart();
  
  public abstract String getText();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.UserMentionEntity
 * JD-Core Version:    0.7.0.1
 */