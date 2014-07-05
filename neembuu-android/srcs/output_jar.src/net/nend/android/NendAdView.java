package net.nend.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import java.io.IOException;
import org.apache.http.HttpEntity;

public final class NendAdView
  extends RelativeLayout
  implements AdListener, DownloadTask.Downloadable<Bitmap>, NendAdImageView.OnAdImageClickListener
{
  private Ad mAd = null;
  private String mApiKey;
  private NendAdController mController = null;
  private float mDensity = 1.0F;
  private boolean mHasWindowFocus = false;
  private NendAdImageView mImageView = null;
  private boolean mIsClicked = false;
  private RelativeLayout mLayout = null;
  private NendAdListener mListener = null;
  private OptOutImageView mOptOutImageView = null;
  private int mSpotId;
  private DownloadTask<Bitmap> mTask = null;
  private WebView mWebView = null;
  
  static
  {
    if (!NendAdView.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public NendAdView(Context paramContext, int paramInt, String paramString)
  {
    super(paramContext, null, 0);
    init(paramContext, paramInt, paramString);
  }
  
  public NendAdView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public NendAdView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    if (paramAttributeSet == null) {
      throw new NullPointerException(NendStatus.ERR_INVALID_ATTRIBUTE_SET.getMsg());
    }
    init(paramContext, Integer.parseInt(paramAttributeSet.getAttributeValue(null, NendConstants.Attribute.SPOT_ID.getName())), paramAttributeSet.getAttributeValue(null, NendConstants.Attribute.API_KEY.getName()));
    if (!paramAttributeSet.getAttributeBooleanValue(null, NendConstants.Attribute.RELOADABLE.getName(), true)) {
      pause();
    }
    loadAd();
  }
  
  private void deallocateAd()
  {
    if (this.mAd != null)
    {
      this.mAd.removeListener();
      this.mAd = null;
    }
  }
  
  private void deallocateAdView()
  {
    if (this.mLayout != null) {
      this.mLayout = null;
    }
    if (this.mOptOutImageView != null)
    {
      this.mOptOutImageView.setImageDrawable(null);
      this.mOptOutImageView = null;
    }
    if (this.mImageView != null) {
      this.mImageView = null;
    }
  }
  
  private void deallocateChildView()
  {
    removeAllViews();
    deallocateAdView();
    deallocateWebView();
  }
  
  private void deallocateController()
  {
    if (this.mController != null)
    {
      this.mController.cancelRequest();
      this.mController = null;
    }
  }
  
  private void deallocateField()
  {
    deallocateController();
    deallocateTask();
    deallocateAd();
    removeListener();
    deallocateChildView();
  }
  
  private void deallocateTask()
  {
    if (this.mTask != null)
    {
      this.mTask.cancel(true);
      this.mTask = null;
    }
  }
  
  private void deallocateWebView()
  {
    if (this.mWebView != null)
    {
      this.mWebView.stopLoading();
      this.mWebView.getSettings().setJavaScriptEnabled(false);
      this.mWebView.destroy();
      this.mWebView = null;
    }
  }
  
  private void init(Context paramContext, int paramInt, String paramString)
  {
    if (paramInt <= 0) {
      throw new IllegalArgumentException(NendStatus.ERR_INVALID_SPOT_ID.getMsg("spot id : " + paramInt));
    }
    if ((paramString == null) || (paramString.length() == 0)) {
      throw new IllegalArgumentException(NendStatus.ERR_INVALID_API_KEY.getMsg("api key : " + paramString));
    }
    NendHelper.setDebuggable(paramContext);
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
    this.mDensity = localDisplayMetrics.density;
    this.mSpotId = paramInt;
    this.mApiKey = paramString;
    this.mAd = new NendAd(paramContext, paramInt, paramString);
    this.mAd.setListener(this);
    this.mController = new NendAdController(this.mAd);
  }
  
  private boolean isDeallocate()
  {
    if (this.mAd == null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  private void restartController()
  {
    if (this.mController == null)
    {
      if (this.mAd == null)
      {
        this.mAd = new NendAd(getContext(), this.mSpotId, this.mApiKey);
        this.mAd.setListener(this);
      }
      this.mController = new NendAdController(this.mAd);
    }
  }
  
  private void setAdView(Bitmap paramBitmap)
  {
    assert (paramBitmap != null);
    assert (this.mAd != null);
    if (this.mAd == null) {
      return;
    }
    removeAllViews();
    deallocateWebView();
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams((int)(this.mAd.getWidth() * this.mDensity), (int)(this.mAd.getHeight() * this.mDensity));
    if ((this.mLayout != null) && (this.mImageView != null) && (this.mOptOutImageView != null) && (this.mOptOutImageView.hasDrawable())) {
      this.mImageView.setAdInfo(paramBitmap, this.mAd.getClickUrl());
    }
    for (;;)
    {
      this.mOptOutImageView.bringToFront();
      addView(this.mLayout, localLayoutParams1);
      break;
      this.mLayout = new RelativeLayout(getContext());
      this.mImageView = new NendAdImageView(getContext());
      this.mImageView.setAdInfo(paramBitmap, this.mAd.getClickUrl());
      this.mImageView.setOnAdImageClickListener(this);
      this.mLayout.addView(this.mImageView, localLayoutParams1);
      this.mOptOutImageView = new OptOutImageView(getContext(), this.mAd.getUid());
      this.mOptOutImageView.loadImage();
      RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
      localLayoutParams2.addRule(11);
      this.mLayout.addView(this.mOptOutImageView, localLayoutParams2);
    }
  }
  
  private void setWebView()
  {
    assert (this.mAd != null);
    removeAllViews();
    deallocateAdView();
    this.mWebView = new NendAdWebView(getContext());
    addView(this.mWebView, new RelativeLayout.LayoutParams((int)(this.mAd.getWidth() * this.mDensity), (int)(this.mAd.getHeight() * this.mDensity)));
    this.mWebView.loadUrl(this.mAd.getWebViewUrl());
  }
  
  public String getRequestUrl()
  {
    return this.mAd.getImageUrl();
  }
  
  boolean hasView(View paramView)
  {
    if (indexOfChild(paramView) >= 0) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public void loadAd()
  {
    restartController();
    this.mController.requestAd();
  }
  
  public Bitmap makeResponse(HttpEntity paramHttpEntity)
  {
    Object localObject = null;
    if (paramHttpEntity == null) {}
    for (;;)
    {
      return localObject;
      try
      {
        Bitmap localBitmap = BitmapFactory.decodeStream(paramHttpEntity.getContent());
        localObject = localBitmap;
      }
      catch (IllegalStateException localIllegalStateException)
      {
        if (!$assertionsDisabled) {
          throw new AssertionError();
        }
        NendLog.d(NendStatus.ERR_HTTP_REQUEST, localIllegalStateException);
      }
      catch (IOException localIOException)
      {
        if (!$assertionsDisabled) {
          throw new AssertionError();
        }
        NendLog.d(NendStatus.ERR_HTTP_REQUEST, localIOException);
      }
    }
  }
  
  public void onAdImageClick(View paramView)
  {
    this.mIsClicked = true;
    if (this.mListener != null) {
      this.mListener.onClick(this);
    }
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    if (this.mAd == null)
    {
      this.mAd = new NendAd(getContext(), this.mSpotId, this.mApiKey);
      this.mAd.setListener(this);
      this.mController = new NendAdController(this.mAd);
      loadAd();
    }
  }
  
  protected void onDetachedFromWindow()
  {
    NendLog.d("onDetachedFromWindow!");
    deallocateField();
    super.onDetachedFromWindow();
  }
  
  public void onDownload(Bitmap paramBitmap)
  {
    NendLog.d("onImageDownload!");
    if ((paramBitmap != null) && (this.mController != null))
    {
      setAdView(paramBitmap);
      this.mController.reloadAd();
      if (this.mListener != null) {
        this.mListener.onReceiveAd(this);
      }
    }
    for (;;)
    {
      return;
      onFailedToReceiveAd();
    }
  }
  
  public void onFailedToReceiveAd()
  {
    NendLog.d("onFailedToReceive!");
    assert (this.mController != null);
    if ((isDeallocate()) || (this.mController == null)) {}
    for (;;)
    {
      return;
      if (!this.mController.reloadAd()) {
        NendLog.d("Failed to reload.");
      }
      if (this.mListener != null) {
        this.mListener.onFailedToReceiveAd(this);
      }
    }
  }
  
  public void onReceiveAd()
  {
    NendLog.d("onReceive!");
    assert (this.mAd != null);
    if (isDeallocate()) {}
    for (;;)
    {
      return;
      if (NendHelper.isConnected(getContext())) {
        switch (1.$SwitchMap$net$nend$android$AdParameter$ViewType[this.mAd.getViewType().ordinal()])
        {
        default: 
          if (!$assertionsDisabled) {
            throw new AssertionError();
          }
        case 1: 
          this.mTask = new DownloadTask(this);
          this.mTask.execute(new Void[0]);
          break;
        case 2: 
          if (this.mHasWindowFocus) {
            setWebView();
          }
          if (this.mListener == null) {
            continue;
          }
          this.mListener.onReceiveAd(this);
          continue;
          onFailedToReceiveAd();
          break;
        }
      } else {
        onFailedToReceiveAd();
      }
    }
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    NendLog.d("onWindowFocusChanged!" + String.valueOf(paramBoolean));
    super.onWindowFocusChanged(paramBoolean);
    this.mHasWindowFocus = paramBoolean;
    if (this.mController == null) {}
    label112:
    label117:
    for (;;)
    {
      return;
      this.mController.onWindowFocusChanged(paramBoolean);
      if (this.mAd.getViewType() == AdParameter.ViewType.WEBVIEW)
      {
        if (!paramBoolean) {
          break label112;
        }
        setWebView();
      }
      for (;;)
      {
        if ((!paramBoolean) || (!this.mIsClicked)) {
          break label117;
        }
        this.mIsClicked = false;
        if (this.mListener == null) {
          break;
        }
        this.mListener.onDismissScreen(this);
        break;
        deallocateWebView();
      }
    }
  }
  
  public void pause()
  {
    NendLog.d("pause!");
    restartController();
    this.mController.setReloadable(false);
  }
  
  public void removeListener()
  {
    this.mListener = null;
  }
  
  public void resume()
  {
    NendLog.d("resume!");
    restartController();
    this.mController.setReloadable(true);
  }
  
  public void setListener(NendAdListener paramNendAdListener)
  {
    this.mListener = paramNendAdListener;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendAdView
 * JD-Core Version:    0.7.0.1
 */