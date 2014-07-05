package twitter4j.management;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class APIStatistics
  implements APIStatisticsMBean
{
  private final InvocationStatisticsCalculator API_STATS_CALCULATOR;
  private final int HISTORY_SIZE;
  private final Map<String, InvocationStatisticsCalculator> METHOD_STATS_MAP;
  
  public APIStatistics(int paramInt)
  {
    this.API_STATS_CALCULATOR = new InvocationStatisticsCalculator("API", paramInt);
    this.METHOD_STATS_MAP = new HashMap(100);
    this.HISTORY_SIZE = paramInt;
  }
  
  /**
   * @deprecated
   */
  private InvocationStatisticsCalculator getMethodStatistics(String paramString)
  {
    try
    {
      InvocationStatisticsCalculator localInvocationStatisticsCalculator = (InvocationStatisticsCalculator)this.METHOD_STATS_MAP.get(paramString);
      if (localInvocationStatisticsCalculator == null)
      {
        localInvocationStatisticsCalculator = new InvocationStatisticsCalculator(paramString, this.HISTORY_SIZE);
        this.METHOD_STATS_MAP.put(paramString, localInvocationStatisticsCalculator);
      }
      return localInvocationStatisticsCalculator;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public long getAverageTime()
  {
    return this.API_STATS_CALCULATOR.getAverageTime();
  }
  
  public long getCallCount()
  {
    return this.API_STATS_CALCULATOR.getCallCount();
  }
  
  public long getErrorCount()
  {
    return this.API_STATS_CALCULATOR.getErrorCount();
  }
  
  /**
   * @deprecated
   */
  public Iterable<? extends InvocationStatistics> getInvocationStatistics()
  {
    try
    {
      Collection localCollection = this.METHOD_STATS_MAP.values();
      return localCollection;
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
  public Map<String, String> getMethodLevelSummariesAsString()
  {
    try
    {
      HashMap localHashMap = new HashMap();
      Iterator localIterator = this.METHOD_STATS_MAP.values().iterator();
      if (localIterator.hasNext())
      {
        InvocationStatisticsCalculator localInvocationStatisticsCalculator = (InvocationStatisticsCalculator)localIterator.next();
        localHashMap.put(localInvocationStatisticsCalculator.getName(), localInvocationStatisticsCalculator.toString());
      }
      return localHashMap;
    }
    finally {}
  }
  
  /**
   * @deprecated
   */
  public String getMethodLevelSummary(String paramString)
  {
    try
    {
      String str = ((InvocationStatisticsCalculator)this.METHOD_STATS_MAP.get(paramString)).toString();
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public String getName()
  {
    return this.API_STATS_CALCULATOR.getName();
  }
  
  public long getTotalTime()
  {
    return this.API_STATS_CALCULATOR.getTotalTime();
  }
  
  /**
   * @deprecated
   */
  public void methodCalled(String paramString, long paramLong, boolean paramBoolean)
  {
    try
    {
      getMethodStatistics(paramString).increment(paramLong, paramBoolean);
      this.API_STATS_CALCULATOR.increment(paramLong, paramBoolean);
      return;
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
  public void reset()
  {
    try
    {
      this.API_STATS_CALCULATOR.reset();
      this.METHOD_STATS_MAP.clear();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.management.APIStatistics
 * JD-Core Version:    0.7.0.1
 */