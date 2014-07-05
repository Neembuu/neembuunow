package jp.co.asbit.pvstar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import java.util.ArrayList;
import jp.co.asbit.pvstar.api.UpdateCategoryIndexTask;

public class CategoryIndexFlagment
  extends Fragment
{
  private Category category;
  private Context mContext;
  private View view;
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.view = getActivity().getLayoutInflater().inflate(2130903043, null);
    this.category = ((Category)getArguments().getSerializable("CATEGORY"));
    this.mContext = getActivity().getApplicationContext();
    UpdateCategoryIndexTask local1 = new UpdateCategoryIndexTask()
    {
      protected void onPostExecute(ArrayList<String> paramAnonymousArrayList)
      {
        String[] arrayOfString;
        int i;
        if (paramAnonymousArrayList != null)
        {
          arrayOfString = new String[paramAnonymousArrayList.size()];
          i = 0;
        }
        for (;;)
        {
          if (i >= paramAnonymousArrayList.size())
          {
            CategoryIndexFlagment.CategoryIndexAdapter localCategoryIndexAdapter = new CategoryIndexFlagment.CategoryIndexAdapter(CategoryIndexFlagment.this.mContext, 0, arrayOfString);
            GridView localGridView = (GridView)CategoryIndexFlagment.this.view.findViewById(2131492876);
            localGridView.setAdapter(localCategoryIndexAdapter);
            localGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
              public void onItemClick(AdapterView<?> paramAnonymous2AdapterView, View paramAnonymous2View, int paramAnonymous2Int, long paramAnonymous2Long)
              {
                String str = (String)paramAnonymous2AdapterView.getItemAtPosition(paramAnonymous2Int);
                if (!str.equals(Util.space()))
                {
                  Intent localIntent = new Intent(CategoryIndexFlagment.this.mContext, CategoryDetailActivity.class);
                  localIntent.putExtra("CATEGORY", CategoryIndexFlagment.this.category);
                  localIntent.putExtra("CATEGORY_INDEX", str);
                  CategoryIndexFlagment.this.startActivity(localIntent);
                }
              }
            });
          }
          try
          {
            ((CategoryFlagmentsActivity)CategoryIndexFlagment.this.getActivity()).dismissProgressDialog();
            super.onPostExecute(paramAnonymousArrayList);
            return;
            arrayOfString[i] = ((String)paramAnonymousArrayList.get(i));
            i++;
          }
          catch (NullPointerException localNullPointerException)
          {
            for (;;)
            {
              localNullPointerException.printStackTrace();
            }
          }
        }
      }
      
      protected void onPreExecute()
      {
        ((CategoryFlagmentsActivity)CategoryIndexFlagment.this.getActivity()).showProgressDialog();
        super.onPreExecute();
      }
    };
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.category.getDir();
    local1.execute(arrayOfString);
    return this.view;
  }
  
  private static class CategoryIndexAdapter
    extends ArrayAdapter<String>
  {
    private LayoutInflater layoutInflater_;
    
    public CategoryIndexAdapter(Context paramContext, int paramInt, String[] paramArrayOfString)
    {
      super(paramInt, paramArrayOfString);
      this.layoutInflater_ = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      String str = (String)getItem(paramInt);
      if (paramView == null) {
        paramView = this.layoutInflater_.inflate(2130903081, null);
      }
      ((TextView)paramView.findViewById(2131492971)).setText(str);
      if (paramInt / 5 % 2 == 0) {
        paramView.setBackgroundColor(Color.argb(127, 0, 0, 0));
      }
      return paramView;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.CategoryIndexFlagment
 * JD-Core Version:    0.7.0.1
 */