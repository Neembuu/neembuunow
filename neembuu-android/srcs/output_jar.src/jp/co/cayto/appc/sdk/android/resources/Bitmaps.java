package jp.co.cayto.appc.sdk.android.resources;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Base64;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.banner_new_image;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.btn_area_bg_bottom_anywhere_dl;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.btn_area_bg_bottom_anywhere_nomal;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.btn_area_bg_bottom_anywhere_other_app;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.btn_area_bg_bottom_cancel;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.btn_area_bg_bottom_dl;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.btn_area_bg_bottom_exit;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.btn_area_bg_bottom_free;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.btn_area_bg_bottom_nomal;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.btn_area_bg_bottom_other_app;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.btn_area_bg_top;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.cutin_b_base_bottom;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.cutin_b_base_top;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.cutin_b_btn_apps;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.cutin_b_btn_exit;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.cutin_b_icon_bg;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.cutin_b_icon_campaign;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.cutin_b_icon_loading;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.cutin_b_icon_noapp;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.hot_h;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.hot_l;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.hot_m;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.hot_non;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.logo_b;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.logo_w;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.marquee_back_putturn;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.marquee_new_icon_l;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.marquee_new_icon_s;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.marquee_new_long_red;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.marquee_new_long_white;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.marquee_open_btn_l;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.marquee_open_btn_s;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.move_icon_bg;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.move_icon_bg_shadow;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.move_icon_icon_webview;
import jp.co.cayto.appc.sdk.android.resources.bitmaps.new_icon;

public class Bitmaps
{
  public static final int BANNER_NEW_IMAGE = 0;
  public static final int BTN_AREA_BG_BOTTOM_ANYWHERE_DL = 1;
  public static final int BTN_AREA_BG_BOTTOM_ANYWHERE_NOMAL = 2;
  public static final int BTN_AREA_BG_BOTTOM_ANYWHERE_OTHER_APP = 3;
  public static final int BTN_AREA_BG_BOTTOM_CANCEL = 4;
  public static final int BTN_AREA_BG_BOTTOM_DL = 5;
  public static final int BTN_AREA_BG_BOTTOM_EXIT = 6;
  public static final int BTN_AREA_BG_BOTTOM_FREE = 7;
  public static final int BTN_AREA_BG_BOTTOM_NOMAL = 8;
  public static final int BTN_AREA_BG_BOTTOM_OTHER_APP = 9;
  public static final int BTN_AREA_BG_TOP = 10;
  public static final int CUTIN_B_BASE_BOTTOM = 11;
  public static final int CUTIN_B_BASE_TOP = 12;
  public static final int CUTIN_B_BTN_APPS = 13;
  public static final int CUTIN_B_BTN_EXIT = 14;
  public static final int CUTIN_B_ICON_BG = 15;
  public static final int CUTIN_B_ICON_CAMPAIGN = 16;
  public static final int CUTIN_B_ICON_LOADING = 17;
  public static final int CUTIN_B_ICON_NOAPP = 18;
  public static final int HOT_H = 19;
  public static final int HOT_L = 20;
  public static final int HOT_M = 21;
  public static final int HOT_NON = 22;
  public static final int LOGO_B = 23;
  public static final int LOGO_W = 24;
  public static final int MARQUEE_BACK_PUTTURN = 25;
  public static final int MARQUEE_NEW_ICON_L = 26;
  public static final int MARQUEE_NEW_ICON_S = 27;
  public static final int MARQUEE_NEW_LONG_RED = 28;
  public static final int MARQUEE_NEW_LONG_WHITE = 29;
  public static final int MARQUEE_OPEN_BTN_L = 30;
  public static final int MARQUEE_OPEN_BTN_S = 31;
  public static final int MOVE_ICON_BG = 32;
  public static final int MOVE_ICON_BG_SHADOW = 33;
  public static final int MOVE_ICON_ICON_WEBVIEW = 34;
  public static final int NEW_ICON = 35;
  static BitmapFactory.Options options = new BitmapFactory.Options();
  
  static
  {
    options.inPurgeable = true;
  }
  
