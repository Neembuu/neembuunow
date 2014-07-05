package com.google.ads;

import android.text.TextUtils;
import android.webkit.WebView;
import com.google.ads.internal.ActivationOverlay;
import com.google.ads.internal.AdWebView;
import com.google.ads.internal.d;
import com.google.ads.internal.h;
import com.google.ads.util.AdUtil;
import com.google.ads.util.b;
import com.google.ads.util.g;
import com.google.ads.util.i.b;
import com.google.ads.util.i.c;
import java.util.HashMap;

public class x
  implements o
{
  private void a(HashMap<String, String> paramHashMap, String paramString, i.c<Integer> paramc)
  {
    try
    {
      String str = (String)paramHashMap.get(paramString);
      if (!TextUtils.isEmpty(str)) {
        paramc.a(Integer.valueOf(str));
      }
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        b.a("Could not parse \"" + paramString + "\" constant.");
      }
    }
  }
  
  private void b(HashMap<String, String> paramHashMap, String paramString, i.c<Long> paramc)
  {
    try
    {
      String str = (String)paramHashMap.get(paramString);
      if (!TextUtils.isEmpty(str)) {
        paramc.a(Long.valueOf(str));
      }
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        b.a("Could not parse \"" + paramString + "\" constant.");
      }
    }
  }
  
  private void c(HashMap<String, String> paramHashMap, String paramString, i.c<String> paramc)
  {
    String str = (String)paramHashMap.get(paramString);
    if (!TextUtils.isEmpty(str)) {
      paramc.a(str);
    }
  }
  
  public void a(d paramd, HashMap<String, String> paramHashMap, WebView paramWebView)
  {
    n localn = paramd.i();
    m.a locala = (m.a)((m)localn.d.a()).b.a();
    c(paramHashMap, "as_domains", locala.a);
    c(paramHashMap, "bad_ad_report_path", locala.h);
    a(paramHashMap, "min_hwa_banner", locala.b);
    a(paramHashMap, "min_hwa_activation_overlay", locala.c);
    a(paramHashMap, "min_hwa_overlay", locala.d);
    c(paramHashMap, "mraid_banner_path", locala.e);
    c(paramHashMap, "mraid_expanded_banner_path", locala.f);
    c(paramHashMap, "mraid_interstitial_path", locala.g);
    b(paramHashMap, "ac_max_size", locala.i);
    b(paramHashMap, "ac_padding", locala.j);
    b(paramHashMap, "ac_total_quota", locala.k);
    b(paramHashMap, "db_total_quota", locala.l);
    b(paramHashMap, "db_quota_per_origin", locala.m);
    b(paramHashMap, "db_quota_step_size", locala.n);
    AdWebView localAdWebView = paramd.l();
    if (AdUtil.a >= 11)
    {
      g.a(localAdWebView.getSettings(), localn);
      g.a(paramWebView.getSettings(), localn);
    }
    boolean bool2;
    int j;
    label296:
    ActivationOverlay localActivationOverlay;
    boolean bool1;
    int i;
    if (!((h)localn.g.a()).a())
    {
      bool2 = localAdWebView.k();
      if (AdUtil.a < ((Integer)locala.b.a()).intValue())
      {
        j = 1;
        if ((j != 0) || (!bool2)) {
          break label446;
        }
        b.a("Re-enabling hardware acceleration for a banner after reading constants.");
        localAdWebView.h();
      }
    }
    else
    {
      localActivationOverlay = (ActivationOverlay)localn.e.a();
      if ((!((h)localn.g.a()).b()) && (localActivationOverlay != null))
      {
        bool1 = localActivationOverlay.k();
        if (AdUtil.a >= ((Integer)locala.c.a()).intValue()) {
          break label469;
        }
        i = 1;
        label361:
        if ((i != 0) || (!bool1)) {
          break label475;
        }
        b.a("Re-enabling hardware acceleration for an activation overlay after reading constants.");
        localActivationOverlay.h();
      }
    }
    for (;;)
    {
      String str = (String)locala.a.a();
      al localal = (al)localn.s.a();
      if ((localal != null) && (!TextUtils.isEmpty(str))) {
        localal.a(str);
      }
      locala.o.a(Boolean.valueOf(true));
      return;
      j = 0;
      break;
      label446:
      if ((j == 0) || (bool2)) {
        break label296;
      }
      b.a("Disabling hardware acceleration for a banner after reading constants.");
      localAdWebView.g();
      break label296;
      label469:
      i = 0;
      break label361;
      label475:
      if ((i != 0) && (!bool1))
      {
        b.a("Disabling hardware acceleration for an activation overlay after reading constants.");
        localActivationOverlay.g();
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.x
 * JD-Core Version:    0.7.0.1
 */