package jp.co.asbit.pvstar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import com.clarion.android.appmgr.service.IClarionCallback;
import com.clarion.android.appmgr.service.IClarionCallback.Stub;
import com.clarion.android.appmgr.service.IClarionService;
import com.clarion.android.appmgr.service.IClarionService.Stub;

public abstract class Drv_BaseActivity
  extends Activity
{
  public static final String FILE_NAME = "file_name";
  public static final int MESSAGE_JPG_AVAILABLE = 1;
  private static final String TAG = "BaseActivity";
  private IClarionCallback callback = new IClarionCallback.Stub()
  {
    public void onAccessoryNotify(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int[] paramAnonymousArrayOfInt, String paramAnonymousString)
      throws RemoteException
    {
      Log.d("BaseActivity", "onAccessoryNotify");
      Drv_BaseActivity.this.mAccessoryHandler.sendMessage(Drv_BaseActivity.this.mAccessoryHandler.obtainMessage(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousArrayOfInt));
    }
  };
  private ServiceConnection clarionServiceConn = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      Drv_BaseActivity.this.mClarionServiceIf = IClarionService.Stub.asInterface(paramAnonymousIBinder);
      try
      {
        Drv_BaseActivity.this.mClarionServiceIf.registerCallback(Drv_BaseActivity.this.callback, Drv_BaseActivity.this.getApplicationContext().getPackageName());
        if (Drv_BaseActivity.this.mClarionServiceIf.getState() == 3) {}
        for (Drv_BaseActivity.this.mIsConnected = true;; Drv_BaseActivity.this.mIsConnected = false)
        {
          int i = Drv_BaseActivity.this.mClarionServiceIf.getDrivingSts();
          Drv_BaseActivity.this.onChangeVehicleState(i);
          break;
        }
        return;
      }
      catch (RemoteException localRemoteException) {}
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      Drv_BaseActivity.this.mClarionServiceIf = null;
    }
  };
  private Handler mAccessoryHandler;
  protected IClarionService mClarionServiceIf = null;
  protected Context mContext;
  protected float mDensity;
  protected boolean mIsConnected = false;
  protected boolean mIsForeground = false;
  
  private void OnCLStateChange(int paramInt)
  {
    if (paramInt == 3) {
      this.mIsConnected = true;
    }
    for (;;)
    {
      return;
      if (paramInt == 0) {
        this.mIsConnected = false;
      }
    }
  }
  
  protected void OnCLNotifyCommand(int paramInt1, int paramInt2, Object paramObject)
  {
    Log.d("BaseActivity", "super.OnCLNotifyCommand");
    switch (paramInt1)
    {
    }
    for (;;)
    {
      return;
      Log.d("BaseActivity", "PROTOCOL_GLS");
      continue;
      Log.d("BaseActivity", "PROTOCOL_NAVI");
      continue;
      Log.d("BaseActivity", "PROTOCOL_VEHICLE_DATA");
      switch (paramInt2)
      {
      default: 
        break;
      case 2: 
        try
        {
          if (this.mClarionServiceIf == null) {
            continue;
          }
          onChangeVehicleState(this.mClarionServiceIf.getDrivingSts());
        }
        catch (RemoteException localRemoteException)
        {
          localRemoteException.printStackTrace();
        }
        continue;
        Log.d("BaseActivity", "PROTOCOL_APP_COMMON");
        continue;
        Log.d("BaseActivity", "PROTOCOL_APP_MGR");
        continue;
        Log.d("BaseActivity", "PROTOCOL_UNKNOWN");
      }
    }
  }
  
  protected void driveModeAlert()
  {
    View localView = getLayoutInflater().inflate(2130903054, null);
    Button localButton = (Button)localView.findViewById(2131492903);
    final AlertDialog localAlertDialog = new AlertDialog.Builder(this).setView(localView).show();
    WindowManager.LayoutParams localLayoutParams = localAlertDialog.getWindow().getAttributes();
    this.mDensity = this.mContext.getResources().getDisplayMetrics().density;
    localLayoutParams.width = ((int)(360.0F * this.mDensity));
    localAlertDialog.getWindow().setAttributes(localLayoutParams);
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if ((localAlertDialog != null) && (localAlertDialog.isShowing())) {
          localAlertDialog.dismiss();
        }
      }
    });
  }
  
  protected abstract void onChangeVehicleState(int paramInt);
  
  @SuppressLint({"HandlerLeak"})
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mContext = getApplicationContext();
    this.mDensity = this.mContext.getResources().getDisplayMetrics().density;
    getWindow().addFlags(1024);
    this.mAccessoryHandler = new Handler()
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        switch (paramAnonymousMessage.what)
        {
        default: 
          super.handleMessage(paramAnonymousMessage);
        }
        for (;;)
        {
          return;
          Drv_BaseActivity.this.OnCLStateChange(paramAnonymousMessage.arg1);
          continue;
          Drv_BaseActivity.this.OnCLNotifyCommand(paramAnonymousMessage.arg1, paramAnonymousMessage.arg2, paramAnonymousMessage.obj);
        }
      }
    };
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    }
    for (boolean bool = super.onOptionsItemSelected(paramMenuItem);; bool = true)
    {
      return bool;
      finish();
    }
  }
  
  protected void onPause()
  {
    super.onPause();
    this.mIsForeground = false;
  }
  
  protected void onResume()
  {
    super.onResume();
    this.mIsForeground = true;
  }
  
  protected void onStart()
  {
    super.onStart();
    bindService(new Intent(IClarionService.class.getName()), this.clarionServiceConn, 1);
  }
  
  protected void onStop()
  {
    super.onStop();
    if (this.mClarionServiceIf != null) {}
    try
    {
      this.mClarionServiceIf.unregisterCallback(this.callback, getPackageName());
      label28:
      unbindService(this.clarionServiceConn);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      break label28;
    }
  }
  
  @SuppressLint({"NewApi"})
  protected void setContentAndTitle(int paramInt1, int paramInt2)
  {
    setContentView(paramInt1);
    ActionBar localActionBar = getActionBar();
    switch (paramInt2)
    {
    }
    for (;;)
    {
      return;
      localActionBar.setDisplayShowTitleEnabled(false);
      localActionBar.setDisplayUseLogoEnabled(true);
      localActionBar.setLogo(2130837585);
      localActionBar.setHomeButtonEnabled(false);
      continue;
      localActionBar.setDisplayShowTitleEnabled(true);
      localActionBar.setDisplayUseLogoEnabled(false);
      localActionBar.setDisplayShowHomeEnabled(false);
      localActionBar.setDisplayHomeAsUpEnabled(true);
      localActionBar.setHomeButtonEnabled(true);
    }
  }
  
  @SuppressLint({"NewApi"})
  protected void setTitle(String paramString)
  {
    getActionBar().setTitle(paramString);
  }
  
  protected void setTitleBar()
  {
    ((ImageButton)findViewById(2131492908)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Drv_BaseActivity.this.finish();
      }
    });
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.Drv_BaseActivity
 * JD-Core Version:    0.7.0.1
 */