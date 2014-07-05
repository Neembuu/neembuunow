package jp.co.imobile.android;

final class e
  implements Runnable
{
  e(a parama, h paramh, int paramInt) {}
  
  public final void run()
  {
    try
    {
      a.b(this.a, this.b.a);
      if (this.b.a.h())
      {
        cj.b("succeeded default ad imp count", this.a, new String[0]);
      }
      else
      {
        a locala3 = this.a;
        String[] arrayOfString3 = new String[2];
        arrayOfString3[0] = ", advertisementId:";
        arrayOfString3[1] = String.valueOf(this.c);
        cj.c("succeeded imp count", locala3, arrayOfString3);
      }
    }
    catch (p localp)
    {
      a locala2 = this.a;
      String[] arrayOfString2 = new String[4];
      arrayOfString2[0] = " type:";
      arrayOfString2[1] = localp.a().toString();
      arrayOfString2[2] = ", advertisementId:";
      arrayOfString2[3] = String.valueOf(this.c);
      cj.a("imp count up error", locala2, localp, arrayOfString2);
    }
    catch (Exception localException)
    {
      a locala1 = this.a;
      String[] arrayOfString1 = new String[3];
      arrayOfString1[0] = " (UNKOWN_ERROR)";
      arrayOfString1[1] = ", advertisementId:";
      arrayOfString1[2] = String.valueOf(this.c);
      cj.a("imp count up error", locala1, localException, arrayOfString1);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.e
 * JD-Core Version:    0.7.0.1
 */