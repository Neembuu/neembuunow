package jp.co.cayto.appc.sdk.android.view;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import jp.co.cayto.appc.sdk.android.common.AppController;
import jp.co.cayto.appc.sdk.android.common.AppPreference;
import jp.co.cayto.appc.sdk.android.resources.Texts;
import jp.co.cayto.appc.sdk.android.resources.Texts.ITexts;

public final class AgreementDialog
{
  public static final int DIALOG_AGREEMENT_NO = 800004;
  public static final int DIALOG_AGREEMENT_OPTOUT = 800002;
  public static final int DIALOG_AGREEMENT_PRIVACY = 800001;
  public static final int DIALOG_AGREEMENT_TOP = 800000;
  public static final int DIALOG_AGREEMENT_YES = 800003;
  public static final int DIALOG_OPTIONAL_AGREEMENT = 800005;
  private static final int FP = -1;
  private static final int WC = -2;
  private Activity mActivity = null;
  private RadioGroup mAgreeGroup;
  private RadioButton mAgreeboxNo;
  private RadioButton mAgreeboxYes;
  private AppController mAppController = null;
  private DialogInterface.OnClickListener mClickNo = null;
  private DialogInterface.OnClickListener mClickYes = null;
  
  public AgreementDialog(Activity paramActivity)
  {
    this.mActivity = paramActivity;
    init();
  }
  
  private void crearDialog()
  {
    this.mActivity.removeDialog(800000);
    this.mActivity.removeDialog(800001);
    this.mActivity.removeDialog(800003);
    this.mActivity.removeDialog(800004);
    this.mActivity.removeDialog(800002);
  }
  
  private View createAgreeMent0()
  {
    LinearLayout localLinearLayout1 = new LinearLayout(this.mActivity);
    localLinearLayout1.setBackgroundColor(Color.argb(255, 255, 255, 255));
    localLinearLayout1.setOrientation(1);
    ScrollView localScrollView = new ScrollView(this.mActivity);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    LinearLayout localLinearLayout2 = new LinearLayout(this.mActivity);
    localLinearLayout2.setBackgroundColor(Color.argb(255, 255, 255, 255));
    LinearLayout localLinearLayout3 = new LinearLayout(this.mActivity);
    localLinearLayout3.setOrientation(1);
    localLinearLayout3.setBackgroundColor(Color.argb(255, 196, 196, 196));
    new LinearLayout.LayoutParams(-1, 4).setMargins(0, 10, 0, 10);
    localLinearLayout2.setOrientation(1);
    localScrollView.addView(localLinearLayout2);
    localLinearLayout1.addView(localScrollView, localLayoutParams);
    LinearLayout localLinearLayout4 = AgreementLayout.getAgreementLayout(this.mActivity, 0);
    TextView localTextView = new TextView(this.mActivity);
    localTextView.setTextSize(12.0F);
    localTextView.setTextColor(Color.parseColor("#333333"));
    localLinearLayout4.addView(localTextView);
    localLinearLayout2.addView(localLinearLayout4);
    return localLinearLayout1;
  }
  
