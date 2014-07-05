package com.google.ads.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.l;
import com.google.ads.n;
import com.google.ads.util.AdUtil;
import com.google.ads.util.b;
import com.google.ads.util.i.b;
import com.google.ads.util.i.c;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class f
  implements Runnable
{
  private final l a;
  private final a b;
  private volatile boolean c;
  private boolean d;
  private String e;
  private Thread f = null;
  
  f(l paraml)
  {
    this(paraml, new a()
    {
      public HttpURLConnection a(URL paramAnonymousURL)
        throws IOException
      {
        return (HttpURLConnection)paramAnonymousURL.openConnection();
      }
    });
  }
  
  f(l paraml, a parama)
  {
    this.a = paraml;
    this.b = parama;
  }
  
  private void a(Context paramContext, HttpURLConnection paramHttpURLConnection)
  {
    String str = PreferenceManager.getDefaultSharedPreferences(paramContext).getString("drt", "");
    if ((this.d) && (!TextUtils.isEmpty(str)))
    {
      if (AdUtil.a != 8) {
        break label44;
      }
      paramHttpURLConnection.addRequestProperty("X-Afma-drt-Cookie", str);
    }
    for (;;)
    {
      return;
      label44:
      paramHttpURLConnection.addRequestProperty("Cookie", str);
    }
  }
  
  private void a(HttpURLConnection paramHttpURLConnection)
  {
    b(paramHttpURLConnection);
    f(paramHttpURLConnection);
    g(paramHttpURLConnection);
    h(paramHttpURLConnection);
    i(paramHttpURLConnection);
    e(paramHttpURLConnection);
    j(paramHttpURLConnection);
    k(paramHttpURLConnection);
    l(paramHttpURLConnection);
    d(paramHttpURLConnection);
    c(paramHttpURLConnection);
    m(paramHttpURLConnection);
    n(paramHttpURLConnection);
  }
  
  private void a(HttpURLConnection paramHttpURLConnection, int paramInt)
    throws IOException
  {
    String str2;
    if ((300 <= paramInt) && (paramInt < 400))
    {
      str2 = paramHttpURLConnection.getHeaderField("Location");
      if (str2 == null)
      {
        b.c("Could not get redirect location from a " + paramInt + " redirect.");
        ((c)this.a.b.a()).a(AdRequest.ErrorCode.INTERNAL_ERROR);
        a();
      }
    }
    for (;;)
    {
      return;
      a(paramHttpURLConnection);
      this.e = str2;
      continue;
      if (paramInt == 200)
      {
        a(paramHttpURLConnection);
        String str1 = AdUtil.a(new InputStreamReader(paramHttpURLConnection.getInputStream())).trim();
        b.a("Response content is: " + str1);
        if (TextUtils.isEmpty(str1))
        {
          b.a("Response message is null or zero length: " + str1);
          ((c)this.a.b.a()).a(AdRequest.ErrorCode.NO_FILL);
          a();
        }
        else
        {
          ((c)this.a.b.a()).a(str1, this.e);
          a();
        }
      }
      else if (paramInt == 400)
      {
        b.c("Bad request");
        ((c)this.a.b.a()).a(AdRequest.ErrorCode.INVALID_REQUEST);
        a();
      }
      else
      {
        b.c("Invalid response code: " + paramInt);
        ((c)this.a.b.a()).a(AdRequest.ErrorCode.INTERNAL_ERROR);
        a();
      }
    }
  }
  
  private void b()
    throws MalformedURLException, IOException
  {
    while (!this.c)
    {
      URL localURL = new URL(this.e);
      HttpURLConnection localHttpURLConnection = this.b.a(localURL);
      try
      {
        a((Context)((n)this.a.a.a()).f.a(), localHttpURLConnection);
        AdUtil.a(localHttpURLConnection, (Context)((n)this.a.a.a()).f.a());
        localHttpURLConnection.setInstanceFollowRedirects(false);
        localHttpURLConnection.connect();
        a(localHttpURLConnection, localHttpURLConnection.getResponseCode());
        localHttpURLConnection.disconnect();
      }
      finally
      {
        localHttpURLConnection.disconnect();
      }
    }
  }
  
  private void b(HttpURLConnection paramHttpURLConnection)
  {
    String str = paramHttpURLConnection.getHeaderField("X-Afma-Debug-Dialog");
    if (!TextUtils.isEmpty(str)) {
      ((c)this.a.b.a()).f(str);
    }
  }
  
  private void c(HttpURLConnection paramHttpURLConnection)
  {
    String str = paramHttpURLConnection.getHeaderField("Content-Type");
    if (!TextUtils.isEmpty(str)) {
      ((c)this.a.b.a()).b(str);
    }
  }
  
  private void d(HttpURLConnection paramHttpURLConnection)
  {
    String str = paramHttpURLConnection.getHeaderField("X-Afma-Mediation");
    if (!TextUtils.isEmpty(str)) {
      ((c)this.a.b.a()).b(Boolean.valueOf(str).booleanValue());
    }
  }
  
  private void e(HttpURLConnection paramHttpURLConnection)
  {
    String str = paramHttpURLConnection.getHeaderField("X-Afma-Interstitial-Timeout");
    if (!TextUtils.isEmpty(str)) {}
    try
    {
      float f1 = Float.parseFloat(str);
      ((d)((n)this.a.a.a()).b.a()).a((f1 * 1000.0F));
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        b.d("Could not get timeout value: " + str, localNumberFormatException);
      }
    }
  }
  
  private void f(HttpURLConnection paramHttpURLConnection)
  {
    String str1 = paramHttpURLConnection.getHeaderField("X-Afma-Tracking-Urls");
    if (!TextUtils.isEmpty(str1)) {
      for (String str2 : str1.trim().split("\\s+")) {
        ((d)((n)this.a.a.a()).b.a()).b(str2);
      }
    }
  }
  
  private void g(HttpURLConnection paramHttpURLConnection)
  {
    String str1 = paramHttpURLConnection.getHeaderField("X-Afma-Manual-Tracking-Urls");
    if (!TextUtils.isEmpty(str1)) {
      for (String str2 : str1.trim().split("\\s+")) {
        ((d)((n)this.a.a.a()).b.a()).c(str2);
      }
    }
  }
  
  private void h(HttpURLConnection paramHttpURLConnection)
  {
    String str1 = paramHttpURLConnection.getHeaderField("X-Afma-Click-Tracking-Urls");
    if (!TextUtils.isEmpty(str1)) {
      for (String str2 : str1.trim().split("\\s+")) {
        ((c)this.a.b.a()).a(str2);
      }
    }
  }
  
  private void i(HttpURLConnection paramHttpURLConnection)
  {
    String str = paramHttpURLConnection.getHeaderField("X-Afma-Refresh-Rate");
    if (!TextUtils.isEmpty(str)) {}
    try
    {
      float f1 = Float.parseFloat(str);
      locald = (d)((n)this.a.a.a()).b.a();
      if (f1 > 0.0F)
      {
        locald.a(f1);
        if (!locald.t()) {
          locald.g();
        }
        return;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        d locald;
        b.d("Could not get refresh value: " + str, localNumberFormatException);
        continue;
        if (locald.t()) {
          locald.f();
        }
      }
    }
  }
  
  private void j(HttpURLConnection paramHttpURLConnection)
  {
    String str = paramHttpURLConnection.getHeaderField("X-Afma-Orientation");
    if (!TextUtils.isEmpty(str))
    {
      if (!str.equals("portrait")) {
        break label45;
      }
      ((c)this.a.b.a()).a(AdUtil.b());
    }
    for (;;)
    {
      return;
      label45:
      if (str.equals("landscape")) {
        ((c)this.a.b.a()).a(AdUtil.a());
      }
    }
  }
  
  private void k(HttpURLConnection paramHttpURLConnection)
  {
    if (!TextUtils.isEmpty(paramHttpURLConnection.getHeaderField("X-Afma-Doritos-Cache-Life"))) {}
    try
    {
      long l = Long.parseLong(paramHttpURLConnection.getHeaderField("X-Afma-Doritos-Cache-Life"));
      ((d)((n)this.a.a.a()).b.a()).b(l);
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      for (;;)
      {
        b.e("Got bad value of Doritos cookie cache life from header: " + paramHttpURLConnection.getHeaderField("X-Afma-Doritos-Cache-Life") + ". Using default value instead.");
      }
    }
  }
  
  private void l(HttpURLConnection paramHttpURLConnection)
  {
    String str = paramHttpURLConnection.getHeaderField("Cache-Control");
    if (!TextUtils.isEmpty(str)) {
      ((c)this.a.b.a()).c(str);
    }
  }
  
  private void m(HttpURLConnection paramHttpURLConnection)
  {
    String str = paramHttpURLConnection.getHeaderField("X-Afma-Ad-Size");
    if (!TextUtils.isEmpty(str)) {
      try
      {
        String[] arrayOfString = str.split("x", 2);
        if (arrayOfString.length != 2)
        {
          b.e("Could not parse size header: " + str);
        }
        else
        {
          int i = Integer.parseInt(arrayOfString[0]);
          int j = Integer.parseInt(arrayOfString[1]);
          ((c)this.a.b.a()).a(new AdSize(i, j));
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        b.e("Could not parse size header: " + str);
      }
    }
  }
  
  private void n(HttpURLConnection paramHttpURLConnection)
  {
    String str = paramHttpURLConnection.getHeaderField("X-Afma-Disable-Activation-And-Scroll");
    if (!TextUtils.isEmpty(str)) {
      ((c)this.a.b.a()).a(str.equals("1"));
    }
  }
  
  void a()
  {
    this.c = true;
  }
  
  /**
   * @deprecated
   */
  void a(String paramString)
  {
    try
    {
      if (this.f == null)
      {
        this.e = paramString;
        this.c = false;
        this.f = new Thread(this);
        this.f.start();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void a(boolean paramBoolean)
  {
    this.d = paramBoolean;
  }
  
  public void run()
  {
    try
    {
      b();
      return;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      for (;;)
      {
        b.b("Received malformed ad url from javascript.", localMalformedURLException);
        ((c)this.a.b.a()).a(AdRequest.ErrorCode.INTERNAL_ERROR);
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        b.b("IOException connecting to ad url.", localIOException);
        ((c)this.a.b.a()).a(AdRequest.ErrorCode.NETWORK_ERROR);
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        b.b("An unknown error occurred in AdResponseLoader.", localThrowable);
        ((c)this.a.b.a()).a(AdRequest.ErrorCode.INTERNAL_ERROR);
      }
    }
  }
  
  public static abstract interface a
  {
    public abstract HttpURLConnection a(URL paramURL)
      throws IOException;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.internal.f
 * JD-Core Version:    0.7.0.1
 */