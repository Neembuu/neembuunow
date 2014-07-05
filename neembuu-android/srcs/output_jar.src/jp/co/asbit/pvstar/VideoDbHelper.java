package jp.co.asbit.pvstar;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class VideoDbHelper
  extends SQLiteOpenHelper
{
  public static final long BOOKMARK_MAX_ROWS = 100L;
  private static final String DB_NAME = "pvstar";
  private static final int DB_VERSION = 5;
  public static final long HISTORY_NUMBER = 100L;
  public static final long MYLIST_DEFAULT = -10000L;
  public static final long MYLIST_HISTORY = -20000L;
  public static final long MYLIST_MAX_ROWS = 100L;
  private static final String TAG = "VideoDbHelper";
  public static final long VIDEOS_MAX_ROWS = 500L;
  private Long lastInsertId;
  private Context mContext;
  
  public VideoDbHelper(Context paramContext)
  {
    super(paramContext, "pvstar", null, 5);
    this.mContext = paramContext;
  }
  
  private ArrayList<Playlist> _getBookmarks(SQLiteCursor paramSQLiteCursor)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramSQLiteCursor.getCount() > 0)
    {
      paramSQLiteCursor.moveToFirst();
      do
      {
        Playlist localPlaylist = new Playlist();
        localPlaylist.setId(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("list_id")));
        localPlaylist.setListType(paramSQLiteCursor.getInt(paramSQLiteCursor.getColumnIndex("list_type")));
        localPlaylist.setTitle(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("title")));
        localPlaylist.setSearchEngine(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("site")));
        localPlaylist.setThumbnailUrl(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("thumbnail_url")));
        localPlaylist.setDescription(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("description")));
        localArrayList.add(localPlaylist);
      } while (paramSQLiteCursor.moveToNext());
    }
    paramSQLiteCursor.close();
    return localArrayList;
  }
  
  private ArrayList<Mylist> _getMylists(SQLiteCursor paramSQLiteCursor)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramSQLiteCursor.getCount() > 0)
    {
      paramSQLiteCursor.moveToFirst();
      do
      {
        Mylist localMylist = new Mylist();
        localMylist.setId(paramSQLiteCursor.getLong(paramSQLiteCursor.getColumnIndex("_id")));
        localMylist.setName(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("name")));
        localMylist.setDescription(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("description")));
        localMylist.setPermission(paramSQLiteCursor.getInt(paramSQLiteCursor.getColumnIndex("permission")));
        localMylist.setVideoCount(paramSQLiteCursor.getInt(paramSQLiteCursor.getColumnIndex("video_count")));
        localArrayList.add(localMylist);
      } while (paramSQLiteCursor.moveToNext());
    }
    paramSQLiteCursor.close();
    return localArrayList;
  }
  
  private ArrayList<Video> _getVideos(SQLiteCursor paramSQLiteCursor)
  {
    ArrayList localArrayList;
    if (paramSQLiteCursor.getCount() > 0)
    {
      paramSQLiteCursor.moveToFirst();
      localArrayList = new ArrayList();
      do
      {
        Video localVideo = new Video();
        localVideo.setSearchEngine(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("search_engine")));
        localVideo.setId(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("video_id")));
        localVideo.setThumbnailUrl(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("thumbnail_url")));
        localVideo.setTitle(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("title")));
        localVideo.setDescription(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("description")));
        localVideo.setDuration(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("duration")));
        localVideo.setViewCount(paramSQLiteCursor.getString(paramSQLiteCursor.getColumnIndex("view_count")));
        localArrayList.add(localVideo);
      } while (paramSQLiteCursor.moveToNext());
    }
    for (;;)
    {
      return localArrayList;
      localArrayList = null;
    }
  }
  
  private boolean insertVideo_(Video paramVideo, Long paramLong)
  {
    try
    {
      localSQLiteDatabase = getWritableDatabase();
      localContentValues1 = new ContentValues();
      localLong = Long.valueOf(System.currentTimeMillis());
      localContentValues1.put("search_engine", paramVideo.getSearchEngine());
      localContentValues1.put("video_id", paramVideo.getId());
      localContentValues1.put("mylist_id", paramLong.toString());
      localContentValues1.put("thumbnail_url", paramVideo.getThumbnailUrl());
      localContentValues1.put("title", paramVideo.getTitle());
      localContentValues1.put("description", paramVideo.getDescription());
      localContentValues1.put("duration", paramVideo.getDuration());
      localContentValues1.put("view_count", paramVideo.getViewCount());
      localContentValues1.put("created", localLong.toString());
      localContentValues1.put("modified", localLong.toString());
    }
    catch (SQLException localSQLException1)
    {
      for (;;)
      {
        ContentValues localContentValues1;
        long l;
        label171:
        localSQLException1.printStackTrace();
        bool = false;
        continue;
        label185:
        bool = false;
      }
    }
    try
    {
      this.lastInsertId = Long.valueOf(localSQLiteDatabase.insertOrThrow("videos", null, localContentValues1));
      l = this.lastInsertId.longValue();
      if (l <= 0L) {
        break label185;
      }
      bool = true;
    }
    catch (SQLException localSQLException2)
    {
      for (;;)
      {
        if (paramLong.longValue() == -20000L)
        {
          ContentValues localContentValues2 = new ContentValues();
          localContentValues2.put("modified", localLong.toString());
          String[] arrayOfString = new String[2];
          arrayOfString[0] = paramLong.toString();
          arrayOfString[1] = paramVideo.getId();
          try
          {
            int i = localSQLiteDatabase.updateWithOnConflict("videos", localContentValues2, "mylist_id = ? AND video_id = ?", arrayOfString, 2);
            if (i > 0)
            {
              bool = true;
              break;
            }
            bool = false;
          }
          catch (SQLiteException localSQLiteException)
          {
            localSQLiteException.printStackTrace();
          }
        }
      }
      bool = false;
      break label171;
    }
    return bool;
  }
  
  private String loadSql(String paramString)
    throws IOException
  {
    AssetManager localAssetManager = this.mContext.getResources().getAssets();
    try
    {
      String str2 = readFile(localAssetManager.open(paramString));
      str1 = str2;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        localIOException.printStackTrace();
        String str1 = null;
      }
    }
    return str1;
  }
  
  private String readFile(InputStream paramInputStream)
    throws IOException
  {
    Object localObject1 = null;
    label100:
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream, "UTF-8"));
      try
      {
        StringBuilder localStringBuilder = new StringBuilder();
        for (;;)
        {
          String str1 = localBufferedReader.readLine();
          if (str1 == null)
          {
            String str2 = localStringBuilder.toString();
            if (localBufferedReader != null) {
              localBufferedReader.close();
            }
            return str2;
          }
          localStringBuilder.append(str1 + '\n');
        }
        if (localObject1 == null) {
          break label100;
        }
      }
      finally
      {
        localObject1 = localBufferedReader;
      }
    }
    finally {}
    localObject1.close();
    throw localObject2;
  }
  
  public int deleteAllVideo(ArrayList<Video> paramArrayList, Long paramLong)
  {
    try
    {
      localSQLiteDatabase = getWritableDatabase();
      str = paramLong.toString();
      i = 0;
      j = 0;
    }
    catch (SQLException localSQLException)
    {
      for (;;)
      {
        SQLiteDatabase localSQLiteDatabase;
        String str;
        int j;
        localSQLException.printStackTrace();
        int i = 0;
        continue;
        String[] arrayOfString = new String[2];
        arrayOfString[0] = str;
        arrayOfString[1] = ((Video)paramArrayList.get(j)).getId();
        if (localSQLiteDatabase.delete("videos", "mylist_id = ? AND video_id = ?", arrayOfString) > 0) {
          i++;
        }
        j++;
      }
    }
    if (j >= paramArrayList.size()) {
      return i;
    }
  }
  
  public void deleteBookmark(Playlist paramPlaylist)
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
      String[] arrayOfString = new String[3];
      arrayOfString[0] = paramPlaylist.getId();
      arrayOfString[1] = String.valueOf(paramPlaylist.getListType());
      arrayOfString[2] = paramPlaylist.getSearchEngine();
      localSQLiteDatabase.delete("bookmarks", "list_id = ? AND list_type = ? AND site = ?", arrayOfString);
      return;
    }
    catch (SQLException localSQLException)
    {
      for (;;)
      {
        localSQLException.printStackTrace();
      }
    }
  }
  
  public void deleteMylist(Long paramLong)
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramLong.toString();
      localSQLiteDatabase.delete("videos", "mylist_id = ?", arrayOfString);
      localSQLiteDatabase.delete("mylists", "_id = ?", arrayOfString);
      return;
    }
    catch (SQLException localSQLException)
    {
      for (;;)
      {
        localSQLException.printStackTrace();
      }
    }
  }
  
  public void editBookmark(Playlist paramPlaylist)
    throws VideoDbHelper.ValidateErrorException
  {
    localObject = null;
    try
    {
      SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
      localObject = localSQLiteDatabase;
    }
    catch (SQLException localSQLException)
    {
      long l;
      ContentValues localContentValues;
      for (;;)
      {
        localSQLException.printStackTrace();
      }
      localContentValues.put("title", paramPlaylist.getTitle());
      localContentValues.put("description", paramPlaylist.getDescription());
      localContentValues.put("modified", Long.valueOf(l));
      String[] arrayOfString = new String[3];
      arrayOfString[0] = paramPlaylist.getId();
      arrayOfString[1] = String.valueOf(paramPlaylist.getListType());
      arrayOfString[2] = paramPlaylist.getSearchEngine();
      try
      {
        localObject.updateWithOnConflict("bookmarks", localContentValues, "list_id = ? AND list_type = ? AND site = ?", arrayOfString, 3);
        return;
      }
      catch (SQLiteException localSQLiteException)
      {
        throw new SQLiteException(this.mContext.getString(2131296370));
      }
    }
    l = System.currentTimeMillis();
    localContentValues = new ContentValues();
    if ((paramPlaylist.getTitle() == null) || (paramPlaylist.getTitle().length() == 0)) {
      throw new ValidateErrorException(this.mContext.getString(2131296369));
    }
  }
  
  public Long editMylist(Long paramLong, String paramString1, String paramString2)
    throws VideoDbHelper.ValidateErrorException
  {
    for (;;)
    {
      SQLiteDatabase localSQLiteDatabase;
      Long localLong;
      ContentValues localContentValues;
      try
      {
        localSQLiteDatabase = getWritableDatabase();
        localLong = Long.valueOf(System.currentTimeMillis());
        localContentValues = new ContentValues();
        if (paramString1.length() == 0) {
          throw new ValidateErrorException(this.mContext.getString(2131296365));
        }
      }
      catch (SQLException localSQLException)
      {
        localSQLException.printStackTrace();
        return paramLong;
      }
      localContentValues.put("name", paramString1);
      localContentValues.put("description", paramString2);
      localContentValues.put("modified", localLong.toString());
      String[] arrayOfString = new String[1];
      arrayOfString[0] = paramLong.toString();
      try
      {
        localSQLiteDatabase.updateWithOnConflict("mylists", localContentValues, "_id = ?", arrayOfString, 3);
      }
      catch (SQLiteException localSQLiteException)
      {
        throw new SQLiteException(this.mContext.getString(2131296367));
      }
    }
  }
  
  public boolean editVideo(long paramLong, String paramString1, String paramString2, String paramString3)
    throws VideoDbHelper.ValidateErrorException
  {
    boolean bool = true;
    try
    {
      if (paramString2.length() == 0) {
        throw new ValidateErrorException(this.mContext.getString(2131296365));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      bool = false;
    }
    for (;;)
    {
      return bool;
      SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("title", paramString2);
      localContentValues.put("description", paramString3);
      String[] arrayOfString = new String[2];
      arrayOfString[0] = String.valueOf(paramLong);
      arrayOfString[1] = paramString1;
      localSQLiteDatabase.update("videos", localContentValues, "mylist_id = ? AND video_id = ?", arrayOfString);
    }
  }
  
  public int getBookmarkCount()
  {
    int i = 0;
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    String[] arrayOfString = new String[1];
    arrayOfString[i] = "COUNT(_id) AS rows";
    SQLiteCursor localSQLiteCursor = (SQLiteCursor)localSQLiteDatabase.query("bookmarks", arrayOfString, null, null, null, null, null);
    if (localSQLiteCursor.getCount() > 0)
    {
      localSQLiteCursor.moveToFirst();
      i = localSQLiteCursor.getInt(localSQLiteCursor.getColumnIndex("rows"));
    }
    return i;
  }
  
  public ArrayList<Playlist> getBookmarks()
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
      SQLiteCursor localSQLiteCursor = (SQLiteCursor)localSQLiteDatabase.rawQuery("SELECT * FROM bookmarks ORDER BY sort ASC, _id ASC ", null);
      localArrayList = _getBookmarks(localSQLiteCursor);
      localSQLiteCursor.close();
    }
    catch (SQLiteException localSQLiteException)
    {
      for (;;)
      {
        localSQLiteException.printStackTrace();
        ArrayList localArrayList = new ArrayList();
      }
    }
    return localArrayList;
  }
  
  public ArrayList<Video> getLastPlaylist()
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
      String[] arrayOfString = new String[7];
      arrayOfString[0] = "search_engine";
      arrayOfString[1] = "video_id";
      arrayOfString[2] = "thumbnail_url";
      arrayOfString[3] = "title";
      arrayOfString[4] = "description";
      arrayOfString[5] = "duration";
      arrayOfString[6] = "view_count";
      SQLiteCursor localSQLiteCursor = (SQLiteCursor)localSQLiteDatabase.query("last_playlist", arrayOfString, null, null, null, null, "_id ASC");
      localArrayList = _getVideos(localSQLiteCursor);
      localSQLiteCursor.close();
    }
    catch (SQLiteException localSQLiteException)
    {
      for (;;)
      {
        ArrayList localArrayList = new ArrayList();
      }
    }
    return localArrayList;
  }
  
  public Mylist getMylist(Long paramLong)
  {
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    String[] arrayOfString1 = new String[4];
    arrayOfString1[0] = "_id";
    arrayOfString1[1] = "name";
    arrayOfString1[2] = "description";
    arrayOfString1[3] = "permission";
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = paramLong.toString();
    SQLiteCursor localSQLiteCursor = (SQLiteCursor)localSQLiteDatabase.query("mylists", arrayOfString1, "_id = ?", arrayOfString2, null, null, null);
    Mylist localMylist = null;
    localSQLiteCursor.moveToFirst();
    if (localSQLiteCursor.getCount() > 0)
    {
      localMylist = new Mylist();
      localMylist.setId(localSQLiteCursor.getLong(localSQLiteCursor.getColumnIndex("_id")));
      localMylist.setName(localSQLiteCursor.getString(localSQLiteCursor.getColumnIndex("name")));
      localMylist.setDescription(localSQLiteCursor.getString(localSQLiteCursor.getColumnIndex("description")));
      localMylist.setPermission(localSQLiteCursor.getInt(localSQLiteCursor.getColumnIndex("permission")));
    }
    localSQLiteCursor.close();
    return localMylist;
  }
  
  public Mylist getMylist(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    String[] arrayOfString1 = new String[4];
    arrayOfString1[0] = "_id";
    arrayOfString1[1] = "name";
    arrayOfString1[2] = "description";
    arrayOfString1[3] = "permission";
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = paramString;
    SQLiteCursor localSQLiteCursor = (SQLiteCursor)localSQLiteDatabase.query("mylists", arrayOfString1, "name = ?", arrayOfString2, null, null, null);
    Mylist localMylist = null;
    localSQLiteCursor.moveToFirst();
    if (localSQLiteCursor.getCount() > 0)
    {
      localMylist = new Mylist();
      localMylist.setId(localSQLiteCursor.getLong(localSQLiteCursor.getColumnIndex("_id")));
      localMylist.setName(localSQLiteCursor.getString(localSQLiteCursor.getColumnIndex("name")));
      localMylist.setDescription(localSQLiteCursor.getString(localSQLiteCursor.getColumnIndex("description")));
      localMylist.setPermission(localSQLiteCursor.getInt(localSQLiteCursor.getColumnIndex("permission")));
    }
    localSQLiteCursor.close();
    return localMylist;
  }
  
  public int getMylistCount()
  {
    int i = 0;
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    String[] arrayOfString = new String[1];
    arrayOfString[i] = "COUNT(_id) AS rows";
    SQLiteCursor localSQLiteCursor = (SQLiteCursor)localSQLiteDatabase.query("mylists", arrayOfString, null, null, null, null, null);
    if (localSQLiteCursor.getCount() > 0)
    {
      localSQLiteCursor.moveToFirst();
      i = localSQLiteCursor.getInt(localSQLiteCursor.getColumnIndex("rows"));
    }
    return i;
  }
  
  public ArrayList<Mylist> getMylists()
  {
    try
    {
      SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
      SQLiteCursor localSQLiteCursor = (SQLiteCursor)localSQLiteDatabase.rawQuery("SELECT mylists._id, mylists.name, mylists.description, mylists.permission, COUNT(videos._id) AS video_count FROM mylists LEFT JOIN videos ON mylists._id = videos.mylist_id GROUP BY mylists._id ORDER BY mylists.sort ASC, mylists._id ASC ", null);
      localArrayList = _getMylists(localSQLiteCursor);
      localSQLiteCursor.close();
    }
    catch (SQLiteException localSQLiteException)
    {
      for (;;)
      {
        localSQLiteException.printStackTrace();
        ArrayList localArrayList = new ArrayList();
      }
    }
    return localArrayList;
  }
  
  public ArrayList<Mylist> getMylistsExcludeHistory()
  {
    ArrayList localArrayList1 = getMylists();
    ArrayList localArrayList2 = new ArrayList();
    for (int i = 0;; i++)
    {
      if (i >= localArrayList1.size()) {
        return localArrayList2;
      }
      if (((Mylist)localArrayList1.get(i)).getId() != -20000L) {
        localArrayList2.add((Mylist)localArrayList1.get(i));
      }
    }
  }
  
  public int getVideoCount(Long paramLong)
  {
    int i = 0;
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    String[] arrayOfString1 = new String[1];
    arrayOfString1[i] = "COUNT(_id) AS rows";
    String[] arrayOfString2 = new String[1];
    arrayOfString2[i] = paramLong.toString();
    SQLiteCursor localSQLiteCursor = (SQLiteCursor)localSQLiteDatabase.query("videos", arrayOfString1, "mylist_id = ?", arrayOfString2, null, null, null);
    if (localSQLiteCursor.getCount() > 0)
    {
      localSQLiteCursor.moveToFirst();
      i = localSQLiteCursor.getInt(localSQLiteCursor.getColumnIndex("rows"));
    }
    return i;
  }
  
  public LocalProxyUrl getVideoUrl(String paramString)
  {
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    String[] arrayOfString1 = new String[4];
    arrayOfString1[0] = "key";
    arrayOfString1[1] = "url";
    arrayOfString1[2] = "cookie";
    arrayOfString1[3] = "useragent";
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = paramString;
    SQLiteCursor localSQLiteCursor = (SQLiteCursor)localSQLiteDatabase.query("video_urls", arrayOfString1, "key = ?", arrayOfString2, null, null, null);
    LocalProxyUrl localLocalProxyUrl = null;
    if (localSQLiteCursor.getCount() > 0)
    {
      localSQLiteCursor.moveToFirst();
      localLocalProxyUrl = new LocalProxyUrl();
      localLocalProxyUrl.setKey(localSQLiteCursor.getString(localSQLiteCursor.getColumnIndex("key")));
      localLocalProxyUrl.setUrl(localSQLiteCursor.getString(localSQLiteCursor.getColumnIndex("url")));
      localLocalProxyUrl.setCookie(localSQLiteCursor.getString(localSQLiteCursor.getColumnIndex("cookie")));
      localLocalProxyUrl.setUseragent(localSQLiteCursor.getString(localSQLiteCursor.getColumnIndex("useragent")));
    }
    localSQLiteCursor.close();
    return localLocalProxyUrl;
  }
  
  public ArrayList<Video> getVideos(Long paramLong)
  {
    return getVideos(paramLong, null);
  }
  
  public ArrayList<Video> getVideos(Long paramLong, String paramString)
  {
    String str = "sort ASC, _id DESC";
    if (paramLong.longValue() == -20000L)
    {
      trimHistory();
      str = "modified DESC, _id DESC";
    }
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    String[] arrayOfString1 = new String[8];
    arrayOfString1[0] = "search_engine";
    arrayOfString1[1] = "video_id";
    arrayOfString1[2] = "mylist_id";
    arrayOfString1[3] = "thumbnail_url";
    arrayOfString1[4] = "title";
    arrayOfString1[5] = "description";
    arrayOfString1[6] = "duration";
    arrayOfString1[7] = "view_count";
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = String.valueOf(paramLong);
    SQLiteCursor localSQLiteCursor = (SQLiteCursor)localSQLiteDatabase.query("videos", arrayOfString1, "mylist_id = ?", arrayOfString2, null, null, str, paramString);
    ArrayList localArrayList = _getVideos(localSQLiteCursor);
    localSQLiteCursor.close();
    return localArrayList;
  }
  
  /* Error */
  public int insertAllVideo(ArrayList<Video> paramArrayList, Long paramLong)
    throws VideoDbHelper.MaxVideoCountException
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_3
    //   2: iconst_0
    //   3: istore 4
    //   5: aload_1
    //   6: invokestatic 483	java/util/Collections:reverse	(Ljava/util/List;)V
    //   9: aload_0
    //   10: invokevirtual 188	jp/co/asbit/pvstar/VideoDbHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   13: astore 6
    //   15: aload 6
    //   17: invokevirtual 486	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   20: aload_0
    //   21: aload_2
    //   22: invokevirtual 488	jp/co/asbit/pvstar/VideoDbHelper:getVideoCount	(Ljava/lang/Long;)I
    //   25: istore 7
    //   27: aload_1
    //   28: invokevirtual 492	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   31: astore 10
    //   33: aload 10
    //   35: invokeinterface 497 1 0
    //   40: ifne +28 -> 68
    //   43: aload 6
    //   45: invokevirtual 500	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   48: aload 6
    //   50: invokevirtual 503	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   53: iload 4
    //   55: istore_3
    //   56: iload_3
    //   57: ireturn
    //   58: astore 5
    //   60: aload 5
    //   62: invokevirtual 254	android/database/SQLException:printStackTrace	()V
    //   65: goto -9 -> 56
    //   68: iload 7
    //   70: iload 4
    //   72: iadd
    //   73: i2l
    //   74: ldc2_w 40
    //   77: lcmp
    //   78: iflt +70 -> 148
    //   81: aload 6
    //   83: invokevirtual 500	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   86: aload_0
    //   87: getfield 52	jp/co/asbit/pvstar/VideoDbHelper:mContext	Landroid/content/Context;
    //   90: ldc_w 504
    //   93: invokevirtual 363	android/content/Context:getString	(I)Ljava/lang/String;
    //   96: astore 12
    //   98: iconst_1
    //   99: anewarray 506	java/lang/Object
    //   102: astore 13
    //   104: aload 13
    //   106: iconst_0
    //   107: ldc2_w 40
    //   110: invokestatic 203	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   113: aastore
    //   114: new 12	jp/co/asbit/pvstar/VideoDbHelper$MaxVideoCountException
    //   117: dup
    //   118: aload_0
    //   119: aload 12
    //   121: aload 13
    //   123: invokestatic 510	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   126: invokespecial 511	jp/co/asbit/pvstar/VideoDbHelper$MaxVideoCountException:<init>	(Ljp/co/asbit/pvstar/VideoDbHelper;Ljava/lang/String;)V
    //   129: athrow
    //   130: astore 9
    //   132: aload 9
    //   134: invokevirtual 263	android/database/sqlite/SQLiteException:printStackTrace	()V
    //   137: aload 6
    //   139: invokevirtual 503	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   142: iload 4
    //   144: istore_3
    //   145: goto -89 -> 56
    //   148: aload_0
    //   149: aload 10
    //   151: invokeinterface 515 1 0
    //   156: checkcast 158	jp/co/asbit/pvstar/Video
    //   159: aload_2
    //   160: invokespecial 517	jp/co/asbit/pvstar/VideoDbHelper:insertVideo_	(Ljp/co/asbit/pvstar/Video;Ljava/lang/Long;)Z
    //   163: istore 11
    //   165: iload 11
    //   167: ifeq -134 -> 33
    //   170: iinc 4 1
    //   173: goto -140 -> 33
    //   176: astore 8
    //   178: aload 6
    //   180: invokevirtual 503	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   183: aload 8
    //   185: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	186	0	this	VideoDbHelper
    //   0	186	1	paramArrayList	ArrayList<Video>
    //   0	186	2	paramLong	Long
    //   1	144	3	i	int
    //   3	168	4	j	int
    //   58	3	5	localSQLException	SQLException
    //   13	166	6	localSQLiteDatabase	SQLiteDatabase
    //   25	48	7	k	int
    //   176	8	8	localObject	Object
    //   130	3	9	localSQLiteException	SQLiteException
    //   31	119	10	localIterator	Iterator
    //   163	3	11	bool	boolean
    //   96	24	12	str	String
    //   102	20	13	arrayOfObject	Object[]
    // Exception table:
    //   from	to	target	type
    //   9	15	58	android/database/SQLException
    //   27	48	130	android/database/sqlite/SQLiteException
    //   81	130	130	android/database/sqlite/SQLiteException
    //   148	165	130	android/database/sqlite/SQLiteException
    //   27	48	176	finally
    //   81	130	176	finally
    //   132	137	176	finally
    //   148	165	176	finally
  }
  
  public boolean insertBookmark(Playlist paramPlaylist)
    throws VideoDbHelper.MaxBookmarkCountException
  {
    bool = true;
    if (getBookmarkCount() >= 100L)
    {
      String str = this.mContext.getString(2131296368);
      Object[] arrayOfObject = new Object[bool];
      arrayOfObject[0] = Long.valueOf(100L);
      throw new MaxBookmarkCountException(String.format(str, arrayOfObject));
    }
    try
    {
      localSQLiteDatabase = getWritableDatabase();
      localContentValues = new ContentValues();
      long l1 = System.currentTimeMillis();
      localContentValues.put("list_id", paramPlaylist.getId());
      localContentValues.put("list_type", Integer.valueOf(paramPlaylist.getListType()));
      localContentValues.put("site", paramPlaylist.getSearchEngine());
      localContentValues.put("title", paramPlaylist.getTitle());
      localContentValues.put("description", paramPlaylist.getDescription());
      localContentValues.put("thumbnail_url", paramPlaylist.getThumbnailUrl());
      localContentValues.put("created", Long.valueOf(l1));
      localContentValues.put("modified", Long.valueOf(l1));
    }
    catch (SQLException localSQLException1)
    {
      for (;;)
      {
        SQLiteDatabase localSQLiteDatabase;
        ContentValues localContentValues;
        long l2;
        label205:
        localSQLException1.printStackTrace();
        bool = false;
        continue;
        bool = false;
      }
    }
    try
    {
      this.lastInsertId = Long.valueOf(localSQLiteDatabase.insertOrThrow("bookmarks", null, localContentValues));
      l2 = this.lastInsertId.longValue();
      if (l2 <= 0L) {
        break label217;
      }
    }
    catch (SQLException localSQLException2)
    {
      localSQLException2.printStackTrace();
      bool = false;
      break label205;
    }
    return bool;
  }
  
  public boolean insertHistory(Video paramVideo)
  {
    boolean bool = insertVideo_(paramVideo, Long.valueOf(-20000L));
    trimHistory();
    return bool;
  }
  
  public int insertLastPlaylist(ArrayList<Video> paramArrayList)
  {
    i = 0;
    try
    {
      localSQLiteDatabase = getWritableDatabase();
      localSQLiteDatabase.delete("last_playlist", null, null);
    }
    catch (SQLException localSQLException1)
    {
      for (;;)
      {
        Iterator localIterator;
        label50:
        localSQLException1.printStackTrace();
        int j = 0;
        continue;
        Video localVideo = (Video)localIterator.next();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("search_engine", localVideo.getSearchEngine());
        localContentValues.put("video_id", localVideo.getId());
        localContentValues.put("thumbnail_url", localVideo.getThumbnailUrl());
        localContentValues.put("title", localVideo.getTitle());
        localContentValues.put("description", localVideo.getDescription());
        localContentValues.put("duration", localVideo.getDuration());
        localContentValues.put("view_count", localVideo.getViewCount());
        long l = localSQLiteDatabase.insert("last_playlist", null, localContentValues);
        if (l > 0L) {
          i++;
        }
      }
    }
    try
    {
      localSQLiteDatabase.beginTransaction();
      localIterator = paramArrayList.iterator();
      if (localIterator.hasNext()) {
        break label67;
      }
      localSQLiteDatabase.setTransactionSuccessful();
    }
    catch (SQLException localSQLException2)
    {
      localSQLException2.printStackTrace();
      localSQLiteDatabase.endTransaction();
      break label50;
    }
    finally
    {
      localSQLiteDatabase.endTransaction();
    }
    j = i;
    return j;
  }
  
  public boolean insertVideo(Video paramVideo, Long paramLong)
    throws VideoDbHelper.MaxVideoCountException
  {
    if (getVideoCount(paramLong) >= 500L)
    {
      String str = this.mContext.getString(2131296363);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Long.valueOf(500L);
      throw new MaxVideoCountException(String.format(str, arrayOfObject));
    }
    return insertVideo_(paramVideo, paramLong);
  }
  
  public boolean isPLaylistExists()
  {
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = "COUNT(_id) AS rows";
    SQLiteCursor localSQLiteCursor = (SQLiteCursor)localSQLiteDatabase.query("videos", arrayOfString, null, null, null, null, null);
    boolean bool;
    if (localSQLiteCursor.getCount() > 0)
    {
      localSQLiteCursor.moveToFirst();
      if (localSQLiteCursor.getInt(localSQLiteCursor.getColumnIndex("rows")) > 0) {
        bool = true;
      }
    }
    for (;;)
    {
      return bool;
      bool = false;
      continue;
      bool = false;
    }
  }
  
  public Long lastInsertId()
  {
    return this.lastInsertId;
  }
  
  public Long makeMylist(String paramString1, String paramString2)
    throws VideoDbHelper.ValidateErrorException, VideoDbHelper.MaxMylistCountException, NullPointerException
  {
    if (getMylistCount() >= 100L)
    {
      String str = this.mContext.getString(2131296364);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Long.valueOf(100L);
      throw new MaxMylistCountException(String.format(str, arrayOfObject));
    }
    SQLiteDatabase localSQLiteDatabase;
    Long localLong1;
    ContentValues localContentValues;
    try
    {
      localSQLiteDatabase = getWritableDatabase();
      localLong1 = Long.valueOf(System.currentTimeMillis());
      localContentValues = new ContentValues();
      if (paramString1.length() == 0) {
        throw new ValidateErrorException(this.mContext.getString(2131296365));
      }
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
      throw new NullPointerException();
    }
    localContentValues.put("name", paramString1);
    localContentValues.put("description", paramString2);
    localContentValues.put("permission", Integer.valueOf(1));
    localContentValues.put("created", localLong1.toString());
    localContentValues.put("modified", localLong1.toString());
    try
    {
      Long localLong2 = Long.valueOf(localSQLiteDatabase.insertOrThrow("mylists", null, localContentValues));
      return localLong2;
    }
    catch (SQLiteException localSQLiteException)
    {
      localSQLiteException.printStackTrace();
      throw new SQLiteException(this.mContext.getString(2131296366));
    }
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    try
    {
      paramSQLiteDatabase.execSQL(loadSql("sql/created/videos"));
      paramSQLiteDatabase.execSQL(loadSql("sql/created/mylists"));
      paramSQLiteDatabase.execSQL(loadSql("sql/created/profile"));
      paramSQLiteDatabase.execSQL(loadSql("sql/created/video_urls"));
      paramSQLiteDatabase.execSQL(loadSql("sql/created/last_playlist"));
      paramSQLiteDatabase.execSQL(loadSql("sql/created/bookmarks"));
      Long localLong = Long.valueOf(System.currentTimeMillis());
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("_id", Long.valueOf(-10000L));
      localContentValues.put("name", this.mContext.getString(2131296359));
      localContentValues.put("description", this.mContext.getString(2131296360));
      localContentValues.put("permission", Integer.valueOf(0));
      localContentValues.put("created", localLong.toString());
      localContentValues.put("modified", localLong.toString());
      paramSQLiteDatabase.insert("mylists", null, localContentValues);
      localContentValues.put("_id", Long.valueOf(-20000L));
      localContentValues.put("name", this.mContext.getString(2131296361));
      String str = this.mContext.getString(2131296362);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Long.valueOf(100L);
      localContentValues.put("description", String.format(str, arrayOfObject));
      localContentValues.put("permission", Integer.valueOf(0));
      localContentValues.put("created", localLong.toString());
      localContentValues.put("modified", localLong.toString());
      paramSQLiteDatabase.insert("mylists", null, localContentValues);
      label289:
      return;
    }
    catch (IOException localIOException)
    {
      break label289;
    }
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    Log.d("VideoDbHelper", "Version up to " + paramInt2 + " FROM " + paramInt1);
    if (paramInt1 < 2) {
      paramSQLiteDatabase.execSQL("ALTER TABLE mylists ADD sort INTEGER NOT NULL DEFAULT 999");
    }
    if (paramInt1 < 3) {}
    try
    {
      paramSQLiteDatabase.execSQL(loadSql("sql/created/video_urls"));
      if (paramInt1 >= 4) {}
    }
    catch (IOException localIOException2)
    {
      try
      {
        paramSQLiteDatabase.execSQL(loadSql("sql/created/last_playlist"));
        if (paramInt1 >= 5) {}
      }
      catch (IOException localIOException2)
      {
        try
        {
          for (;;)
          {
            paramSQLiteDatabase.execSQL(loadSql("sql/created/bookmarks"));
            return;
            localIOException3 = localIOException3;
            Log.d("VideoDbHelper", "Failed to Upgrade.");
          }
          localIOException2 = localIOException2;
          Log.d("VideoDbHelper", "Failed to Upgrade.");
        }
        catch (IOException localIOException1)
        {
          for (;;)
          {
            Log.d("VideoDbHelper", "Failed to Upgrade.");
          }
        }
      }
    }
  }
  
  /* Error */
  public void setBookmarksOrder(ArrayList<Playlist> paramArrayList)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 188	jp/co/asbit/pvstar/VideoDbHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   4: astore_3
    //   5: aload_3
    //   6: ldc_w 598
    //   9: invokevirtual 602	android/database/sqlite/SQLiteDatabase:compileStatement	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteStatement;
    //   12: astore 4
    //   14: aload_3
    //   15: invokevirtual 486	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   18: iconst_0
    //   19: istore 5
    //   21: iload 5
    //   23: aload_1
    //   24: invokevirtual 327	java/util/ArrayList:size	()I
    //   27: if_icmplt +20 -> 47
    //   30: aload_3
    //   31: invokevirtual 500	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   34: aload_3
    //   35: invokevirtual 503	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   38: return
    //   39: astore_2
    //   40: aload_2
    //   41: invokevirtual 254	android/database/SQLException:printStackTrace	()V
    //   44: goto -6 -> 38
    //   47: iload 5
    //   49: i2l
    //   50: lstore 8
    //   52: aload 4
    //   54: iconst_1
    //   55: lload 8
    //   57: invokevirtual 608	android/database/sqlite/SQLiteStatement:bindLong	(IJ)V
    //   60: aload 4
    //   62: iconst_2
    //   63: aload_1
    //   64: iload 5
    //   66: invokevirtual 331	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   69: checkcast 71	jp/co/asbit/pvstar/Playlist
    //   72: invokevirtual 338	jp/co/asbit/pvstar/Playlist:getId	()Ljava/lang/String;
    //   75: invokevirtual 612	android/database/sqlite/SQLiteStatement:bindString	(ILjava/lang/String;)V
    //   78: aload 4
    //   80: iconst_3
    //   81: aload_1
    //   82: iload 5
    //   84: invokevirtual 331	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   87: checkcast 71	jp/co/asbit/pvstar/Playlist
    //   90: invokevirtual 341	jp/co/asbit/pvstar/Playlist:getListType	()I
    //   93: invokestatic 343	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   96: invokevirtual 612	android/database/sqlite/SQLiteStatement:bindString	(ILjava/lang/String;)V
    //   99: aload 4
    //   101: iconst_4
    //   102: aload_1
    //   103: iload 5
    //   105: invokevirtual 331	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   108: checkcast 71	jp/co/asbit/pvstar/Playlist
    //   111: invokevirtual 344	jp/co/asbit/pvstar/Playlist:getSearchEngine	()Ljava/lang/String;
    //   114: invokevirtual 612	android/database/sqlite/SQLiteStatement:bindString	(ILjava/lang/String;)V
    //   117: aload 4
    //   119: invokevirtual 615	android/database/sqlite/SQLiteStatement:execute	()V
    //   122: iinc 5 1
    //   125: goto -104 -> 21
    //   128: astore 7
    //   130: aload 7
    //   132: invokevirtual 263	android/database/sqlite/SQLiteException:printStackTrace	()V
    //   135: aload_3
    //   136: invokevirtual 503	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   139: goto -101 -> 38
    //   142: astore 6
    //   144: aload_3
    //   145: invokevirtual 503	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   148: aload 6
    //   150: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	151	0	this	VideoDbHelper
    //   0	151	1	paramArrayList	ArrayList<Playlist>
    //   39	2	2	localSQLException	SQLException
    //   4	141	3	localSQLiteDatabase	SQLiteDatabase
    //   12	106	4	localSQLiteStatement	android.database.sqlite.SQLiteStatement
    //   19	104	5	i	int
    //   142	7	6	localObject	Object
    //   128	3	7	localSQLiteException	SQLiteException
    //   50	6	8	l	long
    // Exception table:
    //   from	to	target	type
    //   0	5	39	android/database/SQLException
    //   21	34	128	android/database/sqlite/SQLiteException
    //   52	122	128	android/database/sqlite/SQLiteException
    //   21	34	142	finally
    //   52	122	142	finally
    //   130	135	142	finally
  }
  
  /* Error */
  public void setMylistsOrder(ArrayList<Mylist> paramArrayList)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 188	jp/co/asbit/pvstar/VideoDbHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   4: astore_3
    //   5: aload_3
    //   6: ldc_w 618
    //   9: invokevirtual 602	android/database/sqlite/SQLiteDatabase:compileStatement	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteStatement;
    //   12: astore 4
    //   14: aload_3
    //   15: invokevirtual 486	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   18: iconst_0
    //   19: istore 5
    //   21: iload 5
    //   23: aload_1
    //   24: invokevirtual 327	java/util/ArrayList:size	()I
    //   27: if_icmplt +20 -> 47
    //   30: aload_3
    //   31: invokevirtual 500	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   34: aload_3
    //   35: invokevirtual 503	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   38: return
    //   39: astore_2
    //   40: aload_2
    //   41: invokevirtual 254	android/database/SQLException:printStackTrace	()V
    //   44: goto -6 -> 38
    //   47: iload 5
    //   49: i2l
    //   50: lstore 8
    //   52: aload 4
    //   54: iconst_1
    //   55: lload 8
    //   57: invokevirtual 608	android/database/sqlite/SQLiteStatement:bindLong	(IJ)V
    //   60: aload 4
    //   62: iconst_2
    //   63: aload_1
    //   64: iload 5
    //   66: invokevirtual 331	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   69: checkcast 129	jp/co/asbit/pvstar/Mylist
    //   72: invokevirtual 430	jp/co/asbit/pvstar/Mylist:getId	()J
    //   75: invokevirtual 608	android/database/sqlite/SQLiteStatement:bindLong	(IJ)V
    //   78: aload 4
    //   80: invokevirtual 615	android/database/sqlite/SQLiteStatement:execute	()V
    //   83: iinc 5 1
    //   86: goto -65 -> 21
    //   89: astore 7
    //   91: aload 7
    //   93: invokevirtual 263	android/database/sqlite/SQLiteException:printStackTrace	()V
    //   96: aload_3
    //   97: invokevirtual 503	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   100: goto -62 -> 38
    //   103: astore 6
    //   105: aload_3
    //   106: invokevirtual 503	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   109: aload 6
    //   111: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	112	0	this	VideoDbHelper
    //   0	112	1	paramArrayList	ArrayList<Mylist>
    //   39	2	2	localSQLException	SQLException
    //   4	102	3	localSQLiteDatabase	SQLiteDatabase
    //   12	67	4	localSQLiteStatement	android.database.sqlite.SQLiteStatement
    //   19	65	5	i	int
    //   103	7	6	localObject	Object
    //   89	3	7	localSQLiteException	SQLiteException
    //   50	6	8	l	long
    // Exception table:
    //   from	to	target	type
    //   0	5	39	android/database/SQLException
    //   21	34	89	android/database/sqlite/SQLiteException
    //   52	83	89	android/database/sqlite/SQLiteException
    //   21	34	103	finally
    //   52	83	103	finally
    //   91	96	103	finally
  }
  
  public boolean setVideoUrl(LocalProxyUrl paramLocalProxyUrl)
  {
    bool = true;
    try
    {
      localSQLiteDatabase = getWritableDatabase();
      localContentValues = new ContentValues();
      localContentValues.put("key", paramLocalProxyUrl.getKey());
      localContentValues.put("url", paramLocalProxyUrl.getUrl());
      localContentValues.put("cookie", paramLocalProxyUrl.getCookie());
      localContentValues.put("useragent", paramLocalProxyUrl.getUseragent());
    }
    catch (SQLException localSQLException1)
    {
      for (;;)
      {
        long l;
        label85:
        localSQLException1.printStackTrace();
        bool = false;
        continue;
        bool = false;
      }
    }
    try
    {
      l = localSQLiteDatabase.insertOrThrow("video_urls", null, localContentValues);
      if (l <= 0L) {
        break label97;
      }
    }
    catch (SQLException localSQLException2)
    {
      for (;;)
      {
        String[] arrayOfString = new String[bool];
        arrayOfString[0] = paramLocalProxyUrl.getKey();
        try
        {
          int i = localSQLiteDatabase.update("video_urls", localContentValues, "key = ?", arrayOfString);
          if (i > 0) {
            break;
          }
          bool = false;
        }
        catch (SQLException localSQLException3)
        {
          localSQLException3.printStackTrace();
          bool = false;
        }
      }
      break label85;
    }
    return bool;
  }
  
  /* Error */
  public void setVideosOrder(ArrayList<Video> paramArrayList, Long paramLong)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 188	jp/co/asbit/pvstar/VideoDbHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   4: astore 4
    //   6: aload 4
    //   8: ldc_w 636
    //   11: invokevirtual 602	android/database/sqlite/SQLiteDatabase:compileStatement	(Ljava/lang/String;)Landroid/database/sqlite/SQLiteStatement;
    //   14: astore 5
    //   16: aload 4
    //   18: invokevirtual 486	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   21: iconst_0
    //   22: istore 6
    //   24: iload 6
    //   26: aload_1
    //   27: invokevirtual 327	java/util/ArrayList:size	()I
    //   30: if_icmplt +22 -> 52
    //   33: aload 4
    //   35: invokevirtual 500	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   38: aload 4
    //   40: invokevirtual 503	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   43: return
    //   44: astore_3
    //   45: aload_3
    //   46: invokevirtual 254	android/database/SQLException:printStackTrace	()V
    //   49: goto -6 -> 43
    //   52: iload 6
    //   54: i2l
    //   55: lstore 9
    //   57: aload 5
    //   59: iconst_1
    //   60: lload 9
    //   62: invokevirtual 608	android/database/sqlite/SQLiteStatement:bindLong	(IJ)V
    //   65: aload 5
    //   67: iconst_2
    //   68: aload_2
    //   69: invokevirtual 251	java/lang/Long:longValue	()J
    //   72: invokevirtual 608	android/database/sqlite/SQLiteStatement:bindLong	(IJ)V
    //   75: aload 5
    //   77: iconst_3
    //   78: aload_1
    //   79: iload 6
    //   81: invokevirtual 331	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   84: checkcast 158	jp/co/asbit/pvstar/Video
    //   87: invokevirtual 214	jp/co/asbit/pvstar/Video:getId	()Ljava/lang/String;
    //   90: invokevirtual 612	android/database/sqlite/SQLiteStatement:bindString	(ILjava/lang/String;)V
    //   93: aload 5
    //   95: invokevirtual 615	android/database/sqlite/SQLiteStatement:execute	()V
    //   98: iinc 6 1
    //   101: goto -77 -> 24
    //   104: astore 8
    //   106: aload 8
    //   108: invokevirtual 263	android/database/sqlite/SQLiteException:printStackTrace	()V
    //   111: aload 4
    //   113: invokevirtual 503	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   116: goto -73 -> 43
    //   119: astore 7
    //   121: aload 4
    //   123: invokevirtual 503	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   126: aload 7
    //   128: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	129	0	this	VideoDbHelper
    //   0	129	1	paramArrayList	ArrayList<Video>
    //   0	129	2	paramLong	Long
    //   44	2	3	localSQLException	SQLException
    //   4	118	4	localSQLiteDatabase	SQLiteDatabase
    //   14	80	5	localSQLiteStatement	android.database.sqlite.SQLiteStatement
    //   22	77	6	i	int
    //   119	8	7	localObject	Object
    //   104	3	8	localSQLiteException	SQLiteException
    //   55	6	9	l	long
    // Exception table:
    //   from	to	target	type
    //   0	6	44	android/database/SQLException
    //   24	38	104	android/database/sqlite/SQLiteException
    //   57	98	104	android/database/sqlite/SQLiteException
    //   24	38	119	finally
    //   57	98	119	finally
    //   106	111	119	finally
  }
  
  /* Error */
  public void trimHistory()
  {
    // Byte code:
    //   0: ldc2_w 33
    //   3: invokestatic 203	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   6: astore_1
    //   7: aload_0
    //   8: invokevirtual 188	jp/co/asbit/pvstar/VideoDbHelper:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   11: astore_3
    //   12: iconst_1
    //   13: anewarray 256	java/lang/String
    //   16: astore 4
    //   18: aload 4
    //   20: iconst_0
    //   21: aload_1
    //   22: invokevirtual 219	java/lang/Long:toString	()Ljava/lang/String;
    //   25: aastore
    //   26: aload_3
    //   27: ldc_w 638
    //   30: aload 4
    //   32: invokevirtual 641	android/database/sqlite/SQLiteDatabase:execSQL	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   35: return
    //   36: astore_2
    //   37: aload_2
    //   38: invokevirtual 254	android/database/SQLException:printStackTrace	()V
    //   41: goto -6 -> 35
    //   44: astore 5
    //   46: aload 5
    //   48: invokevirtual 263	android/database/sqlite/SQLiteException:printStackTrace	()V
    //   51: goto -16 -> 35
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	54	0	this	VideoDbHelper
    //   6	16	1	localLong	Long
    //   36	2	2	localSQLException	SQLException
    //   11	16	3	localSQLiteDatabase	SQLiteDatabase
    //   16	15	4	arrayOfString	String[]
    //   44	3	5	localSQLiteException	SQLiteException
    // Exception table:
    //   from	to	target	type
    //   7	12	36	android/database/SQLException
    //   26	35	44	android/database/sqlite/SQLiteException
  }
  
  public void truncateVideoUrl()
  {
    try
    {
      getWritableDatabase().delete("video_urls", null, null);
      return;
    }
    catch (SQLException localSQLException)
    {
      for (;;)
      {
        localSQLException.printStackTrace();
      }
    }
  }
  
  public class MaxBookmarkCountException
    extends Exception
  {
    private static final long serialVersionUID = 2199762006853457184L;
    
    public MaxBookmarkCountException(String paramString)
    {
      super();
    }
  }
  
  public class MaxMylistCountException
    extends Exception
  {
    private static final long serialVersionUID = -5633654818571253423L;
    
    public MaxMylistCountException(String paramString)
    {
      super();
    }
  }
  
  public class MaxVideoCountException
    extends Exception
  {
    private static final long serialVersionUID = -660291090791238476L;
    
    public MaxVideoCountException(String paramString)
    {
      super();
    }
  }
  
  public class ValidateErrorException
    extends Exception
  {
    private static final long serialVersionUID = 4905916494144973613L;
    
    public ValidateErrorException(String paramString)
    {
      super();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.VideoDbHelper
 * JD-Core Version:    0.7.0.1
 */