package android.support.v4.util;

public class DebugUtils
{
  public static void buildShortClassTag(Object paramObject, StringBuilder paramStringBuilder)
  {
    if (paramObject == null) {
      paramStringBuilder.append("null");
    }
    for (;;)
    {
      return;
      String str = paramObject.getClass().getSimpleName();
      if ((str == null) || (str.length() <= 0))
      {
        str = paramObject.getClass().getName();
        int i = str.lastIndexOf('.');
        if (i > 0) {
          str = str.substring(i + 1);
        }
      }
      paramStringBuilder.append(str);
      paramStringBuilder.append('{');
      paramStringBuilder.append(Integer.toHexString(System.identityHashCode(paramObject)));
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     android.support.v4.util.DebugUtils
 * JD-Core Version:    0.7.0.1
 */