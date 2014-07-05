package twitter4j.internal.json;

import java.io.Serializable;
import twitter4j.OEmbed;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

public class OEmbedJSONImpl
  extends TwitterResponseImpl
  implements OEmbed, Serializable
{
  private static final long serialVersionUID = -675438169712979958L;
  private String authorName;
  private String authorURL;
  private long cacheAge;
  private String html;
  private String url;
  private String version;
  private int width;
  
  OEmbedJSONImpl(HttpResponse paramHttpResponse, Configuration paramConfiguration)
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
  
  OEmbedJSONImpl(JSONObject paramJSONObject)
    throws TwitterException
  {
    init(paramJSONObject);
  }
  
  private void init(JSONObject paramJSONObject)
    throws TwitterException
  {
    try
    {
      this.html = paramJSONObject.getString("html");
      this.authorName = paramJSONObject.getString("author_name");
      this.url = paramJSONObject.getString("url");
      this.version = paramJSONObject.getString("version");
      this.cacheAge = paramJSONObject.getLong("cache_age");
      this.authorURL = paramJSONObject.getString("author_url");
      this.width = paramJSONObject.getInt("width");
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
    OEmbedJSONImpl localOEmbedJSONImpl;
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
          localOEmbedJSONImpl = (OEmbedJSONImpl)paramObject;
          if (this.cacheAge != localOEmbedJSONImpl.cacheAge)
          {
            bool = false;
          }
          else
          {
            if (this.width == localOEmbedJSONImpl.width) {
              break;
            }
            bool = false;
          }
        }
      }
      if (this.authorName != null)
      {
        if (this.authorName.equals(localOEmbedJSONImpl.authorName)) {}
      }
      else {
        while (localOEmbedJSONImpl.authorName != null)
        {
          bool = false;
          break;
        }
      }
      if (this.authorURL != null)
      {
        if (this.authorURL.equals(localOEmbedJSONImpl.authorURL)) {}
      }
      else {
        while (localOEmbedJSONImpl.authorURL != null)
        {
          bool = false;
          break;
        }
      }
      if (this.html != null)
      {
        if (this.html.equals(localOEmbedJSONImpl.html)) {}
      }
      else {
        while (localOEmbedJSONImpl.html != null)
        {
          bool = false;
          break;
        }
      }
      if (this.url != null)
      {
        if (this.url.equals(localOEmbedJSONImpl.url)) {}
      }
      else {
        while (localOEmbedJSONImpl.url != null)
        {
          bool = false;
          break;
        }
      }
      if (this.version == null) {
        break;
      }
    } while (this.version.equals(localOEmbedJSONImpl.version));
    for (;;)
    {
      bool = false;
      break;
      if (localOEmbedJSONImpl.version == null) {
        break;
      }
    }
  }
  
  public String getAuthorName()
  {
    return this.authorName;
  }
  
  public String getAuthorURL()
  {
    return this.authorURL;
  }
  
  public long getCacheAge()
  {
    return this.cacheAge;
  }
  
  public String getHtml()
  {
    return this.html;
  }
  
  public String getURL()
  {
    return this.url;
  }
  
  public String getVersion()
  {
    return this.version;
  }
  
  public int getWidth()
  {
    return this.width;
  }
  
  public int hashCode()
  {
    int i = 0;
    int j;
    int m;
    label38:
    int i1;
    label63:
    int i2;
    if (this.html != null)
    {
      j = this.html.hashCode();
      int k = j * 31;
      if (this.authorName == null) {
        break label149;
      }
      m = this.authorName.hashCode();
      int n = 31 * (k + m);
      if (this.url == null) {
        break label155;
      }
      i1 = this.url.hashCode();
      i2 = 31 * (n + i1);
      if (this.version == null) {
        break label161;
      }
    }
    label149:
    label155:
    label161:
    for (int i3 = this.version.hashCode();; i3 = 0)
    {
      int i4 = 31 * (31 * (i2 + i3) + (int)(this.cacheAge ^ this.cacheAge >>> 32));
      if (this.authorURL != null) {
        i = this.authorURL.hashCode();
      }
      return 31 * (i4 + i) + this.width;
      j = 0;
      break;
      m = 0;
      break label38;
      i1 = 0;
      break label63;
    }
  }
  
  public String toString()
  {
    return "OEmbedJSONImpl{html='" + this.html + '\'' + ", authorName='" + this.authorName + '\'' + ", url='" + this.url + '\'' + ", version='" + this.version + '\'' + ", cacheAge=" + this.cacheAge + ", authorURL='" + this.authorURL + '\'' + ", width=" + this.width + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.OEmbedJSONImpl
 * JD-Core Version:    0.7.0.1
 */