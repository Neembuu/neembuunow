package jp.co.imobile.android;

import java.util.List;

final class bg
  implements bd
{
  private int b = 0;
  private int c = 0;
  
  bg(bb parambb)
  {
    if (ay.a(bb.a(parambb))) {}
    for (;;)
    {
      return;
      this.c = bb.a(parambb).size();
    }
  }
  
  public final bm a()
  {
    bm localbm;
    if (!this.a.c()) {
      localbm = null;
    }
    for (;;)
    {
      return localbm;
      do
      {
        int i = this.b % this.c;
        this.b = (1 + this.b);
        localbm = (bm)bb.a(this.a).get(i);
      } while (localbm.d());
      bb localbb = this.a;
      String[] arrayOfString = new String[4];
      arrayOfString[0] = ", houseAdvertisementId:";
      arrayOfString[1] = String.valueOf(localbm.a());
      arrayOfString[2] = ", index:";
      arrayOfString[3] = String.valueOf(this.b);
      cj.b("select house ad info", localbb, arrayOfString);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.bg
 * JD-Core Version:    0.7.0.1
 */