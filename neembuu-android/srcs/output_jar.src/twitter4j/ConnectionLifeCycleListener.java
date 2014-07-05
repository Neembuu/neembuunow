package twitter4j;

public abstract interface ConnectionLifeCycleListener
{
  public abstract void onCleanUp();
  
  public abstract void onConnect();
  
  public abstract void onDisconnect();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.ConnectionLifeCycleListener
 * JD-Core Version:    0.7.0.1
 */