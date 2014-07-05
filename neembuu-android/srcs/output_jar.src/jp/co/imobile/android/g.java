package jp.co.imobile.android;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

final class g
  extends Handler
  implements bp
{
  private final WeakReference a;
  private final WeakReference b;
  
  public g(a parama, o paramo)
  {
    super(Looper.getMainLooper());
    this.a = new WeakReference(parama);
    this.b = new WeakReference(paramo);
  }
  
  public final String getLogContents()
  {
    return "";
  }
  
  public final String getLogTag()
  {
    return "(IM)AdRequestControllerHandler:";
  }
  
  public final void handleMessage(Message paramMessage)
  {
    a locala = (a)this.a.get();
    o localo = (o)this.b.get();
    if ((locala != null) && (localo != null)) {
      a.a(locala, localo, paramMessage);
    }
    for (;;)
    {
      return;
      String[] arrayOfString = new String[2];
      arrayOfString[0] = "type:";
      arrayOfString[1] = "callback";
      cj.b("removed week ref", this, arrayOfString);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.g
 * JD-Core Version:    0.7.0.1
 */