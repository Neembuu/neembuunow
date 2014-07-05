package jp.co.asbit.pvstar.api;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import jp.co.asbit.pvstar.Playlist;
import jp.co.asbit.pvstar.Video;
import jp.co.asbit.pvstar.video.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImportMylistBackupPlaylists
  extends ImportPlaylists
{
  private static final String PLAYLIST_URL = "http://pvstar.dooga.org/api2/mylists/videos";
  private String id;
  private Context mContext;
  private String passwd;
  
  public ImportMylistBackupPlaylists(Context paramContext, String paramString1, String paramString2)
  {
    this.mContext = paramContext;
    this.id = paramString1;
    this.passwd = paramString2;
  }
  
  private ArrayList<Video> getVideosPlaylist(String paramString)
    throws JSONException
  {
    ArrayList localArrayList = new ArrayList();
    HttpClient localHttpClient = new HttpClient("http://pvstar.dooga.org/api2/mylists/videos");
    localHttpClient.setRequestMethod(2);
    localHttpClient.setParameter("id", this.id);
    localHttpClient.setParameter("passwd", this.passwd);
    localHttpClient.setParameter("mylist_id", paramString);
    JSONArray localJSONArray;
    if (localHttpClient.request()) {
      localJSONArray = new JSONArray(localHttpClient.getResponseBody());
    }
    for (int i = 0;; i++)
    {
      if (i >= localJSONArray.length())
      {
        localHttpClient.shutdown();
        return localArrayList;
      }
      JSONObject localJSONObject = localJSONArray.getJSONObject(i);
      Video localVideo = new Video();
      localVideo.setSearchEngine(localJSONObject.getString("search_engine"));
      localVideo.setId(localJSONObject.getString("video_id"));
      localVideo.setTitle(localJSONObject.getString("title"));
      localVideo.setViewCount(localJSONObject.getString("view_count"));
      localVideo.setThumbnailUrl(localJSONObject.getString("thumbnail_url"));
      localVideo.setDescription(localJSONObject.getString("description"));
      localVideo.setDuration(localJSONObject.getString("duration"));
      localArrayList.add(localVideo);
    }
  }
  
  protected Integer doInBackground(ArrayList<Playlist>... paramVarArgs)
  {
    Iterator localIterator = paramVarArgs[0].iterator();
    int i = 0;
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
        ArrayList localArrayList = getVideosPlaylist(localPlaylist.getId());
        Collections.reverse(localArrayList);
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
 * Qualified Name:     jp.co.asbit.pvstar.api.ImportMylistBackupPlaylists
 * JD-Core Version:    0.7.0.1
 */