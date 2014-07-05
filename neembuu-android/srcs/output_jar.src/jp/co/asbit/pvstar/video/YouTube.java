package jp.co.asbit.pvstar.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.co.asbit.pvstar.Constants.Quality;
import jp.co.asbit.pvstar.Util;
import org.apache.http.cookie.Cookie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YouTube
{
  public static final String DEVELOPER_KEY = "AI39si7nl9N7o9H2jKBYsqw-YUicGIgegAnlZxPANFbDVNC0NNWmnlvg1NxtlU9p0zUS8x3-R5IrceH4FeG1UKjMJ112IGNIXw";
  private static final String LOGIN_URL = "https://www.google.com/accounts/ClientLogin";
  private static final String SIG_PERMS = "SIG_PERM_PARAMS";
  private static final String SIG_PERMS_API = "http://pvstar.dooga.org/api2/youtube_sig_perms";
  private static final String SIG_PERM_TIMESTAMP = "SIG_PERM_TIMESTAMP";
  private static final String SIG_PERM_TTL = "SIG_PERM_TTL";
  private static final long SIG_PERM_TTL_MAX = 3600000L;
  private static final String TAG = "YouTube";
  private static final String URL_MOBILE = "http://m.youtube.com/";
  public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0";
  public static final String USER_AGENT_MOBILE = "Mozilla/5.0 (Linux; U; Android 2.3.6; ja-jp; SC-02C Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
  public static final String WATCH_URL = "http://www.youtube.com/watch?v=%s";
  private static final String WATCH_URL_MOBILE_AJAX = "http://m.youtube.com/watch?ajax=1&feature=mhee&layout=mobile&tsp=1&v=%s";
  public static String auth;
  private static List<Cookie> cookies = new ArrayList();
  
  private static int[] getQuality(Constants.Quality paramQuality)
  {
    int[] arrayOfInt1 = new int[3];
    arrayOfInt1[0] = 18;
    arrayOfInt1[1] = 36;
    arrayOfInt1[2] = 22;
    int[] arrayOfInt2 = new int[3];
    arrayOfInt2[0] = 36;
    arrayOfInt2[1] = 18;
    arrayOfInt2[2] = 22;
    int[] arrayOfInt3 = new int[3];
    arrayOfInt3[0] = 22;
    arrayOfInt3[1] = 18;
    arrayOfInt3[2] = 36;
    switch (paramQuality)
    {
    }
    for (arrayOfInt3 = arrayOfInt1;; arrayOfInt3 = arrayOfInt2) {
      return arrayOfInt3;
    }
  }
  
  private static String getReverse(String paramString)
  {
    return new StringBuffer(paramString).reverse().toString();
  }
  
  private static String getSig(Context paramContext, String paramString)
  {
    for (;;)
    {
      try
      {
        String str1 = (String)getSigPerms(paramContext).get(String.valueOf(paramString.length()));
        try
        {
          JSONArray localJSONArray1 = new JSONArray(str1);
          Object localObject = "";
          int i = 0;
          int j = localJSONArray1.length();
          if (i >= j) {
            return localObject;
          }
          try
          {
            JSONArray localJSONArray2 = localJSONArray1.getJSONArray(i);
            String str3 = localObject + paramString.substring(localJSONArray2.getInt(0), localJSONArray2.getInt(1));
            localObject = str3;
            i++;
          }
          catch (JSONException localJSONException2)
          {
            if (!localJSONArray1.getString(i).equals("REV")) {
              continue;
            }
            String str2 = getReverse(paramString);
            paramString = str2;
            continue;
          }
          continue;
          localObject = null;
        }
        catch (JSONException localJSONException1)
        {
          localJSONException1.printStackTrace();
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  private static String getSigForPC(Context paramContext, String paramString)
  {
    return getSig(paramContext, paramString);
  }
  
  private static HashMap<String, String> getSigPerms(Context paramContext)
  {
    HashMap localHashMap = new HashMap();
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    long l1 = localSharedPreferences.getLong("SIG_PERM_TIMESTAMP", 0L);
    long l2 = localSharedPreferences.getLong("SIG_PERM_TTL", 60000L);
    String str1 = localSharedPreferences.getString("SIG_PERM_PARAMS", null);
    String str3;
    if ((str1 == null) || (System.currentTimeMillis() - l1 > l2))
    {
      HttpClient localHttpClient = new HttpClient("http://pvstar.dooga.org/api2/youtube_sig_perms");
      if (localHttpClient.request()) {
        str3 = localHttpClient.getResponseBody();
      }
    }
    try
    {
      JSONObject localJSONObject2 = new JSONObject(str3);
      long l3 = 1000L * localJSONObject2.getLong("ttl");
      if (l3 > 3600000L) {
        l3 = 3600000L;
      }
      str1 = localJSONObject2.getJSONObject("sig_perms").toString();
      SharedPreferences.Editor localEditor = localSharedPreferences.edit();
      localEditor.putLong("SIG_PERM_TIMESTAMP", System.currentTimeMillis());
      localEditor.putLong("SIG_PERM_TTL", l3);
      localEditor.putString("SIG_PERM_PARAMS", str1);
      localEditor.commit();
      try
      {
        localJSONObject1 = new JSONObject(str1);
        localIterator = localJSONObject1.keys();
        boolean bool = localIterator.hasNext();
        if (bool) {
          break label236;
        }
      }
      catch (JSONException localJSONException1)
      {
        for (;;)
        {
          JSONObject localJSONObject1;
          Iterator localIterator;
          String str2;
          localJSONException1.printStackTrace();
        }
      }
      return localHashMap;
    }
    catch (JSONException localJSONException2)
    {
      for (;;)
      {
        localJSONException2.printStackTrace();
        continue;
        label236:
        str2 = (String)localIterator.next();
        localHashMap.put(str2, localJSONObject1.getJSONArray(str2).toString());
      }
    }
  }
  
  @SuppressLint({"UseSparseArrays"})
  public static String getVideoUrl(Context paramContext, String paramString1, String paramString2, String paramString3, Constants.Quality paramQuality)
  {
    if (cookies.size() == 0)
    {
      HttpClient localHttpClient1 = new HttpClient("http://m.youtube.com/");
      localHttpClient1.addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 2.3.6; ja-jp; SC-02C Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
      if (localHttpClient1.request()) {
        cookies = localHttpClient1.getCookies();
      }
    }
    String str1 = null;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString1;
    HttpClient localHttpClient2 = new HttpClient(String.format("http://m.youtube.com/watch?ajax=1&feature=mhee&layout=mobile&tsp=1&v=%s", arrayOfObject));
    localHttpClient2.addHeader("User-Agent", "Mozilla/5.0 (Linux; U; Android 2.3.6; ja-jp; SC-02C Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
    localHttpClient2.addHeader("Referer", "http://m.youtube.com/");
    Iterator localIterator;
    HashMap localHashMap;
    String str3;
    if (cookies.size() > 0)
    {
      localIterator = cookies.iterator();
      if (localIterator.hasNext()) {}
    }
    else if (localHttpClient2.request())
    {
      cookies = localHttpClient2.getCookies();
      localHashMap = new HashMap();
      String str2 = localHttpClient2.getResponseBody();
      Matcher localMatcher = Pattern.compile("\\\"fmt_stream_map\\\": (\\[.*?\\])").matcher(str2);
      if (localMatcher.find()) {
        str3 = localMatcher.group(1).replaceAll("\\\"", "\"");
      }
    }
    for (;;)
    {
      try
      {
        localJSONArray = new JSONArray(str3);
        i = 0;
        if (i < localJSONArray.length()) {
          continue;
        }
        arrayOfInt = getQuality(paramQuality);
        k = 0;
        int m = arrayOfInt.length;
        if (k < m) {
          continue;
        }
      }
      catch (JSONException localJSONException1)
      {
        JSONArray localJSONArray;
        int i;
        int[] arrayOfInt;
        int k;
        Cookie localCookie;
        JSONObject localJSONObject;
        localJSONException1.printStackTrace();
        continue;
      }
      localHttpClient2.shutdown();
      return str1;
      localCookie = (Cookie)localIterator.next();
      if (localCookie == null) {
        break;
      }
      localHttpClient2.setCookie(localCookie);
      break;
      localJSONObject = localJSONArray.getJSONObject(i);
      try
      {
        cookies = new ArrayList();
        String str5 = getSig(paramContext, localJSONObject.getString("sig"));
        j = Integer.parseInt(localJSONObject.getString("itag"));
        String str6 = localJSONObject.getString("url") + "&signature=" + str5;
        str4 = str6;
      }
      catch (JSONException localJSONException2)
      {
        localJSONException2.printStackTrace();
        int j = Integer.parseInt(localJSONObject.getString("itag"));
        String str4 = localJSONObject.getString("url");
        continue;
      }
      localHashMap.put(Integer.valueOf(j), str4);
      i++;
      continue;
      if (localHashMap.containsKey(Integer.valueOf(arrayOfInt[k]))) {
        str1 = (String)localHashMap.get(Integer.valueOf(arrayOfInt[k]));
      } else {
        k++;
      }
    }
  }
  
  @SuppressLint({"UseSparseArrays"})
  public static String getVideoUrl2(Context paramContext, String paramString1, String paramString2, String paramString3, Constants.Quality paramQuality)
    throws IOException, JSONException
  {
    String str1 = null;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString1;
    HttpClient localHttpClient = new HttpClient(String.format("http://www.youtube.com/watch?v=%s", arrayOfObject));
    localHttpClient.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0");
    HashMap localHashMap;
    String[] arrayOfString;
    if (localHttpClient.request())
    {
      String str2 = localHttpClient.getResponseBody();
      localHashMap = new HashMap();
      Matcher localMatcher = Pattern.compile("\"url_encoded_fmt_stream_map\": ?(\".*?\")").matcher(str2);
      if (localMatcher.find()) {
        arrayOfString = new JSONArray("[" + localMatcher.group(1) + "]").getString(0).split(",");
      }
    }
    for (int i = 0;; i++)
    {
      int[] arrayOfInt;
      if (i >= arrayOfString.length) {
        arrayOfInt = getQuality(paramQuality);
      }
      for (int k = 0;; k++)
      {
        if (k >= arrayOfInt.length) {}
        for (;;)
        {
          localHttpClient.shutdown();
          return str1;
          try
          {
            Map localMap = Util.getQueryMap(arrayOfString[i]);
            String str3 = URLDecoder.decode((String)localMap.get("url"), "UTF-8");
            String str4 = (String)localMap.get("sig");
            String str5 = (String)localMap.get("s");
            int j = Integer.parseInt((String)localMap.get("itag"));
            Object localObject;
            if (str4 != null) {
              localObject = str3 + "&signature=" + str4;
            }
            for (;;)
            {
              localHashMap.put(Integer.valueOf(j), localObject);
              break;
              if (str5 != null)
              {
                String str6 = getSigForPC(paramContext, str5);
                String str7 = str3 + "&signature=" + str6;
                localObject = str7;
              }
              else
              {
                localObject = str3;
              }
            }
            if (!localHashMap.containsKey(Integer.valueOf(arrayOfInt[k]))) {
              break;
            }
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
          str1 = (String)localHashMap.get(Integer.valueOf(arrayOfInt[k]));
        }
      }
    }
  }
  
  public static boolean login(String paramString1, String paramString2)
  {
    boolean bool = false;
    HttpClient localHttpClient = new HttpClient("https://www.google.com/accounts/ClientLogin");
    localHttpClient.setRequestMethod(2);
    localHttpClient.addHeader("Content-Type", "application/x-www-form-urlencoded");
    localHttpClient.setParameter("Email", paramString1);
    localHttpClient.setParameter("Passwd", paramString2);
    localHttpClient.setParameter("service", "youtube");
    localHttpClient.setParameter("source", "pvstar");
    if (localHttpClient.request())
    {
      String str = localHttpClient.getResponseBody();
      Matcher localMatcher = Pattern.compile("Auth=(.+)").matcher(str);
      if (localMatcher.find())
      {
        auth = localMatcher.group(1);
        bool = true;
      }
    }
    localHttpClient.shutdown();
    return bool;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.video.YouTube
 * JD-Core Version:    0.7.0.1
 */