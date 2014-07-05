package jp.adlantis.android;

import java.util.ArrayList;

public class AdRequestListeners
  implements AdRequestNotifier
{
  protected ArrayList<AdRequestListener> listeners = new ArrayList();
  
  public void addRequestListener(AdRequestListener paramAdRequestListener)
  {
    synchronized (this.listeners)
    {
      this.listeners.add(paramAdRequestListener);
      return;
    }
  }
  
  public void addRequestListeners(ArrayList<AdRequestListener> paramArrayList)
  {
    synchronized (this.listeners)
    {
      this.listeners.addAll(paramArrayList);
      return;
    }
  }
  
  public void addRequestListeners(AdRequestListeners paramAdRequestListeners)
  {
    addRequestListeners(paramAdRequestListeners.listeners);
  }
  
  /* Error */
  public void notifyListenersAdReceived(AdRequestNotifier paramAdRequestNotifier)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 18	jp/adlantis/android/AdRequestListeners:listeners	Ljava/util/ArrayList;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 18	jp/adlantis/android/AdRequestListeners:listeners	Ljava/util/ArrayList;
    //   11: invokevirtual 39	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   14: astore 4
    //   16: aload 4
    //   18: invokeinterface 45 1 0
    //   23: ifeq +27 -> 50
    //   26: aload 4
    //   28: invokeinterface 49 1 0
    //   33: checkcast 51	jp/adlantis/android/AdRequestListener
    //   36: aload_1
    //   37: invokeinterface 54 2 0
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
    //   0	53	0	this	AdRequestListeners
    //   0	53	1	paramAdRequestNotifier	AdRequestNotifier
    //   4	47	2	localArrayList	ArrayList
    //   45	4	3	localObject	Object
    //   14	13	4	localIterator	java.util.Iterator
    // Exception table:
    //   from	to	target	type
    //   7	48	45	finally
    //   50	52	45	finally
  }
  
  /* Error */
  public void notifyListenersAdTouched(AdRequestNotifier paramAdRequestNotifier)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 18	jp/adlantis/android/AdRequestListeners:listeners	Ljava/util/ArrayList;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 18	jp/adlantis/android/AdRequestListeners:listeners	Ljava/util/ArrayList;
    //   11: invokevirtual 39	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   14: astore 4
    //   16: aload 4
    //   18: invokeinterface 45 1 0
    //   23: ifeq +27 -> 50
    //   26: aload 4
    //   28: invokeinterface 49 1 0
    //   33: checkcast 51	jp/adlantis/android/AdRequestListener
    //   36: aload_1
    //   37: invokeinterface 58 2 0
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
    //   0	53	0	this	AdRequestListeners
    //   0	53	1	paramAdRequestNotifier	AdRequestNotifier
    //   4	47	2	localArrayList	ArrayList
    //   45	4	3	localObject	Object
    //   14	13	4	localIterator	java.util.Iterator
    // Exception table:
    //   from	to	target	type
    //   7	48	45	finally
    //   50	52	45	finally
  }
  
  /* Error */
  public void notifyListenersFailedToReceiveAd(AdRequestNotifier paramAdRequestNotifier)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 18	jp/adlantis/android/AdRequestListeners:listeners	Ljava/util/ArrayList;
    //   4: astore_2
    //   5: aload_2
    //   6: monitorenter
    //   7: aload_0
    //   8: getfield 18	jp/adlantis/android/AdRequestListeners:listeners	Ljava/util/ArrayList;
    //   11: invokevirtual 39	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   14: astore 4
    //   16: aload 4
    //   18: invokeinterface 45 1 0
    //   23: ifeq +27 -> 50
    //   26: aload 4
    //   28: invokeinterface 49 1 0
    //   33: checkcast 51	jp/adlantis/android/AdRequestListener
    //   36: aload_1
    //   37: invokeinterface 62 2 0
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
    //   0	53	0	this	AdRequestListeners
    //   0	53	1	paramAdRequestNotifier	AdRequestNotifier
    //   4	47	2	localArrayList	ArrayList
    //   45	4	3	localObject	Object
    //   14	13	4	localIterator	java.util.Iterator
    // Exception table:
    //   from	to	target	type
    //   7	48	45	finally
    //   50	52	45	finally
  }
  
  public void removeRequestListener(AdRequestListener paramAdRequestListener)
  {
    synchronized (this.listeners)
    {
      this.listeners.remove(paramAdRequestListener);
      return;
    }
  }
  
  public void removeRequestListeners(ArrayList<AdRequestListener> paramArrayList)
  {
    synchronized (this.listeners)
    {
      this.listeners.removeAll(paramArrayList);
      return;
    }
  }
  
  public void removeRequestListeners(AdRequestListeners paramAdRequestListeners)
  {
    removeRequestListeners(paramAdRequestListeners.listeners);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.AdRequestListeners
 * JD-Core Version:    0.7.0.1
 */