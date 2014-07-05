package twitter4j.internal.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class HttpResponseImpl
  extends HttpResponse
{
  private HttpURLConnection con;
  
  HttpResponseImpl(String paramString)
  {
    this.responseAsString = paramString;
  }
  
  HttpResponseImpl(HttpURLConnection paramHttpURLConnection, HttpClientConfiguration paramHttpClientConfiguration)
    throws IOException
  {
    super(paramHttpClientConfiguration);
    this.con = paramHttpURLConnection;
    this.statusCode = paramHttpURLConnection.getResponseCode();
    InputStream localInputStream = paramHttpURLConnection.getErrorStream();
    this.is = localInputStream;
    if (localInputStream == null) {
      this.is = paramHttpURLConnection.getInputStream();
    }
    if ((this.is != null) && ("gzip".equals(paramHttpURLConnection.getContentEncoding()))) {
      this.is = new StreamingGZIPInputStream(this.is);
    }
  }
  
  public void disconnect()
  {
    this.con.disconnect();
  }
  
  public String getResponseHeader(String paramString)
  {
    return this.con.getHeaderField(paramString);
  }
  
  public Map<String, List<String>> getResponseHeaderFields()
  {
    return this.con.getHeaderFields();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.http.HttpResponseImpl
 * JD-Core Version:    0.7.0.1
 */