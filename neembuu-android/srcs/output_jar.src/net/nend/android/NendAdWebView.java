package net.nend.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

final class NendAdWebView
  extends WebView
{
  @SuppressLint({"SetJavaScriptEnabled"})
  NendAdWebView(Context paramContext)
  {
    super(paramContext);
    getSettings().setJavaScriptEnabled(true);
    setBackgroundColor(0);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendAdWebView
 * JD-Core Version:    0.7.0.1
 */