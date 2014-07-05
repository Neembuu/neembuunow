package jp.adlantis.android.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri.Builder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class AdlantisUtils
{
  public static int adHeightForOrientation(int paramInt)
  {
    return adSizeForOrientation(paramInt).height();
  }
  
  public static int adHeightPixels(Context paramContext)
  {
    return displayPointsToPixels(paramContext, adHeightForOrientation(paramContext.getResources().getConfiguration().orientation));
  }
  
  public static Rect adSizeForOrientation(int paramInt)
  {
    switch (paramInt)
    {
    }
    for (Rect localRect = new Rect(0, 0, 320, 50);; localRect = new Rect(0, 0, 480, 32)) {
      return localRect;
    }
  }
  
  public static String convertInputToString(InputStream paramInputStream)
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    localStringBuilder = new StringBuilder();
    try
    {
      for (;;)
      {
        String str = localBufferedReader.readLine();
        if (str == null) {
          break;
        }
        localStringBuilder.append(str + "\n");
      }
      try
      {
        paramInputStream.close();
        return localStringBuilder.toString();
      }
      catch (IOException localIOException2)
      {
        for (;;)
        {
          localIOException2.printStackTrace();
        }
      }
    }
    catch (IOException localIOException1)
    {
      localIOException1.printStackTrace();
    }
  }
  
  public static float displayDensity(Context paramContext)
  {
    return paramContext.getResources().getDisplayMetrics().density;
  }
  
  static float displayDensityDpi(Context paramContext)
  {
    f1 = -1.0F;
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
    try
    {
      Field localField = DisplayMetrics.class.getField("densityDpi");
      if (localField == null) {
        break label70;
      }
      int i = localField.getInt(localDisplayMetrics);
      f2 = i;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      break label59;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
      for (;;)
      {
        continue;
        float f2 = f1;
      }
    }
    f1 = f2;
    label59:
    return f1;
  }
  
  public static int displayPointsToPixels(Context paramContext, int paramInt)
  {
    return (int)(displayDensity(paramContext) * paramInt);
  }
  
  public static boolean findClass(String[] paramArrayOfString)
  {
    boolean bool = false;
    for (;;)
    {
      try
      {
        int i = paramArrayOfString.length;
        j = 0;
        if (j >= i) {
          continue;
        }
        Class localClass = Class.forName(paramArrayOfString[j]);
        if (localClass != null) {
          continue;
        }
      }
      catch (Exception localException)
      {
        int j;
        continue;
      }
      return bool;
      j++;
      continue;
      bool = true;
    }
  }
  
  public static boolean hasHighResolutionDisplay(Context paramContext)
  {
    if (displayDensityDpi(paramContext) >= 240.0F) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public static HashMap<String, Object> jsonObjectToHashMap(JSONObject paramJSONObject)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramJSONObject.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      try
      {
        Object localObject = paramJSONObject.get(str);
        if ((localObject instanceof JSONObject)) {
          localObject = jsonObjectToHashMap((JSONObject)localObject);
        }
        localHashMap.put(str, localObject);
      }
      catch (JSONException localJSONException) {}
    }
    return localHashMap;
  }
  
  public static final String md5(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      StringBuffer localStringBuffer = new StringBuffer();
      for (int i = 0; i < arrayOfByte.length; i++)
      {
        for (String str3 = Integer.toHexString(0xFF & arrayOfByte[i]); str3.length() < 2; str3 = "0" + str3) {}
        localStringBuffer.append(str3);
      }
      String str2 = localStringBuffer.toString();
      str1 = str2;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      for (;;)
      {
        localNoSuchAlgorithmException.printStackTrace();
        String str1 = "";
      }
    }
    return str1;
  }
  
  public static int orientation(View paramView)
  {
    return paramView.getResources().getConfiguration().orientation;
  }
  
  public static String orientationToString(int paramInt)
  {
    switch (paramInt)
    {
    }
    for (String str = "portrait";; str = "landscape") {
      return str;
    }
  }
  
  public static void setUriParamsFromMap(Uri.Builder paramBuilder, Map<String, String> paramMap)
  {
    if (paramMap == null) {}
    for (;;)
    {
      return;
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        paramBuilder.appendQueryParameter((String)localEntry.getKey(), (String)localEntry.getValue());
      }
    }
  }
  
  public static void setViewVisibilityOnMainThread(final View paramView, int paramInt)
  {
    new Handler(Looper.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        paramView.setVisibility(paramAnonymousMessage.what);
      }
    }.sendEmptyMessage(paramInt);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.utils.AdlantisUtils
 * JD-Core Version:    0.7.0.1
 */