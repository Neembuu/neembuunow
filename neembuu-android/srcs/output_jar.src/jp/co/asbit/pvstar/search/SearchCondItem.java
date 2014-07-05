package jp.co.asbit.pvstar.search;

public class SearchCondItem
{
  public String key;
  public String value;
  
  public SearchCondItem(String paramString1, String paramString2)
  {
    this.key = paramString1;
    this.value = paramString2;
  }
  
  public String toString()
  {
    return this.value;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.search.SearchCondItem
 * JD-Core Version:    0.7.0.1
 */