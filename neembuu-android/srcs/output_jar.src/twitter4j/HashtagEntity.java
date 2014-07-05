package twitter4j;

import java.io.Serializable;

public abstract interface HashtagEntity
  extends TweetEntity, Serializable
{
  public abstract int getEnd();
  
  public abstract int getStart();
  
  public abstract String getText();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.HashtagEntity
 * JD-Core Version:    0.7.0.1
 */