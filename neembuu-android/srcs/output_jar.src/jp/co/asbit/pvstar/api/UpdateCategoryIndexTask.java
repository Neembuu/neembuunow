package jp.co.asbit.pvstar.api;

import java.util.ArrayList;
import jp.co.asbit.pvstar.AsyncTask;

public class UpdateCategoryIndexTask
  extends AsyncTask<String, Integer, ArrayList<String>>
{
  static final String CATEGORY_INDEX_API = "http://pvstar.dooga.org/api2/categories/categories_%s/";
  
  /* Error */
  protected ArrayList<String> doInBackground(String... paramVarArgs)
  {
    // Byte code:
    //   0: iconst_1
    //   1: anewarray 26	java/lang/Object
    //   4: astore_2
    //   5: aload_2
    //   6: iconst_0
    //   7: aload_1
    //   8: iconst_0
    //   9: aaload
    //   10: aastore
    //   11: ldc 9
    //   13: aload_2
    //   14: invokestatic 32	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   17: astore_3
    //   18: aload_0
    //   19: monitorenter
    //   20: new 34	java/util/ArrayList
    //   23: dup
    //   24: invokespecial 35	java/util/ArrayList:<init>	()V
    //   27: astore 4
    //   29: aconst_null
    //   30: astore 5
    //   32: aconst_null
    //   33: astore 6
    //   35: aconst_null
    //   36: astore 7
    //   38: iconst_0
    //   39: istore 8
    //   41: goto +482 -> 523
    //   44: aload_0
    //   45: monitorexit
    //   46: aload 4
    //   48: areturn
    //   49: invokestatic 41	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   52: astore 20
    //   54: new 43	java/net/URL
    //   57: dup
    //   58: aload_3
    //   59: invokespecial 46	java/net/URL:<init>	(Ljava/lang/String;)V
    //   62: astore 18
    //   64: aload 18
    //   66: invokevirtual 50	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   69: checkcast 52	java/net/HttpURLConnection
    //   72: astore 5
    //   74: aload 5
    //   76: ldc 54
    //   78: invokestatic 60	java/util/Locale:getDefault	()Ljava/util/Locale;
    //   81: invokevirtual 64	java/util/Locale:getLanguage	()Ljava/lang/String;
    //   84: invokevirtual 68	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   87: aload 5
    //   89: sipush 5000
    //   92: invokevirtual 72	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   95: aload 5
    //   97: sipush 5000
    //   100: invokevirtual 75	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   103: aload 5
    //   105: iconst_1
    //   106: invokevirtual 79	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   109: aload 5
    //   111: invokevirtual 83	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   114: astore 6
    //   116: aload 20
    //   118: aload 6
    //   120: ldc 85
    //   122: invokeinterface 91 3 0
    //   127: iconst_0
    //   128: istore 21
    //   130: aload 20
    //   132: invokeinterface 95 1 0
    //   137: istore 22
    //   139: iload 22
    //   141: istore 23
    //   143: iload 23
    //   145: iconst_1
    //   146: if_icmpne +43 -> 189
    //   149: aload 5
    //   151: ifnull +8 -> 159
    //   154: aload 5
    //   156: invokevirtual 98	java/net/HttpURLConnection:disconnect	()V
    //   159: aload 6
    //   161: ifnull -117 -> 44
    //   164: aload 6
    //   166: invokevirtual 103	java/io/InputStream:close	()V
    //   169: goto -125 -> 44
    //   172: astore 31
    //   174: aload 31
    //   176: invokevirtual 106	java/lang/Exception:printStackTrace	()V
    //   179: goto -135 -> 44
    //   182: astore 13
    //   184: aload_0
    //   185: monitorexit
    //   186: aload 13
    //   188: athrow
    //   189: aload 20
    //   191: invokeinterface 109 1 0
    //   196: astore 24
    //   198: aload_0
    //   199: invokevirtual 113	jp/co/asbit/pvstar/api/UpdateCategoryIndexTask:isCancelled	()Z
    //   202: istore 25
    //   204: iload 25
    //   206: ifeq +38 -> 244
    //   209: aload 5
    //   211: ifnull +8 -> 219
    //   214: aload 5
    //   216: invokevirtual 98	java/net/HttpURLConnection:disconnect	()V
    //   219: aload 6
    //   221: ifnull +8 -> 229
    //   224: aload 6
    //   226: invokevirtual 103	java/io/InputStream:close	()V
    //   229: aload_0
    //   230: monitorexit
    //   231: goto -185 -> 46
    //   234: astore 30
    //   236: aload 30
    //   238: invokevirtual 106	java/lang/Exception:printStackTrace	()V
    //   241: goto -12 -> 229
    //   244: iload 23
    //   246: iconst_2
    //   247: if_icmpne +67 -> 314
    //   250: aload 24
    //   252: ldc 115
    //   254: invokevirtual 119	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   257: ifeq +6 -> 263
    //   260: iconst_1
    //   261: istore 21
    //   263: aload 20
    //   265: invokeinterface 122 1 0
    //   270: pop
    //   271: aload 20
    //   273: invokeinterface 95 1 0
    //   278: iconst_4
    //   279: if_icmpne +35 -> 314
    //   282: aload 20
    //   284: invokeinterface 125 1 0
    //   289: astore 28
    //   291: iload 21
    //   293: ifeq +21 -> 314
    //   296: aload 24
    //   298: ldc 127
    //   300: invokevirtual 119	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   303: ifeq +11 -> 314
    //   306: aload 4
    //   308: aload 28
    //   310: invokevirtual 130	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   313: pop
    //   314: aload 20
    //   316: invokeinterface 95 1 0
    //   321: iconst_3
    //   322: if_icmpne +16 -> 338
    //   325: aload 24
    //   327: ldc 115
    //   329: invokevirtual 119	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   332: ifeq +6 -> 338
    //   335: iconst_0
    //   336: istore 21
    //   338: aload 20
    //   340: invokeinterface 122 1 0
    //   345: istore 26
    //   347: iload 26
    //   349: istore 23
    //   351: goto -208 -> 143
    //   354: astore 17
    //   356: aload 7
    //   358: astore 18
    //   360: aload 17
    //   362: invokevirtual 131	org/xmlpull/v1/XmlPullParserException:printStackTrace	()V
    //   365: aload 5
    //   367: ifnull +8 -> 375
    //   370: aload 5
    //   372: invokevirtual 98	java/net/HttpURLConnection:disconnect	()V
    //   375: aload 6
    //   377: ifnull +138 -> 515
    //   380: aload 6
    //   382: invokevirtual 103	java/io/InputStream:close	()V
    //   385: aload 18
    //   387: astore 7
    //   389: iload 9
    //   391: istore 8
    //   393: goto +130 -> 523
    //   396: astore 19
    //   398: aload 19
    //   400: invokevirtual 106	java/lang/Exception:printStackTrace	()V
    //   403: aload 18
    //   405: astore 7
    //   407: iload 9
    //   409: istore 8
    //   411: goto +112 -> 523
    //   414: astore 14
    //   416: aload 7
    //   418: pop
    //   419: aload 14
    //   421: invokevirtual 106	java/lang/Exception:printStackTrace	()V
    //   424: aload 5
    //   426: ifnull +8 -> 434
    //   429: aload 5
    //   431: invokevirtual 98	java/net/HttpURLConnection:disconnect	()V
    //   434: aload 6
    //   436: ifnull +8 -> 444
    //   439: aload 6
    //   441: invokevirtual 103	java/io/InputStream:close	()V
    //   444: aload_0
    //   445: monitorexit
    //   446: aconst_null
    //   447: astore 4
    //   449: goto -403 -> 46
    //   452: astore 16
    //   454: aload 16
    //   456: invokevirtual 106	java/lang/Exception:printStackTrace	()V
    //   459: goto -15 -> 444
    //   462: astore 10
    //   464: aload 7
    //   466: pop
    //   467: aload 5
    //   469: ifnull +8 -> 477
    //   472: aload 5
    //   474: invokevirtual 98	java/net/HttpURLConnection:disconnect	()V
    //   477: aload 6
    //   479: ifnull +8 -> 487
    //   482: aload 6
    //   484: invokevirtual 103	java/io/InputStream:close	()V
    //   487: aload 10
    //   489: athrow
    //   490: astore 12
    //   492: aload 12
    //   494: invokevirtual 106	java/lang/Exception:printStackTrace	()V
    //   497: goto -10 -> 487
    //   500: astore 10
    //   502: goto -35 -> 467
    //   505: astore 14
    //   507: goto -88 -> 419
    //   510: astore 17
    //   512: goto -152 -> 360
    //   515: aload 18
    //   517: astore 7
    //   519: iload 9
    //   521: istore 8
    //   523: iload 8
    //   525: iconst_1
    //   526: iadd
    //   527: istore 9
    //   529: iload 8
    //   531: iconst_3
    //   532: if_icmplt -483 -> 49
    //   535: aload 7
    //   537: pop
    //   538: goto -494 -> 44
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	541	0	this	UpdateCategoryIndexTask
    //   0	541	1	paramVarArgs	String[]
    //   4	10	2	arrayOfObject	java.lang.Object[]
    //   17	42	3	str1	String
    //   27	421	4	localArrayList	ArrayList
    //   30	443	5	localHttpURLConnection	java.net.HttpURLConnection
    //   33	450	6	localInputStream	java.io.InputStream
    //   36	500	7	localObject1	java.lang.Object
    //   39	494	8	i	int
    //   389	139	9	j	int
    //   462	26	10	localObject2	java.lang.Object
    //   500	1	10	localObject3	java.lang.Object
    //   490	3	12	localException1	java.lang.Exception
    //   182	5	13	localObject4	java.lang.Object
    //   414	6	14	localException2	java.lang.Exception
    //   505	1	14	localException3	java.lang.Exception
    //   452	3	16	localException4	java.lang.Exception
    //   354	7	17	localXmlPullParserException1	org.xmlpull.v1.XmlPullParserException
    //   510	1	17	localXmlPullParserException2	org.xmlpull.v1.XmlPullParserException
    //   62	454	18	localObject5	java.lang.Object
    //   396	3	19	localException5	java.lang.Exception
    //   52	287	20	localXmlPullParser	org.xmlpull.v1.XmlPullParser
    //   128	209	21	k	int
    //   137	3	22	m	int
    //   141	209	23	n	int
    //   196	130	24	str2	String
    //   202	3	25	bool	boolean
    //   345	3	26	i1	int
    //   289	20	28	str3	String
    //   234	3	30	localException6	java.lang.Exception
    //   172	3	31	localException7	java.lang.Exception
    // Exception table:
    //   from	to	target	type
    //   154	169	172	java/lang/Exception
    //   20	46	182	finally
    //   154	169	182	finally
    //   174	186	182	finally
    //   214	229	182	finally
    //   229	241	182	finally
    //   370	385	182	finally
    //   398	403	182	finally
    //   429	444	182	finally
    //   444	459	182	finally
    //   472	487	182	finally
    //   487	497	182	finally
    //   214	229	234	java/lang/Exception
    //   49	64	354	org/xmlpull/v1/XmlPullParserException
    //   370	385	396	java/lang/Exception
    //   49	64	414	java/lang/Exception
    //   429	444	452	java/lang/Exception
    //   49	64	462	finally
    //   472	487	490	java/lang/Exception
    //   64	139	500	finally
    //   189	204	500	finally
    //   250	347	500	finally
    //   360	365	500	finally
    //   419	424	500	finally
    //   64	139	505	java/lang/Exception
    //   189	204	505	java/lang/Exception
    //   250	347	505	java/lang/Exception
    //   64	139	510	org/xmlpull/v1/XmlPullParserException
    //   189	204	510	org/xmlpull/v1/XmlPullParserException
    //   250	347	510	org/xmlpull/v1/XmlPullParserException
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.UpdateCategoryIndexTask
 * JD-Core Version:    0.7.0.1
 */