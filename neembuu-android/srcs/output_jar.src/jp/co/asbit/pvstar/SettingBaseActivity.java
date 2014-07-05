package jp.co.asbit.pvstar;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceActivity;
import com.clarion.android.appmgr.service.IClarionService;
import com.clarion.android.appmgr.service.IClarionService.Stub;

public abstract class SettingBaseActivity
  extends PreferenceActivity
{
  private ServiceConnection clarionServiceConn = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      SettingBaseActivity.this.mClarionServiceIf = IClarionService.Stub.asInterface(paramAnonymousIBinder);
      try
      {
        if (SettingBaseActivity.this.mClarionServiceIf.getState() == 3)
        {
          Intent localIntent = new Intent(SettingBaseActivity.this.getApplicationContext(), Drv_PvstarActivity.class);
          localIntent.setFlags(268533760);
          SettingBaseActivity.this.startActivity(localIntent);
          SettingBaseActivity.this.finish();
        }
        return;
      }
      catch (RemoteException localRemoteException)
      {
        for (;;)
        {
          localRemoteException.printStackTrace();
        }
      }
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      SettingBaseActivity.this.mClarionServiceIf = null;
    }
  };
  private IClarionService mClarionServiceIf = null;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setTheme(16973931);
  }
  
  protected void onStart()
  {
    super.onStart();
    bindService(new Intent(IClarionService.class.getName()), this.clarionServiceConn, 1);
  }
  
  protected void onStop()
  {
    super.onStop();
    unbindService(this.clarionServiceConn);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.SettingBaseActivity
 * JD-Core Version:    0.7.0.1
 */