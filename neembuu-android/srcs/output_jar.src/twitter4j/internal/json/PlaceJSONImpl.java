package twitter4j.internal.json;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

final class PlaceJSONImpl
  extends TwitterResponseImpl
  implements Place, Serializable
{
  private static final long serialVersionUID = -2873364341474633812L;
  private GeoLocation[][] boundingBoxCoordinates;
  private String boundingBoxType;
  private Place[] containedWithIn;
  private String country;
  private String countryCode;
  private String fullName;
  private GeoLocation[][] geometryCoordinates;
  private String geometryType;
  private String id;
  private String name;
  private String placeType;
  private String streetAddress;
  private String url;
  
  PlaceJSONImpl() {}
  
  PlaceJSONImpl(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    super(paramHttpResponse);
    JSONObject localJSONObject = paramHttpResponse.asJSONObject();
    init(localJSONObject);
    if (paramConfiguration.isJSONStoreEnabled())
    {
      DataObjectFactoryUtil.clearThreadLocalMap();
      DataObjectFactoryUtil.registerJSONObject(this, localJSONObject);
    }
  }
  
  PlaceJSONImpl(JSONObject paramJSONObject)
    throws TwitterException
  {
    init(paramJSONObject);
  }
  
  PlaceJSONImpl(JSONObject paramJSONObject, HttpResponse paramHttpResponse)
    throws TwitterException
  {
    super(paramHttpResponse);
    init(paramJSONObject);
  }
  
  static ResponseList<Place> createPlaceList(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    JSONObject localJSONObject = null;
    try
    {
      localJSONObject = paramHttpResponse.asJSONObject();
      ResponseList localResponseList = createPlaceList(localJSONObject.getJSONObject("result").getJSONArray("places"), paramHttpResponse, paramConfiguration);
      return localResponseList;
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException.getMessage() + ":" + localJSONObject.toString(), localJSONException);
    }
  }
  
  static ResponseList<Place> createPlaceList(JSONArray paramJSONArray, HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    if (paramConfiguration.isJSONStoreEnabled()) {
      DataObjectFactoryUtil.clearThreadLocalMap();
    }
    for (;;)
    {
      int j;
      try
      {
        int i = paramJSONArray.length();
        ResponseListImpl localResponseListImpl = new ResponseListImpl(i, paramHttpResponse);
        j = 0;
        if (j < i)
        {
          JSONObject localJSONObject = paramJSONArray.getJSONObject(j);
          PlaceJSONImpl localPlaceJSONImpl = new PlaceJSONImpl(localJSONObject);
          localResponseListImpl.add(localPlaceJSONImpl);
          if (paramConfiguration.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.registerJSONObject(localPlaceJSONImpl, localJSONObject);
          }
        }
        else
        {
          if (paramConfiguration.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.registerJSONObject(localResponseListImpl, paramJSONArray);
          }
          return localResponseListImpl;
        }
      }
      catch (JSONException localJSONException)
      {
        throw new TwitterException(localJSONException);
      }
      catch (TwitterException localTwitterException)
      {
        throw localTwitterException;
      }
      j++;
    }
  }
  
  private void init(JSONObject paramJSONObject)
    throws TwitterException
  {
    label409:
    for (;;)
    {
      JSONArray localJSONArray2;
      try
      {
        this.name = z_T4JInternalParseUtil.getUnescapedString("name", paramJSONObject);
        this.streetAddress = z_T4JInternalParseUtil.getUnescapedString("street_address", paramJSONObject);
        this.countryCode = z_T4JInternalParseUtil.getRawString("country_code", paramJSONObject);
        this.id = z_T4JInternalParseUtil.getRawString("id", paramJSONObject);
        this.country = z_T4JInternalParseUtil.getRawString("country", paramJSONObject);
        if (!paramJSONObject.isNull("place_type"))
        {
          this.placeType = z_T4JInternalParseUtil.getRawString("place_type", paramJSONObject);
          this.url = z_T4JInternalParseUtil.getRawString("url", paramJSONObject);
          this.fullName = z_T4JInternalParseUtil.getRawString("full_name", paramJSONObject);
          if (!paramJSONObject.isNull("bounding_box"))
          {
            JSONObject localJSONObject2 = paramJSONObject.getJSONObject("bounding_box");
            this.boundingBoxType = z_T4JInternalParseUtil.getRawString("type", localJSONObject2);
            this.boundingBoxCoordinates = z_T4JInternalJSONImplFactory.coordinatesAsGeoLocationArray(localJSONObject2.getJSONArray("coordinates"));
            if (paramJSONObject.isNull("geometry")) {
              break label409;
            }
            JSONObject localJSONObject1 = paramJSONObject.getJSONObject("geometry");
            this.geometryType = z_T4JInternalParseUtil.getRawString("type", localJSONObject1);
            localJSONArray2 = localJSONObject1.getJSONArray("coordinates");
            if (!this.geometryType.equals("Point")) {
              break label369;
            }
            int[] arrayOfInt = new int[2];
            arrayOfInt[0] = 1;
            arrayOfInt[1] = 1;
            this.geometryCoordinates = ((GeoLocation[][])Array.newInstance(GeoLocation.class, arrayOfInt));
            this.geometryCoordinates[0][0] = new GeoLocation(localJSONArray2.getDouble(0), localJSONArray2.getDouble(1));
            if (paramJSONObject.isNull("contained_within")) {
              break;
            }
            JSONArray localJSONArray1 = paramJSONObject.getJSONArray("contained_within");
            this.containedWithIn = new Place[localJSONArray1.length()];
            int i = 0;
            if (i >= localJSONArray1.length()) {
              return;
            }
            this.containedWithIn[i] = new PlaceJSONImpl(localJSONArray1.getJSONObject(i));
            i++;
            continue;
          }
        }
        else
        {
          this.placeType = z_T4JInternalParseUtil.getRawString("type", paramJSONObject);
          continue;
        }
        this.boundingBoxType = null;
      }
      catch (JSONException localJSONException)
      {
        throw new TwitterException(localJSONException.getMessage() + ":" + paramJSONObject.toString(), localJSONException);
      }
      this.boundingBoxCoordinates = ((GeoLocation[][])null);
      continue;
      label369:
      if (this.geometryType.equals("Polygon"))
      {
        this.geometryCoordinates = z_T4JInternalJSONImplFactory.coordinatesAsGeoLocationArray(localJSONArray2);
      }
      else
      {
        this.geometryType = null;
        this.geometryCoordinates = ((GeoLocation[][])null);
        continue;
        this.geometryType = null;
        this.geometryCoordinates = ((GeoLocation[][])null);
      }
    }
    this.containedWithIn = null;
  }
  
  public int compareTo(Place paramPlace)
  {
    return this.id.compareTo(paramPlace.getId());
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == null) {}
    for (bool = false;; bool = false) {
      do
      {
        return bool;
      } while ((this == paramObject) || (((paramObject instanceof Place)) && (((Place)paramObject).getId().equals(this.id))));
    }
  }
  
  public GeoLocation[][] getBoundingBoxCoordinates()
  {
    return this.boundingBoxCoordinates;
  }
  
  public String getBoundingBoxType()
  {
    return this.boundingBoxType;
  }
  
  public Place[] getContainedWithIn()
  {
    return this.containedWithIn;
  }
  
  public String getCountry()
  {
    return this.country;
  }
  
  public String getCountryCode()
  {
    return this.countryCode;
  }
  
  public String getFullName()
  {
    return this.fullName;
  }
  
  public GeoLocation[][] getGeometryCoordinates()
  {
    return this.geometryCoordinates;
  }
  
  public String getGeometryType()
  {
    return this.geometryType;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getPlaceType()
  {
    return this.placeType;
  }
  
  public String getStreetAddress()
  {
    return this.streetAddress;
  }
  
  public String getURL()
  {
    return this.url;
  }
  
  public int hashCode()
  {
    return this.id.hashCode();
  }
  
  public String toString()
  {
    Object localObject1 = null;
    StringBuilder localStringBuilder1 = new StringBuilder().append("PlaceJSONImpl{name='").append(this.name).append('\'').append(", streetAddress='").append(this.streetAddress).append('\'').append(", countryCode='").append(this.countryCode).append('\'').append(", id='").append(this.id).append('\'').append(", country='").append(this.country).append('\'').append(", placeType='").append(this.placeType).append('\'').append(", url='").append(this.url).append('\'').append(", fullName='").append(this.fullName).append('\'').append(", boundingBoxType='").append(this.boundingBoxType).append('\'').append(", boundingBoxCoordinates=");
    Object localObject2;
    Object localObject3;
    label227:
    StringBuilder localStringBuilder3;
    if (this.boundingBoxCoordinates == null)
    {
      localObject2 = null;
      StringBuilder localStringBuilder2 = localStringBuilder1.append(localObject2).append(", geometryType='").append(this.geometryType).append('\'').append(", geometryCoordinates=");
      if (this.geometryCoordinates != null) {
        break label275;
      }
      localObject3 = null;
      localStringBuilder3 = localStringBuilder2.append(localObject3).append(", containedWithIn=");
      if (this.containedWithIn != null) {
        break label287;
      }
    }
    for (;;)
    {
      return localObject1 + '}';
      localObject2 = Arrays.asList(this.boundingBoxCoordinates);
      break;
      label275:
      localObject3 = Arrays.asList(this.geometryCoordinates);
      break label227;
      label287:
      localObject1 = Arrays.asList(this.containedWithIn);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.PlaceJSONImpl
 * JD-Core Version:    0.7.0.1
 */