package jp.co.asbit.pvstar.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.Playlist;
import jp.co.asbit.pvstar.video.HttpClient;
import jp.co.asbit.pvstar.video.NicoNico;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetNicoUserPlaylistsTask
  extends AsyncTask<Integer, Long, ArrayList<Playlist>>
{
  private static final String USER_PLAYLISTS_URL = "http://www.nicovideo.jp/api/mylistgroup/list";
  private String password;
  private String userid;
  
  public GetNicoUserPlaylistsTask(String paramString1, String paramString2)
  {
    this.userid = paramString1;
    this.password = paramString2;
  }
  
  private ArrayList<Playlist> getPlaylists()
    throws IOException, JSONException
  {
    ArrayList localArrayList = new ArrayList();
    Playlist localPlaylist1 = new Playlist();
    localPlaylist1.setId("-1");
    localPlaylist1.setTitle("とりあえずマイリスト");
    localPlaylist1.setDescription("");
    localArrayList.add(localPlaylist1);
    HttpClient localHttpClient = new HttpClient("http://www.nicovideo.jp/api/mylistgroup/list");
    localHttpClient.setRequestMethod(2);
    localHttpClient.addHeader("Cookie", NicoNico.getCookie());
    localHttpClient.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
    String str = NicoNico.token();
    if (str != null) {
      localHttpClient.setParameter("token", str);
    }
    JSONArray localJSONArray;
    int i;
    if (localHttpClient.request())
    {
      localJSONArray = new JSONObject(localHttpClient.getResponseBody()).getJSONArray("mylistgroup");
      i = localJSONArray.length();
    }
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        localHttpClient.shutdown();
        return localArrayList;
      }
      Playlist localPlaylist2 = new Playlist();
      localPlaylist2.setId(localJSONArray.getJSONObject(j).getString("id"));
      localPlaylist2.setTitle(localJSONArray.getJSONObject(j).getString("name"));
      localPlaylist2.setDescription(localJSONArray.getJSONObject(j).getString("description"));
      localArrayList.add(localPlaylist2);
    }
  }
  
  protected ArrayList<Playlist> doInBackground(Integer... paramVarArgs)
  {
    if (NicoNico.login(this.userid, this.password)) {}
    try
    {
      new ArrayList();
      ArrayList localArrayList2 = getPlaylists();
      localArrayList1 = localArrayList2;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      for (;;)
      {
        localMalformedURLException.printStackTrace();
        ArrayList localArrayList1 = null;
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        localIOException.printStackTrace();
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
      }
    }
    catch (IllegalStateException localIllegalStateException)
    {
      for (;;)
      {
        localIllegalStateException.printStackTrace();
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;)
      {
        localIllegalArgumentException.printStackTrace();
      }
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        localJSONException.printStackTrace();
      }
    }
    return localArrayList1;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.GetNicoUserPlaylistsTask
 * JD-Core Version:    0.7.0.1
 */