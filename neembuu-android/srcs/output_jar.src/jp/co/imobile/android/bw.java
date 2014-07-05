package jp.co.imobile.android;

import org.json.JSONObject;

final class bw
{
  private final String a;
  private final bt b;
  private final ca c;
  
  private bw(bx parambx)
  {
    this.a = bx.a(parambx);
    this.b = bx.b(parambx);
    this.c = bx.c(parambx);
  }
  
  static final bw a(JSONObject paramJSONObject)
  {
    bx localbx = new bx();
    int i = paramJSONObject.getInt("oat");
    bt localbt = (bt)ay.a(bt.a(), Integer.valueOf(i), bt.a);
    bw localbw;
    if (localbt == bt.a) {
      localbw = null;
    }
    for (;;)
    {
      return localbw;
      bx.a(localbx, localbt);
      int j = paramJSONObject.getInt("ct");
      ca localca = (ca)ay.a(ca.b(), Integer.valueOf(j), ca.a);
      if (localca == ca.a)
      {
        localbw = null;
      }
      else
      {
        bx.a(localbx, localca);
        bx.a(localbx, paramJSONObject.getString("nm"));
        localbw = new bw(localbx);
      }
    }
  }
  
  final String a()
  {
    return this.a;
  }
  
  final bt b()
  {
    return this.b;
  }
  
  final ca c()
  {
    return this.c;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.bw
 * JD-Core Version:    0.7.0.1
 */