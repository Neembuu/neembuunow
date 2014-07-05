package jp.co.cayto.appc.sdk.android.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Base64;
import android.view.animation.AlphaAnimation;
import java.io.ByteArrayOutputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import jp.co.cayto.appc.sdk.android.resources.Bitmaps;

public class AppImages
{
  private static HashMap<String, SoftReference<Bitmap>> bitmapCache = new HashMap();
  
  public static Bitmap createSpecialCircle(float paramFloat)
  {
    int i = 25 / 5;
    Bitmap localBitmap = Bitmap.createBitmap(25, 25, Bitmap.Config.ARGB_4444);
    Canvas localCanvas = new Canvas(localBitmap);
    localCanvas.rotate(paramFloat, 12, 12);
    Paint localPaint = new Paint();
    localPaint.setColor(Color.parseColor("#33FFFFFF"));
    localPaint.setAntiAlias(true);
    localPaint.setStyle(Paint.Style.FILL);
    localCanvas.drawCircle(12, 12, 10, localPaint);
    localPaint.setColor(Color.parseColor("#33FFFFFF"));
    localPaint.setAntiAlias(true);
    localPaint.setStyle(Paint.Style.STROKE);
    localCanvas.drawCircle(12, 12, 10, localPaint);
    localPaint.setColor(Color.parseColor("#FFFFFF"));
    Rect localRect1 = new Rect(i, 11, 20, 13);
    localPaint.setStyle(Paint.Style.FILL);
    localCanvas.drawRect(localRect1, localPaint);
    Rect localRect2 = new Rect(11, i, 13, 20);
    localPaint.setStyle(Paint.Style.FILL);
    localCanvas.drawRect(localRect2, localPaint);
    return localBitmap;
  }
  
