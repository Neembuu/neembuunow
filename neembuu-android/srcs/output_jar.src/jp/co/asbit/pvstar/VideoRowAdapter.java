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

public class VideoRowAdapter
  extends ArrayAdapter<Video>
{
  protected LayoutInflater layoutInflater_;
  
  public VideoRowAdapter(Context paramContext, int paramInt, List<Video> paramList)
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
    String str;
    Bitmap localBitmap;
    if (paramView == null)
    {
      paramView = this.layoutInflater_.inflate(2130903096, null);
      localViewHolder = new ViewHolder();
      localViewHolder.thumbnail = ((ImageView)paramView.findViewById(2131492877));
      localViewHolder.title = ((TextView)paramView.findViewById(2131492879));
      localViewHolder.duration = ((TextView)paramView.findViewById(2131492878));
      localViewHolder.description = ((TextView)paramView.findViewById(2131492945));
      paramView.setTag(localViewHolder);
      localViewHolder.title.setText(localVideo.getTitle());
      localViewHolder.description.setText(localVideo.getDescription());
      localViewHolder.duration.setText(localVideo.getDuration());
      str = localVideo.getThumbnailUrl();
      if (str != null)
      {
        localViewHolder.thumbnail.setTag(str);
        localViewHolder.thumbnail.setVisibility(4);
        localBitmap = ImageCache.getImage(str);
        if (localBitmap != null) {
          break label274;
        }
      }
    }
    for (;;)
    {
      try
      {
        ImageDownloadTask localImageDownloadTask = new ImageDownloadTask(localViewHolder.thumbnail);
        URL[] arrayOfURL = new URL[1];
        arrayOfURL[0] = new URL(str);
        localImageDownloadTask.execute(arrayOfURL);
        if (!localVideo.isChecked()) {
          break label296;
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
      label274:
      localViewHolder.thumbnail.setImageBitmap(localBitmap);
      localViewHolder.thumbnail.setVisibility(0);
      continue;
      label296:
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
 * Qualified Name:     jp.co.asbit.pvstar.VideoRowAdapter
 * JD-Core Version:    0.7.0.1
 */