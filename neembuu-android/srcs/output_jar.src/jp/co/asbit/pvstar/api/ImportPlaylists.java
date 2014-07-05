package jp.co.asbit.pvstar.api;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import java.util.ArrayList;
import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.Mylist;
import jp.co.asbit.pvstar.Playlist;
import jp.co.asbit.pvstar.Util;
import jp.co.asbit.pvstar.Video;
import jp.co.asbit.pvstar.VideoDbHelper;
import jp.co.asbit.pvstar.VideoDbHelper.MaxMylistCountException;
import jp.co.asbit.pvstar.VideoDbHelper.MaxVideoCountException;
import jp.co.asbit.pvstar.VideoDbHelper.ValidateErrorException;

public abstract class ImportPlaylists
  extends AsyncTask<ArrayList<Playlist>, Long, Integer>
{
  public boolean insertVideos(Context paramContext, Playlist paramPlaylist, ArrayList<Video> paramArrayList)
    throws NullPointerException, VideoDbHelper.MaxMylistCountException
  {
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(paramContext);
    String str1 = paramPlaylist.getTitle();
    String str2;
    if (paramPlaylist.getDescription() == null) {
      str2 = Util.empty();
    }
    for (;;)
    {
      try
      {
        l2 = localVideoDbHelper.makeMylist(str1, str2).longValue();
      }
      catch (VideoDbHelper.ValidateErrorException localValidateErrorException)
      {
        long l2;
        localValidateErrorException.printStackTrace();
        localVideoDbHelper.close();
        bool = false;
        continue;
      }
      catch (SQLiteException localSQLiteException)
      {
        boolean bool;
        localMylist = localVideoDbHelper.getMylist(str1);
        l1 = localMylist.getId();
        if (l1 == 0L) {
          break label173;
        }
        try
        {
          localVideoDbHelper.insertAllVideo(paramArrayList, Long.valueOf(localMylist.getId()));
          localVideoDbHelper.close();
          bool = true;
        }
        catch (VideoDbHelper.MaxVideoCountException localMaxVideoCountException1)
        {
          localMaxVideoCountException1.printStackTrace();
          continue;
        }
      }
      finally
      {
        localVideoDbHelper.close();
      }
      try
      {
        localVideoDbHelper.insertAllVideo(paramArrayList, Long.valueOf(l2));
        localVideoDbHelper.close();
        bool = true;
        return bool;
        str2 = paramPlaylist.getDescription();
      }
      catch (VideoDbHelper.MaxVideoCountException localMaxVideoCountException2)
      {
        localMaxVideoCountException2.printStackTrace();
      }
    }
    for (;;)
    {
      Mylist localMylist;
      long l1;
      label173:
      localVideoDbHelper.close();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.ImportPlaylists
 * JD-Core Version:    0.7.0.1
 */