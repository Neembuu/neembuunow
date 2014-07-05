package twitter4j.internal.logging;

final class SLF4JLoggerFactory
  extends LoggerFactory
{
  public Logger getLogger(Class paramClass)
  {
    return new SLF4JLogger(org.slf4j.LoggerFactory.getLogger(paramClass));
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.logging.SLF4JLoggerFactory
 * JD-Core Version:    0.7.0.1
 */