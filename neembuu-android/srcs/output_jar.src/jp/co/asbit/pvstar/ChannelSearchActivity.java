package jp.co.asbit.pvstar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import jp.co.asbit.pvstar.api.UpdateSearchChannelTask;

public class ChannelSearchActivity
  extends BaseActivity
  implements AbsListView.OnScrollListener
{
  private static final int MAX_SEARCH_RESULTS = 200;
  private ChannelRowAdapter mAdapter;
  private View mFooter;
  private ArrayList<Playlist> mList;
  private ListView mListView;
  private UpdateSearchChannelTask mTask;
  private int page = 1;
  private int per_page = 12;
  private ProgressDialog progressDialog;
  private String query;
  
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
    getListView().setSelection(0);
  }
  
  private boolean isTaskRunning()
  {
    if ((this.mTask != null) && (this.mTask.getStatus() == AsyncTask.Status.RUNNING)) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  private void updateListView()
  {
    if ((this.query == null) || (isTaskRunning())) {}
    for (;;)
    {
      return;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.query;
      setTitle(getString(2131296443, arrayOfObject));
      this.mTask = new UpdateSearchChannelTask(this.query, this.page, this.per_page)
      {
        protected void onPostExecute(ArrayList<Playlist> paramAnonymousArrayList)
        {
          if (paramAnonymousArrayList != null) {
            ChannelSearchActivity.this.addListData(paramAnonymousArrayList, this.totalResults);
          }
          if ((ChannelSearchActivity.this.progressDialog != null) && (ChannelSearchActivity.this.progressDialog.isShowing())) {
            ChannelSearchActivity.this.progressDialog.dismiss();
          }
          super.onPostExecute(paramAnonymousArrayList);
        }
        
        protected void onPreExecute()
        {
          if (ChannelSearchActivity.this.page == 1)
          {
            ChannelSearchActivity.this.progressDialog = new ProgressDialog(ChannelSearchActivity.this);
            ChannelSearchActivity.this.progressDialog.setMessage(ChannelSearchActivity.this.getString(2131296481));
            ChannelSearchActivity.this.progressDialog.setProgressStyle(0);
            ChannelSearchActivity.this.progressDialog.show();
          }
          super.onPreExecute();
        }
      };
      this.mTask.execute(new URL[0]);
    }
  }
  
  public void addListData(ArrayList<Playlist> paramArrayList, int paramInt)
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
  
  protected ChannelRowAdapter getAdapter()
  {
    if (this.mAdapter == null) {
      this.mAdapter = new ChannelRowAdapter(this.mContext, 0, getList());
    }
    return this.mAdapter;
  }
  
  protected ArrayList<Playlist> getList()
  {
    if (this.mList == null) {
      this.mList = new ArrayList();
    }
    return this.mList;
  }
  
  protected ListView getListView()
  {
    if (this.mListView == null) {
      this.mListView = ((ListView)findViewById(2131492880));
    }
    return this.mListView;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentAndTitle(2130903046, 2130903086);
    setTitle(getString(2131296407));
    ListView localListView = getListView();
    localListView.addFooterView(getFooter());
    localListView.setAdapter(getAdapter());
    localListView.setOnScrollListener(this);
    localListView.removeFooterView(getFooter());
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        Playlist localPlaylist = (Playlist)((Adapter)paramAnonymousAdapterView.getAdapter()).getItem(paramAnonymousInt);
        if (localPlaylist != null)
        {
          Intent localIntent = new Intent(ChannelSearchActivity.this.mContext, UserVideosActivity.class);
          localIntent.putExtra("PLAYLIST", localPlaylist);
          ChannelSearchActivity.this.startActivity(localIntent);
        }
      }
    });
    if (this.query == null) {
      onSearchRequested();
    }
    updateListView();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131427333, paramMenu);
    return super.onCreateOptionsMenu(paramMenu);
  }
  
  protected void onDestroy()
  {
    getListView().setOnItemClickListener(null);
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
  
  protected void onNewIntent(Intent paramIntent)
  {
    setIntent(paramIntent);
    if ("android.intent.action.SEARCH".equals(paramIntent.getAction()))
    {
      this.query = paramIntent.getStringExtra("query");
      new SearchRecentSuggestions(this.mContext, "jp.co.asbit.pvstar.MySuggestionProvider", 1).saveRecentQuery(this.query, null);
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
  
  protected void onStop()
  {
    getAdapter().clearImageCache();
    super.onStop();
  }
  
  static class ChannelRowAdapter
    extends ArrayAdapter<Playlist>
  {
    private LayoutInflater layoutInflater_;
    
    public ChannelRowAdapter(Context paramContext, int paramInt, List<Playlist> paramList)
    {
      super(paramInt, paramList);
      this.layoutInflater_ = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
    }
    
    public void clear()
    {
      ImageCache.clear();
      super.clear();
    }
    
    public void clearImageCache() {}
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      Playlist localPlaylist = (Playlist)getItem(paramInt);
      ChannelSearchActivity.ViewHolder localViewHolder;
      String str;
      Bitmap localBitmap;
      if (paramView == null)
      {
        paramView = this.layoutInflater_.inflate(2130903045, null);
        localViewHolder = new ChannelSearchActivity.ViewHolder();
        localViewHolder.thumbnail = ((ImageView)paramView.findViewById(2131492877));
        localViewHolder.title = ((TextView)paramView.findViewById(2131492879));
        localViewHolder.duration = ((TextView)paramView.findViewById(2131492878));
        paramView.setTag(localViewHolder);
        localViewHolder.title.setText(localPlaylist.getTitle());
        localViewHolder.duration.setText(String.valueOf(localPlaylist.getVideoCount()));
        str = localPlaylist.getThumbnailUrl();
        if (str != null)
        {
          localViewHolder.thumbnail.setTag(str);
          localViewHolder.thumbnail.setVisibility(4);
          localBitmap = ImageCache.getImage(str);
          if (localBitmap != null) {
            break label229;
          }
        }
      }
      for (;;)
      {
        try
        {
          ImageDownloadTask localImageDownloadTask = new ImageDownloadTask(localViewHolder.thumbnail);
          URL[] arrayOfURL = new URL[1];
          arrayOfURL[0] = new URL(str);
          localImageDownloadTask.execute(arrayOfURL);
          return paramView;
          localViewHolder = (ChannelSearchActivity.ViewHolder)paramView.getTag();
        }
        catch (MalformedURLException localMalformedURLException)
        {
          localMalformedURLException.printStackTrace();
          continue;
        }
        catch (RejectedExecutionException localRejectedExecutionException)
        {
          localRejectedExecutionException.printStackTrace();
          continue;
        }
        label229:
        localViewHolder.thumbnail.setImageBitmap(localBitmap);
        localViewHolder.thumbnail.setVisibility(0);
      }
    }
  }
  
  static class ViewHolder
  {
    TextView duration;
    ImageView thumbnail;
    TextView title;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.ChannelSearchActivity
 * JD-Core Version:    0.7.0.1
 */