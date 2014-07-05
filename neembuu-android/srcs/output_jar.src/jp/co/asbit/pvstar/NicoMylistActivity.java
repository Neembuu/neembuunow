package jp.co.asbit.pvstar;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import jp.co.asbit.pvstar.api.UpdateVideoResultTask;
import jp.co.asbit.pvstar.search.Search;
import jp.co.asbit.pvstar.search.SearchCondItem;
import jp.co.asbit.pvstar.search.SearchFactory;

public class NicoMylistActivity
  extends VideoListActivity
  implements AbsListView.OnScrollListener
{
  private View mFooter;
  private UpdateVideoResultTask mTask;
  private String mTitle;
  private String mylistId;
  private int page = 1;
  private int per_page = 10;
  private String preOrder;
  private ProgressDialog progressDialog;
  private Search search;
  
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
    this.search = SearchFactory.factory("niconico");
  }
  
  private boolean isTaskRunning()
  {
    if ((this.mTask != null) && (this.mTask.getStatus() == AsyncTask.Status.RUNNING)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  public void addListData(ArrayList<Video> paramArrayList, ArrayList<SearchCondItem> paramArrayList1)
  {
    ArrayList localArrayList = getList();
    if (this.page == 1)
    {
      localArrayList.clear();
      getAdapter().clear();
      this.search.setOrders(paramArrayList1);
    }
    ListView localListView = getListView();
    if (paramArrayList.size() > 0)
    {
      localArrayList.addAll(paramArrayList);
      this.page = (1 + this.page);
    }
    if (paramArrayList.size() == 0) {
      localListView.removeFooterView(getFooter());
    }
    for (;;)
    {
      getAdapter().notifyDataSetChanged();
      return;
      if (getListView().getFooterViewsCount() == 0) {
        localListView.addFooterView(getFooter());
      }
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mylistId = getIntent().getStringExtra("NICO_MYLIST_ID");
    getListView().setOnScrollListener(this);
    initPage();
    updateListView();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131427332, paramMenu);
    return super.onCreateOptionsMenu(paramMenu);
  }
  
  protected void onDestroy()
  {
    cancelAddListData();
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
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    }
    for (;;)
    {
      return super.onOptionsItemSelected(paramMenuItem);
      VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
      Playlist localPlaylist = new Playlist();
      localPlaylist.setId(this.mylistId);
      localPlaylist.setListType(1);
      localPlaylist.setSearchEngine("niconico");
      localPlaylist.setThumbnailUrl(((Video)getList().get(0)).getThumbnailUrl());
      localPlaylist.setTitle(this.mTitle);
      localPlaylist.setDescription(null);
      try
      {
        if (localVideoDbHelper.insertBookmark(localPlaylist)) {
          Toast.makeText(this.mContext, 2131296435, 0).show();
        }
        localVideoDbHelper.close();
      }
      catch (Resources.NotFoundException localNotFoundException)
      {
        for (;;)
        {
          localNotFoundException.printStackTrace();
        }
      }
      catch (VideoDbHelper.MaxBookmarkCountException localMaxBookmarkCountException)
      {
        for (;;)
        {
          localMaxBookmarkCountException.printStackTrace();
        }
      }
    }
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
    new AlertDialog.Builder(this).setTitle(2131296478).setSingleChoiceItems(this.search.getOrderNames(), this.search.getOrderIndex(this.search.getOrder()), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        NicoMylistActivity.this.preOrder = NicoMylistActivity.this.search.getOrderKey(paramAnonymousInt);
      }
    }).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        if ((NicoMylistActivity.this.preOrder != null) && (!NicoMylistActivity.this.preOrder.equals(NicoMylistActivity.this.search.getOrder())))
        {
          NicoMylistActivity.this.cancelAddListData();
          NicoMylistActivity.this.initPage();
          NicoMylistActivity.this.search.setOrder(NicoMylistActivity.this.preOrder);
          NicoMylistActivity.this.updateListView();
        }
      }
    }).setNegativeButton(2131296382, null).show();
  }
  
  protected void updateListView()
  {
    if (isTaskRunning()) {}
    for (;;)
    {
      return;
      this.mTask = new UpdateVideoResultTask()
      {
        protected void onCancelled()
        {
          if ((NicoMylistActivity.this.progressDialog != null) && (NicoMylistActivity.this.progressDialog.isShowing())) {
            NicoMylistActivity.this.progressDialog.dismiss();
          }
          super.onCancelled();
        }
        
        protected void onPostExecute(ArrayList<Video> paramAnonymousArrayList)
        {
          if (paramAnonymousArrayList != null)
          {
            NicoMylistActivity.this.mTitle = this.title;
            NicoMylistActivity.this.setTitle(this.title);
            NicoMylistActivity.this.addListData(paramAnonymousArrayList, this.orders);
          }
          if ((NicoMylistActivity.this.progressDialog != null) && (NicoMylistActivity.this.progressDialog.isShowing())) {
            NicoMylistActivity.this.progressDialog.dismiss();
          }
          super.onPostExecute(paramAnonymousArrayList);
        }
        
        public void onPreExecute()
        {
          if (NicoMylistActivity.this.page == 1)
          {
            NicoMylistActivity.this.progressDialog = new ProgressDialog(NicoMylistActivity.this);
            NicoMylistActivity.this.progressDialog.setMessage(NicoMylistActivity.this.getString(2131296481));
            NicoMylistActivity.this.progressDialog.setCancelable(true);
            NicoMylistActivity.this.progressDialog.setProgressStyle(0);
            NicoMylistActivity.this.progressDialog.show();
          }
          Uri.Builder localBuilder = new Uri.Builder();
          localBuilder.scheme("http");
          localBuilder.encodedAuthority("pvstar.dooga.org");
          localBuilder.path("/api2/nico_mylists/");
          localBuilder.appendQueryParameter("mylist_id", NicoMylistActivity.this.mylistId);
          localBuilder.appendQueryParameter("page", String.valueOf(NicoMylistActivity.this.page));
          localBuilder.appendQueryParameter("per_page", String.valueOf(NicoMylistActivity.this.per_page));
          if (NicoMylistActivity.this.search.getOrder() != null) {
            localBuilder.appendQueryParameter("order", String.valueOf(NicoMylistActivity.this.search.getOrder()));
          }
          this.uri = localBuilder.build().toString();
          super.onPreExecute();
        }
      };
      this.mTask.execute(new String[0]);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.NicoMylistActivity
 * JD-Core Version:    0.7.0.1
 */