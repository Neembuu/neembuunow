package jp.co.asbit.pvstar;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.provider.SearchRecentSuggestions;
import jp.co.asbit.pvstar.cache.CacheManager;
import jp.co.asbit.pvstar.cache.CacheManager.CachingDisableException;

public class SettingActivity
  extends SettingBaseActivity
  implements SharedPreferences.OnSharedPreferenceChangeListener
{
  private ListPreference cache_max_size;
  private Preference clear_cache;
  private ListPreference default_search_engine;
  private Context mContext;
  private ListPreference quarity;
  private ListPreference screen_orientation;
  
  private void setSummary()
  {
    this.quarity.setSummary(this.quarity.getEntry());
    this.default_search_engine.setSummary(this.default_search_engine.getEntry());
    this.screen_orientation.setSummary(this.screen_orientation.getEntry());
    this.cache_max_size.setSummary(this.cache_max_size.getEntry());
    try
    {
      CacheManager localCacheManager = new CacheManager(this.mContext);
      int i = (int)(localCacheManager.getTotalSize(localCacheManager.getCacheFiles()) / 1024L / 1024L);
      Preference localPreference = this.clear_cache;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(i);
      localPreference.setSummary(getString(2131296325, arrayOfObject));
      return;
    }
    catch (CacheManager.CachingDisableException localCachingDisableException)
    {
      for (;;)
      {
        localCachingDisableException.printStackTrace();
      }
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mContext = getApplicationContext();
    addPreferencesFromResource(2131034116);
    this.quarity = ((ListPreference)getPreferenceScreen().findPreference("quarity"));
    this.default_search_engine = ((ListPreference)getPreferenceScreen().findPreference("default_search_engine"));
    this.screen_orientation = ((ListPreference)getPreferenceScreen().findPreference("screen_orientation"));
    this.cache_max_size = ((ListPreference)getPreferenceScreen().findPreference("cache_max_size"));
    this.clear_cache = getPreferenceScreen().findPreference("clear_cache");
    findPreference("backup_settings").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        Intent localIntent = new Intent(SettingActivity.this.mContext, SettingBackupActivity.class);
        SettingActivity.this.startActivity(localIntent);
        return false;
      }
    });
    findPreference("twitter_settings").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        Intent localIntent = new Intent(SettingActivity.this.mContext, SettingTwitterActivity.class);
        SettingActivity.this.startActivity(localIntent);
        return false;
      }
    });
    findPreference("youtube_settings").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        SettingActivity.this.startActivity(new Intent(SettingActivity.this.mContext, SettingYouTubeActivity.class));
        return false;
      }
    });
    findPreference("niconico_settings").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        SettingActivity.this.startActivity(new Intent(SettingActivity.this.mContext, SettingNiconicoActivity.class));
        return false;
      }
    });
    findPreference("help").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://sp.pvstar.dooga.org/apps/help"));
        SettingActivity.this.startActivity(localIntent);
        return false;
      }
    });
    findPreference("share").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        Intent localIntent = new Intent("android.intent.action.SEND");
        localIntent.putExtra("android.intent.extra.TEXT", "PVSTAR+ https://play.google.com/store/apps/details?id=" + SettingActivity.this.getPackageName());
        localIntent.setType("text/plain");
        SettingActivity.this.startActivity(localIntent);
        return false;
      }
    });
    findPreference("clear_search_history").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        new AlertDialog.Builder(SettingActivity.this).setTitle(SettingActivity.this.getText(2131296313)).setMessage(2131296315).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            new SearchRecentSuggestions(SettingActivity.this.mContext, "jp.co.asbit.pvstar.MySuggestionProvider", 1).clearHistory();
          }
        }).setNegativeButton(2131296382, null).show();
        return false;
      }
    });
    this.clear_cache = findPreference("clear_cache");
    this.clear_cache.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        new AlertDialog.Builder(SettingActivity.this).setTitle(SettingActivity.this.getText(2131296322)).setMessage(2131296324).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            AsyncTask local1 = new AsyncTask()
            {
              protected String doInBackground(Long... paramAnonymous3VarArgs)
              {
                try
                {
                  new CacheManager(SettingActivity.this.mContext).trimCache(paramAnonymous3VarArgs[0].longValue());
                  return null;
                }
                catch (CacheManager.CachingDisableException localCachingDisableException)
                {
                  for (;;)
                  {
                    localCachingDisableException.printStackTrace();
                  }
                }
              }
              
              protected void onPostExecute(String paramAnonymous3String)
              {
                SettingActivity.this.setSummary();
              }
            };
            Long[] arrayOfLong = new Long[1];
            arrayOfLong[0] = Long.valueOf(0L);
            local1.execute(arrayOfLong);
          }
        }).setNegativeButton(2131296382, null).show();
        return false;
      }
    });
    try
    {
      String str = getPackageManager().getPackageInfo(getPackageName(), 1).versionName;
      findPreference("version_info").setSummary(str);
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      try
      {
        for (;;)
        {
          new CacheManager(this.mContext);
          setSummary();
          return;
          localNameNotFoundException = localNameNotFoundException;
          localNameNotFoundException.printStackTrace();
        }
      }
      catch (CacheManager.CachingDisableException localCachingDisableException)
      {
        for (;;)
        {
          this.clear_cache.setEnabled(false);
          this.cache_max_size.setEnabled(false);
        }
      }
    }
  }
  
  protected void onPause()
  {
    super.onPause();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }
  
  protected void onResume()
  {
    super.onResume();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    setSummary();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.SettingActivity
 * JD-Core Version:    0.7.0.1
 */