package twitter4j.internal.json;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import twitter4j.PagableResponseList;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

final class UserJSONImpl
  extends TwitterResponseImpl
  implements User, Serializable
{
  private static final long serialVersionUID = -6345893237975349030L;
  private Date createdAt;
  private String description;
  private URLEntity[] descriptionURLEntities;
  private int favouritesCount;
  private int followersCount;
  private int friendsCount;
  private long id;
  private boolean isContributorsEnabled;
  private boolean isFollowRequestSent;
  private boolean isGeoEnabled;
  private boolean isProtected;
  private boolean isVerified;
  private String lang;
  private int listedCount;
  private String location;
  private String name;
  private String profileBackgroundColor;
  private String profileBackgroundImageUrl;
  private String profileBackgroundImageUrlHttps;
  private boolean profileBackgroundTiled;
  private String profileBannerImageUrl;
  private String profileImageUrl;
  private String profileImageUrlHttps;
  private String profileLinkColor;
  private String profileSidebarBorderColor;
  private String profileSidebarFillColor;
  private String profileTextColor;
  private boolean profileUseBackgroundImage;
  private String screenName;
  private boolean showAllInlineMedia;
  private Status status;
  private int statusesCount;
  private String timeZone;
  private boolean translator;
  private String url;
  private URLEntity urlEntity;
  private int utcOffset;
  
  UserJSONImpl() {}
  
  UserJSONImpl(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    super(paramHttpResponse);
    if (paramConfiguration.isJSONStoreEnabled()) {
      DataObjectFactoryUtil.clearThreadLocalMap();
    }
    JSONObject localJSONObject = paramHttpResponse.asJSONObject();
    init(localJSONObject);
    if (paramConfiguration.isJSONStoreEnabled()) {
      DataObjectFactoryUtil.registerJSONObject(this, localJSONObject);
    }
  }
  
  UserJSONImpl(JSONObject paramJSONObject)
    throws TwitterException
  {
    init(paramJSONObject);
  }
  
  static PagableResponseList<User> createPagableUserList(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    try
    {
      if (paramConfiguration.isJSONStoreEnabled()) {
        DataObjectFactoryUtil.clearThreadLocalMap();
      }
      JSONObject localJSONObject1 = paramHttpResponse.asJSONObject();
      JSONArray localJSONArray = localJSONObject1.getJSONArray("users");
      int i = localJSONArray.length();
      PagableResponseListImpl localPagableResponseListImpl = new PagableResponseListImpl(i, localJSONObject1, paramHttpResponse);
      for (int j = 0; j < i; j++)
      {
        JSONObject localJSONObject2 = localJSONArray.getJSONObject(j);
        UserJSONImpl localUserJSONImpl = new UserJSONImpl(localJSONObject2);
        if (paramConfiguration.isJSONStoreEnabled()) {
          DataObjectFactoryUtil.registerJSONObject(localUserJSONImpl, localJSONObject2);
        }
        localPagableResponseListImpl.add(localUserJSONImpl);
      }
      if (paramConfiguration.isJSONStoreEnabled()) {
        DataObjectFactoryUtil.registerJSONObject(localPagableResponseListImpl, localJSONObject1);
      }
      return localPagableResponseListImpl;
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
    catch (TwitterException localTwitterException)
    {
      throw localTwitterException;
    }
  }
  
  static ResponseList<User> createUserList(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    return createUserList(paramHttpResponse.asJSONArray(), paramHttpResponse, paramConfiguration);
  }
  
  static ResponseList<User> createUserList(JSONArray paramJSONArray, HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    for (;;)
    {
      int j;
      try
      {
        if (paramConfiguration.isJSONStoreEnabled()) {
          DataObjectFactoryUtil.clearThreadLocalMap();
        }
        int i = paramJSONArray.length();
        ResponseListImpl localResponseListImpl = new ResponseListImpl(i, paramHttpResponse);
        j = 0;
        if (j < i)
        {
          JSONObject localJSONObject = paramJSONArray.getJSONObject(j);
          UserJSONImpl localUserJSONImpl = new UserJSONImpl(localJSONObject);
          localResponseListImpl.add(localUserJSONImpl);
          if (paramConfiguration.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.registerJSONObject(localUserJSONImpl, localJSONObject);
          }
        }
        else
        {
          if (paramConfiguration.isJSONStoreEnabled()) {
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
  
  private static URLEntity[] getURLEntitiesFromJSON(JSONObject paramJSONObject, String paramString)
    throws JSONException, TwitterException
  {
    if (!paramJSONObject.isNull("entities"))
    {
      JSONObject localJSONObject1 = paramJSONObject.getJSONObject("entities");
      if (!localJSONObject1.isNull(paramString))
      {
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject(paramString);
        if (!localJSONObject2.isNull("urls"))
        {
          JSONArray localJSONArray = localJSONObject2.getJSONArray("urls");
          int i = localJSONArray.length();
          arrayOfURLEntity = new URLEntity[i];
          for (int j = 0; j < i; j++) {
            arrayOfURLEntity[j] = new URLEntityJSONImpl(localJSONArray.getJSONObject(j));
          }
        }
      }
    }
    URLEntity[] arrayOfURLEntity = null;
    return arrayOfURLEntity;
  }
  
  private void init(JSONObject paramJSONObject)
    throws TwitterException
  {
    try
    {
      this.id = z_T4JInternalParseUtil.getLong("id", paramJSONObject);
      this.name = z_T4JInternalParseUtil.getRawString("name", paramJSONObject);
      this.screenName = z_T4JInternalParseUtil.getRawString("screen_name", paramJSONObject);
      this.location = z_T4JInternalParseUtil.getRawString("location", paramJSONObject);
      this.descriptionURLEntities = getURLEntitiesFromJSON(paramJSONObject, "description");
      if (this.descriptionURLEntities == null) {}
      for (URLEntity[] arrayOfURLEntity1 = new URLEntity[0];; arrayOfURLEntity1 = this.descriptionURLEntities)
      {
        this.descriptionURLEntities = arrayOfURLEntity1;
        URLEntity[] arrayOfURLEntity2 = getURLEntitiesFromJSON(paramJSONObject, "url");
        if ((arrayOfURLEntity2 != null) && (arrayOfURLEntity2.length > 0)) {
          this.urlEntity = arrayOfURLEntity2[0];
        }
        this.description = z_T4JInternalParseUtil.getRawString("description", paramJSONObject);
        if (this.description != null) {
          this.description = HTMLEntity.unescapeAndSlideEntityIncdices(this.description, null, this.descriptionURLEntities, null, null);
        }
        this.isContributorsEnabled = z_T4JInternalParseUtil.getBoolean("contributors_enabled", paramJSONObject);
        this.profileImageUrl = z_T4JInternalParseUtil.getRawString("profile_image_url", paramJSONObject);
        this.profileImageUrlHttps = z_T4JInternalParseUtil.getRawString("profile_image_url_https", paramJSONObject);
        this.url = z_T4JInternalParseUtil.getRawString("url", paramJSONObject);
        this.isProtected = z_T4JInternalParseUtil.getBoolean("protected", paramJSONObject);
        this.isGeoEnabled = z_T4JInternalParseUtil.getBoolean("geo_enabled", paramJSONObject);
        this.isVerified = z_T4JInternalParseUtil.getBoolean("verified", paramJSONObject);
        this.translator = z_T4JInternalParseUtil.getBoolean("is_translator", paramJSONObject);
        this.followersCount = z_T4JInternalParseUtil.getInt("followers_count", paramJSONObject);
        this.profileBackgroundColor = z_T4JInternalParseUtil.getRawString("profile_background_color", paramJSONObject);
        this.profileTextColor = z_T4JInternalParseUtil.getRawString("profile_text_color", paramJSONObject);
        this.profileLinkColor = z_T4JInternalParseUtil.getRawString("profile_link_color", paramJSONObject);
        this.profileSidebarFillColor = z_T4JInternalParseUtil.getRawString("profile_sidebar_fill_color", paramJSONObject);
        this.profileSidebarBorderColor = z_T4JInternalParseUtil.getRawString("profile_sidebar_border_color", paramJSONObject);
        this.profileUseBackgroundImage = z_T4JInternalParseUtil.getBoolean("profile_use_background_image", paramJSONObject);
        this.showAllInlineMedia = z_T4JInternalParseUtil.getBoolean("show_all_inline_media", paramJSONObject);
        this.friendsCount = z_T4JInternalParseUtil.getInt("friends_count", paramJSONObject);
        this.createdAt = z_T4JInternalParseUtil.getDate("created_at", paramJSONObject, "EEE MMM dd HH:mm:ss z yyyy");
        this.favouritesCount = z_T4JInternalParseUtil.getInt("favourites_count", paramJSONObject);
        this.utcOffset = z_T4JInternalParseUtil.getInt("utc_offset", paramJSONObject);
        this.timeZone = z_T4JInternalParseUtil.getRawString("time_zone", paramJSONObject);
        this.profileBackgroundImageUrl = z_T4JInternalParseUtil.getRawString("profile_background_image_url", paramJSONObject);
        this.profileBackgroundImageUrlHttps = z_T4JInternalParseUtil.getRawString("profile_background_image_url_https", paramJSONObject);
        this.profileBannerImageUrl = z_T4JInternalParseUtil.getRawString("profile_banner_url", paramJSONObject);
        this.profileBackgroundTiled = z_T4JInternalParseUtil.getBoolean("profile_background_tile", paramJSONObject);
        this.lang = z_T4JInternalParseUtil.getRawString("lang", paramJSONObject);
        this.statusesCount = z_T4JInternalParseUtil.getInt("statuses_count", paramJSONObject);
        this.listedCount = z_T4JInternalParseUtil.getInt("listed_count", paramJSONObject);
        this.isFollowRequestSent = z_T4JInternalParseUtil.getBoolean("follow_request_sent", paramJSONObject);
        if (paramJSONObject.isNull("status")) {
          break;
        }
        this.status = new StatusJSONImpl(paramJSONObject.getJSONObject("status"));
        break;
      }
      return;
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException.getMessage() + ":" + paramJSONObject.toString(), localJSONException);
    }
  }
  
  private String toResizedURL(String paramString1, String paramString2)
  {
    int j;
    if (paramString1 != null)
    {
      int i = paramString1.lastIndexOf("_");
      j = paramString1.lastIndexOf(".");
      int k = paramString1.lastIndexOf("/");
      str = paramString1.substring(0, i) + paramString2;
      if (j <= k) {}
    }
    for (String str = str + paramString1.substring(j);; str = null) {
      return str;
    }
  }
  
  public int compareTo(User paramUser)
  {
    return (int)(this.id - paramUser.getId());
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == null) {}
    for (bool = false;; bool = false) {
      do
      {
        return bool;
      } while ((this == paramObject) || (((paramObject instanceof User)) && (((User)paramObject).getId() == this.id)));
    }
  }
  
  public String getBiggerProfileImageURL()
  {
    return toResizedURL(this.profileImageUrl, "_bigger");
  }
  
  public String getBiggerProfileImageURLHttps()
  {
    return toResizedURL(this.profileImageUrlHttps, "_bigger");
  }
  
  public Date getCreatedAt()
  {
    return this.createdAt;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public URLEntity[] getDescriptionURLEntities()
  {
    return this.descriptionURLEntities;
  }
  
  public int getFavouritesCount()
  {
    return this.favouritesCount;
  }
  
  public int getFollowersCount()
  {
    return this.followersCount;
  }
  
  public int getFriendsCount()
  {
    return this.friendsCount;
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public String getLang()
  {
    return this.lang;
  }
  
  public int getListedCount()
  {
    return this.listedCount;
  }
  
  public String getLocation()
  {
    return this.location;
  }
  
  public String getMiniProfileImageURL()
  {
    return toResizedURL(this.profileImageUrl, "_mini");
  }
  
  public String getMiniProfileImageURLHttps()
  {
    return toResizedURL(this.profileImageUrlHttps, "_mini");
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getOriginalProfileImageURL()
  {
    return toResizedURL(this.profileImageUrl, "");
  }
  
  public String getOriginalProfileImageURLHttps()
  {
    return toResizedURL(this.profileImageUrlHttps, "");
  }
  
  public String getProfileBackgroundColor()
  {
    return this.profileBackgroundColor;
  }
  
  public String getProfileBackgroundImageURL()
  {
    return this.profileBackgroundImageUrl;
  }
  
  public String getProfileBackgroundImageUrl()
  {
    return getProfileBackgroundImageURL();
  }
  
  public String getProfileBackgroundImageUrlHttps()
  {
    return this.profileBackgroundImageUrlHttps;
  }
  
  public String getProfileBannerIPadRetinaURL()
  {
    if (this.profileBannerImageUrl != null) {}
    for (String str = this.profileBannerImageUrl + "/ipad_retina";; str = null) {
      return str;
    }
  }
  
  public String getProfileBannerIPadURL()
  {
    if (this.profileBannerImageUrl != null) {}
    for (String str = this.profileBannerImageUrl + "/ipad";; str = null) {
      return str;
    }
  }
  
  public String getProfileBannerMobileRetinaURL()
  {
    if (this.profileBannerImageUrl != null) {}
    for (String str = this.profileBannerImageUrl + "/mobile_retina";; str = null) {
      return str;
    }
  }
  
  public String getProfileBannerMobileURL()
  {
    if (this.profileBannerImageUrl != null) {}
    for (String str = this.profileBannerImageUrl + "/mobile";; str = null) {
      return str;
    }
  }
  
  public String getProfileBannerRetinaURL()
  {
    if (this.profileBannerImageUrl != null) {}
    for (String str = this.profileBannerImageUrl + "/web_retina";; str = null) {
      return str;
    }
  }
  
  public String getProfileBannerURL()
  {
    if (this.profileBannerImageUrl != null) {}
    for (String str = this.profileBannerImageUrl + "/web";; str = null) {
      return str;
    }
  }
  
  public String getProfileImageURL()
  {
    return this.profileImageUrl;
  }
  
  public String getProfileImageURLHttps()
  {
    return this.profileImageUrlHttps;
  }
  
  public URL getProfileImageUrlHttps()
  {
    try
    {
      localURL = new URL(this.profileImageUrlHttps);
      return localURL;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      for (;;)
      {
        URL localURL = null;
      }
    }
  }
  
  public String getProfileLinkColor()
  {
    return this.profileLinkColor;
  }
  
  public String getProfileSidebarBorderColor()
  {
    return this.profileSidebarBorderColor;
  }
  
  public String getProfileSidebarFillColor()
  {
    return this.profileSidebarFillColor;
  }
  
  public String getProfileTextColor()
  {
    return this.profileTextColor;
  }
  
  public String getScreenName()
  {
    return this.screenName;
  }
  
  public Status getStatus()
  {
    return this.status;
  }
  
  public int getStatusesCount()
  {
    return this.statusesCount;
  }
  
  public String getTimeZone()
  {
    return this.timeZone;
  }
  
  public String getURL()
  {
    return this.url;
  }
  
  public URLEntity getURLEntity()
  {
    if (this.urlEntity == null) {
      if (this.url != null) {
        break label42;
      }
    }
    label42:
    for (String str = "";; str = this.url)
    {
      this.urlEntity = new URLEntityJSONImpl(0, str.length(), str, str, str);
      return this.urlEntity;
    }
  }
  
  public int getUtcOffset()
  {
    return this.utcOffset;
  }
  
  public int hashCode()
  {
    return (int)this.id;
  }
  
  public boolean isContributorsEnabled()
  {
    return this.isContributorsEnabled;
  }
  
  public boolean isFollowRequestSent()
  {
    return this.isFollowRequestSent;
  }
  
  public boolean isGeoEnabled()
  {
    return this.isGeoEnabled;
  }
  
  public boolean isProfileBackgroundTiled()
  {
    return this.profileBackgroundTiled;
  }
  
  public boolean isProfileUseBackgroundImage()
  {
    return this.profileUseBackgroundImage;
  }
  
  public boolean isProtected()
  {
    return this.isProtected;
  }
  
  public boolean isShowAllInlineMedia()
  {
    return this.showAllInlineMedia;
  }
  
  public boolean isTranslator()
  {
    return this.translator;
  }
  
  public boolean isVerified()
  {
    return this.isVerified;
  }
  
  public String toString()
  {
    return "UserJSONImpl{id=" + this.id + ", name='" + this.name + '\'' + ", screenName='" + this.screenName + '\'' + ", location='" + this.location + '\'' + ", description='" + this.description + '\'' + ", isContributorsEnabled=" + this.isContributorsEnabled + ", profileImageUrl='" + this.profileImageUrl + '\'' + ", profileImageUrlHttps='" + this.profileImageUrlHttps + '\'' + ", url='" + this.url + '\'' + ", isProtected=" + this.isProtected + ", followersCount=" + this.followersCount + ", status=" + this.status + ", profileBackgroundColor='" + this.profileBackgroundColor + '\'' + ", profileTextColor='" + this.profileTextColor + '\'' + ", profileLinkColor='" + this.profileLinkColor + '\'' + ", profileSidebarFillColor='" + this.profileSidebarFillColor + '\'' + ", profileSidebarBorderColor='" + this.profileSidebarBorderColor + '\'' + ", profileUseBackgroundImage=" + this.profileUseBackgroundImage + ", showAllInlineMedia=" + this.showAllInlineMedia + ", friendsCount=" + this.friendsCount + ", createdAt=" + this.createdAt + ", favouritesCount=" + this.favouritesCount + ", utcOffset=" + this.utcOffset + ", timeZone='" + this.timeZone + '\'' + ", profileBackgroundImageUrl='" + this.profileBackgroundImageUrl + '\'' + ", profileBackgroundImageUrlHttps='" + this.profileBackgroundImageUrlHttps + '\'' + ", profileBackgroundTiled=" + this.profileBackgroundTiled + ", lang='" + this.lang + '\'' + ", statusesCount=" + this.statusesCount + ", isGeoEnabled=" + this.isGeoEnabled + ", isVerified=" + this.isVerified + ", translator=" + this.translator + ", listedCount=" + this.listedCount + ", isFollowRequestSent=" + this.isFollowRequestSent + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.UserJSONImpl
 * JD-Core Version:    0.7.0.1
 */