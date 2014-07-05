package com.amoad.amoadsdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

abstract class SyntaxSugar
{
  public static <T> T[] A(T... paramVarArgs)
  {
    if (paramVarArgs == null) {
      paramVarArgs = new Object[1];
    }
    return paramVarArgs;
  }
  
  public static <T> List<T> L(T... paramVarArgs)
  {
    ArrayList localArrayList = new ArrayList();
    int i = paramVarArgs.length;
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return localArrayList;
      }
      localArrayList.add(paramVarArgs[j]);
    }
  }
  
  public static <K, V> M<K, V> M()
  {
    return new M();
  }
  
  public static <K, V> M<K, V> M(K paramK, V paramV)
  {
    return new M().$(paramK, paramV);
  }
  
  public static <K, V> OM<K, V> OM()
  {
    return new OM();
  }
  
  public static <K, V> OM<K, V> OM(K paramK, V paramV)
  {
    return new OM().$(paramK, paramV);
  }
  
  public static String S(Object... paramVarArgs)
  {
    String str;
    if (paramVarArgs == null)
    {
      str = "";
      return str;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramVarArgs.length;
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        str = localStringBuilder.toString();
        break;
      }
      localStringBuilder.append(paramVarArgs[j]);
    }
  }
  
  public static class M<K, V>
    extends HashMap<K, V>
  {
    public <T> M<K, V> $(K paramK, T paramT)
    {
      put(paramK, paramT);
      return this;
    }
    
    public Boolean asBoolean(K paramK)
    {
      return Cast.toBoolean(get(paramK));
    }
    
    public Boolean asBoolean(K paramK, Boolean paramBoolean)
    {
      return Cast.forceBoolean(get(paramK), false, paramBoolean);
    }
    
    public Integer asInteger(K paramK)
    {
      return Cast.toInteger(get(paramK));
    }
    
    public Integer asInteger(K paramK, Integer paramInteger)
    {
      return Cast.forceInteger(get(paramK), false, paramInteger);
    }
    
    public String asString(K paramK)
    {
      return Cast.toString(get(paramK));
    }
  }
  
  public static class OM<K, V>
    extends TreeMap<K, V>
  {
    private static final long serialVersionUID = -4279628749187817949L;
    
    public <T> OM<K, V> $(K paramK, T paramT)
    {
      put(paramK, paramT);
      return this;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.SyntaxSugar
 * JD-Core Version:    0.7.0.1
 */