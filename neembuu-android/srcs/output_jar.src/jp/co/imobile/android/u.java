package jp.co.imobile.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class u
  extends BroadcastReceiver
{
  u(AdView paramAdView) {}
  
  public final void onReceive(Context paramContext, Intent paramIntent)
  {
    try
    {
      AdView localAdView = this.a;
      String[] arrayOfString = new String[2];
      arrayOfString[0] = ",action:";
      arrayOfString[1] = paramIntent.getAction();
      cj.b("adview stop screen on or off", localAdView, arrayOfString);
      this.a.a();
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        cj.a("(IM)AdView:", localException);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.u
 * JD-Core Version:    0.7.0.1
 */