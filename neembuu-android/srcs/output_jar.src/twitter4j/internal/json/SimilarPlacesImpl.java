package twitter4j.internal.json;

import twitter4j.Place;
import twitter4j.ResponseList;
import twitter4j.SimilarPlaces;
import twitter4j.TwitterException;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

public class SimilarPlacesImpl
  extends ResponseListImpl<Place>
  implements SimilarPlaces
{
  private static final long serialVersionUID = -7897806745732767803L;
  private final String token;
  
  SimilarPlacesImpl(ResponseList<Place> paramResponseList, HttpResponse paramHttpResponse, String paramString)
  {
    super(paramResponseList.size(), paramHttpResponse);
    addAll(paramResponseList);
    this.token = paramString;
  }
  
  static SimilarPlaces createSimilarPlaces(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    JSONObject localJSONObject1 = null;
    try
    {
      localJSONObject1 = paramHttpResponse.asJSONObject();
      JSONObject localJSONObject2 = localJSONObject1.getJSONObject("result");
      SimilarPlacesImpl localSimilarPlacesImpl = new SimilarPlacesImpl(PlaceJSONImpl.createPlaceList(localJSONObject2.getJSONArray("places"), paramHttpResponse, paramConfiguration), paramHttpResponse, localJSONObject2.getString("token"));
      return localSimilarPlacesImpl;
    }
    catch (JSONException localJSONException)
    {
      throw new TwitterException(localJSONException.getMessage() + ":" + localJSONObject1.toString(), localJSONException);
    }
  }
  
  public String getToken()
  {
    return this.token;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.SimilarPlacesImpl
 * JD-Core Version:    0.7.0.1
 */