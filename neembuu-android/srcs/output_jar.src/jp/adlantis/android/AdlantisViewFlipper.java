package jp.adlantis.android;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ViewFlipper;

public class AdlantisViewFlipper
  extends ViewFlipper
{
  public AdlantisViewFlipper(Context paramContext)
  {
    super(paramContext);
  }
  
  public AdlantisViewFlipper(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void logD(String paramString)
  {
    Log.d(getClass().getSimpleName(), paramString);
  }
  
  protected void onDetachedFromWindow()
  {
    try
    {
      super.onDetachedFromWindow();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      for (;;)
      {
        Log.d("AdlantisViewFlipper", "AdlantisViewFlipper ignoring IllegalArgumentException");
        stopFlipping();
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdlantisViewFlipper
 * JD-Core Version:    0.7.0.1
 */