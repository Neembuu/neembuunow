package jp.co.imobile.android;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class bb
  implements ag, bp
{
  private static final ba a = new bc();
  private final int b;
  private final int c;
  private final long d;
  private final long e;
  private final cg f;
  private final List g;
  private final int h;
  private final boolean i;
  private final bd j;
  private final List k;
  private final AtomicBoolean l;
  
  private bb(be parambe)
  {
    this.b = be.a(parambe);
    this.c = be.b(parambe);
    this.d = be.c(parambe);
    this.e = (Calendar.getInstance().getTimeInMillis() + this.d);
    this.f = be.d(parambe);
    this.h = be.e(parambe);
    this.i = be.f(parambe);
    if (be.g(parambe) == null)
    {
      this.g = Collections.unmodifiableList(Collections.emptyList());
      String[] arrayOfString = new String[2];
      arrayOfString[bool] = ", random:";
      arrayOfString[1] = String.valueOf(cj.c());
      cj.c("house ad deliver type", this, arrayOfString);
      if (!cj.c()) {
        break label181;
      }
    }
    label181:
    for (this.j = new bf(this);; this.j = new bg(this))
    {
      if (be.h(parambe) != null) {
        break label196;
      }
      this.k = Collections.unmodifiableList(Collections.emptyList());
      this.l = new AtomicBoolean(false);
      return;
      this.g = Collections.unmodifiableList(new CopyOnWriteArrayList(be.g(parambe)));
      break;
    }
    label196:
    this.k = Collections.unmodifiableList(new CopyOnWriteArrayList(be.h(parambe)));
    if (ay.a(this.k)) {}
    for (;;)
    {
      this.l = new AtomicBoolean(bool);
      break;
      bool = true;
    }
  }
  
  private static List a(JSONObject paramJSONObject)
  {
    try
    {
      JSONArray localJSONArray = paramJSONObject.optJSONArray("pi");
      ArrayList localArrayList = new ArrayList();
      if (localJSONArray != null)
      {
        int m = localJSONArray.length();
        for (int n = 0; n < m; n++)
        {
          cb localcb = cb.a(localJSONArray.getJSONObject(n));
          if (!ay.a(localcb.b())) {
            localArrayList.add(localcb);
          }
        }
      }
      return localArrayList;
    }
    catch (JSONException localJSONException)
    {
      localArrayList = new ArrayList();
    }
  }
  
  static bb a(int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    try
    {
      n.b(paramString);
      JSONObject localJSONObject1 = new JSONObject(paramString).getJSONObject("result");
      be localbe = new be();
      be.b(be.a(be.a(localbe, 1000L * localJSONObject1.getLong("expiration")), paramInt1), paramInt2);
      be.a(localbe, localJSONObject1.optBoolean("enableHouseAd", false));
      be.c(localbe, localJSONObject1.optInt("houseAdSelectRate", 0));
      be.a(localbe, b(localJSONObject1));
      JSONObject localJSONObject2 = localJSONObject1.getJSONArray("environments").getJSONObject(0);
      ch localch = new ch();
      localch.a(paramInt3);
      JSONObject localJSONObject3 = localJSONObject2.getJSONObject("spotInfo");
      localch.a(1000L * localJSONObject3.optLong("duration", 20L));
      int m = localJSONObject3.optInt("animationType", ar.b.a().intValue());
      localch.a((ar)ay.a(ar.d(), Integer.valueOf(m), ar.b));
      be.a(localbe, new cg(localch));
      be.b(localbe, a(localJSONObject1));
      bb localbb = new bb(localbe);
      return localbb;
    }
    catch (JSONException localJSONException)
    {
      throw new p(AdRequestResultType.UNKNOWN_ERROR, "json deserialize error", localJSONException);
    }
  }
  
  private static List b(JSONObject paramJSONObject)
  {
    JSONArray localJSONArray = paramJSONObject.optJSONArray("houseAds");
    ArrayList localArrayList = new ArrayList();
    if (localJSONArray == null) {}
    for (;;)
    {
      return localArrayList;
      int m = localJSONArray.length();
      for (int n = 0; n < m; n++)
      {
        JSONObject localJSONObject = localJSONArray.getJSONObject(n);
        bn localbn = new bn();
        localbn.a(s.c);
        localbn.a(ai.b);
        int i1 = localJSONObject.getInt("houseAdvertisementID");
        localbn.a(localJSONObject.optString("applicationIdentifier", "")).a(i1).b(localJSONObject.optString("landingURL", "")).a(n.a(localJSONObject, i1));
        localArrayList.add(new bm(localbn));
      }
    }
  }
  
  /* Error */
  /**
   * @deprecated
   */
  private bm j()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 96	jp/co/imobile/android/bb:g	Ljava/util/List;
    //   6: invokestatic 145	jp/co/imobile/android/ay:a	(Ljava/util/Collection;)Z
    //   9: istore_2
    //   10: iload_2
    //   11: ifeq +11 -> 22
    //   14: aconst_null
    //   15: astore 4
    //   17: aload_0
    //   18: monitorexit
    //   19: aload 4
    //   21: areturn
    //   22: aload_0
    //   23: getfield 121	jp/co/imobile/android/bb:j	Ljp/co/imobile/android/bd;
    //   26: invokeinterface 375 1 0
    //   31: astore_3
    //   32: aload_3
    //   33: astore 4
    //   35: goto -18 -> 17
    //   38: astore_1
    //   39: aload_0
    //   40: monitorexit
    //   41: aload_1
    //   42: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	43	0	this	bb
    //   38	4	1	localObject1	Object
    //   9	2	2	bool	boolean
    //   31	2	3	localbm	bm
    //   15	19	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   2	10	38	finally
    //   22	32	38	finally
  }
  
  public final af a()
  {
    return j();
  }
  
  final ce a(PackageManager paramPackageManager, ao paramao)
  {
    List localList = paramPackageManager.getInstalledApplications(128);
    HashMap localHashMap = new HashMap();
    bt[] arrayOfbt = bt.a();
    int m = arrayOfbt.length;
    int n = 0;
    ArrayList localArrayList;
    Iterator localIterator1;
    if (n >= m)
    {
      cf localcf = new cf();
      localcf.a(paramao.m());
      localArrayList = new ArrayList();
      localIterator1 = this.k.iterator();
      if (!localIterator1.hasNext())
      {
        localcf.a(localArrayList);
        return new ce(localcf);
      }
    }
    else
    {
      bt localbt = arrayOfbt[n];
      HashSet localHashSet2 = new HashSet();
      Iterator localIterator3 = localList.iterator();
      for (;;)
      {
        if (!localIterator3.hasNext())
        {
          localHashMap.put(localbt, localHashSet2);
          n++;
          break;
        }
        localHashSet2.add(localbt.a(((ApplicationInfo)localIterator3.next()).packageName));
      }
    }
    cb localcb = (cb)localIterator1.next();
    cd localcd1 = cd.a;
    ca localca1 = ca.b;
    bz localbz = new bz();
    localbz.a(localcb.a());
    Iterator localIterator2 = localcb.b().iterator();
    label235:
    ca localca2;
    cd localcd2;
    if (!localIterator2.hasNext())
    {
      localca2 = localca1;
      localcd2 = localcd1;
    }
    for (;;)
    {
      localbz.a(localcd2);
      localbz.a(localca2);
      localArrayList.add(new by(localbz));
      break;
      bw localbw = (bw)localIterator2.next();
      if (localbw.c() != ca.b) {
        break label235;
      }
      HashSet localHashSet1 = (HashSet)localHashMap.get(localbw.b());
      if (localHashSet1 == null)
      {
        localbz.a(localbw.c());
        break label235;
      }
      if (!localHashSet1.contains(localbw.a())) {
        break label235;
      }
      localcd2 = cd.b;
      localca2 = localbw.c();
    }
  }
  
  final void a(PackageManager paramPackageManager)
  {
    if (!this.i) {
      return;
    }
    List localList = paramPackageManager.getInstalledApplications(128);
    Iterator localIterator1 = this.g.iterator();
    label26:
    bm localbm;
    String str;
    int m;
    label71:
    Iterator localIterator2;
    if (localIterator1.hasNext())
    {
      localbm = (bm)localIterator1.next();
      str = localbm.e();
      if ((str == null) || (str.length() == 0)) {
        break label107;
      }
      m = 0;
      if (m != 0) {
        break label111;
      }
      localIterator2 = localList.iterator();
      label84:
      if (localIterator2.hasNext()) {
        break label113;
      }
    }
    for (boolean bool = false;; bool = true)
    {
      localbm.a(bool);
      break label26;
      break;
      label107:
      m = 1;
      break label71;
      label111:
      break label26;
      label113:
      if (!str.equals(((ApplicationInfo)localIterator2.next()).packageName)) {
        break label84;
      }
      String[] arrayOfString = new String[6];
      arrayOfString[0] = ",mediaId:";
      arrayOfString[1] = String.valueOf(this.c);
      arrayOfString[2] = ",houseAdId:";
      arrayOfString[3] = String.valueOf(localbm.a());
      arrayOfString[4] = ",package:";
      arrayOfString[5] = str;
      cj.b("house ad installed package ", this, arrayOfString);
    }
  }
  
  public final int b()
  {
    int m = 0;
    if (!this.i) {}
    for (;;)
    {
      return m;
      if (!ay.a(this.g))
      {
        List localList = this.g;
        ba localba = a;
        int n = localList.size();
        for (int i1 = 0; i1 < n; i1++) {
          if (localba.a(localList.get(i1))) {
            m++;
          }
        }
      }
    }
  }
  
  public final boolean c()
  {
    if (b() > 0) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  final boolean d()
  {
    boolean bool = true;
    if (Calendar.getInstance().getTimeInMillis() > this.e)
    {
      String[] arrayOfString = new String[2];
      arrayOfString[0] = ", mediaId:";
      arrayOfString[bool] = String.valueOf(this.c);
      cj.c("expiration spot environment data", this, arrayOfString);
    }
    for (;;)
    {
      return bool;
      bool = false;
    }
  }
  
  final cg e()
  {
    return this.f;
  }
  
  final boolean f()
  {
    return this.i;
  }
  
  final int g()
  {
    return this.h;
  }
  
  public final String getLogContents()
  {
    if (this.f == null) {}
    for (String str = ",mediaId:" + String.valueOf(this.c);; str = ",spotId:" + String.valueOf(this.f.a())) {
      return str;
    }
  }
  
  public final String getLogTag()
  {
    return "(IM)Environment:";
  }
  
  protected final AtomicBoolean h()
  {
    return this.l;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.bb
 * JD-Core Version:    0.7.0.1
 */