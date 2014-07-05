package jp.co.asbit.pvstar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import jp.co.asbit.pvstar.api.UpdateCategoryDetailTask;
import jp.co.asbit.pvstar.api.UpdateCategoryDirTask;

public class CategoryDetailActivity
  extends BaseActivity
  implements AbsListView.OnScrollListener
{
  private Category category;
  private String index;
  private CategoryDetailAdapter mAdapter;
  private View mFooter;
  private ArrayList<Category> mList;
  private ListView mListView;
  private UpdateCategoryDetailTask mTask;
  private int page = 1;
  private ProgressDialog progressDialog;
  
  private void cancelAddListData()
  {
    if (isTaskRunning()) {
      this.mTask.cancel(true);
    }
    this.mTask = null;
  }
  
  private View getFooter()
  {
    if (this.mFooter == null) {
      this.mFooter = getLayoutInflater().inflate(2130903068, null);
    }
    return this.mFooter;
  }
  
  private boolean isTaskRunning()
  {
    if ((this.mTask != null) && (this.mTask.getStatus() == AsyncTask.Status.RUNNING)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public void addListData(ArrayList<Category> paramArrayList, int paramInt)
  {
    ArrayList localArrayList = getList();
    ListView localListView = getListView();
    if (paramArrayList.size() > 0)
    {
      localArrayList.addAll(paramArrayList);
      this.page = (1 + this.page);
    }
    if (getList().size() >= paramInt) {
      localListView.removeFooterView(getFooter());
    }
    for (;;)
    {
      getAdapter().notifyDataSetChanged();
      return;
      if (getFooter().getVisibility() != 0) {
        getFooter().setVisibility(0);
      }
    }
  }
  
  protected CategoryDetailAdapter getAdapter()
  {
    if (this.mAdapter == null) {
      this.mAdapter = new CategoryDetailAdapter(this.mContext, 0, getList());
    }
    return this.mAdapter;
  }
  
  protected ArrayList<Category> getList()
  {
    if (this.mList == null) {
      this.mList = new ArrayList();
    }
    return this.mList;
  }
  
  protected ListView getListView()
  {
    if (this.mListView == null) {
      this.mListView = ((ListView)findViewById(2131492875));
    }
    return this.mListView;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentAndTitle(2130903042, 2130903086);
    this.category = ((Category)getIntent().getSerializableExtra("CATEGORY"));
    this.index = getIntent().getStringExtra("CATEGORY_INDEX");
    ListView localListView = getListView();
    localListView.setOnScrollListener(this);
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        Category localCategory = (Category)paramAnonymousAdapterView.getItemAtPosition(paramAnonymousInt);
        if (localCategory == null) {}
        for (;;)
        {
          return;
          if (localCategory.getChildrenCount() > 0)
          {
            Intent localIntent1 = new Intent(CategoryDetailActivity.this.mContext, CategoryDetailActivity.class);
            localCategory.setDir(CategoryDetailActivity.this.category.getDir());
            localIntent1.putExtra("CATEGORY", localCategory);
            CategoryDetailActivity.this.startActivity(localIntent1);
          }
          else
          {
            Intent localIntent2 = new Intent(CategoryDetailActivity.this.mContext, SearchActivity.class);
            localIntent2.putExtra("QUERY", localCategory.getName());
            CategoryDetailActivity.this.startActivity(localIntent2);
          }
        }
      }
    });
    localListView.addFooterView(getFooter());
    getFooter().setVisibility(8);
    localListView.setAdapter(getAdapter());
    updateListView();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131427333, paramMenu);
    return super.onCreateOptionsMenu(paramMenu);
  }
  
  protected void onDestroy()
  {
    cancelAddListData();
    getListView().setOnItemClickListener(null);
    this.progressDialog = null;
    if (this.mFooter != null)
    {
      ProgressBar localProgressBar = (ProgressBar)this.mFooter.findViewById(2131492959);
      localProgressBar.setIndeterminateDrawable(null);
      localProgressBar.clearAnimation();
      this.mFooter = null;
    }
    super.onDestroy();
  }
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt3 > 1) && (paramInt3 == paramInt1 + paramInt2) && (getFooter().isShown())) {
      updateListView();
    }
  }
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {}
  
  protected void updateListView()
  {
    if (isTaskRunning()) {}
    for (;;)
    {
      return;
      if ((this.index == null) || (this.index.length() == 0))
      {
        if (this.category.getChildrenCount() > 0)
        {
          Category localCategory = new Category();
          localCategory.setName(this.category.getName());
          this.mList.add(localCategory);
        }
        this.mTask = new UpdateCategoryDirTask()
        {
          protected void onPostExecute(ArrayList<Category> paramAnonymousArrayList)
          {
            if (paramAnonymousArrayList != null) {
              CategoryDetailActivity.this.addListData(paramAnonymousArrayList, this.totalResults);
            }
            if ((CategoryDetailActivity.this.progressDialog != null) && (CategoryDetailActivity.this.progressDialog.isShowing())) {
              CategoryDetailActivity.this.progressDialog.dismiss();
            }
            super.onPostExecute(paramAnonymousArrayList);
          }
          
          protected void onPreExecute()
          {
            if (CategoryDetailActivity.this.page == 1)
            {
              CategoryDetailActivity.this.progressDialog = new ProgressDialog(CategoryDetailActivity.this);
              CategoryDetailActivity.this.progressDialog.setMessage(CategoryDetailActivity.this.getString(2131296481));
              CategoryDetailActivity.this.progressDialog.setProgressStyle(0);
              CategoryDetailActivity.this.progressDialog.show();
            }
            super.onPreExecute();
          }
        };
        UpdateCategoryDetailTask localUpdateCategoryDetailTask1 = this.mTask;
        String[] arrayOfString1 = new String[2];
        arrayOfString1[0] = this.category.getDir();
        arrayOfString1[1] = String.valueOf(this.category.getId());
        localUpdateCategoryDetailTask1.execute(arrayOfString1);
        setTitle(this.category.getName());
      }
      else
      {
        this.mTask = new UpdateCategoryDetailTask()
        {
          protected void onPostExecute(ArrayList<Category> paramAnonymousArrayList)
          {
            if (paramAnonymousArrayList != null) {
              CategoryDetailActivity.this.addListData(paramAnonymousArrayList, this.totalResults);
            }
            if ((CategoryDetailActivity.this.progressDialog != null) && (CategoryDetailActivity.this.progressDialog.isShowing())) {
              CategoryDetailActivity.this.progressDialog.dismiss();
            }
            super.onPostExecute(paramAnonymousArrayList);
          }
          
          protected void onPreExecute()
          {
            if (CategoryDetailActivity.this.page == 1)
            {
              CategoryDetailActivity.this.progressDialog = new ProgressDialog(CategoryDetailActivity.this);
              CategoryDetailActivity.this.progressDialog.setMessage(CategoryDetailActivity.this.getString(2131296481));
              CategoryDetailActivity.this.progressDialog.setProgressStyle(0);
              CategoryDetailActivity.this.progressDialog.show();
            }
            super.onPreExecute();
          }
        };
        UpdateCategoryDetailTask localUpdateCategoryDetailTask2 = this.mTask;
        String[] arrayOfString2 = new String[3];
        arrayOfString2[0] = this.category.getDir();
        arrayOfString2[1] = this.index;
        arrayOfString2[2] = String.valueOf(this.page);
        localUpdateCategoryDetailTask2.execute(arrayOfString2);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = this.category.getName();
        arrayOfObject[1] = this.index;
        setTitle(getString(2131296358, arrayOfObject));
      }
    }
  }
  
  static class CategoryDetailAdapter
    extends ArrayAdapter<Category>
  {
    private LayoutInflater layoutInflater_;
    
    public CategoryDetailAdapter(Context paramContext, int paramInt, List<Category> paramList)
    {
      super(paramInt, paramList);
      this.layoutInflater_ = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      Category localCategory = (Category)getItem(paramInt);
      if (paramView == null) {
        paramView = this.layoutInflater_.inflate(2130903085, null);
      }
      ((TextView)paramView.findViewById(2131492971)).setText(localCategory.getName());
      TextView localTextView = (TextView)paramView.findViewById(2131492973);
      if (localCategory.getChildrenCount() > 0)
      {
        localTextView.setText(String.valueOf(localCategory.getChildrenCount()));
        localTextView.setVisibility(0);
      }
      for (;;)
      {
        return paramView;
        localTextView.setVisibility(8);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.CategoryDetailActivity
 * JD-Core Version:    0.7.0.1
 */