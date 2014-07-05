package jp.co.imobile.android;

import java.util.concurrent.atomic.AtomicBoolean;

final class w
  implements Runnable
{
  w(AdView paramAdView) {}
  
  public final void run()
  {
    r localr;
    try
    {
      if (AdView.a(this.a).get()) {
        return;
      }
      localr = AdView.f(this.a).d();
      if (localr == r.d)
      {
        AdView.j(this.a);
        AdView.f(this.a).a(AdView.k(this.a));
      }
    }
    catch (Exception localException)
    {
      cj.a("(IM)AdView:", localException);
    }
    if (localr == r.e)
    {
      cj.c("stop view for disable network", this.a, new String[0]);
      AdView.a(this.a, AdRequestResult.f);
    }
    else if (localr == r.b)
    {
      cj.b("wait for async executing", this.a, new String[0]);
    }
    else if ((localr == r.c) && (cj.a()))
    {
      cj.a("It is an initialization error. Please review setting.", this.a, new String[0]);
      AdView.a(this.a, "(IM)It is an initialization error. Please review setting.");
      AdView.a(this.a, AdRequestResult.a);
    }
    else if ((localr == r.c) && (!cj.a()))
    {
      cj.a("It is an initialization error. Please review setting.", this.a, new String[0]);
      AdView.a(this.a, AdRequestResult.a);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.w
 * JD-Core Version:    0.7.0.1
 */