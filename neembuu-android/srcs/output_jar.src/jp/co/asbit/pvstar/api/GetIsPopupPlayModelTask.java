package jp.co.asbit.pvstar.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.video.HttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class GetIsPopupPlayModelTask
  extends AsyncTask<String, Void, Boolean>
{
  protected Boolean doInBackground(String... paramVarArgs)
  {
    boolean bool1 = true;
    Object localObject = "";
    try
    {
      String str2 = "http://pvstar.dooga.org/api2/drives/popupplay/effective?model=" + URLEncoder.encode(paramVarArgs[0], "UTF-8");
      localObject = str2;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;)
      {
        HttpClient localHttpClient;
        String str1;
        localUnsupportedEncodingException.printStackTrace();
      }
    }
    localHttpClient = new HttpClient((String)localObject);
    if (localHttpClient.request()) {
      str1 = localHttpClient.getResponseBody();
    }
    try
    {
      boolean bool2 = new JSONObject(str1).getBoolean("result");
      bool1 = bool2;
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        localJSONException.printStackTrace();
      }
    }
    localHttpClient.shutdown();
    return Boolean.valueOf(bool1);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.GetIsPopupPlayModelTask
 * JD-Core Version:    0.7.0.1
 */