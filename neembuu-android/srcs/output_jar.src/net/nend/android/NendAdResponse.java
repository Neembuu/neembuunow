package net.nend.android;

final class NendAdResponse
  implements AdParameter
{
  private final String mClickUrl;
  private final String mImageUrl;
  private final int mReloadIntervalInSeconds;
  private final AdParameter.ViewType mViewType;
  private final String mWebViewUrl;
  
  private NendAdResponse(Builder paramBuilder)
  {
    switch (1.$SwitchMap$net$nend$android$AdParameter$ViewType[paramBuilder.mViewType.ordinal()])
    {
    default: 
      throw new IllegalArgumentException("Uknown view type.");
    case 1: 
      if ((paramBuilder.mImageUrl == null) || (paramBuilder.mImageUrl.length() == 0)) {
        throw new IllegalArgumentException("Image url is invalid.");
      }
      if ((paramBuilder.mClickUrl == null) || (paramBuilder.mClickUrl.length() == 0)) {
        throw new IllegalArgumentException("Click url is invalid");
      }
      this.mViewType = AdParameter.ViewType.ADVIEW;
      this.mImageUrl = paramBuilder.mImageUrl;
      this.mClickUrl = paramBuilder.mClickUrl;
      this.mWebViewUrl = null;
    }
    for (this.mReloadIntervalInSeconds = paramBuilder.mReloadIntervalInSeconds;; this.mReloadIntervalInSeconds = 0)
    {
      return;
      if ((paramBuilder.mWebViewUrl == null) || (paramBuilder.mWebViewUrl.length() == 0)) {
        throw new IllegalArgumentException("Web view url is invalid");
      }
      this.mViewType = AdParameter.ViewType.WEBVIEW;
      this.mImageUrl = null;
      this.mClickUrl = null;
      this.mWebViewUrl = paramBuilder.mWebViewUrl;
    }
  }
  
  public String getClickUrl()
  {
    return this.mClickUrl;
  }
  
  public int getHeight()
  {
    throw new UnsupportedOperationException();
  }
  
  public String getImageUrl()
  {
    return this.mImageUrl;
  }
  
  public int getReloadIntervalInSeconds()
  {
    return this.mReloadIntervalInSeconds;
  }
  
  public AdParameter.ViewType getViewType()
  {
    return this.mViewType;
  }
  
  public String getWebViewUrl()
  {
    return this.mWebViewUrl;
  }
  
  public int getWidth()
  {
    throw new UnsupportedOperationException();
  }
  
  static final class Builder
  {
    private String mClickUrl;
    private String mImageUrl;
    private int mReloadIntervalInSeconds;
    private AdParameter.ViewType mViewType = AdParameter.ViewType.NONE;
    private String mWebViewUrl;
    
    static
    {
      if (!NendAdResponse.class.desiredAssertionStatus()) {}
      for (boolean bool = true;; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }
    
    NendAdResponse build()
    {
      return new NendAdResponse(this, null);
    }
    
    Builder setClickUrl(String paramString)
    {
      if (paramString != null) {}
      for (this.mClickUrl = paramString.replaceAll(" ", "%20");; this.mClickUrl = null) {
        return this;
      }
    }
    
    Builder setImageUrl(String paramString)
    {
      if (paramString != null) {}
      for (this.mImageUrl = paramString.replaceAll(" ", "%20");; this.mImageUrl = null) {
        return this;
      }
    }
    
    Builder setReloadIntervalInSeconds(int paramInt)
    {
      this.mReloadIntervalInSeconds = paramInt;
      return this;
    }
    
    Builder setViewType(AdParameter.ViewType paramViewType)
    {
      assert (paramViewType != null);
      this.mViewType = paramViewType;
      return this;
    }
    
    Builder setWebViewUrl(String paramString)
    {
      if (paramString != null) {}
      for (this.mWebViewUrl = paramString.replaceAll(" ", "%20");; this.mWebViewUrl = null) {
        return this;
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     net.nend.android.NendAdResponse
 * JD-Core Version:    0.7.0.1
 */