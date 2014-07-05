package jp.co.asbit.pvstar.api;

import android.net.Uri;
import android.net.Uri.Builder;
import java.util.Locale;
import jp.co.asbit.pvstar.video.HttpClient;

public class VideoViewCount
{
  public static void count(String paramString1, String paramString2)
  {
    Uri.Builder localBuilder = new Uri.Builder();
    localBuilder.scheme("http");
    localBuilder.encodedAuthority("pvstar.dooga.org");
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramString2;
    arrayOfObject[1] = paramString1;
    localBuilder.path(String.format("/api2/view_video/index/%s/%s", arrayOfObject));
    String str = localBuilder.build().toString();
    HttpClient localHttpClient = new HttpClient();
    localHttpClient.setUrl(str);
    localHttpClient.setRequestMethod(3);
    localHttpClient.addHeader("Accept-Language", Locale.getDefault().getLanguage());
    localHttpClient.request();
    localHttpClient.shutdown();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.VideoViewCount
 * JD-Core Version:    0.7.0.1
 */