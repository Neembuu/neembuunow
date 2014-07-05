package jp.co.imobile.android;

public enum SupportAdSize
{
  private final int a = 50;
  private final int b = 320;
  
  static
  {
    SupportAdSize[] arrayOfSupportAdSize = new SupportAdSize[1];
    arrayOfSupportAdSize[0] = BUNNER;
    c = arrayOfSupportAdSize;
  }
  
  private SupportAdSize() {}
  
  public final int getHeight()
  {
    return this.a;
  }
  
  public final int getWidth()
  {
    return this.b;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.SupportAdSize
 * JD-Core Version:    0.7.0.1
 */