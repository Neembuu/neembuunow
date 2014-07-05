package jp.adlantis.android;

import android.content.Context;
import android.text.TextPaint;
import android.widget.TextView;

class SizeFitTextView
  extends TextView
{
  private float _maxTextSize = 20.0F;
  private float _minTextSize = 9.0F;
  
  public SizeFitTextView(Context paramContext)
  {
    super(paramContext);
  }
  
  private void refitText(String paramString, int paramInt)
  {
    int i;
    float f;
    TextPaint localTextPaint;
    if (paramInt > 0)
    {
      i = paramInt - getPaddingLeft() - getPaddingRight();
      f = this._maxTextSize;
      localTextPaint = getPaint();
    }
    for (;;)
    {
      if ((f > this._minTextSize) && (localTextPaint.measureText(paramString) > i))
      {
        f -= 1.0F;
        if (f <= this._minTextSize) {
          f = this._minTextSize;
        }
      }
      else
      {
        setTextSize(f);
        return;
      }
      setTextSize(f);
    }
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 != paramInt3) {
      refitText(getText().toString(), paramInt1);
    }
  }
  
  public void setTextAndSize(String paramString)
  {
    setTextSize(this._maxTextSize);
    super.setText(paramString);
    refitText(paramString, getWidth());
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.SizeFitTextView
 * JD-Core Version:    0.7.0.1
 */