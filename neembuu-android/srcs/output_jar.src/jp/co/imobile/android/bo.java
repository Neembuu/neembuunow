package jp.co.imobile.android;

 enum bo
  implements ax
{
  private final Integer g;
  
  static
  {
    bo[] arrayOfbo = new bo[6];
    arrayOfbo[0] = a;
    arrayOfbo[1] = b;
    arrayOfbo[2] = c;
    arrayOfbo[3] = d;
    arrayOfbo[4] = e;
    arrayOfbo[5] = f;
    h = arrayOfbo;
  }
  
  private bo(Integer paramInteger)
  {
    this.g = paramInteger;
  }
  
  public static bo[] b()
  {
    bo[] arrayOfbo1 = h;
    int i = arrayOfbo1.length;
    bo[] arrayOfbo2 = new bo[i];
    System.arraycopy(arrayOfbo1, 0, arrayOfbo2, 0, i);
    return arrayOfbo2;
  }
  
  public final Integer a()
  {
    return this.g;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.bo
 * JD-Core Version:    0.7.0.1
 */