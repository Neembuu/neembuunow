package twitter4j.internal.org.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

public class JSONObject
{
  public static final Object NULL = new Null(null);
  private Map map = new HashMap();
  
  public JSONObject() {}
  
  public JSONObject(Object paramObject)
  {
    this();
    populateMap(paramObject);
  }
  
  public JSONObject(Object paramObject, String[] paramArrayOfString)
  {
    this();
    Class localClass = paramObject.getClass();
    int i = 0;
    for (;;)
    {
      String str;
      if (i < paramArrayOfString.length) {
        str = paramArrayOfString[i];
      }
      try
      {
        putOpt(str, localClass.getField(str).get(paramObject));
        label42:
        i++;
        continue;
        return;
      }
      catch (Exception localException)
      {
        break label42;
      }
    }
  }
  
  public JSONObject(String paramString)
    throws JSONException
  {
    this(new JSONTokener(paramString));
  }
  
  public JSONObject(String paramString, Locale paramLocale)
    throws JSONException
  {
    this();
    ResourceBundle localResourceBundle = ResourceBundle.getBundle(paramString, paramLocale, Thread.currentThread().getContextClassLoader());
    Enumeration localEnumeration = localResourceBundle.getKeys();
    while (localEnumeration.hasMoreElements())
    {
      Object localObject1 = localEnumeration.nextElement();
      if ((localObject1 instanceof String))
      {
        String[] arrayOfString = ((String)localObject1).split("\\.");
        int i = -1 + arrayOfString.length;
        Object localObject2 = this;
        int j = 0;
        if (j < i)
        {
          String str = arrayOfString[j];
          Object localObject3 = ((JSONObject)localObject2).opt(str);
          if ((localObject3 instanceof JSONObject)) {}
          for (JSONObject localJSONObject = (JSONObject)localObject3;; localJSONObject = null)
          {
            if (localJSONObject == null)
            {
              localJSONObject = new JSONObject();
              ((JSONObject)localObject2).put(str, localJSONObject);
            }
            localObject2 = localJSONObject;
            j++;
            break;
          }
        }
        ((JSONObject)localObject2).put(arrayOfString[i], localResourceBundle.getString((String)localObject1));
      }
    }
  }
  
