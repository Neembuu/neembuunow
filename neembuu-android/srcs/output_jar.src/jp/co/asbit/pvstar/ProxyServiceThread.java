package jp.co.asbit.pvstar;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

class ProxyServiceThread
  extends Thread
{
  private static final String TAG = "ProxyServiceThread";
  private Context mContext;
  
  public ProxyServiceThread(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 25	java/net/ServerSocket
    //   5: dup
    //   6: sipush 25252
    //   9: invokespecial 28	java/net/ServerSocket:<init>	(I)V
    //   12: astore_2
    //   13: iconst_0
    //   14: istore_3
    //   15: new 6	jp/co/asbit/pvstar/ProxyServiceThread$ConnectionThread
    //   18: dup
    //   19: aload_2
    //   20: invokevirtual 32	java/net/ServerSocket:accept	()Ljava/net/Socket;
    //   23: iload_3
    //   24: aload_0
    //   25: getfield 20	jp/co/asbit/pvstar/ProxyServiceThread:mContext	Landroid/content/Context;
    //   28: invokespecial 35	jp/co/asbit/pvstar/ProxyServiceThread$ConnectionThread:<init>	(Ljava/net/Socket;ILandroid/content/Context;)V
    //   31: invokevirtual 38	jp/co/asbit/pvstar/ProxyServiceThread$ConnectionThread:start	()V
    //   34: iinc 3 1
    //   37: goto -22 -> 15
    //   40: astore 6
    //   42: aload_1
    //   43: ifnull +7 -> 50
    //   46: aload_1
    //   47: invokevirtual 41	java/net/ServerSocket:close	()V
    //   50: return
    //   51: astore 5
    //   53: goto -3 -> 50
    //   56: astore 4
    //   58: aload_2
    //   59: astore_1
    //   60: goto -18 -> 42
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	63	0	this	ProxyServiceThread
    //   1	59	1	localObject	java.lang.Object
    //   12	47	2	localServerSocket	java.net.ServerSocket
    //   14	21	3	i	int
    //   56	1	4	localException1	java.lang.Exception
    //   51	1	5	localException2	java.lang.Exception
    //   40	1	6	localException3	java.lang.Exception
    // Exception table:
    //   from	to	target	type
    //   2	13	40	java/lang/Exception
    //   46	50	51	java/lang/Exception
    //   15	34	56	java/lang/Exception
  }
  
  static class ConnectionThread
    extends Thread
  {
    private Socket client;
    private Context mContext;
    
    public ConnectionThread(Socket paramSocket, int paramInt, Context paramContext)
    {
      this.client = paramSocket;
      this.mContext = paramContext;
    }
    
    private String readline(InputStream paramInputStream)
      throws IOException
    {
      byte[] arrayOfByte = new byte[1024];
      for (int i = 0;; i++)
      {
        if (paramInputStream.read(arrayOfByte, i, 1) < 0) {}
        while (arrayOfByte[i] == 10)
        {
          if ((i > 0) && (arrayOfByte[(i - 1)] == 13)) {
            i--;
          }
          return new String(arrayOfByte, 0, i);
        }
      }
    }
    
    /* Error */
    public void run()
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_1
      //   2: aconst_null
      //   3: astore_2
      //   4: aconst_null
      //   5: astore_3
      //   6: aconst_null
      //   7: astore 4
      //   9: aconst_null
      //   10: astore 5
      //   12: iconst_0
      //   13: istore 6
      //   15: aconst_null
      //   16: astore 7
      //   18: new 42	jp/co/asbit/pvstar/cache/CacheManager
      //   21: dup
      //   22: aload_0
      //   23: getfield 20	jp/co/asbit/pvstar/ProxyServiceThread$ConnectionThread:mContext	Landroid/content/Context;
      //   26: invokespecial 45	jp/co/asbit/pvstar/cache/CacheManager:<init>	(Landroid/content/Context;)V
      //   29: astore 8
      //   31: aload 8
      //   33: astore 7
      //   35: aload_0
      //   36: getfield 18	jp/co/asbit/pvstar/ProxyServiceThread$ConnectionThread:client	Ljava/net/Socket;
      //   39: invokevirtual 51	java/net/Socket:getInetAddress	()Ljava/net/InetAddress;
      //   42: invokevirtual 57	java/net/InetAddress:toString	()Ljava/lang/String;
      //   45: ldc 59
      //   47: invokevirtual 63	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   50: ifne +102 -> 152
      //   53: ldc 65
      //   55: ldc 67
      //   57: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   60: pop
      //   61: iconst_0
      //   62: ifeq +16 -> 78
      //   65: iconst_0
      //   66: ifeq +12 -> 78
      //   69: aconst_null
      //   70: invokevirtual 78	jp/co/asbit/pvstar/cache/Cache:completeCache	()V
      //   73: aload 7
      //   75: invokevirtual 81	jp/co/asbit/pvstar/cache/CacheManager:trimCache	()V
      //   78: iconst_0
      //   79: ifeq +7 -> 86
      //   82: aconst_null
      //   83: invokevirtual 86	java/io/BufferedWriter:close	()V
      //   86: iconst_0
      //   87: ifeq +7 -> 94
      //   90: aconst_null
      //   91: invokevirtual 89	java/io/BufferedInputStream:close	()V
      //   94: iconst_0
      //   95: ifeq +7 -> 102
      //   98: aconst_null
      //   99: invokevirtual 90	java/net/Socket:close	()V
      //   102: iconst_0
      //   103: ifeq +7 -> 110
      //   106: aconst_null
      //   107: invokevirtual 93	java/io/BufferedOutputStream:close	()V
      //   110: return
      //   111: astore 90
      //   113: aload 90
      //   115: invokevirtual 96	jp/co/asbit/pvstar/cache/CacheManager$CachingDisableException:printStackTrace	()V
      //   118: goto -83 -> 35
      //   121: astore 87
      //   123: ldc 65
      //   125: new 98	java/lang/StringBuilder
      //   128: dup
      //   129: ldc 100
      //   131: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   134: aload 87
      //   136: invokevirtual 106	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   139: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   142: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   145: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   148: pop
      //   149: goto -39 -> 110
      //   152: new 92	java/io/BufferedOutputStream
      //   155: dup
      //   156: aload_0
      //   157: getfield 18	jp/co/asbit/pvstar/ProxyServiceThread$ConnectionThread:client	Ljava/net/Socket;
      //   160: invokevirtual 115	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
      //   163: invokespecial 118	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
      //   166: astore 20
      //   168: new 120	java/io/InputStreamReader
      //   171: dup
      //   172: aload_0
      //   173: getfield 18	jp/co/asbit/pvstar/ProxyServiceThread$ConnectionThread:client	Ljava/net/Socket;
      //   176: invokevirtual 124	java/net/Socket:getInputStream	()Ljava/io/InputStream;
      //   179: invokespecial 127	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
      //   182: astore 21
      //   184: new 129	java/io/BufferedReader
      //   187: dup
      //   188: aload 21
      //   190: invokespecial 132	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
      //   193: astore 22
      //   195: aload 22
      //   197: invokevirtual 135	java/io/BufferedReader:readLine	()Ljava/lang/String;
      //   200: ldc 137
      //   202: invokevirtual 141	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
      //   205: astore 23
      //   207: aconst_null
      //   208: astore 24
      //   210: new 143	java/net/URI
      //   213: dup
      //   214: aload 23
      //   216: iconst_1
      //   217: aaload
      //   218: invokespecial 144	java/net/URI:<init>	(Ljava/lang/String;)V
      //   221: ldc 146
      //   223: invokestatic 152	org/apache/http/client/utils/URLEncodedUtils:parse	(Ljava/net/URI;Ljava/lang/String;)Ljava/util/List;
      //   226: invokeinterface 158 1 0
      //   231: astore 25
      //   233: aload 25
      //   235: invokeinterface 164 1 0
      //   240: ifne +129 -> 369
      //   243: aload 24
      //   245: ifnonnull +248 -> 493
      //   248: new 166	java/lang/NullPointerException
      //   251: dup
      //   252: ldc 168
      //   254: invokespecial 169	java/lang/NullPointerException:<init>	(Ljava/lang/String;)V
      //   257: athrow
      //   258: astore 9
      //   260: aload 20
      //   262: astore_1
      //   263: iconst_0
      //   264: istore 10
      //   266: aload 9
      //   268: invokevirtual 173	java/lang/Exception:getStackTrace	()[Ljava/lang/StackTraceElement;
      //   271: arraylength
      //   272: istore 15
      //   274: iload 10
      //   276: iload 15
      //   278: if_icmplt +1877 -> 2155
      //   281: aload 5
      //   283: ifnull +18 -> 301
      //   286: iload 6
      //   288: ifeq +13 -> 301
      //   291: aload 5
      //   293: invokevirtual 78	jp/co/asbit/pvstar/cache/Cache:completeCache	()V
      //   296: aload 7
      //   298: invokevirtual 81	jp/co/asbit/pvstar/cache/CacheManager:trimCache	()V
      //   301: aload_3
      //   302: ifnull +7 -> 309
      //   305: aload_3
      //   306: invokevirtual 86	java/io/BufferedWriter:close	()V
      //   309: aload_2
      //   310: ifnull +7 -> 317
      //   313: aload_2
      //   314: invokevirtual 89	java/io/BufferedInputStream:close	()V
      //   317: aload 4
      //   319: ifnull +8 -> 327
      //   322: aload 4
      //   324: invokevirtual 90	java/net/Socket:close	()V
      //   327: aload_1
      //   328: ifnull -218 -> 110
      //   331: aload_1
      //   332: invokevirtual 93	java/io/BufferedOutputStream:close	()V
      //   335: goto -225 -> 110
      //   338: astore 17
      //   340: ldc 65
      //   342: new 98	java/lang/StringBuilder
      //   345: dup
      //   346: ldc 100
      //   348: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   351: aload 17
      //   353: invokevirtual 106	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   356: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   359: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   362: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   365: pop
      //   366: goto -256 -> 110
      //   369: aload 25
      //   371: invokeinterface 177 1 0
      //   376: checkcast 179	org/apache/http/NameValuePair
      //   379: astore 26
      //   381: aload 26
      //   383: invokeinterface 182 1 0
      //   388: ldc 184
      //   390: invokevirtual 63	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   393: ifeq -160 -> 233
      //   396: new 186	jp/co/asbit/pvstar/VideoDbHelper
      //   399: dup
      //   400: aload_0
      //   401: getfield 20	jp/co/asbit/pvstar/ProxyServiceThread$ConnectionThread:mContext	Landroid/content/Context;
      //   404: invokespecial 187	jp/co/asbit/pvstar/VideoDbHelper:<init>	(Landroid/content/Context;)V
      //   407: astore 27
      //   409: aload 27
      //   411: aload 26
      //   413: invokeinterface 190 1 0
      //   418: invokevirtual 194	jp/co/asbit/pvstar/VideoDbHelper:getVideoUrl	(Ljava/lang/String;)Ljp/co/asbit/pvstar/LocalProxyUrl;
      //   421: astore 24
      //   423: aload 27
      //   425: invokevirtual 195	jp/co/asbit/pvstar/VideoDbHelper:close	()V
      //   428: goto -185 -> 243
      //   431: astore 11
      //   433: aload 20
      //   435: astore_1
      //   436: aload 5
      //   438: ifnull +18 -> 456
      //   441: iload 6
      //   443: ifeq +13 -> 456
      //   446: aload 5
      //   448: invokevirtual 78	jp/co/asbit/pvstar/cache/Cache:completeCache	()V
      //   451: aload 7
      //   453: invokevirtual 81	jp/co/asbit/pvstar/cache/CacheManager:trimCache	()V
      //   456: aload_3
      //   457: ifnull +7 -> 464
      //   460: aload_3
      //   461: invokevirtual 86	java/io/BufferedWriter:close	()V
      //   464: aload_2
      //   465: ifnull +7 -> 472
      //   468: aload_2
      //   469: invokevirtual 89	java/io/BufferedInputStream:close	()V
      //   472: aload 4
      //   474: ifnull +8 -> 482
      //   477: aload 4
      //   479: invokevirtual 90	java/net/Socket:close	()V
      //   482: aload_1
      //   483: ifnull +7 -> 490
      //   486: aload_1
      //   487: invokevirtual 93	java/io/BufferedOutputStream:close	()V
      //   490: aload 11
      //   492: athrow
      //   493: aload 24
      //   495: invokevirtual 200	jp/co/asbit/pvstar/LocalProxyUrl:getUrl	()Ljava/lang/String;
      //   498: astore 28
      //   500: aload 24
      //   502: invokevirtual 203	jp/co/asbit/pvstar/LocalProxyUrl:getCookie	()Ljava/lang/String;
      //   505: astore 29
      //   507: aload 24
      //   509: invokevirtual 206	jp/co/asbit/pvstar/LocalProxyUrl:getUseragent	()Ljava/lang/String;
      //   512: astore 30
      //   514: aload 24
      //   516: invokevirtual 209	jp/co/asbit/pvstar/LocalProxyUrl:getKey	()Ljava/lang/String;
      //   519: astore 31
      //   521: aload 28
      //   523: invokestatic 214	android/net/Uri:parse	(Ljava/lang/String;)Landroid/net/Uri;
      //   526: astore 32
      //   528: iconst_0
      //   529: istore 33
      //   531: aload 32
      //   533: invokevirtual 218	android/net/Uri:getPort	()I
      //   536: ifle +301 -> 837
      //   539: aload 32
      //   541: invokevirtual 218	android/net/Uri:getPort	()I
      //   544: istore 33
      //   546: iload 33
      //   548: sipush 443
      //   551: if_icmpne +329 -> 880
      //   554: invokestatic 224	javax/net/ssl/SSLSocketFactory:getDefault	()Ljavax/net/SocketFactory;
      //   557: aload 32
      //   559: invokevirtual 227	android/net/Uri:getHost	()Ljava/lang/String;
      //   562: iload 33
      //   564: invokevirtual 233	javax/net/SocketFactory:createSocket	(Ljava/lang/String;I)Ljava/net/Socket;
      //   567: astore 4
      //   569: new 83	java/io/BufferedWriter
      //   572: dup
      //   573: new 235	java/io/OutputStreamWriter
      //   576: dup
      //   577: aload 4
      //   579: invokevirtual 115	java/net/Socket:getOutputStream	()Ljava/io/OutputStream;
      //   582: invokespecial 236	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;)V
      //   585: invokespecial 239	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
      //   588: astore 35
      //   590: aload 35
      //   592: new 98	java/lang/StringBuilder
      //   595: dup
      //   596: aload 23
      //   598: iconst_0
      //   599: aaload
      //   600: invokestatic 243	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
      //   603: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   606: ldc 137
      //   608: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   611: aload 32
      //   613: invokevirtual 246	android/net/Uri:getPath	()Ljava/lang/String;
      //   616: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   619: ldc 248
      //   621: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   624: aload 32
      //   626: invokevirtual 251	android/net/Uri:getQuery	()Ljava/lang/String;
      //   629: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   632: ldc 253
      //   634: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   637: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   640: invokevirtual 256	java/io/BufferedWriter:write	(Ljava/lang/String;)V
      //   643: ldc 65
      //   645: new 98	java/lang/StringBuilder
      //   648: dup
      //   649: aload 23
      //   651: iconst_0
      //   652: aaload
      //   653: invokestatic 243	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
      //   656: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   659: ldc 137
      //   661: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   664: aload 32
      //   666: invokevirtual 246	android/net/Uri:getPath	()Ljava/lang/String;
      //   669: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   672: ldc 248
      //   674: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   677: aload 32
      //   679: invokevirtual 251	android/net/Uri:getQuery	()Ljava/lang/String;
      //   682: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   685: ldc 253
      //   687: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   690: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   693: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   696: pop
      //   697: aload 22
      //   699: invokevirtual 259	java/io/BufferedReader:ready	()Z
      //   702: pop
      //   703: iconst_0
      //   704: istore 38
      //   706: aload 22
      //   708: invokevirtual 135	java/io/BufferedReader:readLine	()Ljava/lang/String;
      //   711: astore 39
      //   713: aload 39
      //   715: ifnonnull +188 -> 903
      //   718: aload 35
      //   720: invokevirtual 262	java/io/BufferedWriter:flush	()V
      //   723: new 88	java/io/BufferedInputStream
      //   726: dup
      //   727: aload 4
      //   729: invokevirtual 124	java/net/Socket:getInputStream	()Ljava/io/InputStream;
      //   732: invokespecial 263	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
      //   735: astore 47
      //   737: iconst_0
      //   738: istore 48
      //   740: bipush 255
      //   742: istore 49
      //   744: aconst_null
      //   745: astore 50
      //   747: aload_0
      //   748: aload 47
      //   750: invokespecial 265	jp/co/asbit/pvstar/ProxyServiceThread$ConnectionThread:readline	(Ljava/io/InputStream;)Ljava/lang/String;
      //   753: astore 51
      //   755: aload 51
      //   757: ifnonnull +604 -> 1361
      //   760: aload 23
      //   762: iconst_0
      //   763: aaload
      //   764: ldc_w 267
      //   767: invokevirtual 63	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   770: istore 57
      //   772: iload 57
      //   774: ifne +1130 -> 1904
      //   777: iconst_0
      //   778: ifeq +16 -> 794
      //   781: iconst_0
      //   782: ifeq +12 -> 794
      //   785: aconst_null
      //   786: invokevirtual 78	jp/co/asbit/pvstar/cache/Cache:completeCache	()V
      //   789: aload 7
      //   791: invokevirtual 81	jp/co/asbit/pvstar/cache/CacheManager:trimCache	()V
      //   794: aload 35
      //   796: ifnull +8 -> 804
      //   799: aload 35
      //   801: invokevirtual 86	java/io/BufferedWriter:close	()V
      //   804: aload 47
      //   806: ifnull +8 -> 814
      //   809: aload 47
      //   811: invokevirtual 89	java/io/BufferedInputStream:close	()V
      //   814: aload 4
      //   816: ifnull +8 -> 824
      //   819: aload 4
      //   821: invokevirtual 90	java/net/Socket:close	()V
      //   824: aload 20
      //   826: ifnull +8 -> 834
      //   829: aload 20
      //   831: invokevirtual 93	java/io/BufferedOutputStream:close	()V
      //   834: goto -724 -> 110
      //   837: aload 32
      //   839: invokevirtual 270	android/net/Uri:getScheme	()Ljava/lang/String;
      //   842: ldc_w 272
      //   845: invokevirtual 63	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   848: ifeq +10 -> 858
      //   851: bipush 80
      //   853: istore 33
      //   855: goto -309 -> 546
      //   858: aload 32
      //   860: invokevirtual 270	android/net/Uri:getScheme	()Ljava/lang/String;
      //   863: ldc_w 274
      //   866: invokevirtual 63	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   869: ifeq -323 -> 546
      //   872: sipush 443
      //   875: istore 33
      //   877: goto -331 -> 546
      //   880: new 47	java/net/Socket
      //   883: dup
      //   884: aload 32
      //   886: invokevirtual 227	android/net/Uri:getHost	()Ljava/lang/String;
      //   889: iload 33
      //   891: invokespecial 277	java/net/Socket:<init>	(Ljava/lang/String;I)V
      //   894: astore 34
      //   896: aload 34
      //   898: astore 4
      //   900: goto -331 -> 569
      //   903: ldc 65
      //   905: aload 39
      //   907: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   910: pop
      //   911: aload 39
      //   913: invokevirtual 280	java/lang/String:length	()I
      //   916: ifle +336 -> 1252
      //   919: aload 39
      //   921: ldc_w 282
      //   924: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   927: ifeq +123 -> 1050
      //   930: aload 35
      //   932: new 98	java/lang/StringBuilder
      //   935: dup
      //   936: ldc_w 288
      //   939: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   942: aload 32
      //   944: invokevirtual 227	android/net/Uri:getHost	()Ljava/lang/String;
      //   947: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   950: ldc_w 290
      //   953: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   956: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   959: invokevirtual 256	java/io/BufferedWriter:write	(Ljava/lang/String;)V
      //   962: ldc 65
      //   964: new 98	java/lang/StringBuilder
      //   967: dup
      //   968: ldc_w 292
      //   971: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   974: aload 32
      //   976: invokevirtual 227	android/net/Uri:getHost	()Ljava/lang/String;
      //   979: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   982: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   985: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   988: pop
      //   989: aload 29
      //   991: ifnull -285 -> 706
      //   994: aload 35
      //   996: new 98	java/lang/StringBuilder
      //   999: dup
      //   1000: ldc_w 294
      //   1003: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   1006: aload 29
      //   1008: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1011: ldc_w 290
      //   1014: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1017: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1020: invokevirtual 256	java/io/BufferedWriter:write	(Ljava/lang/String;)V
      //   1023: ldc 65
      //   1025: new 98	java/lang/StringBuilder
      //   1028: dup
      //   1029: ldc_w 296
      //   1032: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   1035: aload 29
      //   1037: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1040: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1043: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   1046: pop
      //   1047: goto -341 -> 706
      //   1050: aload 39
      //   1052: ldc_w 298
      //   1055: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1058: ifeq +59 -> 1117
      //   1061: aload 35
      //   1063: new 98	java/lang/StringBuilder
      //   1066: dup
      //   1067: ldc_w 300
      //   1070: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   1073: aload 30
      //   1075: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1078: ldc_w 290
      //   1081: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1084: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1087: invokevirtual 256	java/io/BufferedWriter:write	(Ljava/lang/String;)V
      //   1090: ldc 65
      //   1092: new 98	java/lang/StringBuilder
      //   1095: dup
      //   1096: ldc_w 302
      //   1099: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   1102: aload 30
      //   1104: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1107: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1110: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   1113: pop
      //   1114: goto -408 -> 706
      //   1117: aload 39
      //   1119: ldc_w 304
      //   1122: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1125: ifne +36 -> 1161
      //   1128: aload 39
      //   1130: ldc_w 306
      //   1133: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1136: ifne +25 -> 1161
      //   1139: aload 39
      //   1141: ldc_w 308
      //   1144: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1147: ifne +14 -> 1161
      //   1150: aload 39
      //   1152: ldc_w 310
      //   1155: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1158: ifeq +15 -> 1173
      //   1161: ldc 65
      //   1163: ldc_w 312
      //   1166: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   1169: pop
      //   1170: goto -464 -> 706
      //   1173: aload 39
      //   1175: ldc_w 314
      //   1178: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1181: ifeq +42 -> 1223
      //   1184: aload 39
      //   1186: bipush 15
      //   1188: invokevirtual 318	java/lang/String:substring	(I)Ljava/lang/String;
      //   1191: invokevirtual 321	java/lang/String:trim	()Ljava/lang/String;
      //   1194: invokestatic 327	java/lang/Integer:parseInt	(Ljava/lang/String;)I
      //   1197: istore 38
      //   1199: ldc 65
      //   1201: new 98	java/lang/StringBuilder
      //   1204: dup
      //   1205: ldc_w 329
      //   1208: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   1211: iload 38
      //   1213: invokevirtual 332	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   1216: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1219: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   1222: pop
      //   1223: aload 35
      //   1225: new 98	java/lang/StringBuilder
      //   1228: dup
      //   1229: aload 39
      //   1231: invokestatic 243	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
      //   1234: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   1237: ldc_w 290
      //   1240: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1243: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1246: invokevirtual 256	java/io/BufferedWriter:write	(Ljava/lang/String;)V
      //   1249: goto -543 -> 706
      //   1252: aload 35
      //   1254: ldc_w 290
      //   1257: invokevirtual 256	java/io/BufferedWriter:write	(Ljava/lang/String;)V
      //   1260: iload 38
      //   1262: ifle -544 -> 718
      //   1265: iload 38
      //   1267: newarray char
      //   1269: astore 41
      //   1271: aload 22
      //   1273: aload 41
      //   1275: iconst_0
      //   1276: iload 38
      //   1278: invokevirtual 335	java/io/BufferedReader:read	([CII)I
      //   1281: pop
      //   1282: new 98	java/lang/StringBuilder
      //   1285: dup
      //   1286: ldc_w 337
      //   1289: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   1292: astore 43
      //   1294: new 32	java/lang/String
      //   1297: dup
      //   1298: aload 41
      //   1300: invokespecial 340	java/lang/String:<init>	([C)V
      //   1303: astore 44
      //   1305: ldc 65
      //   1307: aload 43
      //   1309: aload 44
      //   1311: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1314: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1317: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   1320: pop
      //   1321: new 32	java/lang/String
      //   1324: dup
      //   1325: aload 41
      //   1327: invokespecial 340	java/lang/String:<init>	([C)V
      //   1330: astore 46
      //   1332: aload 35
      //   1334: new 98	java/lang/StringBuilder
      //   1337: dup
      //   1338: aload 46
      //   1340: invokestatic 243	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
      //   1343: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   1346: ldc_w 290
      //   1349: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1352: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1355: invokevirtual 256	java/io/BufferedWriter:write	(Ljava/lang/String;)V
      //   1358: goto -640 -> 718
      //   1361: ldc 65
      //   1363: new 98	java/lang/StringBuilder
      //   1366: dup
      //   1367: ldc_w 342
      //   1370: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   1373: aload 51
      //   1375: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1378: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1381: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   1384: pop
      //   1385: aload 51
      //   1387: ldc_w 314
      //   1390: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1393: ifeq +78 -> 1471
      //   1396: aload 51
      //   1398: ldc_w 344
      //   1401: invokevirtual 141	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
      //   1404: astore 79
      //   1406: aload 79
      //   1408: arraylength
      //   1409: iconst_1
      //   1410: if_icmple +21 -> 1431
      //   1413: aload 79
      //   1415: iconst_1
      //   1416: aaload
      //   1417: invokestatic 327	java/lang/Integer:parseInt	(Ljava/lang/String;)I
      //   1420: istore 80
      //   1422: iload 48
      //   1424: ifne +7 -> 1431
      //   1427: iload 80
      //   1429: istore 49
      //   1431: aload 20
      //   1433: new 98	java/lang/StringBuilder
      //   1436: dup
      //   1437: aload 51
      //   1439: invokestatic 243	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
      //   1442: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   1445: ldc_w 290
      //   1448: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1451: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1454: invokevirtual 348	java/lang/String:getBytes	()[B
      //   1457: invokevirtual 351	java/io/BufferedOutputStream:write	([B)V
      //   1460: aload 51
      //   1462: invokevirtual 280	java/lang/String:length	()I
      //   1465: ifne -718 -> 747
      //   1468: goto -708 -> 760
      //   1471: aload 51
      //   1473: ldc_w 353
      //   1476: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1479: ifeq +67 -> 1546
      //   1482: aload 51
      //   1484: ldc_w 344
      //   1487: invokevirtual 141	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
      //   1490: astore 77
      //   1492: aload 77
      //   1494: arraylength
      //   1495: iconst_2
      //   1496: if_icmplt -65 -> 1431
      //   1499: aload 77
      //   1501: iconst_1
      //   1502: aaload
      //   1503: ldc_w 355
      //   1506: invokevirtual 141	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
      //   1509: astore 78
      //   1511: aload 78
      //   1513: arraylength
      //   1514: iconst_1
      //   1515: if_icmple +12 -> 1527
      //   1518: aload 78
      //   1520: iconst_1
      //   1521: aaload
      //   1522: invokestatic 327	java/lang/Integer:parseInt	(Ljava/lang/String;)I
      //   1525: istore 48
      //   1527: aload 78
      //   1529: arraylength
      //   1530: iconst_3
      //   1531: if_icmple -100 -> 1431
      //   1534: aload 78
      //   1536: iconst_3
      //   1537: aaload
      //   1538: invokestatic 327	java/lang/Integer:parseInt	(Ljava/lang/String;)I
      //   1541: istore 49
      //   1543: goto -112 -> 1431
      //   1546: aload 51
      //   1548: ldc_w 357
      //   1551: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1554: ifeq +138 -> 1692
      //   1557: ldc_w 359
      //   1560: invokestatic 365	java/util/regex/Pattern:compile	(Ljava/lang/String;)Ljava/util/regex/Pattern;
      //   1563: aload 51
      //   1565: invokevirtual 369	java/util/regex/Pattern:matcher	(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
      //   1568: astore 71
      //   1570: aload 71
      //   1572: invokevirtual 374	java/util/regex/Matcher:find	()Z
      //   1575: ifeq -144 -> 1431
      //   1578: new 197	jp/co/asbit/pvstar/LocalProxyUrl
      //   1581: dup
      //   1582: aload 31
      //   1584: aload 71
      //   1586: iconst_1
      //   1587: invokevirtual 377	java/util/regex/Matcher:group	(I)Ljava/lang/String;
      //   1590: aload 29
      //   1592: aload 30
      //   1594: invokespecial 380	jp/co/asbit/pvstar/LocalProxyUrl:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      //   1597: astore 72
      //   1599: new 186	jp/co/asbit/pvstar/VideoDbHelper
      //   1602: dup
      //   1603: aload_0
      //   1604: getfield 20	jp/co/asbit/pvstar/ProxyServiceThread$ConnectionThread:mContext	Landroid/content/Context;
      //   1607: invokespecial 187	jp/co/asbit/pvstar/VideoDbHelper:<init>	(Landroid/content/Context;)V
      //   1610: astore 73
      //   1612: aload 73
      //   1614: aload 72
      //   1616: invokevirtual 384	jp/co/asbit/pvstar/VideoDbHelper:setVideoUrl	(Ljp/co/asbit/pvstar/LocalProxyUrl;)Z
      //   1619: pop
      //   1620: aload 73
      //   1622: invokevirtual 195	jp/co/asbit/pvstar/VideoDbHelper:close	()V
      //   1625: iconst_2
      //   1626: anewarray 386	java/lang/Object
      //   1629: astore 75
      //   1631: aload 75
      //   1633: iconst_0
      //   1634: sipush 25252
      //   1637: invokestatic 389	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   1640: aastore
      //   1641: aload 75
      //   1643: iconst_1
      //   1644: aload 31
      //   1646: aastore
      //   1647: ldc_w 391
      //   1650: aload 75
      //   1652: invokestatic 395	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
      //   1655: astore 76
      //   1657: aload 20
      //   1659: new 98	java/lang/StringBuilder
      //   1662: dup
      //   1663: ldc_w 397
      //   1666: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   1669: aload 76
      //   1671: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1674: ldc_w 290
      //   1677: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1680: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1683: invokevirtual 348	java/lang/String:getBytes	()[B
      //   1686: invokevirtual 351	java/io/BufferedOutputStream:write	([B)V
      //   1689: goto -942 -> 747
      //   1692: aload 51
      //   1694: ldc_w 399
      //   1697: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1700: ifeq +18 -> 1718
      //   1703: aload 51
      //   1705: ldc_w 344
      //   1708: invokevirtual 141	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
      //   1711: iconst_1
      //   1712: aaload
      //   1713: astore 50
      //   1715: goto -284 -> 1431
      //   1718: aload 51
      //   1720: ldc_w 401
      //   1723: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1726: ifeq +82 -> 1808
      //   1729: new 403	java/util/Date
      //   1732: dup
      //   1733: invokespecial 404	java/util/Date:<init>	()V
      //   1736: astore 53
      //   1738: new 406	java/text/SimpleDateFormat
      //   1741: dup
      //   1742: ldc_w 408
      //   1745: getstatic 414	java/util/Locale:US	Ljava/util/Locale;
      //   1748: invokespecial 417	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;Ljava/util/Locale;)V
      //   1751: astore 54
      //   1753: aload 54
      //   1755: ldc_w 419
      //   1758: invokestatic 425	java/util/TimeZone:getTimeZone	(Ljava/lang/String;)Ljava/util/TimeZone;
      //   1761: invokevirtual 429	java/text/SimpleDateFormat:setTimeZone	(Ljava/util/TimeZone;)V
      //   1764: aload 54
      //   1766: aload 53
      //   1768: invokevirtual 432	java/text/SimpleDateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
      //   1771: astore 55
      //   1773: aload 20
      //   1775: new 98	java/lang/StringBuilder
      //   1778: dup
      //   1779: ldc_w 434
      //   1782: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   1785: aload 55
      //   1787: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1790: ldc_w 436
      //   1793: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1796: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1799: invokevirtual 348	java/lang/String:getBytes	()[B
      //   1802: invokevirtual 351	java/io/BufferedOutputStream:write	([B)V
      //   1805: goto -1058 -> 747
      //   1808: aload 51
      //   1810: ldc_w 438
      //   1813: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1816: ifeq +17 -> 1833
      //   1819: aload 20
      //   1821: ldc_w 440
      //   1824: invokevirtual 348	java/lang/String:getBytes	()[B
      //   1827: invokevirtual 351	java/io/BufferedOutputStream:write	([B)V
      //   1830: goto -1083 -> 747
      //   1833: aload 51
      //   1835: ldc_w 442
      //   1838: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1841: ifne -1094 -> 747
      //   1844: aload 51
      //   1846: ldc_w 444
      //   1849: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1852: ifne -1105 -> 747
      //   1855: aload 51
      //   1857: ldc_w 306
      //   1860: invokevirtual 286	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   1863: istore 56
      //   1865: iload 56
      //   1867: ifeq -436 -> 1431
      //   1870: goto -1123 -> 747
      //   1873: astore 68
      //   1875: ldc 65
      //   1877: new 98	java/lang/StringBuilder
      //   1880: dup
      //   1881: ldc 100
      //   1883: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   1886: aload 68
      //   1888: invokevirtual 106	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   1891: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   1894: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   1897: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   1900: pop
      //   1901: goto -1067 -> 834
      //   1904: aload 31
      //   1906: ifnull +23 -> 1929
      //   1909: aload 7
      //   1911: ifnull +18 -> 1929
      //   1914: aload 7
      //   1916: aload 31
      //   1918: iload 49
      //   1920: invokevirtual 448	jp/co/asbit/pvstar/cache/CacheManager:getCacheForWrite	(Ljava/lang/String;I)Ljp/co/asbit/pvstar/cache/Cache;
      //   1923: astore 67
      //   1925: aload 67
      //   1927: astore 5
      //   1929: aload 50
      //   1931: ifnull +46 -> 1977
      //   1934: aload 50
      //   1936: ldc_w 450
      //   1939: invokevirtual 63	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   1942: ifne +14 -> 1956
      //   1945: aload 50
      //   1947: ldc_w 452
      //   1950: invokevirtual 63	java/lang/String:equals	(Ljava/lang/Object;)Z
      //   1953: ifeq +139 -> 2092
      //   1956: iload 49
      //   1958: ifle +134 -> 2092
      //   1961: aload 5
      //   1963: ifnull +129 -> 2092
      //   1966: aload 5
      //   1968: invokevirtual 455	jp/co/asbit/pvstar/cache/Cache:isExists	()Z
      //   1971: ifne +121 -> 2092
      //   1974: iconst_1
      //   1975: istore 6
      //   1977: sipush 8192
      //   1980: newarray byte
      //   1982: astore 58
      //   1984: iload 48
      //   1986: istore 59
      //   1988: iload 59
      //   1990: iload 49
      //   1992: if_icmpge +21 -> 2013
      //   1995: aload 47
      //   1997: aload 58
      //   1999: invokevirtual 458	java/io/BufferedInputStream:read	([B)I
      //   2002: istore 63
      //   2004: iload 63
      //   2006: istore 64
      //   2008: iload 64
      //   2010: ifgt +88 -> 2098
      //   2013: aload 5
      //   2015: ifnull +18 -> 2033
      //   2018: iload 6
      //   2020: ifeq +13 -> 2033
      //   2023: aload 5
      //   2025: invokevirtual 78	jp/co/asbit/pvstar/cache/Cache:completeCache	()V
      //   2028: aload 7
      //   2030: invokevirtual 81	jp/co/asbit/pvstar/cache/CacheManager:trimCache	()V
      //   2033: aload 35
      //   2035: ifnull +8 -> 2043
      //   2038: aload 35
      //   2040: invokevirtual 86	java/io/BufferedWriter:close	()V
      //   2043: aload 47
      //   2045: ifnull +8 -> 2053
      //   2048: aload 47
      //   2050: invokevirtual 89	java/io/BufferedInputStream:close	()V
      //   2053: aload 4
      //   2055: ifnull +8 -> 2063
      //   2058: aload 4
      //   2060: invokevirtual 90	java/net/Socket:close	()V
      //   2063: aload 20
      //   2065: ifnull +185 -> 2250
      //   2068: aload 20
      //   2070: invokevirtual 93	java/io/BufferedOutputStream:close	()V
      //   2073: goto -1963 -> 110
      //   2076: astore 66
      //   2078: aconst_null
      //   2079: astore 5
      //   2081: goto -152 -> 1929
      //   2084: astore 65
      //   2086: aconst_null
      //   2087: astore 5
      //   2089: goto -160 -> 1929
      //   2092: iconst_0
      //   2093: istore 6
      //   2095: goto -118 -> 1977
      //   2098: lconst_0
      //   2099: invokestatic 462	java/lang/Thread:sleep	(J)V
      //   2102: iload 59
      //   2104: iload 64
      //   2106: iadd
      //   2107: iload 49
      //   2109: if_icmple +10 -> 2119
      //   2112: iload 49
      //   2114: iload 59
      //   2116: isub
      //   2117: istore 64
      //   2119: iload 6
      //   2121: ifeq +14 -> 2135
      //   2124: aload 5
      //   2126: aload 58
      //   2128: iload 59
      //   2130: iload 64
      //   2132: invokevirtual 464	jp/co/asbit/pvstar/cache/Cache:write	([BII)V
      //   2135: iload 59
      //   2137: iload 64
      //   2139: iadd
      //   2140: istore 59
      //   2142: aload 20
      //   2144: aload 58
      //   2146: iconst_0
      //   2147: iload 64
      //   2149: invokevirtual 465	java/io/BufferedOutputStream:write	([BII)V
      //   2152: goto -164 -> 1988
      //   2155: ldc 65
      //   2157: new 98	java/lang/StringBuilder
      //   2160: dup
      //   2161: ldc_w 467
      //   2164: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   2167: aload 9
      //   2169: invokevirtual 173	java/lang/Exception:getStackTrace	()[Ljava/lang/StackTraceElement;
      //   2172: iload 10
      //   2174: aaload
      //   2175: invokevirtual 470	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   2178: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   2181: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   2184: pop
      //   2185: iinc 10 1
      //   2188: goto -1922 -> 266
      //   2191: astore 12
      //   2193: ldc 65
      //   2195: new 98	java/lang/StringBuilder
      //   2198: dup
      //   2199: ldc 100
      //   2201: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   2204: aload 12
      //   2206: invokevirtual 106	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   2209: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   2212: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   2215: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   2218: pop
      //   2219: goto -1729 -> 490
      //   2222: astore 60
      //   2224: ldc 65
      //   2226: new 98	java/lang/StringBuilder
      //   2229: dup
      //   2230: ldc 100
      //   2232: invokespecial 103	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   2235: aload 60
      //   2237: invokevirtual 106	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   2240: invokevirtual 110	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   2243: invokevirtual 111	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   2246: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   2249: pop
      //   2250: goto -2140 -> 110
      //   2253: astore 62
      //   2255: goto -222 -> 2033
      //   2258: astore 14
      //   2260: goto -1804 -> 456
      //   2263: astore 11
      //   2265: goto -1829 -> 436
      //   2268: astore 19
      //   2270: goto -1969 -> 301
      //   2273: astore 9
      //   2275: goto -2012 -> 263
      //   2278: astore 70
      //   2280: goto -1486 -> 794
      //   2283: astore 89
      //   2285: goto -2207 -> 78
      //   2288: astore 9
      //   2290: aload 35
      //   2292: astore_3
      //   2293: aload 20
      //   2295: astore_1
      //   2296: goto -2033 -> 263
      //   2299: astore 11
      //   2301: aload 35
      //   2303: astore_3
      //   2304: aload 20
      //   2306: astore_1
      //   2307: goto -1871 -> 436
      //   2310: astore 9
      //   2312: aload 35
      //   2314: astore_3
      //   2315: aload 47
      //   2317: astore_2
      //   2318: aload 20
      //   2320: astore_1
      //   2321: goto -2058 -> 263
      //   2324: astore 11
      //   2326: aload 35
      //   2328: astore_3
      //   2329: aload 47
      //   2331: astore_2
      //   2332: aload 20
      //   2334: astore_1
      //   2335: goto -1899 -> 436
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	2338	0	this	ConnectionThread
      //   1	2334	1	localObject1	java.lang.Object
      //   3	2329	2	localObject2	java.lang.Object
      //   5	2324	3	localObject3	java.lang.Object
      //   7	2052	4	localObject4	java.lang.Object
      //   10	2115	5	localObject5	java.lang.Object
      //   13	2107	6	i	int
      //   16	2013	7	localObject6	java.lang.Object
      //   29	3	8	localCacheManager	jp.co.asbit.pvstar.cache.CacheManager
      //   258	1910	9	localException1	java.lang.Exception
      //   2273	1	9	localException2	java.lang.Exception
      //   2288	1	9	localException3	java.lang.Exception
      //   2310	1	9	localException4	java.lang.Exception
      //   264	1922	10	j	int
      //   431	60	11	localObject7	java.lang.Object
      //   2263	1	11	localObject8	java.lang.Object
      //   2299	1	11	localObject9	java.lang.Object
      //   2324	1	11	localObject10	java.lang.Object
      //   2191	14	12	localException5	java.lang.Exception
      //   2258	1	14	localIOException1	IOException
      //   272	7	15	k	int
      //   338	14	17	localException6	java.lang.Exception
      //   2268	1	19	localIOException2	IOException
      //   166	2167	20	localBufferedOutputStream	java.io.BufferedOutputStream
      //   182	7	21	localInputStreamReader	java.io.InputStreamReader
      //   193	1079	22	localBufferedReader	java.io.BufferedReader
      //   205	556	23	arrayOfString1	String[]
      //   208	307	24	localLocalProxyUrl1	LocalProxyUrl
      //   231	139	25	localIterator	java.util.Iterator
      //   379	33	26	localNameValuePair	org.apache.http.NameValuePair
      //   407	17	27	localVideoDbHelper1	VideoDbHelper
      //   498	24	28	str1	String
      //   505	1086	29	str2	String
      //   512	1081	30	str3	String
      //   519	1398	31	str4	String
      //   526	449	32	localUri	android.net.Uri
      //   529	361	33	m	int
      //   894	3	34	localSocket	Socket
      //   588	1739	35	localBufferedWriter	java.io.BufferedWriter
      //   704	573	38	n	int
      //   711	519	39	str5	String
      //   1269	57	41	arrayOfChar	char[]
      //   1292	16	43	localStringBuilder	java.lang.StringBuilder
      //   1303	7	44	str6	String
      //   1330	9	46	str7	String
      //   735	1595	47	localBufferedInputStream	java.io.BufferedInputStream
      //   738	1247	48	i1	int
      //   742	1375	49	i2	int
      //   745	1201	50	str8	String
      //   753	1103	51	str9	String
      //   1736	31	53	localDate	java.util.Date
      //   1751	14	54	localSimpleDateFormat	java.text.SimpleDateFormat
      //   1771	15	55	str10	String
      //   1863	3	56	bool1	boolean
      //   770	3	57	bool2	boolean
      //   1982	163	58	arrayOfByte	byte[]
      //   1986	155	59	i3	int
      //   2222	14	60	localException7	java.lang.Exception
      //   2253	1	62	localIOException3	IOException
      //   2002	3	63	i4	int
      //   2006	142	64	i5	int
      //   2084	1	65	localCachingDisableException1	jp.co.asbit.pvstar.cache.CacheManager.CachingDisableException
      //   2076	1	66	localIOException4	IOException
      //   1923	3	67	localCache	jp.co.asbit.pvstar.cache.Cache
      //   1873	14	68	localException8	java.lang.Exception
      //   2278	1	70	localIOException5	IOException
      //   1568	17	71	localMatcher	java.util.regex.Matcher
      //   1597	18	72	localLocalProxyUrl2	LocalProxyUrl
      //   1610	11	73	localVideoDbHelper2	VideoDbHelper
      //   1629	22	75	arrayOfObject	java.lang.Object[]
      //   1655	15	76	str11	String
      //   1490	10	77	arrayOfString2	String[]
      //   1509	26	78	arrayOfString3	String[]
      //   1404	10	79	arrayOfString4	String[]
      //   1420	8	80	i6	int
      //   121	14	87	localException9	java.lang.Exception
      //   2283	1	89	localIOException6	IOException
      //   111	3	90	localCachingDisableException2	jp.co.asbit.pvstar.cache.CacheManager.CachingDisableException
      // Exception table:
      //   from	to	target	type
      //   18	31	111	jp/co/asbit/pvstar/cache/CacheManager$CachingDisableException
      //   69	78	121	java/lang/Exception
      //   82	110	121	java/lang/Exception
      //   168	258	258	java/lang/Exception
      //   369	428	258	java/lang/Exception
      //   493	590	258	java/lang/Exception
      //   837	896	258	java/lang/Exception
      //   291	301	338	java/lang/Exception
      //   305	335	338	java/lang/Exception
      //   168	258	431	finally
      //   369	428	431	finally
      //   493	590	431	finally
      //   837	896	431	finally
      //   785	794	1873	java/lang/Exception
      //   799	834	1873	java/lang/Exception
      //   1914	1925	2076	java/io/IOException
      //   1914	1925	2084	jp/co/asbit/pvstar/cache/CacheManager$CachingDisableException
      //   446	456	2191	java/lang/Exception
      //   460	490	2191	java/lang/Exception
      //   2023	2033	2222	java/lang/Exception
      //   2038	2073	2222	java/lang/Exception
      //   2023	2033	2253	java/io/IOException
      //   446	456	2258	java/io/IOException
      //   35	61	2263	finally
      //   152	168	2263	finally
      //   266	274	2263	finally
      //   2155	2185	2263	finally
      //   291	301	2268	java/io/IOException
      //   35	61	2273	java/lang/Exception
      //   152	168	2273	java/lang/Exception
      //   785	794	2278	java/io/IOException
      //   69	78	2283	java/io/IOException
      //   590	737	2288	java/lang/Exception
      //   903	1358	2288	java/lang/Exception
      //   590	737	2299	finally
      //   903	1358	2299	finally
      //   747	772	2310	java/lang/Exception
      //   1361	1865	2310	java/lang/Exception
      //   1914	1925	2310	java/lang/Exception
      //   1934	2004	2310	java/lang/Exception
      //   2098	2152	2310	java/lang/Exception
      //   747	772	2324	finally
      //   1361	1865	2324	finally
      //   1914	1925	2324	finally
      //   1934	2004	2324	finally
      //   2098	2152	2324	finally
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.ProxyServiceThread
 * JD-Core Version:    0.7.0.1
 */