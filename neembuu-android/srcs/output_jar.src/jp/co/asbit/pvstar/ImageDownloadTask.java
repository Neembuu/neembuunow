package jp.co.asbit.pvstar;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.widget.ImageView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class ImageDownloadTask
  extends AsyncTask<URL, Long, Bitmap>
{
  private String tag;
  private ImageView thumbnail;
  
  public ImageDownloadTask(ImageView paramImageView)
  {
    this.thumbnail = paramImageView;
    this.tag = paramImageView.getTag().toString();
  }
  
  private Bitmap getBitmapFromURL(URL paramURL)
  {
    HttpURLConnection localHttpURLConnection = null;
    InputStream localInputStream = null;
    for (;;)
    {
      try
      {
        localHttpURLConnection = (HttpURLConnection)paramURL.openConnection();
        localHttpURLConnection.setReadTimeout(3000);
        localHttpURLConnection.setConnectTimeout(3000);
        localHttpURLConnection.setUseCaches(true);
        localHttpURLConnection.setDoInput(true);
        localHttpURLConnection.connect();
        localInputStream = localHttpURLConnection.getInputStream();
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inPurgeable = true;
        localObject2 = BitmapFactory.decodeStream(localInputStream, null, localOptions);
        if (localObject2 != null)
        {
          Bitmap localBitmap = ((Bitmap)localObject2).copy(Bitmap.Config.RGB_565, true);
          localObject2 = localBitmap;
        }
      }
      catch (IOException localIOException)
      {
        Object localObject2;
        localIOException.printStackTrace();
        if (localHttpURLConnection == null) {
          continue;
        }
        try
        {
          localHttpURLConnection.disconnect();
          if (localInputStream == null) {
            continue;
          }
          localInputStream.close();
          localObject2 = null;
        }
        catch (Exception localException6)
        {
          localException6.printStackTrace();
          continue;
        }
      }
      catch (IllegalStateException localIllegalStateException)
      {
        localIllegalStateException.printStackTrace();
        if (localHttpURLConnection == null) {
          continue;
        }
        try
        {
          localHttpURLConnection.disconnect();
          if (localInputStream == null) {
            continue;
          }
          localInputStream.close();
        }
        catch (Exception localException5)
        {
          localException5.printStackTrace();
        }
        continue;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        localIllegalArgumentException.printStackTrace();
        if (localHttpURLConnection == null) {
          continue;
        }
        try
        {
          localHttpURLConnection.disconnect();
          if (localInputStream == null) {
            continue;
          }
          localInputStream.close();
        }
        catch (Exception localException4)
        {
          localException4.printStackTrace();
        }
        continue;
      }
      catch (IllegalAccessError localIllegalAccessError)
      {
        localIllegalAccessError.printStackTrace();
        if (localHttpURLConnection == null) {
          continue;
        }
        try
        {
          localHttpURLConnection.disconnect();
          if (localInputStream == null) {
            continue;
          }
          localInputStream.close();
        }
        catch (Exception localException3)
        {
          localException3.printStackTrace();
        }
        continue;
      }
      catch (NullPointerException localNullPointerException)
      {
        localNullPointerException.printStackTrace();
        if (localHttpURLConnection == null) {
          continue;
        }
        try
        {
          localHttpURLConnection.disconnect();
          if (localInputStream == null) {
            continue;
          }
          localInputStream.close();
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
        continue;
      }
      finally
      {
        if (localHttpURLConnection == null) {
          break label311;
        }
      }
      try
      {
        localHttpURLConnection.disconnect();
        if (localInputStream != null) {
          localInputStream.close();
        }
        return localObject2;
      }
      catch (Exception localException7)
      {
        localException7.printStackTrace();
      }
    }
    try
    {
      localHttpURLConnection.disconnect();
      label311:
      if (localInputStream != null) {
        localInputStream.close();
      }
      throw localObject1;
    }
    catch (Exception localException1)
    {
      for (;;)
      {
        localException1.printStackTrace();
      }
    }
  }
  
  protected Bitmap doInBackground(URL... paramVarArgs)
  {
    Bitmap localBitmap;
    try
    {
      String str = paramVarArgs[0].toString();
      localBitmap = getBitmapFromURL(paramVarArgs[0]);
      if (localBitmap == null) {
        localBitmap = null;
      } else {
        ImageCache.setImage(str, localBitmap);
      }
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
    return localBitmap;
  }
  
  protected void onPostExecute(Bitmap paramBitmap)
  {
    if (this.tag.equals(this.thumbnail.getTag()))
    {
      this.thumbnail.setImageBitmap(paramBitmap);
      this.thumbnail.setVisibility(0);
    }
    super.onPostExecute(paramBitmap);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.ImageDownloadTask
 * JD-Core Version:    0.7.0.1
 */