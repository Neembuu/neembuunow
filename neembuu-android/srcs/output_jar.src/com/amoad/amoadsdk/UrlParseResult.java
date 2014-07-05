package com.amoad.amoadsdk;

class UrlParseResult
  extends SyntaxSugar.M<Key, Object>
{
  private static final long serialVersionUID = 1624220459278058797L;
  boolean valid = false;
  
  static UrlParseResult build(boolean paramBoolean)
  {
    UrlParseResult localUrlParseResult = new UrlParseResult();
    localUrlParseResult.valid = paramBoolean;
    return localUrlParseResult;
  }
  
  public <T> UrlParseResult $(Key paramKey, T paramT)
  {
    super.$(paramKey, paramT);
    return this;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.amoad.amoadsdk.UrlParseResult
 * JD-Core Version:    0.7.0.1
 */