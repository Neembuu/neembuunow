package twitter4j;

import java.io.Serializable;

public abstract interface URLEntity
  extends TweetEntity, Serializable
{
  public abstract String getDisplayURL();
  
  public abstract int getEnd();
  
  public abstract String getExpandedURL();
  
  public abstract int getStart();
  
  public abstract String getText();
  
  public abstract String getURL();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.URLEntity
 * JD-Core Version:    0.7.0.1
 */