  public JSONObject(Map paramMap)
  {
    if (paramMap != null)
    {
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        Object localObject = localEntry.getValue();
        if (localObject != null) {
          this.map.put(localEntry.getKey(), wrap(localObject));
        }
      }
    }
  }
  
  public JSONObject(JSONObject paramJSONObject, String[] paramArrayOfString)
  {
    this();
    int i = 0;
    for (;;)
    {
      if (i < paramArrayOfString.length) {}
      try
      {
        putOnce(paramArrayOfString[i], paramJSONObject.opt(paramArrayOfString[i]));
        label27:
        i++;
        continue;
        return;
      }
      catch (Exception localException)
      {
        break label27;
      }
    }
  }
  
  public JSONObject(JSONTokener paramJSONTokener)
    throws JSONException
  {
    this();
    if (paramJSONTokener.nextClean() != '{') {
      throw paramJSONTokener.syntaxError("A JSONObject text must begin with '{' found:" + paramJSONTokener.nextClean());
    }
    do
    {
      paramJSONTokener.back();
      String str;
      int i;
      switch (paramJSONTokener.nextClean())
      {
      default: 
        paramJSONTokener.back();
        str = paramJSONTokener.nextValue().toString();
        i = paramJSONTokener.nextClean();
        if (i == 61) {
          if (paramJSONTokener.next() != '>') {
            paramJSONTokener.back();
          }
        }
      case '\000': 
        while (i == 58)
        {
          putOnce(str, paramJSONTokener.nextValue());
          switch (paramJSONTokener.nextClean())
          {
          default: 
            throw paramJSONTokener.syntaxError("Expected a ',' or '}'");
            throw paramJSONTokener.syntaxError("A JSONObject text must end with '}'");
          }
        }
        throw paramJSONTokener.syntaxError("Expected a ':' after a key");
      }
    } while (paramJSONTokener.nextClean() != '}');
  }
  
  public static String numberToString(Number paramNumber)
    throws JSONException
  {
    if (paramNumber == null) {
      throw new JSONException("Null pointer");
    }
    testValidity(paramNumber);
    String str = paramNumber.toString();
    if ((str.indexOf('.') > 0) && (str.indexOf('e') < 0) && (str.indexOf('E') < 0))
    {
      while (str.endsWith("0")) {
        str = str.substring(0, -1 + str.length());
      }
      if (str.endsWith(".")) {
        str = str.substring(0, -1 + str.length());
      }
    }
    return str;
  }
  
  private void populateMap(Object paramObject)
  {
    int i = 0;
    Class localClass = paramObject.getClass();
    if (localClass.getClassLoader() != null) {
      i = 1;
    }
    Method[] arrayOfMethod;
    if (i != 0) {
      arrayOfMethod = localClass.getMethods();
    }
    for (;;)
    {
      int j = 0;
      label29:
      if (j < arrayOfMethod.length) {}
      try
      {
        Method localMethod = arrayOfMethod[j];
        String str1;
        if (Modifier.isPublic(localMethod.getModifiers()))
        {
          str1 = localMethod.getName();
          localObject1 = "";
          if (!str1.startsWith("get")) {
            break label205;
          }
          if (str1.equals("getClass")) {
            break label286;
          }
          if (!str1.equals("getDeclaringClass")) {
            break label194;
          }
          break label286;
          label100:
          if ((((String)localObject1).length() > 0) && (Character.isUpperCase(((String)localObject1).charAt(0))) && (localMethod.getParameterTypes().length == 0))
          {
            if (((String)localObject1).length() != 1) {
              break label227;
            }
            localObject1 = ((String)localObject1).toLowerCase();
          }
        }
        for (;;)
        {
          Object localObject2 = localMethod.invoke(paramObject, (Object[])null);
          if (localObject2 != null) {
            this.map.put(localObject1, wrap(localObject2));
          }
          j++;
          break label29;
          arrayOfMethod = localClass.getDeclaredMethods();
          break;
          label194:
          localObject1 = str1.substring(3);
          break label100;
          label205:
          if (!str1.startsWith("is")) {
            break label100;
          }
          localObject1 = str1.substring(2);
          break label100;
          label227:
          if (!Character.isUpperCase(((String)localObject1).charAt(1)))
          {
            String str2 = ((String)localObject1).substring(0, 1).toLowerCase() + ((String)localObject1).substring(1);
            localObject1 = str2;
          }
        }
        return;
      }
      catch (Exception localException)
      {
        for (;;)
        {
          continue;
          label286:
          Object localObject1 = "";
        }
      }
    }
  }
  
  public static String quote(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {}
    StringBuilder localStringBuilder;
    for (String str1 = "\"\"";; str1 = localStringBuilder.toString())
    {
      return str1;
      int i = 0;
      int j = paramString.length();
      localStringBuilder = new StringBuilder(j + 4);
      localStringBuilder.append('"');
      int k = 0;
      if (k < j)
      {
        int m = i;
        i = paramString.charAt(k);
        switch (i)
        {
        default: 
          if ((i < 32) || ((i >= 128) && (i < 160)) || ((i >= 8192) && (i < 8448)))
          {
            String str2 = "000" + Integer.toHexString(i);
            localStringBuilder.append("\\u").append(str2.substring(-4 + str2.length()));
          }
          break;
        }
        for (;;)
        {
          k++;
          break;
          localStringBuilder.append('\\');
          localStringBuilder.append(i);
          continue;
          if (m == 60) {
            localStringBuilder.append('\\');
          }
          localStringBuilder.append(i);
          continue;
          localStringBuilder.append("\\b");
          continue;
          localStringBuilder.append("\\t");
          continue;
          localStringBuilder.append("\\n");
          continue;
          localStringBuilder.append("\\f");
          continue;
          localStringBuilder.append("\\r");
          continue;
          localStringBuilder.append(i);
        }
      }
      localStringBuilder.append('"');
    }
  }
  
  public static Object stringToValue(String paramString)
  {
    if (paramString.equals("")) {}
    for (;;)
    {
      return paramString;
      if (paramString.equalsIgnoreCase("true"))
      {
        paramString = Boolean.TRUE;
      }
      else if (paramString.equalsIgnoreCase("false"))
      {
        paramString = Boolean.FALSE;
      }
      else if (paramString.equalsIgnoreCase("null"))
      {
        paramString = NULL;
      }
      else
      {
        int i = paramString.charAt(0);
        if (((i >= 48) && (i <= 57)) || (i == 46) || (i == 45) || (i == 43)) {
          if ((i == 48) && (paramString.length() > 2) && ((paramString.charAt(1) == 'x') || (paramString.charAt(1) == 'X'))) {
            try
            {
              Integer localInteger2 = Integer.valueOf(Integer.parseInt(paramString.substring(2), 16));
              paramString = localInteger2;
            }
            catch (Exception localException2) {}
          } else {
            try
            {
              if ((paramString.indexOf('.') > -1) || (paramString.indexOf('e') > -1) || (paramString.indexOf('E') > -1))
              {
                paramString = Double.valueOf(paramString);
              }
              else
              {
                Long localLong = new Long(paramString);
                if (localLong.longValue() == localLong.intValue())
                {
                  Integer localInteger1 = Integer.valueOf(localLong.intValue());
                  paramString = localInteger1;
                }
                else
                {
                  paramString = localLong;
                }
              }
            }
            catch (Exception localException1) {}
          }
        }
      }
    }
  }
  
  public static void testValidity(Object paramObject)
    throws JSONException
  {
    if (paramObject != null) {
      if ((paramObject instanceof Double))
      {
        if ((((Double)paramObject).isInfinite()) || (((Double)paramObject).isNaN())) {
          throw new JSONException("JSON does not allow non-finite numbers.");
        }
      }
      else if (((paramObject instanceof Float)) && ((((Float)paramObject).isInfinite()) || (((Float)paramObject).isNaN()))) {
        throw new JSONException("JSON does not allow non-finite numbers.");
      }
    }
  }
  
  public static String valueToString(Object paramObject)
    throws JSONException
  {
    String str;
    if ((paramObject == null) || (paramObject.equals(null))) {
      str = "null";
    }
    for (;;)
    {
      return str;
      if ((paramObject instanceof Number)) {
        str = numberToString((Number)paramObject);
      } else if (((paramObject instanceof Boolean)) || ((paramObject instanceof JSONObject)) || ((paramObject instanceof JSONArray))) {
        str = paramObject.toString();
      } else if ((paramObject instanceof Map)) {
        str = new JSONObject((Map)paramObject).toString();
      } else if ((paramObject instanceof Collection)) {
        str = new JSONArray((Collection)paramObject).toString();
      } else if (paramObject.getClass().isArray()) {
        str = new JSONArray(paramObject).toString();
      } else {
        str = quote(paramObject.toString());
      }
    }
  }
  
  static String valueToString(Object paramObject, int paramInt1, int paramInt2)
    throws JSONException
  {
    String str;
    if ((paramObject == null) || (paramObject.equals(null))) {
      str = "null";
    }
    for (;;)
    {
      return str;
      if ((paramObject instanceof Number)) {
        str = numberToString((Number)paramObject);
      } else if ((paramObject instanceof Boolean)) {
        str = paramObject.toString();
      } else if ((paramObject instanceof JSONObject)) {
        str = ((JSONObject)paramObject).toString(paramInt1, paramInt2);
      } else if ((paramObject instanceof JSONArray)) {
        str = ((JSONArray)paramObject).toString(paramInt1, paramInt2);
      } else if ((paramObject instanceof Map)) {
        str = new JSONObject((Map)paramObject).toString(paramInt1, paramInt2);
      } else if ((paramObject instanceof Collection)) {
        str = new JSONArray((Collection)paramObject).toString(paramInt1, paramInt2);
      } else if (paramObject.getClass().isArray()) {
        str = new JSONArray(paramObject).toString(paramInt1, paramInt2);
      } else {
        str = quote(paramObject.toString());
      }
    }
  }
  
  public static Object wrap(Object paramObject)
  {
    if (paramObject == null) {}
    try
    {
      paramObject = NULL;
    }
    catch (Exception localException)
    {
      Package localPackage;
      paramObject = null;
    }
    if ((!(paramObject instanceof JSONObject)) && (!(paramObject instanceof JSONArray)) && (!NULL.equals(paramObject)) && (!(paramObject instanceof Byte)) && (!(paramObject instanceof Character)) && (!(paramObject instanceof Short)) && (!(paramObject instanceof Integer)) && (!(paramObject instanceof Long)) && (!(paramObject instanceof Boolean)) && (!(paramObject instanceof Float)) && (!(paramObject instanceof Double)) && (!(paramObject instanceof String))) {
      if ((paramObject instanceof Collection))
      {
        paramObject = new JSONArray((Collection)paramObject);
      }
      else if (paramObject.getClass().isArray())
      {
        paramObject = new JSONArray(paramObject);
      }
      else if ((paramObject instanceof Map))
      {
        paramObject = new JSONObject((Map)paramObject);
      }
      else
      {
        localPackage = paramObject.getClass().getPackage();
        if (localPackage == null) {
          break label240;
        }
      }
    }
    label240:
    for (String str = localPackage.getName();; str = "")
    {
      if ((str.startsWith("java.")) || (str.startsWith("javax.")) || (paramObject.getClass().getClassLoader() == null))
      {
        paramObject = paramObject.toString();
      }
      else
      {
        JSONObject localJSONObject = new JSONObject(paramObject);
        paramObject = localJSONObject;
      }
      return paramObject;
    }
  }
  
  public JSONObject append(String paramString, Object paramObject)
    throws JSONException
  {
    testValidity(paramObject);
    Object localObject = opt(paramString);
    if (localObject == null) {
      put(paramString, new JSONArray().put(paramObject));
    }
    for (;;)
    {
      return this;
      if (!(localObject instanceof JSONArray)) {
        break;
      }
      put(paramString, ((JSONArray)localObject).put(paramObject));
    }
    throw new JSONException("JSONObject[" + paramString + "] is not a JSONArray.");
  }
  
  public Object get(String paramString)
    throws JSONException
  {
    if (paramString == null) {
      throw new JSONException("Null key.");
    }
    Object localObject = opt(paramString);
    if (localObject == null) {
      throw new JSONException("JSONObject[" + quote(paramString) + "] not found.");
    }
    return localObject;
  }
  
  public boolean getBoolean(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if ((localObject.equals(Boolean.FALSE)) || (((localObject instanceof String)) && (((String)localObject).equalsIgnoreCase("false")))) {}
    for (boolean bool = false;; bool = true)
    {
      return bool;
      if ((!localObject.equals(Boolean.TRUE)) && ((!(localObject instanceof String)) || (!((String)localObject).equalsIgnoreCase("true")))) {
        break;
      }
    }
    throw new JSONException("JSONObject[" + quote(paramString) + "] is not a Boolean.");
  }
  
  public int getInt(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
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
      throw new JSONException("JSONObject[" + quote(paramString) + "] is not an int.");
    }
    return j;
  }
  
  public JSONArray getJSONArray(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if ((localObject instanceof JSONArray)) {
      return (JSONArray)localObject;
    }
    throw new JSONException("JSONObject[" + quote(paramString) + "] is not a JSONArray.");
  }
  
  public JSONObject getJSONObject(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if ((localObject instanceof JSONObject)) {
      return (JSONObject)localObject;
    }
    throw new JSONException("JSONObject[" + quote(paramString) + "] is not a JSONObject.");
  }
  
  public long getLong(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
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
      throw new JSONException("JSONObject[" + quote(paramString) + "] is not a long.");
    }
    return l2;
  }
  
  public String getString(String paramString)
    throws JSONException
  {
    Object localObject = get(paramString);
    if (localObject == NULL) {}
    for (String str = null;; str = localObject.toString()) {
      return str;
    }
  }
  
  public boolean has(String paramString)
  {
    return this.map.containsKey(paramString);
  }
  
  public boolean isNull(String paramString)
  {
    return NULL.equals(opt(paramString));
  }
  
  public Iterator keys()
  {
    return this.map.keySet().iterator();
  }
  
  public int length()
  {
    return this.map.size();
  }
  
  public JSONArray names()
  {
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = keys();
    while (localIterator.hasNext()) {
      localJSONArray.put(localIterator.next());
    }
    if (localJSONArray.length() == 0) {
      localJSONArray = null;
    }
    return localJSONArray;
  }
  
  public Object opt(String paramString)
  {
    if (paramString == null) {}
    for (Object localObject = null;; localObject = this.map.get(paramString)) {
      return localObject;
    }
  }
  
  public JSONObject put(String paramString, double paramDouble)
    throws JSONException
  {
    put(paramString, new Double(paramDouble));
    return this;
  }
  
  public JSONObject put(String paramString, int paramInt)
    throws JSONException
  {
    put(paramString, new Integer(paramInt));
    return this;
  }
  
  public JSONObject put(String paramString, long paramLong)
    throws JSONException
  {
    put(paramString, new Long(paramLong));
    return this;
  }
  
  public JSONObject put(String paramString, Object paramObject)
    throws JSONException
  {
    if (paramString == null) {
      throw new JSONException("Null key.");
    }
    if (paramObject != null)
    {
      testValidity(paramObject);
      this.map.put(paramString, paramObject);
    }
    for (;;)
    {
      return this;
      remove(paramString);
    }
  }
  
  public JSONObject put(String paramString, Collection paramCollection)
    throws JSONException
  {
    put(paramString, new JSONArray(paramCollection));
    return this;
  }
  
  public JSONObject put(String paramString, Map paramMap)
    throws JSONException
  {
    put(paramString, new JSONObject(paramMap));
    return this;
  }
  
  public JSONObject put(String paramString, boolean paramBoolean)
    throws JSONException
  {
    if (paramBoolean) {}
    for (Boolean localBoolean = Boolean.TRUE;; localBoolean = Boolean.FALSE)
    {
      put(paramString, localBoolean);
      return this;
    }
  }
  
  public JSONObject putOnce(String paramString, Object paramObject)
    throws JSONException
  {
    if ((paramString != null) && (paramObject != null))
    {
      if (opt(paramString) != null) {
        throw new JSONException("Duplicate key \"" + paramString + "\"");
      }
      put(paramString, paramObject);
    }
    return this;
  }
  
  public JSONObject putOpt(String paramString, Object paramObject)
    throws JSONException
  {
    if ((paramString != null) && (paramObject != null)) {
      put(paramString, paramObject);
    }
    return this;
  }
  
  public Object remove(String paramString)
  {
    return this.map.remove(paramString);
  }
  
  public Iterator sortedKeys()
  {
    return new TreeSet(this.map.keySet()).iterator();
  }
  
  public String toString()
  {
    String str1;
    try
    {
      Iterator localIterator = keys();
      StringBuilder localStringBuilder = new StringBuilder("{");
      while (localIterator.hasNext())
      {
        if (localStringBuilder.length() > 1) {
          localStringBuilder.append(',');
        }
        Object localObject = localIterator.next();
        localStringBuilder.append(quote(localObject.toString()));
        localStringBuilder.append(':');
        localStringBuilder.append(valueToString(this.map.get(localObject)));
      }
      localStringBuilder.append('}');
      String str2 = localStringBuilder.toString();
      str1 = str2;
    }
    catch (Exception localException)
    {
      str1 = null;
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
      str = "{}";
      return str;
    }
    Iterator localIterator = sortedKeys();
    int j = paramInt2 + paramInt1;
    StringBuilder localStringBuilder = new StringBuilder("{");
    if (i == 1)
    {
      Object localObject2 = localIterator.next();
      localStringBuilder.append(quote(localObject2.toString()));
      localStringBuilder.append(": ");
      localStringBuilder.append(valueToString(this.map.get(localObject2), paramInt1, paramInt2));
    }
    for (;;)
    {
      localStringBuilder.append('}');
      str = localStringBuilder.toString();
      break;
      Object localObject1;
      localStringBuilder.append(quote(localObject1.toString()));
      localStringBuilder.append(": ");
      localStringBuilder.append(valueToString(this.map.get(localObject1), paramInt1, j));
      if (localIterator.hasNext())
      {
        localObject1 = localIterator.next();
        if (localStringBuilder.length() > 1) {
          localStringBuilder.append(",\n");
        }
        for (;;)
        {
          for (int m = 0; m < j; m++) {
            localStringBuilder.append(' ');
          }
          break;
          localStringBuilder.append('\n');
        }
      }
      if (localStringBuilder.length() > 1)
      {
        localStringBuilder.append('\n');
        for (int k = 0; k < paramInt2; k++) {
          localStringBuilder.append(' ');
        }
      }
    }
  }
  
  public Writer write(Writer paramWriter)
    throws JSONException
  {
    for (int i = 0;; i = 1)
    {
      Object localObject2;
      try
      {
        Iterator localIterator = keys();
        paramWriter.write(123);
        if (!localIterator.hasNext()) {
          break label138;
        }
        if (i != 0) {
          paramWriter.write(44);
        }
        Object localObject1 = localIterator.next();
        paramWriter.write(quote(localObject1.toString()));
        paramWriter.write(58);
        localObject2 = this.map.get(localObject1);
        if ((localObject2 instanceof JSONObject)) {
          ((JSONObject)localObject2).write(paramWriter);
        } else if ((localObject2 instanceof JSONArray)) {
          ((JSONArray)localObject2).write(paramWriter);
        }
      }
      catch (IOException localIOException)
      {
        throw new JSONException(localIOException);
      }
      paramWriter.write(valueToString(localObject2));
      continue;
      label138:
      paramWriter.write(125);
      return paramWriter;
    }
  }
  
  private static final class Null
  {
    protected final Object clone()
    {
      return this;
    }
    
    public boolean equals(Object paramObject)
    {
      if ((paramObject == null) || (paramObject == this)) {}
      for (boolean bool = true;; bool = false) {
        return bool;
      }
    }
    
    public String toString()
    {
      return "null";
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.org.json.JSONObject
 * JD-Core Version:    0.7.0.1
 */