package jp.co.cayto.appc.sdk.android;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import jp.co.cayto.appc.sdk.android.view.CutinViewBasic;
import jp.co.cayto.appc.sdk.android.view.CutinViewCube;

public final class AppCCutinView
{
  private Activity mActivity;
  private String mCutinType;
  private Boolean mDispFlag = Boolean.valueOf(false);
  private View mMainLayout;
  private ViewGroup mParentView;
  
  public AppCCutinView(Activity paramActivity)
  {
    this.mActivity = paramActivity;
    this.mParentView = ((ViewGroup)this.mActivity.findViewById(16908290));
  }
  
  private static final void cleanupView(View paramView)
  {
    ViewGroup localViewGroup;
    int i;
    if ((paramView instanceof ImageButton))
    {
      ((ImageButton)paramView).setImageDrawable(null);
      paramView.setBackgroundDrawable(null);
      if ((paramView instanceof ViewGroup))
      {
        localViewGroup = (ViewGroup)paramView;
        i = localViewGroup.getChildCount();
      }
    }
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        return;
        if ((paramView instanceof ImageView))
        {
          ((ImageView)paramView).setImageDrawable(null);
          break;
        }
        if (!(paramView instanceof SeekBar)) {
          break;
        }
        SeekBar localSeekBar = (SeekBar)paramView;
        localSeekBar.setProgressDrawable(null);
        localSeekBar.setThumb(null);
        break;
      }
      cleanupView(localViewGroup.getChildAt(j));
    }
  }
  
  private void createCutInView(String paramString1, String paramString2)
  {
    this.mCutinType = paramString2;
    this.mDispFlag = Boolean.valueOf(true);
    ImageButton localImageButton1 = new ImageButton(this.mActivity);
    ImageButton localImageButton2 = new ImageButton(this.mActivity);
    ImageButton localImageButton3;
    Object localObject;
    if ("finish".equals(paramString2))
    {
      localImageButton3 = new ImageButton(this.mActivity);
      if (!"basic".equals(paramString1)) {
        break label173;
      }
      localObject = new CutinViewBasic();
    }
    for (;;)
    {
      this.mMainLayout = ((ICutInView)localObject).createLayout(this.mActivity, paramString2, localImageButton1, localImageButton2, localImageButton3);
      localImageButton1.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          this.val$cutinView.cancelButtonClick(paramAnonymousView);
          AppCCutinView.this.removeLayout();
        }
      });
      localImageButton2.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          this.val$cutinView.otherButtonClick(paramAnonymousView);
          Intent localIntent = new Intent(AppCCutinView.this.mActivity, AppCWebActivity.class);
          localIntent.putExtra("type", "pr_list");
          localIntent.putExtra("pr_type", "back_btn_web");
          AppCCutinView.this.mActivity.startActivity(localIntent);
        }
      });
      if (localImageButton3 != null) {
        localImageButton3.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            this.val$cutinView.exitButtonClick(paramAnonymousView);
            AppCCutinView.this.mDispFlag = Boolean.valueOf(false);
            AppCCutinView.this.mActivity.finish();
          }
        });
      }
      this.mParentView.addView(this.mMainLayout);
      this.mMainLayout.bringToFront();
      return;
      localImageButton3 = null;
      break;
      label173:
      if ("icons".equals(paramString1)) {
        localObject = new CutinViewCube();
      } else {
        localObject = new CutinViewBasic();
      }
    }
  }
  
  private void removeLayout()
  {
    if ((this.mParentView != null) && (this.mMainLayout != null))
    {
      cleanupView(this.mMainLayout);
      this.mParentView.removeView(this.mMainLayout);
      this.mMainLayout = null;
    }
    this.mCutinType = null;
    this.mDispFlag = Boolean.valueOf(false);
  }
  
  public void init()
  {
    if (this.mDispFlag.booleanValue()) {
      removeLayout();
    }
  }
  
  public void show(String paramString1, String paramString2)
  {
    if (this.mDispFlag.booleanValue()) {
      if (this.mCutinType.equals("anywhere")) {
        removeLayout();
      }
    }
    for (;;)
    {
      return;
      createCutInView(paramString1, paramString2);
    }
  }
  
  public static abstract interface ICutInView
  {
    public static final int _MP = -1;
    public static final int _WC = -2;
    
    public abstract void cancelButtonClick(View paramView);
    
    public abstract View createLayout(Activity paramActivity, String paramString, ImageButton paramImageButton1, ImageButton paramImageButton2, ImageButton paramImageButton3);
    
    public abstract void exitButtonClick(View paramView);
    
    public abstract void installButtonClick(View paramView);
    
    public abstract void otherButtonClick(View paramView);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.AppCCutinView
 * JD-Core Version:    0.7.0.1
 */