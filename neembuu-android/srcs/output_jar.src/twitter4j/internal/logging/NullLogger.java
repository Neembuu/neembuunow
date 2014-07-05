package twitter4j.internal.logging;

final class NullLogger
  extends Logger
{
  public void debug(String paramString) {}
  
  public void debug(String paramString1, String paramString2) {}
  
  public void error(String paramString) {}
  
  public void error(String paramString, Throwable paramThrowable) {}
  
  public void info(String paramString) {}
  
  public void info(String paramString1, String paramString2) {}
  
  public boolean isDebugEnabled()
  {
    return false;
  }
  
  public boolean isErrorEnabled()
  {
    return false;
  }
  
  public boolean isInfoEnabled()
  {
    return false;
  }
  
  public boolean isWarnEnabled()
  {
    return false;
  }
  
  public void warn(String paramString) {}
  
  public void warn(String paramString1, String paramString2) {}
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.logging.NullLogger
 * JD-Core Version:    0.7.0.1
 */