  private View createAgreeMent1()
  {
    LinearLayout localLinearLayout1 = new LinearLayout(this.mActivity);
    localLinearLayout1.setBackgroundColor(Color.argb(255, 255, 255, 255));
    localLinearLayout1.setOrientation(1);
    ScrollView localScrollView = new ScrollView(this.mActivity);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    LinearLayout localLinearLayout2 = new LinearLayout(this.mActivity);
    localLinearLayout2.setBackgroundColor(Color.argb(255, 255, 255, 255));
    LinearLayout localLinearLayout3 = new LinearLayout(this.mActivity);
    localLinearLayout3.setOrientation(1);
    localLinearLayout3.setBackgroundColor(Color.argb(255, 196, 196, 196));
    new LinearLayout.LayoutParams(-1, 4).setMargins(0, 10, 0, 10);
    localLinearLayout2.setOrientation(1);
    localScrollView.addView(localLinearLayout2);
    localLinearLayout1.addView(localScrollView, localLayoutParams);
    LinearLayout localLinearLayout4 = AgreementLayout.getAgreementLayout(this.mActivity, 1);
    this.mAgreeGroup = new RadioGroup(this.mActivity);
    this.mAgreeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
    {
      public void onCheckedChanged(RadioGroup paramAnonymousRadioGroup, int paramAnonymousInt) {}
    });
    this.mAgreeboxYes = new RadioButton(this.mActivity);
    this.mAgreeboxYes.setId(90001);
    this.mAgreeboxNo = new RadioButton(this.mActivity);
    this.mAgreeboxNo.setId(90002);
    this.mAgreeGroup.addView(this.mAgreeboxYes);
    this.mAgreeGroup.addView(this.mAgreeboxNo);
    Texts localTexts = new Texts(this.mActivity.getApplicationContext());
    this.mAgreeboxYes.setText(localTexts.get.利用規約_ラベル_同意する());
    this.mAgreeboxYes.setTextColor(Color.parseColor("#333333"));
    this.mAgreeboxYes.setTextSize(14.0F);
    this.mAgreeboxNo.setText(localTexts.get.利用規約_ラベル_同意しない());
    this.mAgreeboxNo.setTextColor(Color.parseColor("#333333"));
    this.mAgreeboxNo.setTextSize(14.0F);
    if (this.mAppController.isInitialized())
    {
      if (!AppPreference.isPermission(this.mActivity.getApplicationContext())) {
        break label421;
      }
      this.mAgreeGroup.check(90001);
    }
    for (;;)
    {
      localLinearLayout4.addView(this.mAgreeGroup);
      localLinearLayout2.addView(localLinearLayout4);
      return localLinearLayout1;
      label421:
      this.mAgreeGroup.check(90002);
    }
  }
  
  private View createAgreeMent2()
  {
    LinearLayout localLinearLayout1 = new LinearLayout(this.mActivity);
    localLinearLayout1.setBackgroundColor(Color.argb(255, 255, 255, 255));
    localLinearLayout1.setOrientation(1);
    ScrollView localScrollView = new ScrollView(this.mActivity);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    LinearLayout localLinearLayout2 = new LinearLayout(this.mActivity);
    localLinearLayout2.setBackgroundColor(Color.argb(255, 255, 255, 255));
    LinearLayout localLinearLayout3 = new LinearLayout(this.mActivity);
    localLinearLayout3.setOrientation(1);
    localLinearLayout3.setBackgroundColor(Color.argb(255, 196, 196, 196));
    new LinearLayout.LayoutParams(-1, 4).setMargins(0, 10, 0, 10);
    localLinearLayout2.setOrientation(1);
    localScrollView.addView(localLinearLayout2);
    localLinearLayout1.addView(localScrollView, localLayoutParams);
    localLinearLayout2.addView(AgreementLayout.getAgreementLayout(this.mActivity, 2));
    return localLinearLayout1;
  }
  
  private void init()
  {
    this.mAppController = AppController.createIncetance(this.mActivity);
    this.mClickYes = new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
        AgreementDialog.this.crearDialog();
      }
    };
    this.mClickNo = new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
        AgreementDialog.this.crearDialog();
      }
    };
  }
  
  public Dialog alterOnCreateDialog(int paramInt)
  {
    final Activity localActivity = this.mActivity;
    Texts localTexts = new Texts(localActivity);
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.mActivity);
    Object localObject;
    switch (paramInt)
    {
    default: 
      localObject = null;
    }
    for (;;)
    {
      if (localObject != null)
      {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        this.mActivity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.copyFrom(((Dialog)localObject).getWindow().getAttributes());
        localLayoutParams.width = -1;
        localLayoutParams.height = (8 * (localDisplayMetrics.heightPixels / 10));
        localLayoutParams.alpha = 0.8F;
        ((Dialog)localObject).getWindow().setAttributes(localLayoutParams);
        ((Dialog)localObject).getWindow().setBackgroundDrawable(new ColorDrawable(0));
      }
      return localObject;
      localBuilder.setTitle(localTexts.get.利用規約_タイトル()).setCancelable(false).setView(createAgreeMent2()).setPositiveButton(localTexts.get.利用規約_ラベル_プライバシーポリシー(), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          AgreementDialog.this.crearDialog();
          AgreementDialog.this.mActivity.showDialog(800001);
          paramAnonymousDialogInterface.dismiss();
        }
      }).setNegativeButton(localTexts.get.利用規約_ラベル_閉じる(), this.mClickNo);
      localObject = localBuilder.create();
      continue;
      localBuilder.setTitle(localTexts.get.利用規約_タイトル()).setCancelable(false).setView(createAgreeMent0()).setPositiveButton(localTexts.get.利用規約_ラベル_オプトアウト(), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          AgreementDialog.this.crearDialog();
          AgreementDialog.this.mActivity.showDialog(800002);
          paramAnonymousDialogInterface.dismiss();
        }
      }).setNegativeButton(localTexts.get.利用規約_ラベル_戻る(), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          AgreementDialog.this.mActivity.showDialog(800000);
          paramAnonymousDialogInterface.dismiss();
        }
      });
      localObject = localBuilder.create();
      continue;
      localBuilder.setTitle(localTexts.get.利用規約_タイトル()).setCancelable(false).setView(createAgreeMent1()).setPositiveButton(localTexts.get.利用規約_ラベル_設定(), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          int i = AgreementDialog.this.mAgreeGroup.getCheckedRadioButtonId();
          AgreementDialog.this.mActivity.findViewById(i);
          int j = AgreementDialog.this.mAgreeboxYes.getId();
          int k = AgreementDialog.this.mAgreeboxNo.getId();
          if (j == i)
          {
            AgreementDialog.this.mAppController.sendLogOff(localActivity);
            AppPreference.setPermissionOff(localActivity);
            Intent localIntent2 = new Intent("jp.co.cayto.appc.sdk.android.remote.command.gid.remove");
            AgreementDialog.this.mActivity.sendOrderedBroadcast(localIntent2, null, new BroadcastReceiver()
            {
              public void onReceive(Context paramAnonymous2Context, Intent paramAnonymous2Intent)
              {
                AppPreference.setPermissionOn(paramAnonymous2Context);
                AgreementDialog.this.mAppController.configure(paramAnonymous2Context, paramAnonymous2Intent);
                try
                {
                  AgreementDialog.this.mActivity.showDialog(800003);
                  label34:
                  return;
                }
                catch (Exception localException)
                {
                  break label34;
                }
              }
            }, null, -1, "r1", new Bundle());
          }
          for (;;)
          {
            paramAnonymousDialogInterface.dismiss();
            return;
            if (k == i)
            {
              AgreementDialog.this.mAppController.sendLogOff(localActivity);
              AppPreference.setPermissionOff(localActivity);
              AgreementDialog.this.mAppController.configure(localActivity);
              Intent localIntent1 = new Intent("jp.co.cayto.appc.sdk.android.remote.command.gid.remove");
              AgreementDialog.this.mActivity.sendOrderedBroadcast(localIntent1, null, new BroadcastReceiver()
              {
                public void onReceive(Context paramAnonymous2Context, Intent paramAnonymous2Intent) {}
              }, null, -1, "r1", new Bundle());
              try
              {
                AgreementDialog.this.mActivity.showDialog(800004);
              }
              catch (Exception localException) {}
            }
          }
        }
      }).setNegativeButton(localTexts.get.利用規約_ラベル_戻る(), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          AgreementDialog.this.mActivity.showDialog(800001);
          paramAnonymousDialogInterface.dismiss();
        }
      });
      localObject = localBuilder.create();
      continue;
      localBuilder.setCancelable(false);
      localBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
        }
      });
      localObject = localBuilder.create();
      continue;
      AppPreference.setPermissionOn(localActivity);
      this.mAppController.configure(localActivity);
      localBuilder.setTitle(localTexts.get.利用規約_ラベル_設定完了());
      localBuilder.setMessage(localTexts.get.利用規約_ラベル_同意するを選択());
      localBuilder.setCancelable(false);
      localBuilder.setPositiveButton("OK", this.mClickYes);
      localObject = localBuilder.create();
      continue;
      this.mAppController.sendLogOff(localActivity);
      AppPreference.setPermissionOff(localActivity);
      this.mAppController.configure(localActivity);
      Intent localIntent = new Intent("jp.co.cayto.appc.sdk.android.remote.command.gid.remove");
      this.mActivity.sendOrderedBroadcast(localIntent, null, new BroadcastReceiver()
      {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent) {}
      }, null, -1, "r1", new Bundle());
      localBuilder.setTitle(localTexts.get.利用規約_ラベル_設定完了());
      localBuilder.setMessage(localTexts.get.利用規約_ラベル_同意しないを選択());
      localBuilder.setCancelable(false);
      localBuilder.setPositiveButton("OK", this.mClickNo);
      localObject = localBuilder.create();
    }
  }
  
  public boolean checkPermission()
  {
    if ((this.mAppController.isInitialized()) && (AppPreference.isPermission(this.mActivity.getApplicationContext()))) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public boolean isAgreementDialogID(int paramInt)
  {
    if ((paramInt == 800000) || (paramInt == 800001) || (paramInt == 800002) || (paramInt == 800003) || (paramInt == 800004)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public void setOnClickListenerByNo(DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mClickNo = paramOnClickListener;
  }
  
  public void setOnClickListenerByYes(DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mClickYes = paramOnClickListener;
  }
  
  public void showDialog()
  {
    if (this.mAppController.isInitialized()) {
      this.mActivity.showDialog(800000);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.view.AgreementDialog
 * JD-Core Version:    0.7.0.1
 */