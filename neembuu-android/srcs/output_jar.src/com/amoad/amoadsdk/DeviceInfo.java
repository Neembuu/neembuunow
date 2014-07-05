package com.amoad.amoadsdk;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

class DeviceInfo
{
  static final double BASE_LANDSCAPE_HEIGHT = 300.0D;
  static final double BASE_LANDSCAPE_WIDTH = 480.0D;
  static final double BASE_PORTRAIT_HEIGHT = 460.0D;
  static final double BASE_PORTRAIT_WIDTH = 320.0D;
  double aspectRaito;
  double clientHeight;
  double clientWidth;
  double deviceHeight;
  double deviceWidth;
  boolean isPortrait;
  double raitoX;
  double raitoY;
  double statusBarHeight;
  
  DeviceInfo(Activity paramActivity)
  {
    initScreenInfo(paramActivity);
  }
  
  public static int getStatusBarHeight(Display paramDisplay)
  {
    int i = 25;
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    paramDisplay.getMetrics(localDisplayMetrics);
    switch (localDisplayMetrics.densityDpi)
    {
    }
    for (;;)
    {
      return i;
      i = 38;
      continue;
      i = 19;
    }
  }
  
  public void initScreenInfo(Activity paramActivity)
  {
    Display localDisplay = ((WindowManager)paramActivity.getSystemService("window")).getDefaultDisplay();
    this.deviceWidth = localDisplay.getWidth();
    this.deviceHeight = localDisplay.getHeight();
    this.statusBarHeight = getStatusBarHeight(localDisplay);
    this.clientWidth = this.deviceWidth;
    this.clientHeight = (this.deviceHeight - this.statusBarHeight);
    if (isPortrait())
    {
      this.aspectRaito = (1.4375D * (this.deviceHeight / this.deviceWidth));
      this.raitoX = (this.clientWidth / 320.0D);
      this.raitoY = (this.clientHeight / 460.0D);
    }
    for (;;)
    {
      return;
      this.aspectRaito = (0.625D * (this.deviceWidth / this.deviceHeight));
      this.raitoY = (this.clientWidth / 480.0D);
      this.raitoX = (this.clientHeight / 300.0D);
    }
  }
  
  public boolean isPortrait()
  {
    if (this.deviceHeight > this.deviceWidth) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public int x(int paramInt)
  {
    return (int)(this.raitoX * paramInt);
  }
  
  public int y(int paramInt)
  {
    return (int)(this.raitoY * paramInt);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.DeviceInfo
 * JD-Core Version:    0.7.0.1
 */