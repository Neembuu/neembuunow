package twitter4j.internal.json;

import twitter4j.HashtagEntity;
import twitter4j.SymbolEntity;
import twitter4j.TwitterException;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

class HashtagEntityJSONImpl
  extends EntityIndex
  implements HashtagEntity, SymbolEntity
{
  private static final long serialVersionUID = 4068992372784813200L;
  private String text;
  
  HashtagEntityJSONImpl() {}
  
  HashtagEntityJSONImpl(int paramInt1, int paramInt2, String paramString)
  {
    setStart(paramInt1);
    setEnd(paramInt2);
    this.text = paramString;
  }
  
  HashtagEntityJSONImpl(JSONObject paramJSONObject)
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
      if (!paramJSONObject.isNull("text")) {
        this.text = paramJSONObject.getString("text");
      }
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
    HashtagEntityJSONImpl localHashtagEntityJSONImpl;
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
      localHashtagEntityJSONImpl = (HashtagEntityJSONImpl)paramObject;
      if (this.text == null) {
        break;
      }
    } while (this.text.equals(localHashtagEntityJSONImpl.text));
    for (;;)
    {
      bool = false;
      break;
      if (localHashtagEntityJSONImpl.text == null) {
        break;
      }
    }
  }
  
  public int getEnd()
  {
    return super.getEnd();
  }
  
  public int getStart()
  {
    return super.getStart();
  }
  
  public String getText()
  {
    return this.text;
  }
  
  public int hashCode()
  {
    if (this.text != null) {}
    for (int i = this.text.hashCode();; i = 0) {
      return i;
    }
  }
  
  public String toString()
  {
    return "HashtagEntityJSONImpl{text='" + this.text + '\'' + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.HashtagEntityJSONImpl
 * JD-Core Version:    0.7.0.1
 */