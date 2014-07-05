package jp.co.asbit.pvstar;

import java.io.Serializable;

public class Category
  implements Serializable
{
  private static final long serialVersionUID = 4287234660242052765L;
  private int childrenCount = 0;
  private String dir;
  private int id;
  private String name;
  private int rank;
  
  public Category() {}
  
  public Category(int paramInt, String paramString1, String paramString2)
  {
    setId(paramInt);
    setDir(paramString1);
    setName(paramString2);
  }
  
  public int getChildrenCount()
  {
    return this.childrenCount;
  }
  
  public String getDir()
  {
    return this.dir;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public int getRank()
  {
    return this.rank;
  }
  
  public void setChildrenCount(int paramInt)
  {
    this.childrenCount = paramInt;
  }
  
  public void setDir(String paramString)
  {
    this.dir = paramString;
  }
  
  public void setId(int paramInt)
  {
    this.id = paramInt;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setRank(int paramInt)
  {
    this.rank = paramInt;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.Category
 * JD-Core Version:    0.7.0.1
 */