package twitter4j.internal.logging;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;

public abstract class Logger
{
  private static final LoggerFactory LOGGER_FACTORY;
  private static final String LOGGER_FACTORY_IMPLEMENTATION = "twitter4j.loggerFactory";
  
  static
  {
    Object localObject = null;
    String str1 = System.getProperty("twitter4j.loggerFactory");
    if (str1 != null) {
      localObject = getLoggerFactoryIfAvailable(str1, str1);
    }
    Configuration localConfiguration = ConfigurationContext.getInstance();
    String str2 = localConfiguration.getLoggerFactory();
    if (str2 != null) {
      localObject = getLoggerFactoryIfAvailable(str2, str2);
    }
    if (localObject == null) {
      localObject = getLoggerFactoryIfAvailable("org.slf4j.impl.StaticLoggerBinder", "twitter4j.internal.logging.SLF4JLoggerFactory");
    }
    if (localObject == null) {
      localObject = getLoggerFactoryIfAvailable("org.apache.commons.logging.Log", "twitter4j.internal.logging.CommonsLoggingLoggerFactory");
    }
    if (localObject == null) {
      localObject = getLoggerFactoryIfAvailable("org.apache.log4j.Logger", "twitter4j.internal.logging.Log4JLoggerFactory");
    }
    if (localObject == null) {
      localObject = getLoggerFactoryIfAvailable("com.google.appengine.api.urlfetch.URLFetchService", "twitter4j.internal.logging.JULLoggerFactory");
    }
    if (localObject == null) {
      localObject = new StdOutLoggerFactory();
    }
    LOGGER_FACTORY = (LoggerFactory)localObject;
    try
    {
      Method localMethod = localConfiguration.getClass().getMethod("dumpConfiguration", new Class[0]);
      localMethod.setAccessible(true);
      localMethod.invoke(localConfiguration, new Object[0]);
      label135:
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      break label135;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      break label135;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      break label135;
    }
  }
  
  public static Logger getLogger(Class paramClass)
  {
    return LOGGER_FACTORY.getLogger(paramClass);
  }
  
  private static LoggerFactory getLoggerFactoryIfAvailable(String paramString1, String paramString2)
  {
    try
    {
      Class.forName(paramString1);
      localLoggerFactory = (LoggerFactory)Class.forName(paramString2).newInstance();
      return localLoggerFactory;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new AssertionError(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (SecurityException localSecurityException)
    {
      for (;;)
      {
        LoggerFactory localLoggerFactory = null;
      }
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      label44:
      break label44;
    }
  }
  
  public abstract void debug(String paramString);
  
  public abstract void debug(String paramString1, String paramString2);
  
  public abstract void error(String paramString);
  
  public abstract void error(String paramString, Throwable paramThrowable);
  
  public abstract void info(String paramString);
  
  public abstract void info(String paramString1, String paramString2);
  
  public abstract boolean isDebugEnabled();
  
  public abstract boolean isErrorEnabled();
  
  public abstract boolean isInfoEnabled();
  
  public abstract boolean isWarnEnabled();
  
  public abstract void warn(String paramString);
  
  public abstract void warn(String paramString1, String paramString2);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.logging.Logger
 * JD-Core Version:    0.7.0.1
 */