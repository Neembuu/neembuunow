package jp.co.imobile.android;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.Uri.Builder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

final class n
  implements bp
{
  private final int a;
  private final int b;
  private final int c;
  private final ao d;
  private Boolean e;
  
  n(int paramInt1, int paramInt2, int paramInt3, ao paramao)
  {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
    this.d = paramao;
  }
  
  /* Error */
  private Drawable a(int paramInt, bo parambo, boolean paramBoolean)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: ldc 34
    //   5: astore 5
    //   7: invokestatic 37	jp/co/imobile/android/n:e	()Lorg/apache/http/impl/client/DefaultHttpClient;
    //   10: astore 6
    //   12: iload_3
    //   13: ifeq +275 -> 288
    //   16: aload_0
    //   17: ldc 39
    //   19: ldc 41
    //   21: invokespecial 44	jp/co/imobile/android/n:a	(Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   24: astore 15
    //   26: aload 15
    //   28: ldc 46
    //   30: iload_1
    //   31: invokestatic 52	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   34: invokevirtual 57	android/net/Uri$Builder:appendQueryParameter	(Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   37: pop
    //   38: aload 15
    //   40: ldc 59
    //   42: aload_2
    //   43: invokevirtual 64	jp/co/imobile/android/bo:a	()Ljava/lang/Integer;
    //   46: invokestatic 67	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   49: invokevirtual 57	android/net/Uri$Builder:appendQueryParameter	(Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   52: pop
    //   53: aload 15
    //   55: invokevirtual 71	android/net/Uri$Builder:toString	()Ljava/lang/String;
    //   58: astore 18
    //   60: aload 18
    //   62: astore 8
    //   64: iconst_2
    //   65: anewarray 48	java/lang/String
    //   68: astore 19
    //   70: aload 19
    //   72: iconst_0
    //   73: ldc 73
    //   75: aastore
    //   76: aload 19
    //   78: iconst_1
    //   79: aload 8
    //   81: aastore
    //   82: ldc 75
    //   84: aload_0
    //   85: aload 19
    //   87: invokestatic 80	jp/co/imobile/android/cj:b	(Ljava/lang/String;Ljp/co/imobile/android/bp;[Ljava/lang/String;)V
    //   90: aload 6
    //   92: new 82	org/apache/http/client/methods/HttpGet
    //   95: dup
    //   96: aload 8
    //   98: invokespecial 85	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
    //   101: invokevirtual 91	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   104: astore 20
    //   106: aload 20
    //   108: invokeinterface 97 1 0
    //   113: invokeinterface 103 1 0
    //   118: istore 21
    //   120: iload 21
    //   122: sipush 200
    //   125: if_icmpeq +196 -> 321
    //   128: getstatic 109	jp/co/imobile/android/AdRequestResultType:NETWORK_ERROR	Ljp/co/imobile/android/AdRequestResultType;
    //   131: astore 33
    //   133: iconst_4
    //   134: anewarray 48	java/lang/String
    //   137: astore 34
    //   139: aload 34
    //   141: iconst_0
    //   142: ldc 111
    //   144: aastore
    //   145: aload 34
    //   147: iconst_1
    //   148: aload 8
    //   150: aastore
    //   151: aload 34
    //   153: iconst_2
    //   154: ldc 113
    //   156: aastore
    //   157: aload 34
    //   159: iconst_3
    //   160: iload 21
    //   162: invokestatic 52	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   165: aastore
    //   166: new 115	jp/co/imobile/android/p
    //   169: dup
    //   170: aload 33
    //   172: ldc 117
    //   174: aload_0
    //   175: aload 34
    //   177: invokestatic 120	jp/co/imobile/android/cj:d	(Ljava/lang/String;Ljp/co/imobile/android/bp;[Ljava/lang/String;)Ljava/lang/String;
    //   180: invokespecial 123	jp/co/imobile/android/p:<init>	(Ljp/co/imobile/android/AdRequestResultType;Ljava/lang/String;)V
    //   183: athrow
    //   184: astore 11
    //   186: aload 8
    //   188: astore 5
    //   190: aconst_null
    //   191: astore 12
    //   193: getstatic 109	jp/co/imobile/android/AdRequestResultType:NETWORK_ERROR	Ljp/co/imobile/android/AdRequestResultType;
    //   196: astore 13
    //   198: iconst_4
    //   199: anewarray 48	java/lang/String
    //   202: astore 14
    //   204: aload 14
    //   206: iconst_0
    //   207: ldc 125
    //   209: aastore
    //   210: aload 14
    //   212: iconst_1
    //   213: iload_1
    //   214: invokestatic 52	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   217: aastore
    //   218: aload 14
    //   220: iconst_2
    //   221: ldc 111
    //   223: aastore
    //   224: aload 14
    //   226: iconst_3
    //   227: aload 5
    //   229: aastore
    //   230: new 115	jp/co/imobile/android/p
    //   233: dup
    //   234: aload 13
    //   236: ldc 127
    //   238: aload_0
    //   239: aload 14
    //   241: invokestatic 120	jp/co/imobile/android/cj:d	(Ljava/lang/String;Ljp/co/imobile/android/bp;[Ljava/lang/String;)Ljava/lang/String;
    //   244: aload 11
    //   246: invokespecial 130	jp/co/imobile/android/p:<init>	(Ljp/co/imobile/android/AdRequestResultType;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   249: athrow
    //   250: astore 7
    //   252: aload 12
    //   254: astore 4
    //   256: aload 5
    //   258: astore 8
    //   260: aload 4
    //   262: ifnull +8 -> 270
    //   265: aload 4
    //   267: invokevirtual 135	java/io/InputStream:close	()V
    //   270: aload 6
    //   272: ifnull +13 -> 285
    //   275: aload 6
    //   277: invokevirtual 139	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   280: invokeinterface 144 1 0
    //   285: aload 7
    //   287: athrow
    //   288: aload_0
    //   289: ldc 39
    //   291: ldc 146
    //   293: invokespecial 44	jp/co/imobile/android/n:a	(Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   296: astore 15
    //   298: aload 15
    //   300: ldc 148
    //   302: iload_1
    //   303: invokestatic 52	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   306: invokevirtual 57	android/net/Uri$Builder:appendQueryParameter	(Ljava/lang/String;Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   309: pop
    //   310: goto -272 -> 38
    //   313: astore 11
    //   315: aconst_null
    //   316: astore 12
    //   318: goto -125 -> 193
    //   321: aload 20
    //   323: invokeinterface 152 1 0
    //   328: invokeinterface 158 1 0
    //   333: astore 22
    //   335: aload 22
    //   337: ifnull +183 -> 520
    //   340: aload 22
    //   342: ldc 160
    //   344: invokestatic 166	android/graphics/drawable/Drawable:createFromStream	(Ljava/io/InputStream;Ljava/lang/String;)Landroid/graphics/drawable/Drawable;
    //   347: astore 30
    //   349: aload 30
    //   351: astore 25
    //   353: aload 22
    //   355: ifnull +8 -> 363
    //   358: aload 22
    //   360: invokevirtual 135	java/io/InputStream:close	()V
    //   363: aload 6
    //   365: ifnull +13 -> 378
    //   368: aload 6
    //   370: invokevirtual 139	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   373: invokeinterface 144 1 0
    //   378: aload 25
    //   380: areturn
    //   381: astore 31
    //   383: iconst_4
    //   384: anewarray 48	java/lang/String
    //   387: astore 32
    //   389: aload 32
    //   391: iconst_0
    //   392: ldc 125
    //   394: aastore
    //   395: aload 32
    //   397: iconst_1
    //   398: iload_1
    //   399: invokestatic 52	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   402: aastore
    //   403: aload 32
    //   405: iconst_2
    //   406: ldc 111
    //   408: aastore
    //   409: aload 32
    //   411: iconst_3
    //   412: aload 8
    //   414: aastore
    //   415: ldc 168
    //   417: aload_0
    //   418: aload 31
    //   420: aload 32
    //   422: invokestatic 171	jp/co/imobile/android/cj:a	(Ljava/lang/String;Ljp/co/imobile/android/bp;Ljava/lang/Throwable;[Ljava/lang/String;)V
    //   425: goto -47 -> 378
    //   428: astore 27
    //   430: getstatic 109	jp/co/imobile/android/AdRequestResultType:NETWORK_ERROR	Ljp/co/imobile/android/AdRequestResultType;
    //   433: astore 28
    //   435: bipush 6
    //   437: anewarray 48	java/lang/String
    //   440: astore 29
    //   442: aload 29
    //   444: iconst_0
    //   445: ldc 125
    //   447: aastore
    //   448: aload 29
    //   450: iconst_1
    //   451: iload_1
    //   452: invokestatic 52	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   455: aastore
    //   456: aload 29
    //   458: iconst_2
    //   459: ldc 111
    //   461: aastore
    //   462: aload 29
    //   464: iconst_3
    //   465: aload 8
    //   467: aastore
    //   468: aload 29
    //   470: iconst_4
    //   471: ldc 113
    //   473: aastore
    //   474: aload 29
    //   476: iconst_5
    //   477: iload 21
    //   479: invokestatic 52	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   482: aastore
    //   483: new 115	jp/co/imobile/android/p
    //   486: dup
    //   487: aload 28
    //   489: ldc 173
    //   491: aload_0
    //   492: aload 29
    //   494: invokestatic 120	jp/co/imobile/android/cj:d	(Ljava/lang/String;Ljp/co/imobile/android/bp;[Ljava/lang/String;)Ljava/lang/String;
    //   497: aload 27
    //   499: invokespecial 130	jp/co/imobile/android/p:<init>	(Ljp/co/imobile/android/AdRequestResultType;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   502: athrow
    //   503: astore 11
    //   505: aload 8
    //   507: astore 26
    //   509: aload 22
    //   511: astore 12
    //   513: aload 26
    //   515: astore 5
    //   517: goto -324 -> 193
    //   520: aload 22
    //   522: ifnull +8 -> 530
    //   525: aload 22
    //   527: invokevirtual 135	java/io/InputStream:close	()V
    //   530: aload 6
    //   532: ifnull +13 -> 545
    //   535: aload 6
    //   537: invokevirtual 139	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   540: invokeinterface 144 1 0
    //   545: aconst_null
    //   546: astore 25
    //   548: goto -170 -> 378
    //   551: astore 23
    //   553: iconst_4
    //   554: anewarray 48	java/lang/String
    //   557: astore 24
    //   559: aload 24
    //   561: iconst_0
    //   562: ldc 125
    //   564: aastore
    //   565: aload 24
    //   567: iconst_1
    //   568: iload_1
    //   569: invokestatic 52	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   572: aastore
    //   573: aload 24
    //   575: iconst_2
    //   576: ldc 111
    //   578: aastore
    //   579: aload 24
    //   581: iconst_3
    //   582: aload 8
    //   584: aastore
    //   585: ldc 168
    //   587: aload_0
    //   588: aload 23
    //   590: aload 24
    //   592: invokestatic 171	jp/co/imobile/android/cj:a	(Ljava/lang/String;Ljp/co/imobile/android/bp;Ljava/lang/Throwable;[Ljava/lang/String;)V
    //   595: goto -50 -> 545
    //   598: astore 9
    //   600: iconst_4
    //   601: anewarray 48	java/lang/String
    //   604: astore 10
    //   606: aload 10
    //   608: iconst_0
    //   609: ldc 125
    //   611: aastore
    //   612: aload 10
    //   614: iconst_1
    //   615: iload_1
    //   616: invokestatic 52	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   619: aastore
    //   620: aload 10
    //   622: iconst_2
    //   623: ldc 111
    //   625: aastore
    //   626: aload 10
    //   628: iconst_3
    //   629: aload 8
    //   631: aastore
    //   632: ldc 168
    //   634: aload_0
    //   635: aload 9
    //   637: aload 10
    //   639: invokestatic 171	jp/co/imobile/android/cj:a	(Ljava/lang/String;Ljp/co/imobile/android/bp;Ljava/lang/Throwable;[Ljava/lang/String;)V
    //   642: goto -357 -> 285
    //   645: astore 7
    //   647: aload 5
    //   649: astore 8
    //   651: goto -391 -> 260
    //   654: astore 7
    //   656: goto -396 -> 260
    //   659: astore 7
    //   661: aload 22
    //   663: astore 4
    //   665: goto -405 -> 260
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	668	0	this	n
    //   0	668	1	paramInt	int
    //   0	668	2	parambo	bo
    //   0	668	3	paramBoolean	boolean
    //   1	663	4	localObject1	Object
    //   5	643	5	localObject2	Object
    //   10	526	6	localDefaultHttpClient	DefaultHttpClient
    //   250	36	7	localObject3	Object
    //   645	1	7	localObject4	Object
    //   654	1	7	localObject5	Object
    //   659	1	7	localObject6	Object
    //   62	588	8	localObject7	Object
    //   598	38	9	localIOException1	IOException
    //   604	34	10	arrayOfString1	String[]
    //   184	61	11	localIOException2	IOException
    //   313	1	11	localIOException3	IOException
    //   503	1	11	localIOException4	IOException
    //   191	321	12	localObject8	Object
    //   196	39	13	localAdRequestResultType1	AdRequestResultType
    //   202	38	14	arrayOfString2	String[]
    //   24	275	15	localBuilder	Uri.Builder
    //   58	3	18	str	String
    //   68	18	19	arrayOfString3	String[]
    //   104	218	20	localHttpResponse	HttpResponse
    //   118	360	21	i	int
    //   333	329	22	localInputStream	java.io.InputStream
    //   551	38	23	localIOException5	IOException
    //   557	34	24	arrayOfString4	String[]
    //   351	196	25	localDrawable1	Drawable
    //   507	7	26	localObject9	Object
    //   428	70	27	localException	java.lang.Exception
    //   433	55	28	localAdRequestResultType2	AdRequestResultType
    //   440	53	29	arrayOfString5	String[]
    //   347	3	30	localDrawable2	Drawable
    //   381	38	31	localIOException6	IOException
    //   387	34	32	arrayOfString6	String[]
    //   131	40	33	localAdRequestResultType3	AdRequestResultType
    //   137	39	34	arrayOfString7	String[]
    // Exception table:
    //   from	to	target	type
    //   64	184	184	java/io/IOException
    //   321	335	184	java/io/IOException
    //   193	250	250	finally
    //   16	60	313	java/io/IOException
    //   288	310	313	java/io/IOException
    //   358	378	381	java/io/IOException
    //   340	349	428	java/lang/Exception
    //   340	349	503	java/io/IOException
    //   430	503	503	java/io/IOException
    //   525	545	551	java/io/IOException
    //   265	285	598	java/io/IOException
    //   16	60	645	finally
    //   288	310	645	finally
    //   64	184	654	finally
    //   321	335	654	finally
    //   340	349	659	finally
    //   430	503	659	finally
  }
  
  private final Uri.Builder a(String paramString1, String paramString2)
  {
    Uri.Builder localBuilder1 = new Uri.Builder();
    localBuilder1.scheme("http").encodedAuthority(paramString1).path(paramString2);
    Uri.Builder localBuilder2 = localBuilder1.appendQueryParameter("spt", "android");
    localBuilder2.appendQueryParameter("lang", ao.c()).appendQueryParameter("pid", String.valueOf(this.a)).appendQueryParameter("mid", String.valueOf(this.b)).appendQueryParameter("asid", String.valueOf(this.c)).appendQueryParameter("test", String.valueOf(a()));
    Uri.Builder localBuilder3 = localBuilder1.appendQueryParameter("appid", this.d.d()).appendQueryParameter("dpw", String.valueOf(this.d.k())).appendQueryParameter("dph", String.valueOf(this.d.l())).appendQueryParameter("sdkv", "1.4");
    localBuilder3.appendQueryParameter("os", ao.e());
    String str1 = this.d.i();
    if (ci.a(str1)) {
      localBuilder1.appendQueryParameter("nk", str1);
    }
    Uri.Builder localBuilder4 = localBuilder1.appendQueryParameter("dvbrand", ao.f());
    Uri.Builder localBuilder5 = localBuilder4.appendQueryParameter("dvnamec", ao.g());
    localBuilder5.appendQueryParameter("dvname", ao.h());
    if (this.d.a()) {
      localBuilder1.appendQueryParameter("adwhirl", String.valueOf(true));
    }
    String str2 = this.d.m();
    if ((!a()) && (cj.b()) && (ci.a(str2))) {
      localBuilder1.appendQueryParameter("mk", str2);
    }
    return localBuilder1;
  }
  
  private final String a(String paramString, List paramList)
  {
    DefaultHttpClient localDefaultHttpClient = e();
    HttpEntity localHttpEntity = null;
    try
    {
      String[] arrayOfString3 = new String[2];
      arrayOfString3[0] = ", url:";
      arrayOfString3[1] = paramString;
      cj.b("server result url", this, arrayOfString3);
      HttpPost localHttpPost = new HttpPost(paramString);
      localHttpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
      localHttpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
      int i = localHttpResponse.getStatusLine().getStatusCode();
      if (i == 200)
      {
        localHttpEntity = localHttpResponse.getEntity();
        String str1;
        if (localHttpEntity != null)
        {
          String str2 = EntityUtils.toString(localHttpEntity);
          str1 = str2;
          if (localHttpEntity == null) {}
        }
        for (;;)
        {
          try
          {
            localHttpEntity.consumeContent();
            if (localDefaultHttpClient != null) {
              localDefaultHttpClient.getConnectionManager().shutdown();
            }
            return str1;
          }
          catch (IOException localIOException4)
          {
            String[] arrayOfString6 = new String[2];
            arrayOfString6[0] = ", post jsonData URL:";
            arrayOfString6[1] = paramString;
            cj.a("IO close error", this, localIOException4, arrayOfString6);
            continue;
          }
          if (localHttpEntity != null) {}
          try
          {
            localHttpEntity.consumeContent();
            if (localDefaultHttpClient != null) {
              localDefaultHttpClient.getConnectionManager().shutdown();
            }
            str1 = "";
          }
          catch (IOException localIOException3)
          {
            for (;;)
            {
              String[] arrayOfString5 = new String[2];
              arrayOfString5[0] = ", post jsonData URL:";
              arrayOfString5[1] = paramString;
              cj.a("IO close error", this, localIOException3, arrayOfString5);
            }
          }
        }
      }
      AdRequestResultType localAdRequestResultType2 = AdRequestResultType.NETWORK_ERROR;
      String[] arrayOfString4 = new String[4];
      arrayOfString4[0] = ", post jsonData URL:";
      arrayOfString4[1] = paramString;
      arrayOfString4[2] = ", status:";
      arrayOfString4[3] = String.valueOf(i);
      throw new p(localAdRequestResultType2, cj.d("server response status error", this, arrayOfString4));
    }
    catch (IOException localIOException2)
    {
      AdRequestResultType localAdRequestResultType1 = AdRequestResultType.NETWORK_ERROR;
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = ", post jsonData URL:";
      arrayOfString2[1] = paramString;
      throw new p(localAdRequestResultType1, cj.d("server response io error", this, arrayOfString2), localIOException2);
    }
    finally
    {
      if (localHttpEntity == null) {}
    }
    try
    {
      localHttpEntity.consumeContent();
      if (localDefaultHttpClient != null) {
        localDefaultHttpClient.getConnectionManager().shutdown();
      }
      throw localObject;
    }
    catch (IOException localIOException1)
    {
      for (;;)
      {
        String[] arrayOfString1 = new String[2];
        arrayOfString1[0] = ", post jsonData URL:";
        arrayOfString1[1] = paramString;
        cj.a("IO close error", this, localIOException1, arrayOfString1);
      }
    }
  }
  
  static List a(JSONObject paramJSONObject, int paramInt)
  {
    JSONArray localJSONArray = paramJSONObject.optJSONArray("imageAds");
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2;
    if (localJSONArray == null)
    {
      localArrayList2 = localArrayList1;
      return localArrayList2;
    }
    int i = localJSONArray.length();
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        localArrayList2 = localArrayList1;
        break;
      }
      JSONObject localJSONObject = localJSONArray.getJSONObject(j);
      int k = localJSONObject.getInt("eid");
      bo localbo = (bo)ay.a(bo.b(), Integer.valueOf(k), bo.b);
      String str = ci.a(localJSONObject.optString("alt", ""));
      localArrayList1.add(new bi(paramInt, localbo, localJSONObject.getInt("width"), localJSONObject.getInt("height"), str));
    }
  }
  
  static void b(String paramString)
  {
    JSONObject localJSONObject1 = new JSONObject(paramString);
    if (localJSONObject1.getString("status").equalsIgnoreCase("succeed")) {}
    JSONObject localJSONObject2;
    AdRequestResultType localAdRequestResultType;
    do
    {
      return;
      localJSONObject2 = localJSONObject1.getJSONObject("error");
      int i = localJSONObject2.optInt("code", -1);
      localAdRequestResultType = (AdRequestResultType)ay.a(AdRequestResultType.values(), Integer.valueOf(i));
    } while (localAdRequestResultType == null);
    throw new p(localAdRequestResultType, localJSONObject2.optString("message", "unknown message"));
  }
  
  /* Error */
  private final String c(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: invokestatic 37	jp/co/imobile/android/n:e	()Lorg/apache/http/impl/client/DefaultHttpClient;
    //   5: astore_3
    //   6: iconst_2
    //   7: anewarray 48	java/lang/String
    //   10: astore 13
    //   12: aload 13
    //   14: iconst_0
    //   15: ldc 73
    //   17: aastore
    //   18: aload 13
    //   20: iconst_1
    //   21: aload_1
    //   22: aastore
    //   23: ldc 75
    //   25: aload_0
    //   26: aload 13
    //   28: invokestatic 80	jp/co/imobile/android/cj:b	(Ljava/lang/String;Ljp/co/imobile/android/bp;[Ljava/lang/String;)V
    //   31: new 82	org/apache/http/client/methods/HttpGet
    //   34: dup
    //   35: aload_1
    //   36: invokespecial 85	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
    //   39: astore 14
    //   41: aload 14
    //   43: ldc_w 401
    //   46: ldc_w 403
    //   49: invokevirtual 406	org/apache/http/client/methods/HttpGet:addHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   52: aload_3
    //   53: aload 14
    //   55: invokevirtual 91	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   58: astore 15
    //   60: aload 15
    //   62: invokeinterface 97 1 0
    //   67: invokeinterface 103 1 0
    //   72: istore 16
    //   74: iload 16
    //   76: sipush 200
    //   79: if_icmpne +353 -> 432
    //   82: aload 15
    //   84: ldc_w 408
    //   87: invokeinterface 412 2 0
    //   92: astore 19
    //   94: aload 19
    //   96: ifnull +150 -> 246
    //   99: aload 19
    //   101: invokeinterface 417 1 0
    //   106: ldc_w 403
    //   109: invokevirtual 377	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   112: ifeq +134 -> 246
    //   115: new 419	java/util/zip/GZIPInputStream
    //   118: dup
    //   119: aload 15
    //   121: invokeinterface 152 1 0
    //   126: invokeinterface 158 1 0
    //   131: invokespecial 422	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   134: astore 5
    //   136: iconst_2
    //   137: anewarray 48	java/lang/String
    //   140: astore 31
    //   142: aload 31
    //   144: iconst_0
    //   145: ldc 73
    //   147: aastore
    //   148: aload 31
    //   150: iconst_1
    //   151: aload_1
    //   152: aastore
    //   153: ldc_w 424
    //   156: aload_0
    //   157: aload 31
    //   159: invokestatic 80	jp/co/imobile/android/cj:b	(Ljava/lang/String;Ljp/co/imobile/android/bp;[Ljava/lang/String;)V
    //   162: aload 5
    //   164: astore 21
    //   166: new 426	java/io/BufferedReader
    //   169: dup
    //   170: new 428	java/io/InputStreamReader
    //   173: dup
    //   174: aload 21
    //   176: invokespecial 429	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   179: sipush 8192
    //   182: invokespecial 432	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   185: astore 22
    //   187: new 434	java/lang/StringBuilder
    //   190: dup
    //   191: invokespecial 435	java/lang/StringBuilder:<init>	()V
    //   194: astore 23
    //   196: aload 22
    //   198: invokevirtual 438	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   201: astore 24
    //   203: aload 24
    //   205: ifnonnull +62 -> 267
    //   208: aload 23
    //   210: invokevirtual 439	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   213: astore 26
    //   215: aload 21
    //   217: ifnull +8 -> 225
    //   220: aload 21
    //   222: invokevirtual 135	java/io/InputStream:close	()V
    //   225: aload 22
    //   227: invokevirtual 440	java/io/BufferedReader:close	()V
    //   230: aload_3
    //   231: ifnull +12 -> 243
    //   234: aload_3
    //   235: invokevirtual 139	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   238: invokeinterface 144 1 0
    //   243: aload 26
    //   245: areturn
    //   246: aload 15
    //   248: invokeinterface 152 1 0
    //   253: invokeinterface 158 1 0
    //   258: astore 20
    //   260: aload 20
    //   262: astore 21
    //   264: goto -98 -> 166
    //   267: aload 23
    //   269: aload 24
    //   271: invokevirtual 444	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   274: pop
    //   275: goto -79 -> 196
    //   278: astore 10
    //   280: aload 22
    //   282: astore_2
    //   283: aload 21
    //   285: astore 5
    //   287: getstatic 109	jp/co/imobile/android/AdRequestResultType:NETWORK_ERROR	Ljp/co/imobile/android/AdRequestResultType;
    //   290: astore 11
    //   292: iconst_2
    //   293: anewarray 48	java/lang/String
    //   296: astore 12
    //   298: aload 12
    //   300: iconst_0
    //   301: ldc_w 446
    //   304: aastore
    //   305: aload 12
    //   307: iconst_1
    //   308: aload_1
    //   309: aastore
    //   310: new 115	jp/co/imobile/android/p
    //   313: dup
    //   314: aload 11
    //   316: ldc 127
    //   318: aload_0
    //   319: aload 12
    //   321: invokestatic 120	jp/co/imobile/android/cj:d	(Ljava/lang/String;Ljp/co/imobile/android/bp;[Ljava/lang/String;)Ljava/lang/String;
    //   324: aload 10
    //   326: invokespecial 130	jp/co/imobile/android/p:<init>	(Ljp/co/imobile/android/AdRequestResultType;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   329: athrow
    //   330: astore 4
    //   332: aload 5
    //   334: ifnull +8 -> 342
    //   337: aload 5
    //   339: invokevirtual 135	java/io/InputStream:close	()V
    //   342: aload_2
    //   343: ifnull +7 -> 350
    //   346: aload_2
    //   347: invokevirtual 440	java/io/BufferedReader:close	()V
    //   350: aload_3
    //   351: ifnull +12 -> 363
    //   354: aload_3
    //   355: invokevirtual 139	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   358: invokeinterface 144 1 0
    //   363: aload 4
    //   365: athrow
    //   366: astore 29
    //   368: iconst_2
    //   369: anewarray 48	java/lang/String
    //   372: astore 30
    //   374: aload 30
    //   376: iconst_0
    //   377: ldc_w 446
    //   380: aastore
    //   381: aload 30
    //   383: iconst_1
    //   384: aload_1
    //   385: aastore
    //   386: ldc 168
    //   388: aload_0
    //   389: aload 29
    //   391: aload 30
    //   393: invokestatic 171	jp/co/imobile/android/cj:a	(Ljava/lang/String;Ljp/co/imobile/android/bp;Ljava/lang/Throwable;[Ljava/lang/String;)V
    //   396: goto -171 -> 225
    //   399: astore 27
    //   401: iconst_2
    //   402: anewarray 48	java/lang/String
    //   405: astore 28
    //   407: aload 28
    //   409: iconst_0
    //   410: ldc_w 446
    //   413: aastore
    //   414: aload 28
    //   416: iconst_1
    //   417: aload_1
    //   418: aastore
    //   419: ldc 168
    //   421: aload_0
    //   422: aload 27
    //   424: aload 28
    //   426: invokestatic 171	jp/co/imobile/android/cj:a	(Ljava/lang/String;Ljp/co/imobile/android/bp;Ljava/lang/Throwable;[Ljava/lang/String;)V
    //   429: goto -199 -> 230
    //   432: getstatic 109	jp/co/imobile/android/AdRequestResultType:NETWORK_ERROR	Ljp/co/imobile/android/AdRequestResultType;
    //   435: astore 17
    //   437: iconst_4
    //   438: anewarray 48	java/lang/String
    //   441: astore 18
    //   443: aload 18
    //   445: iconst_0
    //   446: ldc_w 446
    //   449: aastore
    //   450: aload 18
    //   452: iconst_1
    //   453: aload_1
    //   454: aastore
    //   455: aload 18
    //   457: iconst_2
    //   458: ldc 113
    //   460: aastore
    //   461: aload 18
    //   463: iconst_3
    //   464: iload 16
    //   466: invokestatic 52	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   469: aastore
    //   470: new 115	jp/co/imobile/android/p
    //   473: dup
    //   474: aload 17
    //   476: ldc 117
    //   478: aload_0
    //   479: aload 18
    //   481: invokestatic 120	jp/co/imobile/android/cj:d	(Ljava/lang/String;Ljp/co/imobile/android/bp;[Ljava/lang/String;)Ljava/lang/String;
    //   484: invokespecial 123	jp/co/imobile/android/p:<init>	(Ljp/co/imobile/android/AdRequestResultType;Ljava/lang/String;)V
    //   487: athrow
    //   488: astore 10
    //   490: aconst_null
    //   491: astore 5
    //   493: goto -206 -> 287
    //   496: astore 8
    //   498: iconst_2
    //   499: anewarray 48	java/lang/String
    //   502: astore 9
    //   504: aload 9
    //   506: iconst_0
    //   507: ldc_w 446
    //   510: aastore
    //   511: aload 9
    //   513: iconst_1
    //   514: aload_1
    //   515: aastore
    //   516: ldc 168
    //   518: aload_0
    //   519: aload 8
    //   521: aload 9
    //   523: invokestatic 171	jp/co/imobile/android/cj:a	(Ljava/lang/String;Ljp/co/imobile/android/bp;Ljava/lang/Throwable;[Ljava/lang/String;)V
    //   526: goto -184 -> 342
    //   529: astore 6
    //   531: iconst_2
    //   532: anewarray 48	java/lang/String
    //   535: astore 7
    //   537: aload 7
    //   539: iconst_0
    //   540: ldc_w 446
    //   543: aastore
    //   544: aload 7
    //   546: iconst_1
    //   547: aload_1
    //   548: aastore
    //   549: ldc 168
    //   551: aload_0
    //   552: aload 6
    //   554: aload 7
    //   556: invokestatic 171	jp/co/imobile/android/cj:a	(Ljava/lang/String;Ljp/co/imobile/android/bp;Ljava/lang/Throwable;[Ljava/lang/String;)V
    //   559: goto -209 -> 350
    //   562: astore 4
    //   564: aconst_null
    //   565: astore 5
    //   567: goto -235 -> 332
    //   570: astore 4
    //   572: aload 21
    //   574: astore 5
    //   576: goto -244 -> 332
    //   579: astore 4
    //   581: aload 22
    //   583: astore_2
    //   584: aload 21
    //   586: astore 5
    //   588: goto -256 -> 332
    //   591: astore 10
    //   593: goto -306 -> 287
    //   596: astore 10
    //   598: aload 21
    //   600: astore 5
    //   602: goto -315 -> 287
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	605	0	this	n
    //   0	605	1	paramString	String
    //   1	583	2	localObject1	Object
    //   5	350	3	localDefaultHttpClient	DefaultHttpClient
    //   330	34	4	localObject2	Object
    //   562	1	4	localObject3	Object
    //   570	1	4	localObject4	Object
    //   579	1	4	localObject5	Object
    //   134	467	5	localObject6	Object
    //   529	24	6	localIOException1	IOException
    //   535	20	7	arrayOfString1	String[]
    //   496	24	8	localIOException2	IOException
    //   502	20	9	arrayOfString2	String[]
    //   278	47	10	localIOException3	IOException
    //   488	1	10	localIOException4	IOException
    //   591	1	10	localIOException5	IOException
    //   596	1	10	localIOException6	IOException
    //   290	25	11	localAdRequestResultType1	AdRequestResultType
    //   296	24	12	arrayOfString3	String[]
    //   10	17	13	arrayOfString4	String[]
    //   39	15	14	localHttpGet	org.apache.http.client.methods.HttpGet
    //   58	189	15	localHttpResponse	HttpResponse
    //   72	393	16	i	int
    //   435	40	17	localAdRequestResultType2	AdRequestResultType
    //   441	39	18	arrayOfString5	String[]
    //   92	8	19	localHeader	org.apache.http.Header
    //   258	3	20	localInputStream	java.io.InputStream
    //   164	435	21	localObject7	Object
    //   185	397	22	localBufferedReader	java.io.BufferedReader
    //   194	74	23	localStringBuilder	java.lang.StringBuilder
    //   201	69	24	str1	String
    //   213	31	26	str2	String
    //   399	24	27	localIOException7	IOException
    //   405	20	28	arrayOfString6	String[]
    //   366	24	29	localIOException8	IOException
    //   372	20	30	arrayOfString7	String[]
    //   140	18	31	arrayOfString8	String[]
    // Exception table:
    //   from	to	target	type
    //   187	215	278	java/io/IOException
    //   267	275	278	java/io/IOException
    //   136	162	330	finally
    //   287	330	330	finally
    //   220	225	366	java/io/IOException
    //   225	230	399	java/io/IOException
    //   6	136	488	java/io/IOException
    //   246	260	488	java/io/IOException
    //   432	488	488	java/io/IOException
    //   337	342	496	java/io/IOException
    //   346	350	529	java/io/IOException
    //   6	136	562	finally
    //   246	260	562	finally
    //   432	488	562	finally
    //   166	187	570	finally
    //   187	215	579	finally
    //   267	275	579	finally
    //   136	162	591	java/io/IOException
    //   166	187	596	java/io/IOException
  }
  
  private static DefaultHttpClient e()
  {
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    HttpParams localHttpParams = localDefaultHttpClient.getParams();
    localHttpParams.setParameter("http.useragent", cj.e());
    HttpConnectionParams.setSocketBufferSize(localHttpParams, 8192);
    HttpConnectionParams.setConnectionTimeout(localHttpParams, 20000);
    HttpConnectionParams.setSoTimeout(localHttpParams, 20000);
    return localDefaultHttpClient;
  }
  
  final Drawable a(int paramInt, bo parambo)
  {
    return a(paramInt, parambo, false);
  }
  
  final String a(int paramInt1, int paramInt2)
  {
    Uri.Builder localBuilder = a("spapi.i-mobile.co.jp", "/app/api/ad_imp_count.ashx");
    localBuilder.appendQueryParameter("advid", String.valueOf(paramInt1)).appendQueryParameter("ctid", String.valueOf(paramInt2));
    return c(localBuilder.toString());
  }
  
  final String a(String paramString)
  {
    Uri.Builder localBuilder = a("spdmg.i-mobile.co.jp", "tr_pir.ashx");
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new BasicNameValuePair("pir", paramString));
    return a(localBuilder.toString(), localArrayList);
  }
  
  final void a(boolean paramBoolean)
  {
    this.e = Boolean.valueOf(paramBoolean);
  }
  
  final boolean a()
  {
    if (this.e == null) {}
    for (boolean bool = cj.a();; bool = this.e.booleanValue()) {
      return bool;
    }
  }
  
  final Drawable b(int paramInt, bo parambo)
  {
    return a(paramInt, parambo, true);
  }
  
  final Uri b(int paramInt1, int paramInt2)
  {
    Uri.Builder localBuilder = a("spapi.i-mobile.co.jp", "/app/api/ad_click.ashx");
    localBuilder.appendQueryParameter("advid", String.valueOf(paramInt1)).appendQueryParameter("ctid", String.valueOf(paramInt2));
    return localBuilder.build();
  }
  
  final String b()
  {
    return c(a("spapi.i-mobile.co.jp", "/app/api/ad_spot_environment.ashx").toString());
  }
  
  final String c()
  {
    return c(a("spapi.i-mobile.co.jp", "/app/api/ad_deliver.ashx").toString());
  }
  
  final int d()
  {
    return this.c;
  }
  
  public final String getLogContents()
  {
    return ",spotId:" + this.c;
  }
  
  public final String getLogTag()
  {
    return "(IM)AdRequest:";
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.n
 * JD-Core Version:    0.7.0.1
 */