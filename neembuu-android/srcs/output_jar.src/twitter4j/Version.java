package twitter4j;

import java.io.PrintStream;

public final class Version
{
  private static final String TITLE = "Twitter4J";
  private static final String VERSION = "3.0.5";
  
  private Version()
  {
    throw new AssertionError();
  }
  
  public static String getVersion()
  {
    return "3.0.5";
  }
  
  public static void main(String[] paramArrayOfString)
  {
    System.out.println("Twitter4J 3.0.5");
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.Version
 * JD-Core Version:    0.7.0.1
 */