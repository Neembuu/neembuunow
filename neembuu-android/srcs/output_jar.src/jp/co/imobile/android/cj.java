package jp.co.imobile.android;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

final class cj
{
  static final Random a;
  static final ExecutorService b = Executors.newCachedThreadPool(new cl());
  private static boolean c;
  private static boolean d = true;
  private static boolean e;
  private static boolean f;
  private static final AtomicInteger g = new AtomicInteger(0);
  private static String h;
  private static final Thread.UncaughtExceptionHandler i;
  
  static
  {
    a = new Random(System.currentTimeMillis());
    h = null;
    i = new ck();
  }
  
  /**
   * @deprecated
   */
  static void a(String paramString)
  {
    try
    {
      h = paramString;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  static void a(String paramString, Throwable paramThrowable)
  {
    if (d().a("i-mobile SDK", 6)) {
      d().c("i-mobile SDK", "Unknown error" + "-" + paramString + "[params]" + " ex:" + paramThrowable.toString());
    }
  }
  
  static final void a(String paramString, bp parambp, Throwable paramThrowable, String... paramVarArgs)
  {
    d().a("i-mobile SDK", d(paramString, parambp, paramVarArgs), paramThrowable);
  }
  
  static final void a(String paramString, bp parambp, String... paramVarArgs)
  {
    if (d().a("i-mobile SDK", 6)) {
      d().c("i-mobile SDK", d(paramString, parambp, paramVarArgs));
    }
  }
  
  /**
   * @deprecated
   */
  static void a(boolean paramBoolean)
  {
    try
    {
      c = paramBoolean;
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
  static boolean a()
  {
    try
    {
      boolean bool = c;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  static final void b(String paramString, bp parambp, String... paramVarArgs)
  {
    if (d().a("i-mobile SDK", 3)) {
      d().a("i-mobile SDK", d(paramString, parambp, paramVarArgs));
    }
  }
  
  /**
   * @deprecated
   */
  static void b(boolean paramBoolean)
  {
    try
    {
      d = paramBoolean;
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
  static boolean b()
  {
    try
    {
      boolean bool = d;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  static final void c(String paramString, bp parambp, String... paramVarArgs)
  {
    if (d().a("i-mobile SDK", 4)) {
      d().b("i-mobile SDK", d(paramString, parambp, paramVarArgs));
    }
  }
  
  /**
   * @deprecated
   */
  static void c(boolean paramBoolean)
  {
    try
    {
      e = paramBoolean;
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
  static boolean c()
  {
    try
    {
      boolean bool = f;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  static final String d(String paramString, bp parambp, String... paramVarArgs)
  {
    StringBuilder localStringBuilder = new StringBuilder().append(parambp.getLogTag()).append(paramString).append("[params]").append(parambp.getLogContents());
    int j = paramVarArgs.length;
    for (int k = 0;; k++)
    {
      if (k >= j)
      {
        localStringBuilder.append(", by SDK InternalVersion:");
        localStringBuilder.append("1.4.0");
        return localStringBuilder.toString();
      }
      localStringBuilder.append(paramVarArgs[k]);
    }
  }
  
  static bq d()
  {
    if ((c) || (e)) {}
    for (bq localbq = bq.a;; localbq = bq.b) {
      return localbq;
    }
  }
  
  /**
   * @deprecated
   */
  static void d(boolean paramBoolean)
  {
    try
    {
      f = paramBoolean;
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
  static String e()
  {
    try
    {
      String str = h;
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.cj
 * JD-Core Version:    0.7.0.1
 */