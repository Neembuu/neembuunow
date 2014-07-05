package jp.co.cayto.appc.sdk.android.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import jp.co.cayto.appc.sdk.android.common.AppController;
import jp.co.cayto.appc.sdk.android.common.AppPreference;

public class BootStrap
  extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    AppController localAppController = AppController.createIncetance(paramContext);
    if ((!localAppController.isInitialized()) || (!AppPreference.isPermission(paramContext)) || (!"android.intent.action.PACKAGE_ADDED".equals(paramIntent.getAction()))) {}
    for (;;)
    {
      return;
      localAppController.sendCPI(paramContext, paramIntent.getDataString().substring(8));
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.background.BootStrap
 * JD-Core Version:    0.7.0.1
 */