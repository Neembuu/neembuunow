package twitter4j.internal.http;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import twitter4j.auth.Authorization;

public final class HttpRequest
  implements Serializable
{
  private static final HttpParameter[] NULL_PARAMETERS = new HttpParameter[0];
  private static final long serialVersionUID = -3463594029098858381L;
  private final Authorization authorization;
  private final RequestMethod method;
  private final HttpParameter[] parameters;
  private Map<String, String> requestHeaders;
  private final String url;
  
  public HttpRequest(RequestMethod paramRequestMethod, String paramString, HttpParameter[] paramArrayOfHttpParameter, Authorization paramAuthorization, Map<String, String> paramMap)
  {
    this.method = paramRequestMethod;
    if ((paramRequestMethod != RequestMethod.POST) && (paramArrayOfHttpParameter != null) && (paramArrayOfHttpParameter.length != 0)) {
      this.url = (paramString + "?" + HttpParameter.encodeParameters(paramArrayOfHttpParameter));
    }
    for (this.parameters = NULL_PARAMETERS;; this.parameters = paramArrayOfHttpParameter)
    {
      this.authorization = paramAuthorization;
      this.requestHeaders = paramMap;
      return;
      this.url = paramString;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    HttpRequest localHttpRequest;
    do
    {
      for (;;)
      {
        return bool;
        if ((paramObject == null) || (getClass() != paramObject.getClass()))
        {
          bool = false;
        }
        else
        {
          localHttpRequest = (HttpRequest)paramObject;
          if (this.authorization != null)
          {
            if (this.authorization.equals(localHttpRequest.authorization)) {}
          }
          else {
            while (localHttpRequest.authorization != null)
            {
              bool = false;
              break;
            }
          }
          if (Arrays.equals(this.parameters, localHttpRequest.parameters)) {
            break;
          }
          bool = false;
        }
      }
      if (this.requestHeaders != null)
      {
        if (this.requestHeaders.equals(localHttpRequest.requestHeaders)) {}
      }
      else {
        while (localHttpRequest.requestHeaders != null)
        {
          bool = false;
          break;
        }
      }
      if (this.method != null)
      {
        if (this.method.equals(localHttpRequest.method)) {}
      }
      else {
        while (localHttpRequest.method != null)
        {
          bool = false;
          break;
        }
      }
      if (this.url == null) {
        break;
      }
    } while (this.url.equals(localHttpRequest.url));
    for (;;)
    {
      bool = false;
      break;
      if (localHttpRequest.url == null) {
        break;
      }
    }
  }
  
  public Authorization getAuthorization()
  {
    return this.authorization;
  }
  
  public RequestMethod getMethod()
  {
    return this.method;
  }
  
  public HttpParameter[] getParameters()
  {
    return this.parameters;
  }
  
  public Map<String, String> getRequestHeaders()
  {
    return this.requestHeaders;
  }
  
  public String getURL()
  {
    return this.url;
  }
  
  public int hashCode()
  {
    int i = 0;
    int j;
    int m;
    label38:
    int i1;
    label63:
    int i2;
    if (this.method != null)
    {
      j = this.method.hashCode();
      int k = j * 31;
      if (this.url == null) {
        break label126;
      }
      m = this.url.hashCode();
      int n = 31 * (k + m);
      if (this.parameters == null) {
        break label132;
      }
      i1 = Arrays.hashCode(this.parameters);
      i2 = 31 * (n + i1);
      if (this.authorization == null) {
        break label138;
      }
    }
    label132:
    label138:
    for (int i3 = this.authorization.hashCode();; i3 = 0)
    {
      int i4 = 31 * (i2 + i3);
      if (this.requestHeaders != null) {
        i = this.requestHeaders.hashCode();
      }
      return i4 + i;
      j = 0;
      break;
      label126:
      m = 0;
      break label38;
      i1 = 0;
      break label63;
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("HttpRequest{requestMethod=").append(this.method).append(", url='").append(this.url).append('\'').append(", postParams=");
    if (this.parameters == null) {}
    for (Object localObject = null;; localObject = Arrays.asList(this.parameters)) {
      return localObject + ", authentication=" + this.authorization + ", requestHeaders=" + this.requestHeaders + '}';
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.http.HttpRequest
 * JD-Core Version:    0.7.0.1
 */