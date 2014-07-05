package jp.co.asbit.pvstar.api;

import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.Video;

public class GetVideoDetailTask
  extends AsyncTask<Video, Long, Video>
{
  private static final String VIDEO_DETAIL_API = "http://pvstar.dooga.org/api2/videos/video_detail/?id=%s&site=%s";
  
  /* Error */
  protected Video doInBackground(Video... paramVarArgs)
  {
    // Byte code:
    //   0: aload_1
    //   1: iconst_0
    //   2: aaload
    //   3: astore_2
    //   4: aconst_null
    //   5: astore_3
    //   6: aconst_null
    //   7: astore 4
    //   9: aconst_null
    //   10: astore 5
    //   12: iconst_2
    //   13: anewarray 24	java/lang/Object
    //   16: astore 10
    //   18: aload 10
    //   20: iconst_0
    //   21: aload_2
    //   22: invokevirtual 30	jp/co/asbit/pvstar/Video:getId	()Ljava/lang/String;
    //   25: aastore
    //   26: aload 10
    //   28: iconst_1
    //   29: aload_2
    //   30: invokevirtual 33	jp/co/asbit/pvstar/Video:getSearchEngine	()Ljava/lang/String;
    //   33: aastore
    //   34: new 35	java/net/URL
    //   37: dup
    //   38: ldc 9
    //   40: aload 10
    //   42: invokestatic 41	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   45: invokespecial 44	java/net/URL:<init>	(Ljava/lang/String;)V
    //   48: astore 11
    //   50: aload 11
    //   52: invokevirtual 48	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   55: checkcast 50	java/net/HttpURLConnection
    //   58: astore 4
    //   60: aload 4
    //   62: sipush 5000
    //   65: invokevirtual 54	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   68: aload 4
    //   70: sipush 5000
    //   73: invokevirtual 57	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   76: aload 4
    //   78: iconst_0
    //   79: invokevirtual 61	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   82: invokestatic 67	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   85: astore 12
    //   87: aload 4
    //   89: invokevirtual 71	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   92: astore 5
    //   94: aload 12
    //   96: aload 5
    //   98: ldc 73
    //   100: invokeinterface 79 3 0
    //   105: iconst_0
    //   106: istore 13
    //   108: aload 12
    //   110: invokeinterface 83 1 0
    //   115: istore 14
    //   117: iload 14
    //   119: istore 15
    //   121: iload 15
    //   123: iconst_1
    //   124: if_icmpne +25 -> 149
    //   127: aload 4
    //   129: ifnull +8 -> 137
    //   132: aload 4
    //   134: invokevirtual 86	java/net/HttpURLConnection:disconnect	()V
    //   137: aload 5
    //   139: ifnull +362 -> 501
    //   142: aload 5
    //   144: invokevirtual 91	java/io/InputStream:close	()V
    //   147: aload_2
    //   148: areturn
    //   149: aload 12
    //   151: invokeinterface 94 1 0
    //   156: astore 16
    //   158: iload 15
    //   160: iconst_2
    //   161: if_icmpne +92 -> 253
    //   164: aload 16
    //   166: ldc 96
    //   168: invokevirtual 100	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   171: ifeq +118 -> 289
    //   174: aload 12
    //   176: iconst_0
    //   177: invokeinterface 104 2 0
    //   182: astore_3
    //   183: aload 12
    //   185: invokeinterface 107 1 0
    //   190: pop
    //   191: aload 12
    //   193: invokeinterface 83 1 0
    //   198: iconst_4
    //   199: if_icmpne +54 -> 253
    //   202: aload 12
    //   204: invokeinterface 110 1 0
    //   209: astore 18
    //   211: aload 16
    //   213: ldc 112
    //   215: invokevirtual 100	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   218: ifeq +87 -> 305
    //   221: aload_2
    //   222: aload_3
    //   223: invokevirtual 115	jp/co/asbit/pvstar/Video:setSearchEngine	(Ljava/lang/String;)V
    //   226: aload_2
    //   227: aload 18
    //   229: invokevirtual 118	jp/co/asbit/pvstar/Video:setTitle	(Ljava/lang/String;)V
    //   232: iload 13
    //   234: ifeq +19 -> 253
    //   237: aload 16
    //   239: ldc 120
    //   241: invokevirtual 100	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   244: ifeq +9 -> 253
    //   247: aload_2
    //   248: aload 18
    //   250: invokevirtual 123	jp/co/asbit/pvstar/Video:setTag	(Ljava/lang/String;)V
    //   253: aload 12
    //   255: invokeinterface 83 1 0
    //   260: iconst_3
    //   261: if_icmpne +16 -> 277
    //   264: aload 16
    //   266: ldc 125
    //   268: invokevirtual 100	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   271: ifeq +6 -> 277
    //   274: iconst_0
    //   275: istore 13
    //   277: aload 12
    //   279: invokeinterface 107 1 0
    //   284: istore 15
    //   286: goto -165 -> 121
    //   289: aload 16
    //   291: ldc 125
    //   293: invokevirtual 100	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   296: ifeq -113 -> 183
    //   299: iconst_1
    //   300: istore 13
    //   302: goto -119 -> 183
    //   305: aload 16
    //   307: ldc 127
    //   309: invokevirtual 100	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   312: ifeq +52 -> 364
    //   315: aload_2
    //   316: aload 18
    //   318: invokevirtual 130	jp/co/asbit/pvstar/Video:setId	(Ljava/lang/String;)V
    //   321: goto -89 -> 232
    //   324: astore 6
    //   326: aload 6
    //   328: invokevirtual 133	java/lang/Exception:printStackTrace	()V
    //   331: aload 4
    //   333: ifnull +8 -> 341
    //   336: aload 4
    //   338: invokevirtual 86	java/net/HttpURLConnection:disconnect	()V
    //   341: aload 5
    //   343: ifnull -196 -> 147
    //   346: aload 5
    //   348: invokevirtual 91	java/io/InputStream:close	()V
    //   351: goto -204 -> 147
    //   354: astore 9
    //   356: aload 9
    //   358: invokevirtual 133	java/lang/Exception:printStackTrace	()V
    //   361: goto -214 -> 147
    //   364: aload 16
    //   366: ldc 135
    //   368: invokevirtual 100	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   371: ifeq +37 -> 408
    //   374: aload_2
    //   375: aload 18
    //   377: invokevirtual 138	jp/co/asbit/pvstar/Video:setDescription	(Ljava/lang/String;)V
    //   380: goto -148 -> 232
    //   383: astore 7
    //   385: aload 4
    //   387: ifnull +8 -> 395
    //   390: aload 4
    //   392: invokevirtual 86	java/net/HttpURLConnection:disconnect	()V
    //   395: aload 5
    //   397: ifnull +8 -> 405
    //   400: aload 5
    //   402: invokevirtual 91	java/io/InputStream:close	()V
    //   405: aload 7
    //   407: athrow
    //   408: aload 16
    //   410: ldc 140
    //   412: invokevirtual 100	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   415: ifeq +12 -> 427
    //   418: aload_2
    //   419: aload 18
    //   421: invokevirtual 143	jp/co/asbit/pvstar/Video:setDuration	(Ljava/lang/String;)V
    //   424: goto -192 -> 232
    //   427: aload 16
    //   429: ldc 145
    //   431: invokevirtual 100	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   434: ifeq +12 -> 446
    //   437: aload_2
    //   438: aload 18
    //   440: invokevirtual 148	jp/co/asbit/pvstar/Video:setViewCount	(Ljava/lang/String;)V
    //   443: goto -211 -> 232
    //   446: aload 16
    //   448: ldc 150
    //   450: invokevirtual 100	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   453: ifeq +12 -> 465
    //   456: aload_2
    //   457: aload 18
    //   459: invokevirtual 153	jp/co/asbit/pvstar/Video:setThumbnailUrl	(Ljava/lang/String;)V
    //   462: goto -230 -> 232
    //   465: aload 16
    //   467: ldc 155
    //   469: invokevirtual 100	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   472: ifeq -240 -> 232
    //   475: aload_2
    //   476: aload 18
    //   478: invokevirtual 158	jp/co/asbit/pvstar/Video:setUserId	(Ljava/lang/String;)V
    //   481: goto -249 -> 232
    //   484: astore 8
    //   486: aload 8
    //   488: invokevirtual 133	java/lang/Exception:printStackTrace	()V
    //   491: goto -86 -> 405
    //   494: astore 19
    //   496: aload 19
    //   498: invokevirtual 133	java/lang/Exception:printStackTrace	()V
    //   501: goto -354 -> 147
    //   504: astore 7
    //   506: goto -121 -> 385
    //   509: astore 6
    //   511: goto -185 -> 326
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	514	0	this	GetVideoDetailTask
    //   0	514	1	paramVarArgs	Video[]
    //   3	473	2	localVideo	Video
    //   5	218	3	str1	String
    //   7	384	4	localHttpURLConnection	java.net.HttpURLConnection
    //   10	391	5	localInputStream	java.io.InputStream
    //   324	3	6	localException1	java.lang.Exception
    //   509	1	6	localException2	java.lang.Exception
    //   383	23	7	localObject1	java.lang.Object
    //   504	1	7	localObject2	java.lang.Object
    //   484	3	8	localException3	java.lang.Exception
    //   354	3	9	localException4	java.lang.Exception
    //   16	25	10	arrayOfObject	java.lang.Object[]
    //   48	3	11	localURL	java.net.URL
    //   85	193	12	localXmlPullParser	org.xmlpull.v1.XmlPullParser
    //   106	195	13	i	int
    //   115	3	14	j	int
    //   119	166	15	k	int
    //   156	310	16	str2	String
    //   209	268	18	str3	String
    //   494	3	19	localException5	java.lang.Exception
    // Exception table:
    //   from	to	target	type
    //   50	117	324	java/lang/Exception
    //   149	321	324	java/lang/Exception
    //   364	380	324	java/lang/Exception
    //   408	481	324	java/lang/Exception
    //   336	351	354	java/lang/Exception
    //   50	117	383	finally
    //   149	321	383	finally
    //   364	380	383	finally
    //   408	481	383	finally
    //   390	405	484	java/lang/Exception
    //   132	147	494	java/lang/Exception
    //   12	50	504	finally
    //   326	331	504	finally
    //   12	50	509	java/lang/Exception
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.GetVideoDetailTask
 * JD-Core Version:    0.7.0.1
 */