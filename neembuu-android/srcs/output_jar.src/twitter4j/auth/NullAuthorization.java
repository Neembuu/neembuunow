package twitter4j.auth;

import java.io.ObjectStreamException;
import java.io.Serializable;
import twitter4j.internal.http.HttpRequest;

public class NullAuthorization
  implements Authorization, Serializable
{
  private static NullAuthorization SINGLETON = new NullAuthorization();
  private static final long serialVersionUID = -8748173338942663960L;
  
  public static NullAuthorization getInstance()
  {
    return SINGLETON;
  }
  
  private Object readResolve()
    throws ObjectStreamException
  {
    return SINGLETON;
  }
  
  public boolean equals(Object paramObject)
  {
    if (SINGLETON == paramObject) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public String getAuthorizationHeader(HttpRequest paramHttpRequest)
  {
    return null;
  }
  
  public boolean isEnabled()
  {
    return false;
  }
  
  public String toString()
  {
    return "NullAuthentication{SINGLETON}";
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.auth.NullAuthorization
 * JD-Core Version:    0.7.0.1
 */