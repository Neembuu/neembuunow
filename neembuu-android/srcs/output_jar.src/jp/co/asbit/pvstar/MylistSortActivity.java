package jp.co.asbit.pvstar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MylistSortActivity
  extends BaseActivity
{
  private MylistSortAdapter mAdapter;
  private int mDraggingPosition = -1;
  private SortableListView mListView;
  private Long mylistId;
  private ArrayList<Video> videoList;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentAndTitle(2130903099, 2130903080);
    this.mylistId = Long.valueOf(getIntent().getLongExtra("MYLIST_ID", -10000L));
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    this.videoList = localVideoDbHelper.getVideos(this.mylistId);
    Mylist localMylist = localVideoDbHelper.getMylist(this.mylistId);
    localVideoDbHelper.close();
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = getText(2131296478);
    arrayOfObject[1] = localMylist.getName();
    setTitle(String.format("%s - %s", arrayOfObject));
    this.mAdapter = new MylistSortAdapter(getApplicationContext(), 0, this.videoList);
    this.mListView = ((SortableListView)findViewById(2131493009));
    this.mListView.setDragListener(new DragListener());
    this.mListView.setSortable(true);
    this.mListView.setAdapter(this.mAdapter);
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
  
  protected void onPause()
  {
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    localVideoDbHelper.setVideosOrder(this.videoList, this.mylistId);
    localVideoDbHelper.close();
    super.onPause();
  }
  
  protected void onStart()
  {
    Toast.makeText(this.mContext, 2131296397, 1).show();
    super.onStart();
  }
  
  class DragListener
    extends SortableListView.SimpleDragListener
  {
    DragListener() {}
    
    public int onDuringDrag(int paramInt1, int paramInt2)
    {
      if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 == paramInt2))
      {
        paramInt2 = paramInt1;
        return paramInt2;
      }
      if (paramInt1 < paramInt2)
      {
        m = paramInt2;
        localVideo2 = (Video)MylistSortActivity.this.videoList.get(paramInt1);
        n = paramInt1;
        if (n >= m) {
          MylistSortActivity.this.videoList.set(m, localVideo2);
        }
      }
      while (paramInt1 <= paramInt2) {
        for (;;)
        {
          int m;
          Video localVideo2;
          MylistSortActivity.this.mDraggingPosition = paramInt2;
          MylistSortActivity.this.mListView.invalidateViews();
          break;
          ArrayList localArrayList3 = MylistSortActivity.this.videoList;
          ArrayList localArrayList4 = MylistSortActivity.this.videoList;
          int i1 = n + 1;
          localArrayList3.set(n, (Video)localArrayList4.get(i1));
          int n = i1;
        }
      }
      int i = paramInt2;
      Video localVideo1 = (Video)MylistSortActivity.this.videoList.get(paramInt1);
      int k;
      for (int j = paramInt1;; j = k)
      {
        if (j <= i)
        {
          MylistSortActivity.this.videoList.set(i, localVideo1);
          break;
        }
        ArrayList localArrayList1 = MylistSortActivity.this.videoList;
        ArrayList localArrayList2 = MylistSortActivity.this.videoList;
        k = j - 1;
        localArrayList1.set(j, (Video)localArrayList2.get(k));
      }
    }
    
    public int onStartDrag(int paramInt)
    {
      MylistSortActivity.this.mDraggingPosition = paramInt;
      MylistSortActivity.this.mListView.invalidateViews();
      return paramInt;
    }
    
    public boolean onStopDrag(int paramInt1, int paramInt2)
    {
      MylistSortActivity.this.mDraggingPosition = -1;
      MylistSortActivity.this.mListView.invalidateViews();
      return super.onStopDrag(paramInt1, paramInt2);
    }
  }
  
  class MylistSortAdapter
    extends ArrayAdapter<Video>
  {
    private LayoutInflater layoutInflater_;
    
    public MylistSortAdapter(int paramInt, List<Video> paramList)
    {
      super(paramList, localList);
      this.layoutInflater_ = ((LayoutInflater)paramInt.getSystemService("layout_inflater"));
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {
        paramView = this.layoutInflater_.inflate(2130903082, null);
      }
      Video localVideo = (Video)getItem(paramInt);
      TextView localTextView = (TextView)paramView.findViewById(2131492971);
      localTextView.setText(localVideo.getTitle());
      if (paramInt == MylistSortActivity.this.mDraggingPosition) {}
      for (int i = 4;; i = 0)
      {
        localTextView.setVisibility(i);
        return paramView;
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.MylistSortActivity
 * JD-Core Version:    0.7.0.1
 */