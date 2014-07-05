package jp.co.asbit.pvstar.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.Playlist;
import jp.co.asbit.pvstar.video.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeleteMylistBackupPlaylists
  extends AsyncTask<ArrayList<Playlist>, Long, Integer>
{
  private static final String PLAYLIST_DELETE_URL = "http://pvstar.dooga.org/api2/mylists/delete";
  private String id;
  private String passwd;
  
  public DeleteMylistBackupPlaylists(String paramString1, String paramString2)
  {
    this.id = paramString1;
    this.passwd = paramString2;
  }
  
  protected Integer doInBackground(ArrayList<Playlist>... paramVarArgs)
  {
    ArrayList<Playlist> localArrayList = paramVarArgs[0];
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = localArrayList.iterator();
    HttpClient localHttpClient;
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        localHttpClient = new HttpClient("http://pvstar.dooga.org/api2/mylists/delete");
        localHttpClient.setRequestMethod(2);
        localHttpClient.setParameter("id", this.id);
        localHttpClient.setParameter("passwd", this.passwd);
      }
      try
      {
        localHttpClient.setParameter("json", URLEncoder.encode(localJSONArray.toString(), "UTF-8"));
        String str;
        if (localHttpClient.request()) {
          str = localHttpClient.getResponseBody();
        }
        try
        {
          int i = new JSONObject(str).getInt("result");
          if (i <= 0) {
            break label200;
          }
          Integer localInteger2 = Integer.valueOf(i);
          localInteger1 = localInteger2;
        }
        catch (JSONException localJSONException)
        {
          for (;;)
          {
            localJSONException.printStackTrace();
            localHttpClient.shutdown();
            Integer localInteger1 = null;
          }
        }
        finally
        {
          localHttpClient.shutdown();
        }
        return localInteger1;
        localJSONArray.put(Integer.valueOf(((Playlist)localIterator.next()).getId()));
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        for (;;)
        {
          localUnsupportedEncodingException.printStackTrace();
        }
      }
    }
    for (;;)
    {
      label200:
      localHttpClient.shutdown();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.DeleteMylistBackupPlaylists
 * JD-Core Version:    0.7.0.1
 */