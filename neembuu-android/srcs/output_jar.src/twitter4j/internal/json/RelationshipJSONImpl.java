package twitter4j.internal.json;

import java.io.Serializable;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

class RelationshipJSONImpl
  extends TwitterResponseImpl
  implements Relationship, Serializable
{
  private static final long serialVersionUID = 7725021608907856360L;
  private final boolean sourceBlockingTarget;
  private final boolean sourceCanDm;
  private final boolean sourceFollowedByTarget;
  private final boolean sourceFollowingTarget;
  private final boolean sourceNotificationsEnabled;
  private final long sourceUserId;
  private final String sourceUserScreenName;
  private final long targetUserId;
  private final String targetUserScreenName;
  private boolean wantRetweets;
  
  RelationshipJSONImpl(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    this(paramHttpResponse, paramHttpResponse.asJSONObject());
    if (paramConfiguration.isJSONStoreEnabled())
    {
      DataObjectFactoryUtil.clearThreadLocalMap();
      DataObjectFactoryUtil.registerJSONObject(this, paramHttpResponse.asJSONObject());
    }
  }
  
  RelationshipJSONImpl(HttpResponse paramHttpResponse, JSONObject paramJSONObject)
    throws TwitterException
  {
    super(paramHttpResponse);
    try
    {
      JSONObject localJSONObject1 = paramJSONObject.getJSONObject("relationship");
      JSONObject localJSONObject2 = localJSONObject1.getJSONObject("source");
      JSONObject localJSONObject3 = localJSONObject1.getJSONObject("target");
      this.sourceUserId = z_T4JInternalParseUtil.getLong("id", localJSONObject2);
      this.targetUserId = z_T4JInternalParseUtil.getLong("id", localJSONObject3);
      this.sourceUserScreenName = z_T4JInternalParseUtil.getUnescapedString("screen_name", localJSONObject2);
      this.targetUserScreenName = z_T4JInternalParseUtil.getUnescapedString("screen_name", localJSONObject3);
      this.sourceBlockingTarget = z_T4JInternalParseUtil.getBoolean("blocking", localJSONObject2);
      this.sourceFollowingTarget = z_T4JInternalParseUtil.getBoolean("following", localJSONObject2);
      this.sourceFollowedByTarget = z_T4JInternalParseUtil.getBoolean("followed_by", localJSONObject2);
      this.sourceCanDm = z_T4JInternalParseUtil.getBoolean("can_dm", localJSONObject2);
      this.sourceNotificationsEnabled = z_T4JInternalParseUtil.getBoolean("notifications_enabled", localJSONObject2);
      this.wantRetweets = z_T4JInternalParseUtil.getBoolean("want_retweets", localJSONObject2);
      return;
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException.getMessage() + ":" + paramJSONObject.toString(), localJSONException);
    }
  }
  
  RelationshipJSONImpl(JSONObject paramJSONObject)
    throws TwitterException
  {
    this(null, paramJSONObject);
  }
  
  static ResponseList<Relationship> createRelationshipList(HttpResponse paramHttpResponse, Configuration paramConfiguration)
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
        RelationshipJSONImpl localRelationshipJSONImpl = new RelationshipJSONImpl(localJSONObject);
        if (paramConfiguration.isJSONStoreEnabled()) {
          DataObjectFactoryUtil.registerJSONObject(localRelationshipJSONImpl, localJSONObject);
        }
        localResponseListImpl.add(localRelationshipJSONImpl);
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
    catch (TwitterException localTwitterException)
    {
      throw localTwitterException;
    }
  }
  
  public boolean canSourceDm()
  {
    return this.sourceCanDm;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    for (;;)
    {
      return bool;
      if (!(paramObject instanceof Relationship))
      {
        bool = false;
      }
      else
      {
        Relationship localRelationship = (Relationship)paramObject;
        if (this.sourceUserId != localRelationship.getSourceUserId()) {
          bool = false;
        } else if (this.targetUserId != localRelationship.getTargetUserId()) {
          bool = false;
        } else if (!this.sourceUserScreenName.equals(localRelationship.getSourceUserScreenName())) {
          bool = false;
        } else if (!this.targetUserScreenName.equals(localRelationship.getTargetUserScreenName())) {
          bool = false;
        }
      }
    }
  }
  
  public long getSourceUserId()
  {
    return this.sourceUserId;
  }
  
  public String getSourceUserScreenName()
  {
    return this.sourceUserScreenName;
  }
  
  public long getTargetUserId()
  {
    return this.targetUserId;
  }
  
  public String getTargetUserScreenName()
  {
    return this.targetUserScreenName;
  }
  
  public int hashCode()
  {
    int i = 1;
    int j = 0;
    int k = 31 * (int)(this.targetUserId ^ this.targetUserId >>> 32);
    int m;
    int i1;
    label56:
    int i3;
    label76:
    int i5;
    label96:
    int i7;
    label116:
    int i8;
    if (this.targetUserScreenName != null)
    {
      m = this.targetUserScreenName.hashCode();
      int n = 31 * (k + m);
      if (!this.sourceBlockingTarget) {
        break label185;
      }
      i1 = i;
      int i2 = 31 * (n + i1);
      if (!this.sourceNotificationsEnabled) {
        break label191;
      }
      i3 = i;
      int i4 = 31 * (i2 + i3);
      if (!this.sourceFollowingTarget) {
        break label197;
      }
      i5 = i;
      int i6 = 31 * (i4 + i5);
      if (!this.sourceFollowedByTarget) {
        break label203;
      }
      i7 = i;
      i8 = 31 * (i6 + i7);
      if (!this.sourceCanDm) {
        break label209;
      }
    }
    for (;;)
    {
      int i9 = 31 * (31 * (i8 + i) + (int)(this.sourceUserId ^ this.sourceUserId >>> 32));
      if (this.sourceUserScreenName != null) {
        j = this.sourceUserScreenName.hashCode();
      }
      return i9 + j;
      m = 0;
      break;
      label185:
      i1 = 0;
      break label56;
      label191:
      i3 = 0;
      break label76;
      label197:
      i5 = 0;
      break label96;
      label203:
      i7 = 0;
      break label116;
      label209:
      i = 0;
    }
  }
  
  public boolean isSourceBlockingTarget()
  {
    return this.sourceBlockingTarget;
  }
  
  public boolean isSourceFollowedByTarget()
  {
    return this.sourceFollowedByTarget;
  }
  
  public boolean isSourceFollowingTarget()
  {
    return this.sourceFollowingTarget;
  }
  
  public boolean isSourceNotificationsEnabled()
  {
    return this.sourceNotificationsEnabled;
  }
  
  public boolean isSourceWantRetweets()
  {
    return this.wantRetweets;
  }
  
  public boolean isTargetFollowedBySource()
  {
    return this.sourceFollowingTarget;
  }
  
  public boolean isTargetFollowingSource()
  {
    return this.sourceFollowedByTarget;
  }
  
  public String toString()
  {
    return "RelationshipJSONImpl{sourceUserId=" + this.sourceUserId + ", targetUserId=" + this.targetUserId + ", sourceUserScreenName='" + this.sourceUserScreenName + '\'' + ", targetUserScreenName='" + this.targetUserScreenName + '\'' + ", sourceFollowingTarget=" + this.sourceFollowingTarget + ", sourceFollowedByTarget=" + this.sourceFollowedByTarget + ", sourceCanDm=" + this.sourceCanDm + ", sourceNotificationsEnabled=" + this.sourceNotificationsEnabled + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.RelationshipJSONImpl
 * JD-Core Version:    0.7.0.1
 */