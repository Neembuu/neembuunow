package jp.co.asbit.pvstar;

public class OptionMenuItem
{
  private int leftIcon;
  private int title;
  
  public OptionMenuItem(int paramInt1, int paramInt2)
  {
    setTitle(paramInt1);
    setLeftIcon(paramInt2);
  }
  
  public int getLeftIcon()
  {
    return this.leftIcon;
  }
  
  public int getTitle()
  {
    return this.title;
  }
  
  public void setLeftIcon(int paramInt)
  {
    this.leftIcon = paramInt;
  }
  
  public void setTitle(int paramInt)
  {
    this.title = paramInt;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.OptionMenuItem
 * JD-Core Version:    0.7.0.1
 */