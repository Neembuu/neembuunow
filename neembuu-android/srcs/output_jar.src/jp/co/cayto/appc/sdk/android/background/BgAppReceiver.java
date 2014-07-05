package jp.co.cayto.appc.sdk.android.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import jp.co.cayto.appc.sdk.android.common.AppPreference;

public final class BgAppReceiver
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Bundle localBundle = getResultExtras(true);
    if (localBundle == null) {
      localBundle = new Bundle();
    }
    if (paramIntent.getAction().equals("jp.co.cayto.appc.sdk.android.remote.command.gid.search"))
    {
      ArrayList localArrayList = localBundle.getStringArrayList("gid_search_result");
      if (localArrayList == null) {
        localArrayList = new ArrayList();
      }
      String str = AppPreference.getGid(paramContext);
      if (str != null) {
        localArrayList.add(str);
      }
      localBundle.putStringArrayList("gid_search_result", localArrayList);
      setResultExtras(localBundle);
    }
    for (;;)
    {
      return;
      if (paramIntent.getAction().equals("jp.co.cayto.appc.sdk.android.remote.command.gid.set"))
      {
        AppPreference.setGid(paramContext, paramIntent.getExtras().getString("gid"));
        AppPreference.setPermissionOn(paramContext);
      }
      else if (paramIntent.getAction().equals("jp.co.cayto.appc.sdk.android.remote.command.gid.remove"))
      {
        AppPreference.removeGid(paramContext);
        AppPreference.setPermissionOff(paramContext);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.background.BgAppReceiver
 * JD-Core Version:    0.7.0.1
 */