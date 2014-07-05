package jp.co.imobile.android;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

final class j
  implements Runnable
{
  private final Handler b;
  
  private j(a parama, Handler paramHandler, byte paramByte)
  {
    this.b = paramHandler;
  }
  
  private final void a(long paramLong1, long paramLong2, ar paramar)
  {
    a locala = this.a;
    String[] arrayOfString = new String[6];
    arrayOfString[0] = ",realWaitTime:";
    arrayOfString[1] = String.valueOf(paramLong1);
    arrayOfString[2] = ",duration:";
    arrayOfString[3] = String.valueOf(paramLong2);
    arrayOfString[4] = ",animType:";
    arrayOfString[5] = paramar.toString();
    cj.c("callback sdk wait time(Millis)", locala, arrayOfString);
    if (paramLong1 > 0L) {
      TimeUnit.MILLISECONDS.sleep(paramLong1);
    }
  }
  
  public final void run()
  {
    try
    {
      long l1 = SystemClock.uptimeMillis();
      l2 = 0L;
      bb localbb = a.a(this.a);
      if ((localbb != null) && (!this.a.b.get()))
      {
        cg localcg = localbb.e();
        if (localcg != null) {
          l2 = localcg.b();
        }
      }
      long l3 = l1 + (l2 - 350L);
      this.a.b.compareAndSet(true, false);
      a.b(this.a);
      if (!Thread.currentThread().isInterrupted())
      {
        a.c(this.a);
        localh1 = a.d(this.a);
        if (!Thread.currentThread().isInterrupted())
        {
          l4 = l3 - SystemClock.uptimeMillis();
          if (localh1.a.h())
          {
            if (!this.a.a.a()) {
              a(l4, l2, localh1.a.i());
            }
            cj.c("not found ad", this.a, new String[0]);
            ap localap = localh1.a;
            AdRequestResult localAdRequestResult3 = AdRequestResult.a(a.a(this.a), a.e(this.a), "not found ad", AdRequestResultType.NOT_FOUND_AD);
            h localh2 = new h(localap, localAdRequestResult3);
            Message localMessage4 = this.b.obtainMessage(2, localh2);
            if (a.e(this.a).c()) {
              a.f(this.a);
            }
            a.a(this.a, localh2);
            this.b.sendMessage(localMessage4);
            a.a(this.a, this.b, null, localAdRequestResult3);
            this.a.b.compareAndSet(false, true);
          }
        }
      }
    }
    catch (p localp)
    {
      long l2;
      h localh1;
      long l4;
      cj.a("async prepare error env and deliver", this.a, localp, new String[0]);
      AdRequestResult localAdRequestResult2 = AdRequestResult.a(a.a(this.a), a.e(this.a), localp.getMessage(), localp.a());
      a.a(this.a, this.b, localp, localAdRequestResult2);
      Message localMessage2 = this.b.obtainMessage(1, new h(ap.a, localAdRequestResult2));
      this.b.sendMessage(localMessage2);
      return;
      Message localMessage3 = this.b.obtainMessage(0, localh1);
      a(l4, l2, localh1.a.i());
      if (!Thread.currentThread().isInterrupted()) {
        this.b.sendMessage(localMessage3);
      }
    }
    catch (InterruptedException localInterruptedException) {}catch (Exception localException)
    {
      cj.a("(IM)AdController:", localException);
      AdRequestResult localAdRequestResult1 = AdRequestResult.a(a.a(this.a), a.e(this.a), localException.getMessage(), AdRequestResultType.UNKNOWN_ERROR);
      a.a(this.a, this.b, localException, localAdRequestResult1);
      Message localMessage1 = this.b.obtainMessage(1, new h(ap.a, localAdRequestResult1));
      this.b.sendMessage(localMessage1);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.j
 * JD-Core Version:    0.7.0.1
 */