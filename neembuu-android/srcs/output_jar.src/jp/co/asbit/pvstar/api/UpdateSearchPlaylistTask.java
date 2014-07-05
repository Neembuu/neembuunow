package jp.co.asbit.pvstar.api;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.Playlist;

public class UpdateSearchPlaylistTask
  extends AsyncTask<URL, Integer, ArrayList<Playlist>>
{
  private static final String SEARCH_PLAYLIST_API = "http://pvstar.dooga.org/api2/playlists2/searches/?query=%s&page=%s&per_page=%s";
  protected int totalResults = 200;
  private String uri = "";
  
  public UpdateSearchPlaylistTask(String paramString, int paramInt1, int paramInt2)
  {
    try
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = URLEncoder.encode(paramString, "UTF-8");
      arrayOfObject[1] = Integer.valueOf(paramInt1);
      arrayOfObject[2] = Integer.valueOf(paramInt2);
      this.uri = String.format("http://pvstar.dooga.org/api2/playlists2/searches/?query=%s&page=%s&per_page=%s", arrayOfObject);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;)
      {
        localUnsupportedEncodingException.printStackTrace();
      }
    }
  }
  
  /* Error */
  protected ArrayList<Playlist> doInBackground(URL... paramVarArgs)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: aload_0
    //   3: monitorenter
    //   4: new 63	java/util/ArrayList
    //   7: dup
    //   8: invokespecial 64	java/util/ArrayList:<init>	()V
    //   11: astore_3
    //   12: aconst_null
    //   13: astore 4
    //   15: aconst_null
    //   16: astore 5
    //   18: iconst_0
    //   19: istore 6
    //   21: goto +604 -> 625
    //   24: aload_0
    //   25: monitorexit
    //   26: aload_3
    //   27: areturn
    //   28: invokestatic 70	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   31: astore 16
    //   33: new 72	java/net/URL
    //   36: dup
    //   37: aload_0
    //   38: getfield 25	jp/co/asbit/pvstar/api/UpdateSearchPlaylistTask:uri	Ljava/lang/String;
    //   41: invokespecial 75	java/net/URL:<init>	(Ljava/lang/String;)V
    //   44: invokevirtual 79	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   47: checkcast 81	java/net/HttpURLConnection
    //   50: astore 4
    //   52: aload 4
    //   54: sipush 5000
    //   57: invokevirtual 85	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   60: aload 4
    //   62: sipush 5000
    //   65: invokevirtual 88	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   68: aload 4
    //   70: iconst_0
    //   71: invokevirtual 92	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   74: aload 4
    //   76: invokevirtual 96	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   79: astore 5
    //   81: aload 16
    //   83: aload 5
    //   85: ldc 29
    //   87: invokeinterface 102 3 0
    //   92: iconst_0
    //   93: istore 17
    //   95: aconst_null
    //   96: astore 18
    //   98: ldc 23
    //   100: astore 19
    //   102: aload 16
    //   104: invokeinterface 106 1 0
    //   109: istore 20
    //   111: iload 20
    //   113: istore 21
    //   115: iload 21
    //   117: iconst_1
    //   118: if_icmpne +32 -> 150
    //   121: aload 4
    //   123: ifnull +8 -> 131
    //   126: aload 4
    //   128: invokevirtual 109	java/net/HttpURLConnection:disconnect	()V
    //   131: aload 5
    //   133: ifnull +8 -> 141
    //   136: aload 5
    //   138: invokevirtual 114	java/io/InputStream:close	()V
    //   141: iconst_1
    //   142: istore_2
    //   143: iload 9
    //   145: istore 6
    //   147: goto +478 -> 625
    //   150: aload 16
    //   152: invokeinterface 118 1 0
    //   157: astore 22
    //   159: aload_0
    //   160: invokevirtual 122	jp/co/asbit/pvstar/api/UpdateSearchPlaylistTask:isCancelled	()Z
    //   163: istore 23
    //   165: iload 23
    //   167: ifeq +45 -> 212
    //   170: aload 4
    //   172: ifnull +8 -> 180
    //   175: aload 4
    //   177: invokevirtual 109	java/net/HttpURLConnection:disconnect	()V
    //   180: aload 5
    //   182: ifnull +8 -> 190
    //   185: aload 5
    //   187: invokevirtual 114	java/io/InputStream:close	()V
    //   190: aload_0
    //   191: monitorexit
    //   192: goto -166 -> 26
    //   195: astore 8
    //   197: aload_0
    //   198: monitorexit
    //   199: aload 8
    //   201: athrow
    //   202: astore 27
    //   204: aload 27
    //   206: invokevirtual 123	java/lang/Exception:printStackTrace	()V
    //   209: goto -19 -> 190
    //   212: iload 21
    //   214: iconst_2
    //   215: if_icmpne +108 -> 323
    //   218: aload 22
    //   220: ldc 125
    //   222: invokevirtual 129	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   225: ifeq +141 -> 366
    //   228: aload 16
    //   230: iconst_0
    //   231: invokeinterface 133 2 0
    //   236: astore 19
    //   238: aload 16
    //   240: invokeinterface 136 1 0
    //   245: pop
    //   246: aload 16
    //   248: invokeinterface 106 1 0
    //   253: iconst_4
    //   254: if_icmpne +69 -> 323
    //   257: aload 16
    //   259: invokeinterface 139 1 0
    //   264: astore 26
    //   266: aload 22
    //   268: ldc 140
    //   270: invokevirtual 129	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   273: ifeq +15 -> 288
    //   276: aload_0
    //   277: aload 26
    //   279: invokestatic 143	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   282: invokevirtual 146	java/lang/Integer:intValue	()I
    //   285: putfield 21	jp/co/asbit/pvstar/api/UpdateSearchPlaylistTask:totalResults	I
    //   288: iload 17
    //   290: ifeq +33 -> 323
    //   293: aload 22
    //   295: ldc 148
    //   297: invokevirtual 129	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   300: ifeq +91 -> 391
    //   303: aload 18
    //   305: aload 26
    //   307: invokevirtual 153	jp/co/asbit/pvstar/Playlist:setTitle	(Ljava/lang/String;)V
    //   310: aload 18
    //   312: aload 19
    //   314: invokevirtual 156	jp/co/asbit/pvstar/Playlist:setSearchEngine	(Ljava/lang/String;)V
    //   317: aload 18
    //   319: iconst_1
    //   320: invokevirtual 159	jp/co/asbit/pvstar/Playlist:setListType	(I)V
    //   323: aload 16
    //   325: invokeinterface 106 1 0
    //   330: iconst_3
    //   331: if_icmpne +23 -> 354
    //   334: aload 22
    //   336: ldc 161
    //   338: invokevirtual 129	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   341: ifeq +13 -> 354
    //   344: iconst_0
    //   345: istore 17
    //   347: aload_3
    //   348: aload 18
    //   350: invokevirtual 164	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   353: pop
    //   354: aload 16
    //   356: invokeinterface 136 1 0
    //   361: istore 21
    //   363: goto -248 -> 115
    //   366: aload 22
    //   368: ldc 161
    //   370: invokevirtual 129	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   373: ifeq -135 -> 238
    //   376: iconst_1
    //   377: istore 17
    //   379: new 150	jp/co/asbit/pvstar/Playlist
    //   382: dup
    //   383: invokespecial 165	jp/co/asbit/pvstar/Playlist:<init>	()V
    //   386: astore 18
    //   388: goto -150 -> 238
    //   391: aload 22
    //   393: ldc 167
    //   395: invokevirtual 129	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   398: ifeq +47 -> 445
    //   401: aload 18
    //   403: aload 26
    //   405: invokevirtual 170	jp/co/asbit/pvstar/Playlist:setId	(Ljava/lang/String;)V
    //   408: goto -85 -> 323
    //   411: astore 14
    //   413: aload 14
    //   415: invokevirtual 171	org/xmlpull/v1/XmlPullParserException:printStackTrace	()V
    //   418: aload 4
    //   420: ifnull +8 -> 428
    //   423: aload 4
    //   425: invokevirtual 109	java/net/HttpURLConnection:disconnect	()V
    //   428: aload 5
    //   430: ifnull +182 -> 612
    //   433: aload 5
    //   435: invokevirtual 114	java/io/InputStream:close	()V
    //   438: iload 9
    //   440: istore 6
    //   442: goto +183 -> 625
    //   445: aload 22
    //   447: ldc 173
    //   449: invokevirtual 129	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   452: ifeq +45 -> 497
    //   455: aload 18
    //   457: aload 26
    //   459: invokevirtual 176	jp/co/asbit/pvstar/Playlist:setDescription	(Ljava/lang/String;)V
    //   462: goto -139 -> 323
    //   465: astore 12
    //   467: aload 12
    //   469: invokevirtual 123	java/lang/Exception:printStackTrace	()V
    //   472: aload 4
    //   474: ifnull +8 -> 482
    //   477: aload 4
    //   479: invokevirtual 109	java/net/HttpURLConnection:disconnect	()V
    //   482: aload 5
    //   484: ifnull +8 -> 492
    //   487: aload 5
    //   489: invokevirtual 114	java/io/InputStream:close	()V
    //   492: aload_0
    //   493: monitorexit
    //   494: goto -468 -> 26
    //   497: aload 22
    //   499: ldc 178
    //   501: invokevirtual 129	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   504: ifeq +44 -> 548
    //   507: aload 18
    //   509: aload 26
    //   511: invokestatic 143	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   514: invokevirtual 146	java/lang/Integer:intValue	()I
    //   517: invokevirtual 181	jp/co/asbit/pvstar/Playlist:setVideoCount	(I)V
    //   520: goto -197 -> 323
    //   523: astore 10
    //   525: aload 4
    //   527: ifnull +8 -> 535
    //   530: aload 4
    //   532: invokevirtual 109	java/net/HttpURLConnection:disconnect	()V
    //   535: aload 5
    //   537: ifnull +8 -> 545
    //   540: aload 5
    //   542: invokevirtual 114	java/io/InputStream:close	()V
    //   545: aload 10
    //   547: athrow
    //   548: aload 22
    //   550: ldc 183
    //   552: invokevirtual 129	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   555: ifeq -232 -> 323
    //   558: aload 18
    //   560: aload 26
    //   562: invokevirtual 186	jp/co/asbit/pvstar/Playlist:setThumbnailUrl	(Ljava/lang/String;)V
    //   565: goto -242 -> 323
    //   568: astore 15
    //   570: aload 15
    //   572: invokevirtual 123	java/lang/Exception:printStackTrace	()V
    //   575: iload 9
    //   577: istore 6
    //   579: goto +46 -> 625
    //   582: astore 13
    //   584: aload 13
    //   586: invokevirtual 123	java/lang/Exception:printStackTrace	()V
    //   589: goto -97 -> 492
    //   592: astore 11
    //   594: aload 11
    //   596: invokevirtual 123	java/lang/Exception:printStackTrace	()V
    //   599: goto -54 -> 545
    //   602: astore 28
    //   604: aload 28
    //   606: invokevirtual 123	java/lang/Exception:printStackTrace	()V
    //   609: goto -468 -> 141
    //   612: iload 9
    //   614: istore 6
    //   616: goto +9 -> 625
    //   619: iload 6
    //   621: pop
    //   622: goto -598 -> 24
    //   625: iload_2
    //   626: ifne -7 -> 619
    //   629: iload 6
    //   631: iconst_1
    //   632: iadd
    //   633: istore 9
    //   635: iload 6
    //   637: iconst_3
    //   638: if_icmplt -610 -> 28
    //   641: goto -617 -> 24
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	644	0	this	UpdateSearchPlaylistTask
    //   0	644	1	paramVarArgs	URL[]
    //   1	625	2	i	int
    //   11	337	3	localArrayList	ArrayList
    //   13	518	4	localHttpURLConnection	java.net.HttpURLConnection
    //   16	525	5	localInputStream	java.io.InputStream
    //   19	620	6	j	int
    //   195	5	8	localObject1	Object
    //   143	491	9	k	int
    //   523	23	10	localObject2	Object
    //   592	3	11	localException1	java.lang.Exception
    //   465	3	12	localException2	java.lang.Exception
    //   582	3	13	localException3	java.lang.Exception
    //   411	3	14	localXmlPullParserException	org.xmlpull.v1.XmlPullParserException
    //   568	3	15	localException4	java.lang.Exception
    //   31	324	16	localXmlPullParser	org.xmlpull.v1.XmlPullParser
    //   93	285	17	m	int
    //   96	463	18	localObject3	Object
    //   100	213	19	str1	String
    //   109	3	20	n	int
    //   113	249	21	i1	int
    //   157	392	22	str2	String
    //   163	3	23	bool	boolean
    //   264	297	26	str3	String
    //   202	3	27	localException5	java.lang.Exception
    //   602	3	28	localException6	java.lang.Exception
    // Exception table:
    //   from	to	target	type
    //   4	26	195	finally
    //   126	141	195	finally
    //   175	190	195	finally
    //   190	199	195	finally
    //   204	209	195	finally
    //   423	438	195	finally
    //   477	492	195	finally
    //   492	494	195	finally
    //   530	545	195	finally
    //   545	548	195	finally
    //   570	609	195	finally
    //   175	190	202	java/lang/Exception
    //   28	111	411	org/xmlpull/v1/XmlPullParserException
    //   150	165	411	org/xmlpull/v1/XmlPullParserException
    //   218	408	411	org/xmlpull/v1/XmlPullParserException
    //   445	462	411	org/xmlpull/v1/XmlPullParserException
    //   497	520	411	org/xmlpull/v1/XmlPullParserException
    //   548	565	411	org/xmlpull/v1/XmlPullParserException
    //   28	111	465	java/lang/Exception
    //   150	165	465	java/lang/Exception
    //   218	408	465	java/lang/Exception
    //   445	462	465	java/lang/Exception
    //   497	520	465	java/lang/Exception
    //   548	565	465	java/lang/Exception
    //   28	111	523	finally
    //   150	165	523	finally
    //   218	408	523	finally
    //   413	418	523	finally
    //   445	462	523	finally
    //   467	472	523	finally
    //   497	520	523	finally
    //   548	565	523	finally
    //   423	438	568	java/lang/Exception
    //   477	492	582	java/lang/Exception
    //   530	545	592	java/lang/Exception
    //   126	141	602	java/lang/Exception
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.UpdateSearchPlaylistTask
 * JD-Core Version:    0.7.0.1
 */