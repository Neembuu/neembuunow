package com.google.ads.internal;

import android.content.Context;
import com.google.ads.util.AdUtil;
import com.google.ads.util.b;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

public class j
  implements Runnable
{
  private String a;
  private Context b;
  
  public j(String paramString, Context paramContext)
  {
    this.a = paramString;
    this.b = paramContext;
  }
  
  protected BufferedOutputStream a(HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    return new BufferedOutputStream(paramHttpURLConnection.getOutputStream());
  }
  
  protected HttpURLConnection a(URL paramURL)
    throws IOException
  {
    HttpURLConnection localHttpURLConnection = (HttpURLConnection)paramURL.openConnection();
    localHttpURLConnection.setDoOutput(true);
    localHttpURLConnection.setInstanceFollowRedirects(true);
    AdUtil.a(localHttpURLConnection, this.b);
    localHttpURLConnection.setRequestProperty("Accept", "application/json");
    localHttpURLConnection.setRequestProperty("Content-Type", "application/json");
    return localHttpURLConnection;
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: invokestatic 70	com/google/ads/m:a	()Lcom/google/ads/m;
    //   3: getfield 73	com/google/ads/m:b	Lcom/google/ads/util/i$b;
    //   6: invokevirtual 78	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   9: checkcast 80	com/google/ads/m$a
    //   12: getfield 84	com/google/ads/m$a:h	Lcom/google/ads/util/i$c;
    //   15: invokevirtual 87	com/google/ads/util/i$c:a	()Ljava/lang/Object;
    //   18: checkcast 89	java/lang/String
    //   21: astore_1
    //   22: aload_0
    //   23: new 38	java/net/URL
    //   26: dup
    //   27: aload_1
    //   28: invokespecial 92	java/net/URL:<init>	(Ljava/lang/String;)V
    //   31: invokevirtual 94	com/google/ads/internal/j:a	(Ljava/net/URL;)Ljava/net/HttpURLConnection;
    //   34: astore_3
    //   35: new 8	com/google/ads/internal/j$a
    //   38: dup
    //   39: aload_0
    //   40: getfield 19	com/google/ads/internal/j:a	Ljava/lang/String;
    //   43: invokespecial 95	com/google/ads/internal/j$a:<init>	(Ljava/lang/String;)V
    //   46: invokevirtual 98	com/google/ads/internal/j$a:a	()Lorg/json/JSONObject;
    //   49: invokevirtual 104	org/json/JSONObject:toString	()Ljava/lang/String;
    //   52: invokevirtual 108	java/lang/String:getBytes	()[B
    //   55: astore 4
    //   57: aload_3
    //   58: aload 4
    //   60: arraylength
    //   61: invokevirtual 112	java/net/HttpURLConnection:setFixedLengthStreamingMode	(I)V
    //   64: aload_0
    //   65: aload_3
    //   66: invokevirtual 114	com/google/ads/internal/j:a	(Ljava/net/HttpURLConnection;)Ljava/io/BufferedOutputStream;
    //   69: astore 6
    //   71: aload 6
    //   73: aload 4
    //   75: invokevirtual 120	java/io/OutputStream:write	([B)V
    //   78: aload 6
    //   80: invokevirtual 123	java/io/OutputStream:close	()V
    //   83: aload_3
    //   84: invokevirtual 127	java/net/HttpURLConnection:getResponseCode	()I
    //   87: sipush 200
    //   90: if_icmpeq +28 -> 118
    //   93: new 129	java/lang/StringBuilder
    //   96: dup
    //   97: invokespecial 130	java/lang/StringBuilder:<init>	()V
    //   100: ldc 132
    //   102: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   105: aload_3
    //   106: invokevirtual 139	java/net/HttpURLConnection:getResponseMessage	()Ljava/lang/String;
    //   109: invokevirtual 136	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: invokevirtual 140	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   115: invokestatic 144	com/google/ads/util/b:b	(Ljava/lang/String;)V
    //   118: aload_3
    //   119: invokevirtual 147	java/net/HttpURLConnection:disconnect	()V
    //   122: goto +19 -> 141
    //   125: astore 5
    //   127: aload_3
    //   128: invokevirtual 147	java/net/HttpURLConnection:disconnect	()V
    //   131: aload 5
    //   133: athrow
    //   134: astore_2
    //   135: ldc 149
    //   137: aload_2
    //   138: invokestatic 152	com/google/ads/util/b:b	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   141: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	142	0	this	j
    //   21	7	1	str	String
    //   134	4	2	localIOException	IOException
    //   34	94	3	localHttpURLConnection	HttpURLConnection
    //   55	19	4	arrayOfByte	byte[]
    //   125	7	5	localObject	Object
    //   69	10	6	localBufferedOutputStream	BufferedOutputStream
    // Exception table:
    //   from	to	target	type
    //   64	118	125	finally
    //   22	64	134	java/io/IOException
    //   118	134	134	java/io/IOException
  }
  
  public static class a
  {
    private final String a;
    
    public a(String paramString)
    {
      this.a = paramString;
    }
    
    public JSONObject a()
    {
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("debugHeader", this.a);
        return localJSONObject;
      }
      catch (JSONException localJSONException)
      {
        for (;;)
        {
          b.b("Could not build ReportAdJson from inputs.", localJSONException);
        }
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.internal.j
 * JD-Core Version:    0.7.0.1
 */