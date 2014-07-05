package jp.co.asbit.pvstar.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.Category;

public class UpdateCategoryDetailTask
  extends AsyncTask<String, Integer, ArrayList<Category>>
{
  private static final String CATEGORY_DETAIL_API = "http://pvstar.dooga.org/api2/categories/categories_%s/detail/%s/%s/";
  protected int totalResults;
  
  protected ArrayList<Category> doInBackground(String... paramVarArgs)
  {
    Object localObject1 = "";
    try
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = paramVarArgs[0];
      arrayOfObject[1] = URLEncoder.encode(paramVarArgs[1], "UTF-8");
      arrayOfObject[2] = paramVarArgs[2];
      String str = String.format("http://pvstar.dooga.org/api2/categories/categories_%s/detail/%s/%s/", arrayOfObject);
      localObject1 = str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;)
      {
        try
        {
          ArrayList localArrayList = getCategories((String)localObject1);
          return localArrayList;
        }
        finally {}
        localUnsupportedEncodingException = localUnsupportedEncodingException;
        localUnsupportedEncodingException.printStackTrace();
      }
    }
  }
  
  /* Error */
  protected ArrayList<Category> getCategories(String paramString)
  {
    // Byte code:
    //   0: new 55	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 56	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: aconst_null
    //   9: astore_3
    //   10: aconst_null
    //   11: astore 4
    //   13: aconst_null
    //   14: astore 5
    //   16: iconst_0
    //   17: istore 6
    //   19: iload 6
    //   21: iconst_1
    //   22: iadd
    //   23: istore 7
    //   25: iload 6
    //   27: iconst_3
    //   28: if_icmplt +8 -> 36
    //   31: aload 5
    //   33: pop
    //   34: aload_2
    //   35: areturn
    //   36: invokestatic 62	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   39: astore 17
    //   41: new 64	java/net/URL
    //   44: dup
    //   45: aload_1
    //   46: invokespecial 67	java/net/URL:<init>	(Ljava/lang/String;)V
    //   49: astore 15
    //   51: aload 15
    //   53: invokevirtual 71	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   56: checkcast 73	java/net/HttpURLConnection
    //   59: astore_3
    //   60: aload_3
    //   61: ldc 75
    //   63: invokestatic 81	java/util/Locale:getDefault	()Ljava/util/Locale;
    //   66: invokevirtual 85	java/util/Locale:getLanguage	()Ljava/lang/String;
    //   69: invokevirtual 89	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   72: aload_3
    //   73: sipush 5000
    //   76: invokevirtual 93	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   79: aload_3
    //   80: sipush 5000
    //   83: invokevirtual 96	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   86: aload_3
    //   87: iconst_1
    //   88: invokevirtual 100	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   91: aload_3
    //   92: invokevirtual 104	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   95: astore 4
    //   97: aload 17
    //   99: aload 4
    //   101: ldc 30
    //   103: invokeinterface 110 3 0
    //   108: iconst_0
    //   109: istore 18
    //   111: aconst_null
    //   112: astore 19
    //   114: aload 17
    //   116: invokeinterface 114 1 0
    //   121: istore 20
    //   123: iload 20
    //   125: istore 21
    //   127: iload 21
    //   129: iconst_1
    //   130: if_icmpne +34 -> 164
    //   133: aload_3
    //   134: ifnull +7 -> 141
    //   137: aload_3
    //   138: invokevirtual 117	java/net/HttpURLConnection:disconnect	()V
    //   141: aload 4
    //   143: ifnull -109 -> 34
    //   146: aload 4
    //   148: invokevirtual 122	java/io/InputStream:close	()V
    //   151: goto -117 -> 34
    //   154: astore 29
    //   156: aload 29
    //   158: invokevirtual 123	java/lang/Exception:printStackTrace	()V
    //   161: goto -127 -> 34
    //   164: aload 17
    //   166: invokeinterface 126 1 0
    //   171: astore 22
    //   173: aload_0
    //   174: invokevirtual 130	jp/co/asbit/pvstar/api/UpdateCategoryDetailTask:isCancelled	()Z
    //   177: istore 23
    //   179: iload 23
    //   181: ifeq +34 -> 215
    //   184: aload_3
    //   185: ifnull +7 -> 192
    //   188: aload_3
    //   189: invokevirtual 117	java/net/HttpURLConnection:disconnect	()V
    //   192: aload 4
    //   194: ifnull -160 -> 34
    //   197: aload 4
    //   199: invokevirtual 122	java/io/InputStream:close	()V
    //   202: goto -168 -> 34
    //   205: astore 28
    //   207: aload 28
    //   209: invokevirtual 123	java/lang/Exception:printStackTrace	()V
    //   212: goto -178 -> 34
    //   215: iload 21
    //   217: iconst_2
    //   218: if_icmpne +163 -> 381
    //   221: aload 22
    //   223: ldc 132
    //   225: invokevirtual 136	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   228: ifeq +15 -> 243
    //   231: iconst_1
    //   232: istore 18
    //   234: new 138	jp/co/asbit/pvstar/Category
    //   237: dup
    //   238: invokespecial 139	jp/co/asbit/pvstar/Category:<init>	()V
    //   241: astore 19
    //   243: aload 17
    //   245: invokeinterface 142 1 0
    //   250: pop
    //   251: aload 17
    //   253: invokeinterface 114 1 0
    //   258: iconst_4
    //   259: if_icmpne +122 -> 381
    //   262: aload 17
    //   264: invokeinterface 145 1 0
    //   269: astore 27
    //   271: aload 22
    //   273: ldc 146
    //   275: invokevirtual 136	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   278: ifeq +15 -> 293
    //   281: aload_0
    //   282: aload 27
    //   284: invokestatic 152	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   287: invokevirtual 155	java/lang/Integer:intValue	()I
    //   290: putfield 157	jp/co/asbit/pvstar/api/UpdateCategoryDetailTask:totalResults	I
    //   293: iload 18
    //   295: ifeq +86 -> 381
    //   298: aload 22
    //   300: ldc 159
    //   302: invokevirtual 136	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   305: ifeq +13 -> 318
    //   308: aload 19
    //   310: aload 27
    //   312: invokestatic 163	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   315: invokevirtual 166	jp/co/asbit/pvstar/Category:setRank	(I)V
    //   318: aload 22
    //   320: ldc 168
    //   322: invokevirtual 136	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   325: ifeq +16 -> 341
    //   328: aload 19
    //   330: aload 27
    //   332: invokestatic 152	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   335: invokevirtual 155	java/lang/Integer:intValue	()I
    //   338: invokevirtual 171	jp/co/asbit/pvstar/Category:setChildrenCount	(I)V
    //   341: aload 22
    //   343: ldc 173
    //   345: invokevirtual 136	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   348: ifeq +10 -> 358
    //   351: aload 19
    //   353: aload 27
    //   355: invokevirtual 176	jp/co/asbit/pvstar/Category:setName	(Ljava/lang/String;)V
    //   358: aload 22
    //   360: ldc 178
    //   362: invokevirtual 136	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   365: ifeq +16 -> 381
    //   368: aload 19
    //   370: aload 27
    //   372: invokestatic 152	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   375: invokevirtual 155	java/lang/Integer:intValue	()I
    //   378: invokevirtual 181	jp/co/asbit/pvstar/Category:setId	(I)V
    //   381: aload 17
    //   383: invokeinterface 114 1 0
    //   388: iconst_3
    //   389: if_icmpne +23 -> 412
    //   392: aload 22
    //   394: ldc 132
    //   396: invokevirtual 136	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   399: ifeq +13 -> 412
    //   402: iconst_0
    //   403: istore 18
    //   405: aload_2
    //   406: aload 19
    //   408: invokevirtual 184	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   411: pop
    //   412: aload 17
    //   414: invokeinterface 142 1 0
    //   419: istore 24
    //   421: iload 24
    //   423: istore 21
    //   425: goto -298 -> 127
    //   428: astore 14
    //   430: aload 5
    //   432: astore 15
    //   434: aload 14
    //   436: invokevirtual 185	org/xmlpull/v1/XmlPullParserException:printStackTrace	()V
    //   439: aload_3
    //   440: ifnull +7 -> 447
    //   443: aload_3
    //   444: invokevirtual 117	java/net/HttpURLConnection:disconnect	()V
    //   447: aload 4
    //   449: ifnull +131 -> 580
    //   452: aload 4
    //   454: invokevirtual 122	java/io/InputStream:close	()V
    //   457: aload 15
    //   459: astore 5
    //   461: iload 7
    //   463: istore 6
    //   465: goto -446 -> 19
    //   468: astore 16
    //   470: aload 16
    //   472: invokevirtual 123	java/lang/Exception:printStackTrace	()V
    //   475: aload 15
    //   477: astore 5
    //   479: iload 7
    //   481: istore 6
    //   483: goto -464 -> 19
    //   486: astore 11
    //   488: aload 5
    //   490: pop
    //   491: aload 11
    //   493: invokevirtual 123	java/lang/Exception:printStackTrace	()V
    //   496: aload_3
    //   497: ifnull +7 -> 504
    //   500: aload_3
    //   501: invokevirtual 117	java/net/HttpURLConnection:disconnect	()V
    //   504: aload 4
    //   506: ifnull +8 -> 514
    //   509: aload 4
    //   511: invokevirtual 122	java/io/InputStream:close	()V
    //   514: aconst_null
    //   515: astore_2
    //   516: goto -482 -> 34
    //   519: astore 13
    //   521: aload 13
    //   523: invokevirtual 123	java/lang/Exception:printStackTrace	()V
    //   526: goto -12 -> 514
    //   529: astore 8
    //   531: aload 5
    //   533: pop
    //   534: aload_3
    //   535: ifnull +7 -> 542
    //   538: aload_3
    //   539: invokevirtual 117	java/net/HttpURLConnection:disconnect	()V
    //   542: aload 4
    //   544: ifnull +8 -> 552
    //   547: aload 4
    //   549: invokevirtual 122	java/io/InputStream:close	()V
    //   552: aload 8
    //   554: athrow
    //   555: astore 10
    //   557: aload 10
    //   559: invokevirtual 123	java/lang/Exception:printStackTrace	()V
    //   562: goto -10 -> 552
    //   565: astore 8
    //   567: goto -33 -> 534
    //   570: astore 11
    //   572: goto -81 -> 491
    //   575: astore 14
    //   577: goto -143 -> 434
    //   580: aload 15
    //   582: astore 5
    //   584: iload 7
    //   586: istore 6
    //   588: goto -569 -> 19
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	591	0	this	UpdateCategoryDetailTask
    //   0	591	1	paramString	String
    //   7	509	2	localArrayList	ArrayList
    //   9	530	3	localHttpURLConnection	java.net.HttpURLConnection
    //   11	537	4	localInputStream	java.io.InputStream
    //   14	569	5	localObject1	Object
    //   17	570	6	i	int
    //   23	562	7	j	int
    //   529	24	8	localObject2	Object
    //   565	1	8	localObject3	Object
    //   555	3	10	localException1	java.lang.Exception
    //   486	6	11	localException2	java.lang.Exception
    //   570	1	11	localException3	java.lang.Exception
    //   519	3	13	localException4	java.lang.Exception
    //   428	7	14	localXmlPullParserException1	org.xmlpull.v1.XmlPullParserException
    //   575	1	14	localXmlPullParserException2	org.xmlpull.v1.XmlPullParserException
    //   49	532	15	localObject4	Object
    //   468	3	16	localException5	java.lang.Exception
    //   39	374	17	localXmlPullParser	org.xmlpull.v1.XmlPullParser
    //   109	295	18	k	int
    //   112	295	19	localCategory	Category
    //   121	3	20	m	int
    //   125	299	21	n	int
    //   171	222	22	str1	String
    //   177	3	23	bool	boolean
    //   419	3	24	i1	int
    //   269	102	27	str2	String
    //   205	3	28	localException6	java.lang.Exception
    //   154	3	29	localException7	java.lang.Exception
    // Exception table:
    //   from	to	target	type
    //   137	151	154	java/lang/Exception
    //   188	202	205	java/lang/Exception
    //   36	51	428	org/xmlpull/v1/XmlPullParserException
    //   443	457	468	java/lang/Exception
    //   36	51	486	java/lang/Exception
    //   500	514	519	java/lang/Exception
    //   36	51	529	finally
    //   538	552	555	java/lang/Exception
    //   51	123	565	finally
    //   164	179	565	finally
    //   221	421	565	finally
    //   434	439	565	finally
    //   491	496	565	finally
    //   51	123	570	java/lang/Exception
    //   164	179	570	java/lang/Exception
    //   221	421	570	java/lang/Exception
    //   51	123	575	org/xmlpull/v1/XmlPullParserException
    //   164	179	575	org/xmlpull/v1/XmlPullParserException
    //   221	421	575	org/xmlpull/v1/XmlPullParserException
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.UpdateCategoryDetailTask
 * JD-Core Version:    0.7.0.1
 */