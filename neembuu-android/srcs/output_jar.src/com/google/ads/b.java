package com.google.ads;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class b
{
  private static b c = null;
  private final BigInteger a = d();
  private BigInteger b = BigInteger.ONE;
  
  /**
   * @deprecated
   */
  public static b a()
  {
    try
    {
      if (c == null) {
        c = new b();
      }
      b localb = c;
      return localb;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private static byte[] a(long paramLong)
  {
    return BigInteger.valueOf(paramLong).toByteArray();
  }
  
  private static BigInteger d()
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      UUID localUUID = UUID.randomUUID();
      localMessageDigest.update(a(localUUID.getLeastSignificantBits()));
      localMessageDigest.update(a(localUUID.getMostSignificantBits()));
      byte[] arrayOfByte = new byte[9];
      arrayOfByte[0] = 0;
      System.arraycopy(localMessageDigest.digest(), 0, arrayOfByte, 1, 8);
      BigInteger localBigInteger = new BigInteger(arrayOfByte);
      return localBigInteger;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new RuntimeException("Cannot find MD5 message digest algorithm.");
    }
  }
  
  /**
   * @deprecated
   */
  public BigInteger b()
  {
    try
    {
      BigInteger localBigInteger = this.a;
      return localBigInteger;
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
  public BigInteger c()
  {
    try
    {
      BigInteger localBigInteger = this.b;
      this.b = this.b.add(BigInteger.ONE);
      return localBigInteger;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.b
 * JD-Core Version:    0.7.0.1
 */