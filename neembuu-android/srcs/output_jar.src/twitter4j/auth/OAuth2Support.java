package twitter4j.auth;

import twitter4j.TwitterException;

public abstract interface OAuth2Support
{
  public abstract OAuth2Token getOAuth2Token()
    throws TwitterException;
  
  public abstract void invalidateOAuth2Token()
    throws TwitterException;
  
  public abstract void setOAuth2Token(OAuth2Token paramOAuth2Token);
  
  public abstract void setOAuthConsumer(String paramString1, String paramString2);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.auth.OAuth2Support
 * JD-Core Version:    0.7.0.1
 */