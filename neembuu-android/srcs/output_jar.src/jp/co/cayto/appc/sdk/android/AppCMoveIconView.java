package jp.co.cayto.appc.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import jp.co.cayto.appc.sdk.android.common.AppController;
import jp.co.cayto.appc.sdk.android.common.AppImages;
import jp.co.cayto.appc.sdk.android.entity.HttpData;
import jp.co.cayto.appc.sdk.android.resources.Texts;
import jp.co.cayto.appc.sdk.android.resources.Texts.ITexts;

public final class AppCMoveIconView
  extends LinearLayout
{
  private static final String ICON_INFO_TYPE_EMPTY = "EMPTY";
  private static final String ICON_INFO_TYPE_WEB_ACTIVITY = "WEB_ACTIVITY";
  private static final int MP = -1;
  private static final String PR_TYPE = "move_icon";
  private static final int REPEAT_INTERVAL = 7500;
  private static final int TEXT_LENGTH = 10;
  private static final int WC = -2;
  private boolean mCreatedFlag = false;
  private int mIconCount;
  private int mPageCount;
  private SizeInfo mSizeInfo;
  private int mSkinColor;
  private int mTextColor;
  
  public AppCMoveIconView(Context paramContext)
  {
    super(paramContext);
  }
  
  public AppCMoveIconView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    String str1 = null;
    Object localObject = null;
    if (paramAttributeSet != null) {}
    try
    {
      str1 = paramAttributeSet.getAttributeValue(null, "appc_skin_color");
      String str2 = paramAttributeSet.getAttributeValue(null, "appc_text_color");
      localObject = str2;
    }
    catch (ParseException localParseException)
    {
      label45:
      break label45;
    }
    createView(str1, localObject);
  }
  
  public AppCMoveIconView(Context paramContext, String paramString1, String paramString2)
  {
    super(paramContext);
    createView(paramString1, paramString2);
  }
  
  private void setView(final FrameLayout paramFrameLayout)
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
            AppCMoveIconView.this.addView(this.val$view, new ViewGroup.LayoutParams(-2, -2));
            AppCMoveIconView.this.setVisibility(0);
          }
        });
      }
    });
  }
  
  public AppCMoveIconView createView(String paramString1, String paramString2)
  {
    if (this.mCreatedFlag) {}
    for (;;)
    {
      return this;
      this.mCreatedFlag = true;
      setVisibility(4);
      this.mSkinColor = AppImages.parseColor(paramString1, "#333333");
      this.mSkinColor = Color.argb(68, Color.red(this.mSkinColor), Color.green(this.mSkinColor), Color.blue(this.mSkinColor));
      this.mTextColor = AppImages.parseColor(paramString2, "#FFFFFF");
      setView(getView());
    }
  }
  
  public FrameLayout getView()
  {
    Context localContext = getContext();
    ViewPager localViewPager = new ViewPager(localContext);
    localViewPager.setId(100);
    Display localDisplay = ((WindowManager)localContext.getSystemService("window")).getDefaultDisplay();
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    ((Activity)localContext).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    float f1 = getResources().getDisplayMetrics().scaledDensity;
    int i;
    int j;
    label109:
    SizeInfo localSizeInfo;
    float f2;
    if (((Activity)localContext).getResources().getConfiguration().orientation == 2)
    {
      i = 1;
      if (i == 0) {
        break label368;
      }
      this.mIconCount = 5;
      j = localDisplay.getHeight();
      this.mSizeInfo = new SizeInfo();
      this.mSizeInfo.mIconSize = ((int)(0.9F * (j / (2 + this.mIconCount))));
      localSizeInfo = this.mSizeInfo;
      f2 = this.mSizeInfo.mIconSize;
      if (i == 0) {
        break label382;
      }
    }
    label368:
    label382:
    for (int k = 18;; k = 25)
    {
      localSizeInfo.mTextWidth = ((int)(f2 + f1 * k));
      this.mSizeInfo.mTextHeight = ((int)(13.0F * f1));
      this.mSizeInfo.mMainWidth = j;
      this.mSizeInfo.mMainHeight = (this.mSizeInfo.mIconSize + this.mSizeInfo.mTextHeight + (int)(3.0F * f1));
      this.mSizeInfo.mTextSize = ((int)(3.0F * f1));
      this.mSizeInfo.mTextSize = 8;
      new CreateMoveIconTask(localContext, localViewPager).execute(new Void[0]);
      FrameLayout localFrameLayout = new FrameLayout(localContext);
      BitmapDrawable localBitmapDrawable = new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSize(AppImages.getBitmap(32, false, localContext), this.mSizeInfo.mMainWidth / 2, this.mSizeInfo.mMainHeight / 2));
      localBitmapDrawable.setColorFilter(this.mSkinColor, PorterDuff.Mode.LIGHTEN);
      localFrameLayout.setBackgroundDrawable(localBitmapDrawable);
      localFrameLayout.addView(localViewPager, this.mSizeInfo.mMainWidth, this.mSizeInfo.mMainHeight);
      return localFrameLayout;
      i = 0;
      break;
      this.mIconCount = 4;
      j = localDisplay.getWidth();
      break label109;
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
  
  class CreateMoveIconTask
    extends AsyncTask<Void, Void, ArrayList<ArrayList<ImageView>>>
  {
    private Context context;
    private ViewPager viewPager;
    
    public CreateMoveIconTask(Context paramContext, ViewPager paramViewPager)
    {
      this.context = paramContext;
      this.viewPager = paramViewPager;
    }
    
    private ImageView getImageViewApp(HashMap<String, String> paramHashMap)
    {
      AppCMoveIconView.IconInfo localIconInfo = new AppCMoveIconView.IconInfo(AppCMoveIconView.this);
      localIconInfo.adAppsId = ((String)paramHashMap.get("ad_apps_id"));
      localIconInfo.imageUrl = ((String)paramHashMap.get("icon_url"));
      localIconInfo.pkg = ((String)paramHashMap.get("package"));
      localIconInfo.redirectUrl = ((String)paramHashMap.get("redirect_url"));
      localIconInfo.appName = ((String)paramHashMap.get("app_name")).replaceAll("\n", "").trim();
      localIconInfo.changeFlg = true;
      ImageView localImageView = new ImageView(this.context);
      localImageView.setTag(localIconInfo);
      return localImageView;
    }
    
    private ImageView getImageViewEmpty()
    {
      AppCMoveIconView.IconInfo localIconInfo = new AppCMoveIconView.IconInfo(AppCMoveIconView.this);
      localIconInfo.adAppsId = "EMPTY";
      localIconInfo.changeFlg = false;
      ImageView localImageView = new ImageView(this.context);
      localImageView.setTag(localIconInfo);
      return localImageView;
    }
    
    private ImageView getImageViewWeb()
    {
      AppCMoveIconView.IconInfo localIconInfo = new AppCMoveIconView.IconInfo(AppCMoveIconView.this);
      localIconInfo.adAppsId = "WEB_ACTIVITY";
      localIconInfo.changeFlg = true;
      localIconInfo.appName = new Texts(this.context).get.テキスト_おすすめアプリ();
      ImageView localImageView = new ImageView(this.context);
      localImageView.setTag(localIconInfo);
      return localImageView;
    }
    
    private void setRefresh(final AppCMoveIconView.MoveIconPagerAdapter paramMoveIconPagerAdapter)
    {
      final Handler localHandler = new Handler();
      localHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          int i = AppCMoveIconView.CreateMoveIconTask.this.viewPager.getCurrentItem();
          int j;
          do
          {
            j = new Random(System.currentTimeMillis()).nextInt(AppCMoveIconView.this.mPageCount);
          } while (i == j);
          ArrayList localArrayList1 = AppCMoveIconView.MoveIconPagerAdapter.access$0(paramMoveIconPagerAdapter);
          ArrayList localArrayList2 = (ArrayList)localArrayList1.get(i);
          ArrayList localArrayList3 = (ArrayList)localArrayList1.get(j);
          int k = new Random(System.currentTimeMillis()).nextInt(localArrayList2.size());
          ImageView localImageView1 = (ImageView)localArrayList2.get(k);
          if (((AppCMoveIconView.IconInfo)localImageView1.getTag()).adAppsId.equals("EMPTY"))
          {
            k = 0;
            localImageView1 = (ImageView)localArrayList2.get(0);
          }
          ((AppCMoveIconView.IconInfo)localImageView1.getTag()).changeFlg = true;
          int m = new Random(System.currentTimeMillis()).nextInt(localArrayList3.size());
          ImageView localImageView2 = (ImageView)localArrayList3.get(m);
          if (((AppCMoveIconView.IconInfo)localImageView2.getTag()).adAppsId.equals("EMPTY"))
          {
            m = 0;
            localImageView2 = (ImageView)localArrayList3.get(0);
          }
          ((AppCMoveIconView.IconInfo)localImageView2.getTag()).changeFlg = true;
          localArrayList2.set(k, localImageView2);
          localArrayList3.set(m, localImageView1);
          paramMoveIconPagerAdapter.notifyDataSetChanged();
          localHandler.postDelayed(this, 7500L);
        }
      }, 7500L);
    }
    
    protected ArrayList<ArrayList<ImageView>> doInBackground(Void... paramVarArgs)
    {
      HashMap localHashMap1 = new HashMap();
      localHashMap1.put("m", "bb");
      localHashMap1.put("linktag", "move_icon");
      ArrayList localArrayList1 = AppController.createIncetance(this.context).getCPIList(this.context, localHashMap1).getAppsList();
      ArrayList localArrayList2 = new ArrayList();
      ArrayList localArrayList3;
      int i;
      if ((localArrayList1 == null) || (localArrayList1.isEmpty()))
      {
        localArrayList3 = new ArrayList();
        localArrayList3.add(getImageViewWeb());
        i = 0;
        if (i >= -1 + AppCMoveIconView.this.mIconCount)
        {
          localArrayList2.add(localArrayList3);
          AppCMoveIconView.this.mPageCount = 1;
        }
      }
      ArrayList localArrayList4;
      ArrayList localArrayList5;
      PackageManager localPackageManager;
      Iterator localIterator;
      label174:
      int i2;
      label223:
      ArrayList localArrayList6;
      int k;
      do
      {
        return localArrayList2;
        localArrayList3.add(getImageViewEmpty());
        i++;
        break;
        localArrayList4 = new ArrayList();
        localArrayList5 = new ArrayList();
        localPackageManager = this.context.getPackageManager();
        localIterator = localArrayList1.iterator();
        if (localIterator.hasNext()) {
          break label355;
        }
        if (localArrayList4.size() < -1 + AppCMoveIconView.this.mIconCount)
        {
          int i1 = -1 + AppCMoveIconView.this.mIconCount - localArrayList4.size();
          i2 = 0;
          if (i2 < i1) {
            break label412;
          }
        }
        HashMap localHashMap3 = new HashMap();
        localHashMap3.put("ad_apps_id", "WEB_ACTIVITY");
        localArrayList4.add(localHashMap3);
        int j = localArrayList4.size();
        AppCMoveIconView.this.mPageCount = ((-1 + (j + AppCMoveIconView.this.mIconCount)) / AppCMoveIconView.this.mIconCount);
        localArrayList6 = new ArrayList();
        k = 0;
        if (k < j) {
          break label434;
        }
      } while (localArrayList6.size() <= 0);
      int m = AppCMoveIconView.this.mIconCount - localArrayList6.size();
      for (int n = 0;; n++)
      {
        for (;;)
        {
          if (n < m) {
            break label531;
          }
          localArrayList2.add(localArrayList6);
          break;
          label355:
          HashMap localHashMap2 = (HashMap)localIterator.next();
          String str = (String)localHashMap2.get("package");
          try
          {
            localPackageManager.getApplicationInfo(str, 0);
            localArrayList5.add(localHashMap2);
          }
          catch (PackageManager.NameNotFoundException localNameNotFoundException)
          {
            localArrayList4.add(localHashMap2);
          }
        }
        break label174;
        label412:
        localArrayList4.add((HashMap)localArrayList5.get(i2));
        i2++;
        break label223;
        label434:
        if (((String)((HashMap)localArrayList4.get(k)).get("ad_apps_id")).equals("WEB_ACTIVITY")) {
          localArrayList6.add(getImageViewWeb());
        }
        for (;;)
        {
          if ((k + 1) % AppCMoveIconView.this.mIconCount == 0)
          {
            localArrayList2.add(localArrayList6);
            localArrayList6 = new ArrayList();
          }
          k++;
          break;
          localArrayList6.add(getImageViewApp((HashMap)localArrayList4.get(k)));
        }
        label531:
        localArrayList6.add(getImageViewEmpty());
      }
    }
    
    protected void onPostExecute(ArrayList<ArrayList<ImageView>> paramArrayList)
    {
      AppCMoveIconView.MoveIconPagerAdapter localMoveIconPagerAdapter = new AppCMoveIconView.MoveIconPagerAdapter(AppCMoveIconView.this, this.context, paramArrayList);
      this.viewPager.setAdapter(localMoveIconPagerAdapter);
      if (AppCMoveIconView.this.mPageCount > 1) {
        setRefresh(localMoveIconPagerAdapter);
      }
    }
  }
  
  class IconInfo
  {
    String adAppsId;
    String appName;
    boolean changeFlg;
    String imageUrl;
    String pkg;
    String redirectUrl;
    
    IconInfo() {}
  }
  
  class MoveIconPagerAdapter
    extends PagerAdapter
  {
    private Context context;
    private ArrayList<ArrayList<ImageView>> iconImages;
    private LinearLayout pageLLayout;
    private AppCMoveIconView.PageView pageView;
    
    public MoveIconPagerAdapter(ArrayList<ArrayList<ImageView>> paramArrayList)
    {
      this.context = paramArrayList;
      Object localObject;
      this.iconImages = localObject;
      this.pageView = new AppCMoveIconView.PageView(AppCMoveIconView.this, paramArrayList);
    }
    
    public void destroyItem(View paramView, int paramInt, Object paramObject)
    {
      ((ViewPager)paramView).removeView((View)paramObject);
    }
    
    public int getCount()
    {
      return AppCMoveIconView.this.mPageCount;
    }
    
    public int getItemPosition(Object paramObject)
    {
      return -2;
    }
    
    public Object instantiateItem(View paramView, int paramInt)
    {
      this.pageLLayout = this.pageView.getView((ArrayList)this.iconImages.get(paramInt));
      FrameLayout localFrameLayout = new FrameLayout(this.context);
      this.pageLLayout.setGravity(81);
      localFrameLayout.addView(this.pageLLayout, -1, AppCMoveIconView.this.mSizeInfo.mMainHeight);
      ((ViewPager)paramView).addView(localFrameLayout, AppCMoveIconView.this.mSizeInfo.mMainWidth, AppCMoveIconView.this.mSizeInfo.mMainHeight);
      return localFrameLayout;
    }
    
    public boolean isViewFromObject(View paramView, Object paramObject)
    {
      if (paramView == paramObject) {}
      for (boolean bool = true;; bool = false) {
        return bool;
      }
    }
  }
  
  class PageView
  {
    private BitmapDrawable bmpBGShadow;
    private Context context;
    private LinearLayout pageLayout;
    
    public PageView(Context paramContext)
    {
      this.context = paramContext;
      this.bmpBGShadow = new BitmapDrawable(AppImages.getBitmap(33, false, paramContext));
      this.bmpBGShadow.setTileModeX(Shader.TileMode.REPEAT);
    }
    
    private View.OnClickListener createOnClickListener(final ImageView paramImageView)
    {
      final AppCMoveIconView.IconInfo localIconInfo = (AppCMoveIconView.IconInfo)paramImageView.getTag();
      if (localIconInfo.adAppsId.equals("EMPTY")) {}
      for (Object localObject = null;; localObject = new View.OnClickListener()
          {
            public void onClick(View paramAnonymousView)
            {
              new CountDownTimer(1000L, 200L)
              {
                public void onFinish()
                {
                  this.val$imageView.getDrawable().setColorFilter(null);
                  this.val$imageView.invalidate();
                }
                
                public void onTick(long paramAnonymous2Long)
                {
                  this.val$imageView.getDrawable().setColorFilter(-862348903, PorterDuff.Mode.LIGHTEN);
                  this.val$imageView.invalidate();
                }
              }.start();
              if (localIconInfo.adAppsId.equals("WEB_ACTIVITY"))
              {
                Intent localIntent = new Intent(AppCMoveIconView.PageView.this.context, AppCWebActivity.class);
                localIntent.putExtra("type", "pr_list");
                localIntent.putExtra("pr_type", "move_icon");
                AppCMoveIconView.PageView.this.context.startActivity(localIntent);
              }
              for (;;)
              {
                return;
                HashMap localHashMap = new HashMap();
                localHashMap.put("target_package", localIconInfo.pkg);
                localHashMap.put("ad_apps_id", localIconInfo.adAppsId);
                localHashMap.put("redirect_url", localIconInfo.redirectUrl);
                AppController.createIncetance(AppCMoveIconView.PageView.this.context).registCPIMoveMarket(AppCMoveIconView.PageView.this.context, localHashMap, "move_icon");
              }
            }
          }) {
        return localObject;
      }
    }
    
    private void setImage(final Context paramContext, final ImageView paramImageView, final int paramInt)
    {
      new Thread(new Runnable()
      {
        public void run()
        {
          try
          {
            final AppCMoveIconView.IconInfo localIconInfo = (AppCMoveIconView.IconInfo)paramImageView.getTag();
            if (localIconInfo.adAppsId.equals("WEB_ACTIVITY")) {}
            Bitmap localBitmap;
            for (Object localObject = AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmap(34, true, paramContext), paramInt, paramInt, 15);; localObject = localBitmap)
            {
              this.val$handler.post(new Runnable()
              {
                public void run()
                {
                  this.val$imageView.startAnimation(AppImages.getAnimation(0.1F, 1.0F, 1000));
                  localIconInfo.changeFlg = false;
                  this.val$imageView.setImageBitmap(this.val$bmp);
                }
              });
              break;
              localBitmap = AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmapIcon(localIconInfo.imageUrl, true, paramContext), paramInt, paramInt, 15);
            }
            return;
          }
          catch (Exception localException) {}
        }
      }).start();
    }
    
    public LinearLayout getView(ArrayList<ImageView> paramArrayList)
    {
      this.pageLayout = new LinearLayout(this.context);
      this.pageLayout.setBackgroundDrawable(this.bmpBGShadow);
      Iterator localIterator = paramArrayList.iterator();
      for (;;)
      {
        if (!localIterator.hasNext()) {
          return this.pageLayout;
        }
        ImageView localImageView = (ImageView)localIterator.next();
        AppCMoveIconView.IconInfo localIconInfo = (AppCMoveIconView.IconInfo)localImageView.getTag();
        LinearLayout localLinearLayout1 = (LinearLayout)localImageView.getParent();
        if (localLinearLayout1 != null) {
          localLinearLayout1.removeView(localImageView);
        }
        LinearLayout localLinearLayout2 = new LinearLayout(this.context);
        localLinearLayout2.setOrientation(1);
        localImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(AppCMoveIconView.this.mSizeInfo.mIconSize, AppCMoveIconView.this.mSizeInfo.mIconSize);
        localLayoutParams1.gravity = 17;
        localLinearLayout2.addView(localImageView, localLayoutParams1);
        if (localIconInfo.changeFlg)
        {
          localImageView.setOnClickListener(createOnClickListener(localImageView));
          setImage(this.context, localImageView, AppCMoveIconView.this.mSizeInfo.mIconSize);
        }
        TextView localTextView = new TextView(this.context);
        localTextView.setTextSize(AppCMoveIconView.this.mSizeInfo.mTextSize);
        localTextView.setSingleLine();
        localTextView.setEllipsize(TextUtils.TruncateAt.END);
        localTextView.setGravity(17);
        localTextView.setTextColor(AppCMoveIconView.this.mTextColor);
        localTextView.setText(localIconInfo.appName);
        LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(AppCMoveIconView.this.mSizeInfo.mTextWidth, AppCMoveIconView.this.mSizeInfo.mTextHeight);
        localLayoutParams2.gravity = 17;
        localLinearLayout2.addView(localTextView, localLayoutParams2);
        LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        localLayoutParams3.weight = 1.0F;
        this.pageLayout.addView(localLinearLayout2, localLayoutParams3);
      }
    }
  }
  
  class SizeInfo
  {
    int mIconSize;
    int mMainHeight;
    int mMainWidth;
    int mTextHeight;
    int mTextSize;
    int mTextWidth;
    
    SizeInfo() {}
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.AppCMoveIconView
 * JD-Core Version:    0.7.0.1
 */