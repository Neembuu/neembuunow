package net.nend.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Scroller;
import java.io.IOException;
import java.lang.ref.WeakReference;
import org.apache.http.HttpEntity;

@SuppressLint({"ViewConstructor"})
final class OptOutImageView
  extends ImageView
  implements View.OnClickListener, DownloadTask.Downloadable<Bitmap>
{
  private static final int MAX_RETRY_CNT = 3;
  private static final int MESSAGE_CODE = 718;
  private static Bitmap optOutImage;
  private final float mDensity;
  private final Handler mHandler;
  private final String mOptOutImageUrl;
  private final String mOptOutUrl;
  private int mRetryCnt = 0;
  private final Scroller mScroller;
  
  static
  {
    if (!OptOutImageView.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  OptOutImageView(Context paramContext, String paramString)
  {
    super(paramContext);
    this.mScroller = new Scroller(paramContext);
    this.mHandler = new OptOutHandler(Looper.getMainLooper(), this);
    this.mDensity = getContext().getResources().getDisplayMetrics().density;
    String str1 = "http://nend.net/privacy/optsdkgate";
    Object localObject1 = "http://img1.nend.net/img/common/optout/icon.png";
    for (;;)
    {
      try
      {
        ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
        if (localApplicationInfo.metaData != null)
        {
          if (localApplicationInfo.metaData.getString(NendConstants.MetaData.OPT_OUT_URL.getName()) != null) {
            str1 = localApplicationInfo.metaData.getString(NendConstants.MetaData.OPT_OUT_URL.getName());
          }
          if (localApplicationInfo.metaData.getString(NendConstants.MetaData.OPT_OUT_IMAGE_URL.getName()) != null)
          {
            String str2 = localApplicationInfo.metaData.getString(NendConstants.MetaData.OPT_OUT_IMAGE_URL.getName());
            localObject1 = str2;
          }
        }
        this.mOptOutUrl = (str1 + "?uid=" + paramString);
        this.mOptOutImageUrl = ((String)localObject1);
        setPadding(realScrollLength(18), 0, -1 * realScrollLength(78), realScrollLength(18));
        setOnClickListener(this);
        return;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        if (!$assertionsDisabled) {
          throw new AssertionError();
        }
      }
      finally
      {
        this.mOptOutUrl = (str1 + "?uid=" + paramString);
        this.mOptOutImageUrl = ((String)localObject1);
      }
      NendLog.d(NendStatus.ERR_UNEXPECTED, localNameNotFoundException);
      this.mOptOutUrl = (str1 + "?uid=" + paramString);
      this.mOptOutImageUrl = ((String)localObject1);
    }
  }
  
  private int realScrollLength(int paramInt)
  {
    return (int)(paramInt * this.mDensity);
  }
  
  private Bitmap resizeBitmap(Bitmap paramBitmap)
  {
    assert (paramBitmap != null);
    Matrix localMatrix = new Matrix();
    localMatrix.setScale(this.mDensity, this.mDensity);
    return Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), localMatrix, true);
  }
  
  public void computeScroll()
  {
    if (this.mScroller.computeScrollOffset())
    {
      setPadding(this.mScroller.getCurrX() + realScrollLength(18) * (realScrollLength(78) - this.mScroller.getCurrX()) / realScrollLength(78), 0, -1 * realScrollLength(78), realScrollLength(18));
      scrollTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
      postInvalidate();
    }
  }
  
  public String getRequestUrl()
  {
    return this.mOptOutImageUrl;
  }
  
  boolean hasDrawable()
  {
    if (getDrawable() != null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  void loadImage()
  {
    if (optOutImage == null) {
      new DownloadTask(this).execute(new Void[0]);
    }
    for (;;)
    {
      return;
      setImageBitmap(optOutImage);
    }
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
        Bitmap localBitmap1 = BitmapFactory.decodeStream(paramHttpEntity.getContent());
        if (localBitmap1 != null)
        {
          Bitmap localBitmap2 = resizeBitmap(localBitmap1);
          localObject = localBitmap2;
        }
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
  
  public void onClick(View paramView)
  {
    if (this.mScroller.getCurrX() == (int)(78.0F * this.mDensity)) {
      NendHelper.startBrowser(paramView, this.mOptOutUrl);
    }
    for (;;)
    {
      return;
      scrollLeft();
      this.mHandler.removeMessages(718);
      this.mHandler.sendEmptyMessageDelayed(718, 2000L);
    }
  }
  
  public void onDownload(Bitmap paramBitmap)
  {
    if (paramBitmap != null)
    {
      optOutImage = paramBitmap;
      setImageBitmap(paramBitmap);
    }
    for (;;)
    {
      return;
      this.mRetryCnt = (1 + this.mRetryCnt);
      if (this.mRetryCnt < 3) {
        loadImage();
      }
    }
  }
  
  void scrollLeft()
  {
    this.mScroller.forceFinished(true);
    this.mScroller.startScroll(this.mScroller.getCurrX(), this.mScroller.getCurrY(), realScrollLength(78) - this.mScroller.getCurrX(), 0, 1000);
    invalidate();
  }
  
  void scrollRight()
  {
    this.mScroller.startScroll(this.mScroller.getCurrX(), this.mScroller.getCurrY(), -1 * this.mScroller.getCurrX(), 0, 1000);
    invalidate();
  }
  
  private static final class TapMargin
  {
    private static final int BOTTOM = 18;
    private static final int LEFT = 18;
  }
  
  private static final class ScrollParams
  {
    private static final int SCROLL_LENGTH = 78;
    private static final int SCROLL_TIME_IN_SECOND = 1;
    private static final int WAIT_TIME_IN_SECOND = 1;
  }
  
  private static class OptOutHandler
    extends Handler
  {
    private WeakReference<OptOutImageView> weakReference;
    
    OptOutHandler(Looper paramLooper, OptOutImageView paramOptOutImageView)
    {
      super();
      this.weakReference = new WeakReference(paramOptOutImageView);
    }
    
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      OptOutImageView localOptOutImageView = (OptOutImageView)this.weakReference.get();
      if (localOptOutImageView != null) {
        localOptOutImageView.scrollRight();
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.OptOutImageView
 * JD-Core Version:    0.7.0.1
 */