package jp.co.asbit.pvstar;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteException;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BookmarkEditDialog
  extends AlertDialog
{
  static OnBookmarkSavedListener callback_;
  private EditText descriptionEdit;
  private Context mContext;
  private EditText nameEdit;
  
  public BookmarkEditDialog(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    View localView = getInputView();
    this.nameEdit = ((EditText)localView.findViewById(2131492893));
    this.descriptionEdit = ((EditText)localView.findViewById(2131492895));
    setIcon(17301659);
    setView(localView);
    setButton(-3, paramContext.getString(2131296382), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
    });
  }
  
  public static BookmarkEditDialog create(Context paramContext, Playlist paramPlaylist)
  {
    BookmarkEditDialog localBookmarkEditDialog = new BookmarkEditDialog(paramContext);
    localBookmarkEditDialog.editBookmark(paramPlaylist);
    return localBookmarkEditDialog;
  }
  
  static void execCallback()
  {
    callback_.onBookmarkSaved();
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
  
  public void editBookmark(final Playlist paramPlaylist)
  {
    setName(paramPlaylist.getTitle());
    setDescription(paramPlaylist.getDescription());
    setTitle(this.mContext.getString(2131296389));
    setButton(-1, getContext().getString(2131296470), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        try
        {
          VideoDbHelper localVideoDbHelper = new VideoDbHelper(BookmarkEditDialog.this.mContext);
          Playlist localPlaylist = new Playlist();
          localPlaylist.setId(paramPlaylist.getId());
          localPlaylist.setListType(paramPlaylist.getListType());
          localPlaylist.setSearchEngine(paramPlaylist.getSearchEngine());
          localPlaylist.setThumbnailUrl(paramPlaylist.getThumbnailUrl());
          localPlaylist.setTitle(BookmarkEditDialog.this.getName());
          localPlaylist.setDescription(BookmarkEditDialog.this.getDescription());
          localVideoDbHelper.editBookmark(localPlaylist);
          localVideoDbHelper.close();
          BookmarkEditDialog.execCallback();
          return;
        }
        catch (VideoDbHelper.ValidateErrorException localValidateErrorException)
        {
          for (;;)
          {
            Toast.makeText(BookmarkEditDialog.this.mContext, localValidateErrorException.getMessage(), 1).show();
          }
        }
        catch (SQLiteException localSQLiteException)
        {
          for (;;)
          {
            Toast.makeText(BookmarkEditDialog.this.mContext, localSQLiteException.getMessage(), 1).show();
          }
        }
      }
    });
    setButton(-2, this.mContext.getString(2131296471), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        new AlertDialog.Builder(BookmarkEditDialog.this.mContext).setMessage(2131296391).setCancelable(false).setTitle(2131296392).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            VideoDbHelper localVideoDbHelper = new VideoDbHelper(BookmarkEditDialog.this.mContext);
            localVideoDbHelper.deleteBookmark(this.val$playlist);
            localVideoDbHelper.close();
            BookmarkEditDialog.execCallback();
          }
        }).setNegativeButton(2131296382, null).show();
      }
    });
  }
  
  public BookmarkEditDialog setOnDestoryListener(OnBookmarkSavedListener paramOnBookmarkSavedListener)
  {
    callback_ = paramOnBookmarkSavedListener;
    return this;
  }
  
  public static abstract interface OnBookmarkSavedListener
  {
    public abstract void onBookmarkSaved();
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.BookmarkEditDialog
 * JD-Core Version:    0.7.0.1
 */