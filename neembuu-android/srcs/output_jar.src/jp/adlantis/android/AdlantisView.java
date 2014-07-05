package jp.adlantis.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class AdlantisView
  extends RelativeLayout
  implements AdRequestNotifier
{
  protected AdViewAdapter adViewAdapter = null;
  
  public AdlantisView(Context paramContext)
  {
    super(paramContext);
    setupView();
  }
  
  public AdlantisView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setupView();
  }
  
  protected AdManager adManager()
  {
    return AdManager.getInstance();
  }
  
  protected AdService adService()
  {
    if (isInEditMode()) {}
    for (Object localObject = new NullAdService();; localObject = adManager().getActiveAdService(getContext())) {
      return localObject;
    }
  }
  
  public void addRequestListener(AdRequestListener paramAdRequestListener)
  {
    this.adViewAdapter.addRequestListener(paramAdRequestListener);
  }
  
  protected AdlantisAdViewContainer adlantisAdViewContainer()
  {
    View localView = this.adViewAdapter.adView();
    if ((localView != null) && ((localView instanceof AdlantisAdViewContainer))) {}
    for (AdlantisAdViewContainer localAdlantisAdViewContainer = (AdlantisAdViewContainer)localView;; localAdlantisAdViewContainer = null) {
      return localAdlantisAdViewContainer;
    }
  }
  
  public void clearAds()
  {
    this.adViewAdapter.clearAds();
  }
  
  public void connect()
  {
    this.adViewAdapter.connect();
  }
  
  protected void onWindowVisibilityChanged(int paramInt)
  {
    AdService localAdService = adService();
    if (localAdService != null)
    {
      if (paramInt != 0) {
        break label18;
      }
      localAdService.resume();
    }
    for (;;)
    {
      return;
      label18:
      localAdService.pause();
    }
  }
  
  public void removeRequestListener(AdRequestListener paramAdRequestListener)
  {
    this.adViewAdapter.removeRequestListener(paramAdRequestListener);
  }
  
  public void setAdFetchInterval(long paramLong)
  {
    AdlantisAdViewContainer localAdlantisAdViewContainer = adlantisAdViewContainer();
    if (localAdlantisAdViewContainer != null) {
      localAdlantisAdViewContainer.setAdFetchInterval(paramLong);
    }
  }
  
  public void setGapPublisherID(String paramString)
  {
    AdlantisAdViewContainer localAdlantisAdViewContainer = adlantisAdViewContainer();
    if (localAdlantisAdViewContainer != null) {
      localAdlantisAdViewContainer.setGapPublisherID(paramString);
    }
  }
  
  public void setKeywords(String paramString)
  {
    AdlantisAdViewContainer localAdlantisAdViewContainer = adlantisAdViewContainer();
    if (localAdlantisAdViewContainer != null) {
      localAdlantisAdViewContainer.setKeywords(paramString);
    }
  }
  
  public void setPublisherID(String paramString)
  {
    AdlantisAdViewContainer localAdlantisAdViewContainer = adlantisAdViewContainer();
    if (localAdlantisAdViewContainer != null) {
      localAdlantisAdViewContainer.setPublisherID(paramString);
    }
  }
  
  protected void setupView()
  {
    AdService localAdService = adService();
    if (localAdService != null)
    {
      localAdService.setTargetingParam(adManager().getTargetingParam());
      this.adViewAdapter = localAdService.adViewAdapter(getContext());
      View localView = this.adViewAdapter.adView();
      if (localView != null) {
        addView(localView);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdlantisView
 * JD-Core Version:    0.7.0.1
 */