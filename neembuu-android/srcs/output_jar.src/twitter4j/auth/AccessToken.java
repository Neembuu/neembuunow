package twitter4j.auth;

import java.io.Serializable;
import twitter4j.TwitterException;
import twitter4j.internal.http.HttpResponse;

public class AccessToken
  extends OAuthToken
  implements Serializable
{
  private static final long serialVersionUID = -8344528374458826291L;
  private String screenName;
  private long userId = -1L;
  
  AccessToken(String paramString)
  {
    super(paramString);
    this.screenName = getParameter("screen_name");
    String str = getParameter("user_id");
    if (str != null) {
      this.userId = Long.parseLong(str);
    }
  }
  
  public AccessToken(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
    int i = paramString1.indexOf("-");
    String str;
    if (i != -1) {
      str = paramString1.substring(0, i);
    }
    try
    {
      this.userId = Long.parseLong(str);
      label43:
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      break label43;
    }
  }
  
  public AccessToken(String paramString1, String paramString2, long paramLong)
  {
    super(paramString1, paramString2);
    this.userId = paramLong;
  }
  
  AccessToken(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    this(paramHttpResponse.asString());
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    AccessToken localAccessToken;
    do
    {
      for (;;)
      {
        return bool;
        if ((paramObject == null) || (getClass() != paramObject.getClass()))
        {
          bool = false;
        }
        else if (!super.equals(paramObject))
        {
          bool = false;
        }
        else
        {
          localAccessToken = (AccessToken)paramObject;
          if (this.userId == localAccessToken.userId) {
            break;
          }
          bool = false;
        }
      }
      if (this.screenName == null) {
        break;
      }
    } while (this.screenName.equals(localAccessToken.screenName));
    for (;;)
    {
      bool = false;
      break;
      if (localAccessToken.screenName == null) {
        break;
      }
    }
  }
  
  public String getScreenName()
  {
    return this.screenName;
  }
  
  public long getUserId()
  {
    return this.userId;
  }
  
  public int hashCode()
  {
    int i = 31 * super.hashCode();
    if (this.screenName != null) {}
    for (int j = this.screenName.hashCode();; j = 0) {
      return 31 * (i + j) + (int)(this.userId ^ this.userId >>> 32);
    }
  }
  
  public String toString()
  {
    return "AccessToken{screenName='" + this.screenName + '\'' + ", userId=" + this.userId + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.auth.AccessToken
 * JD-Core Version:    0.7.0.1
 */