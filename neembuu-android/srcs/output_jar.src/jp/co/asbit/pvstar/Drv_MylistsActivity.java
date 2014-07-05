package jp.co.asbit.pvstar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

public class Drv_MylistsActivity
  extends Drv_BaseActivity
{
  private MylistRowAdapter mAdapter;
  private int mDownY;
  private boolean mDriving;
  private ArrayList<Mylist> mList;
  private ListView mListView;
  private boolean mMoving;
  private int mUpY;
  
  private MylistRowAdapter getAdapter()
  {
    if (this.mAdapter == null) {
      this.mAdapter = new MylistRowAdapter(getApplicationContext(), 0, getList());
    }
    return this.mAdapter;
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
  
  public void addListData(Mylist paramMylist)
  {
    getList().add(paramMylist);
    getListView().invalidateViews();
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
    setContentAndTitle(2130903058, 2130903055);
    setTitle(getString(2131296388));
    ListView localListView = (ListView)findViewById(2131492914);
    localListView.setAdapter(getAdapter());
    localListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        if ((!Drv_MylistsActivity.this.mMoving) || (Math.abs(Drv_MylistsActivity.this.mDownY - Drv_MylistsActivity.this.mUpY) < 30))
        {
          Mylist localMylist = (Mylist)((Adapter)paramAnonymousAdapterView.getAdapter()).getItem(paramAnonymousInt);
          Intent localIntent = new Intent(Drv_MylistsActivity.this.mContext, Drv_MylistActivity.class);
          localIntent.putExtra("MYLIST_ID", localMylist.getId());
          Drv_MylistsActivity.this.startActivity(localIntent);
        }
        for (;;)
        {
          return;
          Drv_MylistsActivity.this.driveModeAlert();
          Drv_MylistsActivity.this.mMoving = false;
        }
      }
    });
    localListView.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        int i = 1;
        if (Drv_MylistsActivity.this.mDriving) {
          if (paramAnonymousMotionEvent.getAction() == 2) {
            Drv_MylistsActivity.this.mMoving = i;
          }
        }
        for (;;)
        {
          return i;
          int j;
          if (paramAnonymousMotionEvent.getAction() == i)
          {
            Drv_MylistsActivity.this.mUpY = ((int)paramAnonymousMotionEvent.getY());
            j = 0;
          }
          else if (paramAnonymousMotionEvent.getAction() == 0)
          {
            Drv_MylistsActivity.this.mDownY = ((int)paramAnonymousMotionEvent.getY());
            j = 0;
          }
          else
          {
            j = 0;
          }
        }
      }
    });
    invalidateListView();
  }
  
  protected void onDestroy()
  {
    getListView().setOnItemClickListener(null);
    super.onDestroy();
  }
  
  protected void onStop()
  {
    getAdapter().clearImageCache();
    super.onStop();
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
      Drv_MylistsActivity.ViewHolder localViewHolder;
      String str3;
      if (paramView == null)
      {
        paramView = this.layoutInflater_.inflate(2130903057, null);
        localViewHolder = new Drv_MylistsActivity.ViewHolder();
        localViewHolder.thumbnail = ((ImageView)paramView.findViewById(2131492910));
        localViewHolder.title = ((TextView)paramView.findViewById(2131492912));
        localViewHolder.count = ((TextView)paramView.findViewById(2131492911));
        localViewHolder.description = ((TextView)paramView.findViewById(2131492913));
        paramView.setTag(localViewHolder);
        String str1 = localMylist.getName();
        if ((str1 != null) && (str1.length() > 30)) {
          str1 = str1.substring(0, 30) + "...";
        }
        localViewHolder.title.setText(str1);
        String str2 = localMylist.getDescription();
        if ((str2 != null) && (str2.length() > 30)) {
          str2 = str2.substring(0, 30) + "...";
        }
        localViewHolder.description.setText(str2);
        localViewHolder.count.setText(String.valueOf(localMylist.getVideoCount()));
        str3 = localMylist.getThumbnailUrl();
        localViewHolder.thumbnail.setTag(str3);
        localViewHolder.thumbnail.setVisibility(4);
      }
      for (;;)
      {
        for (;;)
        {
          try
          {
            if ((localMylist.getVideoCount() > 0) && (str3 == null))
            {
              VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
              ArrayList localArrayList = localVideoDbHelper.getVideos(Long.valueOf(localMylist.getId()));
              localVideoDbHelper.close();
              str3 = ((Video)localArrayList.get(0)).getThumbnailUrl();
              localMylist.setThumbnailUrl(str3);
            }
            localViewHolder.thumbnail.setTag(str3);
            localBitmap = ImageCache.getImage(str3);
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
            arrayOfURL[0] = new URL(str3);
            localImageDownloadTask.execute(arrayOfURL);
            return paramView;
            localViewHolder = (Drv_MylistsActivity.ViewHolder)paramView.getTag();
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
 * Qualified Name:     jp.co.asbit.pvstar.Drv_MylistsActivity
 * JD-Core Version:    0.7.0.1
 */