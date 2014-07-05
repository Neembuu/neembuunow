package jp.co.asbit.pvstar.search;

import android.net.Uri;
import android.net.Uri.Builder;

public class DailyMotionRanking
  extends Ranking
{
  private static final String AUTHORITY = "pvstar.dooga.org";
  private static final String RANKING_PATH = "/api2/dailymotion_ranks/index/%d";
  private static final String SCHEME = "http";
  
  public DailyMotionRanking()
  {
    this.order = null;
    this.period = null;
    this.category = null;
  }
  
  public boolean choicesEnable()
  {
    return false;
  }
  
  public String getUrl(int paramInt)
  {
    Uri.Builder localBuilder = new Uri.Builder();
    localBuilder.scheme("http");
    localBuilder.encodedAuthority("pvstar.dooga.org");
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    localBuilder.path(String.format("/api2/dailymotion_ranks/index/%d", arrayOfObject));
    return localBuilder.build().toString();
  }
  
  public boolean loadVariables()
  {
    return false;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.search.DailyMotionRanking
 * JD-Core Version:    0.7.0.1
 */