package twitter4j.json;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import twitter4j.AccountTotals;
import twitter4j.Category;
import twitter4j.DirectMessage;
import twitter4j.IDs;
import twitter4j.Location;
import twitter4j.OEmbed;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Relationship;
import twitter4j.SavedSearch;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

public final class DataObjectFactory
{
  private static final Constructor<IDs> IDsConstructor;
  private static final Constructor<AccountTotals> accountTotalsConstructor;
  private static final Constructor<Category> categoryConstructor;
  private static final Constructor<DirectMessage> directMessageConstructor;
  private static final Constructor<Location> locationConstructor;
  private static final Constructor<OEmbed> oembedConstructor;
  private static final Constructor<Place> placeConstructor;
  private static final Method rateLimitStatusConstructor;
  private static final ThreadLocal<Map> rawJsonMap;
  private static final Constructor<Relationship> relationshipConstructor;
  private static final Constructor<SavedSearch> savedSearchConstructor;
  private static final Constructor<Status> statusConstructor;
  private static final Constructor<StatusDeletionNotice> statusDeletionNoticeConstructor;
  private static final Constructor<Trend> trendConstructor;
  private static final Constructor<Trends> trendsConstructor;
  private static final Constructor<User> userConstructor;
  private static final Constructor<UserList> userListConstructor;
  
  static
  {
    try
    {
      Class localClass1 = Class.forName("twitter4j.internal.json.StatusJSONImpl");
      Class[] arrayOfClass1 = new Class[1];
      arrayOfClass1[0] = JSONObject.class;
      statusConstructor = localClass1.getDeclaredConstructor(arrayOfClass1);
      statusConstructor.setAccessible(true);
      Class localClass2 = Class.forName("twitter4j.internal.json.UserJSONImpl");
      Class[] arrayOfClass2 = new Class[1];
      arrayOfClass2[0] = JSONObject.class;
      userConstructor = localClass2.getDeclaredConstructor(arrayOfClass2);
      userConstructor.setAccessible(true);
      Class localClass3 = Class.forName("twitter4j.internal.json.RelationshipJSONImpl");
      Class[] arrayOfClass3 = new Class[1];
      arrayOfClass3[0] = JSONObject.class;
      relationshipConstructor = localClass3.getDeclaredConstructor(arrayOfClass3);
      relationshipConstructor.setAccessible(true);
      Class localClass4 = Class.forName("twitter4j.internal.json.PlaceJSONImpl");
      Class[] arrayOfClass4 = new Class[1];
      arrayOfClass4[0] = JSONObject.class;
      placeConstructor = localClass4.getDeclaredConstructor(arrayOfClass4);
      placeConstructor.setAccessible(true);
      Class localClass5 = Class.forName("twitter4j.internal.json.SavedSearchJSONImpl");
      Class[] arrayOfClass5 = new Class[1];
      arrayOfClass5[0] = JSONObject.class;
      savedSearchConstructor = localClass5.getDeclaredConstructor(arrayOfClass5);
      savedSearchConstructor.setAccessible(true);
      Class localClass6 = Class.forName("twitter4j.internal.json.TrendJSONImpl");
      Class[] arrayOfClass6 = new Class[1];
      arrayOfClass6[0] = JSONObject.class;
      trendConstructor = localClass6.getDeclaredConstructor(arrayOfClass6);
      trendConstructor.setAccessible(true);
      Class localClass7 = Class.forName("twitter4j.internal.json.TrendsJSONImpl");
      Class[] arrayOfClass7 = new Class[1];
      arrayOfClass7[0] = String.class;
      trendsConstructor = localClass7.getDeclaredConstructor(arrayOfClass7);
      trendsConstructor.setAccessible(true);
      Class localClass8 = Class.forName("twitter4j.internal.json.IDsJSONImpl");
      Class[] arrayOfClass8 = new Class[1];
      arrayOfClass8[0] = String.class;
      IDsConstructor = localClass8.getDeclaredConstructor(arrayOfClass8);
      IDsConstructor.setAccessible(true);
      Class localClass9 = Class.forName("twitter4j.internal.json.RateLimitStatusJSONImpl");
      Class[] arrayOfClass9 = new Class[1];
      arrayOfClass9[0] = JSONObject.class;
      rateLimitStatusConstructor = localClass9.getDeclaredMethod("createRateLimitStatuses", arrayOfClass9);
      rateLimitStatusConstructor.setAccessible(true);
      Class localClass10 = Class.forName("twitter4j.internal.json.CategoryJSONImpl");
      Class[] arrayOfClass10 = new Class[1];
      arrayOfClass10[0] = JSONObject.class;
      categoryConstructor = localClass10.getDeclaredConstructor(arrayOfClass10);
      categoryConstructor.setAccessible(true);
      Class localClass11 = Class.forName("twitter4j.internal.json.DirectMessageJSONImpl");
      Class[] arrayOfClass11 = new Class[1];
      arrayOfClass11[0] = JSONObject.class;
      directMessageConstructor = localClass11.getDeclaredConstructor(arrayOfClass11);
      directMessageConstructor.setAccessible(true);
      Class localClass12 = Class.forName("twitter4j.internal.json.LocationJSONImpl");
      Class[] arrayOfClass12 = new Class[1];
      arrayOfClass12[0] = JSONObject.class;
      locationConstructor = localClass12.getDeclaredConstructor(arrayOfClass12);
      locationConstructor.setAccessible(true);
      Class localClass13 = Class.forName("twitter4j.internal.json.UserListJSONImpl");
      Class[] arrayOfClass13 = new Class[1];
      arrayOfClass13[0] = JSONObject.class;
      userListConstructor = localClass13.getDeclaredConstructor(arrayOfClass13);
      userListConstructor.setAccessible(true);
      Class localClass14 = Class.forName("twitter4j.StatusDeletionNoticeImpl");
      Class[] arrayOfClass14 = new Class[1];
      arrayOfClass14[0] = JSONObject.class;
      statusDeletionNoticeConstructor = localClass14.getDeclaredConstructor(arrayOfClass14);
      statusDeletionNoticeConstructor.setAccessible(true);
      Class localClass15 = Class.forName("twitter4j.internal.json.AccountTotalsJSONImpl");
      Class[] arrayOfClass15 = new Class[1];
      arrayOfClass15[0] = JSONObject.class;
      accountTotalsConstructor = localClass15.getDeclaredConstructor(arrayOfClass15);
      accountTotalsConstructor.setAccessible(true);
      Class localClass16 = Class.forName("twitter4j.internal.json.OEmbedJSONImpl");
      Class[] arrayOfClass16 = new Class[1];
      arrayOfClass16[0] = JSONObject.class;
      oembedConstructor = localClass16.getDeclaredConstructor(arrayOfClass16);
      oembedConstructor.setAccessible(true);
      rawJsonMap = new ThreadLocal()
      {
        protected Map initialValue()
        {
          return new HashMap();
        }
      };
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      throw new ExceptionInInitializerError(localNoSuchMethodException);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new ExceptionInInitializerError(localClassNotFoundException);
    }
  }
  
