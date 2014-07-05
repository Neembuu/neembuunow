package jp.co.asbit.pvstar.api;

import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import jp.co.asbit.pvstar.Playlist;
import jp.co.asbit.pvstar.Video;
import jp.co.asbit.pvstar.video.HttpClient;
import jp.co.asbit.pvstar.video.NicoNico;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImportNicoUserPlaylists
  extends ImportPlaylists
{
  private static final String PLAYLIST_DEFAULT_URL = "http://www.nicovideo.jp/api/deflist/list";
  private static final String PLAYLIST_URL = "http://www.nicovideo.jp/api/mylist/list";
  private Context mContext;
  
  public ImportNicoUserPlaylists(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private ArrayList<Video> getVideosPlaylist(String paramString1, String paramString2)
    throws IOException, JSONException
  {
    ArrayList localArrayList = new ArrayList();
    HttpClient localHttpClient = new HttpClient();
    localHttpClient.setRequestMethod(2);
    localHttpClient.addHeader("Cookie", NicoNico.getCookie());
    localHttpClient.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
    JSONArray localJSONArray;
    if (paramString1 == "-1")
    {
      localHttpClient.setUrl("http://www.nicovideo.jp/api/deflist/list");
      if (paramString2 != null) {
        localHttpClient.setParameter("token", paramString2);
      }
      if (localHttpClient.request()) {
        localJSONArray = new JSONObject(localHttpClient.getResponseBody()).getJSONArray("mylistitem");
      }
    }
    for (int i = 0;; i++)
    {
      if (i >= localJSONArray.length())
      {
        localHttpClient.shutdown();
        return localArrayList;
        localHttpClient.setUrl("http://www.nicovideo.jp/api/mylist/list");
        localHttpClient.setParameter("group_id", paramString1);
        break;
      }
      JSONObject localJSONObject1 = localJSONArray.getJSONObject(i);
      JSONObject localJSONObject2 = localJSONObject1.getJSONObject("item_data");
      Video localVideo = new Video();
      localVideo.setSearchEngine("niconico");
      localVideo.setId(localJSONObject2.getString("video_id"));
      localVideo.setTitle(localJSONObject2.getString("title"));
      localVideo.setViewCount(localJSONObject2.getString("view_counter"));
      localVideo.setThumbnailUrl(localJSONObject2.getString("thumbnail_url"));
      String str1 = localJSONObject1.getString("description");
      if (str1.length() > 40) {
        str1 = str1.substring(0, 40);
      }
      localVideo.setDescription(str1);
      String str2 = localJSONObject2.getString("length_seconds");
      int j = Integer.valueOf(str2).intValue();
      int k = (int)Math.floor(Integer.valueOf(str2).intValue() / 60.0F);
      int m = (int)(j % 60.0F);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(k);
      arrayOfObject[1] = Integer.valueOf(m);
      localVideo.setDuration(String.format("%d:%02d", arrayOfObject));
      localArrayList.add(localVideo);
    }
  }
  
  protected Integer doInBackground(ArrayList<Playlist>... paramVarArgs)
  {
    Iterator localIterator = paramVarArgs[0].iterator();
    int i = 0;
    String str = NicoNico.token();
    try
    {
      Integer localInteger;
      for (;;)
      {
        if (!localIterator.hasNext())
        {
          localInteger = Integer.valueOf(i);
          break;
        }
        Playlist localPlaylist = (Playlist)localIterator.next();
        ArrayList localArrayList = getVideosPlaylist(localPlaylist.getId(), str);
        boolean bool = insertVideos(this.mContext, localPlaylist, localArrayList);
        if (bool) {
          i++;
        }
      }
      return localInteger;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      cancel(true);
      localInteger = Integer.valueOf(i);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.ImportNicoUserPlaylists
 * JD-Core Version:    0.7.0.1
 */