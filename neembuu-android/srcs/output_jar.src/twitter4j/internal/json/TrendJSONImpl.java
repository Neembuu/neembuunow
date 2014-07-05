package twitter4j.internal.json;

import java.io.Serializable;
import twitter4j.Trend;
import twitter4j.internal.org.json.JSONObject;

final class TrendJSONImpl
  implements Trend, Serializable
{
  private static final long serialVersionUID = 1925956704460743946L;
  private String name;
  private String query = null;
  private String url = null;
  
  TrendJSONImpl(JSONObject paramJSONObject)
  {
    this(paramJSONObject, false);
  }
  
  TrendJSONImpl(JSONObject paramJSONObject, boolean paramBoolean)
  {
    this.name = z_T4JInternalParseUtil.getRawString("name", paramJSONObject);
    this.url = z_T4JInternalParseUtil.getRawString("url", paramJSONObject);
    this.query = z_T4JInternalParseUtil.getRawString("query", paramJSONObject);
    if (paramBoolean) {
      DataObjectFactoryUtil.registerJSONObject(this, paramJSONObject);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    Trend localTrend;
    do
    {
      for (;;)
      {
        return bool;
        if (!(paramObject instanceof Trend))
        {
          bool = false;
        }
        else
        {
          localTrend = (Trend)paramObject;
          if (this.name.equals(localTrend.getName())) {
            break;
          }
          bool = false;
        }
      }
      if (this.query != null)
      {
        if (this.query.equals(localTrend.getQuery())) {}
      }
      else {
        while (localTrend.getQuery() != null)
        {
          bool = false;
          break;
        }
      }
      if (this.url == null) {
        break;
      }
    } while (this.url.equals(localTrend.getURL()));
    for (;;)
    {
      bool = false;
      break;
      if (localTrend.getURL() == null) {
        break;
      }
    }
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getQuery()
  {
    return this.query;
  }
  
  public String getURL()
  {
    return this.url;
  }
  
  public String getUrl()
  {
    return getURL();
  }
  
  public int hashCode()
  {
    int i = 0;
    int j = 31 * this.name.hashCode();
    if (this.url != null) {}
    for (int k = this.url.hashCode();; k = 0)
    {
      int m = 31 * (j + k);
      if (this.query != null) {
        i = this.query.hashCode();
      }
      return m + i;
    }
  }
  
  public String toString()
  {
    return "TrendJSONImpl{name='" + this.name + '\'' + ", url='" + this.url + '\'' + ", query='" + this.query + '\'' + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.TrendJSONImpl
 * JD-Core Version:    0.7.0.1
 */