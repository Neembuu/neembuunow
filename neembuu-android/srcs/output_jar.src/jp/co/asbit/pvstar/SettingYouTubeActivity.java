package jp.co.asbit.pvstar;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;
import java.util.ArrayList;
import jp.co.asbit.pvstar.api.GetYouTubeUserPlaylistsTask;
import jp.co.asbit.pvstar.api.ImportYouTubeUserPlaylists;
import jp.co.asbit.pvstar.security.EncryptedEditTextPreference;
import jp.co.asbit.pvstar.video.YouTube;

public class SettingYouTubeActivity
  extends SettingAccountActivity
{
  protected void importPlaylists(ArrayList<Playlist> paramArrayList)
  {
    ImportYouTubeUserPlaylists local2 = new ImportYouTubeUserPlaylists(getApplicationContext())
    {
      protected void onCancelled()
      {
        if ((SettingYouTubeActivity.this.progressDialog != null) && (SettingYouTubeActivity.this.progressDialog.isShowing())) {
          SettingYouTubeActivity.this.progressDialog.dismiss();
        }
        super.onCancelled();
      }
      
      protected void onPostExecute(Integer paramAnonymousInteger)
      {
        if ((SettingYouTubeActivity.this.progressDialog != null) && (SettingYouTubeActivity.this.progressDialog.isShowing())) {
          SettingYouTubeActivity.this.progressDialog.dismiss();
        }
        super.onPostExecute(paramAnonymousInteger);
        if (paramAnonymousInteger != null)
        {
          Context localContext = SettingYouTubeActivity.this.mContext;
          SettingYouTubeActivity localSettingYouTubeActivity = SettingYouTubeActivity.this;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = paramAnonymousInteger;
          Toast.makeText(localContext, localSettingYouTubeActivity.getString(2131296340, arrayOfObject), 0).show();
        }
      }
      
      protected void onPreExecute()
      {
        SettingYouTubeActivity.this.progressDialog = new ProgressDialog(SettingYouTubeActivity.this);
        SettingYouTubeActivity.this.progressDialog.setMessage(SettingYouTubeActivity.this.getString(2131296481));
        SettingYouTubeActivity.this.progressDialog.setIndeterminate(true);
        SettingYouTubeActivity.this.progressDialog.setProgressStyle(0);
        SettingYouTubeActivity.this.progressDialog.show();
        super.onPreExecute();
      }
    };
    ArrayList[] arrayOfArrayList = new ArrayList[1];
    arrayOfArrayList[0] = paramArrayList;
    local2.execute(arrayOfArrayList);
  }
  
  protected boolean loginBackground(String paramString1, String paramString2)
  {
    return YouTube.login(paramString1, paramString2);
  }
  
  public void onCreate(Bundle paramBundle)
  {
    this.ID = "youtube_id";
    this.PASSWD = "youtube_passwd";
    this.LOGINTEST = "youtube_logintest";
    this.prefResource = 2131034120;
    super.onCreate(paramBundle);
    findPreference("import_playlists").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        if (!SettingYouTubeActivity.this.isLoginChecked()) {}
        for (;;)
        {
          return false;
          new GetYouTubeUserPlaylistsTask(SettingYouTubeActivity.this.id.getText(), SettingYouTubeActivity.this.passwd.getText())
          {
            protected void onCancelled()
            {
              if ((SettingYouTubeActivity.this.progressDialog != null) && (SettingYouTubeActivity.this.progressDialog.isShowing())) {
                SettingYouTubeActivity.this.progressDialog.dismiss();
              }
              super.onCancelled();
            }
            
            protected void onPostExecute(ArrayList<Playlist> paramAnonymous2ArrayList)
            {
              if ((SettingYouTubeActivity.this.progressDialog != null) && (SettingYouTubeActivity.this.progressDialog.isShowing())) {
                SettingYouTubeActivity.this.progressDialog.dismiss();
              }
              if ((paramAnonymous2ArrayList != null) && (paramAnonymous2ArrayList.size() > 0)) {
                SettingYouTubeActivity.this.importPlaylistsDialog(paramAnonymous2ArrayList);
              }
              super.onPostExecute(paramAnonymous2ArrayList);
            }
            
            protected void onPreExecute()
            {
              SettingYouTubeActivity.this.progressDialog = new ProgressDialog(SettingYouTubeActivity.this);
              SettingYouTubeActivity.this.progressDialog.setMessage(SettingYouTubeActivity.this.getString(2131296481));
              SettingYouTubeActivity.this.progressDialog.setIndeterminate(true);
              SettingYouTubeActivity.this.progressDialog.setProgressStyle(0);
              SettingYouTubeActivity.this.progressDialog.show();
              this.favorite = new Playlist();
              this.favorite.setId("FAVORITE");
              this.favorite.setTitle(SettingYouTubeActivity.this.mContext.getString(2131296342));
              super.onPreExecute();
            }
          }.execute(new Integer[0]);
        }
      }
    });
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.SettingYouTubeActivity
 * JD-Core Version:    0.7.0.1
 */