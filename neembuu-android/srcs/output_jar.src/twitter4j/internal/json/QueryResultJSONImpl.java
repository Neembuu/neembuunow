package twitter4j.internal.json;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

final class QueryResultJSONImpl
  extends TwitterResponseImpl
  implements QueryResult, Serializable
{
  static Method queryFactoryMethod;
  private static final long serialVersionUID = -6781654399437121238L;
  private double completedIn;
  private int count;
  private long maxId;
  private String nextResults;
  private String query;
  private String refreshUrl;
  private long sinceId;
  private List<Status> tweets;
  
  static
  {
    Method[] arrayOfMethod = Query.class.getDeclaredMethods();
    int i = arrayOfMethod.length;
    for (int j = 0;; j++) {
      if (j < i)
      {
        Method localMethod = arrayOfMethod[j];
        if (localMethod.getName().equals("createWithNextPageQuery"))
        {
          queryFactoryMethod = localMethod;
          queryFactoryMethod.setAccessible(true);
        }
      }
      else
      {
        if (queryFactoryMethod != null) {
          break;
        }
        throw new ExceptionInInitializerError(new NoSuchMethodException("twitter4j.Query.createWithNextPageQuery(java.lang.String)"));
      }
    }
  }
  
  QueryResultJSONImpl(Query paramQuery)
  {
    this.sinceId = paramQuery.getSinceId();
    this.count = paramQuery.getCount();
    this.tweets = new ArrayList(0);
  }
  
  QueryResultJSONImpl(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    super(paramHttpResponse);
    JSONObject localJSONObject1 = paramHttpResponse.asJSONObject();
    for (;;)
    {
      try
      {
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject("search_metadata");
        this.completedIn = z_T4JInternalParseUtil.getDouble("completed_in", localJSONObject2);
        this.count = z_T4JInternalParseUtil.getInt("count", localJSONObject2);
        this.maxId = z_T4JInternalParseUtil.getLong("max_id", localJSONObject2);
        String str;
        if (localJSONObject2.has("next_results"))
        {
          str = localJSONObject2.getString("next_results");
          this.nextResults = str;
          this.query = z_T4JInternalParseUtil.getURLDecodedString("query", localJSONObject2);
          this.refreshUrl = z_T4JInternalParseUtil.getUnescapedString("refresh_url", localJSONObject2);
          this.sinceId = z_T4JInternalParseUtil.getLong("since_id", localJSONObject2);
          JSONArray localJSONArray = localJSONObject1.getJSONArray("statuses");
          this.tweets = new ArrayList(localJSONArray.length());
          if (!paramConfiguration.isJSONStoreEnabled()) {
            break label242;
          }
          DataObjectFactoryUtil.clearThreadLocalMap();
          break label242;
          if (i < localJSONArray.length())
          {
            JSONObject localJSONObject3 = localJSONArray.getJSONObject(i);
            this.tweets.add(new StatusJSONImpl(localJSONObject3, paramConfiguration));
            i++;
            continue;
          }
        }
        else
        {
          str = null;
          continue;
        }
        return;
      }
      catch (JSONException localJSONException)
      {
        throw new TwitterException(localJSONException.getMessage() + ":" + localJSONObject1.toString(), localJSONException);
      }
      label242:
      int i = 0;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    QueryResult localQueryResult;
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
          localQueryResult = (QueryResult)paramObject;
          if (Double.compare(localQueryResult.getCompletedIn(), this.completedIn) != 0)
          {
            bool = false;
          }
          else if (this.maxId != localQueryResult.getMaxId())
          {
            bool = false;
          }
          else if (this.count != localQueryResult.getCount())
          {
            bool = false;
          }
          else if (this.sinceId != localQueryResult.getSinceId())
          {
            bool = false;
          }
          else
          {
            if (this.query.equals(localQueryResult.getQuery())) {
              break;
            }
            bool = false;
          }
        }
      }
      if (this.refreshUrl != null)
      {
        if (this.refreshUrl.equals(localQueryResult.getRefreshUrl())) {}
      }
      else {
        while (localQueryResult.getRefreshUrl() != null)
        {
          bool = false;
          break;
        }
      }
      if (this.tweets == null) {
        break;
      }
    } while (this.tweets.equals(localQueryResult.getTweets()));
    for (;;)
    {
      bool = false;
      break;
      if (localQueryResult.getTweets() == null) {
        break;
      }
    }
  }
  
  public double getCompletedIn()
  {
    return this.completedIn;
  }
  
  public int getCount()
  {
    return this.count;
  }
  
  public long getMaxId()
  {
    return this.maxId;
  }
  
  public String getQuery()
  {
    return this.query;
  }
  
  public String getRefreshURL()
  {
    return this.refreshUrl;
  }
  
  public String getRefreshUrl()
  {
    return getRefreshURL();
  }
  
  public long getSinceId()
  {
    return this.sinceId;
  }
  
  public List<Status> getTweets()
  {
    return this.tweets;
  }
  
  public boolean hasNext()
  {
    if (this.nextResults != null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public int hashCode()
  {
    int i = 0;
    int j = 31 * (31 * (int)(this.sinceId ^ this.sinceId >>> 32) + (int)(this.maxId ^ this.maxId >>> 32));
    int k;
    int m;
    if (this.refreshUrl != null)
    {
      k = this.refreshUrl.hashCode();
      m = 31 * (j + k) + this.count;
      if (this.completedIn == 0.0D) {
        break label140;
      }
    }
    label140:
    for (long l = Double.doubleToLongBits(this.completedIn);; l = 0L)
    {
      int n = 31 * (31 * (m * 31 + (int)(l ^ l >>> 32)) + this.query.hashCode());
      if (this.tweets != null) {
        i = this.tweets.hashCode();
      }
      return n + i;
      k = 0;
      break;
    }
  }
  
  public Query nextQuery()
  {
    Query localQuery = null;
    if (this.nextResults == null) {}
    for (;;)
    {
      return localQuery;
      try
      {
        Method localMethod = queryFactoryMethod;
        String[] arrayOfString = new String[1];
        arrayOfString[0] = this.nextResults;
        localQuery = (Query)localMethod.invoke(null, arrayOfString);
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        throw new RuntimeException(localIllegalAccessException);
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new RuntimeException(localInvocationTargetException);
      }
    }
  }
  
  public String toString()
  {
    return "QueryResultJSONImpl{sinceId=" + this.sinceId + ", maxId=" + this.maxId + ", refreshUrl='" + this.refreshUrl + '\'' + ", count=" + this.count + ", completedIn=" + this.completedIn + ", query='" + this.query + '\'' + ", tweets=" + this.tweets + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.QueryResultJSONImpl
 * JD-Core Version:    0.7.0.1
 */