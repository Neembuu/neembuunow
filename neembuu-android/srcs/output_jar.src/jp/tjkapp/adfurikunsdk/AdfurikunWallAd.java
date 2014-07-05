package jp.tjkapp.adfurikunsdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import java.util.Random;

public class AdfurikunWallAd
  extends Activity
{
  private static final String CLOSE_ICON_DARK = "adfurikun/images/close_dark.png";
  private static final String CLOSE_ICON_LIGHT = "adfurikun/images/close_light.png";
  public static final int ERROR_ALREADY_DISPLAYED = 3001;
  private static final int ID_TITLEBAR = 1;
  public static final int THEME_DARK = 1;
  private static final int THEME_DENSITY = 320;
  public static final int THEME_LIGHT = 2;
  public static final int THEME_RANDOM = 0;
  private static final int TITLEBAR_BOTTOM_DARK = -13881555;
  private static final int TITLEBAR_BOTTOM_LIGHT = -2566699;
  private static final int TITLEBAR_TOP_DARK = -12697025;
  private static final int TITLEBAR_TOP_LIGHT = -1118482;
  private static boolean mIsShowWallAd = false;
  private static OnAdfurikunWallAdFinishListener mOnAdfurikunWallAdFinishListener = null;
  private static Random mRandom;
  private static int mTheme = 0;
  private AdfurikunWallAdLayout mAdfurikunWallAdLayout;
  private int mOrientation;
  
  static GradientDrawable createGradient(int paramInt1, int paramInt2)
  {
    GradientDrawable.Orientation localOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = paramInt1;
    arrayOfInt[1] = paramInt2;
    return new GradientDrawable(localOrientation, arrayOfInt);
  }
  
  /* Error */
  private Bitmap getAssetsBitmap(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aconst_null
    //   3: astore_3
    //   4: aload_0
    //   5: invokevirtual 81	jp/tjkapp/adfurikunsdk/AdfurikunWallAd:getResources	()Landroid/content/res/Resources;
    //   8: invokevirtual 87	android/content/res/Resources:getAssets	()Landroid/content/res/AssetManager;
    //   11: astore 4
    //   13: aload 4
    //   15: aload_1
    //   16: invokevirtual 93	android/content/res/AssetManager:open	(Ljava/lang/String;)Ljava/io/InputStream;
    //   19: astore_3
    //   20: aload_3
    //   21: invokestatic 99	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
    //   24: astore_2
    //   25: aload_2
    //   26: sipush 320
    //   29: invokevirtual 105	android/graphics/Bitmap:setDensity	(I)V
    //   32: aload_3
    //   33: ifnull +7 -> 40
    //   36: aload_3
    //   37: invokevirtual 110	java/io/InputStream:close	()V
    //   40: aload_2
    //   41: areturn
    //   42: astore 11
    //   44: aload_3
    //   45: ifnull -5 -> 40
    //   48: aload_3
    //   49: invokevirtual 110	java/io/InputStream:close	()V
    //   52: goto -12 -> 40
    //   55: astore 12
    //   57: goto -17 -> 40
    //   60: astore 9
    //   62: aload_3
    //   63: ifnull -23 -> 40
    //   66: aload_3
    //   67: invokevirtual 110	java/io/InputStream:close	()V
    //   70: goto -30 -> 40
    //   73: astore 10
    //   75: goto -35 -> 40
    //   78: astore 7
    //   80: aload_3
    //   81: ifnull -41 -> 40
    //   84: aload_3
    //   85: invokevirtual 110	java/io/InputStream:close	()V
    //   88: goto -48 -> 40
    //   91: astore 8
    //   93: goto -53 -> 40
    //   96: astore 5
    //   98: aload_3
    //   99: ifnull +7 -> 106
    //   102: aload_3
    //   103: invokevirtual 110	java/io/InputStream:close	()V
    //   106: aload 5
    //   108: athrow
    //   109: astore 6
    //   111: goto -5 -> 106
    //   114: astore 13
    //   116: goto -76 -> 40
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	119	0	this	AdfurikunWallAd
    //   0	119	1	paramString	String
    //   1	40	2	localBitmap	Bitmap
    //   3	100	3	localInputStream	java.io.InputStream
    //   11	3	4	localAssetManager	android.content.res.AssetManager
    //   96	11	5	localObject	Object
    //   109	1	6	localIOException1	java.io.IOException
    //   78	1	7	localIOException2	java.io.IOException
    //   91	1	8	localIOException3	java.io.IOException
    //   60	1	9	localUnsupportedEncodingException	java.io.UnsupportedEncodingException
    //   73	1	10	localIOException4	java.io.IOException
    //   42	1	11	localFileNotFoundException	java.io.FileNotFoundException
    //   55	1	12	localIOException5	java.io.IOException
    //   114	1	13	localIOException6	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   13	32	42	java/io/FileNotFoundException
    //   48	52	55	java/io/IOException
    //   13	32	60	java/io/UnsupportedEncodingException
    //   66	70	73	java/io/IOException
    //   13	32	78	java/io/IOException
    //   84	88	91	java/io/IOException
    //   13	32	96	finally
    //   102	106	109	java/io/IOException
    //   36	40	114	java/io/IOException
  }
  
  public static void initializeWallAdSetting(Activity paramActivity, String paramString)
  {
    SharedPreferences.Editor localEditor = paramActivity.getSharedPreferences(AdfurikunConstants.PREF_FILE, 3).edit();
    localEditor.putString(AdfurikunConstants.PREFKEY_WALL_APPID, paramString);
    localEditor.commit();
  }
  
  private void setImageView(ImageView paramImageView, Bitmap paramBitmap, boolean paramBoolean)
  {
    if (paramImageView != null)
    {
      if (paramBitmap == null) {
        break label84;
      }
      paramBitmap.setDensity(320);
      if (!paramBoolean) {
        break label41;
      }
      paramImageView.setAdjustViewBounds(true);
      paramImageView.setImageDrawable(new BitmapDrawable(getResources(), paramBitmap));
    }
    for (;;)
    {
      return;
      label41:
      int i = paramBitmap.getWidth();
      int j = paramBitmap.getHeight();
      paramImageView.setMinimumWidth(i);
      paramImageView.setMinimumHeight(j);
      paramImageView.setBackgroundDrawable(new BitmapDrawable(getResources(), paramBitmap));
      continue;
      label84:
      if (paramBoolean) {
        paramImageView.setImageDrawable(null);
      } else {
        paramImageView.setBackgroundDrawable(null);
      }
    }
  }
  
  public static void setWallAdTheme(Activity paramActivity, int paramInt)
  {
    mTheme = paramInt;
  }
  
  private void showWallAd()
  {
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-1, -1);
    RelativeLayout localRelativeLayout1 = new RelativeLayout(this);
    localRelativeLayout1.setLayoutParams(localLayoutParams1);
    localRelativeLayout1.setBackgroundColor(-1);
    int i = mTheme;
    if (i == 0)
    {
      if (mRandom == null) {
        mRandom = new Random();
      }
      if (mRandom.nextInt(2) != 0) {
        break label368;
      }
    }
    label368:
    for (i = 1;; i = 2)
    {
      int j = -12697025;
      int k = -13881555;
      if (i == 2)
      {
        j = -1118482;
        k = -2566699;
      }
      RelativeLayout localRelativeLayout2 = new RelativeLayout(this);
      localRelativeLayout2.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
      localRelativeLayout2.setBackgroundDrawable(createGradient(j, k));
      int m = (int)(0.5F + 27.0F * getResources().getDisplayMetrics().density);
      RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(m, m);
      ImageView localImageView = new ImageView(this);
      String str1 = "adfurikun/images/close_dark.png";
      if (mTheme == 2) {
        str1 = "adfurikun/images/close_light.png";
      }
      Bitmap localBitmap = getAssetsBitmap(str1);
      if (localBitmap != null) {
        setImageView(localImageView, localBitmap, true);
      }
      localLayoutParams2.addRule(11, -1);
      localRelativeLayout2.addView(localImageView, localLayoutParams2);
      View.OnClickListener local1 = new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          AdfurikunWallAd.this.cancelWallAd();
        }
      };
      localImageView.setOnClickListener(local1);
      localRelativeLayout2.setId(1);
      localRelativeLayout1.addView(localRelativeLayout2);
      RelativeLayout localRelativeLayout3 = new RelativeLayout(this);
      RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(-1, 0);
      localLayoutParams3.addRule(3, 1);
      localLayoutParams3.addRule(12, -1);
      localRelativeLayout1.addView(localRelativeLayout3, localLayoutParams3);
      String str2 = getApplicationContext().getSharedPreferences(AdfurikunConstants.PREF_FILE, 3).getString(AdfurikunConstants.PREFKEY_WALL_APPID, "");
      AdfurikunWebViewWallType.OnActionListener local2 = new AdfurikunWebViewWallType.OnActionListener()
      {
        public void windowClose()
        {
          AdfurikunWallAd.this.cancelWallAd();
        }
      };
      AdfurikunWallAdLayout localAdfurikunWallAdLayout = new AdfurikunWallAdLayout(this, str2, local2);
      this.mAdfurikunWallAdLayout = localAdfurikunWallAdLayout;
      localRelativeLayout3.addView(this.mAdfurikunWallAdLayout, localLayoutParams1);
      setContentView(localRelativeLayout1);
      return;
    }
  }
  
  public static void showWallAd(Activity paramActivity, OnAdfurikunWallAdFinishListener paramOnAdfurikunWallAdFinishListener)
  {
    if (!mIsShowWallAd)
    {
      mOnAdfurikunWallAdFinishListener = paramOnAdfurikunWallAdFinishListener;
      paramActivity.startActivity(new Intent(paramActivity, AdfurikunWallAd.class));
    }
    for (;;)
    {
      return;
      if (paramOnAdfurikunWallAdFinishListener != null) {
        paramOnAdfurikunWallAdFinishListener.onAdfurikunWallAdError(3001);
      }
    }
  }
  
  public void cancelWallAd()
  {
    if (mOnAdfurikunWallAdFinishListener != null) {
      mOnAdfurikunWallAdFinishListener.onAdfurikunWallAdClose();
    }
    mIsShowWallAd = false;
    finish();
  }
  
  public void onBackPressed()
  {
    if (this.mAdfurikunWallAdLayout != null) {
      if (!this.mAdfurikunWallAdLayout.goBack()) {
        cancelWallAd();
      }
    }
    for (;;)
    {
      return;
      cancelWallAd();
    }
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if (this.mOrientation != paramConfiguration.orientation)
    {
      this.mOrientation = paramConfiguration.orientation;
      showWallAd();
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    requestWindowFeature(1);
    super.onCreate(paramBundle);
    mIsShowWallAd = true;
    this.mOrientation = getResources().getConfiguration().orientation;
    showWallAd();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    mIsShowWallAd = false;
    if (this.mAdfurikunWallAdLayout != null) {
      this.mAdfurikunWallAdLayout.destroy();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.tjkapp.adfurikunsdk.AdfurikunWallAd
 * JD-Core Version:    0.7.0.1
 */