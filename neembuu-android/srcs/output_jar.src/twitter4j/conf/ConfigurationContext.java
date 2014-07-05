package twitter4j.conf;

public final class ConfigurationContext
{
  public static final String CONFIGURATION_IMPL = "twitter4j.configurationFactory";
  public static final String DEFAULT_CONFIGURATION_FACTORY = "twitter4j.conf.PropertyConfigurationFactory";
  private static final ConfigurationFactory factory;
  
  /* Error */
  static
  {
    // Byte code:
    //   0: ldc 8
    //   2: ldc 11
    //   4: invokestatic 29	java/lang/System:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   7: astore 5
    //   9: aload 5
    //   11: astore_1
    //   12: aload_1
    //   13: invokestatic 35	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   16: invokevirtual 39	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   19: checkcast 41	twitter4j/conf/ConfigurationFactory
    //   22: putstatic 43	twitter4j/conf/ConfigurationContext:factory	Ltwitter4j/conf/ConfigurationFactory;
    //   25: return
    //   26: astore_0
    //   27: ldc 11
    //   29: astore_1
    //   30: goto -18 -> 12
    //   33: astore 4
    //   35: new 45	java/lang/AssertionError
    //   38: dup
    //   39: aload 4
    //   41: invokespecial 49	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   44: athrow
    //   45: astore_3
    //   46: new 45	java/lang/AssertionError
    //   49: dup
    //   50: aload_3
    //   51: invokespecial 49	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   54: athrow
    //   55: astore_2
    //   56: new 45	java/lang/AssertionError
    //   59: dup
    //   60: aload_2
    //   61: invokespecial 49	java/lang/AssertionError:<init>	(Ljava/lang/Object;)V
    //   64: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   26	1	0	localSecurityException	java.lang.SecurityException
    //   11	19	1	str1	String
    //   55	6	2	localIllegalAccessException	java.lang.IllegalAccessException
    //   45	6	3	localInstantiationException	java.lang.InstantiationException
    //   33	7	4	localClassNotFoundException	java.lang.ClassNotFoundException
    //   7	3	5	str2	String
    // Exception table:
    //   from	to	target	type
    //   0	9	26	java/lang/SecurityException
    //   12	25	33	java/lang/ClassNotFoundException
    //   12	25	45	java/lang/InstantiationException
    //   12	25	55	java/lang/IllegalAccessException
  }
  
  public static Configuration getInstance()
  {
    return factory.getInstance();
  }
  
  public static Configuration getInstance(String paramString)
  {
    return factory.getInstance(paramString);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.conf.ConfigurationContext
 * JD-Core Version:    0.7.0.1
 */