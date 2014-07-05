package com.google.ads;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.WebView;
import com.google.ads.internal.c;
import com.google.ads.internal.c.d;
import com.google.ads.internal.d;
import com.google.ads.util.AdUtil;
import com.google.ads.util.b;
import com.google.ads.util.i.c;
import com.google.ads.util.i.d;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Locale;

public class w
  implements o
{
  public void a(d paramd, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    String str1 = (String)paramHashMap.get("url");
    String str2 = (String)paramHashMap.get("type");
    String str3 = (String)paramHashMap.get("afma_notify_dt");
    String str4 = (String)paramHashMap.get("activation_overlay_url");
    String str5 = (String)paramHashMap.get("check_packages");
    boolean bool1 = "1".equals(paramHashMap.get("drt_include"));
    String str6 = (String)paramHashMap.get("request_scenario");
    boolean bool2 = "1".equals(paramHashMap.get("use_webview_loadurl"));
    c.d locald;
    if (c.d.d.e.equals(str6)) {
      locald = c.d.d;
    }
    BigInteger localBigInteger2;
    for (;;)
    {
      b.c("Received ad url: <url: \"" + str1 + "\" type: \"" + str2 + "\" afmaNotifyDt: \"" + str3 + "\" activationOverlayUrl: \"" + str4 + "\" useWebViewLoadUrl: \"" + bool2 + "\">");
      if ((TextUtils.isEmpty(str5)) || (TextUtils.isEmpty(str1))) {
        break label395;
      }
      BigInteger localBigInteger1 = new BigInteger(new byte[1]);
      String[] arrayOfString = str5.split(",");
      int i = 0;
      localBigInteger2 = localBigInteger1;
      while (i < arrayOfString.length)
      {
        if (AdUtil.a((Context)paramd.i().c.a(), arrayOfString[i])) {
          localBigInteger2 = localBigInteger2.setBit(i);
        }
        i++;
      }
      if (c.d.c.e.equals(str6)) {
        locald = c.d.c;
      } else if (c.d.a.e.equals(str6)) {
        locald = c.d.a;
      } else {
        locald = c.d.b;
      }
    }
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = localBigInteger2;
    String str7 = String.format(localLocale, "%X", arrayOfObject);
    str1 = str1.replaceAll("%40installed_markets%40", str7);
    m.a().a.a(str7);
    b.c("Ad url modified to " + str1);
    label395:
    c localc = paramd.k();
    if (localc != null)
    {
      localc.d(bool1);
      localc.a(locald);
      localc.e(bool2);
      localc.e(str4);
      localc.d(str1);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.w
 * JD-Core Version:    0.7.0.1
 */