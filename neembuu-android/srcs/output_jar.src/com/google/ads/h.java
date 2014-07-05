package com.google.ads;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import com.google.ads.mediation.MediationAdapter;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.util.a;
import com.google.ads.util.b;
import com.google.ads.util.i.b;
import java.util.HashMap;

public class h
{
  final com.google.ads.internal.h a;
  private final f b;
  private boolean c;
  private boolean d;
  private g.a e;
  private final e f;
  private MediationAdapter<?, ?> g;
  private boolean h;
  private boolean i;
  private View j;
  private final String k;
  private final AdRequest l;
  private final HashMap<String, String> m;
  
  public h(e parame, com.google.ads.internal.h paramh, f paramf, String paramString, AdRequest paramAdRequest, HashMap<String, String> paramHashMap)
  {
    a.b(TextUtils.isEmpty(paramString));
    this.f = parame;
    this.a = paramh;
    this.b = paramf;
    this.k = paramString;
    this.l = paramAdRequest;
    this.m = paramHashMap;
    this.c = false;
    this.d = false;
    this.e = null;
    this.g = null;
    this.h = false;
    this.i = false;
    this.j = null;
  }
  
  public f a()
  {
    return this.b;
  }
  
  /**
   * @deprecated
   */
  public void a(Activity paramActivity)
  {
    try
    {
      a.b(this.h, "startLoadAdTask has already been called.");
      this.h = true;
      ((Handler)m.a().c.a()).post(new i(this, paramActivity, this.k, this.l, this.m));
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
  void a(View paramView)
  {
    try
    {
      this.j = paramView;
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
  void a(MediationAdapter<?, ?> paramMediationAdapter)
  {
    try
    {
      this.g = paramMediationAdapter;
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
  void a(boolean paramBoolean, g.a parama)
  {
    try
    {
      this.d = paramBoolean;
      this.c = true;
      this.e = parama;
      notify();
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
  public void b()
  {
    try
    {
      a.a(this.h, "destroy() called but startLoadAdTask has not been called.");
      ((Handler)m.a().c.a()).post(new Runnable()
      {
        public void run()
        {
          if (h.this.l()) {
            a.b(h.a(h.this));
          }
          try
          {
            h.a(h.this).destroy();
            b.a("Called destroy() for adapter with class: " + h.a(h.this).getClass().getName());
            return;
          }
          catch (Throwable localThrowable)
          {
            for (;;)
            {
              b.b("Error while destroying adapter (" + h.this.h() + "):", localThrowable);
            }
          }
        }
      });
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
  public boolean c()
  {
    try
    {
      boolean bool = this.c;
      return bool;
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
  public boolean d()
  {
    try
    {
      a.a(this.c, "isLoadAdTaskSuccessful() called when isLoadAdTaskDone() is false.");
      boolean bool = this.d;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  /**
   * @deprecated
   */
  public g.a e()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 67	com/google/ads/h:e	Lcom/google/ads/g$a;
    //   6: ifnonnull +11 -> 17
    //   9: getstatic 128	com/google/ads/g$a:d	Lcom/google/ads/g$a;
    //   12: astore_2
    //   13: aload_0
    //   14: monitorexit
    //   15: aload_2
    //   16: areturn
    //   17: aload_0
    //   18: getfield 67	com/google/ads/h:e	Lcom/google/ads/g$a;
    //   21: astore_2
    //   22: goto -9 -> 13
    //   25: astore_1
    //   26: aload_0
    //   27: monitorexit
    //   28: aload_1
    //   29: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	30	0	this	h
    //   25	4	1	localObject	Object
    //   12	10	2	locala	g.a
    // Exception table:
    //   from	to	target	type
    //   2	13	25	finally
    //   17	22	25	finally
  }
  
  /**
   * @deprecated
   */
  public View f()
  {
    try
    {
      a.a(this.c, "getAdView() called when isLoadAdTaskDone() is false.");
      View localView = this.j;
      return localView;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  /**
   * @deprecated
   */
  public void g()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 53	com/google/ads/h:a	Lcom/google/ads/internal/h;
    //   6: invokevirtual 137	com/google/ads/internal/h:a	()Z
    //   9: invokestatic 139	com/google/ads/util/a:a	(Z)V
    //   12: aload_0
    //   13: getfield 69	com/google/ads/h:g	Lcom/google/ads/mediation/MediationAdapter;
    //   16: checkcast 141	com/google/ads/mediation/MediationInterstitialAdapter
    //   19: astore_3
    //   20: invokestatic 88	com/google/ads/m:a	()Lcom/google/ads/m;
    //   23: getfield 91	com/google/ads/m:c	Lcom/google/ads/util/i$b;
    //   26: invokevirtual 96	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   29: checkcast 98	android/os/Handler
    //   32: new 8	com/google/ads/h$2
    //   35: dup
    //   36: aload_0
    //   37: aload_3
    //   38: invokespecial 144	com/google/ads/h$2:<init>	(Lcom/google/ads/h;Lcom/google/ads/mediation/MediationInterstitialAdapter;)V
    //   41: invokevirtual 107	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   44: pop
    //   45: aload_0
    //   46: monitorexit
    //   47: return
    //   48: astore_2
    //   49: ldc 146
    //   51: aload_2
    //   52: invokestatic 151	com/google/ads/util/b:b	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   55: goto -10 -> 45
    //   58: astore_1
    //   59: aload_0
    //   60: monitorexit
    //   61: aload_1
    //   62: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	63	0	this	h
    //   58	4	1	localObject	Object
    //   48	4	2	localClassCastException	java.lang.ClassCastException
    //   19	19	3	localMediationInterstitialAdapter	MediationInterstitialAdapter
    // Exception table:
    //   from	to	target	type
    //   12	45	48	java/lang/ClassCastException
    //   2	12	58	finally
    //   12	45	58	finally
    //   49	55	58	finally
  }
  
  /* Error */
  /**
   * @deprecated
   */
  public String h()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 69	com/google/ads/h:g	Lcom/google/ads/mediation/MediationAdapter;
    //   6: ifnull +20 -> 26
    //   9: aload_0
    //   10: getfield 69	com/google/ads/h:g	Lcom/google/ads/mediation/MediationAdapter;
    //   13: invokevirtual 156	java/lang/Object:getClass	()Ljava/lang/Class;
    //   16: invokevirtual 161	java/lang/Class:getName	()Ljava/lang/String;
    //   19: astore_3
    //   20: aload_3
    //   21: astore_2
    //   22: aload_0
    //   23: monitorexit
    //   24: aload_2
    //   25: areturn
    //   26: ldc 163
    //   28: astore_2
    //   29: goto -7 -> 22
    //   32: astore_1
    //   33: aload_0
    //   34: monitorexit
    //   35: aload_1
    //   36: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	37	0	this	h
    //   32	4	1	localObject	Object
    //   21	8	2	str1	String
    //   19	2	3	str2	String
    // Exception table:
    //   from	to	target	type
    //   2	20	32	finally
    //   26	29	32	finally
  }
  
  /**
   * @deprecated
   */
  MediationAdapter<?, ?> i()
  {
    try
    {
      MediationAdapter localMediationAdapter = this.g;
      return localMediationAdapter;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  e j()
  {
    return this.f;
  }
  
  /**
   * @deprecated
   */
  void k()
  {
    try
    {
      this.i = true;
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
  boolean l()
  {
    try
    {
      boolean bool = this.i;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.h
 * JD-Core Version:    0.7.0.1
 */