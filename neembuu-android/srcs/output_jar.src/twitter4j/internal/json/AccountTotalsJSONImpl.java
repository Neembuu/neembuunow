package twitter4j.internal.json;

import java.io.Serializable;
import twitter4j.AccountTotals;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONObject;

class AccountTotalsJSONImpl
  extends TwitterResponseImpl
  implements AccountTotals, Serializable
{
  private static final long serialVersionUID = -2291419345865627123L;
  private final int favorites;
  private final int followers;
  private final int friends;
  private final int updates;
  
  AccountTotalsJSONImpl(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    this(paramHttpResponse, paramHttpResponse.asJSONObject());
    if (paramConfiguration.isJSONStoreEnabled())
    {
      DataObjectFactoryUtil.clearThreadLocalMap();
      DataObjectFactoryUtil.registerJSONObject(this, paramHttpResponse.asJSONObject());
    }
  }
  
  private AccountTotalsJSONImpl(HttpResponse paramHttpResponse, JSONObject paramJSONObject)
  {
    super(paramHttpResponse);
    this.updates = z_T4JInternalParseUtil.getInt("updates", paramJSONObject);
    this.followers = z_T4JInternalParseUtil.getInt("followers", paramJSONObject);
    this.favorites = z_T4JInternalParseUtil.getInt("favorites", paramJSONObject);
    this.friends = z_T4JInternalParseUtil.getInt("friends", paramJSONObject);
  }
  
  AccountTotalsJSONImpl(JSONObject paramJSONObject)
    throws TwitterException
  {
    this(null, paramJSONObject);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    for (;;)
    {
      return bool;
      if ((paramObject == null) || (getClass() != paramObject.getClass()))
      {
        bool = false;
      }
      else
      {
        AccountTotalsJSONImpl localAccountTotalsJSONImpl = (AccountTotalsJSONImpl)paramObject;
        if (this.favorites != localAccountTotalsJSONImpl.favorites) {
          bool = false;
        } else if (this.followers != localAccountTotalsJSONImpl.followers) {
          bool = false;
        } else if (this.friends != localAccountTotalsJSONImpl.friends) {
          bool = false;
        } else if (this.updates != localAccountTotalsJSONImpl.updates) {
          bool = false;
        }
      }
    }
  }
  
  public int getFavorites()
  {
    return this.favorites;
  }
  
  public int getFollowers()
  {
    return this.followers;
  }
  
  public int getFriends()
  {
    return this.friends;
  }
  
  public int getUpdates()
  {
    return this.updates;
  }
  
  public int hashCode()
  {
    return 31 * (31 * (31 * this.updates + this.followers) + this.favorites) + this.friends;
  }
  
  public String toString()
  {
    return "AccountTotalsJSONImpl{updates=" + this.updates + ", followers=" + this.followers + ", favorites=" + this.favorites + ", friends=" + this.friends + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.AccountTotalsJSONImpl
 * JD-Core Version:    0.7.0.1
 */