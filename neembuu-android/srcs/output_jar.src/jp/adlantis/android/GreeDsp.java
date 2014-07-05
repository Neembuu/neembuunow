package jp.adlantis.android;

import android.util.Log;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class GreeDsp
{
  private static final String LOG_TAG = "GreeDsp";
  public static final String UUID_LOCAL_STORAGE_KEY = "uuid";
  
  public static String getUUID()
  {
    if (SharedStorage.getInstance().get("uuid") != null) {}
    for (String str = SharedStorage.getInstance().getProperty("uuid");; str = null)
    {
      if ((str == null) || (str.length() == 0))
      {
        str = md5(UUID.randomUUID().toString());
        SharedStorage.getInstance().setProperty("uuid", str);
      }
      try
      {
        SharedStorage.getInstance().store();
        return str;
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          Log.w("GreeDsp", "failed to store uuid:" + localIOException.getMessage());
          str = null;
        }
      }
    }
  }
  
  private static String md5(String paramString)
  {
    StringBuffer localStringBuffer;
    int i;
    String str;
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      localStringBuffer = new StringBuffer();
      i = 0;
      if (i < arrayOfByte.length)
      {
        str = Integer.toHexString(0xFF & arrayOfByte[i]);
        while (str.length() < 2)
        {
          str = "0" + str;
          continue;
          return paramString;
        }
      }
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    for (;;)
    {
      localStringBuffer.append(str);
      i++;
      break;
      paramString = localStringBuffer.toString();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.GreeDsp
 * JD-Core Version:    0.7.0.1
 */