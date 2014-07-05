package jp.tjkapp.adfurikunsdk;

import android.util.Log;

class AdfurikunLogUtil
{
  private boolean isDebugable = true;
  
  public void debug(String paramString1, String paramString2)
  {
    if (this.isDebugable) {
      Log.d(paramString1, paramString2);
    }
  }
  
  public void debug_e(String paramString, Exception paramException)
  {
    if (this.isDebugable)
    {
      String str = paramException.getMessage();
      if (str == null) {
        str = paramException.toString();
      }
      if (str == null) {
        break label32;
      }
      Log.e(paramString, str);
    }
    for (;;)
    {
      return;
      label32:
      Log.e(paramString, "Exception is no message!");
    }
  }
  
  public void debug_e(String paramString1, String paramString2)
  {
    if (this.isDebugable) {
      Log.e(paramString1, paramString2);
    }
  }
  
  public void debug_e(String paramString1, String paramString2, Exception paramException)
  {
    StringBuffer localStringBuffer;
    if (this.isDebugable)
    {
      localStringBuffer = new StringBuffer();
      localStringBuffer.append(paramString2);
      String str = paramException.getMessage();
      if (str == null) {
        str = paramException.toString();
      }
      if (str == null) {
        break label64;
      }
      localStringBuffer.append(str);
    }
    for (;;)
    {
      Log.e(paramString1, localStringBuffer.toString());
      return;
      label64:
      localStringBuffer.append("Exception is no message!");
    }
  }
  
  public void debug_i(String paramString1, String paramString2)
  {
    if (this.isDebugable) {
      Log.i(paramString1, paramString2);
    }
  }
  
  public void setIsDebugable(boolean paramBoolean)
  {
    this.isDebugable = paramBoolean;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunLogUtil
 * JD-Core Version:    0.7.0.1
 */