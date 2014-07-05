package jp.co.asbit.pvstar.search;

import android.net.Uri;
import android.net.Uri.Builder;
import java.util.ArrayList;
import java.util.Iterator;

public class Search
{
  private static final String AUTHORITY = "pvstar.dooga.org";
  private static final String SCHEME = "http";
  private static final String SEARCH_PATH = "/api2/searches/%s/";
  protected String order;
  protected ArrayList<SearchCondItem> orders;
  
  private int getIndex(ArrayList<SearchCondItem> paramArrayList, String paramString)
  {
    int i = paramArrayList.size();
    for (int j = 0;; j++)
    {
      if (j >= i) {
        j = 0;
      }
      while (((SearchCondItem)paramArrayList.get(j)).key.equals(paramString)) {
        return j;
      }
    }
  }
  
  private String[] getValues(ArrayList<SearchCondItem> paramArrayList)
  {
    int i = paramArrayList.size();
    int j = 0;
    String[] arrayOfString = new String[i];
    Iterator localIterator = paramArrayList.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return arrayOfString;
      }
      int k = j + 1;
      arrayOfString[j] = ((SearchCondItem)localIterator.next()).value;
      j = k;
    }
  }
  
  public boolean choicesEnable()
  {
    if (this.orders != null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public String getOrder()
  {
    return this.order;
  }
  
  public int getOrderIndex(String paramString)
  {
    return getIndex(this.orders, paramString);
  }
  
  public String getOrderKey(int paramInt)
  {
    return ((SearchCondItem)this.orders.get(paramInt)).key;
  }
  
  public String getOrderName(int paramInt)
  {
    return ((SearchCondItem)this.orders.get(paramInt)).value;
  }
  
  public String[] getOrderNames()
  {
    return getValues(this.orders);
  }
  
  public int getOrderSize()
  {
    return this.orders.size();
  }
  
  public ArrayList<SearchCondItem> getOrders()
  {
    return this.orders;
  }
  
  public String getUrl(String paramString1, String paramString2, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Uri.Builder localBuilder = new Uri.Builder();
    localBuilder.scheme("http");
    localBuilder.encodedAuthority("pvstar.dooga.org");
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString1;
    localBuilder.path(String.format("/api2/searches/%s/", arrayOfObject));
    localBuilder.appendQueryParameter("query", paramString2);
    if (this.order != null) {
      localBuilder.appendQueryParameter("order", this.order);
    }
    localBuilder.appendQueryParameter("page", String.valueOf(paramInt1));
    localBuilder.appendQueryParameter("per_page", String.valueOf(paramInt2));
    if (paramBoolean) {}
    for (String str = "1";; str = "0")
    {
      localBuilder.appendQueryParameter("adult_thru", str);
      return localBuilder.build().toString();
    }
  }
  
  public void setOrder(String paramString)
  {
    this.order = paramString;
  }
  
  public void setOrders(ArrayList<SearchCondItem> paramArrayList)
  {
    this.orders = paramArrayList;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.search.Search
 * JD-Core Version:    0.7.0.1
 */