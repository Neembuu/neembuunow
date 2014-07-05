package jp.tjkapp.adfurikunsdk;

class AdfurikunConstants
{
  public static final String ADFURIKUN_FOLDER;
  public static final String ADFURIKUN_VERSION = "2.3.1";
  public static final String ADNETWORKKEY_ADCROPS = "1043";
  public static final String ADNETWORKKEY_ADLANTIS;
  public static final String ADNETWORKKEY_APPLI_PROMOTION;
  public static final String ADNETWORKKEY_DEFAULT;
  public static final String ADNETWORKKEY_XMAX;
  public static final String ADNETWORKKEY_YDN;
  public static final String DEFAULT_LOCALE;
  public static final boolean DETAIL_LOG;
  public static final String EXTRA_INTERS_AD_INDEX;
  public static final String GETINFO_FILE;
  public static final int LOAD_MAIN_INIT;
  public static final int LOAD_MAIN_RETRY;
  public static final int LOAD_SUB_INIT;
  public static final int LOAD_SUB_RETRY;
  public static final String PREFKEY_AD_LAST_TIME;
  public static final String PREFKEY_DEVICE_ID;
  public static final String PREFKEY_INTERS_AD_FREQUENCY_CT;
  public static final String PREFKEY_INTERS_AD_MAX_CT;
  public static final String PREFKEY_TESTMODE;
  public static final String PREFKEY_WALL_APPID;
  public static final String PREF_FILE = "adfurikun_adpref.dat";
  public static final int PREF_TESTMODE_NOSETTING;
  public static final int PREF_TESTMODE_PRODUCT;
  public static final int PREF_TESTMODE_TEST;
  public static final int REGETINFO_TIME;
  public static final int RETRY_STOP_TIME;
  public static final int RETRY_STOP_TIME_SHORT;
  public static final int RETRY_TIME;
  public static final int RETRY_TIME_SHORT;
  public static final String TAG_NAME;
  public static final int WALL_ERR_TYPE_API;
  public static final int WALL_ERR_TYPE_CONNECTED;
  public static final int WALL_ERR_TYPE_GETINFO;
  public static final int WALL_ERR_TYPE_NOTFOUND;
  public static final int WALL_TYPE_HTML;
  public static final int WALL_TYPE_ID;
  public static final int WALL_TYPE_NONE;
  public static final int WALL_TYPE_URL;
  public static final int WEBAPI_CONNECTEDERR;
  public static final int WEBAPI_EXCEPTIONERR;
  public static final int WEBAPI_NOERR;
  
  static
  {
    ADFURIKUN_FOLDER = "/adfurikun/";
    GETINFO_FILE = "adfurikun_getinfo.dat";
    PREFKEY_AD_LAST_TIME = "ad_last_time_";
    PREFKEY_DEVICE_ID = "device_id";
    PREFKEY_INTERS_AD_FREQUENCY_CT = "_inters_ad_frequency_ct";
    PREFKEY_INTERS_AD_MAX_CT = "_inters_ad_max_ct";
    PREFKEY_TESTMODE = "test_mode";
    PREFKEY_WALL_APPID = "wall_ad_appid";
    PREF_TESTMODE_NOSETTING = -1;
    PREF_TESTMODE_TEST = 0;
    PREF_TESTMODE_PRODUCT = 1;
    WEBAPI_NOERR = 0;
    WEBAPI_CONNECTEDERR = -1;
    WEBAPI_EXCEPTIONERR = -2;
    TAG_NAME = "adfurikun";
    REGETINFO_TIME = 900000;
    RETRY_TIME = 10000;
    RETRY_TIME_SHORT = 250;
    RETRY_STOP_TIME = 240000;
    RETRY_STOP_TIME_SHORT = 16000;
    DEFAULT_LOCALE = "en";
    LOAD_MAIN_INIT = 0;
    LOAD_MAIN_RETRY = 1;
    LOAD_SUB_INIT = 2;
    LOAD_SUB_RETRY = 3;
    EXTRA_INTERS_AD_INDEX = "inters_ad_index";
    DETAIL_LOG = false;
    WALL_ERR_TYPE_CONNECTED = 1;
    WALL_ERR_TYPE_GETINFO = 2;
    WALL_ERR_TYPE_API = 3;
    WALL_ERR_TYPE_NOTFOUND = 4;
    WALL_TYPE_NONE = 0;
    WALL_TYPE_URL = 1;
    WALL_TYPE_ID = 2;
    WALL_TYPE_HTML = 3;
    ADNETWORKKEY_DEFAULT = "default";
    ADNETWORKKEY_YDN = "1026";
    ADNETWORKKEY_ADLANTIS = "1001";
    ADNETWORKKEY_XMAX = "1037";
    ADNETWORKKEY_APPLI_PROMOTION = "1041";
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunConstants
 * JD-Core Version:    0.7.0.1
 */