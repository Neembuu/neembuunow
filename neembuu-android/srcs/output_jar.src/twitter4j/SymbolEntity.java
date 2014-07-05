package twitter4j;

import java.io.Serializable;

public abstract interface SymbolEntity
  extends TweetEntity, Serializable
{
  public abstract int getEnd();
  
  public abstract int getStart();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.SymbolEntity
 * JD-Core Version:    0.7.0.1
 */