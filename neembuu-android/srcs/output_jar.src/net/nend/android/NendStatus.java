package net.nend.android;

import java.util.Locale;

 enum NendStatus
{
  private static final String STATUS_PREFIX = "AE";
  private final int code;
  private final String msg;
  
  static
  {
    INITIAL = new NendStatus("INITIAL", 1, 800, "Initial state");
    ERR_INVALID_ATTRIBUTE_SET = new NendStatus("ERR_INVALID_ATTRIBUTE_SET", 2, 811, "AttributeSet is invalid.");
    ERR_INVALID_API_KEY = new NendStatus("ERR_INVALID_API_KEY", 3, 812, "Api key is invalid.");
    ERR_INVALID_SPOT_ID = new NendStatus("ERR_INVALID_SPOT_ID", 4, 813, "Spot id is invalid.");
    ERR_INVALID_CONTEXT = new NendStatus("ERR_INVALID_CONTEXT", 5, 814, "Context is invalid.");
    ERR_INVALID_URL = new NendStatus("ERR_INVALID_URL", 6, 815, "Url is invalid.");
    ERR_INVALID_RESPONSE = new NendStatus("ERR_INVALID_RESPONSE", 7, 814, "Response is invalid.");
    ERR_INVALID_AD_STATUS = new NendStatus("ERR_INVALID_AD_STATUS", 8, 815, "Ad status is invalid.");
    ERR_INVALID_RESPONSE_TYPE = new NendStatus("ERR_INVALID_RESPONSE_TYPE", 9, 816, "Response type is invalid.");
    ERR_HTTP_REQUEST = new NendStatus("ERR_HTTP_REQUEST", 10, 820, "Failed to http request.");
    ERR_FAILED_TO_PARSE = new NendStatus("ERR_FAILED_TO_PARSE", 11, 821, "Failed to parse response.");
    ERR_OUT_OF_STOCK = new NendStatus("ERR_OUT_OF_STOCK", 12, 830, "Ad is out of stock.");
    ERR_UNEXPECTED = new NendStatus("ERR_UNEXPECTED", 13, 899, "Unexpected error.");
    NendStatus[] arrayOfNendStatus = new NendStatus[14];
    arrayOfNendStatus[0] = SUCCESS;
    arrayOfNendStatus[1] = INITIAL;
    arrayOfNendStatus[2] = ERR_INVALID_ATTRIBUTE_SET;
    arrayOfNendStatus[3] = ERR_INVALID_API_KEY;
    arrayOfNendStatus[4] = ERR_INVALID_SPOT_ID;
    arrayOfNendStatus[5] = ERR_INVALID_CONTEXT;
    arrayOfNendStatus[6] = ERR_INVALID_URL;
    arrayOfNendStatus[7] = ERR_INVALID_RESPONSE;
    arrayOfNendStatus[8] = ERR_INVALID_AD_STATUS;
    arrayOfNendStatus[9] = ERR_INVALID_RESPONSE_TYPE;
    arrayOfNendStatus[10] = ERR_HTTP_REQUEST;
    arrayOfNendStatus[11] = ERR_FAILED_TO_PARSE;
    arrayOfNendStatus[12] = ERR_OUT_OF_STOCK;
    arrayOfNendStatus[13] = ERR_UNEXPECTED;
    $VALUES = arrayOfNendStatus;
  }
  
  private NendStatus(int paramInt, String paramString)
  {
    this.code = paramInt;
    this.msg = paramString;
  }
  
  int getCode()
  {
    return this.code;
  }
  
  String getMsg()
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = "AE";
    arrayOfObject[1] = Integer.valueOf(this.code);
    arrayOfObject[2] = this.msg;
    return String.format(localLocale, "[%s%d] %s", arrayOfObject);
  }
  
  String getMsg(String paramString)
  {
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = "AE";
    arrayOfObject[1] = Integer.valueOf(this.code);
    arrayOfObject[2] = this.msg;
    arrayOfObject[3] = paramString;
    return String.format(localLocale, "[%s%d] %s %s", arrayOfObject);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendStatus
 * JD-Core Version:    0.7.0.1
 */