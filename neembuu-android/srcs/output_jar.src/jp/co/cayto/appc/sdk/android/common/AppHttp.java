package jp.co.cayto.appc.sdk.android.common;

import java.util.ArrayList;
import org.apache.http.NameValuePair;

public class AppHttp
{
  private static final String BOUNDARY = "----------V2ymHFg03ehbqgZCaKO6jy";
  private static final int TIMEOUT = 10000;
  
  /* Error */
  public static byte[] doGetImage(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: sipush 1024
    //   5: newarray byte
    //   7: astore_2
    //   8: aconst_null
    //   9: astore_3
    //   10: aconst_null
    //   11: astore 4
    //   13: aconst_null
    //   14: astore 5
    //   16: new 21	java/net/URL
    //   19: dup
    //   20: aload_0
    //   21: invokespecial 24	java/net/URL:<init>	(Ljava/lang/String;)V
    //   24: invokevirtual 28	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   27: checkcast 30	java/net/HttpURLConnection
    //   30: astore_3
    //   31: aload_3
    //   32: ldc 32
    //   34: invokevirtual 35	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   37: aload_3
    //   38: sipush 5000
    //   41: invokevirtual 39	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   44: aload_3
    //   45: sipush 10000
    //   48: invokevirtual 42	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   51: aload_3
    //   52: invokevirtual 45	java/net/HttpURLConnection:connect	()V
    //   55: aload_3
    //   56: invokevirtual 49	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   59: astore 4
    //   61: new 51	java/io/ByteArrayOutputStream
    //   64: dup
    //   65: invokespecial 52	java/io/ByteArrayOutputStream:<init>	()V
    //   68: astore 14
    //   70: aload 4
    //   72: aload_2
    //   73: invokevirtual 58	java/io/InputStream:read	([B)I
    //   76: istore 16
    //   78: iload 16
    //   80: ifgt +57 -> 137
    //   83: aload 14
    //   85: invokevirtual 61	java/io/ByteArrayOutputStream:close	()V
    //   88: aload 4
    //   90: invokevirtual 62	java/io/InputStream:close	()V
    //   93: aload_3
    //   94: invokevirtual 65	java/net/HttpURLConnection:disconnect	()V
    //   97: aload 14
    //   99: invokevirtual 69	java/io/ByteArrayOutputStream:toByteArray	()[B
    //   102: astore 17
    //   104: aload 17
    //   106: astore_1
    //   107: aload 4
    //   109: ifnull +8 -> 117
    //   112: aload 4
    //   114: invokevirtual 62	java/io/InputStream:close	()V
    //   117: aload 14
    //   119: ifnull +8 -> 127
    //   122: aload 14
    //   124: invokevirtual 61	java/io/ByteArrayOutputStream:close	()V
    //   127: aload_3
    //   128: ifnull +150 -> 278
    //   131: aload_3
    //   132: invokevirtual 65	java/net/HttpURLConnection:disconnect	()V
    //   135: aload_1
    //   136: areturn
    //   137: aload 14
    //   139: aload_2
    //   140: iconst_0
    //   141: iload 16
    //   143: invokevirtual 73	java/io/ByteArrayOutputStream:write	([BII)V
    //   146: goto -76 -> 70
    //   149: astore 15
    //   151: aload 14
    //   153: astore 5
    //   155: aload 4
    //   157: ifnull +8 -> 165
    //   160: aload 4
    //   162: invokevirtual 62	java/io/InputStream:close	()V
    //   165: aload 5
    //   167: ifnull +8 -> 175
    //   170: aload 5
    //   172: invokevirtual 61	java/io/ByteArrayOutputStream:close	()V
    //   175: aload_3
    //   176: ifnull -41 -> 135
    //   179: aload_3
    //   180: invokevirtual 65	java/net/HttpURLConnection:disconnect	()V
    //   183: goto -48 -> 135
    //   186: astore 7
    //   188: goto -53 -> 135
    //   191: astore 10
    //   193: aload 4
    //   195: ifnull +8 -> 203
    //   198: aload 4
    //   200: invokevirtual 62	java/io/InputStream:close	()V
    //   203: aload 5
    //   205: ifnull +8 -> 213
    //   208: aload 5
    //   210: invokevirtual 61	java/io/ByteArrayOutputStream:close	()V
    //   213: aload_3
    //   214: ifnull +7 -> 221
    //   217: aload_3
    //   218: invokevirtual 65	java/net/HttpURLConnection:disconnect	()V
    //   221: aload 10
    //   223: athrow
    //   224: astore 18
    //   226: goto -91 -> 135
    //   229: astore 9
    //   231: goto -66 -> 165
    //   234: astore 8
    //   236: goto -61 -> 175
    //   239: astore 13
    //   241: goto -38 -> 203
    //   244: astore 12
    //   246: goto -33 -> 213
    //   249: astore 11
    //   251: goto -30 -> 221
    //   254: astore 20
    //   256: goto -139 -> 117
    //   259: astore 19
    //   261: goto -134 -> 127
    //   264: astore 10
    //   266: aload 14
    //   268: astore 5
    //   270: goto -77 -> 193
    //   273: astore 6
    //   275: goto -120 -> 155
    //   278: goto -143 -> 135
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	281	0	paramString	String
    //   1	135	1	localObject1	Object
    //   7	133	2	arrayOfByte1	byte[]
    //   9	209	3	localHttpURLConnection	java.net.HttpURLConnection
    //   11	188	4	localInputStream	java.io.InputStream
    //   14	255	5	localObject2	Object
    //   273	1	6	localException1	java.lang.Exception
    //   186	1	7	localException2	java.lang.Exception
    //   234	1	8	localException3	java.lang.Exception
    //   229	1	9	localException4	java.lang.Exception
    //   191	31	10	localObject3	Object
    //   264	1	10	localObject4	Object
    //   249	1	11	localException5	java.lang.Exception
    //   244	1	12	localException6	java.lang.Exception
    //   239	1	13	localException7	java.lang.Exception
    //   68	199	14	localByteArrayOutputStream	java.io.ByteArrayOutputStream
    //   149	1	15	localException8	java.lang.Exception
    //   76	66	16	i	int
    //   102	3	17	arrayOfByte2	byte[]
    //   224	1	18	localException9	java.lang.Exception
    //   259	1	19	localException10	java.lang.Exception
    //   254	1	20	localException11	java.lang.Exception
    // Exception table:
    //   from	to	target	type
    //   70	104	149	java/lang/Exception
    //   137	146	149	java/lang/Exception
    //   179	183	186	java/lang/Exception
    //   16	70	191	finally
    //   131	135	224	java/lang/Exception
    //   160	165	229	java/lang/Exception
    //   170	175	234	java/lang/Exception
    //   198	203	239	java/lang/Exception
    //   208	213	244	java/lang/Exception
    //   217	221	249	java/lang/Exception
    //   112	117	254	java/lang/Exception
    //   122	127	259	java/lang/Exception
    //   70	104	264	finally
    //   137	146	264	finally
    //   16	70	273	java/lang/Exception
  }
  
