package com.amoad.amoadsdk;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AMoAdSdkPopUpActivity
  extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    Resources localResources = getResources();
    RelativeLayout localRelativeLayout = (RelativeLayout)getLayoutInflater().inflate(localResources.getIdentifier("amoadsdk_popup", "layout", getPackageName()), null);
    setContentView(localRelativeLayout, new ViewGroup.LayoutParams(Util.getDipToPix(300, this), Util.getDipToPix(200, this)));
    final String str1 = getIntent().getStringExtra("triggerkey");
    SharedPreferences localSharedPreferences = getSharedPreferences("popup_info", 0);
    String str2 = localSharedPreferences.getString(str1 + "text", "");
    final String str3 = localSharedPreferences.getString(str1 + "url", "");
    ((TextView)localRelativeLayout.getChildAt(0)).setText(str2);
    ((Button)localRelativeLayout.getChildAt(1)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        new ClickPopResultSetSendTask(str3, AMoAdSdkPopUpActivity.this, str1).execute(new String[0]);
        Intent localIntent = new Intent(AMoAdSdkPopUpActivity.this, AMoAdSdkWallActivity.class);
        AMoAdSdkPopUpActivity.this.startActivity(localIntent);
        AMoAdSdkPopUpActivity.this.finish();
      }
    });
    ((Button)localRelativeLayout.getChildAt(2)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AMoAdSdkPopUpActivity.this.finish();
      }
    });
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.AMoAdSdkPopUpActivity
 * JD-Core Version:    0.7.0.1
 */