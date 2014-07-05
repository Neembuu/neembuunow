package jp.co.asbit.pvstar;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;
import java.util.ArrayList;
import jp.co.asbit.pvstar.api.GetNicoUserPlaylistsTask;
import jp.co.asbit.pvstar.api.ImportNicoUserPlaylists;
import jp.co.asbit.pvstar.security.EncryptedEditTextPreference;
import jp.co.asbit.pvstar.video.NicoNico;

public class SettingNiconicoActivity
  extends SettingAccountActivity
{
  protected void importPlaylists(ArrayList<Playlist> paramArrayList)
  {
    ImportNicoUserPlaylists local2 = new ImportNicoUserPlaylists(this.mContext)
    {
      protected void onCancelled()
      {
        if ((SettingNiconicoActivity.this.progressDialog != null) && (SettingNiconicoActivity.this.progressDialog.isShowing())) {
          SettingNiconicoActivity.this.progressDialog.dismiss();
        }
        super.onCancelled();
      }
      
      protected void onPostExecute(Integer paramAnonymousInteger)
      {
        if ((SettingNiconicoActivity.this.progressDialog != null) && (SettingNiconicoActivity.this.progressDialog.isShowing())) {
          SettingNiconicoActivity.this.progressDialog.dismiss();
        }
        super.onPostExecute(paramAnonymousInteger);
        if (paramAnonymousInteger != null)
        {
          Context localContext = SettingNiconicoActivity.this.mContext;
          SettingNiconicoActivity localSettingNiconicoActivity = SettingNiconicoActivity.this;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = paramAnonymousInteger;
          Toast.makeText(localContext, localSettingNiconicoActivity.getString(2131296340, arrayOfObject), 0).show();
        }
      }
      
      protected void onPreExecute()
      {
        SettingNiconicoActivity.this.progressDialog = new ProgressDialog(SettingNiconicoActivity.this);
        SettingNiconicoActivity.this.progressDialog.setMessage(SettingNiconicoActivity.this.getString(2131296481));
        SettingNiconicoActivity.this.progressDialog.setIndeterminate(true);
        SettingNiconicoActivity.this.progressDialog.setProgressStyle(0);
        SettingNiconicoActivity.this.progressDialog.show();
        super.onPreExecute();
      }
    };
    ArrayList[] arrayOfArrayList = new ArrayList[1];
    arrayOfArrayList[0] = paramArrayList;
    local2.execute(arrayOfArrayList);
  }
  
  protected boolean loginBackground(String paramString1, String paramString2)
  {
    return NicoNico.login(paramString1, paramString2);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    this.ID = "niconico_id";
    this.PASSWD = "niconico_passwd";
    this.LOGINTEST = "niconico_logintest";
    this.prefResource = 2131034118;
    super.onCreate(paramBundle);
    findPreference("import_playlists").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        if (!SettingNiconicoActivity.this.isLoginChecked()) {}
        for (;;)
        {
          return false;
          new GetNicoUserPlaylistsTask(SettingNiconicoActivity.this.id.getText(), SettingNiconicoActivity.this.passwd.getText())
          {
            protected void onCancelled()
            {
              if ((SettingNiconicoActivity.this.progressDialog != null) && (SettingNiconicoActivity.this.progressDialog.isShowing())) {
                SettingNiconicoActivity.this.progressDialog.dismiss();
              }
              super.onCancelled();
            }
            
            protected void onPostExecute(ArrayList<Playlist> paramAnonymous2ArrayList)
            {
              if ((SettingNiconicoActivity.this.progressDialog != null) && (SettingNiconicoActivity.this.progressDialog.isShowing())) {
                SettingNiconicoActivity.this.progressDialog.dismiss();
              }
              if ((paramAnonymous2ArrayList != null) && (paramAnonymous2ArrayList.size() > 0)) {
                SettingNiconicoActivity.this.importPlaylistsDialog(paramAnonymous2ArrayList);
              }
              super.onPostExecute(paramAnonymous2ArrayList);
            }
            
            protected void onPreExecute()
            {
              SettingNiconicoActivity.this.progressDialog = new ProgressDialog(SettingNiconicoActivity.this);
              SettingNiconicoActivity.this.progressDialog.setMessage(SettingNiconicoActivity.this.getString(2131296481));
              SettingNiconicoActivity.this.progressDialog.setIndeterminate(true);
              SettingNiconicoActivity.this.progressDialog.setProgressStyle(0);
              SettingNiconicoActivity.this.progressDialog.show();
              super.onPreExecute();
            }
          }.execute(new Integer[0]);
        }
      }
    });
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.SettingNiconicoActivity
 * JD-Core Version:    0.7.0.1
 */