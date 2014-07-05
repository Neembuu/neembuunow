package jp.co.imobile.android;

import android.graphics.drawable.Drawable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

abstract class bl
  extends af
{
  private final List a;
  
  bl(s params, int paramInt, ai paramai, List paramList, boolean paramBoolean)
  {
    super(params, paramInt, paramai, paramBoolean);
    if (paramList == null) {}
    for (this.a = Collections.unmodifiableList(Collections.emptyList());; this.a = Collections.unmodifiableList(new CopyOnWriteArrayList(paramList))) {
      return;
    }
  }
  
  abstract Drawable a(n paramn);
  
  public void a(aq paramaq)
  {
    super.a(paramaq);
    bi localbi = c();
    paramaq.a(localbi.d()).g(localbi.c()).f(localbi.b()).a(localbi.a());
  }
  
  final bi c()
  {
    return (bi)this.a.get(-1 + bo.b.a().intValue());
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.imobile.android.bl
 * JD-Core Version:    0.7.0.1
 */