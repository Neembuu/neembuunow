package jp.co.asbit.pvstar.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.video.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class MylistBackupTask
  extends AsyncTask<String, Long, Integer>
{
  private static final String MYLIST_BACKUP_API = "http://pvstar.dooga.org/api2/mylists/backup";
  protected String errorMessage = null;
  
  protected Integer doInBackground(String... paramVarArgs)
  {
    Object localObject1 = null;
    localHttpClient = new HttpClient("http://pvstar.dooga.org/api2/mylists/backup");
    localHttpClient.setRequestMethod(2);
    try
    {
      localHttpClient.setParameter("json", URLEncoder.encode(paramVarArgs[0], "UTF-8"));
      localHttpClient.setParameter("id", paramVarArgs[1]);
      localHttpClient.setParameter("passwd", paramVarArgs[2]);
      String str;
      if (localHttpClient.request()) {
        str = localHttpClient.getResponseBody();
      }
      try
      {
        localJSONObject = new JSONObject(str);
        int i = localJSONObject.getInt("result");
        if (i <= 0) {
          break label113;
        }
        Integer localInteger = Integer.valueOf(i);
        localObject1 = localInteger;
      }
      catch (JSONException localJSONException)
      {
        for (;;)
        {
          JSONObject localJSONObject;
          localJSONException.printStackTrace();
          localHttpClient.shutdown();
        }
      }
      finally
      {
        localHttpClient.shutdown();
      }
      return localObject1;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;)
      {
        localUnsupportedEncodingException.printStackTrace();
        continue;
        label113:
        this.errorMessage = localJSONObject.getString("error_message");
        localHttpClient.shutdown();
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.MylistBackupTask
 * JD-Core Version:    0.7.0.1
 */