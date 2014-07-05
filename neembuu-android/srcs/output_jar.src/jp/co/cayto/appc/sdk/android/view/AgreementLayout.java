package jp.co.cayto.appc.sdk.android.view;

import android.app.Activity;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import jp.co.cayto.appc.sdk.android.resources.Texts;
import jp.co.cayto.appc.sdk.android.resources.Texts.ITexts;

public final class AgreementLayout
{
  public static final int LOCATION_DIALOG0 = 0;
  public static final int LOCATION_DIALOG1 = 1;
  public static final int LOCATION_DIALOG2 = 2;
  public static final int LOCATION_FLOAT_CONTENT_CONFIG0 = 3;
  public static final int LOCATION_FLOAT_CONTENT_CONFIG1 = 4;
  public static final int LOCATION_FLOAT_CONTENT_CONFIG2 = 5;
  
  public static LinearLayout getAgreementLayout(Activity paramActivity, int paramInt)
  {
    LinearLayout localLinearLayout = new LinearLayout(paramActivity);
    localLinearLayout.setOrientation(1);
    localLinearLayout.setBackgroundColor(-1);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    TextView localTextView1;
    TextView localTextView2;
    TextView localTextView3;
    if ((paramInt == 3) || (paramInt == 4) || (paramInt == 5))
    {
      localLayoutParams.setMargins(10, 10, 10, 10);
      localLinearLayout.setLayoutParams(localLayoutParams);
      Texts localTexts = new Texts(paramActivity.getApplicationContext());
      localTextView1 = new TextView(paramActivity);
      localTextView1.setText(localTexts.get.利用規約_プライバシーポリシー_本文());
      localTextView1.setTextSize(12.0F);
      localTextView1.setTextColor(Color.parseColor("#333333"));
      localTextView2 = new TextView(paramActivity);
      localTextView2.setText(localTexts.get.利用規約_オプトアウト_本文());
      localTextView2.setTextSize(12.0F);
      localTextView2.setTextColor(Color.parseColor("#333333"));
      localTextView3 = new TextView(paramActivity);
      localTextView3.setText(localTexts.get.利用規約_本文());
      localTextView3.setTextSize(12.0F);
      localTextView3.setTextColor(Color.parseColor("#333333"));
      if (paramInt != 0) {
        break label228;
      }
      localLinearLayout.addView(localTextView1);
    }
    for (;;)
    {
      return localLinearLayout;
      localLayoutParams.setMargins(3, 10, 3, 10);
      break;
      label228:
      if (paramInt == 1) {
        localLinearLayout.addView(localTextView2);
      } else if (paramInt == 2) {
        localLinearLayout.addView(localTextView3);
      } else if (paramInt == 3) {
        localLinearLayout.addView(localTextView1);
      } else if (paramInt == 4) {
        localLinearLayout.addView(localTextView2);
      } else if (paramInt == 5) {
        localLinearLayout.addView(localTextView3);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.view.AgreementLayout
 * JD-Core Version:    0.7.0.1
 */