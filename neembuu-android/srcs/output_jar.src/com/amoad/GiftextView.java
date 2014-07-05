package com.amoad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class GiftextView
  extends AdBaseView
{
  private String LICENSE_STRING = "Ads by AMoAd";
  private final String TAG = "GiftextView";
  private TextView mAdsBy = null;
  private Bitmap mBitmap;
  private String mColor = null;
  private String[] mColors;
  private final Boolean mDebug = Boolean.valueOf(false);
  private String mEncode;
  private GradientDrawable mGradient;
  private int[] mGradientBlack;
  private int[] mGradientWhite;
  private String mHrefUrl = null;
  private ImageView mImageView = null;
  private String mSrc = null;
  private TextView mTextView = null;
  private String mTitle = null;
  
  GiftextView(Context paramContext)
  {
    super(paramContext);
    int[] arrayOfInt1 = new int[2];
    arrayOfInt1[0] = -2147483648;
    arrayOfInt1[1] = 0;
    this.mGradientBlack = arrayOfInt1;
    int[] arrayOfInt2 = new int[2];
    arrayOfInt2[0] = -2130706433;
    arrayOfInt2[1] = 16777215;
    this.mGradientWhite = arrayOfInt2;
    this.mGradient = null;
    this.mBitmap = null;
    this.mEncode = "UTF-8";
    initilize(paramContext);
  }
  
  void initilize(Context paramContext)
  {
    this.mContext = paramContext;
    this.mTextView = new TextView(this.mContext);
    this.mTextView.setId(1);
    this.mImageView = new ImageView(this.mContext);
    this.mImageView.setId(2);
    this.mAdsBy = new TextView(this.mContext);
    this.mAdsBy.setId(3);
    setOnClickListener(this);
    setWillNotDraw(false);
  }
  
  void loadAdData()
  {
    if (this.mOrientation.equals("portrait")) {}
    try
    {
      this.mBitmap = BitmapFactory.decodeStream(new URL(this.mSrc).openStream());
      return;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      for (;;)
      {
        if (this.mDebug.booleanValue()) {
          localMalformedURLException.printStackTrace();
        }
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        if (this.mDebug.booleanValue()) {
          localIOException.printStackTrace();
        }
      }
    }
  }
  
  void loadView()
  {
    super.loadView();
    int i = (int)(6.0F * this.mDensity);
    int j = (int)(5.0F * this.mDensity);
    int k = (int)(4.0F * this.mDensity);
    int m = (int)(5.0F * this.mDensity);
    int n = (int)(10.0F * this.mDensity);
    int i1 = (int)(1.0F * this.mDensity);
    int i2 = (int)(38.0F * this.mDensity);
    removeAllViews();
    setLayoutParams(new RelativeLayout.LayoutParams(this.mWidth, this.mHeight));
    setBackgroundColor(Color.parseColor(this.mColors[2]));
    try
    {
      this.mTextView.setText(URLDecoder.decode(this.mTitle, this.mEncode));
      this.mAdsBy.setText(this.LICENSE_STRING);
      if (this.mOrientation.equals("portrait"))
      {
        this.mImageView.setImageBitmap(this.mBitmap);
        RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(i2, i2);
        localLayoutParams3.addRule(10);
        localLayoutParams3.topMargin = i;
        localLayoutParams3.addRule(9);
        localLayoutParams3.leftMargin = j;
        this.mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        addView(this.mImageView, localLayoutParams3);
      }
      RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-2, -2);
      if (this.mOrientation.equals("portrait")) {
        localLayoutParams1.addRule(1, 2);
      }
      localLayoutParams1.leftMargin = m;
      localLayoutParams1.topMargin = k;
      this.mTextView.setTextSize(14.0F);
      this.mTextView.setTextColor(Color.parseColor(this.mColors[0]));
      addView(this.mTextView, localLayoutParams1);
      RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
      localLayoutParams2.addRule(12);
      localLayoutParams2.addRule(11);
      localLayoutParams2.rightMargin = n;
      localLayoutParams2.bottomMargin = i1;
      this.mAdsBy.setTextSize(10.0F);
      this.mAdsBy.setTextColor(Color.parseColor(this.mColors[0]));
      addView(this.mAdsBy, localLayoutParams2);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;)
      {
        if (this.mDebug.booleanValue()) {
          localUnsupportedEncodingException.printStackTrace();
        }
      }
    }
  }
  
  public void onClick(View paramView)
  {
    super.onClick(paramView);
    if (this.mClickAnimation)
    {
      ScaleAnimation localScaleAnimation = new ScaleAnimation(1.0F, 1.3F, 1.0F, 1.3F, 0, this.mWidth / 2, 0, this.mHeight / 2);
      localScaleAnimation.setDuration(1000L);
      localScaleAnimation.setAnimationListener(new Animation.AnimationListener()
      {
        public void onAnimationEnd(Animation paramAnonymousAnimation)
        {
          GiftextView.this.openUrlwithBrowser(GiftextView.this.mHrefUrl);
        }
        
        public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
        
        public void onAnimationStart(Animation paramAnonymousAnimation) {}
      });
      startAnimation(localScaleAnimation);
    }
    for (;;)
    {
      return;
      openUrlwithBrowser(this.mHrefUrl);
    }
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    this.mGradient.setBounds(new Rect(0, 0, this.mWidth, this.mHeight / 2));
    this.mGradient.draw(paramCanvas);
    super.onDraw(paramCanvas);
  }
  
  void parseColor()
  {
    if (this.mColor != null) {
      this.mColors = this.mColor.split("-");
    }
  }
  
  void parseJson(String paramString)
    throws JSONException
  {
    super.parseJson(paramString);
    if (this.mDebug.booleanValue()) {
      Log.d("GiftextView", paramString);
    }
    JSONObject localJSONObject = new JSONObject(paramString);
    this.mColor = localJSONObject.getString("color");
    parseColor();
    setGradient(this.mColors[2]);
    JSONArray localJSONArray = localJSONObject.getJSONArray("ads");
    this.mTitle = localJSONArray.getJSONObject(0).getString("title");
    this.mSrc = localJSONArray.getJSONObject(0).getString("src");
    this.mHrefUrl = localJSONArray.getJSONObject(0).getString("href");
  }
  
  void setEncode(String paramString)
  {
    this.mEncode = paramString;
  }
  
  void setGradient(String paramString)
  {
    if (paramString.equals("#FFFFFF")) {}
    for (int[] arrayOfInt = this.mGradientBlack;; arrayOfInt = this.mGradientWhite)
    {
      this.mGradient = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, arrayOfInt);
      return;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.GiftextView
 * JD-Core Version:    0.7.0.1
 */