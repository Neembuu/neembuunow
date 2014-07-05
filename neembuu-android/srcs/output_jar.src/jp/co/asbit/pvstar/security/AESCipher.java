package jp.co.asbit.pvstar.security;

import android.util.Base64;
import android.util.Log;
import java.io.Serializable;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import jp.co.asbit.pvstar.Util;

public final class AESCipher
  implements Serializable
{
  private static final String TAG = null;
  private static final String mykey = ")70(GiosYMwuPN!C,#q=_LzlBJD?GAUn";
  private static final long serialVersionUID = 6870654927009892757L;
  
  public static final String decrypt(String paramString)
  {
    try
    {
      SecretKeySpec localSecretKeySpec = new SecretKeySpec(")70(GiosYMwuPN!C,#q=_LzlBJD?GAUn".getBytes(), "AES");
      byte[] arrayOfByte1 = Base64.decode(paramString, 8);
      Cipher localCipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
      localCipher.init(2, localSecretKeySpec);
      byte[] arrayOfByte2 = new byte[localCipher.getOutputSize(arrayOfByte1.length)];
      localCipher.doFinal(arrayOfByte2, localCipher.update(arrayOfByte1, 0, arrayOfByte1.length, arrayOfByte2, 0));
      String str;
      for (int i = 0;; i++)
      {
        if (i >= arrayOfByte2.length) {}
        int j;
        do
        {
          byte[] arrayOfByte3 = new byte[i];
          System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 0, i);
          str = new String(arrayOfByte3);
          break;
          j = arrayOfByte2[i];
        } while (j == 0);
      }
      return str;
    }
    catch (Exception localException)
    {
      Log.d(TAG, "failed to decrypt.");
      str = Util.empty();
    }
  }
  
  public static final String encrypt(String paramString)
  {
    try
    {
      SecretKeySpec localSecretKeySpec = new SecretKeySpec(")70(GiosYMwuPN!C,#q=_LzlBJD?GAUn".getBytes(), "AES");
      byte[] arrayOfByte1 = paramString.getBytes();
      Cipher localCipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
      localCipher.init(1, localSecretKeySpec);
      byte[] arrayOfByte2 = new byte[localCipher.getOutputSize(arrayOfByte1.length)];
      localCipher.doFinal(arrayOfByte2, localCipher.update(arrayOfByte1, 0, arrayOfByte1.length, arrayOfByte2, 0));
      String str2 = new String(Base64.encode(arrayOfByte2, 8)).replaceFirst("==$", "");
      str1 = str2;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Log.d(TAG, "failed to encrypt.");
        String str1 = Util.empty();
      }
    }
    return str1;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.security.AESCipher
 * JD-Core Version:    0.7.0.1
 */