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
import java.util.LinkedHashMap;
import org.json.JSONException;
import org.json.JSONObject;

class RtbView
  extends AdBaseView
{
  private static final String SSP_ID = "100003";
  private static final String TAG = "RtbView";
  private ConfigFile mConfig = null;
  private final Boolean mDebug = Boolean.valueOf(false);
  private String mDspName = "";
  private String mDspUid = "";
  private String mEncode = "UTF-8";
  private String mHtml = "";
  private String mUrl = "";
  private WebView mWebView = null;
  
  RtbView(Context paramContext)
  {
    super(paramContext);
    initilize(paramContext);
  }
  
  private void initilize(Context paramContext)
  {
    this.mWebView = new WebView(this.mContext);
  }
  
  void loadAdData()
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        RtbView.access$002(RtbView.this, new ConfigFile(RtbView.this.mContext));
        RtbView.this.mConfig.generateFileName(RtbView.this.mDspName, RtbView.this.mModelName);
        if (RtbView.this.mDebug.booleanValue()) {
          Log.d("RtbView", "read localfile.");
        }
        LinkedHashMap localLinkedHashMap3;
        if (!RtbView.this.mConfig.readLoacalFile())
        {
          if (RtbView.this.mDebug.booleanValue()) {
            Log.d("RtbView", "read other's packages");
          }
          if (!RtbView.this.mConfig.readOtherFile())
          {
            if (RtbView.this.mDebug.booleanValue()) {
              Log.d("RtbView", "new config file");
            }
            localLinkedHashMap3 = RtbView.this.mConfig.getConfig();
            if ((RtbView.this.mDspUid == null) || (RtbView.this.mDspUid.equals(""))) {
              break label395;
            }
            localLinkedHashMap3.put("dspuid", RtbView.this.mDspUid);
            localLinkedHashMap3.put("dsp_name", RtbView.this.mDspName);
            RtbView.this.mConfig.setConfig(localLinkedHashMap3);
          }
        }
        LinkedHashMap localLinkedHashMap1 = RtbView.this.mConfig.getConfig();
        if ((RtbView.this.mDspUid != null) && (!RtbView.this.mDspUid.equals("")))
        {
          if (!localLinkedHashMap1.containsKey("dspuid")) {
            break label408;
          }
          if (!((String)localLinkedHashMap1.get("dspuid")).equals(RtbView.this.mDspUid))
          {
            localLinkedHashMap1.put("dspuid", RtbView.this.mDspUid);
            RtbView.this.mConfig.setConfig(localLinkedHashMap1);
          }
        }
        for (;;)
        {
          if (RtbView.this.mDebug.booleanValue())
          {
            LinkedHashMap localLinkedHashMap2 = RtbView.this.mConfig.getConfig();
            Log.d("RtbView", "mDspUid:" + RtbView.this.mDspUid);
            Log.d("RtbView", "ConfigFile.KEY_DSPUID:" + (String)localLinkedHashMap2.get("dspuid"));
          }
          return;
          label395:
          localLinkedHashMap3.put("dspuid", "");
          break;
          label408:
          localLinkedHashMap1.put("dspuid", RtbView.this.mDspUid);
          RtbView.this.mConfig.setConfig(localLinkedHashMap1);
        }
      }
    }).start();
    if ((this.mDspUid != null) && (!this.mDspUid.equals("")))
    {
      this.mHtml = this.mHtml.replaceAll("\\$\\{DSP_UID\\}", this.mDspUid);
      this.mHtml = this.mHtml.replaceAll("\\$\\{SSP_ID\\}", "100003");
    }
    if (this.mDebug.booleanValue()) {
      Log.d("RtbView", "HTML:" + this.mHtml);
    }
  }
  
  void loadView()
  {
    super.loadView();
    removeAllViews();
    this.mWebView.clearView();
    this.mWebView.getSettings().setJavaScriptEnabled(true);
    this.mWebView.setVerticalScrollbarOverlay(true);
    this.mWebView.setWebViewClient(new MyWebViewClient());
    this.mWebView.loadDataWithBaseURL("http://adcloud.jp", this.mHtml, "text/html", "UTF-8", null);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(this.mWidth, this.mHeight);
    setLayoutParams(localLayoutParams);
    addView(this.mWebView, localLayoutParams);
  }
  
  void parseJson(String paramString)
    throws JSONException
  {
    super.parseJson(paramString);
    if (this.mDebug.booleanValue()) {
      Log.d("RtbView", paramString);
    }
    for (;;)
    {
      try
      {
        JSONObject localJSONObject = new JSONObject(paramString);
        try
        {
          this.mDspName = localJSONObject.getString("dsp_name");
          this.mHtml = URLDecoder.decode(localJSONObject.getString("html"), this.mEncode);
          if (localJSONObject.has("dsp_uid"))
          {
            this.mDspUid = localJSONObject.getString("dsp_uid");
            if (this.mDebug.booleanValue()) {
              Log.d("RtbView", "mDspName:" + this.mDspName + " mDspUid:" + this.mDspUid + " mHtml:" + this.mHtml);
            }
            return;
          }
          this.mDspUid = null;
          continue;
          if (!this.mDebug.booleanValue()) {
            continue;
          }
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException1) {}
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException2)
      {
        continue;
      }
      localUnsupportedEncodingException1.printStackTrace();
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
      RtbView.access$402(RtbView.this, paramString);
      if (RtbView.this.mClickAnimation)
      {
        ScaleAnimation localScaleAnimation = new ScaleAnimation(1.0F, 1.3F, 1.0F, 1.3F, 0, RtbView.this.mWidth / 2, 0, RtbView.this.mHeight / 2);
        localScaleAnimation.setDuration(1000L);
        localScaleAnimation.setAnimationListener(new Animation.AnimationListener()
        {
          public void onAnimationEnd(Animation paramAnonymousAnimation)
          {
            RtbView.this.openUrlwithBrowser(RtbView.this.mUrl);
          }
          
          public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
          
          public void onAnimationStart(Animation paramAnonymousAnimation) {}
        });
        RtbView.this.startAnimation(localScaleAnimation);
      }
      for (;;)
      {
        return true;
        RtbView.this.openUrlwithBrowser(RtbView.this.mUrl);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.RtbView
 * JD-Core Version:    0.7.0.1
 */