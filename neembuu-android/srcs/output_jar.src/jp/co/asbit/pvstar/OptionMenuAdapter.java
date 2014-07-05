package jp.co.asbit.pvstar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

public class OptionMenuAdapter
  extends ArrayAdapter<OptionMenuItem>
{
  private LayoutInflater layoutInflater_;
  
  public OptionMenuAdapter(Context paramContext, int paramInt, List<OptionMenuItem> paramList)
  {
    super(paramContext, paramInt, paramList);
    this.layoutInflater_ = ((LayoutInflater)paramContext.getSystemService("layout_inflater"));
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    OptionMenuItem localOptionMenuItem = (OptionMenuItem)getItem(paramInt);
    if (paramView == null) {
      paramView = this.layoutInflater_.inflate(2130903074, null);
    }
    ((ImageView)paramView.findViewById(2131492960)).setImageResource(localOptionMenuItem.getLeftIcon());
    ((TextView)paramView.findViewById(2131492961)).setText(localOptionMenuItem.getTitle());
    return paramView;
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.asbit.pvstar.OptionMenuAdapter
 * JD-Core Version:    0.7.0.1
 */