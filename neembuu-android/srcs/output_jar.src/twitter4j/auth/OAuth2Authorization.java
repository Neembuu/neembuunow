package twitter4j.auth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.BASE64Encoder;
import twitter4j.internal.http.HttpClientWrapper;
import twitter4j.internal.http.HttpParameter;
import twitter4j.internal.http.HttpRequest;
import twitter4j.internal.http.HttpResponse;

public class OAuth2Authorization
  implements Authorization, Serializable, OAuth2Support
{
  private static final long serialVersionUID = 4274784415515174129L;
  private final Configuration conf;
  private String consumerKey;
  private String consumerSecret;
  private HttpClientWrapper http;
  private OAuth2Token token;
  
  public OAuth2Authorization(Configuration paramConfiguration)
  {
    this.conf = paramConfiguration;
    setOAuthConsumer(paramConfiguration.getOAuthConsumerKey(), paramConfiguration.getOAuthConsumerSecret());
    this.http = new HttpClientWrapper(paramConfiguration);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = false;
    if ((paramObject == null) || (!(paramObject instanceof OAuth2Authorization))) {}
    for (;;)
    {
      return bool;
      OAuth2Authorization localOAuth2Authorization = (OAuth2Authorization)paramObject;
      if (this.consumerKey != null)
      {
        if (!this.consumerKey.equals(localOAuth2Authorization.consumerKey)) {
          continue;
        }
        label41:
        if (this.consumerSecret == null) {
          break label98;
        }
        if (!this.consumerSecret.equals(localOAuth2Authorization.consumerSecret)) {
          continue;
        }
        label62:
        if (this.token == null) {
          break label108;
        }
        if (!this.token.equals(localOAuth2Authorization.token)) {
          continue;
        }
      }
      label98:
      label108:
      while (localOAuth2Authorization.token == null)
      {
        bool = true;
        break;
        if (localOAuth2Authorization.consumerKey == null) {
          break label41;
        }
        break;
        if (localOAuth2Authorization.consumerSecret == null) {
          break label62;
        }
        break;
      }
    }
  }
  
  public String getAuthorizationHeader(HttpRequest paramHttpRequest)
  {
    Object localObject;
    if (this.token == null) {
      localObject = "";
    }
    try
    {
      String str2 = URLEncoder.encode(this.consumerKey, "UTF-8") + ":" + URLEncoder.encode(this.consumerSecret, "UTF-8");
      localObject = str2;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      label54:
      String str1;
      break label54;
    }
    for (str1 = "Basic " + BASE64Encoder.encode(((String)localObject).getBytes());; str1 = this.token.generateAuthorizationHeader()) {
      return str1;
    }
  }
  
  public OAuth2Token getOAuth2Token()
    throws TwitterException
  {
    if (this.token != null) {
      throw new IllegalStateException("OAuth 2 Bearer Token is already available.");
    }
    HttpParameter[] arrayOfHttpParameter = new HttpParameter[1];
    arrayOfHttpParameter[0] = new HttpParameter("grant_type", "client_credentials");
    HttpResponse localHttpResponse = this.http.post(this.conf.getOAuth2TokenURL(), arrayOfHttpParameter, this);
    if (localHttpResponse.getStatusCode() != 200) {
      throw new TwitterException("Obtaining OAuth 2 Bearer Token failed.", localHttpResponse);
    }
    this.token = new OAuth2Token(localHttpResponse);
    return this.token;
  }
  
  public int hashCode()
  {
    int i = 0;
    int j;
    int k;
    if (this.consumerKey != null)
    {
      j = this.consumerKey.hashCode();
      k = j * 31;
      if (this.consumerSecret == null) {
        break label72;
      }
    }
    label72:
    for (int m = this.consumerSecret.hashCode();; m = 0)
    {
      int n = 31 * (k + m);
      if (this.token != null) {
        i = this.token.hashCode();
      }
      return n + i;
      j = 0;
      break;
    }
  }
  
  public void invalidateOAuth2Token()
    throws TwitterException
  {
    if (this.token == null) {
      throw new IllegalStateException("OAuth 2 Bearer Token is not available.");
    }
    HttpParameter[] arrayOfHttpParameter = new HttpParameter[1];
    arrayOfHttpParameter[0] = new HttpParameter("access_token", this.token.getAccessToken());
    OAuth2Token localOAuth2Token = this.token;
    try
    {
      this.token = null;
      HttpResponse localHttpResponse = this.http.post(this.conf.getOAuth2InvalidateTokenURL(), arrayOfHttpParameter, this);
      if (localHttpResponse.getStatusCode() != 200) {
        throw new TwitterException("Invalidating OAuth 2 Bearer Token failed.", localHttpResponse);
      }
    }
    finally
    {
      if (0 == 0) {
        this.token = localOAuth2Token;
      }
    }
    if (1 == 0) {
      this.token = localOAuth2Token;
    }
  }
  
  public boolean isEnabled()
  {
    if (this.token != null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public void setOAuth2Token(OAuth2Token paramOAuth2Token)
  {
    this.token = paramOAuth2Token;
  }
  
  public void setOAuthConsumer(String paramString1, String paramString2)
  {
    if (paramString1 != null)
    {
      this.consumerKey = paramString1;
      if (paramString2 == null) {
        break label25;
      }
    }
    for (;;)
    {
      this.consumerSecret = paramString2;
      return;
      paramString1 = "";
      break;
      label25:
      paramString2 = "";
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("OAuth2Authorization{consumerKey='").append(this.consumerKey).append('\'').append(", consumerSecret='******************************************'").append(", token=");
    if (this.token == null) {}
    for (String str = "null";; str = this.token.toString()) {
      return str + '}';
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.auth.OAuth2Authorization
 * JD-Core Version:    0.7.0.1
 */