package jp.adlantis.android.utils;

import android.net.Uri;

public class ADLStringUtils
{
  public static boolean isHttpUrl(String paramString)
  {
    boolean bool = false;
    if (paramString == null) {}
    for (;;)
    {
      return bool;
      String str = Uri.parse(paramString).getScheme();
      if (("http".equalsIgnoreCase(str)) || ("https".equalsIgnoreCase(str))) {
        bool = true;
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.utils.ADLStringUtils
 * JD-Core Version:    0.7.0.1
 */