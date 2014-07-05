package jp.co.imobile.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class v
  extends BroadcastReceiver
{
  v(AdView paramAdView) {}
  
  public final void onReceive(Context paramContext, Intent paramIntent)
  {
    try
    {
      if (AdView.h(this.a).b())
      {
        AdView localAdView2 = this.a;
        String[] arrayOfString2 = new String[2];
        arrayOfString2[0] = ",action:";
        arrayOfString2[1] = paramIntent.getAction();
        cj.b("adview start enable network", localAdView2, arrayOfString2);
        AdView.i(this.a);
      }
      else
      {
        AdView localAdView1 = this.a;
        String[] arrayOfString1 = new String[2];
        arrayOfString1[0] = ",action:";
        arrayOfString1[1] = paramIntent.getAction();
        cj.b("adview stop disable network", localAdView1, arrayOfString1);
        this.a.a();
      }
    }
    catch (Exception localException)
    {
      cj.a("(IM)AdView:", localException);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.v
 * JD-Core Version:    0.7.0.1
 */