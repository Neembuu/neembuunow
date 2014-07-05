package jp.co.cayto.appc.sdk.android.entity;

import android.text.TextUtils;
import java.util.HashMap;

public class HttpApp
{
  public static final String AD_APPS_ID = "ad_apps_id";
  public static final String APP_AM_CATEGORY = "app_am_category";
  public static final String APP_DESCRIPTION = "app_description";
  public static final String APP_ID = "app_id";
  public static final String APP_IMAGE = "app_image";
  public static final String APP_MOMENT = "app_moment";
  public static final String APP_NAME = "app_name";
  public static final String APP_PRICE = "app_price";
  public static final String APP_RANK = "app_rank";
  public static final String APP_RATING = "app_rating";
  public static final String APP_TYPE = "app_type";
  public static final String APP_VERSION = "app_version";
  public static final String BANNER_URL = "banner_url";
  public static final String CAMPAIGN_ID = "campaign_id";
  public static final String CAMPAIGN_URL = "campaign_url";
  public static final String CNV_AD_APPS_ID = "ad_apps_id";
  public static final String CNV_BANNER_URL = "banner_url";
  public static final String CNV_CAMPAIGN_ID = "campaign_id";
  public static final String CNV_CAMPAIGN_URL = "campaign_url";
  public static final String CNV_CATEGORY = "category";
  public static final String CNV_CONTENT_AUTHOR = "content_author";
  public static final String CNV_CONTENT_BODY = "content_body";
  public static final String CNV_CONTENT_DATE = "content_date";
  public static final String CNV_CONTENT_ID = "content_id";
  public static final String CNV_CONTENT_PROMO_TXT = "content_promo_txt";
  public static final String CNV_CONTENT_URL = "content_url";
  public static final String CNV_DESCRIPTION = "description";
  public static final String CNV_ICON_URL = "icon_url";
  public static final String CNV_MARKEE_TEXT = "markee_text";
  public static final String CNV_MOMENT = "moment";
  public static final String CNV_PACKAGE = "package";
  public static final String CNV_PRICE = "price";
  public static final String CNV_RANK = "rank";
  public static final String CNV_RATING = "rating";
  public static final String CNV_REDIRECT_URL = "redirect_url";
  public static final String CNV_TITLE = "title";
  public static final String CNV_TYPE = "type";
  public static final String CONTENT_AUTHOR = "content_author";
  public static final String CONTENT_BODY = "content_body";
  public static final String CONTENT_DATE = "content_date";
  public static final String CONTENT_ID = "content_id";
  public static final String CONTENT_PROMO_TXT = "content_promo_txt";
  public static final String CONTENT_URL = "content_url";
  public static final String MARKEE_TEXT = "markee_text";
  public static final String REDIRECT_URL = "redirect_url";
  private static final HashMap<String, String> checkMap;
  private static final HashMap<String, String> convertMap = new HashMap();
  private HashMap<String, String> map;
  
  static
  {
    convertMap.put("package", "app_id");
    convertMap.put("title", "app_name");
    convertMap.put("description", "app_description");
    convertMap.put("markee_text", "markee_text");
    convertMap.put("icon_url", "app_image");
    convertMap.put("banner_url", "banner_url");
    convertMap.put("redirect_url", "redirect_url");
    convertMap.put("price", "app_price");
    convertMap.put("rating", "app_rating");
    convertMap.put("moment", "app_moment");
    convertMap.put("rank", "app_rank");
    convertMap.put("type", "app_type");
    convertMap.put("category", "app_am_category");
    convertMap.put("ad_apps_id", "ad_apps_id");
    convertMap.put("content_url", "content_url");
    convertMap.put("content_body", "content_body");
    convertMap.put("content_id", "content_id");
    convertMap.put("content_promo_txt", "content_promo_txt");
    convertMap.put("content_date", "content_date");
    convertMap.put("content_author", "content_author");
    convertMap.put("campaign_id", "campaign_id");
    convertMap.put("campaign_url", "campaign_url");
    checkMap = new HashMap();
    checkMap.put("app_id", "package");
    checkMap.put("app_name", "title");
    checkMap.put("app_description", "description");
    checkMap.put("markee_text", "markee_text");
    checkMap.put("app_image", "icon_url");
    checkMap.put("banner_url", "banner_url");
    checkMap.put("redirect_url", "redirect_url");
    checkMap.put("app_price", "price");
    checkMap.put("app_rating", "rating");
    checkMap.put("app_moment", "moment");
    checkMap.put("app_rank", "rank");
    checkMap.put("app_type", "type");
    checkMap.put("app_category", "category");
    checkMap.put("ad_apps_id", "ad_apps_id");
    checkMap.put("content_url", "content_url");
    checkMap.put("content_body", "content_body");
    checkMap.put("content_id", "content_id");
    checkMap.put("content_promo_txt", "content_promo_txt");
    checkMap.put("content_date", "content_date");
    checkMap.put("content_author", "content_author");
    checkMap.put("campaign_id", "campaign_id");
    checkMap.put("campaign_url", "campaign_url");
  }
  
  public HttpApp()
  {
    this.map = new HashMap();
  }
  
  public HttpApp(HashMap<String, String> paramHashMap)
  {
    this.map = paramHashMap;
  }
  
  private String convertKey(String paramString)
  {
    if (convertMap.containsKey(paramString)) {}
    for (String str = (String)convertMap.get(paramString);; str = "")
    {
      TextUtils.isEmpty(str);
      return str;
    }
  }
  
  public String getCnvValue(String paramString)
  {
    return getValue(convertKey(paramString));
  }
  
  public HashMap<String, String> getMap()
  {
    return this.map;
  }
  
  public String getValue(String paramString)
  {
    if (this.map.containsKey(paramString)) {}
    for (String str = (String)this.map.get(paramString);; str = "") {
      return str;
    }
  }
  
  public void setCnvValue(String paramString1, String paramString2)
  {
    setValue(convertKey(paramString1), paramString2);
  }
  
  public void setValue(String paramString1, String paramString2)
  {
    HashMap localHashMap = this.map;
    if (paramString2 == null) {}
    for (String str = "";; str = paramString2)
    {
      localHashMap.put(paramString1, str);
      if (!this.map.containsKey(checkMap.get(paramString1))) {
        this.map.put((String)checkMap.get(paramString1), paramString2);
      }
      return;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.entity.HttpApp
 * JD-Core Version:    0.7.0.1
 */