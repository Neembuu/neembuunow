package twitter4j;

import java.io.Serializable;

public abstract interface OEmbed
  extends TwitterResponse, Serializable
{
  public abstract String getAuthorName();
  
  public abstract String getAuthorURL();
  
  public abstract long getCacheAge();
  
  public abstract String getHtml();
  
  public abstract String getURL();
  
  public abstract String getVersion();
  
  public abstract int getWidth();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.OEmbed
 * JD-Core Version:    0.7.0.1
 */