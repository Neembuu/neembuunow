package twitter4j.internal.json;

import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

final class LocationJSONImpl
  implements Location
{
  private static final long serialVersionUID = 7095092358530897222L;
  private final String countryCode;
  private final String countryName;
  private final String name;
  private final int placeCode;
  private final String placeName;
  private final String url;
  private final int woeid;
  
  /* Error */
  LocationJSONImpl(JSONObject paramJSONObject)
    throws TwitterException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 28	java/lang/Object:<init>	()V
    //   4: aload_0
    //   5: ldc 29
    //   7: aload_1
    //   8: invokestatic 35	twitter4j/internal/json/z_T4JInternalParseUtil:getInt	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)I
    //   11: putfield 37	twitter4j/internal/json/LocationJSONImpl:woeid	I
    //   14: aload_0
    //   15: ldc 39
    //   17: aload_1
    //   18: invokestatic 43	twitter4j/internal/json/z_T4JInternalParseUtil:getUnescapedString	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)Ljava/lang/String;
    //   21: putfield 45	twitter4j/internal/json/LocationJSONImpl:countryName	Ljava/lang/String;
    //   24: aload_0
    //   25: ldc 46
    //   27: aload_1
    //   28: invokestatic 49	twitter4j/internal/json/z_T4JInternalParseUtil:getRawString	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)Ljava/lang/String;
    //   31: putfield 51	twitter4j/internal/json/LocationJSONImpl:countryCode	Ljava/lang/String;
    //   34: aload_1
    //   35: ldc 53
    //   37: invokevirtual 59	twitter4j/internal/org/json/JSONObject:isNull	(Ljava/lang/String;)Z
    //   40: ifne +51 -> 91
    //   43: aload_1
    //   44: ldc 53
    //   46: invokevirtual 63	twitter4j/internal/org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Ltwitter4j/internal/org/json/JSONObject;
    //   49: astore_3
    //   50: aload_0
    //   51: ldc 64
    //   53: aload_3
    //   54: invokestatic 43	twitter4j/internal/json/z_T4JInternalParseUtil:getUnescapedString	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)Ljava/lang/String;
    //   57: putfield 66	twitter4j/internal/json/LocationJSONImpl:placeName	Ljava/lang/String;
    //   60: aload_0
    //   61: ldc 68
    //   63: aload_3
    //   64: invokestatic 35	twitter4j/internal/json/z_T4JInternalParseUtil:getInt	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)I
    //   67: putfield 70	twitter4j/internal/json/LocationJSONImpl:placeCode	I
    //   70: aload_0
    //   71: ldc 64
    //   73: aload_1
    //   74: invokestatic 43	twitter4j/internal/json/z_T4JInternalParseUtil:getUnescapedString	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)Ljava/lang/String;
    //   77: putfield 72	twitter4j/internal/json/LocationJSONImpl:name	Ljava/lang/String;
    //   80: aload_0
    //   81: ldc 73
    //   83: aload_1
    //   84: invokestatic 43	twitter4j/internal/json/z_T4JInternalParseUtil:getUnescapedString	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)Ljava/lang/String;
    //   87: putfield 75	twitter4j/internal/json/LocationJSONImpl:url	Ljava/lang/String;
    //   90: return
    //   91: aload_0
    //   92: aconst_null
    //   93: putfield 66	twitter4j/internal/json/LocationJSONImpl:placeName	Ljava/lang/String;
    //   96: aload_0
    //   97: bipush 255
    //   99: putfield 70	twitter4j/internal/json/LocationJSONImpl:placeCode	I
    //   102: goto -32 -> 70
    //   105: astore_2
    //   106: new 23	twitter4j/TwitterException
    //   109: dup
    //   110: aload_2
    //   111: invokespecial 78	twitter4j/TwitterException:<init>	(Ljava/lang/Exception;)V
    //   114: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	115	0	this	LocationJSONImpl
    //   0	115	1	paramJSONObject	JSONObject
    //   105	6	2	localJSONException	JSONException
    //   49	15	3	localJSONObject	JSONObject
    // Exception table:
    //   from	to	target	type
    //   4	102	105	twitter4j/internal/org/json/JSONException
  }
  
  static ResponseList<Location> createLocationList(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    if (paramConfiguration.isJSONStoreEnabled()) {
      DataObjectFactoryUtil.clearThreadLocalMap();
    }
    return createLocationList(paramHttpResponse.asJSONArray(), paramConfiguration.isJSONStoreEnabled());
  }
  
  static ResponseList<Location> createLocationList(JSONArray paramJSONArray, boolean paramBoolean)
    throws TwitterException
  {
    for (;;)
    {
      int j;
      try
      {
        int i = paramJSONArray.length();
        ResponseListImpl localResponseListImpl = new ResponseListImpl(i, null);
        j = 0;
        if (j < i)
        {
          JSONObject localJSONObject = paramJSONArray.getJSONObject(j);
          LocationJSONImpl localLocationJSONImpl = new LocationJSONImpl(localJSONObject);
          localResponseListImpl.add(localLocationJSONImpl);
          if (paramBoolean) {
            DataObjectFactoryUtil.registerJSONObject(localLocationJSONImpl, localJSONObject);
          }
        }
        else
        {
          if (paramBoolean) {
            DataObjectFactoryUtil.registerJSONObject(localResponseListImpl, paramJSONArray);
          }
          return localResponseListImpl;
        }
      }
      catch (JSONException localJSONException)
      {
        throw new TwitterException(localJSONException);
      }
      catch (TwitterException localTwitterException)
      {
        throw localTwitterException;
      }
      j++;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    for (;;)
    {
      return bool;
      if (!(paramObject instanceof LocationJSONImpl))
      {
        bool = false;
      }
      else
      {
        LocationJSONImpl localLocationJSONImpl = (LocationJSONImpl)paramObject;
        if (this.woeid != localLocationJSONImpl.woeid) {
          bool = false;
        }
      }
    }
  }
  
  public String getCountryCode()
  {
    return this.countryCode;
  }
  
  public String getCountryName()
  {
    return this.countryName;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public int getPlaceCode()
  {
    return this.placeCode;
  }
  
  public String getPlaceName()
  {
    return this.placeName;
  }
  
  public String getURL()
  {
    return this.url;
  }
  
  public int getWoeid()
  {
    return this.woeid;
  }
  
  public int hashCode()
  {
    return this.woeid;
  }
  
  public String toString()
  {
    return "LocationJSONImpl{woeid=" + this.woeid + ", countryName='" + this.countryName + '\'' + ", countryCode='" + this.countryCode + '\'' + ", placeName='" + this.placeName + '\'' + ", placeCode='" + this.placeCode + '\'' + ", name='" + this.name + '\'' + ", url='" + this.url + '\'' + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.LocationJSONImpl
 * JD-Core Version:    0.7.0.1
 */