package twitter4j.internal.http;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

public final class HttpParameter
  implements Comparable, Serializable
{
  private static final String GIF = "image/gif";
  private static final String JPEG = "image/jpeg";
  private static final String OCTET = "application/octet-stream";
  private static final String PNG = "image/png";
  private static final long serialVersionUID = -8708108746980739212L;
  private File file = null;
  private InputStream fileBody = null;
  private String name = null;
  private String value = null;
  
  public HttpParameter(String paramString, double paramDouble)
  {
    this.name = paramString;
    this.value = String.valueOf(paramDouble);
  }
  
  public HttpParameter(String paramString, int paramInt)
  {
    this.name = paramString;
    this.value = String.valueOf(paramInt);
  }
  
  public HttpParameter(String paramString, long paramLong)
  {
    this.name = paramString;
    this.value = String.valueOf(paramLong);
  }
  
  public HttpParameter(String paramString, File paramFile)
  {
    this.name = paramString;
    this.file = paramFile;
  }
  
  public HttpParameter(String paramString1, String paramString2)
  {
    this.name = paramString1;
    this.value = paramString2;
  }
  
  public HttpParameter(String paramString1, String paramString2, InputStream paramInputStream)
  {
    this.name = paramString1;
    this.file = new File(paramString2);
    this.fileBody = paramInputStream;
  }
  
  public HttpParameter(String paramString, boolean paramBoolean)
  {
    this.name = paramString;
    this.value = String.valueOf(paramBoolean);
  }
  
  static boolean containsFile(List<HttpParameter> paramList)
  {
    boolean bool = false;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      if (((HttpParameter)localIterator.next()).isFile()) {
        bool = true;
      }
    }
    return bool;
  }
  
  public static boolean containsFile(HttpParameter[] paramArrayOfHttpParameter)
  {
    boolean bool1 = false;
    boolean bool2;
    if (paramArrayOfHttpParameter == null)
    {
      bool2 = false;
      return bool2;
    }
    int i = paramArrayOfHttpParameter.length;
    for (int j = 0;; j++) {
      if (j < i)
      {
        if (paramArrayOfHttpParameter[j].isFile()) {
          bool1 = true;
        }
      }
      else
      {
        bool2 = bool1;
        break;
      }
    }
  }
  
  public static String encode(String paramString)
  {
    Object localObject = null;
    try
    {
      String str = URLEncoder.encode(paramString, "UTF-8");
      localObject = str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      label13:
      StringBuilder localStringBuilder;
      int i;
      char c;
      break label13;
    }
    localStringBuilder = new StringBuilder(localObject.length());
    i = 0;
    if (i < localObject.length())
    {
      c = localObject.charAt(i);
      if (c == '*') {
        localStringBuilder.append("%2A");
      }
      for (;;)
      {
        i++;
        break;
        if (c == '+')
        {
          localStringBuilder.append("%20");
        }
        else if ((c == '%') && (i + 1 < localObject.length()) && (localObject.charAt(i + 1) == '7') && (localObject.charAt(i + 2) == 'E'))
        {
          localStringBuilder.append('~');
          i += 2;
        }
        else
        {
          localStringBuilder.append(c);
        }
      }
    }
    return localStringBuilder.toString();
  }
  
  public static String encodeParameters(HttpParameter[] paramArrayOfHttpParameter)
  {
    if (paramArrayOfHttpParameter == null) {}
    StringBuilder localStringBuilder;
    for (String str = "";; str = localStringBuilder.toString())
    {
      return str;
      localStringBuilder = new StringBuilder();
      for (int i = 0; i < paramArrayOfHttpParameter.length; i++)
      {
        if (paramArrayOfHttpParameter[i].isFile()) {
          throw new IllegalArgumentException("parameter [" + paramArrayOfHttpParameter[i].name + "]should be text");
        }
        if (i != 0) {
          localStringBuilder.append("&");
        }
        localStringBuilder.append(encode(paramArrayOfHttpParameter[i].name)).append("=").append(encode(paramArrayOfHttpParameter[i].value));
      }
    }
  }
  
  public static HttpParameter[] getParameterArray(String paramString, int paramInt)
  {
    return getParameterArray(paramString, String.valueOf(paramInt));
  }
  
  public static HttpParameter[] getParameterArray(String paramString1, int paramInt1, String paramString2, int paramInt2)
  {
    return getParameterArray(paramString1, String.valueOf(paramInt1), paramString2, String.valueOf(paramInt2));
  }
  
  public static HttpParameter[] getParameterArray(String paramString1, String paramString2)
  {
    HttpParameter[] arrayOfHttpParameter = new HttpParameter[1];
    arrayOfHttpParameter[0] = new HttpParameter(paramString1, paramString2);
    return arrayOfHttpParameter;
  }
  
  public static HttpParameter[] getParameterArray(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    HttpParameter[] arrayOfHttpParameter = new HttpParameter[2];
    arrayOfHttpParameter[0] = new HttpParameter(paramString1, paramString2);
    arrayOfHttpParameter[1] = new HttpParameter(paramString3, paramString4);
    return arrayOfHttpParameter;
  }
  
  public int compareTo(Object paramObject)
  {
    HttpParameter localHttpParameter = (HttpParameter)paramObject;
    int i = this.name.compareTo(localHttpParameter.name);
    if (i == 0) {
      i = this.value.compareTo(localHttpParameter.value);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    HttpParameter localHttpParameter;
    do
    {
      for (;;)
      {
        return bool;
        if (!(paramObject instanceof HttpParameter))
        {
          bool = false;
        }
        else
        {
          localHttpParameter = (HttpParameter)paramObject;
          if (this.file != null)
          {
            if (this.file.equals(localHttpParameter.file)) {}
          }
          else {
            while (localHttpParameter.file != null)
            {
              bool = false;
              break;
            }
          }
          if (this.fileBody != null)
          {
            if (this.fileBody.equals(localHttpParameter.fileBody)) {}
          }
          else {
            while (localHttpParameter.fileBody != null)
            {
              bool = false;
              break;
            }
          }
          if (this.name.equals(localHttpParameter.name)) {
            break;
          }
          bool = false;
        }
      }
      if (this.value == null) {
        break;
      }
    } while (this.value.equals(localHttpParameter.value));
    for (;;)
    {
      bool = false;
      break;
      if (localHttpParameter.value == null) {
        break;
      }
    }
  }
  
  public String getContentType()
  {
    if (!isFile()) {
      throw new IllegalStateException("not a file");
    }
    String str1 = this.file.getName();
    String str3;
    if (-1 == str1.lastIndexOf(".")) {
      str3 = "application/octet-stream";
    }
    for (;;)
    {
      return str3;
      String str2 = str1.substring(1 + str1.lastIndexOf(".")).toLowerCase();
      if (str2.length() == 3)
      {
        if ("gif".equals(str2)) {
          str3 = "image/gif";
        } else if ("png".equals(str2)) {
          str3 = "image/png";
        } else if ("jpg".equals(str2)) {
          str3 = "image/jpeg";
        } else {
          str3 = "application/octet-stream";
        }
      }
      else if (str2.length() == 4)
      {
        if ("jpeg".equals(str2)) {
          str3 = "image/jpeg";
        } else {
          str3 = "application/octet-stream";
        }
      }
      else {
        str3 = "application/octet-stream";
      }
    }
  }
  
  public File getFile()
  {
    return this.file;
  }
  
  public InputStream getFileBody()
  {
    return this.fileBody;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public boolean hasFileBody()
  {
    if (this.fileBody != null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public int hashCode()
  {
    int i = 0;
    int j = 31 * this.name.hashCode();
    int k;
    int m;
    if (this.value != null)
    {
      k = this.value.hashCode();
      m = 31 * (j + k);
      if (this.file == null) {
        break label87;
      }
    }
    label87:
    for (int n = this.file.hashCode();; n = 0)
    {
      int i1 = 31 * (m + n);
      if (this.fileBody != null) {
        i = this.fileBody.hashCode();
      }
      return i1 + i;
      k = 0;
      break;
    }
  }
  
  public boolean isFile()
  {
    if (this.file != null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public String toString()
  {
    return "PostParameter{name='" + this.name + '\'' + ", value='" + this.value + '\'' + ", file=" + this.file + ", fileBody=" + this.fileBody + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.http.HttpParameter
 * JD-Core Version:    0.7.0.1
 */