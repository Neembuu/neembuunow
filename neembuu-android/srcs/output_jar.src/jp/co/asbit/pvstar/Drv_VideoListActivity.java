package jp.co.asbit.pvstar;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import jp.co.asbit.pvstar.search.SearchCondItem;

public abstract class Drv_VideoListActivity
  extends Drv_BaseActivity
{
  protected Drv_VideoRowAdapter mAdapter;
  private int mDownY;
  protected boolean mDriving = false;
  private View mHeader;
  private ArrayList<Video> mList;
  private ListView mListView;
  private boolean mMoving = false;
  private ImageView mPlayButton;
  protected ImageView mSortButton;
  private int mUpY;
  
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
  
  protected Drv_VideoRowAdapter getAdapter()
  {
    if (this.mAdapter == null) {
      this.mAdapter = new Drv_VideoRowAdapter(this.mContext, 0, getList());
    }
    return this.mAdapter;
  }
  
  protected View getHeader()
  {
    if (this.mHeader == null) {
      this.mHeader = getLayoutInflater().inflate(2130903061, null);
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
  
  protected void onChangeVehicleState(int paramInt)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      return;
      this.mDriving = false;
      continue;
      this.mDriving = true;
      continue;
      this.mDriving = false;
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentAndTitle(2130903063, 2130903055);
    ListView localListView = getListView();
    localListView.addHeaderView(getHeader());
    localListView.setAdapter(getAdapter());
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        if ((!Drv_VideoListActivity.this.mMoving) || (Math.abs(Drv_VideoListActivity.this.mDownY - Drv_VideoListActivity.this.mUpY) < 30)) {
          if (paramAnonymousInt != 0) {}
        }
        for (;;)
        {
          return;
          ArrayList localArrayList1 = new ArrayList();
          ArrayList localArrayList2 = Drv_VideoListActivity.this.getList();
          for (int i = paramAnonymousInt;; i++)
          {
            if (i > localArrayList2.size())
            {
              if (localArrayList1.size() <= 0) {
                break;
              }
              Drv_VideoListActivity.this.startVideoActivity(localArrayList1);
              break;
            }
            Video localVideo = (Video)((Adapter)paramAnonymousAdapterView.getAdapter()).getItem(i);
            if (localVideo != null) {
              localArrayList1.add(localVideo);
            }
          }
          Drv_VideoListActivity.this.driveModeAlert();
          Drv_VideoListActivity.this.mMoving = false;
        }
      }
    });
    localListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
    {
      public boolean onItemLongClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        boolean bool1 = true;
        if (!Drv_VideoListActivity.this.mDriving) {}
        for (;;)
        {
          try
          {
            Video localVideo = (Video)Drv_VideoListActivity.this.getList().get(paramAnonymousInt - 1);
            if (localVideo.isChecked())
            {
              bool2 = false;
              localVideo.setChecked(bool2);
              Drv_VideoListActivity.this.updateSelectedRow();
              return bool1;
            }
            boolean bool2 = bool1;
            continue;
          }
          catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
          {
            localArrayIndexOutOfBoundsException.printStackTrace();
            bool1 = false;
            continue;
          }
          catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
          {
            localIndexOutOfBoundsException.printStackTrace();
            continue;
          }
          Drv_VideoListActivity.this.driveModeAlert();
        }
      }
    });
    localListView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        int i = 1;
        if (Drv_VideoListActivity.this.mDriving) {
          if (paramAnonymousMotionEvent.getAction() == 2) {
            Drv_VideoListActivity.this.mMoving = i;
          }
        }
        for (;;)
        {
          return i;
          int j;
          if (paramAnonymousMotionEvent.getAction() == i)
          {
            Drv_VideoListActivity.this.mUpY = ((int)paramAnonymousMotionEvent.getY());
            j = 0;
          }
          else if (paramAnonymousMotionEvent.getAction() == 0)
          {
            Drv_VideoListActivity.this.mDownY = ((int)paramAnonymousMotionEvent.getY());
            j = 0;
          }
          else
          {
            j = 0;
          }
        }
      }
    });
    this.mSortButton = ((ImageView)findViewById(2131492952));
    this.mPlayButton = ((ImageView)findViewById(2131492950));
    this.mPlayButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Drv_VideoListActivity.this.startVideoActivity(Drv_VideoListActivity.this.getSelectedList());
      }
    });
    this.mPlayButton.setOnLongClickListener(new View.OnLongClickListener()
    {
      public boolean onLongClick(View paramAnonymousView)
      {
        new AlertDialog.Builder(Drv_VideoListActivity.this).setTitle(2131296513).setItems(2131099661, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(Drv_VideoListActivity.this.mContext);
            switch (paramAnonymous2Int)
            {
            }
            for (;;)
            {
              Drv_VideoListActivity.this.startVideoActivity(Drv_VideoListActivity.this.getSelectedList());
              return;
              localSharedPreferences.edit().putBoolean("repeat2", false).commit();
              localSharedPreferences.edit().putBoolean("shuffle", false).commit();
              continue;
              localSharedPreferences.edit().putBoolean("repeat2", false).commit();
              localSharedPreferences.edit().putBoolean("shuffle", true).commit();
              continue;
              localSharedPreferences.edit().putBoolean("repeat2", true).commit();
              localSharedPreferences.edit().putBoolean("shuffle", false).commit();
              continue;
              localSharedPreferences.edit().putBoolean("repeat2", true).commit();
              localSharedPreferences.edit().putBoolean("shuffle", true).commit();
            }
          }
        }).show();
        return true;
      }
    });
  }
  
  protected void onDestroy()
  {
    ListView localListView = getListView();
    localListView.setOnItemClickListener(null);
    localListView.setOnItemLongClickListener(null);
    try
    {
      this.mPlayButton.setOnClickListener(null);
      this.mPlayButton.setOnLongClickListener(null);
      this.mSortButton.setOnClickListener(null);
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
    if ((this.mDriving) && (Util.isPopUpPlayModel(this.mContext))) {
      driveModeAlert();
    }
    for (;;)
    {
      return;
      Intent localIntent = new Intent(this.mContext, VideoActivity.class);
      localIntent.setFlags(131072);
      localIntent.removeExtra("VIDEO_LIST");
      if (paramArrayList != null) {
        localIntent.putExtra("VIDEO_LIST", paramArrayList);
      }
      localIntent.putExtra("CLARION_MODE", true);
      startActivity(localIntent);
    }
  }
  
  protected abstract void updateListView();
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.Drv_VideoListActivity
 * JD-Core Version:    0.7.0.1
 */