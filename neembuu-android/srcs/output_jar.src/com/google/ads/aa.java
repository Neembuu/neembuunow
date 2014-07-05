package com.google.ads;

import android.text.TextUtils;
import android.webkit.WebView;
import com.google.ads.internal.ActivationOverlay;
import com.google.ads.internal.d;
import com.google.ads.util.b;
import java.util.HashMap;

public class aa
  implements o
{
  public void a(d paramd, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    int i = -1;
    if ((paramWebView instanceof ActivationOverlay)) {}
    for (;;)
    {
      try
      {
        if (TextUtils.isEmpty((CharSequence)paramHashMap.get("w"))) {
          break label264;
        }
        j = Integer.parseInt((String)paramHashMap.get("w"));
        if (TextUtils.isEmpty((CharSequence)paramHashMap.get("h"))) {
          break label257;
        }
        k = Integer.parseInt((String)paramHashMap.get("h"));
        if (TextUtils.isEmpty((CharSequence)paramHashMap.get("x"))) {
          break label250;
        }
        m = Integer.parseInt((String)paramHashMap.get("x"));
        if (!TextUtils.isEmpty((CharSequence)paramHashMap.get("y")))
        {
          int n = Integer.parseInt((String)paramHashMap.get("y"));
          i = n;
        }
        if ((paramHashMap.get("a") != null) && (((String)paramHashMap.get("a")).equals("1")))
        {
          paramd.a(null, true, m, i, j, k);
          return;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        b.d("Invalid number format in activation overlay response.", localNumberFormatException);
        continue;
        if ((paramHashMap.get("a") != null) && (((String)paramHashMap.get("a")).equals("0")))
        {
          paramd.a(null, false, m, i, j, k);
          continue;
        }
        paramd.a(m, i, j, k);
        continue;
      }
      b.b("Trying to activate an overlay when this is not an overlay.");
      continue;
      label250:
      int m = i;
      continue;
      label257:
      int k = i;
      continue;
      label264:
      int j = i;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.aa
 * JD-Core Version:    0.7.0.1
 */