package com.google.ads;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public abstract class aj
  implements ai
{
  protected MotionEvent a = null;
  protected DisplayMetrics b = null;
  private au c = null;
  private ByteArrayOutputStream d = null;
  
  protected aj(Context paramContext)
  {
    try
    {
      this.b = paramContext.getResources().getDisplayMetrics();
      return;
    }
    catch (UnsupportedOperationException localUnsupportedOperationException)
    {
      for (;;)
      {
        this.b = new DisplayMetrics();
        this.b.density = 1.0F;
      }
    }
  }
  
  private String a(Context paramContext, String paramString, boolean paramBoolean)
  {
    try
    {
      a();
      if (paramBoolean) {
        c(paramContext);
      }
      byte[] arrayOfByte;
      Object localObject;
      for (;;)
      {
        arrayOfByte = b();
        if (arrayOfByte.length != 0) {
          break;
        }
        localObject = Integer.toString(5);
        break label90;
        b(paramContext);
      }
      String str;
      return localObject;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localObject = Integer.toString(7);
      break label90;
      str = a(arrayOfByte, paramString);
      localObject = str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      localObject = Integer.toString(7);
    }
    catch (IOException localIOException)
    {
      localObject = Integer.toString(3);
    }
  }
  
  private void a()
  {
    this.d = new ByteArrayOutputStream();
    this.c = au.a(this.d);
  }
  
  private byte[] b()
    throws IOException
  {
    this.c.a();
    return this.d.toByteArray();
  }
  
  public String a(Context paramContext)
  {
    return a(paramContext, null, false);
  }
  
  public String a(Context paramContext, String paramString)
  {
    return a(paramContext, paramString, true);
  }
  
  String a(byte[] paramArrayOfByte, String paramString)
    throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException
  {
    if (paramArrayOfByte.length > 239)
    {
      a();
      a(20, 1L);
      paramArrayOfByte = b();
    }
    byte[] arrayOfByte5;
    if (paramArrayOfByte.length < 239)
    {
      arrayOfByte5 = new byte[239 - paramArrayOfByte.length];
      new SecureRandom().nextBytes(arrayOfByte5);
    }
    for (byte[] arrayOfByte1 = ByteBuffer.allocate(240).put((byte)paramArrayOfByte.length).put(paramArrayOfByte).put(arrayOfByte5).array();; arrayOfByte1 = ByteBuffer.allocate(240).put((byte)paramArrayOfByte.length).put(paramArrayOfByte).array())
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(arrayOfByte1);
      byte[] arrayOfByte2 = localMessageDigest.digest();
      byte[] arrayOfByte3 = ByteBuffer.allocate(256).put(arrayOfByte2).put(arrayOfByte1).array();
      byte[] arrayOfByte4 = new byte[256];
      new ag().a(arrayOfByte3, arrayOfByte4);
      if ((paramString != null) && (paramString.length() > 0)) {
        a(paramString, arrayOfByte4);
      }
      return aq.a(arrayOfByte4, false);
    }
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.a != null) {
      this.a.recycle();
    }
    this.a = MotionEvent.obtain(0L, paramInt3, 1, paramInt1 * this.b.density, paramInt2 * this.b.density, 0.0F, 0.0F, 0, 0.0F, 0.0F, 0, 0);
  }
  
  protected void a(int paramInt, long paramLong)
    throws IOException
  {
    this.c.a(paramInt, paramLong);
  }
  
  protected void a(int paramInt, String paramString)
    throws IOException
  {
    this.c.a(paramInt, paramString);
  }
  
  public void a(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getAction() == 1)
    {
      if (this.a != null) {
        this.a.recycle();
      }
      this.a = MotionEvent.obtain(paramMotionEvent);
    }
  }
  
  void a(String paramString, byte[] paramArrayOfByte)
    throws UnsupportedEncodingException
  {
    if (paramString.length() > 32) {
      paramString = paramString.substring(0, 32);
    }
    new ar(paramString.getBytes("UTF-8")).a(paramArrayOfByte);
  }
  
  protected abstract void b(Context paramContext);
  
  protected abstract void c(Context paramContext);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.aj
 * JD-Core Version:    0.7.0.1
 */