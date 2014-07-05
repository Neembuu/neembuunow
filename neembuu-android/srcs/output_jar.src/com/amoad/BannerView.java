package com.amoad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

class BannerView
  extends AdBaseView
{
  private final String TAG = "BannerView";
  private Boolean isGif = Boolean.valueOf(false);
  private String mAlterSrcUrl = "";
  private String mBannerUrl = "";
  private Bitmap mBitmap = null;
  private final Boolean mDebug = Boolean.valueOf(false);
  private String mHrefUrl = "";
  private ImageView mImageView = null;
  private String mSrcUrl = "";
  private WebView mWebView = null;
  View.OnTouchListener touchlistener = new View.OnTouchListener()
  {
    public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
    {
      if (BannerView.this.mClickAnimation)
      {
        ScaleAnimation localScaleAnimation = new ScaleAnimation(1.0F, 1.3F, 1.0F, 1.3F, 0, BannerView.this.mWidth / 2, 0, BannerView.this.mHeight / 2);
        localScaleAnimation.setDuration(1000L);
        localScaleAnimation.setAnimationListener(new Animation.AnimationListener()
        {
          public void onAnimationEnd(Animation paramAnonymous2Animation)
          {
            BannerView.this.openUrlwithBrowser(BannerView.this.mHrefUrl);
          }
          
          public void onAnimationRepeat(Animation paramAnonymous2Animation) {}
          
          public void onAnimationStart(Animation paramAnonymous2Animation) {}
        });
        BannerView.this.startAnimation(localScaleAnimation);
      }
      for (;;)
      {
        return false;
        BannerView.this.openUrlwithBrowser(BannerView.this.mHrefUrl);
      }
    }
  };
  
  BannerView(Context paramContext)
  {
    super(paramContext);
    initialize(paramContext);
  }
  
  private void initialize(Context paramContext)
  {
    this.mImageView = new ImageView(this.mContext);
    this.mWebView = new WebView(this.mContext);
    this.mWebView.setOnTouchListener(this.touchlistener);
    setOnClickListener(this);
  }
  
  void loadAdData()
  {
    try
    {
      if (this.mOrientation.equals("landscape")) {}
      for (this.mBannerUrl = this.mAlterSrcUrl;; this.mBannerUrl = this.mSrcUrl)
      {
        this.isGif = Boolean.valueOf(this.mBannerUrl.endsWith(".gif"));
        if (this.isGif.booleanValue()) {
          break;
        }
        this.mBitmap = BitmapFactory.decodeStream(new URL(this.mBannerUrl).openStream());
        break;
      }
      return;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      if (this.mDebug.booleanValue()) {
        localMalformedURLException.printStackTrace();
      }
    }
    catch (IOException localIOException)
    {
      if (this.mDebug.booleanValue()) {
        localIOException.printStackTrace();
      }
    }
  }
  
  void loadView()
  {
    super.loadView();
    removeAllViews();
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(this.mWidth, this.mHeight);
    setLayoutParams(localLayoutParams);
    if (this.isGif.booleanValue())
    {
      this.mWebView.loadUrl(this.mBannerUrl);
      addView(this.mWebView, localLayoutParams);
    }
    for (;;)
    {
      return;
      if (this.mBitmap != null)
      {
        this.mImageView.setImageBitmap(this.mBitmap);
        addView(this.mImageView, localLayoutParams);
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
          BannerView.this.openUrlwithBrowser(BannerView.this.mHrefUrl);
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
  
  void parseJson(String paramString)
    throws JSONException
  {
    super.parseJson(paramString);
    if (this.mDebug.booleanValue()) {
      Log.d("BannerView", paramString);
    }
    JSONObject localJSONObject = new JSONObject(paramString);
    this.mSrcUrl = localJSONObject.getString("src");
    this.mHrefUrl = localJSONObject.getString("href");
    if (this.mOrientation.equals("landscape")) {
      this.mAlterSrcUrl = localJSONObject.getJSONObject("alter").getString("src");
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.BannerView
 * JD-Core Version:    0.7.0.1
 */