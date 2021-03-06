package twitter4j.internal.json;

import twitter4j.TimeZone;
import twitter4j.TwitterException;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

public class TimeZoneJSONImpl
  implements TimeZone
{
  private final String NAME;
  private final String TZINFO_NAME;
  private final int UTC_OFFSET;
  
  TimeZoneJSONImpl(JSONObject paramJSONObject)
    throws TwitterException
  {
    try
    {
      this.UTC_OFFSET = z_T4JInternalParseUtil.getInt("utc_offset", paramJSONObject);
      this.NAME = paramJSONObject.getString("name");
      this.TZINFO_NAME = paramJSONObject.getString("tzinfo_name");
      return;
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException);
    }
  }
  
  public String getName()
  {
    return this.NAME;
  }
  
  public String tzinfoName()
  {
    return this.TZINFO_NAME;
  }
  
  public int utcOffset()
  {
    return this.UTC_OFFSET;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.TimeZoneJSONImpl
 * JD-Core Version:    0.7.0.1
 */