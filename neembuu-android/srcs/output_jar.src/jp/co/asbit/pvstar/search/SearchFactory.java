package jp.co.asbit.pvstar.search;

import java.util.HashMap;

public class SearchFactory
{
  static HashMap<String, Search> searchObj = new HashMap();
  
  public static void clear()
  {
    searchObj = new HashMap();
  }
  
  public static Search factory(String paramString)
  {
    if (searchObj.containsKey(paramString)) {}
    for (Search localSearch = (Search)searchObj.get(paramString);; localSearch = (Search)searchObj.get(paramString))
    {
      return localSearch;
      searchObj.put(paramString, new Search());
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.search.SearchFactory
 * JD-Core Version:    0.7.0.1
 */