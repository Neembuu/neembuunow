package com.amoad.amoadsdk;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class ClickPopResultSetSendTask
  extends AsyncTask<String, Void, String>
{
  private Activity activity = null;
  private String triggerKey = "";
  private String url = "";
  
  public ClickPopResultSetSendTask(String paramString1, Activity paramActivity, String paramString2)
  {
    this.activity = paramActivity;
    this.url = paramString1;
    this.triggerKey = paramString2;
  }
  
  protected String doInBackground(String... paramVarArgs)
  {
    try
    {
      String str2 = String.valueOf(new DefaultHttpClient().execute(new HttpGet(this.url)).getStatusLine().getStatusCode());
      str1 = str2;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        String str1 = null;
      }
    }
    return str1;
  }
  
  protected void onPostExecute(String paramString)
  {
    SharedPreferences.Editor localEditor = this.activity.getSharedPreferences("popup_info", 0).edit();
    localEditor.putString(this.triggerKey + "_click_status", paramString);
    localEditor.commit();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.ClickPopResultSetSendTask
 * JD-Core Version:    0.7.0.1
 */