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

public class MylistEditDialog
  extends AlertDialog
{
  static OnMylistSavedListener callback_;
  private static long mylistId;
  private EditText descriptionEdit;
  private Context mContext;
  private EditText nameEdit;
  
  public MylistEditDialog(Context paramContext)
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
  
  public static MylistEditDialog create(Context paramContext, Mylist paramMylist)
  {
    MylistEditDialog localMylistEditDialog = new MylistEditDialog(paramContext);
    if (paramMylist == null) {
      localMylistEditDialog.createMylist();
    }
    for (;;)
    {
      return localMylistEditDialog;
      localMylistEditDialog.editMylist(paramMylist);
    }
  }
  
  static void execCallback()
  {
    callback_.onMylistSaved(mylistId);
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
  
  public void createMylist()
  {
    setTitle(getContext().getString(2131296383));
    setButton(-1, getContext().getString(2131296470), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        try
        {
          VideoDbHelper localVideoDbHelper = new VideoDbHelper(MylistEditDialog.this.mContext);
          MylistEditDialog.mylistId = localVideoDbHelper.makeMylist(MylistEditDialog.this.getName(), MylistEditDialog.this.getDescription()).longValue();
          localVideoDbHelper.close();
          MylistEditDialog.execCallback();
          return;
        }
        catch (VideoDbHelper.ValidateErrorException localValidateErrorException)
        {
          for (;;)
          {
            Toast.makeText(MylistEditDialog.this.mContext, localValidateErrorException.getMessage(), 1).show();
          }
        }
        catch (SQLiteException localSQLiteException)
        {
          for (;;)
          {
            Toast.makeText(MylistEditDialog.this.mContext, localSQLiteException.getMessage(), 1).show();
          }
        }
        catch (VideoDbHelper.MaxMylistCountException localMaxMylistCountException)
        {
          for (;;)
          {
            Toast.makeText(MylistEditDialog.this.mContext, localMaxMylistCountException.getMessage(), 1).show();
          }
        }
        catch (NullPointerException localNullPointerException)
        {
          for (;;)
          {
            Toast.makeText(MylistEditDialog.this.mContext, localNullPointerException.getMessage(), 1).show();
          }
        }
      }
    });
  }
  
  public void editMylist(final Mylist paramMylist)
  {
    setName(paramMylist.getName());
    setDescription(paramMylist.getDescription());
    setTitle(getContext().getString(2131296384));
    setButton(-1, getContext().getString(2131296470), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        try
        {
          VideoDbHelper localVideoDbHelper = new VideoDbHelper(MylistEditDialog.this.mContext);
          MylistEditDialog.mylistId = paramMylist.getId();
          localVideoDbHelper.editMylist(Long.valueOf(MylistEditDialog.mylistId), MylistEditDialog.this.getName(), MylistEditDialog.this.getDescription());
          localVideoDbHelper.close();
          MylistEditDialog.execCallback();
          return;
        }
        catch (VideoDbHelper.ValidateErrorException localValidateErrorException)
        {
          for (;;)
          {
            Toast.makeText(MylistEditDialog.this.mContext, localValidateErrorException.getMessage(), 1).show();
          }
        }
        catch (SQLiteException localSQLiteException)
        {
          for (;;)
          {
            Toast.makeText(MylistEditDialog.this.mContext, localSQLiteException.getMessage(), 1).show();
          }
        }
      }
    });
    setButton(-2, getContext().getString(2131296471), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        new AlertDialog.Builder(MylistEditDialog.this.mContext).setMessage(2131296386).setCancelable(false).setTitle(2131296387).setPositiveButton(2131296470, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            MylistEditDialog.mylistId = this.val$mylist.getId();
            VideoDbHelper localVideoDbHelper = new VideoDbHelper(MylistEditDialog.this.mContext);
            localVideoDbHelper.deleteMylist(Long.valueOf(this.val$mylist.getId()));
            localVideoDbHelper.close();
            BackgroundImageTask.removeBackgroudImage(Long.valueOf(MylistEditDialog.mylistId), MylistEditDialog.this.mContext);
            MylistEditDialog.execCallback();
          }
        }).setNegativeButton(2131296382, null).show();
      }
    });
  }
  
  public MylistEditDialog setOnDestoryListener(OnMylistSavedListener paramOnMylistSavedListener)
  {
    callback_ = paramOnMylistSavedListener;
    return this;
  }
  
  public static abstract interface OnMylistSavedListener
  {
    public abstract void onMylistSaved(long paramLong);
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.MylistEditDialog
 * JD-Core Version:    0.7.0.1
 */