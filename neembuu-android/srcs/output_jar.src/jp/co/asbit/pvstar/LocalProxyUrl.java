package jp.co.asbit.pvstar;

public class LocalProxyUrl
{
  private String cookie = null;
  private String key = null;
  private String url = null;
  private String useragent = null;
  
  public LocalProxyUrl() {}
  
  public LocalProxyUrl(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.key = paramString1;
    this.url = paramString2;
    this.cookie = paramString3;
    this.useragent = paramString4;
  }
  
  public String getCookie()
  {
    return this.cookie;
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public String getUseragent()
  {
    return this.useragent;
  }
  
  public void setCookie(String paramString)
  {
    this.cookie = paramString;
  }
  
  public void setKey(String paramString)
  {
    this.key = paramString;
  }
  
  public void setUrl(String paramString)
  {
    this.url = paramString;
  }
  
  public void setUseragent(String paramString)
  {
    this.useragent = paramString;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.LocalProxyUrl
 * JD-Core Version:    0.7.0.1
 */