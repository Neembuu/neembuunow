package com.amoad.amoadsdk;

import android.app.Activity;

public abstract class AMoAdSdk
{
  public static boolean isFirstOnToday(Activity paramActivity)
  {
    Config.initialize(paramActivity);
    return Util.isFirstOnToday(paramActivity);
  }
  
  public static void sendUUID(Activity paramActivity)
  {
    Config.initialize(paramActivity);
    Util.sendConversion(paramActivity);
  }
  
  public static void showTrigger(Activity paramActivity, String paramString, int paramInt1, int paramInt2)
  {
    new AMoAdSdkTrigger().showTrigger(paramActivity, paramString, paramInt1, paramInt2);
  }
  
  public static void showTriggerForUnity(Activity paramActivity, String paramString, int paramInt1, int paramInt2)
  {
    new AMoAdSdkTrigger().showTriggerForUnity(paramActivity, paramString, paramInt1, paramInt2);
  }
  
  public static void startPopup(Activity paramActivity, String paramString)
  {
    AMoAdSdkPopUp.startPopUp(paramActivity, paramString);
  }
  
  public static void startPopupForUnity(Activity paramActivity, String paramString)
  {
    AMoAdSdkPopUp.startPopUpForUnity(paramActivity, paramString);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.AMoAdSdk
 * JD-Core Version:    0.7.0.1
 */