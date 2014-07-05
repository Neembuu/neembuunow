package jp.co.asbit.pvstar.api;

import java.util.Locale;
import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.video.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class AddBackupUserTask
  extends AsyncTask<String, Long, Boolean>
{
  private static final String ADD_BACKUP_USER_API = "http://pvstar.dooga.org/api2/users/add";
  protected String errorMessage = null;
  
  protected Boolean doInBackground(String... paramVarArgs)
  {
    String str1 = paramVarArgs[0];
    String str2 = paramVarArgs[1];
    String str3 = paramVarArgs[2];
    localHttpClient = new HttpClient("http://pvstar.dooga.org/api2/users/add");
    localHttpClient.setRequestMethod(2);
    localHttpClient.setParameter("id", str1);
    localHttpClient.setParameter("passwd", str2);
    localHttpClient.setParameter("passwd2", str3);
    localHttpClient.addHeader("Accept-Language", Locale.getDefault().getLanguage());
    if (localHttpClient.request())
    {
      String str4 = localHttpClient.getResponseBody();
      for (;;)
      {
        try
        {
          localJSONObject = new JSONObject(str4);
          if (localJSONObject.getInt("result") != 1) {
            continue;
          }
          Boolean localBoolean2 = Boolean.valueOf(true);
          localObject1 = localBoolean2;
        }
        catch (JSONException localJSONException)
        {
          JSONObject localJSONObject;
          Boolean localBoolean1;
          localJSONException.printStackTrace();
          localHttpClient.shutdown();
          Object localObject1 = Boolean.valueOf(false);
          continue;
        }
        finally
        {
          localHttpClient.shutdown();
        }
        return localObject1;
        this.errorMessage = localJSONObject.getString("error_message");
        localBoolean1 = Boolean.valueOf(false);
        localObject1 = localBoolean1;
        localHttpClient.shutdown();
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.AddBackupUserTask
 * JD-Core Version:    0.7.0.1
 */