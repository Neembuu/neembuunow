package com.google.ads;

import android.app.Activity;
import android.webkit.WebView;
import com.google.ads.internal.d;
import com.google.ads.util.b;
import com.google.ads.util.i.c;
import com.google.ads.util.i.d;
import java.util.HashMap;

public class ab
  implements o
{
  public void a(d paramd, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    if ((Activity)paramd.i().c.a() == null) {
      b.e("Activity was null while responding to touch gmsg.");
    }
    for (;;)
    {
      return;
      String str1 = (String)paramHashMap.get("tx");
      String str2 = (String)paramHashMap.get("ty");
      String str3 = (String)paramHashMap.get("td");
      try
      {
        int i = Integer.parseInt(str1);
        int j = Integer.parseInt(str2);
        int k = Integer.parseInt(str3);
        ak localak = (ak)paramd.i().r.a();
        if (localak != null) {
          localak.a(i, j, k);
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        b.e("Could not parse touch parameters from gmsg.");
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.ab
 * JD-Core Version:    0.7.0.1
 */