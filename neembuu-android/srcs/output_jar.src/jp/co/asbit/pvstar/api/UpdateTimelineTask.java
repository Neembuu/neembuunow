package jp.co.asbit.pvstar.api;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import jp.co.asbit.pvstar.AsyncTask;
import jp.co.asbit.pvstar.Video;
import twitter4j.Query;
import twitter4j.auth.AccessToken;

public class UpdateTimelineTask
  extends AsyncTask<Integer, Long, ArrayList<Video>>
{
  private static final String TAG = "UpdateTimelineTask";
  private Context mContext;
  private long maxId = 0L;
  private Query query;
  private AccessToken token = null;
  
  public UpdateTimelineTask(Context paramContext, String paramString1, int paramInt, long paramLong, String paramString2, AccessToken paramAccessToken)
  {
    this.mContext = paramContext;
    this.query = new Query(paramString1);
    this.query.setCount(10);
    if (paramAccessToken != null) {
      this.token = paramAccessToken;
    }
    if (paramString2 != null) {
      this.query.setLang(paramString2);
    }
    Log.d("UpdateTimelineTask", "construct maxId:" + paramLong);
    if (paramLong > 0L) {
      this.query.setMaxId(paramLong);
    }
  }
  
  public String diffDate(Date paramDate)
  {
    long l1 = paramDate.getTime();
    long l2 = (new Date().getTime() - l1) / 1000L;
    String str;
    if (l2 < 60L)
    {
      Context localContext4 = this.mContext;
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = Long.valueOf(l2);
      str = localContext4.getString(2131296509, arrayOfObject4);
    }
    for (;;)
    {
      return str;
      if (l2 < 3600L)
      {
        Context localContext3 = this.mContext;
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = Long.valueOf(l2 / 60L);
        str = localContext3.getString(2131296510, arrayOfObject3);
      }
      else if (l2 < 86400L)
      {
        Context localContext2 = this.mContext;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Long.valueOf(l2 / 3600L);
        str = localContext2.getString(2131296511, arrayOfObject2);
      }
      else
      {
        Context localContext1 = this.mContext;
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Long.valueOf(l2 / 86400L);
        str = localContext1.getString(2131296512, arrayOfObject1);
      }
    }
  }
  
  /* Error */
  protected ArrayList<Video> doInBackground(Integer... paramVarArgs)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: new 118	twitter4j/conf/ConfigurationBuilder
    //   5: dup
    //   6: invokespecial 119	twitter4j/conf/ConfigurationBuilder:<init>	()V
    //   9: astore_2
    //   10: aload_2
    //   11: ldc 121
    //   13: invokevirtual 125	twitter4j/conf/ConfigurationBuilder:setOAuthConsumerKey	(Ljava/lang/String;)Ltwitter4j/conf/ConfigurationBuilder;
    //   16: pop
    //   17: aload_2
    //   18: ldc 127
    //   20: invokevirtual 130	twitter4j/conf/ConfigurationBuilder:setOAuthConsumerSecret	(Ljava/lang/String;)Ltwitter4j/conf/ConfigurationBuilder;
    //   23: pop
    //   24: new 132	twitter4j/TwitterFactory
    //   27: dup
    //   28: aload_2
    //   29: invokevirtual 136	twitter4j/conf/ConfigurationBuilder:build	()Ltwitter4j/conf/Configuration;
    //   32: invokespecial 139	twitter4j/TwitterFactory:<init>	(Ltwitter4j/conf/Configuration;)V
    //   35: aload_0
    //   36: getfield 29	jp/co/asbit/pvstar/api/UpdateTimelineTask:token	Ltwitter4j/auth/AccessToken;
    //   39: invokevirtual 143	twitter4j/TwitterFactory:getInstance	(Ltwitter4j/auth/AccessToken;)Ltwitter4j/Twitter;
    //   42: astore 4
    //   44: ldc 12
    //   46: ldc 145
    //   48: invokestatic 64	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   51: pop
    //   52: aload 4
    //   54: aload_0
    //   55: getfield 38	jp/co/asbit/pvstar/api/UpdateTimelineTask:query	Ltwitter4j/Query;
    //   58: invokeinterface 151 2 0
    //   63: invokeinterface 157 1 0
    //   68: astore 9
    //   70: new 159	java/util/ArrayList
    //   73: dup
    //   74: invokespecial 160	java/util/ArrayList:<init>	()V
    //   77: astore 10
    //   79: new 7	jp/co/asbit/pvstar/api/UpdateTimelineTask$NamePatternPair
    //   82: dup
    //   83: aload_0
    //   84: ldc 162
    //   86: ldc 164
    //   88: invokespecial 167	jp/co/asbit/pvstar/api/UpdateTimelineTask$NamePatternPair:<init>	(Ljp/co/asbit/pvstar/api/UpdateTimelineTask;Ljava/lang/String;Ljava/lang/String;)V
    //   91: astore 11
    //   93: aload 10
    //   95: aload 11
    //   97: invokevirtual 171	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   100: pop
    //   101: new 7	jp/co/asbit/pvstar/api/UpdateTimelineTask$NamePatternPair
    //   104: dup
    //   105: aload_0
    //   106: ldc 162
    //   108: ldc 173
    //   110: invokespecial 167	jp/co/asbit/pvstar/api/UpdateTimelineTask$NamePatternPair:<init>	(Ljp/co/asbit/pvstar/api/UpdateTimelineTask;Ljava/lang/String;Ljava/lang/String;)V
    //   113: astore 13
    //   115: aload 10
    //   117: aload 13
    //   119: invokevirtual 171	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   122: pop
    //   123: new 159	java/util/ArrayList
    //   126: dup
    //   127: invokespecial 160	java/util/ArrayList:<init>	()V
    //   130: astore 7
    //   132: aload 9
    //   134: invokeinterface 179 1 0
    //   139: astore 15
    //   141: aload 15
    //   143: invokeinterface 185 1 0
    //   148: ifne +89 -> 237
    //   151: ldc 12
    //   153: new 47	java/lang/StringBuilder
    //   156: dup
    //   157: ldc 187
    //   159: invokespecial 50	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   162: aload_0
    //   163: getfield 27	jp/co/asbit/pvstar/api/UpdateTimelineTask:maxId	J
    //   166: invokevirtual 54	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   169: invokevirtual 58	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   172: invokestatic 64	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   175: pop
    //   176: aload_0
    //   177: monitorexit
    //   178: goto +348 -> 526
    //   181: astore 27
    //   183: new 132	twitter4j/TwitterFactory
    //   186: dup
    //   187: invokespecial 188	twitter4j/TwitterFactory:<init>	()V
    //   190: invokevirtual 191	twitter4j/TwitterFactory:getInstance	()Ltwitter4j/Twitter;
    //   193: astore 4
    //   195: ldc 12
    //   197: ldc 193
    //   199: invokestatic 64	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   202: pop
    //   203: goto -151 -> 52
    //   206: astore 8
    //   208: aload_0
    //   209: monitorexit
    //   210: aload 8
    //   212: athrow
    //   213: astore_3
    //   214: new 132	twitter4j/TwitterFactory
    //   217: dup
    //   218: invokespecial 188	twitter4j/TwitterFactory:<init>	()V
    //   221: invokevirtual 191	twitter4j/TwitterFactory:getInstance	()Ltwitter4j/Twitter;
    //   224: astore 4
    //   226: ldc 12
    //   228: ldc 195
    //   230: invokestatic 64	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   233: pop
    //   234: goto -182 -> 52
    //   237: aload 15
    //   239: invokeinterface 199 1 0
    //   244: checkcast 201	twitter4j/Status
    //   247: astore 16
    //   249: aload 16
    //   251: invokeinterface 204 1 0
    //   256: ldc 206
    //   258: invokevirtual 212	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   261: ifne -120 -> 141
    //   264: iconst_0
    //   265: istore 17
    //   267: aload 16
    //   269: invokeinterface 216 1 0
    //   274: astore 18
    //   276: aload 18
    //   278: ifnull +14 -> 292
    //   281: iconst_0
    //   282: istore 20
    //   284: iload 20
    //   286: aload 18
    //   288: arraylength
    //   289: if_icmplt +59 -> 348
    //   292: aload_0
    //   293: aload 16
    //   295: invokeinterface 219 1 0
    //   300: lconst_1
    //   301: lsub
    //   302: putfield 27	jp/co/asbit/pvstar/api/UpdateTimelineTask:maxId	J
    //   305: ldc 12
    //   307: new 47	java/lang/StringBuilder
    //   310: dup
    //   311: ldc 221
    //   313: invokespecial 50	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   316: aload_0
    //   317: getfield 27	jp/co/asbit/pvstar/api/UpdateTimelineTask:maxId	J
    //   320: invokevirtual 54	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   323: invokevirtual 58	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   326: invokestatic 64	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   329: pop
    //   330: goto -189 -> 141
    //   333: astore 6
    //   335: aload 6
    //   337: invokevirtual 224	twitter4j/TwitterException:printStackTrace	()V
    //   340: aload_0
    //   341: monitorexit
    //   342: aconst_null
    //   343: astore 7
    //   345: goto +181 -> 526
    //   348: aload 10
    //   350: invokevirtual 225	java/util/ArrayList:iterator	()Ljava/util/Iterator;
    //   353: astore 21
    //   355: aload 21
    //   357: invokeinterface 185 1 0
    //   362: ifne +6 -> 368
    //   365: goto +164 -> 529
    //   368: aload 21
    //   370: invokeinterface 199 1 0
    //   375: checkcast 7	jp/co/asbit/pvstar/api/UpdateTimelineTask$NamePatternPair
    //   378: astore 22
    //   380: aload 22
    //   382: invokevirtual 229	jp/co/asbit/pvstar/api/UpdateTimelineTask$NamePatternPair:getPattern	()Ljava/util/regex/Pattern;
    //   385: aload 18
    //   387: iload 20
    //   389: aaload
    //   390: invokeinterface 234 1 0
    //   395: invokevirtual 235	java/lang/String:toString	()Ljava/lang/String;
    //   398: invokevirtual 241	java/util/regex/Pattern:matcher	(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   401: astore 23
    //   403: aload 23
    //   405: invokevirtual 246	java/util/regex/Matcher:find	()Z
    //   408: ifeq -53 -> 355
    //   411: new 248	jp/co/asbit/pvstar/Video
    //   414: dup
    //   415: invokespecial 249	jp/co/asbit/pvstar/Video:<init>	()V
    //   418: astore 24
    //   420: aload 24
    //   422: aload 23
    //   424: iconst_1
    //   425: invokevirtual 253	java/util/regex/Matcher:group	(I)Ljava/lang/String;
    //   428: invokevirtual 256	jp/co/asbit/pvstar/Video:setId	(Ljava/lang/String;)V
    //   431: aload 24
    //   433: aload 22
    //   435: invokevirtual 259	jp/co/asbit/pvstar/api/UpdateTimelineTask$NamePatternPair:getName	()Ljava/lang/String;
    //   438: invokevirtual 262	jp/co/asbit/pvstar/Video:setSearchEngine	(Ljava/lang/String;)V
    //   441: aload 24
    //   443: aload 16
    //   445: invokeinterface 266 1 0
    //   450: invokeinterface 269 1 0
    //   455: invokevirtual 272	jp/co/asbit/pvstar/Video:setTitle	(Ljava/lang/String;)V
    //   458: aload 24
    //   460: aload 16
    //   462: invokeinterface 204 1 0
    //   467: ldc_w 274
    //   470: ldc_w 276
    //   473: invokevirtual 280	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   476: invokevirtual 283	jp/co/asbit/pvstar/Video:setDescription	(Ljava/lang/String;)V
    //   479: aload 24
    //   481: aload_0
    //   482: aload 16
    //   484: invokeinterface 287 1 0
    //   489: invokevirtual 289	jp/co/asbit/pvstar/api/UpdateTimelineTask:diffDate	(Ljava/util/Date;)Ljava/lang/String;
    //   492: invokevirtual 292	jp/co/asbit/pvstar/Video:setDuration	(Ljava/lang/String;)V
    //   495: aload 24
    //   497: aload 16
    //   499: invokeinterface 266 1 0
    //   504: invokeinterface 295 1 0
    //   509: invokevirtual 298	jp/co/asbit/pvstar/Video:setThumbnailUrl	(Ljava/lang/String;)V
    //   512: aload 7
    //   514: aload 24
    //   516: invokevirtual 171	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   519: pop
    //   520: iconst_1
    //   521: istore 17
    //   523: goto +6 -> 529
    //   526: aload 7
    //   528: areturn
    //   529: iload 17
    //   531: ifne -239 -> 292
    //   534: iinc 20 1
    //   537: goto -253 -> 284
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	540	0	this	UpdateTimelineTask
    //   0	540	1	paramVarArgs	Integer[]
    //   9	20	2	localConfigurationBuilder	twitter4j.conf.ConfigurationBuilder
    //   213	1	3	localNullPointerException	java.lang.NullPointerException
    //   42	183	4	localTwitter	twitter4j.Twitter
    //   333	3	6	localTwitterException	twitter4j.TwitterException
    //   130	397	7	localArrayList1	ArrayList
    //   206	5	8	localObject	Object
    //   68	65	9	localList	java.util.List
    //   77	272	10	localArrayList2	ArrayList
    //   91	5	11	localNamePatternPair1	NamePatternPair
    //   113	5	13	localNamePatternPair2	NamePatternPair
    //   139	99	15	localIterator1	java.util.Iterator
    //   247	251	16	localStatus	twitter4j.Status
    //   265	265	17	i	int
    //   274	112	18	arrayOfURLEntity	twitter4j.URLEntity[]
    //   282	253	20	j	int
    //   353	16	21	localIterator2	java.util.Iterator
    //   378	56	22	localNamePatternPair3	NamePatternPair
    //   401	22	23	localMatcher	java.util.regex.Matcher
    //   418	97	24	localVideo	Video
    //   181	1	27	localIllegalStateException	java.lang.IllegalStateException
    // Exception table:
    //   from	to	target	type
    //   2	52	181	java/lang/IllegalStateException
    //   2	52	206	finally
    //   52	176	206	finally
    //   176	210	206	finally
    //   214	234	206	finally
    //   237	330	206	finally
    //   335	342	206	finally
    //   348	520	206	finally
    //   2	52	213	java/lang/NullPointerException
    //   52	176	333	twitter4j/TwitterException
    //   237	330	333	twitter4j/TwitterException
    //   348	520	333	twitter4j/TwitterException
  }
  
  protected long getMaxId()
  {
    Log.d("UpdateTimelineTask", "get maxId:" + this.maxId);
    return this.maxId;
  }
  
  class NamePatternPair
  {
    String name;
    Pattern pattern;
    
    public NamePatternPair(String paramString1, String paramString2)
    {
      this.name = paramString1;
      this.pattern = Pattern.compile(paramString2);
    }
    
    public String getName()
    {
      return this.name;
    }
    
    public Pattern getPattern()
    {
      return this.pattern;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.api.UpdateTimelineTask
 * JD-Core Version:    0.7.0.1
 */