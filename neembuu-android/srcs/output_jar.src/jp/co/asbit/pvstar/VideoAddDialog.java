package jp.co.asbit.pvstar;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.util.ArrayList;

public class VideoAddDialog
  extends AlertDialog.Builder
{
  static OnVideoAddedListener callback_;
  private Context mContext;
  private ArrayList<Mylist> mylists;
  
  protected VideoAddDialog(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    VideoDbHelper localVideoDbHelper = new VideoDbHelper(this.mContext);
    this.mylists = localVideoDbHelper.getMylistsExcludeHistory();
    localVideoDbHelper.close();
    int i = this.mylists.size();
    CharSequence[] arrayOfCharSequence = new CharSequence[i + 1];
    for (int j = 0;; j++)
    {
      if (j >= i)
      {
        arrayOfCharSequence[i] = paramContext.getString(2131296383);
        setTitle(2131296432);
        setItems(arrayOfCharSequence, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            if (VideoAddDialog.this.mylists.size() == paramAnonymousInt) {
              MylistEditDialog.create(VideoAddDialog.this.mContext, null).setOnDestoryListener(new MylistEditDialog.OnMylistSavedListener()
              {
                public void onMylistSaved(long paramAnonymous2Long)
                {
                  VideoAddDialog.execCallback(paramAnonymous2Long);
                }
              }).show();
            }
            for (;;)
            {
              return;
              VideoAddDialog.execCallback(((Mylist)VideoAddDialog.this.mylists.get(paramAnonymousInt)).getId());
            }
          }
        });
        return;
      }
      arrayOfCharSequence[j] = ((Mylist)this.mylists.get(j)).getName();
    }
  }
  
  static void execCallback(long paramLong)
  {
    callback_.onVideoAdded(paramLong);
  }
  
  public VideoAddDialog setOnVideoAddedListener(OnVideoAddedListener paramOnVideoAddedListener)
  {
    callback_ = paramOnVideoAddedListener;
    return this;
  }
  
  public static abstract interface OnVideoAddedListener
  {
    public abstract void onVideoAdded(long paramLong);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.VideoAddDialog
 * JD-Core Version:    0.7.0.1
 */