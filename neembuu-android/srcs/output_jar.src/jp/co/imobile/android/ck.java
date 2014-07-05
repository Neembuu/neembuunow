package jp.co.imobile.android;

final class ck
  implements Thread.UncaughtExceptionHandler
{
  public final void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    cj.d().a("AdControllerBase Thread", "thread uncaught exception" + "[params]thread name:" + paramThread.getName(), paramThrowable);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.ck
 * JD-Core Version:    0.7.0.1
 */