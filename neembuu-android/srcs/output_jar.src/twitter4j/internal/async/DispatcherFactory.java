package twitter4j.internal.async;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;

public final class DispatcherFactory
{
  private Configuration conf;
  private String dispatcherImpl;
  
  public DispatcherFactory()
  {
    this(ConfigurationContext.getInstance());
  }
  
  public DispatcherFactory(Configuration paramConfiguration)
  {
    this.dispatcherImpl = paramConfiguration.getDispatcherImpl();
    this.conf = paramConfiguration;
  }
  
  public Dispatcher getInstance()
  {
    try
    {
      Class localClass = Class.forName(this.dispatcherImpl);
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = Configuration.class;
      Constructor localConstructor = localClass.getConstructor(arrayOfClass);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.conf;
      Dispatcher localDispatcher = (Dispatcher)localConstructor.newInstance(arrayOfObject);
      return localDispatcher;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new AssertionError(localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new AssertionError(localIllegalAccessException);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new AssertionError(localClassNotFoundException);
    }
    catch (ClassCastException localClassCastException)
    {
      throw new AssertionError(localClassCastException);
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      throw new AssertionError(localNoSuchMethodException);
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      throw new AssertionError(localInvocationTargetException);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.async.DispatcherFactory
 * JD-Core Version:    0.7.0.1
 */