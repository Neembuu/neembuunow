package jp.co.asbit.pvstar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class BackgroundImageTask
  extends AsyncTask<Uri, Long, String>
{
  public static final int BG_MODE_DEFAULT = 1;
  public static final int BG_MODE_MYLIST = 2;
  private static final String TAG = "MylistBGTask";
  private int bgMode;
  private Context mContext;
  private Long mylistId;
  private ProgressDialog progressDialog;
  
  public BackgroundImageTask(Context paramContext)
  {
    this.mContext = paramContext;
    this.bgMode = 1;
  }
  
  public BackgroundImageTask(Long paramLong, Context paramContext)
  {
    this.mContext = paramContext;
    this.bgMode = 2;
    this.mylistId = paramLong;
  }
  
  private static byte[] chngBmpToData(Bitmap paramBitmap, Bitmap.CompressFormat paramCompressFormat, int paramInt)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(paramCompressFormat, paramInt, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }
  
  public static String getBackgroudImagePath(int paramInt, Context paramContext)
  {
    return new BackgroundImageTask(paramContext).getFilepath(paramInt);
  }
  
  public static String getBackgroudImagePath(int paramInt, Long paramLong, Context paramContext)
  {
    return new BackgroundImageTask(paramLong, paramContext).getFilepath(paramInt);
  }
  
  private String getFilepath(int paramInt)
  {
    String str1 = Util.getCacheDir(this.mContext, "wallpapers");
    String str2 = null;
    switch (this.bgMode)
    {
    }
    for (;;)
    {
      return str1 + "/" + str2;
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(paramInt);
      String.format("bg_default_%d.jpg", arrayOfObject2);
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = this.mylistId;
      arrayOfObject1[1] = Integer.valueOf(paramInt);
      str2 = String.format("bg_mylist_%d_%d.jpg", arrayOfObject1);
    }
  }
  
  private Bitmap getFixedBitmap(Display paramDisplay, Bitmap paramBitmap)
  {
    int i = paramDisplay.getWidth();
    int j = paramDisplay.getHeight();
    int k = paramBitmap.getWidth();
    int m = paramBitmap.getHeight();
    Configuration localConfiguration = this.mContext.getResources().getConfiguration();
    float f2;
    if (localConfiguration.orientation == 2)
    {
      int i3 = j;
      j = i;
      i = i3;
      Log.d("MylistBGTask", "ORIENTATION_LANDSCAPE : " + k + ":" + m);
      if (m < k)
      {
        Matrix localMatrix = new Matrix();
        localMatrix.postRotate(90.0F);
        paramBitmap = Bitmap.createBitmap(paramBitmap, 0, 0, k, m, localMatrix, true);
        k = paramBitmap.getWidth();
        m = paramBitmap.getHeight();
      }
      float f1 = k / m;
      f2 = i / j;
      if (f1 <= f2) {
        break label377;
      }
      if (m <= j) {
        break label370;
      }
    }
    float f4;
    float f3;
    float f5;
    for (;;)
    {
      f4 = j;
      f3 = f4 * f2;
      f5 = f4 / m;
      if (f5 != 1.0F)
      {
        paramBitmap = resize(paramBitmap, f5);
        k = paramBitmap.getWidth();
        paramBitmap.getHeight();
      }
      float f6 = (float)Math.ceil(f3);
      float f7 = (float)Math.ceil(f4);
      Log.d("MylistBGTask", "SCALE=" + f5 + ":WSCALE=" + f6 + ":HSCALE=" + f7);
      float f8 = 0.0F;
      if (f6 < k) {
        f8 = (k - f6) / 2.0F;
      }
      int n = (int)f8;
      int i1 = (int)f6;
      int i2 = (int)f7;
      return Bitmap.createBitmap(paramBitmap, n, 0, i1, i2);
      if (localConfiguration.orientation != 1) {
        break;
      }
      Log.d("MylistBGTask", "ORIENTATION_PORTRAIT : " + k + ":" + m);
      break;
      label370:
      j = m;
    }
    label377:
    if (k > i) {}
    for (;;)
    {
      f3 = i;
      f4 = f3 / f2;
      f5 = f3 / k;
      break;
      i = k;
    }
  }
  
  public static void removeBackgroudImage(Long paramLong, Context paramContext)
  {
    BackgroundImageTask localBackgroundImageTask = new BackgroundImageTask(paramLong, paramContext);
    if (new File(localBackgroundImageTask.getFilepath(1)).delete()) {
      Log.d("MylistBGTask", "Success to delete image for portrait.");
    }
    if (new File(localBackgroundImageTask.getFilepath(2)).delete()) {
      Log.d("MylistBGTask", "Success to delete image for landscape.");
    }
  }
  
  private Bitmap resize(Bitmap paramBitmap, float paramFloat)
  {
    Matrix localMatrix = new Matrix();
    localMatrix.postScale(paramFloat, paramFloat);
    return Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), localMatrix, true);
  }
  
  /* Error */
  private void saveDataToStorage(byte[] paramArrayOfByte, String paramString)
    throws Exception
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new 204	java/io/FileOutputStream
    //   5: dup
    //   6: aload_2
    //   7: invokespecial 205	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   10: astore 4
    //   12: aload 4
    //   14: aload_1
    //   15: invokevirtual 209	java/io/FileOutputStream:write	([B)V
    //   18: aload 4
    //   20: ifnull +59 -> 79
    //   23: aload 4
    //   25: invokevirtual 212	java/io/FileOutputStream:close	()V
    //   28: return
    //   29: astore 8
    //   31: ldc 14
    //   33: ldc 214
    //   35: invokestatic 146	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   38: pop
    //   39: aload_3
    //   40: ifnull -12 -> 28
    //   43: aload_3
    //   44: invokevirtual 212	java/io/FileOutputStream:close	()V
    //   47: goto -19 -> 28
    //   50: astore 6
    //   52: aload_3
    //   53: ifnull +7 -> 60
    //   56: aload_3
    //   57: invokevirtual 212	java/io/FileOutputStream:close	()V
    //   60: aload 6
    //   62: athrow
    //   63: astore 6
    //   65: aload 4
    //   67: astore_3
    //   68: goto -16 -> 52
    //   71: astore 5
    //   73: aload 4
    //   75: astore_3
    //   76: goto -45 -> 31
    //   79: goto -51 -> 28
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	82	0	this	BackgroundImageTask
    //   0	82	1	paramArrayOfByte	byte[]
    //   0	82	2	paramString	String
    //   1	75	3	localObject1	Object
    //   10	64	4	localFileOutputStream	java.io.FileOutputStream
    //   71	1	5	localException1	Exception
    //   50	11	6	localObject2	Object
    //   63	1	6	localObject3	Object
    //   29	1	8	localException2	Exception
    // Exception table:
    //   from	to	target	type
    //   2	12	29	java/lang/Exception
    //   2	12	50	finally
    //   31	39	50	finally
    //   12	18	63	finally
    //   12	18	71	java/lang/Exception
  }
  
  protected String doInBackground(Uri... paramVarArgs)
  {
    Uri localUri = paramVarArgs[0];
    ContentResolver localContentResolver = this.mContext.getContentResolver();
    if (localUri != null) {}
    for (;;)
    {
      try
      {
        Bitmap localBitmap1 = MediaStore.Images.Media.getBitmap(localContentResolver, localUri);
        Bitmap localBitmap2 = getFixedBitmap(((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay(), localBitmap1);
        saveDataToStorage(chngBmpToData(localBitmap2, Bitmap.CompressFormat.JPEG, 100), getFilepath(1));
        Matrix localMatrix = new Matrix();
        localMatrix.postRotate(270.0F);
        Bitmap localBitmap3 = Bitmap.createBitmap(localBitmap2, 0, 0, localBitmap2.getWidth(), localBitmap2.getHeight(), localMatrix, true);
        byte[] arrayOfByte = chngBmpToData(localBitmap3, Bitmap.CompressFormat.JPEG, 100);
        str = getFilepath(2);
        saveDataToStorage(arrayOfByte, str);
        if (localBitmap3 != null) {
          localBitmap3.recycle();
        }
        return str;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      String str = null;
    }
  }
  
  protected void onPostExecute(String paramString)
  {
    this.progressDialog.dismiss();
    super.onPostExecute(paramString);
  }
  
  protected void onPreExecute()
  {
    this.progressDialog = new ProgressDialog(this.mContext);
    this.progressDialog.setTitle(this.mContext.getString(2131296371));
    this.progressDialog.setMessage(this.mContext.getString(2131296372));
    this.progressDialog.setIndeterminate(false);
    this.progressDialog.setProgressStyle(0);
    this.progressDialog.show();
    super.onPreExecute();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.BackgroundImageTask
 * JD-Core Version:    0.7.0.1
 */