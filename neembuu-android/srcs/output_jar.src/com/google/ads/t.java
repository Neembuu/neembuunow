package com.google.ads;

import android.webkit.WebView;
import com.google.ads.internal.AdWebView;
import com.google.ads.internal.d;
import com.google.ads.util.b;
import java.util.HashMap;

public class t
  implements o
{
  public void a(d paramd, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    if ((paramWebView instanceof AdWebView)) {
      ((AdWebView)paramWebView).setCustomClose("1".equals(paramHashMap.get("custom_close")));
    }
    for (;;)
    {
      return;
      b.b("Trying to set a custom close icon on a WebView that isn't an AdWebView");
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.t
 * JD-Core Version:    0.7.0.1
 */