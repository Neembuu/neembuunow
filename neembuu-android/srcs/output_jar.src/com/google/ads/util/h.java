package com.google.ads.util;

import android.annotation.TargetApi;
import android.webkit.WebSettings;
import com.google.ads.n;

@TargetApi(17)
public final class h
{
  public static void a(WebSettings paramWebSettings, n paramn)
  {
    g.a(paramWebSettings, paramn);
    paramWebSettings.setMediaPlaybackRequiresUserGesture(false);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.util.h
 * JD-Core Version:    0.7.0.1
 */