package jp.co.asbit.pvstar;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceScreen;
import android.view.WindowManager.BadTokenException;
import java.util.ArrayList;
import jp.co.asbit.pvstar.security.EncryptedEditTextPreference;

public abstract class SettingAccountActivity
  extends SettingBaseActivity
  implements SharedPreferences.OnSharedPreferenceChangeListener
{
  protected String ID;
  protected String LOGINTEST;
  protected String PASSWD;
  protected EncryptedEditTextPreference id;
  protected CheckBoxPreference loginTest;
  protected Context mContext;
  protected EncryptedEditTextPreference passwd;
  protected int prefResource;
  protected ProgressDialog progressDialog;
  
  protected abstract void importPlaylists(ArrayList<Playlist> paramArrayList);
  
  protected void importPlaylistsDialog(final ArrayList<Playlist> paramArrayList)
  {
    final boolean[] arrayOfBoolean = new boolean[paramArrayList.size()];
    String[] arrayOfString = new String[paramArrayList.size()];
    int i = 0;
    for (;;)
    {
      if (i >= paramArrayList.size()) {}
      try
      {
        new AlertDialog.Builder(this).setTitle(2131296338).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            ArrayList localArrayList = new ArrayList();
            for (int i = 0;; i++)
            {
              if (i >= paramArrayList.size())
              {
                if (localArrayList.size() > 0) {
                  SettingAccountActivity.this.importPlaylists(localArrayList);
                }
                return;
              }
              if (arrayOfBoolean[i] != 0) {
                localArrayList.add((Playlist)paramArrayList.get(i));
              }
            }
          }
        }).setNegativeButton(2131296382, null).setMultiChoiceItems(arrayOfString, null, new DialogInterface.OnMultiChoiceClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, boolean paramAnonymousBoolean)
          {
            arrayOfBoolean[paramAnonymousInt] = paramAnonymousBoolean;
          }
        }).show();
        return;
        arrayOfString[i] = ((Playlist)paramArrayList.get(i)).getTitle();
        arrayOfBoolean[i] = false;
        i++;
      }
      catch (WindowManager.BadTokenException localBadTokenException)
      {
        for (;;)
        {
          localBadTokenException.printStackTrace();
        }
      }
    }
  }
  
  protected boolean isLoginChecked()
  {
    if (!this.loginTest.isChecked()) {
      new AlertDialog.Builder(this).setTitle(2131296347).setMessage(2131296347).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
        }
      }).show();
    }
    for (boolean bool = false;; bool = true) {
      return bool;
    }
  }
  
  protected abstract boolean loginBackground(String paramString1, String paramString2);
  
  protected void loginTest()
  {
    this.loginTest.setChecked(false);
    AsyncTask local2 = new AsyncTask()
    {
      protected Boolean doInBackground(String... paramAnonymousVarArgs)
      {
        return Boolean.valueOf(SettingAccountActivity.this.loginBackground(paramAnonymousVarArgs[0], paramAnonymousVarArgs[1]));
      }
      
      protected void onPostExecute(final Boolean paramAnonymousBoolean)
      {
        if ((SettingAccountActivity.this.progressDialog != null) && (SettingAccountActivity.this.progressDialog.isShowing())) {}
        try
        {
          SettingAccountActivity.this.progressDialog.dismiss();
          if (paramAnonymousBoolean.booleanValue())
          {
            str = SettingAccountActivity.this.getString(2131296346);
            new AlertDialog.Builder(SettingAccountActivity.this).setTitle(str).setMessage(str).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
              {
                SettingAccountActivity.this.loginTest.setChecked(paramAnonymousBoolean.booleanValue());
                SettingAccountActivity.this.setSummary();
                paramAnonymous2DialogInterface.dismiss();
              }
            }).show();
            return;
          }
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          for (;;)
          {
            localIllegalArgumentException.printStackTrace();
            continue;
            String str = SettingAccountActivity.this.getString(2131296347);
          }
        }
      }
      
      protected void onPreExecute()
      {
        SettingAccountActivity.this.progressDialog = new ProgressDialog(SettingAccountActivity.this);
        SettingAccountActivity.this.progressDialog.setMessage(SettingAccountActivity.this.getString(2131296345));
        SettingAccountActivity.this.progressDialog.setIndeterminate(true);
        SettingAccountActivity.this.progressDialog.setProgressStyle(0);
        SettingAccountActivity.this.progressDialog.setCancelable(false);
        SettingAccountActivity.this.progressDialog.show();
      }
    };
    String[] arrayOfString = new String[2];
    arrayOfString[0] = this.id.getText();
    arrayOfString[1] = this.passwd.getText();
    local2.execute(arrayOfString);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    addPreferencesFromResource(this.prefResource);
    this.id = ((EncryptedEditTextPreference)getPreferenceScreen().findPreference(this.ID));
    this.passwd = ((EncryptedEditTextPreference)getPreferenceScreen().findPreference(this.PASSWD));
    this.loginTest = ((CheckBoxPreference)getPreferenceScreen().findPreference(this.LOGINTEST));
    this.loginTest.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        SettingAccountActivity.this.loginTest();
        return false;
      }
    });
    this.mContext = getApplicationContext();
    setSummary();
  }
  
  protected void onDestroy()
  {
    this.progressDialog = null;
    super.onDestroy();
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
    if ((this.id.getText() != null) && (this.id.getText().length() > 0) && (this.passwd.getText() != null) && (this.passwd.getText().length() > 0) && ((paramString.equals(this.ID)) || (paramString.equals(this.PASSWD)))) {
      loginTest();
    }
  }
  
  protected void setSummary()
  {
    if ((this.id.getText() != null) && (this.id.getText().length() > 0))
    {
      this.id.setSummary(this.id.getText());
      if ((this.passwd.getText() == null) || (this.passwd.getText().length() <= 0)) {
        break label101;
      }
      this.passwd.setSummary("********");
      label69:
      if (!this.loginTest.isChecked()) {
        break label113;
      }
      this.loginTest.setSummary(2131296348);
    }
    for (;;)
    {
      return;
      this.id.setSummary(2131296474);
      break;
      label101:
      this.passwd.setSummary(2131296474);
      break label69;
      label113:
      this.loginTest.setSummary(2131296349);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.SettingAccountActivity
 * JD-Core Version:    0.7.0.1
 */