  /* Error */
  public static String doPost(String paramString, ArrayList<NameValuePair> paramArrayList1, ArrayList<NameValuePair> paramArrayList2, boolean paramBoolean)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: aconst_null
    //   4: astore 5
    //   6: aconst_null
    //   7: astore 6
    //   9: new 79	java/lang/StringBuilder
    //   12: dup
    //   13: invokespecial 80	java/lang/StringBuilder:<init>	()V
    //   16: astore 7
    //   18: new 21	java/net/URL
    //   21: dup
    //   22: aload_0
    //   23: invokespecial 24	java/net/URL:<init>	(Ljava/lang/String;)V
    //   26: invokevirtual 28	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   29: astore 4
    //   31: aload 4
    //   33: ldc 82
    //   35: ldc 84
    //   37: invokevirtual 90	java/net/URLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   40: aload 4
    //   42: sipush 10000
    //   45: invokevirtual 91	java/net/URLConnection:setConnectTimeout	(I)V
    //   48: aload 4
    //   50: checkcast 30	java/net/HttpURLConnection
    //   53: ldc 93
    //   55: invokevirtual 35	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   58: aload 4
    //   60: iconst_1
    //   61: invokevirtual 97	java/net/URLConnection:setDoOutput	(Z)V
    //   64: aload 4
    //   66: invokevirtual 98	java/net/URLConnection:connect	()V
    //   69: aload 4
    //   71: invokevirtual 102	java/net/URLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   74: astore 5
    //   76: new 104	java/lang/StringBuffer
    //   79: dup
    //   80: ldc 106
    //   82: invokespecial 107	java/lang/StringBuffer:<init>	(Ljava/lang/String;)V
    //   85: ldc 8
    //   87: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   90: ldc 113
    //   92: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   95: astore 16
    //   97: aload_1
    //   98: invokevirtual 119	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   101: astore 17
    //   103: aload 17
    //   105: invokeinterface 125 1 0
    //   110: ifne +120 -> 230
    //   113: aload_2
    //   114: ifnull +19 -> 133
    //   117: aload_2
    //   118: invokevirtual 119	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   121: astore 26
    //   123: aload 26
    //   125: invokeinterface 125 1 0
    //   130: ifne +226 -> 356
    //   133: aload 5
    //   135: aload 16
    //   137: invokevirtual 129	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   140: invokevirtual 134	java/lang/String:getBytes	()[B
    //   143: invokevirtual 139	java/io/OutputStream:write	([B)V
    //   146: aload 5
    //   148: ldc 141
    //   150: invokevirtual 134	java/lang/String:getBytes	()[B
    //   153: invokevirtual 139	java/io/OutputStream:write	([B)V
    //   156: new 143	java/io/BufferedReader
    //   159: dup
    //   160: new 145	java/io/InputStreamReader
    //   163: dup
    //   164: aload 4
    //   166: invokevirtual 146	java/net/URLConnection:getInputStream	()Ljava/io/InputStream;
    //   169: ldc 148
    //   171: invokespecial 151	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   174: invokespecial 154	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   177: astore 20
    //   179: aload 20
    //   181: invokevirtual 157	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   184: astore 21
    //   186: aload 21
    //   188: ifnonnull +284 -> 472
    //   191: aload 20
    //   193: ifnull +8 -> 201
    //   196: aload 20
    //   198: invokevirtual 158	java/io/BufferedReader:close	()V
    //   201: aload 5
    //   203: ifnull +8 -> 211
    //   206: aload 5
    //   208: invokevirtual 159	java/io/OutputStream:close	()V
    //   211: aload 4
    //   213: ifnull +333 -> 546
    //   216: aload 4
    //   218: checkcast 30	java/net/HttpURLConnection
    //   221: invokevirtual 65	java/net/HttpURLConnection:disconnect	()V
    //   224: aload 7
    //   226: invokevirtual 160	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   229: areturn
    //   230: aload 17
    //   232: invokeinterface 164 1 0
    //   237: checkcast 166	org/apache/http/NameValuePair
    //   240: astore 18
    //   242: aload 16
    //   244: ldc 168
    //   246: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   249: aload 18
    //   251: invokeinterface 171 1 0
    //   256: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   259: ldc 173
    //   261: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   264: ldc 175
    //   266: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   269: ldc 113
    //   271: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   274: aload 18
    //   276: invokeinterface 178 1 0
    //   281: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   284: ldc 113
    //   286: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   289: ldc 106
    //   291: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   294: ldc 8
    //   296: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   299: ldc 113
    //   301: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   304: pop
    //   305: goto -202 -> 103
    //   308: astore 12
    //   310: aload 12
    //   312: invokevirtual 181	java/lang/Exception:printStackTrace	()V
    //   315: aload 6
    //   317: ifnull +8 -> 325
    //   320: aload 6
    //   322: invokevirtual 158	java/io/BufferedReader:close	()V
    //   325: aload 5
    //   327: ifnull +8 -> 335
    //   330: aload 5
    //   332: invokevirtual 159	java/io/OutputStream:close	()V
    //   335: aload 4
    //   337: ifnull -113 -> 224
    //   340: aload 4
    //   342: checkcast 30	java/net/HttpURLConnection
    //   345: invokevirtual 65	java/net/HttpURLConnection:disconnect	()V
    //   348: goto -124 -> 224
    //   351: astore 13
    //   353: goto -129 -> 224
    //   356: aload 26
    //   358: invokeinterface 164 1 0
    //   363: checkcast 166	org/apache/http/NameValuePair
    //   366: astore 27
    //   368: aload 16
    //   370: ldc 168
    //   372: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   375: aload 27
    //   377: invokeinterface 171 1 0
    //   382: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   385: ldc 173
    //   387: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   390: ldc 175
    //   392: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   395: ldc 113
    //   397: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   400: aload 27
    //   402: invokeinterface 178 1 0
    //   407: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   410: ldc 113
    //   412: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   415: ldc 106
    //   417: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   420: ldc 8
    //   422: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   425: ldc 113
    //   427: invokevirtual 111	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   430: pop
    //   431: goto -308 -> 123
    //   434: astore 8
    //   436: aload 6
    //   438: ifnull +8 -> 446
    //   441: aload 6
    //   443: invokevirtual 158	java/io/BufferedReader:close	()V
    //   446: aload 5
    //   448: ifnull +8 -> 456
    //   451: aload 5
    //   453: invokevirtual 159	java/io/OutputStream:close	()V
    //   456: aload 4
    //   458: ifnull +11 -> 469
    //   461: aload 4
    //   463: checkcast 30	java/net/HttpURLConnection
    //   466: invokevirtual 65	java/net/HttpURLConnection:disconnect	()V
    //   469: aload 8
    //   471: athrow
    //   472: aload 7
    //   474: aload 21
    //   476: invokevirtual 184	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   479: ldc 186
    //   481: invokevirtual 184	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   484: pop
    //   485: goto -306 -> 179
    //   488: astore 12
    //   490: aload 20
    //   492: astore 6
    //   494: goto -184 -> 310
    //   497: astore 23
    //   499: goto -275 -> 224
    //   502: astore 15
    //   504: goto -179 -> 325
    //   507: astore 14
    //   509: goto -174 -> 335
    //   512: astore 11
    //   514: goto -68 -> 446
    //   517: astore 10
    //   519: goto -63 -> 456
    //   522: astore 25
    //   524: goto -323 -> 201
    //   527: astore 24
    //   529: goto -318 -> 211
    //   532: astore 9
    //   534: goto -65 -> 469
    //   537: astore 8
    //   539: aload 20
    //   541: astore 6
    //   543: goto -107 -> 436
    //   546: goto -322 -> 224
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	549	0	paramString	String
    //   0	549	1	paramArrayList1	ArrayList<NameValuePair>
    //   0	549	2	paramArrayList2	ArrayList<NameValuePair>
    //   0	549	3	paramBoolean	boolean
    //   1	461	4	localURLConnection	java.net.URLConnection
    //   4	448	5	localOutputStream	java.io.OutputStream
    //   7	535	6	localObject1	Object
    //   16	457	7	localStringBuilder	java.lang.StringBuilder
    //   434	36	8	localObject2	Object
    //   537	1	8	localObject3	Object
    //   532	1	9	localException1	java.lang.Exception
    //   517	1	10	localIOException1	java.io.IOException
    //   512	1	11	localIOException2	java.io.IOException
    //   308	3	12	localException2	java.lang.Exception
    //   488	1	12	localException3	java.lang.Exception
    //   351	1	13	localException4	java.lang.Exception
    //   507	1	14	localIOException3	java.io.IOException
    //   502	1	15	localIOException4	java.io.IOException
    //   95	274	16	localStringBuffer	java.lang.StringBuffer
    //   101	130	17	localIterator1	java.util.Iterator
    //   240	35	18	localNameValuePair1	NameValuePair
    //   177	363	20	localBufferedReader	java.io.BufferedReader
    //   184	291	21	str	String
    //   497	1	23	localException5	java.lang.Exception
    //   527	1	24	localIOException5	java.io.IOException
    //   522	1	25	localIOException6	java.io.IOException
    //   121	236	26	localIterator2	java.util.Iterator
    //   366	35	27	localNameValuePair2	NameValuePair
    // Exception table:
    //   from	to	target	type
    //   18	179	308	java/lang/Exception
    //   230	305	308	java/lang/Exception
    //   356	431	308	java/lang/Exception
    //   340	348	351	java/lang/Exception
    //   18	179	434	finally
    //   230	305	434	finally
    //   310	315	434	finally
    //   356	431	434	finally
    //   179	186	488	java/lang/Exception
    //   472	485	488	java/lang/Exception
    //   216	224	497	java/lang/Exception
    //   320	325	502	java/io/IOException
    //   330	335	507	java/io/IOException
    //   441	446	512	java/io/IOException
    //   451	456	517	java/io/IOException
    //   196	201	522	java/io/IOException
    //   206	211	527	java/io/IOException
    //   461	469	532	java/lang/Exception
    //   179	186	537	finally
    //   472	485	537	finally
  }
  
  public static String doPost(String paramString, ArrayList<NameValuePair> paramArrayList, boolean paramBoolean)
  {
    return doPost(paramString, paramArrayList, null, paramBoolean);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.common.AppHttp
 * JD-Core Version:    0.7.0.1
 */