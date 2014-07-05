package twitter4j.internal.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import twitter4j.internal.logging.Logger;

public abstract class HttpClientBase
  implements HttpClient, Serializable
{
  private static final Logger logger = Logger.getLogger(HttpClientBase.class);
  private static final long serialVersionUID = 6944924907755685265L;
  protected final HttpClientConfiguration CONF;
  
  public HttpClientBase(HttpClientConfiguration paramHttpClientConfiguration)
  {
    this.CONF = paramHttpClientConfiguration;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    for (;;)
    {
      return bool;
      if (!(paramObject instanceof HttpClientBase))
      {
        bool = false;
      }
      else
      {
        HttpClientBase localHttpClientBase = (HttpClientBase)paramObject;
        if (!this.CONF.equals(localHttpClientBase.CONF)) {
          bool = false;
        }
      }
    }
  }
  
  public int hashCode()
  {
    return this.CONF.hashCode();
  }
  
  protected boolean isProxyConfigured()
  {
    if ((this.CONF.getHttpProxyHost() != null) && (!this.CONF.getHttpProxyHost().equals(""))) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public void shutdown() {}
  
  public String toString()
  {
    return "HttpClientBase{CONF=" + this.CONF + '}';
  }
  
  public void write(DataOutputStream paramDataOutputStream, String paramString)
    throws IOException
  {
    paramDataOutputStream.writeBytes(paramString);
    logger.debug(paramString);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.http.HttpClientBase
 * JD-Core Version:    0.7.0.1
 */