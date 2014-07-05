package twitter4j;

import java.io.Serializable;
import java.util.List;

public abstract interface QueryResult
  extends TwitterResponse, Serializable
{
  public abstract double getCompletedIn();
  
  public abstract int getCount();
  
  public abstract long getMaxId();
  
  public abstract String getQuery();
  
  public abstract String getRefreshURL();
  
  public abstract String getRefreshUrl();
  
  public abstract long getSinceId();
  
  public abstract List<Status> getTweets();
  
  public abstract boolean hasNext();
  
  public abstract Query nextQuery();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.QueryResult
 * JD-Core Version:    0.7.0.1
 */