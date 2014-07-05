package jp.co.cayto.appc.sdk.android.common;

import android.util.Log;

public final class Logger
{
  private static final String BASE_TAG = "appC";
  private static final boolean DEBUG;
  private final boolean debug;
  private final String tag;
  
  private Logger(String paramString)
  {
    this(paramString, false);
  }
  
  private Logger(String paramString, boolean paramBoolean)
  {
    this.tag = ("appC - " + paramString);
    this.debug = paramBoolean;
  }
  
  public void debug(String paramString)
  {
    if (this.debug)
    {
      if (paramString != null) {
        break label22;
      }
      Log.d(this.tag, "no message for Logger#debug()");
    }
    for (;;)
    {
      return;
      label22:
      Log.d(this.tag, paramString);
    }
  }
  
  public void error(String paramString)
  {
    if (paramString == null) {
      Log.e(this.tag, "no message for Logger#error()");
    }
    for (;;)
    {
      return;
      Log.e(this.tag, paramString);
    }
  }
  
  public void error(String paramString, Throwable paramThrowable)
  {
    if (paramString == null) {
      Log.e(this.tag, "no message for Logger#error()", paramThrowable);
    }
    for (;;)
    {
      return;
      Log.e(this.tag, paramString, paramThrowable);
    }
  }
  
  public void error(Throwable paramThrowable)
  {
    error(null, paramThrowable);
  }
  
  public void info(String paramString)
  {
    if (this.debug)
    {
      if (paramString != null) {
        break label22;
      }
      Log.i(this.tag, "no message for Logger#info()");
    }
    for (;;)
    {
      return;
      label22:
      Log.i(this.tag, paramString);
    }
  }
  
  public void verbose(String paramString)
  {
    if (this.debug)
    {
      if (paramString != null) {
        break label22;
      }
      Log.v(this.tag, "no message for Logger#verbose()");
    }
    for (;;)
    {
      return;
      label22:
      Log.v(this.tag, paramString);
    }
  }
  
  public void warn(String paramString)
  {
    if (paramString == null) {
      Log.w(this.tag, "no message for Logger#warn()");
    }
    for (;;)
    {
      return;
      Log.w(this.tag, paramString);
    }
  }
  
  public void warn(String paramString, Throwable paramThrowable)
  {
    if (paramString == null) {
      Log.w(this.tag, "no message for Logger#warn()", paramThrowable);
    }
    for (;;)
    {
      return;
      Log.w(this.tag, paramString, paramThrowable);
    }
  }
  
  public void warn(Throwable paramThrowable)
  {
    warn(null, paramThrowable);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.common.Logger
 * JD-Core Version:    0.7.0.1
 */