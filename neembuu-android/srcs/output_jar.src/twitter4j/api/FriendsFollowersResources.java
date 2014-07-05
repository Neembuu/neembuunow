package twitter4j.api;

import twitter4j.Friendship;
import twitter4j.IDs;
import twitter4j.PagableResponseList;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

public abstract interface FriendsFollowersResources
{
  public abstract User createFriendship(long paramLong)
    throws TwitterException;
  
  public abstract User createFriendship(long paramLong, boolean paramBoolean)
    throws TwitterException;
  
  public abstract User createFriendship(String paramString)
    throws TwitterException;
  
  public abstract User createFriendship(String paramString, boolean paramBoolean)
    throws TwitterException;
  
  public abstract User destroyFriendship(long paramLong)
    throws TwitterException;
  
  public abstract User destroyFriendship(String paramString)
    throws TwitterException;
  
  public abstract IDs getFollowersIDs(long paramLong)
    throws TwitterException;
  
  public abstract IDs getFollowersIDs(long paramLong1, long paramLong2)
    throws TwitterException;
  
  public abstract IDs getFollowersIDs(String paramString, long paramLong)
    throws TwitterException;
  
  public abstract PagableResponseList<User> getFollowersList(long paramLong1, long paramLong2)
    throws TwitterException;
  
  public abstract PagableResponseList<User> getFollowersList(String paramString, long paramLong)
    throws TwitterException;
  
  public abstract IDs getFriendsIDs(long paramLong)
    throws TwitterException;
  
  public abstract IDs getFriendsIDs(long paramLong1, long paramLong2)
    throws TwitterException;
  
  public abstract IDs getFriendsIDs(String paramString, long paramLong)
    throws TwitterException;
  
  public abstract PagableResponseList<User> getFriendsList(long paramLong1, long paramLong2)
    throws TwitterException;
  
  public abstract PagableResponseList<User> getFriendsList(String paramString, long paramLong)
    throws TwitterException;
  
  public abstract IDs getIncomingFriendships(long paramLong)
    throws TwitterException;
  
  public abstract IDs getOutgoingFriendships(long paramLong)
    throws TwitterException;
  
  public abstract ResponseList<Friendship> lookupFriendships(long[] paramArrayOfLong)
    throws TwitterException;
  
  public abstract ResponseList<Friendship> lookupFriendships(String[] paramArrayOfString)
    throws TwitterException;
  
  public abstract Relationship showFriendship(long paramLong1, long paramLong2)
    throws TwitterException;
  
  public abstract Relationship showFriendship(String paramString1, String paramString2)
    throws TwitterException;
  
  public abstract Relationship updateFriendship(long paramLong, boolean paramBoolean1, boolean paramBoolean2)
    throws TwitterException;
  
  public abstract Relationship updateFriendship(String paramString, boolean paramBoolean1, boolean paramBoolean2)
    throws TwitterException;
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.api.FriendsFollowersResources
 * JD-Core Version:    0.7.0.1
 */