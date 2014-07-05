package jp.co.asbit.pvstar.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.StatFs;
import android.preference.PreferenceManager;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import jp.co.asbit.pvstar.AsyncTask;

public class CacheManager
{
  private static final String TAG = "CacheManager";
  private Cache cache;
  private File cacheDir;
  private Context mContext;
  private long maxSize = 0L;
  private final long padSize = 104857600L;
  
  public CacheManager(Context paramContext)
    throws CacheManager.CachingDisableException
  {
    this.mContext = paramContext;
    this.cacheDir = this.mContext.getExternalCacheDir();
    if (this.cacheDir == null) {
      throw new CachingDisableException();
    }
    this.cacheDir = new File(this.cacheDir, "videos");
    if (this.cacheDir.mkdir()) {
      Log.d("CacheManager", "CACHE DIR:" + this.cacheDir.getPath());
    }
    this.maxSize = maxSize();
  }
  
  private long externalBlankSize()
  {
    String str = Environment.getExternalStorageDirectory().getAbsolutePath();
    Log.d("CacheManager", str);
    StatFs localStatFs = new StatFs(str);
    return localStatFs.getBlockSize() * localStatFs.getAvailableBlocks();
  }
  
  private long maxSize()
  {
    return 1024L * (1024L * Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(this.mContext).getString("cache_max_size", "256")));
  }
  
  public void clearTemporary()
    throws IOException
  {
    new Cache(this.cacheDir).clearTemporaryFiles();
  }
  
  public Cache getCache(String paramString)
    throws IOException, CacheManager.CachingDisableException
  {
    this.maxSize = maxSize();
    if (this.maxSize == 0L) {
      throw new CachingDisableException();
    }
    this.cache = new Cache(this.cacheDir, paramString);
    return this.cache;
  }
  
  public File[] getCacheFiles()
  {
    return this.cacheDir.listFiles();
  }
  
  public Cache getCacheForWrite(String paramString, int paramInt)
    throws IOException, CacheManager.CachingDisableException
  {
    this.maxSize = maxSize();
    if (this.maxSize == 0L) {
      throw new CachingDisableException();
    }
    AsyncTask local1 = new AsyncTask()
    {
      protected String doInBackground(Long... paramAnonymousVarArgs)
      {
        CacheManager.this.trimCache(paramAnonymousVarArgs[0].longValue());
        return null;
      }
    };
    Long[] arrayOfLong = new Long[1];
    arrayOfLong[0] = Long.valueOf(this.maxSize);
    local1.execute(arrayOfLong);
    this.cache = new Cache(this.cacheDir, paramString, paramInt);
    return this.cache;
  }
  
  public long getTotalSize(File[] paramArrayOfFile)
  {
    long l = 0L;
    if (paramArrayOfFile != null) {}
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfFile.length) {
        return l;
      }
      l += paramArrayOfFile[i].length();
    }
  }
  
  public void trimCache()
  {
    trimCache(this.maxSize);
  }
  
  public void trimCache(long paramLong)
  {
    File[] arrayOfFile;
    if (this.cacheDir.exists())
    {
      arrayOfFile = getCacheFiles();
      if (arrayOfFile != null) {
        break label20;
      }
    }
    label20:
    label232:
    for (;;)
    {
      return;
      long l1 = getTotalSize(arrayOfFile);
      Log.d("CacheManager", "キャッシュファイルのトータルは" + l1 / 1024L / 1024L + "MBです。");
      long l2 = externalBlankSize();
      Log.d("CacheManager", "ディスク空き領域は" + l2 / 1024L / 1024L + "MBです。");
      if (l2 < 104857600L)
      {
        Log.d("CacheManager", "残り容量が100MB以下です。");
        paramLong = l1 + l2 - 104857600L;
      }
      if (paramLong <= l1)
      {
        Log.d("CacheManager", "キャッシュファイルの切り詰めを実施します。");
        Arrays.sort(arrayOfFile, new LastModifiedComparator());
        for (int i = 0;; i++)
        {
          if (i >= arrayOfFile.length) {
            break label232;
          }
          l1 -= arrayOfFile[i].length();
          arrayOfFile[i].delete();
          Log.d("CacheManager", "TOTAL " + l1 + " MAXSIZE " + paramLong);
          if (paramLong > l1) {
            break;
          }
        }
      }
    }
  }
  
  public class CachingDisableException
    extends Exception
  {
    private static final long serialVersionUID = -4131151355209503666L;
    
    public CachingDisableException() {}
  }
  
  class LastModifiedComparator
    implements Comparator<File>
  {
    LastModifiedComparator() {}
    
    public int compare(File paramFile1, File paramFile2)
    {
      return new Long(paramFile1.lastModified()).compareTo(new Long(paramFile2.lastModified()));
    }
    
    public boolean equals(File paramFile1, File paramFile2)
    {
      if (paramFile1.lastModified() == paramFile2.lastModified()) {}
      for (boolean bool = true;; bool = false) {
        return bool;
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.cache.CacheManager
 * JD-Core Version:    0.7.0.1
 */