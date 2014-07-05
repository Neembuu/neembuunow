package net.nend.android;

import android.util.Log;

final class NendLog
{
  static final String TAG = "nend_SDK";
  
  static int d(String paramString)
  {
    return outputLog(3, "nend_SDK", paramString, null);
  }
  
  static int d(String paramString1, String paramString2)
  {
    return outputLog(3, paramString1, paramString2, null);
  }
  
  static int d(String paramString1, String paramString2, Throwable paramThrowable)
  {
    return outputLog(3, paramString1, paramString2, paramThrowable);
  }
  
  static int d(String paramString, Throwable paramThrowable)
  {
    return outputLog(3, "nend_SDK", paramString, paramThrowable);
  }
  
  static int d(NendStatus paramNendStatus)
  {
    return outputLog(3, "nend_SDK", paramNendStatus.getMsg(), null);
  }
  
  static int d(NendStatus paramNendStatus, String paramString)
  {
    return outputLog(3, "nend_SDK", paramNendStatus.getMsg(paramString), null);
  }
  
  static int d(NendStatus paramNendStatus, Throwable paramThrowable)
  {
    return outputLog(3, "nend_SDK", paramNendStatus.getMsg(), paramThrowable);
  }
  
  static int e(String paramString)
  {
    return outputLog(6, "nend_SDK", paramString, null);
  }
  
  static int e(String paramString1, String paramString2)
  {
    return outputLog(6, paramString1, paramString2, null);
  }
  
  static int e(String paramString1, String paramString2, Throwable paramThrowable)
  {
    return outputLog(6, paramString1, paramString2, paramThrowable);
  }
  
  static int e(String paramString, Throwable paramThrowable)
  {
    return outputLog(6, "nend_SDK", paramString, paramThrowable);
  }
  
  static int e(NendStatus paramNendStatus)
  {
    return outputLog(6, "nend_SDK", paramNendStatus.getMsg(), null);
  }
  
  static int e(NendStatus paramNendStatus, String paramString)
  {
    return outputLog(6, "nend_SDK", paramNendStatus.getMsg(paramString), null);
  }
  
  static int e(NendStatus paramNendStatus, Throwable paramThrowable)
  {
    return outputLog(6, "nend_SDK", paramNendStatus.getMsg(), paramThrowable);
  }
  
  static int i(String paramString)
  {
    return outputLog(4, "nend_SDK", paramString, null);
  }
  
  static int i(String paramString1, String paramString2)
  {
    return outputLog(4, paramString1, paramString2, null);
  }
  
  static int i(String paramString1, String paramString2, Throwable paramThrowable)
  {
    return outputLog(4, paramString1, paramString2, paramThrowable);
  }
  
  static int i(String paramString, Throwable paramThrowable)
  {
    return outputLog(4, "nend_SDK", paramString, paramThrowable);
  }
  
  static int i(NendStatus paramNendStatus)
  {
    return outputLog(4, "nend_SDK", paramNendStatus.getMsg(), null);
  }
  
  static int i(NendStatus paramNendStatus, String paramString)
  {
    return outputLog(4, "nend_SDK", paramNendStatus.getMsg(paramString), null);
  }
  
  static int i(NendStatus paramNendStatus, Throwable paramThrowable)
  {
    return outputLog(4, "nend_SDK", paramNendStatus.getMsg(), paramThrowable);
  }
  
  private static boolean isLoggable(String paramString, int paramInt)
  {
    boolean bool = true;
    switch (paramInt)
    {
    default: 
      if ((!Log.isLoggable(paramString, paramInt)) || (!NendHelper.isDebuggable())) {
        break;
      }
    }
    for (;;)
    {
      return bool;
      if ((!Log.isLoggable(paramString, paramInt)) || (!NendHelper.isDebuggable()) || (!NendHelper.isDev()))
      {
        bool = false;
        continue;
        bool = false;
      }
    }
  }
  
  private static int outputLog(int paramInt, String paramString1, String paramString2, Throwable paramThrowable)
  {
    String str;
    if (isLoggable(paramString1, paramInt))
    {
      StackTraceElement localStackTraceElement = java.lang.Thread.currentThread().getStackTrace()[4];
      str = localStackTraceElement.getClassName() + "." + localStackTraceElement.getMethodName() + ":\n" + paramString2;
      if (paramThrowable != null) {
        str = str + '\n' + Log.getStackTraceString(paramThrowable);
      }
    }
    for (int i = Log.println(paramInt, paramString1, str);; i = 0) {
      return i;
    }
  }
  
  static int v(String paramString)
  {
    return outputLog(2, "nend_SDK", paramString, null);
  }
  
  static int v(String paramString1, String paramString2)
  {
    return outputLog(2, paramString1, paramString2, null);
  }
  
  static int v(String paramString1, String paramString2, Throwable paramThrowable)
  {
    return outputLog(2, paramString1, paramString2, paramThrowable);
  }
  
  static int v(String paramString, Throwable paramThrowable)
  {
    return outputLog(2, "nend_SDK", paramString, paramThrowable);
  }
  
  static int v(NendStatus paramNendStatus)
  {
    return outputLog(2, "nend_SDK", paramNendStatus.getMsg(), null);
  }
  
  static int v(NendStatus paramNendStatus, String paramString)
  {
    return outputLog(2, "nend_SDK", paramNendStatus.getMsg(paramString), null);
  }
  
  static int v(NendStatus paramNendStatus, Throwable paramThrowable)
  {
    return outputLog(2, "nend_SDK", paramNendStatus.getMsg(), paramThrowable);
  }
  
  static int w(String paramString)
  {
    return outputLog(5, "nend_SDK", paramString, null);
  }
  
  static int w(String paramString1, String paramString2)
  {
    return outputLog(5, paramString1, paramString2, null);
  }
  
  static int w(String paramString1, String paramString2, Throwable paramThrowable)
  {
    return outputLog(5, paramString1, paramString2, paramThrowable);
  }
  
  static int w(String paramString, Throwable paramThrowable)
  {
    return outputLog(5, "nend_SDK", paramString, paramThrowable);
  }
  
  static int w(NendStatus paramNendStatus)
  {
    return outputLog(5, "nend_SDK", paramNendStatus.getMsg(), null);
  }
  
  static int w(NendStatus paramNendStatus, String paramString)
  {
    return outputLog(5, "nend_SDK", paramNendStatus.getMsg(paramString), null);
  }
  
  static int w(NendStatus paramNendStatus, Throwable paramThrowable)
  {
    return outputLog(5, "nend_SDK", paramNendStatus.getMsg(), paramThrowable);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendLog
 * JD-Core Version:    0.7.0.1
 */