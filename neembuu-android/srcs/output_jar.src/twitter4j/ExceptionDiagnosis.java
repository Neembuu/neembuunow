package twitter4j;

import java.io.Serializable;

final class ExceptionDiagnosis
  implements Serializable
{
  private static final long serialVersionUID = 453958937114285988L;
  String hexString = "";
  int lineNumberHash;
  int stackLineHash;
  Throwable th;
  
  ExceptionDiagnosis(Throwable paramThrowable)
  {
    this(paramThrowable, new String[0]);
  }
  
  ExceptionDiagnosis(Throwable paramThrowable, String[] paramArrayOfString)
  {
    this.th = paramThrowable;
    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
    this.stackLineHash = 0;
    this.lineNumberHash = 0;
    int i = -1 + arrayOfStackTraceElement.length;
    if (i >= 0)
    {
      StackTraceElement localStackTraceElement = arrayOfStackTraceElement[i];
      int j = paramArrayOfString.length;
      for (int k = 0;; k++) {
        if (k < j)
        {
          String str = paramArrayOfString[k];
          if (localStackTraceElement.getClassName().startsWith(str))
          {
            this.stackLineHash = (localStackTraceElement.getClassName().hashCode() + localStackTraceElement.getMethodName().hashCode() + 31 * this.stackLineHash);
            this.lineNumberHash = (31 * this.lineNumberHash + localStackTraceElement.getLineNumber());
          }
        }
        else
        {
          i--;
          break;
        }
      }
    }
    this.hexString = (this.hexString + toHexString(this.stackLineHash) + "-" + toHexString(this.lineNumberHash));
    if (paramThrowable.getCause() != null) {
      this.hexString = (this.hexString + " " + new ExceptionDiagnosis(paramThrowable.getCause(), paramArrayOfString).asHexString());
    }
  }
  
  private String toHexString(int paramInt)
  {
    String str = "0000000" + Integer.toHexString(paramInt);
    return str.substring(-8 + str.length(), str.length());
  }
  
  String asHexString()
  {
    return this.hexString;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    for (;;)
    {
      return bool;
      if ((paramObject == null) || (getClass() != paramObject.getClass()))
      {
        bool = false;
      }
      else
      {
        ExceptionDiagnosis localExceptionDiagnosis = (ExceptionDiagnosis)paramObject;
        if (this.lineNumberHash != localExceptionDiagnosis.lineNumberHash) {
          bool = false;
        } else if (this.stackLineHash != localExceptionDiagnosis.stackLineHash) {
          bool = false;
        }
      }
    }
  }
  
  int getLineNumberHash()
  {
    return this.lineNumberHash;
  }
  
  String getLineNumberHashAsHex()
  {
    return toHexString(this.lineNumberHash);
  }
  
  int getStackLineHash()
  {
    return this.stackLineHash;
  }
  
  String getStackLineHashAsHex()
  {
    return toHexString(this.stackLineHash);
  }
  
  public int hashCode()
  {
    return 31 * this.stackLineHash + this.lineNumberHash;
  }
  
  public String toString()
  {
    return "ExceptionDiagnosis{stackLineHash=" + this.stackLineHash + ", lineNumberHash=" + this.lineNumberHash + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.ExceptionDiagnosis
 * JD-Core Version:    0.7.0.1
 */