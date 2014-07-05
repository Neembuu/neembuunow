package twitter4j.util;

public final class CharacterUtil
{
  private CharacterUtil()
  {
    throw new AssertionError();
  }
  
  public static int count(String paramString)
  {
    return paramString.length();
  }
  
  public static boolean isExceedingLengthLimitation(String paramString)
  {
    if (count(paramString) > 140) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.util.CharacterUtil
 * JD-Core Version:    0.7.0.1
 */