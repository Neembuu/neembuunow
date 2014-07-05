package jp.co.cayto.appc.sdk.android;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import jp.co.cayto.appc.sdk.android.common.AppController;
import jp.co.cayto.appc.sdk.android.view.AgreementDialog;

public class AppCAgreementActivity
  extends Activity
{
  private AgreementDialog mAgreementDialog;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    Bundle localBundle = getIntent().getExtras();
    final String str;
    if (localBundle != null)
    {
      str = localBundle.getString("redirect_class");
      this.mAgreementDialog = new AgreementDialog(this);
      this.mAgreementDialog.setOnClickListenerByYes(new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          if (TextUtils.isEmpty(str))
          {
            Intent localIntent1 = new Intent();
            localIntent1.putExtra("CPI_SDK_AGREEMENT_DONE", true);
            AppCAgreementActivity.this.setResult(-1, localIntent1);
            AppCAgreementActivity.this.finish();
          }
          for (;;)
          {
            return;
            Intent localIntent2 = new Intent();
            localIntent2.setClassName(AppCAgreementActivity.this, str);
            AppCAgreementActivity.this.startActivity(localIntent2);
            AppCAgreementActivity.this.finish();
          }
        }
      });
      this.mAgreementDialog.setOnClickListenerByNo(new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          if (TextUtils.isEmpty(str))
          {
            Intent localIntent1 = new Intent();
            localIntent1.putExtra("CPI_SDK_AGREEMENT_DONE", true);
            AppCAgreementActivity.this.setResult(-1, localIntent1);
            AppCAgreementActivity.this.finish();
          }
          for (;;)
          {
            return;
            Intent localIntent2 = new Intent();
            localIntent2.setClassName(AppCAgreementActivity.this, str);
            AppCAgreementActivity.this.startActivity(localIntent2);
            AppCAgreementActivity.this.finish();
          }
        }
      });
      if (!AppController.createIncetance(getApplicationContext()).isInitialized()) {
        break label136;
      }
      LinearLayout localLinearLayout1 = new LinearLayout(this);
      localLinearLayout1.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
      setContentView(localLinearLayout1);
      this.mAgreementDialog.showDialog();
    }
    for (;;)
    {
      return;
      str = null;
      break;
      label136:
      LinearLayout localLinearLayout2 = new LinearLayout(this);
      localLinearLayout2.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
      setContentView(localLinearLayout2);
      this.mAgreementDialog.showDialog();
    }
  }
  
  protected Dialog onCreateDialog(int paramInt)
  {
    return this.mAgreementDialog.alterOnCreateDialog(paramInt);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.AppCAgreementActivity
 * JD-Core Version:    0.7.0.1
 */