package twitter4j.internal.logging;

final class NullLoggerFactory
  extends LoggerFactory
{
  private static final Logger SINGLETON = new NullLogger();
  
  public Logger getLogger(Class paramClass)
  {
    return SINGLETON;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.logging.NullLoggerFactory
 * JD-Core Version:    0.7.0.1
 */