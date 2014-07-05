package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.util.Log;

public class NavUtils
{
  private static final NavUtilsImpl IMPL;
  public static final String PARENT_ACTIVITY = "android.support.PARENT_ACTIVITY";
  private static final String TAG = "NavUtils";
  
  static
  {
    if (Build.VERSION.SDK_INT >= 16) {}
    for (IMPL = new NavUtilsImplJB();; IMPL = new NavUtilsImplBase()) {
      return;
    }
  }
  
  public static Intent getParentActivityIntent(Activity paramActivity)
  {
    return IMPL.getParentActivityIntent(paramActivity);
  }
  
  public static Intent getParentActivityIntent(Context paramContext, ComponentName paramComponentName)
    throws PackageManager.NameNotFoundException
  {
    String str = getParentActivityName(paramContext, paramComponentName);
    if (str == null)
    {
      localIntent = null;
      return localIntent;
    }
    ComponentName localComponentName = new ComponentName(paramComponentName.getPackageName(), str);
    if (getParentActivityName(paramContext, localComponentName) == null) {}
    for (Intent localIntent = IntentCompat.makeMainActivity(localComponentName);; localIntent = new Intent().setComponent(localComponentName)) {
      break;
    }
  }
  
  public static Intent getParentActivityIntent(Context paramContext, Class<?> paramClass)
    throws PackageManager.NameNotFoundException
  {
    String str = getParentActivityName(paramContext, new ComponentName(paramContext, paramClass));
    if (str == null)
    {
      localIntent = null;
      return localIntent;
    }
    ComponentName localComponentName = new ComponentName(paramContext, str);
    if (getParentActivityName(paramContext, localComponentName) == null) {}
    for (Intent localIntent = IntentCompat.makeMainActivity(localComponentName);; localIntent = new Intent().setComponent(localComponentName)) {
      break;
    }
  }
  
  public static String getParentActivityName(Activity paramActivity)
  {
    try
    {
      String str = getParentActivityName(paramActivity, paramActivity.getComponentName());
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      throw new IllegalArgumentException(localNameNotFoundException);
    }
  }
  
  public static String getParentActivityName(Context paramContext, ComponentName paramComponentName)
    throws PackageManager.NameNotFoundException
  {
    ActivityInfo localActivityInfo = paramContext.getPackageManager().getActivityInfo(paramComponentName, 128);
    return IMPL.getParentActivityName(paramContext, localActivityInfo);
  }
  
  public static void navigateUpFromSameTask(Activity paramActivity)
  {
    Intent localIntent = getParentActivityIntent(paramActivity);
    if (localIntent == null) {
      throw new IllegalArgumentException("Activity " + paramActivity.getClass().getSimpleName() + " does not have a parent activity name specified." + " (Did you forget to add the android.support.PARENT_ACTIVITY <meta-data> " + " element in your manifest?)");
    }
    navigateUpTo(paramActivity, localIntent);
  }
  
  public static void navigateUpTo(Activity paramActivity, Intent paramIntent)
  {
    IMPL.navigateUpTo(paramActivity, paramIntent);
  }
  
  public static boolean shouldUpRecreateTask(Activity paramActivity, Intent paramIntent)
  {
    return IMPL.shouldUpRecreateTask(paramActivity, paramIntent);
  }
  
  static class NavUtilsImplJB
    extends NavUtils.NavUtilsImplBase
  {
    public Intent getParentActivityIntent(Activity paramActivity)
    {
      Intent localIntent = NavUtilsJB.getParentActivityIntent(paramActivity);
      if (localIntent == null) {
        localIntent = superGetParentActivityIntent(paramActivity);
      }
      return localIntent;
    }
    
    public String getParentActivityName(Context paramContext, ActivityInfo paramActivityInfo)
    {
      String str = NavUtilsJB.getParentActivityName(paramActivityInfo);
      if (str == null) {
        str = super.getParentActivityName(paramContext, paramActivityInfo);
      }
      return str;
    }
    
    public void navigateUpTo(Activity paramActivity, Intent paramIntent)
    {
      NavUtilsJB.navigateUpTo(paramActivity, paramIntent);
    }
    
    public boolean shouldUpRecreateTask(Activity paramActivity, Intent paramIntent)
    {
      return NavUtilsJB.shouldUpRecreateTask(paramActivity, paramIntent);
    }
    
    Intent superGetParentActivityIntent(Activity paramActivity)
    {
      return super.getParentActivityIntent(paramActivity);
    }
  }
  
  static class NavUtilsImplBase
    implements NavUtils.NavUtilsImpl
  {
    public Intent getParentActivityIntent(Activity paramActivity)
    {
      Object localObject = null;
      String str = NavUtils.getParentActivityName(paramActivity);
      if (str == null) {}
      for (;;)
      {
        return localObject;
        ComponentName localComponentName = new ComponentName(paramActivity, str);
        try
        {
          if (NavUtils.getParentActivityName(paramActivity, localComponentName) == null)
          {
            localObject = IntentCompat.makeMainActivity(localComponentName);
          }
          else
          {
            Intent localIntent = new Intent().setComponent(localComponentName);
            localObject = localIntent;
          }
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
          Log.e("NavUtils", "getParentActivityIntent: bad parentActivityName '" + str + "' in manifest");
        }
      }
    }
    
    public String getParentActivityName(Context paramContext, ActivityInfo paramActivityInfo)
    {
      String str;
      if (paramActivityInfo.metaData == null) {
        str = null;
      }
      for (;;)
      {
        return str;
        str = paramActivityInfo.metaData.getString("android.support.PARENT_ACTIVITY");
        if (str == null) {
          str = null;
        } else if (str.charAt(0) == '.') {
          str = paramContext.getPackageName() + str;
        }
      }
    }
    
    public void navigateUpTo(Activity paramActivity, Intent paramIntent)
    {
      paramIntent.addFlags(67108864);
      paramActivity.startActivity(paramIntent);
      paramActivity.finish();
    }
    
    public boolean shouldUpRecreateTask(Activity paramActivity, Intent paramIntent)
    {
      String str = paramActivity.getIntent().getAction();
      if ((str != null) && (!str.equals("android.intent.action.MAIN"))) {}
      for (boolean bool = true;; bool = false) {
        return bool;
      }
    }
  }
  
  static abstract interface NavUtilsImpl
  {
    public abstract Intent getParentActivityIntent(Activity paramActivity);
    
    public abstract String getParentActivityName(Context paramContext, ActivityInfo paramActivityInfo);
    
    public abstract void navigateUpTo(Activity paramActivity, Intent paramIntent);
    
    public abstract boolean shouldUpRecreateTask(Activity paramActivity, Intent paramIntent);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     android.support.v4.app.NavUtils
 * JD-Core Version:    0.7.0.1
 */