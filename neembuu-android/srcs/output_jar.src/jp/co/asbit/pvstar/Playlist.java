package jp.co.asbit.pvstar;

import java.io.Serializable;

public class Playlist
  implements Serializable
{
  public static final int CHANNEL = 2;
  public static final int PLAYLIST = 1;
  private static final long serialVersionUID = -5233778578116866602L;
  private String description;
  private String id;
  private int listType;
  private String searchEngine;
  private String thumbnailUrl;
  private String title;
  private int videoCount;
  
  public String getDescription()
  {
    return this.description;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public int getListType()
  {
    return this.listType;
  }
  
  public String getSearchEngine()
  {
    return this.searchEngine;
  }
  
  public String getThumbnailUrl()
  {
    return this.thumbnailUrl;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public int getVideoCount()
  {
    return this.videoCount;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public void setListType(int paramInt)
  {
    this.listType = paramInt;
  }
  
  public void setSearchEngine(String paramString)
  {
    this.searchEngine = paramString;
  }
  
  public void setThumbnailUrl(String paramString)
  {
    this.thumbnailUrl = paramString;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
  
  public void setVideoCount(int paramInt)
  {
    this.videoCount = paramInt;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.Playlist
 * JD-Core Version:    0.7.0.1
 */