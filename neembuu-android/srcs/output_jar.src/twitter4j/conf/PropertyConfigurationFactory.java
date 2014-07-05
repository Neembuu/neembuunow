package twitter4j.conf;

class PropertyConfigurationFactory
  implements ConfigurationFactory
{
  private static final PropertyConfiguration ROOT_CONFIGURATION = new PropertyConfiguration();
  
  public void dispose() {}
  
  public Configuration getInstance()
  {
    return ROOT_CONFIGURATION;
  }
  
  public Configuration getInstance(String paramString)
  {
    PropertyConfiguration localPropertyConfiguration = new PropertyConfiguration(paramString);
    localPropertyConfiguration.dumpConfiguration();
    return localPropertyConfiguration;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.conf.PropertyConfigurationFactory
 * JD-Core Version:    0.7.0.1
 */