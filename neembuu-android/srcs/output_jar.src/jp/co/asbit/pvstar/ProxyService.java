package jp.co.asbit.pvstar;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.util.Log;
import java.io.IOException;
import jp.co.asbit.pvstar.cache.CacheManager;
import jp.co.asbit.pvstar.cache.CacheManager.CachingDisableException;

public class ProxyService
  extends Service
{
  private static final int HELLO_ID = 2;
  private static final String TAG = "ProxyService";
  private Context mContext;
  private ProxyServiceThread mProxyThread;
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.mContext = getApplicationContext();
    int i = 0;
    if (Build.VERSION.SDK_INT >= 18) {
      i = 2130837574;
    }
    Notification localNotification = new Notification(i, getText(2131296256), System.currentTimeMillis());
    Intent localIntent = new Intent(this.mContext, VideoActivity.class);
    localIntent.setFlags(67108864);
    PendingIntent localPendingIntent = PendingIntent.getActivity(this.mContext, 0, localIntent, 0);
    localNotification.setLatestEventInfo(this.mContext, getText(2131296256), getString(2131296257), localPendingIntent);
    startForeground(2, localNotification);
  }
  
  public void onDestroy()
  {
    stopForeground(true);
    if (this.mProxyThread != null)
    {
      this.mProxyThread.interrupt();
      this.mProxyThread = null;
    }
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(getApplicationContext());
    localVideoDbHelper.truncateVideoUrl();
    localVideoDbHelper.close();
    try
    {
      new CacheManager(getApplicationContext()).clearTemporary();
      label58:
      return;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        Log.d("ProxyService", "Failed to delete temporary files.");
      }
    }
    catch (CacheManager.CachingDisableException localCachingDisableException)
    {
      break label58;
    }
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    if (this.mProxyThread == null)
    {
      this.mProxyThread = new ProxyServiceThread(getApplicationContext());
      this.mProxyThread.start();
    }
    return 1;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.ProxyService
 * JD-Core Version:    0.7.0.1
 */