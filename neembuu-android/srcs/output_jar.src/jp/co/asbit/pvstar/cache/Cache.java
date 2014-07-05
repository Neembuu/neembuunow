package jp.co.asbit.pvstar.cache;

import android.util.Log;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cache
{
  private static final String TAG = "Cache";
  File cacheDir;
  File cacheFile;
  long fileSize = 0L;
  String key;
  Pattern p = Pattern.compile("([0-9]+)\\.mp4$");
  File temporaryFile;
  RandomAccessFile temporaryFileStream;
  String temporaryFormat = "tmp_%s_%d.mp4";
  String temporaryFormatStartsWith = "tmp_%s_";
  String temporaryStartsWith;
  
  Cache(File paramFile)
    throws IOException
  {
    this.cacheDir = paramFile;
  }
  
  Cache(File paramFile, String paramString)
    throws IOException
  {
    this.cacheDir = paramFile;
    this.key = paramString;
    this.cacheFile = new File(paramFile, paramString + ".mp4");
  }
  
  Cache(File paramFile, String paramString, long paramLong)
    throws IOException
  {
    this.cacheDir = paramFile;
    this.key = paramString;
    this.cacheFile = new File(paramFile, paramString + ".mp4");
    this.fileSize = paramLong;
    String str = this.temporaryFormatStartsWith;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString;
    this.temporaryStartsWith = String.format(str, arrayOfObject);
  }
  
  private void touch()
  {
    this.cacheFile.setLastModified(System.currentTimeMillis());
  }
  
  public void clearTemporaryFiles()
    throws IOException
  {
    Log.d("Cache", "キャッシュの一時ファイルを削除します。");
    File[] arrayOfFile = this.cacheDir.listFiles(new FilenameFilter()
    {
      public boolean accept(File paramAnonymousFile, String paramAnonymousString)
      {
        return paramAnonymousString.startsWith("tmp_");
      }
    });
    if (arrayOfFile != null) {}
    for (int i = 0;; i++)
    {
      if (i >= arrayOfFile.length) {
        return;
      }
      arrayOfFile[i].delete();
    }
  }
  
  public void completeCache()
    throws IOException
  {
    TemporaryCache[] arrayOfTemporaryCache;
    try
    {
      this.temporaryFileStream.close();
      arrayOfFile = this.cacheDir.listFiles(new FilenameFilter()
      {
        public boolean accept(File paramAnonymousFile, String paramAnonymousString)
        {
          return paramAnonymousString.startsWith(Cache.this.temporaryStartsWith);
        }
      });
      arrayOfTemporaryCache = new TemporaryCache[arrayOfFile.length];
      i = 0;
      if (i >= arrayOfFile.length)
      {
        Arrays.sort(arrayOfTemporaryCache, new StartPositionComparator());
        l = -1L;
        j = 0;
        if (j < arrayOfTemporaryCache.length) {
          break label191;
        }
        Log.d("Cache", "lastPos:" + l + "/fileSize:" + this.fileSize);
        if (l == this.fileSize) {
          break label261;
        }
        Log.d("Cache", "キャッシュは完全ではありません。");
      }
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        File[] arrayOfFile;
        int i;
        long l;
        int j;
        Log.d("Cache", "temporaryFileStreamを閉じられませんでした。");
        continue;
        Matcher localMatcher = this.p.matcher(arrayOfFile[i].getName());
        if (localMatcher.find()) {
          arrayOfTemporaryCache[i] = new TemporaryCache(arrayOfFile[i], Integer.valueOf(localMatcher.group(1)));
        }
        i++;
        continue;
        label191:
        if (1L + l < arrayOfTemporaryCache[j].startPosition)
        {
          Log.d("Cache", "キャッシュは完全ではありません。" + j);
        }
        else
        {
          l = arrayOfTemporaryCache[j].startPosition + arrayOfTemporaryCache[j].file.length();
          j++;
        }
      }
    }
    label261:
    if (arrayOfTemporaryCache.length == 1)
    {
      Log.d("Cache", "キャッシュは完全です。リネームをします。");
      arrayOfTemporaryCache[0].file.renameTo(this.cacheFile);
    }
    RandomAccessFile localRandomAccessFile1;
    int k;
    for (;;)
    {
      Log.d("Cache", "キャッシュファイルを作成しました。SIZE:" + this.cacheFile.length());
      break;
      Log.d("Cache", "キャッシュは完全です。結合処理をします。");
      localRandomAccessFile1 = new RandomAccessFile(this.cacheFile, "rw");
      k = 0;
      if (k < arrayOfTemporaryCache.length) {
        break label364;
      }
      localRandomAccessFile1.close();
    }
    label364:
    RandomAccessFile localRandomAccessFile2 = new RandomAccessFile(arrayOfTemporaryCache[k].file, "r");
    byte[] arrayOfByte = new byte[8192];
    localRandomAccessFile1.seek(arrayOfTemporaryCache[k].startPosition);
    for (;;)
    {
      int m = localRandomAccessFile2.read(arrayOfByte);
      if (m == -1)
      {
        localRandomAccessFile2.close();
        arrayOfTemporaryCache[k].file.delete();
        k++;
        break;
      }
      localRandomAccessFile1.write(arrayOfByte, 0, m);
    }
  }
  
  public File get()
  {
    if (isExists()) {}
    for (File localFile = this.cacheFile;; localFile = null) {
      return localFile;
    }
  }
  
  public String getUri()
  {
    touch();
    return this.cacheFile.getPath();
  }
  
  public boolean isExists()
  {
    return this.cacheFile.exists();
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.temporaryFile == null)
    {
      File localFile = this.cacheDir;
      String str = this.temporaryFormat;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.key;
      arrayOfObject[1] = Integer.valueOf(paramInt1);
      this.temporaryFile = new File(localFile, String.format(str, arrayOfObject));
      if (!this.temporaryFile.exists()) {
        this.temporaryFile.createNewFile();
      }
      this.temporaryFileStream = new RandomAccessFile(this.temporaryFile, "rw");
      this.temporaryFileStream.seek(0L);
    }
    this.temporaryFileStream.write(paramArrayOfByte, 0, paramInt2);
  }
  
  public class StartPositionComparator
    implements Comparator<Cache.TemporaryCache>
  {
    public StartPositionComparator() {}
    
    public int compare(Cache.TemporaryCache paramTemporaryCache1, Cache.TemporaryCache paramTemporaryCache2)
    {
      long l1 = paramTemporaryCache1.startPosition;
      long l2 = paramTemporaryCache2.startPosition;
      int i;
      if (l1 > l2) {
        i = 1;
      }
      for (;;)
      {
        return i;
        if (l1 < l2) {
          i = -1;
        } else {
          i = 0;
        }
      }
    }
  }
  
  public class TemporaryCache
  {
    public File file;
    public long startPosition;
    
    public TemporaryCache(File paramFile, Integer paramInteger)
    {
      this.file = paramFile;
      this.startPosition = paramInteger.intValue();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.cache.Cache
 * JD-Core Version:    0.7.0.1
 */