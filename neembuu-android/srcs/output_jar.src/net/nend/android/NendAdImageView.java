package net.nend.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

@SuppressLint({"ViewConstructor"})
final class NendAdImageView
  extends ImageView
  implements View.OnClickListener
{
  private OnAdImageClickListener listener;
  private String mClickUrl = "";
  
  NendAdImageView(Context paramContext)
  {
    super(paramContext);
    setScaleType(ImageView.ScaleType.FIT_XY);
    setOnClickListener(this);
  }
  
  public void onClick(View paramView)
  {
    NendLog.v("click!! url: " + this.mClickUrl);
    if (this.listener != null) {
      this.listener.onAdImageClick(paramView);
    }
    NendHelper.startBrowser(paramView, this.mClickUrl);
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    setImageDrawable(null);
  }
  
  void setAdInfo(Bitmap paramBitmap, String paramString)
  {
    setImageBitmap(paramBitmap);
    if (paramString != null) {
      this.mClickUrl = paramString;
    }
  }
  
  void setOnAdImageClickListener(OnAdImageClickListener paramOnAdImageClickListener)
  {
    this.listener = paramOnAdImageClickListener;
  }
  
  static abstract interface OnAdImageClickListener
  {
    public abstract void onAdImageClick(View paramView);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendAdImageView
 * JD-Core Version:    0.7.0.1
 */