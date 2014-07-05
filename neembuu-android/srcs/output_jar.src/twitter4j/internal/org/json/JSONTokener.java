package twitter4j.internal.org.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class JSONTokener
{
  private int character;
  private boolean eof;
  private int index;
  private int line;
  private char previous;
  private Reader reader;
  private boolean usePrevious;
  
  public JSONTokener(InputStream paramInputStream)
    throws JSONException
  {
    this(new InputStreamReader(paramInputStream));
  }
  
  public JSONTokener(Reader paramReader)
  {
    if (paramReader.markSupported()) {}
    for (;;)
    {
      this.reader = paramReader;
      this.eof = false;
      this.usePrevious = false;
      this.previous = '\000';
      this.index = 0;
      this.character = 1;
      this.line = 1;
      return;
      paramReader = new BufferedReader(paramReader);
    }
  }
  
  public JSONTokener(String paramString)
  {
    this(new StringReader(paramString));
  }
  
  public void back()
    throws JSONException
  {
    if ((this.usePrevious) || (this.index <= 0)) {
      throw new JSONException("Stepping back two steps is not supported");
    }
    this.index = (-1 + this.index);
    this.character = (-1 + this.character);
    this.usePrevious = true;
    this.eof = false;
  }
  
  public boolean end()
  {
    if ((this.eof) && (!this.usePrevious)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public boolean more()
    throws JSONException
  {
    next();
    if (end()) {}
    for (boolean bool = false;; bool = true)
    {
      return bool;
      back();
    }
  }
  
  public char next()
    throws JSONException
  {
    int i = 0;
    char c;
    if (this.usePrevious)
    {
      this.usePrevious = false;
      c = this.previous;
      this.index = (1 + this.index);
      if (this.previous != '\r') {
        break label114;
      }
      this.line = (1 + this.line);
      if (c != '\n') {
        break label109;
      }
      label56:
      this.character = i;
    }
    for (;;)
    {
      for (;;)
      {
        this.previous = c;
        return this.previous;
        try
        {
          int j = this.reader.read();
          c = j;
          if (c > 0) {
            break;
          }
          this.eof = true;
          c = '\000';
        }
        catch (IOException localIOException)
        {
          throw new JSONException(localIOException);
        }
      }
      label109:
      i = 1;
      break label56;
      label114:
      if (c == '\n')
      {
        this.line = (1 + this.line);
        this.character = 0;
      }
      else
      {
        this.character = (1 + this.character);
      }
    }
  }
  
  public char next(char paramChar)
    throws JSONException
  {
    char c = next();
    if (c != paramChar) {
      throw syntaxError("Expected '" + paramChar + "' and instead saw '" + c + "'");
    }
    return c;
  }
  
  public String next(int paramInt)
    throws JSONException
  {
    if (paramInt == 0) {}
    char[] arrayOfChar;
    for (String str = "";; str = new String(arrayOfChar))
    {
      return str;
      arrayOfChar = new char[paramInt];
      for (int i = 0; i < paramInt; i++)
      {
        arrayOfChar[i] = next();
        if (end()) {
          throw syntaxError("Substring bounds error");
        }
      }
    }
  }
  
  public char nextClean()
    throws JSONException
  {
    char c;
    do
    {
      c = next();
    } while ((c != 0) && (c <= ' '));
    return c;
  }
  
  public String nextString(char paramChar)
    throws JSONException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    for (;;)
    {
      char c1 = next();
      switch (c1)
      {
      default: 
        if (c1 == paramChar) {
          return localStringBuilder.toString();
        }
        break;
      case '\000': 
      case '\n': 
      case '\r': 
        throw syntaxError("Unterminated string");
      case '\\': 
        char c2 = next();
        switch (c2)
        {
        default: 
          throw syntaxError("Illegal escape.");
        case 'b': 
          localStringBuilder.append('\b');
          break;
        case 't': 
          localStringBuilder.append('\t');
          break;
        case 'n': 
          localStringBuilder.append('\n');
          break;
        case 'f': 
          localStringBuilder.append('\f');
          break;
        case 'r': 
          localStringBuilder.append('\r');
          break;
        case 'u': 
          localStringBuilder.append((char)Integer.parseInt(next(4), 16));
          break;
        case '"': 
        case '\'': 
        case '/': 
        case '\\': 
          localStringBuilder.append(c2);
        }
        break;
      }
      localStringBuilder.append(c1);
    }
  }
  
  public Object nextValue()
    throws JSONException
  {
    char c = nextClean();
    StringBuilder localStringBuilder;
    Object localObject;
    switch (c)
    {
    default: 
      localStringBuilder = new StringBuilder();
    case '"': 
    case '\'': 
      while ((c >= ' ') && (",:]}/\\\"[{;=#".indexOf(c) < 0))
      {
        localStringBuilder.append(c);
        c = next();
        continue;
        localObject = nextString(c);
      }
    }
    for (;;)
    {
      return localObject;
      back();
      localObject = new JSONObject(this);
      continue;
      back();
      localObject = new JSONArray(this);
      continue;
      back();
      String str = localStringBuilder.toString().trim();
      if (str.equals("")) {
        throw syntaxError("Missing value");
      }
      localObject = JSONObject.stringToValue(str);
    }
  }
  
  public JSONException syntaxError(String paramString)
  {
    return new JSONException(paramString + toString());
  }
  
  public String toString()
  {
    return " at " + this.index + " [character " + this.character + " line " + this.line + "]";
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.org.json.JSONTokener
 * JD-Core Version:    0.7.0.1
 */