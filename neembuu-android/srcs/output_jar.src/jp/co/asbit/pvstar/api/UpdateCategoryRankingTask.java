package jp.co.asbit.pvstar.api;

import java.util.ArrayList;
import jp.co.asbit.pvstar.Category;

public class UpdateCategoryRankingTask
  extends UpdateCategoryDetailTask
{
  private static final String CATEGORY_RANKING_API = "http://pvstar.dooga.org/api2/categories/categories_%s/ranking";
  
  protected ArrayList<Category> doInBackground(String... paramVarArgs)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramVarArgs[0];
    String str = String.format("http://pvstar.dooga.org/api2/categories/categories_%s/ranking", arrayOfObject);
    try
    {
      ArrayList localArrayList = getCategories(str);
      return localArrayList;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.UpdateCategoryRankingTask
 * JD-Core Version:    0.7.0.1
 */