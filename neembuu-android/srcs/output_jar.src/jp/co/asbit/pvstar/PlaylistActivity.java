package jp.co.asbit.pvstar;

import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import jp.co.asbit.pvstar.api.UpdateVideoResultTask;

public class PlaylistActivity
  extends VideoListActivity
  implements AbsListView.OnScrollListener
{
  private static final int MAX_SEARCH_RESULTS = 1000;
  private View mFooter;
  private UpdateVideoResultTask mTask;
  private int page = 1;
  private int per_page = 10;
  private Playlist playlist;
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
  
  public void addListData(ArrayList<Video> paramArrayList)
  {
    ArrayList localArrayList = getList();
    if (this.page == 1)
    {
      localArrayList.clear();
      getAdapter().clear();
      if (paramArrayList.size() == 0) {
        Toast.makeText(this.mContext, 2131296448, 1).show();
      }
    }
    ListView localListView = getListView();
    if (paramArrayList.size() > 0)
    {
      localArrayList.addAll(paramArrayList);
      this.page = (1 + this.page);
    }
    if ((getList().size() >= 1000) || (paramArrayList.size() == 0)) {
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
    if (localIntent.hasExtra("PLAYLIST")) {
      this.playlist = ((Playlist)localIntent.getSerializableExtra("PLAYLIST"));
    }
    this.sortButton.setImageResource(2130837572);
    getListView().setOnScrollListener(this);
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
      try
      {
        if (localVideoDbHelper.insertBookmark(this.playlist)) {
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
    Toast.makeText(this.mContext, 2131296394, 0).show();
  }
  
  protected void updateListView()
  {
    if (isTaskRunning()) {}
    for (;;)
    {
      return;
      setTitle(this.playlist.getTitle());
      this.mTask = new UpdateVideoResultTask()
      {
        protected void onCancelled()
        {
          if ((PlaylistActivity.this.progressDialog != null) && (PlaylistActivity.this.progressDialog.isShowing())) {
            PlaylistActivity.this.progressDialog.dismiss();
          }
          super.onCancelled();
        }
        
        protected void onPostExecute(ArrayList<Video> paramAnonymousArrayList)
        {
          if (paramAnonymousArrayList != null) {
            PlaylistActivity.this.addListData(paramAnonymousArrayList);
          }
          if ((PlaylistActivity.this.progressDialog != null) && (PlaylistActivity.this.progressDialog.isShowing())) {
            PlaylistActivity.this.progressDialog.dismiss();
          }
          super.onPostExecute(paramAnonymousArrayList);
        }
        
        public void onPreExecute()
        {
          if (PlaylistActivity.this.page == 1)
          {
            PlaylistActivity.this.progressDialog = new ProgressDialog(PlaylistActivity.this);
            PlaylistActivity.this.progressDialog.setMessage(PlaylistActivity.this.getString(2131296481));
            PlaylistActivity.this.progressDialog.setCancelable(true);
            PlaylistActivity.this.progressDialog.setProgressStyle(0);
            PlaylistActivity.this.progressDialog.show();
          }
          Uri.Builder localBuilder = new Uri.Builder();
          localBuilder.scheme("http");
          localBuilder.encodedAuthority("pvstar.dooga.org");
          localBuilder.path("/api2/playlists/details/");
          localBuilder.appendQueryParameter("playlist_id", PlaylistActivity.this.playlist.getId());
          localBuilder.appendQueryParameter("page", String.valueOf(PlaylistActivity.this.page));
          localBuilder.appendQueryParameter("per_page", String.valueOf(PlaylistActivity.this.per_page));
          this.uri = localBuilder.build().toString();
          super.onPreExecute();
        }
      };
      this.mTask.execute(new String[0]);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.PlaylistActivity
 * JD-Core Version:    0.7.0.1
 */