package jp.adlantis.android.model;

import java.util.ArrayList;

public class SimpleObservable<T>
  implements EasyObservable<T>
{
  private final ArrayList<OnChangeListener<T>> listeners = new ArrayList();
  
  public void addListener(OnChangeListener<T> paramOnChangeListener)
  {
    synchronized (this.listeners)
    {
      this.listeners.add(paramOnChangeListener);
      return;
    }
  }
  
  /* Error */
  protected void notifyListeners(T paramT)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 19	jp/adlantis/android/model/SimpleObservable:listeners	Ljava/util/ArrayList;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 19	jp/adlantis/android/model/SimpleObservable:listeners	Ljava/util/ArrayList;
    //   11: invokevirtual 31	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   14: astore 4
    //   16: aload 4
    //   18: invokeinterface 37 1 0
    //   23: ifeq +27 -> 50
    //   26: aload 4
    //   28: invokeinterface 41 1 0
    //   33: checkcast 43	jp/adlantis/android/model/OnChangeListener
    //   36: aload_1
    //   37: invokeinterface 46 2 0
    //   42: goto -26 -> 16
    //   45: astore_3
    //   46: aload_2
    //   47: monitorexit
    //   48: aload_3
    //   49: athrow
    //   50: aload_2
    //   51: monitorexit
    //   52: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	53	0	this	SimpleObservable
    //   0	53	1	paramT	T
    //   4	47	2	localArrayList	ArrayList
    //   45	4	3	localObject	Object
    //   14	13	4	localIterator	java.util.Iterator
    // Exception table:
    //   from	to	target	type
    //   7	48	45	finally
    //   50	52	45	finally
  }
  
  public void removeListener(OnChangeListener<T> paramOnChangeListener)
  {
    synchronized (this.listeners)
    {
      this.listeners.remove(paramOnChangeListener);
      return;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.model.SimpleObservable
 * JD-Core Version:    0.7.0.1
 */