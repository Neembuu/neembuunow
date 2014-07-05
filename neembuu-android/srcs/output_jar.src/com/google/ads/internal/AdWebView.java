package com.google.ads.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.ads.AdActivity;
import com.google.ads.AdSize;
import com.google.ads.ak;
import com.google.ads.n;
import com.google.ads.util.AdUtil;
import com.google.ads.util.IcsUtil.a;
import com.google.ads.util.b;
import com.google.ads.util.g;
import com.google.ads.util.g.a;
import com.google.ads.util.h;
import com.google.ads.util.i.b;
import com.google.ads.util.i.c;
import java.lang.ref.WeakReference;

public class AdWebView
  extends WebView
{
  protected final n a;
  private WeakReference<AdActivity> b;
  private AdSize c;
  private boolean d;
  private boolean e;
  private boolean f;
  
  public AdWebView(n paramn, AdSize paramAdSize)
  {
    super((Context)paramn.f.a());
    this.a = paramn;
    this.c = paramAdSize;
    this.b = null;
    this.d = false;
    this.e = false;
    this.f = false;
    setBackgroundColor(0);
    AdUtil.a(this);
    WebSettings localWebSettings = getSettings();
    localWebSettings.setSupportMultipleWindows(false);
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setSavePassword(false);
    setDownloadListener(new DownloadListener()
    {
      public void onDownloadStart(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, String paramAnonymousString4, long paramAnonymousLong)
      {
        try
        {
          Intent localIntent = new Intent("android.intent.action.VIEW");
          localIntent.setDataAndType(Uri.parse(paramAnonymousString1), paramAnonymousString4);
          AdActivity localAdActivity = AdWebView.this.i();
          if ((localAdActivity != null) && (AdUtil.a(localIntent, localAdActivity))) {
            localAdActivity.startActivity(localIntent);
          }
          return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          for (;;)
          {
            b.a("Couldn't find an Activity to view url/mimetype: " + paramAnonymousString1 + " / " + paramAnonymousString4);
          }
        }
        catch (Throwable localThrowable)
        {
          for (;;)
          {
            b.b("Unknown error trying to start activity to view URL: " + paramAnonymousString1, localThrowable);
          }
        }
      }
    });
    if (AdUtil.a >= 17) {
      h.a(localWebSettings, paramn);
    }
    label162:
    for (;;)
    {
      setScrollBarStyle(33554432);
      if (AdUtil.a >= 14) {
        setWebChromeClient(new IcsUtil.a(paramn));
      }
      for (;;)
      {
        return;
        if (AdUtil.a < 11) {
          break label162;
        }
        g.a(localWebSettings, paramn);
        break;
        if (AdUtil.a >= 11) {
          setWebChromeClient(new g.a(paramn));
        }
      }
    }
  }
  
  public void a(boolean paramBoolean)
  {
    if (paramBoolean) {
      setOnTouchListener(new View.OnTouchListener()
      {
        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
          if (paramAnonymousMotionEvent.getAction() == 2) {}
          for (boolean bool = true;; bool = false) {
            return bool;
          }
        }
      });
    }
    for (;;)
    {
      return;
      setOnTouchListener(null);
    }
  }
  
  public void destroy()
  {
    try
    {
      super.destroy();
    }
    catch (Throwable localThrowable1)
    {
      try
      {
        for (;;)
        {
          setWebViewClient(new WebViewClient());
          label15:
          return;
          localThrowable1 = localThrowable1;
          b.d("An error occurred while destroying an AdWebView:", localThrowable1);
        }
      }
      catch (Throwable localThrowable2)
      {
        break label15;
      }
    }
  }
  
  public void f()
  {
    AdActivity localAdActivity = i();
    if (localAdActivity != null) {
      localAdActivity.finish();
    }
  }
  
  public void g()
  {
    if (AdUtil.a >= 11) {
      g.a(this);
    }
    this.e = true;
  }
  
  public void h()
  {
    if ((this.e) && (AdUtil.a >= 11)) {
      g.b(this);
    }
    this.e = false;
  }
  
  public AdActivity i()
  {
    if (this.b != null) {}
    for (AdActivity localAdActivity = (AdActivity)this.b.get();; localAdActivity = null) {
      return localAdActivity;
    }
  }
  
  public boolean j()
  {
    return this.f;
  }
  
  public boolean k()
  {
    return this.e;
  }
  
  public void loadDataWithBaseURL(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    try
    {
      super.loadDataWithBaseURL(paramString1, paramString2, paramString3, paramString4, paramString5);
      return;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        b.d("An error occurred while loading data in AdWebView:", localThrowable);
      }
    }
  }
  
  public void loadUrl(String paramString)
  {
    try
    {
      super.loadUrl(paramString);
      return;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        b.d("An error occurred while loading a URL in AdWebView:", localThrowable);
      }
    }
  }
  
  /**
   * @deprecated
   */
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = 2147483647;
    try
    {
      if (isInEditMode()) {
        super.onMeasure(paramInt1, paramInt2);
      }
      for (;;)
      {
        return;
        if ((this.c != null) && (!this.d)) {
          break;
        }
        super.onMeasure(paramInt1, paramInt2);
      }
      j = View.MeasureSpec.getMode(paramInt1);
    }
    finally {}
    int j;
    int k = View.MeasureSpec.getSize(paramInt1);
    int m = View.MeasureSpec.getMode(paramInt2);
    int n = View.MeasureSpec.getSize(paramInt2);
    float f1 = getContext().getResources().getDisplayMetrics().density;
    int i1 = (int)(f1 * this.c.getWidth());
    int i2 = (int)(f1 * this.c.getHeight());
    if (j != -2147483648) {
      if (j == 1073741824) {
        break label227;
      }
    }
    for (;;)
    {
      label135:
      b.b("Not enough space to show ad! Wants: <" + i1 + ", " + i2 + ">, Has: <" + k + ", " + n + ">");
      setVisibility(8);
      setMeasuredDimension(k, n);
      break;
      label227:
      label231:
      do
      {
        setMeasuredDimension(i1, i2);
        break;
        int i3 = i;
        break label231;
        i3 = k;
        if ((m == -2147483648) || (m == 1073741824)) {
          i = n;
        }
        if (i1 - f1 * 6.0F > i3) {
          break label135;
        }
      } while (i2 <= i);
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    ak localak = (ak)this.a.r.a();
    if (localak != null) {
      localak.a(paramMotionEvent);
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void setAdActivity(AdActivity paramAdActivity)
  {
    this.b = new WeakReference(paramAdActivity);
  }
  
  /**
   * @deprecated
   */
  public void setAdSize(AdSize paramAdSize)
  {
    try
    {
      this.c = paramAdSize;
      requestLayout();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void setCustomClose(boolean paramBoolean)
  {
    this.f = paramBoolean;
    if (this.b != null)
    {
      AdActivity localAdActivity = (AdActivity)this.b.get();
      if (localAdActivity != null) {
        localAdActivity.setCustomClose(paramBoolean);
      }
    }
  }
  
  public void setIsExpandedMraid(boolean paramBoolean)
  {
    this.d = paramBoolean;
  }
  
  public void stopLoading()
  {
    try
    {
      super.stopLoading();
      return;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        b.d("An error occurred while stopping loading in AdWebView:", localThrowable);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.internal.AdWebView
 * JD-Core Version:    0.7.0.1
 */