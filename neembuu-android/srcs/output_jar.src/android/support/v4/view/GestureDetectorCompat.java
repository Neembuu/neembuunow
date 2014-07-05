package android.support.v4.view;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public class GestureDetectorCompat
{
  private final GestureDetectorCompatImpl mImpl;
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener)
  {
    this(paramContext, paramOnGestureListener, null);
  }
  
  public GestureDetectorCompat(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
  {
    if (Build.VERSION.SDK_INT >= 17) {}
    for (this.mImpl = new GestureDetectorCompatImplJellybeanMr1(paramContext, paramOnGestureListener, paramHandler);; this.mImpl = new GestureDetectorCompatImplBase(paramContext, paramOnGestureListener, paramHandler)) {
      return;
    }
  }
  
  public boolean isLongpressEnabled()
  {
    return this.mImpl.isLongpressEnabled();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return this.mImpl.onTouchEvent(paramMotionEvent);
  }
  
  public void setIsLongpressEnabled(boolean paramBoolean)
  {
    this.mImpl.setIsLongpressEnabled(paramBoolean);
  }
  
  public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener)
  {
    this.mImpl.setOnDoubleTapListener(paramOnDoubleTapListener);
  }
  
  static class GestureDetectorCompatImplJellybeanMr1
    implements GestureDetectorCompat.GestureDetectorCompatImpl
  {
    private final GestureDetector mDetector;
    
    public GestureDetectorCompatImplJellybeanMr1(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
    {
      this.mDetector = new GestureDetector(paramContext, paramOnGestureListener, paramHandler);
    }
    
    public boolean isLongpressEnabled()
    {
      return this.mDetector.isLongpressEnabled();
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      return this.mDetector.onTouchEvent(paramMotionEvent);
    }
    
    public void setIsLongpressEnabled(boolean paramBoolean)
    {
      this.mDetector.setIsLongpressEnabled(paramBoolean);
    }
    
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener)
    {
      this.mDetector.setOnDoubleTapListener(paramOnDoubleTapListener);
    }
  }
  
  static class GestureDetectorCompatImplBase
    implements GestureDetectorCompat.GestureDetectorCompatImpl
  {
    private static final int DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
    private static final int LONGPRESS_TIMEOUT = ;
    private static final int LONG_PRESS = 2;
    private static final int SHOW_PRESS = 1;
    private static final int TAP = 3;
    private static final int TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
    private boolean mAlwaysInBiggerTapRegion;
    private boolean mAlwaysInTapRegion;
    private MotionEvent mCurrentDownEvent;
    private GestureDetector.OnDoubleTapListener mDoubleTapListener;
    private int mDoubleTapSlopSquare;
    private float mDownFocusX;
    private float mDownFocusY;
    private final Handler mHandler;
    private boolean mInLongPress;
    private boolean mIsDoubleTapping;
    private boolean mIsLongpressEnabled;
    private float mLastFocusX;
    private float mLastFocusY;
    private final GestureDetector.OnGestureListener mListener;
    private int mMaximumFlingVelocity;
    private int mMinimumFlingVelocity;
    private MotionEvent mPreviousUpEvent;
    private boolean mStillDown;
    private int mTouchSlopSquare;
    private VelocityTracker mVelocityTracker;
    
    public GestureDetectorCompatImplBase(Context paramContext, GestureDetector.OnGestureListener paramOnGestureListener, Handler paramHandler)
    {
      if (paramHandler != null) {}
      for (this.mHandler = new GestureHandler(paramHandler);; this.mHandler = new GestureHandler())
      {
        this.mListener = paramOnGestureListener;
        if ((paramOnGestureListener instanceof GestureDetector.OnDoubleTapListener)) {
          setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)paramOnGestureListener);
        }
        init(paramContext);
        return;
      }
    }
    
    private void cancel()
    {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
      this.mIsDoubleTapping = false;
      this.mStillDown = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      if (this.mInLongPress) {
        this.mInLongPress = false;
      }
    }
    
    private void cancelTaps()
    {
      this.mHandler.removeMessages(1);
      this.mHandler.removeMessages(2);
      this.mHandler.removeMessages(3);
      this.mIsDoubleTapping = false;
      this.mAlwaysInTapRegion = false;
      this.mAlwaysInBiggerTapRegion = false;
      if (this.mInLongPress) {
        this.mInLongPress = false;
      }
    }
    
    private void dispatchLongPress()
    {
      this.mHandler.removeMessages(3);
      this.mInLongPress = true;
      this.mListener.onLongPress(this.mCurrentDownEvent);
    }
    
    private void init(Context paramContext)
    {
      if (paramContext == null) {
        throw new IllegalArgumentException("Context must not be null");
      }
      if (this.mListener == null) {
        throw new IllegalArgumentException("OnGestureListener must not be null");
      }
      this.mIsLongpressEnabled = true;
      ViewConfiguration localViewConfiguration = ViewConfiguration.get(paramContext);
      int i = localViewConfiguration.getScaledTouchSlop();
      int j = localViewConfiguration.getScaledDoubleTapSlop();
      this.mMinimumFlingVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
      this.mMaximumFlingVelocity = localViewConfiguration.getScaledMaximumFlingVelocity();
      this.mTouchSlopSquare = (i * i);
      this.mDoubleTapSlopSquare = (j * j);
    }
    
    private boolean isConsideredDoubleTap(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, MotionEvent paramMotionEvent3)
    {
      boolean bool = false;
      if (!this.mAlwaysInBiggerTapRegion) {}
      for (;;)
      {
        return bool;
        if (paramMotionEvent3.getEventTime() - paramMotionEvent2.getEventTime() <= DOUBLE_TAP_TIMEOUT)
        {
          int i = (int)paramMotionEvent1.getX() - (int)paramMotionEvent3.getX();
          int j = (int)paramMotionEvent1.getY() - (int)paramMotionEvent3.getY();
          if (i * i + j * j < this.mDoubleTapSlopSquare) {
            bool = true;
          }
        }
      }
    }
    
    public boolean isLongpressEnabled()
    {
      return this.mIsLongpressEnabled;
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      int i = paramMotionEvent.getAction();
      if (this.mVelocityTracker == null) {
        this.mVelocityTracker = VelocityTracker.obtain();
      }
      this.mVelocityTracker.addMovement(paramMotionEvent);
      int j;
      int k;
      label49:
      float f1;
      float f2;
      int m;
      int n;
      if ((i & 0xFF) == 6)
      {
        j = 1;
        if (j == 0) {
          break label89;
        }
        k = MotionEventCompat.getActionIndex(paramMotionEvent);
        f1 = 0.0F;
        f2 = 0.0F;
        m = MotionEventCompat.getPointerCount(paramMotionEvent);
        n = 0;
        label64:
        if (n >= m) {
          break label121;
        }
        if (k != n) {
          break label96;
        }
      }
      for (;;)
      {
        n++;
        break label64;
        j = 0;
        break;
        label89:
        k = -1;
        break label49;
        label96:
        f1 += MotionEventCompat.getX(paramMotionEvent, n);
        f2 += MotionEventCompat.getY(paramMotionEvent, n);
      }
      label121:
      int i1;
      float f3;
      float f4;
      boolean bool1;
      if (j != 0)
      {
        i1 = m - 1;
        f3 = f1 / i1;
        f4 = f2 / i1;
        bool1 = false;
        switch (i & 0xFF)
        {
        }
      }
      for (;;)
      {
        return bool1;
        i1 = m;
        break;
        this.mLastFocusX = f3;
        this.mDownFocusX = f3;
        this.mLastFocusY = f4;
        this.mDownFocusY = f4;
        cancelTaps();
        continue;
        this.mLastFocusX = f3;
        this.mDownFocusX = f3;
        this.mLastFocusY = f4;
        this.mDownFocusY = f4;
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
        int i6 = MotionEventCompat.getActionIndex(paramMotionEvent);
        int i7 = MotionEventCompat.getPointerId(paramMotionEvent, i6);
        float f9 = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, i7);
        float f10 = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, i7);
        int i8 = 0;
        if (i8 < m)
        {
          if (i8 == i6) {}
          int i9;
          do
          {
            i8++;
            break;
            i9 = MotionEventCompat.getPointerId(paramMotionEvent, i8);
          } while (f9 * VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, i9) + f10 * VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, i9) >= 0.0F);
          this.mVelocityTracker.clear();
          continue;
          if (this.mDoubleTapListener != null)
          {
            boolean bool2 = this.mHandler.hasMessages(3);
            if (bool2) {
              this.mHandler.removeMessages(3);
            }
            if ((this.mCurrentDownEvent == null) || (this.mPreviousUpEvent == null) || (!bool2) || (!isConsideredDoubleTap(this.mCurrentDownEvent, this.mPreviousUpEvent, paramMotionEvent))) {
              break label627;
            }
            this.mIsDoubleTapping = true;
            bool1 = false | this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);
          }
          for (;;)
          {
            this.mLastFocusX = f3;
            this.mDownFocusX = f3;
            this.mLastFocusY = f4;
            this.mDownFocusY = f4;
            if (this.mCurrentDownEvent != null) {
              this.mCurrentDownEvent.recycle();
            }
            this.mCurrentDownEvent = MotionEvent.obtain(paramMotionEvent);
            this.mAlwaysInTapRegion = true;
            this.mAlwaysInBiggerTapRegion = true;
            this.mStillDown = true;
            this.mInLongPress = false;
            if (this.mIsLongpressEnabled)
            {
              this.mHandler.removeMessages(2);
              this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT + LONGPRESS_TIMEOUT);
            }
            this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + TAP_TIMEOUT);
            bool1 |= this.mListener.onDown(paramMotionEvent);
            break;
            label627:
            this.mHandler.sendEmptyMessageDelayed(3, DOUBLE_TAP_TIMEOUT);
          }
          if (!this.mInLongPress)
          {
            float f7 = this.mLastFocusX - f3;
            float f8 = this.mLastFocusY - f4;
            if (this.mIsDoubleTapping)
            {
              bool1 = false | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);
            }
            else if (this.mAlwaysInTapRegion)
            {
              int i3 = (int)(f3 - this.mDownFocusX);
              int i4 = (int)(f4 - this.mDownFocusY);
              int i5 = i3 * i3 + i4 * i4;
              if (i5 > this.mTouchSlopSquare)
              {
                bool1 = this.mListener.onScroll(this.mCurrentDownEvent, paramMotionEvent, f7, f8);
                this.mLastFocusX = f3;
                this.mLastFocusY = f4;
                this.mAlwaysInTapRegion = false;
                this.mHandler.removeMessages(3);
                this.mHandler.removeMessages(1);
                this.mHandler.removeMessages(2);
              }
              if (i5 > this.mTouchSlopSquare) {
                this.mAlwaysInBiggerTapRegion = false;
              }
            }
            else if ((Math.abs(f7) >= 1.0F) || (Math.abs(f8) >= 1.0F))
            {
              bool1 = this.mListener.onScroll(this.mCurrentDownEvent, paramMotionEvent, f7, f8);
              this.mLastFocusX = f3;
              this.mLastFocusY = f4;
              continue;
              this.mStillDown = false;
              MotionEvent localMotionEvent = MotionEvent.obtain(paramMotionEvent);
              if (this.mIsDoubleTapping) {
                bool1 = false | this.mDoubleTapListener.onDoubleTapEvent(paramMotionEvent);
              }
              for (;;)
              {
                if (this.mPreviousUpEvent != null) {
                  this.mPreviousUpEvent.recycle();
                }
                this.mPreviousUpEvent = localMotionEvent;
                if (this.mVelocityTracker != null)
                {
                  this.mVelocityTracker.recycle();
                  this.mVelocityTracker = null;
                }
                this.mIsDoubleTapping = false;
                this.mHandler.removeMessages(1);
                this.mHandler.removeMessages(2);
                break;
                if (this.mInLongPress)
                {
                  this.mHandler.removeMessages(3);
                  this.mInLongPress = false;
                }
                else if (this.mAlwaysInTapRegion)
                {
                  bool1 = this.mListener.onSingleTapUp(paramMotionEvent);
                }
                else
                {
                  VelocityTracker localVelocityTracker = this.mVelocityTracker;
                  int i2 = MotionEventCompat.getPointerId(paramMotionEvent, 0);
                  localVelocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                  float f5 = VelocityTrackerCompat.getYVelocity(localVelocityTracker, i2);
                  float f6 = VelocityTrackerCompat.getXVelocity(localVelocityTracker, i2);
                  if ((Math.abs(f5) > this.mMinimumFlingVelocity) || (Math.abs(f6) > this.mMinimumFlingVelocity)) {
                    bool1 = this.mListener.onFling(this.mCurrentDownEvent, paramMotionEvent, f6, f5);
                  }
                }
              }
              cancel();
            }
          }
        }
      }
    }
    
    public void setIsLongpressEnabled(boolean paramBoolean)
    {
      this.mIsLongpressEnabled = paramBoolean;
    }
    
    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener)
    {
      this.mDoubleTapListener = paramOnDoubleTapListener;
    }
    
    private class GestureHandler
      extends Handler
    {
      GestureHandler() {}
      
      GestureHandler(Handler paramHandler)
      {
        super();
      }
      
      public void handleMessage(Message paramMessage)
      {
        switch (paramMessage.what)
        {
        default: 
          throw new RuntimeException("Unknown message " + paramMessage);
        case 1: 
          GestureDetectorCompat.GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
        }
        for (;;)
        {
          return;
          GestureDetectorCompat.GestureDetectorCompatImplBase.this.dispatchLongPress();
          continue;
          if ((GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener != null) && (!GestureDetectorCompat.GestureDetectorCompatImplBase.this.mStillDown)) {
            GestureDetectorCompat.GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompat.GestureDetectorCompatImplBase.this.mCurrentDownEvent);
          }
        }
      }
    }
  }
  
  static abstract interface GestureDetectorCompatImpl
  {
    public abstract boolean isLongpressEnabled();
    
    public abstract boolean onTouchEvent(MotionEvent paramMotionEvent);
    
    public abstract void setIsLongpressEnabled(boolean paramBoolean);
    
    public abstract void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener paramOnDoubleTapListener);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     android.support.v4.view.GestureDetectorCompat
 * JD-Core Version:    0.7.0.1
 */