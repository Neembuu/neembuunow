package twitter4j.internal.json;

import java.util.ArrayList;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.internal.http.HttpResponse;

class ResponseListImpl<T>
  extends ArrayList<T>
  implements ResponseList<T>
{
  private static final long serialVersionUID = 5646617841989265312L;
  private transient int accessLevel;
  private transient RateLimitStatus rateLimitStatus = null;
  
  ResponseListImpl(int paramInt, HttpResponse paramHttpResponse)
  {
    super(paramInt);
    init(paramHttpResponse);
  }
  
  ResponseListImpl(RateLimitStatus paramRateLimitStatus, int paramInt)
  {
    this.rateLimitStatus = paramRateLimitStatus;
    this.accessLevel = paramInt;
  }
  
  ResponseListImpl(HttpResponse paramHttpResponse)
  {
    init(paramHttpResponse);
  }
  
  private void init(HttpResponse paramHttpResponse)
  {
    this.rateLimitStatus = RateLimitStatusJSONImpl.createFromResponseHeader(paramHttpResponse);
    this.accessLevel = z_T4JInternalParseUtil.toAccessLevel(paramHttpResponse);
  }
  
  public int getAccessLevel()
  {
    return this.accessLevel;
  }
  
  public RateLimitStatus getRateLimitStatus()
  {
    return this.rateLimitStatus;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.ResponseListImpl
 * JD-Core Version:    0.7.0.1
 */