package twitter4j.internal.org.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class JSONArray
{
  private ArrayList myArrayList = new ArrayList();
  
  public JSONArray() {}
  
  public JSONArray(Object paramObject)
    throws JSONException
  {
    this();
    if (paramObject.getClass().isArray())
    {
      int i = Array.getLength(paramObject);
      for (int j = 0; j < i; j++) {
        put(JSONObject.wrap(Array.get(paramObject, j)));
      }
    }
    throw new JSONException("JSONArray initial value should be a string or collection or array.");
  }
  
  public JSONArray(String paramString)
    throws JSONException
  {
    this(new JSONTokener(paramString));
  }
  
  public JSONArray(Collection paramCollection)
  {
    if (paramCollection != null)
    {
      Iterator localIterator = paramCollection.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = localIterator.next();
        this.myArrayList.add(JSONObject.wrap(localObject));
      }
    }
  }
  
  public JSONArray(JSONTokener paramJSONTokener)
    throws JSONException
  {
    this();
    if (paramJSONTokener.nextClean() != '[') {
      throw paramJSONTokener.syntaxError("A JSONArray text must start with '['");
    }
    if (paramJSONTokener.nextClean() != ']') {
      paramJSONTokener.back();
    }
    for (;;)
    {
      if (paramJSONTokener.nextClean() == ',')
      {
        paramJSONTokener.back();
        this.myArrayList.add(JSONObject.NULL);
      }
      for (;;)
      {
        switch (paramJSONTokener.nextClean())
        {
        default: 
          throw paramJSONTokener.syntaxError("Expected a ',' or ']'");
          paramJSONTokener.back();
          this.myArrayList.add(paramJSONTokener.nextValue());
        }
      }
      if (paramJSONTokener.nextClean() == ']') {
        return;
      }
      paramJSONTokener.back();
    }
  }
  
  public Object get(int paramInt)
    throws JSONException
  {
    Object localObject = opt(paramInt);
    if (localObject == null) {
      throw new JSONException("JSONArray[" + paramInt + "] not found.");
    }
    return localObject;
  }
  
  public boolean getBoolean(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    if ((localObject.equals(Boolean.FALSE)) || (((localObject instanceof String)) && (((String)localObject).equalsIgnoreCase("false")))) {}
    for (boolean bool = false;; bool = true)
    {
      return bool;
      if ((!localObject.equals(Boolean.TRUE)) && ((!(localObject instanceof String)) || (!((String)localObject).equalsIgnoreCase("true")))) {
        break;
      }
    }
    throw new JSONException("JSONArray[" + paramInt + "] is not a boolean.");
  }
  
  public double getDouble(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    double d2;
    try
    {
      if ((localObject instanceof Number))
      {
        d2 = ((Number)localObject).doubleValue();
      }
      else
      {
        double d1 = Double.parseDouble((String)localObject);
        d2 = d1;
      }
    }
    catch (Exception localException)
    {
      throw new JSONException("JSONArray[" + paramInt + "] is not a number.");
    }
    return d2;
  }
  
  public int getInt(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    int j;
    try
    {
      if ((localObject instanceof Number))
      {
        j = ((Number)localObject).intValue();
      }
      else
      {
        int i = Integer.parseInt((String)localObject);
        j = i;
      }
    }
    catch (Exception localException)
    {
      throw new JSONException("JSONArray[" + paramInt + "] is not a number.");
    }
    return j;
  }
  
  public JSONArray getJSONArray(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    if ((localObject instanceof JSONArray)) {
      return (JSONArray)localObject;
    }
    throw new JSONException("JSONArray[" + paramInt + "] is not a JSONArray.");
  }
  
  public JSONObject getJSONObject(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    if ((localObject instanceof JSONObject)) {
      return (JSONObject)localObject;
    }
    throw new JSONException("JSONArray[" + paramInt + "] is not a JSONObject.");
  }
  
  public long getLong(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    long l2;
    try
    {
      if ((localObject instanceof Number))
      {
        l2 = ((Number)localObject).longValue();
      }
      else
      {
        long l1 = Long.parseLong((String)localObject);
        l2 = l1;
      }
    }
    catch (Exception localException)
    {
      throw new JSONException("JSONArray[" + paramInt + "] is not a number.");
    }
    return l2;
  }
  
  public String getString(int paramInt)
    throws JSONException
  {
    Object localObject = get(paramInt);
    if (localObject == JSONObject.NULL) {}
    for (String str = null;; str = localObject.toString()) {
      return str;
    }
  }
  
  public boolean isNull(int paramInt)
  {
    return JSONObject.NULL.equals(opt(paramInt));
  }
  
  public String join(String paramString)
    throws JSONException
  {
    int i = length();
    StringBuilder localStringBuilder = new StringBuilder();
    for (int j = 0; j < i; j++)
    {
      if (j > 0) {
        localStringBuilder.append(paramString);
      }
      localStringBuilder.append(JSONObject.valueToString(this.myArrayList.get(j)));
    }
    return localStringBuilder.toString();
  }
  
  public int length()
  {
    return this.myArrayList.size();
  }
  
  public Object opt(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= length())) {}
    for (Object localObject = null;; localObject = this.myArrayList.get(paramInt)) {
      return localObject;
    }
  }
  
  public JSONArray put(int paramInt)
  {
    put(new Integer(paramInt));
    return this;
  }
  
  public JSONArray put(int paramInt, double paramDouble)
    throws JSONException
  {
    put(paramInt, new Double(paramDouble));
    return this;
  }
  
  public JSONArray put(int paramInt1, int paramInt2)
    throws JSONException
  {
    put(paramInt1, new Integer(paramInt2));
    return this;
  }
  
  public JSONArray put(int paramInt, long paramLong)
    throws JSONException
  {
    put(paramInt, new Long(paramLong));
    return this;
  }
  
  public JSONArray put(int paramInt, Object paramObject)
    throws JSONException
  {
    JSONObject.testValidity(paramObject);
    if (paramInt < 0) {
      throw new JSONException("JSONArray[" + paramInt + "] not found.");
    }
    if (paramInt < length()) {
      this.myArrayList.set(paramInt, paramObject);
    }
    for (;;)
    {
      return this;
      while (paramInt != length()) {
        put(JSONObject.NULL);
      }
      put(paramObject);
    }
  }
  
  public JSONArray put(int paramInt, Collection paramCollection)
    throws JSONException
  {
    put(paramInt, new JSONArray(paramCollection));
    return this;
  }
  
  public JSONArray put(int paramInt, Map paramMap)
    throws JSONException
  {
    put(paramInt, new JSONObject(paramMap));
    return this;
  }
  
  public JSONArray put(int paramInt, boolean paramBoolean)
    throws JSONException
  {
    if (paramBoolean) {}
    for (Boolean localBoolean = Boolean.TRUE;; localBoolean = Boolean.FALSE)
    {
      put(paramInt, localBoolean);
      return this;
    }
  }
  
  public JSONArray put(long paramLong)
  {
    put(new Long(paramLong));
    return this;
  }
  
  public JSONArray put(Object paramObject)
  {
    this.myArrayList.add(paramObject);
    return this;
  }
  
  public JSONArray put(Collection paramCollection)
  {
    put(new JSONArray(paramCollection));
    return this;
  }
  
  public JSONArray put(Map paramMap)
  {
    put(new JSONObject(paramMap));
    return this;
  }
  
  public JSONArray put(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (Boolean localBoolean = Boolean.TRUE;; localBoolean = Boolean.FALSE)
    {
      put(localBoolean);
      return this;
    }
  }
  
  public String toString()
  {
    try
    {
      String str2 = '[' + join(",") + ']';
      str1 = str2;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        String str1 = null;
      }
    }
    return str1;
  }
  
  public String toString(int paramInt)
    throws JSONException
  {
    return toString(paramInt, 0);
  }
  
  String toString(int paramInt1, int paramInt2)
    throws JSONException
  {
    int i = length();
    String str;
    if (i == 0)
    {
      str = "[]";
      return str;
    }
    StringBuilder localStringBuilder = new StringBuilder("[");
    if (i == 1) {
      localStringBuilder.append(JSONObject.valueToString(this.myArrayList.get(0), paramInt1, paramInt2));
    }
    for (;;)
    {
      localStringBuilder.append(']');
      str = localStringBuilder.toString();
      break;
      int j = paramInt2 + paramInt1;
      localStringBuilder.append('\n');
      for (int k = 0; k < i; k++)
      {
        if (k > 0) {
          localStringBuilder.append(",\n");
        }
        for (int n = 0; n < j; n++) {
          localStringBuilder.append(' ');
        }
        localStringBuilder.append(JSONObject.valueToString(this.myArrayList.get(k), paramInt1, j));
      }
      localStringBuilder.append('\n');
      for (int m = 0; m < paramInt2; m++) {
        localStringBuilder.append(' ');
      }
    }
  }
  
  public Writer write(Writer paramWriter)
    throws JSONException
  {
    int i = 0;
    for (;;)
    {
      int k;
      Object localObject;
      try
      {
        int j = length();
        paramWriter.write(91);
        k = 0;
        if (k >= j) {
          break label109;
        }
        if (i != 0) {
          paramWriter.write(44);
        }
        localObject = this.myArrayList.get(k);
        if ((localObject instanceof JSONObject)) {
          ((JSONObject)localObject).write(paramWriter);
        } else if ((localObject instanceof JSONArray)) {
          ((JSONArray)localObject).write(paramWriter);
        }
      }
      catch (IOException localIOException)
      {
        throw new JSONException(localIOException);
      }
      paramWriter.write(JSONObject.valueToString(localObject));
      break label117;
      label109:
      paramWriter.write(93);
      return paramWriter;
      label117:
      i = 1;
      k++;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.org.json.JSONArray
 * JD-Core Version:    0.7.0.1
 */