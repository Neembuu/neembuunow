package jp.co.asbit.pvstar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class BookmarksSortActivity
  extends BaseActivity
{
  private BookmarkSortAdapter mAdapter;
  private int mDraggingPosition = -1;
  private SortableListView mListView;
  private ArrayList<Playlist> playlists;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentAndTitle(2130903099, 2130903080);
    setTitle(getString(2131296390));
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    this.playlists = localVideoDbHelper.getBookmarks();
    localVideoDbHelper.close();
    this.mAdapter = new BookmarkSortAdapter(this.mContext, 0, this.playlists);
    this.mListView = ((SortableListView)findViewById(2131493009));
    this.mListView.setDragListener(new DragListener());
    this.mListView.setSortable(true);
    this.mListView.setAdapter(this.mAdapter);
  }
  
  protected void onPause()
  {
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    localVideoDbHelper.setBookmarksOrder(this.playlists);
    localVideoDbHelper.close();
    super.onPause();
  }
  
  protected void onStart()
  {
    Toast.makeText(this.mContext, 2131296397, 1).show();
    super.onStart();
  }
  
  class BookmarkSortAdapter
    extends ArrayAdapter<Playlist>
  {
    private LayoutInflater layoutInflater_;
    
    public BookmarkSortAdapter(int paramInt, ArrayList<Playlist> paramArrayList)
    {
      super(paramArrayList, localList);
      this.layoutInflater_ = ((LayoutInflater)paramInt.getSystemService("layout_inflater"));
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {
        paramView = this.layoutInflater_.inflate(2130903082, null);
      }
      Playlist localPlaylist = (Playlist)getItem(paramInt);
      TextView localTextView = (TextView)paramView.findViewById(2131492971);
      localTextView.setText(localPlaylist.getTitle());
      if (paramInt == BookmarksSortActivity.this.mDraggingPosition) {}
      for (int i = 4;; i = 0)
      {
        localTextView.setVisibility(i);
        return paramView;
      }
    }
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
        localPlaylist2 = (Playlist)BookmarksSortActivity.this.playlists.get(paramInt1);
        n = paramInt1;
        if (n >= m) {
          BookmarksSortActivity.this.playlists.set(m, localPlaylist2);
        }
      }
      while (paramInt1 <= paramInt2) {
        for (;;)
        {
          int m;
          Playlist localPlaylist2;
          BookmarksSortActivity.this.mDraggingPosition = paramInt2;
          BookmarksSortActivity.this.mListView.invalidateViews();
          break;
          ArrayList localArrayList3 = BookmarksSortActivity.this.playlists;
          ArrayList localArrayList4 = BookmarksSortActivity.this.playlists;
          int i1 = n + 1;
          localArrayList3.set(n, (Playlist)localArrayList4.get(i1));
          int n = i1;
        }
      }
      int i = paramInt2;
      Playlist localPlaylist1 = (Playlist)BookmarksSortActivity.this.playlists.get(paramInt1);
      int k;
      for (int j = paramInt1;; j = k)
      {
        if (j <= i)
        {
          BookmarksSortActivity.this.playlists.set(i, localPlaylist1);
          break;
        }
        ArrayList localArrayList1 = BookmarksSortActivity.this.playlists;
        ArrayList localArrayList2 = BookmarksSortActivity.this.playlists;
        k = j - 1;
        localArrayList1.set(j, (Playlist)localArrayList2.get(k));
      }
    }
    
    public int onStartDrag(int paramInt)
    {
      BookmarksSortActivity.this.mDraggingPosition = paramInt;
      BookmarksSortActivity.this.mListView.invalidateViews();
      return paramInt;
    }
    
    public boolean onStopDrag(int paramInt1, int paramInt2)
    {
      BookmarksSortActivity.this.mDraggingPosition = -1;
      BookmarksSortActivity.this.mListView.invalidateViews();
      return super.onStopDrag(paramInt1, paramInt2);
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.BookmarksSortActivity
 * JD-Core Version:    0.7.0.1
 */