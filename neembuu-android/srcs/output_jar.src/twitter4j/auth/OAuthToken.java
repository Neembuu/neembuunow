package twitter4j.auth;

import java.io.Serializable;
import javax.crypto.spec.SecretKeySpec;
import twitter4j.TwitterException;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.util.z_T4JInternalStringUtil;

abstract class OAuthToken
  implements Serializable
{
  private static final long serialVersionUID = 3891133932519746686L;
  String[] responseStr = null;
  private transient SecretKeySpec secretKeySpec;
  private String token;
  private String tokenSecret;
  
  OAuthToken(String paramString)
  {
    this.responseStr = z_T4JInternalStringUtil.split(paramString, "&");
    this.tokenSecret = getParameter("oauth_token_secret");
    this.token = getParameter("oauth_token");
  }
  
  public OAuthToken(String paramString1, String paramString2)
  {
    this.token = paramString1;
    this.tokenSecret = paramString2;
  }
  
  OAuthToken(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    this(paramHttpResponse.asString());
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    for (;;)
    {
      return bool;
      if (!(paramObject instanceof OAuthToken))
      {
        bool = false;
      }
      else
      {
        OAuthToken localOAuthToken = (OAuthToken)paramObject;
        if (!this.token.equals(localOAuthToken.token)) {
          bool = false;
        } else if (!this.tokenSecret.equals(localOAuthToken.tokenSecret)) {
          bool = false;
        }
      }
    }
  }
  
  public String getParameter(String paramString)
  {
    String str1 = null;
    String[] arrayOfString = this.responseStr;
    int i = arrayOfString.length;
    for (int j = 0;; j++) {
      if (j < i)
      {
        String str2 = arrayOfString[j];
        if (str2.startsWith(paramString + '=')) {
          str1 = z_T4JInternalStringUtil.split(str2, "=")[1].trim();
        }
      }
      else
      {
        return str1;
      }
    }
  }
  
  SecretKeySpec getSecretKeySpec()
  {
    return this.secretKeySpec;
  }
  
  public String getToken()
  {
    return this.token;
  }
  
  public String getTokenSecret()
  {
    return this.tokenSecret;
  }
  
  public int hashCode()
  {
    return 31 * this.token.hashCode() + this.tokenSecret.hashCode();
  }
  
  void setSecretKeySpec(SecretKeySpec paramSecretKeySpec)
  {
    this.secretKeySpec = paramSecretKeySpec;
  }
  
  public String toString()
  {
    return "OAuthToken{token='" + this.token + '\'' + ", tokenSecret='" + this.tokenSecret + '\'' + ", secretKeySpec=" + this.secretKeySpec + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.auth.OAuthToken
 * JD-Core Version:    0.7.0.1
 */