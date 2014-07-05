package jp.co.cayto.appc.sdk.android.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import jp.co.cayto.appc.sdk.android.background.IDService;
import jp.co.cayto.appc.sdk.android.entity.HttpApp;
import jp.co.cayto.appc.sdk.android.entity.HttpData;
import jp.co.cayto.appc.sdk.android.resources.Texts;
import jp.co.cayto.appc.sdk.android.resources.Texts.ITexts;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class AppController
{
  private boolean initialFlag = false;
  
  private ArrayList<NameValuePair> addPermissionHttpParams(Context paramContext, ArrayList<NameValuePair> paramArrayList)
  {
    Configuration localConfiguration = paramContext.getResources().getConfiguration();
    String str1 = String.valueOf(localConfiguration.mcc);
    String str2 = String.valueOf(localConfiguration.mnc);
    paramArrayList.add(new BasicNameValuePair("sid", AppPreference.getGid(paramContext)));
    if (str1 != null)
    {
      paramArrayList.add(new BasicNameValuePair("mcc", URLEncoder.encode(str1)));
      if (str2 == null) {
        break label252;
      }
      label73:
      paramArrayList.add(new BasicNameValuePair("mnc", URLEncoder.encode(str2)));
      if (Locale.getDefault().toString() == null) {
        break label259;
      }
    }
    label259:
    for (String str3 = Locale.getDefault().toString();; str3 = "")
    {
      paramArrayList.add(new BasicNameValuePair("locale", URLEncoder.encode(str3)));
      paramArrayList.add(new BasicNameValuePair("model", URLEncoder.encode(Build.MODEL)));
      paramArrayList.add(new BasicNameValuePair("fwv", URLEncoder.encode(Build.VERSION.RELEASE)));
      paramArrayList.add(new BasicNameValuePair("apl", URLEncoder.encode(Build.VERSION.SDK)));
      paramArrayList.add(new BasicNameValuePair("sdkv", "appc2.4"));
      paramArrayList.add(new BasicNameValuePair("mvn", AppPreference.getAppVersion(paramContext)));
      paramArrayList.add(new BasicNameValuePair("mvc", String.valueOf(AppPreference.getAppVersionCode(paramContext))));
      return paramArrayList;
      str1 = "";
      break;
      label252:
      str2 = "";
      break label73;
    }
  }
  
  private HttpData createGIDByServer(Context paramContext)
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = addPermissionHttpParams(paramContext, createHttpParams(paramContext, "get_gid"));
    try
    {
      localHttpData = parseXml(AppHttp.doPost("https://api.app-c.net/AppC/", localArrayList2, localArrayList1, true));
      if ((localHttpData.getValue("status").equals("403")) && (localHttpData.getValue("command").equals("1"))) {
        forceOptout(paramContext);
      }
      return localHttpData;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        HttpData localHttpData = new HttpData();
      }
    }
  }
  
  private ArrayList<NameValuePair> createHttpParams(Context paramContext, String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    String str = AppPreference.getMediaKey(paramContext);
    if (TextUtils.isEmpty(str)) {
      Log.e("appC", new Texts(paramContext).get.トースト_appC_media_key_なし());
    }
    localArrayList.add(new BasicNameValuePair("mk", URLEncoder.encode(str)));
    localArrayList.add(new BasicNameValuePair("mpkg", paramContext.getPackageName()));
    localArrayList.add(new BasicNameValuePair("act", paramString));
    localArrayList.add(new BasicNameValuePair("service", "appC"));
    return localArrayList;
  }
  
  public static AppController createIncetance(Context paramContext)
  {
    return createIncetance(paramContext, null);
  }
  
  /**
   * @deprecated
   */
  public static AppController createIncetance(Context paramContext, Intent paramIntent)
  {
    try
    {
      AppController localAppController = new AppController();
      localAppController.configure(paramContext, paramIntent);
      return localAppController;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private void forceOptout(Context paramContext)
  {
    AppPreference.setPermissionOff(paramContext);
    paramContext.sendOrderedBroadcast(new Intent("jp.co.cayto.appc.sdk.android.remote.command.gid.remove"), null, new BroadcastReceiver()
    {
      public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent) {}
    }, null, -1, "r3", new Bundle());
  }
  
  private HttpData parseXml(String paramString)
    throws IOException, XmlPullParserException
  {
    if (TextUtils.isEmpty(paramString))
    {
      localHttpData = new HttpData();
      return localHttpData;
    }
    String str1 = null;
    HttpData localHttpData = new HttpData();
    int i = 0;
    for (;;)
    {
      XmlPullParser localXmlPullParser;
      try
      {
        localXmlPullParser = Xml.newPullParser();
        localXmlPullParser.setInput(new StringReader(paramString));
        j = localXmlPullParser.getEventType();
        localObject1 = null;
      }
      catch (Exception localException1) {}
      int k = localXmlPullParser.next();
      int j = k;
      Object localObject1 = localObject2;
      break label232;
      try
      {
        str1 = localXmlPullParser.getName();
        if (!str1.equals("app")) {
          break label272;
        }
        i = 1;
        localObject2 = new HttpApp();
      }
      catch (Exception localException2) {}
      String str2 = localXmlPullParser.getText();
      if (str2 != null)
      {
        if (str2.trim().equals(""))
        {
          localObject2 = localObject1;
          continue;
        }
        if (i == 0)
        {
          localHttpData.setValue(str1, str2);
          localObject2 = localObject1;
          continue;
        }
        localObject1.setValue(str1, str2);
        localObject2 = localObject1;
        continue;
        str1 = localXmlPullParser.getName();
        if (str1.equals("app"))
        {
          i = 0;
          localHttpData.addApp(localObject1);
          localObject2 = null;
          continue;
          break;
          break;
          label232:
          if (j == 1) {
            break;
          }
          switch (j)
          {
          }
        }
      }
      label272:
      Object localObject2 = localObject1;
    }
  }
  
  private void sendLogThread(final Context paramContext, final String paramString)
  {
    HandlerThread localHandlerThread = new HandlerThread("BGThread");
    localHandlerThread.start();
    new Handler(localHandlerThread.getLooper()).post(new Runnable()
    {
      public void run()
      {
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = AppController.this.createHttpParams(paramContext, paramString);
        if (AppPreference.isPermission(paramContext)) {
          localArrayList2 = AppController.this.addPermissionHttpParams(paramContext, localArrayList2);
        }
        try
        {
          String str = AppHttp.doPost("https://api.app-c.net/AppC/", localArrayList2, localArrayList1, true);
          HttpData localHttpData = AppController.this.parseXml(str);
          if ((localHttpData.getValue("status").equals("403")) && (localHttpData.getValue("command").equals("1"))) {
            AppController.this.forceOptout(paramContext);
          }
          label109:
          return;
        }
        catch (Exception localException)
        {
          break label109;
        }
      }
    });
  }
  
  private void startIDService(Context paramContext)
  {
    Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningServices(2147483647).iterator();
    if (!localIterator.hasNext()) {
      if (TextUtils.isEmpty(AppPreference.getGid(paramContext)))
      {
        Intent localIntent = new Intent(paramContext, IDService.class);
        localIntent.setAction("start");
        paramContext.startService(localIntent);
      }
    }
    for (;;)
    {
      return;
      ActivityManager.RunningServiceInfo localRunningServiceInfo = (ActivityManager.RunningServiceInfo)localIterator.next();
      if (!IDService.class.getName().equals(localRunningServiceInfo.service.getClassName())) {
        break;
      }
    }
  }
  
  public void configure(Context paramContext)
  {
    configure(paramContext, null);
  }
  
  /**
   * @deprecated
   */
  public void configure(Context paramContext, Intent paramIntent)
  {
    for (;;)
    {
      try
      {
        String str = AppPreference.getPermission(paramContext);
        if (TextUtils.isEmpty(str))
        {
          startIDService(paramContext);
          this.initialFlag = true;
          return;
        }
        if (!str.equals("0")) {
          if (str.equals("1"))
          {
            if ((TextUtils.isEmpty(AppPreference.getGid(paramContext))) && ((paramIntent == null) || (paramIntent.getAction() == null) || (!paramIntent.getAction().contains("jp.co.cayto.appc.sdk.android.remote.command.gid")))) {
              startIDService(paramContext);
            }
          }
          else {
            this.initialFlag = false;
          }
        }
      }
      finally {}
    }
  }
  
  public HttpData getCPIList(Context paramContext, HashMap<String, String> paramHashMap)
  {
    configure(paramContext);
    String str1 = (String)paramHashMap.get("m");
    String str2 = (String)paramHashMap.get("cache");
    String str3 = (String)paramHashMap.get("linktag");
    Object localObject1 = "";
    AppDB localAppDB = new AppDB(paramContext);
    if (TextUtils.isEmpty(str2)) {}
    for (;;)
    {
      HttpData localHttpData;
      try
      {
        String str6 = localAppDB.findCPIList(str1);
        localObject1 = str6;
      }
      catch (Exception localException4)
      {
        Object localObject2;
        String str5;
        continue;
        continue;
      }
      try
      {
        if ((!TextUtils.isEmpty((CharSequence)localObject1)) && (((String)localObject1).contains("<apps>")))
        {
          localObject2 = localObject1;
          localHttpData = parseXml((String)localObject2);
          if ((localHttpData.getValue("status").equals("403")) && (localHttpData.getValue("command").equals("1"))) {
            forceOptout(paramContext);
          }
        }
        else
        {
          ArrayList localArrayList1 = new ArrayList();
          String str4;
          if (str1 != null)
          {
            str4 = str1;
            localArrayList1.add(new BasicNameValuePair("m", URLEncoder.encode(str4)));
            if (str2 == null) {
              break label373;
            }
            localArrayList1.add(new BasicNameValuePair("cache", URLEncoder.encode(str2)));
            if (str3 != null)
            {
              localArrayList1.add(new BasicNameValuePair("linktag", URLEncoder.encode(str3)));
              ArrayList localArrayList2 = createHttpParams(paramContext, "get_cpi_list");
              if ((isInitialized()) && (AppPreference.isPermission(paramContext))) {
                localArrayList2 = addPermissionHttpParams(paramContext, localArrayList2);
              }
              localObject2 = AppHttp.doPost("https://api.app-c.net/v1.0/ja/list.xml", localArrayList2, localArrayList1, true);
              if (TextUtils.isEmpty((CharSequence)localObject2)) {
                continue;
              }
              boolean bool = ((String)localObject2).contains("<apps>");
              if (!bool) {
                continue;
              }
              try
              {
                localAppDB.createCPIList(str1, (String)localObject2);
              }
              catch (Exception localException3) {}
            }
          }
          else
          {
            str4 = "";
            continue;
          }
          str3 = "";
          continue;
        }
      }
      catch (Exception localException1)
      {
        try
        {
          str5 = localAppDB.findCPIListOneDay(str1);
          localObject2 = str5;
        }
        catch (Exception localException2) {}
        localException1 = localException1;
        localHttpData = new HttpData();
      }
      return localHttpData;
      label373:
      str2 = "";
    }
  }
  
  public boolean isInitialized()
  {
    return this.initialFlag;
  }
  
  public void registCPI(final Context paramContext, final HashMap<String, String> paramHashMap, final String paramString)
  {
    configure(paramContext);
    if ((isInitialized()) && (AppPreference.isPermission(paramContext)))
    {
      HandlerThread localHandlerThread = new HandlerThread("BGThread");
      localHandlerThread.start();
      new Handler(localHandlerThread.getLooper()).post(new Runnable()
      {
        public void run()
        {
          String str1 = (String)paramHashMap.get("target_package");
          String str2 = (String)paramHashMap.get("ad_apps_id");
          int i;
          try
          {
            paramContext.getPackageManager().getApplicationInfo(str1, 128);
            i = 1;
          }
          catch (PackageManager.NameNotFoundException localNameNotFoundException)
          {
            for (;;)
            {
              i = 0;
            }
            localArrayList1 = new ArrayList();
            if (str2 == null) {
              break label314;
            }
          }
          if (i != 0) {}
          for (;;)
          {
            return;
            ArrayList localArrayList1;
            label70:
            localArrayList1.add(new BasicNameValuePair("ad_apps_id", URLEncoder.encode(str2)));
            String str3;
            label96:
            String str4;
            label129:
            String str5;
            ArrayList localArrayList3;
            if (str1 != null)
            {
              str3 = str1;
              localArrayList1.add(new BasicNameValuePair("target_package", URLEncoder.encode(str3)));
              if (paramString == null) {
                break label327;
              }
              str4 = paramString;
              localArrayList1.add(new BasicNameValuePair("linktag", URLEncoder.encode(str4)));
              if (i == 0) {
                break label334;
              }
              str5 = "1";
              localArrayList1.add(new BasicNameValuePair("target_package_exist", URLEncoder.encode(str5)));
              ArrayList localArrayList2 = AppController.this.createHttpParams(paramContext, "regist_cpi");
              localArrayList3 = AppController.this.addPermissionHttpParams(paramContext, localArrayList2);
            }
            try
            {
              AppDB localAppDB = new AppDB(paramContext);
              localAppDB.createRegistCPI(str1);
              localAppDB.removeRegistCPI();
              try
              {
                String str6 = AppHttp.doPost("https://api.app-c.net/AppC/", localArrayList3, localArrayList1, true);
                HttpData localHttpData = AppController.this.parseXml(str6);
                if ((!localHttpData.getValue("status").equals("403")) || (!localHttpData.getValue("command").equals("1"))) {
                  continue;
                }
                AppController.this.forceOptout(paramContext);
              }
              catch (Exception localException2)
              {
                Log.e("appC", "registCPI", localException2);
              }
              continue;
              label314:
              str2 = "0";
              break label70;
              str3 = "";
              break label96;
              label327:
              str4 = "";
              break label129;
              label334:
              str5 = "0";
            }
            catch (Exception localException1)
            {
              for (;;)
              {
                Log.e("appC", "registCPI", localException1);
              }
            }
          }
        }
      });
    }
  }
  
  public void registCPIMoveMarket(Context paramContext, HashMap<String, String> paramHashMap, String paramString)
  {
    registCPI(paramContext, paramHashMap, paramString);
    String str1 = (String)paramHashMap.get("target_package");
    String str2 = (String)paramHashMap.get("redirect_url");
    if (!TextUtils.isEmpty(str2)) {}
    for (Uri localUri = Uri.parse(str2);; localUri = Uri.parse("market://details?id=" + str1 + "&referrer=appC_" + paramContext.getPackageName()))
    {
      Intent localIntent = new Intent("android.intent.action.VIEW", localUri);
      ((Activity)paramContext).startActivity(localIntent);
      return;
    }
  }
  
  public void salvageGID(Context paramContext)
  {
    synchronized (Global.lock)
    {
      if (TextUtils.isEmpty(AppPreference.getGid(paramContext))) {
        paramContext.sendOrderedBroadcast(new Intent("jp.co.cayto.appc.sdk.android.remote.command.gid.search"), null, new BroadcastReceiver()
        {
          public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
          {
            int i = 0;
            int j = 0;
            Object localObject = "";
            ArrayList localArrayList = getResultExtras(false).getStringArrayList("gid_search_result");
            if (localArrayList == null) {
              localArrayList = new ArrayList();
            }
            Iterator localIterator = localArrayList.iterator();
            if (!localIterator.hasNext())
            {
              label52:
              if (i == 0) {
                break label118;
              }
              AppPreference.setGid(paramAnonymousContext, (String)localObject);
              AppPreference.setPermissionOn(paramAnonymousContext);
            }
            for (;;)
            {
              return;
              String str = (String)localIterator.next();
              if (!TextUtils.isEmpty(str))
              {
                i = 1;
                localObject = str;
                break label52;
              }
              if ((str == null) || (AppPreference.getPermission(paramAnonymousContext) != null)) {
                break;
              }
              j = 1;
              localObject = "";
              break label52;
              label118:
              if (j != 0)
              {
                AppPreference.setGid(paramAnonymousContext, (String)localObject);
                AppPreference.setPermissionOff(paramAnonymousContext);
              }
              else
              {
                new AppController.CreateGIDTask(AppController.this, paramAnonymousContext, (String)localObject).execute(new String[0]);
              }
            }
          }
        }, null, -1, "r1", new Bundle());
      }
    }
  }
  
  public void sendCPI(final Context paramContext, final String paramString)
  {
    configure(paramContext);
    try
    {
      paramContext.getPackageManager().getApplicationInfo(paramString, 128);
      try
      {
        label17:
        AppDB localAppDB = new AppDB(paramContext);
        bool = localAppDB.isRegistCPI(paramString);
        localAppDB.removeRegistCPIByPkgName(paramString);
        if (!bool) {
          return;
        }
      }
      catch (Exception localException)
      {
        for (;;)
        {
          boolean bool = false;
          continue;
          HandlerThread localHandlerThread = new HandlerThread("BGThread");
          localHandlerThread.start();
          new Handler(localHandlerThread.getLooper()).post(new Runnable()
          {
            public void run()
            {
              ArrayList localArrayList = AppController.this.createHttpParams(paramContext, "logging_change");
              if (AppPreference.isPermission(paramContext)) {
                localArrayList = AppController.this.addPermissionHttpParams(paramContext, localArrayList);
              }
              localArrayList.add(new BasicNameValuePair("apps", paramString + "::PACKAGE_ADD;"));
              AppHttp.doPost("https://api.app-c.net/AppC/", localArrayList, true);
            }
          });
        }
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label17;
    }
  }
  
  public void sendLogOff(Context paramContext)
  {
    sendLogThread(paramContext, "send_logff");
  }
  
  private class CreateGIDTask
    extends AsyncTask<String, Void, HttpData>
  {
    private Context context;
    private boolean emptyFlag;
    private String gid;
    
    public CreateGIDTask(Context paramContext, String paramString)
    {
      this.context = paramContext;
      this.gid = paramString;
      this.emptyFlag = TextUtils.isEmpty(paramString);
    }
    
    protected HttpData doInBackground(String... paramVarArgs)
    {
      return AppController.this.createGIDByServer(this.context);
    }
    
    protected void onPostExecute(HttpData paramHttpData)
    {
      this.gid = paramHttpData.getValue("gid");
      AppPreference.setGid(this.context, this.gid);
      AppPreference.setPermissionOn(this.context);
      Intent localIntent = new Intent("jp.co.cayto.appc.sdk.android.remote.command.gid.set");
      localIntent.putExtra("package", this.context.getPackageName());
      localIntent.putExtra("gid", this.gid);
      this.context.sendOrderedBroadcast(localIntent, null, new BroadcastReceiver()
      {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
        {
          if (AppController.CreateGIDTask.this.emptyFlag) {
            AppController.this.sendLogThread(paramAnonymousContext, "send_logon");
          }
          for (;;)
          {
            Intent localIntent = new Intent(paramAnonymousContext, IDService.class);
            localIntent.setAction("stop");
            paramAnonymousContext.stopService(localIntent);
            return;
            AppController.this.sendLogThread(paramAnonymousContext, "send_relogon");
          }
        }
      }, null, -1, "r2", new Bundle());
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.common.AppController
 * JD-Core Version:    0.7.0.1
 */