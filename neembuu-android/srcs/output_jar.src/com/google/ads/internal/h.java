package com.google.ads.internal;

import android.content.Context;
import com.google.ads.AdSize;

public class h
{
  public static final h a = new h(null, true);
  private AdSize b;
  private boolean c;
  private final boolean d;
  
  private h(AdSize paramAdSize, boolean paramBoolean)
  {
    this.b = paramAdSize;
    this.d = paramBoolean;
  }
  
  public static h a(AdSize paramAdSize)
  {
    return a(paramAdSize, null);
  }
  
  public static h a(AdSize paramAdSize, Context paramContext)
  {
    return new h(AdSize.createAdSize(paramAdSize, paramContext), false);
  }
  
  public void a(boolean paramBoolean)
  {
    this.c = paramBoolean;
  }
  
  public boolean a()
  {
    return this.d;
  }
  
  public void b(AdSize paramAdSize)
  {
    if (!this.d) {
      this.b = paramAdSize;
    }
  }
  
  public boolean b()
  {
    return this.c;
  }
  
  public AdSize c()
  {
    return this.b;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.internal.h
 * JD-Core Version:    0.7.0.1
 */