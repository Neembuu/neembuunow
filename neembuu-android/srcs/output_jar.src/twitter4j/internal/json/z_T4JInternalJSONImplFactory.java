package twitter4j.internal.json;

import java.util.Map;
import twitter4j.AccountSettings;
import twitter4j.AccountTotals;
import twitter4j.Category;
import twitter4j.DirectMessage;
import twitter4j.Friendship;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.IDs;
import twitter4j.Location;
import twitter4j.OEmbed;
import twitter4j.PagableResponseList;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.SavedSearch;
import twitter4j.SimilarPlaces;
import twitter4j.Status;
import twitter4j.Trends;
import twitter4j.TwitterAPIConfiguration;
import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserMentionEntity;
import twitter4j.api.HelpResources.Language;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;
import twitter4j.internal.util.z_T4JInternalStringUtil;

public class z_T4JInternalJSONImplFactory
  implements z_T4JInternalFactory
{
  private static final long serialVersionUID = 5217622295050444866L;
  private Configuration conf;
  
  public z_T4JInternalJSONImplFactory(Configuration paramConfiguration)
  {
    this.conf = paramConfiguration;
  }
  
  static GeoLocation[][] coordinatesAsGeoLocationArray(JSONArray paramJSONArray)
    throws TwitterException
  {
    try
    {
      GeoLocation[][] arrayOfGeoLocation; = new GeoLocation[paramJSONArray.length()][];
      for (int i = 0; i < paramJSONArray.length(); i++)
      {
        JSONArray localJSONArray1 = paramJSONArray.getJSONArray(i);
        arrayOfGeoLocation;[i] = new GeoLocation[localJSONArray1.length()];
        for (int j = 0; j < localJSONArray1.length(); j++)
        {
          JSONArray localJSONArray2 = localJSONArray1.getJSONArray(j);
          arrayOfGeoLocation;[i][j] = new GeoLocation(localJSONArray2.getDouble(1), localJSONArray2.getDouble(0));
        }
      }
      return arrayOfGeoLocation;;
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  static GeoLocation createGeoLocation(JSONObject paramJSONObject)
    throws TwitterException
  {
    for (;;)
    {
      try
      {
        if (!paramJSONObject.isNull("coordinates"))
        {
          String str = paramJSONObject.getJSONObject("coordinates").getString("coordinates");
          String[] arrayOfString = z_T4JInternalStringUtil.split(str.substring(1, -1 + str.length()), ",");
          localGeoLocation = new GeoLocation(Double.parseDouble(arrayOfString[1]), Double.parseDouble(arrayOfString[0]));
          return localGeoLocation;
        }
      }
      catch (JSONException localJSONException)
      {
        throw new TwitterException(localJSONException);
      }
      GeoLocation localGeoLocation = null;
    }
  }
  
  public static HashtagEntity createHashtagEntity(int paramInt1, int paramInt2, String paramString)
  {
    return new HashtagEntityJSONImpl(paramInt1, paramInt2, paramString);
  }
  
  public static RateLimitStatus createRateLimitStatusFromResponseHeader(HttpResponse paramHttpResponse)
  {
    return RateLimitStatusJSONImpl.createFromResponseHeader(paramHttpResponse);
  }
  
  public static URLEntity createUrlEntity(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3)
  {
    return new URLEntityJSONImpl(paramInt1, paramInt2, paramString1, paramString2, paramString3);
  }
  
  public static UserMentionEntity createUserMentionEntity(int paramInt1, int paramInt2, String paramString1, String paramString2, long paramLong)
  {
    return new UserMentionEntityJSONImpl(paramInt1, paramInt2, paramString1, paramString2, paramLong);
  }
  
  public UserList createAUserList(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return new UserListJSONImpl(paramHttpResponse, this.conf);
  }
  
  public UserList createAUserList(JSONObject paramJSONObject)
    throws TwitterException
  {
    return new UserListJSONImpl(paramJSONObject);
  }
  
  public AccountSettings createAccountSettings(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return new AccountSettingsJSONImpl(paramHttpResponse, this.conf);
  }
  
  public AccountTotals createAccountTotals(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return new AccountTotalsJSONImpl(paramHttpResponse, this.conf);
  }
  
  public ResponseList<Category> createCategoryList(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return CategoryJSONImpl.createCategoriesList(paramHttpResponse, this.conf);
  }
  
  public DirectMessage createDirectMessage(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return new DirectMessageJSONImpl(paramHttpResponse, this.conf);
  }
  
  public DirectMessage createDirectMessage(JSONObject paramJSONObject)
    throws TwitterException
  {
    return new DirectMessageJSONImpl(paramJSONObject);
  }
  
  public ResponseList<DirectMessage> createDirectMessageList(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return DirectMessageJSONImpl.createDirectMessageList(paramHttpResponse, this.conf);
  }
  
  public <T> ResponseList<T> createEmptyResponseList()
  {
    return new ResponseListImpl(0, null);
  }
  
  public ResponseList<Friendship> createFriendshipList(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return FriendshipJSONImpl.createFriendshipList(paramHttpResponse, this.conf);
  }
  
  public IDs createIDs(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return new IDsJSONImpl(paramHttpResponse, this.conf);
  }
  
  public ResponseList<HelpResources.Language> createLanguageList(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return LanguageJSONImpl.createLanguageList(paramHttpResponse, this.conf);
  }
  
  public ResponseList<Location> createLocationList(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return LocationJSONImpl.createLocationList(paramHttpResponse, this.conf);
  }
  
  public OEmbed createOEmbed(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return new OEmbedJSONImpl(paramHttpResponse, this.conf);
  }
  
  public PagableResponseList<User> createPagableUserList(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return UserJSONImpl.createPagableUserList(paramHttpResponse, this.conf);
  }
  
  public PagableResponseList<UserList> createPagableUserListList(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return UserListJSONImpl.createPagableUserListList(paramHttpResponse, this.conf);
  }
  
  public Place createPlace(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return new PlaceJSONImpl(paramHttpResponse, this.conf);
  }
  
  public ResponseList<Place> createPlaceList(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    try
    {
      ResponseList localResponseList = PlaceJSONImpl.createPlaceList(paramHttpResponse, this.conf);
      localObject = localResponseList;
    }
    catch (TwitterException localTwitterException)
    {
      Object localObject;
      while (localTwitterException.getStatusCode() == 404) {
        localObject = new ResponseListImpl(0, null);
      }
      throw localTwitterException;
    }
    return localObject;
  }
  
  public QueryResult createQueryResult(HttpResponse paramHttpResponse, Query paramQuery)
    throws TwitterException
  {
    try
    {
      localQueryResultJSONImpl = new QueryResultJSONImpl(paramHttpResponse, this.conf);
      return localQueryResultJSONImpl;
    }
    catch (TwitterException localTwitterException)
    {
      QueryResultJSONImpl localQueryResultJSONImpl;
      while (404 == localTwitterException.getStatusCode()) {
        localQueryResultJSONImpl = new QueryResultJSONImpl(paramQuery);
      }
      throw localTwitterException;
    }
  }
  
  public Map<String, RateLimitStatus> createRateLimitStatuses(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return RateLimitStatusJSONImpl.createRateLimitStatuses(paramHttpResponse, this.conf);
  }
  
  public Relationship createRelationship(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return new RelationshipJSONImpl(paramHttpResponse, this.conf);
  }
  
  public SavedSearch createSavedSearch(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return new SavedSearchJSONImpl(paramHttpResponse, this.conf);
  }
  
  public ResponseList<SavedSearch> createSavedSearchList(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return SavedSearchJSONImpl.createSavedSearchList(paramHttpResponse, this.conf);
  }
  
  public SimilarPlaces createSimilarPlaces(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return SimilarPlacesImpl.createSimilarPlaces(paramHttpResponse, this.conf);
  }
  
  public Status createStatus(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return new StatusJSONImpl(paramHttpResponse, this.conf);
  }
  
  public Status createStatus(JSONObject paramJSONObject)
    throws TwitterException
  {
    return new StatusJSONImpl(paramJSONObject);
  }
  
  public ResponseList<Status> createStatusList(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return StatusJSONImpl.createStatusList(paramHttpResponse, this.conf);
  }
  
  public Trends createTrends(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return new TrendsJSONImpl(paramHttpResponse, this.conf);
  }
  
  public TwitterAPIConfiguration createTwitterAPIConfiguration(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return new TwitterAPIConfigurationJSONImpl(paramHttpResponse, this.conf);
  }
  
  public User createUser(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return new UserJSONImpl(paramHttpResponse, this.conf);
  }
  
  public User createUser(JSONObject paramJSONObject)
    throws TwitterException
  {
    return new UserJSONImpl(paramJSONObject);
  }
  
  public ResponseList<User> createUserList(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return UserJSONImpl.createUserList(paramHttpResponse, this.conf);
  }
  
  public ResponseList<User> createUserListFromJSONArray(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return UserJSONImpl.createUserList(paramHttpResponse.asJSONArray(), paramHttpResponse, this.conf);
  }
  
  public ResponseList<User> createUserListFromJSONArray_Users(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    try
    {
      ResponseList localResponseList = UserJSONImpl.createUserList(paramHttpResponse.asJSONObject().getJSONArray("users"), paramHttpResponse, this.conf);
      return localResponseList;
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public ResponseList<UserList> createUserListList(HttpResponse paramHttpResponse)
    throws TwitterException
  {
    return UserListJSONImpl.createUserListList(paramHttpResponse, this.conf);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    z_T4JInternalJSONImplFactory localz_T4JInternalJSONImplFactory;
    do
    {
      for (;;)
      {
        return bool;
        if ((paramObject instanceof z_T4JInternalJSONImplFactory)) {
          break;
        }
        bool = false;
      }
      localz_T4JInternalJSONImplFactory = (z_T4JInternalJSONImplFactory)paramObject;
      if (this.conf == null) {
        break;
      }
    } while (this.conf.equals(localz_T4JInternalJSONImplFactory.conf));
    for (;;)
    {
      bool = false;
      break;
      if (localz_T4JInternalJSONImplFactory.conf == null) {
        break;
      }
    }
  }
  
  public int hashCode()
  {
    if (this.conf != null) {}
    for (int i = this.conf.hashCode();; i = 0) {
      return i;
    }
  }
  
  public String toString()
  {
    return "JSONImplFactory{conf=" + this.conf + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.z_T4JInternalJSONImplFactory
 * JD-Core Version:    0.7.0.1
 */