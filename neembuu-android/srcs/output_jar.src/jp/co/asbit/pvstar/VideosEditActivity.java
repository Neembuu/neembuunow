package jp.co.asbit.pvstar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class VideosEditActivity
  extends BaseActivity
{
  private ArrayAdapter<Video> mAdapter;
  private ListView mListView;
  private long mylistId;
  private ArrayList<Video> videos;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentAndTitle(2130903098, 2130903080);
    this.mylistId = getIntent().getLongExtra("MYLIST_ID", -10000L);
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    this.videos = localVideoDbHelper.getVideos(Long.valueOf(this.mylistId));
    localVideoDbHelper.close();
    setTitle(getString(2131296398));
    this.mAdapter = new VideoRowAdapter(this.mContext, 0, this.videos);
    this.mListView = ((ListView)findViewById(2131493008));
    this.mListView.setAdapter(this.mAdapter);
    this.mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, final int paramAnonymousInt, long paramAnonymousLong)
      {
        VideoEditDialog.create(VideosEditActivity.this, VideosEditActivity.this.mylistId, (Video)VideosEditActivity.this.videos.get(paramAnonymousInt)).setOnDestoryListener(new VideoEditDialog.OnVideoSavedListener()
        {
          public void onVideoSaved(String paramAnonymous2String1, String paramAnonymous2String2)
          {
            Video localVideo = (Video)VideosEditActivity.this.videos.get(paramAnonymousInt);
            localVideo.setTitle(paramAnonymous2String1);
            localVideo.setDescription(paramAnonymous2String2);
            VideosEditActivity.this.mAdapter.notifyDataSetChanged();
          }
        }).show();
      }
    });
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    switch (paramMenuItem.getItemId())
    {
    }
    for (boolean bool = super.onOptionsItemSelected(paramMenuItem);; bool = true)
    {
      return bool;
      finish();
    }
  }
  
  static class VideoRowAdapter
    extends ArrayAdapter<Video>
  {
    protected LayoutInflater layoutInflater_;
    
    public VideoRowAdapter(Context paramContext, int paramInt, List<Video> paramList)
    {
      super(paramInt, paramList);
      this.layoutInflater_ = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      Video localVideo = (Video)getItem(paramInt);
      ViewHolder localViewHolder;
      if (paramView == null)
      {
        paramView = this.layoutInflater_.inflate(2130903092, null);
        localViewHolder = new ViewHolder();
        localViewHolder.title = ((TextView)paramView.findViewById(2131492971));
        localViewHolder.description = ((TextView)paramView.findViewById(2131492972));
        paramView.setTag(localViewHolder);
      }
      for (;;)
      {
        localViewHolder.title.setText(localVideo.getTitle());
        localViewHolder.description.setText(localVideo.getDescription());
        return paramView;
        localViewHolder = (ViewHolder)paramView.getTag();
      }
    }
    
    static class ViewHolder
    {
      TextView description;
      TextView title;
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.VideosEditActivity
 * JD-Core Version:    0.7.0.1
 */