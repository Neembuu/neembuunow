package twitter4j;

import java.io.Serializable;
import java.util.Date;

public abstract interface SavedSearch
  extends Comparable<SavedSearch>, TwitterResponse, Serializable
{
  public abstract Date getCreatedAt();
  
  public abstract int getId();
  
  public abstract String getName();
  
  public abstract int getPosition();
  
  public abstract String getQuery();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.SavedSearch
 * JD-Core Version:    0.7.0.1
 */