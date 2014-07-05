package net.nend.android;

abstract interface AdParameter
{
  public abstract String getClickUrl();
  
  public abstract int getHeight();
  
  public abstract String getImageUrl();
  
  public abstract int getReloadIntervalInSeconds();
  
  public abstract ViewType getViewType();
  
  public abstract String getWebViewUrl();
  
  public abstract int getWidth();
  
  public static enum ViewType
  {
    static
    {
      ADVIEW = new ViewType("ADVIEW", 1);
      WEBVIEW = new ViewType("WEBVIEW", 2);
      ViewType[] arrayOfViewType = new ViewType[3];
      arrayOfViewType[0] = NONE;
      arrayOfViewType[1] = ADVIEW;
      arrayOfViewType[2] = WEBVIEW;
      $VALUES = arrayOfViewType;
    }
    
    private ViewType() {}
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.AdParameter
 * JD-Core Version:    0.7.0.1
 */