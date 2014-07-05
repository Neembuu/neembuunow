package jp.co.asbit.pvstar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;

public class SortableListView
  extends ListView
  implements AdapterView.OnItemLongClickListener
{
  private static final Bitmap.Config DRAG_BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
  private static final int SCROLL_SPEED_FAST = 25;
  private static final int SCROLL_SPEED_SLOW = 8;
  private MotionEvent mActionDownEvent;
  private int mBitmapBackgroundColor = Color.argb(128, 255, 255, 255);
  private Bitmap mDragBitmap = null;
  private ImageView mDragImageView = null;
  private DragListener mDragListener = new SimpleDragListener();
  private boolean mDragging = false;
  private WindowManager.LayoutParams mLayoutParams = null;
  private int mPositionFrom = -1;
  private boolean mSortable = false;
  private int mStoreY = 0;
  
  public SortableListView(Context paramContext)
  {
    super(paramContext);
    setOnItemLongClickListener(this);
  }
  
  public SortableListView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setOnItemLongClickListener(this);
  }
  
  public SortableListView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setOnItemLongClickListener(this);
  }
  
  private boolean duringDrag(MotionEvent paramMotionEvent)
  {
    boolean bool = false;
    if ((!this.mDragging) || (this.mDragImageView == null)) {
      return bool;
    }
    int i = (int)paramMotionEvent.getX();
    int j;
    label42:
    int k;
    int n;
    int i1;
    int i2;
    if (this.mStoreY != 0)
    {
      j = this.mStoreY;
      this.mStoreY = 0;
      k = getHeight();
      int m = k / 2;
      n = k / 9;
      i1 = k / 4;
      if (paramMotionEvent.getEventTime() - paramMotionEvent.getDownTime() >= 500L) {
        break label239;
      }
      i2 = 0;
      label86:
      if (i2 != 0)
      {
        int i3 = pointToPosition(0, m);
        if (i3 == -1) {
          i3 = pointToPosition(0, 64 + (m + getDividerHeight()));
        }
        View localView = getChildByIndex(i3);
        if (localView != null) {
          setSelectionFromTop(i3, localView.getTop() - i2);
        }
      }
      if (this.mDragImageView.getHeight() >= 0) {
        break label307;
      }
      this.mDragImageView.setVisibility(4);
    }
    for (;;)
    {
      updateLayoutParams(i, j);
      getWindowManager().updateViewLayout(this.mDragImageView, this.mLayoutParams);
      if (this.mDragListener != null) {
        this.mPositionFrom = this.mDragListener.onDuringDrag(this.mPositionFrom, pointToPosition(i, j));
      }
      bool = true;
      break;
      j = (int)paramMotionEvent.getY();
      break label42;
      label239:
      if (j < i1)
      {
        if (j < n) {}
        for (i2 = -25;; i2 = -8) {
          break;
        }
      }
      if (j > k - i1)
      {
        if (j > k - n) {}
        for (i2 = 25;; i2 = 8) {
          break;
        }
      }
      i2 = 0;
      break label86;
      label307:
      this.mDragImageView.setVisibility(0);
    }
  }
  
  private int eventToPosition(MotionEvent paramMotionEvent)
  {
    return pointToPosition((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
  }
  
  private View getChildByIndex(int paramInt)
  {
    return getChildAt(paramInt - getFirstVisiblePosition());
  }
  
  private boolean startDrag(int paramInt)
  {
    this.mPositionFrom = paramInt;
    if (this.mPositionFrom < 0) {}
    for (boolean bool = false;; bool = duringDrag(this.mActionDownEvent))
    {
      return bool;
      this.mDragging = true;
      View localView = getChildByIndex(this.mPositionFrom);
      Canvas localCanvas = new Canvas();
      WindowManager localWindowManager = getWindowManager();
      this.mDragBitmap = Bitmap.createBitmap(localView.getWidth(), localView.getHeight(), DRAG_BITMAP_CONFIG);
      localCanvas.setBitmap(this.mDragBitmap);
      localView.draw(localCanvas);
      if (this.mDragImageView != null) {
        localWindowManager.removeView(this.mDragImageView);
      }
      if (this.mLayoutParams == null) {
        initLayoutParams();
      }
      this.mDragImageView = new ImageView(getContext());
      this.mDragImageView.setBackgroundColor(this.mBitmapBackgroundColor);
      this.mDragImageView.setImageBitmap(this.mDragBitmap);
      localWindowManager.addView(this.mDragImageView, this.mLayoutParams);
      if (this.mDragListener != null) {
        this.mPositionFrom = this.mDragListener.onStartDrag(this.mPositionFrom);
      }
    }
  }
  
  private boolean stopDrag(MotionEvent paramMotionEvent, boolean paramBoolean)
  {
    boolean bool = false;
    if (!this.mDragging) {}
    for (;;)
    {
      return bool;
      if ((paramBoolean) && (this.mDragListener != null)) {
        this.mDragListener.onStopDrag(this.mPositionFrom, eventToPosition(paramMotionEvent));
      }
      this.mDragging = false;
      if (this.mDragImageView != null)
      {
        getWindowManager().removeView(this.mDragImageView);
        this.mDragImageView = null;
        this.mDragBitmap = null;
        bool = true;
      }
    }
  }
  
  private void storeMotionEvent(MotionEvent paramMotionEvent)
  {
    this.mActionDownEvent = paramMotionEvent;
    this.mStoreY = ((int)paramMotionEvent.getY());
  }
  
  public boolean getSortable()
  {
    return this.mSortable;
  }
  
  protected WindowManager getWindowManager()
  {
    return (WindowManager)getContext().getSystemService("window");
  }
  
  protected void initLayoutParams()
  {
    this.mLayoutParams = new WindowManager.LayoutParams();
    this.mLayoutParams.gravity = 51;
    this.mLayoutParams.height = -2;
    this.mLayoutParams.width = -2;
    this.mLayoutParams.flags = 664;
    this.mLayoutParams.format = -3;
    this.mLayoutParams.windowAnimations = 0;
    this.mLayoutParams.x = getLeft();
    this.mLayoutParams.y = getTop();
  }
  
  public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    return startDrag(paramInt);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = true;
    if (!this.mSortable) {
      bool = super.onTouchEvent(paramMotionEvent);
    }
    for (;;)
    {
      return bool;
      switch (paramMotionEvent.getAction())
      {
      }
      do
      {
        do
        {
          do
          {
            for (;;)
            {
              bool = super.onTouchEvent(paramMotionEvent);
              break;
              storeMotionEvent(paramMotionEvent);
            }
          } while (!duringDrag(paramMotionEvent));
          break;
        } while (!stopDrag(paramMotionEvent, bool));
        break;
      } while (!stopDrag(paramMotionEvent, false));
    }
  }
  
  public void setBackgroundColor(int paramInt)
  {
    this.mBitmapBackgroundColor = paramInt;
  }
  
  public void setDragListener(DragListener paramDragListener)
  {
    this.mDragListener = paramDragListener;
  }
  
  public void setSortable(boolean paramBoolean)
  {
    this.mSortable = paramBoolean;
  }
  
  protected void updateLayoutParams(int paramInt1, int paramInt2)
  {
    int i = (int)(32.0F * getResources().getDisplayMetrics().density);
    this.mLayoutParams.y = (i + (paramInt2 + getTop()));
  }
  
  public static abstract interface DragListener
  {
    public abstract int onDuringDrag(int paramInt1, int paramInt2);
    
    public abstract int onStartDrag(int paramInt);
    
    public abstract boolean onStopDrag(int paramInt1, int paramInt2);
  }
  
  public static class SimpleDragListener
    implements SortableListView.DragListener
  {
    public int onDuringDrag(int paramInt1, int paramInt2)
    {
      return paramInt1;
    }
    
    public int onStartDrag(int paramInt)
    {
      return paramInt;
    }
    
    public boolean onStopDrag(int paramInt1, int paramInt2)
    {
      if (((paramInt1 == paramInt2) || (paramInt1 < 0)) && (paramInt2 < 0)) {}
      for (boolean bool = false;; bool = true) {
        return bool;
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.SortableListView
 * JD-Core Version:    0.7.0.1
 */