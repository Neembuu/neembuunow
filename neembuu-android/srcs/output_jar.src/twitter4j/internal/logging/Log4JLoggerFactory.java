package twitter4j.internal.logging;

final class Log4JLoggerFactory
  extends LoggerFactory
{
  public Logger getLogger(Class paramClass)
  {
    return new Log4JLogger(org.apache.log4j.Logger.getLogger(paramClass));
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.logging.Log4JLoggerFactory
 * JD-Core Version:    0.7.0.1
 */