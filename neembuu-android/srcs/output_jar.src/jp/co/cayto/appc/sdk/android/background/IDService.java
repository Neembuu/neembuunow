package jp.co.cayto.appc.sdk.android.background;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import jp.co.cayto.appc.sdk.android.common.AppController;
import jp.co.cayto.appc.sdk.android.common.AppPreference;

public final class IDService
  extends Service
{
  public static final String START_ACTION = "start";
  public static final String STOP_ACTION = "stop";
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public void onCreate() {}
  
  public void onDestroy()
  {
    super.onDestroy();
  }
  
  public void onStart(Intent paramIntent, int paramInt)
  {
    final Context localContext = getApplicationContext();
    final AppController localAppController = AppController.createIncetance(localContext);
    if (!TextUtils.isEmpty(AppPreference.getGid(localContext))) {
      stopSelf();
    }
    for (;;)
    {
      return;
      super.onStart(paramIntent, paramInt);
      new Thread()
      {
        public void run()
        {
          localAppController.salvageGID(localContext);
          try
          {
            Thread.sleep(30000L);
            label17:
            IDService.this.stopSelf();
            return;
          }
          catch (InterruptedException localInterruptedException)
          {
            break label17;
          }
        }
      }.start();
    }
  }
  
  public boolean onUnbind(Intent paramIntent)
  {
    super.onUnbind(paramIntent);
    return true;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.background.IDService
 * JD-Core Version:    0.7.0.1
 */