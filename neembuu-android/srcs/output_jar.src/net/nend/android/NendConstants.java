package net.nend.android;

final class NendConstants
{
  static final boolean IS_DEBUG_CODE = false;
  static final String NEND_UID_FILE_NAME = "NENDUUID";
  static final String VERSION = "2.1.0";
  
  static enum Attribute
  {
    private String name;
    
    static
    {
      API_KEY = new Attribute("API_KEY", 1, "NendApiKey");
      RELOADABLE = new Attribute("RELOADABLE", 2, "NendReloadable");
      Attribute[] arrayOfAttribute = new Attribute[3];
      arrayOfAttribute[0] = SPOT_ID;
      arrayOfAttribute[1] = API_KEY;
      arrayOfAttribute[2] = RELOADABLE;
      $VALUES = arrayOfAttribute;
    }
    
    private Attribute(String paramString)
    {
      this.name = paramString;
    }
    
    String getName()
    {
      return this.name;
    }
  }
  
  static enum MetaData
  {
    private String name;
    
    static
    {
      ADAUTHORITY = new MetaData("ADAUTHORITY", 1, "NendAdAuthority");
      ADPATH = new MetaData("ADPATH", 2, "NendAdPath");
      OPT_OUT_URL = new MetaData("OPT_OUT_URL", 3, "NendOptOutUrl");
      OPT_OUT_IMAGE_URL = new MetaData("OPT_OUT_IMAGE_URL", 4, "NendOptOutImageUrl");
      MetaData[] arrayOfMetaData = new MetaData[5];
      arrayOfMetaData[0] = ADSCHEME;
      arrayOfMetaData[1] = ADAUTHORITY;
      arrayOfMetaData[2] = ADPATH;
      arrayOfMetaData[3] = OPT_OUT_URL;
      arrayOfMetaData[4] = OPT_OUT_IMAGE_URL;
      $VALUES = arrayOfMetaData;
    }
    
    private MetaData(String paramString)
    {
      this.name = paramString;
    }
    
    String getName()
    {
      return this.name;
    }
  }
  
  static final class OptOutParams
  {
    static final String IMAGE_URL = "http://img1.nend.net/img/common/optout/icon.png";
    static final String PAGE_URL = "http://nend.net/privacy/optsdkgate";
  }
  
  static final class RequestParams
  {
    static final String DOMAIN = "ad1.nend.net";
    static final String PATH = "na.php";
    static final String PROTOCOL = "http";
  }
  
  static class NendHttpParams
  {
    static final int CONNECTION_TIMEOUT_IN_SECOND = 10;
    static final int SOCKET_TIMEOUT_IN_SECOND = 10;
  }
  
  static class AdDefaultParams
  {
    static final int HEIGHT = 50;
    static final int MAX_AD_RELOAD_INTERVAL_IN_SECONDS = 99999;
    static final int MIN_AD_RELOAD_INTERVAL_IN_SECONDS = 30;
    static final int RELOAD_INTERVAL_IN_SECONDS = 60;
    static final int WIDTH = 320;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendConstants
 * JD-Core Version:    0.7.0.1
 */