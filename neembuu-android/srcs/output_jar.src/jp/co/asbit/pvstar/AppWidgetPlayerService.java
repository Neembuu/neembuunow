package jp.co.asbit.pvstar;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

public class AppWidgetPlayerService
  extends Service
{
  private ServiceConnection mConnection;
  private MyBindService mService;
  
  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }
  
  public int onStartCommand(final Intent paramIntent, int paramInt1, int paramInt2)
  {
    Context localContext = getApplicationContext();
    if (Util.isServiceRunning(localContext, VideoService.class))
    {
      this.mConnection = new ServiceConnection()
      {
        public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
        {
          AppWidgetPlayerService.this.mService = MyBindService.Stub.asInterface(paramAnonymousIBinder);
          try
          {
            if (paramIntent.getStringExtra("jp.co.asbit.pvstar.APPWIDGET_ACTION").equals("jp.co.asbit.pvstar.APPWIDGET_ACTION_PLAY")) {
              AppWidgetPlayerService.this.mService.play();
            }
            for (;;)
            {
              AppWidgetPlayerService.this.unbindService(AppWidgetPlayerService.this.mConnection);
              AppWidgetPlayerService.this.stopSelf();
              return;
              if (!paramIntent.getStringExtra("jp.co.asbit.pvstar.APPWIDGET_ACTION").equals("jp.co.asbit.pvstar.APPWIDGET_ACTION_PAUSE")) {
                break;
              }
              AppWidgetPlayerService.this.mService.pause();
            }
          }
          catch (RemoteException localRemoteException)
          {
            for (;;)
            {
              localRemoteException.printStackTrace();
              continue;
              if (!paramIntent.getStringExtra("jp.co.asbit.pvstar.APPWIDGET_ACTION").equals("jp.co.asbit.pvstar.APPWIDGET_ACTION_NEXT")) {
                break;
              }
              AppWidgetPlayerService.this.mService.next();
            }
          }
          catch (NullPointerException localNullPointerException)
          {
            for (;;)
            {
              localNullPointerException.printStackTrace();
              continue;
              if (paramIntent.getStringExtra("jp.co.asbit.pvstar.APPWIDGET_ACTION").equals("jp.co.asbit.pvstar.APPWIDGET_ACTION_PREV")) {
                AppWidgetPlayerService.this.mService.fprev();
              }
            }
          }
        }
        
        public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
        {
          AppWidgetPlayerService.this.mService = null;
        }
      };
      bindService(new Intent(localContext, VideoService.class), this.mConnection, 1);
    }
    return super.onStartCommand(paramIntent, paramInt1, paramInt2);
  }
  
  public boolean onUnbind(Intent paramIntent)
  {
    return super.onUnbind(paramIntent);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.AppWidgetPlayerService
 * JD-Core Version:    0.7.0.1
 */