package jp.co.imobile.android;

import android.view.animation.Animation;

 enum ar
  implements ax
{
  private final Integer e;
  
  static
  {
    ar[] arrayOfar = new ar[4];
    arrayOfar[0] = a;
    arrayOfar[1] = b;
    arrayOfar[2] = c;
    arrayOfar[3] = d;
    f = arrayOfar;
  }
  
  private ar(Integer paramInteger, byte paramByte)
  {
    this.e = paramInteger;
  }
  
  public static ar[] d()
  {
    ar[] arrayOfar1 = f;
    int i = arrayOfar1.length;
    ar[] arrayOfar2 = new ar[i];
    System.arraycopy(arrayOfar1, 0, arrayOfar2, 0, i);
    return arrayOfar2;
  }
  
  public final Integer a()
  {
    return this.e;
  }
  
  abstract Animation b();
  
  abstract Animation c();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.ar
 * JD-Core Version:    0.7.0.1
 */