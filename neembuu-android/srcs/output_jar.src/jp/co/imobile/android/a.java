package jp.co.imobile.android;

import android.content.Context;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

class a
  implements bp
{
  final ao a;
  final AtomicBoolean b = new AtomicBoolean(true);
  private final n c;
  private final int d;
  private final int e;
  private final int f;
  private WeakReference g;
  private bb h;
  private al i;
  private final AtomicReference j = new AtomicReference();
  private WeakReference k = new WeakReference(null);
  private final AtomicBoolean l = new AtomicBoolean(false);
  
  a(Context paramContext, int paramInt1, int paramInt2, int paramInt3)
  {
    this.d = paramInt1;
    this.e = paramInt2;
    this.f = paramInt3;
    this.g = new WeakReference(paramContext);
    this.a = new ao(paramContext, (byte)0);
    this.c = new n(paramInt1, paramInt2, paramInt3, this.a);
  }
  
  private af a(aq paramaq, ag paramag)
  {
    af localaf = paramag.a();
    localaf.a(paramaq);
    if ((localaf instanceof bl))
    {
      bl localbl = (bl)localaf;
      n localn = this.c;
      paramaq.a(localbl.c().a(localbl, localn));
    }
    return localaf;
  }
  
  /* Error */
  /**
   * @deprecated
   */
  private final void a(AdRequestResult paramAdRequestResult)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 50	jp/co/imobile/android/a:k	Ljava/lang/ref/WeakReference;
    //   6: invokevirtual 109	java/lang/ref/WeakReference:get	()Ljava/lang/Object;
    //   9: checkcast 111	jp/co/imobile/android/q
    //   12: astore_3
    //   13: aload_3
    //   14: ifnonnull +6 -> 20
    //   17: aload_0
    //   18: monitorexit
    //   19: return
    //   20: aload_3
    //   21: aload_1
    //   22: invokeinterface 113 2 0
    //   27: aload_3
    //   28: invokeinterface 116 1 0
    //   33: ifeq -16 -> 17
    //   36: aload_0
    //   37: invokevirtual 118	jp/co/imobile/android/a:h	()V
    //   40: goto -23 -> 17
    //   43: astore_2
    //   44: aload_0
    //   45: monitorexit
    //   46: aload_2
    //   47: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	48	0	this	a
    //   0	48	1	paramAdRequestResult	AdRequestResult
    //   43	4	2	localObject	Object
    //   12	16	3	localq	q
    // Exception table:
    //   from	to	target	type
    //   2	13	43	finally
    //   20	40	43	finally
  }
  
  /**
   * @deprecated
   */
  private final void a(al paramal)
  {
    try
    {
      this.i = paramal;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  private final void a(bb parambb)
  {
    try
    {
      this.h = parambb;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private void a(h paramh)
  {
    int m = paramh.a.c();
    cj.b.execute(new e(this, paramh, m));
  }
  
  private static void a(o paramo, Message paramMessage)
  {
    paramo.a(((h)paramMessage.obj).b);
  }
  
  /* Error */
  /**
   * @deprecated
   */
  private final void b(AdRequestResult paramAdRequestResult)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 50	jp/co/imobile/android/a:k	Ljava/lang/ref/WeakReference;
    //   6: invokevirtual 109	java/lang/ref/WeakReference:get	()Ljava/lang/Object;
    //   9: checkcast 111	jp/co/imobile/android/q
    //   12: astore_3
    //   13: aload_3
    //   14: ifnonnull +6 -> 20
    //   17: aload_0
    //   18: monitorexit
    //   19: return
    //   20: aload_3
    //   21: aload_1
    //   22: invokeinterface 269 2 0
    //   27: aload_3
    //   28: invokeinterface 271 1 0
    //   33: ifeq -16 -> 17
    //   36: aload_0
    //   37: invokevirtual 118	jp/co/imobile/android/a:h	()V
    //   40: goto -23 -> 17
    //   43: astore_2
    //   44: aload_0
    //   45: monitorexit
    //   46: aload_2
    //   47: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	48	0	this	a
    //   0	48	1	paramAdRequestResult	AdRequestResult
    //   43	4	2	localObject	Object
    //   12	16	3	localq	q
    // Exception table:
    //   from	to	target	type
    //   2	13	43	finally
    //   20	40	43	finally
  }
  
  private Future i()
  {
    if (this.j == null) {}
    for (Future localFuture = null;; localFuture = (Future)this.j.get()) {
      return localFuture;
    }
  }
  
  /**
   * @deprecated
   */
  private Context j()
  {
    try
    {
      Context localContext = (Context)this.g.get();
      return localContext;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  private final bb k()
  {
    try
    {
      bb localbb = this.h;
      return localbb;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  private final al l()
  {
    try
    {
      al localal = this.i;
      return localal;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final r a(o paramo)
  {
    return b(paramo);
  }
  
  /**
   * @deprecated
   */
  final void a(Context paramContext)
  {
    try
    {
      if ((Context)this.g.get() == null)
      {
        String[] arrayOfString = new String[2];
        arrayOfString[0] = "type:";
        arrayOfString[1] = "context";
        cj.b("removed week ref", this, arrayOfString);
        this.g.clear();
        this.g = new WeakReference(paramContext);
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  final void a(q paramq)
  {
    try
    {
      this.k.clear();
      this.k = new WeakReference(paramq);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  final void a(boolean paramBoolean)
  {
    this.c.a(paramBoolean);
  }
  
  final boolean a()
  {
    return this.c.a();
  }
  
  protected r b(o paramo)
  {
    r localr;
    try
    {
      g localg = new g(this, paramo);
      localr = d();
      if (localr == r.d)
      {
        cj.c("beginRequestAd -> start execute async", this, new String[0]);
        Future localFuture = cj.b.submit(new j(this, localg));
        this.j.set(localFuture);
        label66:
        localr = r.a;
      }
    }
    catch (RejectedExecutionException localRejectedExecutionException)
    {
      break label66;
    }
    return localr;
  }
  
  final boolean b()
  {
    return this.l.getAndSet(false);
  }
  
  final void c()
  {
    cj.b("cancelRequestAd -> accept request", this, new String[0]);
    Future localFuture = i();
    if ((localFuture != null) && (!localFuture.isDone()) && (!localFuture.isCancelled()))
    {
      cj.b("cancelRequestAd -> async executing", this, new String[0]);
      localFuture.cancel(true);
    }
  }
  
  r d()
  {
    r localr;
    if (!this.a.b()) {
      localr = r.e;
    }
    for (;;)
    {
      return localr;
      Future localFuture = i();
      if ((localFuture != null) && (!localFuture.isDone())) {
        localr = r.b;
      } else {
        localr = r.d;
      }
    }
  }
  
  final int e()
  {
    return this.d;
  }
  
  final int f()
  {
    return this.e;
  }
  
  final int g()
  {
    return this.f;
  }
  
  public String getLogContents()
  {
    return ",spotId:" + this.f;
  }
  
  public String getLogTag()
  {
    return "(IM)AdController:";
  }
  
  /**
   * @deprecated
   */
  final void h()
  {
    try
    {
      this.k.clear();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.a
 * JD-Core Version:    0.7.0.1
 */