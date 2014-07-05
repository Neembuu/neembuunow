package twitter4j.management;

public class InvocationStatisticsCalculator
  implements InvocationStatistics
{
  private long callCount;
  private long errorCount;
  private int index;
  private String name;
  private long[] times;
  private long totalTime;
  
  public InvocationStatisticsCalculator(String paramString, int paramInt)
  {
    this.name = paramString;
    this.times = new long[paramInt];
  }
  
  /* Error */
  /**
   * @deprecated
   */
  public long getAverageTime()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 29	twitter4j/management/InvocationStatisticsCalculator:callCount	J
    //   6: l2i
    //   7: invokestatic 35	java/lang/Math:abs	(I)I
    //   10: aload_0
    //   11: getfield 25	twitter4j/management/InvocationStatisticsCalculator:times	[J
    //   14: arraylength
    //   15: invokestatic 39	java/lang/Math:min	(II)I
    //   18: istore_2
    //   19: iload_2
    //   20: ifne +11 -> 31
    //   23: lconst_0
    //   24: lstore 6
    //   26: aload_0
    //   27: monitorexit
    //   28: lload 6
    //   30: lreturn
    //   31: lconst_0
    //   32: lstore_3
    //   33: iconst_0
    //   34: istore 5
    //   36: iload 5
    //   38: iload_2
    //   39: if_icmpge +19 -> 58
    //   42: lload_3
    //   43: aload_0
    //   44: getfield 25	twitter4j/management/InvocationStatisticsCalculator:times	[J
    //   47: iload 5
    //   49: laload
    //   50: ladd
    //   51: lstore_3
    //   52: iinc 5 1
    //   55: goto -19 -> 36
    //   58: lload_3
    //   59: iload_2
    //   60: i2l
    //   61: ldiv
    //   62: lstore 6
    //   64: goto -38 -> 26
    //   67: astore_1
    //   68: aload_0
    //   69: monitorexit
    //   70: aload_1
    //   71: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	72	0	this	InvocationStatisticsCalculator
    //   67	4	1	localObject	Object
    //   18	42	2	i	int
    //   32	27	3	l1	long
    //   34	19	5	j	int
    //   24	39	6	l2	long
    // Exception table:
    //   from	to	target	type
    //   2	19	67	finally
    //   42	64	67	finally
  }
  
  public long getCallCount()
  {
    return this.callCount;
  }
  
  public long getErrorCount()
  {
    return this.errorCount;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public long getTotalTime()
  {
    return this.totalTime;
  }
  
  void increment(long paramLong, boolean paramBoolean)
  {
    long l1 = 1L;
    this.callCount = (l1 + this.callCount);
    long l2 = this.errorCount;
    if (paramBoolean) {
      l1 = 0L;
    }
    this.errorCount = (l1 + l2);
    this.totalTime = (paramLong + this.totalTime);
    this.times[this.index] = paramLong;
    int i = 1 + this.index;
    this.index = i;
    if (i >= this.times.length) {
      this.index = 0;
    }
  }
  
  /**
   * @deprecated
   */
  public void reset()
  {
    try
    {
      this.callCount = 0L;
      this.errorCount = 0L;
      this.totalTime = 0L;
      this.times = new long[this.times.length];
      this.index = 0;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("calls=").append(getCallCount()).append(",").append("errors=").append(getErrorCount()).append(",").append("totalTime=").append(getTotalTime()).append(",").append("avgTime=").append(getAverageTime());
    return localStringBuilder.toString();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.management.InvocationStatisticsCalculator
 * JD-Core Version:    0.7.0.1
 */