package twitter4j.internal.http;

import twitter4j.TwitterException;
import twitter4j.auth.Authorization;

public final class HttpResponseEvent
{
  private HttpRequest request;
  private HttpResponse response;
  private TwitterException twitterException;
  
  HttpResponseEvent(HttpRequest paramHttpRequest, HttpResponse paramHttpResponse, TwitterException paramTwitterException)
  {
    this.request = paramHttpRequest;
    this.response = paramHttpResponse;
    this.twitterException = paramTwitterException;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    HttpResponseEvent localHttpResponseEvent;
    do
    {
      for (;;)
      {
        return bool;
        if ((paramObject != null) && (getClass() == paramObject.getClass())) {
          break;
        }
        bool = false;
      }
      localHttpResponseEvent = (HttpResponseEvent)paramObject;
      if (this.request != null)
      {
        if (this.request.equals(localHttpResponseEvent.request)) {}
      }
      else {
        while (localHttpResponseEvent.request != null)
        {
          bool = false;
          break;
        }
      }
      if (this.response == null) {
        break;
      }
    } while (this.response.equals(localHttpResponseEvent.response));
    for (;;)
    {
      bool = false;
      break;
      if (localHttpResponseEvent.response == null) {
        break;
      }
    }
  }
  
  public HttpRequest getRequest()
  {
    return this.request;
  }
  
  public HttpResponse getResponse()
  {
    return this.response;
  }
  
  public TwitterException getTwitterException()
  {
    return this.twitterException;
  }
  
  public int hashCode()
  {
    int i = 0;
    if (this.request != null) {}
    for (int j = this.request.hashCode();; j = 0)
    {
      int k = j * 31;
      if (this.response != null) {
        i = this.response.hashCode();
      }
      return k + i;
    }
  }
  
  public boolean isAuthenticated()
  {
    return this.request.getAuthorization().isEnabled();
  }
  
  public String toString()
  {
    return "HttpResponseEvent{request=" + this.request + ", response=" + this.response + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.http.HttpResponseEvent
 * JD-Core Version:    0.7.0.1
 */