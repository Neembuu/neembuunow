package jp.co.asbit.pvstar;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CategoryActivity
  extends BaseActivity
{
  private ListView mListView;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentAndTitle(2130903041, 2130903086);
    setTitle(getString(2131296409));
    ArrayList localArrayList = new ArrayList();
    int[] arrayOfInt = getResources().getIntArray(2131099657);
    String[] arrayOfString = getResources().getStringArray(2131099658);
    String str = getApplication().getPackageName();
    for (int i = 0;; i++)
    {
      if (i >= arrayOfInt.length)
      {
        CategoryRowAdapter localCategoryRowAdapter = new CategoryRowAdapter(this.mContext, 0, localArrayList);
        this.mListView = ((ListView)findViewById(2131492873));
        this.mListView.setAdapter(localCategoryRowAdapter);
        this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
          public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            Category localCategory = (Category)paramAnonymousAdapterView.getItemAtPosition(paramAnonymousInt);
            if (localCategory.getId() == 5)
            {
              Intent localIntent1 = new Intent(CategoryActivity.this.mContext, CategoryDetailActivity.class);
              localIntent1.putExtra("CATEGORY", localCategory);
              CategoryActivity.this.startActivity(localIntent1);
            }
            for (;;)
            {
              return;
              Intent localIntent2 = new Intent(CategoryActivity.this.mContext, CategoryFlagmentsActivity.class);
              localIntent2.putExtra("CATEGORY", localCategory);
              CategoryActivity.this.startActivity(localIntent2);
            }
          }
        });
        return;
      }
      localArrayList.add(new Category(arrayOfInt[i], arrayOfString[i], getString(getResources().getIdentifier(arrayOfString[i], "string", str))));
    }
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131427333, paramMenu);
    return super.onCreateOptionsMenu(paramMenu);
  }
  
  protected void onDestroy()
  {
    try
    {
      this.mListView.setOnItemClickListener(null);
      super.onDestroy();
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
      }
    }
  }
  
  static class CategoryRowAdapter
    extends ArrayAdapter<Category>
  {
    private LayoutInflater layoutInflater_;
    
    public CategoryRowAdapter(Context paramContext, int paramInt, List<Category> paramList)
    {
      super(paramInt, paramList);
      this.layoutInflater_ = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      Category localCategory = (Category)getItem(paramInt);
      if (paramView == null) {
        paramView = this.layoutInflater_.inflate(2130903084, null);
      }
      ((TextView)paramView.findViewById(2131492971)).setText(localCategory.getName());
      return paramView;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.CategoryActivity
 * JD-Core Version:    0.7.0.1
 */