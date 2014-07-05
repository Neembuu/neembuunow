package com.amoad;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import org.json.JSONException;
import org.json.JSONObject;

class AdBaseView
  extends RelativeLayout
  implements View.OnClickListener
{
  int LANDSCAPE_HEIGHT = 32;
  int LANDSCAPE_WIDTH = 480;
  private final String TAG = "AdBaseView";
  boolean mClickAnimation = false;
  Context mContext = null;
  private final Boolean mDebug = Boolean.valueOf(false);
  float mDensity = 0.0F;
  int mHeight = 0;
  String mModelName = null;
  String mOrientation = "";
  int mWidth = 0;
  
  AdBaseView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }
  
  void doAnimation(int paramInt)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      return;
      AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 1.0F);
      localAlphaAnimation.setDuration(3000L);
      startAnimation(localAlphaAnimation);
      continue;
      RotateAnimation localRotateAnimation = new RotateAnimation(0.0F, 360.0F, this.mWidth / 2, this.mHeight / 2);
      localRotateAnimation.setDuration(3000L);
      startAnimation(localRotateAnimation);
      continue;
      ScaleAnimation localScaleAnimation = new ScaleAnimation(0.7F, 1.0F, 0.7F, 1.0F, 0, this.mWidth / 2, 0, this.mHeight / 2);
      localScaleAnimation.setDuration(1000L);
      startAnimation(localScaleAnimation);
      continue;
      TranslateAnimation localTranslateAnimation = new TranslateAnimation(0.0F, 0.0F, 200.0F, 0.0F);
      localTranslateAnimation.setDuration(3000L);
      startAnimation(localTranslateAnimation);
    }
  }
  
  void loadAdData() {}
  
  void loadView()
  {
    this.mWidth = ((int)(this.mWidth * this.mDensity));
    this.mHeight = ((int)(this.mHeight * this.mDensity));
    if (this.mDebug.booleanValue()) {
      Log.d("AdBaseView", "orientation:" + this.mOrientation + " density:" + this.mDensity + " width:" + this.mWidth + " height:" + this.mHeight);
    }
  }
  
  public void onClick(View paramView) {}
  
  void openUrlwithBrowser(String paramString)
  {
    if ((this.mContext != null) && (!paramString.equals("")))
    {
      Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
      localIntent.addFlags(268435456);
      this.mContext.startActivity(localIntent);
    }
  }
  
  void parseJson(String paramString)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject(paramString);
    this.mWidth = localJSONObject.getInt("width");
    this.mHeight = localJSONObject.getInt("height");
  }
  
  void setClickAnimation(boolean paramBoolean)
  {
    this.mClickAnimation = paramBoolean;
  }
  
  void setDensity(float paramFloat)
  {
    this.mDensity = paramFloat;
  }
  
  void setModleName(String paramString)
  {
    this.mModelName = paramString;
  }
  
  void setOrientation(String paramString)
  {
    this.mOrientation = paramString;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.AdBaseView
 * JD-Core Version:    0.7.0.1
 */