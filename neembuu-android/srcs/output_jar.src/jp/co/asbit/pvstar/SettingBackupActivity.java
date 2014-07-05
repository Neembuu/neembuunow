package jp.co.asbit.pvstar;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.WindowManager.BadTokenException;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import jp.co.asbit.pvstar.api.DeleteMylistBackupPlaylists;
import jp.co.asbit.pvstar.api.GetMylistBackupTask;
import jp.co.asbit.pvstar.api.ImportMylistBackupPlaylists;
import jp.co.asbit.pvstar.api.MylistBackupTask;
import jp.co.asbit.pvstar.security.EncryptedEditTextPreference;
import jp.co.asbit.pvstar.video.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingBackupActivity
  extends SettingAccountActivity
{
  private static final String BACKUP_ID_CREATED = "BACKUP_ID_CREATED";
  private static final int CREATE_BACKUP_USER_REQUEST = 100;
  
  private void deletePlaylists(ArrayList<Playlist> paramArrayList)
  {
    DeleteMylistBackupPlaylists local8 = new DeleteMylistBackupPlaylists(this.id.getText(), this.passwd.getText())
    {
      protected void onCancelled()
      {
        if ((SettingBackupActivity.this.progressDialog != null) && (SettingBackupActivity.this.progressDialog.isShowing())) {
          SettingBackupActivity.this.progressDialog.dismiss();
        }
        super.onCancelled();
      }
      
      protected void onPostExecute(Integer paramAnonymousInteger)
      {
        if ((SettingBackupActivity.this.progressDialog != null) && (SettingBackupActivity.this.progressDialog.isShowing())) {
          SettingBackupActivity.this.progressDialog.dismiss();
        }
        super.onPostExecute(paramAnonymousInteger);
        if (paramAnonymousInteger != null)
        {
          Context localContext = SettingBackupActivity.this.mContext;
          SettingBackupActivity localSettingBackupActivity = SettingBackupActivity.this;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = paramAnonymousInteger;
          Toast.makeText(localContext, localSettingBackupActivity.getString(2131296306, arrayOfObject), 0).show();
        }
      }
      
      protected void onPreExecute()
      {
        SettingBackupActivity.this.progressDialog = new ProgressDialog(SettingBackupActivity.this);
        SettingBackupActivity.this.progressDialog.setMessage(SettingBackupActivity.this.getString(2131296481));
        SettingBackupActivity.this.progressDialog.setIndeterminate(true);
        SettingBackupActivity.this.progressDialog.setProgressStyle(0);
        SettingBackupActivity.this.progressDialog.show();
        super.onPreExecute();
      }
    };
    ArrayList[] arrayOfArrayList = new ArrayList[1];
    arrayOfArrayList[0] = paramArrayList;
    local8.execute(arrayOfArrayList);
  }
  
  private void exportPlaylists(ArrayList<Mylist> paramArrayList)
  {
    JSONArray localJSONArray1 = new JSONArray();
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    for (;;)
    {
      try
      {
        localIterator1 = paramArrayList.iterator();
        if (!localIterator1.hasNext())
        {
          MylistBackupTask local7 = new MylistBackupTask()
          {
            protected void onCancelled()
            {
              if ((SettingBackupActivity.this.progressDialog != null) && (SettingBackupActivity.this.progressDialog.isShowing())) {
                SettingBackupActivity.this.progressDialog.dismiss();
              }
              super.onCancelled();
            }
            
            protected void onPostExecute(Integer paramAnonymousInteger)
            {
              if ((SettingBackupActivity.this.progressDialog != null) && (SettingBackupActivity.this.progressDialog.isShowing())) {
                SettingBackupActivity.this.progressDialog.dismiss();
              }
              super.onPostExecute(paramAnonymousInteger);
              if (paramAnonymousInteger != null)
              {
                Context localContext = SettingBackupActivity.this.mContext;
                SettingBackupActivity localSettingBackupActivity = SettingBackupActivity.this;
                Object[] arrayOfObject = new Object[1];
                arrayOfObject[0] = paramAnonymousInteger;
                Toast.makeText(localContext, localSettingBackupActivity.getString(2131296305, arrayOfObject), 0).show();
              }
            }
            
            protected void onPreExecute()
            {
              SettingBackupActivity.this.progressDialog = new ProgressDialog(SettingBackupActivity.this);
              SettingBackupActivity.this.progressDialog.setMessage(SettingBackupActivity.this.getString(2131296481));
              SettingBackupActivity.this.progressDialog.setIndeterminate(true);
              SettingBackupActivity.this.progressDialog.setProgressStyle(0);
              SettingBackupActivity.this.progressDialog.show();
              super.onPreExecute();
            }
          };
          String[] arrayOfString = new String[3];
          arrayOfString[0] = localJSONArray1.toString();
          arrayOfString[1] = this.id.getText();
          arrayOfString[2] = this.passwd.getText();
          local7.execute(arrayOfString);
          return;
        }
      }
      catch (JSONException localJSONException)
      {
        Iterator localIterator1;
        Mylist localMylist;
        JSONObject localJSONObject1;
        ArrayList localArrayList;
        JSONArray localJSONArray2;
        Iterator localIterator2;
        localJSONException.printStackTrace();
        localVideoDbHelper.close();
        continue;
        localVideo = (Video)localIterator2.next();
        localJSONObject2 = new JSONObject();
        localJSONObject2.put("search_engine", localVideo.getSearchEngine());
        localJSONObject2.put("video_id", localVideo.getId());
        localJSONObject2.put("thumbnail_url", localVideo.getThumbnailUrl());
        localJSONObject2.put("title", localVideo.getTitle());
        localJSONObject2.put("description", localVideo.getDescription());
        localJSONObject2.put("duration", localVideo.getDuration());
        if (localVideo.getViewCount() != null) {
          break label362;
        }
        localObject2 = "0";
        localJSONObject2.put("view_count", localObject2);
        localJSONArray2.put(localJSONObject2);
        continue;
      }
      finally
      {
        localVideoDbHelper.close();
      }
      localMylist = (Mylist)localIterator1.next();
      localJSONObject1 = new JSONObject();
      localJSONObject1.put("name", localMylist.getName());
      localJSONObject1.put("description", localMylist.getDescription());
      localArrayList = localVideoDbHelper.getVideos(Long.valueOf(localMylist.getId()));
      if (localArrayList != null)
      {
        localJSONArray2 = new JSONArray();
        localIterator2 = localArrayList.iterator();
        if (localIterator2.hasNext()) {
          continue;
        }
        localJSONObject1.put("videos", localJSONArray2);
        localJSONArray1.put(localJSONObject1);
      }
    }
    for (;;)
    {
      Video localVideo;
      JSONObject localJSONObject2;
      label362:
      String str = localVideo.getViewCount();
      Object localObject2 = str;
    }
  }
  
  protected void deleteBackupDialog(final ArrayList<Playlist> paramArrayList)
  {
    final boolean[] arrayOfBoolean = new boolean[paramArrayList.size()];
    String[] arrayOfString = new String[paramArrayList.size()];
    int i = 0;
    for (;;)
    {
      if (i >= paramArrayList.size()) {}
      try
      {
        new AlertDialog.Builder(this).setTitle(2131296293).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            ArrayList localArrayList = new ArrayList();
            for (int i = 0;; i++)
            {
              if (i >= paramArrayList.size())
              {
                if (localArrayList.size() > 0) {
                  SettingBackupActivity.this.deletePlaylists(localArrayList);
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
  
  protected void importPlaylists(ArrayList<Playlist> paramArrayList)
  {
    ImportMylistBackupPlaylists local9 = new ImportMylistBackupPlaylists(this.mContext, this.id.getText(), this.passwd.getText())
    {
      protected void onCancelled()
      {
        if ((SettingBackupActivity.this.progressDialog != null) && (SettingBackupActivity.this.progressDialog.isShowing())) {
          SettingBackupActivity.this.progressDialog.dismiss();
        }
        super.onCancelled();
      }
      
      protected void onPostExecute(Integer paramAnonymousInteger)
      {
        if ((SettingBackupActivity.this.progressDialog != null) && (SettingBackupActivity.this.progressDialog.isShowing())) {
          SettingBackupActivity.this.progressDialog.dismiss();
        }
        super.onPostExecute(paramAnonymousInteger);
        if (paramAnonymousInteger != null)
        {
          Context localContext = SettingBackupActivity.this.mContext;
          SettingBackupActivity localSettingBackupActivity = SettingBackupActivity.this;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = paramAnonymousInteger;
          Toast.makeText(localContext, localSettingBackupActivity.getString(2131296340, arrayOfObject), 0).show();
        }
      }
      
      protected void onPreExecute()
      {
        SettingBackupActivity.this.progressDialog = new ProgressDialog(SettingBackupActivity.this);
        SettingBackupActivity.this.progressDialog.setMessage(SettingBackupActivity.this.getString(2131296481));
        SettingBackupActivity.this.progressDialog.setIndeterminate(true);
        SettingBackupActivity.this.progressDialog.setProgressStyle(0);
        SettingBackupActivity.this.progressDialog.show();
        super.onPreExecute();
      }
    };
    ArrayList[] arrayOfArrayList = new ArrayList[1];
    arrayOfArrayList[0] = paramArrayList;
    local9.execute(arrayOfArrayList);
  }
  
  protected boolean loginBackground(String paramString1, String paramString2)
  {
    boolean bool = false;
    HttpClient localHttpClient = new HttpClient("http://pvstar.dooga.org/api2/users/auth");
    localHttpClient.setRequestMethod(2);
    localHttpClient.setParameter("id", paramString1);
    localHttpClient.setParameter("passwd", paramString2);
    String str;
    if (localHttpClient.request()) {
      str = localHttpClient.getResponseBody();
    }
    for (;;)
    {
      try
      {
        int i = new JSONObject(str).getInt("result");
        if (i != 1) {
          continue;
        }
        bool = true;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        continue;
      }
      localHttpClient.shutdown();
      return bool;
      bool = false;
    }
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt1 == 100) && (paramInt2 == -1))
    {
      Intent localIntent = new Intent(this.mContext, SettingBackupActivity.class);
      localIntent.setFlags(65536);
      localIntent.putExtra("BACKUP_ID_CREATED", true);
      startActivity(localIntent);
      Toast.makeText(this.mContext, 2131296304, 1).show();
      finish();
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    this.ID = "backup_id";
    this.PASSWD = "backup_passwd";
    this.LOGINTEST = "backup_logintest";
    this.prefResource = 2131034117;
    super.onCreate(paramBundle);
    findPreference("create_backup_id").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        Intent localIntent = new Intent(SettingBackupActivity.this.mContext, CreateBackupAccountActivity.class);
        SettingBackupActivity.this.startActivityForResult(localIntent, 100);
        return false;
      }
    });
    findPreference("backup_export").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        if (!SettingBackupActivity.this.isLoginChecked()) {
          return false;
        }
        VideoDbHelper localVideoDbHelper = new VideoDbHelper(SettingBackupActivity.this.mContext);
        final ArrayList localArrayList = localVideoDbHelper.getMylists();
        localVideoDbHelper.close();
        final int i = localArrayList.size();
        final boolean[] arrayOfBoolean = new boolean[i];
        String[] arrayOfString = new String[i];
        for (int j = 0;; j++)
        {
          for (;;)
          {
            if (j < i) {
              break label139;
            }
            try
            {
              new AlertDialog.Builder(SettingBackupActivity.this).setTitle(2131296289).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                {
                  ArrayList localArrayList = new ArrayList();
                  for (int i = 0;; i++)
                  {
                    if (i >= i)
                    {
                      if (localArrayList.size() > 0) {
                        SettingBackupActivity.this.exportPlaylists(localArrayList);
                      }
                      return;
                    }
                    if (arrayOfBoolean[i] != 0) {
                      localArrayList.add((Mylist)localArrayList.get(i));
                    }
                  }
                }
              }).setNegativeButton(2131296382, null).setMultiChoiceItems(arrayOfString, arrayOfBoolean, new DialogInterface.OnMultiChoiceClickListener()
              {
                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int, boolean paramAnonymous2Boolean)
                {
                  arrayOfBoolean[paramAnonymous2Int] = paramAnonymous2Boolean;
                }
              }).show();
            }
            catch (WindowManager.BadTokenException localBadTokenException)
            {
              localBadTokenException.printStackTrace();
            }
          }
          break;
          label139:
          arrayOfString[j] = ((Mylist)localArrayList.get(j)).getName();
          arrayOfBoolean[j] = true;
        }
      }
    });
    findPreference("backup_import").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        if (!SettingBackupActivity.this.isLoginChecked()) {}
        for (;;)
        {
          return false;
          GetMylistBackupTask local1 = new GetMylistBackupTask()
          {
            protected void onCancelled()
            {
              if ((SettingBackupActivity.this.progressDialog != null) && (SettingBackupActivity.this.progressDialog.isShowing())) {
                SettingBackupActivity.this.progressDialog.dismiss();
              }
              super.onCancelled();
            }
            
            protected void onPostExecute(ArrayList<Playlist> paramAnonymous2ArrayList)
            {
              if ((SettingBackupActivity.this.progressDialog != null) && (SettingBackupActivity.this.progressDialog.isShowing())) {
                SettingBackupActivity.this.progressDialog.dismiss();
              }
              if ((paramAnonymous2ArrayList != null) && (paramAnonymous2ArrayList.size() > 0)) {
                SettingBackupActivity.this.importPlaylistsDialog(paramAnonymous2ArrayList);
              }
              super.onPostExecute(paramAnonymous2ArrayList);
            }
            
            protected void onPreExecute()
            {
              SettingBackupActivity.this.progressDialog = new ProgressDialog(SettingBackupActivity.this);
              SettingBackupActivity.this.progressDialog.setMessage(SettingBackupActivity.this.getString(2131296481));
              SettingBackupActivity.this.progressDialog.setIndeterminate(true);
              SettingBackupActivity.this.progressDialog.setProgressStyle(0);
              SettingBackupActivity.this.progressDialog.show();
              super.onPreExecute();
            }
          };
          String[] arrayOfString = new String[2];
          arrayOfString[0] = SettingBackupActivity.this.id.getText();
          arrayOfString[1] = SettingBackupActivity.this.passwd.getText();
          local1.execute(arrayOfString);
        }
      }
    });
    findPreference("backup_delete").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        if (!SettingBackupActivity.this.isLoginChecked()) {}
        for (;;)
        {
          return false;
          GetMylistBackupTask local1 = new GetMylistBackupTask()
          {
            protected void onCancelled()
            {
              if ((SettingBackupActivity.this.progressDialog != null) && (SettingBackupActivity.this.progressDialog.isShowing())) {
                SettingBackupActivity.this.progressDialog.dismiss();
              }
              super.onCancelled();
            }
            
            protected void onPostExecute(ArrayList<Playlist> paramAnonymous2ArrayList)
            {
              if ((SettingBackupActivity.this.progressDialog != null) && (SettingBackupActivity.this.progressDialog.isShowing())) {
                SettingBackupActivity.this.progressDialog.dismiss();
              }
              if ((paramAnonymous2ArrayList != null) && (paramAnonymous2ArrayList.size() > 0)) {
                SettingBackupActivity.this.deleteBackupDialog(paramAnonymous2ArrayList);
              }
              super.onPostExecute(paramAnonymous2ArrayList);
            }
            
            protected void onPreExecute()
            {
              SettingBackupActivity.this.progressDialog = new ProgressDialog(SettingBackupActivity.this);
              SettingBackupActivity.this.progressDialog.setMessage(SettingBackupActivity.this.getString(2131296481));
              SettingBackupActivity.this.progressDialog.setIndeterminate(true);
              SettingBackupActivity.this.progressDialog.setProgressStyle(0);
              SettingBackupActivity.this.progressDialog.show();
              super.onPreExecute();
            }
          };
          String[] arrayOfString = new String[2];
          arrayOfString[0] = SettingBackupActivity.this.id.getText();
          arrayOfString[1] = SettingBackupActivity.this.passwd.getText();
          local1.execute(arrayOfString);
        }
      }
    });
    if (getIntent().getBooleanExtra("BACKUP_ID_CREATED", false)) {
      loginTest();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.SettingBackupActivity
 * JD-Core Version:    0.7.0.1
 */