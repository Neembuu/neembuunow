package jp.co.cayto.appc.sdk.android;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import java.util.HashMap;
import jp.co.cayto.appc.sdk.android.common.AppController;

public final class AppC
{
  public static final int CUTIN_BASIC = 0;
  public static final int CUTIN_CUBE = 1;
  private Activity mActivity;
  private AppCCutinView mAppCutInView;
  
  public AppC(Activity paramActivity)
  {
    AppController.createIncetance(paramActivity.getApplicationContext());
    this.mActivity = paramActivity;
  }
  
  private void callCutin(int paramInt, String paramString)
  {
    if (this.mAppCutInView == null)
    {
      Log.w("appC", "CutIn not Initialize.");
      return;
    }
    String str;
    switch (paramInt)
    {
    default: 
      str = null;
    }
    for (;;)
    {
      this.mAppCutInView.show(str, paramString);
      break;
      str = "basic";
      continue;
      str = "icons";
    }
  }
  
  public void callAgreementActivity()
  {
    callAgreementActivity(null);
  }
  
  public void callAgreementActivity(String paramString)
  {
    Intent localIntent = new Intent(this.mActivity, AppCAgreementActivity.class);
    localIntent.putExtra("redirect_class", paramString);
    this.mActivity.startActivity(localIntent);
  }
  
  public void callCutin(int paramInt)
  {
    callCutin(paramInt, "anywhere");
  }
  
  public void callCutinFinish(int paramInt)
  {
    callCutin(paramInt, "finish");
  }
  
  public void callWebActivity()
  {
    Intent localIntent = new Intent(this.mActivity, AppCWebActivity.class);
    localIntent.putExtra("type", "pr_list");
    this.mActivity.startActivity(localIntent);
  }
  
  public void initCutin()
  {
    if (this.mAppCutInView == null) {
      this.mAppCutInView = new AppCCutinView(this.mActivity);
    }
    this.mAppCutInView.init();
  }
  
  public AppCBannerView loadBannerView()
  {
    return new AppCBannerView(this.mActivity).createView();
  }
  
  public AppCMarqueeView loadMarqueeView()
  {
    return new AppCMarqueeView(this.mActivity).createView(null);
  }
  
  public AppCMarqueeView loadMarqueeView(String paramString)
  {
    return new AppCMarqueeView(this.mActivity).createView(paramString);
  }
  
  public AppCMoveIconView loadMoveIconView()
  {
    return new AppCMoveIconView(this.mActivity).createView(null, null);
  }
  
  public AppCMoveIconView loadMoveIconView(String paramString)
  {
    return new AppCMoveIconView(this.mActivity).createView(paramString, null);
  }
  
  public AppCMoveIconView loadMoveIconView(String paramString1, String paramString2)
  {
    return new AppCMoveIconView(this.mActivity).createView(paramString1, paramString2);
  }
  
  public AppCSimpleView loadSimpleView()
  {
    return new AppCSimpleView(this.mActivity).createView(null, null);
  }
  
  public AppCSimpleView loadSimpleView(String paramString)
  {
    return new AppCSimpleView(this.mActivity).createView(paramString, null);
  }
  
  public AppCSimpleView loadSimpleView(String paramString1, String paramString2)
  {
    return new AppCSimpleView(this.mActivity).createView(paramString1, paramString2);
  }
  
  public void registCPI(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {}
    for (;;)
    {
      return;
      HashMap localHashMap = new HashMap();
      localHashMap.put("target_package", paramString1);
      AppController.createIncetance(this.mActivity.getApplicationContext()).registCPI(this.mActivity.getApplicationContext(), localHashMap, paramString2);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.AppC
 * JD-Core Version:    0.7.0.1
 */