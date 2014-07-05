package jp.co.asbit.pvstar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import jp.tjkapp.adfurikunsdk.AdfurikunWallAd;
import jp.tjkapp.adfurikunsdk.OnAdfurikunWallAdFinishListener;

public class WallAd
  extends Activity
  implements OnAdfurikunWallAdFinishListener
{
  public static final String ADFURIKUN_APPID = "5315c44ebb323cc86b00000a";
  
  private void showWallAd()
  {
    AdfurikunWallAd.showWallAd(this, this);
  }
  
  public void onAdfurikunWallAdClose() {}
  
  public void onAdfurikunWallAdError(int paramInt) {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903100);
    ((LinearLayout)findViewById(2131493010)).setBackgroundColor(-7829368);
    AdfurikunWallAd.initializeWallAdSetting(this, "5315c44ebb323cc86b00000a");
  }
  
  protected void onStart()
  {
    super.onStart();
    showWallAd();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.WallAd
 * JD-Core Version:    0.7.0.1
 */