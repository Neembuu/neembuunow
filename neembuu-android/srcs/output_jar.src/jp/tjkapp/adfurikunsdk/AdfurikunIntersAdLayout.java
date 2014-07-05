package jp.tjkapp.adfurikunsdk;

import android.content.Context;

class AdfurikunIntersAdLayout
  extends AdfurikunBase
{
  public AdfurikunIntersAdLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  protected String getIsText()
  {
    AdfurikunWebView localAdfurikunWebView = (AdfurikunWebView)getChildAt(getDisplayedChild());
    AdfurikunInfo.AdInfoForWebView localAdInfoForWebView;
    if (localAdfurikunWebView != null)
    {
      localAdInfoForWebView = localAdfurikunWebView.getAdInfoForWebView();
      if (localAdInfoForWebView == null) {}
    }
    for (String str = localAdInfoForWebView.is_text;; str = "") {
      return str;
    }
  }
  
  protected void initialize(Context paramContext)
  {
    this.mIsIntersAd = true;
    super.initialize(paramContext);
    for (int i = 0;; i++)
    {
      if (i >= 2) {
        return;
      }
      AdfurikunWebView localAdfurikunWebView = (AdfurikunWebView)getChildAt(i);
      if (localAdfurikunWebView != null)
      {
        localAdfurikunWebView.setOneShotMode(false);
        localAdfurikunWebView.setFocusable(false);
      }
    }
  }
  
  protected boolean isTapChkOff()
  {
    int i = 1;
    AdfurikunWebView localAdfurikunWebView = (AdfurikunWebView)getChildAt(getDisplayedChild());
    if (localAdfurikunWebView != null)
    {
      AdfurikunInfo.AdInfoForWebView localAdInfoForWebView = localAdfurikunWebView.getAdInfoForWebView();
      if ((localAdInfoForWebView == null) || (localAdInfoForWebView.tapchk_off_flg != i)) {}
    }
    for (;;)
    {
      return i;
      int j = 0;
    }
  }
  
  protected void recImpression()
  {
    AdfurikunWebView localAdfurikunWebView = (AdfurikunWebView)getChildAt(getDisplayedChild());
    if (localAdfurikunWebView != null) {
      localAdfurikunWebView.recImpression();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunIntersAdLayout
 * JD-Core Version:    0.7.0.1
 */