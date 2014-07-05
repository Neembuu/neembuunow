package jp.adlantis.android;

import android.content.Context;
import android.view.View;

public class InternationalAdService
  extends AdService
{
  private String adSpace;
  
  public InternationalAdService(String paramString)
  {
    this.adSpace = paramString;
  }
  
  public AdViewAdapter adViewAdapter(Context paramContext)
  {
    return new NullAdViewAdapter(createAdView(paramContext));
  }
  
  public View createAdView(Context paramContext)
  {
    return null;
  }
  
  public String getAdSpace()
  {
    return this.adSpace;
  }
  
  public void pause() {}
  
  public void resume() {}
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.InternationalAdService
 * JD-Core Version:    0.7.0.1
 */