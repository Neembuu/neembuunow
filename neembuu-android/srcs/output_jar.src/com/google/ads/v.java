package com.google.ads;

import android.webkit.WebView;
import com.google.ads.internal.c;
import com.google.ads.internal.d;
import com.google.ads.util.b;
import java.util.HashMap;

public class v
  implements o
{
  public void a(d paramd, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    String str1 = (String)paramHashMap.get("type");
    String str2 = (String)paramHashMap.get("errors");
    b.e("Invalid " + str1 + " request error: " + str2);
    c localc = paramd.k();
    if (localc != null) {
      localc.a(AdRequest.ErrorCode.INVALID_REQUEST);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.v
 * JD-Core Version:    0.7.0.1
 */