package twitter4j.json;

import twitter4j.internal.logging.Logger;
import twitter4j.internal.org.json.JSONException;
import twitter4j.internal.org.json.JSONObject;

public final class JSONObjectType
{
  private static final Logger logger = Logger.getLogger(JSONObjectType.class);
  
  public static Type determine(JSONObject paramJSONObject)
  {
    Type localType;
    if (!paramJSONObject.isNull("sender")) {
      localType = Type.SENDER;
    }
    for (;;)
    {
      return localType;
      if (!paramJSONObject.isNull("text"))
      {
        localType = Type.STATUS;
        continue;
      }
      if (!paramJSONObject.isNull("direct_message"))
      {
        localType = Type.DIRECT_MESSAGE;
        continue;
      }
      if (!paramJSONObject.isNull("delete"))
      {
        localType = Type.DELETE;
        continue;
      }
      if (!paramJSONObject.isNull("limit"))
      {
        localType = Type.LIMIT;
        continue;
      }
      if (!paramJSONObject.isNull("warning"))
      {
        localType = Type.STALL_WARNING;
        continue;
      }
      if (!paramJSONObject.isNull("scrub_geo"))
      {
        localType = Type.SCRUB_GEO;
        continue;
      }
      if (!paramJSONObject.isNull("friends"))
      {
        localType = Type.FRIENDS;
        continue;
      }
      if (!paramJSONObject.isNull("event")) {
        try
        {
          String str = paramJSONObject.getString("event");
          if ("favorite".equals(str))
          {
            localType = Type.FAVORITE;
            continue;
          }
          if ("unfavorite".equals(str))
          {
            localType = Type.UNFAVORITE;
            continue;
          }
          if ("follow".equals(str))
          {
            localType = Type.FOLLOW;
            continue;
          }
          if ("unfollow".equals(str))
          {
            localType = Type.UNFOLLOW;
            continue;
          }
          if (str.startsWith("list"))
          {
            if ("list_member_added".equals(str))
            {
              localType = Type.USER_LIST_MEMBER_ADDED;
              continue;
            }
            if ("list_member_removed".equals(str))
            {
              localType = Type.USER_LIST_MEMBER_DELETED;
              continue;
            }
            if ("list_user_subscribed".equals(str))
            {
              localType = Type.USER_LIST_SUBSCRIBED;
              continue;
            }
            if ("list_user_unsubscribed".equals(str))
            {
              localType = Type.USER_LIST_UNSUBSCRIBED;
              continue;
            }
            if ("list_created".equals(str))
            {
              localType = Type.USER_LIST_CREATED;
              continue;
            }
            if ("list_updated".equals(str))
            {
              localType = Type.USER_LIST_UPDATED;
              continue;
            }
            if ("list_destroyed".equals(str)) {
              localType = Type.USER_LIST_DESTROYED;
            }
          }
          else
          {
            if ("user_update".equals(str))
            {
              localType = Type.USER_UPDATE;
              continue;
            }
            if ("block".equals(str))
            {
              localType = Type.BLOCK;
              continue;
            }
            if ("unblock".equals(str)) {
              localType = Type.UNBLOCK;
            }
          }
        }
        catch (JSONException localJSONException1) {}
      }
      try
      {
        logger.warn("Failed to get event element: ", paramJSONObject.toString(2));
        label406:
        do
        {
          localType = Type.UNKNOWN;
          break;
        } while (paramJSONObject.isNull("disconnect"));
        localType = Type.DISCONNECTION;
      }
      catch (JSONException localJSONException2)
      {
        break label406;
      }
    }
  }
  
  public static enum Type
  {
    static
    {
      DIRECT_MESSAGE = new Type("DIRECT_MESSAGE", 2);
      DELETE = new Type("DELETE", 3);
      LIMIT = new Type("LIMIT", 4);
      STALL_WARNING = new Type("STALL_WARNING", 5);
      SCRUB_GEO = new Type("SCRUB_GEO", 6);
      FRIENDS = new Type("FRIENDS", 7);
      FAVORITE = new Type("FAVORITE", 8);
      UNFAVORITE = new Type("UNFAVORITE", 9);
      FOLLOW = new Type("FOLLOW", 10);
      UNFOLLOW = new Type("UNFOLLOW", 11);
      USER_LIST_MEMBER_ADDED = new Type("USER_LIST_MEMBER_ADDED", 12);
      USER_LIST_MEMBER_DELETED = new Type("USER_LIST_MEMBER_DELETED", 13);
      USER_LIST_SUBSCRIBED = new Type("USER_LIST_SUBSCRIBED", 14);
      USER_LIST_UNSUBSCRIBED = new Type("USER_LIST_UNSUBSCRIBED", 15);
      USER_LIST_CREATED = new Type("USER_LIST_CREATED", 16);
      USER_LIST_UPDATED = new Type("USER_LIST_UPDATED", 17);
      USER_LIST_DESTROYED = new Type("USER_LIST_DESTROYED", 18);
      USER_UPDATE = new Type("USER_UPDATE", 19);
      BLOCK = new Type("BLOCK", 20);
      UNBLOCK = new Type("UNBLOCK", 21);
      DISCONNECTION = new Type("DISCONNECTION", 22);
      UNKNOWN = new Type("UNKNOWN", 23);
      Type[] arrayOfType = new Type[24];
      arrayOfType[0] = SENDER;
      arrayOfType[1] = STATUS;
      arrayOfType[2] = DIRECT_MESSAGE;
      arrayOfType[3] = DELETE;
      arrayOfType[4] = LIMIT;
      arrayOfType[5] = STALL_WARNING;
      arrayOfType[6] = SCRUB_GEO;
      arrayOfType[7] = FRIENDS;
      arrayOfType[8] = FAVORITE;
      arrayOfType[9] = UNFAVORITE;
      arrayOfType[10] = FOLLOW;
      arrayOfType[11] = UNFOLLOW;
      arrayOfType[12] = USER_LIST_MEMBER_ADDED;
      arrayOfType[13] = USER_LIST_MEMBER_DELETED;
      arrayOfType[14] = USER_LIST_SUBSCRIBED;
      arrayOfType[15] = USER_LIST_UNSUBSCRIBED;
      arrayOfType[16] = USER_LIST_CREATED;
      arrayOfType[17] = USER_LIST_UPDATED;
      arrayOfType[18] = USER_LIST_DESTROYED;
      arrayOfType[19] = USER_UPDATE;
      arrayOfType[20] = BLOCK;
      arrayOfType[21] = UNBLOCK;
      arrayOfType[22] = DISCONNECTION;
      arrayOfType[23] = UNKNOWN;
      $VALUES = arrayOfType;
    }
    
    private Type() {}
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     twitter4j.json.JSONObjectType
 * JD-Core Version:    0.7.0.1
 */