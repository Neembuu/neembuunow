package jp.co.asbit.pvstar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.co.asbit.pvstar.api.AddBackupUserTask;
import jp.co.asbit.pvstar.security.ObscuredSharedPreferences;

public class CreateBackupAccountActivity
  extends BaseActivity
{
  private Context mContext;
  private EditText mNewBackupId;
  private EditText mNewBackupPasswd;
  private EditText mNewBackupPasswd2;
  private ProgressDialog progressDialog;
  
  private boolean checkInput()
  {
    boolean bool = false;
    String str1 = getNewId();
    if ((str1.length() < 4) || (str1.length() > 8)) {
      showErrorMessage(2131296301);
    }
    for (;;)
    {
      return bool;
      Pattern localPattern = Pattern.compile("^[a-zA-Z0-9_\\-\\.]+$");
      if (!localPattern.matcher(str1).find())
      {
        showErrorMessage(2131296301);
      }
      else
      {
        String str2 = getNewPasswd();
        if ((str2.length() < 4) || (str2.length() > 8)) {
          showErrorMessage(2131296302);
        } else if (!localPattern.matcher(str2).find()) {
          showErrorMessage(2131296302);
        } else if (!getNewPasswd2().equals(str2)) {
          showErrorMessage(2131296303);
        } else {
          bool = true;
        }
      }
    }
  }
  
  private String getNewId()
  {
    return this.mNewBackupId.getText().toString().trim();
  }
  
  private String getNewPasswd()
  {
    return this.mNewBackupPasswd.getText().toString().trim();
  }
  
  private String getNewPasswd2()
  {
    return this.mNewBackupPasswd2.getText().toString().trim();
  }
  
  void finishSetting()
  {
    setResult(-1, new Intent());
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentAndTitle(2130903047, 2130903080);
    setTitle(getString(2131296297));
    this.mContext = getApplicationContext();
    this.mNewBackupId = ((EditText)findViewById(2131492882));
    this.mNewBackupPasswd = ((EditText)findViewById(2131492884));
    this.mNewBackupPasswd2 = ((EditText)findViewById(2131492886));
    ((Button)findViewById(2131492887)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (CreateBackupAccountActivity.this.checkInput())
        {
          AddBackupUserTask local1 = new AddBackupUserTask()
          {
            protected void onPostExecute(Boolean paramAnonymous2Boolean)
            {
              if (!paramAnonymous2Boolean.booleanValue()) {
                Toast.makeText(CreateBackupAccountActivity.this.mContext, this.errorMessage, 1).show();
              }
              for (;;)
              {
                if ((CreateBackupAccountActivity.this.progressDialog != null) && (CreateBackupAccountActivity.this.progressDialog.isShowing())) {
                  CreateBackupAccountActivity.this.progressDialog.dismiss();
                }
                if (paramAnonymous2Boolean.booleanValue()) {
                  CreateBackupAccountActivity.this.finishSetting();
                }
                return;
                CreateBackupAccountActivity.this.setAccount();
              }
            }
            
            protected void onPreExecute()
            {
              CreateBackupAccountActivity.this.progressDialog = new ProgressDialog(CreateBackupAccountActivity.this);
              CreateBackupAccountActivity.this.progressDialog.setMessage(CreateBackupAccountActivity.this.getString(2131296481));
              CreateBackupAccountActivity.this.progressDialog.setProgressStyle(0);
              CreateBackupAccountActivity.this.progressDialog.setCancelable(false);
              CreateBackupAccountActivity.this.progressDialog.show();
              super.onPreExecute();
            }
          };
          String[] arrayOfString = new String[3];
          arrayOfString[0] = CreateBackupAccountActivity.this.getNewId();
          arrayOfString[1] = CreateBackupAccountActivity.this.getNewPasswd();
          arrayOfString[2] = CreateBackupAccountActivity.this.getNewPasswd2();
          local1.execute(arrayOfString);
        }
      }
    });
  }
  
  void setAccount()
  {
    SharedPreferences.Editor localEditor = new ObscuredSharedPreferences(this.mContext, PreferenceManager.getDefaultSharedPreferences(this.mContext)).edit();
    localEditor.putString("backup_id", getNewId());
    localEditor.putString("backup_passwd", getNewPasswd());
    localEditor.commit();
  }
  
  void showErrorMessage(int paramInt)
  {
    Toast.makeText(this.mContext, paramInt, 1).show();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.CreateBackupAccountActivity
 * JD-Core Version:    0.7.0.1
 */