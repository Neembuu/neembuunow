package jp.co.imobile.android;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

final class cb
{
  private final int a;
  private final List b;
  
  private cb(cc paramcc)
  {
    this.a = cc.a(paramcc);
    if (cc.b(paramcc) == null) {}
    for (this.b = Collections.unmodifiableList(Collections.emptyList());; this.b = Collections.unmodifiableList(new CopyOnWriteArrayList(cc.b(paramcc)))) {
      return;
    }
  }
  
  static final cb a(JSONObject paramJSONObject)
  {
    cc localcc = new cc();
    cc.a(localcc, paramJSONObject.getInt("id"));
    JSONArray localJSONArray = paramJSONObject.optJSONArray("cns");
    ArrayList localArrayList = new ArrayList();
    int i;
    if (localJSONArray != null) {
      i = localJSONArray.length();
    }
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        cc.a(localcc, localArrayList);
        return new cb(localcc);
      }
      bw localbw = bw.a(localJSONArray.getJSONObject(j));
      if (localbw != null) {
        localArrayList.add(localbw);
      }
    }
  }
  
  final int a()
  {
    return this.a;
  }
  
  final List b()
  {
    return this.b;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.cb
 * JD-Core Version:    0.7.0.1
 */