package twitter4j;

import java.lang.management.ManagementFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationContext;
import twitter4j.internal.logging.Logger;
import twitter4j.management.APIStatistics;
import twitter4j.management.APIStatisticsMBean;
import twitter4j.management.APIStatisticsOpenMBean;

public class TwitterAPIMonitor
{
  private static final TwitterAPIMonitor SINGLETON;
  private static final APIStatistics STATISTICS;
  private static final Logger logger = Logger.getLogger(TwitterAPIMonitor.class);
  private static final Pattern pattern = Pattern.compile("https?:\\/\\/[^\\/]+\\/[0-9.]*\\/([a-zA-Z_\\.]*).*");
  
  static
  {
    SINGLETON = new TwitterAPIMonitor();
    STATISTICS = new APIStatistics(100);
    int i = 0;
    try
    {
      String str = System.getProperty("java.specification.version");
      if (str != null) {
        if (1.5D <= Double.parseDouble(str)) {
          break label231;
        }
      }
      label231:
      for (i = 1;; i = 0)
      {
        if (ConfigurationContext.getInstance().isDalvik()) {
          System.setProperty("http.keepAlive", "false");
        }
        try
        {
          MBeanServer localMBeanServer = ManagementFactory.getPlatformMBeanServer();
          if (i != 0)
          {
            ObjectName localObjectName1 = new ObjectName("twitter4j.mbean:type=APIStatistics");
            localMBeanServer.registerMBean(STATISTICS, localObjectName1);
          }
          else
          {
            ObjectName localObjectName2 = new ObjectName("twitter4j.mbean:type=APIStatisticsOpenMBean");
            localMBeanServer.registerMBean(new APIStatisticsOpenMBean(STATISTICS), localObjectName2);
          }
        }
        catch (InstanceAlreadyExistsException localInstanceAlreadyExistsException)
        {
          localInstanceAlreadyExistsException.printStackTrace();
          logger.error(localInstanceAlreadyExistsException.getMessage());
        }
        catch (MBeanRegistrationException localMBeanRegistrationException)
        {
          localMBeanRegistrationException.printStackTrace();
          logger.error(localMBeanRegistrationException.getMessage());
        }
        catch (NotCompliantMBeanException localNotCompliantMBeanException)
        {
          localNotCompliantMBeanException.printStackTrace();
          logger.error(localNotCompliantMBeanException.getMessage());
        }
        catch (MalformedObjectNameException localMalformedObjectNameException)
        {
          localMalformedObjectNameException.printStackTrace();
          logger.error(localMalformedObjectNameException.getMessage());
        }
        return;
      }
    }
    catch (SecurityException localSecurityException)
    {
      for (;;)
      {
        i = 1;
      }
    }
  }
  
  public static TwitterAPIMonitor getInstance()
  {
    return SINGLETON;
  }
  
  public APIStatisticsMBean getStatistics()
  {
    return STATISTICS;
  }
  
  void methodCalled(String paramString, long paramLong, boolean paramBoolean)
  {
    Matcher localMatcher = pattern.matcher(paramString);
    if ((localMatcher.matches()) && (localMatcher.groupCount() > 0))
    {
      String str = localMatcher.group(1);
      STATISTICS.methodCalled(str, paramLong, paramBoolean);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.TwitterAPIMonitor
 * JD-Core Version:    0.7.0.1
 */