package jp.co.asbit.pvstar.video;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

public class NicoNico
{
  private static final String INFO_URL = "http://flapi.nicovideo.jp/api/getflv";
  private static final String LOGIN_URL = "https://secure.nicovideo.jp/secure/login?site=niconico";
  private static final String MYLIST_URL = "http://www.nicovideo.jp/my/mylist";
  private static final String TAG = "NicoNico";
  public static final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)";
  public static final String WATCH_URL = "http://www.nicovideo.jp/watch/%s";
  private static HashMap<String, String> cookies = new HashMap();
  private static HttpClient httpClient;
  private static boolean lowQuarity = false;
  private static NicoNico nico;
  private String userId;
  private String userPasswd;
  
  public NicoNico(String paramString1, String paramString2)
  {
    httpClient = new HttpClient();
    this.userId = paramString1;
    this.userPasswd = paramString2;
  }
  
  public static String getCookie()
  {
    Iterator localIterator = cookies.keySet().iterator();
    StringBuilder localStringBuilder = new StringBuilder();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return localStringBuilder.toString().trim();
      }
      String str1 = (String)localIterator.next();
      String str2 = (String)cookies.get(str1);
      localStringBuilder.append(str1 + "=" + str2 + "; ");
    }
  }
  
  private String getToken()
  {
    String str1 = null;
    if (httpClient != null)
    {
      httpClient.clear();
      httpClient.setUrl("http://www.nicovideo.jp/my/mylist");
      if (httpClient.request())
      {
        String str2 = httpClient.getResponseBody();
        Matcher localMatcher = Pattern.compile("NicoAPI\\.token = \"(.*?)\";").matcher(str2);
        if (localMatcher.find()) {
          str1 = localMatcher.group(1);
        }
      }
    }
    return str1;
  }
  
  private String getUrl(String paramString)
  {
    Object localObject = null;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString;
    String str1 = String.format("http://www.nicovideo.jp/watch/%s", arrayOfObject);
    httpClient.clear();
    httpClient.setUrl("http://flapi.nicovideo.jp/api/getflv");
    httpClient.setRequestMethod(2);
    httpClient.setParameter("v", paramString);
    if (lowQuarity)
    {
      httpClient.setParameter("eco", "1");
      str1 = str1 + "?eco=1";
    }
    httpClient.addHeader("Referer", str1);
    httpClient.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
    Matcher localMatcher;
    if (httpClient.request())
    {
      localMatcher = Pattern.compile("&?url=([^&]+)&?").matcher(httpClient.getResponseBody());
      if (!localMatcher.find()) {}
    }
    try
    {
      String str2 = URLDecoder.decode(localMatcher.group(1), "UTF-8");
      localObject = str2;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;)
      {
        localUnsupportedEncodingException.printStackTrace();
      }
    }
    return localObject;
  }
  
  public static String getVideoUrl(String paramString1, String paramString2, String paramString3)
  {
    if (nico == null) {
      nico = new NicoNico(paramString2, paramString3);
    }
    return nico.getVideoUrl(paramString1);
  }
  
  public static String getVideoUrlForLowQuarity(String paramString1, String paramString2, String paramString3)
  {
    lowQuarity = true;
    return getVideoUrl(paramString1, paramString2, paramString3);
  }
  
  private boolean login()
  {
    bool1 = false;
    httpClient.clear();
    httpClient.setUrl("https://secure.nicovideo.jp/secure/login?site=niconico");
    httpClient.setRequestMethod(2);
    httpClient.setParameter("next_url", "/my/top");
    httpClient.setParameter("show_button_facebook", "1");
    httpClient.setParameter("show_button_twitter", "1");
    httpClient.setParameter("mail_tel", this.userId);
    httpClient.setParameter("password", this.userPasswd);
    httpClient.addHeader("Referer", "https://secure.nicovideo.jp/secure/login?site=niconico");
    httpClient.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
    if (httpClient.request()) {}
    for (;;)
    {
      try
      {
        localIterator1 = httpClient.getCookies().iterator();
        boolean bool2 = localIterator1.hasNext();
        if (bool2) {}
      }
      catch (NullPointerException localNullPointerException1)
      {
        Iterator localIterator1;
        String str3;
        boolean bool3;
        Cookie localCookie1;
        String str1;
        String str2;
        localNullPointerException1.printStackTrace();
        continue;
        httpClient.setUrl(str3);
        httpClient.setRequestMethod(1);
        httpClient.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
        if (!httpClient.request()) {
          continue;
        }
        Iterator localIterator2 = httpClient.getCookies().iterator();
        if (!localIterator2.hasNext())
        {
          if (Integer.parseInt(httpClient.getResponseHeader("x-niconico-authflag").getValue()) <= 0) {
            continue;
          }
          bool1 = true;
          continue;
        }
        Cookie localCookie2 = (Cookie)localIterator2.next();
        String str4 = localCookie2.getName();
        String str5 = localCookie2.getValue();
        cookies.put(str4, str5);
        continue;
      }
      try
      {
        str3 = httpClient.getResponseHeader("location").getValue();
        bool3 = str3.contains("cant_login");
        if (!bool3) {
          continue;
        }
      }
      catch (NullPointerException localNullPointerException2)
      {
        localNullPointerException2.printStackTrace();
        continue;
      }
      return bool1;
      localCookie1 = (Cookie)localIterator1.next();
      str1 = localCookie1.getName();
      str2 = localCookie1.getValue();
      cookies.put(str1, str2);
    }
  }
  
  public static boolean login(String paramString1, String paramString2)
  {
    nico = new NicoNico(paramString1, paramString2);
    return nico.login();
  }
  
  private boolean setHistory(String paramString)
  {
    boolean bool = false;
    if (cookies == null) {}
    for (;;)
    {
      return bool;
      httpClient.clear();
      httpClient.setRequestMethod(1);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[bool] = paramString;
      String str1 = String.format("http://www.nicovideo.jp/watch/%s", arrayOfObject);
      if (lowQuarity) {
        str1 = str1 + "?eco=1";
      }
      httpClient.setUrl(str1);
      httpClient.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
      if (!httpClient.request()) {
        break label171;
      }
      if (Integer.valueOf(httpClient.getResponseHeader("x-niconico-authflag").getValue()).intValue() > 0) {
        break;
      }
      Log.d("NicoNico", "ログインしていません。再ログインを試行します。");
      cookies.clear();
      if (login()) {
        bool = setHistory(paramString);
      }
    }
    Iterator localIterator = httpClient.getCookies().iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        label171:
        bool = true;
        break;
      }
      Cookie localCookie = (Cookie)localIterator.next();
      String str2 = localCookie.getName();
      String str3 = localCookie.getValue();
      cookies.put(str2, str3);
    }
  }
  
  public static String token()
  {
    if (nico != null) {}
    for (String str = nico.getToken();; str = null) {
      return str;
    }
  }
  
  public String getVideoUrl(String paramString)
  {
    try
    {
      if (cookies == null) {
        login();
      }
      if (!setHistory(paramString)) {
        break label44;
      }
      String str2 = getUrl(paramString);
      str1 = str2;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        Log.d("NicoNico", localNullPointerException.getMessage());
        label44:
        String str1 = null;
      }
    }
    return str1;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.video.NicoNico
 * JD-Core Version:    0.7.0.1
 */