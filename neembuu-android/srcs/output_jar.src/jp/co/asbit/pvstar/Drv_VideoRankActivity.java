package jp.co.asbit.pvstar;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.concurrent.RejectedExecutionException;
import jp.co.asbit.pvstar.api.UpdateVideoResultTask;
import jp.co.asbit.pvstar.search.Drv_SearchTabs;
import jp.co.asbit.pvstar.search.Drv_SearchTabs.OnTabSelectedListener;
import jp.co.asbit.pvstar.search.Ranking;
import jp.co.asbit.pvstar.search.RankingFactory;
import jp.co.asbit.pvstar.search.SearchCondItem;

public class Drv_VideoRankActivity
  extends Drv_VideoListActivity
  implements AbsListView.OnScrollListener
{
  private static final int MAX_SEARCH_RESULTS = 100;
  protected static final int MULTI_SORT_DIALOG_ID = 1;
  protected static final int ORDER_SORT_DIALOG_ID = 2;
  private Drv_SearchTabs Drv_SearchTabs;
  private View mFooter;
  private int mPage = 1;
  private UpdateVideoResultTask mTask;
  private String mVideoRankKey = "youtube";
  private String[] mVideoRanksKeys;
  protected String preOrder;
  private CustomDialog progressDialog;
  private Ranking search;
  
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
    this.mPage = 1;
    clearSelectedRows();
    this.search = RankingFactory.factory(this.mVideoRankKey);
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
    label90:
    ListView localListView;
    if (this.mPage == i)
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
        if ((!this.search.choicesEnable()) || (this.mDriving)) {
          break label181;
        }
        this.mSortButton.setImageResource(2130837571);
      }
      localListView = getListView();
      if (paramArrayList.size() > 0)
      {
        localArrayList.addAll(paramArrayList);
        this.mPage = (1 + this.mPage);
      }
      if (paramInt > 100) {
        paramInt = 100;
      }
      if ((getList().size() < paramInt) && (paramArrayList.size() != 0)) {
        break label193;
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
      label181:
      this.mSortButton.setImageResource(2130837572);
      break label90;
      label193:
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
        Drv_VideoRankActivity.this.cancelAddListData();
        Drv_VideoRankActivity.this.initPage();
        Drv_VideoRankActivity.this.search.setOrder(((SearchCondItem)localSpinner1.getSelectedItem()).key);
        Drv_VideoRankActivity.this.search.setPeriod(((SearchCondItem)localSpinner2.getSelectedItem()).key);
        Drv_VideoRankActivity.this.search.setCategory(((SearchCondItem)localSpinner3.getSelectedItem()).key);
        Drv_VideoRankActivity.this.updateListView();
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
          Drv_VideoRankActivity.this.preOrder = Drv_VideoRankActivity.this.search.getOrderKey(paramAnonymousInt);
        }
      }).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          if ((Drv_VideoRankActivity.this.preOrder != null) && (!Drv_VideoRankActivity.this.preOrder.equals(Drv_VideoRankActivity.this.search.getOrder())))
          {
            Drv_VideoRankActivity.this.cancelAddListData();
            Drv_VideoRankActivity.this.initPage();
            Drv_VideoRankActivity.this.search.setOrder(Drv_VideoRankActivity.this.preOrder);
            Drv_VideoRankActivity.this.updateListView();
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
  
  protected void onChangeVehicleState(int paramInt)
  {
    super.onChangeVehicleState(paramInt);
    switch (paramInt)
    {
    default: 
      if (this.search.choicesEnable()) {
        this.mSortButton.setImageResource(2130837571);
      }
      this.mSortButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Drv_VideoRankActivity.this.sortDialog();
        }
      });
    }
    for (;;)
    {
      return;
      this.mSortButton.setImageResource(2130837572);
      this.mSortButton.setOnClickListener(null);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mVideoRanksKeys = getResources().getStringArray(2131099650);
    LinearLayout localLinearLayout = (LinearLayout)findViewById(2131492947);
    this.Drv_SearchTabs = new Drv_SearchTabs(this.mContext, this.mVideoRanksKeys);
    this.Drv_SearchTabs.setOnTabSelectedListener(new Drv_SearchTabs.OnTabSelectedListener()
    {
      public void onTabSelected(View paramAnonymousView1, View paramAnonymousView2)
      {
        Drv_VideoRankActivity.this.cancelAddListData();
        Drv_VideoRankActivity.this.mVideoRankKey = ((String)paramAnonymousView1.getTag());
        Drv_VideoRankActivity.this.initPage();
        Drv_VideoRankActivity.this.updateListView();
      }
    });
    int i = 0;
    if (i >= this.mVideoRanksKeys.length)
    {
      findViewById(2131492946).setVisibility(0);
      setTitle(getString(2131296401));
      getListView().setOnScrollListener(this);
      this.Drv_SearchTabs.selectTab(this.mVideoRankKey);
      return;
    }
    if (this.mVideoRanksKeys[i].equals("appli")) {}
    for (;;)
    {
      i++;
      break;
      localLinearLayout.addView(this.Drv_SearchTabs.createTab(this.mVideoRanksKeys[i]));
      localLinearLayout.addView(this.Drv_SearchTabs.createDivider());
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
    this.Drv_SearchTabs.setOnTabSelectedListener(null);
    this.Drv_SearchTabs = null;
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
    if (this.search.choicesEnable())
    {
      if (!this.mVideoRankKey.equals("youtube")) {
        break label28;
      }
      showDialog(2);
    }
    for (;;)
    {
      return;
      label28:
      if (this.mVideoRankKey.equals("niconico")) {
        showDialog(1);
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
          if (Drv_VideoRankActivity.this.mVideoRankKey.equals("niconico")) {
            Drv_VideoRankActivity.this.search.loadVariables();
          }
          return super.doInBackground(paramAnonymousVarArgs);
        }
        
        protected void onCancelled()
        {
          if ((Drv_VideoRankActivity.this.progressDialog != null) && (Drv_VideoRankActivity.this.progressDialog.isShowing())) {
            Drv_VideoRankActivity.this.progressDialog.dismiss();
          }
          super.onCancelled();
        }
        
        protected void onPostExecute(ArrayList<Video> paramAnonymousArrayList)
        {
          if (paramAnonymousArrayList != null) {
            Drv_VideoRankActivity.this.addListData(paramAnonymousArrayList, this.totalReuslts, this.orders, this.title);
          }
          if ((Drv_VideoRankActivity.this.progressDialog != null) && (Drv_VideoRankActivity.this.progressDialog.isShowing())) {
            Drv_VideoRankActivity.this.progressDialog.dismiss();
          }
          super.onPostExecute(paramAnonymousArrayList);
        }
        
        protected void onPreExecute()
        {
          if (Drv_VideoRankActivity.this.mPage == 1)
          {
            Drv_VideoRankActivity.this.progressDialog = new CustomDialog(Drv_VideoRankActivity.this);
            Drv_VideoRankActivity.this.progressDialog.requestWindowFeature(1);
            Drv_VideoRankActivity.this.progressDialog.setContentView(2130903073);
            Drv_VideoRankActivity.this.progressDialog.getWindow().setFlags(0, 2);
            Drv_VideoRankActivity.this.progressDialog.setCancelable(true);
            Drv_VideoRankActivity.this.progressDialog.setCancelable(true);
            Drv_VideoRankActivity.this.progressDialog.show();
          }
          this.uri = Drv_VideoRankActivity.this.search.getUrl(Drv_VideoRankActivity.this.mPage);
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
 * Qualified Name:     jp.co.asbit.pvstar.Drv_VideoRankActivity
 * JD-Core Version:    0.7.0.1
 */