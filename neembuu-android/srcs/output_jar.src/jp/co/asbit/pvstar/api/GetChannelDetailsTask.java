package jp.co.asbit.pvstar.api;

import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.Playlist;
import jp.co.asbit.pvstar.video.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class GetChannelDetailsTask
  extends AsyncTask<String, Void, Playlist>
{
  private static final String API = "http://pvstar.dooga.org/api2/channels/details/%s/%s";
  
  protected Playlist doInBackground(String... paramVarArgs)
  {
    String str1 = paramVarArgs[1];
    String str2 = paramVarArgs[0];
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = str2;
    arrayOfObject[1] = str1;
    HttpClient localHttpClient = new HttpClient(String.format("http://pvstar.dooga.org/api2/channels/details/%s/%s", arrayOfObject));
    String str3;
    if (localHttpClient.request()) {
      str3 = localHttpClient.getResponseBody();
    }
    for (;;)
    {
      try
      {
        localPlaylist = new Playlist();
        JSONObject localJSONObject = new JSONObject(str3);
        localPlaylist.setTitle(localJSONObject.getString("title"));
        localPlaylist.setDescription(localJSONObject.getString("description"));
        localPlaylist.setThumbnailUrl(localJSONObject.getString("thumbnailUrl"));
        localPlaylist.setVideoCount(localJSONObject.getInt("videoCount"));
        localPlaylist.setSearchEngine(str2);
        localPlaylist.setListType(2);
        localPlaylist.setId(localJSONObject.getString("userId"));
        return localPlaylist;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      Playlist localPlaylist = null;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.GetChannelDetailsTask
 * JD-Core Version:    0.7.0.1
 */