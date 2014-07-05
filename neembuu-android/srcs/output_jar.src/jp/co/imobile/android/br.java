package jp.co.imobile.android;

import android.util.Log;

 enum br
{
  br() {}
  
  final int a(String paramString1, String paramString2)
  {
    return Log.d(paramString1, paramString2);
  }
  
  final int a(String paramString1, String paramString2, Throwable paramThrowable)
  {
    return Log.e(paramString1, paramString2, paramThrowable);
  }
  
  final boolean a(String paramString, int paramInt)
  {
    return true;
  }
  
  final int b(String paramString1, String paramString2)
  {
    return Log.i(paramString1, paramString2);
  }
  
  final int c(String paramString1, String paramString2)
  {
    return Log.e(paramString1, paramString2);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.br
 * JD-Core Version:    0.7.0.1
 */