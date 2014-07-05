package jp.co.imobile.android;

public enum AdRequestResultType
  implements ax
{
  private final Integer a;
  private final boolean b;
  
  static
  {
    SUCCEED_DELIVER = new AdRequestResultType("SUCCEED_DELIVER", 1, Integer.valueOf(0), true);
    PARAM_ERROR = new AdRequestResultType("PARAM_ERROR", 2, Integer.valueOf(1), false);
    AUTHORITY_ERROR = new AdRequestResultType("AUTHORITY_ERROR", 3, Integer.valueOf(2), false);
    NETWORK_ERROR = new AdRequestResultType("NETWORK_ERROR", 4, Integer.valueOf(3), false);
    NETWORK_NOT_READY = new AdRequestResultType("NETWORK_NOT_READY", 5, Integer.valueOf(4), false);
    SUCCEED_CLICK = new AdRequestResultType("SUCCEED_CLICK", 6, Integer.valueOf(100), true);
    FAIL_CLICK = new AdRequestResultType("FAIL_CLICK", 7, Integer.valueOf(101), false);
    NOT_FOUND_AD = new AdRequestResultType("NOT_FOUND_AD", 8, Integer.valueOf(400), false);
    SUCCEED_HOUSE_AD = new AdRequestResultType("SUCCEED_HOUSE_AD", 9, Integer.valueOf(1000), true);
    SUCCEED_CLICK_HOUSE_AD = new AdRequestResultType("SUCCEED_CLICK_HOUSE_AD", 10, Integer.valueOf(1100), true);
    FAIL_CLICK_HOUSE_AD = new AdRequestResultType("FAIL_CLICK_HOUSE_AD", 11, Integer.valueOf(1101), false);
    BAD_REQUEST = new AdRequestResultType("BAD_REQUEST", 12, Integer.valueOf(9999), false);
    AdRequestResultType[] arrayOfAdRequestResultType = new AdRequestResultType[13];
    arrayOfAdRequestResultType[0] = UNKNOWN_ERROR;
    arrayOfAdRequestResultType[1] = SUCCEED_DELIVER;
    arrayOfAdRequestResultType[2] = PARAM_ERROR;
    arrayOfAdRequestResultType[3] = AUTHORITY_ERROR;
    arrayOfAdRequestResultType[4] = NETWORK_ERROR;
    arrayOfAdRequestResultType[5] = NETWORK_NOT_READY;
    arrayOfAdRequestResultType[6] = SUCCEED_CLICK;
    arrayOfAdRequestResultType[7] = FAIL_CLICK;
    arrayOfAdRequestResultType[8] = NOT_FOUND_AD;
    arrayOfAdRequestResultType[9] = SUCCEED_HOUSE_AD;
    arrayOfAdRequestResultType[10] = SUCCEED_CLICK_HOUSE_AD;
    arrayOfAdRequestResultType[11] = FAIL_CLICK_HOUSE_AD;
    arrayOfAdRequestResultType[12] = BAD_REQUEST;
    c = arrayOfAdRequestResultType;
  }
  
  private AdRequestResultType(Integer paramInteger, boolean paramBoolean)
  {
    this.a = paramInteger;
    this.b = paramBoolean;
  }
  
  public final Integer baseValue()
  {
    return this.a;
  }
  
  public final boolean isSucceeded()
  {
    return this.b;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.AdRequestResultType
 * JD-Core Version:    0.7.0.1
 */