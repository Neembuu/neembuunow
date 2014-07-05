package jp.co.asbit.pvstar;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.amoad.amoadsdk.AMoAdSdkWallActivity;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import jp.co.asbit.pvstar.api.UpdateVideoResultTask;
import jp.co.asbit.pvstar.search.Ranking;
import jp.co.asbit.pvstar.search.RankingFactory;
import jp.co.asbit.pvstar.search.SearchCondItem;
import jp.co.asbit.pvstar.search.SearchTabs;
import jp.co.asbit.pvstar.search.SearchTabs.OnTabSelectedListener;
import jp.tjkapp.adfurikunsdk.AdfurikunWallAd;

public class VideoRankActivity
  extends VideoListActivity
  implements AbsListView.OnScrollListener
{
  private static final int MAX_SEARCH_RESULTS = 100;
  protected static final int MULTI_SORT_DIALOG_ID = 1;
  protected static final int ORDER_SORT_DIALOG_ID = 2;
  private View mFooter;
  private UpdateVideoResultTask mTask;
  protected int page = 1;
  protected int per_page = 10;
  protected String preOrder;
  private ProgressDialog progressDialog;
  private Ranking search;
  private SearchTabs searchTabs;
  private String video_rank_key = "youtube";
  protected String[] video_ranks_keys;
  
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
  
  private void initPage()
  {
    this.page = 1;
    clearSelectedRows();
    this.search = RankingFactory.factory(this.video_rank_key);
  }
  
  private boolean isTaskRunning()
  {
    if ((this.mTask != null) && (this.mTask.getStatus() == AsyncTask.Status.RUNNING)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public void addListData(ArrayList<Video> paramArrayList, int paramInt, ArrayList<SearchCondItem> paramArrayList1, String paramString)
  {
    int i = 1;
    ArrayList localArrayList = getList();
    label83:
    ListView localListView;
    if (this.page == i)
    {
      if (i != 0)
      {
        if (paramString != null) {
          setTitle(paramString);
        }
        localArrayList.clear();
        getAdapter().clear();
        if (!this.search.choicesEnable()) {
          this.search.setOrders(paramArrayList1);
        }
        if (!this.search.choicesEnable()) {
          break label174;
        }
        this.sortButton.setImageResource(2130837571);
      }
      localListView = getListView();
      if (paramArrayList.size() > 0)
      {
        localArrayList.addAll(paramArrayList);
        this.page = (1 + this.page);
      }
      if (paramInt > 100) {
        paramInt = 100;
      }
      if ((getList().size() < paramInt) && (paramArrayList.size() != 0)) {
        break label186;
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
      label174:
      this.sortButton.setImageResource(2130837572);
      break label83;
      label186:
      if (getListView().getFooterViewsCount() == 0) {
        localListView.addFooterView(getFooter());
      }
    }
  }
  
  protected Dialog createMultiSortDialog()
  {
    View localView = getLayoutInflater().inflate(2130903051, null);
    ArrayAdapter localArrayAdapter1 = new ArrayAdapter(this.mContext, 17367048, this.search.getOrders());
    localArrayAdapter1.setDropDownViewResource(17367049);
    final Spinner localSpinner1 = (Spinner)localView.findViewById(2131492898);
    localSpinner1.setAdapter(localArrayAdapter1);
    localSpinner1.setSelection(this.search.getOrderIndex(this.search.getOrder()));
    localArrayAdapter1.notifyDataSetChanged();
    ArrayAdapter localArrayAdapter2 = new ArrayAdapter(this.mContext, 17367048, this.search.getPeriods());
    localArrayAdapter2.setDropDownViewResource(17367049);
    final Spinner localSpinner2 = (Spinner)localView.findViewById(2131492899);
    localSpinner2.setAdapter(localArrayAdapter2);
    localSpinner2.setSelection(this.search.getPeriodIndex(this.search.getPeriod()));
    localArrayAdapter2.notifyDataSetChanged();
    ArrayAdapter localArrayAdapter3 = new ArrayAdapter(this.mContext, 17367048, this.search.getCategories());
    localArrayAdapter3.setDropDownViewResource(17367049);
    final Spinner localSpinner3 = (Spinner)localView.findViewById(2131492897);
    localSpinner3.setAdapter(localArrayAdapter3);
    localSpinner3.setSelection(this.search.getCategoryIndex(this.search.getCategory()));
    localArrayAdapter3.notifyDataSetChanged();
    new AlertDialog.Builder(this).setTitle(2131296478).setView(localView).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        VideoRankActivity.this.cancelAddListData();
        VideoRankActivity.this.initPage();
        VideoRankActivity.this.search.setOrder(((SearchCondItem)localSpinner1.getSelectedItem()).key);
        VideoRankActivity.this.search.setPeriod(((SearchCondItem)localSpinner2.getSelectedItem()).key);
        VideoRankActivity.this.search.setCategory(((SearchCondItem)localSpinner3.getSelectedItem()).key);
        VideoRankActivity.this.updateListView();
      }
    }).setNegativeButton(2131296382, null).create();
  }
  
  protected Dialog createOrderSortDialog()
  {
    Object localObject = null;
    try
    {
      AlertDialog localAlertDialog = new AlertDialog.Builder(this).setTitle(2131296478).setSingleChoiceItems(this.search.getOrderNames(), this.search.getOrderIndex(this.search.getOrder()), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          VideoRankActivity.this.preOrder = VideoRankActivity.this.search.getOrderKey(paramAnonymousInt);
        }
      }).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          if ((VideoRankActivity.this.preOrder != null) && (!VideoRankActivity.this.preOrder.equals(VideoRankActivity.this.search.getOrder())))
          {
            VideoRankActivity.this.cancelAddListData();
            VideoRankActivity.this.initPage();
            VideoRankActivity.this.search.setOrder(VideoRankActivity.this.preOrder);
            VideoRankActivity.this.updateListView();
          }
        }
      }).setNegativeButton(2131296382, null).create();
      localObject = localAlertDialog;
    }
    catch (NullPointerException localNullPointerException)
    {
      label75:
      break label75;
    }
    return localObject;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.video_ranks_keys = getResources().getStringArray(2131099650);
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131492947);
    this.searchTabs = new SearchTabs(this.mContext, this.video_ranks_keys);
    this.searchTabs.setOnTabSelectedListener(new SearchTabs.OnTabSelectedListener()
    {
      public void onTabSelected(View paramAnonymousView1, View paramAnonymousView2)
      {
        VideoRankActivity.this.cancelAddListData();
        VideoRankActivity.this.video_rank_key = ((String)paramAnonymousView1.getTag());
        VideoRankActivity.this.initPage();
        VideoRankActivity.this.updateListView();
      }
    });
    for (int i = 0;; i++)
    {
      if (i >= this.video_ranks_keys.length)
      {
        findViewById(2131492946).setVisibility(0);
        setTitle(getString(2131296401));
        getListView().setOnScrollListener(this);
        this.searchTabs.selectTab(this.video_rank_key);
        this.searchTabs.setOnTabSelectedListener(new SearchTabs.OnTabSelectedListener()
        {
          public void onTabSelected(View paramAnonymousView1, View paramAnonymousView2)
          {
            VideoRankActivity.this.video_rank_key = ((String)paramAnonymousView1.getTag());
            if (VideoRankActivity.this.video_rank_key.equals("appli"))
            {
              VideoRankActivity.this.searchTabs.changeTabState(paramAnonymousView1, false);
              VideoRankActivity.this.searchTabs.changeTabState(paramAnonymousView2, true);
              VideoRankActivity.this.searchTabs.currentTab = ((ImageView)paramAnonymousView2);
              if (Locale.JAPAN.equals(Locale.getDefault())) {
                VideoRankActivity.this.startActivity(new Intent(VideoRankActivity.this.mContext, AMoAdSdkWallActivity.class));
              }
            }
            for (;;)
            {
              return;
              AdfurikunWallAd.initializeWallAdSetting(VideoRankActivity.this, "5315c44ebb323cc86b00000a");
              AdfurikunWallAd.showWallAd(VideoRankActivity.this, null);
              continue;
              VideoRankActivity.this.cancelAddListData();
              VideoRankActivity.this.initPage();
              VideoRankActivity.this.updateListView();
            }
          }
        });
        return;
      }
      localLinearLayout.addView(this.searchTabs.createTab(this.video_ranks_keys[i]));
      localLinearLayout.addView(this.searchTabs.createDivider());
    }
  }
  
  protected Dialog onCreateDialog(int paramInt)
  {
    Dialog localDialog = super.onCreateDialog(paramInt);
    switch (paramInt)
    {
    }
    for (;;)
    {
      return localDialog;
      localDialog = createMultiSortDialog();
      continue;
      localDialog = createOrderSortDialog();
    }
  }
  
  protected void onDestroy()
  {
    cancelAddListData();
    RankingFactory.clear();
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
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt3 > 1) && (paramInt3 == paramInt1 + paramInt2) && (getFooter().isShown())) {
      updateListView();
    }
  }
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {}
  
  protected void sortDialog()
  {
    if (this.search.choicesEnable()) {
      if (this.video_rank_key.equals("youtube")) {
        showDialog(2);
      }
    }
    for (;;)
    {
      return;
      if (this.video_rank_key.equals("niconico"))
      {
        showDialog(1);
        continue;
        Toast.makeText(this.mContext, 2131296394, 0).show();
      }
    }
  }
  
  protected void updateListView()
  {
    if (isTaskRunning()) {}
    for (;;)
    {
      return;
      this.mTask = new UpdateVideoResultTask()
      {
        protected ArrayList<Video> doInBackground(String... paramAnonymousVarArgs)
        {
          if (VideoRankActivity.this.video_rank_key.equals("niconico")) {
            VideoRankActivity.this.search.loadVariables();
          }
          return super.doInBackground(paramAnonymousVarArgs);
        }
        
        protected void onCancelled()
        {
          if ((VideoRankActivity.this.progressDialog != null) && (VideoRankActivity.this.progressDialog.isShowing())) {
            VideoRankActivity.this.progressDialog.dismiss();
          }
          super.onCancelled();
        }
        
        protected void onPostExecute(ArrayList<Video> paramAnonymousArrayList)
        {
          if (paramAnonymousArrayList != null) {
            VideoRankActivity.this.addListData(paramAnonymousArrayList, this.totalReuslts, this.orders, this.title);
          }
          if ((VideoRankActivity.this.progressDialog != null) && (VideoRankActivity.this.progressDialog.isShowing())) {
            VideoRankActivity.this.progressDialog.dismiss();
          }
          super.onPostExecute(paramAnonymousArrayList);
        }
        
        protected void onPreExecute()
        {
          if (VideoRankActivity.this.page == 1)
          {
            VideoRankActivity.this.progressDialog = new ProgressDialog(VideoRankActivity.this);
            VideoRankActivity.this.progressDialog.setMessage(VideoRankActivity.this.getString(2131296481));
            VideoRankActivity.this.progressDialog.setCancelable(true);
            VideoRankActivity.this.progressDialog.setProgressStyle(0);
            VideoRankActivity.this.progressDialog.show();
          }
          this.uri = VideoRankActivity.this.search.getUrl(VideoRankActivity.this.page);
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


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.VideoRankActivity
 * JD-Core Version:    0.7.0.1
 */