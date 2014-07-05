package com.google.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.ak;
import com.google.ads.al;
import com.google.ads.l;
import com.google.ads.m;
import com.google.ads.m.a;
import com.google.ads.n;
import com.google.ads.searchads.SearchAdRequest;
import com.google.ads.util.AdUtil;
import com.google.ads.util.AdUtil.a;
import com.google.ads.util.i.b;
import com.google.ads.util.i.c;
import com.google.ads.util.i.d;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;

public class c
  implements Runnable
{
  boolean a;
  private String b;
  private String c;
  private String d;
  private String e;
  private boolean f;
  private f g;
  private AdRequest h;
  private WebView i;
  private l j;
  private String k;
  private String l;
  private LinkedList<String> m;
  private String n;
  private AdSize o;
  private boolean p = false;
  private volatile boolean q;
  private boolean r;
  private AdRequest.ErrorCode s;
  private boolean t;
  private int u;
  private Thread v;
  private boolean w;
  private d x = d.b;
  
  protected c() {}
  
  public c(l paraml)
  {
    this.j = paraml;
    this.k = null;
    this.b = null;
    this.c = null;
    this.d = null;
    this.m = new LinkedList();
    this.s = null;
    this.t = false;
    this.u = -1;
    this.f = false;
    this.r = false;
    this.n = null;
    this.o = null;
    if ((Activity)((n)paraml.a.a()).c.a() != null)
    {
      this.i = new AdWebView((n)paraml.a.a(), null);
      this.i.setWebViewClient(i.a((d)((n)paraml.a.a()).b.a(), a.b, false, false));
      this.i.setVisibility(8);
      this.i.setWillNotDraw(true);
      this.g = new f(paraml);
    }
    for (;;)
    {
      return;
      this.i = null;
      this.g = null;
      com.google.ads.util.b.e("activity was null while trying to create an AdLoader.");
    }
  }
  
  static void a(String paramString, com.google.ads.c paramc, com.google.ads.d paramd)
  {
    if (paramString == null) {}
    for (;;)
    {
      return;
      if ((!paramString.contains("no-store")) && (!paramString.contains("no-cache")))
      {
        Matcher localMatcher = Pattern.compile("max-age\\s*=\\s*(\\d+)").matcher(paramString);
        if (localMatcher.find()) {
          try
          {
            int i1 = Integer.parseInt(localMatcher.group(1));
            paramd.a(paramc, i1);
            Locale localLocale = Locale.US;
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Integer.valueOf(i1);
            com.google.ads.util.b.c(String.format(localLocale, "Caching gWhirl configuration for: %d seconds", arrayOfObject));
          }
          catch (NumberFormatException localNumberFormatException)
          {
            com.google.ads.util.b.b("Caught exception trying to parse cache control directive. Overflow?", localNumberFormatException);
          }
        } else {
          com.google.ads.util.b.c("Unrecognized cacheControlDirective: '" + paramString + "'. Not caching configuration.");
        }
      }
    }
  }
  
  private void b(String paramString1, String paramString2)
  {
    ((Handler)m.a().c.a()).post(new c(this.i, paramString2, paramString1));
  }
  
  private String d()
  {
    if ((this.h instanceof SearchAdRequest)) {}
    for (String str = "AFMA_buildAdURL";; str = "AFMA_buildAdURL") {
      return str;
    }
  }
  
  private String e()
  {
    if ((this.h instanceof SearchAdRequest)) {}
    for (String str = "AFMA_getSdkConstants();";; str = "AFMA_getSdkConstants();") {
      return str;
    }
  }
  
  private String f()
  {
    if ((this.h instanceof SearchAdRequest)) {}
    for (String str = "http://www.gstatic.com/safa/";; str = "http://media.admob.com/") {
      return str;
    }
  }
  
  private String g()
  {
    if ((this.h instanceof SearchAdRequest)) {}
    for (String str = "<html><head><script src=\"http://www.gstatic.com/safa/sdk-core-v40.js\"></script><script>";; str = "<html><head><script src=\"http://media.admob.com/sdk-core-v40.js\"></script><script>") {
      return str;
    }
  }
  
  private String h()
  {
    if ((this.h instanceof SearchAdRequest)) {}
    for (String str = "</script></head><body></body></html>";; str = "</script></head><body></body></html>") {
      return str;
    }
  }
  
  private void i()
  {
    AdWebView localAdWebView = ((d)((n)this.j.a.a()).b.a()).l();
    ((d)((n)this.j.a.a()).b.a()).m().c(true);
    ((d)((n)this.j.a.a()).b.a()).n().h();
    ((Handler)m.a().c.a()).post(new c(localAdWebView, this.b, this.c));
  }
  
  private void j()
  {
    ((Handler)m.a().c.a()).post(new e((d)((n)this.j.a.a()).b.a(), this.i, this.m, this.u, this.r, this.n, this.o));
  }
  
  public String a(Map<String, Object> paramMap, Activity paramActivity)
    throws c.b
  {
    int i1 = 0;
    Context localContext = paramActivity.getApplicationContext();
    g localg = ((d)((n)this.j.a.a()).b.a()).n();
    long l1 = localg.m();
    if (l1 > 0L) {
      paramMap.put("prl", Long.valueOf(l1));
    }
    long l2 = localg.n();
    if (l2 > 0L) {
      paramMap.put("prnl", Long.valueOf(l2));
    }
    String str1 = localg.l();
    if (str1 != null) {
      paramMap.put("ppcl", str1);
    }
    String str2 = localg.k();
    if (str2 != null) {
      paramMap.put("pcl", str2);
    }
    long l3 = localg.j();
    if (l3 > 0L) {
      paramMap.put("pcc", Long.valueOf(l3));
    }
    paramMap.put("preqs", Long.valueOf(localg.o()));
    paramMap.put("oar", Long.valueOf(localg.p()));
    paramMap.put("bas_on", Long.valueOf(localg.s()));
    paramMap.put("bas_off", Long.valueOf(localg.v()));
    if (localg.y()) {
      paramMap.put("aoi_timeout", "true");
    }
    if (localg.A()) {
      paramMap.put("aoi_nofill", "true");
    }
    String str3 = localg.D();
    if (str3 != null) {
      paramMap.put("pit", str3);
    }
    paramMap.put("ptime", Long.valueOf(g.E()));
    localg.a();
    localg.i();
    String str4;
    if (((n)this.j.a.a()).b())
    {
      paramMap.put("format", "interstitial_mb");
      paramMap.put("slotname", ((n)this.j.a.a()).h.a());
      paramMap.put("js", "afma-sdk-a-v6.4.1");
      str4 = localContext.getPackageName();
    }
    for (;;)
    {
      StringBuilder localStringBuilder;
      try
      {
        PackageInfo localPackageInfo = localContext.getPackageManager().getPackageInfo(str4, 0);
        int i2 = localPackageInfo.versionCode;
        String str5 = AdUtil.f(localContext);
        if (!TextUtils.isEmpty(str5)) {
          paramMap.put("mv", str5);
        }
        String str6 = (String)m.a().a.a();
        if (!TextUtils.isEmpty(str6)) {
          paramMap.put("imbf", str6);
        }
        paramMap.put("msid", localContext.getPackageName());
        paramMap.put("app_name", i2 + ".android." + localContext.getPackageName());
        paramMap.put("isu", AdUtil.a(localContext));
        String str7 = AdUtil.d(localContext);
        if (str7 == null) {
          str7 = "null";
        }
        paramMap.put("net", str7);
        String str8 = AdUtil.e(localContext);
        if ((str8 != null) && (str8.length() != 0)) {
          paramMap.put("cap", str8);
        }
        paramMap.put("u_audio", Integer.valueOf(AdUtil.g(localContext).ordinal()));
        DisplayMetrics localDisplayMetrics1 = AdUtil.a(paramActivity);
        paramMap.put("u_sd", Float.valueOf(localDisplayMetrics1.density));
        paramMap.put("u_h", Integer.valueOf(AdUtil.a(localContext, localDisplayMetrics1)));
        paramMap.put("u_w", Integer.valueOf(AdUtil.b(localContext, localDisplayMetrics1)));
        paramMap.put("hl", Locale.getDefault().getLanguage());
        n localn = (n)this.j.a.a();
        ak localak = (ak)localn.r.a();
        if (localak == null)
        {
          localak = ak.a("afma-sdk-a-v6.4.1", paramActivity);
          localn.r.a(localak);
          localn.s.a(new al(localak));
        }
        paramMap.put("ms", localak.a(localContext));
        if ((((n)this.j.a.a()).j != null) && (((n)this.j.a.a()).j.a() != null))
        {
          AdView localAdView = (AdView)((n)this.j.a.a()).j.a();
          if (localAdView.getParent() != null)
          {
            int[] arrayOfInt = new int[2];
            localAdView.getLocationOnScreen(arrayOfInt);
            int i4 = arrayOfInt[0];
            int i5 = arrayOfInt[1];
            DisplayMetrics localDisplayMetrics2 = ((Context)((n)this.j.a.a()).f.a()).getResources().getDisplayMetrics();
            int i6 = localDisplayMetrics2.widthPixels;
            int i7 = localDisplayMetrics2.heightPixels;
            if ((!localAdView.isShown()) || (i4 + localAdView.getWidth() <= 0) || (i5 + localAdView.getHeight() <= 0) || (i4 > i6) || (i5 > i7)) {
              break label1817;
            }
            i8 = 1;
            HashMap localHashMap2 = new HashMap();
            localHashMap2.put("x", Integer.valueOf(i4));
            localHashMap2.put("y", Integer.valueOf(i5));
            localHashMap2.put("width", Integer.valueOf(localAdView.getWidth()));
            localHashMap2.put("height", Integer.valueOf(localAdView.getHeight()));
            localHashMap2.put("visible", Integer.valueOf(i8));
            paramMap.put("ad_pos", localHashMap2);
          }
        }
        localStringBuilder = new StringBuilder();
        AdSize[] arrayOfAdSize = (AdSize[])((n)this.j.a.a()).n.a();
        if (arrayOfAdSize == null) {
          break label1410;
        }
        int i3 = arrayOfAdSize.length;
        if (i1 < i3)
        {
          AdSize localAdSize2 = arrayOfAdSize[i1];
          if (localStringBuilder.length() != 0) {
            localStringBuilder.append("|");
          }
          localStringBuilder.append(localAdSize2.getWidth() + "x" + localAdSize2.getHeight());
          i1++;
          continue;
          AdSize localAdSize1 = ((h)((n)this.j.a.a()).g.a()).c();
          if (localAdSize1.isFullWidth()) {
            paramMap.put("smart_w", "full");
          }
          if (localAdSize1.isAutoHeight()) {
            paramMap.put("smart_h", "auto");
          }
          if (!localAdSize1.isCustomAdSize())
          {
            paramMap.put("format", localAdSize1.toString());
            break;
          }
          HashMap localHashMap1 = new HashMap();
          localHashMap1.put("w", Integer.valueOf(localAdSize1.getWidth()));
          localHashMap1.put("h", Integer.valueOf(localAdSize1.getHeight()));
          paramMap.put("ad_frame", localHashMap1);
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        throw new b("NameNotFoundException");
      }
      paramMap.put("sz", localStringBuilder.toString());
      label1410:
      TelephonyManager localTelephonyManager = (TelephonyManager)localContext.getSystemService("phone");
      String str9 = localTelephonyManager.getNetworkOperator();
      if (!TextUtils.isEmpty(str9)) {
        paramMap.put("carrier", str9);
      }
      paramMap.put("pt", Integer.valueOf(localTelephonyManager.getPhoneType()));
      paramMap.put("gnt", Integer.valueOf(localTelephonyManager.getNetworkType()));
      if (AdUtil.c()) {
        paramMap.put("simulator", Integer.valueOf(1));
      }
      paramMap.put("session_id", com.google.ads.b.a().b().toString());
      paramMap.put("seq_num", com.google.ads.b.a().c().toString());
      if (((h)((n)this.j.a.a()).g.a()).b()) {
        paramMap.put("swipeable", Integer.valueOf(1));
      }
      if (((Boolean)((n)this.j.a.a()).t.a()).booleanValue()) {
        paramMap.put("d_imp_hdr", Integer.valueOf(1));
      }
      String str10 = AdUtil.a(paramMap);
      if (((Boolean)((m.a)((m)((n)this.j.a.a()).d.a()).b.a()).o.a()).booleanValue()) {}
      for (String str11 = g() + d() + "(" + str10 + ");" + h();; str11 = g() + e() + d() + "(" + str10 + ");" + h())
      {
        com.google.ads.util.b.c("adRequestUrlHtml: " + str11);
        return str11;
      }
      label1817:
      int i8 = 0;
    }
  }
  
  protected void a()
  {
    com.google.ads.util.b.a("AdLoader cancelled.");
    if (this.i != null)
    {
      this.i.stopLoading();
      this.i.destroy();
    }
    if (this.v != null)
    {
      this.v.interrupt();
      this.v = null;
    }
    if (this.g != null) {
      this.g.a();
    }
    this.q = true;
  }
  
  /**
   * @deprecated
   */
  public void a(int paramInt)
  {
    try
    {
      this.u = paramInt;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public void a(AdRequest.ErrorCode paramErrorCode)
  {
    try
    {
      this.s = paramErrorCode;
      notify();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  protected void a(AdRequest.ErrorCode paramErrorCode, boolean paramBoolean)
  {
    ((Handler)m.a().c.a()).post(new a((d)((n)this.j.a.a()).b.a(), this.i, this.g, paramErrorCode, paramBoolean));
  }
  
  protected void a(AdRequest paramAdRequest)
  {
    this.h = paramAdRequest;
    this.q = false;
    this.v = new Thread(this);
    this.v.start();
  }
  
  /**
   * @deprecated
   */
  public void a(AdSize paramAdSize)
  {
    try
    {
      this.o = paramAdSize;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public void a(d paramd)
  {
    try
    {
      this.x = paramd;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  protected void a(String paramString)
  {
    try
    {
      this.m.add(paramString);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  protected void a(String paramString1, String paramString2)
  {
    try
    {
      this.b = paramString2;
      this.c = paramString1;
      notify();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public void a(boolean paramBoolean)
  {
    try
    {
      this.p = paramBoolean;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  protected void b()
  {
    try
    {
      if (TextUtils.isEmpty(this.e))
      {
        com.google.ads.util.b.b("Got a mediation response with no content type. Aborting mediation.");
        a(AdRequest.ErrorCode.INTERNAL_ERROR, false);
      }
      else if (!this.e.startsWith("application/json"))
      {
        com.google.ads.util.b.b("Got a mediation response with a content type: '" + this.e + "'. Expected something starting with 'application/json'. Aborting mediation.");
        a(AdRequest.ErrorCode.INTERNAL_ERROR, false);
      }
    }
    catch (JSONException localJSONException)
    {
      com.google.ads.util.b.b("AdLoader can't parse gWhirl server configuration.", localJSONException);
      a(AdRequest.ErrorCode.INTERNAL_ERROR, false);
    }
    final com.google.ads.c localc = com.google.ads.c.a(this.c);
    a(this.d, localc, ((d)((n)this.j.a.a()).b.a()).j());
    ((Handler)m.a().c.a()).post(new Runnable()
    {
      public void run()
      {
        if (c.d(c.this) != null)
        {
          c.d(c.this).stopLoading();
          c.d(c.this).destroy();
        }
        ((d)((n)c.b(c.this).a.a()).b.a()).a(c.e(c.this));
        if (c.f(c.this) != null) {
          ((h)((n)c.b(c.this).a.a()).g.a()).b(c.f(c.this));
        }
        ((d)((n)c.b(c.this).a.a()).b.a()).a(localc);
      }
    });
  }
  
  /**
   * @deprecated
   */
  protected void b(String paramString)
  {
    try
    {
      this.e = paramString;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  protected void b(boolean paramBoolean)
  {
    try
    {
      this.f = paramBoolean;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  protected void c()
  {
    try
    {
      this.t = true;
      notify();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  protected void c(String paramString)
  {
    try
    {
      this.d = paramString;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public void c(boolean paramBoolean)
  {
    try
    {
      this.r = paramBoolean;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public void d(String paramString)
  {
    try
    {
      this.k = paramString;
      notify();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public void d(boolean paramBoolean)
  {
    try
    {
      this.w = paramBoolean;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public void e(String paramString)
  {
    try
    {
      this.l = paramString;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public void e(boolean paramBoolean)
  {
    try
    {
      this.a = paramBoolean;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public void f(String paramString)
  {
    try
    {
      this.n = paramString;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 126	com/google/ads/internal/c:i	Landroid/webkit/WebView;
    //   6: ifnull +10 -> 16
    //   9: aload_0
    //   10: getfield 160	com/google/ads/internal/c:g	Lcom/google/ads/internal/f;
    //   13: ifnonnull +20 -> 33
    //   16: ldc_w 807
    //   19: invokestatic 167	com/google/ads/util/b:e	(Ljava/lang/String;)V
    //   22: aload_0
    //   23: getstatic 769	com/google/ads/AdRequest$ErrorCode:INTERNAL_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   26: iconst_0
    //   27: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   30: aload_0
    //   31: monitorexit
    //   32: return
    //   33: aload_0
    //   34: getfield 72	com/google/ads/internal/c:j	Lcom/google/ads/l;
    //   37: getfield 104	com/google/ads/l:a	Lcom/google/ads/util/i$b;
    //   40: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   43: checkcast 111	com/google/ads/n
    //   46: getfield 114	com/google/ads/n:c	Lcom/google/ads/util/i$d;
    //   49: invokevirtual 117	com/google/ads/util/i$d:a	()Ljava/lang/Object;
    //   52: checkcast 119	android/app/Activity
    //   55: astore_3
    //   56: aload_3
    //   57: ifnonnull +27 -> 84
    //   60: ldc_w 809
    //   63: invokestatic 167	com/google/ads/util/b:e	(Ljava/lang/String;)V
    //   66: aload_0
    //   67: getstatic 769	com/google/ads/AdRequest$ErrorCode:INTERNAL_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   70: iconst_0
    //   71: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   74: aload_0
    //   75: monitorexit
    //   76: goto -44 -> 32
    //   79: astore_2
    //   80: aload_0
    //   81: monitorexit
    //   82: aload_2
    //   83: athrow
    //   84: aload_0
    //   85: getfield 72	com/google/ads/internal/c:j	Lcom/google/ads/l;
    //   88: getfield 104	com/google/ads/l:a	Lcom/google/ads/util/i$b;
    //   91: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   94: checkcast 111	com/google/ads/n
    //   97: getfield 128	com/google/ads/n:b	Lcom/google/ads/util/i$b;
    //   100: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   103: checkcast 130	com/google/ads/internal/d
    //   106: invokevirtual 810	com/google/ads/internal/d:p	()J
    //   109: lstore 4
    //   111: invokestatic 815	android/os/SystemClock:elapsedRealtime	()J
    //   114: lstore 6
    //   116: aload_0
    //   117: getfield 276	com/google/ads/internal/c:h	Lcom/google/ads/AdRequest;
    //   120: aload_0
    //   121: getfield 72	com/google/ads/internal/c:j	Lcom/google/ads/l;
    //   124: getfield 104	com/google/ads/l:a	Lcom/google/ads/util/i$b;
    //   127: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   130: checkcast 111	com/google/ads/n
    //   133: getfield 550	com/google/ads/n:f	Lcom/google/ads/util/i$b;
    //   136: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   139: checkcast 407	android/content/Context
    //   142: invokevirtual 821	com/google/ads/AdRequest:getRequestMap	(Landroid/content/Context;)Ljava/util/Map;
    //   145: astore 8
    //   147: aload 8
    //   149: ldc_w 823
    //   152: invokeinterface 827 2 0
    //   157: astore 9
    //   159: aload 9
    //   161: instanceof 330
    //   164: ifeq +189 -> 353
    //   167: aload 9
    //   169: checkcast 330	java/util/Map
    //   172: astore 31
    //   174: aload 31
    //   176: ldc_w 829
    //   179: invokeinterface 827 2 0
    //   184: astore 32
    //   186: aload 32
    //   188: instanceof 174
    //   191: ifeq +12 -> 203
    //   194: aload_0
    //   195: aload 32
    //   197: checkcast 174	java/lang/String
    //   200: putfield 76	com/google/ads/internal/c:b	Ljava/lang/String;
    //   203: aload 31
    //   205: ldc_w 831
    //   208: invokeinterface 827 2 0
    //   213: astore 33
    //   215: aload 33
    //   217: instanceof 174
    //   220: ifeq +12 -> 232
    //   223: aload_0
    //   224: aload 33
    //   226: checkcast 174	java/lang/String
    //   229: putfield 74	com/google/ads/internal/c:k	Ljava/lang/String;
    //   232: aload 31
    //   234: ldc_w 833
    //   237: invokeinterface 827 2 0
    //   242: astore 34
    //   244: aload 34
    //   246: instanceof 174
    //   249: ifeq +12 -> 261
    //   252: aload_0
    //   253: aload 34
    //   255: checkcast 174	java/lang/String
    //   258: putfield 273	com/google/ads/internal/c:l	Ljava/lang/String;
    //   261: aload 31
    //   263: ldc_w 835
    //   266: invokeinterface 827 2 0
    //   271: astore 35
    //   273: aload 35
    //   275: instanceof 174
    //   278: ifeq +19 -> 297
    //   281: aload 35
    //   283: ldc_w 836
    //   286: invokevirtual 839	java/lang/Object:equals	(Ljava/lang/Object;)Z
    //   289: ifeq +145 -> 434
    //   292: aload_0
    //   293: iconst_1
    //   294: putfield 91	com/google/ads/internal/c:u	I
    //   297: aload 31
    //   299: ldc_w 841
    //   302: invokeinterface 827 2 0
    //   307: astore 36
    //   309: aload 36
    //   311: instanceof 174
    //   314: ifeq +39 -> 353
    //   317: aload 36
    //   319: ldc_w 842
    //   322: invokevirtual 839	java/lang/Object:equals	(Ljava/lang/Object;)Z
    //   325: ifeq +28 -> 353
    //   328: aload_0
    //   329: getfield 72	com/google/ads/internal/c:j	Lcom/google/ads/l;
    //   332: getfield 104	com/google/ads/l:a	Lcom/google/ads/util/i$b;
    //   335: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   338: checkcast 111	com/google/ads/n
    //   341: getfield 128	com/google/ads/n:b	Lcom/google/ads/util/i$b;
    //   344: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   347: checkcast 130	com/google/ads/internal/d
    //   350: invokevirtual 844	com/google/ads/internal/d:e	()V
    //   353: aload_0
    //   354: getfield 76	com/google/ads/internal/c:b	Ljava/lang/String;
    //   357: ifnonnull +1272 -> 1629
    //   360: aload_0
    //   361: getfield 74	com/google/ads/internal/c:k	Ljava/lang/String;
    //   364: astore 14
    //   366: aload 14
    //   368: ifnonnull +304 -> 672
    //   371: aload_0
    //   372: aload 8
    //   374: aload_3
    //   375: invokevirtual 846	com/google/ads/internal/c:a	(Ljava/util/Map;Landroid/app/Activity;)Ljava/lang/String;
    //   378: astore 24
    //   380: aload_0
    //   381: aload 24
    //   383: aload_0
    //   384: invokespecial 848	com/google/ads/internal/c:f	()Ljava/lang/String;
    //   387: invokespecial 850	com/google/ads/internal/c:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   390: invokestatic 815	android/os/SystemClock:elapsedRealtime	()J
    //   393: lstore 25
    //   395: lload 4
    //   397: lload 25
    //   399: lload 6
    //   401: lsub
    //   402: lsub
    //   403: lstore 27
    //   405: lload 27
    //   407: lconst_0
    //   408: lcmp
    //   409: ifle +9 -> 418
    //   412: aload_0
    //   413: lload 27
    //   415: invokevirtual 854	java/lang/Object:wait	(J)V
    //   418: aload_0
    //   419: getfield 735	com/google/ads/internal/c:q	Z
    //   422: istore 29
    //   424: iload 29
    //   426: ifeq +118 -> 544
    //   429: aload_0
    //   430: monitorexit
    //   431: goto -399 -> 32
    //   434: aload 35
    //   436: ldc_w 855
    //   439: invokevirtual 839	java/lang/Object:equals	(Ljava/lang/Object;)Z
    //   442: ifeq -145 -> 297
    //   445: aload_0
    //   446: iconst_0
    //   447: putfield 91	com/google/ads/internal/c:u	I
    //   450: goto -153 -> 297
    //   453: astore_1
    //   454: ldc_w 857
    //   457: aload_1
    //   458: invokestatic 236	com/google/ads/util/b:b	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   461: aload_0
    //   462: getstatic 769	com/google/ads/AdRequest$ErrorCode:INTERNAL_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   465: iconst_1
    //   466: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   469: aload_0
    //   470: monitorexit
    //   471: goto -439 -> 32
    //   474: astore 23
    //   476: new 238	java/lang/StringBuilder
    //   479: dup
    //   480: invokespecial 239	java/lang/StringBuilder:<init>	()V
    //   483: ldc_w 859
    //   486: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   489: aload 23
    //   491: invokevirtual 862	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   494: invokevirtual 251	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   497: invokestatic 231	com/google/ads/util/b:c	(Ljava/lang/String;)V
    //   500: aload_0
    //   501: getstatic 769	com/google/ads/AdRequest$ErrorCode:INTERNAL_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   504: iconst_0
    //   505: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   508: aload_0
    //   509: monitorexit
    //   510: goto -478 -> 32
    //   513: astore 30
    //   515: new 238	java/lang/StringBuilder
    //   518: dup
    //   519: invokespecial 239	java/lang/StringBuilder:<init>	()V
    //   522: ldc_w 864
    //   525: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   528: aload 30
    //   530: invokevirtual 862	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   533: invokevirtual 251	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   536: invokestatic 719	com/google/ads/util/b:a	(Ljava/lang/String;)V
    //   539: aload_0
    //   540: monitorexit
    //   541: goto -509 -> 32
    //   544: aload_0
    //   545: getfield 87	com/google/ads/internal/c:s	Lcom/google/ads/AdRequest$ErrorCode;
    //   548: ifnull +17 -> 565
    //   551: aload_0
    //   552: aload_0
    //   553: getfield 87	com/google/ads/internal/c:s	Lcom/google/ads/AdRequest$ErrorCode;
    //   556: iconst_0
    //   557: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   560: aload_0
    //   561: monitorexit
    //   562: goto -530 -> 32
    //   565: aload_0
    //   566: getfield 74	com/google/ads/internal/c:k	Ljava/lang/String;
    //   569: ifnonnull +46 -> 615
    //   572: new 238	java/lang/StringBuilder
    //   575: dup
    //   576: invokespecial 239	java/lang/StringBuilder:<init>	()V
    //   579: ldc_w 866
    //   582: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   585: lload 4
    //   587: invokevirtual 869	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   590: ldc_w 871
    //   593: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   596: invokevirtual 251	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   599: invokestatic 231	com/google/ads/util/b:c	(Ljava/lang/String;)V
    //   602: aload_0
    //   603: getstatic 874	com/google/ads/AdRequest$ErrorCode:NETWORK_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   606: iconst_0
    //   607: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   610: aload_0
    //   611: monitorexit
    //   612: goto -580 -> 32
    //   615: aload_0
    //   616: getfield 72	com/google/ads/internal/c:j	Lcom/google/ads/l;
    //   619: getfield 104	com/google/ads/l:a	Lcom/google/ads/util/i$b;
    //   622: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   625: checkcast 111	com/google/ads/n
    //   628: getfield 601	com/google/ads/n:g	Lcom/google/ads/util/i$b;
    //   631: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   634: checkcast 603	com/google/ads/internal/h
    //   637: invokevirtual 680	com/google/ads/internal/h:b	()Z
    //   640: ifeq +32 -> 672
    //   643: aload_0
    //   644: getfield 273	com/google/ads/internal/c:l	Ljava/lang/String;
    //   647: invokestatic 435	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   650: ifeq +22 -> 672
    //   653: ldc_w 876
    //   656: invokestatic 231	com/google/ads/util/b:c	(Ljava/lang/String;)V
    //   659: aload_0
    //   660: getstatic 769	com/google/ads/AdRequest$ErrorCode:INTERNAL_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   663: iconst_0
    //   664: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   667: aload_0
    //   668: monitorexit
    //   669: goto -637 -> 32
    //   672: aload_0
    //   673: getfield 72	com/google/ads/internal/c:j	Lcom/google/ads/l;
    //   676: getfield 104	com/google/ads/l:a	Lcom/google/ads/util/i$b;
    //   679: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   682: checkcast 111	com/google/ads/n
    //   685: getfield 128	com/google/ads/n:b	Lcom/google/ads/util/i$b;
    //   688: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   691: checkcast 130	com/google/ads/internal/d
    //   694: invokevirtual 304	com/google/ads/internal/d:n	()Lcom/google/ads/internal/g;
    //   697: astore 15
    //   699: getstatic 879	com/google/ads/internal/c$3:a	[I
    //   702: aload_0
    //   703: getfield 69	com/google/ads/internal/c:x	Lcom/google/ads/internal/c$d;
    //   706: invokevirtual 880	com/google/ads/internal/c$d:ordinal	()I
    //   709: iaload
    //   710: tableswitch	default:+30 -> 740, 1:+233->943, 2:+257->967, 3:+271->981, 4:+290->1000
    //   741: getfield 800	com/google/ads/internal/c:a	Z
    //   744: ifne +373 -> 1117
    //   747: ldc_w 882
    //   750: invokestatic 719	com/google/ads/util/b:a	(Ljava/lang/String;)V
    //   753: aload_0
    //   754: getfield 160	com/google/ads/internal/c:g	Lcom/google/ads/internal/f;
    //   757: aload_0
    //   758: getfield 798	com/google/ads/internal/c:w	Z
    //   761: invokevirtual 884	com/google/ads/internal/f:a	(Z)V
    //   764: aload_0
    //   765: getfield 72	com/google/ads/internal/c:j	Lcom/google/ads/l;
    //   768: getfield 104	com/google/ads/l:a	Lcom/google/ads/util/i$b;
    //   771: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   774: checkcast 111	com/google/ads/n
    //   777: getfield 601	com/google/ads/n:g	Lcom/google/ads/util/i$b;
    //   780: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   783: checkcast 603	com/google/ads/internal/h
    //   786: invokevirtual 680	com/google/ads/internal/h:b	()Z
    //   789: ifeq +834 -> 1623
    //   792: aload_0
    //   793: getfield 72	com/google/ads/internal/c:j	Lcom/google/ads/l;
    //   796: getfield 104	com/google/ads/l:a	Lcom/google/ads/util/i$b;
    //   799: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   802: checkcast 111	com/google/ads/n
    //   805: getfield 886	com/google/ads/n:e	Lcom/google/ads/util/i$b;
    //   808: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   811: checkcast 888	com/google/ads/internal/ActivationOverlay
    //   814: invokevirtual 890	com/google/ads/internal/ActivationOverlay:e	()Lcom/google/ads/internal/i;
    //   817: astore 21
    //   819: aload 21
    //   821: iconst_1
    //   822: invokevirtual 301	com/google/ads/internal/i:c	(Z)V
    //   825: invokestatic 259	com/google/ads/m:a	()Lcom/google/ads/m;
    //   828: getfield 261	com/google/ads/m:c	Lcom/google/ads/util/i$b;
    //   831: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   834: checkcast 263	android/os/Handler
    //   837: new 8	com/google/ads/internal/c$1
    //   840: dup
    //   841: aload_0
    //   842: invokespecial 893	com/google/ads/internal/c$1:<init>	(Lcom/google/ads/internal/c;)V
    //   845: invokevirtual 270	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   848: pop
    //   849: aload 21
    //   851: astore 16
    //   853: aload_0
    //   854: getfield 160	com/google/ads/internal/c:g	Lcom/google/ads/internal/f;
    //   857: aload_0
    //   858: getfield 74	com/google/ads/internal/c:k	Ljava/lang/String;
    //   861: invokevirtual 894	com/google/ads/internal/f:a	(Ljava/lang/String;)V
    //   864: aload_0
    //   865: getfield 735	com/google/ads/internal/c:q	Z
    //   868: ifne +162 -> 1030
    //   871: aload_0
    //   872: getfield 87	com/google/ads/internal/c:s	Lcom/google/ads/AdRequest$ErrorCode;
    //   875: ifnonnull +155 -> 1030
    //   878: aload_0
    //   879: getfield 78	com/google/ads/internal/c:c	Ljava/lang/String;
    //   882: ifnonnull +148 -> 1030
    //   885: lload 4
    //   887: invokestatic 815	android/os/SystemClock:elapsedRealtime	()J
    //   890: lload 6
    //   892: lsub
    //   893: lsub
    //   894: lstore 19
    //   896: lload 19
    //   898: lconst_0
    //   899: lcmp
    //   900: ifle +130 -> 1030
    //   903: aload_0
    //   904: lload 19
    //   906: invokevirtual 854	java/lang/Object:wait	(J)V
    //   909: goto -45 -> 864
    //   912: astore 17
    //   914: new 238	java/lang/StringBuilder
    //   917: dup
    //   918: invokespecial 239	java/lang/StringBuilder:<init>	()V
    //   921: ldc_w 896
    //   924: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   927: aload 17
    //   929: invokevirtual 862	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   932: invokevirtual 251	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   935: invokestatic 719	com/google/ads/util/b:a	(Ljava/lang/String;)V
    //   938: aload_0
    //   939: monitorexit
    //   940: goto -908 -> 32
    //   943: aload 15
    //   945: invokevirtual 898	com/google/ads/internal/g:r	()V
    //   948: aload 15
    //   950: invokevirtual 900	com/google/ads/internal/g:u	()V
    //   953: aload 15
    //   955: invokevirtual 902	com/google/ads/internal/g:x	()V
    //   958: ldc_w 904
    //   961: invokestatic 231	com/google/ads/util/b:c	(Ljava/lang/String;)V
    //   964: goto -224 -> 740
    //   967: aload 15
    //   969: invokevirtual 906	com/google/ads/internal/g:t	()V
    //   972: ldc_w 908
    //   975: invokestatic 231	com/google/ads/util/b:c	(Ljava/lang/String;)V
    //   978: goto -238 -> 740
    //   981: aload 15
    //   983: invokevirtual 910	com/google/ads/internal/g:w	()V
    //   986: aload 15
    //   988: invokevirtual 912	com/google/ads/internal/g:q	()V
    //   991: ldc_w 914
    //   994: invokestatic 231	com/google/ads/util/b:c	(Ljava/lang/String;)V
    //   997: goto -257 -> 740
    //   1000: aload 15
    //   1002: invokevirtual 912	com/google/ads/internal/g:q	()V
    //   1005: ldc_w 916
    //   1008: invokestatic 231	com/google/ads/util/b:c	(Ljava/lang/String;)V
    //   1011: ldc_w 918
    //   1014: invokestatic 231	com/google/ads/util/b:c	(Ljava/lang/String;)V
    //   1017: aload_0
    //   1018: getstatic 874	com/google/ads/AdRequest$ErrorCode:NETWORK_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   1021: iconst_0
    //   1022: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   1025: aload_0
    //   1026: monitorexit
    //   1027: goto -995 -> 32
    //   1030: aload_0
    //   1031: getfield 735	com/google/ads/internal/c:q	Z
    //   1034: istore 18
    //   1036: iload 18
    //   1038: ifeq +8 -> 1046
    //   1041: aload_0
    //   1042: monitorexit
    //   1043: goto -1011 -> 32
    //   1046: aload_0
    //   1047: getfield 87	com/google/ads/internal/c:s	Lcom/google/ads/AdRequest$ErrorCode;
    //   1050: ifnull +17 -> 1067
    //   1053: aload_0
    //   1054: aload_0
    //   1055: getfield 87	com/google/ads/internal/c:s	Lcom/google/ads/AdRequest$ErrorCode;
    //   1058: iconst_0
    //   1059: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   1062: aload_0
    //   1063: monitorexit
    //   1064: goto -1032 -> 32
    //   1067: aload_0
    //   1068: getfield 78	com/google/ads/internal/c:c	Ljava/lang/String;
    //   1071: ifnonnull +545 -> 1616
    //   1074: new 238	java/lang/StringBuilder
    //   1077: dup
    //   1078: invokespecial 239	java/lang/StringBuilder:<init>	()V
    //   1081: ldc_w 866
    //   1084: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1087: lload 4
    //   1089: invokevirtual 869	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1092: ldc_w 920
    //   1095: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1098: invokevirtual 251	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1101: invokestatic 231	com/google/ads/util/b:c	(Ljava/lang/String;)V
    //   1104: aload_0
    //   1105: getstatic 874	com/google/ads/AdRequest$ErrorCode:NETWORK_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   1108: iconst_0
    //   1109: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   1112: aload_0
    //   1113: monitorexit
    //   1114: goto -1082 -> 32
    //   1117: aload_0
    //   1118: aload_0
    //   1119: getfield 74	com/google/ads/internal/c:k	Ljava/lang/String;
    //   1122: putfield 76	com/google/ads/internal/c:b	Ljava/lang/String;
    //   1125: new 238	java/lang/StringBuilder
    //   1128: dup
    //   1129: invokespecial 239	java/lang/StringBuilder:<init>	()V
    //   1132: ldc_w 922
    //   1135: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1138: aload_0
    //   1139: getfield 76	com/google/ads/internal/c:b	Ljava/lang/String;
    //   1142: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1145: invokevirtual 251	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1148: invokestatic 719	com/google/ads/util/b:a	(Ljava/lang/String;)V
    //   1151: goto +478 -> 1629
    //   1154: aload_0
    //   1155: getfield 800	com/google/ads/internal/c:a	Z
    //   1158: ifne +265 -> 1423
    //   1161: aload_0
    //   1162: getfield 93	com/google/ads/internal/c:f	Z
    //   1165: ifeq +38 -> 1203
    //   1168: aload_0
    //   1169: getfield 72	com/google/ads/internal/c:j	Lcom/google/ads/l;
    //   1172: getfield 104	com/google/ads/l:a	Lcom/google/ads/util/i$b;
    //   1175: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   1178: checkcast 111	com/google/ads/n
    //   1181: getfield 128	com/google/ads/n:b	Lcom/google/ads/util/i$b;
    //   1184: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   1187: checkcast 130	com/google/ads/internal/d
    //   1190: iconst_1
    //   1191: invokevirtual 924	com/google/ads/internal/d:b	(Z)V
    //   1194: aload_0
    //   1195: invokevirtual 926	com/google/ads/internal/c:b	()V
    //   1198: aload_0
    //   1199: monitorexit
    //   1200: goto -1168 -> 32
    //   1203: aload_0
    //   1204: getfield 760	com/google/ads/internal/c:e	Ljava/lang/String;
    //   1207: ifnull +74 -> 1281
    //   1210: aload_0
    //   1211: getfield 760	com/google/ads/internal/c:e	Ljava/lang/String;
    //   1214: ldc_w 773
    //   1217: invokevirtual 777	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   1220: ifne +16 -> 1236
    //   1223: aload_0
    //   1224: getfield 760	com/google/ads/internal/c:e	Ljava/lang/String;
    //   1227: ldc_w 928
    //   1230: invokevirtual 777	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   1233: ifeq +48 -> 1281
    //   1236: new 238	java/lang/StringBuilder
    //   1239: dup
    //   1240: invokespecial 239	java/lang/StringBuilder:<init>	()V
    //   1243: ldc_w 930
    //   1246: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1249: aload_0
    //   1250: getfield 760	com/google/ads/internal/c:e	Ljava/lang/String;
    //   1253: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1256: ldc_w 932
    //   1259: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1262: invokevirtual 251	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1265: invokestatic 764	com/google/ads/util/b:b	(Ljava/lang/String;)V
    //   1268: aload_0
    //   1269: getstatic 769	com/google/ads/AdRequest$ErrorCode:INTERNAL_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   1272: iconst_0
    //   1273: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   1276: aload_0
    //   1277: monitorexit
    //   1278: goto -1246 -> 32
    //   1281: aload_0
    //   1282: getfield 72	com/google/ads/internal/c:j	Lcom/google/ads/l;
    //   1285: getfield 104	com/google/ads/l:a	Lcom/google/ads/util/i$b;
    //   1288: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   1291: checkcast 111	com/google/ads/n
    //   1294: getfield 590	com/google/ads/n:n	Lcom/google/ads/util/i$c;
    //   1297: invokevirtual 443	com/google/ads/util/i$c:a	()Ljava/lang/Object;
    //   1300: ifnull +105 -> 1405
    //   1303: aload_0
    //   1304: getfield 99	com/google/ads/internal/c:o	Lcom/google/ads/AdSize;
    //   1307: ifnonnull +22 -> 1329
    //   1310: ldc_w 934
    //   1313: invokestatic 764	com/google/ads/util/b:b	(Ljava/lang/String;)V
    //   1316: aload_0
    //   1317: getstatic 769	com/google/ads/AdRequest$ErrorCode:INTERNAL_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   1320: iconst_0
    //   1321: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   1324: aload_0
    //   1325: monitorexit
    //   1326: goto -1294 -> 32
    //   1329: aload_0
    //   1330: getfield 72	com/google/ads/internal/c:j	Lcom/google/ads/l;
    //   1333: getfield 104	com/google/ads/l:a	Lcom/google/ads/util/i$b;
    //   1336: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   1339: checkcast 111	com/google/ads/n
    //   1342: getfield 590	com/google/ads/n:n	Lcom/google/ads/util/i$c;
    //   1345: invokevirtual 443	com/google/ads/util/i$c:a	()Ljava/lang/Object;
    //   1348: checkcast 936	[Ljava/lang/Object;
    //   1351: invokestatic 942	java/util/Arrays:asList	([Ljava/lang/Object;)Ljava/util/List;
    //   1354: aload_0
    //   1355: getfield 99	com/google/ads/internal/c:o	Lcom/google/ads/AdSize;
    //   1358: invokeinterface 946 2 0
    //   1363: ifne +60 -> 1423
    //   1366: new 238	java/lang/StringBuilder
    //   1369: dup
    //   1370: invokespecial 239	java/lang/StringBuilder:<init>	()V
    //   1373: ldc_w 948
    //   1376: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1379: aload_0
    //   1380: getfield 99	com/google/ads/internal/c:o	Lcom/google/ads/AdSize;
    //   1383: invokevirtual 862	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1386: invokevirtual 251	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1389: invokestatic 764	com/google/ads/util/b:b	(Ljava/lang/String;)V
    //   1392: aload_0
    //   1393: getstatic 769	com/google/ads/AdRequest$ErrorCode:INTERNAL_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   1396: iconst_0
    //   1397: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   1400: aload_0
    //   1401: monitorexit
    //   1402: goto -1370 -> 32
    //   1405: aload_0
    //   1406: getfield 99	com/google/ads/internal/c:o	Lcom/google/ads/AdSize;
    //   1409: ifnull +14 -> 1423
    //   1412: ldc_w 950
    //   1415: invokestatic 167	com/google/ads/util/b:e	(Ljava/lang/String;)V
    //   1418: aload_0
    //   1419: aconst_null
    //   1420: putfield 99	com/google/ads/internal/c:o	Lcom/google/ads/AdSize;
    //   1423: aload_0
    //   1424: getfield 72	com/google/ads/internal/c:j	Lcom/google/ads/l;
    //   1427: getfield 104	com/google/ads/l:a	Lcom/google/ads/util/i$b;
    //   1430: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   1433: checkcast 111	com/google/ads/n
    //   1436: getfield 128	com/google/ads/n:b	Lcom/google/ads/util/i$b;
    //   1439: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   1442: checkcast 130	com/google/ads/internal/d
    //   1445: iconst_0
    //   1446: invokevirtual 924	com/google/ads/internal/d:b	(Z)V
    //   1449: aload_0
    //   1450: invokespecial 951	com/google/ads/internal/c:i	()V
    //   1453: aload_0
    //   1454: getfield 735	com/google/ads/internal/c:q	Z
    //   1457: ifne +104 -> 1561
    //   1460: aload_0
    //   1461: getfield 89	com/google/ads/internal/c:t	Z
    //   1464: ifeq +39 -> 1503
    //   1467: aload_0
    //   1468: getfield 72	com/google/ads/internal/c:j	Lcom/google/ads/l;
    //   1471: getfield 104	com/google/ads/l:a	Lcom/google/ads/util/i$b;
    //   1474: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   1477: checkcast 111	com/google/ads/n
    //   1480: getfield 601	com/google/ads/n:g	Lcom/google/ads/util/i$b;
    //   1483: invokevirtual 109	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   1486: checkcast 603	com/google/ads/internal/h
    //   1489: invokevirtual 680	com/google/ads/internal/h:b	()Z
    //   1492: ifeq +69 -> 1561
    //   1495: aload 10
    //   1497: invokevirtual 953	com/google/ads/internal/i:a	()Z
    //   1500: ifeq +61 -> 1561
    //   1503: lload 4
    //   1505: invokestatic 815	android/os/SystemClock:elapsedRealtime	()J
    //   1508: lload 6
    //   1510: lsub
    //   1511: lsub
    //   1512: lstore 12
    //   1514: lload 12
    //   1516: lconst_0
    //   1517: lcmp
    //   1518: ifle +43 -> 1561
    //   1521: aload_0
    //   1522: lload 12
    //   1524: invokevirtual 854	java/lang/Object:wait	(J)V
    //   1527: goto -74 -> 1453
    //   1530: astore 11
    //   1532: new 238	java/lang/StringBuilder
    //   1535: dup
    //   1536: invokespecial 239	java/lang/StringBuilder:<init>	()V
    //   1539: ldc_w 955
    //   1542: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1545: aload 11
    //   1547: invokevirtual 862	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1550: invokevirtual 251	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1553: invokestatic 719	com/google/ads/util/b:a	(Ljava/lang/String;)V
    //   1556: aload_0
    //   1557: monitorexit
    //   1558: goto -1526 -> 32
    //   1561: aload_0
    //   1562: getfield 89	com/google/ads/internal/c:t	Z
    //   1565: ifeq +10 -> 1575
    //   1568: aload_0
    //   1569: invokespecial 957	com/google/ads/internal/c:j	()V
    //   1572: goto -1103 -> 469
    //   1575: new 238	java/lang/StringBuilder
    //   1578: dup
    //   1579: invokespecial 239	java/lang/StringBuilder:<init>	()V
    //   1582: ldc_w 866
    //   1585: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1588: lload 4
    //   1590: invokevirtual 869	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1593: ldc_w 959
    //   1596: invokevirtual 245	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1599: invokevirtual 251	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1602: invokestatic 231	com/google/ads/util/b:c	(Ljava/lang/String;)V
    //   1605: aload_0
    //   1606: getstatic 874	com/google/ads/AdRequest$ErrorCode:NETWORK_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   1609: iconst_1
    //   1610: invokevirtual 771	com/google/ads/internal/c:a	(Lcom/google/ads/AdRequest$ErrorCode;Z)V
    //   1613: goto -1144 -> 469
    //   1616: aload 16
    //   1618: astore 10
    //   1620: goto -466 -> 1154
    //   1623: aconst_null
    //   1624: astore 16
    //   1626: goto -773 -> 853
    //   1629: aconst_null
    //   1630: astore 10
    //   1632: goto -478 -> 1154
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1635	0	this	c
    //   453	5	1	localThrowable	java.lang.Throwable
    //   79	4	2	localObject1	Object
    //   55	320	3	localActivity	Activity
    //   109	1480	4	l1	long
    //   114	1395	6	l2	long
    //   145	228	8	localMap1	Map
    //   157	11	9	localObject2	Object
    //   1495	136	10	locali1	i
    //   1530	16	11	localInterruptedException1	java.lang.InterruptedException
    //   1512	11	12	l3	long
    //   364	3	14	str1	String
    //   697	304	15	localg	g
    //   851	774	16	locali2	i
    //   912	16	17	localInterruptedException2	java.lang.InterruptedException
    //   1034	3	18	bool1	boolean
    //   894	11	19	l4	long
    //   817	33	21	locali3	i
    //   474	16	23	localb	b
    //   378	4	24	str2	String
    //   393	5	25	l5	long
    //   403	11	27	l6	long
    //   422	3	29	bool2	boolean
    //   513	16	30	localInterruptedException3	java.lang.InterruptedException
    //   172	126	31	localMap2	Map
    //   184	12	32	localObject3	Object
    //   213	12	33	localObject4	Object
    //   242	12	34	localObject5	Object
    //   271	164	35	localObject6	Object
    //   307	11	36	localObject7	Object
    // Exception table:
    //   from	to	target	type
    //   2	30	79	finally
    //   30	32	79	finally
    //   33	74	79	finally
    //   74	82	79	finally
    //   84	366	79	finally
    //   371	380	79	finally
    //   380	395	79	finally
    //   412	418	79	finally
    //   418	424	79	finally
    //   429	431	79	finally
    //   434	450	79	finally
    //   454	471	79	finally
    //   476	508	79	finally
    //   508	510	79	finally
    //   515	539	79	finally
    //   539	541	79	finally
    //   544	560	79	finally
    //   560	562	79	finally
    //   565	610	79	finally
    //   610	612	79	finally
    //   615	667	79	finally
    //   667	669	79	finally
    //   672	864	79	finally
    //   864	909	79	finally
    //   914	938	79	finally
    //   938	940	79	finally
    //   943	1025	79	finally
    //   1025	1027	79	finally
    //   1030	1036	79	finally
    //   1041	1043	79	finally
    //   1046	1062	79	finally
    //   1062	1064	79	finally
    //   1067	1112	79	finally
    //   1112	1114	79	finally
    //   1117	1198	79	finally
    //   1198	1200	79	finally
    //   1203	1276	79	finally
    //   1276	1278	79	finally
    //   1281	1324	79	finally
    //   1324	1326	79	finally
    //   1329	1400	79	finally
    //   1400	1402	79	finally
    //   1405	1453	79	finally
    //   1453	1527	79	finally
    //   1532	1556	79	finally
    //   1556	1558	79	finally
    //   1561	1613	79	finally
    //   2	30	453	java/lang/Throwable
    //   33	74	453	java/lang/Throwable
    //   84	366	453	java/lang/Throwable
    //   371	380	453	java/lang/Throwable
    //   380	395	453	java/lang/Throwable
    //   412	418	453	java/lang/Throwable
    //   418	424	453	java/lang/Throwable
    //   434	450	453	java/lang/Throwable
    //   476	508	453	java/lang/Throwable
    //   515	539	453	java/lang/Throwable
    //   544	560	453	java/lang/Throwable
    //   565	610	453	java/lang/Throwable
    //   615	667	453	java/lang/Throwable
    //   672	864	453	java/lang/Throwable
    //   864	909	453	java/lang/Throwable
    //   914	938	453	java/lang/Throwable
    //   943	1025	453	java/lang/Throwable
    //   1030	1036	453	java/lang/Throwable
    //   1046	1062	453	java/lang/Throwable
    //   1067	1112	453	java/lang/Throwable
    //   1117	1198	453	java/lang/Throwable
    //   1203	1276	453	java/lang/Throwable
    //   1281	1324	453	java/lang/Throwable
    //   1329	1400	453	java/lang/Throwable
    //   1405	1453	453	java/lang/Throwable
    //   1453	1527	453	java/lang/Throwable
    //   1532	1556	453	java/lang/Throwable
    //   1561	1613	453	java/lang/Throwable
    //   371	380	474	com/google/ads/internal/c$b
    //   412	418	513	java/lang/InterruptedException
    //   864	909	912	java/lang/InterruptedException
    //   1453	1527	1530	java/lang/InterruptedException
  }
  
  public static enum d
  {
    public String e;
    
    static
    {
      d[] arrayOfd = new d[4];
      arrayOfd[0] = a;
      arrayOfd[1] = b;
      arrayOfd[2] = c;
      arrayOfd[3] = d;
      f = arrayOfd;
    }
    
    private d(String paramString)
    {
      this.e = paramString;
    }
  }
  
  private class e
    implements Runnable
  {
    private final d b;
    private final WebView c;
    private final LinkedList<String> d;
    private final int e;
    private final boolean f;
    private final String g;
    private final AdSize h;
    
    public e(WebView paramWebView, LinkedList<String> paramLinkedList, int paramInt, boolean paramBoolean, String paramString, AdSize paramAdSize)
    {
      this.b = paramWebView;
      this.c = paramLinkedList;
      this.d = paramInt;
      this.e = paramBoolean;
      this.f = paramString;
      this.g = paramAdSize;
      Object localObject;
      this.h = localObject;
    }
    
    public void run()
    {
      if (this.c != null)
      {
        this.c.stopLoading();
        this.c.destroy();
      }
      this.b.a(this.d);
      this.b.a(this.e);
      this.b.a(this.f);
      this.b.a(this.g);
      if (this.h != null)
      {
        ((h)((n)c.b(c.this).a.a()).g.a()).b(this.h);
        this.b.l().setAdSize(this.h);
      }
      this.b.E();
    }
  }
  
  private class c
    implements Runnable
  {
    private final String b;
    private final String c;
    private final WebView d;
    
    public c(WebView paramWebView, String paramString1, String paramString2)
    {
      this.d = paramWebView;
      this.b = paramString1;
      this.c = paramString2;
    }
    
    public void run()
    {
      c.b(c.this).c.a(Boolean.valueOf(c.a(c.this)));
      ((d)((n)c.b(c.this).a.a()).b.a()).l().a(c.a(c.this));
      boolean bool;
      if (((n)c.b(c.this).a.a()).e.a() != null)
      {
        ActivationOverlay localActivationOverlay = (ActivationOverlay)((n)c.b(c.this).a.a()).e.a();
        if (!c.a(c.this))
        {
          bool = true;
          localActivationOverlay.setOverlayEnabled(bool);
        }
      }
      else
      {
        if (this.c == null) {
          break label162;
        }
        this.d.loadDataWithBaseURL(this.b, this.c, "text/html", "utf-8", null);
      }
      for (;;)
      {
        return;
        bool = false;
        break;
        label162:
        this.d.loadUrl(this.b);
      }
    }
  }
  
  private static class a
    implements Runnable
  {
    private final d a;
    private final WebView b;
    private final f c;
    private final AdRequest.ErrorCode d;
    private final boolean e;
    
    public a(d paramd, WebView paramWebView, f paramf, AdRequest.ErrorCode paramErrorCode, boolean paramBoolean)
    {
      this.a = paramd;
      this.b = paramWebView;
      this.c = paramf;
      this.d = paramErrorCode;
      this.e = paramBoolean;
    }
    
    public void run()
    {
      if (this.b != null)
      {
        this.b.stopLoading();
        this.b.destroy();
      }
      if (this.c != null) {
        this.c.a();
      }
      if (this.e)
      {
        this.a.l().stopLoading();
        if (this.a.i().i.a() != null) {
          ((ViewGroup)this.a.i().i.a()).setVisibility(8);
        }
      }
      this.a.a(this.d);
    }
  }
  
  private class b
    extends Exception
  {
    public b(String paramString)
    {
      super();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.internal.c
 * JD-Core Version:    0.7.0.1
 */