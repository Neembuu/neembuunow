package twitter4j.auth;

import java.io.Serializable;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
import twitter4j.internal.http.HttpResponse;

public final class RequestToken
  extends OAuthToken
  implements Serializable
{
  private static final long serialVersionUID = -8214365845469757952L;
  private final Configuration conf = ConfigurationContext.getInstance();
  private OAuthSupport oauth;
  
  public RequestToken(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }
  
  RequestToken(String paramString1, String paramString2, OAuthSupport paramOAuthSupport)
  {
    super(paramString1, paramString2);
    this.oauth = paramOAuthSupport;
  }
  
  RequestToken(HttpResponse paramHttpResponse, OAuthSupport paramOAuthSupport)
    throws TwitterException
  {
    super(paramHttpResponse);
    this.oauth = paramOAuthSupport;
  }
  
  public String getAuthenticationURL()
  {
    return this.conf.getOAuthAuthenticationURL() + "?oauth_token=" + getToken();
  }
  
  public String getAuthorizationURL()
  {
    return this.conf.getOAuthAuthorizationURL() + "?oauth_token=" + getToken();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.auth.RequestToken
 * JD-Core Version:    0.7.0.1
 */