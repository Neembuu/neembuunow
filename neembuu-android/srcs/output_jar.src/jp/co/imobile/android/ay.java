package jp.co.imobile.android;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

final class ay
{
  static final List a(List paramList, ba paramba)
  {
    ArrayList localArrayList = new ArrayList();
    int i = paramList.size();
    for (int j = 0;; j++)
    {
      if (j >= i) {
        return localArrayList;
      }
      Object localObject = paramList.get(j);
      if (paramba.a(localObject)) {
        localArrayList.add(localObject);
      }
    }
  }
  
  static final ax a(ax[] paramArrayOfax, Object paramObject)
  {
    az localaz = new az(paramObject);
    for (int i = 0;; i++)
    {
      if (i >= paramArrayOfax.length) {}
      ax localax;
      for (Object localObject = null;; localObject = localax)
      {
        return (ax)localObject;
        localax = paramArrayOfax[i];
        if (!localaz.a(localax)) {
          break;
        }
      }
    }
  }
  
  static final ax a(ax[] paramArrayOfax, Object paramObject, ax paramax)
  {
    ax localax = a(paramArrayOfax, paramObject);
    if (localax == null) {}
    for (;;)
    {
      return paramax;
      paramax = localax;
    }
  }
  
  static final boolean a(Collection paramCollection)
  {
    if ((paramCollection != null) && (paramCollection.size() != 0)) {}
    for (boolean bool = false;; bool = true) {
      return bool;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.ay
 * JD-Core Version:    0.7.0.1
 */