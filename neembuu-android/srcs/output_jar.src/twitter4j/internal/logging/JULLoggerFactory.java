package twitter4j.internal.logging;

final class JULLoggerFactory
  extends LoggerFactory
{
  public Logger getLogger(Class paramClass)
  {
    return new JULLogger(java.util.logging.Logger.getLogger(paramClass.getName()));
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.logging.JULLoggerFactory
 * JD-Core Version:    0.7.0.1
 */