  @TargetApi(8)
  public static Bitmap decodeByBase64(String paramString)
  {
    Object localObject = null;
    try
    {
      byte[] arrayOfByte = Base64.decode(paramString, 0);
      localObject = arrayOfByte;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        BitmapFactory.Options localOptions;
        localException.printStackTrace();
      }
    }
    localOptions = new BitmapFactory.Options();
    localOptions.inPurgeable = true;
    return BitmapFactory.decodeByteArray(localObject, 0, localObject.length, localOptions);
  }
  
  public static Bitmap getBitmap(int paramInt)
  {
    Bitmap localBitmap;
    switch (paramInt)
    {
    default: 
      localBitmap = null;
    }
    for (;;)
    {
      return localBitmap;
      localBitmap = decodeByBase64(new banner_new_image().base64);
      continue;
      localBitmap = decodeByBase64(new btn_area_bg_bottom_anywhere_dl().base64);
      continue;
      localBitmap = decodeByBase64(new btn_area_bg_bottom_anywhere_nomal().base64);
      continue;
      localBitmap = decodeByBase64(new btn_area_bg_bottom_anywhere_other_app().base64);
      continue;
      localBitmap = decodeByBase64(new btn_area_bg_bottom_cancel().base64);
      continue;
      localBitmap = decodeByBase64(new btn_area_bg_bottom_dl().base64);
      continue;
      localBitmap = decodeByBase64(new btn_area_bg_bottom_exit().base64);
      continue;
      localBitmap = decodeByBase64(new btn_area_bg_bottom_free().base64);
      continue;
      localBitmap = decodeByBase64(new btn_area_bg_bottom_nomal().base64);
      continue;
      localBitmap = decodeByBase64(new btn_area_bg_bottom_other_app().base64);
      continue;
      localBitmap = decodeByBase64(new btn_area_bg_top().base64);
      continue;
      localBitmap = decodeByBase64(new cutin_b_base_bottom().base64);
      continue;
      localBitmap = decodeByBase64(new cutin_b_base_top().base64);
      continue;
      localBitmap = decodeByBase64(new cutin_b_btn_apps().base64);
      continue;
      localBitmap = decodeByBase64(new cutin_b_btn_exit().base64);
      continue;
      localBitmap = decodeByBase64(new cutin_b_icon_bg().base64);
      continue;
      localBitmap = decodeByBase64(new cutin_b_icon_campaign().base64);
      continue;
      localBitmap = decodeByBase64(new cutin_b_icon_loading().base64);
      continue;
      localBitmap = decodeByBase64(new cutin_b_icon_noapp().base64);
      continue;
      localBitmap = decodeByBase64(new hot_h().base64);
      continue;
      localBitmap = decodeByBase64(new hot_l().base64);
      continue;
      localBitmap = decodeByBase64(new hot_m().base64);
      continue;
      localBitmap = decodeByBase64(new hot_non().base64);
      continue;
      localBitmap = decodeByBase64(new logo_b().base64);
      continue;
      localBitmap = decodeByBase64(new logo_w().base64);
      continue;
      localBitmap = decodeByBase64(new marquee_back_putturn().base64);
      continue;
      localBitmap = decodeByBase64(new marquee_new_icon_l().base64);
      continue;
      localBitmap = decodeByBase64(new marquee_new_icon_s().base64);
      continue;
      localBitmap = decodeByBase64(new marquee_new_long_red().base64);
      continue;
      localBitmap = decodeByBase64(new marquee_new_long_white().base64);
      continue;
      localBitmap = decodeByBase64(new marquee_open_btn_l().base64);
      continue;
      localBitmap = decodeByBase64(new marquee_open_btn_s().base64);
      continue;
      localBitmap = decodeByBase64(new move_icon_bg().base64);
      continue;
      localBitmap = decodeByBase64(new move_icon_bg_shadow().base64);
      continue;
      localBitmap = decodeByBase64(new move_icon_icon_webview().base64);
      continue;
      localBitmap = decodeByBase64(new new_icon().base64);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.resources.Bitmaps
 * JD-Core Version:    0.7.0.1
 */