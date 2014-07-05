package twitter4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import twitter4j.internal.http.HttpParameter;

public final class GeoQuery
  implements Serializable
{
  private static final long serialVersionUID = 927081526936169802L;
  private String accuracy = null;
  private String granularity = null;
  private String ip = null;
  private GeoLocation location;
  private int maxResults = -1;
  private String query = null;
  
  public GeoQuery(String paramString)
  {
    this.ip = paramString;
  }
  
  public GeoQuery(GeoLocation paramGeoLocation)
  {
    this.location = paramGeoLocation;
  }
  
  private void appendParameter(String paramString, double paramDouble, List<HttpParameter> paramList)
  {
    paramList.add(new HttpParameter(paramString, String.valueOf(paramDouble)));
  }
  
  private void appendParameter(String paramString, int paramInt, List<HttpParameter> paramList)
  {
    if (paramInt > 0) {
      paramList.add(new HttpParameter(paramString, String.valueOf(paramInt)));
    }
  }
  
  private void appendParameter(String paramString1, String paramString2, List<HttpParameter> paramList)
  {
    if (paramString2 != null) {
      paramList.add(new HttpParameter(paramString1, paramString2));
    }
  }
  
  public GeoQuery accuracy(String paramString)
  {
    setAccuracy(paramString);
    return this;
  }
  
  HttpParameter[] asHttpParameterArray()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.location != null)
    {
      appendParameter("lat", this.location.getLatitude(), localArrayList);
      appendParameter("long", this.location.getLongitude(), localArrayList);
    }
    if (this.ip != null) {
      appendParameter("ip", this.ip, localArrayList);
    }
    appendParameter("accuracy", this.accuracy, localArrayList);
    appendParameter("query", this.query, localArrayList);
    appendParameter("granularity", this.granularity, localArrayList);
    appendParameter("max_results", this.maxResults, localArrayList);
    return (HttpParameter[])localArrayList.toArray(new HttpParameter[localArrayList.size()]);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    GeoQuery localGeoQuery;
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
          localGeoQuery = (GeoQuery)paramObject;
          if (this.maxResults == localGeoQuery.maxResults) {
            break;
          }
          bool = false;
        }
      }
      if (this.accuracy != null)
      {
        if (this.accuracy.equals(localGeoQuery.accuracy)) {}
      }
      else {
        while (localGeoQuery.accuracy != null)
        {
          bool = false;
          break;
        }
      }
      if (this.granularity != null)
      {
        if (this.granularity.equals(localGeoQuery.granularity)) {}
      }
      else {
        while (localGeoQuery.granularity != null)
        {
          bool = false;
          break;
        }
      }
      if (this.ip != null)
      {
        if (this.ip.equals(localGeoQuery.ip)) {}
      }
      else {
        while (localGeoQuery.ip != null)
        {
          bool = false;
          break;
        }
      }
      if (this.location == null) {
        break;
      }
    } while (this.location.equals(localGeoQuery.location));
    for (;;)
    {
      bool = false;
      break;
      if (localGeoQuery.location == null) {
        break;
      }
    }
  }
  
  public String getAccuracy()
  {
    return this.accuracy;
  }
  
  public String getGranularity()
  {
    return this.granularity;
  }
  
  public String getIp()
  {
    return this.ip;
  }
  
  public GeoLocation getLocation()
  {
    return this.location;
  }
  
  public int getMaxResults()
  {
    return this.maxResults;
  }
  
  public String getQuery()
  {
    return this.query;
  }
  
  public GeoQuery granularity(String paramString)
  {
    setGranularity(paramString);
    return this;
  }
  
  public int hashCode()
  {
    int i = 0;
    int j;
    int m;
    label38:
    int n;
    if (this.location != null)
    {
      j = this.location.hashCode();
      int k = j * 31;
      if (this.ip == null) {
        break label106;
      }
      m = this.ip.hashCode();
      n = 31 * (k + m);
      if (this.accuracy == null) {
        break label112;
      }
    }
    label106:
    label112:
    for (int i1 = this.accuracy.hashCode();; i1 = 0)
    {
      int i2 = 31 * (n + i1);
      if (this.granularity != null) {
        i = this.granularity.hashCode();
      }
      return 31 * (i2 + i) + this.maxResults;
      j = 0;
      break;
      m = 0;
      break label38;
    }
  }
  
  public GeoQuery maxResults(int paramInt)
  {
    setMaxResults(paramInt);
    return this;
  }
  
  public void setAccuracy(String paramString)
  {
    this.accuracy = paramString;
  }
  
  public void setGranularity(String paramString)
  {
    this.granularity = paramString;
  }
  
  public void setMaxResults(int paramInt)
  {
    this.maxResults = paramInt;
  }
  
  public void setQuery(String paramString)
  {
    this.query = paramString;
  }
  
  public String toString()
  {
    return "GeoQuery{location=" + this.location + ", query='" + this.query + '\'' + ", ip='" + this.ip + '\'' + ", accuracy='" + this.accuracy + '\'' + ", granularity='" + this.granularity + '\'' + ", maxResults=" + this.maxResults + '}';
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.GeoQuery
 * JD-Core Version:    0.7.0.1
 */