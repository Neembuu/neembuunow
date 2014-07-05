package jp.co.asbit.pvstar.search;

import android.content.Context;
import android.content.res.Resources;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SearchTabs
{
  OnTabSelectedListener callback;
  public ImageView currentTab;
  private Context mContext;
  private int tabi = 0;
  private ImageView[] tabs;
  
  public SearchTabs(Context paramContext, String[] paramArrayOfString)
  {
    this.mContext = paramContext;
    this.tabs = new ImageView[paramArrayOfString.length];
  }
  
  public void changeTabState(View paramView, boolean paramBoolean)
  {
    if (paramBoolean) {
      paramView.setBackgroundResource(2130837591);
    }
    for (;;)
    {
      paramView.setPadding(20, 10, 20, 10);
      return;
      paramView.setBackgroundResource(2130837589);
    }
  }
  
  public ImageView createDivider()
  {
    ImageView localImageView = new ImageView(this.mContext);
    localImageView.setImageResource(2130837509);
    localImageView.setScaleType(ImageView.ScaleType.FIT_XY);
    localImageView.setLayoutParams(new ViewGroup.LayoutParams(1, -1));
    return localImageView;
  }
  
  public ImageView createTab(String paramString)
  {
    ImageView localImageView = new ImageView(this.mContext);
    localImageView.setImageResource(this.mContext.getResources().getIdentifier("ic_" + paramString, "drawable", this.mContext.getPackageName()));
    localImageView.setBackgroundResource(2130837589);
    localImageView.setClickable(true);
    localImageView.setPadding(20, 10, 20, 10);
    localImageView.setTag(paramString);
    localImageView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        if (paramAnonymousMotionEvent.getAction() == 0) {
          SearchTabs.this.changeTabState(paramAnonymousView, true);
        }
        for (;;)
        {
          return true;
          if (paramAnonymousMotionEvent.getAction() == 1) {
            SearchTabs.this.selectTab((ImageView)paramAnonymousView);
          } else if (((paramAnonymousMotionEvent.getAction() == 3) || (paramAnonymousMotionEvent.getAction() == 4)) && (SearchTabs.this.currentTab != paramAnonymousView)) {
            SearchTabs.this.changeTabState(paramAnonymousView, false);
          }
        }
      }
    });
    ImageView[] arrayOfImageView = this.tabs;
    int i = this.tabi;
    this.tabi = (i + 1);
    arrayOfImageView[i] = localImageView;
    return localImageView;
  }
  
  public ImageView getCurrentTab()
  {
    return this.currentTab;
  }
  
  public ImageView getTab(String paramString)
  {
    for (int i = 0;; i++)
    {
      if (i >= this.tabs.length) {}
      for (ImageView localImageView = null;; localImageView = this.tabs[i])
      {
        return localImageView;
        if (!this.tabs[i].getTag().equals(paramString)) {
          break;
        }
      }
    }
  }
  
  public void selectTab(ImageView paramImageView)
  {
    if (!paramImageView.equals(this.currentTab))
    {
      if (this.currentTab != null) {
        changeTabState(this.currentTab, false);
      }
      changeTabState(paramImageView, true);
      ImageView localImageView = this.currentTab;
      this.currentTab = paramImageView;
      this.callback.onTabSelected(paramImageView, localImageView);
    }
  }
  
  public void selectTab(String paramString)
  {
    selectTab(getTab(paramString));
  }
  
  public SearchTabs setOnTabSelectedListener(OnTabSelectedListener paramOnTabSelectedListener)
  {
    this.callback = paramOnTabSelectedListener;
    return this;
  }
  
  public static abstract interface OnTabSelectedListener
  {
    public abstract void onTabSelected(View paramView1, View paramView2);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.search.SearchTabs
 * JD-Core Version:    0.7.0.1
 */