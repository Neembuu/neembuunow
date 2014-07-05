package jp.co.imobile.android;

import android.content.Context;
import android.widget.ViewFlipper;

final class bh
  extends ViewFlipper
{
  bh(Context paramContext)
  {
    super(paramContext);
  }
  
  protected final void onAttachedToWindow()
  {
    super.onAttachedToWindow();
  }
  
  protected final void onDetachedFromWindow()
  {
    try
    {
      super.onDetachedFromWindow();
      label4:
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      break label4;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.bh
 * JD-Core Version:    0.7.0.1
 */