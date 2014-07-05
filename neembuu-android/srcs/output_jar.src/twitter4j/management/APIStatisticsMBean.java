package twitter4j.management;

import java.util.Map;

public abstract interface APIStatisticsMBean
  extends InvocationStatistics
{
  public abstract Iterable<? extends InvocationStatistics> getInvocationStatistics();
  
  public abstract Map<String, String> getMethodLevelSummariesAsString();
  
  public abstract String getMethodLevelSummary(String paramString);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.management.APIStatisticsMBean
 * JD-Core Version:    0.7.0.1
 */