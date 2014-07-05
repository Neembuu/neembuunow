package jp.co.asbit.pvstar.api;

import java.util.ArrayList;
import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.Playlist;
import jp.co.asbit.pvstar.video.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetMylistBackupTask
  extends AsyncTask<String, Long, ArrayList<Playlist>>
{
  private static final String USER_PLAYLISTS_URL = "http://pvstar.dooga.org/api2/mylists/index";
  
  protected ArrayList<Playlist> doInBackground(String... paramVarArgs)
  {
    localHttpClient = new HttpClient("http://pvstar.dooga.org/api2/mylists/index");
    localHttpClient.setRequestMethod(2);
    localHttpClient.setParameter("id", paramVarArgs[0]);
    localHttpClient.setParameter("passwd", paramVarArgs[1]);
    if (localHttpClient.request())
    {
      String str = localHttpClient.getResponseBody();
      localArrayList = new ArrayList();
      for (;;)
      {
        try
        {
          localJSONArray = new JSONArray(str);
          i = 0;
          int j = localJSONArray.length();
          if (i < j) {
            continue;
          }
        }
        catch (JSONException localJSONException)
        {
          JSONArray localJSONArray;
          int i;
          Playlist localPlaylist;
          JSONObject localJSONObject;
          localJSONException.printStackTrace();
          localHttpClient.shutdown();
          localArrayList = null;
          continue;
        }
        finally
        {
          localHttpClient.shutdown();
        }
        return localArrayList;
        localPlaylist = new Playlist();
        localJSONObject = localJSONArray.getJSONObject(i);
        localPlaylist.setId(localJSONObject.getString("id"));
        localPlaylist.setTitle(localJSONObject.getString("name"));
        localPlaylist.setDescription(localJSONObject.getString("description"));
        localArrayList.add(localPlaylist);
        i++;
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.GetMylistBackupTask
 * JD-Core Version:    0.7.0.1
 */