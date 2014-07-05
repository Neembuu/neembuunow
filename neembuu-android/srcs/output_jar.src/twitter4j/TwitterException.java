package twitter4j;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.http.HttpResponseCode;
import twitter4j.internal.json.z_T4JInternalJSONImplFactory;
import twitter4j.internal.json.z_T4JInternalParseUtil;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

public class TwitterException
  extends Exception
  implements TwitterResponse, HttpResponseCode
{
  private static final String[] FILTER;
  private static final long serialVersionUID = -2623309261327598087L;
  private int errorCode = -1;
  private String errorMessage = null;
  private ExceptionDiagnosis exceptionDiagnosis = null;
  boolean nested = false;
  private HttpResponse response;
  private int statusCode = -1;
  
  static
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = "twitter4j";
    FILTER = arrayOfString;
  }
  
  public TwitterException(Exception paramException)
  {
    this(paramException.getMessage(), paramException);
    if ((paramException instanceof TwitterException)) {
      ((TwitterException)paramException).setNested();
    }
  }
  
  public TwitterException(String paramString)
  {
    this(paramString, (Throwable)null);
  }
  
  public TwitterException(String paramString, Exception paramException, int paramInt)
  {
    this(paramString, paramException);
    this.statusCode = paramInt;
  }
  
  public TwitterException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
    decode(paramString);
  }
  
  public TwitterException(String paramString, HttpResponse paramHttpResponse)
  {
    this(paramString);
    this.response = paramHttpResponse;
    this.statusCode = paramHttpResponse.getStatusCode();
  }
  
  private void decode(String paramString)
  {
    if ((paramString != null) && (paramString.startsWith("{"))) {}
    try
    {
      JSONObject localJSONObject1 = new JSONObject(paramString);
      if (!localJSONObject1.isNull("errors"))
      {
        JSONObject localJSONObject2 = localJSONObject1.getJSONArray("errors").getJSONObject(0);
        this.errorMessage = localJSONObject2.getString("message");
        this.errorCode = z_T4JInternalParseUtil.getInt("code", localJSONObject2);
      }
      label65:
      return;
    }
    catch (JSONException localJSONException)
    {
      break label65;
    }
  }
  
  private static String getCause(int paramInt)
  {
    String str;
    switch (paramInt)
    {
    default: 
      str = "";
    }
    for (;;)
    {
      return paramInt + ":" + str;
      str = "There was no new data to return.";
      continue;
      str = "The request was invalid. An accompanying error message will explain why. This is the status code will be returned during version 1.0 rate limiting(https://dev.twitter.com/pages/rate-limiting). In API v1.1, a request without authentication is considered invalid and you will get this response.";
      continue;
      str = "Authentication credentials (https://dev.twitter.com/pages/auth) were missing or incorrect. Ensure that you have set valid consumer key/secret, access token/secret, and the system clock is in sync.";
      continue;
      str = "The request is understood, but it has been refused. An accompanying error message will explain why. This code is used when requests are being denied due to update limits (https://support.twitter.com/articles/15364-about-twitter-limits-update-api-dm-and-following).";
      continue;
      str = "The URI requested is invalid or the resource requested, such as a user, does not exists. Also returned when the requested format is not supported by the requested method.";
      continue;
      str = "Returned by the Search API when an invalid format is specified in the request.\nReturned by the Streaming API when one or more of the parameters are not suitable for the resource. The track parameter, for example, would throw this error if:\n The track keyword is too long or too short.\n The bounding box specified is invalid.\n No predicates defined for filtered resource, for example, neither track nor follow parameter defined.\n Follow userid cannot be read.";
      continue;
      str = "Returned by the Search and Trends API when you are being rate limited (https://dev.twitter.com/docs/rate-limiting).\nReturned by the Streaming API:\n Too many login attempts in a short period of time.\n Running too many copies of the same application authenticating with the same account name.";
      continue;
      str = "Returned when an image uploaded to POST account/update_profile_banner(https://dev.twitter.com/docs/api/1/post/account/update_profile_banner) is unable to be processed.";
      continue;
      str = "Returned in API v1.1 when a request cannot be served due to the application's rate limit having been exhausted for the resource. See Rate Limiting in API v1.1.(https://dev.twitter.com/docs/rate-limiting/1.1)";
      continue;
      str = "Something is broken. Please post to the group (https://dev.twitter.com/docs/support) so the Twitter team can investigate.";
      continue;
      str = "Twitter is down or being upgraded.";
      continue;
      str = "The Twitter servers are up, but overloaded with requests. Try again later.";
      continue;
      str = "The Twitter servers are up, but the request couldn't be serviced due to some failure within our stack. Try again later.";
    }
  }
  
  private ExceptionDiagnosis getExceptionDiagnosis()
  {
    if (this.exceptionDiagnosis == null) {
      this.exceptionDiagnosis = new ExceptionDiagnosis(this, FILTER);
    }
    return this.exceptionDiagnosis;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    TwitterException localTwitterException;
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
          localTwitterException = (TwitterException)paramObject;
          if (this.errorCode != localTwitterException.errorCode)
          {
            bool = false;
          }
          else if (this.nested != localTwitterException.nested)
          {
            bool = false;
          }
          else
          {
            if (this.statusCode == localTwitterException.statusCode) {
              break;
            }
            bool = false;
          }
        }
      }
      if (this.errorMessage != null)
      {
        if (this.errorMessage.equals(localTwitterException.errorMessage)) {}
      }
      else {
        while (localTwitterException.errorMessage != null)
        {
          bool = false;
          break;
        }
      }
      if (this.exceptionDiagnosis != null)
      {
        if (this.exceptionDiagnosis.equals(localTwitterException.exceptionDiagnosis)) {}
      }
      else {
        while (localTwitterException.exceptionDiagnosis != null)
        {
          bool = false;
          break;
        }
      }
      if (this.response == null) {
        break;
      }
    } while (this.response.equals(localTwitterException.response));
    for (;;)
    {
      bool = false;
      break;
      if (localTwitterException.response == null) {
        break;
      }
    }
  }
  
  public boolean exceededRateLimitation()
  {
    if (((this.statusCode == 400) && (getRateLimitStatus() != null)) || (this.statusCode == 420) || (this.statusCode == 429)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public int getAccessLevel()
  {
    return z_T4JInternalParseUtil.toAccessLevel(this.response);
  }
  
  public int getErrorCode()
  {
    return this.errorCode;
  }
  
  public String getErrorMessage()
  {
    return this.errorMessage;
  }
  
  public String getExceptionCode()
  {
    return getExceptionDiagnosis().asHexString();
  }
  
  public String getMessage()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ((this.errorMessage != null) && (this.errorCode != -1))
    {
      localStringBuilder.append("message - ").append(this.errorMessage).append("\n");
      localStringBuilder.append("code - ").append(this.errorCode).append("\n");
      if (this.statusCode == -1) {
        break label118;
      }
    }
    label118:
    for (String str = getCause(this.statusCode) + "\n" + localStringBuilder.toString();; str = localStringBuilder.toString())
    {
      return str;
      localStringBuilder.append(super.getMessage());
      break;
    }
  }
  
  public RateLimitStatus getRateLimitStatus()
  {
    if (this.response == null) {}
    for (RateLimitStatus localRateLimitStatus = null;; localRateLimitStatus = z_T4JInternalJSONImplFactory.createRateLimitStatusFromResponseHeader(this.response)) {
      return localRateLimitStatus;
    }
  }
  
  public String getResponseHeader(String paramString)
  {
    String str = null;
    if (this.response != null)
    {
      List localList = (List)this.response.getResponseHeaderFields().get(paramString);
      if (localList.size() > 0) {
        str = (String)localList.get(0);
      }
    }
    return str;
  }
  
  public int getRetryAfter()
  {
    int i = -1;
    if (this.statusCode == 400)
    {
      RateLimitStatus localRateLimitStatus = getRateLimitStatus();
      if (localRateLimitStatus != null) {
        i = localRateLimitStatus.getSecondsUntilReset();
      }
    }
    for (;;)
    {
      return i;
      if (this.statusCode == 420) {
        try
        {
          String str = this.response.getResponseHeader("Retry-After");
          if (str != null)
          {
            int j = Integer.valueOf(str).intValue();
            i = j;
          }
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
    }
  }
  
  public int getStatusCode()
  {
    return this.statusCode;
  }
  
  public int hashCode()
  {
    int i = 0;
    int j = 31 * (31 * this.statusCode + this.errorCode);
    int k;
    int n;
    label57:
    int i1;
    if (this.exceptionDiagnosis != null)
    {
      k = this.exceptionDiagnosis.hashCode();
      int m = 31 * (j + k);
      if (this.response == null) {
        break label112;
      }
      n = this.response.hashCode();
      i1 = 31 * (m + n);
      if (this.errorMessage == null) {
        break label118;
      }
    }
    label112:
    label118:
    for (int i2 = this.errorMessage.hashCode();; i2 = 0)
    {
      int i3 = 31 * (i1 + i2);
      if (this.nested) {
        i = 1;
      }
      return i3 + i;
      k = 0;
      break;
      n = 0;
      break label57;
    }
  }
  
  public boolean isCausedByNetworkIssue()
  {
    return getCause() instanceof IOException;
  }
  
  public boolean isErrorMessageAvailable()
  {
    if (this.errorMessage != null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public boolean resourceNotFound()
  {
    if (this.statusCode == 404) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  void setNested()
  {
    this.nested = true;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder1 = new StringBuilder().append(getMessage());
    String str1;
    StringBuilder localStringBuilder2;
    if (this.nested)
    {
      str1 = "";
      localStringBuilder2 = localStringBuilder1.append(str1).append("\nTwitterException{");
      if (!this.nested) {
        break label186;
      }
    }
    label186:
    for (String str2 = "";; str2 = "exceptionCode=[" + getExceptionCode() + "], ")
    {
      return str2 + "statusCode=" + this.statusCode + ", message=" + this.errorMessage + ", code=" + this.errorCode + ", retryAfter=" + getRetryAfter() + ", rateLimitStatus=" + getRateLimitStatus() + ", version=" + Version.getVersion() + '}';
      str1 = "\nRelevant discussions can be found on the Internet at:\n\thttp://www.google.co.jp/search?q=" + getExceptionDiagnosis().getStackLineHashAsHex() + " or\n\thttp://www.google.co.jp/search?q=" + getExceptionDiagnosis().getLineNumberHashAsHex();
      break;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.TwitterException
 * JD-Core Version:    0.7.0.1
 */