  public static Bitmap createTriangle(int paramInt1, String paramString, int paramInt2)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt1, Bitmap.Config.ARGB_4444);
    Canvas localCanvas = new Canvas(localBitmap);
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    localPaint.setColor(paramInt2);
    localPaint.setStyle(Paint.Style.FILL);
    Path localPath = new Path();
    if (paramString.equals("right"))
    {
      localPath.moveTo(paramInt1 / 2, 0.0F);
      localPath.lineTo(paramInt1, paramInt1 / 2);
      localPath.lineTo(paramInt1 / 2, paramInt1);
      localPath.lineTo(paramInt1 / 2, 0.0F);
    }
    for (;;)
    {
      localCanvas.drawPath(localPath, localPaint);
      return localBitmap;
      if (paramString.equals("left"))
      {
        localPath.moveTo(paramInt1 / 2, 0.0F);
        localPath.lineTo(0.0F, paramInt1 / 2);
        localPath.lineTo(paramInt1 / 2, paramInt1);
        localPath.lineTo(paramInt1 / 2, 0.0F);
      }
    }
  }
  
  public static AlphaAnimation getAnimation(float paramFloat1, float paramFloat2, int paramInt)
  {
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(paramFloat1, paramFloat2);
    localAlphaAnimation.setDuration(paramInt);
    return localAlphaAnimation;
  }
  
  public static Bitmap getBitmap(int paramInt, boolean paramBoolean, Context paramContext)
  {
    String str = paramInt;
    Bitmap localBitmap1 = getBitmapCache(str);
    if (localBitmap1 != null) {}
    Bitmap localBitmap2;
    for (Object localObject = localBitmap1;; localObject = localBitmap2)
    {
      return localObject;
      localBitmap2 = Bitmaps.getBitmap(paramInt);
      if (paramBoolean) {
        setBitmapCache(str, localBitmap2);
      }
    }
  }
  
  private static Bitmap getBitmapCache(String paramString)
  {
    SoftReference localSoftReference = (SoftReference)bitmapCache.get(paramString);
    if (localSoftReference != null) {}
    for (Bitmap localBitmap = (Bitmap)localSoftReference.get();; localBitmap = null) {
      return localBitmap;
    }
  }
  
  private static Bitmap getBitmapDB(String paramString, boolean paramBoolean, Context paramContext)
  {
    String str = new AppDB(paramContext).loadBmpBase64(paramString, paramBoolean);
    if (TextUtils.isEmpty(str)) {}
    Object localObject;
    for (Bitmap localBitmap = null;; localBitmap = BitmapFactory.decodeByteArray(localObject, 0, localObject.length, localOptions))
    {
      return localBitmap;
      localObject = null;
      try
      {
        byte[] arrayOfByte = Base64.decode(str, 0);
        localObject = arrayOfByte;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          BitmapFactory.Options localOptions;
          localException.printStackTrace();
        }
      }
      localOptions = new BitmapFactory.Options();
      localOptions.inPurgeable = true;
    }
  }
  
  private static Bitmap getBitmapHttp(String paramString)
  {
    try
    {
      byte[] arrayOfByte = AppHttp.doGetImage(paramString);
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inPurgeable = true;
      localBitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length, localOptions);
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Bitmap localBitmap = null;
      }
    }
    return localBitmap;
  }
  
  public static Bitmap getBitmapIcon(String paramString, Context paramContext)
  {
    return getBitmapIcon(paramString, true, paramContext);
  }
  
  public static Bitmap getBitmapIcon(String paramString, boolean paramBoolean, Context paramContext)
  {
    Bitmap localBitmap1 = getBitmapCache(paramString);
    if (localBitmap1 != null) {}
    Bitmap localBitmap2;
    for (Object localObject = localBitmap1;; localObject = localBitmap2)
    {
      return localObject;
      localBitmap2 = getBitmapDB(paramString, false, paramContext);
      if (localBitmap2 == null) {
        break;
      }
      if (paramBoolean) {
        setBitmapCache(paramString, localBitmap2);
      }
    }
    Bitmap localBitmap3 = getBitmapHttp(paramString);
    if (localBitmap3 == null) {
      localBitmap3 = getBitmapDB(paramString, true, paramContext);
    }
    for (;;)
    {
      if (paramBoolean) {
        setBitmapCache(paramString, localBitmap3);
      }
      localObject = localBitmap3;
      break;
      setBitmapDB(paramString, localBitmap3, paramContext);
    }
  }
  
  public static AlphaAnimation getIconAnimation()
  {
    return getAnimation(0.5F, 1.0F, 500);
  }
  
  public static int parseColor(String paramString1, String paramString2)
  {
    int i;
    if (TextUtils.isEmpty(paramString1)) {
      i = Color.parseColor(paramString2);
    }
    for (;;)
    {
      return i;
      if (paramString1.equals("black")) {
        i = Color.parseColor("#333333");
      } else if (paramString1.equals("pink")) {
        i = Color.parseColor("#FFD4DD");
      } else {
        try
        {
          int j = Color.parseColor(paramString1);
          i = j;
        }
        catch (Exception localException)
        {
          i = Color.parseColor(paramString2);
        }
      }
    }
  }
  
  public static Bitmap resizeBitmapToSpecifiedSize(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    if (paramBitmap == null) {}
    for (paramBitmap = null;; paramBitmap = Bitmap.createScaledBitmap(paramBitmap, paramInt1, paramInt2, true)) {
      do
      {
        return paramBitmap;
      } while ((paramBitmap.getWidth() == paramInt1) && (paramBitmap.getHeight() == paramInt2));
    }
  }
  
  public static Bitmap resizeBitmapToSpecifiedSizeDrawable(Bitmap paramBitmap, int paramInt1, int paramInt2, int paramInt3)
  {
    Bitmap localBitmap2;
    if (paramBitmap == null) {
      localBitmap2 = null;
    }
    for (;;)
    {
      return localBitmap2;
      Bitmap localBitmap1 = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
      new Canvas(localBitmap1).drawRoundRect(new RectF(0.0F, 0.0F, paramInt1, paramInt2), paramInt3, paramInt3, new Paint(1));
      localBitmap2 = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap2);
      Paint localPaint = new Paint();
      localCanvas.drawBitmap(localBitmap1, 0.0F, 0.0F, localPaint);
      localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      localCanvas.drawBitmap(paramBitmap, new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight()), new Rect(0, 0, paramInt1, paramInt2), localPaint);
    }
  }
  
  public static void setBitmapCache(String paramString, Bitmap paramBitmap)
  {
    if (paramBitmap != null) {
      bitmapCache.put(paramString, new SoftReference(paramBitmap));
    }
  }
  
  private static void setBitmapDB(String paramString, Bitmap paramBitmap, Context paramContext)
  {
    if (paramBitmap == null) {}
    for (;;)
    {
      return;
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
      String str = Base64.encodeToString(localByteArrayOutputStream.toByteArray(), 0);
      new AppDB(paramContext).createBmpBase64(paramString, str);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.common.AppImages
 * JD-Core Version:    0.7.0.1
 */