package twitter4j.internal.json;

import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

final class URLEntityJSONImpl
  extends EntityIndex
  implements URLEntity
{
  private static final long serialVersionUID = -8948472760821379376L;
  private String displayURL;
  private String expandedURL;
  private String url;
  
  URLEntityJSONImpl() {}
  
  URLEntityJSONImpl(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3)
  {
    setStart(paramInt1);
    setEnd(paramInt2);
    this.url = paramString1;
    this.expandedURL = paramString2;
    this.displayURL = paramString3;
  }
  
  URLEntityJSONImpl(JSONObject paramJSONObject)
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
      this.url = paramJSONObject.getString("url");
      if (!paramJSONObject.isNull("expanded_url")) {}
      for (this.expandedURL = paramJSONObject.getString("expanded_url"); !paramJSONObject.isNull("display_url"); this.expandedURL = this.url)
      {
        this.displayURL = paramJSONObject.getString("display_url");
        return;
      }
      this.displayURL = this.url;
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
    URLEntityJSONImpl localURLEntityJSONImpl;
    do
    {
      for (;;)
      {
        return bool;
        if ((paramObject != null) && (getClass() == paramObject.getClass())) {
          break;
        }
        bool = false;
      }
      localURLEntityJSONImpl = (URLEntityJSONImpl)paramObject;
      if (this.displayURL != null)
      {
        if (this.displayURL.equals(localURLEntityJSONImpl.displayURL)) {}
      }
      else {
        while (localURLEntityJSONImpl.displayURL != null)
        {
          bool = false;
          break;
        }
      }
      if (this.expandedURL != null)
      {
        if (this.expandedURL.equals(localURLEntityJSONImpl.expandedURL)) {}
      }
      else {
        while (localURLEntityJSONImpl.expandedURL != null)
        {
          bool = false;
          break;
        }
      }
      if (this.url == null) {
        break;
      }
    } while (this.url.equals(localURLEntityJSONImpl.url));
    for (;;)
    {
      bool = false;
      break;
      if (localURLEntityJSONImpl.url == null) {
        break;
      }
    }
  }
  
  public String getDisplayURL()
  {
    return this.displayURL;
  }
  
  public int getEnd()
  {
    return super.getEnd();
  }
  
  public String getExpandedURL()
  {
    return this.expandedURL;
  }
  
  public int getStart()
  {
    return super.getStart();
  }
  
  public String getText()
  {
    return this.url;
  }
  
  public String getURL()
  {
    return this.url;
  }
  
  public int hashCode()
  {
    int i = 0;
    int j;
    int k;
    if (this.url != null)
    {
      j = this.url.hashCode();
      k = j * 31;
      if (this.expandedURL == null) {
        break label72;
      }
    }
    label72:
    for (int m = this.expandedURL.hashCode();; m = 0)
    {
      int n = 31 * (k + m);
      if (this.displayURL != null) {
        i = this.displayURL.hashCode();
      }
      return n + i;
      j = 0;
      break;
    }
  }
  
  public String toString()
  {
    return "URLEntityJSONImpl{url='" + this.url + '\'' + ", expandedURL='" + this.expandedURL + '\'' + ", displayURL='" + this.displayURL + '\'' + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.URLEntityJSONImpl
 * JD-Core Version:    0.7.0.1
 */