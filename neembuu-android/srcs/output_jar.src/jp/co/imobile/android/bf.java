package jp.co.imobile.android;

import java.util.List;
import java.util.Random;

final class bf
  implements bd
{
  final Random a = new Random(System.currentTimeMillis());
  
  private bf(bb parambb, byte paramByte) {}
  
  public final bm a()
  {
    bm localbm = null;
    if (!this.b.c()) {}
    for (;;)
    {
      return localbm;
      List localList = ay.a(bb.a(this.b), bb.i());
      if (!ay.a(localList))
      {
        int i = this.a.nextInt(localList.size());
        localbm = (bm)localList.get(i);
        bb localbb = this.b;
        String[] arrayOfString = new String[4];
        arrayOfString[0] = ", houseAdvertisementId:";
        arrayOfString[1] = String.valueOf(localbm.a());
        arrayOfString[2] = ", index:";
        arrayOfString[3] = String.valueOf(i);
        cj.b("select house ad info", localbb, arrayOfString);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.bf
 * JD-Core Version:    0.7.0.1
 */