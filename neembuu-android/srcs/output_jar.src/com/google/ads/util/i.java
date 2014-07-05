package com.google.ads.util;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class i
{
  private static final Object a = new Object();
  private static int b = 0;
  private static HashMap<Class<?>, Integer> c = new HashMap();
  private final ArrayList<a<?>> d = new ArrayList();
  public final int u;
  
  public i()
  {
    synchronized (a)
    {
      int i = b;
      b = i + 1;
      this.u = i;
      Integer localInteger = (Integer)c.get(getClass());
      if (localInteger == null)
      {
        c.put(getClass(), Integer.valueOf(1));
        b.d("State created: " + toString());
        return;
      }
      c.put(getClass(), Integer.valueOf(1 + localInteger.intValue()));
    }
  }
  
  private void a(a<?> parama)
  {
    this.d.add(parama);
  }
  
  protected void finalize()
    throws Throwable
  {
    synchronized (a)
    {
      c.put(getClass(), Integer.valueOf(-1 + ((Integer)c.get(getClass())).intValue()));
      super.finalize();
      return;
    }
  }
  
  public String toString()
  {
    return getClass().getSimpleName() + "[" + this.u + "]";
  }
  
  public final class d<T>
    extends i.a<WeakReference<T>>
  {
    public d(T paramT)
    {
      super(paramT, new WeakReference(localObject), null);
    }
    
    public T a()
    {
      return ((WeakReference)this.a).get();
    }
    
    public String toString()
    {
      return i.this.toString() + "." + this.b + " = " + a() + " (?)";
    }
  }
  
  public final class b<T>
    extends i.a<T>
  {
    public b(T paramT)
    {
      super(paramT, localObject, null);
    }
    
    public T a()
    {
      return this.a;
    }
    
    public String toString()
    {
      return super.toString() + " (!)";
    }
  }
  
  public final class c<T>
    extends i.a<T>
  {
    private boolean e = false;
    
    public c(String paramString)
    {
      super(paramString, null);
    }
    
    public c(T paramT)
    {
      super(paramT, localObject, null);
    }
    
    /**
     * @deprecated
     */
    public T a()
    {
      try
      {
        Object localObject2 = this.a;
        return localObject2;
      }
      finally
      {
        localObject1 = finally;
        throw localObject1;
      }
    }
    
    /**
     * @deprecated
     */
    public void a(T paramT)
    {
      try
      {
        b.d("State changed - " + i.this.toString() + "." + this.b + ": '" + paramT + "' <-- '" + this.a + "'.");
        this.a = paramT;
        this.e = true;
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
      StringBuilder localStringBuilder = new StringBuilder().append(super.toString());
      if (this.e) {}
      for (String str = " (*)";; str = "") {
        return str;
      }
    }
  }
  
  public abstract class a<T>
  {
    protected T a;
    protected final String b;
    
    private a(String paramString)
    {
      this(paramString, null);
    }
    
    private a(T paramT)
    {
      this.b = paramT;
      i.a(i.this, this);
      Object localObject;
      this.a = localObject;
    }
    
    public String toString()
    {
      return i.this.toString() + "." + this.b + " = " + this.a;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.util.i
 * JD-Core Version:    0.7.0.1
 */