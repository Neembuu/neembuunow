package jp.co.imobile.android;

public final class AdRequestResult
{
  static final AdRequestResult a = new AdRequestResult(0, 0, "setting fail", 0L, AdRequestResultType.PARAM_ERROR);
  static final AdRequestResult b = new AdRequestResult(0, 0, "", 0L, AdRequestResultType.SUCCEED_CLICK);
  static final AdRequestResult c = new AdRequestResult(0, 0, "click fail", 0L, AdRequestResultType.FAIL_CLICK);
  static final AdRequestResult d = new AdRequestResult(0, 0, "", 0L, AdRequestResultType.SUCCEED_CLICK_HOUSE_AD);
  static final AdRequestResult e = new AdRequestResult(0, 0, "click fail", 0L, AdRequestResultType.FAIL_CLICK_HOUSE_AD);
  static final AdRequestResult f = new AdRequestResult(0, 0, "because the network was not ready, can not start", 0L, AdRequestResultType.NETWORK_NOT_READY);
  private final int g;
  private final int h;
  private final String i;
  private final long j;
  private final AdRequestResultType k;
  
  private AdRequestResult(int paramInt1, int paramInt2, String paramString, long paramLong, AdRequestResultType paramAdRequestResultType)
  {
    this.g = paramInt1;
    this.i = paramString;
    this.j = paramLong;
    this.k = paramAdRequestResultType;
    this.h = paramInt2;
  }
  
  static final AdRequestResult a(AdRequestResult paramAdRequestResult, String paramString)
  {
    return new AdRequestResult(paramAdRequestResult.getAdCount(), paramAdRequestResult.getHouseAdCount(), paramString, paramAdRequestResult.getNextRequestSpan(), paramAdRequestResult.getResult());
  }
  
  static final AdRequestResult a(bb parambb, al paramal, String paramString, AdRequestResultType paramAdRequestResultType)
  {
    AdRequestResult localAdRequestResult;
    if (parambb == null) {
      localAdRequestResult = new AdRequestResult(0, 0, paramString, 0L, paramAdRequestResultType);
    }
    for (;;)
    {
      return localAdRequestResult;
      cg localcg = parambb.e();
      if (localcg == null) {
        localAdRequestResult = new AdRequestResult(0, 0, paramString, 0L, paramAdRequestResultType);
      } else if (paramal == null) {
        localAdRequestResult = new AdRequestResult(0, 0, paramString, localcg.b(), paramAdRequestResultType);
      } else {
        localAdRequestResult = new AdRequestResult(paramal.d(), parambb.b(), paramString, localcg.b(), paramAdRequestResultType);
      }
    }
  }
  
  static final AdRequestResult a(bb parambb, al paramal, AdRequestResultType paramAdRequestResultType)
  {
    return a(parambb, paramal, "", paramAdRequestResultType);
  }
  
  public final int getAdCount()
  {
    return this.g;
  }
  
  public final String getErrorMessage()
  {
    return this.i;
  }
  
  public final int getHouseAdCount()
  {
    return this.h;
  }
  
  public final long getNextRequestSpan()
  {
    return this.j;
  }
  
  public final AdRequestResultType getResult()
  {
    return this.k;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.AdRequestResult
 * JD-Core Version:    0.7.0.1
 */