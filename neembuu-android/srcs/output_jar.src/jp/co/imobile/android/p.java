package jp.co.imobile.android;

final class p
  extends Exception
{
  private final AdRequestResultType a;
  
  p(AdRequestResultType paramAdRequestResultType, String paramString)
  {
    super(paramString);
    this.a = paramAdRequestResultType;
  }
  
  p(AdRequestResultType paramAdRequestResultType, String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
    this.a = paramAdRequestResultType;
  }
  
  final AdRequestResultType a()
  {
    return this.a;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.p
 * JD-Core Version:    0.7.0.1
 */