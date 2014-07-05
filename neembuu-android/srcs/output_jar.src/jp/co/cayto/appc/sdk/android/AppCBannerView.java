package jp.co.cayto.appc.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jp.co.cayto.appc.sdk.android.common.AppController;
import jp.co.cayto.appc.sdk.android.common.AppDB;
import jp.co.cayto.appc.sdk.android.common.AppImages;
import jp.co.cayto.appc.sdk.android.entity.HttpData;

public final class AppCBannerView
  extends LinearLayout
{
  private static final int MP = -1;
  private static final int REPEAT_INTERVAL = 10000;
  private static final int WC = -2;
  private boolean mCreatedFlag = false;
  private int mMainHeight;
  private int mMainWidth;
  private int mPageCount;
  
  public AppCBannerView(Context paramContext)
  {
    super(paramContext);
    createView();
  }
  
  public AppCBannerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    createView();
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
            AppCBannerView.this.addView(this.val$view, new LinearLayout.LayoutParams(-2, -2));
            AppCBannerView.this.setVisibility(0);
          }
        });
      }
    });
  }
  
  public AppCBannerView createView()
  {
    if (this.mCreatedFlag) {}
    for (;;)
    {
      return this;
      this.mCreatedFlag = true;
      setVisibility(4);
      setView(getView());
    }
  }
  
  public View getView()
  {
    Context localContext = getContext();
    ViewPager localViewPager = new ViewPager(localContext);
    localViewPager.setId(100);
    ArrayList localArrayList = new AppDB(localContext).loadClickHistorys();
    Display localDisplay = ((WindowManager)localContext.getSystemService("window")).getDefaultDisplay();
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    ((Activity)localContext).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    int i;
    if (((Activity)localContext).getResources().getConfiguration().orientation == 2)
    {
      i = 1;
      if (i == 0) {
        break label188;
      }
      this.mMainWidth = ((int)(0.9F * localDisplay.getHeight()));
    }
    for (this.mMainHeight = ((int)(0.9F * (localDisplay.getWidth() / 11)));; this.mMainHeight = (localDisplay.getHeight() / 11))
    {
      new CreateBannerTask(localContext, localViewPager, localArrayList).execute(new Void[0]);
      LinearLayout localLinearLayout = new LinearLayout(localContext);
      localLinearLayout.addView(localViewPager, this.mMainWidth, this.mMainHeight);
      return localLinearLayout;
      i = 0;
      break;
      label188:
      this.mMainWidth = localDisplay.getWidth();
    }
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
  }
  
  class CreateBannerTask
    extends AsyncTask<Void, Void, Void>
  {
    private ArrayList<String> adAppsIds;
    private Context context;
    private List<HashMap<String, String>> results;
    private boolean reverseFlg;
    private ViewPager viewPager;
    
    public CreateBannerTask(ViewPager paramViewPager, ArrayList<String> paramArrayList)
    {
      this.context = paramViewPager;
      this.viewPager = paramArrayList;
      Object localObject;
      this.adAppsIds = localObject;
      this.results = null;
      this.reverseFlg = false;
    }
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("m", "vp");
      localHashMap.put("linktag", "banner");
      this.results = AppController.createIncetance(this.context).getCPIList(this.context, localHashMap).getAppsList();
      AppCBannerView.this.mPageCount = this.results.size();
      return null;
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      AppCBannerView.MyPagerAdapter localMyPagerAdapter = new AppCBannerView.MyPagerAdapter(AppCBannerView.this, this.context, this.viewPager, this.adAppsIds, this.results);
      this.viewPager.setAdapter(localMyPagerAdapter);
      final Handler localHandler = new Handler();
      localHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          int i = AppCBannerView.CreateBannerTask.this.viewPager.getCurrentItem();
          int j;
          if (!AppCBannerView.CreateBannerTask.this.reverseFlg)
          {
            j = i + 1;
            if (j < AppCBannerView.this.mPageCount) {
              break label81;
            }
            j -= 2;
            AppCBannerView.CreateBannerTask.this.reverseFlg = true;
          }
          for (;;)
          {
            AppCBannerView.CreateBannerTask.this.viewPager.setCurrentItem(j);
            localHandler.postDelayed(this, 10000L);
            return;
            j = i - 1;
            break;
            label81:
            if (j < 0)
            {
              j += 2;
              AppCBannerView.CreateBannerTask.this.reverseFlg = false;
            }
          }
        }
      }, 10000L);
    }
  }
  
  class InnerView
    implements View.OnClickListener
  {
    private ArrayList<String> adAppsIds;
    private Context context;
    private List<HashMap<String, String>> results;
    private ViewPager viewPager;
    
    public InnerView(ViewPager paramViewPager, ArrayList<String> paramArrayList, List<HashMap<String, String>> paramList)
    {
      this.context = paramViewPager;
      this.viewPager = paramArrayList;
      this.adAppsIds = paramList;
      Object localObject;
      this.results = localObject;
    }
    
    private void setImage(final Context paramContext, final String paramString, final ImageView paramImageView, final int paramInt1, final int paramInt2)
    {
      new Thread(new Runnable()
      {
        public void run()
        {
          try
          {
            final Bitmap localBitmap = AppImages.resizeBitmapToSpecifiedSize(AppImages.getBitmapIcon(paramString, false, paramContext), paramInt1, paramInt2);
            this.val$handler.post(new Runnable()
            {
              public void run()
              {
                this.val$imageView.startAnimation(AppImages.getIconAnimation());
                this.val$imageView.setImageBitmap(localBitmap);
              }
            });
            label45:
            return;
          }
          catch (Exception localException)
          {
            break label45;
          }
        }
      }).start();
    }
    
    public View getInnerView(int paramInt)
    {
      HashMap localHashMap = (HashMap)this.results.get(paramInt);
      FrameLayout localFrameLayout = new FrameLayout(this.context);
      ImageView localImageView1 = new ImageView(this.context);
      localImageView1.setScaleType(ImageView.ScaleType.FIT_START);
      String str1 = (String)localHashMap.get("ad_apps_id");
      localImageView1.setId(paramInt);
      localImageView1.setTag(str1);
      localImageView1.setBackgroundColor(-16777216);
      String str2 = "http://android.giveapp.jp/images/banner/appc/" + str1 + ".png";
      localImageView1.setOnClickListener(this);
      localFrameLayout.addView(localImageView1, new LinearLayout.LayoutParams(-1, -2));
      setImage(this.context, str2, localImageView1, AppCBannerView.this.mMainWidth, AppCBannerView.this.mMainHeight);
      if (this.adAppsIds.indexOf(str1) == -1)
      {
        ImageView localImageView2 = new ImageView(this.context);
        localImageView2.setImageBitmap(AppImages.getBitmap(0, false, this.context));
        localFrameLayout.addView(localImageView2);
      }
      return localFrameLayout;
    }
    
    public void onClick(View paramView)
    {
      String str = paramView.getTag().toString();
      if (this.adAppsIds.indexOf(str) == -1) {
        this.adAppsIds.add(str);
      }
      this.viewPager.getAdapter().notifyDataSetChanged();
      AppDB localAppDB = new AppDB(this.context);
      if (!localAppDB.isClickHistory(str)) {
        localAppDB.createClickHistory(str);
      }
      int i = paramView.getId();
      HashMap localHashMap = new HashMap();
      localHashMap.put("target_package", (String)((HashMap)this.results.get(i)).get("package"));
      localHashMap.put("ad_apps_id", (String)((HashMap)this.results.get(i)).get("ad_apps_id"));
      localHashMap.put("redirect_url", (String)((HashMap)this.results.get(i)).get("redirect_url"));
      AppController.createIncetance(this.context).registCPIMoveMarket(this.context, localHashMap, "banner");
    }
  }
  
  class MyPagerAdapter
    extends PagerAdapter
  {
    private ArrayList<String> adAppsIds;
    private Context context;
    private List<HashMap<String, String>> results;
    private ViewPager viewPager;
    
    public MyPagerAdapter(ViewPager paramViewPager, ArrayList<String> paramArrayList, List<HashMap<String, String>> paramList)
    {
      this.context = paramViewPager;
      this.viewPager = paramArrayList;
      this.adAppsIds = paramList;
      Object localObject;
      this.results = localObject;
    }
    
    public void destroyItem(View paramView, int paramInt, Object paramObject)
    {
      ((ViewPager)paramView).removeView((View)paramObject);
    }
    
    public int getCount()
    {
      return AppCBannerView.this.mPageCount;
    }
    
    public int getItemPosition(Object paramObject)
    {
      return -2;
    }
    
    public Object instantiateItem(View paramView, int paramInt)
    {
      View localView = new AppCBannerView.InnerView(AppCBannerView.this, this.context, this.viewPager, this.adAppsIds, this.results).getInnerView(paramInt);
      ((ViewPager)paramView).addView(localView, -1, -2);
      return localView;
    }
    
    public boolean isViewFromObject(View paramView, Object paramObject)
    {
      if (paramView == paramObject) {}
      for (boolean bool = true;; bool = false) {
        return bool;
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.AppCBannerView
 * JD-Core Version:    0.7.0.1
 */