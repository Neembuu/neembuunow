package twitter4j.auth;

import java.io.Serializable;
import twitter4j.internal.http.HttpRequest;

public abstract interface Authorization
  extends Serializable
{
  public abstract String getAuthorizationHeader(HttpRequest paramHttpRequest);
  
  public abstract boolean isEnabled();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.auth.Authorization
 * JD-Core Version:    0.7.0.1
 */