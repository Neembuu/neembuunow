package jp.adlantis.android.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class ADLDebugUtils
{
  static String DEBUG_CONTEXT = "ADLDebugUtils";
  
  static void dumpMemoryInfo(Context paramContext)
  {
    ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");
    ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
    localActivityManager.getMemoryInfo(localMemoryInfo);
    logd(" memoryInfo.availMem " + localMemoryInfo.availMem);
    logd(" memoryInfo.lowMemory " + localMemoryInfo.lowMemory);
    logd(" memoryInfo.threshold " + localMemoryInfo.threshold);
    logd(" Debug.getNativeHeapAllocatedSize " + Debug.getNativeHeapAllocatedSize());
    logd(" Debug.getNativeHeapFreeSize " + Debug.getNativeHeapFreeSize());
  }
  
  public static void dumpSubviewLayout(View paramView, int paramInt)
  {
    dumpViewGeometry(paramView, paramInt);
    if ((paramView instanceof ViewGroup))
    {
      ViewGroup localViewGroup = (ViewGroup)paramView;
      for (int i = 0; i < localViewGroup.getChildCount(); i++) {
        dumpSubviewLayout(localViewGroup.getChildAt(i), paramInt + 2);
      }
    }
  }
  
  public static void dumpViewGeometry(View paramView, int paramInt)
  {
    logd(spaces(paramInt) + "view = " + paramView + " l = " + paramView.getLeft() + " t = " + paramView.getTop() + " r = " + paramView.getRight() + " b = " + paramView.getBottom());
  }
  
  static void dumpViewGroupInfo(ViewGroup paramViewGroup)
  {
    logd(" childCount=" + paramViewGroup.getChildCount());
    for (int i = 0; i < paramViewGroup.getChildCount(); i++)
    {
      View localView = paramViewGroup.getChildAt(i);
      logd("view[" + i + "]=" + localView);
      Rect localRect = new Rect();
      Point localPoint = new Point();
      boolean bool = paramViewGroup.getChildVisibleRect(localView, localRect, localPoint);
      logd("result=" + bool + " childRect=" + localRect + " childPoint=" + localPoint);
    }
  }
  
  static void logd(String paramString)
  {
    Log.d(DEBUG_CONTEXT, paramString);
  }
  
  public static String spaces(int paramInt)
  {
    String str2;
    Object[] arrayOfObject;
    if (paramInt > 0)
    {
      str2 = "%" + paramInt + "s";
      arrayOfObject = new Object[1];
      arrayOfObject[0] = "";
    }
    for (String str1 = String.format(str2, arrayOfObject);; str1 = "") {
      return str1;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.utils.ADLDebugUtils
 * JD-Core Version:    0.7.0.1
 */