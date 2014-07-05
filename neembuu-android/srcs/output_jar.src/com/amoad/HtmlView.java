package com.amoad;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout.LayoutParams;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import org.json.JSONException;
import org.json.JSONObject;

class HtmlView
  extends AdBaseView
{
  private final String JSON_HTML = "html";
  private final String JSON_IFRAMELOCATION = "iframeLocation";
  private final String TAG = "HtmlView";
  private final Boolean mDebug = Boolean.valueOf(false);
  private String mEncode = "UTF-8";
  private String mHtml = "";
  private String mIframeLocation = "";
  private String mUrl;
  private WebView mWebView = null;
  
  HtmlView(Context paramContext)
  {
    super(paramContext);
    initilize(paramContext);
  }
  
  void initilize(Context paramContext)
  {
    this.mWebView = new WebView(this.mContext);
  }
  
  void loadView()
  {
    super.loadView();
    if ((!this.mIframeLocation.equals("")) || (!this.mHtml.equals("")))
    {
      removeAllViews();
      this.mWebView.clearView();
      this.mWebView.getSettings().setJavaScriptEnabled(true);
      this.mWebView.setVerticalScrollbarOverlay(true);
      this.mWebView.setWebViewClient(new MyWebViewClient());
      if (this.mHtml.equals("")) {
        break label152;
      }
      if (this.mDebug.booleanValue()) {
        Log.d("HtmlView", "route html");
      }
      this.mWebView.loadDataWithBaseURL("http://adcloud.jp", this.mHtml, "text/html", "UTF-8", null);
    }
    for (;;)
    {
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(this.mWidth, this.mHeight);
      setLayoutParams(localLayoutParams);
      addView(this.mWebView, localLayoutParams);
      return;
      label152:
      if (this.mDebug.booleanValue()) {
        Log.d("HtmlView", "route iframelocation");
      }
      this.mWebView.loadUrl(this.mIframeLocation);
    }
  }
  
  void parseJson(String paramString)
    throws JSONException
  {
    super.parseJson(paramString);
    if (this.mDebug.booleanValue()) {
      Log.d("HtmlView", paramString);
    }
    this.mIframeLocation = "";
    this.mHtml = "";
    JSONObject localJSONObject = new JSONObject(paramString);
    if (localJSONObject.has("iframeLocation")) {
      this.mIframeLocation = localJSONObject.getString("iframeLocation");
    }
    if (localJSONObject.has("html")) {}
    try
    {
      this.mHtml = URLDecoder.decode(localJSONObject.getString("html"), this.mEncode);
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
  
  void setEncode(String paramString)
  {
    this.mEncode = paramString;
  }
  
  private class MyWebViewClient
    extends WebViewClient
  {
    public MyWebViewClient() {}
    
    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      HtmlView.access$002(HtmlView.this, paramString);
      if (HtmlView.this.mClickAnimation)
      {
        ScaleAnimation localScaleAnimation = new ScaleAnimation(1.0F, 1.3F, 1.0F, 1.3F, 0, HtmlView.this.mWidth / 2, 0, HtmlView.this.mHeight / 2);
        localScaleAnimation.setDuration(1000L);
        localScaleAnimation.setAnimationListener(new Animation.AnimationListener()
        {
          public void onAnimationEnd(Animation paramAnonymousAnimation)
          {
            HtmlView.this.openUrlwithBrowser(HtmlView.this.mUrl);
          }
          
          public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
          
          public void onAnimationStart(Animation paramAnonymousAnimation) {}
        });
        HtmlView.this.startAnimation(localScaleAnimation);
      }
      for (;;)
      {
        return true;
        HtmlView.this.openUrlwithBrowser(HtmlView.this.mUrl);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.HtmlView
 * JD-Core Version:    0.7.0.1
 */