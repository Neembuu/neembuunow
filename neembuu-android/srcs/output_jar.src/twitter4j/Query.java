package twitter4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import twitter4j.internal.http.HttpParameter;

public final class Query
  implements Serializable
{
  public static final String KILOMETERS = "km";
  public static final String MILES = "mi";
  public static final String MIXED = "mixed";
  public static final String POPULAR = "popular";
  public static final String RECENT = "recent";
  private static HttpParameter WITH_TWITTER_USER_ID = new HttpParameter("with_twitter_user_id", "true");
  private static final long serialVersionUID = -8108425822233599808L;
  private int count = -1;
  private String geocode = null;
  private String lang = null;
  private String locale = null;
  private long maxId = -1L;
  private String nextPageQuery = null;
  private String query = null;
  private String resultType = null;
  private String since = null;
  private long sinceId = -1L;
  private String until = null;
  
  public Query() {}
  
  public Query(String paramString)
  {
    this.query = paramString;
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
  
  private static Query createWithNextPageQuery(String paramString)
  {
    Query localQuery = new Query();
    localQuery.nextPageQuery = paramString;
    return localQuery;
  }
  
  HttpParameter[] asHttpParameterArray()
  {
    ArrayList localArrayList = new ArrayList(12);
    appendParameter("q", this.query, localArrayList);
    appendParameter("lang", this.lang, localArrayList);
    appendParameter("locale", this.locale, localArrayList);
    appendParameter("max_id", this.maxId, localArrayList);
    appendParameter("count", this.count, localArrayList);
    appendParameter("since", this.since, localArrayList);
    appendParameter("since_id", this.sinceId, localArrayList);
    appendParameter("geocode", this.geocode, localArrayList);
    appendParameter("until", this.until, localArrayList);
    appendParameter("result_type", this.resultType, localArrayList);
    localArrayList.add(WITH_TWITTER_USER_ID);
    return (HttpParameter[])localArrayList.toArray(new HttpParameter[localArrayList.size()]);
  }
  
  public Query count(int paramInt)
  {
    setCount(paramInt);
    return this;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (this == paramObject) {}
    Query localQuery;
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
          localQuery = (Query)paramObject;
          if (this.maxId != localQuery.maxId)
          {
            bool = false;
          }
          else if (this.count != localQuery.count)
          {
            bool = false;
          }
          else
          {
            if (this.sinceId == localQuery.sinceId) {
              break;
            }
            bool = false;
          }
        }
      }
      if (this.geocode != null)
      {
        if (this.geocode.equals(localQuery.geocode)) {}
      }
      else {
        while (localQuery.geocode != null)
        {
          bool = false;
          break;
        }
      }
      if (this.lang != null)
      {
        if (this.lang.equals(localQuery.lang)) {}
      }
      else {
        while (localQuery.lang != null)
        {
          bool = false;
          break;
        }
      }
      if (this.locale != null)
      {
        if (this.locale.equals(localQuery.locale)) {}
      }
      else {
        while (localQuery.locale != null)
        {
          bool = false;
          break;
        }
      }
      if (this.nextPageQuery != null)
      {
        if (this.nextPageQuery.equals(localQuery.nextPageQuery)) {}
      }
      else {
        while (localQuery.nextPageQuery != null)
        {
          bool = false;
          break;
        }
      }
      if (this.query != null)
      {
        if (this.query.equals(localQuery.query)) {}
      }
      else {
        while (localQuery.query != null)
        {
          bool = false;
          break;
        }
      }
      if (this.resultType != null)
      {
        if (this.resultType.equals(localQuery.resultType)) {}
      }
      else {
        while (localQuery.resultType != null)
        {
          bool = false;
          break;
        }
      }
      if (this.since != null)
      {
        if (this.since.equals(localQuery.since)) {}
      }
      else {
        while (localQuery.since != null)
        {
          bool = false;
          break;
        }
      }
      if (this.until == null) {
        break;
      }
    } while (this.until.equals(localQuery.until));
    for (;;)
    {
      bool = false;
      break;
      if (localQuery.until == null) {
        break;
      }
    }
  }
  
  public Query geoCode(GeoLocation paramGeoLocation, double paramDouble, String paramString)
  {
    setGeoCode(paramGeoLocation, paramDouble, paramString);
    return this;
  }
  
  public int getCount()
  {
    return this.count;
  }
  
  public String getGeocode()
  {
    return this.geocode;
  }
  
  public String getLang()
  {
    return this.lang;
  }
  
  public String getLocale()
  {
    return this.locale;
  }
  
  public long getMaxId()
  {
    return this.maxId;
  }
  
  public String getQuery()
  {
    return this.query;
  }
  
  public String getResultType()
  {
    return this.resultType;
  }
  
  public String getSince()
  {
    return this.since;
  }
  
  public long getSinceId()
  {
    return this.sinceId;
  }
  
  public String getUntil()
  {
    return this.until;
  }
  
  public int hashCode()
  {
    int i = 0;
    int j;
    int m;
    label38:
    int i1;
    label63:
    int i3;
    label114:
    int i5;
    label157:
    int i7;
    label183:
    int i8;
    if (this.query != null)
    {
      j = this.query.hashCode();
      int k = j * 31;
      if (this.lang == null) {
        break label244;
      }
      m = this.lang.hashCode();
      int n = 31 * (k + m);
      if (this.locale == null) {
        break label250;
      }
      i1 = this.locale.hashCode();
      int i2 = 31 * (31 * (31 * (n + i1) + (int)(this.maxId ^ this.maxId >>> 32)) + this.count);
      if (this.since == null) {
        break label256;
      }
      i3 = this.since.hashCode();
      int i4 = 31 * (31 * (i2 + i3) + (int)(this.sinceId ^ this.sinceId >>> 32));
      if (this.geocode == null) {
        break label262;
      }
      i5 = this.geocode.hashCode();
      int i6 = 31 * (i4 + i5);
      if (this.until == null) {
        break label268;
      }
      i7 = this.until.hashCode();
      i8 = 31 * (i6 + i7);
      if (this.resultType == null) {
        break label274;
      }
    }
    label256:
    label262:
    label268:
    label274:
    for (int i9 = this.resultType.hashCode();; i9 = 0)
    {
      int i10 = 31 * (i8 + i9);
      if (this.nextPageQuery != null) {
        i = this.nextPageQuery.hashCode();
      }
      return i10 + i;
      j = 0;
      break;
      label244:
      m = 0;
      break label38;
      label250:
      i1 = 0;
      break label63;
      i3 = 0;
      break label114;
      i5 = 0;
      break label157;
      i7 = 0;
      break label183;
    }
  }
  
  public Query lang(String paramString)
  {
    setLang(paramString);
    return this;
  }
  
  public Query locale(String paramString)
  {
    setLocale(paramString);
    return this;
  }
  
  public Query maxId(long paramLong)
  {
    setMaxId(paramLong);
    return this;
  }
  
  String nextPage()
  {
    return this.nextPageQuery;
  }
  
  public Query query(String paramString)
  {
    setQuery(paramString);
    return this;
  }
  
  public Query resultType(String paramString)
  {
    setResultType(paramString);
    return this;
  }
  
  public void setCount(int paramInt)
  {
    this.count = paramInt;
  }
  
  public void setGeoCode(GeoLocation paramGeoLocation, double paramDouble, String paramString)
  {
    this.geocode = (paramGeoLocation.getLatitude() + "," + paramGeoLocation.getLongitude() + "," + paramDouble + paramString);
  }
  
  public void setLang(String paramString)
  {
    this.lang = paramString;
  }
  
  public void setLocale(String paramString)
  {
    this.locale = paramString;
  }
  
  public void setMaxId(long paramLong)
  {
    this.maxId = paramLong;
  }
  
  public void setQuery(String paramString)
  {
    this.query = paramString;
  }
  
  public void setResultType(String paramString)
  {
    this.resultType = paramString;
  }
  
  public void setSince(String paramString)
  {
    this.since = paramString;
  }
  
  public void setSinceId(long paramLong)
  {
    this.sinceId = paramLong;
  }
  
  public void setUntil(String paramString)
  {
    this.until = paramString;
  }
  
  public Query since(String paramString)
  {
    setSince(paramString);
    return this;
  }
  
  public Query sinceId(long paramLong)
  {
    setSinceId(paramLong);
    return this;
  }
  
  public String toString()
  {
    return "Query{query='" + this.query + '\'' + ", lang='" + this.lang + '\'' + ", locale='" + this.locale + '\'' + ", maxId=" + this.maxId + ", count=" + this.count + ", since='" + this.since + '\'' + ", sinceId=" + this.sinceId + ", geocode='" + this.geocode + '\'' + ", until='" + this.until + '\'' + ", resultType='" + this.resultType + '\'' + ", nextPageQuery='" + this.nextPageQuery + '\'' + '}';
  }
  
  public Query until(String paramString)
  {
    setUntil(paramString);
    return this;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.Query
 * JD-Core Version:    0.7.0.1
 */