  private DataObjectFactory()
  {
    throw new AssertionError("not intended to be instantiated.");
  }
  
  static void clearThreadLocalMap()
  {
    ((Map)rawJsonMap.get()).clear();
  }
  
  public static AccountTotals createAccountTotals(String paramString)
    throws TwitterException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Constructor localConstructor = accountTotalsConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localJSONObject;
      AccountTotals localAccountTotals = (AccountTotals)localConstructor.newInstance(arrayOfObject);
      return localAccountTotals;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public static Category createCategory(String paramString)
    throws TwitterException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Constructor localConstructor = categoryConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localJSONObject;
      Category localCategory = (Category)localConstructor.newInstance(arrayOfObject);
      return localCategory;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public static DirectMessage createDirectMessage(String paramString)
    throws TwitterException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Constructor localConstructor = directMessageConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localJSONObject;
      DirectMessage localDirectMessage = (DirectMessage)localConstructor.newInstance(arrayOfObject);
      return localDirectMessage;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public static IDs createIDs(String paramString)
    throws TwitterException
  {
    try
    {
      Constructor localConstructor = IDsConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramString;
      IDs localIDs = (IDs)localConstructor.newInstance(arrayOfObject);
      return localIDs;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
  }
  
  public static Location createLocation(String paramString)
    throws TwitterException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Constructor localConstructor = locationConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localJSONObject;
      Location localLocation = (Location)localConstructor.newInstance(arrayOfObject);
      return localLocation;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public static OEmbed createOEmbed(String paramString)
    throws TwitterException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Constructor localConstructor = oembedConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localJSONObject;
      OEmbed localOEmbed = (OEmbed)localConstructor.newInstance(arrayOfObject);
      return localOEmbed;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public static Object createObject(String paramString)
    throws TwitterException
  {
    try
    {
      localObject1 = new JSONObject(paramString);
      JSONObjectType.Type localType = JSONObjectType.determine((JSONObject)localObject1);
      switch (2.$SwitchMap$twitter4j$json$JSONObjectType$Type[localType.ordinal()])
      {
      case 1: 
        Constructor localConstructor4 = directMessageConstructor;
        Object[] arrayOfObject4 = new Object[1];
        arrayOfObject4[0] = ((JSONObject)localObject1).getJSONObject("direct_message");
        localObject1 = registerJSONObject(localConstructor4.newInstance(arrayOfObject4), localObject1);
      }
    }
    catch (InstantiationException localInstantiationException)
    {
      Constructor localConstructor3;
      Object[] arrayOfObject3;
      Constructor localConstructor2;
      Object[] arrayOfObject2;
      Constructor localConstructor1;
      Object[] arrayOfObject1;
      Object localObject2;
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
    localConstructor3 = statusConstructor;
    arrayOfObject3 = new Object[1];
    arrayOfObject3[0] = localObject1;
    Object localObject1 = registerJSONObject(localConstructor3.newInstance(arrayOfObject3), localObject1);
    return localObject1;
    localConstructor2 = directMessageConstructor;
    arrayOfObject2 = new Object[1];
    arrayOfObject2[0] = ((JSONObject)localObject1).getJSONObject("direct_message");
    return registerJSONObject(localConstructor2.newInstance(arrayOfObject2), localObject1);
    localConstructor1 = statusDeletionNoticeConstructor;
    arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = ((JSONObject)localObject1).getJSONObject("delete").getJSONObject("status");
    localObject2 = registerJSONObject(localConstructor1.newInstance(arrayOfObject1), localObject1);
    localObject1 = localObject2;
    return localObject1;
  }
  
  public static Place createPlace(String paramString)
    throws TwitterException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Constructor localConstructor = placeConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localJSONObject;
      Place localPlace = (Place)localConstructor.newInstance(arrayOfObject);
      return localPlace;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public static Map<String, RateLimitStatus> createRateLimitStatus(String paramString)
    throws TwitterException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Method localMethod = rateLimitStatusConstructor;
      Class localClass = Class.forName("twitter4j.internal.json.RateLimitStatusJSONImpl");
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localJSONObject;
      Map localMap = (Map)localMethod.invoke(localClass, arrayOfObject);
      return localMap;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new TwitterException(localClassNotFoundException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public static Relationship createRelationship(String paramString)
    throws TwitterException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Constructor localConstructor = relationshipConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localJSONObject;
      Relationship localRelationship = (Relationship)localConstructor.newInstance(arrayOfObject);
      return localRelationship;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public static SavedSearch createSavedSearch(String paramString)
    throws TwitterException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Constructor localConstructor = savedSearchConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localJSONObject;
      SavedSearch localSavedSearch = (SavedSearch)localConstructor.newInstance(arrayOfObject);
      return localSavedSearch;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public static Status createStatus(String paramString)
    throws TwitterException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Constructor localConstructor = statusConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localJSONObject;
      Status localStatus = (Status)localConstructor.newInstance(arrayOfObject);
      return localStatus;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public static Trend createTrend(String paramString)
    throws TwitterException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Constructor localConstructor = trendConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localJSONObject;
      Trend localTrend = (Trend)localConstructor.newInstance(arrayOfObject);
      return localTrend;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public static Trends createTrends(String paramString)
    throws TwitterException
  {
    try
    {
      Constructor localConstructor = trendsConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramString;
      Trends localTrends = (Trends)localConstructor.newInstance(arrayOfObject);
      return localTrends;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new TwitterException(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new AssertionError(localInvocationTargetException);
    }
  }
  
  public static User createUser(String paramString)
    throws TwitterException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Constructor localConstructor = userConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localJSONObject;
      User localUser = (User)localConstructor.newInstance(arrayOfObject);
      return localUser;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public static UserList createUserList(String paramString)
    throws TwitterException
  {
    try
    {
      JSONObject localJSONObject = new JSONObject(paramString);
      Constructor localConstructor = userListConstructor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = localJSONObject;
      UserList localUserList = (UserList)localConstructor.newInstance(arrayOfObject);
      return localUserList;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new TwitterException(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new TwitterException(localInvocationTargetException);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public static String getRawJSON(Object paramObject)
  {
    Object localObject = ((Map)rawJsonMap.get()).get(paramObject);
    String str;
    if ((localObject instanceof String)) {
      str = (String)localObject;
    }
    for (;;)
    {
      return str;
      if (localObject != null) {
        str = localObject.toString();
      } else {
        str = null;
      }
    }
  }
  
  static <T> T registerJSONObject(T paramT, Object paramObject)
  {
    ((Map)rawJsonMap.get()).put(paramT, paramObject);
    return paramT;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.json.DataObjectFactory
 * JD-Core Version:    0.7.0.1
 */