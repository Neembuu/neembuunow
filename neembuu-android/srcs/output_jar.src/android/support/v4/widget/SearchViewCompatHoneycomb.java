package android.support.v4.widget;

import android.content.Context;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

class SearchViewCompatHoneycomb
{
  public static Object newOnQueryTextListener(OnQueryTextListenerCompatBridge paramOnQueryTextListenerCompatBridge)
  {
    new SearchView.OnQueryTextListener()
    {
      public boolean onQueryTextChange(String paramAnonymousString)
      {
        return SearchViewCompatHoneycomb.this.onQueryTextChange(paramAnonymousString);
      }
      
      public boolean onQueryTextSubmit(String paramAnonymousString)
      {
        return SearchViewCompatHoneycomb.this.onQueryTextSubmit(paramAnonymousString);
      }
    };
  }
  
  public static View newSearchView(Context paramContext)
  {
    return new SearchView(paramContext);
  }
  
  public static void setOnQueryTextListener(Object paramObject1, Object paramObject2)
  {
    ((SearchView)paramObject1).setOnQueryTextListener((SearchView.OnQueryTextListener)paramObject2);
  }
  
  static abstract interface OnQueryTextListenerCompatBridge
  {
    public abstract boolean onQueryTextChange(String paramString);
    
    public abstract boolean onQueryTextSubmit(String paramString);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     android.support.v4.widget.SearchViewCompatHoneycomb
 * JD-Core Version:    0.7.0.1
 */