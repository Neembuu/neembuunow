package twitter4j.internal.json;

import java.io.Serializable;

abstract class EntityIndex
  implements Comparable<EntityIndex>, Serializable
{
  private static final long serialVersionUID = 3864336402689899384L;
  private int end = -1;
  private int start = -1;
  
  public int compareTo(EntityIndex paramEntityIndex)
  {
    long l = this.start - paramEntityIndex.start;
    int i;
    if (l < -2147483648L) {
      i = -2147483648;
    }
    for (;;)
    {
      return i;
      if (l > 2147483647L) {
        i = 2147483647;
      } else {
        i = (int)l;
      }
    }
  }
  
  int getEnd()
  {
    return this.end;
  }
  
  int getStart()
  {
    return this.start;
  }
  
  void setEnd(int paramInt)
  {
    this.end = paramInt;
  }
  
  void setStart(int paramInt)
  {
    this.start = paramInt;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.EntityIndex
 * JD-Core Version:    0.7.0.1
 */