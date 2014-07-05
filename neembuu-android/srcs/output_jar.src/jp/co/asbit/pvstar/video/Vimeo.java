package jp.co.asbit.pvstar.video;

import android.util.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.co.asbit.pvstar.Constants.Quality;
import org.json.JSONException;
import org.json.JSONObject;

public class Vimeo
{
  private static final String TAG = "Vimeo";
  public static final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)";
  public static final String WATCH_URL = "http://vimeo.com/%s";
  
  private static String[] getQuality(Constants.Quality paramQuality)
  {
    String[] arrayOfString1 = new String[3];
    arrayOfString1[0] = "sd";
    arrayOfString1[1] = "mobile";
    arrayOfString1[2] = "hd";
    String[] arrayOfString2 = new String[3];
    arrayOfString2[0] = "mobile";
    arrayOfString2[1] = "sd";
    arrayOfString2[2] = "hd";
    String[] arrayOfString3 = new String[3];
    arrayOfString3[0] = "hd";
    arrayOfString3[1] = "sd";
    arrayOfString3[2] = "mobile";
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
    HttpClient localHttpClient1 = new HttpClient(String.format("http://vimeo.com/%s", arrayOfObject));
    localHttpClient1.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
    String str3;
    if (!localHttpClient1.request())
    {
      localHttpClient1.shutdown();
      str3 = null;
    }
    HttpClient localHttpClient2;
    for (;;)
    {
      return str3;
      String str1 = localHttpClient1.getResponseBody();
      Matcher localMatcher = Pattern.compile("data-config-url=\"(.*?)\"").matcher(str1);
      if (!localMatcher.find())
      {
        localHttpClient1.shutdown();
        str3 = null;
      }
      else
      {
        localHttpClient2 = new HttpClient(localMatcher.group(1).replaceAll("&amp;", "&"));
        localHttpClient2.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
        if (localHttpClient2.request()) {
          break;
        }
        localHttpClient2.shutdown();
        str3 = null;
      }
    }
    String str2 = localHttpClient2.getResponseBody();
    HashMap localHashMap = new HashMap();
    for (;;)
    {
      int i;
      try
      {
        JSONObject localJSONObject1 = new JSONObject(str2).getJSONObject("request").getJSONObject("files").getJSONObject("h264");
        Iterator localIterator = localJSONObject1.keys();
        boolean bool = localIterator.hasNext();
        if (!bool)
        {
          str3 = null;
          String[] arrayOfString = getQuality(paramQuality);
          i = 0;
          if (i >= arrayOfString.length) {
            break;
          }
          if (!localHashMap.containsKey(arrayOfString[i])) {
            break label345;
          }
          str3 = (String)localHashMap.get(arrayOfString[i]);
          break;
        }
        String str4 = (String)localIterator.next();
        JSONObject localJSONObject2 = localJSONObject1.getJSONObject(str4);
        localHashMap.put(str4, localJSONObject2.getString("url"));
        Log.d("Vimeo", str4 + ":" + localJSONObject2.getString("url"));
        continue;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        str3 = null;
      }
      label345:
      i++;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.video.Vimeo
 * JD-Core Version:    0.7.0.1
 */