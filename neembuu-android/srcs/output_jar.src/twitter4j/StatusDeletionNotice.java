package twitter4j;

import java.io.Serializable;

public abstract interface StatusDeletionNotice
  extends Comparable<StatusDeletionNotice>, Serializable
{
  public abstract long getStatusId();
  
  public abstract long getUserId();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.StatusDeletionNotice
 * JD-Core Version:    0.7.0.1
 */