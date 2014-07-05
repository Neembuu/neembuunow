package com.google.ads;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.google.ads.internal.a;
import com.google.ads.util.b;
import com.google.ads.util.f;
import java.lang.ref.WeakReference;
import java.util.Date;

public final class at
{
  private static final a a = (a)a.a.b();
  
  public static void a(Activity paramActivity)
  {
    new Thread(new a(paramActivity)).start();
  }
  
  public static boolean a(Context paramContext, long paramLong)
  {
    if (!a(paramContext, paramLong, PreferenceManager.getDefaultSharedPreferences(paramContext.getApplicationContext()))) {}
    for (boolean bool = false;; bool = true)
    {
      return bool;
      new Thread(new Runnable()
      {
        public void run()
        {
          SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(at.this.getApplicationContext()).edit();
          localEditor.putString("drt", "");
          localEditor.putLong("drt_ts", 0L);
          localEditor.commit();
        }
      }).start();
    }
  }
  
  static boolean a(Context paramContext, long paramLong, SharedPreferences paramSharedPreferences)
  {
    if ((!paramSharedPreferences.contains("drt")) || (!paramSharedPreferences.contains("drt_ts")) || (paramSharedPreferences.getLong("drt_ts", 0L) < new Date().getTime() - paramLong)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  private static class a
    implements Runnable
  {
    private final WeakReference<Activity> a;
    private final SharedPreferences.Editor b;
    
    public a(Activity paramActivity)
    {
      this(paramActivity, null);
    }
    
    a(Activity paramActivity, SharedPreferences.Editor paramEditor)
    {
      this.a = new WeakReference(paramActivity);
      this.b = paramEditor;
    }
    
    private SharedPreferences.Editor a(Context paramContext)
    {
      if (this.b == null) {}
      for (SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(paramContext.getApplicationContext()).edit();; localEditor = this.b) {
        return localEditor;
      }
    }
    
    public void run()
    {
      for (;;)
      {
        SharedPreferences.Editor localEditor;
        try
        {
          Activity localActivity = (Activity)this.a.get();
          if (localActivity == null)
          {
            b.a("Activity was null while making a doritos cookie request.");
          }
          else
          {
            Cursor localCursor = localActivity.getContentResolver().query(as.a, as.b, null, null, null);
            if ((localCursor != null) && (localCursor.moveToFirst()) && (localCursor.getColumnNames().length > 0))
            {
              str = localCursor.getString(localCursor.getColumnIndex(localCursor.getColumnName(0)));
              localEditor = a(localActivity);
              if (TextUtils.isEmpty(str)) {
                break label163;
              }
              localEditor.putString("drt", str);
              localEditor.putLong("drt_ts", new Date().getTime());
              localEditor.commit();
            }
          }
        }
        catch (Throwable localThrowable)
        {
          b.d("An unknown error occurred while sending a doritos request.", localThrowable);
        }
        b.a("Google+ app not installed, not storing doritos cookie");
        String str = null;
        continue;
        label163:
        localEditor.putString("drt", "");
        localEditor.putLong("drt_ts", 0L);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     com.google.ads.at
 * JD-Core Version:    0.7.0.1
 */