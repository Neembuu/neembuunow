package twitter4j.conf;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import twitter4j.Version;
import twitter4j.internal.logging.Logger;
import twitter4j.internal.util.z_T4JInternalStringUtil;

class ConfigurationBase
  implements Configuration, Serializable
{
  public static final String DALVIK = "twitter4j.dalvik";
  private static final String DEFAULT_OAUTH2_INVALIDATE_TOKEN_URL = "https://api.twitter.com/oauth2/invalidate_token";
  private static final String DEFAULT_OAUTH2_TOKEN_URL = "https://api.twitter.com/oauth2/token";
  private static final String DEFAULT_OAUTH_ACCESS_TOKEN_URL = "http://api.twitter.com/oauth/access_token";
  private static final String DEFAULT_OAUTH_AUTHENTICATION_URL = "http://api.twitter.com/oauth/authenticate";
  private static final String DEFAULT_OAUTH_AUTHORIZATION_URL = "http://api.twitter.com/oauth/authorize";
  private static final String DEFAULT_OAUTH_REQUEST_TOKEN_URL = "http://api.twitter.com/oauth/request_token";
  private static final String DEFAULT_REST_BASE_URL = "http://api.twitter.com/1.1/";
  private static final String DEFAULT_SITE_STREAM_BASE_URL = "https://sitestream.twitter.com/1.1/";
  private static final String DEFAULT_STREAM_BASE_URL = "https://stream.twitter.com/1.1/";
  private static final String DEFAULT_USER_STREAM_BASE_URL = "https://userstream.twitter.com/1.1/";
  public static final String GAE = "twitter4j.gae";
  static String dalvikDetected;
  static String gaeDetected;
  private static final List<ConfigurationBase> instances;
  private static final long serialVersionUID = -6610497517837844232L;
  private boolean IS_DALVIK;
  private boolean IS_GAE;
  private boolean applicationOnlyAuthEnabled = false;
  private int asyncNumThreads;
  private String clientURL;
  private String clientVersion;
  private long contributingTo;
  private boolean debug;
  private int defaultMaxPerRoute;
  private String dispatcherImpl;
  private boolean gzipEnabled;
  private int httpConnectionTimeout;
  private String httpProxyHost;
  private String httpProxyPassword;
  private int httpProxyPort;
  private String httpProxyUser;
  private int httpReadTimeout;
  private int httpRetryCount;
  private int httpRetryIntervalSeconds;
  private int httpStreamingReadTimeout;
  private boolean includeEntitiesEnabled = true;
  private boolean includeMyRetweetEnabled = true;
  private boolean includeRTsEnabled = true;
  private boolean jsonStoreEnabled;
  private String loggerFactory;
  private int maxTotalConnections;
  private boolean mbeanEnabled;
  private String mediaProvider;
  private String mediaProviderAPIKey;
  private Properties mediaProviderParameters;
  private String oAuth2AccessToken;
  private String oAuth2InvalidateTokenURL;
  private String oAuth2TokenType;
  private String oAuth2TokenURL;
  private String oAuthAccessToken;
  private String oAuthAccessTokenSecret;
  private String oAuthAccessTokenURL;
  private String oAuthAuthenticationURL;
  private String oAuthAuthorizationURL;
  private String oAuthConsumerKey;
  private String oAuthConsumerSecret;
  private String oAuthRequestTokenURL;
  private String password;
  private boolean prettyDebug;
  Map<String, String> requestHeaders;
  private String restBaseURL;
  private String siteStreamBaseURL;
  private boolean stallWarningsEnabled;
  private String streamBaseURL;
  private boolean trimUserEnabled = false;
  private boolean useSSL;
  private String user;
  private String userAgent;
  private String userStreamBaseURL;
  private boolean userStreamRepliesAllEnabled;
  
  static
  {
    try
    {
      Class.forName("dalvik.system.VMRuntime");
      dalvikDetected = "true";
    }
    catch (ClassNotFoundException localClassNotFoundException1)
    {
      try
      {
        for (;;)
        {
          Class.forName("com.google.appengine.api.urlfetch.URLFetchService");
          gaeDetected = "true";
          instances = new ArrayList();
          return;
          localClassNotFoundException1 = localClassNotFoundException1;
          dalvikDetected = "false";
        }
      }
      catch (ClassNotFoundException localClassNotFoundException2)
      {
        for (;;)
        {
          gaeDetected = "false";
        }
      }
    }
  }
  
  protected ConfigurationBase()
  {
    setDebug(false);
    setUser(null);
    setPassword(null);
    setUseSSL(true);
    setPrettyDebugEnabled(false);
    setGZIPEnabled(true);
    setHttpProxyHost(null);
    setHttpProxyUser(null);
    setHttpProxyPassword(null);
    setHttpProxyPort(-1);
    setHttpConnectionTimeout(20000);
    setHttpReadTimeout(120000);
    setHttpStreamingReadTimeout(40000);
    setHttpRetryCount(0);
    setHttpRetryIntervalSeconds(5);
    setHttpMaxTotalConnections(20);
    setHttpDefaultMaxPerRoute(2);
    setOAuthConsumerKey(null);
    setOAuthConsumerSecret(null);
    setOAuthAccessToken(null);
    setOAuthAccessTokenSecret(null);
    setAsyncNumThreads(1);
    setContributingTo(-1L);
    setClientVersion(Version.getVersion());
    setClientURL("http://twitter4j.org/en/twitter4j-" + Version.getVersion() + ".xml");
    setUserAgent("twitter4j http://twitter4j.org/ /" + Version.getVersion());
    setJSONStoreEnabled(false);
    setMBeanEnabled(false);
    setOAuthRequestTokenURL("http://api.twitter.com/oauth/request_token");
    setOAuthAuthorizationURL("http://api.twitter.com/oauth/authorize");
    setOAuthAccessTokenURL("http://api.twitter.com/oauth/access_token");
    setOAuthAuthenticationURL("http://api.twitter.com/oauth/authenticate");
    setOAuth2TokenURL("https://api.twitter.com/oauth2/token");
    setOAuth2InvalidateTokenURL("https://api.twitter.com/oauth2/invalidate_token");
    setRestBaseURL("http://api.twitter.com/1.1/");
    setStreamBaseURL("https://stream.twitter.com/1.1/");
    setUserStreamBaseURL("https://userstream.twitter.com/1.1/");
    setSiteStreamBaseURL("https://sitestream.twitter.com/1.1/");
    setDispatcherImpl("twitter4j.internal.async.DispatcherImpl");
    setLoggerFactory(null);
    setUserStreamRepliesAllEnabled(false);
    setStallWarningsEnabled(true);
    try
    {
      String str4 = System.getProperty("twitter4j.dalvik", dalvikDetected);
      str1 = str4;
    }
    catch (SecurityException localSecurityException1)
    {
      for (;;)
      {
        String str1 = dalvikDetected;
      }
    }
    this.IS_DALVIK = Boolean.valueOf(str1).booleanValue();
    try
    {
      String str3 = System.getProperty("twitter4j.gae", gaeDetected);
      str2 = str3;
    }
    catch (SecurityException localSecurityException2)
    {
      for (;;)
      {
        String str2 = gaeDetected;
      }
    }
    this.IS_GAE = Boolean.valueOf(str2).booleanValue();
    setMediaProvider("TWITTER");
    setMediaProviderAPIKey(null);
    setMediaProviderParameters(null);
  }
  
  private static void cacheInstance(ConfigurationBase paramConfigurationBase)
  {
    if (!instances.contains(paramConfigurationBase)) {
      instances.add(paramConfigurationBase);
    }
  }
  
  private void fixRestBaseURL()
  {
    if ("http://api.twitter.com/1.1/".equals(fixURL(false, this.restBaseURL))) {
      this.restBaseURL = fixURL(this.useSSL, this.restBaseURL);
    }
    if ("http://api.twitter.com/oauth/access_token".equals(fixURL(false, this.oAuthAccessTokenURL))) {
      this.oAuthAccessTokenURL = fixURL(this.useSSL, this.oAuthAccessTokenURL);
    }
    if ("http://api.twitter.com/oauth/authenticate".equals(fixURL(false, this.oAuthAuthenticationURL))) {
      this.oAuthAuthenticationURL = fixURL(this.useSSL, this.oAuthAuthenticationURL);
    }
    if ("http://api.twitter.com/oauth/authorize".equals(fixURL(false, this.oAuthAuthorizationURL))) {
      this.oAuthAuthorizationURL = fixURL(this.useSSL, this.oAuthAuthorizationURL);
    }
    if ("http://api.twitter.com/oauth/request_token".equals(fixURL(false, this.oAuthRequestTokenURL))) {
      this.oAuthRequestTokenURL = fixURL(this.useSSL, this.oAuthRequestTokenURL);
    }
  }
  
  static String fixURL(boolean paramBoolean, String paramString)
  {
    String str2;
    if (paramString == null) {
      str2 = null;
    }
    for (;;)
    {
      return str2;
      int i = paramString.indexOf("://");
      if (-1 == i) {
        throw new IllegalArgumentException("url should contain '://'");
      }
      String str1 = paramString.substring(i + 3);
      if (paramBoolean) {
        str2 = "https://" + str1;
      } else {
        str2 = "http://" + str1;
      }
    }
  }
  
  private static ConfigurationBase getInstance(ConfigurationBase paramConfigurationBase)
  {
    int i = instances.indexOf(paramConfigurationBase);
    if (i == -1) {
      instances.add(paramConfigurationBase);
    }
    for (;;)
    {
      return paramConfigurationBase;
      paramConfigurationBase = (ConfigurationBase)instances.get(i);
    }
  }
  
  private void initRequestHeaders()
  {
    this.requestHeaders = new HashMap();
    this.requestHeaders.put("X-Twitter-Client-Version", getClientVersion());
    this.requestHeaders.put("X-Twitter-Client-URL", getClientURL());
    this.requestHeaders.put("X-Twitter-Client", "Twitter4J");
    this.requestHeaders.put("User-Agent", getUserAgent());
    if (this.gzipEnabled) {
      this.requestHeaders.put("Accept-Encoding", "gzip");
    }
    if (this.IS_DALVIK) {
      this.requestHeaders.put("Connection", "close");
    }
  }
  
  protected void cacheInstance()
  {
    cacheInstance(this);
  }
  
  public void dumpConfiguration()
  {
    Logger localLogger = Logger.getLogger(ConfigurationBase.class);
    Field[] arrayOfField;
    int i;
    int j;
    if (this.debug)
    {
      arrayOfField = ConfigurationBase.class.getDeclaredFields();
      i = arrayOfField.length;
      j = 0;
    }
    for (;;)
    {
      Field localField;
      if (j < i) {
        localField = arrayOfField[j];
      }
      try
      {
        Object localObject = localField.get(this);
        String str = String.valueOf(localObject);
        if ((localObject != null) && (localField.getName().matches("oAuthConsumerSecret|oAuthAccessTokenSecret|password"))) {
          str = z_T4JInternalStringUtil.maskString(String.valueOf(localObject));
        }
        localLogger.debug(localField.getName() + ": " + str);
        label114:
        j++;
        continue;
        if (!this.includeRTsEnabled) {
          localLogger.warn("includeRTsEnabled is set to false. This configuration may not take effect after May 14th, 2012. https://dev.twitter.com/blog/api-housekeeping");
        }
        if (!this.includeEntitiesEnabled) {
          localLogger.warn("includeEntitiesEnabled is set to false. This configuration may not take effect after May 14th, 2012. https://dev.twitter.com/blog/api-housekeeping");
        }
        return;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        break label114;
      }
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    ConfigurationBase localConfigurationBase;
    do
    {
      for (;;)
      {
        return bool;
        if ((paramObject == null) || (getClass() != paramObject.getClass()))
        {
          bool = false;
        }
        else
        {
          localConfigurationBase = (ConfigurationBase)paramObject;
          if (this.IS_DALVIK != localConfigurationBase.IS_DALVIK)
          {
            bool = false;
          }
          else if (this.IS_GAE != localConfigurationBase.IS_GAE)
          {
            bool = false;
          }
          else if (this.asyncNumThreads != localConfigurationBase.asyncNumThreads)
          {
            bool = false;
          }
          else if (this.contributingTo != localConfigurationBase.contributingTo)
          {
            bool = false;
          }
          else if (this.debug != localConfigurationBase.debug)
          {
            bool = false;
          }
          else if (this.defaultMaxPerRoute != localConfigurationBase.defaultMaxPerRoute)
          {
            bool = false;
          }
          else if (this.gzipEnabled != localConfigurationBase.gzipEnabled)
          {
            bool = false;
          }
          else if (this.httpConnectionTimeout != localConfigurationBase.httpConnectionTimeout)
          {
            bool = false;
          }
          else if (this.httpProxyPort != localConfigurationBase.httpProxyPort)
          {
            bool = false;
          }
          else if (this.httpReadTimeout != localConfigurationBase.httpReadTimeout)
          {
            bool = false;
          }
          else if (this.httpRetryCount != localConfigurationBase.httpRetryCount)
          {
            bool = false;
          }
          else if (this.httpRetryIntervalSeconds != localConfigurationBase.httpRetryIntervalSeconds)
          {
            bool = false;
          }
          else if (this.httpStreamingReadTimeout != localConfigurationBase.httpStreamingReadTimeout)
          {
            bool = false;
          }
          else if (this.includeEntitiesEnabled != localConfigurationBase.includeEntitiesEnabled)
          {
            bool = false;
          }
          else if (this.includeMyRetweetEnabled != localConfigurationBase.includeMyRetweetEnabled)
          {
            bool = false;
          }
          else if (this.trimUserEnabled != localConfigurationBase.trimUserEnabled)
          {
            bool = false;
          }
          else if (this.includeRTsEnabled != localConfigurationBase.includeRTsEnabled)
          {
            bool = false;
          }
          else if (this.jsonStoreEnabled != localConfigurationBase.jsonStoreEnabled)
          {
            bool = false;
          }
          else if (this.maxTotalConnections != localConfigurationBase.maxTotalConnections)
          {
            bool = false;
          }
          else if (this.mbeanEnabled != localConfigurationBase.mbeanEnabled)
          {
            bool = false;
          }
          else if (this.prettyDebug != localConfigurationBase.prettyDebug)
          {
            bool = false;
          }
          else if (this.stallWarningsEnabled != localConfigurationBase.stallWarningsEnabled)
          {
            bool = false;
          }
          else if (this.applicationOnlyAuthEnabled != localConfigurationBase.applicationOnlyAuthEnabled)
          {
            bool = false;
          }
          else if (this.useSSL != localConfigurationBase.useSSL)
          {
            bool = false;
          }
          else
          {
            if (this.userStreamRepliesAllEnabled == localConfigurationBase.userStreamRepliesAllEnabled) {
              break;
            }
            bool = false;
          }
        }
      }
      if (this.clientURL != null)
      {
        if (this.clientURL.equals(localConfigurationBase.clientURL)) {}
      }
      else {
        while (localConfigurationBase.clientURL != null)
        {
          bool = false;
          break;
        }
      }
      if (this.clientVersion != null)
      {
        if (this.clientVersion.equals(localConfigurationBase.clientVersion)) {}
      }
      else {
        while (localConfigurationBase.clientVersion != null)
        {
          bool = false;
          break;
        }
      }
      if (this.dispatcherImpl != null)
      {
        if (this.dispatcherImpl.equals(localConfigurationBase.dispatcherImpl)) {}
      }
      else {
        while (localConfigurationBase.dispatcherImpl != null)
        {
          bool = false;
          break;
        }
      }
      if (this.httpProxyHost != null)
      {
        if (this.httpProxyHost.equals(localConfigurationBase.httpProxyHost)) {}
      }
      else {
        while (localConfigurationBase.httpProxyHost != null)
        {
          bool = false;
          break;
        }
      }
      if (this.httpProxyPassword != null)
      {
        if (this.httpProxyPassword.equals(localConfigurationBase.httpProxyPassword)) {}
      }
      else {
        while (localConfigurationBase.httpProxyPassword != null)
        {
          bool = false;
          break;
        }
      }
      if (this.httpProxyUser != null)
      {
        if (this.httpProxyUser.equals(localConfigurationBase.httpProxyUser)) {}
      }
      else {
        while (localConfigurationBase.httpProxyUser != null)
        {
          bool = false;
          break;
        }
      }
      if (this.loggerFactory != null)
      {
        if (this.loggerFactory.equals(localConfigurationBase.loggerFactory)) {}
      }
      else {
        while (localConfigurationBase.loggerFactory != null)
        {
          bool = false;
          break;
        }
      }
      if (this.mediaProvider != null)
      {
        if (this.mediaProvider.equals(localConfigurationBase.mediaProvider)) {}
      }
      else {
        while (localConfigurationBase.mediaProvider != null)
        {
          bool = false;
          break;
        }
      }
      if (this.mediaProviderAPIKey != null)
      {
        if (this.mediaProviderAPIKey.equals(localConfigurationBase.mediaProviderAPIKey)) {}
      }
      else {
        while (localConfigurationBase.mediaProviderAPIKey != null)
        {
          bool = false;
          break;
        }
      }
      if (this.mediaProviderParameters != null)
      {
        if (this.mediaProviderParameters.equals(localConfigurationBase.mediaProviderParameters)) {}
      }
      else {
        while (localConfigurationBase.mediaProviderParameters != null)
        {
          bool = false;
          break;
        }
      }
      if (this.oAuthAccessToken != null)
      {
        if (this.oAuthAccessToken.equals(localConfigurationBase.oAuthAccessToken)) {}
      }
      else {
        while (localConfigurationBase.oAuthAccessToken != null)
        {
          bool = false;
          break;
        }
      }
      if (this.oAuthAccessTokenSecret != null)
      {
        if (this.oAuthAccessTokenSecret.equals(localConfigurationBase.oAuthAccessTokenSecret)) {}
      }
      else {
        while (localConfigurationBase.oAuthAccessTokenSecret != null)
        {
          bool = false;
          break;
        }
      }
      if (this.oAuth2TokenType != null)
      {
        if (this.oAuth2TokenType.equals(localConfigurationBase.oAuth2TokenType)) {}
      }
      else {
        while (localConfigurationBase.oAuth2TokenType != null)
        {
          bool = false;
          break;
        }
      }
      if (this.oAuth2AccessToken != null)
      {
        if (this.oAuth2AccessToken.equals(localConfigurationBase.oAuth2AccessToken)) {}
      }
      else {
        while (localConfigurationBase.oAuth2AccessToken != null)
        {
          bool = false;
          break;
        }
      }
      if (this.oAuthAccessTokenURL != null)
      {
        if (this.oAuthAccessTokenURL.equals(localConfigurationBase.oAuthAccessTokenURL)) {}
      }
      else {
        while (localConfigurationBase.oAuthAccessTokenURL != null)
        {
          bool = false;
          break;
        }
      }
      if (this.oAuthAuthenticationURL != null)
      {
        if (this.oAuthAuthenticationURL.equals(localConfigurationBase.oAuthAuthenticationURL)) {}
      }
      else {
        while (localConfigurationBase.oAuthAuthenticationURL != null)
        {
          bool = false;
          break;
        }
      }
      if (this.oAuthAuthorizationURL != null)
      {
        if (this.oAuthAuthorizationURL.equals(localConfigurationBase.oAuthAuthorizationURL)) {}
      }
      else {
        while (localConfigurationBase.oAuthAuthorizationURL != null)
        {
          bool = false;
          break;
        }
      }
      if (this.oAuth2TokenURL != null)
      {
        if (this.oAuth2TokenURL.equals(localConfigurationBase.oAuth2TokenURL)) {}
      }
      else {
        while (localConfigurationBase.oAuth2TokenURL != null)
        {
          bool = false;
          break;
        }
      }
      if (this.oAuth2InvalidateTokenURL != null)
      {
        if (this.oAuth2InvalidateTokenURL.equals(localConfigurationBase.oAuth2InvalidateTokenURL)) {}
      }
      else {
        while (localConfigurationBase.oAuth2InvalidateTokenURL != null)
        {
          bool = false;
          break;
        }
      }
      if (this.oAuthConsumerKey != null)
      {
        if (this.oAuthConsumerKey.equals(localConfigurationBase.oAuthConsumerKey)) {}
      }
      else {
        while (localConfigurationBase.oAuthConsumerKey != null)
        {
          bool = false;
          break;
        }
      }
      if (this.oAuthConsumerSecret != null)
      {
        if (this.oAuthConsumerSecret.equals(localConfigurationBase.oAuthConsumerSecret)) {}
      }
      else {
        while (localConfigurationBase.oAuthConsumerSecret != null)
        {
          bool = false;
          break;
        }
      }
      if (this.oAuthRequestTokenURL != null)
      {
        if (this.oAuthRequestTokenURL.equals(localConfigurationBase.oAuthRequestTokenURL)) {}
      }
      else {
        while (localConfigurationBase.oAuthRequestTokenURL != null)
        {
          bool = false;
          break;
        }
      }
      if (this.password != null)
      {
        if (this.password.equals(localConfigurationBase.password)) {}
      }
      else {
        while (localConfigurationBase.password != null)
        {
          bool = false;
          break;
        }
      }
      if (this.requestHeaders != null)
      {
        if (this.requestHeaders.equals(localConfigurationBase.requestHeaders)) {}
      }
      else {
        while (localConfigurationBase.requestHeaders != null)
        {
          bool = false;
          break;
        }
      }
      if (this.restBaseURL != null)
      {
        if (this.restBaseURL.equals(localConfigurationBase.restBaseURL)) {}
      }
      else {
        while (localConfigurationBase.restBaseURL != null)
        {
          bool = false;
          break;
        }
      }
      if (this.siteStreamBaseURL != null)
      {
        if (this.siteStreamBaseURL.equals(localConfigurationBase.siteStreamBaseURL)) {}
      }
      else {
        while (localConfigurationBase.siteStreamBaseURL != null)
        {
          bool = false;
          break;
        }
      }
      if (this.streamBaseURL != null)
      {
        if (this.streamBaseURL.equals(localConfigurationBase.streamBaseURL)) {}
      }
      else {
        while (localConfigurationBase.streamBaseURL != null)
        {
          bool = false;
          break;
        }
      }
      if (this.user != null)
      {
        if (this.user.equals(localConfigurationBase.user)) {}
      }
      else {
        while (localConfigurationBase.user != null)
        {
          bool = false;
          break;
        }
      }
      if (this.userAgent != null)
      {
        if (this.userAgent.equals(localConfigurationBase.userAgent)) {}
      }
      else {
        while (localConfigurationBase.userAgent != null)
        {
          bool = false;
          break;
        }
      }
      if (this.userStreamBaseURL == null) {
        break;
      }
    } while (this.userStreamBaseURL.equals(localConfigurationBase.userStreamBaseURL));
    for (;;)
    {
      bool = false;
      break;
      if (localConfigurationBase.userStreamBaseURL == null) {
        break;
      }
    }
  }
  
  public final int getAsyncNumThreads()
  {
    return this.asyncNumThreads;
  }
  
  public final String getClientURL()
  {
    return this.clientURL;
  }
  
  public final String getClientVersion()
  {
    return this.clientVersion;
  }
  
  public final long getContributingTo()
  {
    return this.contributingTo;
  }
  
  public String getDispatcherImpl()
  {
    return this.dispatcherImpl;
  }
  
  public final int getHttpConnectionTimeout()
  {
    return this.httpConnectionTimeout;
  }
  
  public final int getHttpDefaultMaxPerRoute()
  {
    return this.defaultMaxPerRoute;
  }
  
  public final int getHttpMaxTotalConnections()
  {
    return this.maxTotalConnections;
  }
  
  public final String getHttpProxyHost()
  {
    return this.httpProxyHost;
  }
  
  public final String getHttpProxyPassword()
  {
    return this.httpProxyPassword;
  }
  
  public final int getHttpProxyPort()
  {
    return this.httpProxyPort;
  }
  
  public final String getHttpProxyUser()
  {
    return this.httpProxyUser;
  }
  
  public final int getHttpReadTimeout()
  {
    return this.httpReadTimeout;
  }
  
  public final int getHttpRetryCount()
  {
    return this.httpRetryCount;
  }
  
  public final int getHttpRetryIntervalSeconds()
  {
    return this.httpRetryIntervalSeconds;
  }
  
  public int getHttpStreamingReadTimeout()
  {
    return this.httpStreamingReadTimeout;
  }
  
  public String getLoggerFactory()
  {
    return this.loggerFactory;
  }
  
  public String getMediaProvider()
  {
    return this.mediaProvider;
  }
  
  public String getMediaProviderAPIKey()
  {
    return this.mediaProviderAPIKey;
  }
  
  public Properties getMediaProviderParameters()
  {
    return this.mediaProviderParameters;
  }
  
  public String getOAuth2AccessToken()
  {
    return this.oAuth2AccessToken;
  }
  
  public String getOAuth2InvalidateTokenURL()
  {
    return this.oAuth2InvalidateTokenURL;
  }
  
  public String getOAuth2TokenType()
  {
    return this.oAuth2TokenType;
  }
  
  public String getOAuth2TokenURL()
  {
    return this.oAuth2TokenURL;
  }
  
  public String getOAuthAccessToken()
  {
    return this.oAuthAccessToken;
  }
  
  public String getOAuthAccessTokenSecret()
  {
    return this.oAuthAccessTokenSecret;
  }
  
  public String getOAuthAccessTokenURL()
  {
    return this.oAuthAccessTokenURL;
  }
  
  public String getOAuthAuthenticationURL()
  {
    return this.oAuthAuthenticationURL;
  }
  
  public String getOAuthAuthorizationURL()
  {
    return this.oAuthAuthorizationURL;
  }
  
  public final String getOAuthConsumerKey()
  {
    return this.oAuthConsumerKey;
  }
  
  public final String getOAuthConsumerSecret()
  {
    return this.oAuthConsumerSecret;
  }
  
  public String getOAuthRequestTokenURL()
  {
    return this.oAuthRequestTokenURL;
  }
  
  public final String getPassword()
  {
    return this.password;
  }
  
  public Map<String, String> getRequestHeaders()
  {
    return this.requestHeaders;
  }
  
  public String getRestBaseURL()
  {
    return this.restBaseURL;
  }
  
  public String getSiteStreamBaseURL()
  {
    return this.siteStreamBaseURL;
  }
  
  public String getStreamBaseURL()
  {
    return this.streamBaseURL;
  }
  
  public final String getUser()
  {
    return this.user;
  }
  
  public final String getUserAgent()
  {
    return this.userAgent;
  }
  
  public String getUserStreamBaseURL()
  {
    return this.userStreamBaseURL;
  }
  
  public int hashCode()
  {
    int i = 1;
    int j = 0;
    int k;
    int n;
    label35:
    int i2;
    label61:
    int i4;
    label87:
    int i6;
    label107:
    int i8;
    label127:
    int i10;
    label147:
    int i12;
    label173:
    int i14;
    label199:
    int i16;
    label225:
    int i18;
    label315:
    int i20;
    label341:
    int i22;
    label367:
    int i24;
    label393:
    int i26;
    label419:
    int i28;
    label445:
    int i30;
    label471:
    int i32;
    label497:
    int i34;
    label523:
    int i36;
    label549:
    int i38;
    label575:
    int i40;
    label601:
    int i42;
    label627:
    int i44;
    label653:
    int i46;
    label679:
    int i48;
    label705:
    int i50;
    label731:
    int i52;
    label757:
    int i54;
    label802:
    int i56;
    label822:
    int i58;
    label842:
    int i60;
    label862:
    int i62;
    label882:
    int i64;
    label902:
    int i66;
    label922:
    int i68;
    label942:
    int i70;
    label962:
    int i72;
    label988:
    int i74;
    label1014:
    int i76;
    label1040:
    int i78;
    label1066:
    int i80;
    label1092:
    int i82;
    label1112:
    int i83;
    if (this.debug)
    {
      k = i;
      int m = k * 31;
      if (this.userAgent == null) {
        break label1165;
      }
      n = this.userAgent.hashCode();
      int i1 = 31 * (m + n);
      if (this.user == null) {
        break label1171;
      }
      i2 = this.user.hashCode();
      int i3 = 31 * (i1 + i2);
      if (this.password == null) {
        break label1177;
      }
      i4 = this.password.hashCode();
      int i5 = 31 * (i3 + i4);
      if (!this.useSSL) {
        break label1183;
      }
      i6 = i;
      int i7 = 31 * (i5 + i6);
      if (!this.prettyDebug) {
        break label1189;
      }
      i8 = i;
      int i9 = 31 * (i7 + i8);
      if (!this.gzipEnabled) {
        break label1195;
      }
      i10 = i;
      int i11 = 31 * (i9 + i10);
      if (this.httpProxyHost == null) {
        break label1201;
      }
      i12 = this.httpProxyHost.hashCode();
      int i13 = 31 * (i11 + i12);
      if (this.httpProxyUser == null) {
        break label1207;
      }
      i14 = this.httpProxyUser.hashCode();
      int i15 = 31 * (i13 + i14);
      if (this.httpProxyPassword == null) {
        break label1213;
      }
      i16 = this.httpProxyPassword.hashCode();
      int i17 = 31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (i15 + i16) + this.httpProxyPort) + this.httpConnectionTimeout) + this.httpReadTimeout) + this.httpStreamingReadTimeout) + this.httpRetryCount) + this.httpRetryIntervalSeconds) + this.maxTotalConnections) + this.defaultMaxPerRoute);
      if (this.oAuthConsumerKey == null) {
        break label1219;
      }
      i18 = this.oAuthConsumerKey.hashCode();
      int i19 = 31 * (i17 + i18);
      if (this.oAuthConsumerSecret == null) {
        break label1225;
      }
      i20 = this.oAuthConsumerSecret.hashCode();
      int i21 = 31 * (i19 + i20);
      if (this.oAuthAccessToken == null) {
        break label1231;
      }
      i22 = this.oAuthAccessToken.hashCode();
      int i23 = 31 * (i21 + i22);
      if (this.oAuthAccessTokenSecret == null) {
        break label1237;
      }
      i24 = this.oAuthAccessTokenSecret.hashCode();
      int i25 = 31 * (i23 + i24);
      if (this.oAuth2TokenType == null) {
        break label1243;
      }
      i26 = this.oAuth2TokenType.hashCode();
      int i27 = 31 * (i25 + i26);
      if (this.oAuth2AccessToken == null) {
        break label1249;
      }
      i28 = this.oAuth2AccessToken.hashCode();
      int i29 = 31 * (i27 + i28);
      if (this.oAuthRequestTokenURL == null) {
        break label1255;
      }
      i30 = this.oAuthRequestTokenURL.hashCode();
      int i31 = 31 * (i29 + i30);
      if (this.oAuthAuthorizationURL == null) {
        break label1261;
      }
      i32 = this.oAuthAuthorizationURL.hashCode();
      int i33 = 31 * (i31 + i32);
      if (this.oAuthAccessTokenURL == null) {
        break label1267;
      }
      i34 = this.oAuthAccessTokenURL.hashCode();
      int i35 = 31 * (i33 + i34);
      if (this.oAuthAuthenticationURL == null) {
        break label1273;
      }
      i36 = this.oAuthAuthenticationURL.hashCode();
      int i37 = 31 * (i35 + i36);
      if (this.oAuth2TokenURL == null) {
        break label1279;
      }
      i38 = this.oAuth2TokenURL.hashCode();
      int i39 = 31 * (i37 + i38);
      if (this.oAuth2InvalidateTokenURL == null) {
        break label1285;
      }
      i40 = this.oAuth2InvalidateTokenURL.hashCode();
      int i41 = 31 * (i39 + i40);
      if (this.restBaseURL == null) {
        break label1291;
      }
      i42 = this.restBaseURL.hashCode();
      int i43 = 31 * (i41 + i42);
      if (this.streamBaseURL == null) {
        break label1297;
      }
      i44 = this.streamBaseURL.hashCode();
      int i45 = 31 * (i43 + i44);
      if (this.userStreamBaseURL == null) {
        break label1303;
      }
      i46 = this.userStreamBaseURL.hashCode();
      int i47 = 31 * (i45 + i46);
      if (this.siteStreamBaseURL == null) {
        break label1309;
      }
      i48 = this.siteStreamBaseURL.hashCode();
      int i49 = 31 * (i47 + i48);
      if (this.dispatcherImpl == null) {
        break label1315;
      }
      i50 = this.dispatcherImpl.hashCode();
      int i51 = 31 * (i49 + i50);
      if (this.loggerFactory == null) {
        break label1321;
      }
      i52 = this.loggerFactory.hashCode();
      int i53 = 31 * (31 * (31 * (i51 + i52) + this.asyncNumThreads) + (int)(this.contributingTo ^ this.contributingTo >>> 32));
      if (!this.includeRTsEnabled) {
        break label1327;
      }
      i54 = i;
      int i55 = 31 * (i53 + i54);
      if (!this.includeEntitiesEnabled) {
        break label1333;
      }
      i56 = i;
      int i57 = 31 * (i55 + i56);
      if (!this.includeMyRetweetEnabled) {
        break label1339;
      }
      i58 = i;
      int i59 = 31 * (i57 + i58);
      if (!this.trimUserEnabled) {
        break label1345;
      }
      i60 = i;
      int i61 = 31 * (i59 + i60);
      if (!this.jsonStoreEnabled) {
        break label1351;
      }
      i62 = i;
      int i63 = 31 * (i61 + i62);
      if (!this.mbeanEnabled) {
        break label1357;
      }
      i64 = i;
      int i65 = 31 * (i63 + i64);
      if (!this.userStreamRepliesAllEnabled) {
        break label1363;
      }
      i66 = i;
      int i67 = 31 * (i65 + i66);
      if (!this.stallWarningsEnabled) {
        break label1369;
      }
      i68 = i;
      int i69 = 31 * (i67 + i68);
      if (!this.applicationOnlyAuthEnabled) {
        break label1375;
      }
      i70 = i;
      int i71 = 31 * (i69 + i70);
      if (this.mediaProvider == null) {
        break label1381;
      }
      i72 = this.mediaProvider.hashCode();
      int i73 = 31 * (i71 + i72);
      if (this.mediaProviderAPIKey == null) {
        break label1387;
      }
      i74 = this.mediaProviderAPIKey.hashCode();
      int i75 = 31 * (i73 + i74);
      if (this.mediaProviderParameters == null) {
        break label1393;
      }
      i76 = this.mediaProviderParameters.hashCode();
      int i77 = 31 * (i75 + i76);
      if (this.clientVersion == null) {
        break label1399;
      }
      i78 = this.clientVersion.hashCode();
      int i79 = 31 * (i77 + i78);
      if (this.clientURL == null) {
        break label1405;
      }
      i80 = this.clientURL.hashCode();
      int i81 = 31 * (i79 + i80);
      if (!this.IS_DALVIK) {
        break label1411;
      }
      i82 = i;
      i83 = 31 * (i81 + i82);
      if (!this.IS_GAE) {
        break label1417;
      }
    }
    for (;;)
    {
      int i84 = 31 * (i83 + i);
      if (this.requestHeaders != null) {
        j = this.requestHeaders.hashCode();
      }
      return i84 + j;
      k = 0;
      break;
      label1165:
      n = 0;
      break label35;
      label1171:
      i2 = 0;
      break label61;
      label1177:
      i4 = 0;
      break label87;
      label1183:
      i6 = 0;
      break label107;
      label1189:
      i8 = 0;
      break label127;
      label1195:
      i10 = 0;
      break label147;
      label1201:
      i12 = 0;
      break label173;
      label1207:
      i14 = 0;
      break label199;
      label1213:
      i16 = 0;
      break label225;
      label1219:
      i18 = 0;
      break label315;
      label1225:
      i20 = 0;
      break label341;
      label1231:
      i22 = 0;
      break label367;
      label1237:
      i24 = 0;
      break label393;
      label1243:
      i26 = 0;
      break label419;
      label1249:
      i28 = 0;
      break label445;
      label1255:
      i30 = 0;
      break label471;
      label1261:
      i32 = 0;
      break label497;
      label1267:
      i34 = 0;
      break label523;
      label1273:
      i36 = 0;
      break label549;
      label1279:
      i38 = 0;
      break label575;
      label1285:
      i40 = 0;
      break label601;
      label1291:
      i42 = 0;
      break label627;
      label1297:
      i44 = 0;
      break label653;
      label1303:
      i46 = 0;
      break label679;
      label1309:
      i48 = 0;
      break label705;
      label1315:
      i50 = 0;
      break label731;
      label1321:
      i52 = 0;
      break label757;
      label1327:
      i54 = 0;
      break label802;
      label1333:
      i56 = 0;
      break label822;
      label1339:
      i58 = 0;
      break label842;
      label1345:
      i60 = 0;
      break label862;
      label1351:
      i62 = 0;
      break label882;
      label1357:
      i64 = 0;
      break label902;
      label1363:
      i66 = 0;
      break label922;
      label1369:
      i68 = 0;
      break label942;
      label1375:
      i70 = 0;
      break label962;
      label1381:
      i72 = 0;
      break label988;
      label1387:
      i74 = 0;
      break label1014;
      label1393:
      i76 = 0;
      break label1040;
      label1399:
      i78 = 0;
      break label1066;
      label1405:
      i80 = 0;
      break label1092;
      label1411:
      i82 = 0;
      break label1112;
      label1417:
      i = 0;
    }
  }
  
  public boolean isApplicationOnlyAuthEnabled()
  {
    return this.applicationOnlyAuthEnabled;
  }
  
  public final boolean isDalvik()
  {
    return this.IS_DALVIK;
  }
  
  public final boolean isDebugEnabled()
  {
    return this.debug;
  }
  
  public boolean isGAE()
  {
    return this.IS_GAE;
  }
  
  public boolean isGZIPEnabled()
  {
    return this.gzipEnabled;
  }
  
  public boolean isIncludeEntitiesEnabled()
  {
    return this.includeEntitiesEnabled;
  }
  
  public boolean isIncludeMyRetweetEnabled()
  {
    return this.includeMyRetweetEnabled;
  }
  
  public boolean isIncludeRTsEnabled()
  {
    return this.includeRTsEnabled;
  }
  
  public boolean isJSONStoreEnabled()
  {
    return this.jsonStoreEnabled;
  }
  
  public boolean isMBeanEnabled()
  {
    return this.mbeanEnabled;
  }
  
  public boolean isPrettyDebugEnabled()
  {
    return this.prettyDebug;
  }
  
  public boolean isStallWarningsEnabled()
  {
    return this.stallWarningsEnabled;
  }
  
  public boolean isTrimUserEnabled()
  {
    return this.trimUserEnabled;
  }
  
  public boolean isUserStreamRepliesAllEnabled()
  {
    return this.userStreamRepliesAllEnabled;
  }
  
  protected Object readResolve()
    throws ObjectStreamException
  {
    return getInstance(this);
  }
  
  protected final void setApplicationOnlyAuthEnabled(boolean paramBoolean)
  {
    this.applicationOnlyAuthEnabled = paramBoolean;
  }
  
  protected final void setAsyncNumThreads(int paramInt)
  {
    this.asyncNumThreads = paramInt;
  }
  
  protected final void setClientURL(String paramString)
  {
    this.clientURL = paramString;
    initRequestHeaders();
  }
  
  protected final void setClientVersion(String paramString)
  {
    this.clientVersion = paramString;
    initRequestHeaders();
  }
  
  protected final void setContributingTo(long paramLong)
  {
    this.contributingTo = paramLong;
  }
  
  protected final void setDebug(boolean paramBoolean)
  {
    this.debug = paramBoolean;
  }
  
  protected final void setDispatcherImpl(String paramString)
  {
    this.dispatcherImpl = paramString;
  }
  
  protected final void setGZIPEnabled(boolean paramBoolean)
  {
    this.gzipEnabled = paramBoolean;
    initRequestHeaders();
  }
  
  protected final void setHttpConnectionTimeout(int paramInt)
  {
    this.httpConnectionTimeout = paramInt;
  }
  
  protected final void setHttpDefaultMaxPerRoute(int paramInt)
  {
    this.defaultMaxPerRoute = paramInt;
  }
  
  protected final void setHttpMaxTotalConnections(int paramInt)
  {
    this.maxTotalConnections = paramInt;
  }
  
  protected final void setHttpProxyHost(String paramString)
  {
    this.httpProxyHost = paramString;
  }
  
  protected final void setHttpProxyPassword(String paramString)
  {
    this.httpProxyPassword = paramString;
  }
  
  protected final void setHttpProxyPort(int paramInt)
  {
    this.httpProxyPort = paramInt;
  }
  
  protected final void setHttpProxyUser(String paramString)
  {
    this.httpProxyUser = paramString;
  }
  
  protected final void setHttpReadTimeout(int paramInt)
  {
    this.httpReadTimeout = paramInt;
  }
  
  protected final void setHttpRetryCount(int paramInt)
  {
    this.httpRetryCount = paramInt;
  }
  
  protected final void setHttpRetryIntervalSeconds(int paramInt)
  {
    this.httpRetryIntervalSeconds = paramInt;
  }
  
  protected final void setHttpStreamingReadTimeout(int paramInt)
  {
    this.httpStreamingReadTimeout = paramInt;
  }
  
  protected final void setIncludeEntitiesEnbled(boolean paramBoolean)
  {
    this.includeEntitiesEnabled = paramBoolean;
  }
  
  public void setIncludeMyRetweetEnabled(boolean paramBoolean)
  {
    this.includeMyRetweetEnabled = paramBoolean;
  }
  
  protected final void setIncludeRTsEnbled(boolean paramBoolean)
  {
    this.includeRTsEnabled = paramBoolean;
  }
  
  protected final void setJSONStoreEnabled(boolean paramBoolean)
  {
    this.jsonStoreEnabled = paramBoolean;
  }
  
  protected final void setLoggerFactory(String paramString)
  {
    this.loggerFactory = paramString;
  }
  
  protected final void setMBeanEnabled(boolean paramBoolean)
  {
    this.mbeanEnabled = paramBoolean;
  }
  
  protected final void setMediaProvider(String paramString)
  {
    this.mediaProvider = paramString;
  }
  
  protected final void setMediaProviderAPIKey(String paramString)
  {
    this.mediaProviderAPIKey = paramString;
  }
  
  protected final void setMediaProviderParameters(Properties paramProperties)
  {
    this.mediaProviderParameters = paramProperties;
  }
  
  protected final void setOAuth2AccessToken(String paramString)
  {
    this.oAuth2AccessToken = paramString;
  }
  
  protected final void setOAuth2InvalidateTokenURL(String paramString)
  {
    this.oAuth2InvalidateTokenURL = paramString;
    fixRestBaseURL();
  }
  
  protected final void setOAuth2TokenType(String paramString)
  {
    this.oAuth2TokenType = paramString;
  }
  
  protected final void setOAuth2TokenURL(String paramString)
  {
    this.oAuth2TokenURL = paramString;
    fixRestBaseURL();
  }
  
  protected final void setOAuthAccessToken(String paramString)
  {
    this.oAuthAccessToken = paramString;
  }
  
  protected final void setOAuthAccessTokenSecret(String paramString)
  {
    this.oAuthAccessTokenSecret = paramString;
  }
  
  protected final void setOAuthAccessTokenURL(String paramString)
  {
    this.oAuthAccessTokenURL = paramString;
    fixRestBaseURL();
  }
  
  protected final void setOAuthAuthenticationURL(String paramString)
  {
    this.oAuthAuthenticationURL = paramString;
    fixRestBaseURL();
  }
  
  protected final void setOAuthAuthorizationURL(String paramString)
  {
    this.oAuthAuthorizationURL = paramString;
    fixRestBaseURL();
  }
  
  protected final void setOAuthConsumerKey(String paramString)
  {
    this.oAuthConsumerKey = paramString;
    fixRestBaseURL();
  }
  
  protected final void setOAuthConsumerSecret(String paramString)
  {
    this.oAuthConsumerSecret = paramString;
    fixRestBaseURL();
  }
  
  protected final void setOAuthRequestTokenURL(String paramString)
  {
    this.oAuthRequestTokenURL = paramString;
    fixRestBaseURL();
  }
  
  protected final void setPassword(String paramString)
  {
    this.password = paramString;
  }
  
  protected final void setPrettyDebugEnabled(boolean paramBoolean)
  {
    this.prettyDebug = paramBoolean;
  }
  
  protected final void setRestBaseURL(String paramString)
  {
    this.restBaseURL = paramString;
    fixRestBaseURL();
  }
  
  protected final void setSiteStreamBaseURL(String paramString)
  {
    this.siteStreamBaseURL = paramString;
  }
  
  protected final void setStallWarningsEnabled(boolean paramBoolean)
  {
    this.stallWarningsEnabled = paramBoolean;
  }
  
  protected final void setStreamBaseURL(String paramString)
  {
    this.streamBaseURL = paramString;
  }
  
  public void setTrimUserEnabled(boolean paramBoolean)
  {
    this.trimUserEnabled = paramBoolean;
  }
  
  protected final void setUseSSL(boolean paramBoolean)
  {
    this.useSSL = paramBoolean;
    fixRestBaseURL();
  }
  
  protected final void setUser(String paramString)
  {
    this.user = paramString;
  }
  
  protected final void setUserAgent(String paramString)
  {
    this.userAgent = paramString;
    initRequestHeaders();
  }
  
  protected final void setUserStreamBaseURL(String paramString)
  {
    this.userStreamBaseURL = paramString;
  }
  
  protected final void setUserStreamRepliesAllEnabled(boolean paramBoolean)
  {
    this.userStreamRepliesAllEnabled = paramBoolean;
  }
  
  public String toString()
  {
    return "ConfigurationBase{debug=" + this.debug + ", userAgent='" + this.userAgent + '\'' + ", user='" + this.user + '\'' + ", password='" + this.password + '\'' + ", useSSL=" + this.useSSL + ", prettyDebug=" + this.prettyDebug + ", gzipEnabled=" + this.gzipEnabled + ", httpProxyHost='" + this.httpProxyHost + '\'' + ", httpProxyUser='" + this.httpProxyUser + '\'' + ", httpProxyPassword='" + this.httpProxyPassword + '\'' + ", httpProxyPort=" + this.httpProxyPort + ", httpConnectionTimeout=" + this.httpConnectionTimeout + ", httpReadTimeout=" + this.httpReadTimeout + ", httpStreamingReadTimeout=" + this.httpStreamingReadTimeout + ", httpRetryCount=" + this.httpRetryCount + ", httpRetryIntervalSeconds=" + this.httpRetryIntervalSeconds + ", maxTotalConnections=" + this.maxTotalConnections + ", defaultMaxPerRoute=" + this.defaultMaxPerRoute + ", oAuthConsumerKey='" + this.oAuthConsumerKey + '\'' + ", oAuthConsumerSecret='" + this.oAuthConsumerSecret + '\'' + ", oAuthAccessToken='" + this.oAuthAccessToken + '\'' + ", oAuthAccessTokenSecret='" + this.oAuthAccessTokenSecret + '\'' + ", oAuth2TokenType='" + this.oAuth2TokenType + '\'' + ", oAuth2AccessToken='" + this.oAuth2AccessToken + '\'' + ", oAuthRequestTokenURL='" + this.oAuthRequestTokenURL + '\'' + ", oAuthAuthorizationURL='" + this.oAuthAuthorizationURL + '\'' + ", oAuthAccessTokenURL='" + this.oAuthAccessTokenURL + '\'' + ", oAuthAuthenticationURL='" + this.oAuthAuthenticationURL + '\'' + ", oAuth2TokenURL='" + this.oAuth2TokenURL + '\'' + ", oAuth2InvalidateTokenURL='" + this.oAuth2InvalidateTokenURL + '\'' + ", restBaseURL='" + this.restBaseURL + '\'' + ", streamBaseURL='" + this.streamBaseURL + '\'' + ", userStreamBaseURL='" + this.userStreamBaseURL + '\'' + ", siteStreamBaseURL='" + this.siteStreamBaseURL + '\'' + ", dispatcherImpl='" + this.dispatcherImpl + '\'' + ", loggerFactory='" + this.loggerFactory + '\'' + ", asyncNumThreads=" + this.asyncNumThreads + ", contributingTo=" + this.contributingTo + ", includeRTsEnabled=" + this.includeRTsEnabled + ", includeEntitiesEnabled=" + this.includeEntitiesEnabled + ", includeMyRetweetEnabled=" + this.includeMyRetweetEnabled + ", trimUserEnabled=" + this.trimUserEnabled + ", jsonStoreEnabled=" + this.jsonStoreEnabled + ", mbeanEnabled=" + this.mbeanEnabled + ", userStreamRepliesAllEnabled=" + this.userStreamRepliesAllEnabled + ", stallWarningsEnabled=" + this.stallWarningsEnabled + ", applicationOnlyAuthEnabled=" + this.applicationOnlyAuthEnabled + ", mediaProvider='" + this.mediaProvider + '\'' + ", mediaProviderAPIKey='" + this.mediaProviderAPIKey + '\'' + ", mediaProviderParameters=" + this.mediaProviderParameters + ", clientVersion='" + this.clientVersion + '\'' + ", clientURL='" + this.clientURL + '\'' + ", IS_DALVIK=" + this.IS_DALVIK + ", IS_GAE=" + this.IS_GAE + ", requestHeaders=" + this.requestHeaders + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.conf.ConfigurationBase
 * JD-Core Version:    0.7.0.1
 */