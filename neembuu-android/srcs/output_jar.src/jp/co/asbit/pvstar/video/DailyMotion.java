package jp.co.asbit.pvstar.video;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.co.asbit.pvstar.Constants.Quality;
import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

public class DailyMotion
{
  private static final String TAG = "DailyMotion";
  public static final String USER_AGENT = "Mozilla/5.0 (Linux; U; Android 2.2.1; ja-jp; IS05 Build/S2251) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
  public static final String WATCH_URL = "http://www.dailymotion.com/embed/video/%s?api=postMessage&autoplay=false&fullscreen=auto&html=false&info=false&sc_insite_webapp=true";
  private static HashMap<String, String> cookies = new HashMap();
  
  public static String getCookie()
  {
    cookies.put("cookie_enabled", "y");
    cookies.put("cto_dailymotionjp", "");
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
  
  private static String[] getQuality(Constants.Quality paramQuality)
  {
    String[] arrayOfString1 = new String[3];
    arrayOfString1[0] = "stream_h264_url";
    arrayOfString1[1] = "stream_h264_ld_url";
    arrayOfString1[2] = "stream_h264_hq_url";
    String[] arrayOfString2 = new String[3];
    arrayOfString2[0] = "stream_h264_ld_url";
    arrayOfString2[1] = "stream_h264_url";
    arrayOfString2[2] = "stream_h264_hq_url";
    String[] arrayOfString3 = new String[3];
    arrayOfString3[0] = "stream_h264_hq_url";
    arrayOfString3[1] = "stream_h264_url";
    arrayOfString3[2] = "stream_h264_ld_url";
    switch (paramQuality)
    {
    }
    for (arrayOfString3 = arrayOfString1;; arrayOfString3 = arrayOfString2) {
      return arrayOfString3;
    }
  }
  
  public static String getVideoUrl(String paramString1, String paramString2, String paramString3, Constants.Quality paramQuality)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString1;
    HttpClient localHttpClient1 = new HttpClient(String.format("http://www.dailymotion.com/embed/video/%s?api=postMessage&autoplay=false&fullscreen=auto&html=false&info=false&sc_insite_webapp=true", arrayOfObject));
    localHttpClient1.addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 2.2.1; ja-jp; IS05 Build/S2251) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
    Object localObject;
    if (!localHttpClient1.request())
    {
      localHttpClient1.shutdown();
      localObject = null;
    }
    for (;;)
    {
      return localObject;
      for (;;)
      {
        for (;;)
        {
          String str3;
          String[] arrayOfString1;
          HashMap localHashMap;
          int i;
          String[] arrayOfString2;
          int j;
          try
          {
            localIterator = localHttpClient1.getCookies().iterator();
            boolean bool = localIterator.hasNext();
            if (!bool)
            {
              str3 = localHttpClient1.getResponseBody();
              arrayOfString1 = new String[5];
              arrayOfString1[0] = "stream_h264_ld_url";
              arrayOfString1[1] = "stream_h264_url";
              arrayOfString1[2] = "stream_h264_hq_url";
              arrayOfString1[3] = "stream_h264_hd_url";
              arrayOfString1[4] = "stream_h264_hd1080_url";
              localHashMap = new HashMap();
              i = 0;
              if (i < arrayOfString1.length) {
                break label270;
              }
              localObject = null;
              arrayOfString2 = getQuality(paramQuality);
              j = 0;
              if (j < arrayOfString2.length) {
                break label343;
              }
              localHttpClient2 = new HttpClient((String)localObject);
              localHttpClient2.addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 2.2.1; ja-jp; IS05 Build/S2251) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
              if (!localHttpClient2.request()) {
                break;
              }
            }
          }
          catch (NullPointerException localNullPointerException1)
          {
            Iterator localIterator;
            HttpClient localHttpClient2;
            String str4;
            Cookie localCookie;
            String str1;
            String str2;
            localHttpClient1.shutdown();
            localObject = null;
          }
          label270:
          Matcher localMatcher;
          label343:
          try
          {
            str4 = localHttpClient2.getResponseHeader("location").getValue();
            localObject = str4;
          }
          catch (NullPointerException localNullPointerException2) {}
        }
        localCookie = (Cookie)localIterator.next();
        str1 = localCookie.getName();
        str2 = localCookie.getValue();
        cookies.put(str1, str2);
        continue;
        break;
        localMatcher = Pattern.compile("\"" + arrayOfString1[i] + "\": *\"([^\"]+)\"").matcher(str3);
        if (localMatcher.find()) {
          localHashMap.put(arrayOfString1[i], localMatcher.group(1).replace("\\/", "/"));
        }
        i++;
        continue;
        if (localHashMap.containsKey(arrayOfString2[j])) {
          localObject = (String)localHashMap.get(arrayOfString2[j]);
        } else {
          j++;
        }
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.video.DailyMotion
 * JD-Core Version:    0.7.0.1
 */