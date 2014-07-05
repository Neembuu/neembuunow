package jp.adlantis.android.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class AsyncImageLoader
{
  private HashMap<String, SoftReference<Drawable>> imageCache = new HashMap();
  
  protected static InputStream inputStreamForUrl(String paramString)
    throws IOException, MalformedURLException
  {
    return new URL(paramString).openStream();
  }
  
  public static Drawable loadImageFromUrl(String paramString)
  {
    Object localObject = null;
    Log.d("AsyncImageLoader", "loadImageFromUrl=" + paramString);
    if (paramString == null) {}
    for (;;)
    {
      return localObject;
      try
      {
        InputStream localInputStream2 = inputStreamForUrl(paramString);
        localInputStream1 = localInputStream2;
        if (localInputStream1 == null) {}
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          try
          {
            Drawable localDrawable = Drawable.createFromStream(localInputStream1, "src");
            localObject = localDrawable;
          }
          catch (OutOfMemoryError localOutOfMemoryError)
          {
            InputStream localInputStream1;
            Log.e("AsyncImageLoader", "exception calling Drawable.createFromStream() " + localOutOfMemoryError);
          }
          localIOException = localIOException;
          System.out.println(localIOException);
          localInputStream1 = null;
        }
      }
    }
  }
  
  public void clear()
  {
    this.imageCache.clear();
  }
  
  public Drawable loadDrawable(Context paramContext, final String paramString, final ImageLoadedCallback paramImageLoadedCallback)
  {
    if (this.imageCache.containsKey(paramString))
    {
      localObject1 = (Drawable)((SoftReference)this.imageCache.get(paramString)).get();
      if (localObject1 == null) {}
    }
    for (;;)
    {
      return localObject1;
      if ((paramContext != null) && (ADLAssetUtils.isAssetUrl(paramString))) {}
      try
      {
        Drawable localDrawable = Drawable.createFromStream(ADLAssetUtils.inputStreamFromAssetUri(paramContext, Uri.parse(paramString)), paramString);
        localObject1 = localDrawable;
        try
        {
          putDrawableInCache(paramString, (Drawable)localObject1);
        }
        catch (IOException localIOException2) {}
      }
      catch (IOException localIOException1)
      {
        for (;;)
        {
          localObject1 = null;
          Object localObject2 = localIOException1;
        }
      }
      Log.e(getClass().getSimpleName(), "exception calling Drawable.createFromStream() " + localIOException2);
      continue;
      new Thread()
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          if (paramImageLoadedCallback != null) {
            paramImageLoadedCallback.imageLoaded((Drawable)paramAnonymousMessage.obj, paramString);
          }
        }
      }
      {
        public void run()
        {
          Drawable localDrawable = AsyncImageLoader.loadImageFromUrl(paramString);
          if (localDrawable != null)
          {
            AsyncImageLoader.this.putDrawableInCache(paramString, localDrawable);
            Message localMessage = this.val$handler.obtainMessage(0, localDrawable);
            this.val$handler.sendMessage(localMessage);
          }
        }
      }.start();
      localObject1 = null;
    }
  }
  
  public void putDrawableInCache(String paramString, Drawable paramDrawable)
  {
    this.imageCache.put(paramString, new SoftReference(paramDrawable));
    Log.d(getClass().getSimpleName(), "imageCache.size()=" + this.imageCache.size());
  }
  
  public static abstract interface ImageLoadedCallback
  {
    public abstract void imageLoaded(Drawable paramDrawable, String paramString);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.utils.AsyncImageLoader
 * JD-Core Version:    0.7.0.1
 */