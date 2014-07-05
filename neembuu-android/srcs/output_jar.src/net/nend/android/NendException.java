package net.nend.android;

final class NendException
  extends Exception
{
  private static final long serialVersionUID = -1250523971139030161L;
  
  NendException() {}
  
  NendException(String paramString)
  {
    super(paramString);
  }
  
  NendException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
  
  NendException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
  
  NendException(NendStatus paramNendStatus)
  {
    this(paramNendStatus.getMsg());
  }
  
  NendException(NendStatus paramNendStatus, String paramString)
  {
    this(paramNendStatus.getMsg(paramString));
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendException
 * JD-Core Version:    0.7.0.1
 */