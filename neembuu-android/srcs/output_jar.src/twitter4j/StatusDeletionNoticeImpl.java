package twitter4j;

import java.io.Serializable;
import twitter4j.internal.json.z_T4JInternalParseUtil;
import twitter4j.internal.org.json.JSONObject;

class StatusDeletionNoticeImpl
  implements StatusDeletionNotice, Serializable
{
  private static final long serialVersionUID = 1723338404242596062L;
  private long statusId;
  private long userId;
  
  StatusDeletionNoticeImpl(JSONObject paramJSONObject)
  {
    this.statusId = z_T4JInternalParseUtil.getLong("id", paramJSONObject);
    this.userId = z_T4JInternalParseUtil.getLong("user_id", paramJSONObject);
  }
  
  public int compareTo(StatusDeletionNotice paramStatusDeletionNotice)
  {
    long l = this.statusId - paramStatusDeletionNotice.getStatusId();
    int i;
    if (l < -2147483648L) {
      i = -2147483648;
    }
    for (;;)
    {
      return i;
      if (l > 2147483647L) {
        i = 2147483647;
      } else {
        i = (int)l;
      }
    }
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    for (;;)
    {
      return bool;
      if ((paramObject == null) || (getClass() != paramObject.getClass()))
      {
        bool = false;
      }
      else
      {
        StatusDeletionNoticeImpl localStatusDeletionNoticeImpl = (StatusDeletionNoticeImpl)paramObject;
        if (this.statusId != localStatusDeletionNoticeImpl.statusId) {
          bool = false;
        } else if (this.userId != localStatusDeletionNoticeImpl.userId) {
          bool = false;
        }
      }
    }
  }
  
  public long getStatusId()
  {
    return this.statusId;
  }
  
  public long getUserId()
  {
    return this.userId;
  }
  
  public int hashCode()
  {
    return 31 * (int)(this.statusId ^ this.statusId >>> 32) + (int)(this.userId ^ this.userId >>> 32);
  }
  
  public String toString()
  {
    return "StatusDeletionNoticeImpl{statusId=" + this.statusId + ", userId=" + this.userId + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.StatusDeletionNoticeImpl
 * JD-Core Version:    0.7.0.1
 */