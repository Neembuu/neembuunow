package jp.co.asbit.pvstar.search;

import android.net.Uri;
import android.net.Uri.Builder;
import java.util.ArrayList;
import java.util.Locale;
import jp.co.asbit.pvstar.video.HttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NicoRanking
  extends Ranking
{
  private static final String AUTHORITY = "pvstar.dooga.org";
  private static final String CHOICES_PATH = "http://pvstar.dooga.org/api2/nico_ranks/";
  private static final String RANKING_PATH = "/api2/nico_ranks/detail/%s/%s/%s/%d";
  private static final String SCHEME = "http";
  
  public NicoRanking()
  {
    this.order = "fav";
    this.period = "daily";
    this.category = "all";
  }
  
  private ArrayList<SearchCondItem> parseChoice(JSONArray paramJSONArray)
    throws JSONException
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0;; i++)
    {
      if (i >= paramJSONArray.length()) {
        return localArrayList;
      }
      JSONArray localJSONArray = paramJSONArray.getJSONArray(i);
      localArrayList.add(new SearchCondItem(localJSONArray.getString(0), localJSONArray.getString(1)));
    }
  }
  
  public String getUrl(int paramInt)
  {
    Uri.Builder localBuilder = new Uri.Builder();
    localBuilder.scheme("http");
    localBuilder.encodedAuthority("pvstar.dooga.org");
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = this.order;
    arrayOfObject[1] = this.period;
    arrayOfObject[2] = this.category;
    arrayOfObject[3] = Integer.valueOf(paramInt);
    localBuilder.path(String.format("/api2/nico_ranks/detail/%s/%s/%s/%d", arrayOfObject));
    return localBuilder.build().toString();
  }
  
  public boolean loadVariables()
  {
    boolean bool = true;
    HttpClient localHttpClient;
    if (!choicesEnable())
    {
      localHttpClient = new HttpClient("http://pvstar.dooga.org/api2/nico_ranks/");
      localHttpClient.addHeader("Accept-Language", Locale.getDefault().getLanguage());
      if (!localHttpClient.request()) {
        break label123;
      }
    }
    for (;;)
    {
      try
      {
        JSONObject localJSONObject = new JSONObject(localHttpClient.getResponseBody());
        this.orders = parseChoice(localJSONObject.getJSONArray("type"));
        this.periods = parseChoice(localJSONObject.getJSONArray("period"));
        this.categories = parseChoice(localJSONObject.getJSONArray("genre"));
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        localHttpClient.shutdown();
        bool = false;
        continue;
      }
      finally
      {
        localHttpClient.shutdown();
      }
      return bool;
      label123:
      localHttpClient.shutdown();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.search.NicoRanking
 * JD-Core Version:    0.7.0.1
 */