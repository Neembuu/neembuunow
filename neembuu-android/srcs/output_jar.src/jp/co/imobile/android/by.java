package jp.co.imobile.android;

import org.json.JSONObject;

final class by
{
  private final int a;
  private final cd b;
  private final ca c;
  
  by(bz parambz)
  {
    this.a = bz.a(parambz);
    this.b = bz.b(parambz);
    this.c = bz.c(parambz);
  }
  
  final JSONObject a()
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("id", this.a);
    localJSONObject.put("st", this.b.a());
    localJSONObject.put("ct", this.c.a());
    return localJSONObject;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.by
 * JD-Core Version:    0.7.0.1
 */