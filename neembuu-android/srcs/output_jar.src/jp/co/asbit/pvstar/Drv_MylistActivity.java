package jp.co.asbit.pvstar;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;

public class Drv_MylistActivity
  extends Drv_VideoListActivity
{
  private BitmapDrawable[] background = new BitmapDrawable[2];
  private boolean isBackgroundExists = true;
  private Long mylistId;
  
  protected void onChangeVehicleState(int paramInt)
  {
    super.onChangeVehicleState(paramInt);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    boolean bool = false;
    int i = -1 + paramConfiguration.orientation;
    if ((this.mylistId != null) && (this.background[i] == null) && (this.isBackgroundExists))
    {
      Bitmap localBitmap = BitmapFactory.decodeFile(BackgroundImageTask.getBackgroudImagePath(paramConfiguration.orientation, this.mylistId, this.mContext));
      if (localBitmap == null) {
        break label129;
      }
      this.background[i] = new BitmapDrawable(localBitmap);
      this.background[i].setAlpha(155);
      if (this.background[i] != null) {
        bool = true;
      }
    }
    label129:
    for (this.isBackgroundExists = bool;; this.isBackgroundExists = false)
    {
      if (this.background[i] != null) {
        getWindow().setBackgroundDrawable(this.background[i]);
      }
      super.onConfigurationChanged(paramConfiguration);
      return;
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mylistId = Long.valueOf(getIntent().getLongExtra("MYLIST_ID", -10000L));
    this.mSortButton.setImageResource(2130837572);
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    Mylist localMylist = localVideoDbHelper.getMylist(this.mylistId);
    localVideoDbHelper.close();
    setTitle(localMylist.getName());
    updateListView();
  }
  
  protected void onDestroy()
  {
    if (this.background[0] != null) {
      this.background[0].getBitmap().recycle();
    }
    if (this.background[1] != null) {
      this.background[1].getBitmap().recycle();
    }
    super.onDestroy();
  }
  
  protected void onResume()
  {
    this.background[0] = null;
    this.background[1] = null;
    onConfigurationChanged(getResources().getConfiguration());
    if (this.isBackgroundExists) {
      getListView().setDivider(null);
    }
    super.onResume();
  }
  
  protected void sortDialog() {}
  
  protected void updateListView()
  {
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    try
    {
      ArrayList localArrayList1 = getList();
      ArrayList localArrayList2 = localVideoDbHelper.getVideos(this.mylistId);
      localArrayList1.clear();
      localArrayList1.addAll(localArrayList2);
      getListView().invalidateViews();
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localNullPointerException.printStackTrace();
        localVideoDbHelper.close();
      }
    }
    finally
    {
      localVideoDbHelper.close();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.Drv_MylistActivity
 * JD-Core Version:    0.7.0.1
 */