package jp.co.asbit.pvstar;

import android.graphics.Bitmap;
import java.io.Serializable;
import java.util.ArrayList;

public class Video
  implements Serializable
{
  private static final long serialVersionUID = 7543902043134311095L;
  private boolean checked;
  private String description;
  private String duration;
  private boolean error;
  private String id;
  private String searchEngine;
  private ArrayList<String> tag = new ArrayList(0);
  private String thumbnailUrl;
  private String title;
  private String userId;
  private String viewCount;
  
  public Video() {}
  
  public Video(String paramString1, String paramString2, Bitmap paramBitmap) {}
  
  public String getDescription()
  {
    return this.description;
  }
  
  public String getDuration()
  {
    return this.duration;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getSearchEngine()
  {
    return this.searchEngine;
  }
  
  public String getTag(int paramInt)
  {
    return (String)this.tag.get(paramInt);
  }
  
  public int getTagCount()
  {
    return this.tag.size();
  }
  
  public String getThumbnailUrl()
  {
    return this.thumbnailUrl;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public String getViewCount()
  {
    return this.viewCount;
  }
  
  public boolean isChecked()
  {
    return this.checked;
  }
  
  public boolean isError()
  {
    return this.error;
  }
  
  public void setChecked(boolean paramBoolean)
  {
    this.checked = paramBoolean;
  }
  
  public void setDescription(String paramString)
  {
    this.description = paramString;
  }
  
  public void setDuration(String paramString)
  {
    this.duration = paramString;
  }
  
  public void setError(boolean paramBoolean)
  {
    this.error = paramBoolean;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public void setSearchEngine(String paramString)
  {
    this.searchEngine = paramString;
  }
  
  public void setTag(String paramString)
  {
    this.tag.add(paramString);
  }
  
  public void setThumbnailUrl(String paramString)
  {
    this.thumbnailUrl = paramString;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
  
  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }
  
  public void setViewCount(String paramString)
  {
    this.viewCount = paramString;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.Video
 * JD-Core Version:    0.7.0.1
 */