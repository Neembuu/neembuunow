package jp.adlantis.android;

import android.util.Log;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

public class NetworkRequest
{
  protected static String DEBUG_TASK = "NetworkRequest";
  
  protected static AbstractHttpClient httpClientFactory()
  {
    return new DefaultHttpClient();
  }
  
  AdManager adManager()
  {
    return AdManager.getInstance();
  }
  
  AdNetworkConnection getAdNetworkConnection()
  {
    return adManager().getAdNetworkConnection();
  }
  
  protected void log_d(String paramString)
  {
    Log.d(DEBUG_TASK, paramString);
  }
  
  protected void log_e(String paramString)
  {
    Log.e(DEBUG_TASK, paramString);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.NetworkRequest
 * JD-Core Version:    0.7.0.1
 */