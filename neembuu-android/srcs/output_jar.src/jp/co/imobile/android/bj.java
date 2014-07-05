package jp.co.imobile.android;

import android.graphics.drawable.Drawable;

final class bj
  extends bl
{
  private final int a;
  
  bj(bk parambk)
  {
    super(bk.a(parambk), bk.b(parambk), bk.c(parambk), bk.d(parambk), bk.e(parambk));
    this.a = bk.f(parambk);
  }
  
  final Drawable a(n paramn)
  {
    if (!b()) {}
    for (Drawable localDrawable = paramn.a(a(), bo.b);; localDrawable = null)
    {
      return localDrawable;
      cj.d().a("i-mobile SDK", "(IM)ImageAdvertisement:" + "default ad did not request image" + new StringBuilder("[params] advertisementId:").append(String.valueOf(a())).toString());
    }
  }
  
  public final void a(aq paramaq)
  {
    super.a(paramaq);
    paramaq.d(this.a);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.bj
 * JD-Core Version:    0.7.0.1
 */