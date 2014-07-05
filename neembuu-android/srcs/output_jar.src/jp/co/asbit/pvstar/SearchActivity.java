package jp.co.asbit.pvstar;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SearchRecentSuggestions;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.concurrent.RejectedExecutionException;
import jp.co.asbit.pvstar.api.UpdateVideoResultTask;
import jp.co.asbit.pvstar.search.Search;
import jp.co.asbit.pvstar.search.SearchCondItem;
import jp.co.asbit.pvstar.search.SearchFactory;
import jp.co.asbit.pvstar.search.SearchTabs;
import jp.co.asbit.pvstar.search.SearchTabs.OnTabSelectedListener;

public class SearchActivity
  extends VideoListActivity
  implements AbsListView.OnScrollListener
{
  private static final int MAX_SEARCH_RESULTS = 200;
  private boolean adult_thru;
  private View mFooter;
  private UpdateVideoResultTask mTask;
  private boolean niconico_search_method;
  private int page = 1;
  private int per_page = 10;
  protected String preOrder;
  private ProgressDialog progressDialog;
  private String query;
  private Search search;
  private SearchTabs searchTabs;
  private String search_engine_key;
  
  private void cancelAddListData()
  {
    if (isTaskRunning().booleanValue()) {
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
  
  private void initPage()
  {
    this.page = 1;
    clearSelectedRows();
    this.search = SearchFactory.factory(this.search_engine_key);
  }
  
  private Boolean isTaskRunning()
  {
    if ((this.mTask != null) && (this.mTask.getStatus() == AsyncTask.Status.RUNNING)) {}
    for (Boolean localBoolean = Boolean.valueOf(true);; localBoolean = Boolean.valueOf(false)) {
      return localBoolean;
    }
  }
  
  public void addListData(ArrayList<Video> paramArrayList, int paramInt, ArrayList<SearchCondItem> paramArrayList1)
  {
    ArrayList localArrayList = getList();
    int i;
    ListView localListView;
    if (this.page == 1)
    {
      i = 1;
      if (i != 0)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.query;
        setTitle(getString(2131296441, arrayOfObject));
        localArrayList.clear();
        getAdapter().clear();
        this.search.setOrders(paramArrayList1);
      }
      localListView = getListView();
      if (paramArrayList.size() > 0)
      {
        localArrayList.addAll(paramArrayList);
        this.page = (1 + this.page);
      }
      if (paramInt > 200) {
        paramInt = 200;
      }
      if ((getList().size() < paramInt) && (paramArrayList.size() != 0)) {
        break label161;
      }
      localListView.removeFooterView(getFooter());
    }
    for (;;)
    {
      if (i != 0) {
        localListView.setSelectionAfterHeaderView();
      }
      getAdapter().notifyDataSetChanged();
      return;
      i = 0;
      break;
      label161:
      if (getListView().getFooterViewsCount() == 0) {
        localListView.addFooterView(getFooter());
      }
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setTitle(getString(2131296403));
    String[] arrayOfString1 = getResources().getStringArray(2131099649);
    String[] arrayOfString2 = new String[1 + arrayOfString1.length];
    int i = 0;
    LinearLayout localLinearLayout;
    int j;
    if (i >= arrayOfString1.length)
    {
      arrayOfString2[arrayOfString1.length] = "imacon";
      localLinearLayout = (LinearLayout)findViewById(2131492947);
      this.searchTabs = new SearchTabs(this.mContext, arrayOfString2);
      this.searchTabs.setOnTabSelectedListener(new SearchTabs.OnTabSelectedListener()
      {
        public void onTabSelected(View paramAnonymousView1, View paramAnonymousView2)
        {
          SearchActivity.this.search_engine_key = ((String)paramAnonymousView1.getTag());
          if (SearchActivity.this.search_engine_key.equals("imacon"))
          {
            SearchActivity.this.searchTabs.changeTabState(paramAnonymousView1, false);
            SearchActivity.this.searchTabs.changeTabState(paramAnonymousView2, true);
            SearchActivity.this.searchTabs.currentTab = ((ImageView)paramAnonymousView2);
            new AlertDialog.Builder(SearchActivity.this).setTitle(2131296436).setMessage(2131296437).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
              {
                Uri.Builder localBuilder = new Uri.Builder();
                localBuilder.scheme("http");
                localBuilder.encodedAuthority("sp.image.dooga.org");
                localBuilder.path("/searches/");
                if (SearchActivity.this.query != null) {
                  localBuilder.appendQueryParameter("q", SearchActivity.this.query);
                }
                Intent localIntent = new Intent("android.intent.action.VIEW", localBuilder.build());
                SearchActivity.this.startActivity(localIntent);
              }
            }).setNegativeButton(2131296382, null).show();
          }
          for (;;)
          {
            return;
            SearchActivity.this.cancelAddListData();
            SearchActivity.this.initPage();
            SearchActivity.this.updateListView();
          }
        }
      });
      j = 0;
      label95:
      if (j < arrayOfString2.length) {
        break label259;
      }
      findViewById(2131492946).setVisibility(0);
      SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
      if (this.search_engine_key == null) {
        this.search_engine_key = localSharedPreferences.getString("default_search_engine", "youtube");
      }
      this.niconico_search_method = localSharedPreferences.getBoolean("niconico_search_method", true);
      this.adult_thru = localSharedPreferences.getBoolean("adult_thru", false);
      if (this.query == null)
      {
        if (!"android.intent.action.SEARCH".equals(getIntent().getAction())) {
          break label293;
        }
        this.query = getIntent().getStringExtra("query");
        label213:
        if (this.query != null) {
          break label310;
        }
        onSearchRequested();
      }
    }
    for (;;)
    {
      getListView().setOnScrollListener(this);
      this.searchTabs.selectTab(this.search_engine_key);
      return;
      arrayOfString2[i] = arrayOfString1[i];
      i++;
      break;
      label259:
      localLinearLayout.addView(this.searchTabs.createTab(arrayOfString2[j]));
      localLinearLayout.addView(this.searchTabs.createDivider());
      j++;
      break label95;
      label293:
      this.query = getIntent().getStringExtra("QUERY");
      break label213;
      label310:
      new SearchRecentSuggestions(getApplicationContext(), "jp.co.asbit.pvstar.MySuggestionProvider", 1).saveRecentQuery(this.query, null);
    }
  }
  
  protected void onDestroy()
  {
    cancelAddListData();
    SearchFactory.clear();
    this.searchTabs.setOnTabSelectedListener(null);
    this.searchTabs = null;
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
  
  protected void onNewIntent(Intent paramIntent)
  {
    setIntent(paramIntent);
    if ("android.intent.action.SEARCH".equals(paramIntent.getAction()))
    {
      this.query = paramIntent.getStringExtra("query");
      new SearchRecentSuggestions(getApplicationContext(), "jp.co.asbit.pvstar.MySuggestionProvider", 1).saveRecentQuery(this.query, null);
      initPage();
      updateListView();
    }
  }
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt3 > 1) && (paramInt3 == paramInt1 + paramInt2) && (getFooter().isShown())) {
      updateListView();
    }
  }
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {}
  
  public boolean onSearchRequested()
  {
    cancelAddListData();
    startSearch(this.query, false, null, false);
    return true;
  }
  
  protected void sortDialog()
  {
    if (this.search.choicesEnable()) {
      new AlertDialog.Builder(this).setTitle(2131296478).setSingleChoiceItems(this.search.getOrderNames(), this.search.getOrderIndex(this.search.getOrder()), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          SearchActivity.this.preOrder = SearchActivity.this.search.getOrderKey(paramAnonymousInt);
        }
      }).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          if ((SearchActivity.this.preOrder != null) && (!SearchActivity.this.preOrder.equals(SearchActivity.this.search.getOrder())))
          {
            SearchActivity.this.cancelAddListData();
            SearchActivity.this.initPage();
            SearchActivity.this.search.setOrder(SearchActivity.this.preOrder);
            SearchActivity.this.updateListView();
          }
        }
      }).setNegativeButton(2131296382, null).show();
    }
  }
  
  protected void updateListView()
  {
    if (this.query == null) {}
    for (;;)
    {
      return;
      if (!isTaskRunning().booleanValue())
      {
        this.mTask = new UpdateVideoResultTask()
        {
          protected void onCancelled()
          {
            if ((SearchActivity.this.progressDialog != null) && (SearchActivity.this.progressDialog.isShowing())) {}
            try
            {
              SearchActivity.this.progressDialog.dismiss();
              super.onCancelled();
              return;
            }
            catch (IllegalArgumentException localIllegalArgumentException)
            {
              for (;;)
              {
                localIllegalArgumentException.printStackTrace();
              }
            }
          }
          
          protected void onPostExecute(ArrayList<Video> paramAnonymousArrayList)
          {
            if (paramAnonymousArrayList != null) {
              SearchActivity.this.addListData(paramAnonymousArrayList, this.totalReuslts, this.orders);
            }
            if ((SearchActivity.this.progressDialog != null) && (SearchActivity.this.progressDialog.isShowing())) {}
            try
            {
              SearchActivity.this.progressDialog.dismiss();
              super.onPostExecute(paramAnonymousArrayList);
              return;
            }
            catch (IllegalArgumentException localIllegalArgumentException)
            {
              for (;;)
              {
                localIllegalArgumentException.printStackTrace();
              }
            }
          }
          
          public void onPreExecute()
          {
            if (SearchActivity.this.page == 1)
            {
              SearchActivity.this.progressDialog = new ProgressDialog(SearchActivity.this);
              SearchActivity.this.progressDialog.setMessage(SearchActivity.this.getString(2131296481));
              SearchActivity.this.progressDialog.setCancelable(true);
              SearchActivity.this.progressDialog.setProgressStyle(0);
              SearchActivity.this.progressDialog.show();
            }
            this.uri = SearchActivity.this.search.getUrl(SearchActivity.this.search_engine_key, SearchActivity.this.query, SearchActivity.this.page, SearchActivity.this.per_page, SearchActivity.this.adult_thru);
            StringBuilder localStringBuilder;
            if (SearchActivity.this.search_engine_key.equals("niconico"))
            {
              localStringBuilder = new StringBuilder(String.valueOf(this.uri)).append("&type=");
              if (!SearchActivity.this.niconico_search_method) {
                break label194;
              }
            }
            label194:
            for (String str = "0";; str = "1")
            {
              this.uri = str;
              super.onPreExecute();
              return;
            }
          }
        };
        try
        {
          this.mTask.execute(new String[0]);
        }
        catch (RejectedExecutionException localRejectedExecutionException)
        {
          localRejectedExecutionException.printStackTrace();
        }
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.SearchActivity
 * JD-Core Version:    0.7.0.1
 */