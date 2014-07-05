package twitter4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.AuthorizationFactory;
import twitter4j.auth.BasicAuthorization;
import twitter4j.auth.NullAuthorization;
import twitter4j.auth.OAuth2Authorization;
import twitter4j.auth.OAuth2Support;
import twitter4j.auth.OAuth2Token;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.OAuthSupport;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpClientWrapper;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.http.HttpResponseEvent;
import twitter4j.internal.http.HttpResponseListener;
import twitter4j.internal.http.XAuthAuthorization;
import twitter4j.internal.json.z_T4JInternalFactory;
import twitter4j.internal.json.z_T4JInternalJSONImplFactory;

abstract class TwitterBaseImpl
  implements TwitterBase, Serializable, OAuthSupport, OAuth2Support, HttpResponseListener
{
  private static final long serialVersionUID = -3812176145960812140L;
  protected Authorization auth;
  protected Configuration conf;
  protected z_T4JInternalFactory factory;
  protected transient HttpClientWrapper http;
  protected transient long id = 0L;
  private List<RateLimitStatusListener> rateLimitStatusListeners = new ArrayList(0);
  protected transient String screenName = null;
  
  TwitterBaseImpl(Configuration paramConfiguration, Authorization paramAuthorization)
  {
    this.conf = paramConfiguration;
    this.auth = paramAuthorization;
    init();
  }
  
  private OAuthSupport getOAuth()
  {
    if (!(this.auth instanceof OAuthSupport)) {
      throw new IllegalStateException("OAuth consumer key/secret combination not supplied");
    }
    return (OAuthSupport)this.auth;
  }
  
  private OAuth2Support getOAuth2()
  {
    if (!(this.auth instanceof OAuth2Support)) {
      throw new IllegalStateException("OAuth consumer key/secret combination not supplied");
    }
    return (OAuth2Support)this.auth;
  }
  
  private void init()
  {
    if (this.auth == null)
    {
      String str1 = this.conf.getOAuthConsumerKey();
      String str2 = this.conf.getOAuthConsumerSecret();
      if ((str1 == null) || (str2 == null)) {
        break label209;
      }
      if (!this.conf.isApplicationOnlyAuthEnabled()) {
        break label139;
      }
      OAuth2Authorization localOAuth2Authorization = new OAuth2Authorization(this.conf);
      String str3 = this.conf.getOAuth2TokenType();
      String str4 = this.conf.getOAuth2AccessToken();
      if ((str3 != null) && (str4 != null)) {
        localOAuth2Authorization.setOAuth2Token(new OAuth2Token(str3, str4));
      }
      this.auth = localOAuth2Authorization;
    }
    for (;;)
    {
      this.http = new HttpClientWrapper(this.conf);
      this.http.setHttpResponseListener(this);
      setFactory();
      return;
      label139:
      OAuthAuthorization localOAuthAuthorization = new OAuthAuthorization(this.conf);
      String str5 = this.conf.getOAuthAccessToken();
      String str6 = this.conf.getOAuthAccessTokenSecret();
      if ((str5 != null) && (str6 != null)) {
        localOAuthAuthorization.setOAuthAccessToken(new AccessToken(str5, str6));
      }
      this.auth = localOAuthAuthorization;
      continue;
      label209:
      this.auth = NullAuthorization.getInstance();
    }
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.readFields();
    this.conf = ((Configuration)paramObjectInputStream.readObject());
    this.auth = ((Authorization)paramObjectInputStream.readObject());
    this.rateLimitStatusListeners = ((List)paramObjectInputStream.readObject());
    this.http = new HttpClientWrapper(this.conf);
    this.http.setHttpResponseListener(this);
    setFactory();
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.putFields();
    paramObjectOutputStream.writeFields();
    paramObjectOutputStream.writeObject(this.conf);
    paramObjectOutputStream.writeObject(this.auth);
    ArrayList localArrayList = new ArrayList(0);
    Iterator localIterator = this.rateLimitStatusListeners.iterator();
    while (localIterator.hasNext())
    {
      RateLimitStatusListener localRateLimitStatusListener = (RateLimitStatusListener)localIterator.next();
      if ((localRateLimitStatusListener instanceof Serializable)) {
        localArrayList.add(localRateLimitStatusListener);
      }
    }
    paramObjectOutputStream.writeObject(localArrayList);
  }
  
  public void addRateLimitStatusListener(RateLimitStatusListener paramRateLimitStatusListener)
  {
    this.rateLimitStatusListeners.add(paramRateLimitStatusListener);
  }
  
  protected final void ensureAuthorizationEnabled()
  {
    if (!this.auth.isEnabled()) {
      throw new IllegalStateException("Authentication credentials are missing. See http://twitter4j.org/en/configuration.html for the detail.");
    }
  }
  
  protected final void ensureOAuthEnabled()
  {
    if (!(this.auth instanceof OAuthAuthorization)) {
      throw new IllegalStateException("OAuth required. Authentication credentials are missing. See http://twitter4j.org/en/configuration.html for the detail.");
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    for (;;)
    {
      return bool;
      if (!(paramObject instanceof TwitterBaseImpl))
      {
        bool = false;
      }
      else
      {
        TwitterBaseImpl localTwitterBaseImpl = (TwitterBaseImpl)paramObject;
        if (this.auth != null)
        {
          if (this.auth.equals(localTwitterBaseImpl.auth)) {}
        }
        else {
          while (localTwitterBaseImpl.auth != null)
          {
            bool = false;
            break;
          }
        }
        if (!this.conf.equals(localTwitterBaseImpl.conf))
        {
          bool = false;
        }
        else
        {
          if (this.http != null)
          {
            if (this.http.equals(localTwitterBaseImpl.http)) {}
          }
          else {
            while (localTwitterBaseImpl.http != null)
            {
              bool = false;
              break;
            }
          }
          if (!this.rateLimitStatusListeners.equals(localTwitterBaseImpl.rateLimitStatusListeners)) {
            bool = false;
          }
        }
      }
    }
  }
  
  protected User fillInIDAndScreenName()
    throws TwitterException
  {
    ensureAuthorizationEnabled();
    User localUser = this.factory.createUser(this.http.get(this.conf.getRestBaseURL() + "account/verify_credentials.json", this.auth));
    this.screenName = localUser.getScreenName();
    this.id = localUser.getId();
    return localUser;
  }
  
  public final Authorization getAuthorization()
  {
    return this.auth;
  }
  
  public Configuration getConfiguration()
  {
    return this.conf;
  }
  
  public long getId()
    throws TwitterException, IllegalStateException
  {
    if (!this.auth.isEnabled()) {
      throw new IllegalStateException("Neither user ID/password combination nor OAuth consumer key/secret combination supplied");
    }
    if (0L == this.id) {
      fillInIDAndScreenName();
    }
    return this.id;
  }
  
  /**
   * @deprecated
   */
  public OAuth2Token getOAuth2Token()
    throws TwitterException
  {
    try
    {
      OAuth2Token localOAuth2Token = getOAuth2().getOAuth2Token();
      return localOAuth2Token;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public AccessToken getOAuthAccessToken()
    throws TwitterException
  {
    for (;;)
    {
      Authorization localAuthorization1;
      Object localObject2;
      try
      {
        localAuthorization1 = getAuthorization();
        if ((localAuthorization1 instanceof BasicAuthorization))
        {
          BasicAuthorization localBasicAuthorization = (BasicAuthorization)localAuthorization1;
          Authorization localAuthorization2 = AuthorizationFactory.getInstance(this.conf);
          if ((localAuthorization2 instanceof OAuthAuthorization))
          {
            this.auth = localAuthorization2;
            localObject2 = ((OAuthAuthorization)localAuthorization2).getOAuthAccessToken(localBasicAuthorization.getUserId(), localBasicAuthorization.getPassword());
            this.screenName = ((AccessToken)localObject2).getScreenName();
            this.id = ((AccessToken)localObject2).getUserId();
            return localObject2;
          }
          throw new IllegalStateException("consumer key / secret combination not supplied.");
        }
      }
      finally {}
      if ((localAuthorization1 instanceof XAuthAuthorization))
      {
        XAuthAuthorization localXAuthAuthorization = (XAuthAuthorization)localAuthorization1;
        this.auth = localXAuthAuthorization;
        OAuthAuthorization localOAuthAuthorization = new OAuthAuthorization(this.conf);
        localOAuthAuthorization.setOAuthConsumer(localXAuthAuthorization.getConsumerKey(), localXAuthAuthorization.getConsumerSecret());
        localObject2 = localOAuthAuthorization.getOAuthAccessToken(localXAuthAuthorization.getUserId(), localXAuthAuthorization.getPassword());
      }
      else
      {
        AccessToken localAccessToken = getOAuth().getOAuthAccessToken();
        localObject2 = localAccessToken;
      }
    }
  }
  
  /**
   * @deprecated
   */
  public AccessToken getOAuthAccessToken(String paramString)
    throws TwitterException
  {
    try
    {
      AccessToken localAccessToken = getOAuth().getOAuthAccessToken(paramString);
      this.screenName = localAccessToken.getScreenName();
      return localAccessToken;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public AccessToken getOAuthAccessToken(String paramString1, String paramString2)
    throws TwitterException
  {
    try
    {
      AccessToken localAccessToken = getOAuth().getOAuthAccessToken(paramString1, paramString2);
      return localAccessToken;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public AccessToken getOAuthAccessToken(RequestToken paramRequestToken)
    throws TwitterException
  {
    try
    {
      AccessToken localAccessToken = getOAuth().getOAuthAccessToken(paramRequestToken);
      this.screenName = localAccessToken.getScreenName();
      return localAccessToken;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public AccessToken getOAuthAccessToken(RequestToken paramRequestToken, String paramString)
    throws TwitterException
  {
    try
    {
      AccessToken localAccessToken = getOAuth().getOAuthAccessToken(paramRequestToken, paramString);
      return localAccessToken;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public RequestToken getOAuthRequestToken()
    throws TwitterException
  {
    return getOAuthRequestToken(null);
  }
  
  public RequestToken getOAuthRequestToken(String paramString)
    throws TwitterException
  {
    return getOAuth().getOAuthRequestToken(paramString);
  }
  
  public RequestToken getOAuthRequestToken(String paramString1, String paramString2)
    throws TwitterException
  {
    return getOAuth().getOAuthRequestToken(paramString1, paramString2);
  }
  
  public String getScreenName()
    throws TwitterException, IllegalStateException
  {
    if (!this.auth.isEnabled()) {
      throw new IllegalStateException("Neither user ID/password combination nor OAuth consumer key/secret combination supplied");
    }
    if (this.screenName == null)
    {
      if ((this.auth instanceof BasicAuthorization))
      {
        this.screenName = ((BasicAuthorization)this.auth).getUserId();
        if (-1 != this.screenName.indexOf("@")) {
          this.screenName = null;
        }
      }
      if (this.screenName == null) {
        fillInIDAndScreenName();
      }
    }
    return this.screenName;
  }
  
  public int hashCode()
  {
    int i = 0;
    int j = 31 * this.conf.hashCode();
    if (this.http != null) {}
    for (int k = this.http.hashCode();; k = 0)
    {
      int m = 31 * (31 * (j + k) + this.rateLimitStatusListeners.hashCode());
      if (this.auth != null) {
        i = this.auth.hashCode();
      }
      return m + i;
    }
  }
  
  public void httpResponseReceived(HttpResponseEvent paramHttpResponseEvent)
  {
    if (this.rateLimitStatusListeners.size() != 0)
    {
      HttpResponse localHttpResponse = paramHttpResponseEvent.getResponse();
      TwitterException localTwitterException = paramHttpResponseEvent.getTwitterException();
      RateLimitStatus localRateLimitStatus;
      int i;
      if (localTwitterException != null)
      {
        localRateLimitStatus = localTwitterException.getRateLimitStatus();
        i = localTwitterException.getStatusCode();
      }
      while (localRateLimitStatus != null)
      {
        RateLimitStatusEvent localRateLimitStatusEvent = new RateLimitStatusEvent(this, localRateLimitStatus, paramHttpResponseEvent.isAuthenticated());
        Iterator localIterator1;
        if ((i == 420) || (i == 503)) {
          localIterator1 = this.rateLimitStatusListeners.iterator();
        }
        for (;;)
        {
          if (localIterator1.hasNext())
          {
            RateLimitStatusListener localRateLimitStatusListener = (RateLimitStatusListener)localIterator1.next();
            localRateLimitStatusListener.onRateLimitStatus(localRateLimitStatusEvent);
            localRateLimitStatusListener.onRateLimitReached(localRateLimitStatusEvent);
            continue;
            localRateLimitStatus = z_T4JInternalJSONImplFactory.createRateLimitStatusFromResponseHeader(localHttpResponse);
            i = localHttpResponse.getStatusCode();
            break;
            Iterator localIterator2 = this.rateLimitStatusListeners.iterator();
            while (localIterator2.hasNext()) {
              ((RateLimitStatusListener)localIterator2.next()).onRateLimitStatus(localRateLimitStatusEvent);
            }
          }
        }
      }
    }
  }
  
  /**
   * @deprecated
   */
  public void invalidateOAuth2Token()
    throws TwitterException
  {
    try
    {
      getOAuth2().invalidateOAuth2Token();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  protected void setFactory()
  {
    this.factory = new z_T4JInternalJSONImplFactory(this.conf);
  }
  
  public void setOAuth2Token(OAuth2Token paramOAuth2Token)
  {
    getOAuth2().setOAuth2Token(paramOAuth2Token);
  }
  
  /**
   * @deprecated
   */
  public void setOAuthAccessToken(AccessToken paramAccessToken)
  {
    try
    {
      getOAuth().setOAuthAccessToken(paramAccessToken);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public void setOAuthConsumer(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      try
      {
        throw new NullPointerException("consumer key is null");
      }
      finally {}
    }
    if (paramString2 == null) {
      throw new NullPointerException("consumer secret is null");
    }
    if ((this.auth instanceof NullAuthorization)) {
      if (this.conf.isApplicationOnlyAuthEnabled())
      {
        OAuth2Authorization localOAuth2Authorization = new OAuth2Authorization(this.conf);
        localOAuth2Authorization.setOAuthConsumer(paramString1, paramString2);
        this.auth = localOAuth2Authorization;
      }
    }
    do
    {
      for (;;)
      {
        return;
        OAuthAuthorization localOAuthAuthorization = new OAuthAuthorization(this.conf);
        localOAuthAuthorization.setOAuthConsumer(paramString1, paramString2);
        this.auth = localOAuthAuthorization;
        continue;
        if (!(this.auth instanceof BasicAuthorization)) {
          break;
        }
        XAuthAuthorization localXAuthAuthorization = new XAuthAuthorization((BasicAuthorization)this.auth);
        localXAuthAuthorization.setOAuthConsumer(paramString1, paramString2);
        this.auth = localXAuthAuthorization;
      }
    } while ((!(this.auth instanceof OAuthAuthorization)) && (!(this.auth instanceof OAuth2Authorization)));
    throw new IllegalStateException("consumer key/secret pair already set.");
  }
  
  public void shutdown()
  {
    if (this.http != null) {
      this.http.shutdown();
    }
  }
  
  public String toString()
  {
    return "TwitterBase{conf=" + this.conf + ", http=" + this.http + ", rateLimitStatusListeners=" + this.rateLimitStatusListeners + ", auth=" + this.auth + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.TwitterBaseImpl
 * JD-Core Version:    0.7.0.1
 */