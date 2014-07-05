package jp.co.asbit.pvstar.api;

import java.util.ArrayList;
import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.Playlist;
import jp.co.asbit.pvstar.video.YouTube;

public class GetYouTubeUserPlaylistsTask
  extends AsyncTask<Integer, Long, ArrayList<Playlist>>
{
  private static final int MAX_RESULTS = 50;
  private static final String USER_PLAYLISTS_URL = "http://gdata.youtube.com/feeds/api/users/default/playlists?v=2&start-index=%d&max-results=%d";
  protected Playlist favorite;
  private String password;
  private int totalResults = 0;
  private String userid;
  
  public GetYouTubeUserPlaylistsTask(String paramString1, String paramString2)
  {
    this.userid = paramString1;
    this.password = paramString2;
  }
  
  /* Error */
  private ArrayList<Playlist> getPlaylists(int paramInt)
  {
    // Byte code:
    //   0: new 40	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 41	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: aconst_null
    //   9: astore_3
    //   10: aconst_null
    //   11: astore 4
    //   13: invokestatic 47	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   16: astore 13
    //   18: iconst_2
    //   19: anewarray 49	java/lang/Object
    //   22: astore 14
    //   24: aload 14
    //   26: iconst_0
    //   27: iconst_1
    //   28: bipush 50
    //   30: iload_1
    //   31: iconst_1
    //   32: isub
    //   33: imul
    //   34: iadd
    //   35: invokestatic 55	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   38: aastore
    //   39: aload 14
    //   41: iconst_1
    //   42: bipush 50
    //   44: invokestatic 55	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   47: aastore
    //   48: new 57	java/net/URL
    //   51: dup
    //   52: ldc 12
    //   54: aload 14
    //   56: invokestatic 63	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   59: invokespecial 66	java/net/URL:<init>	(Ljava/lang/String;)V
    //   62: astore 15
    //   64: aload 15
    //   66: invokevirtual 70	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   69: checkcast 72	java/net/HttpURLConnection
    //   72: astore_3
    //   73: aload_3
    //   74: ldc 74
    //   76: new 76	java/lang/StringBuilder
    //   79: dup
    //   80: ldc 78
    //   82: invokespecial 79	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   85: getstatic 84	jp/co/asbit/pvstar/video/YouTube:auth	Ljava/lang/String;
    //   88: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   94: invokevirtual 95	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   97: aload_3
    //   98: ldc 97
    //   100: ldc 99
    //   102: invokevirtual 95	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   105: aload_3
    //   106: sipush 5000
    //   109: invokevirtual 103	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   112: aload_3
    //   113: sipush 5000
    //   116: invokevirtual 106	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   119: aload_3
    //   120: iconst_1
    //   121: invokevirtual 110	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   124: aload_3
    //   125: invokevirtual 114	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   128: astore 4
    //   130: aload 13
    //   132: aload 4
    //   134: ldc 116
    //   136: invokeinterface 122 3 0
    //   141: iconst_0
    //   142: istore 16
    //   144: aconst_null
    //   145: astore 17
    //   147: aload 13
    //   149: invokeinterface 126 1 0
    //   154: istore 18
    //   156: iload 18
    //   158: istore 19
    //   160: iload 19
    //   162: iconst_1
    //   163: if_icmpne +23 -> 186
    //   166: aload_3
    //   167: ifnull +7 -> 174
    //   170: aload_3
    //   171: invokevirtual 129	java/net/HttpURLConnection:disconnect	()V
    //   174: aload 4
    //   176: ifnull +377 -> 553
    //   179: aload 4
    //   181: invokevirtual 134	java/io/InputStream:close	()V
    //   184: aload_2
    //   185: areturn
    //   186: aload 13
    //   188: invokeinterface 137 1 0
    //   193: astore 20
    //   195: iload 19
    //   197: iconst_2
    //   198: if_icmpne +154 -> 352
    //   201: aload 20
    //   203: ldc 139
    //   205: invokevirtual 143	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   208: ifeq +15 -> 223
    //   211: iconst_1
    //   212: istore 16
    //   214: new 145	jp/co/asbit/pvstar/Playlist
    //   217: dup
    //   218: invokespecial 146	jp/co/asbit/pvstar/Playlist:<init>	()V
    //   221: astore 17
    //   223: aload 13
    //   225: invokeinterface 149 1 0
    //   230: pop
    //   231: aload 13
    //   233: invokeinterface 126 1 0
    //   238: iconst_4
    //   239: if_icmpne +113 -> 352
    //   242: aload 13
    //   244: invokeinterface 152 1 0
    //   249: astore 24
    //   251: aload 20
    //   253: ldc 153
    //   255: invokevirtual 143	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   258: ifeq +15 -> 273
    //   261: aload_0
    //   262: aload 24
    //   264: invokestatic 156	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   267: invokevirtual 159	java/lang/Integer:intValue	()I
    //   270: putfield 24	jp/co/asbit/pvstar/api/GetYouTubeUserPlaylistsTask:totalResults	I
    //   273: iload 16
    //   275: ifeq +77 -> 352
    //   278: aload 20
    //   280: ldc 161
    //   282: invokevirtual 143	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   285: ifeq +10 -> 295
    //   288: aload 17
    //   290: aload 24
    //   292: invokevirtual 164	jp/co/asbit/pvstar/Playlist:setId	(Ljava/lang/String;)V
    //   295: aload 20
    //   297: ldc 166
    //   299: invokevirtual 143	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   302: ifeq +10 -> 312
    //   305: aload 17
    //   307: aload 24
    //   309: invokevirtual 169	jp/co/asbit/pvstar/Playlist:setTitle	(Ljava/lang/String;)V
    //   312: aload 20
    //   314: ldc 171
    //   316: invokevirtual 143	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   319: ifeq +10 -> 329
    //   322: aload 17
    //   324: aload 24
    //   326: invokevirtual 174	jp/co/asbit/pvstar/Playlist:setDescription	(Ljava/lang/String;)V
    //   329: aload 20
    //   331: ldc 176
    //   333: invokevirtual 143	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   336: ifeq +16 -> 352
    //   339: aload 17
    //   341: aload 24
    //   343: invokestatic 156	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   346: invokevirtual 159	java/lang/Integer:intValue	()I
    //   349: invokevirtual 179	jp/co/asbit/pvstar/Playlist:setVideoCount	(I)V
    //   352: aload 13
    //   354: invokeinterface 126 1 0
    //   359: iconst_3
    //   360: if_icmpne +23 -> 383
    //   363: aload 20
    //   365: ldc 139
    //   367: invokevirtual 143	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   370: ifeq +13 -> 383
    //   373: iconst_0
    //   374: istore 16
    //   376: aload_2
    //   377: aload 17
    //   379: invokevirtual 182	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   382: pop
    //   383: aload 13
    //   385: invokeinterface 149 1 0
    //   390: istore 21
    //   392: iload 21
    //   394: istore 19
    //   396: goto -236 -> 160
    //   399: astore 11
    //   401: aload 11
    //   403: invokevirtual 185	org/xmlpull/v1/XmlPullParserException:printStackTrace	()V
    //   406: aload_3
    //   407: ifnull +7 -> 414
    //   410: aload_3
    //   411: invokevirtual 129	java/net/HttpURLConnection:disconnect	()V
    //   414: aload 4
    //   416: ifnull -232 -> 184
    //   419: aload 4
    //   421: invokevirtual 134	java/io/InputStream:close	()V
    //   424: goto -240 -> 184
    //   427: astore 12
    //   429: aload 12
    //   431: invokevirtual 186	java/lang/Exception:printStackTrace	()V
    //   434: goto -250 -> 184
    //   437: astore 9
    //   439: aload 9
    //   441: invokevirtual 187	java/net/MalformedURLException:printStackTrace	()V
    //   444: aload_3
    //   445: ifnull +7 -> 452
    //   448: aload_3
    //   449: invokevirtual 129	java/net/HttpURLConnection:disconnect	()V
    //   452: aload 4
    //   454: ifnull -270 -> 184
    //   457: aload 4
    //   459: invokevirtual 134	java/io/InputStream:close	()V
    //   462: goto -278 -> 184
    //   465: astore 10
    //   467: aload 10
    //   469: invokevirtual 186	java/lang/Exception:printStackTrace	()V
    //   472: goto -288 -> 184
    //   475: astore 7
    //   477: aload 7
    //   479: invokevirtual 188	java/io/IOException:printStackTrace	()V
    //   482: aload_3
    //   483: ifnull +7 -> 490
    //   486: aload_3
    //   487: invokevirtual 129	java/net/HttpURLConnection:disconnect	()V
    //   490: aload 4
    //   492: ifnull -308 -> 184
    //   495: aload 4
    //   497: invokevirtual 134	java/io/InputStream:close	()V
    //   500: goto -316 -> 184
    //   503: astore 8
    //   505: aload 8
    //   507: invokevirtual 186	java/lang/Exception:printStackTrace	()V
    //   510: goto -326 -> 184
    //   513: astore 5
    //   515: aload_3
    //   516: ifnull +7 -> 523
    //   519: aload_3
    //   520: invokevirtual 129	java/net/HttpURLConnection:disconnect	()V
    //   523: aload 4
    //   525: ifnull +8 -> 533
    //   528: aload 4
    //   530: invokevirtual 134	java/io/InputStream:close	()V
    //   533: aload 5
    //   535: athrow
    //   536: astore 6
    //   538: aload 6
    //   540: invokevirtual 186	java/lang/Exception:printStackTrace	()V
    //   543: goto -10 -> 533
    //   546: astore 25
    //   548: aload 25
    //   550: invokevirtual 186	java/lang/Exception:printStackTrace	()V
    //   553: goto -369 -> 184
    //   556: astore 5
    //   558: goto -43 -> 515
    //   561: astore 7
    //   563: goto -86 -> 477
    //   566: astore 9
    //   568: goto -129 -> 439
    //   571: astore 11
    //   573: goto -172 -> 401
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	576	0	this	GetYouTubeUserPlaylistsTask
    //   0	576	1	paramInt	int
    //   7	370	2	localArrayList	ArrayList
    //   9	511	3	localHttpURLConnection	java.net.HttpURLConnection
    //   11	518	4	localInputStream	java.io.InputStream
    //   513	21	5	localObject1	java.lang.Object
    //   556	1	5	localObject2	java.lang.Object
    //   536	3	6	localException1	Exception
    //   475	3	7	localIOException1	java.io.IOException
    //   561	1	7	localIOException2	java.io.IOException
    //   503	3	8	localException2	Exception
    //   437	3	9	localMalformedURLException1	java.net.MalformedURLException
    //   566	1	9	localMalformedURLException2	java.net.MalformedURLException
    //   465	3	10	localException3	Exception
    //   399	3	11	localXmlPullParserException1	org.xmlpull.v1.XmlPullParserException
    //   571	1	11	localXmlPullParserException2	org.xmlpull.v1.XmlPullParserException
    //   427	3	12	localException4	Exception
    //   16	368	13	localXmlPullParser	org.xmlpull.v1.XmlPullParser
    //   22	33	14	arrayOfObject	java.lang.Object[]
    //   62	3	15	localURL	java.net.URL
    //   142	233	16	i	int
    //   145	233	17	localPlaylist	Playlist
    //   154	3	18	j	int
    //   158	237	19	k	int
    //   193	171	20	str1	String
    //   390	3	21	m	int
    //   249	93	24	str2	String
    //   546	3	25	localException5	Exception
    // Exception table:
    //   from	to	target	type
    //   13	64	399	org/xmlpull/v1/XmlPullParserException
    //   410	424	427	java/lang/Exception
    //   13	64	437	java/net/MalformedURLException
    //   448	462	465	java/lang/Exception
    //   13	64	475	java/io/IOException
    //   486	500	503	java/lang/Exception
    //   13	64	513	finally
    //   401	406	513	finally
    //   439	444	513	finally
    //   477	482	513	finally
    //   519	533	536	java/lang/Exception
    //   170	184	546	java/lang/Exception
    //   64	156	556	finally
    //   186	392	556	finally
    //   64	156	561	java/io/IOException
    //   186	392	561	java/io/IOException
    //   64	156	566	java/net/MalformedURLException
    //   186	392	566	java/net/MalformedURLException
    //   64	156	571	org/xmlpull/v1/XmlPullParserException
    //   186	392	571	org/xmlpull/v1/XmlPullParserException
  }
  
  protected ArrayList<Playlist> doInBackground(Integer... paramVarArgs)
  {
    ArrayList localArrayList;
    if (YouTube.login(this.userid, this.password)) {
      try
      {
        localArrayList = new ArrayList();
        localArrayList.add(this.favorite);
        int j;
        for (int i = 1;; i = j)
        {
          j = i + 1;
          localArrayList.addAll(getPlaylists(i));
          if ((isCancelled()) || (j * 50 > 200)) {
            break;
          }
          int k = this.totalResults;
          int m = localArrayList.size();
          if (k <= m) {
            break;
          }
        }
        localArrayList = null;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    return localArrayList;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.GetYouTubeUserPlaylistsTask
 * JD-Core Version:    0.7.0.1
 */