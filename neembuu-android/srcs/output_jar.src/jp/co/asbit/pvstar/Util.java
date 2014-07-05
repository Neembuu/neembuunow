package jp.co.asbit.pvstar;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Util
{
  private static final String EMPTY = "";
  private static final String EQUALIZER_BANDS_LEVEL = "equalizer";
  private static final String EQUALIZER_ENABLED = "equalizer_enabled";
  private static final String EQUALIZER_PRESET = "equalizer_preset";
  public static final String ERROR_OCCUERED = "error_occuered";
  private static final String IS_POPUP_PLAY_MODEL = "is_popup_play_model";
  public static final String PLAY_COUNT = "play_count";
  public static final String REVIEW_REQUEST = "review_request";
  private static final String SPACE = " ";
  private static final String TAG = "Util";
  
  public static String empty()
  {
    return "";
  }
  
  static String getCacheDir(Context paramContext, String paramString)
  {
    File localFile = new File(paramContext.getExternalCacheDir(), paramString);
    if ((!localFile.exists()) && (!localFile.mkdir())) {}
    for (String str = null;; str = localFile.getPath()) {
      return str;
    }
  }
  
  public static Map<String, String> getQueryMap(String paramString)
  {
    String[] arrayOfString1 = paramString.split("&");
    HashMap localHashMap = new HashMap();
    int i = arrayOfString1.length;
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return localHashMap;
      }
      String[] arrayOfString2 = arrayOfString1[j].split("=");
      localHashMap.put(arrayOfString2[0], arrayOfString2[1]);
    }
  }
  
  public static void incPlayCount(Context paramContext)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    localSharedPreferences.edit().putLong("play_count", 1L + localSharedPreferences.getLong("play_count", 0L)).commit();
  }
  
  public static boolean isEqualizerEnabled(Context paramContext)
  {
    return PreferenceManager.getDefaultSharedPreferences(paramContext).getBoolean("equalizer_enabled", false);
  }
  
  public static boolean isPopUpPlayModel(Context paramContext)
  {
    return PreferenceManager.getDefaultSharedPreferences(paramContext).getBoolean("is_popup_play_model", true);
  }
  
  public static boolean isServiceRunning(Context paramContext, Class<?> paramClass)
  {
    Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningServices(2147483647).iterator();
    if (!localIterator.hasNext()) {}
    for (boolean bool = false;; bool = true)
    {
      return bool;
      ActivityManager.RunningServiceInfo localRunningServiceInfo = (ActivityManager.RunningServiceInfo)localIterator.next();
      if (!paramClass.getName().equals(localRunningServiceInfo.service.getClassName())) {
        break;
      }
    }
  }
  
  public static boolean isStringBlank(String paramString)
  {
    if ((paramString != null) && (!paramString.equals(""))) {}
    for (boolean bool = false;; bool = true) {
      return bool;
    }
  }
  
  public static int[] loadEqualizerBandsLevel(Context paramContext)
  {
    try
    {
      String[] arrayOfString = PreferenceManager.getDefaultSharedPreferences(paramContext).getString("equalizer", null).split(",");
      int[] arrayOfInt = new int[arrayOfString.length];
      for (int i = 0; i < arrayOfString.length; i++) {
        arrayOfInt[i] = Integer.parseInt(arrayOfString[i]);
      }
      return arrayOfInt;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      arrayOfInt = null;
    }
  }
  
  public static int loadEqualizerCurrentPreset(Context paramContext)
  {
    return PreferenceManager.getDefaultSharedPreferences(paramContext).getInt("equalizer_preset", 0);
  }
  
  public static void saveEqualizerBandsLevel(short[] paramArrayOfShort, Context paramContext)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfShort.length)
      {
        SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
        String str1 = localStringBuilder.toString();
        String str2 = str1.substring(0, -1 + str1.length());
        localSharedPreferences.edit().putString("equalizer", str2).commit();
        return;
      }
      localStringBuilder.append(paramArrayOfShort[i]).append(",");
    }
  }
  
  public static void saveEqualizerCurrentPreset(int paramInt, Context paramContext)
  {
    PreferenceManager.getDefaultSharedPreferences(paramContext).edit().putInt("equalizer_preset", paramInt).commit();
  }
  
  public static void setEqualizerEnabled(Context paramContext, boolean paramBoolean)
  {
    PreferenceManager.getDefaultSharedPreferences(paramContext).edit().putBoolean("equalizer_enabled", paramBoolean).commit();
  }
  
  public static void setPopUpPlayModel(Context paramContext, boolean paramBoolean)
  {
    PreferenceManager.getDefaultSharedPreferences(paramContext).edit().putBoolean("is_popup_play_model", paramBoolean).commit();
  }
  
  public static void setVideoError(Context paramContext, boolean paramBoolean)
  {
    PreferenceManager.getDefaultSharedPreferences(paramContext).edit().putBoolean("error_occuered", paramBoolean).commit();
  }
  
  public static boolean shouldShowReviewRequest(Context paramContext)
  {
    boolean bool1 = false;
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    if (localSharedPreferences.getBoolean("review_request", false)) {}
    for (;;)
    {
      return bool1;
      boolean bool2 = localSharedPreferences.getBoolean("error_occuered", false);
      long l = localSharedPreferences.getLong("play_count", 0L);
      if ((!bool2) && (l > 20L))
      {
        localSharedPreferences.edit().putBoolean("review_request", true).commit();
        bool1 = true;
      }
    }
  }
  
  public static String space()
  {
    return " ";
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.Util
 * JD-Core Version:    0.7.0.1
 */