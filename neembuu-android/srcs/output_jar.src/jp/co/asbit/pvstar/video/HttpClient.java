package jp.co.asbit.pvstar.video;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpClient
{
  public static final int HTTP_GET = 1;
  public static final int HTTP_HEAD = 3;
  public static final int HTTP_POST = 2;
  public static final String TAG = "HttpClient";
  protected DefaultHttpClient client;
  protected List<Cookie> cookies;
  protected Params headers = new Params();
  protected int method = 1;
  protected Params params = new Params();
  protected HttpResponse response = null;
  protected String responseBody = null;
  protected URI uri;
  
  public HttpClient()
  {
    setClient();
  }
  
  public HttpClient(String paramString)
  {
    setUrl(paramString);
    setClient();
  }
  
  private String _read(HttpResponse paramHttpResponse)
    throws IllegalStateException, IOException
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(this.response.getEntity().getContent()));
    StringBuilder localStringBuilder = new StringBuilder();
    for (;;)
    {
      String str = localBufferedReader.readLine();
      if (str == null)
      {
        localBufferedReader.close();
        return localStringBuilder.toString();
      }
      localStringBuilder.append(str);
    }
  }
  
  private boolean get()
  {
    try
    {
      HttpGet localHttpGet = new HttpGet(this.uri);
      boolean bool;
      for (int i = 0;; i++)
      {
        if (i >= this.headers.size())
        {
          bool = response(this.client.execute(localHttpGet));
          break;
        }
        localHttpGet.addHeader(((NameValuePair)this.headers.get(i)).getName(), ((NameValuePair)this.headers.get(i)).getValue());
      }
      return bool;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      localClientProtocolException.printStackTrace();
      bool = false;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      bool = false;
    }
  }
  
  private boolean head()
  {
    try
    {
      HttpHead localHttpHead = new HttpHead(this.uri);
      boolean bool;
      for (int i = 0;; i++)
      {
        if (i >= this.headers.size())
        {
          bool = response(this.client.execute(localHttpHead));
          break;
        }
        localHttpHead.addHeader(((NameValuePair)this.headers.get(i)).getName(), ((NameValuePair)this.headers.get(i)).getValue());
      }
      return bool;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      localClientProtocolException.printStackTrace();
      bool = false;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      bool = false;
    }
  }
  
  private boolean post()
  {
    try
    {
      HttpPost localHttpPost = new HttpPost(this.uri);
      boolean bool;
      for (int i = 0;; i++)
      {
        if (i >= this.headers.size())
        {
          localHttpPost.setEntity(new UrlEncodedFormEntity(this.params));
          bool = response(this.client.execute(localHttpPost));
          break;
        }
        localHttpPost.addHeader(((NameValuePair)this.headers.get(i)).getName(), ((NameValuePair)this.headers.get(i)).getValue());
      }
      return bool;
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      localClientProtocolException.printStackTrace();
      bool = false;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      bool = false;
    }
  }
  
  private boolean response(HttpResponse paramHttpResponse)
    throws IllegalStateException, IOException
  {
    Log.d("HttpClient", "status: " + paramHttpResponse.getStatusLine().getStatusCode());
    if ((paramHttpResponse.getStatusLine().getStatusCode() != 200) && (paramHttpResponse.getStatusLine().getStatusCode() != 302)) {}
    for (boolean bool = false;; bool = true)
    {
      return bool;
      this.response = paramHttpResponse;
      if (this.method != 3) {
        this.responseBody = _read(paramHttpResponse);
      }
      this.cookies = this.client.getCookieStore().getCookies();
    }
  }
  
  private void setClient()
  {
    this.client = new DefaultHttpClient();
    HttpClientParams.setRedirecting(this.client.getParams(), false);
    HttpParams localHttpParams = this.client.getParams();
    HttpConnectionParams.setConnectionTimeout(localHttpParams, 5000);
    HttpConnectionParams.setSoTimeout(localHttpParams, 5000);
  }
  
  public void addHeader(String paramString1, String paramString2)
  {
    this.headers.put(paramString1, paramString2);
  }
  
  public void clear()
  {
    this.headers = new Params();
    this.params = new Params();
    this.method = 1;
    this.response = null;
    this.responseBody = null;
  }
  
  public void clearCookie()
  {
    this.client.getCookieStore().clear();
  }
  
  public List<Cookie> getCookies()
  {
    return this.cookies;
  }
  
  public String getResponseBody()
  {
    return this.responseBody;
  }
  
  public Header getResponseHeader(String paramString)
  {
    return this.response.getLastHeader(paramString);
  }
  
  public Header[] getResponseHeaders(String paramString)
  {
    return this.response.getHeaders(paramString);
  }
  
  public boolean request()
  {
    this.response = null;
    this.responseBody = null;
    try
    {
      switch (this.method)
      {
      case 3: 
        bool1 = head();
      }
    }
    catch (IllegalStateException localIllegalStateException)
    {
      boolean bool2;
      localIllegalStateException.printStackTrace();
      bool1 = false;
    }
    boolean bool1 = get();
    return bool1;
    bool2 = post();
    bool1 = bool2;
    return bool1;
  }
  
  public void setCookie(Cookie paramCookie)
  {
    this.client.getCookieStore().addCookie(paramCookie);
  }
  
  public void setParameter(String paramString1, String paramString2)
  {
    this.params.put(paramString1, paramString2);
  }
  
  public void setRequestMethod(int paramInt)
  {
    this.method = paramInt;
  }
  
  public void setUrl(String paramString)
  {
    try
    {
      this.uri = new URI(paramString);
      return;
    }
    catch (URISyntaxException localURISyntaxException)
    {
      for (;;)
      {
        localURISyntaxException.printStackTrace();
      }
    }
  }
  
  public void shutdown()
  {
    if (this.client != null)
    {
      this.client.getConnectionManager().shutdown();
      this.client = null;
    }
  }
  
  public static class Params
    extends ArrayList<NameValuePair>
  {
    private static final long serialVersionUID = 2525123L;
    
    public void put(String paramString1, String paramString2)
    {
      add(new BasicNameValuePair(paramString1, paramString2));
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.video.HttpClient
 * JD-Core Version:    0.7.0.1
 */