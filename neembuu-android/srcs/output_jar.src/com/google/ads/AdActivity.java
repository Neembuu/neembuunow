package com.google.ads;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.google.ads.internal.AdVideoView;
import com.google.ads.internal.AdWebView;
import com.google.ads.internal.a;
import com.google.ads.internal.d;
import com.google.ads.internal.i;
import com.google.ads.util.AdUtil;
import com.google.ads.util.b;
import com.google.ads.util.f;
import com.google.ads.util.g;
import com.google.ads.util.i.b;
import com.google.ads.util.i.c;
import com.google.ads.util.i.d;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdActivity
  extends Activity
  implements View.OnClickListener
{
  public static final String BASE_URL_PARAM = "baseurl";
  public static final String COMPONENT_NAME_PARAM = "c";
  public static final String CUSTOM_CLOSE_PARAM = "custom_close";
  public static final String HTML_PARAM = "html";
  public static final String INTENT_ACTION_PARAM = "i";
  public static final String INTENT_EXTRAS_PARAM = "e";
  public static final String INTENT_FLAGS_PARAM = "f";
  public static final String ORIENTATION_PARAM = "o";
  public static final String PACKAGE_NAME_PARAM = "p";
  public static final String TYPE_PARAM = "m";
  public static final String URL_PARAM = "u";
  private static final a a = (a)a.a.b();
  private static final Object b = new Object();
  private static AdActivity c = null;
  private static d d = null;
  private static AdActivity e = null;
  private static AdActivity f = null;
  private static final StaticMethodWrapper g = new StaticMethodWrapper();
  private AdWebView h;
  private FrameLayout i;
  private int j;
  private ViewGroup k = null;
  private boolean l;
  private long m;
  private RelativeLayout n;
  private AdActivity o = null;
  private boolean p;
  private boolean q;
  private boolean r;
  private boolean s;
  private AdVideoView t;
  
  private RelativeLayout.LayoutParams a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(paramInt3, paramInt4);
    localLayoutParams.setMargins(paramInt1, paramInt2, 0, 0);
    localLayoutParams.addRule(10);
    localLayoutParams.addRule(9);
    return localLayoutParams;
  }
  
  private void a(String paramString)
  {
    b.b(paramString);
    finish();
  }
  
  private void a(String paramString, Throwable paramThrowable)
  {
    b.b(paramString, paramThrowable);
    finish();
  }
  
  private void e()
  {
    if (!this.l) {
      if (this.h != null)
      {
        a.b(this.h);
        this.h.setAdActivity(null);
        this.h.setIsExpandedMraid(false);
        if ((!this.q) && (this.n != null) && (this.k != null))
        {
          if ((!this.r) || (this.s)) {
            break label238;
          }
          b.a("Disabling hardware acceleration on collapsing MRAID WebView.");
          this.h.g();
        }
      }
    }
    for (;;)
    {
      this.n.removeView(this.h);
      this.k.addView(this.h);
      if (this.t != null)
      {
        this.t.e();
        this.t = null;
      }
      if (this == c) {
        c = null;
      }
      f = this.o;
      synchronized (b)
      {
        if ((d != null) && (this.q) && (this.h != null))
        {
          if (this.h == d.l()) {
            d.a();
          }
          this.h.stopLoading();
        }
        if (this == e)
        {
          e = null;
          if (d != null)
          {
            d.u();
            d = null;
          }
        }
        else
        {
          this.l = true;
          b.a("AdActivity is closing.");
          return;
          label238:
          if ((this.r) || (!this.s)) {
            continue;
          }
          b.a("Re-enabling hardware acceleration on collapsing MRAID WebView.");
          this.h.h();
          continue;
        }
        b.e("currentAdManager is null while trying to destroy AdActivity.");
      }
    }
  }
  
  public static boolean isShowing()
  {
    return g.isShowing();
  }
  
  public static void launchAdActivity(d paramd, com.google.ads.internal.e parame)
  {
    g.launchAdActivity(paramd, parame);
  }
  
  public static boolean leftApplication()
  {
    return g.leftApplication();
  }
  
  protected View a(int paramInt, boolean paramBoolean)
  {
    this.j = ((int)TypedValue.applyDimension(1, paramInt, getResources().getDisplayMetrics()));
    this.i = new FrameLayout(getApplicationContext());
    this.i.setMinimumWidth(this.j);
    this.i.setMinimumHeight(this.j);
    this.i.setOnClickListener(this);
    setCustomClose(paramBoolean);
    return this.i;
  }
  
  protected AdVideoView a(Activity paramActivity)
  {
    return new AdVideoView(paramActivity, this.h);
  }
  
  protected void a(AdWebView paramAdWebView, boolean paramBoolean1, int paramInt, boolean paramBoolean2, boolean paramBoolean3)
  {
    requestWindowFeature(1);
    Window localWindow = getWindow();
    localWindow.setFlags(1024, 1024);
    if (AdUtil.a >= 11)
    {
      if (this.r)
      {
        b.a("Enabling hardware acceleration on the AdActivity window.");
        g.a(localWindow);
      }
    }
    else
    {
      ViewParent localViewParent = paramAdWebView.getParent();
      if (localViewParent != null)
      {
        if (!paramBoolean2) {
          break label128;
        }
        if (!(localViewParent instanceof ViewGroup)) {
          break label118;
        }
        this.k = ((ViewGroup)localViewParent);
        this.k.removeView(paramAdWebView);
      }
      if (paramAdWebView.i() == null) {
        break label138;
      }
      a("Interstitial created with an AdWebView that is already in use by another AdActivity.");
    }
    for (;;)
    {
      return;
      b.a("Disabling hardware acceleration on the AdActivity WebView.");
      paramAdWebView.g();
      break;
      label118:
      a("MRAID banner was not a child of a ViewGroup.");
      continue;
      label128:
      a("Interstitial created with an AdWebView that has a parent.");
    }
    label138:
    setRequestedOrientation(paramInt);
    paramAdWebView.setAdActivity(this);
    int i1;
    label157:
    View localView;
    RelativeLayout.LayoutParams localLayoutParams;
    if (paramBoolean2)
    {
      i1 = 50;
      localView = a(i1, paramBoolean3);
      this.n.addView(paramAdWebView, -1, -1);
      localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
      if (!paramBoolean2) {
        break label272;
      }
      localLayoutParams.addRule(10);
      localLayoutParams.addRule(11);
    }
    for (;;)
    {
      this.n.addView(localView, localLayoutParams);
      this.n.setKeepScreenOn(true);
      setContentView(this.n);
      this.n.getRootView().setBackgroundColor(-16777216);
      if (!paramBoolean1) {
        break;
      }
      a.a(paramAdWebView);
      break;
      i1 = 32;
      break label157;
      label272:
      localLayoutParams.addRule(10);
      localLayoutParams.addRule(9);
    }
  }
  
  protected void a(d paramd)
  {
    this.h = null;
    this.m = SystemClock.elapsedRealtime();
    this.p = true;
    synchronized (b)
    {
      if (c == null)
      {
        c = this;
        paramd.w();
      }
      return;
    }
  }
  
  protected void a(HashMap<String, String> paramHashMap, d paramd)
  {
    int i1 = 0;
    if (paramHashMap == null) {
      a("Could not get the paramMap in launchIntent()");
    }
    for (;;)
    {
      return;
      Intent localIntent = new Intent();
      String str1 = (String)paramHashMap.get("u");
      String str2 = (String)paramHashMap.get("m");
      String str3 = (String)paramHashMap.get("i");
      String str4 = (String)paramHashMap.get("p");
      String str5 = (String)paramHashMap.get("c");
      String str6 = (String)paramHashMap.get("f");
      String str7 = (String)paramHashMap.get("e");
      int i2;
      int i3;
      if (!TextUtils.isEmpty(str1))
      {
        i2 = 1;
        if (TextUtils.isEmpty(str2)) {
          break label425;
        }
        i3 = 1;
        label122:
        if ((i2 == 0) || (i3 == 0)) {
          break label431;
        }
        localIntent.setDataAndType(Uri.parse(str1), str2);
        label145:
        if (TextUtils.isEmpty(str3)) {
          break label466;
        }
        localIntent.setAction(str3);
        label161:
        if ((!TextUtils.isEmpty(str4)) && (AdUtil.a >= 4)) {
          com.google.ads.util.e.a(localIntent, str4);
        }
        if (!TextUtils.isEmpty(str5))
        {
          String[] arrayOfString = str5.split("/");
          if (arrayOfString.length < 2) {
            b.e("Warning: Could not parse component name from open GMSG: " + str5);
          }
          localIntent.setClassName(arrayOfString[0], arrayOfString[1]);
        }
        if (TextUtils.isEmpty(str6)) {}
      }
      try
      {
        for (;;)
        {
          int i6 = Integer.parseInt(str6);
          int i5 = i6;
          localIntent.addFlags(i5);
          if (TextUtils.isEmpty(str7)) {
            break label560;
          }
          try
          {
            JSONObject localJSONObject1 = new JSONObject(str7);
            JSONArray localJSONArray = localJSONObject1.names();
            while (i1 < localJSONArray.length())
            {
              str8 = localJSONArray.getString(i1);
              localJSONObject2 = localJSONObject1.getJSONObject(str8);
              int i4 = localJSONObject2.getInt("t");
              switch (i4)
              {
              default: 
                b.e("Warning: Unknown type in extras from open GMSG: " + str8 + " (type: " + i4 + ")");
                i1++;
              }
            }
            i2 = 0;
          }
          catch (JSONException localJSONException)
          {
            label425:
            label431:
            label466:
            b.e("Warning: Could not parse extras from open GMSG: " + str7);
          }
        }
        i3 = 0;
        break label122;
        if (i2 != 0)
        {
          localIntent.setData(Uri.parse(str1));
          break label145;
        }
        if (i3 == 0) {
          break label145;
        }
        localIntent.setType(str2);
        break label145;
        if (i2 == 0) {
          break label161;
        }
        localIntent.setAction("android.intent.action.VIEW");
      }
      catch (NumberFormatException localNumberFormatException)
      {
        for (;;)
        {
          String str8;
          JSONObject localJSONObject2;
          b.e("Warning: Could not parse flags from open GMSG: " + str6);
          i5 = 0;
          continue;
          localIntent.putExtra(str8, localJSONObject2.getBoolean("v"));
          continue;
          label560:
          if (!localIntent.filterEquals(new Intent())) {
            break label661;
          }
          a("Tried to launch empty intent.");
          break;
          localIntent.putExtra(str8, localJSONObject2.getDouble("v"));
          continue;
          localIntent.putExtra(str8, localJSONObject2.getInt("v"));
          continue;
          localIntent.putExtra(str8, localJSONObject2.getLong("v"));
          continue;
          localIntent.putExtra(str8, localJSONObject2.getString("v"));
        }
        try
        {
          label661:
          b.a("Launching an intent from AdActivity: " + localIntent);
          startActivity(localIntent);
          a(paramd);
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          a(localActivityNotFoundException.getMessage(), localActivityNotFoundException);
        }
      }
    }
  }
  
  public AdVideoView getAdVideoView()
  {
    return this.t;
  }
  
  /* Error */
  public AdWebView getOpeningAdWebView()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: getfield 106	com/google/ads/AdActivity:o	Lcom/google/ads/AdActivity;
    //   6: ifnull +13 -> 19
    //   9: aload_0
    //   10: getfield 106	com/google/ads/AdActivity:o	Lcom/google/ads/AdActivity;
    //   13: getfield 138	com/google/ads/AdActivity:h	Lcom/google/ads/internal/AdWebView;
    //   16: astore_1
    //   17: aload_1
    //   18: areturn
    //   19: getstatic 90	com/google/ads/AdActivity:b	Ljava/lang/Object;
    //   22: astore_2
    //   23: aload_2
    //   24: monitorenter
    //   25: getstatic 94	com/google/ads/AdActivity:d	Lcom/google/ads/internal/d;
    //   28: ifnonnull +19 -> 47
    //   31: ldc_w 531
    //   34: invokestatic 204	com/google/ads/util/b:e	(Ljava/lang/String;)V
    //   37: aload_2
    //   38: monitorexit
    //   39: goto -22 -> 17
    //   42: astore_3
    //   43: aload_2
    //   44: monitorexit
    //   45: aload_3
    //   46: athrow
    //   47: getstatic 94	com/google/ads/AdActivity:d	Lcom/google/ads/internal/d;
    //   50: invokevirtual 187	com/google/ads/internal/d:l	()Lcom/google/ads/internal/AdWebView;
    //   53: astore 4
    //   55: aload 4
    //   57: aload_0
    //   58: getfield 138	com/google/ads/AdActivity:h	Lcom/google/ads/internal/AdWebView;
    //   61: if_acmpeq +11 -> 72
    //   64: aload_2
    //   65: monitorexit
    //   66: aload 4
    //   68: astore_1
    //   69: goto -52 -> 17
    //   72: aload_2
    //   73: monitorexit
    //   74: goto -57 -> 17
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	77	0	this	AdActivity
    //   1	68	1	localObject1	Object
    //   22	51	2	localObject2	Object
    //   42	4	3	localObject3	Object
    //   53	14	4	localAdWebView	AdWebView
    // Exception table:
    //   from	to	target	type
    //   25	45	42	finally
    //   47	74	42	finally
  }
  
  public void moveAdVideoView(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.t != null)
    {
      this.t.setLayoutParams(a(paramInt1, paramInt2, paramInt3, paramInt4));
      this.t.requestLayout();
    }
  }
  
  public void newAdVideoView(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.t == null)
    {
      this.t = a(this);
      this.n.addView(this.t, 0, a(paramInt1, paramInt2, paramInt3, paramInt4));
      synchronized (b)
      {
        if (d == null) {
          b.e("currentAdManager was null while trying to get the opening AdWebView.");
        } else {
          d.m().b(false);
        }
      }
    }
  }
  
  public void onClick(View paramView)
  {
    finish();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    boolean bool1 = false;
    super.onCreate(paramBundle);
    this.l = false;
    d locald;
    boolean bool2;
    label274:
    label280:
    String str1;
    HashMap localHashMap;
    for (;;)
    {
      Bundle localBundle;
      synchronized (b)
      {
        if (d != null)
        {
          locald = d;
          if (e == null)
          {
            e = this;
            locald.v();
          }
          if ((this.o == null) && (f != null)) {
            this.o = f;
          }
          f = this;
          if (((locald.i().a()) && (e == this)) || ((locald.i().b()) && (this.o == e))) {
            locald.x();
          }
          bool2 = locald.r();
          m.a locala = (m.a)((m)locald.i().d.a()).b.a();
          if (AdUtil.a >= ((Integer)locala.b.a()).intValue())
          {
            bool3 = true;
            this.s = bool3;
            if (AdUtil.a < ((Integer)locala.d.a()).intValue()) {
              break label274;
            }
            bool4 = true;
            this.r = bool4;
            this.n = null;
            this.p = false;
            this.q = true;
            this.t = null;
            localBundle = getIntent().getBundleExtra("com.google.ads.AdOpener");
            if (localBundle != null) {
              break label280;
            }
            a("Could not get the Bundle used to create AdActivity.");
          }
        }
        else
        {
          a("Could not get currentAdManager.");
        }
      }
      boolean bool3 = false;
      continue;
      boolean bool4 = false;
      continue;
      com.google.ads.internal.e locale = new com.google.ads.internal.e(localBundle);
      str1 = locale.b();
      localHashMap = locale.c();
      if (!str1.equals("intent")) {
        break;
      }
      a(localHashMap, locald);
    }
    this.n = new RelativeLayout(getApplicationContext());
    if (str1.equals("webapp"))
    {
      this.h = new AdWebView(locald.i(), null);
      Map localMap = a.d;
      boolean bool6;
      label383:
      String str3;
      String str4;
      label471:
      String str5;
      int i2;
      label498:
      AdWebView localAdWebView;
      if (!bool2)
      {
        bool6 = true;
        i locali = i.a(locald, localMap, true, bool6);
        locali.d(true);
        if (bool2) {
          locali.a(true);
        }
        this.h.setWebViewClient(locali);
        String str2 = (String)localHashMap.get("u");
        str3 = (String)localHashMap.get("baseurl");
        str4 = (String)localHashMap.get("html");
        if (str2 == null) {
          break label550;
        }
        this.h.loadUrl(str2);
        str5 = (String)localHashMap.get("o");
        if (!"p".equals(str5)) {
          break label586;
        }
        i2 = AdUtil.b();
        localAdWebView = this.h;
        if ((localHashMap == null) || (!"1".equals(localHashMap.get("custom_close")))) {
          break label629;
        }
      }
      label550:
      label586:
      label629:
      for (boolean bool7 = true;; bool7 = false)
      {
        a(localAdWebView, false, i2, bool2, bool7);
        break;
        bool6 = false;
        break label383;
        if (str4 != null)
        {
          this.h.loadDataWithBaseURL(str3, str4, "text/html", "utf-8", null);
          break label471;
        }
        a("Could not get the URL or HTML parameter to show a web app.");
        break;
        if ("l".equals(str5))
        {
          i2 = AdUtil.a();
          break label498;
        }
        if (this == e)
        {
          i2 = locald.o();
          break label498;
        }
        i2 = -1;
        break label498;
      }
    }
    int i1;
    boolean bool5;
    if ((str1.equals("interstitial")) || (str1.equals("expand")))
    {
      this.h = locald.l();
      i1 = locald.o();
      if (str1.equals("expand"))
      {
        this.h.setIsExpandedMraid(true);
        this.q = false;
        if ((localHashMap != null) && ("1".equals(localHashMap.get("custom_close")))) {
          bool1 = true;
        }
        if ((!this.r) || (this.s)) {
          break label814;
        }
        b.a("Re-enabling hardware acceleration on expanding MRAID WebView.");
        this.h.h();
        bool5 = bool1;
      }
    }
    for (;;)
    {
      a(this.h, true, i1, bool2, bool5);
      break;
      bool5 = this.h.j();
      continue;
      a("Unknown AdOpener, <action: " + str1 + ">");
      break;
      label814:
      bool5 = bool1;
    }
  }
  
  public void onDestroy()
  {
    if (this.n != null) {
      this.n.removeAllViews();
    }
    if (isFinishing())
    {
      e();
      if ((this.q) && (this.h != null))
      {
        this.h.stopLoading();
        this.h.destroy();
        this.h = null;
      }
    }
    super.onDestroy();
  }
  
  public void onPause()
  {
    if (isFinishing()) {
      e();
    }
    super.onPause();
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    if ((this.p) && (paramBoolean) && (SystemClock.elapsedRealtime() - this.m > 250L))
    {
      b.d("Launcher AdActivity got focus and is closing.");
      finish();
    }
    super.onWindowFocusChanged(paramBoolean);
  }
  
  public void setCustomClose(boolean paramBoolean)
  {
    if (this.i != null)
    {
      this.i.removeAllViews();
      if (!paramBoolean)
      {
        ImageButton localImageButton = new ImageButton(this);
        localImageButton.setImageResource(17301527);
        localImageButton.setBackgroundColor(0);
        localImageButton.setOnClickListener(this);
        localImageButton.setPadding(0, 0, 0, 0);
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(this.j, this.j, 17);
        this.i.addView(localImageButton, localLayoutParams);
      }
    }
  }
  
  public static class StaticMethodWrapper
  {
    public boolean isShowing()
    {
      for (;;)
      {
        synchronized ()
        {
          if (AdActivity.b() != null)
          {
            bool = true;
            return bool;
          }
        }
        boolean bool = false;
      }
    }
    
    public void launchAdActivity(d paramd, com.google.ads.internal.e parame)
    {
      for (;;)
      {
        Activity localActivity;
        synchronized ()
        {
          if (AdActivity.d() == null)
          {
            AdActivity.b(paramd);
            localActivity = (Activity)paramd.i().c.a();
            if (localActivity == null) {
              b.e("activity was null while launching an AdActivity.");
            }
          }
          else
          {
            if (AdActivity.d() == paramd) {
              continue;
            }
            b.b("Tried to launch a new AdActivity with a different AdManager.");
          }
        }
        Intent localIntent = new Intent(localActivity.getApplicationContext(), AdActivity.class);
        localIntent.putExtra("com.google.ads.AdOpener", parame.a());
        try
        {
          b.a("Launching AdActivity.");
          localActivity.startActivity(localIntent);
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          b.b("Activity not found.", localActivityNotFoundException);
        }
      }
    }
    
    public boolean leftApplication()
    {
      for (;;)
      {
        synchronized ()
        {
          if (AdActivity.c() != null)
          {
            bool = true;
            return bool;
          }
        }
        boolean bool = false;
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.AdActivity
 * JD-Core Version:    0.7.0.1
 */