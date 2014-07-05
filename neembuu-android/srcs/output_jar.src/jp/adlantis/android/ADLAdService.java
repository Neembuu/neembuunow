package jp.adlantis.android;

import android.content.Context;
import android.view.View;

public class ADLAdService
  extends AdService
{
  private String publisherId;
  
  public ADLAdService(String paramString)
  {
    this.publisherId = paramString;
  }
  
  public AdViewAdapter adViewAdapter(Context paramContext)
  {
    return new AdlantisViewAdapter(createAdView(paramContext));
  }
  
  public View createAdView(Context paramContext)
  {
    return new AdlantisAdViewContainer(paramContext);
  }
  
  public String getPublisherId()
  {
    return this.publisherId;
  }
  
  public void pause() {}
  
  public void resume() {}
  
  public void setPublisherId(String paramString)
  {
    this.publisherId = paramString;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.ADLAdService
 * JD-Core Version:    0.7.0.1
 */