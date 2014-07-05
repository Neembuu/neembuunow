package jp.tjkapp.adfurikunsdk;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.graphics.drawable.StateListDrawable;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

class AdfurikunIntersView
  extends RelativeLayout
{
  private static final int AD_BUTTON_BOTTOM = -15163902;
  private static final int AD_BUTTON_LINE = -15363584;
  private static final int AD_BUTTON_TEXT = -1;
  private static final int AD_BUTTON_TOP = -11995872;
  private static final int BUTTON_BOTTOM = -4275258;
  private static final int BUTTON_FONT_SIZE_LAND = 10;
  private static final int BUTTON_FONT_SIZE_PORT = 12;
  private static final int BUTTON_LINE = -4275258;
  private static final int BUTTON_TEXT = -16777216;
  private static final int BUTTON_TOP = -1249294;
  private static final String CANCEL_BUTTON_TEXT = "Cancel";
  private static final int DIALOG_COLOR = -16777216;
  private static final int DIALOG_FRAME_COLOR = -10724260;
  private static final int ID_AD_CONTAINER = 1;
  private static final int ID_LEFT_BUTTON = 3;
  private static final int ID_RIGHT_BUTTON = 4;
  private static final int ID_TOP_BUTTON = 2;
  private static final String INTERS_AD_BUTTON_TEXT = "Download";
  private static final int SHADOW_COLOR = -1728053248;
  private Button mAdButton;
  private Animation mButtonAnimation;
  private Button mCancelButton;
  private String mCancelButtonName = "";
  private ImageView mClickView;
  private Button mCustomButton;
  private String mCustomButtonName = "";
  private String mIntersadButtonName = "";
  private boolean mIsAnimation = false;
  private OnAdfurikunIntersClickListener mOnAdfurikunIntersClickListener = null;
  private RelativeLayout.LayoutParams mTopParams;
  
  public AdfurikunIntersView(Context paramContext, AdfurikunIntersAdLayout paramAdfurikunIntersAdLayout, String paramString1, String paramString2, String paramString3)
  {
    super(paramContext);
    initialize(paramContext, paramAdfurikunIntersAdLayout, paramString1, paramString2, paramString3);
  }
  
  private Animation createAnimation()
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(0.0F, 5.0F, 0.0F, 0.0F);
    localTranslateAnimation.setDuration(1000L);
    localTranslateAnimation.setInterpolator(new CycleInterpolator(4.0F));
    localTranslateAnimation.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        AdfurikunIntersView.this.mIsAnimation = false;
      }
      
      public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
      
      public void onAnimationStart(Animation paramAnonymousAnimation) {}
    });
    return localTranslateAnimation;
  }
  
  private GradientDrawable createGradient(int paramInt1, int paramInt2, int paramInt3, float paramFloat, int paramInt4)
  {
    GradientDrawable.Orientation localOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = paramInt1;
    arrayOfInt[1] = paramInt2;
    GradientDrawable localGradientDrawable = new GradientDrawable(localOrientation, arrayOfInt);
    localGradientDrawable.setStroke(paramInt4, paramInt3);
    localGradientDrawable.setCornerRadius(paramFloat);
    return localGradientDrawable;
  }
  
  private String getLandText(String paramString)
  {
    StringBuffer localStringBuffer;
    int j;
    if (paramString != null)
    {
      localStringBuffer = new StringBuffer();
      int i = paramString.length();
      j = 0;
      if (j < i) {}
    }
    for (String str = localStringBuffer.toString();; str = "")
    {
      return str;
      if (j != 0) {
        localStringBuffer.append("\n");
      }
      localStringBuffer.append(paramString.substring(j, j + 1));
      j++;
      break;
    }
  }
  
  private StateListDrawable getThemeButtonDrawable(int paramInt1, int paramInt2, int paramInt3, float paramFloat, int paramInt4)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    int[] arrayOfInt1 = new int[1];
    arrayOfInt1[0] = 16842919;
    localStateListDrawable.addState(arrayOfInt1, createGradient(paramInt2, paramInt1, paramInt3, paramFloat, paramInt4));
    int[] arrayOfInt2 = new int[1];
    arrayOfInt2[0] = -16842919;
    localStateListDrawable.addState(arrayOfInt2, createGradient(paramInt1, paramInt2, paramInt3, paramFloat, paramInt4));
    return localStateListDrawable;
  }
  
  private void initialize(Context paramContext, final AdfurikunIntersAdLayout paramAdfurikunIntersAdLayout, String paramString1, String paramString2, String paramString3)
  {
    this.mIntersadButtonName = paramString1;
    this.mCancelButtonName = paramString2;
    this.mCustomButtonName = paramString3;
    this.mIsAnimation = false;
    this.mButtonAnimation = createAnimation();
    float f = getResources().getDisplayMetrics().density;
    int i = (int)(0.5F + 4.0F * f);
    int j = (int)(0.5F + 2.0F * f);
    int k = (int)(0.5F + 300.0F * f);
    int m = (int)(0.5F + 250.0F * f);
    final int n = (int)(0.5F + 25.0F * f);
    setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
    setBackgroundColor(-1728053248);
    setClickable(true);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams.addRule(13, -1);
    RelativeLayout localRelativeLayout = new RelativeLayout(paramContext);
    int i1 = j * 2;
    localRelativeLayout.setPadding(i1, i1, i1, i1);
    localRelativeLayout.setLayoutParams(localLayoutParams);
    localRelativeLayout.setBackgroundDrawable(createGradient(-16777216, -16777216, -10724260, 0.0F, j));
    addView(localRelativeLayout, localLayoutParams);
    FrameLayout.LayoutParams localLayoutParams1 = new FrameLayout.LayoutParams(k, m);
    FrameLayout localFrameLayout = new FrameLayout(paramContext);
    localFrameLayout.setId(1);
    localRelativeLayout.addView(localFrameLayout);
    boolean bool = false;
    String str1 = "";
    if (paramAdfurikunIntersAdLayout != null)
    {
      bool = paramAdfurikunIntersAdLayout.isTapChkOff();
      localFrameLayout.addView(paramAdfurikunIntersAdLayout, localLayoutParams1);
      str1 = paramAdfurikunIntersAdLayout.getIsText();
    }
    String str2;
    if (str1.length() <= 0)
    {
      if (this.mIntersadButtonName.length() > 0) {
        str1 = this.mIntersadButtonName;
      }
    }
    else
    {
      if (this.mCancelButtonName.length() <= 0) {
        break label671;
      }
      str2 = this.mCancelButtonName;
      label313:
      this.mClickView = new ImageView(paramContext);
      this.mClickView.setBackgroundColor(0);
      this.mClickView.setClickable(true);
      localFrameLayout.addView(this.mClickView, localLayoutParams1);
      if (getResources().getConfiguration().orientation != 2) {
        break label678;
      }
      setLandLayout(paramContext, localRelativeLayout, f, k, m, j);
      this.mAdButton.setTextSize(10.0F);
      this.mAdButton.setText(getLandText(str1));
      this.mCancelButton.setTextSize(10.0F);
      this.mCancelButton.setText(getLandText(str2));
      if (this.mCustomButton != null)
      {
        this.mCustomButton.setTextSize(10.0F);
        this.mCustomButton.setText(getLandText(this.mCustomButtonName));
      }
    }
    for (;;)
    {
      if (bool)
      {
        this.mClickView.setVisibility(8);
        this.mAdButton.setVisibility(8);
        if (this.mCustomButton == null) {
          this.mCancelButton.setLayoutParams(this.mTopParams);
        }
      }
      this.mAdButton.setTextColor(-1);
      this.mCancelButton.setTextColor(-16777216);
      this.mAdButton.setBackgroundDrawable(getThemeButtonDrawable(-11995872, -15163902, -15363584, i, j));
      this.mCancelButton.setBackgroundDrawable(getThemeButtonDrawable(-1249294, -4275258, -4275258, i, j));
      if (this.mCustomButton != null)
      {
        this.mCustomButton.setTextColor(-16777216);
        this.mCustomButton.setBackgroundDrawable(getThemeButtonDrawable(-1249294, -4275258, -4275258, i, j));
        this.mCustomButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (AdfurikunIntersView.this.mOnAdfurikunIntersClickListener != null) {
              AdfurikunIntersView.this.mOnAdfurikunIntersClickListener.onClickCustom();
            }
          }
        });
      }
      this.mClickView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (!AdfurikunIntersView.this.mIsAnimation)
          {
            AdfurikunIntersView.this.mIsAnimation = true;
            AdfurikunIntersView.this.mAdButton.startAnimation(AdfurikunIntersView.this.mButtonAnimation);
          }
        }
      });
      this.mAdButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (paramAdfurikunIntersAdLayout != null)
          {
            float f = paramAdfurikunIntersAdLayout.getWidth() / 2;
            long l = SystemClock.uptimeMillis();
            MotionEvent localMotionEvent1 = MotionEvent.obtain(l, 0L, 0, f, n, 0);
            paramAdfurikunIntersAdLayout.dispatchTouchEvent(localMotionEvent1);
            MotionEvent localMotionEvent2 = MotionEvent.obtain(l, 0L, 1, f + 1.0F, 1 + n, 0);
            paramAdfurikunIntersAdLayout.dispatchTouchEvent(localMotionEvent2);
          }
        }
      });
      this.mCancelButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (AdfurikunIntersView.this.mOnAdfurikunIntersClickListener != null) {
            AdfurikunIntersView.this.mOnAdfurikunIntersClickListener.onClickCancel();
          }
        }
      });
      return;
      str1 = "Download";
      break;
      label671:
      str2 = "Cancel";
      break label313;
      label678:
      setPortLayout(paramContext, localRelativeLayout, f, k, m, j);
      this.mAdButton.setTextSize(12.0F);
      this.mAdButton.setText(str1);
      this.mCancelButton.setTextSize(12.0F);
      this.mCancelButton.setText(str2);
      if (this.mCustomButton != null)
      {
        this.mCustomButton.setTextSize(12.0F);
        this.mCustomButton.setText(this.mCustomButtonName);
      }
    }
  }
  
  private void setLandLayout(Context paramContext, RelativeLayout paramRelativeLayout, float paramFloat, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = (int)(0.5F + 40.0F * paramFloat);
    int j = (int)(0.5F + 117.0F * paramFloat);
    int k = (int)(0.5F + 4.0F * paramFloat);
    int m = (int)(0.5F + 10.0F * paramFloat);
    int n = (int)(0.5F + 8.0F * paramFloat);
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(i, j);
    localLayoutParams1.setMargins(k, k, 0, k / 2);
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(i, -2);
    localLayoutParams2.setMargins(k, k / 2, 0, k);
    this.mTopParams = new RelativeLayout.LayoutParams(i, -2);
    this.mTopParams.setMargins(k, k, 0, k);
    this.mTopParams.addRule(6, 1);
    this.mTopParams.addRule(8, 1);
    this.mTopParams.addRule(1, 1);
    Button localButton1 = new Button(paramContext);
    localButton1.setPadding(m, n, m, n);
    localButton1.setId(3);
    Button localButton2 = new Button(paramContext);
    localButton2.setPadding(m, n, m, n);
    localButton2.setId(4);
    if (this.mCustomButtonName.length() > 0)
    {
      Button localButton3 = new Button(paramContext);
      localButton3.setPadding(m, n, m, n);
      localButton3.setId(2);
      localLayoutParams1.addRule(1, 2);
      localLayoutParams2.addRule(1, 2);
      this.mAdButton = localButton3;
      this.mCancelButton = localButton2;
      this.mCustomButton = localButton1;
      paramRelativeLayout.addView(localButton3, this.mTopParams);
    }
    for (;;)
    {
      localLayoutParams2.addRule(3, 3);
      localLayoutParams2.addRule(8, 1);
      paramRelativeLayout.addView(localButton1, localLayoutParams1);
      paramRelativeLayout.addView(localButton2, localLayoutParams2);
      RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(k, k);
      localLayoutParams3.addRule(1, 4);
      paramRelativeLayout.addView(new FrameLayout(paramContext), localLayoutParams3);
      return;
      localLayoutParams1.addRule(1, 1);
      localLayoutParams2.addRule(1, 1);
      this.mAdButton = localButton1;
      this.mCancelButton = localButton2;
      this.mCustomButton = null;
    }
  }
  
  private void setPortLayout(Context paramContext, RelativeLayout paramRelativeLayout, float paramFloat, int paramInt1, int paramInt2, int paramInt3)
  {
    int i = (int)(0.5F + 142.0F * paramFloat);
    int j = (int)(0.5F + 4.0F * paramFloat);
    int k = (int)(0.5F + 8.0F * paramFloat);
    int m = (int)(0.5F + 10.0F * paramFloat);
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(i, -2);
    localLayoutParams1.setMargins(j, j, j / 2, 0);
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams2.setMargins(j / 2, j, j, 0);
    this.mTopParams = new RelativeLayout.LayoutParams(-2, -2);
    this.mTopParams.setMargins(j, j, j, 0);
    this.mTopParams.addRule(5, 1);
    this.mTopParams.addRule(7, 1);
    this.mTopParams.addRule(3, 1);
    Button localButton1 = new Button(paramContext);
    localButton1.setPadding(k, m, k, m);
    localButton1.setId(3);
    Button localButton2 = new Button(paramContext);
    localButton2.setPadding(k, m, k, m);
    localButton2.setId(4);
    if (this.mCustomButtonName.length() > 0)
    {
      Button localButton3 = new Button(paramContext);
      localButton3.setPadding(k, m, k, m);
      localButton3.setId(2);
      localLayoutParams1.addRule(3, 2);
      localLayoutParams2.addRule(3, 2);
      this.mAdButton = localButton3;
      this.mCancelButton = localButton2;
      this.mCustomButton = localButton1;
      paramRelativeLayout.addView(localButton3, this.mTopParams);
    }
    for (;;)
    {
      localLayoutParams2.addRule(1, 3);
      localLayoutParams2.addRule(7, 1);
      paramRelativeLayout.addView(localButton1, localLayoutParams1);
      paramRelativeLayout.addView(localButton2, localLayoutParams2);
      RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(j, j);
      localLayoutParams3.addRule(3, 4);
      paramRelativeLayout.addView(new FrameLayout(paramContext), localLayoutParams3);
      return;
      localLayoutParams1.addRule(3, 1);
      localLayoutParams2.addRule(3, 1);
      this.mAdButton = localButton1;
      this.mCancelButton = localButton2;
      this.mCustomButton = null;
    }
  }
  
  public void setOnAdfurikunIntersClickListener(OnAdfurikunIntersClickListener paramOnAdfurikunIntersClickListener)
  {
    this.mOnAdfurikunIntersClickListener = paramOnAdfurikunIntersClickListener;
  }
  
  public static abstract interface OnAdfurikunIntersClickListener
  {
    public abstract void onClickCancel();
    
    public abstract void onClickCustom();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunIntersView
 * JD-Core Version:    0.7.0.1
 */