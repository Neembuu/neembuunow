package jp.co.asbit.pvstar;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;
import jp.co.asbit.pvstar.api.GetIsPopupPlayModelTask;

public class Drv_PvstarActivity
  extends Drv_BaseActivity
  implements View.OnClickListener
{
  private ServiceConnection mConnection;
  private boolean mDriving;
  private ListView mPlayerListView;
  private CustomDialog mPlayerMenu;
  private MyBindService mService;
  
  private void bindVideoService()
  {
    if (Util.isServiceRunning(this.mContext, VideoService.class))
    {
      this.mConnection = new ServiceConnection()
      {
        public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
        {
          Drv_PvstarActivity.this.mService = MyBindService.Stub.asInterface(paramAnonymousIBinder);
        }
        
        public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
        {
          Drv_PvstarActivity.this.mService = null;
        }
      };
      bindService(new Intent(this.mContext, VideoService.class), this.mConnection, 1);
    }
  }
  
  private void exitApp()
  {
    try
    {
      unbindService(this.mConnection);
      this.mService = null;
      stopService(new Intent(this.mContext, VideoService.class));
      stopService(new Intent(this.mContext, ProxyService.class));
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;)
      {
        localIllegalArgumentException.printStackTrace();
        finish();
      }
    }
    finally
    {
      finish();
    }
  }
  
  private void showPlayerMenu()
  {
    ArrayList localArrayList = new ArrayList();
    try
    {
      localArrayList.add(new OptionMenuItem(2131296399, 2130837546));
      if (!this.mService.isPlaying()) {
        localArrayList.add(new OptionMenuItem(2131296485, 2130837538));
      }
      for (;;)
      {
        localArrayList.add(new OptionMenuItem(2131296486, 2130837539));
        localArrayList.add(new OptionMenuItem(2131296487, 2130837536));
        localArrayList.add(new OptionMenuItem(2131296489, 2130837544));
        this.mPlayerListView = new ListView(this.mContext);
        this.mPlayerListView.setAdapter(new OptionMenuAdapter(this.mContext, 0, localArrayList));
        this.mPlayerListView.setScrollingCacheEnabled(false);
        this.mPlayerListView.setDividerHeight(0);
        this.mPlayerListView.setSelector(2130837582);
        this.mPlayerMenu = new CustomDialog(this);
        this.mPlayerMenu.requestWindowFeature(1);
        this.mPlayerMenu.setContentView(this.mPlayerListView);
        this.mPlayerMenu.getWindow().setFlags(0, 2);
        this.mPlayerMenu.setCanceledOnTouchOutside(true);
        this.mPlayerMenu.getWindow().setLayout((int)(300.0F * this.mDensity), -2);
        this.mPlayerMenu.show();
        this.mPlayerListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
          public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            OptionMenuItem localOptionMenuItem = (OptionMenuItem)((Adapter)paramAnonymousAdapterView.getAdapter()).getItem(paramAnonymousInt);
            for (;;)
            {
              try
              {
                int i = localOptionMenuItem.getTitle();
                switch (i)
                {
                }
              }
              catch (NullPointerException localNullPointerException)
              {
                localNullPointerException.printStackTrace();
                continue;
                Drv_PvstarActivity.this.mService.pause();
                continue;
              }
              catch (RemoteException localRemoteException)
              {
                localRemoteException.printStackTrace();
                continue;
                Drv_PvstarActivity.this.mService.fprev();
                continue;
                Drv_PvstarActivity.this.mService.next();
                continue;
                Intent localIntent = new Intent(Drv_PvstarActivity.this.mContext, VideoActivity.class);
                localIntent.setFlags(131072);
                Drv_PvstarActivity.this.startActivity(localIntent);
                continue;
                Drv_PvstarActivity.this.exitApp();
                continue;
              }
              Drv_PvstarActivity.this.mPlayerMenu.dismiss();
              return;
              Drv_PvstarActivity.this.mService.play();
            }
          }
        });
        return;
        localArrayList.add(new OptionMenuItem(2131296488, 2130837537));
      }
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
      }
    }
    catch (RemoteException localRemoteException)
    {
      for (;;)
      {
        localRemoteException.printStackTrace();
      }
    }
  }
  
  protected void onChangeVehicleState(int paramInt)
  {
    if (!this.mIsConnected) {
      finish();
    }
    switch (paramInt)
    {
    }
    for (;;)
    {
      return;
      this.mDriving = false;
      continue;
      this.mDriving = true;
      continue;
      this.mDriving = false;
    }
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    }
    for (;;)
    {
      return;
      if ((this.mDriving) && (Util.isPopUpPlayModel(this.mContext)))
      {
        driveModeAlert();
      }
      else
      {
        Intent localIntent = new Intent(this.mContext, VideoActivity.class);
        localIntent.putExtra("CLARION_MODE", true);
        localIntent.setFlags(131072);
        startActivity(localIntent);
        continue;
        startActivity(new Intent(this.mContext, Drv_VideoRankActivity.class));
        continue;
        startActivity(new Intent(this.mContext, Drv_MylistsActivity.class));
        continue;
        exitApp();
      }
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentAndTitle(2130903059, 2130903056);
    ((RelativeLayout)findViewById(2131492915)).setOnClickListener(this);
    ((RelativeLayout)findViewById(2131492918)).setOnClickListener(this);
    ((RelativeLayout)findViewById(2131492921)).setOnClickListener(this);
    ((RelativeLayout)findViewById(2131492924)).setOnClickListener(this);
    GetIsPopupPlayModelTask local1 = new GetIsPopupPlayModelTask()
    {
      protected void onPostExecute(Boolean paramAnonymousBoolean)
      {
        Util.setPopUpPlayModel(Drv_PvstarActivity.this.mContext, paramAnonymousBoolean.booleanValue());
      }
    };
    String[] arrayOfString = new String[1];
    arrayOfString[0] = Build.MODEL;
    local1.execute(arrayOfString);
  }
  
  protected void onDestroy()
  {
    try
    {
      if (this.mPlayerListView != null) {
        this.mPlayerListView.setOnItemClickListener(null);
      }
      this.mConnection = null;
      super.onDestroy();
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
      }
    }
  }
  
  protected void onPause()
  {
    super.onPause();
    try
    {
      unbindService(this.mConnection);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;)
      {
        localIllegalArgumentException.printStackTrace();
      }
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    bindVideoService();
  }
  
  protected void setTitleBar()
  {
    ((ImageButton)findViewById(2131492909)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Drv_PvstarActivity.this.showPlayerMenu();
      }
    });
    super.setTitleBar();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.Drv_PvstarActivity
 * JD-Core Version:    0.7.0.1
 */