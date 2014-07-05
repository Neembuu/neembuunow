package twitter4j.internal.http;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public final class HttpClientFactory
{
  private static final Constructor HTTP_CLIENT_CONSTRUCTOR;
  private static final String HTTP_CLIENT_IMPLEMENTATION = "twitter4j.http.httpClient";
  
  /* Error */
  static
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_0
    //   2: ldc 10
    //   4: invokestatic 22	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   7: astore_1
    //   8: aload_1
    //   9: ifnull +12 -> 21
    //   12: aload_1
    //   13: invokestatic 28	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   16: astore 9
    //   18: aload 9
    //   20: astore_0
    //   21: aload_0
    //   22: ifnonnull +13 -> 35
    //   25: ldc 30
    //   27: invokestatic 28	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   30: astore 7
    //   32: aload 7
    //   34: astore_0
    //   35: aload_0
    //   36: ifnonnull +13 -> 49
    //   39: ldc 32
    //   41: invokestatic 28	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   44: astore 5
    //   46: aload 5
    //   48: astore_0
    //   49: iconst_1
    //   50: anewarray 24	java/lang/Class
    //   53: astore_3
    //   54: aload_3
    //   55: iconst_0
    //   56: ldc 34
    //   58: aastore
    //   59: aload_0
    //   60: aload_3
    //   61: invokevirtual 38	java/lang/Class:getConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   64: putstatic 40	twitter4j/internal/http/HttpClientFactory:HTTP_CLIENT_CONSTRUCTOR	Ljava/lang/reflect/Constructor;
    //   67: return
    //   68: astore 4
    //   70: new 42	java/lang/AssertionError
    //   73: dup
    //   74: aload 4
    //   76: invokespecial 46	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   79: athrow
    //   80: astore_2
    //   81: new 42	java/lang/AssertionError
    //   84: dup
    //   85: aload_2
    //   86: invokespecial 46	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   89: athrow
    //   90: astore 8
    //   92: goto -71 -> 21
    //   95: astore 6
    //   97: goto -62 -> 35
    // Local variable table:
    //   start	length	slot	name	signature
    //   1	59	0	localObject	Object
    //   7	6	1	str	String
    //   80	6	2	localNoSuchMethodException	java.lang.NoSuchMethodException
    //   53	8	3	arrayOfClass	java.lang.Class[]
    //   68	7	4	localClassNotFoundException1	java.lang.ClassNotFoundException
    //   44	3	5	localClass1	java.lang.Class
    //   95	1	6	localClassNotFoundException2	java.lang.ClassNotFoundException
    //   30	3	7	localClass2	java.lang.Class
    //   90	1	8	localClassNotFoundException3	java.lang.ClassNotFoundException
    //   16	3	9	localClass3	java.lang.Class
    // Exception table:
    //   from	to	target	type
    //   39	46	68	java/lang/ClassNotFoundException
    //   49	67	80	java/lang/NoSuchMethodException
    //   12	18	90	java/lang/ClassNotFoundException
    //   25	32	95	java/lang/ClassNotFoundException
  }
  
  public static HttpClient getInstance(HttpClientConfiguration paramHttpClientConfiguration)
  {
    try
    {
      Constructor localConstructor = HTTP_CLIENT_CONSTRUCTOR;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramHttpClientConfiguration;
      HttpClient localHttpClient = (HttpClient)localConstructor.newInstance(arrayOfObject);
      return localHttpClient;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new AssertionError(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new AssertionError(localInvocationTargetException);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.http.HttpClientFactory
 * JD-Core Version:    0.7.0.1
 */