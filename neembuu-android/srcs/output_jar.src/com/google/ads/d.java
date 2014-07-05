package com.google.ads;

import android.os.SystemClock;
import java.util.concurrent.TimeUnit;

public class d
{
  private c a = null;
  private long b = -1L;
  
  public void a(c paramc, int paramInt)
  {
    this.a = paramc;
    this.b = (TimeUnit.MILLISECONDS.convert(paramInt, TimeUnit.SECONDS) + SystemClock.elapsedRealtime());
  }
  
  public boolean a()
  {
    if ((this.a != null) && (SystemClock.elapsedRealtime() < this.b)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public c b()
  {
    return this.a;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.d
 * JD-Core Version:    0.7.0.1
 */