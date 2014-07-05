package jp.co.asbit.pvstar.api;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import jp.co.asbit.pvstar.Playlist;

public class ImportYouTubeUserPlaylists
  extends ImportPlaylists
{
  public static final String FAVORITE = "FAVORITE";
  private static final String FAVORITE_URL = "http://gdata.youtube.com/feeds/api/users/default/favorites?start-index=%d&max-results=%d";
  private static final int MAX_RESULTS = 50;
  private static final String PLAYLIST_URL = "http://gdata.youtube.com/feeds/api/playlists/%s?v=2&start-index=%d&max-results=%d";
  private static final String TAG = null;
  private Context mContext;
  private int totalResults = 0;
  
  public ImportYouTubeUserPlaylists(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  /* Error */
  private ArrayList<jp.co.asbit.pvstar.Video> getVideosFavorite(int paramInt)
  {
    // Byte code:
    //   0: new 44	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 45	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: aconst_null
    //   9: astore_3
    //   10: aconst_null
    //   11: astore 4
    //   13: invokestatic 51	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   16: astore 13
    //   18: iconst_2
    //   19: anewarray 53	java/lang/Object
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
    //   35: invokestatic 59	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   38: aastore
    //   39: aload 14
    //   41: iconst_1
    //   42: bipush 50
    //   44: invokestatic 59	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   47: aastore
    //   48: ldc 10
    //   50: aload 14
    //   52: invokestatic 65	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   55: astore 15
    //   57: new 67	java/net/URL
    //   60: dup
    //   61: aload 15
    //   63: invokespecial 70	java/net/URL:<init>	(Ljava/lang/String;)V
    //   66: astore 16
    //   68: aload 16
    //   70: invokevirtual 74	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   73: checkcast 76	java/net/HttpURLConnection
    //   76: astore_3
    //   77: aload_3
    //   78: ldc 78
    //   80: new 80	java/lang/StringBuilder
    //   83: dup
    //   84: ldc 82
    //   86: invokespecial 83	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   89: getstatic 88	jp/co/asbit/pvstar/video/YouTube:auth	Ljava/lang/String;
    //   92: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   98: invokevirtual 100	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   101: aload_3
    //   102: ldc 102
    //   104: ldc 104
    //   106: invokevirtual 100	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   109: aload_3
    //   110: sipush 5000
    //   113: invokevirtual 108	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   116: aload_3
    //   117: sipush 5000
    //   120: invokevirtual 111	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   123: aload_3
    //   124: iconst_1
    //   125: invokevirtual 115	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   128: aload_3
    //   129: invokevirtual 119	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   132: astore 4
    //   134: aload 13
    //   136: aload 4
    //   138: ldc 121
    //   140: invokeinterface 127 3 0
    //   145: iconst_0
    //   146: istore 17
    //   148: aconst_null
    //   149: astore 18
    //   151: aconst_null
    //   152: astore 19
    //   154: aload 13
    //   156: invokeinterface 131 1 0
    //   161: istore 20
    //   163: iload 20
    //   165: istore 21
    //   167: iload 21
    //   169: iconst_1
    //   170: if_icmpne +23 -> 193
    //   173: aload_3
    //   174: ifnull +7 -> 181
    //   177: aload_3
    //   178: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   181: aload 4
    //   183: ifnull +601 -> 784
    //   186: aload 4
    //   188: invokevirtual 139	java/io/InputStream:close	()V
    //   191: aload_2
    //   192: areturn
    //   193: iload 21
    //   195: iconst_2
    //   196: if_icmpne +174 -> 370
    //   199: aload 13
    //   201: invokeinterface 142 1 0
    //   206: astore 19
    //   208: aload 19
    //   210: ldc 144
    //   212: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   215: ifeq +15 -> 230
    //   218: iconst_1
    //   219: istore 17
    //   221: new 150	jp/co/asbit/pvstar/Video
    //   224: dup
    //   225: invokespecial 151	jp/co/asbit/pvstar/Video:<init>	()V
    //   228: astore 18
    //   230: iload 17
    //   232: ifeq +138 -> 370
    //   235: aload 19
    //   237: ldc 153
    //   239: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   242: ifeq +32 -> 274
    //   245: aload 13
    //   247: aconst_null
    //   248: ldc 155
    //   250: invokeinterface 159 3 0
    //   255: astore 35
    //   257: aload 35
    //   259: ldc 161
    //   261: invokevirtual 165	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   264: ifeq +10 -> 274
    //   267: aload 18
    //   269: aload 35
    //   271: invokevirtual 168	jp/co/asbit/pvstar/Video:setThumbnailUrl	(Ljava/lang/String;)V
    //   274: aload 19
    //   276: ldc 170
    //   278: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   281: ifeq +89 -> 370
    //   284: aload 13
    //   286: aconst_null
    //   287: ldc 172
    //   289: invokeinterface 159 3 0
    //   294: astore 30
    //   296: aload 30
    //   298: invokestatic 175	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   301: invokevirtual 178	java/lang/Integer:intValue	()I
    //   304: istore 31
    //   306: aload 30
    //   308: invokestatic 175	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   311: invokevirtual 178	java/lang/Integer:intValue	()I
    //   314: i2f
    //   315: ldc 179
    //   317: fdiv
    //   318: f2d
    //   319: invokestatic 185	java/lang/Math:floor	(D)D
    //   322: d2i
    //   323: istore 32
    //   325: iload 31
    //   327: i2f
    //   328: ldc 179
    //   330: frem
    //   331: f2i
    //   332: istore 33
    //   334: iconst_2
    //   335: anewarray 53	java/lang/Object
    //   338: astore 34
    //   340: aload 34
    //   342: iconst_0
    //   343: iload 32
    //   345: invokestatic 59	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   348: aastore
    //   349: aload 34
    //   351: iconst_1
    //   352: iload 33
    //   354: invokestatic 59	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   357: aastore
    //   358: aload 18
    //   360: ldc 187
    //   362: aload 34
    //   364: invokestatic 65	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   367: invokevirtual 190	jp/co/asbit/pvstar/Video:setDuration	(Ljava/lang/String;)V
    //   370: iload 21
    //   372: iconst_4
    //   373: if_icmpne +196 -> 569
    //   376: aload 13
    //   378: invokeinterface 193 1 0
    //   383: astore 24
    //   385: aload 19
    //   387: ldc 194
    //   389: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   392: ifeq +67 -> 459
    //   395: aload_0
    //   396: aload 24
    //   398: invokestatic 175	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   401: invokevirtual 178	java/lang/Integer:intValue	()I
    //   404: putfield 30	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:totalResults	I
    //   407: aload_0
    //   408: getfield 30	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:totalResults	I
    //   411: ifne +48 -> 459
    //   414: aload_0
    //   415: iconst_1
    //   416: invokevirtual 198	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:cancel	(Z)Z
    //   419: pop
    //   420: aload_3
    //   421: ifnull +7 -> 428
    //   424: aload_3
    //   425: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   428: aload_3
    //   429: ifnull +7 -> 436
    //   432: aload_3
    //   433: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   436: aload 4
    //   438: ifnull +8 -> 446
    //   441: aload 4
    //   443: invokevirtual 139	java/io/InputStream:close	()V
    //   446: goto -255 -> 191
    //   449: astore 29
    //   451: aload 29
    //   453: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   456: goto -10 -> 446
    //   459: iload 17
    //   461: ifeq +108 -> 569
    //   464: aload 19
    //   466: ldc 203
    //   468: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   471: ifeq +33 -> 504
    //   474: aload 18
    //   476: ldc 205
    //   478: invokevirtual 208	jp/co/asbit/pvstar/Video:setSearchEngine	(Ljava/lang/String;)V
    //   481: aload 24
    //   483: ldc 210
    //   485: invokevirtual 214	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   488: astore 27
    //   490: aload 18
    //   492: aload 27
    //   494: bipush 255
    //   496: aload 27
    //   498: arraylength
    //   499: iadd
    //   500: aaload
    //   501: invokevirtual 217	jp/co/asbit/pvstar/Video:setId	(Ljava/lang/String;)V
    //   504: aload 19
    //   506: ldc 219
    //   508: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   511: ifeq +10 -> 521
    //   514: aload 18
    //   516: aload 24
    //   518: invokevirtual 222	jp/co/asbit/pvstar/Video:setTitle	(Ljava/lang/String;)V
    //   521: aload 19
    //   523: ldc 224
    //   525: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   528: ifeq +41 -> 569
    //   531: aload 24
    //   533: bipush 10
    //   535: bipush 32
    //   537: invokevirtual 228	java/lang/String:replace	(CC)Ljava/lang/String;
    //   540: astore 25
    //   542: aload 25
    //   544: invokevirtual 231	java/lang/String:length	()I
    //   547: bipush 40
    //   549: if_icmple +74 -> 623
    //   552: aload 25
    //   554: iconst_0
    //   555: bipush 40
    //   557: invokevirtual 235	java/lang/String:substring	(II)Ljava/lang/String;
    //   560: astore 26
    //   562: aload 18
    //   564: aload 26
    //   566: invokevirtual 238	jp/co/asbit/pvstar/Video:setDescription	(Ljava/lang/String;)V
    //   569: iload 21
    //   571: iconst_3
    //   572: if_icmpne +35 -> 607
    //   575: aload 13
    //   577: invokeinterface 142 1 0
    //   582: astore 19
    //   584: aload 19
    //   586: ldc 144
    //   588: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   591: ifeq +16 -> 607
    //   594: aload_2
    //   595: aload 18
    //   597: invokevirtual 241	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   600: pop
    //   601: iconst_0
    //   602: istore 17
    //   604: aconst_null
    //   605: astore 19
    //   607: aload 13
    //   609: invokeinterface 244 1 0
    //   614: istore 22
    //   616: iload 22
    //   618: istore 21
    //   620: goto -453 -> 167
    //   623: aload 25
    //   625: astore 26
    //   627: goto -65 -> 562
    //   630: astore 11
    //   632: aload 11
    //   634: invokevirtual 245	org/xmlpull/v1/XmlPullParserException:printStackTrace	()V
    //   637: aload_3
    //   638: ifnull +7 -> 645
    //   641: aload_3
    //   642: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   645: aload 4
    //   647: ifnull -456 -> 191
    //   650: aload 4
    //   652: invokevirtual 139	java/io/InputStream:close	()V
    //   655: goto -464 -> 191
    //   658: astore 12
    //   660: aload 12
    //   662: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   665: goto -474 -> 191
    //   668: astore 9
    //   670: aload 9
    //   672: invokevirtual 246	java/io/IOException:printStackTrace	()V
    //   675: aload_3
    //   676: ifnull +7 -> 683
    //   679: aload_3
    //   680: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   683: aload 4
    //   685: ifnull -494 -> 191
    //   688: aload 4
    //   690: invokevirtual 139	java/io/InputStream:close	()V
    //   693: goto -502 -> 191
    //   696: astore 10
    //   698: aload 10
    //   700: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   703: goto -512 -> 191
    //   706: astore 7
    //   708: aload 7
    //   710: invokevirtual 247	java/lang/NumberFormatException:printStackTrace	()V
    //   713: aload_3
    //   714: ifnull +7 -> 721
    //   717: aload_3
    //   718: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   721: aload 4
    //   723: ifnull -532 -> 191
    //   726: aload 4
    //   728: invokevirtual 139	java/io/InputStream:close	()V
    //   731: goto -540 -> 191
    //   734: astore 8
    //   736: aload 8
    //   738: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   741: goto -550 -> 191
    //   744: astore 5
    //   746: aload_3
    //   747: ifnull +7 -> 754
    //   750: aload_3
    //   751: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   754: aload 4
    //   756: ifnull +8 -> 764
    //   759: aload 4
    //   761: invokevirtual 139	java/io/InputStream:close	()V
    //   764: aload 5
    //   766: athrow
    //   767: astore 6
    //   769: aload 6
    //   771: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   774: goto -10 -> 764
    //   777: astore 36
    //   779: aload 36
    //   781: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   784: goto -593 -> 191
    //   787: astore 5
    //   789: goto -43 -> 746
    //   792: astore 7
    //   794: goto -86 -> 708
    //   797: astore 9
    //   799: goto -129 -> 670
    //   802: astore 11
    //   804: goto -172 -> 632
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	807	0	this	ImportYouTubeUserPlaylists
    //   0	807	1	paramInt	int
    //   7	588	2	localArrayList	ArrayList
    //   9	742	3	localHttpURLConnection	java.net.HttpURLConnection
    //   11	749	4	localInputStream	java.io.InputStream
    //   744	21	5	localObject1	java.lang.Object
    //   787	1	5	localObject2	java.lang.Object
    //   767	3	6	localException1	Exception
    //   706	3	7	localNumberFormatException1	java.lang.NumberFormatException
    //   792	1	7	localNumberFormatException2	java.lang.NumberFormatException
    //   734	3	8	localException2	Exception
    //   668	3	9	localIOException1	java.io.IOException
    //   797	1	9	localIOException2	java.io.IOException
    //   696	3	10	localException3	Exception
    //   630	3	11	localXmlPullParserException1	org.xmlpull.v1.XmlPullParserException
    //   802	1	11	localXmlPullParserException2	org.xmlpull.v1.XmlPullParserException
    //   658	3	12	localException4	Exception
    //   16	592	13	localXmlPullParser	org.xmlpull.v1.XmlPullParser
    //   22	29	14	arrayOfObject1	java.lang.Object[]
    //   55	7	15	str1	String
    //   66	3	16	localURL	java.net.URL
    //   146	457	17	i	int
    //   149	447	18	localVideo	jp.co.asbit.pvstar.Video
    //   152	454	19	str2	String
    //   161	3	20	j	int
    //   165	454	21	k	int
    //   614	3	22	m	int
    //   383	149	24	str3	String
    //   540	84	25	str4	String
    //   560	66	26	str5	String
    //   488	9	27	arrayOfString	String[]
    //   449	3	29	localException5	Exception
    //   294	13	30	str6	String
    //   304	22	31	n	int
    //   323	21	32	i1	int
    //   332	21	33	i2	int
    //   338	25	34	arrayOfObject2	java.lang.Object[]
    //   255	15	35	str7	String
    //   777	3	36	localException6	Exception
    // Exception table:
    //   from	to	target	type
    //   432	446	449	java/lang/Exception
    //   13	68	630	org/xmlpull/v1/XmlPullParserException
    //   641	655	658	java/lang/Exception
    //   13	68	668	java/io/IOException
    //   679	693	696	java/lang/Exception
    //   13	68	706	java/lang/NumberFormatException
    //   717	731	734	java/lang/Exception
    //   13	68	744	finally
    //   632	637	744	finally
    //   670	675	744	finally
    //   708	713	744	finally
    //   750	764	767	java/lang/Exception
    //   177	191	777	java/lang/Exception
    //   68	163	787	finally
    //   199	428	787	finally
    //   464	616	787	finally
    //   68	163	792	java/lang/NumberFormatException
    //   199	428	792	java/lang/NumberFormatException
    //   464	616	792	java/lang/NumberFormatException
    //   68	163	797	java/io/IOException
    //   199	428	797	java/io/IOException
    //   464	616	797	java/io/IOException
    //   68	163	802	org/xmlpull/v1/XmlPullParserException
    //   199	428	802	org/xmlpull/v1/XmlPullParserException
    //   464	616	802	org/xmlpull/v1/XmlPullParserException
  }
  
  /* Error */
  private ArrayList<jp.co.asbit.pvstar.Video> getVideosPlaylist(int paramInt, String paramString)
  {
    // Byte code:
    //   0: aload_2
    //   1: ldc 7
    //   3: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   6: ifeq +11 -> 17
    //   9: aload_0
    //   10: iload_1
    //   11: invokespecial 253	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:getVideosFavorite	(I)Ljava/util/ArrayList;
    //   14: astore_3
    //   15: aload_3
    //   16: areturn
    //   17: new 44	java/util/ArrayList
    //   20: dup
    //   21: invokespecial 45	java/util/ArrayList:<init>	()V
    //   24: astore_3
    //   25: aconst_null
    //   26: astore 4
    //   28: aconst_null
    //   29: astore 5
    //   31: invokestatic 51	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   34: astore 16
    //   36: iconst_3
    //   37: anewarray 53	java/lang/Object
    //   40: astore 17
    //   42: aload 17
    //   44: iconst_0
    //   45: aload_2
    //   46: aastore
    //   47: aload 17
    //   49: iconst_1
    //   50: iconst_1
    //   51: bipush 50
    //   53: iload_1
    //   54: iconst_1
    //   55: isub
    //   56: imul
    //   57: iconst_2
    //   58: idiv
    //   59: iadd
    //   60: invokestatic 59	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   63: aastore
    //   64: aload 17
    //   66: iconst_2
    //   67: bipush 50
    //   69: invokestatic 59	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   72: aastore
    //   73: new 67	java/net/URL
    //   76: dup
    //   77: ldc 16
    //   79: aload 17
    //   81: invokestatic 65	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   84: invokespecial 70	java/net/URL:<init>	(Ljava/lang/String;)V
    //   87: astore 18
    //   89: getstatic 24	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:TAG	Ljava/lang/String;
    //   92: aload 18
    //   94: invokevirtual 254	java/net/URL:toString	()Ljava/lang/String;
    //   97: invokestatic 260	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   100: pop
    //   101: aload 18
    //   103: invokevirtual 74	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   106: checkcast 76	java/net/HttpURLConnection
    //   109: astore 4
    //   111: aload 4
    //   113: ldc 78
    //   115: new 80	java/lang/StringBuilder
    //   118: dup
    //   119: ldc 82
    //   121: invokespecial 83	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   124: getstatic 88	jp/co/asbit/pvstar/video/YouTube:auth	Ljava/lang/String;
    //   127: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   130: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   133: invokevirtual 100	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   136: aload 4
    //   138: ldc 102
    //   140: ldc 104
    //   142: invokevirtual 100	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   145: getstatic 24	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:TAG	Ljava/lang/String;
    //   148: new 80	java/lang/StringBuilder
    //   151: dup
    //   152: ldc 82
    //   154: invokespecial 83	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   157: getstatic 88	jp/co/asbit/pvstar/video/YouTube:auth	Ljava/lang/String;
    //   160: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   163: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   166: invokestatic 260	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   169: pop
    //   170: getstatic 24	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:TAG	Ljava/lang/String;
    //   173: ldc 104
    //   175: invokestatic 260	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   178: pop
    //   179: aload 4
    //   181: sipush 5000
    //   184: invokevirtual 108	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   187: aload 4
    //   189: sipush 5000
    //   192: invokevirtual 111	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   195: aload 4
    //   197: iconst_1
    //   198: invokevirtual 115	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   201: aload 4
    //   203: invokevirtual 119	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   206: astore 5
    //   208: aload 16
    //   210: aload 4
    //   212: invokevirtual 119	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   215: ldc 121
    //   217: invokeinterface 127 3 0
    //   222: iconst_0
    //   223: istore 22
    //   225: aconst_null
    //   226: astore 23
    //   228: aload 16
    //   230: invokeinterface 131 1 0
    //   235: istore 24
    //   237: iload 24
    //   239: istore 25
    //   241: iload 25
    //   243: iconst_1
    //   244: if_icmpne +26 -> 270
    //   247: aload 4
    //   249: ifnull +8 -> 257
    //   252: aload 4
    //   254: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   257: aload 5
    //   259: ifnull +707 -> 966
    //   262: aload 5
    //   264: invokevirtual 139	java/io/InputStream:close	()V
    //   267: goto -252 -> 15
    //   270: aload 16
    //   272: invokeinterface 142 1 0
    //   277: astore 26
    //   279: iload 25
    //   281: iconst_2
    //   282: if_icmpne +428 -> 710
    //   285: aload 26
    //   287: ldc 144
    //   289: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   292: ifeq +25 -> 317
    //   295: getstatic 24	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:TAG	Ljava/lang/String;
    //   298: ldc_w 262
    //   301: invokestatic 260	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   304: pop
    //   305: iconst_1
    //   306: istore 22
    //   308: new 150	jp/co/asbit/pvstar/Video
    //   311: dup
    //   312: invokespecial 151	jp/co/asbit/pvstar/Video:<init>	()V
    //   315: astore 23
    //   317: iload 22
    //   319: ifeq +138 -> 457
    //   322: aload 26
    //   324: ldc 153
    //   326: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   329: ifeq +32 -> 361
    //   332: aload 16
    //   334: aconst_null
    //   335: ldc 155
    //   337: invokeinterface 159 3 0
    //   342: astore 42
    //   344: aload 42
    //   346: ldc 161
    //   348: invokevirtual 165	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   351: ifeq +10 -> 361
    //   354: aload 23
    //   356: aload 42
    //   358: invokevirtual 168	jp/co/asbit/pvstar/Video:setThumbnailUrl	(Ljava/lang/String;)V
    //   361: aload 26
    //   363: ldc 170
    //   365: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   368: ifeq +89 -> 457
    //   371: aload 16
    //   373: aconst_null
    //   374: ldc 172
    //   376: invokeinterface 159 3 0
    //   381: astore 37
    //   383: aload 37
    //   385: invokestatic 175	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   388: invokevirtual 178	java/lang/Integer:intValue	()I
    //   391: istore 38
    //   393: aload 37
    //   395: invokestatic 175	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   398: invokevirtual 178	java/lang/Integer:intValue	()I
    //   401: i2f
    //   402: ldc 179
    //   404: fdiv
    //   405: f2d
    //   406: invokestatic 185	java/lang/Math:floor	(D)D
    //   409: d2i
    //   410: istore 39
    //   412: iload 38
    //   414: i2f
    //   415: ldc 179
    //   417: frem
    //   418: f2i
    //   419: istore 40
    //   421: iconst_2
    //   422: anewarray 53	java/lang/Object
    //   425: astore 41
    //   427: aload 41
    //   429: iconst_0
    //   430: iload 39
    //   432: invokestatic 59	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   435: aastore
    //   436: aload 41
    //   438: iconst_1
    //   439: iload 40
    //   441: invokestatic 59	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   444: aastore
    //   445: aload 23
    //   447: ldc 187
    //   449: aload 41
    //   451: invokestatic 65	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   454: invokevirtual 190	jp/co/asbit/pvstar/Video:setDuration	(Ljava/lang/String;)V
    //   457: aload 16
    //   459: invokeinterface 244 1 0
    //   464: pop
    //   465: aload 16
    //   467: invokeinterface 131 1 0
    //   472: iconst_4
    //   473: if_icmpne +237 -> 710
    //   476: aload 16
    //   478: invokeinterface 193 1 0
    //   483: astore 30
    //   485: aload 26
    //   487: ldc 194
    //   489: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   492: ifeq +98 -> 590
    //   495: aload_0
    //   496: aload 30
    //   498: invokestatic 175	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   501: invokevirtual 178	java/lang/Integer:intValue	()I
    //   504: putfield 30	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:totalResults	I
    //   507: getstatic 24	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:TAG	Ljava/lang/String;
    //   510: new 80	java/lang/StringBuilder
    //   513: dup
    //   514: ldc_w 264
    //   517: invokespecial 83	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   520: aload_0
    //   521: getfield 30	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:totalResults	I
    //   524: invokevirtual 267	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   527: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   530: invokestatic 260	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   533: pop
    //   534: aload_0
    //   535: getfield 30	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:totalResults	I
    //   538: ifne +52 -> 590
    //   541: aload_0
    //   542: iconst_1
    //   543: invokevirtual 198	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:cancel	(Z)Z
    //   546: pop
    //   547: aload 4
    //   549: ifnull +8 -> 557
    //   552: aload 4
    //   554: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   557: aload 4
    //   559: ifnull +8 -> 567
    //   562: aload 4
    //   564: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   567: aload 5
    //   569: ifnull -554 -> 15
    //   572: aload 5
    //   574: invokevirtual 139	java/io/InputStream:close	()V
    //   577: goto -562 -> 15
    //   580: astore 36
    //   582: aload 36
    //   584: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   587: goto -572 -> 15
    //   590: iload 22
    //   592: ifeq +118 -> 710
    //   595: aload 26
    //   597: ldc_w 269
    //   600: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   603: ifeq +17 -> 620
    //   606: aload 23
    //   608: ldc 205
    //   610: invokevirtual 208	jp/co/asbit/pvstar/Video:setSearchEngine	(Ljava/lang/String;)V
    //   613: aload 23
    //   615: aload 30
    //   617: invokevirtual 217	jp/co/asbit/pvstar/Video:setId	(Ljava/lang/String;)V
    //   620: aload 26
    //   622: ldc 219
    //   624: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   627: ifeq +35 -> 662
    //   630: aload 23
    //   632: aload 30
    //   634: invokevirtual 222	jp/co/asbit/pvstar/Video:setTitle	(Ljava/lang/String;)V
    //   637: getstatic 24	jp/co/asbit/pvstar/api/ImportYouTubeUserPlaylists:TAG	Ljava/lang/String;
    //   640: new 80	java/lang/StringBuilder
    //   643: dup
    //   644: ldc_w 271
    //   647: invokespecial 83	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   650: aload 30
    //   652: invokevirtual 92	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   655: invokevirtual 96	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   658: invokestatic 260	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   661: pop
    //   662: aload 26
    //   664: ldc 224
    //   666: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   669: ifeq +41 -> 710
    //   672: aload 30
    //   674: bipush 10
    //   676: bipush 32
    //   678: invokevirtual 228	java/lang/String:replace	(CC)Ljava/lang/String;
    //   681: astore 31
    //   683: aload 31
    //   685: invokevirtual 231	java/lang/String:length	()I
    //   688: bipush 40
    //   690: if_icmple +67 -> 757
    //   693: aload 31
    //   695: iconst_0
    //   696: bipush 40
    //   698: invokevirtual 235	java/lang/String:substring	(II)Ljava/lang/String;
    //   701: astore 32
    //   703: aload 23
    //   705: aload 32
    //   707: invokevirtual 238	jp/co/asbit/pvstar/Video:setDescription	(Ljava/lang/String;)V
    //   710: aload 16
    //   712: invokeinterface 131 1 0
    //   717: iconst_3
    //   718: if_icmpne +23 -> 741
    //   721: aload 26
    //   723: ldc 144
    //   725: invokevirtual 148	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   728: ifeq +13 -> 741
    //   731: iconst_0
    //   732: istore 22
    //   734: aload_3
    //   735: aload 23
    //   737: invokevirtual 241	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   740: pop
    //   741: aload 16
    //   743: invokeinterface 244 1 0
    //   748: istore 27
    //   750: iload 27
    //   752: istore 25
    //   754: goto -513 -> 241
    //   757: aload 31
    //   759: astore 32
    //   761: goto -58 -> 703
    //   764: astore 14
    //   766: aload 14
    //   768: invokevirtual 245	org/xmlpull/v1/XmlPullParserException:printStackTrace	()V
    //   771: aload 4
    //   773: ifnull +8 -> 781
    //   776: aload 4
    //   778: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   781: aload 5
    //   783: ifnull -768 -> 15
    //   786: aload 5
    //   788: invokevirtual 139	java/io/InputStream:close	()V
    //   791: goto -776 -> 15
    //   794: astore 15
    //   796: aload 15
    //   798: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   801: goto -786 -> 15
    //   804: astore 12
    //   806: aload 12
    //   808: invokevirtual 272	java/net/MalformedURLException:printStackTrace	()V
    //   811: aload 4
    //   813: ifnull +8 -> 821
    //   816: aload 4
    //   818: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   821: aload 5
    //   823: ifnull -808 -> 15
    //   826: aload 5
    //   828: invokevirtual 139	java/io/InputStream:close	()V
    //   831: goto -816 -> 15
    //   834: astore 13
    //   836: aload 13
    //   838: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   841: goto -826 -> 15
    //   844: astore 10
    //   846: aload 10
    //   848: invokevirtual 246	java/io/IOException:printStackTrace	()V
    //   851: aload 4
    //   853: ifnull +8 -> 861
    //   856: aload 4
    //   858: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   861: aload 5
    //   863: ifnull -848 -> 15
    //   866: aload 5
    //   868: invokevirtual 139	java/io/InputStream:close	()V
    //   871: goto -856 -> 15
    //   874: astore 11
    //   876: aload 11
    //   878: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   881: goto -866 -> 15
    //   884: astore 8
    //   886: aload 8
    //   888: invokevirtual 247	java/lang/NumberFormatException:printStackTrace	()V
    //   891: aload 4
    //   893: ifnull +8 -> 901
    //   896: aload 4
    //   898: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   901: aload 5
    //   903: ifnull -888 -> 15
    //   906: aload 5
    //   908: invokevirtual 139	java/io/InputStream:close	()V
    //   911: goto -896 -> 15
    //   914: astore 9
    //   916: aload 9
    //   918: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   921: goto -906 -> 15
    //   924: astore 6
    //   926: aload 4
    //   928: ifnull +8 -> 936
    //   931: aload 4
    //   933: invokevirtual 134	java/net/HttpURLConnection:disconnect	()V
    //   936: aload 5
    //   938: ifnull +8 -> 946
    //   941: aload 5
    //   943: invokevirtual 139	java/io/InputStream:close	()V
    //   946: aload 6
    //   948: athrow
    //   949: astore 7
    //   951: aload 7
    //   953: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   956: goto -10 -> 946
    //   959: astore 44
    //   961: aload 44
    //   963: invokevirtual 201	java/lang/Exception:printStackTrace	()V
    //   966: goto -951 -> 15
    //   969: astore 6
    //   971: goto -45 -> 926
    //   974: astore 8
    //   976: goto -90 -> 886
    //   979: astore 10
    //   981: goto -135 -> 846
    //   984: astore 12
    //   986: goto -180 -> 806
    //   989: astore 14
    //   991: goto -225 -> 766
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	994	0	this	ImportYouTubeUserPlaylists
    //   0	994	1	paramInt	int
    //   0	994	2	paramString	String
    //   14	721	3	localArrayList	ArrayList
    //   26	906	4	localHttpURLConnection	java.net.HttpURLConnection
    //   29	913	5	localInputStream	java.io.InputStream
    //   924	23	6	localObject1	java.lang.Object
    //   969	1	6	localObject2	java.lang.Object
    //   949	3	7	localException1	Exception
    //   884	3	8	localNumberFormatException1	java.lang.NumberFormatException
    //   974	1	8	localNumberFormatException2	java.lang.NumberFormatException
    //   914	3	9	localException2	Exception
    //   844	3	10	localIOException1	java.io.IOException
    //   979	1	10	localIOException2	java.io.IOException
    //   874	3	11	localException3	Exception
    //   804	3	12	localMalformedURLException1	java.net.MalformedURLException
    //   984	1	12	localMalformedURLException2	java.net.MalformedURLException
    //   834	3	13	localException4	Exception
    //   764	3	14	localXmlPullParserException1	org.xmlpull.v1.XmlPullParserException
    //   989	1	14	localXmlPullParserException2	org.xmlpull.v1.XmlPullParserException
    //   794	3	15	localException5	Exception
    //   34	708	16	localXmlPullParser	org.xmlpull.v1.XmlPullParser
    //   40	40	17	arrayOfObject1	java.lang.Object[]
    //   87	15	18	localURL	java.net.URL
    //   223	510	22	i	int
    //   226	510	23	localVideo	jp.co.asbit.pvstar.Video
    //   235	3	24	j	int
    //   239	514	25	k	int
    //   277	445	26	str1	String
    //   748	3	27	m	int
    //   483	190	30	str2	String
    //   681	77	31	str3	String
    //   701	59	32	str4	String
    //   580	3	36	localException6	Exception
    //   381	13	37	str5	String
    //   391	22	38	n	int
    //   410	21	39	i1	int
    //   419	21	40	i2	int
    //   425	25	41	arrayOfObject2	java.lang.Object[]
    //   342	15	42	str6	String
    //   959	3	44	localException7	Exception
    // Exception table:
    //   from	to	target	type
    //   562	577	580	java/lang/Exception
    //   31	89	764	org/xmlpull/v1/XmlPullParserException
    //   776	791	794	java/lang/Exception
    //   31	89	804	java/net/MalformedURLException
    //   816	831	834	java/lang/Exception
    //   31	89	844	java/io/IOException
    //   856	871	874	java/lang/Exception
    //   31	89	884	java/lang/NumberFormatException
    //   896	911	914	java/lang/Exception
    //   31	89	924	finally
    //   766	771	924	finally
    //   806	811	924	finally
    //   846	851	924	finally
    //   886	891	924	finally
    //   931	946	949	java/lang/Exception
    //   252	267	959	java/lang/Exception
    //   89	237	969	finally
    //   270	557	969	finally
    //   595	750	969	finally
    //   89	237	974	java/lang/NumberFormatException
    //   270	557	974	java/lang/NumberFormatException
    //   595	750	974	java/lang/NumberFormatException
    //   89	237	979	java/io/IOException
    //   270	557	979	java/io/IOException
    //   595	750	979	java/io/IOException
    //   89	237	984	java/net/MalformedURLException
    //   270	557	984	java/net/MalformedURLException
    //   595	750	984	java/net/MalformedURLException
    //   89	237	989	org/xmlpull/v1/XmlPullParserException
    //   270	557	989	org/xmlpull/v1/XmlPullParserException
    //   595	750	989	org/xmlpull/v1/XmlPullParserException
  }
  
  protected Integer doInBackground(ArrayList<Playlist>... paramVarArgs)
  {
    Iterator localIterator = paramVarArgs[0].iterator();
    int i = 0;
    try
    {
      Integer localInteger;
      if (!localIterator.hasNext())
      {
        localInteger = Integer.valueOf(i);
      }
      else
      {
        Playlist localPlaylist = (Playlist)localIterator.next();
        this.totalResults = 0;
        ArrayList localArrayList1 = new ArrayList(localPlaylist.getVideoCount());
        int k;
        for (int j = 1;; j = k)
        {
          Log.d(TAG, "page=" + j);
          k = j + 1;
          ArrayList localArrayList2 = getVideosPlaylist(j, localPlaylist.getId());
          Log.d(TAG, "videosnum=" + localArrayList2.size());
          if (localArrayList2.size() == 0) {}
          boolean bool;
          do
          {
            if (!insertVideos(this.mContext, localPlaylist, localArrayList1)) {
              break;
            }
            i++;
            break;
            localArrayList1.addAll(localArrayList2);
            bool = isCancelled();
          } while (bool);
        }
      }
      return localInteger;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      cancel(true);
      localInteger = Integer.valueOf(i);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.ImportYouTubeUserPlaylists
 * JD-Core Version:    0.7.0.1
 */