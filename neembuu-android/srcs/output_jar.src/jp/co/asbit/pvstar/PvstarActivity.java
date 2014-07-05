package jp.co.asbit.pvstar;

import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.amoad.amoadsdk.AMoAdSdkWallActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jp.co.asbit.pvstar.security.ObscuredSharedPreferences;
import jp.tjkapp.adfurikunsdk.AdfurikunWallAd;

public class PvstarActivity
  extends BaseActivity
  implements View.OnClickListener
{
  private ServiceConnection mConnection;
  private MyBindService mService;
  private ListView playerListView;
  private CustomDialog playerMenu;
  
  private void bindVideoService()
  {
    if (Util.isServiceRunning(this.mContext, VideoService.class))
    {
      this.mConnection = new ServiceConnection()
      {
        public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
        {
          PvstarActivity.this.mService = MyBindService.Stub.asInterface(paramAnonymousIBinder);
        }
        
        public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
        {
          PvstarActivity.this.mService = null;
        }
      };
      bindService(new Intent(this.mContext, VideoService.class), this.mConnection, 1);
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
        this.playerListView = new ListView(this.mContext);
        this.playerListView.setAdapter(new OptionMenuAdapter(this.mContext, 0, localArrayList));
        this.playerListView.setScrollingCacheEnabled(false);
        this.playerListView.setDividerHeight(0);
        this.playerListView.setSelector(2130837582);
        this.playerMenu = new CustomDialog(this);
        this.playerMenu.requestWindowFeature(1);
        this.playerMenu.setContentView(this.playerListView);
        this.playerMenu.getWindow().setFlags(0, 2);
        this.playerMenu.setCanceledOnTouchOutside(true);
        float f = getResources().getDisplayMetrics().density;
        this.playerMenu.getWindow().setLayout((int)(300.0F * f), -2);
        this.playerMenu.show();
        this.playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
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
                PvstarActivity.this.mService.pause();
                continue;
              }
              catch (RemoteException localRemoteException)
              {
                localRemoteException.printStackTrace();
                continue;
                PvstarActivity.this.mService.fprev();
                continue;
                PvstarActivity.this.mService.next();
                continue;
                localIntent = new Intent(PvstarActivity.this.mContext, VideoActivity.class);
                localIntent.setFlags(131072);
                PvstarActivity.this.startActivity(localIntent);
                continue;
              }
              PvstarActivity.this.playerMenu.dismiss();
              return;
              PvstarActivity.this.mService.play();
              continue;
              try
              {
                Intent localIntent;
                PvstarActivity.this.unbindService(PvstarActivity.this.mConnection);
                PvstarActivity.this.mService = null;
                PvstarActivity.this.stopService(new Intent(PvstarActivity.this.mContext, VideoService.class));
                PvstarActivity.this.stopService(new Intent(PvstarActivity.this.mContext, ProxyService.class));
                PvstarActivity.this.finish();
              }
              catch (IllegalArgumentException localIllegalArgumentException)
              {
                for (;;)
                {
                  localIllegalArgumentException.printStackTrace();
                }
              }
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
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    }
    for (;;)
    {
      super.onClick(paramView);
      return;
      showPlayerMenu();
      continue;
      Intent localIntent = new Intent(this.mContext, VideoActivity.class);
      localIntent.setFlags(131072);
      startActivity(localIntent);
      continue;
      startActivity(new Intent(this.mContext, VideoRankActivity.class));
      continue;
      startActivity(new Intent(this.mContext, SearchActivity.class));
      continue;
      startActivity(new Intent(this.mContext, CategoryActivity.class));
      continue;
      startActivity(new Intent(this.mContext, PlaylistSearchActivity.class));
      continue;
      startActivity(new Intent(this.mContext, ChannelSearchActivity.class));
      continue;
      startActivity(new Intent(this.mContext, MylistsFlagmentsActivity.class));
      continue;
      startActivity(new Intent(this.mContext, TimelineActivity.class));
      continue;
      if (Locale.JAPAN.equals(Locale.getDefault()))
      {
        startActivity(new Intent(this.mContext, AMoAdSdkWallActivity.class));
      }
      else
      {
        AdfurikunWallAd.initializeWallAdSetting(this, "5315c44ebb323cc86b00000a");
        AdfurikunWallAd.showWallAd(this, null);
        continue;
        startActivity(new Intent(this.mContext, SettingActivity.class));
        continue;
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://sp.pvstar.dooga.org/apps/help")));
      }
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentAndTitle(2130903090, 2130903066);
    ((RelativeLayout)findViewById(2131492915)).setOnClickListener(this);
    ((RelativeLayout)findViewById(2131492918)).setOnClickListener(this);
    ((RelativeLayout)findViewById(2131492921)).setOnClickListener(this);
    ((RelativeLayout)findViewById(2131492976)).setOnClickListener(this);
    ((RelativeLayout)findViewById(2131492979)).setOnClickListener(this);
    ((RelativeLayout)findViewById(2131492982)).setOnClickListener(this);
    ((RelativeLayout)findViewById(2131492985)).setOnClickListener(this);
    ((RelativeLayout)findViewById(2131492988)).setOnClickListener(this);
    ((RelativeLayout)findViewById(2131492991)).setOnClickListener(this);
    ((RelativeLayout)findViewById(2131492994)).setOnClickListener(this);
    ((RelativeLayout)findViewById(2131492997)).setOnClickListener(this);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131427329, paramMenu);
    return super.onCreateOptionsMenu(paramMenu);
  }
  
  protected void onDestroy()
  {
    try
    {
      if (this.playerListView != null) {
        this.playerListView.setOnItemClickListener(null);
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
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    }
    for (boolean bool = super.onOptionsItemSelected(paramMenuItem);; bool = true)
    {
      return bool;
      showPlayerMenu();
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
  
  protected void onStart()
  {
    super.onStart();
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
    if (localSharedPreferences.getBoolean("app_first_boot_22", true))
    {
      SharedPreferences.Editor localEditor2 = localSharedPreferences.edit();
      localEditor2.putBoolean("app_first_boot_22", false);
      if ((Build.MODEL.equals("ISW11M")) || (Build.MODEL.equals("SC-03D"))) {
        localEditor2.putBoolean("pseudo_surface", true);
      }
      localEditor2.commit();
      new AlertDialog.Builder(this).setTitle(2131296258).setMessage(2131296259).setPositiveButton(2131296470, null).show();
    }
    if (!localSharedPreferences.getBoolean("pass_encrypted", false))
    {
      if ((localSharedPreferences.contains("niconico_id")) || (localSharedPreferences.contains("niconico_passwd")))
      {
        SharedPreferences.Editor localEditor1 = new ObscuredSharedPreferences(this.mContext, PreferenceManager.getDefaultSharedPreferences(this.mContext)).edit();
        String str1 = localSharedPreferences.getString("niconico_id", null);
        if (str1 != null) {
          localEditor1.putString("niconico_id", str1);
        }
        String str2 = localSharedPreferences.getString("niconico_passwd", null);
        if (str2 != null) {
          localEditor1.putString("niconico_passwd", str2);
        }
        localEditor1.commit();
      }
      localSharedPreferences.edit().putBoolean("pass_encrypted", true).commit();
    }
    if ((getPackageName().endsWith("pvstar")) && (Util.shouldShowReviewRequest(this.mContext))) {
      new AlertDialog.Builder(this).setTitle(2131296260).setMessage(2131296261).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + PvstarActivity.this.getPackageName()));
          PvstarActivity.this.startActivity(localIntent);
          paramAnonymousDialogInterface.dismiss();
        }
      }).setNegativeButton(2131296382, null).show();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.PvstarActivity
 * JD-Core Version:    0.7.0.1
 */