package jp.adlantis.android;

import android.os.Environment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SharedStorage
  extends Properties
{
  private static final String LOCAL_STORAGE_PATH = "net.gree.android.ads/.data";
  private static final String LOG_TAG = "SharedStorage";
  private static String dataPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "net.gree.android.ads/.data";
  private static SharedStorage instance;
  
  /* Error */
  /**
   * @deprecated
   */
  public static SharedStorage getInstance()
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 51	jp/adlantis/android/SharedStorage:instance	Ljp/adlantis/android/SharedStorage;
    //   6: ifnonnull +19 -> 25
    //   9: new 2	jp/adlantis/android/SharedStorage
    //   12: dup
    //   13: invokespecial 52	jp/adlantis/android/SharedStorage:<init>	()V
    //   16: putstatic 51	jp/adlantis/android/SharedStorage:instance	Ljp/adlantis/android/SharedStorage;
    //   19: getstatic 51	jp/adlantis/android/SharedStorage:instance	Ljp/adlantis/android/SharedStorage;
    //   22: invokevirtual 55	jp/adlantis/android/SharedStorage:load	()V
    //   25: getstatic 51	jp/adlantis/android/SharedStorage:instance	Ljp/adlantis/android/SharedStorage;
    //   28: astore_1
    //   29: ldc 2
    //   31: monitorexit
    //   32: aload_1
    //   33: areturn
    //   34: astore_2
    //   35: ldc 11
    //   37: new 18	java/lang/StringBuilder
    //   40: dup
    //   41: invokespecial 21	java/lang/StringBuilder:<init>	()V
    //   44: ldc 57
    //   46: invokevirtual 37	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: aload_2
    //   50: invokevirtual 60	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   53: invokevirtual 37	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   56: invokevirtual 42	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   59: invokestatic 66	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   62: pop
    //   63: goto -38 -> 25
    //   66: astore_0
    //   67: ldc 2
    //   69: monitorexit
    //   70: aload_0
    //   71: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   66	5	0	localObject	Object
    //   28	5	1	localSharedStorage	SharedStorage
    //   34	16	2	localException	java.lang.Exception
    // Exception table:
    //   from	to	target	type
    //   19	25	34	java/lang/Exception
    //   3	19	66	finally
    //   19	25	66	finally
    //   25	29	66	finally
    //   35	63	66	finally
  }
  
  public static void setDataPath(String paramString)
  {
    dataPath = paramString;
  }
  
  /**
   * @deprecated
   */
  public void clearAll()
  {
    try
    {
      File localFile = new File(dataPath);
      if (localFile.exists()) {
        localFile.delete();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /**
   * @deprecated
   */
  public void load()
    throws IOException
  {
    try
    {
      File localFile = new File(dataPath);
      boolean bool = localFile.exists();
      if (bool) {}
      FileInputStream localFileInputStream;
      try
      {
        localFileInputStream = new FileInputStream(localFile);
        if (localFileInputStream == null) {
          break label66;
        }
      }
      finally
      {
        try
        {
          super.load(localFileInputStream);
          if (localFileInputStream != null) {
            localFileInputStream.close();
          }
          return;
        }
        finally {}
        localObject2 = finally;
        localFileInputStream = null;
      }
      localFileInputStream.close();
      label66:
      throw localObject2;
    }
    finally {}
  }
  
  /**
   * @deprecated
   */
  public void store()
    throws IOException
  {
    try
    {
      File localFile = new File(dataPath);
      if (!localFile.exists())
      {
        localFile.getParentFile().mkdirs();
        localFile.createNewFile();
      }
      FileOutputStream localFileOutputStream;
      try
      {
        localFileOutputStream = new FileOutputStream(localFile);
        if (localFileOutputStream == null) {
          break label72;
        }
      }
      finally
      {
        try
        {
          super.store(localFileOutputStream, "");
          if (localFileOutputStream != null) {
            localFileOutputStream.close();
          }
          return;
        }
        finally {}
        localObject2 = finally;
        localFileOutputStream = null;
      }
      localFileOutputStream.close();
      label72:
      throw localObject2;
    }
    finally {}
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.SharedStorage
 * JD-Core Version:    0.7.0.1
 */