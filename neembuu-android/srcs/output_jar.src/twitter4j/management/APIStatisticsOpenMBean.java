package twitter4j.management;

import java.util.Iterator;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenMBeanAttributeInfoSupport;
import javax.management.openmbean.OpenMBeanConstructorInfoSupport;
import javax.management.openmbean.OpenMBeanInfoSupport;
import javax.management.openmbean.OpenMBeanOperationInfoSupport;
import javax.management.openmbean.OpenMBeanParameterInfoSupport;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import javax.management.openmbean.TabularDataSupport;
import javax.management.openmbean.TabularType;

public class APIStatisticsOpenMBean
  implements DynamicMBean
{
  private static final String[] ITEM_DESCRIPTIONS;
  private static final String[] ITEM_NAMES;
  private static final OpenType[] ITEM_TYPES;
  private final APIStatisticsMBean API_STATISTICS;
  private final TabularType API_STATISTICS_TYPE;
  private final CompositeType METHOD_STATS_TYPE;
  
  static
  {
    String[] arrayOfString1 = new String[5];
    arrayOfString1[0] = "methodName";
    arrayOfString1[1] = "callCount";
    arrayOfString1[2] = "errorCount";
    arrayOfString1[3] = "totalTime";
    arrayOfString1[4] = "avgTime";
    ITEM_NAMES = arrayOfString1;
    OpenType[] arrayOfOpenType = new OpenType[5];
    arrayOfOpenType[0] = SimpleType.STRING;
    arrayOfOpenType[1] = SimpleType.LONG;
    arrayOfOpenType[2] = SimpleType.LONG;
    arrayOfOpenType[3] = SimpleType.LONG;
    arrayOfOpenType[4] = SimpleType.LONG;
    ITEM_TYPES = arrayOfOpenType;
    String[] arrayOfString2 = new String[5];
    arrayOfString2[0] = "The method name";
    arrayOfString2[1] = "The number of times this method has been called";
    arrayOfString2[2] = "The number of calls that failed";
    arrayOfString2[3] = "The total amount of time spent invoking this method in milliseconds";
    arrayOfString2[4] = "The average amount of time spent invoking this method in milliseconds";
    ITEM_DESCRIPTIONS = arrayOfString2;
  }
  
  public APIStatisticsOpenMBean(APIStatistics paramAPIStatistics)
  {
    this.API_STATISTICS = paramAPIStatistics;
    try
    {
      this.METHOD_STATS_TYPE = new CompositeType("method statistics", "method statistics", ITEM_NAMES, ITEM_DESCRIPTIONS, ITEM_TYPES);
      String[] arrayOfString = new String[1];
      arrayOfString[0] = "methodName";
      this.API_STATISTICS_TYPE = new TabularType("API statistics", "list of methods", this.METHOD_STATS_TYPE, arrayOfString);
      return;
    }
    catch (OpenDataException localOpenDataException)
    {
      throw new RuntimeException(localOpenDataException);
    }
  }
  
  public Object getAttribute(String paramString)
    throws AttributeNotFoundException, MBeanException, ReflectionException
  {
    Object localObject;
    if (paramString.equals("statisticsTable")) {
      localObject = getStatistics();
    }
    for (;;)
    {
      return localObject;
      if (paramString.equals("callCount"))
      {
        localObject = Long.valueOf(this.API_STATISTICS.getCallCount());
      }
      else if (paramString.equals("errorCount"))
      {
        localObject = Long.valueOf(this.API_STATISTICS.getErrorCount());
      }
      else if (paramString.equals("totalTime"))
      {
        localObject = Long.valueOf(this.API_STATISTICS.getTotalTime());
      }
      else
      {
        if (!paramString.equals("averageTime")) {
          break;
        }
        localObject = Long.valueOf(this.API_STATISTICS.getAverageTime());
      }
    }
    throw new AttributeNotFoundException("Cannot find " + paramString + " attribute ");
  }
  
  public AttributeList getAttributes(String[] paramArrayOfString)
  {
    AttributeList localAttributeList = new AttributeList();
    if (paramArrayOfString.length == 0) {
      return localAttributeList;
    }
    int i = 0;
    while (i < paramArrayOfString.length) {
      try
      {
        Object localObject = getAttribute(paramArrayOfString[i]);
        localAttributeList.add(new Attribute(paramArrayOfString[i], localObject));
        i++;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          localException.printStackTrace();
        }
      }
    }
  }
  
  public MBeanInfo getMBeanInfo()
  {
    OpenMBeanAttributeInfoSupport[] arrayOfOpenMBeanAttributeInfoSupport = new OpenMBeanAttributeInfoSupport[5];
    OpenMBeanConstructorInfoSupport[] arrayOfOpenMBeanConstructorInfoSupport = new OpenMBeanConstructorInfoSupport[1];
    OpenMBeanOperationInfoSupport[] arrayOfOpenMBeanOperationInfoSupport = new OpenMBeanOperationInfoSupport[1];
    MBeanNotificationInfo[] arrayOfMBeanNotificationInfo = new MBeanNotificationInfo[0];
    int i = 0 + 1;
    arrayOfOpenMBeanAttributeInfoSupport[0] = new OpenMBeanAttributeInfoSupport("callCount", "Total number of API calls", SimpleType.LONG, true, false, false);
    int j = i + 1;
    arrayOfOpenMBeanAttributeInfoSupport[i] = new OpenMBeanAttributeInfoSupport("errorCount", "The number of failed API calls", SimpleType.LONG, true, false, false);
    int k = j + 1;
    arrayOfOpenMBeanAttributeInfoSupport[j] = new OpenMBeanAttributeInfoSupport("averageTime", "Average time spent invoking any API method", SimpleType.LONG, true, false, false);
    int m = k + 1;
    arrayOfOpenMBeanAttributeInfoSupport[k] = new OpenMBeanAttributeInfoSupport("totalTime", "Average time spent invoking any API method", SimpleType.LONG, true, false, false);
    (m + 1);
    arrayOfOpenMBeanAttributeInfoSupport[m] = new OpenMBeanAttributeInfoSupport("statisticsTable", "Table of statisics for all API methods", this.API_STATISTICS_TYPE, true, false, false);
    arrayOfOpenMBeanConstructorInfoSupport[0] = new OpenMBeanConstructorInfoSupport("APIStatisticsOpenMBean", "Constructs an APIStatisticsOpenMBean instance", new OpenMBeanParameterInfoSupport[0]);
    arrayOfOpenMBeanOperationInfoSupport[0] = new OpenMBeanOperationInfoSupport("reset", "reset the statistics", new OpenMBeanParameterInfoSupport[0], SimpleType.VOID, 0);
    return new OpenMBeanInfoSupport(getClass().getName(), "API Statistics Open MBean", arrayOfOpenMBeanAttributeInfoSupport, arrayOfOpenMBeanConstructorInfoSupport, arrayOfOpenMBeanOperationInfoSupport, arrayOfMBeanNotificationInfo);
  }
  
  /**
   * @deprecated
   */
  public TabularDataSupport getStatistics()
  {
    TabularDataSupport localTabularDataSupport;
    try
    {
      localTabularDataSupport = new TabularDataSupport(this.API_STATISTICS_TYPE);
      Iterator localIterator = this.API_STATISTICS.getInvocationStatistics().iterator();
      for (;;)
      {
        if (localIterator.hasNext())
        {
          InvocationStatistics localInvocationStatistics = (InvocationStatistics)localIterator.next();
          Object[] arrayOfObject = new Object[5];
          arrayOfObject[0] = localInvocationStatistics.getName();
          arrayOfObject[1] = Long.valueOf(localInvocationStatistics.getCallCount());
          arrayOfObject[2] = Long.valueOf(localInvocationStatistics.getErrorCount());
          arrayOfObject[3] = Long.valueOf(localInvocationStatistics.getTotalTime());
          arrayOfObject[4] = Long.valueOf(localInvocationStatistics.getAverageTime());
          try
          {
            localTabularDataSupport.put(new CompositeDataSupport(this.METHOD_STATS_TYPE, ITEM_NAMES, arrayOfObject));
          }
          catch (OpenDataException localOpenDataException)
          {
            throw new RuntimeException(localOpenDataException);
          }
        }
      }
    }
    finally {}
    return localTabularDataSupport;
  }
  
  public Object invoke(String paramString, Object[] paramArrayOfObject, String[] paramArrayOfString)
    throws MBeanException, ReflectionException
  {
    if (paramString.equals("reset"))
    {
      reset();
      return "Statistics reset";
    }
    throw new ReflectionException(new NoSuchMethodException(paramString), "Cannot find the operation " + paramString);
  }
  
  public void reset()
  {
    this.API_STATISTICS.reset();
  }
  
  public void setAttribute(Attribute paramAttribute)
    throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException
  {
    throw new AttributeNotFoundException("No attributes can be set in this MBean");
  }
  
  public AttributeList setAttributes(AttributeList paramAttributeList)
  {
    return new AttributeList();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.management.APIStatisticsOpenMBean
 * JD-Core Version:    0.7.0.1
 */