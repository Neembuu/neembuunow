package twitter4j.internal.json;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import twitter4j.TwitterException;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

public final class z_T4JInternalParseUtil
{
  private static final Map<String, LinkedBlockingQueue<SimpleDateFormat>> formatMapQueue = new HashMap();
  
  private z_T4JInternalParseUtil()
  {
    throw new AssertionError();
  }
  
  public static boolean getBoolean(String paramString, JSONObject paramJSONObject)
  {
    String str = getRawString(paramString, paramJSONObject);
    if ((str == null) || ("null".equals(str))) {}
    for (boolean bool = false;; bool = Boolean.valueOf(str).booleanValue()) {
      return bool;
    }
  }
  
  /* Error */
  public static Date getDate(String paramString1, String paramString2)
    throws TwitterException
  {
    // Byte code:
    //   0: getstatic 16	twitter4j/internal/json/z_T4JInternalParseUtil:formatMapQueue	Ljava/util/Map;
    //   3: aload_1
    //   4: invokeinterface 58 2 0
    //   9: checkcast 60	java/util/concurrent/LinkedBlockingQueue
    //   12: astore_2
    //   13: aload_2
    //   14: ifnonnull +22 -> 36
    //   17: new 60	java/util/concurrent/LinkedBlockingQueue
    //   20: dup
    //   21: invokespecial 61	java/util/concurrent/LinkedBlockingQueue:<init>	()V
    //   24: astore_2
    //   25: getstatic 16	twitter4j/internal/json/z_T4JInternalParseUtil:formatMapQueue	Ljava/util/Map;
    //   28: aload_1
    //   29: aload_2
    //   30: invokeinterface 65 3 0
    //   35: pop
    //   36: aload_2
    //   37: invokevirtual 69	java/util/concurrent/LinkedBlockingQueue:poll	()Ljava/lang/Object;
    //   40: checkcast 71	java/text/SimpleDateFormat
    //   43: astore 4
    //   45: aload 4
    //   47: ifnonnull +26 -> 73
    //   50: new 71	java/text/SimpleDateFormat
    //   53: dup
    //   54: aload_1
    //   55: getstatic 77	java/util/Locale:US	Ljava/util/Locale;
    //   58: invokespecial 80	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;Ljava/util/Locale;)V
    //   61: astore 4
    //   63: aload 4
    //   65: ldc 82
    //   67: invokestatic 88	java/util/TimeZone:getTimeZone	(Ljava/lang/String;)Ljava/util/TimeZone;
    //   70: invokevirtual 92	java/text/SimpleDateFormat:setTimeZone	(Ljava/util/TimeZone;)V
    //   73: aload 4
    //   75: aload_0
    //   76: invokevirtual 96	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   79: astore 8
    //   81: aload_2
    //   82: aload 4
    //   84: invokevirtual 99	java/util/concurrent/LinkedBlockingQueue:put	(Ljava/lang/Object;)V
    //   87: aload 8
    //   89: areturn
    //   90: astore 7
    //   92: new 48	twitter4j/TwitterException
    //   95: dup
    //   96: new 101	java/lang/StringBuilder
    //   99: dup
    //   100: invokespecial 102	java/lang/StringBuilder:<init>	()V
    //   103: ldc 104
    //   105: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   108: aload_0
    //   109: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: ldc 110
    //   114: invokevirtual 108	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   117: invokevirtual 114	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   120: aload 7
    //   122: invokespecial 117	twitter4j/TwitterException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   125: athrow
    //   126: astore 5
    //   128: aload_2
    //   129: aload 4
    //   131: invokevirtual 99	java/util/concurrent/LinkedBlockingQueue:put	(Ljava/lang/Object;)V
    //   134: aload 5
    //   136: athrow
    //   137: astore 9
    //   139: goto -52 -> 87
    //   142: astore 6
    //   144: goto -10 -> 134
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	147	0	paramString1	String
    //   0	147	1	paramString2	String
    //   12	117	2	localLinkedBlockingQueue	LinkedBlockingQueue
    //   43	87	4	localSimpleDateFormat	SimpleDateFormat
    //   126	9	5	localObject	Object
    //   142	1	6	localInterruptedException1	java.lang.InterruptedException
    //   90	31	7	localParseException	java.text.ParseException
    //   79	9	8	localDate	Date
    //   137	1	9	localInterruptedException2	java.lang.InterruptedException
    // Exception table:
    //   from	to	target	type
    //   73	81	90	java/text/ParseException
    //   73	81	126	finally
    //   92	126	126	finally
    //   81	87	137	java/lang/InterruptedException
    //   128	134	142	java/lang/InterruptedException
  }
  
