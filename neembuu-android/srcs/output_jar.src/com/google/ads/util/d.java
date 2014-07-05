package com.google.ads.util;

import android.os.Build;

class d
{
  static final d d = new d();
  static final d e = new d("unknown", "generic", "generic");
  static final d f = new d("unknown", "generic_x86", "Android");
  public final String a;
  public final String b;
  public final String c;
  
  d()
  {
    this.a = Build.BOARD;
    this.b = Build.DEVICE;
    this.c = Build.BRAND;
  }
  
  d(String paramString1, String paramString2, String paramString3)
  {
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramString3;
  }
  
  private static boolean a(String paramString1, String paramString2)
  {
    boolean bool;
    if (paramString1 != null) {
      bool = paramString1.equals(paramString2);
    }
    for (;;)
    {
      return bool;
      if (paramString1 == paramString2) {
        bool = true;
      } else {
        bool = false;
      }
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = false;
    if (!(paramObject instanceof d)) {}
    for (;;)
    {
      return bool;
      d locald = (d)paramObject;
      if ((a(this.a, locald.a)) && (a(this.b, locald.b)) && (a(this.c, locald.c))) {
        bool = true;
      }
    }
  }
  
  public int hashCode()
  {
    int i = 0;
    if (this.a != null) {
      i = 0 + this.a.hashCode();
    }
    if (this.b != null) {
      i += this.b.hashCode();
    }
    if (this.c != null) {
      i += this.c.hashCode();
    }
    return i;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.util.d
 * JD-Core Version:    0.7.0.1
 */