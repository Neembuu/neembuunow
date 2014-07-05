package twitter4j.auth;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpClientWrapper;
import twitter4j.internal.http.HttpParameter;
import twitter4j.internal.http.HttpRequest;
import twitter4j.internal.http.RequestMethod;
import twitter4j.internal.logging.Logger;
import twitter4j.internal.util.z_T4JInternalStringUtil;

public class OAuthAuthorization
  implements Authorization, Serializable, OAuthSupport
{
  private static final String HMAC_SHA1 = "HmacSHA1";
  private static final HttpParameter OAUTH_SIGNATURE_METHOD = new HttpParameter("oauth_signature_method", "HMAC-SHA1");
  private static Random RAND = new Random();
  private static transient HttpClientWrapper http;
  private static final Logger logger = Logger.getLogger(OAuthAuthorization.class);
  private static final long serialVersionUID = -4368426677157998618L;
  private final Configuration conf;
  private String consumerKey = "";
  private String consumerSecret;
  private OAuthToken oauthToken = null;
  private String realm = null;
  
  public OAuthAuthorization(Configuration paramConfiguration)
  {
    this.conf = paramConfiguration;
    http = new HttpClientWrapper(paramConfiguration);
    setOAuthConsumer(paramConfiguration.getOAuthConsumerKey(), paramConfiguration.getOAuthConsumerSecret());
    if ((paramConfiguration.getOAuthAccessToken() != null) && (paramConfiguration.getOAuthAccessTokenSecret() != null)) {
      setOAuthAccessToken(new AccessToken(paramConfiguration.getOAuthAccessToken(), paramConfiguration.getOAuthAccessTokenSecret()));
    }
  }
  
  static String constructRequestURL(String paramString)
  {
    int i = paramString.indexOf("?");
    if (-1 != i) {
      paramString = paramString.substring(0, i);
    }
    int j = paramString.indexOf("/", 8);
    String str = paramString.substring(0, j).toLowerCase();
    int k = str.indexOf(":", 8);
    if (-1 != k) {
      if ((!str.startsWith("http://")) || (!str.endsWith(":80"))) {
        break label105;
      }
    }
    for (str = str.substring(0, k);; str = str.substring(0, k)) {
      label105:
      do
      {
        return str + paramString.substring(j);
      } while ((!str.startsWith("https://")) || (!str.endsWith(":443")));
    }
  }
  
  public static String encodeParameters(List<HttpParameter> paramList)
  {
    return encodeParameters(paramList, "&", false);
  }
  
  public static String encodeParameters(List<HttpParameter> paramList, String paramString, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      HttpParameter localHttpParameter = (HttpParameter)localIterator.next();
      if (!localHttpParameter.isFile())
      {
        if (localStringBuilder.length() != 0)
        {
          if (paramBoolean) {
            localStringBuilder.append("\"");
          }
          localStringBuilder.append(paramString);
        }
        localStringBuilder.append(HttpParameter.encode(localHttpParameter.getName())).append("=");
        if (paramBoolean) {
          localStringBuilder.append("\"");
        }
        localStringBuilder.append(HttpParameter.encode(localHttpParameter.getValue()));
      }
    }
    if ((localStringBuilder.length() != 0) && (paramBoolean)) {
      localStringBuilder.append("\"");
    }
    return localStringBuilder.toString();
  }
  
  private void ensureTokenIsAvailable()
  {
    if (this.oauthToken == null) {
      throw new IllegalStateException("No Token available.");
    }
  }
  
  static String normalizeAuthorizationHeaders(List<HttpParameter> paramList)
  {
    Collections.sort(paramList);
    return encodeParameters(paramList);
  }
  
  static String normalizeRequestParameters(List<HttpParameter> paramList)
  {
    Collections.sort(paramList);
    return encodeParameters(paramList);
  }
  
  static String normalizeRequestParameters(HttpParameter[] paramArrayOfHttpParameter)
  {
    return normalizeRequestParameters(toParamList(paramArrayOfHttpParameter));
  }
  
  private void parseGetParameters(String paramString, List<HttpParameter> paramList)
  {
    int i = paramString.indexOf("?");
    String[] arrayOfString1;
    if (-1 != i) {
      arrayOfString1 = z_T4JInternalStringUtil.split(paramString.substring(i + 1), "&");
    }
    for (;;)
    {
      int k;
      try
      {
        int j = arrayOfString1.length;
        k = 0;
        if (k < j)
        {
          String[] arrayOfString2 = z_T4JInternalStringUtil.split(arrayOfString1[k], "=");
          if (arrayOfString2.length == 2) {
            paramList.add(new HttpParameter(URLDecoder.decode(arrayOfString2[0], "UTF-8"), URLDecoder.decode(arrayOfString2[1], "UTF-8")));
          } else {
            paramList.add(new HttpParameter(URLDecoder.decode(arrayOfString2[0], "UTF-8"), ""));
          }
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException) {}
      return;
      k++;
    }
  }
  
  static List<HttpParameter> toParamList(HttpParameter[] paramArrayOfHttpParameter)
  {
    ArrayList localArrayList = new ArrayList(paramArrayOfHttpParameter.length);
    localArrayList.addAll(Arrays.asList(paramArrayOfHttpParameter));
    return localArrayList;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    OAuthAuthorization localOAuthAuthorization;
    do
    {
      for (;;)
      {
        return bool;
        if ((paramObject instanceof OAuthSupport)) {
          break;
        }
        bool = false;
      }
      localOAuthAuthorization = (OAuthAuthorization)paramObject;
      if (this.consumerKey != null)
      {
        if (this.consumerKey.equals(localOAuthAuthorization.consumerKey)) {}
      }
      else {
        while (localOAuthAuthorization.consumerKey != null)
        {
          bool = false;
          break;
        }
      }
      if (this.consumerSecret != null)
      {
        if (this.consumerSecret.equals(localOAuthAuthorization.consumerSecret)) {}
      }
      else {
        while (localOAuthAuthorization.consumerSecret != null)
        {
          bool = false;
          break;
        }
      }
      if (this.oauthToken == null) {
        break;
      }
    } while (this.oauthToken.equals(localOAuthAuthorization.oauthToken));
    for (;;)
    {
      bool = false;
      break;
      if (localOAuthAuthorization.oauthToken == null) {
        break;
      }
    }
  }
  
  String generateAuthorizationHeader(String paramString1, String paramString2, HttpParameter[] paramArrayOfHttpParameter, String paramString3, String paramString4, OAuthToken paramOAuthToken)
  {
    if (paramArrayOfHttpParameter == null) {
      paramArrayOfHttpParameter = new HttpParameter[0];
    }
    ArrayList localArrayList1 = new ArrayList(5);
    localArrayList1.add(new HttpParameter("oauth_consumer_key", this.consumerKey));
    localArrayList1.add(OAUTH_SIGNATURE_METHOD);
    localArrayList1.add(new HttpParameter("oauth_timestamp", paramString4));
    localArrayList1.add(new HttpParameter("oauth_nonce", paramString3));
    localArrayList1.add(new HttpParameter("oauth_version", "1.0"));
    if (paramOAuthToken != null) {
      localArrayList1.add(new HttpParameter("oauth_token", paramOAuthToken.getToken()));
    }
    ArrayList localArrayList2 = new ArrayList(localArrayList1.size() + paramArrayOfHttpParameter.length);
    localArrayList2.addAll(localArrayList1);
    if (!HttpParameter.containsFile(paramArrayOfHttpParameter)) {
      localArrayList2.addAll(toParamList(paramArrayOfHttpParameter));
    }
    parseGetParameters(paramString2, localArrayList2);
    StringBuilder localStringBuilder = new StringBuilder(paramString1).append("&").append(HttpParameter.encode(constructRequestURL(paramString2))).append("&");
    localStringBuilder.append(HttpParameter.encode(normalizeRequestParameters(localArrayList2)));
    String str1 = localStringBuilder.toString();
    logger.debug("OAuth base string: ", str1);
    String str2 = generateSignature(str1, paramOAuthToken);
    logger.debug("OAuth signature: ", str2);
    localArrayList1.add(new HttpParameter("oauth_signature", str2));
    if (this.realm != null) {
      localArrayList1.add(new HttpParameter("realm", this.realm));
    }
    return "OAuth " + encodeParameters(localArrayList1, ",", true);
  }
  
  String generateAuthorizationHeader(String paramString1, String paramString2, HttpParameter[] paramArrayOfHttpParameter, OAuthToken paramOAuthToken)
  {
    long l = System.currentTimeMillis() / 1000L;
    return generateAuthorizationHeader(paramString1, paramString2, paramArrayOfHttpParameter, String.valueOf(l + RAND.nextInt()), String.valueOf(l), paramOAuthToken);
  }
  
  public List<HttpParameter> generateOAuthSignatureHttpParams(String paramString1, String paramString2)
  {
    long l1 = System.currentTimeMillis() / 1000L;
    long l2 = l1 + RAND.nextInt();
    ArrayList localArrayList1 = new ArrayList(5);
    localArrayList1.add(new HttpParameter("oauth_consumer_key", this.consumerKey));
    localArrayList1.add(OAUTH_SIGNATURE_METHOD);
    localArrayList1.add(new HttpParameter("oauth_timestamp", l1));
    localArrayList1.add(new HttpParameter("oauth_nonce", l2));
    localArrayList1.add(new HttpParameter("oauth_version", "1.0"));
    if (this.oauthToken != null) {
      localArrayList1.add(new HttpParameter("oauth_token", this.oauthToken.getToken()));
    }
    ArrayList localArrayList2 = new ArrayList(localArrayList1.size());
    localArrayList2.addAll(localArrayList1);
    parseGetParameters(paramString2, localArrayList2);
    StringBuilder localStringBuilder = new StringBuilder(paramString1).append("&").append(HttpParameter.encode(constructRequestURL(paramString2))).append("&");
    localStringBuilder.append(HttpParameter.encode(normalizeRequestParameters(localArrayList2)));
    localArrayList1.add(new HttpParameter("oauth_signature", generateSignature(localStringBuilder.toString(), this.oauthToken)));
    return localArrayList1;
  }
  
  String generateSignature(String paramString)
  {
    return generateSignature(paramString, null);
  }
  
  /* Error */
  String generateSignature(String paramString, OAuthToken paramOAuthToken)
  {
    // Byte code:
    //   0: ldc 14
    //   2: invokestatic 350	javax/crypto/Mac:getInstance	(Ljava/lang/String;)Ljavax/crypto/Mac;
    //   5: astore 5
    //   7: aload_2
    //   8: ifnonnull +66 -> 74
    //   11: new 352	javax/crypto/spec/SecretKeySpec
    //   14: dup
    //   15: new 142	java/lang/StringBuilder
    //   18: dup
    //   19: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   22: aload_0
    //   23: getfield 267	twitter4j/auth/OAuthAuthorization:consumerSecret	Ljava/lang/String;
    //   26: invokestatic 195	twitter4j/internal/http/HttpParameter:encode	(Ljava/lang/String;)Ljava/lang/String;
    //   29: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   32: ldc 161
    //   34: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   37: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   40: invokevirtual 356	java/lang/String:getBytes	()[B
    //   43: ldc 14
    //   45: invokespecial 359	javax/crypto/spec/SecretKeySpec:<init>	([BLjava/lang/String;)V
    //   48: astore 6
    //   50: aload 5
    //   52: aload 6
    //   54: invokevirtual 363	javax/crypto/Mac:init	(Ljava/security/Key;)V
    //   57: aload 5
    //   59: aload_1
    //   60: invokevirtual 356	java/lang/String:getBytes	()[B
    //   63: invokevirtual 367	javax/crypto/Mac:doFinal	([B)[B
    //   66: astore 7
    //   68: aload 7
    //   70: invokestatic 372	twitter4j/internal/http/BASE64Encoder:encode	([B)Ljava/lang/String;
    //   73: areturn
    //   74: aload_2
    //   75: invokevirtual 376	twitter4j/auth/OAuthToken:getSecretKeySpec	()Ljavax/crypto/spec/SecretKeySpec;
    //   78: astore 6
    //   80: aload 6
    //   82: ifnonnull -32 -> 50
    //   85: new 352	javax/crypto/spec/SecretKeySpec
    //   88: dup
    //   89: new 142	java/lang/StringBuilder
    //   92: dup
    //   93: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   96: aload_0
    //   97: getfield 267	twitter4j/auth/OAuthAuthorization:consumerSecret	Ljava/lang/String;
    //   100: invokestatic 195	twitter4j/internal/http/HttpParameter:encode	(Ljava/lang/String;)Ljava/lang/String;
    //   103: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   106: ldc 161
    //   108: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: aload_2
    //   112: invokevirtual 379	twitter4j/auth/OAuthToken:getTokenSecret	()Ljava/lang/String;
    //   115: invokestatic 195	twitter4j/internal/http/HttpParameter:encode	(Ljava/lang/String;)Ljava/lang/String;
    //   118: invokevirtual 147	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: invokevirtual 153	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   124: invokevirtual 356	java/lang/String:getBytes	()[B
    //   127: ldc 14
    //   129: invokespecial 359	javax/crypto/spec/SecretKeySpec:<init>	([BLjava/lang/String;)V
    //   132: astore 6
    //   134: aload_2
    //   135: aload 6
    //   137: invokevirtual 383	twitter4j/auth/OAuthToken:setSecretKeySpec	(Ljavax/crypto/spec/SecretKeySpec;)V
    //   140: goto -90 -> 50
    //   143: astore 4
    //   145: getstatic 55	twitter4j/auth/OAuthAuthorization:logger	Ltwitter4j/internal/logging/Logger;
    //   148: ldc_w 385
    //   151: aload 4
    //   153: invokevirtual 389	twitter4j/internal/logging/Logger:error	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   156: new 391	java/lang/AssertionError
    //   159: dup
    //   160: aload 4
    //   162: invokespecial 394	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   165: athrow
    //   166: astore_3
    //   167: getstatic 55	twitter4j/auth/OAuthAuthorization:logger	Ltwitter4j/internal/logging/Logger;
    //   170: ldc_w 396
    //   173: aload_3
    //   174: invokevirtual 389	twitter4j/internal/logging/Logger:error	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   177: new 391	java/lang/AssertionError
    //   180: dup
    //   181: aload_3
    //   182: invokespecial 394	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   185: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	186	0	this	OAuthAuthorization
    //   0	186	1	paramString	String
    //   0	186	2	paramOAuthToken	OAuthToken
    //   166	16	3	localNoSuchAlgorithmException	java.security.NoSuchAlgorithmException
    //   143	18	4	localInvalidKeyException	java.security.InvalidKeyException
    //   5	53	5	localMac	javax.crypto.Mac
    //   48	88	6	localSecretKeySpec	javax.crypto.spec.SecretKeySpec
    //   66	3	7	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   0	68	143	java/security/InvalidKeyException
    //   74	140	143	java/security/InvalidKeyException
    //   0	68	166	java/security/NoSuchAlgorithmException
    //   74	140	166	java/security/NoSuchAlgorithmException
  }
  
  public String getAuthorizationHeader(HttpRequest paramHttpRequest)
  {
    return generateAuthorizationHeader(paramHttpRequest.getMethod().name(), paramHttpRequest.getURL(), paramHttpRequest.getParameters(), this.oauthToken);
  }
  
  public AccessToken getOAuthAccessToken()
    throws TwitterException
  {
    ensureTokenIsAvailable();
    if ((this.oauthToken instanceof AccessToken)) {}
    for (AccessToken localAccessToken = (AccessToken)this.oauthToken;; localAccessToken = (AccessToken)this.oauthToken)
    {
      return localAccessToken;
      this.oauthToken = new AccessToken(http.post(this.conf.getOAuthAccessTokenURL(), this));
    }
  }
  
  public AccessToken getOAuthAccessToken(String paramString)
    throws TwitterException
  {
    ensureTokenIsAvailable();
    HttpClientWrapper localHttpClientWrapper = http;
    String str = this.conf.getOAuthAccessTokenURL();
    HttpParameter[] arrayOfHttpParameter = new HttpParameter[1];
    arrayOfHttpParameter[0] = new HttpParameter("oauth_verifier", paramString);
    this.oauthToken = new AccessToken(localHttpClientWrapper.post(str, arrayOfHttpParameter, this));
    return (AccessToken)this.oauthToken;
  }
  
  public AccessToken getOAuthAccessToken(String paramString1, String paramString2)
    throws TwitterException
  {
    try
    {
      String str = this.conf.getOAuthAccessTokenURL();
      if (str.indexOf("http://") == 0) {
        str = "https://" + str.substring(7);
      }
      HttpClientWrapper localHttpClientWrapper = http;
      HttpParameter[] arrayOfHttpParameter = new HttpParameter[3];
      arrayOfHttpParameter[0] = new HttpParameter("x_auth_username", paramString1);
      arrayOfHttpParameter[1] = new HttpParameter("x_auth_password", paramString2);
      arrayOfHttpParameter[2] = new HttpParameter("x_auth_mode", "client_auth");
      this.oauthToken = new AccessToken(localHttpClientWrapper.post(str, arrayOfHttpParameter, this));
      AccessToken localAccessToken = (AccessToken)this.oauthToken;
      return localAccessToken;
    }
    catch (TwitterException localTwitterException)
    {
      throw new TwitterException("The screen name / password combination seems to be invalid.", localTwitterException, localTwitterException.getStatusCode());
    }
  }
  
  public AccessToken getOAuthAccessToken(RequestToken paramRequestToken)
    throws TwitterException
  {
    this.oauthToken = paramRequestToken;
    return getOAuthAccessToken();
  }
  
  public AccessToken getOAuthAccessToken(RequestToken paramRequestToken, String paramString)
    throws TwitterException
  {
    this.oauthToken = paramRequestToken;
    return getOAuthAccessToken(paramString);
  }
  
  public RequestToken getOAuthRequestToken()
    throws TwitterException
  {
    return getOAuthRequestToken(null, null);
  }
  
  public RequestToken getOAuthRequestToken(String paramString)
    throws TwitterException
  {
    return getOAuthRequestToken(paramString, null);
  }
  
  public RequestToken getOAuthRequestToken(String paramString1, String paramString2)
    throws TwitterException
  {
    if ((this.oauthToken instanceof AccessToken)) {
      throw new IllegalStateException("Access token already available.");
    }
    ArrayList localArrayList = new ArrayList();
    if (paramString1 != null) {
      localArrayList.add(new HttpParameter("oauth_callback", paramString1));
    }
    if (paramString2 != null) {
      localArrayList.add(new HttpParameter("x_auth_access_type", paramString2));
    }
    this.oauthToken = new RequestToken(http.post(this.conf.getOAuthRequestTokenURL(), (HttpParameter[])localArrayList.toArray(new HttpParameter[localArrayList.size()]), this), this);
    return (RequestToken)this.oauthToken;
  }
  
  public int hashCode()
  {
    int i = 0;
    int j;
    int k;
    if (this.consumerKey != null)
    {
      j = this.consumerKey.hashCode();
      k = j * 31;
      if (this.consumerSecret == null) {
        break label72;
      }
    }
    label72:
    for (int m = this.consumerSecret.hashCode();; m = 0)
    {
      int n = 31 * (k + m);
      if (this.oauthToken != null) {
        i = this.oauthToken.hashCode();
      }
      return n + i;
      j = 0;
      break;
    }
  }
  
  public boolean isEnabled()
  {
    if ((this.oauthToken != null) && ((this.oauthToken instanceof AccessToken))) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public void setOAuthAccessToken(AccessToken paramAccessToken)
  {
    this.oauthToken = paramAccessToken;
  }
  
  public void setOAuthConsumer(String paramString1, String paramString2)
  {
    if (paramString1 != null)
    {
      this.consumerKey = paramString1;
      if (paramString2 == null) {
        break label25;
      }
    }
    for (;;)
    {
      this.consumerSecret = paramString2;
      return;
      paramString1 = "";
      break;
      label25:
      paramString2 = "";
    }
  }
  
  public void setOAuthRealm(String paramString)
  {
    this.realm = paramString;
  }
  
  public String toString()
  {
    return "OAuthAuthorization{consumerKey='" + this.consumerKey + '\'' + ", consumerSecret='******************************************'" + ", oauthToken=" + this.oauthToken + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.auth.OAuthAuthorization
 * JD-Core Version:    0.7.0.1
 */