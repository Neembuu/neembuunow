package twitter4j.auth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import twitter4j.TwitterException;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.json.z_T4JInternalParseUtil;
import twitter4j.internal.org.json.JSONObject;

public class OAuth2Token
  implements Serializable
{
  private static final long serialVersionUID = 358222644448390610L;
  private String accessToken;
  private String tokenType;
  
  public OAuth2Token(String paramString1, String paramString2)
  {
    this.tokenType = paramString1;
    this.accessToken = paramString2;
  }
  
  OAuth2Token(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    JSONObject localJSONObject = paramHttpResponse.asJSONObject();
    this.tokenType = z_T4JInternalParseUtil.getRawString("token_type", localJSONObject);
    try
    {
      this.accessToken = URLDecoder.decode(z_T4JInternalParseUtil.getRawString("access_token", localJSONObject), "UTF-8");
      label34:
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      break label34;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = false;
    if ((paramObject == null) || (!(paramObject instanceof OAuth2Token))) {}
    for (;;)
    {
      return bool;
      OAuth2Token localOAuth2Token = (OAuth2Token)paramObject;
      if (this.tokenType != null)
      {
        if (!this.tokenType.equals(localOAuth2Token.tokenType)) {
          continue;
        }
        label41:
        if (this.accessToken == null) {
          break label77;
        }
        if (!this.accessToken.equals(localOAuth2Token.accessToken)) {
          continue;
        }
      }
      label77:
      while (localOAuth2Token.accessToken == null)
      {
        bool = true;
        break;
        if (localOAuth2Token.tokenType == null) {
          break label41;
        }
        break;
      }
    }
  }
  
  String generateAuthorizationHeader()
  {
    Object localObject = "";
    try
    {
      String str = URLEncoder.encode(this.accessToken, "UTF-8");
      localObject = str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      label15:
      break label15;
    }
    return "Bearer " + (String)localObject;
  }
  
  public String getAccessToken()
  {
    return this.accessToken;
  }
  
  public String getTokenType()
  {
    return this.tokenType;
  }
  
  public int hashCode()
  {
    int i = 0;
    if (this.tokenType != null) {}
    for (int j = this.tokenType.hashCode();; j = 0)
    {
      int k = j * 31;
      if (this.accessToken != null) {
        i = this.accessToken.hashCode();
      }
      return k + i;
    }
  }
  
  public String toString()
  {
    return "OAuth2Token{tokenType='" + this.tokenType + '\'' + ", accessToken='" + this.accessToken + '\'' + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.auth.OAuth2Token
 * JD-Core Version:    0.7.0.1
 */