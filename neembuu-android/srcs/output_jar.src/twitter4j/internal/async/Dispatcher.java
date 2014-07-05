package twitter4j.internal.async;

public abstract interface Dispatcher
{
  public abstract void invokeLater(Runnable paramRunnable);
  
  public abstract void shutdown();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.async.Dispatcher
 * JD-Core Version:    0.7.0.1
 */