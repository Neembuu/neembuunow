package jp.co.asbit.pvstar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

public class Drv_VideoRowAdapter
  extends ArrayAdapter<Video>
{
  private LayoutInflater layoutInflater_;
  
  public Drv_VideoRowAdapter(Context paramContext, int paramInt, List<Video> paramList)
  {
    super(paramContext, paramInt, paramList);
    this.layoutInflater_ = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
  }
  
  public void clear()
  {
    ImageCache.clear();
    super.clear();
  }
  
  public void clearImageCache() {}
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    Video localVideo = (Video)getItem(paramInt);
    ViewHolder localViewHolder;
    String str3;
    Bitmap localBitmap;
    if (paramView == null)
    {
      paramView = this.layoutInflater_.inflate(2130903062, null);
      localViewHolder = new ViewHolder();
      localViewHolder.thumbnail = ((ImageView)paramView.findViewById(2131492877));
      localViewHolder.title = ((TextView)paramView.findViewById(2131492879));
      localViewHolder.duration = ((TextView)paramView.findViewById(2131492878));
      localViewHolder.description = ((TextView)paramView.findViewById(2131492945));
      paramView.setTag(localViewHolder);
      String str1 = localVideo.getTitle();
      if ((str1 != null) && (str1.length() > 30)) {
        str1 = str1.substring(0, 30) + "...";
      }
      localViewHolder.title.setText(str1);
      String str2 = localVideo.getDescription();
      if ((str2 != null) && (str2.length() > 30)) {
        str2 = str2.substring(0, 30) + "...";
      }
      localViewHolder.description.setText(str2);
      localViewHolder.duration.setText(localVideo.getDuration());
      str3 = localVideo.getThumbnailUrl();
      if (str3 != null)
      {
        localViewHolder.thumbnail.setTag(str3);
        localViewHolder.thumbnail.setVisibility(4);
        localBitmap = ImageCache.getImage(str3);
        if (localBitmap != null) {
          break label368;
        }
      }
    }
    for (;;)
    {
      try
      {
        ImageDownloadTask localImageDownloadTask = new ImageDownloadTask(localViewHolder.thumbnail);
        URL[] arrayOfURL = new URL[1];
        arrayOfURL[0] = new URL(str3);
        localImageDownloadTask.execute(arrayOfURL);
        if (!localVideo.isChecked()) {
          break label390;
        }
        paramView.setBackgroundColor(Color.rgb(96, 96, 96));
        return paramView;
        localViewHolder = (ViewHolder)paramView.getTag();
      }
      catch (MalformedURLException localMalformedURLException)
      {
        localMalformedURLException.printStackTrace();
        continue;
      }
      catch (RejectedExecutionException localRejectedExecutionException)
      {
        localRejectedExecutionException.printStackTrace();
        continue;
      }
      label368:
      localViewHolder.thumbnail.setImageBitmap(localBitmap);
      localViewHolder.thumbnail.setVisibility(0);
      continue;
      label390:
      paramView.setBackgroundColor(0);
    }
  }
  
  static class ViewHolder
  {
    TextView description;
    TextView duration;
    ImageView thumbnail;
    TextView title;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.Drv_VideoRowAdapter
 * JD-Core Version:    0.7.0.1
 */