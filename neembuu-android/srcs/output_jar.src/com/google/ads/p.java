package com.google.ads;

import android.webkit.WebView;
import com.google.ads.internal.d;
import com.google.ads.util.b;
import java.util.HashMap;

public class p
  implements o
{
  public void a(d paramd, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    String str = (String)paramHashMap.get("name");
    if (str == null) {
      b.b("Error: App event with no name parameter.");
    }
    for (;;)
    {
      return;
      paramd.a(str, (String)paramHashMap.get("info"));
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.p
 * JD-Core Version:    0.7.0.1
 */