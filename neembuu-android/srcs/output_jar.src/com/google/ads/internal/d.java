package com.google.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import com.google.ads.Ad;
import com.google.ads.AdActivity;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.AppEventListener;
import com.google.ads.InterstitialAd;
import com.google.ads.SwipeableAdListener;
import com.google.ads.ae;
import com.google.ads.af;
import com.google.ads.at;
import com.google.ads.doubleclick.SwipeableDfpAdView;
import com.google.ads.f;
import com.google.ads.l;
import com.google.ads.m;
import com.google.ads.n;
import com.google.ads.util.AdUtil;
import com.google.ads.util.a;
import com.google.ads.util.i.b;
import com.google.ads.util.i.c;
import com.google.ads.util.i.d;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class d
{
  private static final Object a = new Object();
  private String A = null;
  private String B = null;
  private final n b;
  private c c;
  private AdRequest d;
  private g e;
  private AdWebView f;
  private i g;
  private boolean h = false;
  private long i;
  private boolean j;
  private boolean k;
  private boolean l;
  private boolean m;
  private boolean n;
  private SharedPreferences o;
  private long p;
  private af q;
  private boolean r;
  private LinkedList<String> s;
  private LinkedList<String> t;
  private LinkedList<String> u;
  private int v = -1;
  private Boolean w;
  private com.google.ads.d x;
  private com.google.ads.e y;
  private f z;
  
  public d(Ad paramAd, Activity paramActivity, AdSize paramAdSize, String paramString, ViewGroup paramViewGroup, boolean paramBoolean)
  {
    this.r = paramBoolean;
    this.e = new g();
    this.c = null;
    this.d = null;
    this.k = false;
    this.p = 60000L;
    this.l = false;
    this.n = false;
    this.m = true;
    h localh;
    m localm2;
    AdView localAdView2;
    if (paramAdSize == null)
    {
      localh = h.a;
      if ((paramAd instanceof SwipeableDfpAdView)) {
        localh.a(true);
      }
      if (paramActivity != null) {
        break label189;
      }
      localm2 = m.a();
      if (!(paramAd instanceof AdView)) {
        break label177;
      }
      localAdView2 = (AdView)paramAd;
      label123:
      if (!(paramAd instanceof InterstitialAd)) {
        break label183;
      }
    }
    label177:
    label183:
    for (InterstitialAd localInterstitialAd2 = (InterstitialAd)paramAd;; localInterstitialAd2 = null)
    {
      this.b = new n(localm2, paramAd, localAdView2, localInterstitialAd2, paramString, null, null, paramViewGroup, localh, this);
      return;
      localh = h.a(paramAdSize, paramActivity.getApplicationContext());
      break;
      localAdView2 = null;
      break label123;
    }
    for (;;)
    {
      synchronized (a)
      {
        label189:
        this.o = paramActivity.getApplicationContext().getSharedPreferences("GoogleAdMobAdsPrefs", 0);
        if (paramBoolean)
        {
          long l1 = this.o.getLong("Timeout" + paramString, -1L);
          if (l1 < 0L)
          {
            this.i = 5000L;
            m localm1 = m.a();
            if (!(paramAd instanceof AdView)) {
              break label456;
            }
            localAdView1 = (AdView)paramAd;
            if (!(paramAd instanceof InterstitialAd)) {
              break label462;
            }
            localInterstitialAd1 = (InterstitialAd)paramAd;
            this.b = new n(localm1, paramAd, localAdView1, localInterstitialAd1, paramString, paramActivity, paramActivity.getApplicationContext(), paramViewGroup, localh, this);
            this.q = new af(this);
            this.s = new LinkedList();
            this.t = new LinkedList();
            this.u = new LinkedList();
            a();
            AdUtil.h((Context)this.b.f.a());
            this.x = new com.google.ads.d();
            this.y = new com.google.ads.e(this);
            this.w = null;
            this.z = null;
            break;
          }
          this.i = l1;
        }
      }
      this.i = 60000L;
      continue;
      label456:
      AdView localAdView1 = null;
      continue;
      label462:
      InterstitialAd localInterstitialAd1 = null;
    }
  }
  
  private void a(f paramf, Boolean paramBoolean)
  {
    Object localObject = paramf.d();
    if (localObject == null)
    {
      localObject = new ArrayList();
      ((List)localObject).add("http://e.admob.com/imp?ad_loc=@gw_adlocid@&qdata=@gw_qdata@&ad_network_id=@gw_adnetid@&js=@gw_sdkver@&session_id=@gw_sessid@&seq_num=@gw_seqnum@&nr=@gw_adnetrefresh@&adt=@gw_adt@&aec=@gw_aec@");
    }
    String str = paramf.b();
    a((List)localObject, paramf.a(), str, paramf.c(), paramBoolean, this.e.d(), this.e.e());
  }
  
  private void a(List<String> paramList, String paramString)
  {
    Object localObject;
    if (paramList == null)
    {
      localObject = new ArrayList();
      ((List)localObject).add("http://e.admob.com/nofill?ad_loc=@gw_adlocid@&qdata=@gw_qdata@&js=@gw_sdkver@&session_id=@gw_sessid@&seq_num=@gw_seqnum@&adt=@gw_adt@&aec=@gw_aec@");
    }
    for (;;)
    {
      a((List)localObject, null, null, paramString, null, this.e.d(), this.e.e());
      return;
      localObject = paramList;
    }
  }
  
  private void a(List<String> paramList, String paramString1, String paramString2, String paramString3, Boolean paramBoolean, String paramString4, String paramString5)
  {
    String str1 = AdUtil.a((Context)this.b.f.a());
    com.google.ads.b localb = com.google.ads.b.a();
    String str2 = localb.b().toString();
    String str3 = localb.c().toString();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      new Thread(new ae(com.google.ads.g.a((String)localIterator.next(), (String)this.b.h.a(), paramBoolean, str1, paramString1, paramString2, paramString3, str2, str3, paramString4, paramString5), (Context)this.b.f.a())).start();
    }
    this.e.b();
  }
  
  private void b(f paramf, Boolean paramBoolean)
  {
    Object localObject = paramf.e();
    if (localObject == null)
    {
      localObject = new ArrayList();
      ((List)localObject).add("http://e.admob.com/clk?ad_loc=@gw_adlocid@&qdata=@gw_qdata@&ad_network_id=@gw_adnetid@&js=@gw_sdkver@&session_id=@gw_sessid@&seq_num=@gw_seqnum@&nr=@gw_adnetrefresh@");
    }
    String str = paramf.b();
    a((List)localObject, paramf.a(), str, paramf.c(), paramBoolean, null, null);
  }
  
  /**
   * @deprecated
   */
  public void A()
  {
    for (;;)
    {
      try
      {
        boolean bool = this.h;
        if (bool) {
          return;
        }
        if (this.d == null) {
          break label134;
        }
        if (!this.b.a()) {
          break label125;
        }
        if ((((AdView)this.b.j.a()).isShown()) && (AdUtil.d()))
        {
          com.google.ads.util.b.c("Refreshing ad.");
          a(this.d);
          if (!this.n) {
            break label98;
          }
          f();
          continue;
        }
        com.google.ads.util.b.a("Not refreshing because the ad is not visible.");
      }
      finally {}
      continue;
      label98:
      ((Handler)m.a().c.a()).postDelayed(this.q, this.p);
      continue;
      label125:
      com.google.ads.util.b.a("Tried to refresh an ad that wasn't an AdView.");
      continue;
      label134:
      com.google.ads.util.b.a("Tried to refresh before calling loadAd().");
    }
  }
  
  /**
   * @deprecated
   */
  public void B()
  {
    for (;;)
    {
      try
      {
        a.a(this.b.b());
        if (!this.k) {
          break label101;
        }
        this.k = false;
        if (this.w == null)
        {
          com.google.ads.util.b.b("isMediationFlag is null in show() with isReady() true. we should have an ad and know whether this is a mediation request or not. ");
          return;
        }
        if (this.w.booleanValue())
        {
          if (!this.y.c()) {
            continue;
          }
          a(this.z, Boolean.valueOf(false));
          continue;
        }
        AdActivity.launchAdActivity(this, new e("interstitial"));
      }
      finally {}
      y();
      continue;
      label101:
      com.google.ads.util.b.c("Cannot show interstitial because it is not loaded and ready.");
    }
  }
  
  /**
   * @deprecated
   */
  public void C()
  {
    try
    {
      if (this.c != null)
      {
        this.c.a();
        this.c = null;
      }
      if (this.f != null) {
        this.f.stopLoading();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  /**
   * @deprecated
   */
  protected void D()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 116	com/google/ads/internal/d:b	Lcom/google/ads/n;
    //   6: getfield 385	com/google/ads/n:c	Lcom/google/ads/util/i$d;
    //   9: invokevirtual 388	com/google/ads/util/i$d:a	()Ljava/lang/Object;
    //   12: checkcast 118	android/app/Activity
    //   15: astore_2
    //   16: aload_2
    //   17: ifnonnull +12 -> 29
    //   20: ldc_w 390
    //   23: invokestatic 392	com/google/ads/util/b:e	(Ljava/lang/String;)V
    //   26: aload_0
    //   27: monitorexit
    //   28: return
    //   29: aload_0
    //   30: getfield 176	com/google/ads/internal/d:u	Ljava/util/LinkedList;
    //   33: invokevirtual 393	java/util/LinkedList:iterator	()Ljava/util/Iterator;
    //   36: astore_3
    //   37: aload_3
    //   38: invokeinterface 264 1 0
    //   43: ifeq -17 -> 26
    //   46: new 266	java/lang/Thread
    //   49: dup
    //   50: new 268	com/google/ads/ae
    //   53: dup
    //   54: aload_3
    //   55: invokeinterface 271 1 0
    //   60: checkcast 273	java/lang/String
    //   63: aload_2
    //   64: invokevirtual 122	android/app/Activity:getApplicationContext	()Landroid/content/Context;
    //   67: invokespecial 283	com/google/ads/ae:<init>	(Ljava/lang/String;Landroid/content/Context;)V
    //   70: invokespecial 286	java/lang/Thread:<init>	(Ljava/lang/Runnable;)V
    //   73: invokevirtual 289	java/lang/Thread:start	()V
    //   76: goto -39 -> 37
    //   79: astore_1
    //   80: aload_0
    //   81: monitorexit
    //   82: aload_1
    //   83: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	84	0	this	d
    //   79	4	1	localObject	Object
    //   15	49	2	localActivity	Activity
    //   36	19	3	localIterator	Iterator
    // Exception table:
    //   from	to	target	type
    //   2	26	79	finally
    //   29	76	79	finally
  }
  
  /**
   * @deprecated
   */
  protected void E()
  {
    try
    {
      this.c = null;
      this.k = true;
      this.f.setVisibility(0);
      if (this.b.a()) {
        a(this.f);
      }
      this.e.g();
      if (this.b.a()) {
        y();
      }
      com.google.ads.util.b.c("onReceiveAd()");
      AdListener localAdListener = (AdListener)this.b.o.a();
      if (localAdListener != null) {
        localAdListener.onReceiveAd((Ad)this.b.a.a());
      }
      this.b.l.a(this.b.m.a());
      this.b.m.a(null);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public LinkedList<String> F()
  {
    return this.t;
  }
  
  /* Error */
  /**
   * @deprecated
   */
  public void a()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 116	com/google/ads/internal/d:b	Lcom/google/ads/n;
    //   6: getfield 432	com/google/ads/n:g	Lcom/google/ads/util/i$b;
    //   9: invokevirtual 186	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   12: checkcast 92	com/google/ads/internal/h
    //   15: invokevirtual 435	com/google/ads/internal/h:c	()Lcom/google/ads/AdSize;
    //   18: astore_2
    //   19: getstatic 437	com/google/ads/util/AdUtil:a	I
    //   22: bipush 14
    //   24: if_icmplt +139 -> 163
    //   27: new 439	com/google/ads/util/IcsUtil$IcsAdWebView
    //   30: dup
    //   31: aload_0
    //   32: getfield 116	com/google/ads/internal/d:b	Lcom/google/ads/n;
    //   35: aload_2
    //   36: invokespecial 442	com/google/ads/util/IcsUtil$IcsAdWebView:<init>	(Lcom/google/ads/n;Lcom/google/ads/AdSize;)V
    //   39: astore_3
    //   40: aload_0
    //   41: aload_3
    //   42: putfield 376	com/google/ads/internal/d:f	Lcom/google/ads/internal/AdWebView;
    //   45: aload_0
    //   46: getfield 376	com/google/ads/internal/d:f	Lcom/google/ads/internal/AdWebView;
    //   49: bipush 8
    //   51: invokevirtual 398	com/google/ads/internal/AdWebView:setVisibility	(I)V
    //   54: aload_0
    //   55: aload_0
    //   56: getstatic 447	com/google/ads/internal/a:d	Ljava/util/Map;
    //   59: iconst_1
    //   60: aload_0
    //   61: getfield 116	com/google/ads/internal/d:b	Lcom/google/ads/n;
    //   64: invokevirtual 334	com/google/ads/n:b	()Z
    //   67: invokestatic 452	com/google/ads/internal/i:a	(Lcom/google/ads/internal/d;Ljava/util/Map;ZZ)Lcom/google/ads/internal/i;
    //   70: putfield 454	com/google/ads/internal/d:g	Lcom/google/ads/internal/i;
    //   73: aload_0
    //   74: getfield 376	com/google/ads/internal/d:f	Lcom/google/ads/internal/AdWebView;
    //   77: aload_0
    //   78: getfield 454	com/google/ads/internal/d:g	Lcom/google/ads/internal/i;
    //   81: invokevirtual 458	com/google/ads/internal/AdWebView:setWebViewClient	(Landroid/webkit/WebViewClient;)V
    //   84: aload_0
    //   85: getfield 116	com/google/ads/internal/d:b	Lcom/google/ads/n;
    //   88: getfield 460	com/google/ads/n:d	Lcom/google/ads/util/i$b;
    //   91: invokevirtual 186	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   94: checkcast 102	com/google/ads/m
    //   97: getfield 462	com/google/ads/m:b	Lcom/google/ads/util/i$b;
    //   100: invokevirtual 186	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   103: checkcast 464	com/google/ads/m$a
    //   106: astore 4
    //   108: getstatic 437	com/google/ads/util/AdUtil:a	I
    //   111: aload 4
    //   113: getfield 466	com/google/ads/m$a:b	Lcom/google/ads/util/i$c;
    //   116: invokevirtual 411	com/google/ads/util/i$c:a	()Ljava/lang/Object;
    //   119: checkcast 468	java/lang/Integer
    //   122: invokevirtual 472	java/lang/Integer:intValue	()I
    //   125: if_icmpge +35 -> 160
    //   128: aload_0
    //   129: getfield 116	com/google/ads/internal/d:b	Lcom/google/ads/n;
    //   132: getfield 432	com/google/ads/n:g	Lcom/google/ads/util/i$b;
    //   135: invokevirtual 186	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   138: checkcast 92	com/google/ads/internal/h
    //   141: invokevirtual 473	com/google/ads/internal/h:a	()Z
    //   144: ifne +16 -> 160
    //   147: ldc_w 475
    //   150: invokestatic 320	com/google/ads/util/b:a	(Ljava/lang/String;)V
    //   153: aload_0
    //   154: getfield 376	com/google/ads/internal/d:f	Lcom/google/ads/internal/AdWebView;
    //   157: invokevirtual 476	com/google/ads/internal/AdWebView:g	()V
    //   160: aload_0
    //   161: monitorexit
    //   162: return
    //   163: new 378	com/google/ads/internal/AdWebView
    //   166: dup
    //   167: aload_0
    //   168: getfield 116	com/google/ads/internal/d:b	Lcom/google/ads/n;
    //   171: aload_2
    //   172: invokespecial 477	com/google/ads/internal/AdWebView:<init>	(Lcom/google/ads/n;Lcom/google/ads/AdSize;)V
    //   175: astore_3
    //   176: goto -136 -> 40
    //   179: astore_1
    //   180: aload_0
    //   181: monitorexit
    //   182: aload_1
    //   183: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	184	0	this	d
    //   179	4	1	localObject1	Object
    //   18	154	2	localAdSize	AdSize
    //   39	137	3	localObject2	Object
    //   106	6	4	locala	com.google.ads.m.a
    // Exception table:
    //   from	to	target	type
    //   2	160	179	finally
    //   163	176	179	finally
  }
  
  /**
   * @deprecated
   */
  public void a(float paramFloat)
  {
    try
    {
      long l1 = this.p;
      this.p = ((1000.0F * paramFloat));
      if ((t()) && (this.p != l1))
      {
        f();
        g();
      }
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
  public void a(int paramInt)
  {
    try
    {
      this.v = paramInt;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    ActivationOverlay localActivationOverlay = (ActivationOverlay)this.b.e.a();
    Context localContext1 = (Context)this.b.f.a();
    int i1;
    FrameLayout.LayoutParams localLayoutParams;
    int i3;
    if (paramInt3 < 0)
    {
      i1 = ((h)this.b.g.a()).c().getWidth();
      int i2 = AdUtil.a(localContext1, i1);
      Context localContext2 = (Context)this.b.f.a();
      if (paramInt4 < 0) {
        paramInt4 = ((h)this.b.g.a()).c().getHeight();
      }
      localLayoutParams = new FrameLayout.LayoutParams(i2, AdUtil.a(localContext2, paramInt4));
      if (paramInt3 >= 0) {
        break label279;
      }
      i3 = 0;
    }
    for (int i4 = 0;; i4 = paramInt1)
    {
      if (i4 < 0) {}
      for (int i5 = ((ActivationOverlay)this.b.e.a()).d();; i5 = i4)
      {
        if (i3 < 0) {
          i3 = ((ActivationOverlay)this.b.e.a()).c();
        }
        ((ActivationOverlay)this.b.e.a()).setXPosition(i5);
        ((ActivationOverlay)this.b.e.a()).setYPosition(i3);
        localLayoutParams.setMargins(AdUtil.a((Context)this.b.f.a(), i5), AdUtil.a((Context)this.b.f.a(), i3), 0, 0);
        localActivationOverlay.setLayoutParams(localLayoutParams);
        return;
        i1 = paramInt3;
        break;
      }
      label279:
      i3 = paramInt2;
    }
  }
  
  public void a(long paramLong)
  {
    synchronized (a)
    {
      SharedPreferences.Editor localEditor = this.o.edit();
      localEditor.putLong("Timeout" + this.b.h, paramLong);
      localEditor.commit();
      if (this.r) {
        this.i = paramLong;
      }
      return;
    }
  }
  
  public void a(View paramView)
  {
    ((ViewGroup)this.b.i.a()).setVisibility(0);
    ((ViewGroup)this.b.i.a()).removeAllViews();
    ((ViewGroup)this.b.i.a()).addView(paramView);
    if (((h)this.b.g.a()).b())
    {
      ((d)this.b.b.a()).a((l)this.b.l.a(), false, -1, -1, -1, -1);
      if (((ActivationOverlay)this.b.e.a()).a()) {
        ((ViewGroup)this.b.i.a()).addView((View)this.b.e.a(), AdUtil.a((Context)this.b.f.a(), ((h)this.b.g.a()).c().getWidth()), AdUtil.a((Context)this.b.f.a(), ((h)this.b.g.a()).c().getHeight()));
      }
    }
  }
  
  /**
   * @deprecated
   */
  public void a(View paramView, com.google.ads.h paramh, f paramf, boolean paramBoolean)
  {
    try
    {
      com.google.ads.util.b.a("AdManager.onReceiveGWhirlAd() called.");
      this.k = true;
      this.z = paramf;
      if (this.b.a())
      {
        a(paramView);
        a(paramf, Boolean.valueOf(paramBoolean));
      }
      this.y.d(paramh);
      AdListener localAdListener = (AdListener)this.b.o.a();
      if (localAdListener != null) {
        localAdListener.onReceiveAd((Ad)this.b.a.a());
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  /**
   * @deprecated
   */
  public void a(AdRequest.ErrorCode paramErrorCode)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aconst_null
    //   4: putfield 76	com/google/ads/internal/d:c	Lcom/google/ads/internal/c;
    //   7: aload_1
    //   8: getstatic 574	com/google/ads/AdRequest$ErrorCode:NETWORK_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   11: if_acmpne +21 -> 32
    //   14: aload_0
    //   15: ldc_w 575
    //   18: invokevirtual 577	com/google/ads/internal/d:a	(F)V
    //   21: aload_0
    //   22: invokevirtual 481	com/google/ads/internal/d:t	()Z
    //   25: ifne +7 -> 32
    //   28: aload_0
    //   29: invokevirtual 579	com/google/ads/internal/d:h	()V
    //   32: aload_0
    //   33: getfield 116	com/google/ads/internal/d:b	Lcom/google/ads/n;
    //   36: invokevirtual 334	com/google/ads/n:b	()Z
    //   39: ifeq +17 -> 56
    //   42: aload_1
    //   43: getstatic 582	com/google/ads/AdRequest$ErrorCode:NO_FILL	Lcom/google/ads/AdRequest$ErrorCode;
    //   46: if_acmpne +80 -> 126
    //   49: aload_0
    //   50: getfield 74	com/google/ads/internal/d:e	Lcom/google/ads/internal/g;
    //   53: invokevirtual 584	com/google/ads/internal/g:B	()V
    //   56: new 137	java/lang/StringBuilder
    //   59: dup
    //   60: invokespecial 138	java/lang/StringBuilder:<init>	()V
    //   63: ldc_w 586
    //   66: invokevirtual 144	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   69: aload_1
    //   70: invokevirtual 528	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   73: ldc_w 588
    //   76: invokevirtual 144	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: invokevirtual 148	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   82: invokestatic 311	com/google/ads/util/b:c	(Ljava/lang/String;)V
    //   85: aload_0
    //   86: getfield 116	com/google/ads/internal/d:b	Lcom/google/ads/n;
    //   89: getfield 408	com/google/ads/n:o	Lcom/google/ads/util/i$c;
    //   92: invokevirtual 411	com/google/ads/util/i$c:a	()Ljava/lang/Object;
    //   95: checkcast 413	com/google/ads/AdListener
    //   98: astore_3
    //   99: aload_3
    //   100: ifnull +23 -> 123
    //   103: aload_3
    //   104: aload_0
    //   105: getfield 116	com/google/ads/internal/d:b	Lcom/google/ads/n;
    //   108: getfield 415	com/google/ads/n:a	Lcom/google/ads/util/i$b;
    //   111: invokevirtual 186	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   114: checkcast 417	com/google/ads/Ad
    //   117: aload_1
    //   118: invokeinterface 592 3 0
    //   123: aload_0
    //   124: monitorexit
    //   125: return
    //   126: aload_1
    //   127: getstatic 574	com/google/ads/AdRequest$ErrorCode:NETWORK_ERROR	Lcom/google/ads/AdRequest$ErrorCode;
    //   130: if_acmpne -74 -> 56
    //   133: aload_0
    //   134: getfield 74	com/google/ads/internal/d:e	Lcom/google/ads/internal/g;
    //   137: invokevirtual 594	com/google/ads/internal/g:z	()V
    //   140: goto -84 -> 56
    //   143: astore_2
    //   144: aload_0
    //   145: monitorexit
    //   146: aload_2
    //   147: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	148	0	this	d
    //   0	148	1	paramErrorCode	AdRequest.ErrorCode
    //   143	4	2	localObject	Object
    //   98	6	3	localAdListener	AdListener
    // Exception table:
    //   from	to	target	type
    //   2	123	143	finally
    //   126	140	143	finally
  }
  
  /**
   * @deprecated
   */
  public void a(AdRequest paramAdRequest)
  {
    for (;;)
    {
      try
      {
        com.google.ads.util.b.d("v6.4.1 RC00");
        if (this.h)
        {
          com.google.ads.util.b.e("loadAd called after ad was destroyed.");
          return;
        }
        if (q())
        {
          com.google.ads.util.b.e("loadAd called while the ad is already loading, so aborting.");
          continue;
        }
        if (!AdActivity.isShowing()) {
          break label60;
        }
      }
      finally {}
      com.google.ads.util.b.e("loadAd called while an interstitial or landing page is displayed, so aborting");
      continue;
      label60:
      if ((AdUtil.c((Context)this.b.f.a())) && (AdUtil.b((Context)this.b.f.a())))
      {
        long l1 = this.o.getLong("GoogleAdMobDoritosLife", 60000L);
        if (at.a((Context)this.b.f.a(), l1)) {
          at.a((Activity)this.b.c.a());
        }
        this.k = false;
        this.s.clear();
        this.t.clear();
        this.d = paramAdRequest;
        if (this.x.a())
        {
          this.y.a(this.x.b(), paramAdRequest);
        }
        else
        {
          l locall = new l(this.b);
          this.b.m.a(locall);
          this.c = ((c)locall.b.a());
          this.c.a(paramAdRequest);
        }
      }
    }
  }
  
  /**
   * @deprecated
   */
  public void a(com.google.ads.c paramc)
  {
    try
    {
      this.c = null;
      this.y.a(paramc, this.d);
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
  public void a(f paramf, boolean paramBoolean)
  {
    try
    {
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(paramBoolean);
      com.google.ads.util.b.a(String.format(localLocale, "AdManager.onGWhirlAdClicked(%b) called.", arrayOfObject));
      b(paramf, Boolean.valueOf(paramBoolean));
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void a(l paraml, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    ActivationOverlay localActivationOverlay = (ActivationOverlay)this.b.e.a();
    boolean bool;
    if (!paramBoolean)
    {
      bool = true;
      localActivationOverlay.setOverlayActivated(bool);
      a(paramInt1, paramInt2, paramInt3, paramInt4);
      if (this.b.q.a() != null)
      {
        if (!paramBoolean) {
          break label95;
        }
        ((SwipeableAdListener)this.b.q.a()).onAdActivated((Ad)this.b.a.a());
      }
    }
    for (;;)
    {
      return;
      bool = false;
      break;
      label95:
      ((SwipeableAdListener)this.b.q.a()).onAdDeactivated((Ad)this.b.a.a());
    }
  }
  
  public void a(String paramString)
  {
    this.B = paramString;
    Uri localUri = new Uri.Builder().encodedQuery(paramString).build();
    StringBuilder localStringBuilder = new StringBuilder();
    HashMap localHashMap = AdUtil.b(localUri);
    Iterator localIterator = localHashMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localStringBuilder.append(str).append(" = ").append((String)localHashMap.get(str)).append("\n");
    }
    this.A = localStringBuilder.toString().trim();
    if (TextUtils.isEmpty(this.A)) {
      this.A = null;
    }
  }
  
  /**
   * @deprecated
   */
  public void a(String paramString1, String paramString2)
  {
    try
    {
      AppEventListener localAppEventListener = (AppEventListener)this.b.p.a();
      if (localAppEventListener != null) {
        localAppEventListener.onAppEvent((Ad)this.b.a.a(), paramString1, paramString2);
      }
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
  protected void a(LinkedList<String> paramLinkedList)
  {
    try
    {
      Iterator localIterator = paramLinkedList.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        com.google.ads.util.b.a("Adding a click tracking URL: " + str);
      }
      this.u = paramLinkedList;
    }
    finally {}
  }
  
  /**
   * @deprecated
   */
  public void a(boolean paramBoolean)
  {
    try
    {
      this.j = paramBoolean;
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
  public void b()
  {
    try
    {
      if (this.y != null) {
        this.y.b();
      }
      this.b.o.a(null);
      this.b.p.a(null);
      C();
      f();
      if (this.f != null) {
        this.f.destroy();
      }
      this.h = true;
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
  public void b(long paramLong)
  {
    if (paramLong > 0L) {}
    try
    {
      this.o.edit().putLong("GoogleAdMobDoritosLife", paramLong).commit();
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
  public void b(com.google.ads.c paramc)
  {
    try
    {
      com.google.ads.util.b.a("AdManager.onGWhirlNoFill() called.");
      a(paramc.i(), paramc.c());
      AdListener localAdListener = (AdListener)this.b.o.a();
      if (localAdListener != null) {
        localAdListener.onFailedToReceiveAd((Ad)this.b.a.a(), AdRequest.ErrorCode.NO_FILL);
      }
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
  protected void b(String paramString)
  {
    try
    {
      com.google.ads.util.b.a("Adding a tracking URL: " + paramString);
      this.s.add(paramString);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void b(boolean paramBoolean)
  {
    this.w = Boolean.valueOf(paramBoolean);
  }
  
  public String c()
  {
    return this.A;
  }
  
  /**
   * @deprecated
   */
  protected void c(String paramString)
  {
    try
    {
      com.google.ads.util.b.a("Adding a manual tracking URL: " + paramString);
      F().add(paramString);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public String d()
  {
    return this.B;
  }
  
  /**
   * @deprecated
   */
  public void e()
  {
    try
    {
      this.m = false;
      com.google.ads.util.b.a("Refreshing is no longer allowed on this AdView.");
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  /**
   * @deprecated
   */
  public void f()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 86	com/google/ads/internal/d:l	Z
    //   6: ifeq +36 -> 42
    //   9: ldc_w 750
    //   12: invokestatic 320	com/google/ads/util/b:a	(Ljava/lang/String;)V
    //   15: invokestatic 105	com/google/ads/m:a	()Lcom/google/ads/m;
    //   18: getfield 322	com/google/ads/m:c	Lcom/google/ads/util/i$b;
    //   21: invokevirtual 186	com/google/ads/util/i$b:a	()Ljava/lang/Object;
    //   24: checkcast 324	android/os/Handler
    //   27: aload_0
    //   28: getfield 167	com/google/ads/internal/d:q	Lcom/google/ads/af;
    //   31: invokevirtual 753	android/os/Handler:removeCallbacks	(Ljava/lang/Runnable;)V
    //   34: aload_0
    //   35: iconst_0
    //   36: putfield 86	com/google/ads/internal/d:l	Z
    //   39: aload_0
    //   40: monitorexit
    //   41: return
    //   42: ldc_w 755
    //   45: invokestatic 320	com/google/ads/util/b:a	(Ljava/lang/String;)V
    //   48: goto -9 -> 39
    //   51: astore_1
    //   52: aload_0
    //   53: monitorexit
    //   54: aload_1
    //   55: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	56	0	this	d
    //   51	4	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	39	51	finally
    //   42	48	51	finally
  }
  
  /**
   * @deprecated
   */
  public void g()
  {
    for (;;)
    {
      try
      {
        this.n = false;
        if (!this.b.a()) {
          break label118;
        }
        if (this.m)
        {
          if (!this.l)
          {
            com.google.ads.util.b.a("Enabling refreshing every " + this.p + " milliseconds.");
            ((Handler)m.a().c.a()).postDelayed(this.q, this.p);
            this.l = true;
            return;
          }
          com.google.ads.util.b.a("Refreshing is already enabled.");
          continue;
        }
        com.google.ads.util.b.a("Refreshing disabled on this AdView");
      }
      finally {}
      continue;
      label118:
      com.google.ads.util.b.a("Tried to enable refreshing on something other than an AdView.");
    }
  }
  
  public void h()
  {
    g();
    this.n = true;
  }
  
  public n i()
  {
    return this.b;
  }
  
  /**
   * @deprecated
   */
  public com.google.ads.d j()
  {
    try
    {
      com.google.ads.d locald = this.x;
      return locald;
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
  public c k()
  {
    try
    {
      c localc = this.c;
      return localc;
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
  public AdWebView l()
  {
    try
    {
      AdWebView localAdWebView = this.f;
      return localAdWebView;
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
  public i m()
  {
    try
    {
      i locali = this.g;
      return locali;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public g n()
  {
    return this.e;
  }
  
  /**
   * @deprecated
   */
  public int o()
  {
    try
    {
      int i1 = this.v;
      return i1;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public long p()
  {
    return this.i;
  }
  
  /* Error */
  /**
   * @deprecated
   */
  public boolean q()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 76	com/google/ads/internal/d:c	Lcom/google/ads/internal/c;
    //   6: astore_2
    //   7: aload_2
    //   8: ifnull +9 -> 17
    //   11: iconst_1
    //   12: istore_3
    //   13: aload_0
    //   14: monitorexit
    //   15: iload_3
    //   16: ireturn
    //   17: iconst_0
    //   18: istore_3
    //   19: goto -6 -> 13
    //   22: astore_1
    //   23: aload_0
    //   24: monitorexit
    //   25: aload_1
    //   26: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	27	0	this	d
    //   22	4	1	localObject	Object
    //   6	2	2	localc	c
    //   12	7	3	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	7	22	finally
  }
  
  /**
   * @deprecated
   */
  public boolean r()
  {
    try
    {
      boolean bool = this.j;
      return bool;
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
  public boolean s()
  {
    try
    {
      boolean bool = this.k;
      return bool;
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
  public boolean t()
  {
    try
    {
      boolean bool = this.l;
      return bool;
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
  public void u()
  {
    try
    {
      this.e.C();
      com.google.ads.util.b.c("onDismissScreen()");
      AdListener localAdListener = (AdListener)this.b.o.a();
      if (localAdListener != null) {
        localAdListener.onDismissScreen((Ad)this.b.a.a());
      }
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
  public void v()
  {
    try
    {
      com.google.ads.util.b.c("onPresentScreen()");
      AdListener localAdListener = (AdListener)this.b.o.a();
      if (localAdListener != null) {
        localAdListener.onPresentScreen((Ad)this.b.a.a());
      }
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
  public void w()
  {
    try
    {
      com.google.ads.util.b.c("onLeaveApplication()");
      AdListener localAdListener = (AdListener)this.b.o.a();
      if (localAdListener != null) {
        localAdListener.onLeaveApplication((Ad)this.b.a.a());
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void x()
  {
    this.e.f();
    D();
  }
  
  /* Error */
  /**
   * @deprecated
   */
  public void y()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 116	com/google/ads/internal/d:b	Lcom/google/ads/n;
    //   6: getfield 385	com/google/ads/n:c	Lcom/google/ads/util/i$d;
    //   9: invokevirtual 388	com/google/ads/util/i$d:a	()Ljava/lang/Object;
    //   12: checkcast 118	android/app/Activity
    //   15: astore_2
    //   16: aload_2
    //   17: ifnonnull +12 -> 29
    //   20: ldc_w 796
    //   23: invokestatic 392	com/google/ads/util/b:e	(Ljava/lang/String;)V
    //   26: aload_0
    //   27: monitorexit
    //   28: return
    //   29: aload_0
    //   30: getfield 172	com/google/ads/internal/d:s	Ljava/util/LinkedList;
    //   33: invokevirtual 393	java/util/LinkedList:iterator	()Ljava/util/Iterator;
    //   36: astore_3
    //   37: aload_3
    //   38: invokeinterface 264 1 0
    //   43: ifeq -17 -> 26
    //   46: new 266	java/lang/Thread
    //   49: dup
    //   50: new 268	com/google/ads/ae
    //   53: dup
    //   54: aload_3
    //   55: invokeinterface 271 1 0
    //   60: checkcast 273	java/lang/String
    //   63: aload_2
    //   64: invokevirtual 122	android/app/Activity:getApplicationContext	()Landroid/content/Context;
    //   67: invokespecial 283	com/google/ads/ae:<init>	(Ljava/lang/String;Landroid/content/Context;)V
    //   70: invokespecial 286	java/lang/Thread:<init>	(Ljava/lang/Runnable;)V
    //   73: invokevirtual 289	java/lang/Thread:start	()V
    //   76: goto -39 -> 37
    //   79: astore_1
    //   80: aload_0
    //   81: monitorexit
    //   82: aload_1
    //   83: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	84	0	this	d
    //   79	4	1	localObject	Object
    //   15	49	2	localActivity	Activity
    //   36	19	3	localIterator	Iterator
    // Exception table:
    //   from	to	target	type
    //   2	26	79	finally
    //   29	76	79	finally
  }
  
  /* Error */
  /**
   * @deprecated
   */
  public void z()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 116	com/google/ads/internal/d:b	Lcom/google/ads/n;
    //   6: getfield 385	com/google/ads/n:c	Lcom/google/ads/util/i$d;
    //   9: invokevirtual 388	com/google/ads/util/i$d:a	()Ljava/lang/Object;
    //   12: checkcast 118	android/app/Activity
    //   15: astore_2
    //   16: aload_2
    //   17: ifnonnull +12 -> 29
    //   20: ldc_w 798
    //   23: invokestatic 392	com/google/ads/util/b:e	(Ljava/lang/String;)V
    //   26: aload_0
    //   27: monitorexit
    //   28: return
    //   29: aload_0
    //   30: getfield 174	com/google/ads/internal/d:t	Ljava/util/LinkedList;
    //   33: invokevirtual 393	java/util/LinkedList:iterator	()Ljava/util/Iterator;
    //   36: astore_3
    //   37: aload_3
    //   38: invokeinterface 264 1 0
    //   43: ifeq -17 -> 26
    //   46: new 266	java/lang/Thread
    //   49: dup
    //   50: new 268	com/google/ads/ae
    //   53: dup
    //   54: aload_3
    //   55: invokeinterface 271 1 0
    //   60: checkcast 273	java/lang/String
    //   63: aload_2
    //   64: invokevirtual 122	android/app/Activity:getApplicationContext	()Landroid/content/Context;
    //   67: invokespecial 283	com/google/ads/ae:<init>	(Ljava/lang/String;Landroid/content/Context;)V
    //   70: invokespecial 286	java/lang/Thread:<init>	(Ljava/lang/Runnable;)V
    //   73: invokevirtual 289	java/lang/Thread:start	()V
    //   76: goto -39 -> 37
    //   79: astore_1
    //   80: aload_0
    //   81: monitorexit
    //   82: aload_1
    //   83: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	84	0	this	d
    //   79	4	1	localObject	Object
    //   15	49	2	localActivity	Activity
    //   36	19	3	localIterator	Iterator
    // Exception table:
    //   from	to	target	type
    //   2	26	79	finally
    //   29	76	79	finally
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.internal.d
 * JD-Core Version:    0.7.0.1
 */