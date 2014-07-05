package twitter4j;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.AuthorizationFactory;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;

public final class TwitterFactory
  implements Serializable
{
  static final Authorization DEFAULT_AUTHORIZATION = AuthorizationFactory.getInstance(ConfigurationContext.getInstance());
  private static final Twitter SINGLETON;
  private static final Constructor<Twitter> TWITTER_CONSTRUCTOR;
  private static final long serialVersionUID = 5193900138477709155L;
  private final Configuration conf;
  
  /* Error */
  static
  {
    // Byte code:
    //   0: invokestatic 37	twitter4j/conf/ConfigurationContext:getInstance	()Ltwitter4j/conf/Configuration;
    //   3: invokestatic 42	twitter4j/auth/AuthorizationFactory:getInstance	(Ltwitter4j/conf/Configuration;)Ltwitter4j/auth/Authorization;
    //   6: putstatic 44	twitter4j/TwitterFactory:DEFAULT_AUTHORIZATION	Ltwitter4j/auth/Authorization;
    //   9: aconst_null
    //   10: astore_0
    //   11: invokestatic 37	twitter4j/conf/ConfigurationContext:getInstance	()Ltwitter4j/conf/Configuration;
    //   14: invokeinterface 50 1 0
    //   19: ifeq +12 -> 31
    //   22: ldc 52
    //   24: invokestatic 58	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   27: pop
    //   28: ldc 52
    //   30: astore_0
    //   31: aload_0
    //   32: ifnonnull +6 -> 38
    //   35: ldc 60
    //   37: astore_0
    //   38: aload_0
    //   39: invokestatic 58	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   42: astore_3
    //   43: iconst_2
    //   44: anewarray 54	java/lang/Class
    //   47: astore 4
    //   49: aload 4
    //   51: iconst_0
    //   52: ldc 46
    //   54: aastore
    //   55: aload 4
    //   57: iconst_1
    //   58: ldc 62
    //   60: aastore
    //   61: aload_3
    //   62: aload 4
    //   64: invokevirtual 66	java/lang/Class:getDeclaredConstructor	([Ljava/lang/Class;)Ljava/lang/reflect/Constructor;
    //   67: astore 5
    //   69: aload 5
    //   71: putstatic 68	twitter4j/TwitterFactory:TWITTER_CONSTRUCTOR	Ljava/lang/reflect/Constructor;
    //   74: getstatic 68	twitter4j/TwitterFactory:TWITTER_CONSTRUCTOR	Ljava/lang/reflect/Constructor;
    //   77: astore 9
    //   79: iconst_2
    //   80: anewarray 4	java/lang/Object
    //   83: astore 10
    //   85: aload 10
    //   87: iconst_0
    //   88: invokestatic 37	twitter4j/conf/ConfigurationContext:getInstance	()Ltwitter4j/conf/Configuration;
    //   91: aastore
    //   92: aload 10
    //   94: iconst_1
    //   95: getstatic 44	twitter4j/TwitterFactory:DEFAULT_AUTHORIZATION	Ltwitter4j/auth/Authorization;
    //   98: aastore
    //   99: aload 9
    //   101: aload 10
    //   103: invokevirtual 74	java/lang/reflect/Constructor:newInstance	([Ljava/lang/Object;)Ljava/lang/Object;
    //   106: checkcast 76	twitter4j/Twitter
    //   109: putstatic 78	twitter4j/TwitterFactory:SINGLETON	Ltwitter4j/Twitter;
    //   112: return
    //   113: astore_2
    //   114: new 80	java/lang/AssertionError
    //   117: dup
    //   118: aload_2
    //   119: invokespecial 84	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   122: athrow
    //   123: astore_1
    //   124: new 80	java/lang/AssertionError
    //   127: dup
    //   128: aload_1
    //   129: invokespecial 84	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   132: athrow
    //   133: astore 8
    //   135: new 80	java/lang/AssertionError
    //   138: dup
    //   139: aload 8
    //   141: invokespecial 84	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   144: athrow
    //   145: astore 7
    //   147: new 80	java/lang/AssertionError
    //   150: dup
    //   151: aload 7
    //   153: invokespecial 84	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   156: athrow
    //   157: astore 6
    //   159: new 80	java/lang/AssertionError
    //   162: dup
    //   163: aload 6
    //   165: invokespecial 84	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   168: athrow
    //   169: astore 11
    //   171: goto -140 -> 31
    // Local variable table:
    //   start	length	slot	name	signature
    //   10	29	0	str	String
    //   123	6	1	localClassNotFoundException1	java.lang.ClassNotFoundException
    //   113	6	2	localNoSuchMethodException	java.lang.NoSuchMethodException
    //   42	20	3	localClass	java.lang.Class
    //   47	16	4	arrayOfClass	java.lang.Class[]
    //   67	3	5	localConstructor1	Constructor
    //   157	7	6	localInvocationTargetException	InvocationTargetException
    //   145	7	7	localIllegalAccessException	IllegalAccessException
    //   133	7	8	localInstantiationException	InstantiationException
    //   77	23	9	localConstructor2	Constructor
    //   83	19	10	arrayOfObject	Object[]
    //   169	1	11	localClassNotFoundException2	java.lang.ClassNotFoundException
    // Exception table:
    //   from	to	target	type
    //   38	69	113	java/lang/NoSuchMethodException
    //   38	69	123	java/lang/ClassNotFoundException
    //   74	112	133	java/lang/InstantiationException
    //   74	112	145	java/lang/IllegalAccessException
    //   74	112	157	java/lang/reflect/InvocationTargetException
    //   22	31	169	java/lang/ClassNotFoundException
  }
  
  public TwitterFactory()
  {
    this(ConfigurationContext.getInstance());
  }
  
  public TwitterFactory(String paramString)
  {
    this(ConfigurationContext.getInstance(paramString));
  }
  
  public TwitterFactory(Configuration paramConfiguration)
  {
    if (paramConfiguration == null) {
      throw new NullPointerException("configuration cannot be null");
    }
    this.conf = paramConfiguration;
  }
  
  public static Twitter getSingleton()
  {
    return SINGLETON;
  }
  
  public Twitter getInstance()
  {
    return getInstance(AuthorizationFactory.getInstance(this.conf));
  }
  
  public Twitter getInstance(AccessToken paramAccessToken)
  {
    String str1 = this.conf.getOAuthConsumerKey();
    String str2 = this.conf.getOAuthConsumerSecret();
    if ((str1 == null) && (str2 == null)) {
      throw new IllegalStateException("Consumer key and Consumer secret not supplied.");
    }
    OAuthAuthorization localOAuthAuthorization = new OAuthAuthorization(this.conf);
    localOAuthAuthorization.setOAuthAccessToken(paramAccessToken);
    return getInstance(localOAuthAuthorization);
  }
  
  public Twitter getInstance(Authorization paramAuthorization)
  {
    try
    {
      Constructor localConstructor = TWITTER_CONSTRUCTOR;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.conf;
      arrayOfObject[1] = paramAuthorization;
      Twitter localTwitter = (Twitter)localConstructor.newInstance(arrayOfObject);
      return localTwitter;
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
 * Qualified Name:     twitter4j.TwitterFactory
 * JD-Core Version:    0.7.0.1
 */