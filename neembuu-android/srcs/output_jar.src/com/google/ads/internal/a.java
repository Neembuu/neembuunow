package com.google.ads.internal;

import android.net.Uri;
import android.webkit.WebView;
import com.google.ads.aa;
import com.google.ads.ab;
import com.google.ads.ac;
import com.google.ads.o;
import com.google.ads.p;
import com.google.ads.q;
import com.google.ads.r;
import com.google.ads.s;
import com.google.ads.t;
import com.google.ads.u;
import com.google.ads.util.AdUtil;
import com.google.ads.util.b;
import com.google.ads.util.f;
import com.google.ads.v;
import com.google.ads.w;
import com.google.ads.x;
import com.google.ads.y;
import com.google.ads.z;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class a
{
  public static final f<a> a = new f()
  {
    public a a()
    {
      return a.a();
    }
  };
  public static final Map<String, o> b = Collections.unmodifiableMap(new HashMap() {});
  public static final Map<String, o> c = Collections.unmodifiableMap(new HashMap() {});
  public static final Map<String, o> d = Collections.unmodifiableMap(new HashMap() {});
  private static final a e = new a();
  
  public String a(Uri paramUri, HashMap<String, String> paramHashMap)
  {
    String str1 = null;
    String str2;
    if (c(paramUri))
    {
      str2 = paramUri.getHost();
      if (str2 == null) {
        b.e("An error occurred while parsing the AMSG parameters.");
      }
    }
    for (;;)
    {
      return str1;
      if (str2.equals("launch"))
      {
        paramHashMap.put("a", "intent");
        paramHashMap.put("u", paramHashMap.get("url"));
        paramHashMap.remove("url");
        str1 = "/open";
      }
      else if (str2.equals("closecanvas"))
      {
        str1 = "/close";
      }
      else if (str2.equals("log"))
      {
        str1 = "/log";
      }
      else
      {
        b.e("An error occurred while parsing the AMSG: " + paramUri.toString());
        continue;
        if (b(paramUri)) {
          str1 = paramUri.getPath();
        } else {
          b.e("Message was neither a GMSG nor an AMSG.");
        }
      }
    }
  }
  
  public void a(WebView paramWebView)
  {
    a(paramWebView, "onshow", "{'version': 'afma-sdk-a-v6.4.1'}");
  }
  
  public void a(WebView paramWebView, String paramString)
  {
    b.a("Sending JS to a WebView: " + paramString);
    paramWebView.loadUrl("javascript:" + paramString);
  }
  
  public void a(WebView paramWebView, String paramString1, String paramString2)
  {
    if (paramString2 != null) {
      a(paramWebView, "AFMA_ReceiveMessage" + "('" + paramString1 + "', " + paramString2 + ");");
    }
    for (;;)
    {
      return;
      a(paramWebView, "AFMA_ReceiveMessage" + "('" + paramString1 + "');");
    }
  }
  
  public void a(WebView paramWebView, Map<String, Boolean> paramMap)
  {
    a(paramWebView, "openableURLs", new JSONObject(paramMap).toString());
  }
  
  public void a(d paramd, Map<String, o> paramMap, Uri paramUri, WebView paramWebView)
  {
    HashMap localHashMap = AdUtil.b(paramUri);
    if (localHashMap == null) {
      b.e("An error occurred while parsing the message parameters.");
    }
    for (;;)
    {
      return;
      String str = a(paramUri, localHashMap);
      if (str == null)
      {
        b.e("An error occurred while parsing the message.");
      }
      else
      {
        o localo = (o)paramMap.get(str);
        if (localo == null) {
          b.e("No AdResponse found, <message: " + str + ">");
        } else {
          localo.a(paramd, localHashMap, paramWebView);
        }
      }
    }
  }
  
  public boolean a(Uri paramUri)
  {
    boolean bool = false;
    if ((paramUri == null) || (!paramUri.isHierarchical())) {}
    for (;;)
    {
      return bool;
      if ((b(paramUri)) || (c(paramUri))) {
        bool = true;
      }
    }
  }
  
  public void b(WebView paramWebView)
  {
    a(paramWebView, "onhide", null);
  }
  
  public boolean b(Uri paramUri)
  {
    boolean bool = false;
    String str1 = paramUri.getScheme();
    if ((str1 == null) || (!str1.equals("gmsg"))) {}
    for (;;)
    {
      return bool;
      String str2 = paramUri.getAuthority();
      if ((str2 != null) && (str2.equals("mobileads.google.com"))) {
        bool = true;
      }
    }
  }
  
  public boolean c(Uri paramUri)
  {
    String str = paramUri.getScheme();
    if ((str == null) || (!str.equals("admob"))) {}
    for (boolean bool = false;; bool = true) {
      return bool;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.internal.a
 * JD-Core Version:    0.7.0.1
 */