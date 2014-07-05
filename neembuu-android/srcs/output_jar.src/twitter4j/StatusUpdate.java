package twitter4j;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import twitter4j.internal.http.HttpParameter;

public final class StatusUpdate
  implements Serializable
{
  private static final long serialVersionUID = -3595502688477609916L;
  private boolean displayCoordinates = true;
  private long inReplyToStatusId = -1L;
  private GeoLocation location = null;
  private transient InputStream mediaBody;
  private File mediaFile;
  private String mediaName;
  private String placeId = null;
  private boolean possiblySensitive;
  private String status;
  
  public StatusUpdate(String paramString)
  {
    this.status = paramString;
  }
  
  private void appendParameter(String paramString, double paramDouble, List<HttpParameter> paramList)
  {
    paramList.add(new HttpParameter(paramString, String.valueOf(paramDouble)));
  }
  
  private void appendParameter(String paramString, long paramLong, List<HttpParameter> paramList)
  {
    paramList.add(new HttpParameter(paramString, String.valueOf(paramLong)));
  }
  
  private void appendParameter(String paramString1, String paramString2, List<HttpParameter> paramList)
  {
    if (paramString2 != null) {
      paramList.add(new HttpParameter(paramString1, paramString2));
    }
  }
  
  HttpParameter[] asHttpParameterArray()
  {
    ArrayList localArrayList = new ArrayList();
    appendParameter("status", this.status, localArrayList);
    if (-1L != this.inReplyToStatusId) {
      appendParameter("in_reply_to_status_id", this.inReplyToStatusId, localArrayList);
    }
    if (this.location != null)
    {
      appendParameter("lat", this.location.getLatitude(), localArrayList);
      appendParameter("long", this.location.getLongitude(), localArrayList);
    }
    appendParameter("place_id", this.placeId, localArrayList);
    if (!this.displayCoordinates) {
      appendParameter("display_coordinates", "false", localArrayList);
    }
    if (this.mediaFile != null)
    {
      localArrayList.add(new HttpParameter("media[]", this.mediaFile));
      localArrayList.add(new HttpParameter("possibly_sensitive", this.possiblySensitive));
    }
    for (;;)
    {
      return (HttpParameter[])localArrayList.toArray(new HttpParameter[localArrayList.size()]);
      if ((this.mediaName != null) && (this.mediaBody != null))
      {
        localArrayList.add(new HttpParameter("media[]", this.mediaName, this.mediaBody));
        localArrayList.add(new HttpParameter("possibly_sensitive", this.possiblySensitive));
      }
    }
  }
  
  public StatusUpdate displayCoordinates(boolean paramBoolean)
  {
    setDisplayCoordinates(paramBoolean);
    return this;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    StatusUpdate localStatusUpdate;
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
          localStatusUpdate = (StatusUpdate)paramObject;
          if (this.displayCoordinates != localStatusUpdate.displayCoordinates)
          {
            bool = false;
          }
          else if (this.inReplyToStatusId != localStatusUpdate.inReplyToStatusId)
          {
            bool = false;
          }
          else
          {
            if (this.possiblySensitive == localStatusUpdate.possiblySensitive) {
              break;
            }
            bool = false;
          }
        }
      }
      if (this.location != null)
      {
        if (this.location.equals(localStatusUpdate.location)) {}
      }
      else {
        while (localStatusUpdate.location != null)
        {
          bool = false;
          break;
        }
      }
      if (this.mediaBody != null)
      {
        if (this.mediaBody.equals(localStatusUpdate.mediaBody)) {}
      }
      else {
        while (localStatusUpdate.mediaBody != null)
        {
          bool = false;
          break;
        }
      }
      if (this.mediaFile != null)
      {
        if (this.mediaFile.equals(localStatusUpdate.mediaFile)) {}
      }
      else {
        while (localStatusUpdate.mediaFile != null)
        {
          bool = false;
          break;
        }
      }
      if (this.mediaName != null)
      {
        if (this.mediaName.equals(localStatusUpdate.mediaName)) {}
      }
      else {
        while (localStatusUpdate.mediaName != null)
        {
          bool = false;
          break;
        }
      }
      if (this.placeId != null)
      {
        if (this.placeId.equals(localStatusUpdate.placeId)) {}
      }
      else {
        while (localStatusUpdate.placeId != null)
        {
          bool = false;
          break;
        }
      }
      if (this.status == null) {
        break;
      }
    } while (this.status.equals(localStatusUpdate.status));
    for (;;)
    {
      bool = false;
      break;
      if (localStatusUpdate.status == null) {
        break;
      }
    }
  }
  
  public long getInReplyToStatusId()
  {
    return this.inReplyToStatusId;
  }
  
  public GeoLocation getLocation()
  {
    return this.location;
  }
  
  public String getPlaceId()
  {
    return this.placeId;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public int hashCode()
  {
    int i = 1;
    int j = 0;
    int k;
    int n;
    label58:
    int i2;
    label84:
    int i4;
    label104:
    label121:
    int i7;
    label146:
    int i8;
    if (this.status != null)
    {
      k = this.status.hashCode();
      int m = 31 * (k * 31 + (int)(this.inReplyToStatusId ^ this.inReplyToStatusId >>> 32));
      if (this.location == null) {
        break label207;
      }
      n = this.location.hashCode();
      int i1 = 31 * (m + n);
      if (this.placeId == null) {
        break label213;
      }
      i2 = this.placeId.hashCode();
      int i3 = 31 * (i1 + i2);
      if (!this.displayCoordinates) {
        break label219;
      }
      i4 = i;
      int i5 = 31 * (i3 + i4);
      if (!this.possiblySensitive) {
        break label225;
      }
      int i6 = 31 * (i5 + i);
      if (this.mediaName == null) {
        break label230;
      }
      i7 = this.mediaName.hashCode();
      i8 = 31 * (i6 + i7);
      if (this.mediaBody == null) {
        break label236;
      }
    }
    label207:
    label213:
    label219:
    label225:
    label230:
    label236:
    for (int i9 = this.mediaBody.hashCode();; i9 = 0)
    {
      int i10 = 31 * (i8 + i9);
      if (this.mediaFile != null) {
        j = this.mediaFile.hashCode();
      }
      return i10 + j;
      k = 0;
      break;
      n = 0;
      break label58;
      i2 = 0;
      break label84;
      i4 = 0;
      break label104;
      i = 0;
      break label121;
      i7 = 0;
      break label146;
    }
  }
  
  public StatusUpdate inReplyToStatusId(long paramLong)
  {
    setInReplyToStatusId(paramLong);
    return this;
  }
  
  public boolean isDisplayCoordinates()
  {
    return this.displayCoordinates;
  }
  
  public boolean isPossiblySensitive()
  {
    return this.possiblySensitive;
  }
  
  boolean isWithMedia()
  {
    if ((this.mediaFile != null) || (this.mediaName != null)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public StatusUpdate location(GeoLocation paramGeoLocation)
  {
    setLocation(paramGeoLocation);
    return this;
  }
  
  public StatusUpdate media(File paramFile)
  {
    setMedia(paramFile);
    return this;
  }
  
  public StatusUpdate media(String paramString, InputStream paramInputStream)
  {
    setMedia(paramString, paramInputStream);
    return this;
  }
  
  public StatusUpdate placeId(String paramString)
  {
    setPlaceId(paramString);
    return this;
  }
  
  public StatusUpdate possiblySensitive(boolean paramBoolean)
  {
    setPossiblySensitive(paramBoolean);
    return this;
  }
  
  public void setDisplayCoordinates(boolean paramBoolean)
  {
    this.displayCoordinates = paramBoolean;
  }
  
  public void setInReplyToStatusId(long paramLong)
  {
    this.inReplyToStatusId = paramLong;
  }
  
  public void setLocation(GeoLocation paramGeoLocation)
  {
    this.location = paramGeoLocation;
  }
  
  public void setMedia(File paramFile)
  {
    this.mediaFile = paramFile;
  }
  
  public void setMedia(String paramString, InputStream paramInputStream)
  {
    this.mediaName = paramString;
    this.mediaBody = paramInputStream;
  }
  
  public void setPlaceId(String paramString)
  {
    this.placeId = paramString;
  }
  
  public void setPossiblySensitive(boolean paramBoolean)
  {
    this.possiblySensitive = paramBoolean;
  }
  
  public String toString()
  {
    return "StatusUpdate{status='" + this.status + '\'' + ", inReplyToStatusId=" + this.inReplyToStatusId + ", location=" + this.location + ", placeId='" + this.placeId + '\'' + ", displayCoordinates=" + this.displayCoordinates + ", possiblySensitive=" + this.possiblySensitive + ", mediaName='" + this.mediaName + '\'' + ", mediaBody=" + this.mediaBody + ", mediaFile=" + this.mediaFile + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.StatusUpdate
 * JD-Core Version:    0.7.0.1
 */