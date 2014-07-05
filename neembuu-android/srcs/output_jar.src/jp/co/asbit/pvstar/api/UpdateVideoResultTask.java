package jp.co.asbit.pvstar.api;

import java.util.ArrayList;
import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.Video;
import jp.co.asbit.pvstar.search.SearchCondItem;

public class UpdateVideoResultTask
  extends AsyncTask<String, Integer, ArrayList<Video>>
{
  protected static final String TAG = "UpdateSearchResultTask";
  protected ArrayList<SearchCondItem> orders = new ArrayList();
  protected String title = null;
  protected int totalReuslts = 0;
  protected String uri;
  
  /* Error */
  protected ArrayList<Video> doInBackground(String... paramVarArgs)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: aload_0
    //   5: monitorenter
    //   6: aconst_null
    //   7: astore 4
    //   9: aconst_null
    //   10: astore 5
    //   12: aconst_null
    //   13: astore 6
    //   15: iconst_0
    //   16: istore 7
    //   18: iload_2
    //   19: ifne +788 -> 807
    //   22: iload 7
    //   24: iconst_1
    //   25: iadd
    //   26: istore 12
    //   28: iload 7
    //   30: iconst_3
    //   31: if_icmplt +11 -> 42
    //   34: aload_0
    //   35: monitorexit
    //   36: aload 6
    //   38: pop
    //   39: aload 6
    //   41: areturn
    //   42: invokestatic 46	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   45: astore 22
    //   47: new 26	java/util/ArrayList
    //   50: dup
    //   51: invokespecial 27	java/util/ArrayList:<init>	()V
    //   54: astore 14
    //   56: new 48	java/net/URL
    //   59: dup
    //   60: aload_0
    //   61: getfield 50	jp/co/asbit/pvstar/api/UpdateVideoResultTask:uri	Ljava/lang/String;
    //   64: invokespecial 53	java/net/URL:<init>	(Ljava/lang/String;)V
    //   67: invokevirtual 57	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   70: checkcast 59	java/net/HttpURLConnection
    //   73: astore 4
    //   75: ldc 9
    //   77: new 61	java/lang/StringBuilder
    //   80: dup
    //   81: ldc 63
    //   83: invokespecial 64	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   86: invokestatic 70	java/util/Locale:getDefault	()Ljava/util/Locale;
    //   89: invokevirtual 74	java/util/Locale:getLanguage	()Ljava/lang/String;
    //   92: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: invokevirtual 81	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   98: invokestatic 87	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   101: pop
    //   102: aload 4
    //   104: ldc 89
    //   106: invokestatic 70	java/util/Locale:getDefault	()Ljava/util/Locale;
    //   109: invokevirtual 74	java/util/Locale:getLanguage	()Ljava/lang/String;
    //   112: invokevirtual 93	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   115: aload 4
    //   117: sipush 5000
    //   120: invokevirtual 97	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   123: aload 4
    //   125: sipush 5000
    //   128: invokevirtual 100	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   131: aload 4
    //   133: iconst_0
    //   134: invokevirtual 104	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   137: aload 4
    //   139: invokevirtual 108	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   142: astore 5
    //   144: aload 22
    //   146: aload 5
    //   148: ldc 110
    //   150: invokeinterface 116 3 0
    //   155: iconst_0
    //   156: istore 24
    //   158: aconst_null
    //   159: astore 25
    //   161: aload 22
    //   163: invokeinterface 120 1 0
    //   168: istore 26
    //   170: iload 26
    //   172: istore 27
    //   174: iload 27
    //   176: iconst_1
    //   177: if_icmpne +36 -> 213
    //   180: aload 4
    //   182: ifnull +8 -> 190
    //   185: aload 4
    //   187: invokevirtual 123	java/net/HttpURLConnection:disconnect	()V
    //   190: aload 5
    //   192: ifnull +8 -> 200
    //   195: aload 5
    //   197: invokevirtual 128	java/io/InputStream:close	()V
    //   200: iconst_1
    //   201: istore_2
    //   202: aload 14
    //   204: astore 6
    //   206: iload 12
    //   208: istore 7
    //   210: goto -192 -> 18
    //   213: aload 22
    //   215: invokeinterface 131 1 0
    //   220: astore 28
    //   222: aload_0
    //   223: invokevirtual 135	jp/co/asbit/pvstar/api/UpdateVideoResultTask:isCancelled	()Z
    //   226: istore 29
    //   228: iload 29
    //   230: ifeq +47 -> 277
    //   233: aload 4
    //   235: ifnull +8 -> 243
    //   238: aload 4
    //   240: invokevirtual 123	java/net/HttpURLConnection:disconnect	()V
    //   243: aload 5
    //   245: ifnull +8 -> 253
    //   248: aload 5
    //   250: invokevirtual 128	java/io/InputStream:close	()V
    //   253: aload_0
    //   254: monitorexit
    //   255: aload 14
    //   257: astore 6
    //   259: goto -220 -> 39
    //   262: astore 34
    //   264: aload 34
    //   266: invokevirtual 138	java/lang/Exception:printStackTrace	()V
    //   269: goto -16 -> 253
    //   272: aload_0
    //   273: monitorexit
    //   274: aload 9
    //   276: athrow
    //   277: iload 27
    //   279: iconst_2
    //   280: if_icmpne +100 -> 380
    //   283: aload 28
    //   285: ldc 140
    //   287: invokevirtual 146	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   290: ifeq +134 -> 424
    //   293: aload 22
    //   295: iconst_0
    //   296: invokeinterface 150 2 0
    //   301: astore_3
    //   302: aload 22
    //   304: invokeinterface 153 1 0
    //   309: pop
    //   310: aload 22
    //   312: invokeinterface 120 1 0
    //   317: iconst_4
    //   318: if_icmpne +62 -> 380
    //   321: aload 22
    //   323: invokeinterface 156 1 0
    //   328: astore 33
    //   330: aload 28
    //   332: ldc 158
    //   334: invokevirtual 146	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   337: ifeq +194 -> 531
    //   340: aload_0
    //   341: aload 33
    //   343: invokestatic 164	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   346: invokevirtual 167	java/lang/Integer:intValue	()I
    //   349: putfield 22	jp/co/asbit/pvstar/api/UpdateVideoResultTask:totalReuslts	I
    //   352: iload 24
    //   354: ifeq +26 -> 380
    //   357: aload 28
    //   359: ldc 168
    //   361: invokevirtual 146	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   364: ifeq +226 -> 590
    //   367: aload 25
    //   369: aload_3
    //   370: invokevirtual 173	jp/co/asbit/pvstar/Video:setSearchEngine	(Ljava/lang/String;)V
    //   373: aload 25
    //   375: aload 33
    //   377: invokevirtual 176	jp/co/asbit/pvstar/Video:setTitle	(Ljava/lang/String;)V
    //   380: aload 22
    //   382: invokeinterface 120 1 0
    //   387: iconst_3
    //   388: if_icmpne +24 -> 412
    //   391: aload 28
    //   393: ldc 178
    //   395: invokevirtual 146	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   398: ifeq +14 -> 412
    //   401: iconst_0
    //   402: istore 24
    //   404: aload 14
    //   406: aload 25
    //   408: invokevirtual 181	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   411: pop
    //   412: aload 22
    //   414: invokeinterface 153 1 0
    //   419: istore 27
    //   421: goto -247 -> 174
    //   424: aload 28
    //   426: ldc 178
    //   428: invokevirtual 146	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   431: ifeq +18 -> 449
    //   434: iconst_1
    //   435: istore 24
    //   437: new 170	jp/co/asbit/pvstar/Video
    //   440: dup
    //   441: invokespecial 182	jp/co/asbit/pvstar/Video:<init>	()V
    //   444: astore 25
    //   446: goto -144 -> 302
    //   449: aload 28
    //   451: ldc 184
    //   453: invokevirtual 146	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   456: ifeq -154 -> 302
    //   459: aload_0
    //   460: getfield 29	jp/co/asbit/pvstar/api/UpdateVideoResultTask:orders	Ljava/util/ArrayList;
    //   463: new 186	jp/co/asbit/pvstar/search/SearchCondItem
    //   466: dup
    //   467: aload 22
    //   469: iconst_0
    //   470: invokeinterface 150 2 0
    //   475: aload 22
    //   477: iconst_1
    //   478: invokeinterface 150 2 0
    //   483: invokespecial 188	jp/co/asbit/pvstar/search/SearchCondItem:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   486: invokevirtual 181	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   489: pop
    //   490: goto -188 -> 302
    //   493: astore 13
    //   495: aload 13
    //   497: invokevirtual 189	org/xmlpull/v1/XmlPullParserException:printStackTrace	()V
    //   500: aload 4
    //   502: ifnull +8 -> 510
    //   505: aload 4
    //   507: invokevirtual 123	java/net/HttpURLConnection:disconnect	()V
    //   510: aload 5
    //   512: ifnull +284 -> 796
    //   515: aload 5
    //   517: invokevirtual 128	java/io/InputStream:close	()V
    //   520: aload 14
    //   522: astore 6
    //   524: iload 12
    //   526: istore 7
    //   528: goto -510 -> 18
    //   531: iload 24
    //   533: ifne -181 -> 352
    //   536: aload 28
    //   538: ldc 168
    //   540: invokevirtual 146	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   543: ifeq -191 -> 352
    //   546: aload_0
    //   547: aload 33
    //   549: putfield 24	jp/co/asbit/pvstar/api/UpdateVideoResultTask:title	Ljava/lang/String;
    //   552: goto -200 -> 352
    //   555: astore 18
    //   557: aload 18
    //   559: invokevirtual 138	java/lang/Exception:printStackTrace	()V
    //   562: aload 4
    //   564: ifnull +8 -> 572
    //   567: aload 4
    //   569: invokevirtual 123	java/net/HttpURLConnection:disconnect	()V
    //   572: aload 5
    //   574: ifnull +8 -> 582
    //   577: aload 5
    //   579: invokevirtual 128	java/io/InputStream:close	()V
    //   582: aload_0
    //   583: monitorexit
    //   584: aconst_null
    //   585: astore 6
    //   587: goto -548 -> 39
    //   590: aload 28
    //   592: ldc 191
    //   594: invokevirtual 146	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   597: ifeq +38 -> 635
    //   600: aload 25
    //   602: aload 33
    //   604: invokevirtual 194	jp/co/asbit/pvstar/Video:setId	(Ljava/lang/String;)V
    //   607: goto -227 -> 380
    //   610: astore 15
    //   612: aload 4
    //   614: ifnull +8 -> 622
    //   617: aload 4
    //   619: invokevirtual 123	java/net/HttpURLConnection:disconnect	()V
    //   622: aload 5
    //   624: ifnull +8 -> 632
    //   627: aload 5
    //   629: invokevirtual 128	java/io/InputStream:close	()V
    //   632: aload 15
    //   634: athrow
    //   635: aload 28
    //   637: ldc 196
    //   639: invokevirtual 146	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   642: ifeq +13 -> 655
    //   645: aload 25
    //   647: aload 33
    //   649: invokevirtual 199	jp/co/asbit/pvstar/Video:setDescription	(Ljava/lang/String;)V
    //   652: goto -272 -> 380
    //   655: aload 28
    //   657: ldc 201
    //   659: invokevirtual 146	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   662: ifeq +13 -> 675
    //   665: aload 25
    //   667: aload 33
    //   669: invokevirtual 204	jp/co/asbit/pvstar/Video:setDuration	(Ljava/lang/String;)V
    //   672: goto -292 -> 380
    //   675: aload 28
    //   677: ldc 206
    //   679: invokevirtual 146	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   682: ifeq +13 -> 695
    //   685: aload 25
    //   687: aload 33
    //   689: invokevirtual 209	jp/co/asbit/pvstar/Video:setViewCount	(Ljava/lang/String;)V
    //   692: goto -312 -> 380
    //   695: aload 28
    //   697: ldc 211
    //   699: invokevirtual 146	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   702: ifeq -322 -> 380
    //   705: aload 25
    //   707: aload 33
    //   709: invokevirtual 214	jp/co/asbit/pvstar/Video:setThumbnailUrl	(Ljava/lang/String;)V
    //   712: goto -332 -> 380
    //   715: astore 17
    //   717: aload 17
    //   719: invokevirtual 138	java/lang/Exception:printStackTrace	()V
    //   722: aload 14
    //   724: astore 6
    //   726: iload 12
    //   728: istore 7
    //   730: goto -712 -> 18
    //   733: astore 20
    //   735: aload 20
    //   737: invokevirtual 138	java/lang/Exception:printStackTrace	()V
    //   740: goto -158 -> 582
    //   743: astore 16
    //   745: aload 16
    //   747: invokevirtual 138	java/lang/Exception:printStackTrace	()V
    //   750: goto -118 -> 632
    //   753: astore 35
    //   755: aload 35
    //   757: invokevirtual 138	java/lang/Exception:printStackTrace	()V
    //   760: goto -560 -> 200
    //   763: astore 9
    //   765: aload 6
    //   767: pop
    //   768: goto -496 -> 272
    //   771: astore 15
    //   773: aload 6
    //   775: pop
    //   776: goto -164 -> 612
    //   779: astore 18
    //   781: aload 6
    //   783: pop
    //   784: goto -227 -> 557
    //   787: astore 13
    //   789: aload 6
    //   791: astore 14
    //   793: goto -298 -> 495
    //   796: aload 14
    //   798: astore 6
    //   800: iload 12
    //   802: istore 7
    //   804: goto -786 -> 18
    //   807: iload 7
    //   809: pop
    //   810: goto -776 -> 34
    //   813: astore 9
    //   815: goto -543 -> 272
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	818	0	this	UpdateVideoResultTask
    //   0	818	1	paramVarArgs	String[]
    //   1	201	2	i	int
    //   3	367	3	str1	String
    //   7	611	4	localHttpURLConnection	java.net.HttpURLConnection
    //   10	618	5	localInputStream	java.io.InputStream
    //   13	786	6	localObject1	java.lang.Object
    //   16	792	7	j	int
    //   274	1	9	localObject2	java.lang.Object
    //   763	1	9	localObject3	java.lang.Object
    //   813	1	9	localObject4	java.lang.Object
    //   26	775	12	k	int
    //   493	3	13	localXmlPullParserException1	org.xmlpull.v1.XmlPullParserException
    //   787	1	13	localXmlPullParserException2	org.xmlpull.v1.XmlPullParserException
    //   54	743	14	localObject5	java.lang.Object
    //   610	23	15	localObject6	java.lang.Object
    //   771	1	15	localObject7	java.lang.Object
    //   743	3	16	localException1	java.lang.Exception
    //   715	3	17	localException2	java.lang.Exception
    //   555	3	18	localException3	java.lang.Exception
    //   779	1	18	localException4	java.lang.Exception
    //   733	3	20	localException5	java.lang.Exception
    //   45	431	22	localXmlPullParser	org.xmlpull.v1.XmlPullParser
    //   156	376	24	m	int
    //   159	547	25	localObject8	java.lang.Object
    //   168	3	26	n	int
    //   172	248	27	i1	int
    //   220	476	28	str2	String
    //   226	3	29	bool	boolean
    //   328	380	33	str3	String
    //   262	3	34	localException6	java.lang.Exception
    //   753	3	35	localException7	java.lang.Exception
    // Exception table:
    //   from	to	target	type
    //   238	253	262	java/lang/Exception
    //   56	170	493	org/xmlpull/v1/XmlPullParserException
    //   213	228	493	org/xmlpull/v1/XmlPullParserException
    //   283	490	493	org/xmlpull/v1/XmlPullParserException
    //   536	552	493	org/xmlpull/v1/XmlPullParserException
    //   590	607	493	org/xmlpull/v1/XmlPullParserException
    //   635	712	493	org/xmlpull/v1/XmlPullParserException
    //   56	170	555	java/lang/Exception
    //   213	228	555	java/lang/Exception
    //   283	490	555	java/lang/Exception
    //   536	552	555	java/lang/Exception
    //   590	607	555	java/lang/Exception
    //   635	712	555	java/lang/Exception
    //   56	170	610	finally
    //   213	228	610	finally
    //   283	490	610	finally
    //   495	500	610	finally
    //   536	552	610	finally
    //   557	562	610	finally
    //   590	607	610	finally
    //   635	712	610	finally
    //   505	520	715	java/lang/Exception
    //   567	582	733	java/lang/Exception
    //   617	632	743	java/lang/Exception
    //   185	200	753	java/lang/Exception
    //   34	36	763	finally
    //   42	56	771	finally
    //   42	56	779	java/lang/Exception
    //   42	56	787	org/xmlpull/v1/XmlPullParserException
    //   185	200	813	finally
    //   238	253	813	finally
    //   253	274	813	finally
    //   505	520	813	finally
    //   567	582	813	finally
    //   582	584	813	finally
    //   617	632	813	finally
    //   632	635	813	finally
    //   717	760	813	finally
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.UpdateVideoResultTask
 * JD-Core Version:    0.7.0.1
 */