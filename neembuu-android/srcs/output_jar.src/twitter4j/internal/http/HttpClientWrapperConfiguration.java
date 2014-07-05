package twitter4j.internal.http;

import java.util.Map;

public abstract interface HttpClientWrapperConfiguration
  extends HttpClientConfiguration
{
  public abstract Map<String, String> getRequestHeaders();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.http.HttpClientWrapperConfiguration
 * JD-Core Version:    0.7.0.1
 */