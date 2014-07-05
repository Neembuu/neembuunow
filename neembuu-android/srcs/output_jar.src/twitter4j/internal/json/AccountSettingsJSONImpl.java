package twitter4j.internal.json;

import java.io.Serializable;
import twitter4j.AccountSettings;
import twitter4j.Location;
import twitter4j.TimeZone;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONObject;

class AccountSettingsJSONImpl
  extends TwitterResponseImpl
  implements AccountSettings, Serializable
{
  private static final long serialVersionUID = 7983363611306383416L;
  private final boolean ALWAYS_USE_HTTPS;
  private final boolean DISCOVERABLE_BY_EMAIL;
  private final boolean GEO_ENABLED;
  private final String LANGUAGE;
  private final String SCREEN_NAME;
  private final String SLEEP_END_TIME;
  private final String SLEEP_START_TIME;
  private final boolean SLEEP_TIME_ENABLED;
  private final TimeZone TIMEZONE;
  private final Location[] TREND_LOCATION;
  
  AccountSettingsJSONImpl(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    this(paramHttpResponse, paramHttpResponse.asJSONObject());
    if (paramConfiguration.isJSONStoreEnabled())
    {
      DataObjectFactoryUtil.clearThreadLocalMap();
      DataObjectFactoryUtil.registerJSONObject(this, paramHttpResponse.asJSONObject());
    }
  }
  
  /* Error */
  private AccountSettingsJSONImpl(HttpResponse paramHttpResponse, JSONObject paramJSONObject)
    throws TwitterException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokespecial 60	twitter4j/internal/json/TwitterResponseImpl:<init>	(Ltwitter4j/internal/http/HttpResponse;)V
    //   5: aload_2
    //   6: ldc 62
    //   8: invokevirtual 68	twitter4j/internal/org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Ltwitter4j/internal/org/json/JSONObject;
    //   11: astore 4
    //   13: aload_0
    //   14: ldc 70
    //   16: aload 4
    //   18: invokestatic 76	twitter4j/internal/json/z_T4JInternalParseUtil:getBoolean	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)Z
    //   21: putfield 78	twitter4j/internal/json/AccountSettingsJSONImpl:SLEEP_TIME_ENABLED	Z
    //   24: aload_0
    //   25: aload 4
    //   27: ldc 80
    //   29: invokevirtual 84	twitter4j/internal/org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   32: putfield 86	twitter4j/internal/json/AccountSettingsJSONImpl:SLEEP_START_TIME	Ljava/lang/String;
    //   35: aload_0
    //   36: aload 4
    //   38: ldc 88
    //   40: invokevirtual 84	twitter4j/internal/org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   43: putfield 90	twitter4j/internal/json/AccountSettingsJSONImpl:SLEEP_END_TIME	Ljava/lang/String;
    //   46: aload_2
    //   47: ldc 92
    //   49: invokevirtual 96	twitter4j/internal/org/json/JSONObject:isNull	(Ljava/lang/String;)Z
    //   52: ifeq +79 -> 131
    //   55: aload_0
    //   56: iconst_0
    //   57: anewarray 98	twitter4j/Location
    //   60: putfield 100	twitter4j/internal/json/AccountSettingsJSONImpl:TREND_LOCATION	[Ltwitter4j/Location;
    //   63: aload_0
    //   64: ldc 102
    //   66: aload_2
    //   67: invokestatic 76	twitter4j/internal/json/z_T4JInternalParseUtil:getBoolean	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)Z
    //   70: putfield 104	twitter4j/internal/json/AccountSettingsJSONImpl:GEO_ENABLED	Z
    //   73: aload_0
    //   74: aload_2
    //   75: ldc 106
    //   77: invokevirtual 84	twitter4j/internal/org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   80: putfield 108	twitter4j/internal/json/AccountSettingsJSONImpl:LANGUAGE	Ljava/lang/String;
    //   83: aload_0
    //   84: ldc 110
    //   86: aload_2
    //   87: invokestatic 76	twitter4j/internal/json/z_T4JInternalParseUtil:getBoolean	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)Z
    //   90: putfield 112	twitter4j/internal/json/AccountSettingsJSONImpl:ALWAYS_USE_HTTPS	Z
    //   93: aload_0
    //   94: ldc 114
    //   96: aload_2
    //   97: invokestatic 76	twitter4j/internal/json/z_T4JInternalParseUtil:getBoolean	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)Z
    //   100: putfield 116	twitter4j/internal/json/AccountSettingsJSONImpl:DISCOVERABLE_BY_EMAIL	Z
    //   103: aload_0
    //   104: new 118	twitter4j/internal/json/TimeZoneJSONImpl
    //   107: dup
    //   108: aload_2
    //   109: ldc 120
    //   111: invokevirtual 68	twitter4j/internal/org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Ltwitter4j/internal/org/json/JSONObject;
    //   114: invokespecial 123	twitter4j/internal/json/TimeZoneJSONImpl:<init>	(Ltwitter4j/internal/org/json/JSONObject;)V
    //   117: putfield 125	twitter4j/internal/json/AccountSettingsJSONImpl:TIMEZONE	Ltwitter4j/TimeZone;
    //   120: aload_0
    //   121: aload_2
    //   122: ldc 127
    //   124: invokevirtual 84	twitter4j/internal/org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   127: putfield 129	twitter4j/internal/json/AccountSettingsJSONImpl:SCREEN_NAME	Ljava/lang/String;
    //   130: return
    //   131: aload_2
    //   132: ldc 92
    //   134: invokevirtual 133	twitter4j/internal/org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Ltwitter4j/internal/org/json/JSONArray;
    //   137: astore 5
    //   139: aload_0
    //   140: aload 5
    //   142: invokevirtual 139	twitter4j/internal/org/json/JSONArray:length	()I
    //   145: anewarray 98	twitter4j/Location
    //   148: putfield 100	twitter4j/internal/json/AccountSettingsJSONImpl:TREND_LOCATION	[Ltwitter4j/Location;
    //   151: iconst_0
    //   152: istore 6
    //   154: iload 6
    //   156: aload 5
    //   158: invokevirtual 139	twitter4j/internal/org/json/JSONArray:length	()I
    //   161: if_icmpge -98 -> 63
    //   164: aload_0
    //   165: getfield 100	twitter4j/internal/json/AccountSettingsJSONImpl:TREND_LOCATION	[Ltwitter4j/Location;
    //   168: iload 6
    //   170: new 141	twitter4j/internal/json/LocationJSONImpl
    //   173: dup
    //   174: aload 5
    //   176: iload 6
    //   178: invokevirtual 144	twitter4j/internal/org/json/JSONArray:getJSONObject	(I)Ltwitter4j/internal/org/json/JSONObject;
    //   181: invokespecial 145	twitter4j/internal/json/LocationJSONImpl:<init>	(Ltwitter4j/internal/org/json/JSONObject;)V
    //   184: aastore
    //   185: iinc 6 1
    //   188: goto -34 -> 154
    //   191: astore_3
    //   192: new 30	twitter4j/TwitterException
    //   195: dup
    //   196: aload_3
    //   197: invokespecial 148	twitter4j/TwitterException:<init>	(Ljava/lang/Exception;)V
    //   200: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	201	0	this	AccountSettingsJSONImpl
    //   0	201	1	paramHttpResponse	HttpResponse
    //   0	201	2	paramJSONObject	JSONObject
    //   191	6	3	localJSONException	twitter4j.internal.org.json.JSONException
    //   11	26	4	localJSONObject	JSONObject
    //   137	38	5	localJSONArray	twitter4j.internal.org.json.JSONArray
    //   152	34	6	i	int
    // Exception table:
    //   from	to	target	type
    //   5	185	191	twitter4j/internal/org/json/JSONException
  }
  
  AccountSettingsJSONImpl(JSONObject paramJSONObject)
    throws TwitterException
  {
    this(null, paramJSONObject);
  }
  
  public String getLanguage()
  {
    return this.LANGUAGE;
  }
  
  public String getScreenName()
  {
    return this.SCREEN_NAME;
  }
  
  public String getSleepEndTime()
  {
    return this.SLEEP_END_TIME;
  }
  
  public String getSleepStartTime()
  {
    return this.SLEEP_START_TIME;
  }
  
  public TimeZone getTimeZone()
  {
    return this.TIMEZONE;
  }
  
  public Location[] getTrendLocations()
  {
    return this.TREND_LOCATION;
  }
  
  public boolean isAlwaysUseHttps()
  {
    return this.ALWAYS_USE_HTTPS;
  }
  
  public boolean isDiscoverableByEmail()
  {
    return this.DISCOVERABLE_BY_EMAIL;
  }
  
  public boolean isGeoEnabled()
  {
    return this.GEO_ENABLED;
  }
  
  public boolean isSleepTimeEnabled()
  {
    return this.SLEEP_TIME_ENABLED;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.AccountSettingsJSONImpl
 * JD-Core Version:    0.7.0.1
 */