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

public class MylistsSortActivity
  extends BaseActivity
{
  private MylistSortAdapter mAdapter;
  private int mDraggingPosition = -1;
  private SortableListView mListView;
  private ArrayList<Mylist> mylists;
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentAndTitle(2130903099, 2130903080);
    setTitle(getString(2131296385));
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    this.mylists = localVideoDbHelper.getMylists();
    localVideoDbHelper.close();
    this.mAdapter = new MylistSortAdapter(this.mContext, 0, this.mylists);
    this.mListView = ((SortableListView)findViewById(2131493009));
    this.mListView.setDragListener(new DragListener());
    this.mListView.setSortable(true);
    this.mListView.setAdapter(this.mAdapter);
  }
  
  protected void onPause()
  {
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    localVideoDbHelper.setMylistsOrder(this.mylists);
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
        localMylist2 = (Mylist)MylistsSortActivity.this.mylists.get(paramInt1);
        n = paramInt1;
        if (n >= m) {
          MylistsSortActivity.this.mylists.set(m, localMylist2);
        }
      }
      while (paramInt1 <= paramInt2) {
        for (;;)
        {
          int m;
          Mylist localMylist2;
          MylistsSortActivity.this.mDraggingPosition = paramInt2;
          MylistsSortActivity.this.mListView.invalidateViews();
          break;
          ArrayList localArrayList3 = MylistsSortActivity.this.mylists;
          ArrayList localArrayList4 = MylistsSortActivity.this.mylists;
          int i1 = n + 1;
          localArrayList3.set(n, (Mylist)localArrayList4.get(i1));
          int n = i1;
        }
      }
      int i = paramInt2;
      Mylist localMylist1 = (Mylist)MylistsSortActivity.this.mylists.get(paramInt1);
      int k;
      for (int j = paramInt1;; j = k)
      {
        if (j <= i)
        {
          MylistsSortActivity.this.mylists.set(i, localMylist1);
          break;
        }
        ArrayList localArrayList1 = MylistsSortActivity.this.mylists;
        ArrayList localArrayList2 = MylistsSortActivity.this.mylists;
        k = j - 1;
        localArrayList1.set(j, (Mylist)localArrayList2.get(k));
      }
    }
    
    public int onStartDrag(int paramInt)
    {
      MylistsSortActivity.this.mDraggingPosition = paramInt;
      MylistsSortActivity.this.mListView.invalidateViews();
      return paramInt;
    }
    
    public boolean onStopDrag(int paramInt1, int paramInt2)
    {
      MylistsSortActivity.this.mDraggingPosition = -1;
      MylistsSortActivity.this.mListView.invalidateViews();
      return super.onStopDrag(paramInt1, paramInt2);
    }
  }
  
  class MylistSortAdapter
    extends ArrayAdapter<Mylist>
  {
    private LayoutInflater layoutInflater_;
    
    public MylistSortAdapter(int paramInt, List<Mylist> paramList)
    {
      super(paramList, localList);
      this.layoutInflater_ = ((LayoutInflater)paramInt.getSystemService("layout_inflater"));
    }
    
    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      if (paramView == null) {
        paramView = this.layoutInflater_.inflate(2130903082, null);
      }
      Mylist localMylist = (Mylist)getItem(paramInt);
      TextView localTextView = (TextView)paramView.findViewById(2131492971);
      localTextView.setText(localMylist.getName());
      if (paramInt == MylistsSortActivity.this.mDraggingPosition) {}
      for (int i = 4;; i = 0)
      {
        localTextView.setVisibility(i);
        return paramView;
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.MylistsSortActivity
 * JD-Core Version:    0.7.0.1
 */