package twitter4j.internal.json;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.logging.Logger;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

final class StatusJSONImpl
  extends TwitterResponseImpl
  implements Status, Serializable
{
  private static final Logger logger = Logger.getLogger(StatusJSONImpl.class);
  private static final long serialVersionUID = 7548618898682727465L;
  private long[] contributorsIDs;
  private Date createdAt;
  private long currentUserRetweetId = -1L;
  private int favoriteCount;
  private GeoLocation geoLocation = null;
  private HashtagEntity[] hashtagEntities;
  private long id;
  private String inReplyToScreenName;
  private long inReplyToStatusId;
  private long inReplyToUserId;
  private boolean isFavorited;
  private boolean isPossiblySensitive;
  private boolean isRetweeted;
  private boolean isTruncated;
  private String isoLanguageCode;
  private MediaEntity[] mediaEntities;
  private Place place = null;
  private long retweetCount;
  private Status retweetedStatus;
  private String source;
  private SymbolEntity[] symbolEntities;
  private String text;
  private URLEntity[] urlEntities;
  private User user = null;
  private UserMentionEntity[] userMentionEntities;
  
  StatusJSONImpl() {}
  
  StatusJSONImpl(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    super(paramHttpResponse);
    JSONObject localJSONObject = paramHttpResponse.asJSONObject();
    init(localJSONObject);
    if (paramConfiguration.isJSONStoreEnabled())
    {
      DataObjectFactoryUtil.clearThreadLocalMap();
      DataObjectFactoryUtil.registerJSONObject(this, localJSONObject);
    }
  }
  
  StatusJSONImpl(JSONObject paramJSONObject)
    throws TwitterException
  {
    init(paramJSONObject);
  }
  
  StatusJSONImpl(JSONObject paramJSONObject, Configuration paramConfiguration)
    throws TwitterException
  {
    init(paramJSONObject);
    if (paramConfiguration.isJSONStoreEnabled()) {
      DataObjectFactoryUtil.registerJSONObject(this, paramJSONObject);
    }
  }
  
  static ResponseList<Status> createStatusList(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    try
    {
      if (paramConfiguration.isJSONStoreEnabled()) {
        DataObjectFactoryUtil.clearThreadLocalMap();
      }
      JSONArray localJSONArray = paramHttpResponse.asJSONArray();
      int i = localJSONArray.length();
      ResponseListImpl localResponseListImpl = new ResponseListImpl(i, paramHttpResponse);
      for (int j = 0; j < i; j++)
      {
        JSONObject localJSONObject = localJSONArray.getJSONObject(j);
        StatusJSONImpl localStatusJSONImpl = new StatusJSONImpl(localJSONObject);
        if (paramConfiguration.isJSONStoreEnabled()) {
          DataObjectFactoryUtil.registerJSONObject(localStatusJSONImpl, localJSONObject);
        }
        localResponseListImpl.add(localStatusJSONImpl);
      }
      if (paramConfiguration.isJSONStoreEnabled()) {
        DataObjectFactoryUtil.registerJSONObject(localResponseListImpl, localJSONArray);
      }
      return localResponseListImpl;
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  private void init(JSONObject paramJSONObject)
    throws TwitterException
  {
    this.id = z_T4JInternalParseUtil.getLong("id", paramJSONObject);
    this.source = z_T4JInternalParseUtil.getUnescapedString("source", paramJSONObject);
    this.createdAt = z_T4JInternalParseUtil.getDate("created_at", paramJSONObject);
    this.isTruncated = z_T4JInternalParseUtil.getBoolean("truncated", paramJSONObject);
    this.inReplyToStatusId = z_T4JInternalParseUtil.getLong("in_reply_to_status_id", paramJSONObject);
    this.inReplyToUserId = z_T4JInternalParseUtil.getLong("in_reply_to_user_id", paramJSONObject);
    this.isFavorited = z_T4JInternalParseUtil.getBoolean("favorited", paramJSONObject);
    this.isRetweeted = z_T4JInternalParseUtil.getBoolean("retweeted", paramJSONObject);
    this.inReplyToScreenName = z_T4JInternalParseUtil.getUnescapedString("in_reply_to_screen_name", paramJSONObject);
    this.retweetCount = z_T4JInternalParseUtil.getLong("retweet_count", paramJSONObject);
    this.favoriteCount = z_T4JInternalParseUtil.getInt("favorite_count", paramJSONObject);
    this.isPossiblySensitive = z_T4JInternalParseUtil.getBoolean("possibly_sensitive", paramJSONObject);
    try
    {
      if (!paramJSONObject.isNull("user")) {
        this.user = new UserJSONImpl(paramJSONObject.getJSONObject("user"));
      }
      this.geoLocation = z_T4JInternalJSONImplFactory.createGeoLocation(paramJSONObject);
      if (!paramJSONObject.isNull("place")) {
        this.place = new PlaceJSONImpl(paramJSONObject.getJSONObject("place"));
      }
      if (!paramJSONObject.isNull("retweeted_status")) {
        this.retweetedStatus = new StatusJSONImpl(paramJSONObject.getJSONObject("retweeted_status"));
      }
      if (!paramJSONObject.isNull("contributors"))
      {
        JSONArray localJSONArray6 = paramJSONObject.getJSONArray("contributors");
        this.contributorsIDs = new long[localJSONArray6.length()];
        for (int i6 = 0; i6 < localJSONArray6.length(); i6++) {
          this.contributorsIDs[i6] = Long.parseLong(localJSONArray6.getString(i6));
        }
      }
      this.contributorsIDs = new long[0];
      if (!paramJSONObject.isNull("entities"))
      {
        JSONObject localJSONObject = paramJSONObject.getJSONObject("entities");
        if (!localJSONObject.isNull("user_mentions"))
        {
          JSONArray localJSONArray5 = localJSONObject.getJSONArray("user_mentions");
          int i4 = localJSONArray5.length();
          this.userMentionEntities = new UserMentionEntity[i4];
          for (int i5 = 0; i5 < i4; i5++) {
            this.userMentionEntities[i5] = new UserMentionEntityJSONImpl(localJSONArray5.getJSONObject(i5));
          }
        }
        if (!localJSONObject.isNull("urls"))
        {
          JSONArray localJSONArray4 = localJSONObject.getJSONArray("urls");
          int i2 = localJSONArray4.length();
          this.urlEntities = new URLEntity[i2];
          for (int i3 = 0; i3 < i2; i3++) {
            this.urlEntities[i3] = new URLEntityJSONImpl(localJSONArray4.getJSONObject(i3));
          }
        }
        if (!localJSONObject.isNull("hashtags"))
        {
          JSONArray localJSONArray3 = localJSONObject.getJSONArray("hashtags");
          int n = localJSONArray3.length();
          this.hashtagEntities = new HashtagEntity[n];
          for (int i1 = 0; i1 < n; i1++) {
            this.hashtagEntities[i1] = new HashtagEntityJSONImpl(localJSONArray3.getJSONObject(i1));
          }
        }
        if (!localJSONObject.isNull("symbols"))
        {
          JSONArray localJSONArray2 = localJSONObject.getJSONArray("symbols");
          int k = localJSONArray2.length();
          this.symbolEntities = new SymbolEntity[k];
          for (int m = 0; m < k; m++) {
            this.symbolEntities[m] = new HashtagEntityJSONImpl(localJSONArray2.getJSONObject(m));
          }
        }
        if (!localJSONObject.isNull("media"))
        {
          JSONArray localJSONArray1 = localJSONObject.getJSONArray("media");
          int i = localJSONArray1.length();
          this.mediaEntities = new MediaEntity[i];
          for (int j = 0; j < i; j++) {
            this.mediaEntities[j] = new MediaEntityJSONImpl(localJSONArray1.getJSONObject(j));
          }
        }
      }
      this.isoLanguageCode = z_T4JInternalParseUtil.getRawString("lang", paramJSONObject);
      UserMentionEntity[] arrayOfUserMentionEntity;
      URLEntity[] arrayOfURLEntity;
      label707:
      HashtagEntity[] arrayOfHashtagEntity;
      label726:
      SymbolEntity[] arrayOfSymbolEntity;
      if (this.userMentionEntities == null)
      {
        arrayOfUserMentionEntity = new UserMentionEntity[0];
        this.userMentionEntities = arrayOfUserMentionEntity;
        if (this.urlEntities != null) {
          break label837;
        }
        arrayOfURLEntity = new URLEntity[0];
        this.urlEntities = arrayOfURLEntity;
        if (this.hashtagEntities != null) {
          break label846;
        }
        arrayOfHashtagEntity = new HashtagEntity[0];
        this.hashtagEntities = arrayOfHashtagEntity;
        if (this.symbolEntities != null) {
          break label855;
        }
        arrayOfSymbolEntity = new SymbolEntity[0];
        label745:
        this.symbolEntities = arrayOfSymbolEntity;
        if (this.mediaEntities != null) {
          break label864;
        }
      }
      label837:
      label846:
      label855:
      label864:
      for (MediaEntity[] arrayOfMediaEntity = new MediaEntity[0];; arrayOfMediaEntity = this.mediaEntities)
      {
        this.mediaEntities = arrayOfMediaEntity;
        this.text = HTMLEntity.unescapeAndSlideEntityIncdices(paramJSONObject.getString("text"), this.userMentionEntities, this.urlEntities, this.hashtagEntities, this.mediaEntities);
        if (paramJSONObject.isNull("current_user_retweet")) {
          return;
        }
        this.currentUserRetweetId = paramJSONObject.getJSONObject("current_user_retweet").getLong("id");
        return;
        arrayOfUserMentionEntity = this.userMentionEntities;
        break;
        arrayOfURLEntity = this.urlEntities;
        break label707;
        arrayOfHashtagEntity = this.hashtagEntities;
        break label726;
        arrayOfSymbolEntity = this.symbolEntities;
        break label745;
      }
      return;
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public int compareTo(Status paramStatus)
  {
    long l = this.id - paramStatus.getId();
    int i;
    if (l < -2147483648L) {
      i = -2147483648;
    }
    for (;;)
    {
      return i;
      if (l > 2147483647L) {
        i = 2147483647;
      } else {
        i = (int)l;
      }
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == null) {}
    for (bool = false;; bool = false) {
      do
      {
        return bool;
      } while ((this == paramObject) || (((paramObject instanceof Status)) && (((Status)paramObject).getId() == this.id)));
    }
  }
  
  public long[] getContributors()
  {
    return this.contributorsIDs;
  }
  
  public Date getCreatedAt()
  {
    return this.createdAt;
  }
  
  public long getCurrentUserRetweetId()
  {
    return this.currentUserRetweetId;
  }
  
  public int getFavoriteCount()
  {
    return this.favoriteCount;
  }
  
  public GeoLocation getGeoLocation()
  {
    return this.geoLocation;
  }
  
  public HashtagEntity[] getHashtagEntities()
  {
    return this.hashtagEntities;
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public String getInReplyToScreenName()
  {
    return this.inReplyToScreenName;
  }
  
  public long getInReplyToStatusId()
  {
    return this.inReplyToStatusId;
  }
  
  public long getInReplyToUserId()
  {
    return this.inReplyToUserId;
  }
  
  public String getIsoLanguageCode()
  {
    return this.isoLanguageCode;
  }
  
  public MediaEntity[] getMediaEntities()
  {
    return this.mediaEntities;
  }
  
  public Place getPlace()
  {
    return this.place;
  }
  
  public int getRetweetCount()
  {
    return (int)this.retweetCount;
  }
  
  public Status getRetweetedStatus()
  {
    return this.retweetedStatus;
  }
  
  public String getSource()
  {
    return this.source;
  }
  
  public SymbolEntity[] getSymbolEntities()
  {
    return this.symbolEntities;
  }
  
  public String getText()
  {
    return this.text;
  }
  
  public URLEntity[] getURLEntities()
  {
    return this.urlEntities;
  }
  
  public User getUser()
  {
    return this.user;
  }
  
  public UserMentionEntity[] getUserMentionEntities()
  {
    return this.userMentionEntities;
  }
  
  public int hashCode()
  {
    return (int)this.id;
  }
  
  public boolean isFavorited()
  {
    return this.isFavorited;
  }
  
  public boolean isPossiblySensitive()
  {
    return this.isPossiblySensitive;
  }
  
  public boolean isRetweet()
  {
    if (this.retweetedStatus != null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public boolean isRetweeted()
  {
    return this.isRetweeted;
  }
  
  public boolean isRetweetedByMe()
  {
    if (this.currentUserRetweetId != -1L) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public boolean isTruncated()
  {
    return this.isTruncated;
  }
  
  public String toString()
  {
    Object localObject1 = null;
    StringBuilder localStringBuilder1 = new StringBuilder().append("StatusJSONImpl{createdAt=").append(this.createdAt).append(", id=").append(this.id).append(", text='").append(this.text).append('\'').append(", source='").append(this.source).append('\'').append(", isTruncated=").append(this.isTruncated).append(", inReplyToStatusId=").append(this.inReplyToStatusId).append(", inReplyToUserId=").append(this.inReplyToUserId).append(", isFavorited=").append(this.isFavorited).append(", isRetweeted=").append(this.isRetweeted).append(", favoriteCount=").append(this.favoriteCount).append(", inReplyToScreenName='").append(this.inReplyToScreenName).append('\'').append(", geoLocation=").append(this.geoLocation).append(", place=").append(this.place).append(", retweetCount=").append(this.retweetCount).append(", isPossiblySensitive=").append(this.isPossiblySensitive).append(", isoLanguageCode=").append(this.isoLanguageCode).append(", contributorsIDs=").append(this.contributorsIDs).append(", retweetedStatus=").append(this.retweetedStatus).append(", userMentionEntities=");
    Object localObject2;
    Object localObject3;
    label297:
    Object localObject4;
    label322:
    StringBuilder localStringBuilder4;
    if (this.userMentionEntities == null)
    {
      localObject2 = null;
      StringBuilder localStringBuilder2 = localStringBuilder1.append(localObject2).append(", urlEntities=");
      if (this.urlEntities != null) {
        break label396;
      }
      localObject3 = null;
      StringBuilder localStringBuilder3 = localStringBuilder2.append(localObject3).append(", hashtagEntities=");
      if (this.hashtagEntities != null) {
        break label408;
      }
      localObject4 = null;
      localStringBuilder4 = localStringBuilder3.append(localObject4).append(", mediaEntities=");
      if (this.mediaEntities != null) {
        break label420;
      }
    }
    for (;;)
    {
      return localObject1 + ", currentUserRetweetId=" + this.currentUserRetweetId + ", user=" + this.user + '}';
      localObject2 = Arrays.asList(this.userMentionEntities);
      break;
      label396:
      localObject3 = Arrays.asList(this.urlEntities);
      break label297;
      label408:
      localObject4 = Arrays.asList(this.hashtagEntities);
      break label322;
      label420:
      localObject1 = Arrays.asList(this.mediaEntities);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.StatusJSONImpl
 * JD-Core Version:    0.7.0.1
 */