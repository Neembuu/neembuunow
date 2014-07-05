package jp.co.asbit.pvstar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteException;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class VideoEditDialog
  extends AlertDialog
{
  static OnVideoSavedListener callback_;
  private EditText descriptionEdit;
  private Context mContext;
  private EditText nameEdit;
  
  public VideoEditDialog(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    View localView = getInputView();
    this.nameEdit = ((EditText)localView.findViewById(2131492893));
    this.descriptionEdit = ((EditText)localView.findViewById(2131492895));
    setIcon(17301659);
    setView(localView);
  }
  
  public static VideoEditDialog create(Context paramContext, long paramLong, Video paramVideo)
  {
    VideoEditDialog localVideoEditDialog = new VideoEditDialog(paramContext);
    localVideoEditDialog.editVideo(paramLong, paramVideo);
    return localVideoEditDialog;
  }
  
  static void execCallback(String paramString1, String paramString2)
  {
    callback_.onVideoSaved(paramString1, paramString2);
  }
  
  private String getDescription()
  {
    return this.descriptionEdit.getText().toString();
  }
  
  private View getInputView()
  {
    return LayoutInflater.from(this.mContext).inflate(2130903049, null);
  }
  
  private String getName()
  {
    return this.nameEdit.getText().toString();
  }
  
  private void setDescription(String paramString)
  {
    this.descriptionEdit.setText(paramString);
  }
  
  private void setName(String paramString)
  {
    this.nameEdit.setText(paramString);
  }
  
  public void editVideo(final long paramLong, final Video paramVideo)
  {
    setName(paramVideo.getTitle());
    setDescription(paramVideo.getDescription());
    setTitle(getContext().getString(2131296398));
    setButton(-1, getContext().getString(2131296470), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        try
        {
          VideoDbHelper localVideoDbHelper = new VideoDbHelper(VideoEditDialog.this.mContext);
          String str = paramVideo.getId();
          localVideoDbHelper.editVideo(paramLong, str, VideoEditDialog.this.getName(), VideoEditDialog.this.getDescription());
          localVideoDbHelper.close();
          VideoEditDialog.execCallback(VideoEditDialog.this.getName(), VideoEditDialog.this.getDescription());
          return;
        }
        catch (SQLiteException localSQLiteException)
        {
          for (;;)
          {
            Toast.makeText(VideoEditDialog.this.mContext, localSQLiteException.getMessage(), 1).show();
          }
        }
        catch (VideoDbHelper.ValidateErrorException localValidateErrorException)
        {
          for (;;)
          {
            Toast.makeText(VideoEditDialog.this.mContext, localValidateErrorException.getMessage(), 1).show();
          }
        }
      }
    });
    setButton(-2, getContext().getString(2131296382), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
    });
  }
  
  public VideoEditDialog setOnDestoryListener(OnVideoSavedListener paramOnVideoSavedListener)
  {
    callback_ = paramOnVideoSavedListener;
    return this;
  }
  
  public static abstract interface OnVideoSavedListener
  {
    public abstract void onVideoSaved(String paramString1, String paramString2);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.VideoEditDialog
 * JD-Core Version:    0.7.0.1
 */