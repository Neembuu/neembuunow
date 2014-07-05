package twitter4j.conf;

public abstract interface ConfigurationFactory
{
  public abstract void dispose();
  
  public abstract Configuration getInstance();
  
  public abstract Configuration getInstance(String paramString);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.conf.ConfigurationFactory
 * JD-Core Version:    0.7.0.1
 */