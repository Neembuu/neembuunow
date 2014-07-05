package twitter4j.internal.async;

import java.util.LinkedList;
import java.util.List;
import twitter4j.conf.Configuration;

final class DispatcherImpl
  implements Dispatcher
{
  private boolean active = true;
  private final List<Runnable> q = new LinkedList();
  private ExecuteThread[] threads;
  final Object ticket = new Object();
  
  public DispatcherImpl(Configuration paramConfiguration)
  {
    this.threads = new ExecuteThread[paramConfiguration.getAsyncNumThreads()];
    for (int i = 0; i < this.threads.length; i++)
    {
      this.threads[i] = new ExecuteThread("Twitter4J Async Dispatcher", this, i);
      this.threads[i].setDaemon(true);
      this.threads[i].start();
    }
    Runtime.getRuntime().addShutdownHook(new Thread()
    {
      public void run()
      {
        if (DispatcherImpl.this.active) {
          DispatcherImpl.this.shutdown();
        }
      }
    });
  }
  
  /* Error */
  /**
   * @deprecated
   */
  public void invokeLater(Runnable paramRunnable)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 27	twitter4j/internal/async/DispatcherImpl:q	Ljava/util/List;
    //   6: astore_3
    //   7: aload_3
    //   8: monitorenter
    //   9: aload_0
    //   10: getfield 27	twitter4j/internal/async/DispatcherImpl:q	Ljava/util/List;
    //   13: aload_1
    //   14: invokeinterface 76 2 0
    //   19: pop
    //   20: aload_3
    //   21: monitorexit
    //   22: aload_0
    //   23: getfield 29	twitter4j/internal/async/DispatcherImpl:ticket	Ljava/lang/Object;
    //   26: astore 6
    //   28: aload 6
    //   30: monitorenter
    //   31: aload_0
    //   32: getfield 29	twitter4j/internal/async/DispatcherImpl:ticket	Ljava/lang/Object;
    //   35: invokevirtual 79	java/lang/Object:notify	()V
    //   38: aload 6
    //   40: monitorexit
    //   41: aload_0
    //   42: monitorexit
    //   43: return
    //   44: astore 4
    //   46: aload_3
    //   47: monitorexit
    //   48: aload 4
    //   50: athrow
    //   51: astore_2
    //   52: aload_0
    //   53: monitorexit
    //   54: aload_2
    //   55: athrow
    //   56: astore 7
    //   58: aload 6
    //   60: monitorexit
    //   61: aload 7
    //   63: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	64	0	this	DispatcherImpl
    //   0	64	1	paramRunnable	Runnable
    //   51	4	2	localObject1	Object
    //   44	5	4	localObject2	Object
    //   56	6	7	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   9	22	44	finally
    //   46	48	44	finally
    //   2	9	51	finally
    //   22	31	51	finally
    //   48	51	51	finally
    //   61	64	51	finally
    //   31	41	56	finally
    //   58	61	56	finally
  }
  
  public Runnable poll()
  {
    Runnable localRunnable;
    if (this.active) {
      synchronized (this.q)
      {
        if (this.q.size() > 0)
        {
          localRunnable = (Runnable)this.q.remove(0);
          if (localRunnable != null) {
            return localRunnable;
          }
        }
      }
    }
    try
    {
      label67:
      synchronized (this.ticket)
      {
        this.ticket.wait();
      }
      localObject1 = finally;
      throw localObject1;
      localRunnable = null;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label67;
    }
    return localRunnable;
  }
  
  /* Error */
  /**
   * @deprecated
   */
  public void shutdown()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 31	twitter4j/internal/async/DispatcherImpl:active	Z
    //   6: ifeq +57 -> 63
    //   9: aload_0
    //   10: iconst_0
    //   11: putfield 31	twitter4j/internal/async/DispatcherImpl:active	Z
    //   14: aload_0
    //   15: getfield 41	twitter4j/internal/async/DispatcherImpl:threads	[Ltwitter4j/internal/async/ExecuteThread;
    //   18: astore_2
    //   19: aload_2
    //   20: arraylength
    //   21: istore_3
    //   22: iconst_0
    //   23: istore 4
    //   25: iload 4
    //   27: iload_3
    //   28: if_icmpge +16 -> 44
    //   31: aload_2
    //   32: iload 4
    //   34: aaload
    //   35: invokevirtual 98	twitter4j/internal/async/ExecuteThread:shutdown	()V
    //   38: iinc 4 1
    //   41: goto -16 -> 25
    //   44: aload_0
    //   45: getfield 29	twitter4j/internal/async/DispatcherImpl:ticket	Ljava/lang/Object;
    //   48: astore 5
    //   50: aload 5
    //   52: monitorenter
    //   53: aload_0
    //   54: getfield 29	twitter4j/internal/async/DispatcherImpl:ticket	Ljava/lang/Object;
    //   57: invokevirtual 79	java/lang/Object:notify	()V
    //   60: aload 5
    //   62: monitorexit
    //   63: aload_0
    //   64: monitorexit
    //   65: return
    //   66: astore 6
    //   68: aload 5
    //   70: monitorexit
    //   71: aload 6
    //   73: athrow
    //   74: astore_1
    //   75: aload_0
    //   76: monitorexit
    //   77: aload_1
    //   78: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	79	0	this	DispatcherImpl
    //   74	4	1	localObject1	Object
    //   18	14	2	arrayOfExecuteThread	ExecuteThread[]
    //   21	8	3	i	int
    //   23	16	4	j	int
    //   66	6	6	localObject3	Object
    // Exception table:
    //   from	to	target	type
    //   53	63	66	finally
    //   68	71	66	finally
    //   2	53	74	finally
    //   71	74	74	finally
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.async.DispatcherImpl
 * JD-Core Version:    0.7.0.1
 */