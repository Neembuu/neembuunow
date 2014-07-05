package jp.co.imobile.android;

import android.content.Context;
import android.util.SparseArray;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicBoolean;

final class l
{
  private static SparseArray a = new SparseArray();
  
  static final a a(Context paramContext)
  {
    return new m(paramContext);
  }
  
  /**
   * @deprecated
   */
  static a a(Context paramContext, int paramInt1, int paramInt2, int paramInt3)
  {
    for (;;)
    {
      a locala;
      try
      {
        WeakReference localWeakReference = (WeakReference)a.get(paramInt3);
        if (localWeakReference == null)
        {
          locala = new a(paramContext, paramInt1, paramInt2, paramInt3);
          a.put(paramInt3, new WeakReference(locala));
          a("controller first created", paramInt3);
          return locala;
        }
        locala = (a)localWeakReference.get();
        if (locala == null)
        {
          locala = new a(paramContext, paramInt1, paramInt2, paramInt3);
          a.put(paramInt3, new WeakReference(locala));
          a("controller weak reference gc clear", paramInt3);
          continue;
        }
        a("controller pooling in weak reference", paramInt3);
      }
      finally {}
      locala.b.compareAndSet(false, true);
      locala.a.a(false);
      locala.a(paramContext);
    }
  }
  
  private static final void a(String paramString, int paramInt)
  {
    cj.d().b("i-mobile SDK", new StringBuilder().append("(IM)AdControllerFactory:").append(paramString).append("[params]").append(new StringBuilder(" spotId:").append(String.valueOf(paramInt)).toString()).toString() + ", by SDK InternalVersion:" + "1.4.0");
  }
  
  /**
   * @deprecated
   */
  static a b(Context paramContext, int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      a locala = a(paramContext, paramInt1, paramInt2, paramInt3);
      locala.a.a(true);
      return locala;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.l
 * JD-Core Version:    0.7.0.1
 */