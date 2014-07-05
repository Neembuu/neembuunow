package twitter4j.internal.json;

import twitter4j.PagableResponseList;
import twitter4j.RateLimitStatus;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONObject;

class PagableResponseListImpl<T>
  extends ResponseListImpl
  implements PagableResponseList
{
  private static final long serialVersionUID = 1531950333538983361L;
  private final long nextCursor;
  private final long previousCursor;
  
  PagableResponseListImpl(int paramInt, JSONObject paramJSONObject, HttpResponse paramHttpResponse)
  {
    super(paramInt, paramHttpResponse);
    this.previousCursor = z_T4JInternalParseUtil.getLong("previous_cursor", paramJSONObject);
    this.nextCursor = z_T4JInternalParseUtil.getLong("next_cursor", paramJSONObject);
  }
  
  PagableResponseListImpl(RateLimitStatus paramRateLimitStatus, int paramInt)
  {
    super(paramRateLimitStatus, paramInt);
    this.previousCursor = 0L;
    this.nextCursor = 0L;
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
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.PagableResponseListImpl
 * JD-Core Version:    0.7.0.1
 */