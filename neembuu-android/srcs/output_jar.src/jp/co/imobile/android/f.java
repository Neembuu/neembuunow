package jp.co.imobile.android;

import org.json.JSONException;

final class f
  implements Runnable
{
  f(a parama) {}
  
  public final void run()
  {
    try
    {
      a.i(this.a);
      return;
    }
    catch (JSONException localJSONException)
    {
      for (;;)
      {
        a locala2 = this.a;
        String[] arrayOfString2 = new String[2];
        arrayOfString2[0] = "ex:";
        arrayOfString2[1] = localJSONException.toString();
        cj.b("Unknown error", locala2, arrayOfString2);
      }
    }
    catch (p localp)
    {
      for (;;)
      {
        a locala1 = this.a;
        String[] arrayOfString1 = new String[2];
        arrayOfString1[0] = "ex:";
        arrayOfString1[1] = localp.toString();
        cj.b("Unknown error", locala1, arrayOfString1);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.f
 * JD-Core Version:    0.7.0.1
 */