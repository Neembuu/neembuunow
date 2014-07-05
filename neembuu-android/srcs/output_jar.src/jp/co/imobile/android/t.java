package jp.co.imobile.android;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

final class t
  implements o
{
  t(AdView paramAdView) {}
  
  private void a()
  {
    try
    {
      if (AdView.a(this.a).get()) {
        return;
      }
      ae localae = AdView.b(this.a).a();
      if (!localae.b()) {
        cj.c("end of deliver for once", this.a, new String[0]);
      } else if (AdView.b(this.a) == AdViewRunMode.EASY_MANUAL) {
        AdView.e(this.a);
      }
    }
    catch (Exception localException)
    {
      cj.a("(IM)AdView:", localException);
    }
    AdView.a(this.a, 350L);
  }
  
  private void b()
  {
    try
    {
      AdView.f(this.a).b.compareAndSet(false, true);
      if (!AdView.a(this.a).get())
      {
        ae localae = AdView.b(this.a).a();
        if (!localae.c()) {
          cj.c("end of deliver for not found ad or error", this.a, new String[0]);
        } else {
          AdView.a(this.a, 60000L);
        }
      }
    }
    catch (Exception localException)
    {
      cj.a("(IM)AdView:", localException);
    }
  }
  
  public final aw a(ap paramap)
  {
    for (;;)
    {
      try
      {
        if (!AdView.a(this.a).get()) {
          continue;
        }
        localaw = aw.c;
        if (!paramap.e()) {
          continue;
        }
        a();
      }
      catch (Exception localException)
      {
        cj.a("(IM)AdView:", localException);
        aw localaw = aw.d;
        if (!paramap.e()) {
          continue;
        }
        a();
        continue;
        AdView.c(this.a).set(ap.a);
        localaw = aw.c;
        if (!paramap.e()) {
          continue;
        }
        a();
        continue;
        cj.c("end of deliver for server setting", this.a, new String[0]);
        continue;
        cj.c("end of deliver for server setting", this.a, new String[0]);
        continue;
        cj.c("end of deliver for server setting", this.a, new String[0]);
        continue;
      }
      finally
      {
        if (!paramap.e()) {
          break label498;
        }
      }
      return localaw;
      cj.c("end of deliver for server setting", this.a, new String[0]);
      continue;
      if (!AdView.b(this.a).a().c(this.a))
      {
        localaw = aw.c;
        if (paramap.e()) {
          a();
        } else {
          cj.c("end of deliver for server setting", this.a, new String[0]);
        }
      }
      else if (paramap.equals(ap.a))
      {
        AdView.c(this.a).set(ap.a);
        localaw = aw.c;
        if (paramap.e()) {
          a();
        } else {
          cj.c("end of deliver for server setting", this.a, new String[0]);
        }
      }
      else
      {
        if (AdView.b(this.a).a().d())
        {
          AdView.d(this.a).setOutAnimation(paramap.i().c());
          AdView.d(this.a).setInAnimation(paramap.i().b());
        }
        for (;;)
        {
          if ((paramap.a() != s.a) && (paramap.a() != s.c)) {
            break label404;
          }
          Drawable localDrawable = paramap.d();
          AdView.g(this.a).setImageDrawable(localDrawable);
          localDrawable.setCallback(null);
          AdView.d(this.a).setDisplayedChild(paramap.a().a());
          AdView.c(this.a).set(paramap);
          if (cj.a())
          {
            AdView localAdView = this.a;
            String[] arrayOfString = new String[2];
            arrayOfString[0] = ",advertisementId:";
            arrayOfString[1] = paramap.toString();
            cj.b("DisplayAdvertisementInfo", localAdView, arrayOfString);
          }
          localaw = aw.a;
          if (!paramap.e()) {
            break label452;
          }
          a();
          break;
          AdView.d(this.a).setOutAnimation(ar.a.c());
          AdView.d(this.a).setInAnimation(ar.a.b());
        }
      }
    }
    label404:
    label452:
    a();
    for (;;)
    {
      throw localObject;
      label498:
      cj.c("end of deliver for server setting", this.a, new String[0]);
    }
  }
  
  public final void a(AdRequestResult paramAdRequestResult)
  {
    try
    {
      AdView localAdView = this.a;
      String[] arrayOfString = new String[4];
      arrayOfString[0] = ", result:";
      arrayOfString[1] = paramAdRequestResult.getResult().toString();
      arrayOfString[2] = ", message:";
      arrayOfString[3] = paramAdRequestResult.getErrorMessage();
      cj.a("view callbacked sdk error wait 60m", localAdView, arrayOfString);
      if (cj.a()) {
        AdView.a(this.a, "(IM)" + paramAdRequestResult.getErrorMessage());
      }
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        cj.a("(IM)AdView:", localException);
        b();
      }
    }
    finally
    {
      b();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.t
 * JD-Core Version:    0.7.0.1
 */