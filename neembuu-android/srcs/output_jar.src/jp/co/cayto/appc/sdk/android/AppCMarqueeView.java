package jp.co.cayto.appc.sdk.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import jp.co.cayto.appc.sdk.android.common.AppController;
import jp.co.cayto.appc.sdk.android.common.AppImages;
import jp.co.cayto.appc.sdk.android.common.AppPreference;
import jp.co.cayto.appc.sdk.android.entity.HttpData;

public final class AppCMarqueeView
  extends LinearLayout
  implements View.OnClickListener
{
  private final int FP = -1;
  private Context mContext;
  private boolean mCreatedFlag = false;
  private FrameLayout mMainFLayout;
  private TextView mMarqueeTextView;
  private LinearLayout mNewIconLLayout;
  
  public AppCMarqueeView(Context paramContext)
  {
    super(paramContext);
  }
  
  public AppCMarqueeView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Object localObject = null;
    if (paramAttributeSet != null) {}
    try
    {
      String str = paramAttributeSet.getAttributeValue(null, "appc_text_color");
      localObject = str;
    }
    catch (ParseException localParseException)
    {
      label37:
      break label37;
    }
    createView(localObject);
  }
  
  @SuppressLint({"SimpleDateFormat"})
  private String getToday()
  {
    return new SimpleDateFormat("yyyy/MM/dd").format(new Date(Calendar.getInstance().getTimeInMillis()));
  }
  
  private void setView(final View paramView)
  {
    HandlerThread localHandlerThread = new HandlerThread("BGThread");
    localHandlerThread.start();
    new Handler(localHandlerThread.getLooper()).post(new Runnable()
    {
      public void run()
      {
        new Handler(Looper.getMainLooper()).post(new Runnable()
        {
          public void run()
          {
            AppCMarqueeView.this.addView(this.val$view, new LinearLayout.LayoutParams(-1, -2));
            AppCMarqueeView.this.setVisibility(0);
          }
        });
      }
    });
  }
  
  public AppCMarqueeView createView(String paramString)
  {
    if (this.mCreatedFlag) {}
    for (;;)
    {
      return this;
      this.mCreatedFlag = true;
      this.mContext = getContext();
      setVisibility(4);
      setView(getView(AppImages.parseColor(paramString, "#FFFFFF")));
    }
  }
  
  public View getView(int paramInt)
  {
    int i;
    int j;
    int k;
    int m;
    int n;
    if (((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getWidth() > 480)
    {
      i = 30;
      j = 26;
      k = 15;
      m = 12;
      this.mMainFLayout = new FrameLayout(this.mContext);
      BitmapDrawable localBitmapDrawable = new BitmapDrawable(AppImages.getBitmap(25, false, this.mContext));
      localBitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
      this.mMainFLayout.setBackgroundDrawable(localBitmapDrawable);
      this.mMarqueeTextView = new TextView(this.mContext);
      this.mMarqueeTextView.setPadding(k, m, 0, 0);
      this.mMarqueeTextView.setSingleLine();
      this.mMarqueeTextView.setFocusableInTouchMode(true);
      this.mMarqueeTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
      this.mMarqueeTextView.setMarqueeRepeatLimit(100);
      this.mMarqueeTextView.setFocusable(true);
      this.mMarqueeTextView.setGravity(3);
      this.mMarqueeTextView.setTextSize(16.0F);
      this.mMarqueeTextView.setTextColor(paramInt);
      this.mMarqueeTextView.invalidate();
      this.mMarqueeTextView.setSelected(true);
      this.mMainFLayout.addView(this.mMarqueeTextView, -1, -1);
      LinearLayout localLinearLayout = new LinearLayout(this.mContext);
      ImageView localImageView1 = new ImageView(this.mContext);
      Context localContext = this.mContext;
      localImageView1.setImageBitmap(AppImages.getBitmap(i, false, localContext));
      localLinearLayout.addView(localImageView1);
      localLinearLayout.setGravity(5);
      this.mMainFLayout.addView(localLinearLayout);
      n = 0;
      String str1 = AppPreference.getPrefs(this.mContext, "click_date", null);
      if (TextUtils.isEmpty(str1)) {
        break label499;
      }
      String str2 = getToday();
      if (!str1.equals(str2))
      {
        n = 1;
        AppPreference.setPrefs(this.mContext, "click_date", str2);
      }
    }
    for (;;)
    {
      if (n != 0)
      {
        this.mNewIconLLayout = new LinearLayout(this.mContext);
        ImageView localImageView2 = new ImageView(this.mContext);
        localImageView2.setImageBitmap(AppImages.getBitmap(j, false, this.mContext));
        this.mNewIconLLayout.addView(localImageView2);
        this.mNewIconLLayout.setGravity(3);
        this.mNewIconLLayout.setPadding(10, 0, 0, 0);
        this.mMainFLayout.addView(this.mNewIconLLayout);
        this.mMarqueeTextView.setPadding(k + this.mNewIconLLayout.getWidth(), m, 0, 0);
      }
      this.mMainFLayout.setOnClickListener(this);
      CPIListTask localCPIListTask = new CPIListTask(this.mContext);
      localCPIListTask.execute(new Void[0]);
      return this.mMainFLayout;
      i = 31;
      j = 27;
      k = 10;
      m = 5;
      break;
      label499:
      n = 1;
    }
  }
  
  public void onClick(View paramView)
  {
    if (TextUtils.isEmpty(AppPreference.getPrefs(this.mContext, "click_date", null)))
    {
      AppPreference.setPrefs(this.mContext, "click_date", getToday());
      if (this.mNewIconLLayout != null) {
        this.mMainFLayout.removeView(this.mNewIconLLayout);
      }
    }
    Intent localIntent = new Intent(this.mContext, AppCWebActivity.class);
    localIntent.putExtra("type", "pr_list");
    localIntent.putExtra("pr_type", "marquee_web");
    this.mContext.startActivity(localIntent);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
  }
  
  class CPIListTask
    extends AsyncTask<Void, Void, String>
  {
    private Context context;
    
    public CPIListTask(Context paramContext)
    {
      this.context = paramContext;
    }
    
    protected String doInBackground(Void... paramVarArgs)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("m", "mv");
      localHashMap.put("linktag", "marquee");
      ArrayList localArrayList = AppController.createIncetance(this.context).getCPIList(this.context, localHashMap).getAppsList();
      if (localArrayList.size() > 0) {}
      for (String str = "　　　" + (String)((HashMap)localArrayList.get(0)).get("markee_text");; str = null) {
        return str;
      }
    }
    
    protected void onPostExecute(String paramString)
    {
      if (paramString != null) {
        AppCMarqueeView.this.mMarqueeTextView.setText(paramString);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.AppCMarqueeView
 * JD-Core Version:    0.7.0.1
 */