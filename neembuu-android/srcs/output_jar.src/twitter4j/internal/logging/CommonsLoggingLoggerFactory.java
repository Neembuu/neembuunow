package twitter4j.internal.logging;

import org.apache.commons.logging.LogFactory;

final class CommonsLoggingLoggerFactory
  extends LoggerFactory
{
  public Logger getLogger(Class paramClass)
  {
    return new CommonsLoggingLogger(LogFactory.getLog(paramClass));
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.logging.CommonsLoggingLoggerFactory
 * JD-Core Version:    0.7.0.1
 */