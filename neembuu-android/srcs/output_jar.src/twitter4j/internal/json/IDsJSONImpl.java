package twitter4j.internal.json;

import java.util.Arrays;
import twitter4j.IDs;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

final class IDsJSONImpl
  extends TwitterResponseImpl
  implements IDs
{
  private static final long serialVersionUID = -6585026560164704953L;
  private long[] ids;
  private long nextCursor = -1L;
  private long previousCursor = -1L;
  
  IDsJSONImpl(String paramString)
    throws TwitterException
  {
    init(paramString);
  }
  
  IDsJSONImpl(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    super(paramHttpResponse);
    String str = paramHttpResponse.asString();
    init(str);
    if (paramConfiguration.isJSONStoreEnabled())
    {
      DataObjectFactoryUtil.clearThreadLocalMap();
      DataObjectFactoryUtil.registerJSONObject(this, str);
    }
  }
  
  private void init(String paramString)
    throws TwitterException
  {
    JSONObject localJSONObject;
    try
    {
      if (!paramString.startsWith("{")) {
        break label141;
      }
      localJSONObject = new JSONObject(paramString);
      JSONArray localJSONArray1 = localJSONObject.getJSONArray("ids");
      this.ids = new long[localJSONArray1.length()];
      int i = 0;
      for (;;)
      {
        int j = localJSONArray1.length();
        if (i < j) {
          try
          {
            this.ids[i] = Long.parseLong(localJSONArray1.getString(i));
            i++;
          }
          catch (NumberFormatException localNumberFormatException1)
          {
            throw new TwitterException("Twitter API returned malformed response: " + localJSONObject, localNumberFormatException1);
          }
        }
      }
      this.previousCursor = z_T4JInternalParseUtil.getLong("previous_cursor", localJSONObject);
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
    this.nextCursor = z_T4JInternalParseUtil.getLong("next_cursor", localJSONObject);
    return;
    label141:
    JSONArray localJSONArray2 = new JSONArray(paramString);
    this.ids = new long[localJSONArray2.length()];
    int k = 0;
    for (;;)
    {
      int m = localJSONArray2.length();
      if (k < m) {
        try
        {
          this.ids[k] = Long.parseLong(localJSONArray2.getString(k));
          k++;
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          throw new TwitterException("Twitter API returned malformed response: " + localJSONArray2, localNumberFormatException2);
        }
      }
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    for (;;)
    {
      return bool;
      if (!(paramObject instanceof IDs))
      {
        bool = false;
      }
      else
      {
        IDs localIDs = (IDs)paramObject;
        if (!Arrays.equals(this.ids, localIDs.getIDs())) {
          bool = false;
        }
      }
    }
  }
  
  public long[] getIDs()
  {
    return this.ids;
  }
  
  public long getNextCursor()
  {
    return this.nextCursor;
  }
  
  public long getPreviousCursor()
  {
    return this.previousCursor;
  }
  
  public boolean hasNext()
  {
    if (0L != this.nextCursor) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public boolean hasPrevious()
  {
    if (0L != this.previousCursor) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public int hashCode()
  {
    if (this.ids != null) {}
    for (int i = Arrays.hashCode(this.ids);; i = 0) {
      return i;
    }
  }
  
  public String toString()
  {
    return "IDsJSONImpl{ids=" + Arrays.toString(this.ids) + ", previousCursor=" + this.previousCursor + ", nextCursor=" + this.nextCursor + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.IDsJSONImpl
 * JD-Core Version:    0.7.0.1
 */