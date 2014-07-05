package com.google.ads;

import android.webkit.WebView;
import com.google.ads.internal.AdWebView;
import com.google.ads.internal.d;
import com.google.ads.util.b;
import java.util.HashMap;

public class s
  implements o
{
  public void a(d paramd, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    if ((paramWebView instanceof AdWebView)) {
      ((AdWebView)paramWebView).f();
    }
    for (;;)
    {
      return;
      b.b("Trying to close WebView that isn't an AdWebView");
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.s
 * JD-Core Version:    0.7.0.1
 */