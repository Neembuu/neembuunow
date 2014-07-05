package twitter4j.internal.http;

import twitter4j.TwitterException;

public abstract interface HttpClient
{
  public abstract HttpResponse request(HttpRequest paramHttpRequest)
    throws TwitterException;
  
  public abstract void shutdown();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.http.HttpClient
 * JD-Core Version:    0.7.0.1
 */