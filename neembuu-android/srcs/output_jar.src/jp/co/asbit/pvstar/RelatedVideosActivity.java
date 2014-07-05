package jp.co.asbit.pvstar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import java.util.ArrayList;
import jp.co.asbit.pvstar.api.UpdateVideoResultTask;

public class RelatedVideosActivity
  extends VideoListActivity
  implements AbsListView.OnScrollListener
{
  private static final int MAX_SEARCH_RESULTS = 200;
  private View mFooter;
  private UpdateVideoResultTask mTask;
  private int page = 1;
  private ProgressDialog progressDialog;
  private Video video;
  
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
  
  public void addListData(ArrayList<Video> paramArrayList, int paramInt)
  {
    ArrayList localArrayList = getList();
    if (this.page == 1)
    {
      localArrayList.clear();
      getAdapter().clear();
    }
    ListView localListView = getListView();
    if (paramArrayList.size() > 0)
    {
      localArrayList.addAll(paramArrayList);
      this.page = (1 + this.page);
    }
    if (paramInt > 200) {
      paramInt = 200;
    }
    if ((getList().size() >= paramInt) || (paramArrayList.size() == 0)) {
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
    Intent localIntent = getIntent();
    if (localIntent.hasExtra("VIDEO")) {
      this.video = ((Video)localIntent.getSerializableExtra("VIDEO"));
    }
    this.sortButton.setImageResource(2130837572);
    getListView().setOnScrollListener(this);
    updateListView();
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
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt3 > 1) && (paramInt3 == paramInt1 + paramInt2) && (getFooter().isShown())) {
      updateListView();
    }
  }
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {}
  
  protected void sortDialog()
  {
    Toast.makeText(this.mContext, 2131296394, 0).show();
  }
  
  protected void updateListView()
  {
    if (isTaskRunning()) {}
    for (;;)
    {
      return;
      setTitle(getString(2131296444));
      this.mTask = new UpdateVideoResultTask()
      {
        protected void onCancelled()
        {
          if ((RelatedVideosActivity.this.progressDialog != null) && (RelatedVideosActivity.this.progressDialog.isShowing())) {
            RelatedVideosActivity.this.progressDialog.dismiss();
          }
          super.onCancelled();
        }
        
        protected void onPostExecute(ArrayList<Video> paramAnonymousArrayList)
        {
          if (paramAnonymousArrayList != null) {
            RelatedVideosActivity.this.addListData(paramAnonymousArrayList, this.totalReuslts);
          }
          if ((RelatedVideosActivity.this.progressDialog != null) && (RelatedVideosActivity.this.progressDialog.isShowing())) {
            RelatedVideosActivity.this.progressDialog.dismiss();
          }
          super.onPostExecute(paramAnonymousArrayList);
        }
        
        public void onPreExecute()
        {
          if (RelatedVideosActivity.this.page == 1)
          {
            RelatedVideosActivity.this.progressDialog = new ProgressDialog(RelatedVideosActivity.this);
            RelatedVideosActivity.this.progressDialog.setMessage(RelatedVideosActivity.this.getString(2131296481));
            RelatedVideosActivity.this.progressDialog.setCancelable(true);
            RelatedVideosActivity.this.progressDialog.setProgressStyle(0);
            RelatedVideosActivity.this.progressDialog.show();
          }
          Uri.Builder localBuilder = new Uri.Builder();
          localBuilder.scheme("http");
          localBuilder.encodedAuthority("pvstar.dooga.org");
          localBuilder.path("/api2/relates/relates_" + RelatedVideosActivity.this.video.getSearchEngine() + "/index/" + RelatedVideosActivity.this.video.getId());
          localBuilder.appendQueryParameter("page", String.valueOf(RelatedVideosActivity.this.page));
          this.uri = localBuilder.build().toString();
          super.onPreExecute();
        }
      };
      this.mTask.execute(new String[0]);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.RelatedVideosActivity
 * JD-Core Version:    0.7.0.1
 */