package net.nend.android;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

final class NendAdController
{
  private static final int MESSAGE_CODE = 718;
  private final Ad mAd;
  private final Handler mHandler;
  private boolean mHasWindowFocus = false;
  private boolean mReloadable = true;
  
  NendAdController(Ad paramAd)
  {
    if (paramAd == null) {
      throw new NullPointerException("Ad object is null.");
    }
    this.mAd = paramAd;
    this.mHandler = new ControllerHandler(Looper.getMainLooper(), paramAd);
  }
  
  void cancelRequest()
  {
    this.mHandler.removeMessages(718);
    this.mAd.cancelRequest();
  }
  
  void onWindowFocusChanged(boolean paramBoolean)
  {
    this.mHasWindowFocus = paramBoolean;
    if ((paramBoolean) && (this.mAd.isRequestable())) {
      reloadAd();
    }
  }
  
  boolean reloadAd()
  {
    if ((this.mReloadable) && (this.mHasWindowFocus) && (!this.mHandler.hasMessages(718))) {
      this.mHandler.sendEmptyMessageDelayed(718, 1000 * this.mAd.getReloadIntervalInSeconds());
    }
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  void requestAd()
  {
    cancelRequest();
    this.mHandler.sendEmptyMessage(718);
  }
  
  void setReloadable(boolean paramBoolean)
  {
    this.mReloadable = paramBoolean;
    if (paramBoolean) {
      reloadAd();
    }
    for (;;)
    {
      return;
      cancelRequest();
    }
  }
  
  private static class ControllerHandler
    extends Handler
  {
    private WeakReference<Ad> weakReference;
    
    ControllerHandler(Looper paramLooper, Ad paramAd)
    {
      super();
      this.weakReference = new WeakReference(paramAd);
    }
    
    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      Ad localAd = (Ad)this.weakReference.get();
      if (localAd != null) {
        localAd.requestAd();
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendAdController
 * JD-Core Version:    0.7.0.1
 */