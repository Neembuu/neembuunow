package jp.co.asbit.pvstar;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class MylistActivity
  extends VideoListActivity
{
  private static final int IMAGE_PICKER = 100;
  private static final int VIDEO_EDITOR = 300;
  private static final int VIDEO_SORTER = 200;
  private BitmapDrawable[] background = new BitmapDrawable[2];
  private boolean isBackgroundExists = true;
  private Long mylistId;
  
  private void callSelfActivity()
  {
    Intent localIntent = new Intent(this.mContext, MylistActivity.class);
    localIntent.putExtra("MYLIST_ID", this.mylistId);
    localIntent.setFlags(65536);
    startActivity(localIntent);
    finish();
  }
  
  private void editTitle()
  {
    if (getList().size() == 0) {}
    for (;;)
    {
      return;
      Intent localIntent = new Intent(this.mContext, VideosEditActivity.class);
      localIntent.putExtra("MYLIST_ID", this.mylistId);
      startActivityForResult(localIntent, 300);
    }
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if ((paramInt1 == 100) && (paramIntent != null))
    {
      Uri localUri = paramIntent.getData();
      if (localUri != null)
      {
        BackgroundImageTask local2 = new BackgroundImageTask(this.mylistId, this)
        {
          protected void onPostExecute(String paramAnonymousString)
          {
            super.onPostExecute(paramAnonymousString);
            MylistActivity.this.callSelfActivity();
          }
        };
        Uri[] arrayOfUri = new Uri[1];
        arrayOfUri[0] = localUri;
        local2.execute(arrayOfUri);
      }
    }
    for (;;)
    {
      return;
      if ((paramInt1 == 200) || (paramInt1 == 300)) {
        callSelfActivity();
      }
    }
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
    if (this.mylistId.longValue() == -20000L) {
      this.sortButton.setImageResource(2130837572);
    }
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    Mylist localMylist = localVideoDbHelper.getMylist(this.mylistId);
    localVideoDbHelper.close();
    setTitle(localMylist.getName());
    updateListView();
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getMenuInflater().inflate(2131427330, paramMenu);
    return super.onCreateOptionsMenu(paramMenu);
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
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool = false;
    switch (paramMenuItem.getItemId())
    {
    case 2131493019: 
    default: 
      bool = super.onOptionsItemSelected(paramMenuItem);
    }
    for (;;)
    {
      return bool;
      if (selectedRow() == 0)
      {
        Toast.makeText(this.mContext, 2131296378, 0).show();
      }
      else
      {
        new AlertDialog.Builder(this).setTitle(2131296377).setMessage(2131296380).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            VideoDbHelper localVideoDbHelper = new VideoDbHelper(MylistActivity.this.mContext);
            int i = localVideoDbHelper.deleteAllVideo(MylistActivity.this.getSelectedList(), MylistActivity.this.mylistId);
            Context localContext = MylistActivity.this.mContext;
            MylistActivity localMylistActivity = MylistActivity.this;
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = Integer.valueOf(i);
            Toast.makeText(localContext, localMylistActivity.getString(2131296381, arrayOfObject), 0).show();
            localVideoDbHelper.close();
            MylistActivity.this.updateListView();
            MylistActivity.this.clearSelectedRows();
          }
        }).setNegativeButton(2131296382, null).show();
        bool = true;
        continue;
        sortDialog();
        bool = true;
        continue;
        editTitle();
        bool = true;
        continue;
        Intent localIntent = new Intent("android.intent.action.GET_CONTENT");
        localIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(localIntent, getString(2131296374)), 100);
        bool = true;
        continue;
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://sp.image.dooga.org/")));
        bool = true;
        continue;
        BackgroundImageTask.removeBackgroudImage(this.mylistId, this);
        callSelfActivity();
        bool = true;
      }
    }
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
  
  protected void sortDialog()
  {
    if (getList().size() == 0) {}
    for (;;)
    {
      return;
      if (this.mylistId.longValue() == -20000L)
      {
        Toast.makeText(this.mContext, 2131296394, 0).show();
      }
      else
      {
        Intent localIntent = new Intent(this.mContext, MylistSortActivity.class);
        localIntent.putExtra("MYLIST_ID", this.mylistId);
        startActivityForResult(localIntent, 200);
      }
    }
  }
  
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
 * Qualified Name:     jp.co.asbit.pvstar.MylistActivity
 * JD-Core Version:    0.7.0.1
 */