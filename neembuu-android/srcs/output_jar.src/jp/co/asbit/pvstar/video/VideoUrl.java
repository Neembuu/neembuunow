package jp.co.asbit.pvstar.video;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import java.io.IOException;
import jp.co.asbit.pvstar.Constants.Quality;
import jp.co.asbit.pvstar.LocalProxyUrl;
import jp.co.asbit.pvstar.VideoDbHelper;
import jp.co.asbit.pvstar.cache.Cache;
import jp.co.asbit.pvstar.cache.CacheManager;
import jp.co.asbit.pvstar.cache.CacheManager.CachingDisableException;
import org.json.JSONException;

public class VideoUrl
{
  private static final String PROXY_FORMAT_URL = "http://localhost:25252/?key=%s";
  private static final String TAG = "VideoUrl";
  private Context mContext;
  
  public VideoUrl(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private boolean isVideoExists(String paramString1, String paramString2)
  {
    boolean bool = true;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramString1;
    arrayOfObject[1] = paramString2;
    HttpClient localHttpClient = new HttpClient(String.format("http://pvstar.dooga.org/api2/videos/video_exists/?id=%s&site=%s", arrayOfObject));
    if (localHttpClient.request()) {
      bool = localHttpClient.getResponseBody().equals("1");
    }
    localHttpClient.shutdown();
    Log.d("VideoUrl", "Failed to prove the existence of video.");
    return bool;
  }
  
  private boolean isWifiConnected(Context paramContext)
  {
    int i = 1;
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if (localNetworkInfo != null) {
      if ((localNetworkInfo.getType() != i) || (!localNetworkInfo.isConnected())) {}
    }
    for (;;)
    {
      return i;
      int j = 0;
      continue;
      j = 0;
    }
  }
  
  public String get(String paramString1, String paramString2)
  {
    return get(paramString1, paramString2, null, null);
  }
  
  public String get(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    str1 = paramString1 + paramString2;
    i = 0;
    try
    {
      Cache localCache = new CacheManager(this.mContext).getCache(str1);
      if ((!localCache.isExists()) || (!isVideoExists(paramString2, paramString1))) {
        break label97;
      }
      String str6 = "file://" + localCache.getUri();
      localObject = str6;
    }
    catch (CacheManager.CachingDisableException localCachingDisableException)
    {
      i = 1;
      localCachingDisableException.printStackTrace();
      j = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this.mContext).getString("quarity", "2"));
      if (j != 2) {
        break label138;
      }
      if (!isWifiConnected(this.mContext)) {
        break label298;
      }
      j = 0;
      localObject = null;
      str2 = null;
      str3 = null;
    }
    catch (IOException localIOException1)
    {
      for (;;)
      {
        String str3;
        try
        {
          int j;
          String str2;
          if (paramString1.equals("youtube"))
          {
            if (j == 4)
            {
              Log.d("VideoUrl", "高画質モードで取得します。");
              localObject = YouTube.getVideoUrl2(this.mContext, paramString2, paramString3, paramString4, Constants.Quality.HIGH);
              str3 = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0";
              if ((((String)localObject).startsWith("rtsp")) || (((String)localObject).indexOf("rtmpe=yes") != -1)) {
                continue;
              }
              if (localObject != null)
              {
                LocalProxyUrl localLocalProxyUrl = new LocalProxyUrl(str1, (String)localObject, str2, str3);
                VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
                localVideoDbHelper.setVideoUrl(localLocalProxyUrl);
                localVideoDbHelper.close();
                Object[] arrayOfObject = new Object[1];
                arrayOfObject[0] = str1;
                String str5 = String.format("http://localhost:25252/?key=%s", arrayOfObject);
                localObject = str5;
                continue;
                localIOException1 = localIOException1;
                localIOException1.printStackTrace();
                continue;
                j = 3;
                continue;
              }
            }
            else
            {
              if (j == 0)
              {
                Log.d("VideoUrl", "通常画質モードで取得します。");
                localObject = YouTube.getVideoUrl2(this.mContext, paramString2, paramString3, paramString4, Constants.Quality.STANDARD);
                str3 = "Mozilla/5.0 (Linux; U; Android 2.3.6; ja-jp; SC-02C Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
                continue;
              }
              Log.d("VideoUrl", "低画質モードで取得します。");
              localObject = YouTube.getVideoUrl2(this.mContext, paramString2, paramString3, paramString4, Constants.Quality.LOW);
              str3 = "Mozilla/5.0 (Linux; U; Android 2.3.6; ja-jp; SC-02C Build/GINGERBREAD) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1";
              continue;
            }
          }
          else
          {
            if (paramString1.equals("niconico"))
            {
              if ((j == 0) || (j == 4))
              {
                Log.d("VideoUrl", "通常画質モードで取得します。");
                localObject = NicoNico.getVideoUrl(paramString2, paramString3, paramString4);
                str3 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)";
                str2 = NicoNico.getCookie();
                continue;
              }
              Log.d("VideoUrl", "低画質モードで取得します。");
              localObject = NicoNico.getVideoUrlForLowQuarity(paramString2, paramString3, paramString4);
              if ((localObject != null) || (j != 3)) {
                continue;
              }
              Log.d("VideoUrl", "低画質URL取得失敗。通常画質モードで取得します。");
              localObject = NicoNico.getVideoUrl(paramString2, paramString3, paramString4);
              continue;
            }
            if (paramString1.equals("dailymotion")) {
              switch (j)
              {
              default: 
                Log.d("VideoUrl", "低画質モードで取得します。");
                localObject = DailyMotion.getVideoUrl(paramString2, paramString3, paramString4, Constants.Quality.LOW);
                break;
              case 4: 
                Log.d("VideoUrl", "高画質モードで取得します。");
                localObject = DailyMotion.getVideoUrl(paramString2, paramString3, paramString4, Constants.Quality.HIGH);
                break;
              case 0: 
                Log.d("VideoUrl", "通常画質モードで取得します。");
                localObject = DailyMotion.getVideoUrl(paramString2, paramString3, paramString4, Constants.Quality.STANDARD);
                break;
              }
            }
            if (!paramString1.equals("vimeo")) {
              continue;
            }
            switch (j)
            {
            default: 
              Log.d("VideoUrl", "低画質モードで取得します。");
              localObject = Vimeo.getVideoUrl(paramString2, paramString3, paramString4, Constants.Quality.LOW);
              break;
            case 4: 
              Log.d("VideoUrl", "高画質モードで取得します。");
              localObject = Vimeo.getVideoUrl(paramString2, paramString3, paramString4, Constants.Quality.HIGH);
              break;
            case 0: 
              Log.d("VideoUrl", "通常画質モードで取得します。");
              String str4 = Vimeo.getVideoUrl(paramString2, paramString3, paramString4, Constants.Quality.STANDARD);
              localObject = str4;
            }
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          localNullPointerException.printStackTrace();
          Object localObject = null;
          continue;
        }
        catch (IOException localIOException2)
        {
          localIOException2.printStackTrace();
          continue;
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
          continue;
        }
        if (i == 0)
        {
          str3 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)";
          continue;
          if (i == 0) {
            str3 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)";
          }
        }
      }
    }
    return localObject;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.video.VideoUrl
 * JD-Core Version:    0.7.0.1
 */