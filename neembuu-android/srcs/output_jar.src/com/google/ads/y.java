package com.google.ads;

import android.webkit.WebView;
import com.google.ads.internal.d;
import com.google.ads.util.b;
import java.util.HashMap;

public class y
  implements o
{
  public void a(d paramd, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    String str1 = (String)paramHashMap.get("string");
    String str2 = (String)paramHashMap.get("afma_notify_dt");
    b.c("Received log message: <\"string\": \"" + str1 + "\", \"afmaNotifyDt\": \"" + str2 + "\">");
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.y
 * JD-Core Version:    0.7.0.1
 */