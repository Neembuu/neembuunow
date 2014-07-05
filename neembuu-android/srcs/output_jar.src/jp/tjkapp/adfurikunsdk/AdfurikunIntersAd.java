package jp.tjkapp.adfurikunsdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.ViewGroup;

public class AdfurikunIntersAd
  extends Activity
{
  public static final int ERROR_ALREADY_DISPLAYED = 1001;
  public static final int ERROR_NOT_NETWORK_CONNECTED = 1002;
  private static AdfurikunIntersAdUtil mAdfurikunIntersAdUtil = null;
  private static boolean mIsShowIntersAd = false;
  private static OnAdfurikunIntersAdFinishListener mOnAdfurikunIntersAdFinishListener = null;
  private AdfurikunIntersAdUtil.AdfurikunIntersAdInfo mAdfurikunIntersAdInfo = null;
  private int mOrientation;
  
  public static void addIntersAdSetting(Activity paramActivity, String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4)
  {
    if (mAdfurikunIntersAdUtil == null) {
      mAdfurikunIntersAdUtil = new AdfurikunIntersAdUtil();
    }
    mAdfurikunIntersAdUtil.addIntersAdSetting(paramActivity.getApplicationContext(), paramString1, paramInt1, paramInt2, paramString2, paramString3, paramString4);
  }
  
  public static void adfurikunIntersAdFinalizeAll()
  {
    if ((!mIsShowIntersAd) && (mAdfurikunIntersAdUtil != null)) {
      mAdfurikunIntersAdUtil.removeIntersAdAll();
    }
  }
  
  private void showIntersAd()
  {
    if (this.mAdfurikunIntersAdInfo == null)
    {
      if (mOnAdfurikunIntersAdFinishListener != null) {
        mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdClose(-1);
      }
      mIsShowIntersAd = false;
      finish();
    }
    AdfurikunIntersAdUtil.AdfurikunIntersAdLayoutInfo localAdfurikunIntersAdLayoutInfo;
    for (;;)
    {
      return;
      localAdfurikunIntersAdLayoutInfo = mAdfurikunIntersAdUtil.getIntersAdLayoutInfo(this.mAdfurikunIntersAdInfo.app_id);
      if (localAdfurikunIntersAdLayoutInfo != null) {
        break;
      }
      cancelIntersAd();
    }
    int i;
    if (localAdfurikunIntersAdLayoutInfo.start_time)
    {
      localAdfurikunIntersAdLayoutInfo.start_time = false;
      i = 0;
      label71:
      if (localAdfurikunIntersAdLayoutInfo.ad_layout != null) {
        break label234;
      }
      localAdfurikunIntersAdLayoutInfo.ad_layout = new AdfurikunIntersAdLayout(getApplicationContext());
      if (this.mAdfurikunIntersAdInfo.app_id.length() > 0) {
        localAdfurikunIntersAdLayoutInfo.ad_layout.setAdfurikunAppKey(this.mAdfurikunIntersAdInfo.app_id);
      }
    }
    for (;;)
    {
      if (i != 0) {
        localAdfurikunIntersAdLayoutInfo.ad_layout.nextAd();
      }
      String str = localAdfurikunIntersAdLayoutInfo.ad_layout.getIsText();
      if ((str.length() <= 0) && (this.mAdfurikunIntersAdInfo.intersad_button_name.length() > 0)) {
        str = this.mAdfurikunIntersAdInfo.intersad_button_name;
      }
      AdfurikunIntersView localAdfurikunIntersView = new AdfurikunIntersView(this, localAdfurikunIntersAdLayoutInfo.ad_layout, str, this.mAdfurikunIntersAdInfo.cancel_button_name, this.mAdfurikunIntersAdInfo.custom_button_name);
      localAdfurikunIntersView.setOnAdfurikunIntersClickListener(new AdfurikunIntersView.OnAdfurikunIntersClickListener()
      {
        public void onClickCancel()
        {
          AdfurikunIntersAd.this.cancelIntersAd();
        }
        
        public void onClickCustom()
        {
          if (AdfurikunIntersAd.mOnAdfurikunIntersAdFinishListener != null)
          {
            if (AdfurikunIntersAd.this.mAdfurikunIntersAdInfo == null) {
              break label46;
            }
            AdfurikunIntersAd.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdCustomClose(AdfurikunIntersAd.this.mAdfurikunIntersAdInfo.index);
          }
          for (;;)
          {
            AdfurikunIntersAd.mIsShowIntersAd = false;
            AdfurikunIntersAd.this.finish();
            return;
            label46:
            AdfurikunIntersAd.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdCustomClose(-1);
          }
        }
      });
      localAdfurikunIntersAdLayoutInfo.ad_layout.recImpression();
      setContentView(localAdfurikunIntersView);
      break;
      i = 1;
      break label71;
      label234:
      ViewGroup localViewGroup = (ViewGroup)localAdfurikunIntersAdLayoutInfo.ad_layout.getParent();
      if (localViewGroup != null) {
        localViewGroup.removeView(localAdfurikunIntersAdLayoutInfo.ad_layout);
      }
    }
  }
  
  public static boolean showIntersAd(Activity paramActivity, int paramInt, OnAdfurikunIntersAdFinishListener paramOnAdfurikunIntersAdFinishListener)
  {
    boolean bool = false;
    AdfurikunIntersAdUtil.AdfurikunIntersAdInfo localAdfurikunIntersAdInfo = mAdfurikunIntersAdUtil.getIntersAdInfo(paramInt);
    if ((!mIsShowIntersAd) && (localAdfurikunIntersAdInfo != null)) {
      if (mAdfurikunIntersAdUtil.isLoadFinished(localAdfurikunIntersAdInfo.app_id))
      {
        String str = localAdfurikunIntersAdInfo.index + "_" + localAdfurikunIntersAdInfo.app_id;
        SharedPreferences localSharedPreferences = paramActivity.getApplicationContext().getSharedPreferences(AdfurikunConstants.PREF_FILE, 3);
        int i = localSharedPreferences.getInt(str + AdfurikunConstants.PREFKEY_INTERS_AD_FREQUENCY_CT, 0);
        int j = localSharedPreferences.getInt(str + AdfurikunConstants.PREFKEY_INTERS_AD_MAX_CT, 0);
        if ((localAdfurikunIntersAdInfo.max <= 0) || ((localAdfurikunIntersAdInfo.max > 0) && (j < localAdfurikunIntersAdInfo.max)))
        {
          NetworkInfo localNetworkInfo = ((ConnectivityManager)paramActivity.getSystemService("connectivity")).getActiveNetworkInfo();
          if ((localNetworkInfo != null) && (localNetworkInfo.isConnected()))
          {
            SharedPreferences.Editor localEditor = localSharedPreferences.edit();
            if (i == 0)
            {
              mOnAdfurikunIntersAdFinishListener = paramOnAdfurikunIntersAdFinishListener;
              Intent localIntent = new Intent(paramActivity, AdfurikunIntersAd.class);
              localIntent.putExtra(AdfurikunConstants.EXTRA_INTERS_AD_INDEX, paramInt);
              paramActivity.startActivity(localIntent);
              localEditor.putInt(str + AdfurikunConstants.PREFKEY_INTERS_AD_MAX_CT, j + 1);
              bool = true;
              int k = i + 1;
              if (k >= localAdfurikunIntersAdInfo.frequency) {
                k = 0;
              }
              localEditor.putInt(str + AdfurikunConstants.PREFKEY_INTERS_AD_FREQUENCY_CT, k);
              localEditor.commit();
            }
          }
        }
      }
    }
    for (;;)
    {
      return bool;
      if (paramOnAdfurikunIntersAdFinishListener == null) {
        break;
      }
      paramOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdSkip(paramInt);
      break;
      if (paramOnAdfurikunIntersAdFinishListener != null)
      {
        paramOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdError(paramInt, 1002);
        continue;
        if (paramOnAdfurikunIntersAdFinishListener != null)
        {
          paramOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdMaxEnd(paramInt);
          continue;
          if (paramOnAdfurikunIntersAdFinishListener != null)
          {
            paramOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdError(paramInt, 1002);
            continue;
            if (paramOnAdfurikunIntersAdFinishListener != null) {
              paramOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdError(paramInt, 1001);
            }
          }
        }
      }
    }
  }
  
  public void cancelIntersAd()
  {
    if (mOnAdfurikunIntersAdFinishListener != null)
    {
      if (this.mAdfurikunIntersAdInfo == null) {
        break label37;
      }
      mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdClose(this.mAdfurikunIntersAdInfo.index);
    }
    for (;;)
    {
      mIsShowIntersAd = false;
      finish();
      return;
      label37:
      mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdClose(-1);
    }
  }
  
  public void onBackPressed()
  {
    cancelIntersAd();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if (this.mOrientation != paramConfiguration.orientation)
    {
      this.mOrientation = paramConfiguration.orientation;
      showIntersAd();
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    requestWindowFeature(1);
    super.onCreate(paramBundle);
    mIsShowIntersAd = true;
    this.mOrientation = getResources().getConfiguration().orientation;
    Intent localIntent = getIntent();
    int i = -1;
    if (localIntent != null) {
      i = localIntent.getIntExtra(AdfurikunConstants.EXTRA_INTERS_AD_INDEX, -1);
    }
    this.mAdfurikunIntersAdInfo = mAdfurikunIntersAdUtil.getIntersAdInfo(i);
    showIntersAd();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    mIsShowIntersAd = false;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunIntersAd
 * JD-Core Version:    0.7.0.1
 */