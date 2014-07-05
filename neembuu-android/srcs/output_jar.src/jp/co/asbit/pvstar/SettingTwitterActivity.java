package jp.co.asbit.pvstar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceManager;
import jp.co.asbit.pvstar.security.ObscuredSharedPreferences;
import jp.co.asbit.pvstar.security.ObscuredSharedPreferences.Editor;

public class SettingTwitterActivity
  extends SettingBaseActivity
{
  private static final int REQUEST_OAUTH = 1;
  private Context mContext;
  private Preference twitter_settings;
  
  private void setSummary()
  {
    ObscuredSharedPreferences localObscuredSharedPreferences = new ObscuredSharedPreferences(this.mContext, PreferenceManager.getDefaultSharedPreferences(this.mContext));
    if ((localObscuredSharedPreferences.contains("twitter_token")) && (localObscuredSharedPreferences.contains("twitter_token_secret"))) {
      this.twitter_settings.setSummary(2131296473);
    }
    for (;;)
    {
      return;
      this.twitter_settings.setSummary(2131296474);
    }
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    ObscuredSharedPreferences localObscuredSharedPreferences = new ObscuredSharedPreferences(this.mContext, PreferenceManager.getDefaultSharedPreferences(this.mContext));
    if ((paramInt2 == -1) && (paramInt1 == 1))
    {
      String str1 = paramIntent.getStringExtra("token");
      String str2 = paramIntent.getStringExtra("token_secret");
      ObscuredSharedPreferences.Editor localEditor2 = localObscuredSharedPreferences.edit();
      localEditor2.putString("twitter_token", str1);
      localEditor2.putString("twitter_token_secret", str2);
      localEditor2.commit();
      this.twitter_settings.setSummary(2131296473);
    }
    for (;;)
    {
      setSummary();
      return;
      if ((paramInt2 == 0) && (paramInt1 == 1))
      {
        ObscuredSharedPreferences.Editor localEditor1 = localObscuredSharedPreferences.edit();
        localEditor1.remove("twitter_token");
        localEditor1.remove("twitter_token_secret");
        localEditor1.commit();
        this.twitter_settings.setSummary(2131296474);
      }
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mContext = getApplicationContext();
    addPreferencesFromResource(2131034119);
    this.twitter_settings = findPreference("twitter_settings");
    this.twitter_settings.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        Intent localIntent = new Intent(SettingTwitterActivity.this.mContext, TwitterOAuthActivity.class);
        SettingTwitterActivity.this.startActivityForResult(localIntent, 1);
        return false;
      }
    });
    setSummary();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.SettingTwitterActivity
 * JD-Core Version:    0.7.0.1
 */