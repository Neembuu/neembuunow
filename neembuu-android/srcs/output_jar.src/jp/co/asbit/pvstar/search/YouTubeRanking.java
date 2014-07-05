package jp.co.asbit.pvstar.search;

import android.net.Uri;
import android.net.Uri.Builder;
import jp.co.asbit.pvstar.Util;

public class YouTubeRanking
  extends Ranking
{
  private static final String AUTHORITY = "pvstar.dooga.org";
  private static final String RANKING_PATH = "/api2/movie_ranks/%s/%d";
  private static final String SCHEME = "http";
  
  public YouTubeRanking()
  {
    this.order = Util.empty();
    this.period = null;
    this.category = null;
  }
  
  public boolean choicesEnable()
  {
    if (this.orders != null) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public String getUrl(int paramInt)
  {
    Uri.Builder localBuilder = new Uri.Builder();
    localBuilder.scheme("http");
    localBuilder.encodedAuthority("pvstar.dooga.org");
    if (!this.order.equals(Util.empty())) {}
    for (String str = this.order;; str = "daily")
    {
      this.order = str;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.order;
      arrayOfObject[1] = Integer.valueOf(paramInt);
      localBuilder.path(String.format("/api2/movie_ranks/%s/%d", arrayOfObject));
      return localBuilder.build().toString();
    }
  }
  
  public boolean loadVariables()
  {
    return false;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.search.YouTubeRanking
 * JD-Core Version:    0.7.0.1
 */