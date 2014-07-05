package twitter4j.internal.http;

import java.io.IOException;
import java.io.Serializable;
import java.net.Authenticator;
import java.net.Authenticator.RequestorType;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import twitter4j.TwitterException;
import twitter4j.auth.Authorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
import twitter4j.internal.logging.Logger;
import twitter4j.internal.util.z_T4JInternalStringUtil;

public class HttpClientImpl
  extends HttpClientBase
  implements HttpResponseCode, Serializable
{
  private static final Map<HttpClientConfiguration, HttpClient> instanceMap = new HashMap(1);
  private static final Logger logger = Logger.getLogger(HttpClientImpl.class);
  private static final long serialVersionUID = -8819171414069621503L;
  
  static
  {
    if (ConfigurationContext.getInstance().isDalvik()) {
      System.setProperty("http.keepAlive", "false");
    }
  }
  
  public HttpClientImpl()
  {
    super(ConfigurationContext.getInstance());
  }
  
  public HttpClientImpl(HttpClientConfiguration paramHttpClientConfiguration)
  {
    super(paramHttpClientConfiguration);
  }
  
  public static HttpClient getInstance(HttpClientConfiguration paramHttpClientConfiguration)
  {
    Object localObject = (HttpClient)instanceMap.get(paramHttpClientConfiguration);
    if (localObject == null)
    {
      localObject = new HttpClientImpl(paramHttpClientConfiguration);
      instanceMap.put(paramHttpClientConfiguration, localObject);
    }
    return localObject;
  }
  
  private void setHeaders(HttpRequest paramHttpRequest, HttpURLConnection paramHttpURLConnection)
  {
    if (logger.isDebugEnabled())
    {
      logger.debug("Request: ");
      logger.debug(paramHttpRequest.getMethod().name() + " ", paramHttpRequest.getURL());
    }
    if (paramHttpRequest.getAuthorization() != null)
    {
      String str2 = paramHttpRequest.getAuthorization().getAuthorizationHeader(paramHttpRequest);
      if (str2 != null)
      {
        if (logger.isDebugEnabled()) {
          logger.debug("Authorization: ", z_T4JInternalStringUtil.maskString(str2));
        }
        paramHttpURLConnection.addRequestProperty("Authorization", str2);
      }
    }
    if (paramHttpRequest.getRequestHeaders() != null)
    {
      Iterator localIterator = paramHttpRequest.getRequestHeaders().keySet().iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        paramHttpURLConnection.addRequestProperty(str1, (String)paramHttpRequest.getRequestHeaders().get(str1));
        logger.debug(str1 + ": " + (String)paramHttpRequest.getRequestHeaders().get(str1));
      }
    }
  }
  
  public HttpResponse get(String paramString)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.GET, paramString, null, null, null));
  }
  
  protected HttpURLConnection getConnection(String paramString)
    throws IOException
  {
    Proxy localProxy;
    if (isProxyConfigured())
    {
      if ((this.CONF.getHttpProxyUser() != null) && (!this.CONF.getHttpProxyUser().equals("")))
      {
        if (logger.isDebugEnabled())
        {
          logger.debug("Proxy AuthUser: " + this.CONF.getHttpProxyUser());
          logger.debug("Proxy AuthPassword: " + z_T4JInternalStringUtil.maskString(this.CONF.getHttpProxyPassword()));
        }
        Authenticator.setDefault(new Authenticator()
        {
          protected PasswordAuthentication getPasswordAuthentication()
          {
            if (getRequestorType().equals(Authenticator.RequestorType.PROXY)) {}
            for (PasswordAuthentication localPasswordAuthentication = new PasswordAuthentication(HttpClientImpl.this.CONF.getHttpProxyUser(), HttpClientImpl.this.CONF.getHttpProxyPassword().toCharArray());; localPasswordAuthentication = null) {
              return localPasswordAuthentication;
            }
          }
        });
      }
      localProxy = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(this.CONF.getHttpProxyHost(), this.CONF.getHttpProxyPort()));
      if (logger.isDebugEnabled()) {
        logger.debug("Opening proxied connection(" + this.CONF.getHttpProxyHost() + ":" + this.CONF.getHttpProxyPort() + ")");
      }
    }
    for (HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection(localProxy);; localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection())
    {
      if (this.CONF.getHttpConnectionTimeout() > 0) {
        localHttpURLConnection.setConnectTimeout(this.CONF.getHttpConnectionTimeout());
      }
      if (this.CONF.getHttpReadTimeout() > 0) {
        localHttpURLConnection.setReadTimeout(this.CONF.getHttpReadTimeout());
      }
      localHttpURLConnection.setInstanceFollowRedirects(false);
      return localHttpURLConnection;
    }
  }
  
  public HttpResponse post(String paramString, HttpParameter[] paramArrayOfHttpParameter)
    throws TwitterException
  {
    return request(new HttpRequest(RequestMethod.POST, paramString, paramArrayOfHttpParameter, null, null));
  }
  
  /* Error */
  public HttpResponse request(HttpRequest paramHttpRequest)
    throws TwitterException
  {
    // Byte code:
    //   0: iconst_1
    //   1: aload_0
    //   2: getfield 195	twitter4j/internal/http/HttpClientImpl:CONF	Ltwitter4j/internal/http/HttpClientConfiguration;
    //   5: invokeinterface 294 1 0
    //   10: iadd
    //   11: istore_2
    //   12: iconst_0
    //   13: istore_3
    //   14: aconst_null
    //   15: astore 4
    //   17: iload_3
    //   18: iload_2
    //   19: if_icmpge +1065 -> 1084
    //   22: bipush 255
    //   24: istore 6
    //   26: aconst_null
    //   27: astore 7
    //   29: aload_0
    //   30: aload_1
    //   31: invokevirtual 115	twitter4j/internal/http/HttpRequest:getURL	()Ljava/lang/String;
    //   34: invokevirtual 296	twitter4j/internal/http/HttpClientImpl:getConnection	(Ljava/lang/String;)Ljava/net/HttpURLConnection;
    //   37: astore 15
    //   39: aload 15
    //   41: iconst_1
    //   42: invokevirtual 299	java/net/HttpURLConnection:setDoInput	(Z)V
    //   45: aload_0
    //   46: aload_1
    //   47: aload 15
    //   49: invokespecial 301	twitter4j/internal/http/HttpClientImpl:setHeaders	(Ltwitter4j/internal/http/HttpRequest;Ljava/net/HttpURLConnection;)V
    //   52: aload 15
    //   54: aload_1
    //   55: invokevirtual 97	twitter4j/internal/http/HttpRequest:getMethod	()Ltwitter4j/internal/http/RequestMethod;
    //   58: invokevirtual 103	twitter4j/internal/http/RequestMethod:name	()Ljava/lang/String;
    //   61: invokevirtual 304	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   64: aload_1
    //   65: invokevirtual 97	twitter4j/internal/http/HttpRequest:getMethod	()Ltwitter4j/internal/http/RequestMethod;
    //   68: getstatic 287	twitter4j/internal/http/RequestMethod:POST	Ltwitter4j/internal/http/RequestMethod;
    //   71: if_acmpne +572 -> 643
    //   74: aload_1
    //   75: invokevirtual 308	twitter4j/internal/http/HttpRequest:getParameters	()[Ltwitter4j/internal/http/HttpParameter;
    //   78: invokestatic 314	twitter4j/internal/http/HttpParameter:containsFile	([Ltwitter4j/internal/http/HttpParameter;)Z
    //   81: ifeq +734 -> 815
    //   84: new 89	java/lang/StringBuilder
    //   87: dup
    //   88: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   91: ldc_w 316
    //   94: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: invokestatic 320	java/lang/System:currentTimeMillis	()J
    //   100: invokevirtual 323	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   103: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   106: astore 28
    //   108: aload 15
    //   110: ldc_w 325
    //   113: new 89	java/lang/StringBuilder
    //   116: dup
    //   117: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   120: ldc_w 327
    //   123: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: aload 28
    //   128: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   134: invokevirtual 330	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   137: new 89	java/lang/StringBuilder
    //   140: dup
    //   141: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   144: ldc_w 332
    //   147: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   150: aload 28
    //   152: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   158: astore 29
    //   160: aload 15
    //   162: iconst_1
    //   163: invokevirtual 335	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   166: aload 15
    //   168: invokevirtual 339	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   171: astore 7
    //   173: new 341	java/io/DataOutputStream
    //   176: dup
    //   177: aload 7
    //   179: invokespecial 344	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   182: astore 30
    //   184: aload_1
    //   185: invokevirtual 308	twitter4j/internal/http/HttpRequest:getParameters	()[Ltwitter4j/internal/http/HttpParameter;
    //   188: astore 31
    //   190: aload 31
    //   192: arraylength
    //   193: istore 32
    //   195: iconst_0
    //   196: istore 33
    //   198: iload 33
    //   200: iload 32
    //   202: if_icmpge +395 -> 597
    //   205: aload 31
    //   207: iload 33
    //   209: aaload
    //   210: astore 34
    //   212: aload 34
    //   214: invokevirtual 347	twitter4j/internal/http/HttpParameter:isFile	()Z
    //   217: ifeq +269 -> 486
    //   220: aload_0
    //   221: aload 30
    //   223: new 89	java/lang/StringBuilder
    //   226: dup
    //   227: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   230: aload 29
    //   232: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   235: ldc_w 349
    //   238: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   241: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   244: invokevirtual 353	twitter4j/internal/http/HttpClientImpl:write	(Ljava/io/DataOutputStream;Ljava/lang/String;)V
    //   247: aload_0
    //   248: aload 30
    //   250: new 89	java/lang/StringBuilder
    //   253: dup
    //   254: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   257: ldc_w 355
    //   260: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   263: aload 34
    //   265: invokevirtual 358	twitter4j/internal/http/HttpParameter:getName	()Ljava/lang/String;
    //   268: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   271: ldc_w 360
    //   274: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   277: aload 34
    //   279: invokevirtual 364	twitter4j/internal/http/HttpParameter:getFile	()Ljava/io/File;
    //   282: invokevirtual 367	java/io/File:getName	()Ljava/lang/String;
    //   285: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   288: ldc_w 369
    //   291: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   294: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   297: invokevirtual 353	twitter4j/internal/http/HttpClientImpl:write	(Ljava/io/DataOutputStream;Ljava/lang/String;)V
    //   300: aload_0
    //   301: aload 30
    //   303: new 89	java/lang/StringBuilder
    //   306: dup
    //   307: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   310: ldc_w 371
    //   313: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   316: aload 34
    //   318: invokevirtual 374	twitter4j/internal/http/HttpParameter:getContentType	()Ljava/lang/String;
    //   321: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   324: ldc_w 376
    //   327: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   330: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   333: invokevirtual 353	twitter4j/internal/http/HttpClientImpl:write	(Ljava/io/DataOutputStream;Ljava/lang/String;)V
    //   336: aload 34
    //   338: invokevirtual 379	twitter4j/internal/http/HttpParameter:hasFileBody	()Z
    //   341: ifeq +111 -> 452
    //   344: aload 34
    //   346: invokevirtual 383	twitter4j/internal/http/HttpParameter:getFileBody	()Ljava/io/InputStream;
    //   349: astore 35
    //   351: new 385	java/io/BufferedInputStream
    //   354: dup
    //   355: aload 35
    //   357: invokespecial 388	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   360: astore 36
    //   362: sipush 1024
    //   365: newarray byte
    //   367: astore 37
    //   369: aload 36
    //   371: aload 37
    //   373: invokevirtual 392	java/io/BufferedInputStream:read	([B)I
    //   376: istore 38
    //   378: iload 38
    //   380: bipush 255
    //   382: if_icmpeq +87 -> 469
    //   385: aload 30
    //   387: aload 37
    //   389: iconst_0
    //   390: iload 38
    //   392: invokevirtual 395	java/io/DataOutputStream:write	([BII)V
    //   395: goto -26 -> 369
    //   398: astore 8
    //   400: aload 4
    //   402: astore 5
    //   404: aload 7
    //   406: invokevirtual 400	java/io/OutputStream:close	()V
    //   409: aload 8
    //   411: athrow
    //   412: astore 10
    //   414: aload_0
    //   415: getfield 195	twitter4j/internal/http/HttpClientImpl:CONF	Ltwitter4j/internal/http/HttpClientConfiguration;
    //   418: invokeinterface 294 1 0
    //   423: istore 11
    //   425: iload_3
    //   426: iload 11
    //   428: if_icmpne +549 -> 977
    //   431: new 173	twitter4j/TwitterException
    //   434: dup
    //   435: aload 10
    //   437: invokevirtual 403	java/io/IOException:getMessage	()Ljava/lang/String;
    //   440: aload 10
    //   442: iload 6
    //   444: invokespecial 406	twitter4j/TwitterException:<init>	(Ljava/lang/String;Ljava/lang/Exception;I)V
    //   447: astore 12
    //   449: aload 12
    //   451: athrow
    //   452: new 408	java/io/FileInputStream
    //   455: dup
    //   456: aload 34
    //   458: invokevirtual 364	twitter4j/internal/http/HttpParameter:getFile	()Ljava/io/File;
    //   461: invokespecial 411	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   464: astore 35
    //   466: goto -115 -> 351
    //   469: aload_0
    //   470: aload 30
    //   472: ldc_w 349
    //   475: invokevirtual 353	twitter4j/internal/http/HttpClientImpl:write	(Ljava/io/DataOutputStream;Ljava/lang/String;)V
    //   478: aload 36
    //   480: invokevirtual 412	java/io/BufferedInputStream:close	()V
    //   483: goto +608 -> 1091
    //   486: aload_0
    //   487: aload 30
    //   489: new 89	java/lang/StringBuilder
    //   492: dup
    //   493: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   496: aload 29
    //   498: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   501: ldc_w 349
    //   504: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   507: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   510: invokevirtual 353	twitter4j/internal/http/HttpClientImpl:write	(Ljava/io/DataOutputStream;Ljava/lang/String;)V
    //   513: aload_0
    //   514: aload 30
    //   516: new 89	java/lang/StringBuilder
    //   519: dup
    //   520: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   523: ldc_w 355
    //   526: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   529: aload 34
    //   531: invokevirtual 358	twitter4j/internal/http/HttpParameter:getName	()Ljava/lang/String;
    //   534: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   537: ldc_w 369
    //   540: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   543: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   546: invokevirtual 353	twitter4j/internal/http/HttpClientImpl:write	(Ljava/io/DataOutputStream;Ljava/lang/String;)V
    //   549: aload_0
    //   550: aload 30
    //   552: ldc_w 414
    //   555: invokevirtual 353	twitter4j/internal/http/HttpClientImpl:write	(Ljava/io/DataOutputStream;Ljava/lang/String;)V
    //   558: getstatic 29	twitter4j/internal/http/HttpClientImpl:logger	Ltwitter4j/internal/logging/Logger;
    //   561: aload 34
    //   563: invokevirtual 417	twitter4j/internal/http/HttpParameter:getValue	()Ljava/lang/String;
    //   566: invokevirtual 87	twitter4j/internal/logging/Logger:debug	(Ljava/lang/String;)V
    //   569: aload 30
    //   571: aload 34
    //   573: invokevirtual 417	twitter4j/internal/http/HttpParameter:getValue	()Ljava/lang/String;
    //   576: ldc_w 419
    //   579: invokevirtual 423	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   582: invokevirtual 426	java/io/DataOutputStream:write	([B)V
    //   585: aload_0
    //   586: aload 30
    //   588: ldc_w 349
    //   591: invokevirtual 353	twitter4j/internal/http/HttpClientImpl:write	(Ljava/io/DataOutputStream;Ljava/lang/String;)V
    //   594: goto +497 -> 1091
    //   597: aload_0
    //   598: aload 30
    //   600: new 89	java/lang/StringBuilder
    //   603: dup
    //   604: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   607: aload 29
    //   609: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   612: ldc_w 428
    //   615: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   618: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   621: invokevirtual 353	twitter4j/internal/http/HttpClientImpl:write	(Ljava/io/DataOutputStream;Ljava/lang/String;)V
    //   624: aload_0
    //   625: aload 30
    //   627: ldc_w 349
    //   630: invokevirtual 353	twitter4j/internal/http/HttpClientImpl:write	(Ljava/io/DataOutputStream;Ljava/lang/String;)V
    //   633: aload 7
    //   635: invokevirtual 431	java/io/OutputStream:flush	()V
    //   638: aload 7
    //   640: invokevirtual 400	java/io/OutputStream:close	()V
    //   643: aload_0
    //   644: getfield 195	twitter4j/internal/http/HttpClientImpl:CONF	Ltwitter4j/internal/http/HttpClientConfiguration;
    //   647: astore 16
    //   649: new 433	twitter4j/internal/http/HttpResponseImpl
    //   652: dup
    //   653: aload 15
    //   655: aload 16
    //   657: invokespecial 436	twitter4j/internal/http/HttpResponseImpl:<init>	(Ljava/net/HttpURLConnection;Ltwitter4j/internal/http/HttpClientConfiguration;)V
    //   660: astore 5
    //   662: aload 15
    //   664: invokevirtual 439	java/net/HttpURLConnection:getResponseCode	()I
    //   667: istore 6
    //   669: getstatic 29	twitter4j/internal/http/HttpClientImpl:logger	Ltwitter4j/internal/logging/Logger;
    //   672: invokevirtual 81	twitter4j/internal/logging/Logger:isDebugEnabled	()Z
    //   675: ifeq +422 -> 1097
    //   678: getstatic 29	twitter4j/internal/http/HttpClientImpl:logger	Ltwitter4j/internal/logging/Logger;
    //   681: ldc_w 441
    //   684: invokevirtual 87	twitter4j/internal/logging/Logger:debug	(Ljava/lang/String;)V
    //   687: aload 15
    //   689: invokevirtual 444	java/net/HttpURLConnection:getHeaderFields	()Ljava/util/Map;
    //   692: astore 21
    //   694: aload 21
    //   696: invokeinterface 151 1 0
    //   701: invokeinterface 157 1 0
    //   706: astore 22
    //   708: aload 22
    //   710: invokeinterface 162 1 0
    //   715: ifeq +382 -> 1097
    //   718: aload 22
    //   720: invokeinterface 166 1 0
    //   725: checkcast 168	java/lang/String
    //   728: astore 23
    //   730: aload 21
    //   732: aload 23
    //   734: invokeinterface 69 2 0
    //   739: checkcast 446	java/util/List
    //   742: invokeinterface 447 1 0
    //   747: astore 24
    //   749: aload 24
    //   751: invokeinterface 162 1 0
    //   756: ifeq -48 -> 708
    //   759: aload 24
    //   761: invokeinterface 166 1 0
    //   766: checkcast 168	java/lang/String
    //   769: astore 25
    //   771: aload 23
    //   773: ifnull +120 -> 893
    //   776: getstatic 29	twitter4j/internal/http/HttpClientImpl:logger	Ltwitter4j/internal/logging/Logger;
    //   779: new 89	java/lang/StringBuilder
    //   782: dup
    //   783: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   786: aload 23
    //   788: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   791: ldc 170
    //   793: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   796: aload 25
    //   798: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   801: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   804: invokevirtual 87	twitter4j/internal/logging/Logger:debug	(Ljava/lang/String;)V
    //   807: goto -58 -> 749
    //   810: astore 8
    //   812: goto -408 -> 404
    //   815: aload 15
    //   817: ldc_w 325
    //   820: ldc_w 449
    //   823: invokevirtual 330	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   826: aload_1
    //   827: invokevirtual 308	twitter4j/internal/http/HttpRequest:getParameters	()[Ltwitter4j/internal/http/HttpParameter;
    //   830: invokestatic 453	twitter4j/internal/http/HttpParameter:encodeParameters	([Ltwitter4j/internal/http/HttpParameter;)Ljava/lang/String;
    //   833: astore 26
    //   835: getstatic 29	twitter4j/internal/http/HttpClientImpl:logger	Ltwitter4j/internal/logging/Logger;
    //   838: ldc_w 455
    //   841: aload 26
    //   843: invokevirtual 118	twitter4j/internal/logging/Logger:debug	(Ljava/lang/String;Ljava/lang/String;)V
    //   846: aload 26
    //   848: ldc_w 419
    //   851: invokevirtual 423	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   854: astore 27
    //   856: aload 15
    //   858: ldc_w 457
    //   861: aload 27
    //   863: arraylength
    //   864: invokestatic 462	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   867: invokevirtual 330	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   870: aload 15
    //   872: iconst_1
    //   873: invokevirtual 335	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   876: aload 15
    //   878: invokevirtual 339	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   881: astore 7
    //   883: aload 7
    //   885: aload 27
    //   887: invokevirtual 463	java/io/OutputStream:write	([B)V
    //   890: goto -257 -> 633
    //   893: getstatic 29	twitter4j/internal/http/HttpClientImpl:logger	Ltwitter4j/internal/logging/Logger;
    //   896: aload 25
    //   898: invokevirtual 87	twitter4j/internal/logging/Logger:debug	(Ljava/lang/String;)V
    //   901: goto -152 -> 749
    //   904: iload 6
    //   906: sipush 420
    //   909: if_icmpeq +36 -> 945
    //   912: iload 6
    //   914: sipush 400
    //   917: if_icmpeq +28 -> 945
    //   920: iload 6
    //   922: sipush 500
    //   925: if_icmplt +20 -> 945
    //   928: aload_0
    //   929: getfield 195	twitter4j/internal/http/HttpClientImpl:CONF	Ltwitter4j/internal/http/HttpClientConfiguration;
    //   932: invokeinterface 294 1 0
    //   937: istore 18
    //   939: iload_3
    //   940: iload 18
    //   942: if_icmpne +30 -> 972
    //   945: new 173	twitter4j/TwitterException
    //   948: dup
    //   949: aload 5
    //   951: invokevirtual 468	twitter4j/internal/http/HttpResponse:asString	()Ljava/lang/String;
    //   954: aload 5
    //   956: invokespecial 471	twitter4j/TwitterException:<init>	(Ljava/lang/String;Ltwitter4j/internal/http/HttpResponse;)V
    //   959: astore 17
    //   961: aload 17
    //   963: athrow
    //   964: aload 7
    //   966: invokevirtual 400	java/io/OutputStream:close	()V
    //   969: aload 5
    //   971: areturn
    //   972: aload 7
    //   974: invokevirtual 400	java/io/OutputStream:close	()V
    //   977: getstatic 29	twitter4j/internal/http/HttpClientImpl:logger	Ltwitter4j/internal/logging/Logger;
    //   980: invokevirtual 81	twitter4j/internal/logging/Logger:isDebugEnabled	()Z
    //   983: ifeq +14 -> 997
    //   986: aload 5
    //   988: ifnull +9 -> 997
    //   991: aload 5
    //   993: invokevirtual 468	twitter4j/internal/http/HttpResponse:asString	()Ljava/lang/String;
    //   996: pop
    //   997: getstatic 29	twitter4j/internal/http/HttpClientImpl:logger	Ltwitter4j/internal/logging/Logger;
    //   1000: new 89	java/lang/StringBuilder
    //   1003: dup
    //   1004: invokespecial 91	java/lang/StringBuilder:<init>	()V
    //   1007: ldc_w 473
    //   1010: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1013: aload_0
    //   1014: getfield 195	twitter4j/internal/http/HttpClientImpl:CONF	Ltwitter4j/internal/http/HttpClientConfiguration;
    //   1017: invokeinterface 476 1 0
    //   1022: invokevirtual 253	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1025: ldc_w 478
    //   1028: invokevirtual 107	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1031: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1034: invokevirtual 87	twitter4j/internal/logging/Logger:debug	(Ljava/lang/String;)V
    //   1037: sipush 1000
    //   1040: aload_0
    //   1041: getfield 195	twitter4j/internal/http/HttpClientImpl:CONF	Ltwitter4j/internal/http/HttpClientConfiguration;
    //   1044: invokeinterface 476 1 0
    //   1049: imul
    //   1050: i2l
    //   1051: invokestatic 484	java/lang/Thread:sleep	(J)V
    //   1054: iinc 3 1
    //   1057: aload 5
    //   1059: astore 4
    //   1061: goto -1044 -> 17
    //   1064: astore 20
    //   1066: goto -97 -> 969
    //   1069: astore 19
    //   1071: goto -94 -> 977
    //   1074: astore 9
    //   1076: goto -667 -> 409
    //   1079: astore 13
    //   1081: goto -27 -> 1054
    //   1084: aload 4
    //   1086: astore 5
    //   1088: goto -119 -> 969
    //   1091: iinc 33 1
    //   1094: goto -896 -> 198
    //   1097: iload 6
    //   1099: sipush 200
    //   1102: if_icmplt -198 -> 904
    //   1105: iload 6
    //   1107: sipush 302
    //   1110: if_icmpeq -146 -> 964
    //   1113: sipush 300
    //   1116: iload 6
    //   1118: if_icmpgt -154 -> 964
    //   1121: goto -217 -> 904
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1124	0	this	HttpClientImpl
    //   0	1124	1	paramHttpRequest	HttpRequest
    //   11	9	2	i	int
    //   13	1042	3	j	int
    //   15	1070	4	localObject1	Object
    //   402	685	5	localObject2	Object
    //   24	1095	6	k	int
    //   27	946	7	localOutputStream	java.io.OutputStream
    //   398	12	8	localObject3	Object
    //   810	1	8	localObject4	Object
    //   1074	1	9	localException1	java.lang.Exception
    //   412	29	10	localIOException	IOException
    //   423	6	11	m	int
    //   447	3	12	localTwitterException1	TwitterException
    //   1079	1	13	localInterruptedException	java.lang.InterruptedException
    //   37	840	15	localHttpURLConnection	HttpURLConnection
    //   647	9	16	localHttpClientConfiguration	HttpClientConfiguration
    //   959	3	17	localTwitterException2	TwitterException
    //   937	6	18	n	int
    //   1069	1	19	localException2	java.lang.Exception
    //   1064	1	20	localException3	java.lang.Exception
    //   692	39	21	localMap	Map
    //   706	13	22	localIterator1	Iterator
    //   728	59	23	str1	String
    //   747	13	24	localIterator2	Iterator
    //   769	128	25	str2	String
    //   833	14	26	str3	String
    //   854	32	27	arrayOfByte1	byte[]
    //   106	45	28	str4	String
    //   158	450	29	str5	String
    //   182	444	30	localDataOutputStream	java.io.DataOutputStream
    //   188	18	31	arrayOfHttpParameter	HttpParameter[]
    //   193	10	32	i1	int
    //   196	896	33	i2	int
    //   210	362	34	localHttpParameter	HttpParameter
    //   349	116	35	localObject5	Object
    //   360	119	36	localBufferedInputStream	java.io.BufferedInputStream
    //   367	21	37	arrayOfByte2	byte[]
    //   376	15	38	i3	int
    // Exception table:
    //   from	to	target	type
    //   29	395	398	finally
    //   452	662	398	finally
    //   815	890	398	finally
    //   404	409	412	java/io/IOException
    //   409	412	412	java/io/IOException
    //   964	969	412	java/io/IOException
    //   972	977	412	java/io/IOException
    //   662	807	810	finally
    //   893	964	810	finally
    //   964	969	1064	java/lang/Exception
    //   972	977	1069	java/lang/Exception
    //   404	409	1074	java/lang/Exception
    //   977	1054	1079	java/lang/InterruptedException
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.http.HttpClientImpl
 * JD-Core Version:    0.7.0.1
 */