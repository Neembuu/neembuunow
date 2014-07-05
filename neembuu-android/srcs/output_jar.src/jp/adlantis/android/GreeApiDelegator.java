package jp.adlantis.android;

import android.util.Log;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class GreeApiDelegator
{
  static final String digestClassSpecifier = "net.gree.asdk.core.codec.Digest";
  static final String greeApiClassSpecifier = "net.gree.asdk.api.GreePlatform";
  static final String[] greeClasses;
  
  static
  {
    String[] arrayOfString = new String[2];
    arrayOfString[0] = "net.gree.asdk.api.GreePlatform";
    arrayOfString[1] = "net.gree.asdk.core.codec.Digest";
    greeClasses = arrayOfString;
  }
  
  protected static Class<?> getGreePlatformClass()
    throws ClassNotFoundException
  {
    return Class.forName("net.gree.asdk.api.GreePlatform");
  }
  
  public static String getSha1DigestInString(String paramString)
  {
    try
    {
      Class localClass = Class.forName("net.gree.asdk.core.codec.Digest");
      Class[] arrayOfClass1 = new Class[1];
      arrayOfClass1[0] = String.class;
      Constructor localConstructor = localClass.getConstructor(arrayOfClass1);
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = "SHA-1";
      Object localObject = localConstructor.newInstance(arrayOfObject1);
      Class[] arrayOfClass2 = new Class[1];
      arrayOfClass2[0] = String.class;
      Method localMethod = localClass.getMethod("getDigestInString", arrayOfClass2);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = paramString;
      str = (String)localMethod.invoke(localObject, arrayOfObject2);
      return str;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Log.e("GreeApiDelegator", "getSha1DigestInString exception=" + localException);
        String str = null;
        logProGuardError();
      }
    }
  }
  
  public static String getUserCountry()
  {
    return getUserFieldValue("region");
  }
  
  private static String getUserFieldValue(String paramString)
  {
    try
    {
      Class localClass = getGreePlatformClass();
      Object localObject = localClass.getMethod("getLocalUser", new Class[0]).invoke(localClass, new Object[0]);
      if (localObject != null)
      {
        Field localField = localObject.getClass().getDeclaredField(paramString);
        localField.setAccessible(true);
        str = (String)localField.get(localObject);
        return str;
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        Log.e("GreeApiDelegator", "getUserField('" + paramString + "') exception=" + localException);
        String str = null;
        logProGuardError();
        continue;
        str = null;
      }
    }
  }
  
  public static String getUserId()
  {
    return getUserFieldValue("id");
  }
  
  public static boolean greePlatformAvailable()
  {
    boolean bool = false;
    try
    {
      Class localClass = getGreePlatformClass();
      if (localClass != null) {
        bool = true;
      }
    }
    catch (Exception localException)
    {
      label12:
      break label12;
    }
    return bool;
  }
  
  private static void logProGuardError()
  {
    Log.e("GreeApiDelegator", "If using ProGuard, include the following lines in your proguard.cfg file:");
    for (String str : greeClasses) {
      Log.e("GreeApiDelegator", " -keep public class " + str + " { public static *; }");
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.GreeApiDelegator
 * JD-Core Version:    0.7.0.1
 */