package com.amoad.amoadsdk;

class TypeCastException
  extends RuntimeException
{
  private static final String MSG_PATTERN_1 = "ERROR: Type mismatch: fromValue='%s' fromClass='%s' toClass='%s'";
  private static final String MSG_PATTERN_2 = "ERROR: Type mismatch: fromValue='%s' fromClass='%s' toClass='%s'\n       Root cause: %s";
  private static final long serialVersionUID = 1L;
  
  public <U> TypeCastException(Class<U> paramClass, Object paramObject) {}
  
  public <U> TypeCastException(Throwable paramThrowable, Class<U> paramClass, Object paramObject) {}
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.TypeCastException
 * JD-Core Version:    0.7.0.1
 */