package jp.co.asbit.pvstar.search;

import java.util.HashMap;

public class RankingFactory
{
  static HashMap<String, Ranking> searchObj = new HashMap();
  
  public static void clear()
  {
    searchObj = new HashMap();
  }
  
  public static Ranking factory(String paramString)
  {
    Ranking localRanking;
    if (searchObj.containsKey(paramString)) {
      localRanking = (Ranking)searchObj.get(paramString);
    }
    for (;;)
    {
      return localRanking;
      if (paramString.equals("youtube")) {
        searchObj.put(paramString, new YouTubeRanking());
      }
      for (;;)
      {
        localRanking = (Ranking)searchObj.get(paramString);
        break;
        if (paramString.equals("niconico"))
        {
          searchObj.put(paramString, new NicoRanking());
        }
        else
        {
          if (!paramString.equals("dailymotion")) {
            break label115;
          }
          searchObj.put(paramString, new DailyMotionRanking());
        }
      }
      label115:
      localRanking = null;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.search.RankingFactory
 * JD-Core Version:    0.7.0.1
 */