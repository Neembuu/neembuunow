package jp.co.asbit.pvstar;

import android.graphics.Bitmap;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class ImageCache
{
  private static final int HARD_CACHE_CAPACITY = 50;
  private static final HashMap<String, Bitmap> sHardBitmapCache = new LinkedHashMap(25, 0.75F, true)
  {
    private static final long serialVersionUID = 8845694182257425352L;
    
    protected boolean removeEldestEntry(Map.Entry<String, Bitmap> paramAnonymousEntry)
    {
      if (size() > 50) {
        ImageCache.sSoftBitmapCache.put((String)paramAnonymousEntry.getKey(), new SoftReference((Bitmap)paramAnonymousEntry.getValue()));
      }
      for (boolean bool = true;; bool = false) {
        return bool;
      }
    }
  };
  private static final ConcurrentHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache = new ConcurrentHashMap(25);
  
  public static void clear()
  {
    sHardBitmapCache.clear();
    sSoftBitmapCache.clear();
  }
  
  public static Bitmap getImage(String paramString)
  {
    Bitmap localBitmap;
    SoftReference localSoftReference;
    synchronized (sHardBitmapCache)
    {
      try
      {
        localBitmap = (Bitmap)sHardBitmapCache.get(paramString);
        if ((paramString != null) && (localBitmap != null))
        {
          sHardBitmapCache.remove(paramString);
          sHardBitmapCache.put(paramString, localBitmap);
        }
      }
      catch (NullPointerException localNullPointerException1)
      {
        localNullPointerException1.printStackTrace();
        localSoftReference = (SoftReference)sSoftBitmapCache.get(paramString);
        if (localSoftReference == null) {}
      }
    }
    try
    {
      localBitmap = (Bitmap)localSoftReference.get();
      if (localBitmap == null)
      {
        sSoftBitmapCache.remove(paramString);
        localBitmap = null;
        break label118;
        localObject = finally;
        throw localObject;
      }
    }
    catch (NullPointerException localNullPointerException2)
    {
      for (;;)
      {
        localNullPointerException2.printStackTrace();
      }
    }
    label118:
    return localBitmap;
  }
  
  public static void setImage(String paramString, Bitmap paramBitmap)
  {
    if ((paramString != null) && (paramBitmap != null)) {
      synchronized (sHardBitmapCache)
      {
        try
        {
          sHardBitmapCache.put(paramString, paramBitmap);
        }
        catch (NullPointerException localNullPointerException)
        {
          for (;;)
          {
            localNullPointerException.printStackTrace();
          }
        }
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.ImageCache
 * JD-Core Version:    0.7.0.1
 */