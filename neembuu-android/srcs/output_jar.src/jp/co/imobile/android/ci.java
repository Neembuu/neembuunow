package jp.co.imobile.android;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final class ci
{
  static final String a(String paramString)
  {
    try
    {
      String str2 = URLDecoder.decode(paramString, "utf-8");
      str1 = str2;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;)
      {
        String str1 = "";
      }
    }
    return str1;
  }
  
  static final boolean a(CharSequence paramCharSequence)
  {
    if ((paramCharSequence != null) && (paramCharSequence.length() > 0)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  static final String b(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString.getBytes());
      byte[] arrayOfByte = localMessageDigest.digest();
      StringBuffer localStringBuffer = new StringBuffer(2 * arrayOfByte.length);
      String str;
      for (int i = 0;; i++)
      {
        if (i >= arrayOfByte.length)
        {
          str = localStringBuffer.toString();
          break;
        }
        int j = 0xFF & arrayOfByte[i];
        if (j < 16) {
          localStringBuffer.append("0");
        }
        localStringBuffer.append(Integer.toHexString(j));
      }
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      str = "";
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.ci
 * JD-Core Version:    0.7.0.1
 */