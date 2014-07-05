package com.google.ads;

import android.content.Context;
import android.net.Uri;
import android.webkit.WebView;
import com.google.ads.internal.d;
import com.google.ads.internal.g;
import com.google.ads.util.b;
import com.google.ads.util.i.b;
import com.google.ads.util.i.c;
import java.util.HashMap;
import java.util.Locale;

public class r
  implements o
{
  public void a(d paramd, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    str1 = (String)paramHashMap.get("u");
    if (str1 == null) {
      b.e("Could not get URL from click gmsg.");
    }
    for (;;)
    {
      return;
      g localg = paramd.n();
      if (localg != null)
      {
        Uri localUri4 = Uri.parse(str1);
        String str2 = localUri4.getHost();
        if ((str2 != null) && (str2.toLowerCase(Locale.US).endsWith(".admob.com")))
        {
          String str3 = null;
          String str4 = localUri4.getPath();
          if (str4 != null)
          {
            String[] arrayOfString = str4.split("/");
            if (arrayOfString.length >= 4) {
              str3 = arrayOfString[2] + "/" + arrayOfString[3];
            }
          }
          localg.a(str3);
        }
      }
      n localn = paramd.i();
      Context localContext = (Context)localn.f.a();
      localUri1 = Uri.parse(str1);
      try
      {
        al localal = (al)localn.s.a();
        if ((localal == null) || (!localal.a(localUri1))) {
          break;
        }
        Uri localUri3 = localal.a(localUri1, localContext);
        localUri2 = localUri3;
      }
      catch (am localam)
      {
        for (;;)
        {
          b.e("Unable to append parameter to URL: " + str1);
          Uri localUri2 = localUri1;
        }
      }
      new Thread(new ae(localUri2.toString(), localContext)).start();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.r
 * JD-Core Version:    0.7.0.1
 */