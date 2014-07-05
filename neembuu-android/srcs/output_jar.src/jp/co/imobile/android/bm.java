package jp.co.imobile.android;

import android.graphics.drawable.Drawable;

final class bm
  extends bl
{
  private boolean a = false;
  private final String b;
  private final String c;
  
  bm(bn parambn)
  {
    super(bn.a(parambn), bn.b(parambn), bn.c(parambn), bn.d(parambn), false);
    this.c = bn.e(parambn);
    this.b = bn.f(parambn);
  }
  
  final Drawable a(n paramn)
  {
    return paramn.b(a(), bo.b);
  }
  
  public final void a(aq paramaq)
  {
    super.a(paramaq);
    paramaq.a(true).b(this.c).b(false);
  }
  
  final void a(boolean paramBoolean)
  {
    this.a = paramBoolean;
  }
  
  final boolean d()
  {
    return this.a;
  }
  
  final String e()
  {
    return this.b;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.bm
 * JD-Core Version:    0.7.0.1
 */