package twitter4j.api;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.TwitterException;

public abstract interface SearchResource
{
  public abstract QueryResult search(Query paramQuery)
    throws TwitterException;
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.api.SearchResource
 * JD-Core Version:    0.7.0.1
 */