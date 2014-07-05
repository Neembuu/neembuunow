package jp.adlantis.android;

import android.content.Context;
import android.view.View;

public class NullAdService
  extends AdService
{
  public AdViewAdapter adViewAdapter(Context paramContext)
  {
    return new NullAdViewAdapter(createAdView(paramContext));
  }
  
  public View createAdView(Context paramContext)
  {
    return new NullAdView(paramContext);
  }
  
  public void pause() {}
  
  public void resume() {}
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.NullAdService
 * JD-Core Version:    0.7.0.1
 */