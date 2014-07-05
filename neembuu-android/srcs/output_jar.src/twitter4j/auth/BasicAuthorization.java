package twitter4j.auth;

import java.io.Serializable;
import twitter4j.internal.http.BASE64Encoder;
import twitter4j.internal.http.HttpRequest;

public class BasicAuthorization
  implements Authorization, Serializable
{
  private static final long serialVersionUID = -5861104407848415060L;
  private String basic;
  private String password;
  private String userId;
  
  public BasicAuthorization(String paramString1, String paramString2)
  {
    this.userId = paramString1;
    this.password = paramString2;
    this.basic = encodeBasicAuthenticationString();
  }
  
  private String encodeBasicAuthenticationString()
  {
    if ((this.userId != null) && (this.password != null)) {}
    for (String str = "Basic " + BASE64Encoder.encode(new StringBuilder().append(this.userId).append(":").append(this.password).toString().getBytes());; str = null) {
      return str;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool;
    if (this == paramObject) {
      bool = true;
    }
    for (;;)
    {
      return bool;
      if (!(paramObject instanceof BasicAuthorization))
      {
        bool = false;
      }
      else
      {
        BasicAuthorization localBasicAuthorization = (BasicAuthorization)paramObject;
        bool = this.basic.equals(localBasicAuthorization.basic);
      }
    }
  }
  
  public String getAuthorizationHeader(HttpRequest paramHttpRequest)
  {
    return this.basic;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public int hashCode()
  {
    return this.basic.hashCode();
  }
  
  public boolean isEnabled()
  {
    return true;
  }
  
  public String toString()
  {
    return "BasicAuthorization{userId='" + this.userId + '\'' + ", password='**********''" + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.auth.BasicAuthorization
 * JD-Core Version:    0.7.0.1
 */