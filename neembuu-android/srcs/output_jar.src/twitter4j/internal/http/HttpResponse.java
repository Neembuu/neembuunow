package twitter4j.internal.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import twitter4j.conf.ConfigurationContext;
import twitter4j.internal.logging.Logger;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONObject;

public abstract class HttpResponse
{
  private static final Logger logger = Logger.getLogger(HttpResponseImpl.class);
  protected final HttpClientConfiguration CONF;
  protected InputStream is;
  private JSONObject json = null;
  private JSONArray jsonArray = null;
  protected String responseAsString = null;
  protected int statusCode;
  private boolean streamConsumed = false;
  
  HttpResponse()
  {
    this.CONF = ConfigurationContext.getInstance();
  }
  
  public HttpResponse(HttpClientConfiguration paramHttpClientConfiguration)
  {
    this.CONF = paramHttpClientConfiguration;
  }
  
  private void disconnectForcibly()
  {
    try
    {
      disconnect();
      label4:
      return;
    }
    catch (Exception localException)
    {
      break label4;
    }
  }
  
  /* Error */
  public JSONArray asJSONArray()
    throws twitter4j.TwitterException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 43	twitter4j/internal/http/HttpResponse:jsonArray	Ltwitter4j/internal/org/json/JSONArray;
    //   4: ifnonnull +74 -> 78
    //   7: aconst_null
    //   8: astore_1
    //   9: aload_0
    //   10: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   13: ifnonnull +70 -> 83
    //   16: aload_0
    //   17: invokevirtual 70	twitter4j/internal/http/HttpResponse:asReader	()Ljava/io/Reader;
    //   20: astore_1
    //   21: aload_0
    //   22: new 72	twitter4j/internal/org/json/JSONArray
    //   25: dup
    //   26: new 74	twitter4j/internal/org/json/JSONTokener
    //   29: dup
    //   30: aload_1
    //   31: invokespecial 77	twitter4j/internal/org/json/JSONTokener:<init>	(Ljava/io/Reader;)V
    //   34: invokespecial 80	twitter4j/internal/org/json/JSONArray:<init>	(Ltwitter4j/internal/org/json/JSONTokener;)V
    //   37: putfield 43	twitter4j/internal/http/HttpResponse:jsonArray	Ltwitter4j/internal/org/json/JSONArray;
    //   40: aload_0
    //   41: getfield 51	twitter4j/internal/http/HttpResponse:CONF	Ltwitter4j/internal/http/HttpClientConfiguration;
    //   44: invokeinterface 86 1 0
    //   49: ifeq +118 -> 167
    //   52: getstatic 32	twitter4j/internal/http/HttpResponse:logger	Ltwitter4j/internal/logging/Logger;
    //   55: aload_0
    //   56: getfield 43	twitter4j/internal/http/HttpResponse:jsonArray	Ltwitter4j/internal/org/json/JSONArray;
    //   59: iconst_1
    //   60: invokevirtual 90	twitter4j/internal/org/json/JSONArray:toString	(I)Ljava/lang/String;
    //   63: invokevirtual 94	twitter4j/internal/logging/Logger:debug	(Ljava/lang/String;)V
    //   66: aload_1
    //   67: ifnull +7 -> 74
    //   70: aload_1
    //   71: invokevirtual 99	java/io/Reader:close	()V
    //   74: aload_0
    //   75: invokespecial 101	twitter4j/internal/http/HttpResponse:disconnectForcibly	()V
    //   78: aload_0
    //   79: getfield 43	twitter4j/internal/http/HttpResponse:jsonArray	Ltwitter4j/internal/org/json/JSONArray;
    //   82: areturn
    //   83: aload_0
    //   84: new 72	twitter4j/internal/org/json/JSONArray
    //   87: dup
    //   88: aload_0
    //   89: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   92: invokespecial 103	twitter4j/internal/org/json/JSONArray:<init>	(Ljava/lang/String;)V
    //   95: putfield 43	twitter4j/internal/http/HttpResponse:jsonArray	Ltwitter4j/internal/org/json/JSONArray;
    //   98: goto -58 -> 40
    //   101: astore 4
    //   103: getstatic 32	twitter4j/internal/http/HttpResponse:logger	Ltwitter4j/internal/logging/Logger;
    //   106: invokevirtual 106	twitter4j/internal/logging/Logger:isDebugEnabled	()Z
    //   109: ifeq +102 -> 211
    //   112: new 62	twitter4j/TwitterException
    //   115: dup
    //   116: new 108	java/lang/StringBuilder
    //   119: dup
    //   120: invokespecial 109	java/lang/StringBuilder:<init>	()V
    //   123: aload 4
    //   125: invokevirtual 113	twitter4j/internal/org/json/JSONException:getMessage	()Ljava/lang/String;
    //   128: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: ldc 119
    //   133: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: aload_0
    //   137: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   140: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   143: invokevirtual 121	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   146: aload 4
    //   148: invokespecial 124	twitter4j/TwitterException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   151: athrow
    //   152: astore_2
    //   153: aload_1
    //   154: ifnull +7 -> 161
    //   157: aload_1
    //   158: invokevirtual 99	java/io/Reader:close	()V
    //   161: aload_0
    //   162: invokespecial 101	twitter4j/internal/http/HttpResponse:disconnectForcibly	()V
    //   165: aload_2
    //   166: athrow
    //   167: getstatic 32	twitter4j/internal/http/HttpResponse:logger	Ltwitter4j/internal/logging/Logger;
    //   170: astore 5
    //   172: aload_0
    //   173: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   176: ifnull +19 -> 195
    //   179: aload_0
    //   180: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   183: astore 7
    //   185: aload 5
    //   187: aload 7
    //   189: invokevirtual 94	twitter4j/internal/logging/Logger:debug	(Ljava/lang/String;)V
    //   192: goto -126 -> 66
    //   195: aload_0
    //   196: getfield 43	twitter4j/internal/http/HttpResponse:jsonArray	Ltwitter4j/internal/org/json/JSONArray;
    //   199: invokevirtual 125	twitter4j/internal/org/json/JSONArray:toString	()Ljava/lang/String;
    //   202: astore 6
    //   204: aload 6
    //   206: astore 7
    //   208: goto -23 -> 185
    //   211: new 62	twitter4j/TwitterException
    //   214: dup
    //   215: aload 4
    //   217: invokevirtual 113	twitter4j/internal/org/json/JSONException:getMessage	()Ljava/lang/String;
    //   220: aload 4
    //   222: invokespecial 124	twitter4j/TwitterException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   225: athrow
    //   226: astore 8
    //   228: goto -154 -> 74
    //   231: astore_3
    //   232: goto -71 -> 161
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	235	0	this	HttpResponse
    //   8	150	1	localReader	Reader
    //   152	14	2	localObject1	Object
    //   231	1	3	localIOException1	IOException
    //   101	120	4	localJSONException	twitter4j.internal.org.json.JSONException
    //   170	16	5	localLogger	Logger
    //   202	3	6	str	String
    //   183	24	7	localObject2	Object
    //   226	1	8	localIOException2	IOException
    // Exception table:
    //   from	to	target	type
    //   9	66	101	twitter4j/internal/org/json/JSONException
    //   83	98	101	twitter4j/internal/org/json/JSONException
    //   167	204	101	twitter4j/internal/org/json/JSONException
    //   9	66	152	finally
    //   83	98	152	finally
    //   103	152	152	finally
    //   167	204	152	finally
    //   211	226	152	finally
    //   70	74	226	java/io/IOException
    //   157	161	231	java/io/IOException
  }
  
  /* Error */
  public JSONObject asJSONObject()
    throws twitter4j.TwitterException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 41	twitter4j/internal/http/HttpResponse:json	Ltwitter4j/internal/org/json/JSONObject;
    //   4: ifnonnull +74 -> 78
    //   7: aconst_null
    //   8: astore_1
    //   9: aload_0
    //   10: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   13: ifnonnull +70 -> 83
    //   16: aload_0
    //   17: invokevirtual 70	twitter4j/internal/http/HttpResponse:asReader	()Ljava/io/Reader;
    //   20: astore_1
    //   21: aload_0
    //   22: new 129	twitter4j/internal/org/json/JSONObject
    //   25: dup
    //   26: new 74	twitter4j/internal/org/json/JSONTokener
    //   29: dup
    //   30: aload_1
    //   31: invokespecial 77	twitter4j/internal/org/json/JSONTokener:<init>	(Ljava/io/Reader;)V
    //   34: invokespecial 130	twitter4j/internal/org/json/JSONObject:<init>	(Ltwitter4j/internal/org/json/JSONTokener;)V
    //   37: putfield 41	twitter4j/internal/http/HttpResponse:json	Ltwitter4j/internal/org/json/JSONObject;
    //   40: aload_0
    //   41: getfield 51	twitter4j/internal/http/HttpResponse:CONF	Ltwitter4j/internal/http/HttpClientConfiguration;
    //   44: invokeinterface 86 1 0
    //   49: ifeq +91 -> 140
    //   52: getstatic 32	twitter4j/internal/http/HttpResponse:logger	Ltwitter4j/internal/logging/Logger;
    //   55: aload_0
    //   56: getfield 41	twitter4j/internal/http/HttpResponse:json	Ltwitter4j/internal/org/json/JSONObject;
    //   59: iconst_1
    //   60: invokevirtual 131	twitter4j/internal/org/json/JSONObject:toString	(I)Ljava/lang/String;
    //   63: invokevirtual 94	twitter4j/internal/logging/Logger:debug	(Ljava/lang/String;)V
    //   66: aload_1
    //   67: ifnull +7 -> 74
    //   70: aload_1
    //   71: invokevirtual 99	java/io/Reader:close	()V
    //   74: aload_0
    //   75: invokespecial 101	twitter4j/internal/http/HttpResponse:disconnectForcibly	()V
    //   78: aload_0
    //   79: getfield 41	twitter4j/internal/http/HttpResponse:json	Ltwitter4j/internal/org/json/JSONObject;
    //   82: areturn
    //   83: aload_0
    //   84: new 129	twitter4j/internal/org/json/JSONObject
    //   87: dup
    //   88: aload_0
    //   89: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   92: invokespecial 132	twitter4j/internal/org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   95: putfield 41	twitter4j/internal/http/HttpResponse:json	Ltwitter4j/internal/org/json/JSONObject;
    //   98: goto -58 -> 40
    //   101: astore 4
    //   103: aload_0
    //   104: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   107: ifnonnull +77 -> 184
    //   110: new 62	twitter4j/TwitterException
    //   113: dup
    //   114: aload 4
    //   116: invokevirtual 113	twitter4j/internal/org/json/JSONException:getMessage	()Ljava/lang/String;
    //   119: aload 4
    //   121: invokespecial 124	twitter4j/TwitterException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   124: athrow
    //   125: astore_2
    //   126: aload_1
    //   127: ifnull +7 -> 134
    //   130: aload_1
    //   131: invokevirtual 99	java/io/Reader:close	()V
    //   134: aload_0
    //   135: invokespecial 101	twitter4j/internal/http/HttpResponse:disconnectForcibly	()V
    //   138: aload_2
    //   139: athrow
    //   140: getstatic 32	twitter4j/internal/http/HttpResponse:logger	Ltwitter4j/internal/logging/Logger;
    //   143: astore 5
    //   145: aload_0
    //   146: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   149: ifnull +19 -> 168
    //   152: aload_0
    //   153: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   156: astore 7
    //   158: aload 5
    //   160: aload 7
    //   162: invokevirtual 94	twitter4j/internal/logging/Logger:debug	(Ljava/lang/String;)V
    //   165: goto -99 -> 66
    //   168: aload_0
    //   169: getfield 41	twitter4j/internal/http/HttpResponse:json	Ltwitter4j/internal/org/json/JSONObject;
    //   172: invokevirtual 133	twitter4j/internal/org/json/JSONObject:toString	()Ljava/lang/String;
    //   175: astore 6
    //   177: aload 6
    //   179: astore 7
    //   181: goto -23 -> 158
    //   184: new 62	twitter4j/TwitterException
    //   187: dup
    //   188: new 108	java/lang/StringBuilder
    //   191: dup
    //   192: invokespecial 109	java/lang/StringBuilder:<init>	()V
    //   195: aload 4
    //   197: invokevirtual 113	twitter4j/internal/org/json/JSONException:getMessage	()Ljava/lang/String;
    //   200: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   203: ldc 119
    //   205: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   208: aload_0
    //   209: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   212: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   215: invokevirtual 121	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   218: aload 4
    //   220: invokespecial 124	twitter4j/TwitterException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   223: athrow
    //   224: astore 8
    //   226: goto -152 -> 74
    //   229: astore_3
    //   230: goto -96 -> 134
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	233	0	this	HttpResponse
    //   8	123	1	localReader	Reader
    //   125	14	2	localObject1	Object
    //   229	1	3	localIOException1	IOException
    //   101	118	4	localJSONException	twitter4j.internal.org.json.JSONException
    //   143	16	5	localLogger	Logger
    //   175	3	6	str	String
    //   156	24	7	localObject2	Object
    //   224	1	8	localIOException2	IOException
    // Exception table:
    //   from	to	target	type
    //   9	66	101	twitter4j/internal/org/json/JSONException
    //   83	98	101	twitter4j/internal/org/json/JSONException
    //   140	177	101	twitter4j/internal/org/json/JSONException
    //   9	66	125	finally
    //   83	98	125	finally
    //   103	125	125	finally
    //   140	177	125	finally
    //   184	224	125	finally
    //   70	74	224	java/io/IOException
    //   130	134	229	java/io/IOException
  }
  
  public Reader asReader()
  {
    try
    {
      localObject = new BufferedReader(new InputStreamReader(this.is, "UTF-8"));
      return localObject;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;)
      {
        Object localObject = new InputStreamReader(this.is);
      }
    }
  }
  
  public InputStream asStream()
  {
    if (this.streamConsumed) {
      throw new IllegalStateException("Stream has already been consumed.");
    }
    return this.is;
  }
  
  /* Error */
  public String asString()
    throws twitter4j.TwitterException
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   4: ifnonnull +195 -> 199
    //   7: aconst_null
    //   8: astore_2
    //   9: aconst_null
    //   10: astore_3
    //   11: aload_0
    //   12: invokevirtual 160	twitter4j/internal/http/HttpResponse:asStream	()Ljava/io/InputStream;
    //   15: astore 8
    //   17: aload 8
    //   19: astore_3
    //   20: aload_3
    //   21: ifnonnull +27 -> 48
    //   24: aconst_null
    //   25: astore_1
    //   26: aload_3
    //   27: ifnull +7 -> 34
    //   30: aload_3
    //   31: invokevirtual 163	java/io/InputStream:close	()V
    //   34: iconst_0
    //   35: ifeq +7 -> 42
    //   38: aconst_null
    //   39: invokevirtual 164	java/io/BufferedReader:close	()V
    //   42: aload_0
    //   43: invokespecial 101	twitter4j/internal/http/HttpResponse:disconnectForcibly	()V
    //   46: aload_1
    //   47: areturn
    //   48: new 137	java/io/BufferedReader
    //   51: dup
    //   52: new 139	java/io/InputStreamReader
    //   55: dup
    //   56: aload_3
    //   57: ldc 143
    //   59: invokespecial 146	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   62: invokespecial 147	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   65: astore 9
    //   67: new 108	java/lang/StringBuilder
    //   70: dup
    //   71: invokespecial 109	java/lang/StringBuilder:<init>	()V
    //   74: astore 10
    //   76: aload 9
    //   78: invokevirtual 167	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   81: astore 11
    //   83: aload 11
    //   85: ifnull +64 -> 149
    //   88: aload 10
    //   90: aload 11
    //   92: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: ldc 169
    //   97: invokevirtual 117	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   100: pop
    //   101: goto -25 -> 76
    //   104: astore 4
    //   106: aload 9
    //   108: astore_2
    //   109: new 62	twitter4j/TwitterException
    //   112: dup
    //   113: aload 4
    //   115: invokevirtual 170	java/io/IOException:getMessage	()Ljava/lang/String;
    //   118: aload 4
    //   120: invokespecial 124	twitter4j/TwitterException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   123: athrow
    //   124: astore 5
    //   126: aload_3
    //   127: ifnull +7 -> 134
    //   130: aload_3
    //   131: invokevirtual 163	java/io/InputStream:close	()V
    //   134: aload_2
    //   135: ifnull +7 -> 142
    //   138: aload_2
    //   139: invokevirtual 164	java/io/BufferedReader:close	()V
    //   142: aload_0
    //   143: invokespecial 101	twitter4j/internal/http/HttpResponse:disconnectForcibly	()V
    //   146: aload 5
    //   148: athrow
    //   149: aload_0
    //   150: aload 10
    //   152: invokevirtual 121	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   155: putfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   158: getstatic 32	twitter4j/internal/http/HttpResponse:logger	Ltwitter4j/internal/logging/Logger;
    //   161: aload_0
    //   162: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   165: invokevirtual 94	twitter4j/internal/logging/Logger:debug	(Ljava/lang/String;)V
    //   168: aload_3
    //   169: invokevirtual 163	java/io/InputStream:close	()V
    //   172: aload_0
    //   173: iconst_1
    //   174: putfield 39	twitter4j/internal/http/HttpResponse:streamConsumed	Z
    //   177: aload_3
    //   178: ifnull +7 -> 185
    //   181: aload_3
    //   182: invokevirtual 163	java/io/InputStream:close	()V
    //   185: aload 9
    //   187: ifnull +8 -> 195
    //   190: aload 9
    //   192: invokevirtual 164	java/io/BufferedReader:close	()V
    //   195: aload_0
    //   196: invokespecial 101	twitter4j/internal/http/HttpResponse:disconnectForcibly	()V
    //   199: aload_0
    //   200: getfield 37	twitter4j/internal/http/HttpResponse:responseAsString	Ljava/lang/String;
    //   203: astore_1
    //   204: goto -158 -> 46
    //   207: astore 16
    //   209: goto -175 -> 34
    //   212: astore 15
    //   214: goto -172 -> 42
    //   217: astore 13
    //   219: goto -34 -> 185
    //   222: astore 12
    //   224: goto -29 -> 195
    //   227: astore 7
    //   229: goto -95 -> 134
    //   232: astore 6
    //   234: goto -92 -> 142
    //   237: astore 5
    //   239: aload 9
    //   241: astore_2
    //   242: goto -116 -> 126
    //   245: astore 4
    //   247: goto -138 -> 109
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	250	0	this	HttpResponse
    //   25	179	1	str1	String
    //   8	234	2	localObject1	Object
    //   10	172	3	localObject2	Object
    //   104	15	4	localIOException1	IOException
    //   245	1	4	localIOException2	IOException
    //   124	23	5	localObject3	Object
    //   237	1	5	localObject4	Object
    //   232	1	6	localIOException3	IOException
    //   227	1	7	localIOException4	IOException
    //   15	3	8	localInputStream	InputStream
    //   65	175	9	localBufferedReader	BufferedReader
    //   74	77	10	localStringBuilder	java.lang.StringBuilder
    //   81	10	11	str2	String
    //   222	1	12	localIOException5	IOException
    //   217	1	13	localIOException6	IOException
    //   212	1	15	localIOException7	IOException
    //   207	1	16	localIOException8	IOException
    // Exception table:
    //   from	to	target	type
    //   67	101	104	java/io/IOException
    //   149	177	104	java/io/IOException
    //   11	17	124	finally
    //   48	67	124	finally
    //   109	124	124	finally
    //   30	34	207	java/io/IOException
    //   38	42	212	java/io/IOException
    //   181	185	217	java/io/IOException
    //   190	195	222	java/io/IOException
    //   130	134	227	java/io/IOException
    //   138	142	232	java/io/IOException
    //   67	101	237	finally
    //   149	177	237	finally
    //   11	17	245	java/io/IOException
    //   48	67	245	java/io/IOException
  }
  
  public abstract void disconnect()
    throws IOException;
  
  public abstract String getResponseHeader(String paramString);
  
  public abstract Map<String, List<String>> getResponseHeaderFields();
  
  public int getStatusCode()
  {
    return this.statusCode;
  }
  
  public String toString()
  {
    return "HttpResponse{statusCode=" + this.statusCode + ", responseAsString='" + this.responseAsString + '\'' + ", is=" + this.is + ", streamConsumed=" + this.streamConsumed + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.http.HttpResponse
 * JD-Core Version:    0.7.0.1
 */