package jp.co.asbit.pvstar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

public class BookmarksFlagment
  extends Fragment
{
  private static final int BACKUP_IMPORTER = 300;
  private static final int BOOKMARK_IMPORTER = 200;
  private static final int BOOKMARK_SORTER = 100;
  private BookmarkRowAdapter mAdapter;
  private Context mContext;
  private ArrayList<Playlist> mList;
  private ListView mListView;
  private View mView;
  
  private void callSelfActivity()
  {
    Intent localIntent = new Intent(this.mContext, MylistsFlagmentsActivity.class);
    localIntent.setFlags(65536);
    localIntent.putExtra("CURRENT_ITEM", 1);
    startActivity(localIntent);
    getActivity().finish();
  }
  
  private BookmarkRowAdapter getAdapter()
  {
    if (this.mAdapter == null) {
      this.mAdapter = new BookmarkRowAdapter(this.mContext, 0, getList());
    }
    return this.mAdapter;
  }
  
  private ArrayList<Playlist> getList()
  {
    if (this.mList == null) {
      this.mList = new ArrayList();
    }
    return this.mList;
  }
  
  private ListView getListView()
  {
    if (this.mListView == null) {
      this.mListView = ((ListView)this.mView.findViewById(2131492914));
    }
    return this.mListView;
  }
  
  private void invalidateListView()
  {
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    ArrayList localArrayList = localVideoDbHelper.getBookmarks();
    localVideoDbHelper.close();
    getList().clear();
    getList().addAll(localArrayList);
    getListView().invalidateViews();
  }
  
  private void showEditDialog(Playlist paramPlaylist)
  {
    BookmarkEditDialog.create((MylistsFlagmentsActivity)getActivity(), paramPlaylist).setOnDestoryListener(new BookmarkEditDialog.OnBookmarkSavedListener()
    {
      public void onBookmarkSaved()
      {
        BookmarksFlagment.this.invalidateListView();
      }
    }).show();
  }
  
  public void addListData(Playlist paramPlaylist)
  {
    getList().add(paramPlaylist);
    getListView().invalidateViews();
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == 100) {
      callSelfActivity();
    }
    if (paramInt1 == 200) {
      callSelfActivity();
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setHasOptionsMenu(true);
  }
  
  public void onCreateOptionsMenu(Menu paramMenu, MenuInflater paramMenuInflater)
  {
    super.onCreateOptionsMenu(paramMenu, paramMenuInflater);
    paramMenuInflater.inflate(2131427328, paramMenu);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    this.mView = getActivity().getLayoutInflater().inflate(2130903070, null);
    this.mContext = getActivity().getApplicationContext();
    ListView localListView = (ListView)this.mView.findViewById(2131492914);
    localListView.setAdapter(getAdapter());
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        Playlist localPlaylist = (Playlist)((Adapter)paramAnonymousAdapterView.getAdapter()).getItem(paramAnonymousInt);
        Intent localIntent;
        switch (localPlaylist.getListType())
        {
        default: 
          localIntent = new Intent(BookmarksFlagment.this.mContext, UserVideosActivity.class);
          localIntent.putExtra("PLAYLIST", localPlaylist);
        }
        for (;;)
        {
          BookmarksFlagment.this.startActivity(localIntent);
          return;
          if (localPlaylist.getSearchEngine().equals("niconico"))
          {
            localIntent = new Intent(BookmarksFlagment.this.mContext, NicoMylistActivity.class);
            localIntent.putExtra("NICO_MYLIST_ID", localPlaylist.getId());
          }
          else
          {
            localIntent = new Intent(BookmarksFlagment.this.mContext, PlaylistActivity.class);
            localIntent.putExtra("PLAYLIST", localPlaylist);
          }
        }
      }
    });
    localListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
    {
      public boolean onItemLongClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        Playlist localPlaylist = (Playlist)((Adapter)paramAnonymousAdapterView.getAdapter()).getItem(paramAnonymousInt);
        BookmarksFlagment.this.showEditDialog(localPlaylist);
        return true;
      }
    });
    invalidateListView();
    return this.mView;
  }
  
  public void onDestroyView()
  {
    getListView().setOnItemClickListener(null);
    getAdapter().clearImageCache();
    super.onDestroyView();
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool = true;
    switch (paramMenuItem.getItemId())
    {
    default: 
      bool = super.onOptionsItemSelected(paramMenuItem);
    }
    for (;;)
    {
      return bool;
      if (getList().size() == 0)
      {
        bool = false;
      }
      else
      {
        startActivityForResult(new Intent(this.mContext, BookmarksSortActivity.class), 100);
        continue;
        startActivityForResult(new Intent(this.mContext, SettingYouTubeActivity.class), 200);
        continue;
        startActivityForResult(new Intent(this.mContext, SettingNiconicoActivity.class), 200);
        continue;
        startActivityForResult(new Intent(this.mContext, SettingBackupActivity.class), 300);
      }
    }
  }
  
  static class BookmarkRowAdapter
    extends ArrayAdapter<Playlist>
  {
    private LayoutInflater layoutInflater_;
    
    public BookmarkRowAdapter(Context paramContext, int paramInt, List<Playlist> paramList)
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
      BookmarksFlagment.ViewHolder localViewHolder;
      if (paramView == null)
      {
        paramView = this.layoutInflater_.inflate(2130903069, null);
        localViewHolder = new BookmarksFlagment.ViewHolder();
        localViewHolder.thumbnail = ((ImageView)paramView.findViewById(2131492910));
        localViewHolder.title = ((TextView)paramView.findViewById(2131492912));
        localViewHolder.description = ((TextView)paramView.findViewById(2131492913));
        localViewHolder.count = ((TextView)paramView.findViewById(2131492911));
        paramView.setTag(localViewHolder);
      }
      for (;;)
      {
        localViewHolder.title.setText(localPlaylist.getTitle());
        localViewHolder.description.setText(localPlaylist.getDescription());
        localViewHolder.count.setVisibility(8);
        String str = localPlaylist.getThumbnailUrl();
        localViewHolder.thumbnail.setTag(str);
        localViewHolder.thumbnail.setVisibility(4);
        try
        {
          localViewHolder.thumbnail.setTag(str);
          localBitmap = ImageCache.getImage(str);
          if (localBitmap != null) {}
        }
        catch (NullPointerException localNullPointerException)
        {
          for (;;)
          {
            Bitmap localBitmap;
            try
            {
              ImageDownloadTask localImageDownloadTask = new ImageDownloadTask(localViewHolder.thumbnail);
              URL[] arrayOfURL = new URL[1];
              arrayOfURL[0] = new URL(str);
              localImageDownloadTask.execute(arrayOfURL);
              return paramView;
              localViewHolder = (BookmarksFlagment.ViewHolder)paramView.getTag();
            }
            catch (MalformedURLException localMalformedURLException)
            {
              localMalformedURLException.printStackTrace();
              continue;
              localNullPointerException = localNullPointerException;
              localNullPointerException.printStackTrace();
              continue;
            }
            catch (RejectedExecutionException localRejectedExecutionException)
            {
              localRejectedExecutionException.printStackTrace();
              continue;
            }
            localViewHolder.thumbnail.setImageBitmap(localBitmap);
            localViewHolder.thumbnail.setVisibility(0);
          }
        }
      }
    }
  }
  
  static class ViewHolder
  {
    TextView count;
    TextView description;
    ImageView thumbnail;
    TextView title;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.BookmarksFlagment
 * JD-Core Version:    0.7.0.1
 */