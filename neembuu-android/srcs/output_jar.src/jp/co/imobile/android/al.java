package jp.co.imobile.android;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class al
  implements ag, bp
{
  private static final ba a = new am();
  private final int b;
  private final int c;
  private final int d;
  private final long e;
  private final long f;
  private int g = 0;
  private List h;
  private final int i;
  
  private al(an paraman)
  {
    this.b = an.a(paraman);
    this.c = an.b(paraman);
    this.e = an.c(paraman);
    this.f = (Calendar.getInstance().getTimeInMillis() + this.e);
    this.d = an.d(paraman);
    if (an.e(paraman) == null) {
      this.h = Collections.unmodifiableList(Collections.emptyList());
    }
    for (this.i = 0;; this.i = an.e(paraman).size())
    {
      return;
      this.h = Collections.unmodifiableList(new CopyOnWriteArrayList(an.e(paraman)));
    }
  }
  
  static al a(int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    int j = 0;
    for (;;)
    {
      int k;
      try
      {
        n.b(paramString);
        JSONObject localJSONObject1 = new JSONObject(paramString).getJSONObject("result");
        an localan = new an();
        an.c(an.b(an.a(an.a(localan, 1000L * localJSONObject1.getLong("expiration")), paramInt1), paramInt2), paramInt3);
        localJSONArray = localJSONObject1.optJSONArray("ads");
        localArrayList = new ArrayList();
        if (localJSONArray != null)
        {
          k = localJSONArray.length();
          break label241;
        }
        an.a(localan, localArrayList);
        return new al(localan);
      }
      catch (JSONException localJSONException)
      {
        JSONArray localJSONArray;
        ArrayList localArrayList;
        JSONObject localJSONObject2;
        bk localbk;
        int m;
        throw new p(AdRequestResultType.UNKNOWN_ERROR, "json deserialize error", localJSONException);
      }
      localJSONObject2 = localJSONArray.getJSONObject(j);
      localbk = new bk();
      localbk.a(s.a);
      localbk.a(ai.a);
      m = localJSONObject2.getInt("advertisementId");
      localbk.a(localJSONObject2.getInt("categoryId")).b(m);
      localbk.a(localJSONObject2.optBoolean("isDefaultAd", false));
      localbk.a(n.a(localJSONObject2, m));
      localArrayList.add(new bj(localbk));
      j++;
      label241:
      if (j < k) {}
    }
  }
  
  /* Error */
  /**
   * @deprecated
   */
  private bl e()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 81	jp/co/imobile/android/al:h	Ljava/util/List;
    //   6: invokestatic 228	jp/co/imobile/android/ay:a	(Ljava/util/Collection;)Z
    //   9: istore_2
    //   10: iload_2
    //   11: ifeq +17 -> 28
    //   14: iconst_0
    //   15: istore_3
    //   16: iload_3
    //   17: ifne +16 -> 33
    //   20: aconst_null
    //   21: astore 5
    //   23: aload_0
    //   24: monitorexit
    //   25: aload 5
    //   27: areturn
    //   28: iconst_1
    //   29: istore_3
    //   30: goto -14 -> 16
    //   33: aload_0
    //   34: getfield 34	jp/co/imobile/android/al:g	I
    //   37: aload_0
    //   38: getfield 83	jp/co/imobile/android/al:i	I
    //   41: irem
    //   42: istore 4
    //   44: aload_0
    //   45: iconst_1
    //   46: aload_0
    //   47: getfield 34	jp/co/imobile/android/al:g	I
    //   50: iadd
    //   51: putfield 34	jp/co/imobile/android/al:g	I
    //   54: aload_0
    //   55: getfield 81	jp/co/imobile/android/al:h	Ljava/util/List;
    //   58: iload 4
    //   60: invokeinterface 232 2 0
    //   65: checkcast 234	jp/co/imobile/android/bl
    //   68: astore 5
    //   70: goto -47 -> 23
    //   73: astore_1
    //   74: aload_0
    //   75: monitorexit
    //   76: aload_1
    //   77: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	78	0	this	al
    //   73	4	1	localObject	Object
    //   9	2	2	bool	boolean
    //   15	15	3	j	int
    //   42	17	4	k	int
    //   21	48	5	localbl	bl
    // Exception table:
    //   from	to	target	type
    //   2	10	73	finally
    //   33	70	73	finally
  }
  
  public final af a()
  {
    return e();
  }
  
  final boolean b()
  {
    boolean bool = false;
    if (Calendar.getInstance().getTimeInMillis() > this.f)
    {
      cj.c("expiration deliver data", this, new String[0]);
      bool = true;
    }
    return bool;
  }
  
  public final boolean c()
  {
    List localList = this.h;
    if (ay.a(localList, a).size() == localList.size()) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public final int d()
  {
    if (ay.a(this.h)) {}
    for (int j = 0;; j = this.h.size()) {
      return j;
    }
  }
  
  public final String getLogContents()
  {
    return ",spotId:" + this.d;
  }
  
  public final String getLogTag()
  {
    return "(IM)DeliverEntity:";
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.al
 * JD-Core Version:    0.7.0.1
 */