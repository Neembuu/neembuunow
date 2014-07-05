package twitter4j.internal.json;

import twitter4j.TwitterException;
import twitter4j.UserMentionEntity;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

class UserMentionEntityJSONImpl
  extends EntityIndex
  implements UserMentionEntity
{
  private static final long serialVersionUID = 6580431141350059702L;
  private long id;
  private String name;
  private String screenName;
  
  UserMentionEntityJSONImpl() {}
  
  UserMentionEntityJSONImpl(int paramInt1, int paramInt2, String paramString1, String paramString2, long paramLong)
  {
    setStart(paramInt1);
    setEnd(paramInt2);
    this.name = paramString1;
    this.screenName = paramString2;
    this.id = paramLong;
  }
  
  UserMentionEntityJSONImpl(JSONObject paramJSONObject)
    throws TwitterException
  {
    init(paramJSONObject);
  }
  
  private void init(JSONObject paramJSONObject)
    throws TwitterException
  {
    try
    {
      JSONArray localJSONArray = paramJSONObject.getJSONArray("indices");
      setStart(localJSONArray.getInt(0));
      setEnd(localJSONArray.getInt(1));
      if (!paramJSONObject.isNull("name")) {
        this.name = paramJSONObject.getString("name");
      }
      if (!paramJSONObject.isNull("screen_name")) {
        this.screenName = paramJSONObject.getString("screen_name");
      }
      this.id = z_T4JInternalParseUtil.getLong("id", paramJSONObject);
      return;
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    UserMentionEntityJSONImpl localUserMentionEntityJSONImpl;
    do
    {
      for (;;)
      {
        return bool;
        if ((paramObject == null) || (getClass() != paramObject.getClass()))
        {
          bool = false;
        }
        else
        {
          localUserMentionEntityJSONImpl = (UserMentionEntityJSONImpl)paramObject;
          if (this.id == localUserMentionEntityJSONImpl.id) {
            break;
          }
          bool = false;
        }
      }
      if (this.name != null)
      {
        if (this.name.equals(localUserMentionEntityJSONImpl.name)) {}
      }
      else {
        while (localUserMentionEntityJSONImpl.name != null)
        {
          bool = false;
          break;
        }
      }
      if (this.screenName == null) {
        break;
      }
    } while (this.screenName.equals(localUserMentionEntityJSONImpl.screenName));
    for (;;)
    {
      bool = false;
      break;
      if (localUserMentionEntityJSONImpl.screenName == null) {
        break;
      }
    }
  }
  
  public int getEnd()
  {
    return super.getEnd();
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getScreenName()
  {
    return this.screenName;
  }
  
  public int getStart()
  {
    return super.getStart();
  }
  
  public String getText()
  {
    return this.screenName;
  }
  
  public int hashCode()
  {
    int i = 0;
    if (this.name != null) {}
    for (int j = this.name.hashCode();; j = 0)
    {
      int k = j * 31;
      if (this.screenName != null) {
        i = this.screenName.hashCode();
      }
      return 31 * (k + i) + (int)(this.id ^ this.id >>> 32);
    }
  }
  
  public String toString()
  {
    return "UserMentionEntityJSONImpl{name='" + this.name + '\'' + ", screenName='" + this.screenName + '\'' + ", id=" + this.id + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.UserMentionEntityJSONImpl
 * JD-Core Version:    0.7.0.1
 */