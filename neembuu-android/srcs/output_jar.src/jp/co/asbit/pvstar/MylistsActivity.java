package jp.co.asbit.pvstar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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

public class MylistsActivity
  extends BaseActivity
{
  private static final int BACKUP_IMPORTER = 300;
  private static final int MYLIST_IMPORTER = 200;
  private static final int MYLIST_SORTER = 100;
  private MylistRowAdapter mAdapter;
  private View mHeader;
  private ArrayList<Mylist> mList;
  private ListView mListView;
  
  private void callSelfActivity()
  {
    Intent localIntent = new Intent(this.mContext, MylistsActivity.class);
    localIntent.setFlags(65536);
    startActivity(localIntent);
    finish();
  }
  
  private MylistRowAdapter getAdapter()
  {
    if (this.mAdapter == null) {
      this.mAdapter = new MylistRowAdapter(getApplicationContext(), 0, getList());
    }
    return this.mAdapter;
  }
  
  private View getHeader()
  {
    if (this.mHeader == null) {
      this.mHeader = getLayoutInflater().inflate(2130903071, null);
    }
    return this.mHeader;
  }
  
  private ArrayList<Mylist> getList()
  {
    if (this.mList == null) {
      this.mList = new ArrayList();
    }
    return this.mList;
  }
  
  private ListView getListView()
  {
    if (this.mListView == null) {
      this.mListView = ((ListView)findViewById(2131492914));
    }
    return this.mListView;
  }
  
  private void invalidateListView()
  {
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(getApplicationContext());
    ArrayList localArrayList = localVideoDbHelper.getMylists();
    localVideoDbHelper.close();
    getList().clear();
    getList().addAll(localArrayList);
    getListView().invalidateViews();
  }
  
  private void showEditDialog(Mylist paramMylist)
  {
    MylistEditDialog.create(this, paramMylist).setOnDestoryListener(new MylistEditDialog.OnMylistSavedListener()
    {
      public void onMylistSaved(long paramAnonymousLong)
      {
        MylistsActivity.this.invalidateListView();
      }
    }).show();
  }
  
  public void addListData(Mylist paramMylist)
  {
    getList().add(paramMylist);
    getListView().invalidateViews();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
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
    setContentAndTitle(2130903070, 2130903086);
    setTitle(getString(2131296388));
    ListView localListView = (ListView)findViewById(2131492914);
    localListView.addHeaderView(getHeader());
    localListView.setAdapter(getAdapter());
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        if (paramAnonymousInt == 0) {
          MylistsActivity.this.showEditDialog();
        }
        for (;;)
        {
          return;
          Mylist localMylist = (Mylist)((Adapter)paramAnonymousAdapterView.getAdapter()).getItem(paramAnonymousInt);
          Intent localIntent = new Intent(MylistsActivity.this.mContext, MylistActivity.class);
          localIntent.putExtra("MYLIST_ID", localMylist.getId());
          MylistsActivity.this.startActivity(localIntent);
        }
      }
    });
    localListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
    {
      public boolean onItemLongClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        if (paramAnonymousInt == 0) {}
        for (;;)
        {
          return true;
          Mylist localMylist = (Mylist)((Adapter)paramAnonymousAdapterView.getAdapter()).getItem(paramAnonymousInt);
          if (localMylist.getId() != -20000L) {
            MylistsActivity.this.showEditDialog(localMylist);
          }
        }
      }
    });
    invalidateListView();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131427331, paramMenu);
    return super.onCreateOptionsMenu(paramMenu);
  }
  
  protected void onDestroy()
  {
    getListView().setOnItemClickListener(null);
    super.onDestroy();
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
        startActivityForResult(new Intent(this.mContext, MylistsSortActivity.class), 100);
        continue;
        startActivityForResult(new Intent(this.mContext, SettingYouTubeActivity.class), 200);
        continue;
        startActivityForResult(new Intent(this.mContext, SettingNiconicoActivity.class), 200);
        continue;
        startActivityForResult(new Intent(this.mContext, SettingBackupActivity.class), 300);
      }
    }
  }
  
  protected void onStop()
  {
    getAdapter().clearImageCache();
    super.onStop();
  }
  
  protected void showEditDialog()
  {
    showEditDialog(null);
  }
  
  static class MylistRowAdapter
    extends ArrayAdapter<Mylist>
  {
    private LayoutInflater layoutInflater_;
    private Context mContext;
    
    public MylistRowAdapter(Context paramContext, int paramInt, List<Mylist> paramList)
    {
      super(paramInt, paramList);
      this.mContext = paramContext;
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
      Mylist localMylist = (Mylist)getItem(paramInt);
      MylistsActivity.ViewHolder localViewHolder;
      String str;
      if (paramView == null)
      {
        paramView = this.layoutInflater_.inflate(2130903069, null);
        localViewHolder = new MylistsActivity.ViewHolder();
        localViewHolder.thumbnail = ((ImageView)paramView.findViewById(2131492910));
        localViewHolder.title = ((TextView)paramView.findViewById(2131492912));
        localViewHolder.count = ((TextView)paramView.findViewById(2131492911));
        localViewHolder.description = ((TextView)paramView.findViewById(2131492913));
        paramView.setTag(localViewHolder);
        localViewHolder.title.setText(localMylist.getName());
        localViewHolder.description.setText(localMylist.getDescription());
        localViewHolder.count.setText(localMylist.getVideoCount() + " videos");
        str = localMylist.getThumbnailUrl();
        localViewHolder.thumbnail.setTag(str);
        localViewHolder.thumbnail.setVisibility(4);
      }
      for (;;)
      {
        for (;;)
        {
          try
          {
            if ((localMylist.getVideoCount() > 0) && (str == null))
            {
              VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
              ArrayList localArrayList = localVideoDbHelper.getVideos(Long.valueOf(localMylist.getId()));
              localVideoDbHelper.close();
              str = ((Video)localArrayList.get(0)).getThumbnailUrl();
              localMylist.setThumbnailUrl(str);
            }
            localViewHolder.thumbnail.setTag(str);
            localBitmap = ImageCache.getImage(str);
            if (localBitmap != null) {
              continue;
            }
          }
          catch (NullPointerException localNullPointerException)
          {
            Bitmap localBitmap;
            ImageDownloadTask localImageDownloadTask;
            URL[] arrayOfURL;
            continue;
          }
          try
          {
            localImageDownloadTask = new ImageDownloadTask(localViewHolder.thumbnail);
            arrayOfURL = new URL[1];
            arrayOfURL[0] = new URL(str);
            localImageDownloadTask.execute(arrayOfURL);
            return paramView;
            localViewHolder = (MylistsActivity.ViewHolder)paramView.getTag();
          }
          catch (MalformedURLException localMalformedURLException)
          {
            localMalformedURLException.printStackTrace();
          }
          catch (RejectedExecutionException localRejectedExecutionException)
          {
            localRejectedExecutionException.printStackTrace();
          }
        }
        localViewHolder.thumbnail.setImageBitmap(localBitmap);
        localViewHolder.thumbnail.setVisibility(0);
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
 * Qualified Name:     jp.co.asbit.pvstar.MylistsActivity
 * JD-Core Version:    0.7.0.1
 */