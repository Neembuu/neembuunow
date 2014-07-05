package twitter4j.api;

import twitter4j.Category;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

public abstract interface SuggestedUsersResources
{
  public abstract ResponseList<User> getMemberSuggestions(String paramString)
    throws TwitterException;
  
  public abstract ResponseList<Category> getSuggestedUserCategories()
    throws TwitterException;
  
  public abstract ResponseList<User> getUserSuggestions(String paramString)
    throws TwitterException;
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.api.SuggestedUsersResources
 * JD-Core Version:    0.7.0.1
 */