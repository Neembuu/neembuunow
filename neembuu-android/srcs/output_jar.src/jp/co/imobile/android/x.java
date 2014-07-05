package jp.co.imobile.android;

final class x
  implements q
{
  x(AdView paramAdView, AdViewRequestListener paramAdViewRequestListener) {}
  
  public final void a(AdRequestResult paramAdRequestResult)
  {
    this.b.onCompleted(paramAdRequestResult, this.a);
  }
  
  public final boolean a()
  {
    ae localae = AdView.b(this.a).a();
    if (!localae.b()) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public final void b(AdRequestResult paramAdRequestResult)
  {
    this.b.onFailed(paramAdRequestResult, this.a);
  }
  
  public final boolean b()
  {
    ae localae = AdView.b(this.a).a();
    if (!localae.c()) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.x
 * JD-Core Version:    0.7.0.1
 */