  public static Date getDate(String paramString, JSONObject paramJSONObject)
    throws TwitterException
  {
    return getDate(paramString, paramJSONObject, "EEE MMM d HH:mm:ss z yyyy");
  }
  
  public static Date getDate(String paramString1, JSONObject paramJSONObject, String paramString2)
    throws TwitterException
  {
    String str = getUnescapedString(paramString1, paramJSONObject);
    if (("null".equals(str)) || (str == null)) {}
    for (Date localDate = null;; localDate = getDate(str, paramString2)) {
      return localDate;
    }
  }
  
  public static double getDouble(String paramString, JSONObject paramJSONObject)
  {
    String str = getRawString(paramString, paramJSONObject);
    if ((str == null) || ("".equals(str)) || ("null".equals(str))) {}
    for (double d = -1.0D;; d = Double.valueOf(str).doubleValue()) {
      return d;
    }
  }
  
  public static int getInt(String paramString)
  {
    int i = -1;
    if ((paramString == null) || ("".equals(paramString)) || ("null".equals(paramString))) {}
    for (;;)
    {
      return i;
      try
      {
        int j = Integer.valueOf(paramString).intValue();
        i = j;
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
  }
  
  public static int getInt(String paramString, JSONObject paramJSONObject)
  {
    return getInt(getRawString(paramString, paramJSONObject));
  }
  
  public static long getLong(String paramString)
  {
    long l;
    if ((paramString == null) || ("".equals(paramString)) || ("null".equals(paramString))) {
      l = -1L;
    }
    for (;;)
    {
      return l;
      if (paramString.endsWith("+")) {
        l = 1L + Long.valueOf(paramString.substring(0, -1 + paramString.length())).longValue();
      } else {
        l = Long.valueOf(paramString).longValue();
      }
    }
  }
  
  public static long getLong(String paramString, JSONObject paramJSONObject)
  {
    return getLong(getRawString(paramString, paramJSONObject));
  }
  
  public static String getRawString(String paramString, JSONObject paramJSONObject)
  {
    Object localObject = null;
    try
    {
      if (!paramJSONObject.isNull(paramString))
      {
        String str = paramJSONObject.getString(paramString);
        localObject = str;
      }
    }
    catch (JSONException localJSONException) {}
    return localObject;
  }
  
  static String getURLDecodedString(String paramString, JSONObject paramJSONObject)
  {
    Object localObject = getRawString(paramString, paramJSONObject);
    if (localObject != null) {}
    try
    {
      String str = URLDecoder.decode((String)localObject, "UTF-8");
      localObject = str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      label21:
      break label21;
    }
    return localObject;
  }
  
  static String getUnescapedString(String paramString, JSONObject paramJSONObject)
  {
    return HTMLEntity.unescape(getRawString(paramString, paramJSONObject));
  }
  
  public static Date parseTrendsDate(String paramString)
    throws TwitterException
  {
    Date localDate;
    switch (paramString.length())
    {
    default: 
      localDate = getDate(paramString, "EEE, d MMM yyyy HH:mm:ss z");
    }
    for (;;)
    {
      return localDate;
      localDate = new Date(1000L * Long.parseLong(paramString));
      continue;
      localDate = getDate(paramString, "yyyy-MM-dd'T'HH:mm:ss'Z'");
    }
  }
  
  public static int toAccessLevel(HttpResponse paramHttpResponse)
  {
    int i;
    if (paramHttpResponse == null) {
      i = -1;
    }
    for (;;)
    {
      return i;
      String str = paramHttpResponse.getResponseHeader("X-Access-Level");
      if (str == null) {
        i = 0;
      } else {
        switch (str.length())
        {
        default: 
          i = 0;
          break;
        case 4: 
          i = 1;
          break;
        case 10: 
          i = 2;
          break;
        case 25: 
          i = 3;
          break;
        case 26: 
          i = 3;
        }
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.z_T4JInternalParseUtil
 * JD-Core Version:    0.7.0.1
 */