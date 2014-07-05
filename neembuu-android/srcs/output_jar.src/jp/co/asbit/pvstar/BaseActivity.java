package jp.co.asbit.pvstar;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.clarion.android.appmgr.service.IClarionService;
import com.clarion.android.appmgr.service.IClarionService.Stub;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public abstract class BaseActivity
  extends FragmentActivity
  implements View.OnClickListener
{
  private static final String MY_AD_UNIT_ID = "2e952a47604e416f";
  private AdView adView;
  private ServiceConnection clarionServiceConn = new ServiceConnection()
  {
    @SuppressLint({"InlinedApi"})
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      BaseActivity.this.mClarionServiceIf = IClarionService.Stub.asInterface(paramAnonymousIBinder);
      try
      {
        if (BaseActivity.this.mClarionServiceIf.getState() == 3)
        {
          Intent localIntent = new Intent(BaseActivity.this.mContext, Drv_PvstarActivity.class);
          localIntent.setFlags(268533760);
          BaseActivity.this.startActivity(localIntent);
          BaseActivity.this.finish();
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
      BaseActivity.this.mClarionServiceIf = null;
    }
  };
  private IClarionService mClarionServiceIf = null;
  protected Context mContext;
  protected float mDensity;
  protected int sdkint = Build.VERSION.SDK_INT;
  
  protected void backHome()
  {
    Intent localIntent = new Intent(getApplicationContext(), PvstarActivity.class);
    localIntent.setFlags(67108864);
    startActivity(localIntent);
    finish();
    overridePendingTransition(2130968582, 2130968583);
  }
  
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    }
    for (;;)
    {
      return;
      onSearchRequested();
      continue;
      backHome();
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mContext = getApplicationContext();
    this.mDensity = this.mContext.getResources().getDisplayMetrics().density;
    if (!PreferenceManager.getDefaultSharedPreferences(this.mContext).getBoolean("status_bar", false)) {
      getWindow().addFlags(1024);
    }
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool = true;
    switch (paramMenuItem.getItemId())
    {
    default: 
      bool = super.onOptionsItemSelected(paramMenuItem);
    }
    for (;;)
    {
      return bool;
      finish();
      continue;
      onSearchRequested();
      continue;
      startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://sp.pvstar.dooga.org/apps/help")));
      continue;
      startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
      continue;
      Intent localIntent = new Intent("android.intent.action.SEND");
      localIntent.putExtra("android.intent.extra.TEXT", getText(2131296256) + "https://play.google.com/store/apps/details?id=" + getPackageName());
      localIntent.setType("text/plain");
      startActivity(localIntent);
      continue;
      startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://sp.pvstar.dooga.org/apps/about")));
    }
  }
  
  protected void onPause()
  {
    super.onPause();
    if (this.adView != null) {}
    try
    {
      ((LinearLayout)findViewById(2131492874)).removeAllViews();
      this.adView.destroy();
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        localException.printStackTrace();
      }
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131492874);
    if (localLinearLayout != null)
    {
      this.adView = new AdView(this, AdSize.BANNER, "2e952a47604e416f");
      localLinearLayout.removeAllViews();
      localLinearLayout.setGravity(1);
      localLinearLayout.addView(this.adView);
      AdRequest localAdRequest = new AdRequest();
      this.adView.loadAd(localAdRequest);
    }
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
  
  @SuppressLint({"NewApi"})
  protected void setContentAndTitle(int paramInt1, int paramInt2)
  {
    if (this.sdkint < 11) {
      requestWindowFeature(7);
    }
    setContentView(paramInt1);
    if (this.sdkint < 11)
    {
      getWindow().setFeatureInt(7, paramInt2);
      setTitleBar();
    }
    for (;;)
    {
      return;
      ActionBar localActionBar = getActionBar();
      switch (paramInt2)
      {
      default: 
        break;
      case 2130903066: 
        localActionBar.setDisplayShowTitleEnabled(false);
        localActionBar.setDisplayUseLogoEnabled(true);
        if (this.sdkint >= 14) {
          localActionBar.setLogo(2130837585);
        }
        if (this.sdkint >= 14) {
          localActionBar.setHomeButtonEnabled(false);
        }
        if (this.sdkint < 14)
        {
          localActionBar.setDisplayShowTitleEnabled(true);
          localActionBar.setDisplayUseLogoEnabled(false);
          localActionBar.setDisplayShowHomeEnabled(false);
          localActionBar.setDisplayHomeAsUpEnabled(false);
          localActionBar.setTitle(getString(2131296256));
        }
        break;
      case 2130903080: 
      case 2130903086: 
        localActionBar.setDisplayShowTitleEnabled(true);
        localActionBar.setDisplayUseLogoEnabled(false);
        localActionBar.setDisplayShowHomeEnabled(false);
        localActionBar.setDisplayHomeAsUpEnabled(true);
        if (this.sdkint >= 14) {
          localActionBar.setHomeButtonEnabled(true);
        }
        break;
      }
    }
  }
  
  @SuppressLint({"NewApi"})
  protected void setTitle(String paramString)
  {
    if (this.sdkint < 11)
    {
      ImageView localImageView = (ImageView)findViewById(2131492906);
      if (localImageView != null) {
        localImageView.setVisibility(8);
      }
      TextView localTextView = (TextView)findViewById(2131492907);
      localTextView.setVisibility(0);
      localTextView.setText(paramString);
    }
    for (;;)
    {
      return;
      getActionBar().setTitle(paramString);
    }
  }
  
  protected void setTitleBar()
  {
    ImageButton localImageButton1 = (ImageButton)findViewById(2131492908);
    if (localImageButton1 != null) {
      localImageButton1.setOnClickListener(this);
    }
    ImageButton localImageButton2 = (ImageButton)findViewById(2131492909);
    if (localImageButton2 != null) {
      localImageButton2.setOnClickListener(this);
    }
    ImageButton localImageButton3 = (ImageButton)findViewById(2131492974);
    if (localImageButton3 != null) {
      localImageButton3.setOnClickListener(this);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.BaseActivity
 * JD-Core Version:    0.7.0.1
 */