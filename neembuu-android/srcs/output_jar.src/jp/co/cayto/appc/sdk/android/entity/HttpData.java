package jp.co.cayto.appc.sdk.android.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class HttpData
{
  public static final String ACTION = "action";
  public static final String COMMAND = "command";
  public static final String DELETE_PACKAGE = "delete_package";
  public static final String DIALOG = "dialog";
  public static final String DIALOG_CACHE_SECOND = "dialog_cache_second";
  public static final String DIALOG_LINK = "dialog_link";
  public static final String DIALOG_LINK_BY_FINISH = "dialog_link_by_finish";
  public static final String DIALOG_LINK_TARGET = "dialog_link_target";
  public static final String DIALOG_MSG = "dialog_msg";
  public static final String FROM_APP = "from_app";
  public static final String GID = "gid";
  public static final String INSTALL_PACKAGE = "install_package";
  public static final String INTENT_NAME = "intent_name";
  public static final String LIST_MODE = "list_mode";
  public static final String LIST_TITLE = "list_title";
  public static final String RANKING_DESCRIPTION = "ranking_description";
  public static final String RANKING_NAME = "ranking_name";
  public static final String REINSTALL_FLAG = "reinstall_flag";
  public static final String SCHEMA = "schema";
  public static final String STATUS = "status";
  public static final String STATUS_MSG = "status_msg";
  public static final String TARGET_PACKAGE = "target_package";
  public static final String TYPE = "type";
  public static final String UPDATE_PACKAGE = "update_package";
  private ArrayList<HttpApp> apps;
  private HashMap<String, String> dataMap;
  
  public HttpData()
  {
    this.dataMap = new HashMap();
    this.apps = new ArrayList();
  }
  
  public HttpData(HashMap<String, String> paramHashMap)
  {
    this.dataMap = paramHashMap;
  }
  
  public void addApp(HttpApp paramHttpApp)
  {
    this.apps.add(paramHttpApp);
  }
  
  public ArrayList<HttpApp> getApps()
  {
    return this.apps;
  }
  
  public ArrayList<HashMap<String, String>> getAppsList()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.apps.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return localArrayList;
      }
      localArrayList.add(((HttpApp)localIterator.next()).getMap());
    }
  }
  
  public String getValue(String paramString)
  {
    this.dataMap.containsKey(paramString);
    if (this.dataMap.containsKey(paramString)) {}
    for (String str = (String)this.dataMap.get(paramString);; str = "") {
      return str;
    }
  }
  
  public void setValue(String paramString1, String paramString2)
  {
    this.dataMap.containsKey(paramString1);
    HashMap localHashMap = this.dataMap;
    if (paramString2 == null) {
      paramString2 = "";
    }
    localHashMap.put(paramString1, paramString2);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.entity.HttpData
 * JD-Core Version:    0.7.0.1
 */