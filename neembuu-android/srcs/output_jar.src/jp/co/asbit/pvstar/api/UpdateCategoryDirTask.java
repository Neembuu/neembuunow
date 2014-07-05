package jp.co.asbit.pvstar.api;

import java.util.ArrayList;
import jp.co.asbit.pvstar.Category;

public class UpdateCategoryDirTask
  extends UpdateCategoryDetailTask
{
  static final String CATEGORY_DIR_API = "http://pvstar.dooga.org/api2/categories/categories_%s/dir/%s";
  
  protected ArrayList<Category> doInBackground(String... paramVarArgs)
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = paramVarArgs[0];
    arrayOfObject[1] = paramVarArgs[1];
    String str = String.format("http://pvstar.dooga.org/api2/categories/categories_%s/dir/%s", arrayOfObject);
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
 * Qualified Name:     jp.co.asbit.pvstar.api.UpdateCategoryDirTask
 * JD-Core Version:    0.7.0.1
 */