package jp.co.imobile.android;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

final class cl
  implements ThreadFactory
{
  public final Thread newThread(Runnable paramRunnable)
  {
    Thread localThread = new Thread(paramRunnable, "i-mobile SDK #" + cj.f().incrementAndGet());
    localThread.setUncaughtExceptionHandler(cj.g());
    return localThread;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.cl
 * JD-Core Version:    0.7.0.1
 */