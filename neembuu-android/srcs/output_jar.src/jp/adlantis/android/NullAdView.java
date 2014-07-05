package jp.adlantis.android;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NullAdView
  extends RelativeLayout
{
  public NullAdView(Context paramContext)
  {
    super(paramContext);
    setupView();
  }
  
  public NullAdView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setupView();
  }
  
  protected TextView createLabelTextView()
  {
    TextView localTextView = new TextView(getContext());
    localTextView.setText(AdlantisView.class.getSimpleName());
    localTextView.setTextSize(20.0F);
    localTextView.setTextColor(-1);
    localTextView.setGravity(17);
    return localTextView;
  }
  
  protected void setupView()
  {
    setBackgroundColor(ViewSettings.defaultBackgroundColor());
    addView(createLabelTextView(), new ViewGroup.LayoutParams(-1, -1));
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.adlantis.android.NullAdView
 * JD-Core Version:    0.7.0.1
 */