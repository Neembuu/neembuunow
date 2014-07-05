package jp.co.imobile.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

 enum ai
  implements ax
{
  private final String c;
  private final boolean d;
  
  static
  {
    ai[] arrayOfai = new ai[2];
    arrayOfai[0] = a;
    arrayOfai[1] = b;
    e = arrayOfai;
  }
  
  private ai(String paramString, boolean paramBoolean, byte paramByte)
  {
    this.c = paramString;
    this.d = paramBoolean;
  }
  
  final void a(Context paramContext, Uri paramUri, int paramInt1, int paramInt2)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW", paramUri);
    localIntent.setFlags(268435456);
    cj.d().a("i-mobile SDK", "(IM)ClickAction:" + "perform click action" + "[params]" + " spotId:" + String.valueOf(paramInt1) + ", advertisementId:" + String.valueOf(paramInt2) + ", clickType:" + String.valueOf(this) + ", WEB URL:" + String.valueOf(paramUri.toString()));
    paramContext.startActivity(localIntent);
  }
  
  abstract void a(Context paramContext, ap paramap, n paramn);
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.ai
 * JD-Core Version:    0.7.0.1
 */