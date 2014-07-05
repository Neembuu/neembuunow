package twitter4j.internal.json;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import twitter4j.json.DataObjectFactory;

public class DataObjectFactoryUtil
{
  private static final Method CLEAR_THREAD_LOCAL_MAP;
  private static final Method REGISTER_JSON_OBJECT;
  
  static
  {
    Method[] arrayOfMethod = DataObjectFactory.class.getDeclaredMethods();
    Object localObject1 = null;
    Object localObject2 = null;
    int i = arrayOfMethod.length;
    int j = 0;
    if (j < i)
    {
      Method localMethod = arrayOfMethod[j];
      if (localMethod.getName().equals("clearThreadLocalMap"))
      {
        localObject1 = localMethod;
        localObject1.setAccessible(true);
      }
      for (;;)
      {
        j++;
        break;
        if (localMethod.getName().equals("registerJSONObject"))
        {
          localObject2 = localMethod;
          localObject2.setAccessible(true);
        }
      }
    }
    if ((localObject1 == null) || (localObject2 == null)) {
      throw new AssertionError();
    }
    CLEAR_THREAD_LOCAL_MAP = localObject1;
    REGISTER_JSON_OBJECT = localObject2;
  }
  
  private DataObjectFactoryUtil()
  {
    throw new AssertionError("not intended to be instantiated.");
  }
  
  public static void clearThreadLocalMap()
  {
    try
    {
      CLEAR_THREAD_LOCAL_MAP.invoke(null, new Object[0]);
      return;
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
  
  public static <T> T registerJSONObject(T paramT, Object paramObject)
  {
    try
    {
      Method localMethod = REGISTER_JSON_OBJECT;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramT;
      arrayOfObject[1] = paramObject;
      Object localObject = localMethod.invoke(null, arrayOfObject);
      return localObject;
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
 * Qualified Name:     twitter4j.internal.json.DataObjectFactoryUtil
 * JD-Core Version:    0.7.0.1
 */