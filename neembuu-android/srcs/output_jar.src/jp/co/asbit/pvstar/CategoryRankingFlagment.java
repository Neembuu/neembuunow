package jp.co.asbit.pvstar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import jp.co.asbit.pvstar.api.UpdateCategoryRankingTask;

public class CategoryRankingFlagment
  extends Fragment
{
  private Category category;
  private Context mContext;
  private View view;
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.view = getActivity().getLayoutInflater().inflate(2130903044, null);
    this.category = ((Category)getArguments().getSerializable("CATEGORY"));
    this.mContext = getActivity().getApplicationContext();
    UpdateCategoryRankingTask local1 = new UpdateCategoryRankingTask()
    {
      protected void onPostExecute(ArrayList<Category> paramAnonymousArrayList)
      {
        if (paramAnonymousArrayList != null)
        {
          CategoryRankingFlagment.CategoryRankingAdapter localCategoryRankingAdapter = new CategoryRankingFlagment.CategoryRankingAdapter(CategoryRankingFlagment.this.mContext, 0, paramAnonymousArrayList);
          ListView localListView = (ListView)CategoryRankingFlagment.this.view.findViewById(2131492875);
          localListView.setAdapter(localCategoryRankingAdapter);
          localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
          {
            public void onItemClick(AdapterView<?> paramAnonymous2AdapterView, View paramAnonymous2View, int paramAnonymous2Int, long paramAnonymous2Long)
            {
              Category localCategory = (Category)paramAnonymous2AdapterView.getItemAtPosition(paramAnonymous2Int);
              Intent localIntent = new Intent(CategoryRankingFlagment.this.mContext, SearchActivity.class);
              localIntent.putExtra("QUERY", localCategory.getName());
              CategoryRankingFlagment.this.startActivity(localIntent);
            }
          });
        }
        try
        {
          ((CategoryFlagmentsActivity)CategoryRankingFlagment.this.getActivity()).dismissProgressDialog();
          super.onPostExecute(paramAnonymousArrayList);
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
      
      protected void onPreExecute()
      {
        ((CategoryFlagmentsActivity)CategoryRankingFlagment.this.getActivity()).showProgressDialog();
        super.onPreExecute();
      }
    };
    String[] arrayOfString = new String[1];
    arrayOfString[0] = this.category.getDir();
    local1.execute(arrayOfString);
    return this.view;
  }
  
  private static class CategoryRankingAdapter
    extends ArrayAdapter<Category>
  {
    private LayoutInflater layoutInflater_;
    private Context mContext;
    
    public CategoryRankingAdapter(Context paramContext, int paramInt, List<Category> paramList)
    {
      super(paramInt, paramList);
      this.mContext = paramContext;
      this.layoutInflater_ = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      Category localCategory = (Category)getItem(paramInt);
      if (paramView == null) {
        paramView = this.layoutInflater_.inflate(2130903085, null);
      }
      ((TextView)paramView.findViewById(2131492971)).setText(localCategory.getName());
      ((TextView)paramView.findViewById(2131492972)).setText(String.valueOf(localCategory.getRank()) + this.mContext.getString(2131296484));
      return paramView;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.CategoryRankingFlagment
 * JD-Core Version:    0.7.0.1
 */