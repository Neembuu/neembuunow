package twitter4j;

import java.io.Serializable;

public abstract interface IDs
  extends TwitterResponse, CursorSupport, Serializable
{
  public abstract long[] getIDs();
  
  public abstract long getNextCursor();
  
  public abstract long getPreviousCursor();
  
  public abstract boolean hasNext();
  
  public abstract boolean hasPrevious();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.IDs
 * JD-Core Version:    0.7.0.1
 */