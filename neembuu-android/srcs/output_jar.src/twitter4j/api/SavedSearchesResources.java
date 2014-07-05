package twitter4j.api;

import twitter4j.ResponseList;
import twitter4j.SavedSearch;
import twitter4j.TwitterException;

public abstract interface SavedSearchesResources
{
  public abstract SavedSearch createSavedSearch(String paramString)
    throws TwitterException;
  
  public abstract SavedSearch destroySavedSearch(int paramInt)
    throws TwitterException;
  
  public abstract ResponseList<SavedSearch> getSavedSearches()
    throws TwitterException;
  
  public abstract SavedSearch showSavedSearch(int paramInt)
    throws TwitterException;
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.api.SavedSearchesResources
 * JD-Core Version:    0.7.0.1
 */