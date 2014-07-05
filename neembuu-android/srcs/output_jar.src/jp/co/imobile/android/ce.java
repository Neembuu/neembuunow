package jp.co.imobile.android;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

final class ce
{
  private final String a;
  private final List b;
  
  ce(cf paramcf)
  {
    this.a = cf.a(paramcf);
    if (cf.b(paramcf) == null) {}
    for (this.b = Collections.unmodifiableList(Collections.emptyList());; this.b = Collections.unmodifiableList(new CopyOnWriteArrayList(cf.b(paramcf)))) {
      return;
    }
  }
  
  final JSONObject a()
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("key", this.a);
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = this.b.iterator();
    for (;;)
    {
      if (!localIterator.hasNext())
      {
        localJSONObject.put("results", localJSONArray);
        return localJSONObject;
      }
      localJSONArray.put(((by)localIterator.next()).a());
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.ce
 * JD-Core Version:    0.7.0.1
 */