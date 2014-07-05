package jp.co.asbit.pvstar;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import jp.co.asbit.pvstar.search.SearchCondItem;

public abstract class VideoListActivity
  extends BaseActivity
{
  private int adMargin;
  protected VideoRowAdapter mAdapter;
  private View mHeader;
  private ArrayList<Video> mList;
  private ListView mListView;
  private ImageView playButton;
  protected ImageView sortButton;
  
  private void updateSelectedRow()
  {
    getAdapter().notifyDataSetChanged();
    int i = selectedRow();
    TextView localTextView = (TextView)findViewById(2131492951);
    if (i == 0) {
      localTextView.setVisibility(8);
    }
    for (;;)
    {
      return;
      if (localTextView.getVisibility() != 0) {
        localTextView.setVisibility(0);
      }
      String str = getString(2131296379);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(i);
      localTextView.setText(String.format(str, arrayOfObject));
    }
  }
  
  public void addListData(ArrayList<Video> paramArrayList, int paramInt, ArrayList<SearchCondItem> paramArrayList1) {}
  
  protected void addSelectedVideosToMylist(long paramLong)
  {
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    try
    {
      int i = localVideoDbHelper.insertAllVideo(getSelectedList(), Long.valueOf(paramLong));
      Context localContext = this.mContext;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(i);
      Toast.makeText(localContext, getString(2131296395, arrayOfObject), 0).show();
      return;
    }
    catch (VideoDbHelper.MaxVideoCountException localMaxVideoCountException)
    {
      for (;;)
      {
        Toast.makeText(this.mContext, localMaxVideoCountException.getMessage(), 0).show();
        localVideoDbHelper.close();
      }
    }
    finally
    {
      localVideoDbHelper.close();
    }
  }
  
  protected void clearSelectedRows()
  {
    ArrayList localArrayList = getList();
    for (int i = 0;; i++)
    {
      if (i >= localArrayList.size())
      {
        getAdapter().notifyDataSetChanged();
        findViewById(2131492951).setVisibility(8);
        return;
      }
      Video localVideo = (Video)localArrayList.get(i);
      if (localVideo.isChecked()) {
        localVideo.setChecked(false);
      }
    }
  }
  
  protected VideoRowAdapter getAdapter()
  {
    if (this.mAdapter == null) {
      this.mAdapter = new VideoRowAdapter(this.mContext, 0, getList());
    }
    return this.mAdapter;
  }
  
  protected View getHeader()
  {
    if (this.mHeader == null) {
      this.mHeader = getLayoutInflater().inflate(2130903095, null);
    }
    return this.mHeader;
  }
  
  protected ArrayList<Video> getList()
  {
    if (this.mList == null) {
      this.mList = new ArrayList();
    }
    return this.mList;
  }
  
  protected ListView getListView()
  {
    if (this.mListView == null) {
      this.mListView = ((ListView)findViewById(2131492948));
    }
    return this.mListView;
  }
  
  protected ArrayList<Video> getSelectedList()
  {
    Object localObject = getList();
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    if (i >= ((ArrayList)localObject).size()) {
      if (localArrayList.size() != 0) {
        break label65;
      }
    }
    for (;;)
    {
      return localObject;
      if (((Video)((ArrayList)localObject).get(i)).isChecked()) {
        localArrayList.add((Video)((ArrayList)localObject).get(i));
      }
      i++;
      break;
      label65:
      localObject = localArrayList;
    }
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)getListView().getLayoutParams();
    View localView = findViewById(2131492874);
    if (paramConfiguration.orientation == 2)
    {
      localView.setVisibility(8);
      localLayoutParams.setMargins(0, 0, 0, 0);
    }
    for (;;)
    {
      getListView().setLayoutParams(localLayoutParams);
      super.onConfigurationChanged(paramConfiguration);
      return;
      if (paramConfiguration.orientation == 1)
      {
        localView.setVisibility(0);
        localLayoutParams.setMargins(0, 0, 0, this.adMargin);
      }
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentAndTitle(2130903097, 2130903086);
    ListView localListView = getListView();
    localListView.addHeaderView(getHeader());
    localListView.setAdapter(getAdapter());
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        ArrayList localArrayList = new ArrayList();
        Video localVideo = (Video)((Adapter)paramAnonymousAdapterView.getAdapter()).getItem(paramAnonymousInt);
        if (localVideo != null)
        {
          localArrayList.add(localVideo);
          VideoListActivity.this.startVideoActivity(localArrayList);
        }
      }
    });
    localListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
    {
      public boolean onItemLongClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        boolean bool1 = true;
        try
        {
          Video localVideo = (Video)VideoListActivity.this.getList().get(paramAnonymousInt - 1);
          if (localVideo.isChecked()) {}
          for (boolean bool2 = false;; bool2 = bool1)
          {
            localVideo.setChecked(bool2);
            VideoListActivity.this.updateSelectedRow();
            return bool1;
          }
        }
        catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
        {
          for (;;)
          {
            localArrayIndexOutOfBoundsException.printStackTrace();
            bool1 = false;
          }
        }
        catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
        {
          for (;;)
          {
            localIndexOutOfBoundsException.printStackTrace();
          }
        }
      }
    });
    this.sortButton = ((ImageView)findViewById(2131492952));
    this.sortButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        VideoListActivity.this.sortDialog();
      }
    });
    this.playButton = ((ImageView)findViewById(2131492950));
    this.playButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        VideoListActivity.this.startVideoActivity(VideoListActivity.this.getSelectedList());
      }
    });
    this.adMargin = ((int)(50.0F * this.mDensity));
    onConfigurationChanged(getResources().getConfiguration());
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131427334, paramMenu);
    return super.onCreateOptionsMenu(paramMenu);
  }
  
  protected void onDestroy()
  {
    ListView localListView = getListView();
    localListView.setOnItemClickListener(null);
    localListView.setOnItemLongClickListener(null);
    try
    {
      this.playButton.setOnClickListener(null);
      this.sortButton.setOnClickListener(null);
      super.onDestroy();
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
      }
    }
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
      try
      {
        localArrayList = getList();
        i = 0;
        if (i >= localArrayList.size()) {
          updateSelectedRow();
        }
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
      {
        for (;;)
        {
          ArrayList localArrayList;
          int i;
          localArrayIndexOutOfBoundsException.printStackTrace();
          break;
          ((Video)localArrayList.get(i)).setChecked(true);
          i++;
        }
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
      {
        localIndexOutOfBoundsException.printStackTrace();
      }
      continue;
      if (selectedRow() == 0)
      {
        Toast.makeText(this.mContext, 2131296378, 0).show();
        bool = false;
      }
      else
      {
        new VideoAddDialog(this).setOnVideoAddedListener(new VideoAddDialog.OnVideoAddedListener()
        {
          public void onVideoAdded(long paramAnonymousLong)
          {
            VideoListActivity.this.addSelectedVideosToMylist(paramAnonymousLong);
          }
        }).show();
        continue;
        clearSelectedRows();
      }
    }
  }
  
  protected void onStop()
  {
    getAdapter().clearImageCache();
    super.onStop();
  }
  
  protected int selectedRow()
  {
    int i = 0;
    ArrayList localArrayList = getList();
    for (int j = 0;; j++)
    {
      if (j >= localArrayList.size()) {
        return i;
      }
      if (((Video)localArrayList.get(j)).isChecked()) {
        i++;
      }
    }
  }
  
  protected abstract void sortDialog();
  
  protected void startVideoActivity(ArrayList<Video> paramArrayList)
  {
    Intent localIntent = new Intent(this.mContext, VideoActivity.class);
    localIntent.setFlags(131072);
    localIntent.removeExtra("VIDEO_LIST");
    if (paramArrayList != null) {
      localIntent.putExtra("VIDEO_LIST", paramArrayList);
    }
    startActivity(localIntent);
  }
  
  protected abstract void updateListView();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.VideoListActivity
 * JD-Core Version:    0.7.0.1
 */