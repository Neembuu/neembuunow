package jp.co.cayto.appc.sdk.android.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class AppDB
  extends SQLiteOpenHelper
{
  private static final String CREATE_TABLE_BMP_BASE64 = "CREATE TABLE IF NOT EXISTS appc_bmp_base64( name TEXT PRIMARY KEY, data TEXT NOT NULL, time INTEGER NOT NULL);";
  private static final String CREATE_TABLE_CLICK_HISTORY = "CREATE TABLE IF NOT EXISTS appc_click_history( id INTEGER PRIMARY KEY AUTOINCREMENT, ad_apps_id VARCHR(255) NOT NULL, type VARCHAR(16) NOT NULL);";
  private static final String CREATE_TABLE_INSTALLED_AD = "CREATE TABLE IF NOT EXISTS appc_installed_ad( id INTEGER PRIMARY KEY AUTOINCREMENT, package VARCHR(255) NOT NULL, installed_time LONG  NOT NULL, installed_status INTEGER DEFAULT 0);";
  private static final String CREATE_TABLE_REGIST_CPI = "CREATE TABLE IF NOT EXISTS appc_regist_cpi( id INTEGER PRIMARY KEY AUTOINCREMENT, package VARCHAR(255) NOT NULL, nowtime LONG NOT NULL);";
  private static final String CREATE_TABLE_REGIST_XML = "CREATE TABLE IF NOT EXISTS  appc_regist_xml( id INTEGER PRIMARY KEY AUTOINCREMENT, mode VARCHAR(255) NOT NULL, nowtime LONG NOT NULL, data TEXT);";
  private static final Object LOCK = new Object();
  private static final String _BMP_BASE64 = "appc_bmp_base64";
  private static final String _CLICK_HISTORY = "appc_click_history";
  private static final String _DB_NAME = "appc.db";
  private static final int _DB_VERSION = 2;
  private static final String _INSTALLED_AD = "appc_installed_ad";
  private static final String _REGIST_CPI = "appc_regist_cpi";
  private static final String _REGIST_XML = "appc_regist_xml";
  
  public AppDB(Context paramContext)
  {
    super(paramContext, "appc.db", null, 2);
  }
  
  public void createBmpBase64(String paramString1, String paramString2)
  {
    synchronized (LOCK)
    {
      localSQLiteDatabase = getWritableDatabase();
      try
      {
        localSQLiteDatabase.beginTransaction();
        String[] arrayOfString1 = new String[1];
        arrayOfString1[0] = "time";
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = paramString1;
        Cursor localCursor = localSQLiteDatabase.query("appc_bmp_base64", arrayOfString1, "name = ?", arrayOfString2, null, null, null);
        if (!localCursor.moveToFirst()) {
          break label164;
        }
        long l = System.currentTimeMillis() - 259200000L;
        if (localCursor.getLong(0) < l)
        {
          ContentValues localContentValues2 = new ContentValues();
          localContentValues2.put("data", paramString2);
          localContentValues2.put("time", Long.valueOf(System.currentTimeMillis()));
          String[] arrayOfString3 = new String[1];
          arrayOfString3[0] = paramString1;
          localSQLiteDatabase.update("appc_bmp_base64", localContentValues2, "name = ?", arrayOfString3);
        }
        localSQLiteDatabase.setTransactionSuccessful();
      }
      catch (Exception localException)
      {
        for (;;)
        {
          ContentValues localContentValues1;
          localSQLiteDatabase.endTransaction();
          localSQLiteDatabase.close();
        }
        localObject2 = finally;
        throw localObject2;
      }
      finally
      {
        localSQLiteDatabase.endTransaction();
        localSQLiteDatabase.close();
      }
      return;
      label164:
      localContentValues1 = new ContentValues();
      localContentValues1.put("name", paramString1);
      localContentValues1.put("data", paramString2);
      localContentValues1.put("time", Long.valueOf(System.currentTimeMillis()));
      localSQLiteDatabase.insert("appc_bmp_base64", null, localContentValues1);
    }
  }
  
  /* Error */
  public void createCPIList(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: getstatic 52	jp/co/cayto/appc/sdk/android/common/AppDB:LOCK	Ljava/lang/Object;
    //   3: astore_3
    //   4: aload_3
    //   5: monitorenter
    //   6: aload_0
    //   7: invokevirtual 64	jp/co/cayto/appc/sdk/android/common/AppDB:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   10: astore 5
    //   12: aload 5
    //   14: invokevirtual 69	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   17: new 99	android/content/ContentValues
    //   20: dup
    //   21: invokespecial 100	android/content/ContentValues:<init>	()V
    //   24: astore 8
    //   26: aload 8
    //   28: ldc 136
    //   30: aload_1
    //   31: invokevirtual 105	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   34: aload 8
    //   36: ldc 138
    //   38: invokestatic 91	java/lang/System:currentTimeMillis	()J
    //   41: invokestatic 111	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   44: invokevirtual 114	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   47: aload 8
    //   49: ldc 102
    //   51: aload_2
    //   52: invokevirtual 105	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   55: aload 5
    //   57: ldc 43
    //   59: aconst_null
    //   60: aload 8
    //   62: invokevirtual 133	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   65: pop2
    //   66: aload 5
    //   68: invokevirtual 121	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   71: aload 5
    //   73: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   76: aload 5
    //   78: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   81: aload_3
    //   82: monitorexit
    //   83: return
    //   84: astore 7
    //   86: aload 5
    //   88: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   91: aload 5
    //   93: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   96: goto -15 -> 81
    //   99: astore 4
    //   101: aload_3
    //   102: monitorexit
    //   103: aload 4
    //   105: athrow
    //   106: astore 6
    //   108: aload 5
    //   110: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   113: aload 5
    //   115: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   118: aload 6
    //   120: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	121	0	this	AppDB
    //   0	121	1	paramString1	String
    //   0	121	2	paramString2	String
    //   3	99	3	localObject1	Object
    //   99	5	4	localObject2	Object
    //   10	104	5	localSQLiteDatabase	SQLiteDatabase
    //   106	13	6	localObject3	Object
    //   84	1	7	localException	Exception
    //   24	37	8	localContentValues	ContentValues
    // Exception table:
    //   from	to	target	type
    //   12	71	84	java/lang/Exception
    //   6	12	99	finally
    //   71	103	99	finally
    //   108	121	99	finally
    //   12	71	106	finally
  }
  
  /* Error */
  public void createClickHistory(String paramString)
  {
    // Byte code:
    //   0: getstatic 52	jp/co/cayto/appc/sdk/android/common/AppDB:LOCK	Ljava/lang/Object;
    //   3: astore_2
    //   4: aload_2
    //   5: monitorenter
    //   6: aload_0
    //   7: invokevirtual 64	jp/co/cayto/appc/sdk/android/common/AppDB:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   10: astore 4
    //   12: aload 4
    //   14: invokevirtual 69	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   17: new 99	android/content/ContentValues
    //   20: dup
    //   21: invokespecial 100	android/content/ContentValues:<init>	()V
    //   24: astore 7
    //   26: aload 7
    //   28: ldc 142
    //   30: aload_1
    //   31: invokevirtual 105	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   34: aload 7
    //   36: ldc 144
    //   38: ldc 146
    //   40: invokevirtual 105	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   43: aload 4
    //   45: ldc 28
    //   47: aconst_null
    //   48: aload 7
    //   50: invokevirtual 133	android/database/sqlite/SQLiteDatabase:insert	(Ljava/lang/String;Ljava/lang/String;Landroid/content/ContentValues;)J
    //   53: pop2
    //   54: aload 4
    //   56: invokevirtual 121	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   59: aload 4
    //   61: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   64: aload 4
    //   66: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   69: aload_2
    //   70: monitorexit
    //   71: return
    //   72: astore 6
    //   74: aload 4
    //   76: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   79: aload 4
    //   81: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   84: goto -15 -> 69
    //   87: astore_3
    //   88: aload_2
    //   89: monitorexit
    //   90: aload_3
    //   91: athrow
    //   92: astore 5
    //   94: aload 4
    //   96: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   99: aload 4
    //   101: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   104: aload 5
    //   106: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	107	0	this	AppDB
    //   0	107	1	paramString	String
    //   3	86	2	localObject1	Object
    //   87	4	3	localObject2	Object
    //   10	90	4	localSQLiteDatabase	SQLiteDatabase
    //   92	13	5	localObject3	Object
    //   72	1	6	localException	Exception
    //   24	25	7	localContentValues	ContentValues
    // Exception table:
    //   from	to	target	type
    //   12	59	72	java/lang/Exception
    //   6	12	87	finally
    //   59	90	87	finally
    //   94	107	87	finally
    //   12	59	92	finally
  }
  
  public void createRegistCPI(String paramString)
  {
    synchronized (LOCK)
    {
      SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
      try
      {
        localSQLiteDatabase.beginTransaction();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("package", paramString);
        localContentValues.put("nowtime", Long.valueOf(System.currentTimeMillis()));
        localSQLiteDatabase.insert("appc_regist_cpi", null, localContentValues);
        localSQLiteDatabase.setTransactionSuccessful();
        localSQLiteDatabase.endTransaction();
        localSQLiteDatabase.close();
        return;
      }
      finally
      {
        localObject3 = finally;
        localSQLiteDatabase.endTransaction();
        localSQLiteDatabase.close();
        throw localObject3;
      }
    }
  }
  
  /* Error */
  public String findCPIList(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: getstatic 52	jp/co/cayto/appc/sdk/android/common/AppDB:LOCK	Ljava/lang/Object;
    //   5: astore_3
    //   6: aload_3
    //   7: monitorenter
    //   8: aload_0
    //   9: invokevirtual 154	jp/co/cayto/appc/sdk/android/common/AppDB:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   12: astore 5
    //   14: invokestatic 91	java/lang/System:currentTimeMillis	()J
    //   17: lstore 6
    //   19: lload 6
    //   21: ldc2_w 155
    //   24: lsub
    //   25: lstore 8
    //   27: aconst_null
    //   28: astore 10
    //   30: iconst_1
    //   31: anewarray 71	java/lang/String
    //   34: astore 13
    //   36: aload 13
    //   38: iconst_0
    //   39: ldc 102
    //   41: aastore
    //   42: iconst_2
    //   43: anewarray 71	java/lang/String
    //   46: astore 14
    //   48: aload 14
    //   50: iconst_0
    //   51: aload_1
    //   52: aastore
    //   53: aload 14
    //   55: iconst_1
    //   56: lload 8
    //   58: invokestatic 159	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   61: aastore
    //   62: aload 5
    //   64: ldc 43
    //   66: aload 13
    //   68: ldc 161
    //   70: aload 14
    //   72: aconst_null
    //   73: aconst_null
    //   74: ldc 163
    //   76: invokevirtual 79	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   79: astore 10
    //   81: aload 10
    //   83: invokeinterface 85 1 0
    //   88: ifeq +16 -> 104
    //   91: aload 10
    //   93: iconst_0
    //   94: invokeinterface 167 2 0
    //   99: astore 15
    //   101: aload 15
    //   103: astore_2
    //   104: aload 10
    //   106: invokeinterface 168 1 0
    //   111: aload 5
    //   113: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   116: aload_3
    //   117: monitorexit
    //   118: aload_2
    //   119: areturn
    //   120: astore 12
    //   122: aload 10
    //   124: invokeinterface 168 1 0
    //   129: aload 5
    //   131: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   134: goto -18 -> 116
    //   137: astore 4
    //   139: aload_3
    //   140: monitorexit
    //   141: aload 4
    //   143: athrow
    //   144: astore 11
    //   146: aload 10
    //   148: invokeinterface 168 1 0
    //   153: aload 5
    //   155: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   158: aload 11
    //   160: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	161	0	this	AppDB
    //   0	161	1	paramString	String
    //   1	118	2	localObject1	Object
    //   5	135	3	localObject2	Object
    //   137	5	4	localObject3	Object
    //   12	142	5	localSQLiteDatabase	SQLiteDatabase
    //   17	3	6	l1	long
    //   25	32	8	l2	long
    //   28	119	10	localCursor	Cursor
    //   144	15	11	localObject4	Object
    //   120	1	12	localException	Exception
    //   34	33	13	arrayOfString1	String[]
    //   46	25	14	arrayOfString2	String[]
    //   99	3	15	str	String
    // Exception table:
    //   from	to	target	type
    //   30	101	120	java/lang/Exception
    //   8	19	137	finally
    //   104	141	137	finally
    //   146	161	137	finally
    //   30	101	144	finally
  }
  
  /* Error */
  public String findCPIListOneDay(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: getstatic 52	jp/co/cayto/appc/sdk/android/common/AppDB:LOCK	Ljava/lang/Object;
    //   5: astore_3
    //   6: aload_3
    //   7: monitorenter
    //   8: aload_0
    //   9: invokevirtual 154	jp/co/cayto/appc/sdk/android/common/AppDB:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   12: astore 5
    //   14: invokestatic 91	java/lang/System:currentTimeMillis	()J
    //   17: lstore 6
    //   19: lload 6
    //   21: ldc2_w 170
    //   24: lsub
    //   25: lstore 8
    //   27: aconst_null
    //   28: astore 10
    //   30: iconst_1
    //   31: anewarray 71	java/lang/String
    //   34: astore 13
    //   36: aload 13
    //   38: iconst_0
    //   39: ldc 102
    //   41: aastore
    //   42: iconst_2
    //   43: anewarray 71	java/lang/String
    //   46: astore 14
    //   48: aload 14
    //   50: iconst_0
    //   51: aload_1
    //   52: aastore
    //   53: aload 14
    //   55: iconst_1
    //   56: lload 8
    //   58: invokestatic 159	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   61: aastore
    //   62: aload 5
    //   64: ldc 43
    //   66: aload 13
    //   68: ldc 161
    //   70: aload 14
    //   72: aconst_null
    //   73: aconst_null
    //   74: ldc 163
    //   76: invokevirtual 79	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   79: astore 10
    //   81: aload 10
    //   83: invokeinterface 85 1 0
    //   88: ifeq +16 -> 104
    //   91: aload 10
    //   93: iconst_0
    //   94: invokeinterface 167 2 0
    //   99: astore 15
    //   101: aload 15
    //   103: astore_2
    //   104: aload 10
    //   106: invokeinterface 168 1 0
    //   111: aload 5
    //   113: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   116: aload_3
    //   117: monitorexit
    //   118: aload_2
    //   119: areturn
    //   120: astore 12
    //   122: aload 10
    //   124: invokeinterface 168 1 0
    //   129: aload 5
    //   131: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   134: goto -18 -> 116
    //   137: astore 4
    //   139: aload_3
    //   140: monitorexit
    //   141: aload 4
    //   143: athrow
    //   144: astore 11
    //   146: aload 10
    //   148: invokeinterface 168 1 0
    //   153: aload 5
    //   155: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   158: aload 11
    //   160: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	161	0	this	AppDB
    //   0	161	1	paramString	String
    //   1	118	2	localObject1	Object
    //   5	135	3	localObject2	Object
    //   137	5	4	localObject3	Object
    //   12	142	5	localSQLiteDatabase	SQLiteDatabase
    //   17	3	6	l1	long
    //   25	32	8	l2	long
    //   28	119	10	localCursor	Cursor
    //   144	15	11	localObject4	Object
    //   120	1	12	localException	Exception
    //   34	33	13	arrayOfString1	String[]
    //   46	25	14	arrayOfString2	String[]
    //   99	3	15	str	String
    // Exception table:
    //   from	to	target	type
    //   30	101	120	java/lang/Exception
    //   8	19	137	finally
    //   104	141	137	finally
    //   146	161	137	finally
    //   30	101	144	finally
  }
  
  public boolean isClickHistory(String paramString)
  {
    synchronized (LOCK)
    {
      SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
      Cursor localCursor = null;
      try
      {
        String[] arrayOfString1 = new String[1];
        arrayOfString1[0] = "ad_apps_id";
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = paramString;
        localCursor = localSQLiteDatabase.query("appc_click_history", arrayOfString1, "ad_apps_id = ? AND type = 'app'", arrayOfString2, null, null, null);
        boolean bool = localCursor.moveToFirst();
        localCursor.close();
        localSQLiteDatabase.close();
        return bool;
      }
      finally
      {
        localObject3 = finally;
        localCursor.close();
        localSQLiteDatabase.close();
        throw localObject3;
      }
    }
  }
  
  /* Error */
  public boolean isRegistCPI(String paramString)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: getstatic 52	jp/co/cayto/appc/sdk/android/common/AppDB:LOCK	Ljava/lang/Object;
    //   5: astore_3
    //   6: aload_3
    //   7: monitorenter
    //   8: aload_0
    //   9: invokevirtual 154	jp/co/cayto/appc/sdk/android/common/AppDB:getReadableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   12: astore 5
    //   14: invokestatic 91	java/lang/System:currentTimeMillis	()J
    //   17: lstore 6
    //   19: lload 6
    //   21: ldc2_w 177
    //   24: lsub
    //   25: lstore 8
    //   27: aconst_null
    //   28: astore 10
    //   30: iconst_1
    //   31: anewarray 71	java/lang/String
    //   34: astore 13
    //   36: aload 13
    //   38: iconst_0
    //   39: ldc 149
    //   41: aastore
    //   42: iconst_2
    //   43: anewarray 71	java/lang/String
    //   46: astore 14
    //   48: aload 14
    //   50: iconst_0
    //   51: aload_1
    //   52: aastore
    //   53: aload 14
    //   55: iconst_1
    //   56: lload 8
    //   58: invokestatic 159	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   61: aastore
    //   62: aload 5
    //   64: ldc 40
    //   66: aload 13
    //   68: ldc 180
    //   70: aload 14
    //   72: aconst_null
    //   73: aconst_null
    //   74: aconst_null
    //   75: invokevirtual 79	android/database/sqlite/SQLiteDatabase:query	(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   78: astore 10
    //   80: aload 10
    //   82: invokeinterface 85 1 0
    //   87: istore 15
    //   89: iload 15
    //   91: istore_2
    //   92: aload 10
    //   94: invokeinterface 168 1 0
    //   99: aload 5
    //   101: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   104: aload_3
    //   105: monitorexit
    //   106: iload_2
    //   107: ireturn
    //   108: astore 12
    //   110: aload 10
    //   112: invokeinterface 168 1 0
    //   117: aload 5
    //   119: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   122: goto -18 -> 104
    //   125: astore 4
    //   127: aload_3
    //   128: monitorexit
    //   129: aload 4
    //   131: athrow
    //   132: astore 11
    //   134: aload 10
    //   136: invokeinterface 168 1 0
    //   141: aload 5
    //   143: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   146: aload 11
    //   148: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	149	0	this	AppDB
    //   0	149	1	paramString	String
    //   1	106	2	bool1	boolean
    //   5	123	3	localObject1	Object
    //   125	5	4	localObject2	Object
    //   12	130	5	localSQLiteDatabase	SQLiteDatabase
    //   17	3	6	l1	long
    //   25	32	8	l2	long
    //   28	107	10	localCursor	Cursor
    //   132	15	11	localObject3	Object
    //   108	1	12	localException	Exception
    //   34	33	13	arrayOfString1	String[]
    //   46	25	14	arrayOfString2	String[]
    //   87	3	15	bool2	boolean
    // Exception table:
    //   from	to	target	type
    //   30	89	108	java/lang/Exception
    //   8	19	125	finally
    //   92	129	125	finally
    //   134	149	125	finally
    //   30	89	132	finally
  }
  
  public String loadBmpBase64(String paramString, boolean paramBoolean)
  {
    Object localObject1 = null;
    synchronized (LOCK)
    {
      localSQLiteDatabase = getReadableDatabase();
      localCursor = null;
      if (paramBoolean) {}
      do
      {
        try
        {
          String[] arrayOfString3 = new String[1];
          arrayOfString3[0] = "data";
          String[] arrayOfString4 = new String[1];
          arrayOfString4[0] = paramString;
          localCursor = localSQLiteDatabase.query("appc_bmp_base64", arrayOfString3, "name = ?", arrayOfString4, null, null, null);
          if (localCursor.moveToFirst())
          {
            String str2 = localCursor.getString(0);
            localObject1 = str2;
          }
        }
        catch (Exception localException)
        {
          for (;;)
          {
            long l;
            String[] arrayOfString1;
            String[] arrayOfString2;
            String str1;
            localCursor.close();
            localSQLiteDatabase.close();
          }
          localObject3 = finally;
          throw localObject3;
        }
        finally
        {
          localCursor.close();
          localSQLiteDatabase.close();
        }
        return localObject1;
        l = System.currentTimeMillis() - 259200000L;
        arrayOfString1 = new String[1];
        arrayOfString1[0] = "data";
        arrayOfString2 = new String[2];
        arrayOfString2[0] = paramString;
        arrayOfString2[1] = String.valueOf(l);
        localCursor = localSQLiteDatabase.query("appc_bmp_base64", arrayOfString1, "name = ? AND time > ?", arrayOfString2, null, null, null);
      } while (!localCursor.moveToFirst());
      str1 = localCursor.getString(0);
      localObject1 = str1;
    }
  }
  
  public ArrayList<String> loadClickHistorys()
  {
    ArrayList localArrayList = new ArrayList();
    synchronized (LOCK)
    {
      SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
      Cursor localCursor = null;
      try
      {
        String[] arrayOfString = new String[1];
        arrayOfString[0] = "ad_apps_id";
        localCursor = localSQLiteDatabase.query("appc_click_history", arrayOfString, null, null, null, null, null);
        int i;
        if (localCursor.moveToFirst()) {
          i = localCursor.getCount();
        }
        for (int j = 0;; j++)
        {
          if (j >= i)
          {
            localCursor.close();
            localSQLiteDatabase.close();
            return localArrayList;
          }
          localArrayList.add(localCursor.getString(0));
          localCursor.moveToNext();
        }
        localObject2 = finally;
      }
      finally
      {
        localCursor.close();
        localSQLiteDatabase.close();
      }
    }
  }
  
  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    try
    {
      paramSQLiteDatabase.beginTransaction();
      paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS appc_regist_cpi( id INTEGER PRIMARY KEY AUTOINCREMENT, package VARCHAR(255) NOT NULL, nowtime LONG NOT NULL);");
      paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS  appc_regist_xml( id INTEGER PRIMARY KEY AUTOINCREMENT, mode VARCHAR(255) NOT NULL, nowtime LONG NOT NULL, data TEXT);");
      paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS appc_click_history( id INTEGER PRIMARY KEY AUTOINCREMENT, ad_apps_id VARCHR(255) NOT NULL, type VARCHAR(16) NOT NULL);");
      paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS appc_installed_ad( id INTEGER PRIMARY KEY AUTOINCREMENT, package VARCHR(255) NOT NULL, installed_time LONG  NOT NULL, installed_status INTEGER DEFAULT 0);");
      paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS appc_bmp_base64( name TEXT PRIMARY KEY, data TEXT NOT NULL, time INTEGER NOT NULL);");
      paramSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      paramSQLiteDatabase.endTransaction();
    }
  }
  
  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    try
    {
      paramSQLiteDatabase.beginTransaction();
      paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS appc_bmp_base64( name TEXT PRIMARY KEY, data TEXT NOT NULL, time INTEGER NOT NULL);");
      paramSQLiteDatabase.setTransactionSuccessful();
      return;
    }
    finally
    {
      paramSQLiteDatabase.endTransaction();
    }
  }
  
  /* Error */
  public void removeBmpBase64()
  {
    // Byte code:
    //   0: getstatic 52	jp/co/cayto/appc/sdk/android/common/AppDB:LOCK	Ljava/lang/Object;
    //   3: astore_1
    //   4: aload_1
    //   5: monitorenter
    //   6: aload_0
    //   7: invokevirtual 64	jp/co/cayto/appc/sdk/android/common/AppDB:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   10: astore_3
    //   11: aload_3
    //   12: invokevirtual 69	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   15: invokestatic 91	java/lang/System:currentTimeMillis	()J
    //   18: ldc2_w 209
    //   21: ladd
    //   22: lstore 6
    //   24: iconst_1
    //   25: anewarray 71	java/lang/String
    //   28: astore 8
    //   30: aload 8
    //   32: iconst_0
    //   33: lload 6
    //   35: invokestatic 159	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   38: aastore
    //   39: aload_3
    //   40: ldc 25
    //   42: ldc 212
    //   44: aload 8
    //   46: invokevirtual 216	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   49: pop
    //   50: aload_3
    //   51: invokevirtual 121	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   54: aload_3
    //   55: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   58: aload_3
    //   59: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   62: aload_1
    //   63: monitorexit
    //   64: return
    //   65: astore 5
    //   67: aload_3
    //   68: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   71: aload_3
    //   72: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   75: goto -13 -> 62
    //   78: astore_2
    //   79: aload_1
    //   80: monitorexit
    //   81: aload_2
    //   82: athrow
    //   83: astore 4
    //   85: aload_3
    //   86: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   89: aload_3
    //   90: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   93: aload 4
    //   95: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	96	0	this	AppDB
    //   3	77	1	localObject1	Object
    //   78	4	2	localObject2	Object
    //   10	80	3	localSQLiteDatabase	SQLiteDatabase
    //   83	11	4	localObject3	Object
    //   65	1	5	localException	Exception
    //   22	12	6	l	long
    //   28	17	8	arrayOfString	String[]
    // Exception table:
    //   from	to	target	type
    //   11	54	65	java/lang/Exception
    //   6	11	78	finally
    //   54	81	78	finally
    //   85	96	78	finally
    //   11	54	83	finally
  }
  
  /* Error */
  public void removeCPIListByOld()
  {
    // Byte code:
    //   0: getstatic 52	jp/co/cayto/appc/sdk/android/common/AppDB:LOCK	Ljava/lang/Object;
    //   3: astore_1
    //   4: aload_1
    //   5: monitorenter
    //   6: aload_0
    //   7: invokevirtual 64	jp/co/cayto/appc/sdk/android/common/AppDB:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   10: astore_3
    //   11: aload_3
    //   12: invokevirtual 69	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   15: invokestatic 91	java/lang/System:currentTimeMillis	()J
    //   18: ldc2_w 218
    //   21: lsub
    //   22: lstore 6
    //   24: iconst_1
    //   25: anewarray 71	java/lang/String
    //   28: astore 8
    //   30: aload 8
    //   32: iconst_0
    //   33: lload 6
    //   35: invokestatic 159	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   38: aastore
    //   39: aload_3
    //   40: ldc 43
    //   42: ldc 221
    //   44: aload 8
    //   46: invokevirtual 216	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   49: pop
    //   50: aload_3
    //   51: invokevirtual 121	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   54: aload_3
    //   55: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   58: aload_3
    //   59: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   62: aload_1
    //   63: monitorexit
    //   64: return
    //   65: astore 5
    //   67: aload_3
    //   68: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   71: aload_3
    //   72: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   75: goto -13 -> 62
    //   78: astore_2
    //   79: aload_1
    //   80: monitorexit
    //   81: aload_2
    //   82: athrow
    //   83: astore 4
    //   85: aload_3
    //   86: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   89: aload_3
    //   90: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   93: aload 4
    //   95: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	96	0	this	AppDB
    //   3	77	1	localObject1	Object
    //   78	4	2	localObject2	Object
    //   10	80	3	localSQLiteDatabase	SQLiteDatabase
    //   83	11	4	localObject3	Object
    //   65	1	5	localException	Exception
    //   22	12	6	l	long
    //   28	17	8	arrayOfString	String[]
    // Exception table:
    //   from	to	target	type
    //   11	54	65	java/lang/Exception
    //   6	11	78	finally
    //   54	81	78	finally
    //   85	96	78	finally
    //   11	54	83	finally
  }
  
  /* Error */
  public void removeRegistCPI()
  {
    // Byte code:
    //   0: getstatic 52	jp/co/cayto/appc/sdk/android/common/AppDB:LOCK	Ljava/lang/Object;
    //   3: astore_1
    //   4: aload_1
    //   5: monitorenter
    //   6: aload_0
    //   7: invokevirtual 64	jp/co/cayto/appc/sdk/android/common/AppDB:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   10: astore_3
    //   11: aload_3
    //   12: invokevirtual 69	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   15: invokestatic 91	java/lang/System:currentTimeMillis	()J
    //   18: ldc2_w 177
    //   21: lsub
    //   22: lstore 6
    //   24: iconst_1
    //   25: anewarray 71	java/lang/String
    //   28: astore 8
    //   30: aload 8
    //   32: iconst_0
    //   33: lload 6
    //   35: invokestatic 159	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   38: aastore
    //   39: aload_3
    //   40: ldc 40
    //   42: ldc 224
    //   44: aload 8
    //   46: invokevirtual 216	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   49: pop
    //   50: aload_3
    //   51: invokevirtual 121	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   54: aload_3
    //   55: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   58: aload_3
    //   59: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   62: aload_1
    //   63: monitorexit
    //   64: return
    //   65: astore 5
    //   67: aload_3
    //   68: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   71: aload_3
    //   72: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   75: goto -13 -> 62
    //   78: astore_2
    //   79: aload_1
    //   80: monitorexit
    //   81: aload_2
    //   82: athrow
    //   83: astore 4
    //   85: aload_3
    //   86: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   89: aload_3
    //   90: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   93: aload 4
    //   95: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	96	0	this	AppDB
    //   3	77	1	localObject1	Object
    //   78	4	2	localObject2	Object
    //   10	80	3	localSQLiteDatabase	SQLiteDatabase
    //   83	11	4	localObject3	Object
    //   65	1	5	localException	Exception
    //   22	12	6	l	long
    //   28	17	8	arrayOfString	String[]
    // Exception table:
    //   from	to	target	type
    //   11	54	65	java/lang/Exception
    //   6	11	78	finally
    //   54	81	78	finally
    //   85	96	78	finally
    //   11	54	83	finally
  }
  
  /* Error */
  public void removeRegistCPIByPkgName(String paramString)
  {
    // Byte code:
    //   0: getstatic 52	jp/co/cayto/appc/sdk/android/common/AppDB:LOCK	Ljava/lang/Object;
    //   3: astore_2
    //   4: aload_2
    //   5: monitorenter
    //   6: aload_0
    //   7: invokevirtual 64	jp/co/cayto/appc/sdk/android/common/AppDB:getWritableDatabase	()Landroid/database/sqlite/SQLiteDatabase;
    //   10: astore 4
    //   12: aload 4
    //   14: invokevirtual 69	android/database/sqlite/SQLiteDatabase:beginTransaction	()V
    //   17: invokestatic 91	java/lang/System:currentTimeMillis	()J
    //   20: ldc2_w 177
    //   23: lsub
    //   24: lstore 7
    //   26: iconst_2
    //   27: anewarray 71	java/lang/String
    //   30: astore 9
    //   32: aload 9
    //   34: iconst_0
    //   35: aload_1
    //   36: aastore
    //   37: aload 9
    //   39: iconst_1
    //   40: lload 7
    //   42: invokestatic 159	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   45: aastore
    //   46: aload 4
    //   48: ldc 40
    //   50: ldc 227
    //   52: aload 9
    //   54: invokevirtual 216	android/database/sqlite/SQLiteDatabase:delete	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)I
    //   57: pop
    //   58: aload 4
    //   60: invokevirtual 121	android/database/sqlite/SQLiteDatabase:setTransactionSuccessful	()V
    //   63: aload 4
    //   65: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   68: aload 4
    //   70: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   73: aload_2
    //   74: monitorexit
    //   75: return
    //   76: astore 6
    //   78: aload 4
    //   80: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   83: aload 4
    //   85: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   88: goto -15 -> 73
    //   91: astore_3
    //   92: aload_2
    //   93: monitorexit
    //   94: aload_3
    //   95: athrow
    //   96: astore 5
    //   98: aload 4
    //   100: invokevirtual 124	android/database/sqlite/SQLiteDatabase:endTransaction	()V
    //   103: aload 4
    //   105: invokevirtual 127	android/database/sqlite/SQLiteDatabase:close	()V
    //   108: aload 5
    //   110: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	111	0	this	AppDB
    //   0	111	1	paramString	String
    //   3	90	2	localObject1	Object
    //   91	4	3	localObject2	Object
    //   10	94	4	localSQLiteDatabase	SQLiteDatabase
    //   96	13	5	localObject3	Object
    //   76	1	6	localException	Exception
    //   24	17	7	l	long
    //   30	23	9	arrayOfString	String[]
    // Exception table:
    //   from	to	target	type
    //   12	63	76	java/lang/Exception
    //   6	12	91	finally
    //   63	94	91	finally
    //   98	111	91	finally
    //   12	63	96	finally
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.common.AppDB
 * JD-Core Version:    0.7.0.1
 */