package jp.co.asbit.pvstar.search;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Ranking
{
  protected ArrayList<SearchCondItem> categories;
  protected String category;
  protected String order;
  protected ArrayList<SearchCondItem> orders;
  protected String period;
  protected ArrayList<SearchCondItem> periods;
  
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
    if ((this.orders != null) && (this.periods != null) && (this.categories != null)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public ArrayList<SearchCondItem> getCategories()
  {
    return this.categories;
  }
  
  public String getCategory()
  {
    return this.category;
  }
  
  public int getCategoryIndex(String paramString)
  {
    return getIndex(this.categories, paramString);
  }
  
  public String getCategoryKey(int paramInt)
  {
    return ((SearchCondItem)this.categories.get(paramInt)).key;
  }
  
  public String getCategoryName(int paramInt)
  {
    return ((SearchCondItem)this.categories.get(paramInt)).value;
  }
  
  public String[] getCategoryNames()
  {
    return getValues(this.categories);
  }
  
  public int getCategorySize()
  {
    return this.categories.size();
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
  
  public String getPeriod()
  {
    return this.period;
  }
  
  public int getPeriodIndex(String paramString)
  {
    return getIndex(this.periods, paramString);
  }
  
  public String getPeriodKey(int paramInt)
  {
    return ((SearchCondItem)this.periods.get(paramInt)).key;
  }
  
  public String getPeriodName(int paramInt)
  {
    return ((SearchCondItem)this.periods.get(paramInt)).value;
  }
  
  public String[] getPeriodNames()
  {
    return getValues(this.periods);
  }
  
  public int getPeriodSize()
  {
    return this.periods.size();
  }
  
  public ArrayList<SearchCondItem> getPeriods()
  {
    return this.periods;
  }
  
  public abstract String getUrl(int paramInt);
  
  public abstract boolean loadVariables();
  
  public void setCategories(ArrayList<SearchCondItem> paramArrayList)
  {
    this.categories = paramArrayList;
  }
  
  public void setCategory(String paramString)
  {
    this.category = paramString;
  }
  
  public void setOrder(String paramString)
  {
    this.order = paramString;
  }
  
  public void setOrders(ArrayList<SearchCondItem> paramArrayList)
  {
    this.orders = paramArrayList;
  }
  
  public void setPeriod(String paramString)
  {
    this.period = paramString;
  }
  
  public void setPeriods(ArrayList<SearchCondItem> paramArrayList)
  {
    this.periods = paramArrayList;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.search.Ranking
 * JD-Core Version:    0.7.0.1
 */