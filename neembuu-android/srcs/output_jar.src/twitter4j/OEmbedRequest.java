package twitter4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import twitter4j.internal.http.HttpParameter;
import twitter4j.internal.util.z_T4JInternalStringUtil;

public final class OEmbedRequest
  implements Serializable
{
  private static final long serialVersionUID = -4330607167106242987L;
  private Align align = Align.NONE;
  private boolean hideMedia = true;
  private boolean hideThread = true;
  private String lang;
  private int maxWidth;
  private boolean omitScript = false;
  private String[] related = new String[0];
  private final long statusId;
  private final String url;
  
  public OEmbedRequest(long paramLong, String paramString)
  {
    this.statusId = paramLong;
    this.url = paramString;
  }
  
  private void appendParameter(String paramString, long paramLong, List<HttpParameter> paramList)
  {
    if (0L <= paramLong) {
      paramList.add(new HttpParameter(paramString, String.valueOf(paramLong)));
    }
  }
  
  private void appendParameter(String paramString1, String paramString2, List<HttpParameter> paramList)
  {
    if (paramString2 != null) {
      paramList.add(new HttpParameter(paramString1, paramString2));
    }
  }
  
  public OEmbedRequest HideMedia(boolean paramBoolean)
  {
    this.hideMedia = paramBoolean;
    return this;
  }
  
  public OEmbedRequest HideThread(boolean paramBoolean)
  {
    this.hideThread = paramBoolean;
    return this;
  }
  
  public OEmbedRequest MaxWidth(int paramInt)
  {
    this.maxWidth = paramInt;
    return this;
  }
  
  public OEmbedRequest align(Align paramAlign)
  {
    this.align = paramAlign;
    return this;
  }
  
  HttpParameter[] asHttpParameterArray()
  {
    ArrayList localArrayList = new ArrayList(12);
    appendParameter("id", this.statusId, localArrayList);
    appendParameter("url", this.url, localArrayList);
    appendParameter("maxwidth", this.maxWidth, localArrayList);
    localArrayList.add(new HttpParameter("hide_media", this.hideMedia));
    localArrayList.add(new HttpParameter("hide_thread", this.hideThread));
    localArrayList.add(new HttpParameter("omit_script", this.omitScript));
    localArrayList.add(new HttpParameter("align", this.align.name().toLowerCase()));
    if (this.related.length > 0) {
      appendParameter("related", z_T4JInternalStringUtil.join(this.related), localArrayList);
    }
    appendParameter("lang", this.lang, localArrayList);
    return (HttpParameter[])localArrayList.toArray(new HttpParameter[localArrayList.size()]);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    OEmbedRequest localOEmbedRequest;
    do
    {
      for (;;)
      {
        return bool;
        if ((paramObject == null) || (getClass() != paramObject.getClass()))
        {
          bool = false;
        }
        else
        {
          localOEmbedRequest = (OEmbedRequest)paramObject;
          if (this.hideMedia != localOEmbedRequest.hideMedia)
          {
            bool = false;
          }
          else if (this.hideThread != localOEmbedRequest.hideThread)
          {
            bool = false;
          }
          else if (this.maxWidth != localOEmbedRequest.maxWidth)
          {
            bool = false;
          }
          else if (this.omitScript != localOEmbedRequest.omitScript)
          {
            bool = false;
          }
          else if (this.statusId != localOEmbedRequest.statusId)
          {
            bool = false;
          }
          else if (this.align != localOEmbedRequest.align)
          {
            bool = false;
          }
          else
          {
            if (this.lang != null)
            {
              if (this.lang.equals(localOEmbedRequest.lang)) {}
            }
            else {
              while (localOEmbedRequest.lang != null)
              {
                bool = false;
                break;
              }
            }
            if (Arrays.equals(this.related, localOEmbedRequest.related)) {
              break;
            }
            bool = false;
          }
        }
      }
      if (this.url == null) {
        break;
      }
    } while (this.url.equals(localOEmbedRequest.url));
    for (;;)
    {
      bool = false;
      break;
      if (localOEmbedRequest.url == null) {
        break;
      }
    }
  }
  
  public int hashCode()
  {
    int i = 1;
    int j = 0;
    int k = 31 * (int)(this.statusId ^ this.statusId >>> 32);
    int m;
    int i1;
    label64:
    int i3;
    label84:
    label101:
    int i6;
    label126:
    int i7;
    if (this.url != null)
    {
      m = this.url.hashCode();
      int n = 31 * (31 * (k + m) + this.maxWidth);
      if (!this.hideMedia) {
        break label188;
      }
      i1 = i;
      int i2 = 31 * (n + i1);
      if (!this.hideThread) {
        break label194;
      }
      i3 = i;
      int i4 = 31 * (i2 + i3);
      if (!this.omitScript) {
        break label200;
      }
      int i5 = 31 * (i4 + i);
      if (this.align == null) {
        break label205;
      }
      i6 = this.align.hashCode();
      i7 = 31 * (i5 + i6);
      if (this.related == null) {
        break label211;
      }
    }
    label188:
    label194:
    label200:
    label205:
    label211:
    for (int i8 = Arrays.hashCode(this.related);; i8 = 0)
    {
      int i9 = 31 * (i7 + i8);
      if (this.lang != null) {
        j = this.lang.hashCode();
      }
      return i9 + j;
      m = 0;
      break;
      i1 = 0;
      break label64;
      i3 = 0;
      break label84;
      i = 0;
      break label101;
      i6 = 0;
      break label126;
    }
  }
  
  public OEmbedRequest lang(String paramString)
  {
    this.lang = paramString;
    return this;
  }
  
  public OEmbedRequest omitScript(boolean paramBoolean)
  {
    this.omitScript = paramBoolean;
    return this;
  }
  
  public OEmbedRequest related(String[] paramArrayOfString)
  {
    this.related = paramArrayOfString;
    return this;
  }
  
  public void setAlign(Align paramAlign)
  {
    this.align = paramAlign;
  }
  
  public void setHideMedia(boolean paramBoolean)
  {
    this.hideMedia = paramBoolean;
  }
  
  public void setHideThread(boolean paramBoolean)
  {
    this.hideThread = paramBoolean;
  }
  
  public void setLang(String paramString)
  {
    this.lang = paramString;
  }
  
  public void setMaxWidth(int paramInt)
  {
    this.maxWidth = paramInt;
  }
  
  public void setOmitScript(boolean paramBoolean)
  {
    this.omitScript = paramBoolean;
  }
  
  public void setRelated(String[] paramArrayOfString)
  {
    this.related = paramArrayOfString;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("OEmbedRequest{statusId=").append(this.statusId).append(", url='").append(this.url).append('\'').append(", maxWidth=").append(this.maxWidth).append(", hideMedia=").append(this.hideMedia).append(", hideThread=").append(this.hideThread).append(", omitScript=").append(this.omitScript).append(", align=").append(this.align).append(", related=");
    if (this.related == null) {}
    for (Object localObject = null;; localObject = Arrays.asList(this.related)) {
      return localObject + ", lang='" + this.lang + '\'' + '}';
    }
  }
  
  public static enum Align
  {
    static
    {
      CENTER = new Align("CENTER", 1);
      RIGHT = new Align("RIGHT", 2);
      NONE = new Align("NONE", 3);
      Align[] arrayOfAlign = new Align[4];
      arrayOfAlign[0] = LEFT;
      arrayOfAlign[1] = CENTER;
      arrayOfAlign[2] = RIGHT;
      arrayOfAlign[3] = NONE;
      $VALUES = arrayOfAlign;
    }
    
    private Align() {}
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.OEmbedRequest
 * JD-Core Version:    0.7.0.1
 */