package twitter4j.internal.json;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import twitter4j.DirectMessage;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.ResponseList;
import twitter4j.SymbolEntity;
import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import twitter4j.conf.Configuration;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.org.json.JSONArray;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

final class DirectMessageJSONImpl
  extends TwitterResponseImpl
  implements DirectMessage, Serializable
{
  private static final long serialVersionUID = -7104233663827757577L;
  private Date createdAt;
  private HashtagEntity[] hashtagEntities;
  private long id;
  private MediaEntity[] mediaEntities;
  private User recipient;
  private long recipientId;
  private String recipientScreenName;
  private User sender;
  private long senderId;
  private String senderScreenName;
  private SymbolEntity[] symbolEntities;
  private String text;
  private URLEntity[] urlEntities;
  private UserMentionEntity[] userMentionEntities;
  
  DirectMessageJSONImpl(HttpResponse paramHttpResponse, Configuration paramConfiguration)
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
  
  DirectMessageJSONImpl(JSONObject paramJSONObject)
    throws TwitterException
  {
    init(paramJSONObject);
  }
  
  static ResponseList<DirectMessage> createDirectMessageList(HttpResponse paramHttpResponse, Configuration paramConfiguration)
    throws TwitterException
  {
    for (;;)
    {
      int j;
      try
      {
        if (paramConfiguration.isJSONStoreEnabled()) {
          DataObjectFactoryUtil.clearThreadLocalMap();
        }
        JSONArray localJSONArray = paramHttpResponse.asJSONArray();
        int i = localJSONArray.length();
        ResponseListImpl localResponseListImpl = new ResponseListImpl(i, paramHttpResponse);
        j = 0;
        if (j < i)
        {
          JSONObject localJSONObject = localJSONArray.getJSONObject(j);
          DirectMessageJSONImpl localDirectMessageJSONImpl = new DirectMessageJSONImpl(localJSONObject);
          localResponseListImpl.add(localDirectMessageJSONImpl);
          if (paramConfiguration.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.registerJSONObject(localDirectMessageJSONImpl, localJSONObject);
          }
        }
        else
        {
          if (paramConfiguration.isJSONStoreEnabled()) {
            DataObjectFactoryUtil.registerJSONObject(localResponseListImpl, localJSONArray);
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
  
  /* Error */
  private void init(JSONObject paramJSONObject)
    throws TwitterException
  {
    // Byte code:
    //   0: aload_0
    //   1: ldc 104
    //   3: aload_1
    //   4: invokestatic 110	twitter4j/internal/json/z_T4JInternalParseUtil:getLong	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)J
    //   7: putfield 112	twitter4j/internal/json/DirectMessageJSONImpl:id	J
    //   10: aload_0
    //   11: ldc 114
    //   13: aload_1
    //   14: invokestatic 110	twitter4j/internal/json/z_T4JInternalParseUtil:getLong	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)J
    //   17: putfield 116	twitter4j/internal/json/DirectMessageJSONImpl:senderId	J
    //   20: aload_0
    //   21: ldc 118
    //   23: aload_1
    //   24: invokestatic 110	twitter4j/internal/json/z_T4JInternalParseUtil:getLong	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)J
    //   27: putfield 120	twitter4j/internal/json/DirectMessageJSONImpl:recipientId	J
    //   30: aload_0
    //   31: ldc 122
    //   33: aload_1
    //   34: invokestatic 126	twitter4j/internal/json/z_T4JInternalParseUtil:getDate	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)Ljava/util/Date;
    //   37: putfield 128	twitter4j/internal/json/DirectMessageJSONImpl:createdAt	Ljava/util/Date;
    //   40: aload_0
    //   41: ldc 130
    //   43: aload_1
    //   44: invokestatic 134	twitter4j/internal/json/z_T4JInternalParseUtil:getUnescapedString	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)Ljava/lang/String;
    //   47: putfield 136	twitter4j/internal/json/DirectMessageJSONImpl:senderScreenName	Ljava/lang/String;
    //   50: aload_0
    //   51: ldc 138
    //   53: aload_1
    //   54: invokestatic 134	twitter4j/internal/json/z_T4JInternalParseUtil:getUnescapedString	(Ljava/lang/String;Ltwitter4j/internal/org/json/JSONObject;)Ljava/lang/String;
    //   57: putfield 140	twitter4j/internal/json/DirectMessageJSONImpl:recipientScreenName	Ljava/lang/String;
    //   60: aload_0
    //   61: new 142	twitter4j/internal/json/UserJSONImpl
    //   64: dup
    //   65: aload_1
    //   66: ldc 143
    //   68: invokevirtual 148	twitter4j/internal/org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Ltwitter4j/internal/org/json/JSONObject;
    //   71: invokespecial 149	twitter4j/internal/json/UserJSONImpl:<init>	(Ltwitter4j/internal/org/json/JSONObject;)V
    //   74: putfield 151	twitter4j/internal/json/DirectMessageJSONImpl:sender	Ltwitter4j/User;
    //   77: aload_0
    //   78: new 142	twitter4j/internal/json/UserJSONImpl
    //   81: dup
    //   82: aload_1
    //   83: ldc 152
    //   85: invokevirtual 148	twitter4j/internal/org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Ltwitter4j/internal/org/json/JSONObject;
    //   88: invokespecial 149	twitter4j/internal/json/UserJSONImpl:<init>	(Ltwitter4j/internal/org/json/JSONObject;)V
    //   91: putfield 154	twitter4j/internal/json/DirectMessageJSONImpl:recipient	Ltwitter4j/User;
    //   94: aload_1
    //   95: ldc 156
    //   97: invokevirtual 160	twitter4j/internal/org/json/JSONObject:isNull	(Ljava/lang/String;)Z
    //   100: ifne +371 -> 471
    //   103: aload_1
    //   104: ldc 156
    //   106: invokevirtual 148	twitter4j/internal/org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Ltwitter4j/internal/org/json/JSONObject;
    //   109: astore 8
    //   111: aload 8
    //   113: ldc 162
    //   115: invokevirtual 160	twitter4j/internal/org/json/JSONObject:isNull	(Ljava/lang/String;)Z
    //   118: ifne +65 -> 183
    //   121: aload 8
    //   123: ldc 162
    //   125: invokevirtual 166	twitter4j/internal/org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Ltwitter4j/internal/org/json/JSONArray;
    //   128: astore 21
    //   130: aload 21
    //   132: invokevirtual 83	twitter4j/internal/org/json/JSONArray:length	()I
    //   135: istore 22
    //   137: aload_0
    //   138: iload 22
    //   140: anewarray 168	twitter4j/UserMentionEntity
    //   143: putfield 170	twitter4j/internal/json/DirectMessageJSONImpl:userMentionEntities	[Ltwitter4j/UserMentionEntity;
    //   146: iconst_0
    //   147: istore 23
    //   149: iload 23
    //   151: iload 22
    //   153: if_icmpge +30 -> 183
    //   156: aload_0
    //   157: getfield 170	twitter4j/internal/json/DirectMessageJSONImpl:userMentionEntities	[Ltwitter4j/UserMentionEntity;
    //   160: iload 23
    //   162: new 172	twitter4j/internal/json/UserMentionEntityJSONImpl
    //   165: dup
    //   166: aload 21
    //   168: iload 23
    //   170: invokevirtual 92	twitter4j/internal/org/json/JSONArray:getJSONObject	(I)Ltwitter4j/internal/org/json/JSONObject;
    //   173: invokespecial 173	twitter4j/internal/json/UserMentionEntityJSONImpl:<init>	(Ltwitter4j/internal/org/json/JSONObject;)V
    //   176: aastore
    //   177: iinc 23 1
    //   180: goto -31 -> 149
    //   183: aload 8
    //   185: ldc 175
    //   187: invokevirtual 160	twitter4j/internal/org/json/JSONObject:isNull	(Ljava/lang/String;)Z
    //   190: ifne +65 -> 255
    //   193: aload 8
    //   195: ldc 175
    //   197: invokevirtual 166	twitter4j/internal/org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Ltwitter4j/internal/org/json/JSONArray;
    //   200: astore 18
    //   202: aload 18
    //   204: invokevirtual 83	twitter4j/internal/org/json/JSONArray:length	()I
    //   207: istore 19
    //   209: aload_0
    //   210: iload 19
    //   212: anewarray 177	twitter4j/URLEntity
    //   215: putfield 179	twitter4j/internal/json/DirectMessageJSONImpl:urlEntities	[Ltwitter4j/URLEntity;
    //   218: iconst_0
    //   219: istore 20
    //   221: iload 20
    //   223: iload 19
    //   225: if_icmpge +30 -> 255
    //   228: aload_0
    //   229: getfield 179	twitter4j/internal/json/DirectMessageJSONImpl:urlEntities	[Ltwitter4j/URLEntity;
    //   232: iload 20
    //   234: new 181	twitter4j/internal/json/URLEntityJSONImpl
    //   237: dup
    //   238: aload 18
    //   240: iload 20
    //   242: invokevirtual 92	twitter4j/internal/org/json/JSONArray:getJSONObject	(I)Ltwitter4j/internal/org/json/JSONObject;
    //   245: invokespecial 182	twitter4j/internal/json/URLEntityJSONImpl:<init>	(Ltwitter4j/internal/org/json/JSONObject;)V
    //   248: aastore
    //   249: iinc 20 1
    //   252: goto -31 -> 221
    //   255: aload 8
    //   257: ldc 184
    //   259: invokevirtual 160	twitter4j/internal/org/json/JSONObject:isNull	(Ljava/lang/String;)Z
    //   262: ifne +65 -> 327
    //   265: aload 8
    //   267: ldc 184
    //   269: invokevirtual 166	twitter4j/internal/org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Ltwitter4j/internal/org/json/JSONArray;
    //   272: astore 15
    //   274: aload 15
    //   276: invokevirtual 83	twitter4j/internal/org/json/JSONArray:length	()I
    //   279: istore 16
    //   281: aload_0
    //   282: iload 16
    //   284: anewarray 186	twitter4j/HashtagEntity
    //   287: putfield 188	twitter4j/internal/json/DirectMessageJSONImpl:hashtagEntities	[Ltwitter4j/HashtagEntity;
    //   290: iconst_0
    //   291: istore 17
    //   293: iload 17
    //   295: iload 16
    //   297: if_icmpge +30 -> 327
    //   300: aload_0
    //   301: getfield 188	twitter4j/internal/json/DirectMessageJSONImpl:hashtagEntities	[Ltwitter4j/HashtagEntity;
    //   304: iload 17
    //   306: new 190	twitter4j/internal/json/HashtagEntityJSONImpl
    //   309: dup
    //   310: aload 15
    //   312: iload 17
    //   314: invokevirtual 92	twitter4j/internal/org/json/JSONArray:getJSONObject	(I)Ltwitter4j/internal/org/json/JSONObject;
    //   317: invokespecial 191	twitter4j/internal/json/HashtagEntityJSONImpl:<init>	(Ltwitter4j/internal/org/json/JSONObject;)V
    //   320: aastore
    //   321: iinc 17 1
    //   324: goto -31 -> 293
    //   327: aload 8
    //   329: ldc 193
    //   331: invokevirtual 160	twitter4j/internal/org/json/JSONObject:isNull	(Ljava/lang/String;)Z
    //   334: ifne +65 -> 399
    //   337: aload 8
    //   339: ldc 193
    //   341: invokevirtual 166	twitter4j/internal/org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Ltwitter4j/internal/org/json/JSONArray;
    //   344: astore 12
    //   346: aload 12
    //   348: invokevirtual 83	twitter4j/internal/org/json/JSONArray:length	()I
    //   351: istore 13
    //   353: aload_0
    //   354: iload 13
    //   356: anewarray 195	twitter4j/SymbolEntity
    //   359: putfield 197	twitter4j/internal/json/DirectMessageJSONImpl:symbolEntities	[Ltwitter4j/SymbolEntity;
    //   362: iconst_0
    //   363: istore 14
    //   365: iload 14
    //   367: iload 13
    //   369: if_icmpge +30 -> 399
    //   372: aload_0
    //   373: getfield 197	twitter4j/internal/json/DirectMessageJSONImpl:symbolEntities	[Ltwitter4j/SymbolEntity;
    //   376: iload 14
    //   378: new 190	twitter4j/internal/json/HashtagEntityJSONImpl
    //   381: dup
    //   382: aload 12
    //   384: iload 14
    //   386: invokevirtual 92	twitter4j/internal/org/json/JSONArray:getJSONObject	(I)Ltwitter4j/internal/org/json/JSONObject;
    //   389: invokespecial 191	twitter4j/internal/json/HashtagEntityJSONImpl:<init>	(Ltwitter4j/internal/org/json/JSONObject;)V
    //   392: aastore
    //   393: iinc 14 1
    //   396: goto -31 -> 365
    //   399: aload 8
    //   401: ldc 199
    //   403: invokevirtual 160	twitter4j/internal/org/json/JSONObject:isNull	(Ljava/lang/String;)Z
    //   406: ifne +65 -> 471
    //   409: aload 8
    //   411: ldc 199
    //   413: invokevirtual 166	twitter4j/internal/org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Ltwitter4j/internal/org/json/JSONArray;
    //   416: astore 9
    //   418: aload 9
    //   420: invokevirtual 83	twitter4j/internal/org/json/JSONArray:length	()I
    //   423: istore 10
    //   425: aload_0
    //   426: iload 10
    //   428: anewarray 201	twitter4j/MediaEntity
    //   431: putfield 203	twitter4j/internal/json/DirectMessageJSONImpl:mediaEntities	[Ltwitter4j/MediaEntity;
    //   434: iconst_0
    //   435: istore 11
    //   437: iload 11
    //   439: iload 10
    //   441: if_icmpge +30 -> 471
    //   444: aload_0
    //   445: getfield 203	twitter4j/internal/json/DirectMessageJSONImpl:mediaEntities	[Ltwitter4j/MediaEntity;
    //   448: iload 11
    //   450: new 205	twitter4j/internal/json/MediaEntityJSONImpl
    //   453: dup
    //   454: aload 9
    //   456: iload 11
    //   458: invokevirtual 92	twitter4j/internal/org/json/JSONArray:getJSONObject	(I)Ltwitter4j/internal/org/json/JSONObject;
    //   461: invokespecial 206	twitter4j/internal/json/MediaEntityJSONImpl:<init>	(Ltwitter4j/internal/org/json/JSONObject;)V
    //   464: aastore
    //   465: iinc 11 1
    //   468: goto -31 -> 437
    //   471: aload_0
    //   472: getfield 170	twitter4j/internal/json/DirectMessageJSONImpl:userMentionEntities	[Ltwitter4j/UserMentionEntity;
    //   475: ifnonnull +119 -> 594
    //   478: iconst_0
    //   479: anewarray 168	twitter4j/UserMentionEntity
    //   482: astore_3
    //   483: aload_0
    //   484: aload_3
    //   485: putfield 170	twitter4j/internal/json/DirectMessageJSONImpl:userMentionEntities	[Ltwitter4j/UserMentionEntity;
    //   488: aload_0
    //   489: getfield 179	twitter4j/internal/json/DirectMessageJSONImpl:urlEntities	[Ltwitter4j/URLEntity;
    //   492: ifnonnull +110 -> 602
    //   495: iconst_0
    //   496: anewarray 177	twitter4j/URLEntity
    //   499: astore 4
    //   501: aload_0
    //   502: aload 4
    //   504: putfield 179	twitter4j/internal/json/DirectMessageJSONImpl:urlEntities	[Ltwitter4j/URLEntity;
    //   507: aload_0
    //   508: getfield 188	twitter4j/internal/json/DirectMessageJSONImpl:hashtagEntities	[Ltwitter4j/HashtagEntity;
    //   511: ifnonnull +100 -> 611
    //   514: iconst_0
    //   515: anewarray 186	twitter4j/HashtagEntity
    //   518: astore 5
    //   520: aload_0
    //   521: aload 5
    //   523: putfield 188	twitter4j/internal/json/DirectMessageJSONImpl:hashtagEntities	[Ltwitter4j/HashtagEntity;
    //   526: aload_0
    //   527: getfield 197	twitter4j/internal/json/DirectMessageJSONImpl:symbolEntities	[Ltwitter4j/SymbolEntity;
    //   530: ifnonnull +90 -> 620
    //   533: iconst_0
    //   534: anewarray 195	twitter4j/SymbolEntity
    //   537: astore 6
    //   539: aload_0
    //   540: aload 6
    //   542: putfield 197	twitter4j/internal/json/DirectMessageJSONImpl:symbolEntities	[Ltwitter4j/SymbolEntity;
    //   545: aload_0
    //   546: getfield 203	twitter4j/internal/json/DirectMessageJSONImpl:mediaEntities	[Ltwitter4j/MediaEntity;
    //   549: ifnonnull +80 -> 629
    //   552: iconst_0
    //   553: anewarray 201	twitter4j/MediaEntity
    //   556: astore 7
    //   558: aload_0
    //   559: aload 7
    //   561: putfield 203	twitter4j/internal/json/DirectMessageJSONImpl:mediaEntities	[Ltwitter4j/MediaEntity;
    //   564: aload_0
    //   565: aload_1
    //   566: ldc 207
    //   568: invokevirtual 211	twitter4j/internal/org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   571: aload_0
    //   572: getfield 170	twitter4j/internal/json/DirectMessageJSONImpl:userMentionEntities	[Ltwitter4j/UserMentionEntity;
    //   575: aload_0
    //   576: getfield 179	twitter4j/internal/json/DirectMessageJSONImpl:urlEntities	[Ltwitter4j/URLEntity;
    //   579: aload_0
    //   580: getfield 188	twitter4j/internal/json/DirectMessageJSONImpl:hashtagEntities	[Ltwitter4j/HashtagEntity;
    //   583: aload_0
    //   584: getfield 203	twitter4j/internal/json/DirectMessageJSONImpl:mediaEntities	[Ltwitter4j/MediaEntity;
    //   587: invokestatic 217	twitter4j/internal/json/HTMLEntity:unescapeAndSlideEntityIncdices	(Ljava/lang/String;[Ltwitter4j/UserMentionEntity;[Ltwitter4j/URLEntity;[Ltwitter4j/HashtagEntity;[Ltwitter4j/MediaEntity;)Ljava/lang/String;
    //   590: putfield 219	twitter4j/internal/json/DirectMessageJSONImpl:text	Ljava/lang/String;
    //   593: return
    //   594: aload_0
    //   595: getfield 170	twitter4j/internal/json/DirectMessageJSONImpl:userMentionEntities	[Ltwitter4j/UserMentionEntity;
    //   598: astore_3
    //   599: goto -116 -> 483
    //   602: aload_0
    //   603: getfield 179	twitter4j/internal/json/DirectMessageJSONImpl:urlEntities	[Ltwitter4j/URLEntity;
    //   606: astore 4
    //   608: goto -107 -> 501
    //   611: aload_0
    //   612: getfield 188	twitter4j/internal/json/DirectMessageJSONImpl:hashtagEntities	[Ltwitter4j/HashtagEntity;
    //   615: astore 5
    //   617: goto -97 -> 520
    //   620: aload_0
    //   621: getfield 197	twitter4j/internal/json/DirectMessageJSONImpl:symbolEntities	[Ltwitter4j/SymbolEntity;
    //   624: astore 6
    //   626: goto -87 -> 539
    //   629: aload_0
    //   630: getfield 203	twitter4j/internal/json/DirectMessageJSONImpl:mediaEntities	[Ltwitter4j/MediaEntity;
    //   633: astore 7
    //   635: goto -77 -> 558
    //   638: astore_2
    //   639: new 38	twitter4j/TwitterException
    //   642: dup
    //   643: aload_2
    //   644: invokespecial 103	twitter4j/TwitterException:<init>	(Ljava/lang/Exception;)V
    //   647: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	648	0	this	DirectMessageJSONImpl
    //   0	648	1	paramJSONObject	JSONObject
    //   638	6	2	localJSONException	JSONException
    //   482	117	3	arrayOfUserMentionEntity	UserMentionEntity[]
    //   499	108	4	arrayOfURLEntity	URLEntity[]
    //   518	98	5	arrayOfHashtagEntity	HashtagEntity[]
    //   537	88	6	arrayOfSymbolEntity	SymbolEntity[]
    //   556	78	7	arrayOfMediaEntity	MediaEntity[]
    //   109	301	8	localJSONObject	JSONObject
    //   416	39	9	localJSONArray1	JSONArray
    //   423	19	10	i	int
    //   435	31	11	j	int
    //   344	39	12	localJSONArray2	JSONArray
    //   351	19	13	k	int
    //   363	31	14	m	int
    //   272	39	15	localJSONArray3	JSONArray
    //   279	19	16	n	int
    //   291	31	17	i1	int
    //   200	39	18	localJSONArray4	JSONArray
    //   207	19	19	i2	int
    //   219	31	20	i3	int
    //   128	39	21	localJSONArray5	JSONArray
    //   135	19	22	i4	int
    //   147	31	23	i5	int
    // Exception table:
    //   from	to	target	type
    //   60	635	638	twitter4j/internal/org/json/JSONException
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if (paramObject == null) {}
    for (bool = false;; bool = false) {
      do
      {
        return bool;
      } while ((this == paramObject) || (((paramObject instanceof DirectMessage)) && (((DirectMessage)paramObject).getId() == this.id)));
    }
  }
  
  public Date getCreatedAt()
  {
    return this.createdAt;
  }
  
  public HashtagEntity[] getHashtagEntities()
  {
    return this.hashtagEntities;
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public MediaEntity[] getMediaEntities()
  {
    return this.mediaEntities;
  }
  
  public User getRecipient()
  {
    return this.recipient;
  }
  
  public long getRecipientId()
  {
    return this.recipientId;
  }
  
  public String getRecipientScreenName()
  {
    return this.recipientScreenName;
  }
  
  public User getSender()
  {
    return this.sender;
  }
  
  public long getSenderId()
  {
    return this.senderId;
  }
  
  public String getSenderScreenName()
  {
    return this.senderScreenName;
  }
  
  public SymbolEntity[] getSymbolEntities()
  {
    return this.symbolEntities;
  }
  
  public String getText()
  {
    return this.text;
  }
  
  public URLEntity[] getURLEntities()
  {
    return this.urlEntities;
  }
  
  public UserMentionEntity[] getUserMentionEntities()
  {
    return this.userMentionEntities;
  }
  
  public int hashCode()
  {
    return (int)this.id;
  }
  
  public String toString()
  {
    Object localObject1 = null;
    StringBuilder localStringBuilder1 = new StringBuilder().append("DirectMessageJSONImpl{id=").append(this.id).append(", text='").append(this.text).append('\'').append(", sender_id=").append(this.senderId).append(", recipient_id=").append(this.recipientId).append(", created_at=").append(this.createdAt).append(", userMentionEntities=");
    Object localObject2;
    Object localObject3;
    label117:
    Object localObject4;
    label142:
    Object localObject5;
    label229:
    Object localObject6;
    label254:
    StringBuilder localStringBuilder6;
    if (this.userMentionEntities == null)
    {
      localObject2 = null;
      StringBuilder localStringBuilder2 = localStringBuilder1.append(localObject2).append(", urlEntities=");
      if (this.urlEntities != null) {
        break label302;
      }
      localObject3 = null;
      StringBuilder localStringBuilder3 = localStringBuilder2.append(localObject3).append(", hashtagEntities=");
      if (this.hashtagEntities != null) {
        break label314;
      }
      localObject4 = null;
      StringBuilder localStringBuilder4 = localStringBuilder3.append(localObject4).append(", sender_screen_name='").append(this.senderScreenName).append('\'').append(", recipient_screen_name='").append(this.recipientScreenName).append('\'').append(", sender=").append(this.sender).append(", recipient=").append(this.recipient).append(", userMentionEntities=");
      if (this.userMentionEntities != null) {
        break label326;
      }
      localObject5 = null;
      StringBuilder localStringBuilder5 = localStringBuilder4.append(localObject5).append(", urlEntities=");
      if (this.urlEntities != null) {
        break label338;
      }
      localObject6 = null;
      localStringBuilder6 = localStringBuilder5.append(localObject6).append(", hashtagEntities=");
      if (this.hashtagEntities != null) {
        break label350;
      }
    }
    for (;;)
    {
      return localObject1 + '}';
      localObject2 = Arrays.asList(this.userMentionEntities);
      break;
      label302:
      localObject3 = Arrays.asList(this.urlEntities);
      break label117;
      label314:
      localObject4 = Arrays.asList(this.hashtagEntities);
      break label142;
      label326:
      localObject5 = Arrays.asList(this.userMentionEntities);
      break label229;
      label338:
      localObject6 = Arrays.asList(this.urlEntities);
      break label254;
      label350:
      localObject1 = Arrays.asList(this.hashtagEntities);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.internal.json.DirectMessageJSONImpl
 * JD-Core Version:    0.7.0.1
 */