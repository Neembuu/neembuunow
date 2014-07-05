package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import java.util.ArrayList;
import java.util.Iterator;

public class AdfurikunUnityLayout
  extends RelativeLayout
{
  private static final int AD_HEIGHT = 50;
  public static final int ERROR_ALREADY_DISPLAYED = 2001;
  public static final int ERROR_NOT_NETWORK_CONNECTED = 2002;
  public static final int TRANSITION_TYPE_FADEIN_FADEOUT = 6;
  public static final int TRANSITION_TYPE_NOTHING = 0;
  public static final int TRANSITION_TYPE_RANDOM = 1;
  public static final int TRANSITION_TYPE_SLIDE_FROM_BOTTOM = 5;
  public static final int TRANSITION_TYPE_SLIDE_FROM_LEFT = 3;
  public static final int TRANSITION_TYPE_SLIDE_FROM_RIGHT = 2;
  public static final int TRANSITION_TYPE_SLIDE_FROM_TOP = 4;
  private int mAdHeight = 50;
  private AdfurikunIntersAdUtil.AdfurikunIntersAdInfo mAdfurikunIntersAdInfo = null;
  private AdfurikunIntersAdUtil mAdfurikunIntersAdUtil = null;
  private RelativeLayout mBannerAdRoot;
  private ArrayList<AdfurikunLayout> mBannerLayoutList = null;
  private RelativeLayout mCustomSizeAdRoot;
  private ArrayList<AdfurikunLayout> mCustomSizeLayoutList = null;
  private RelativeLayout mIntersAdRoot;
  private boolean mIsShowIntersAd = false;
  private OnAdfurikunIntersAdFinishListener mOnAdfurikunIntersAdFinishListener = null;
  private int mOrientation;
  private float mScale = 1.0F;
  
  public AdfurikunUnityLayout(Context paramContext)
  {
    super(paramContext);
    initialize(paramContext);
  }
  
  private AdfurikunLayout createAdfurikunLayout(String paramString, int paramInt, boolean paramBoolean)
  {
    AdfurikunLayout localAdfurikunLayout = new AdfurikunLayout(getContext());
    localAdfurikunLayout.setAdfurikunAppKey(paramString);
    switch (paramInt)
    {
    default: 
      localAdfurikunLayout.setTransitionType(-1);
    }
    for (;;)
    {
      localAdfurikunLayout.onResume();
      localAdfurikunLayout.startRotateAd();
      if (!paramBoolean)
      {
        localAdfurikunLayout.stopRotateAd();
        localAdfurikunLayout.setVisibility(4);
      }
      return localAdfurikunLayout;
      localAdfurikunLayout.setTransitionType(-2);
      continue;
      localAdfurikunLayout.setTransitionType(0);
      continue;
      localAdfurikunLayout.setTransitionType(1);
      continue;
      localAdfurikunLayout.setTransitionType(2);
      continue;
      localAdfurikunLayout.setTransitionType(3);
      continue;
      localAdfurikunLayout.setTransitionType(4);
    }
  }
  
  private boolean hideIntersAd()
  {
    boolean bool = false;
    if (this.mIsShowIntersAd)
    {
      this.mIsShowIntersAd = false;
      this.mIntersAdRoot.setVisibility(4);
      bool = true;
    }
    return bool;
  }
  
  private void initialize(Context paramContext)
  {
    this.mBannerLayoutList = new ArrayList();
    this.mCustomSizeLayoutList = new ArrayList();
    this.mScale = getResources().getDisplayMetrics().density;
    this.mAdHeight = ((int)(0.5F + 50.0F * this.mScale));
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -1);
    this.mBannerAdRoot = new RelativeLayout(paramContext);
    this.mCustomSizeAdRoot = new RelativeLayout(paramContext);
    this.mIntersAdRoot = new RelativeLayout(paramContext);
    addView(this.mBannerAdRoot, localLayoutParams);
    addView(this.mCustomSizeAdRoot, localLayoutParams);
    addView(this.mIntersAdRoot, localLayoutParams);
    this.mIntersAdRoot.setVisibility(4);
    this.mOrientation = getResources().getConfiguration().orientation;
    this.mAdfurikunIntersAdUtil = new AdfurikunIntersAdUtil();
  }
  
  private void showIntersAd(AdfurikunIntersAdUtil.AdfurikunIntersAdInfo paramAdfurikunIntersAdInfo)
  {
    this.mIntersAdRoot.removeAllViews();
    if (paramAdfurikunIntersAdInfo.app_id.length() <= 0)
    {
      if (this.mOnAdfurikunIntersAdFinishListener != null) {
        this.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdClose(-1);
      }
      hideIntersAd();
      return;
    }
    this.mAdfurikunIntersAdInfo = paramAdfurikunIntersAdInfo;
    int i = 0;
    AdfurikunIntersAdUtil.AdfurikunIntersAdLayoutInfo localAdfurikunIntersAdLayoutInfo = this.mAdfurikunIntersAdUtil.getIntersAdLayoutInfo(this.mAdfurikunIntersAdInfo.app_id);
    label81:
    Context localContext;
    if (localAdfurikunIntersAdLayoutInfo != null)
    {
      if (localAdfurikunIntersAdLayoutInfo.start_time)
      {
        localAdfurikunIntersAdLayoutInfo.start_time = false;
        i = 0;
      }
    }
    else
    {
      localContext = getContext();
      if (localAdfurikunIntersAdLayoutInfo.ad_layout != null) {
        break label265;
      }
      localAdfurikunIntersAdLayoutInfo.ad_layout = new AdfurikunIntersAdLayout(localContext);
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
      AdfurikunIntersView localAdfurikunIntersView = new AdfurikunIntersView(localContext, localAdfurikunIntersAdLayoutInfo.ad_layout, str, this.mAdfurikunIntersAdInfo.cancel_button_name, this.mAdfurikunIntersAdInfo.custom_button_name);
      localAdfurikunIntersView.setOnAdfurikunIntersClickListener(new AdfurikunIntersView.OnAdfurikunIntersClickListener()
      {
        public void onClickCancel()
        {
          AdfurikunUnityLayout.this.cancelIntersAd();
        }
        
        public void onClickCustom()
        {
          if (AdfurikunUnityLayout.this.mOnAdfurikunIntersAdFinishListener != null)
          {
            if (AdfurikunUnityLayout.this.mAdfurikunIntersAdInfo == null) {
              break label51;
            }
            AdfurikunUnityLayout.this.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdCustomClose(AdfurikunUnityLayout.this.mAdfurikunIntersAdInfo.index);
          }
          for (;;)
          {
            AdfurikunUnityLayout.this.hideIntersAd();
            return;
            label51:
            AdfurikunUnityLayout.this.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdCustomClose(-1);
          }
        }
      });
      this.mIntersAdRoot.addView(localAdfurikunIntersView);
      this.mIsShowIntersAd = true;
      localAdfurikunIntersAdLayoutInfo.ad_layout.recImpression();
      this.mIntersAdRoot.setVisibility(0);
      break;
      i = 1;
      break label81;
      label265:
      ViewGroup localViewGroup = (ViewGroup)localAdfurikunIntersAdLayoutInfo.ad_layout.getParent();
      if (localViewGroup != null) {
        localViewGroup.removeView(localAdfurikunIntersAdLayoutInfo.ad_layout);
      }
    }
  }
  
  public void addBannerAd(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    AdfurikunLayout localAdfurikunLayout = createAdfurikunLayout(paramString, paramInt2, paramBoolean);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, this.mAdHeight);
    if (paramInt1 == 0) {
      localLayoutParams.addRule(10, -1);
    }
    for (;;)
    {
      this.mBannerAdRoot.addView(localAdfurikunLayout, localLayoutParams);
      this.mBannerLayoutList.add(localAdfurikunLayout);
      return;
      localLayoutParams.addRule(12, -1);
    }
  }
  
  public void addCustomSizeAd(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt, boolean paramBoolean)
  {
    AdfurikunLayout localAdfurikunLayout = createAdfurikunLayout(paramString, paramInt, paramBoolean);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams.leftMargin = ((int)paramFloat1);
    localLayoutParams.topMargin = ((int)paramFloat2);
    localLayoutParams.width = ((int)(paramFloat3 + 0.5F));
    localLayoutParams.height = ((int)(paramFloat4 + 0.5F));
    this.mCustomSizeAdRoot.addView(localAdfurikunLayout, localLayoutParams);
    this.mCustomSizeLayoutList.add(localAdfurikunLayout);
  }
  
  public void addIntersAdSetting(String paramString1, int paramInt1, int paramInt2, String paramString2, String paramString3, String paramString4)
  {
    this.mAdfurikunIntersAdUtil.addIntersAdSetting(getContext(), paramString1, paramInt1, paramInt2, paramString2, paramString3, paramString4);
  }
  
  public void cancelIntersAd()
  {
    if ((hideIntersAd()) && (this.mOnAdfurikunIntersAdFinishListener != null))
    {
      if (this.mAdfurikunIntersAdInfo == null) {
        break label38;
      }
      this.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdClose(this.mAdfurikunIntersAdInfo.index);
    }
    for (;;)
    {
      return;
      label38:
      this.mOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdClose(-1);
    }
  }
  
  public void hideBannerAd(int paramInt)
  {
    if (paramInt < this.mBannerLayoutList.size())
    {
      AdfurikunLayout localAdfurikunLayout = (AdfurikunLayout)this.mBannerLayoutList.get(paramInt);
      localAdfurikunLayout.stopRotateAd();
      localAdfurikunLayout.setVisibility(4);
    }
  }
  
  public void hideCustomSizeAd(int paramInt)
  {
    if (paramInt < this.mCustomSizeLayoutList.size())
    {
      AdfurikunLayout localAdfurikunLayout = (AdfurikunLayout)this.mCustomSizeLayoutList.get(paramInt);
      localAdfurikunLayout.stopRotateAd();
      localAdfurikunLayout.setVisibility(4);
    }
  }
  
  public void nextBannerAd(int paramInt)
  {
    if (paramInt < this.mBannerLayoutList.size()) {
      ((AdfurikunLayout)this.mBannerLayoutList.get(paramInt)).nextAd();
    }
  }
  
  public void nextCustomSizeAd(int paramInt)
  {
    if (paramInt < this.mCustomSizeLayoutList.size()) {
      ((AdfurikunLayout)this.mCustomSizeLayoutList.get(paramInt)).nextAd();
    }
  }
  
  public void onDestroy()
  {
    Iterator localIterator1 = this.mBannerLayoutList.iterator();
    Iterator localIterator2;
    if (!localIterator1.hasNext()) {
      localIterator2 = this.mCustomSizeLayoutList.iterator();
    }
    for (;;)
    {
      if (!localIterator2.hasNext())
      {
        this.mAdfurikunIntersAdUtil.removeIntersAdAll();
        return;
        ((AdfurikunLayout)localIterator1.next()).destroy();
        break;
      }
      ((AdfurikunLayout)localIterator2.next()).destroy();
    }
  }
  
  public void onPause()
  {
    Iterator localIterator1 = this.mBannerLayoutList.iterator();
    Iterator localIterator2;
    if (!localIterator1.hasNext()) {
      localIterator2 = this.mCustomSizeLayoutList.iterator();
    }
    for (;;)
    {
      if (!localIterator2.hasNext())
      {
        return;
        ((AdfurikunLayout)localIterator1.next()).onPause();
        break;
      }
      ((AdfurikunLayout)localIterator2.next()).onPause();
    }
  }
  
  public void onResume()
  {
    Iterator localIterator1 = this.mBannerLayoutList.iterator();
    Iterator localIterator2;
    if (!localIterator1.hasNext()) {
      localIterator2 = this.mCustomSizeLayoutList.iterator();
    }
    for (;;)
    {
      if (!localIterator2.hasNext())
      {
        return;
        ((AdfurikunLayout)localIterator1.next()).onResume();
        break;
      }
      ((AdfurikunLayout)localIterator2.next()).onResume();
    }
  }
  
  public void restartBannerAd(int paramInt)
  {
    if (paramInt < this.mBannerLayoutList.size()) {
      ((AdfurikunLayout)this.mBannerLayoutList.get(paramInt)).restartRotateAd();
    }
  }
  
  public void restartCustomSizeAd(int paramInt)
  {
    if (paramInt < this.mCustomSizeLayoutList.size()) {
      ((AdfurikunLayout)this.mCustomSizeLayoutList.get(paramInt)).restartRotateAd();
    }
  }
  
  public void setAdfurikunTestMode(int paramInt)
  {
    SharedPreferences.Editor localEditor = getContext().getSharedPreferences(AdfurikunConstants.PREF_FILE, 3).edit();
    localEditor.putInt(AdfurikunConstants.PREFKEY_TESTMODE, paramInt);
    localEditor.commit();
  }
  
  public void showBannerAd(int paramInt)
  {
    if (paramInt < this.mBannerLayoutList.size())
    {
      AdfurikunLayout localAdfurikunLayout = (AdfurikunLayout)this.mBannerLayoutList.get(paramInt);
      localAdfurikunLayout.setVisibility(0);
      localAdfurikunLayout.restartRotateAd();
    }
  }
  
  public void showCustomSizeAd(int paramInt)
  {
    if (paramInt < this.mCustomSizeLayoutList.size())
    {
      AdfurikunLayout localAdfurikunLayout = (AdfurikunLayout)this.mCustomSizeLayoutList.get(paramInt);
      localAdfurikunLayout.setVisibility(0);
      localAdfurikunLayout.restartRotateAd();
    }
  }
  
  public boolean showIntersAd(int paramInt, OnAdfurikunIntersAdFinishListener paramOnAdfurikunIntersAdFinishListener)
  {
    boolean bool = false;
    Configuration localConfiguration = getResources().getConfiguration();
    AdfurikunIntersAdUtil.AdfurikunIntersAdInfo localAdfurikunIntersAdInfo = this.mAdfurikunIntersAdUtil.getIntersAdInfo(paramInt);
    if (((!this.mIsShowIntersAd) || (this.mOrientation != localConfiguration.orientation)) && (localAdfurikunIntersAdInfo != null)) {
      if (this.mAdfurikunIntersAdUtil.isLoadFinished(localAdfurikunIntersAdInfo.app_id))
      {
        Context localContext = getContext();
        this.mOrientation = localConfiguration.orientation;
        String str = localAdfurikunIntersAdInfo.index + "_" + localAdfurikunIntersAdInfo.app_id;
        SharedPreferences localSharedPreferences = localContext.getSharedPreferences(AdfurikunConstants.PREF_FILE, 3);
        int i = localSharedPreferences.getInt(str + AdfurikunConstants.PREFKEY_INTERS_AD_FREQUENCY_CT, 0);
        int j = localSharedPreferences.getInt(str + AdfurikunConstants.PREFKEY_INTERS_AD_MAX_CT, 0);
        if ((localAdfurikunIntersAdInfo.max <= 0) || ((localAdfurikunIntersAdInfo.max > 0) && (j < localAdfurikunIntersAdInfo.max)))
        {
          NetworkInfo localNetworkInfo = ((ConnectivityManager)localContext.getSystemService("connectivity")).getActiveNetworkInfo();
          if ((localNetworkInfo != null) && (localNetworkInfo.isConnected()))
          {
            SharedPreferences.Editor localEditor = localSharedPreferences.edit();
            if (i == 0)
            {
              this.mOnAdfurikunIntersAdFinishListener = paramOnAdfurikunIntersAdFinishListener;
              showIntersAd(localAdfurikunIntersAdInfo);
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
        paramOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdError(paramInt, 2002);
        continue;
        if (paramOnAdfurikunIntersAdFinishListener != null)
        {
          paramOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdMaxEnd(paramInt);
          continue;
          if (paramOnAdfurikunIntersAdFinishListener != null)
          {
            paramOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdError(paramInt, 2002);
            continue;
            if (paramOnAdfurikunIntersAdFinishListener != null) {
              paramOnAdfurikunIntersAdFinishListener.onAdfurikunIntersAdError(paramInt, 2001);
            }
          }
        }
      }
    }
  }
  
  public void stopBannerAd(int paramInt)
  {
    if (paramInt < this.mBannerLayoutList.size()) {
      ((AdfurikunLayout)this.mBannerLayoutList.get(paramInt)).stopRotateAd();
    }
  }
  
  public void stopCustomSizeAd(int paramInt)
  {
    if (paramInt < this.mCustomSizeLayoutList.size()) {
      ((AdfurikunLayout)this.mCustomSizeLayoutList.get(paramInt)).stopRotateAd();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunUnityLayout
 * JD-Core Version:    0.7.0.1
 */