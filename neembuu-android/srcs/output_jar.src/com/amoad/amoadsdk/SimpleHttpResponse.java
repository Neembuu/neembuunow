package com.amoad.amoadsdk;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

class SimpleHttpResponse
{
  public int code;
  public String contents;
  public Map<String, List<String>> header;
  
  public SimpleHttpResponse(HttpURLConnection paramHttpURLConnection)
  {
    this(paramHttpURLConnection, false);
  }
  
  /* Error */
  public SimpleHttpResponse(HttpURLConnection paramHttpURLConnection, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 23	java/lang/Object:<init>	()V
    //   4: aload_1
    //   5: ifnonnull +26 -> 31
    //   8: aload_0
    //   9: new 25	java/util/HashMap
    //   12: dup
    //   13: invokespecial 26	java/util/HashMap:<init>	()V
    //   16: putfield 28	com/amoad/amoadsdk/SimpleHttpResponse:header	Ljava/util/Map;
    //   19: aload_0
    //   20: iconst_0
    //   21: putfield 30	com/amoad/amoadsdk/SimpleHttpResponse:code	I
    //   24: aload_0
    //   25: ldc 32
    //   27: putfield 34	com/amoad/amoadsdk/SimpleHttpResponse:contents	Ljava/lang/String;
    //   30: return
    //   31: aconst_null
    //   32: astore_3
    //   33: new 36	java/lang/StringBuilder
    //   36: dup
    //   37: invokespecial 37	java/lang/StringBuilder:<init>	()V
    //   40: astore 4
    //   42: aload_0
    //   43: aload_1
    //   44: invokevirtual 43	java/net/HttpURLConnection:getResponseCode	()I
    //   47: putfield 30	com/amoad/amoadsdk/SimpleHttpResponse:code	I
    //   50: aload_0
    //   51: aload_1
    //   52: invokevirtual 47	java/net/HttpURLConnection:getHeaderFields	()Ljava/util/Map;
    //   55: putfield 28	com/amoad/amoadsdk/SimpleHttpResponse:header	Ljava/util/Map;
    //   58: iload_2
    //   59: ifne +138 -> 197
    //   62: sipush 512
    //   65: newarray byte
    //   67: astore 20
    //   69: aload_1
    //   70: invokevirtual 51	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   73: aload 20
    //   75: invokevirtual 57	java/io/InputStream:read	([B)I
    //   78: ifgt -9 -> 69
    //   81: ldc 59
    //   83: new 36	java/lang/StringBuilder
    //   86: dup
    //   87: ldc 61
    //   89: invokespecial 64	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   92: aload_0
    //   93: getfield 30	com/amoad/amoadsdk/SimpleHttpResponse:code	I
    //   96: invokevirtual 68	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   99: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   102: invokestatic 78	android/util/Log:v	(Ljava/lang/String;Ljava/lang/String;)I
    //   105: pop
    //   106: iconst_0
    //   107: ifeq +7 -> 114
    //   110: aconst_null
    //   111: invokevirtual 83	java/io/BufferedReader:close	()V
    //   114: aload_1
    //   115: ifnull +7 -> 122
    //   118: aload_1
    //   119: invokevirtual 86	java/net/HttpURLConnection:disconnect	()V
    //   122: ldc 88
    //   124: new 36	java/lang/StringBuilder
    //   127: dup
    //   128: ldc 90
    //   130: invokespecial 64	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   133: aload 4
    //   135: invokevirtual 93	java/lang/StringBuilder:length	()I
    //   138: invokevirtual 68	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   141: ldc 95
    //   143: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   146: aload_0
    //   147: getfield 34	com/amoad/amoadsdk/SimpleHttpResponse:contents	Ljava/lang/String;
    //   150: invokevirtual 101	java/lang/String:length	()I
    //   153: invokevirtual 68	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   156: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   159: invokestatic 104	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   162: pop
    //   163: goto -133 -> 30
    //   166: astore 23
    //   168: ldc 106
    //   170: new 36	java/lang/StringBuilder
    //   173: dup
    //   174: ldc 108
    //   176: invokespecial 64	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   179: aload 23
    //   181: invokevirtual 111	java/io/IOException:getMessage	()Ljava/lang/String;
    //   184: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   187: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   190: invokestatic 104	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   193: pop
    //   194: goto -80 -> 114
    //   197: new 80	java/io/BufferedReader
    //   200: dup
    //   201: new 113	java/io/InputStreamReader
    //   204: dup
    //   205: aload_1
    //   206: invokevirtual 51	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   209: invokespecial 116	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   212: invokespecial 119	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   215: astore 14
    //   217: aload 14
    //   219: invokevirtual 122	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   222: astore 15
    //   224: aload 15
    //   226: ifnonnull +74 -> 300
    //   229: aload_0
    //   230: aload 4
    //   232: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   235: putfield 34	com/amoad/amoadsdk/SimpleHttpResponse:contents	Ljava/lang/String;
    //   238: aload 14
    //   240: ifnull +8 -> 248
    //   243: aload 14
    //   245: invokevirtual 83	java/io/BufferedReader:close	()V
    //   248: aload_1
    //   249: ifnull +7 -> 256
    //   252: aload_1
    //   253: invokevirtual 86	java/net/HttpURLConnection:disconnect	()V
    //   256: ldc 88
    //   258: new 36	java/lang/StringBuilder
    //   261: dup
    //   262: ldc 90
    //   264: invokespecial 64	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   267: aload 4
    //   269: invokevirtual 93	java/lang/StringBuilder:length	()I
    //   272: invokevirtual 68	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   275: ldc 95
    //   277: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   280: aload_0
    //   281: getfield 34	com/amoad/amoadsdk/SimpleHttpResponse:contents	Ljava/lang/String;
    //   284: invokevirtual 101	java/lang/String:length	()I
    //   287: invokevirtual 68	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   290: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   293: invokestatic 104	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   296: pop
    //   297: goto -267 -> 30
    //   300: aload 4
    //   302: aload 15
    //   304: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   307: ldc 124
    //   309: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   312: pop
    //   313: goto -96 -> 217
    //   316: astore 5
    //   318: aload 14
    //   320: astore_3
    //   321: ldc 106
    //   323: new 36	java/lang/StringBuilder
    //   326: dup
    //   327: ldc 126
    //   329: invokespecial 64	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   332: aload 5
    //   334: invokevirtual 127	java/lang/Throwable:getMessage	()Ljava/lang/String;
    //   337: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   340: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   343: invokestatic 104	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   346: pop
    //   347: aload_0
    //   348: new 25	java/util/HashMap
    //   351: dup
    //   352: invokespecial 26	java/util/HashMap:<init>	()V
    //   355: putfield 28	com/amoad/amoadsdk/SimpleHttpResponse:header	Ljava/util/Map;
    //   358: aload_0
    //   359: iconst_0
    //   360: putfield 30	com/amoad/amoadsdk/SimpleHttpResponse:code	I
    //   363: aload_0
    //   364: ldc 32
    //   366: putfield 34	com/amoad/amoadsdk/SimpleHttpResponse:contents	Ljava/lang/String;
    //   369: aload_3
    //   370: ifnull +7 -> 377
    //   373: aload_3
    //   374: invokevirtual 83	java/io/BufferedReader:close	()V
    //   377: aload_1
    //   378: ifnull +7 -> 385
    //   381: aload_1
    //   382: invokevirtual 86	java/net/HttpURLConnection:disconnect	()V
    //   385: ldc 88
    //   387: new 36	java/lang/StringBuilder
    //   390: dup
    //   391: ldc 90
    //   393: invokespecial 64	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   396: aload 4
    //   398: invokevirtual 93	java/lang/StringBuilder:length	()I
    //   401: invokevirtual 68	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   404: ldc 95
    //   406: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   409: aload_0
    //   410: getfield 34	com/amoad/amoadsdk/SimpleHttpResponse:contents	Ljava/lang/String;
    //   413: invokevirtual 101	java/lang/String:length	()I
    //   416: invokevirtual 68	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   419: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   422: invokestatic 104	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   425: pop
    //   426: goto -396 -> 30
    //   429: astore 12
    //   431: ldc 106
    //   433: new 36	java/lang/StringBuilder
    //   436: dup
    //   437: ldc 108
    //   439: invokespecial 64	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   442: aload 12
    //   444: invokevirtual 111	java/io/IOException:getMessage	()Ljava/lang/String;
    //   447: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   450: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   453: invokestatic 104	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   456: pop
    //   457: goto -80 -> 377
    //   460: astore 6
    //   462: aload_3
    //   463: ifnull +7 -> 470
    //   466: aload_3
    //   467: invokevirtual 83	java/io/BufferedReader:close	()V
    //   470: aload_1
    //   471: ifnull +7 -> 478
    //   474: aload_1
    //   475: invokevirtual 86	java/net/HttpURLConnection:disconnect	()V
    //   478: ldc 88
    //   480: new 36	java/lang/StringBuilder
    //   483: dup
    //   484: ldc 90
    //   486: invokespecial 64	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   489: aload 4
    //   491: invokevirtual 93	java/lang/StringBuilder:length	()I
    //   494: invokevirtual 68	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   497: ldc 95
    //   499: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   502: aload_0
    //   503: getfield 34	com/amoad/amoadsdk/SimpleHttpResponse:contents	Ljava/lang/String;
    //   506: invokevirtual 101	java/lang/String:length	()I
    //   509: invokevirtual 68	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   512: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   515: invokestatic 104	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   518: pop
    //   519: aload 6
    //   521: athrow
    //   522: astore 8
    //   524: ldc 106
    //   526: new 36	java/lang/StringBuilder
    //   529: dup
    //   530: ldc 108
    //   532: invokespecial 64	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   535: aload 8
    //   537: invokevirtual 111	java/io/IOException:getMessage	()Ljava/lang/String;
    //   540: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   543: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   546: invokestatic 104	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   549: pop
    //   550: goto -80 -> 470
    //   553: astore 18
    //   555: ldc 106
    //   557: new 36	java/lang/StringBuilder
    //   560: dup
    //   561: ldc 108
    //   563: invokespecial 64	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   566: aload 18
    //   568: invokevirtual 111	java/io/IOException:getMessage	()Ljava/lang/String;
    //   571: invokevirtual 98	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   574: invokevirtual 72	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   577: invokestatic 104	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   580: pop
    //   581: goto -333 -> 248
    //   584: astore 6
    //   586: aload 14
    //   588: astore_3
    //   589: goto -127 -> 462
    //   592: astore 5
    //   594: goto -273 -> 321
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	597	0	this	SimpleHttpResponse
    //   0	597	1	paramHttpURLConnection	HttpURLConnection
    //   0	597	2	paramBoolean	boolean
    //   32	557	3	localObject1	Object
    //   40	450	4	localStringBuilder	java.lang.StringBuilder
    //   316	17	5	localThrowable1	java.lang.Throwable
    //   592	1	5	localThrowable2	java.lang.Throwable
    //   460	60	6	localObject2	Object
    //   584	1	6	localObject3	Object
    //   522	14	8	localIOException1	java.io.IOException
    //   429	14	12	localIOException2	java.io.IOException
    //   215	372	14	localBufferedReader	java.io.BufferedReader
    //   222	81	15	str	String
    //   553	14	18	localIOException3	java.io.IOException
    //   67	7	20	arrayOfByte	byte[]
    //   166	14	23	localIOException4	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   110	114	166	java/io/IOException
    //   217	238	316	java/lang/Throwable
    //   300	313	316	java/lang/Throwable
    //   373	377	429	java/io/IOException
    //   42	106	460	finally
    //   197	217	460	finally
    //   321	369	460	finally
    //   466	470	522	java/io/IOException
    //   243	248	553	java/io/IOException
    //   217	238	584	finally
    //   300	313	584	finally
    //   42	106	592	java/lang/Throwable
    //   197	217	592	java/lang/Throwable
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.SimpleHttpResponse
 * JD-Core Version:    0.7.0.1
 */