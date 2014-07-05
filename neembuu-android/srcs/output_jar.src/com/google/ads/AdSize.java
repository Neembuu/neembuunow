package com.google.ads;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class AdSize
{
  public static final int AUTO_HEIGHT = -2;
  public static final AdSize BANNER;
  public static final int FULL_WIDTH = -1;
  public static final AdSize IAB_BANNER = new AdSize(468, 60, "as");
  public static final AdSize IAB_LEADERBOARD = new AdSize(728, 90, "as");
  public static final AdSize IAB_MRECT;
  public static final AdSize IAB_WIDE_SKYSCRAPER = new AdSize(160, 600, "as");
  public static final int LANDSCAPE_AD_HEIGHT = 32;
  public static final int LARGE_AD_HEIGHT = 90;
  public static final int PORTRAIT_AD_HEIGHT = 50;
  public static final AdSize SMART_BANNER = new AdSize(-1, -2, "mb");
  private final int a;
  private final int b;
  private boolean c;
  private boolean d;
  private boolean e;
  private String f;
  
  static
  {
    BANNER = new AdSize(320, 50, "mb");
    IAB_MRECT = new AdSize(300, 250, "as");
  }
  
  public AdSize(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, null);
    if (a())
    {
      this.e = false;
      this.f = "mb";
    }
    for (;;)
    {
      return;
      this.e = true;
    }
  }
  
  private AdSize(int paramInt1, int paramInt2, String paramString)
  {
    this.a = paramInt1;
    this.b = paramInt2;
    this.f = paramString;
    boolean bool2;
    if (paramInt1 == -1)
    {
      bool2 = bool1;
      this.c = bool2;
      if (paramInt2 != -2) {
        break label62;
      }
    }
    for (;;)
    {
      this.d = bool1;
      this.e = false;
      return;
      bool2 = false;
      break;
      label62:
      bool1 = false;
    }
  }
  
  private static int a(Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    return (int)(localDisplayMetrics.widthPixels / localDisplayMetrics.density);
  }
  
  private boolean a()
  {
    if ((this.a < 0) || (this.b < 0)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  private static int b(Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    int i = (int)(localDisplayMetrics.heightPixels / localDisplayMetrics.density);
    int j;
    if (i <= 400) {
      j = 32;
    }
    for (;;)
    {
      return j;
      if (i <= 720) {
        j = 50;
      } else {
        j = 90;
      }
    }
  }
  
  public static AdSize createAdSize(AdSize paramAdSize, Context paramContext)
  {
    if ((paramContext == null) || (!paramAdSize.a()))
    {
      if (paramAdSize.a()) {
        paramAdSize = BANNER;
      }
      return paramAdSize;
    }
    int i;
    if (paramAdSize.c)
    {
      i = a(paramContext);
      label36:
      if (!paramAdSize.d) {
        break label104;
      }
    }
    label104:
    for (int j = b(paramContext);; j = paramAdSize.getHeight())
    {
      AdSize localAdSize = new AdSize(i, j, paramAdSize.f);
      localAdSize.d = paramAdSize.d;
      localAdSize.c = paramAdSize.c;
      localAdSize.e = paramAdSize.e;
      paramAdSize = localAdSize;
      break;
      i = paramAdSize.getWidth();
      break label36;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = false;
    if (!(paramObject instanceof AdSize)) {}
    for (;;)
    {
      return bool;
      AdSize localAdSize = (AdSize)paramObject;
      if ((this.a == localAdSize.a) && (this.b == localAdSize.b)) {
        bool = true;
      }
    }
  }
  
  public AdSize findBestSize(AdSize... paramVarArgs)
  {
    Object localObject1 = null;
    double d1 = 0.0D;
    int j;
    AdSize localAdSize;
    double d2;
    if (paramVarArgs != null)
    {
      int i = paramVarArgs.length;
      j = 0;
      if (j < i)
      {
        localAdSize = paramVarArgs[j];
        if (!isSizeAppropriate(localAdSize.a, localAdSize.b)) {
          break label110;
        }
        d2 = localAdSize.a * localAdSize.b / (this.a * this.b);
        if (d2 > 1.0D) {
          d2 = 1.0D / d2;
        }
        if (d2 <= d1) {
          break label110;
        }
      }
    }
    for (Object localObject2 = localAdSize;; localObject2 = localObject1)
    {
      j++;
      localObject1 = localObject2;
      d1 = d2;
      break;
      return localObject1;
      label110:
      d2 = d1;
    }
  }
  
  public int getHeight()
  {
    if (this.b < 0) {
      throw new UnsupportedOperationException("Ad size was not set before getHeight() was called.");
    }
    return this.b;
  }
  
  public int getHeightInPixels(Context paramContext)
  {
    return (int)TypedValue.applyDimension(1, this.b, paramContext.getResources().getDisplayMetrics());
  }
  
  public int getWidth()
  {
    if (this.a < 0) {
      throw new UnsupportedOperationException("Ad size was not set before getWidth() was called.");
    }
    return this.a;
  }
  
  public int getWidthInPixels(Context paramContext)
  {
    return (int)TypedValue.applyDimension(1, this.a, paramContext.getResources().getDisplayMetrics());
  }
  
  public int hashCode()
  {
    return Integer.valueOf(this.a).hashCode() << 16 | 0xFFFF & Integer.valueOf(this.b).hashCode();
  }
  
  public boolean isAutoHeight()
  {
    return this.d;
  }
  
  public boolean isCustomAdSize()
  {
    return this.e;
  }
  
  public boolean isFullWidth()
  {
    return this.c;
  }
  
  public boolean isSizeAppropriate(int paramInt1, int paramInt2)
  {
    if ((paramInt1 <= 1.25D * this.a) && (paramInt1 >= 0.8D * this.a) && (paramInt2 <= 1.25D * this.b) && (paramInt2 >= 0.8D * this.b)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append(getWidth()).append("x").append(getHeight());
    if (this.f == null) {}
    for (String str = "";; str = "_" + this.f) {
      return str;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.AdSize
 * JD-Core Version:    0.7.0.1
 */