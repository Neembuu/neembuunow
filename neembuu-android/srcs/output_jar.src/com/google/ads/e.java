package com.google.ads;

import android.app.Activity;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import com.google.ads.internal.d;
import com.google.ads.internal.g;
import com.google.ads.util.b;
import com.google.ads.util.i.b;
import com.google.ads.util.i.d;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class e
{
  private final d a;
  private h b = null;
  private final Object c = new Object();
  private Thread d = null;
  private final Object e = new Object();
  private boolean f = false;
  private final Object g = new Object();
  
  protected e()
  {
    this.a = null;
  }
  
  public e(d paramd)
  {
    com.google.ads.util.a.b(paramd);
    this.a = paramd;
  }
  
  public static boolean a(c paramc, d paramd)
  {
    boolean bool;
    if (paramc.j() == null) {
      bool = true;
    }
    for (;;)
    {
      return bool;
      if (paramd.i().b())
      {
        if (!paramc.j().a())
        {
          b.e("InterstitialAd received a mediation response corresponding to a non-interstitial ad. Make sure you specify 'interstitial' as the ad-type in the mediation UI.");
          bool = false;
        }
        else
        {
          bool = true;
        }
      }
      else
      {
        AdSize localAdSize1 = ((com.google.ads.internal.h)paramd.i().g.a()).c();
        if (paramc.j().a())
        {
          b.e("AdView received a mediation response corresponding to an interstitial ad. Make sure you specify the banner ad size corresponding to the AdSize you used in your AdView  (" + localAdSize1 + ") in the ad-type field in the mediation UI.");
          bool = false;
        }
        else
        {
          AdSize localAdSize2 = paramc.j().c();
          if (localAdSize2 != localAdSize1)
          {
            b.e("Mediation server returned ad size: '" + localAdSize2 + "', while the AdView was created with ad size: '" + localAdSize1 + "'. Using the ad-size passed to the AdView on creation.");
            bool = false;
          }
          else
          {
            bool = true;
          }
        }
      }
    }
  }
  
  private boolean a(h paramh, String paramString)
  {
    if (e() != paramh) {
      b.c("GWController: ignoring callback to " + paramString + " from non showing ambassador with adapter class: '" + paramh.h() + "'.");
    }
    for (boolean bool = false;; bool = true) {
      return bool;
    }
  }
  
  private boolean a(String paramString, Activity paramActivity, AdRequest paramAdRequest, final f paramf, HashMap<String, String> paramHashMap, long paramLong)
  {
    boolean bool;
    synchronized (new h(this, (com.google.ads.internal.h)this.a.i().g.a(), paramf, paramString, paramAdRequest, paramHashMap))
    {
      ???.a(paramActivity);
      try
      {
        while ((!???.c()) && (paramLong > 0L))
        {
          long l1 = SystemClock.elapsedRealtime();
          ???.wait(paramLong);
          long l2 = SystemClock.elapsedRealtime();
          paramLong -= l2 - l1;
        }
        final View localView;
        localObject = finally;
      }
      catch (InterruptedException localInterruptedException)
      {
        b.a("Interrupted while waiting for ad network to load ad using adapter class: " + paramString);
        this.a.n().a(???.e());
        if ((???.c()) && (???.d()))
        {
          if (this.a.i().b()) {}
          for (localView = null;; localView = ???.f())
          {
            ((Handler)m.a().c.a()).post(new Runnable()
            {
              public void run()
              {
                if (e.a(e.this, localh)) {
                  b.a("Trying to switch GWAdNetworkAmbassadors, but GWController().destroy() has been called. Destroying the new ambassador and terminating mediation.");
                }
                for (;;)
                {
                  return;
                  e.b(e.this).a(localView, localh, paramf, false);
                }
              }
            });
            bool = true;
            break;
          }
        }
        if (!???.c()) {
          b.a("Timeout occurred in adapter class: " + ???.h());
        }
        ???.b();
        bool = false;
      }
    }
    return bool;
  }
  
  private void b(final c paramc, AdRequest paramAdRequest)
  {
    for (;;)
    {
      HashMap localHashMap;
      f localf;
      String str4;
      Activity localActivity;
      synchronized (this.e)
      {
        com.google.ads.util.a.a(Thread.currentThread(), this.d);
        List localList1 = paramc.f();
        if (paramc.a())
        {
          l = paramc.b();
          Iterator localIterator1 = localList1.iterator();
          if (!localIterator1.hasNext()) {
            break label296;
          }
          a locala = (a)localIterator1.next();
          b.a("Looking to fetch ads from network: " + locala.b());
          List localList2 = locala.c();
          localHashMap = locala.e();
          localList3 = locala.d();
          String str1 = locala.a();
          String str2 = locala.b();
          String str3 = paramc.c();
          if (localList3 == null) {
            break label243;
          }
          localf = new f(str1, str2, str3, localList3, paramc.h(), paramc.i());
          Iterator localIterator2 = localList2.iterator();
          if (!localIterator2.hasNext()) {
            continue;
          }
          str4 = (String)localIterator2.next();
          localActivity = (Activity)this.a.i().c.a();
          if (localActivity != null) {
            break label252;
          }
          b.a("Activity is null while mediating.  Terminating mediation thread.");
          return;
        }
      }
      long l = 10000L;
      continue;
      label243:
      List localList3 = paramc.g();
      continue;
      label252:
      this.a.n().c();
      if (!a(str4, localActivity, paramAdRequest, localf, localHashMap, l)) {
        if (d())
        {
          b.a("GWController.destroy() called. Terminating mediation thread.");
          continue;
          label296:
          ((Handler)m.a().c.a()).post(new Runnable()
          {
            public void run()
            {
              e.b(e.this).b(paramc);
            }
          });
        }
      }
    }
  }
  
  private boolean d()
  {
    synchronized (this.g)
    {
      boolean bool = this.f;
      return bool;
    }
  }
  
  private h e()
  {
    synchronized (this.c)
    {
      h localh = this.b;
      return localh;
    }
  }
  
  private boolean e(h paramh)
  {
    boolean bool;
    synchronized (this.g)
    {
      if (d())
      {
        paramh.b();
        bool = true;
      }
      else
      {
        bool = false;
      }
    }
    return bool;
  }
  
  /* Error */
  public void a(final c paramc, final AdRequest paramAdRequest)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 44	com/google/ads/e:e	Ljava/lang/Object;
    //   4: astore_3
    //   5: aload_3
    //   6: monitorenter
    //   7: aload_0
    //   8: invokevirtual 294	com/google/ads/e:a	()Z
    //   11: ifeq +14 -> 25
    //   14: ldc_w 296
    //   17: invokestatic 147	com/google/ads/util/b:c	(Ljava/lang/String;)V
    //   20: aload_3
    //   21: monitorexit
    //   22: goto +108 -> 130
    //   25: aload_1
    //   26: invokevirtual 297	com/google/ads/c:d	()Z
    //   29: ifeq +81 -> 110
    //   32: aload_0
    //   33: getfield 50	com/google/ads/e:a	Lcom/google/ads/internal/d;
    //   36: aload_1
    //   37: invokevirtual 299	com/google/ads/c:e	()I
    //   40: i2f
    //   41: invokevirtual 302	com/google/ads/internal/d:a	(F)V
    //   44: aload_0
    //   45: getfield 50	com/google/ads/e:a	Lcom/google/ads/internal/d;
    //   48: invokevirtual 305	com/google/ads/internal/d:t	()Z
    //   51: ifne +10 -> 61
    //   54: aload_0
    //   55: getfield 50	com/google/ads/e:a	Lcom/google/ads/internal/d;
    //   58: invokevirtual 307	com/google/ads/internal/d:g	()V
    //   61: aload_1
    //   62: aload_0
    //   63: getfield 50	com/google/ads/e:a	Lcom/google/ads/internal/d;
    //   66: invokestatic 309	com/google/ads/e:a	(Lcom/google/ads/c;Lcom/google/ads/internal/d;)Z
    //   69: pop
    //   70: aload_0
    //   71: new 212	java/lang/Thread
    //   74: dup
    //   75: new 6	com/google/ads/e$1
    //   78: dup
    //   79: aload_0
    //   80: aload_1
    //   81: aload_2
    //   82: invokespecial 311	com/google/ads/e$1:<init>	(Lcom/google/ads/e;Lcom/google/ads/c;Lcom/google/ads/AdRequest;)V
    //   85: invokespecial 314	java/lang/Thread:<init>	(Ljava/lang/Runnable;)V
    //   88: putfield 42	com/google/ads/e:d	Ljava/lang/Thread;
    //   91: aload_0
    //   92: getfield 42	com/google/ads/e:d	Ljava/lang/Thread;
    //   95: invokevirtual 317	java/lang/Thread:start	()V
    //   98: aload_3
    //   99: monitorexit
    //   100: goto +30 -> 130
    //   103: astore 4
    //   105: aload_3
    //   106: monitorexit
    //   107: aload 4
    //   109: athrow
    //   110: aload_0
    //   111: getfield 50	com/google/ads/e:a	Lcom/google/ads/internal/d;
    //   114: invokevirtual 305	com/google/ads/internal/d:t	()Z
    //   117: ifeq -56 -> 61
    //   120: aload_0
    //   121: getfield 50	com/google/ads/e:a	Lcom/google/ads/internal/d;
    //   124: invokevirtual 319	com/google/ads/internal/d:f	()V
    //   127: goto -66 -> 61
    //   130: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	131	0	this	e
    //   0	131	1	paramc	c
    //   0	131	2	paramAdRequest	AdRequest
    //   4	102	3	localObject1	Object
    //   103	5	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   7	107	103	finally
    //   110	127	103	finally
  }
  
  public void a(h paramh)
  {
    if (!a(paramh, "onPresentScreen")) {}
    for (;;)
    {
      return;
      ((Handler)m.a().c.a()).post(new Runnable()
      {
        public void run()
        {
          e.b(e.this).v();
        }
      });
    }
  }
  
  public void a(h paramh, final View paramView)
  {
    if (e() != paramh) {
      b.c("GWController: ignoring onAdRefreshed() callback from non-showing ambassador (adapter class name is '" + paramh.h() + "').");
    }
    for (;;)
    {
      return;
      this.a.n().a(g.a.a);
      final f localf = this.b.a();
      ((Handler)m.a().c.a()).post(new Runnable()
      {
        public void run()
        {
          e.b(e.this).a(paramView, e.c(e.this), localf, true);
        }
      });
    }
  }
  
  public void a(h paramh, final boolean paramBoolean)
  {
    if (!a(paramh, "onAdClicked()")) {}
    for (;;)
    {
      return;
      final f localf = paramh.a();
      ((Handler)m.a().c.a()).post(new Runnable()
      {
        public void run()
        {
          e.b(e.this).a(localf, paramBoolean);
        }
      });
    }
  }
  
  public boolean a()
  {
    for (;;)
    {
      synchronized (this.e)
      {
        if (this.d != null)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  public void b()
  {
    synchronized (this.g)
    {
      this.f = true;
      d(null);
      synchronized (this.e)
      {
        if (this.d != null) {
          this.d.interrupt();
        }
        return;
      }
    }
  }
  
  public void b(h paramh)
  {
    if (!a(paramh, "onDismissScreen")) {}
    for (;;)
    {
      return;
      ((Handler)m.a().c.a()).post(new Runnable()
      {
        public void run()
        {
          e.b(e.this).u();
        }
      });
    }
  }
  
  public void c(h paramh)
  {
    if (!a(paramh, "onLeaveApplication")) {}
    for (;;)
    {
      return;
      ((Handler)m.a().c.a()).post(new Runnable()
      {
        public void run()
        {
          e.b(e.this).w();
        }
      });
    }
  }
  
  public boolean c()
  {
    com.google.ads.util.a.a(this.a.i().b());
    h localh = e();
    if (localh != null) {
      localh.g();
    }
    for (boolean bool = true;; bool = false)
    {
      return bool;
      b.b("There is no ad ready to show.");
    }
  }
  
  public void d(h paramh)
  {
    synchronized (this.c)
    {
      if (this.b != paramh)
      {
        if (this.b != null) {
          this.b.b();
        }
        this.b = paramh;
      }
      return;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.e
 * JD-Core Version:    0.7.0.1
 */