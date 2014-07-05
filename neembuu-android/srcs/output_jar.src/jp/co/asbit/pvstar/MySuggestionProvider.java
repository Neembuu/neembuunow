package jp.co.asbit.pvstar;

import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public class MySuggestionProvider
  extends SearchRecentSuggestionsProvider
{
  private static final String API = "http://suggestqueries.google.com/complete/search?output=toolbar&hl=%s&qu=%s&ds=yt";
  public static final String AUTHORITY = "jp.co.asbit.pvstar.MySuggestionProvider";
  private static final String[] FROM;
  public static final int MODE = 1;
  private static final String TAG = null;
  
  static
  {
    String[] arrayOfString = new String[4];
    arrayOfString[0] = "_id";
    arrayOfString[1] = "suggest_intent_query";
    arrayOfString[2] = "suggest_text_1";
    arrayOfString[3] = "suggest_icon_1";
    FROM = arrayOfString;
  }
  
  public MySuggestionProvider()
  {
    setupSuggestions("jp.co.asbit.pvstar.MySuggestionProvider", 1);
  }
  
  /* Error */
  private Cursor getSuggestion(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: new 46	android/database/MatrixCursor
    //   7: dup
    //   8: getstatic 31	jp/co/asbit/pvstar/MySuggestionProvider:FROM	[Ljava/lang/String;
    //   11: invokespecial 49	android/database/MatrixCursor:<init>	([Ljava/lang/String;)V
    //   14: astore 4
    //   16: invokestatic 55	org/xmlpull/v1/XmlPullParserFactory:newInstance	()Lorg/xmlpull/v1/XmlPullParserFactory;
    //   19: invokevirtual 59	org/xmlpull/v1/XmlPullParserFactory:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   22: astore 9
    //   24: iconst_2
    //   25: anewarray 61	java/lang/Object
    //   28: astore 10
    //   30: aload 10
    //   32: iconst_0
    //   33: invokestatic 67	java/util/Locale:getDefault	()Ljava/util/Locale;
    //   36: invokevirtual 71	java/util/Locale:getLanguage	()Ljava/lang/String;
    //   39: invokevirtual 74	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   42: aastore
    //   43: aload 10
    //   45: iconst_1
    //   46: aload_1
    //   47: ldc 76
    //   49: invokestatic 82	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   52: aastore
    //   53: new 84	java/net/URL
    //   56: dup
    //   57: ldc 8
    //   59: aload 10
    //   61: invokestatic 88	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   64: invokespecial 91	java/net/URL:<init>	(Ljava/lang/String;)V
    //   67: invokevirtual 95	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   70: checkcast 97	java/net/HttpURLConnection
    //   73: astore_2
    //   74: aload_2
    //   75: ldc 99
    //   77: ldc 101
    //   79: invokevirtual 105	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   82: aload_2
    //   83: ldc 107
    //   85: invokestatic 67	java/util/Locale:getDefault	()Ljava/util/Locale;
    //   88: invokevirtual 71	java/util/Locale:getLanguage	()Ljava/lang/String;
    //   91: invokevirtual 105	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   94: aload_2
    //   95: sipush 5000
    //   98: invokevirtual 111	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   101: aload_2
    //   102: sipush 5000
    //   105: invokevirtual 114	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   108: aload_2
    //   109: iconst_0
    //   110: invokevirtual 118	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   113: aload_2
    //   114: invokevirtual 122	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   117: astore_3
    //   118: aload 9
    //   120: aload_3
    //   121: ldc 76
    //   123: invokeinterface 128 3 0
    //   128: aload 9
    //   130: invokeinterface 132 1 0
    //   135: istore 11
    //   137: iload 11
    //   139: istore 12
    //   141: ldc2_w 133
    //   144: lstore 13
    //   146: iload 12
    //   148: iconst_1
    //   149: if_icmpne +25 -> 174
    //   152: aload_2
    //   153: ifnull +7 -> 160
    //   156: aload_2
    //   157: invokevirtual 137	java/net/HttpURLConnection:disconnect	()V
    //   160: aload_3
    //   161: ifnull +191 -> 352
    //   164: aload_3
    //   165: invokevirtual 142	java/io/InputStream:close	()V
    //   168: lload 13
    //   170: pop2
    //   171: aload 4
    //   173: areturn
    //   174: aload 9
    //   176: invokeinterface 145 1 0
    //   181: astore 19
    //   183: iload 12
    //   185: iconst_2
    //   186: if_icmpne +188 -> 374
    //   189: aload 19
    //   191: ldc 147
    //   193: invokevirtual 151	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   196: ifeq +178 -> 374
    //   199: aload 9
    //   201: iconst_0
    //   202: invokeinterface 155 2 0
    //   207: astore 23
    //   209: iconst_4
    //   210: anewarray 61	java/lang/Object
    //   213: astore 24
    //   215: lload 13
    //   217: lconst_1
    //   218: ladd
    //   219: lstore 20
    //   221: aload 24
    //   223: iconst_0
    //   224: lload 13
    //   226: invokestatic 161	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   229: aastore
    //   230: aload 24
    //   232: iconst_1
    //   233: aload 23
    //   235: aastore
    //   236: aload 24
    //   238: iconst_2
    //   239: aload 23
    //   241: aastore
    //   242: aload 24
    //   244: iconst_3
    //   245: ldc 162
    //   247: invokestatic 167	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   250: aastore
    //   251: aload 4
    //   253: aload 24
    //   255: invokevirtual 171	android/database/MatrixCursor:addRow	([Ljava/lang/Object;)V
    //   258: aload 9
    //   260: invokeinterface 174 1 0
    //   265: istore 22
    //   267: iload 22
    //   269: istore 12
    //   271: lload 20
    //   273: lstore 13
    //   275: goto -129 -> 146
    //   278: astore 7
    //   280: aload 7
    //   282: invokevirtual 177	java/lang/Exception:printStackTrace	()V
    //   285: aload_2
    //   286: ifnull +7 -> 293
    //   289: aload_2
    //   290: invokevirtual 137	java/net/HttpURLConnection:disconnect	()V
    //   293: aload_3
    //   294: ifnull -123 -> 171
    //   297: aload_3
    //   298: invokevirtual 142	java/io/InputStream:close	()V
    //   301: goto -130 -> 171
    //   304: astore 8
    //   306: aload 8
    //   308: invokevirtual 177	java/lang/Exception:printStackTrace	()V
    //   311: goto -140 -> 171
    //   314: astore 5
    //   316: aload_2
    //   317: ifnull +7 -> 324
    //   320: aload_2
    //   321: invokevirtual 137	java/net/HttpURLConnection:disconnect	()V
    //   324: aload_3
    //   325: ifnull +7 -> 332
    //   328: aload_3
    //   329: invokevirtual 142	java/io/InputStream:close	()V
    //   332: aload 5
    //   334: athrow
    //   335: astore 6
    //   337: aload 6
    //   339: invokevirtual 177	java/lang/Exception:printStackTrace	()V
    //   342: goto -10 -> 332
    //   345: astore 27
    //   347: aload 27
    //   349: invokevirtual 177	java/lang/Exception:printStackTrace	()V
    //   352: lload 13
    //   354: pop2
    //   355: goto -184 -> 171
    //   358: astore 5
    //   360: lload 13
    //   362: pop2
    //   363: goto -47 -> 316
    //   366: astore 7
    //   368: lload 13
    //   370: pop2
    //   371: goto -91 -> 280
    //   374: lload 13
    //   376: lstore 20
    //   378: goto -120 -> 258
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	381	0	this	MySuggestionProvider
    //   0	381	1	paramString	String
    //   1	320	2	localHttpURLConnection	java.net.HttpURLConnection
    //   3	326	3	localInputStream	java.io.InputStream
    //   14	238	4	localMatrixCursor	MatrixCursor
    //   314	19	5	localObject1	Object
    //   358	1	5	localObject2	Object
    //   335	3	6	localException1	java.lang.Exception
    //   278	3	7	localException2	java.lang.Exception
    //   366	1	7	localException3	java.lang.Exception
    //   304	3	8	localException4	java.lang.Exception
    //   22	237	9	localXmlPullParser	org.xmlpull.v1.XmlPullParser
    //   28	32	10	arrayOfObject1	Object[]
    //   135	3	11	i	int
    //   139	131	12	j	int
    //   144	231	13	l1	long
    //   181	9	19	str1	String
    //   219	158	20	l2	long
    //   265	3	22	k	int
    //   207	33	23	str2	String
    //   213	41	24	arrayOfObject2	Object[]
    //   345	3	27	localException5	java.lang.Exception
    // Exception table:
    //   from	to	target	type
    //   16	137	278	java/lang/Exception
    //   221	267	278	java/lang/Exception
    //   289	301	304	java/lang/Exception
    //   16	137	314	finally
    //   221	267	314	finally
    //   280	285	314	finally
    //   320	332	335	java/lang/Exception
    //   156	168	345	java/lang/Exception
    //   174	215	358	finally
    //   174	215	366	java/lang/Exception
  }
  
  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    Cursor localCursor1 = super.query(paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2);
    Object localObject;
    long l1;
    Cursor localCursor2;
    if (!paramArrayOfString2[0].equals(""))
    {
      localObject = new MatrixCursor(FROM);
      l1 = 0L;
      if (!localCursor1.moveToNext())
      {
        localCursor2 = getSuggestion(paramArrayOfString2[0]);
        label60:
        if (localCursor2.moveToNext()) {
          break label147;
        }
      }
    }
    for (;;)
    {
      return localObject;
      String str1 = localCursor1.getString(localCursor1.getColumnIndex("suggest_text_1"));
      Object[] arrayOfObject1 = new Object[4];
      long l2 = l1 + 1L;
      arrayOfObject1[0] = Long.valueOf(l1);
      arrayOfObject1[1] = str1;
      arrayOfObject1[2] = str1;
      arrayOfObject1[3] = Integer.valueOf(17301578);
      ((MatrixCursor)localObject).addRow(arrayOfObject1);
      l1 = l2;
      break;
      label147:
      String str2 = localCursor2.getString(localCursor2.getColumnIndex("suggest_text_1"));
      Object[] arrayOfObject2 = new Object[4];
      long l3 = l1 + 1L;
      arrayOfObject2[0] = Long.valueOf(l1);
      arrayOfObject2[1] = str2;
      arrayOfObject2[2] = str2;
      arrayOfObject2[3] = Integer.valueOf(17301583);
      ((MatrixCursor)localObject).addRow(arrayOfObject2);
      l1 = l3;
      break label60;
      localObject = localCursor1;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.MySuggestionProvider
 * JD-Core Version:    0.7.0.1
 */