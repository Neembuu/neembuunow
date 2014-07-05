package jp.co.cayto.appc.sdk.android.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ViewFlipper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jp.co.cayto.appc.sdk.android.AppCAgreementActivity;
import jp.co.cayto.appc.sdk.android.common.AppController;
import jp.co.cayto.appc.sdk.android.common.AppDB;
import jp.co.cayto.appc.sdk.android.common.AppImages;
import jp.co.cayto.appc.sdk.android.entity.HttpData;

public class AppHorizontalScrollView
  extends HorizontalScrollView
  implements GestureDetector.OnGestureListener
{
  public static final int DEFAULT_BOX_LENGTH = 50;
  private static final int INTERVAL_LOAD_SECOND = 60;
  public int BOX_LENGTH = 45;
  private boolean adjustFlag = false;
  protected final int borderWidth = 0;
  private Button conroleButton;
  private GestureDetector gestureDetector;
  private boolean inLoading = false;
  private boolean initResourceFlag = false;
  boolean isFlip = false;
  private long lastLoadTime_resultList0 = 0L;
  private long lastLoadTime_resultList1 = 0L;
  private long lastLoadTime_resultList2 = 0L;
  private long lastLoadTime_resultList3 = 0L;
  private long lastLoadTime_resultList4 = 0L;
  private long lastLoadTime_resultList5 = 0L;
  private View leftArrow;
  private boolean loadList = false;
  private Activity mActivity;
  private String mFloatViewMode;
  private String mMode;
  private AppController mySdk;
  private View nowLoading;
  private int offsetX;
  private List<HashMap<String, String>> resultList0 = new ArrayList();
  private List<HashMap<String, String>> resultList1 = new ArrayList();
  private List<HashMap<String, String>> resultList2 = new ArrayList();
  private List<HashMap<String, String>> resultList3 = new ArrayList();
  private List<HashMap<String, String>> resultList4 = new ArrayList();
  private List<HashMap<String, String>> resultList5 = new ArrayList();
  private View rightArrow;
  private TextView stackLabel;
  private List<HashMap<String, Object>> targetLoadImage = new ArrayList();
  private String titleResult;
  private ViewFlipper viewFlipper;
  
  public AppHorizontalScrollView(Context paramContext)
  {
    super(paramContext);
    setting();
  }
  
  public AppHorizontalScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setting();
  }
  
  public AppHorizontalScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setting();
  }
  
  private void contentSetting()
  {
    removeAllViews();
    this.mySdk = AppController.createIncetance(this.mActivity);
    this.nowLoading.setVisibility(0);
    this.leftArrow.setVisibility(0);
    this.rightArrow.setVisibility(0);
    System.currentTimeMillis();
    int i = 0;
    Object localObject = new ArrayList();
    LinearLayout localLinearLayout1;
    int j;
    String[] arrayOfString;
    label269:
    int k;
    label272:
    PackageManager localPackageManager;
    int m;
    int n;
    if (this.mMode.equals("Random"))
    {
      if ((this.resultList0 != null) && (this.resultList0.size() != 0)) {
        System.currentTimeMillis();
      }
      localObject = this.resultList0;
      if (((List)localObject).size() == 0) {
        i = 1;
      }
      localLinearLayout1 = new LinearLayout(this.mActivity);
      localLinearLayout1.setBackgroundColor(Color.parseColor("#00000000"));
      if (i != 0) {
        break label1553;
      }
      this.viewFlipper = null;
      localLinearLayout1.setOrientation(0);
      setHorizontalScrollBarEnabled(false);
      j = 3 + ((List)localObject).size();
      arrayOfString = new String[((List)localObject).size()];
      if ((this.mMode.equals("Random")) && ((!this.mFloatViewMode.equals("A")) || (this.titleResult == null) || (this.titleResult.equals(""))))
      {
        if ((this.mFloatViewMode.equals("A")) || (this.titleResult == null) || (this.titleResult.equals(""))) {
          break label654;
        }
        if (this.stackLabel != null) {
          this.stackLabel.setText(this.titleResult);
        }
      }
      k = 0;
      if (k < ((List)localObject).size()) {
        break label673;
      }
      localPackageManager = this.mActivity.getPackageManager();
      m = 0;
      n = 0;
    }
    for (;;)
    {
      if (n >= j)
      {
        removeAllViews();
        if (this.viewFlipper == null) {
          break label1540;
        }
        localLinearLayout1.addView(this.viewFlipper);
        addView(localLinearLayout1, -1, -2);
        label336:
        localLinearLayout1.getLayoutParams();
        this.nowLoading.setVisibility(4);
        this.loadList = true;
        this.inLoading = false;
        System.currentTimeMillis();
        invalidate();
        return;
        if (this.mMode.equals("PR"))
        {
          if ((this.resultList1 != null) && (this.resultList1.size() != 0)) {
            System.currentTimeMillis();
          }
          localObject = this.resultList1;
          if (((List)localObject).size() != 0) {
            break;
          }
          i = 1;
          break;
        }
        if (this.mMode.equals("HotApps"))
        {
          if ((this.resultList2 != null) && (this.resultList2.size() != 0)) {
            System.currentTimeMillis();
          }
          localObject = this.resultList2;
          if (((List)localObject).size() != 0) {
            break;
          }
          i = 1;
          break;
        }
        if (this.mMode.equals("Recommends"))
        {
          if ((this.resultList3 != null) && (this.resultList3.size() != 0)) {
            System.currentTimeMillis();
          }
          localObject = this.resultList3;
          if (((List)localObject).size() != 0) {
            break;
          }
          i = 1;
          break;
        }
        if (this.mMode.equals("StaffReview"))
        {
          if ((this.resultList4 != null) && (this.resultList4.size() != 0)) {
            System.currentTimeMillis();
          }
          localObject = this.resultList4;
          if (((List)localObject).size() != 0) {
            break;
          }
          i = 1;
          break;
        }
        if (!this.mMode.equals("GameRanking")) {
          break;
        }
        if ((this.resultList5 != null) && (this.resultList5.size() != 0)) {
          System.currentTimeMillis();
        }
        localObject = this.resultList5;
        if (((List)localObject).size() != 0) {
          break;
        }
        i = 1;
        break;
        label654:
        if (this.stackLabel == null) {
          break label269;
        }
        this.stackLabel.setVisibility(8);
        break label269;
        label673:
        arrayOfString[k] = ((String)((HashMap)((List)localObject).get(k)).get("icon_url"));
        k++;
        break label272;
      }
      ImageButton localImageButton = new ImageButton(this.mActivity);
      HashMap localHashMap1 = null;
      try
      {
        localHashMap1 = (HashMap)((List)localObject).get(n);
        label735:
        if (localHashMap1 != null) {}
        for (;;)
        {
          try
          {
            localPackageManager.getApplicationInfo((String)localHashMap1.get("package"), 0);
            m++;
            int i1 = ((List)localObject).size();
            if (i1 - m >= 5) {
              n++;
            }
          }
          catch (PackageManager.NameNotFoundException localNameNotFoundException2) {}
          final HashMap localHashMap2 = localHashMap1;
          final FrameLayout localFrameLayout = new FrameLayout(this.mActivity);
          LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(this.BOX_LENGTH, this.BOX_LENGTH);
          localLayoutParams1.setMargins(1, 1, 1, 1);
          TextView localTextView2;
          String str1;
          if (localHashMap2 != null)
          {
            localFrameLayout.setBackgroundColor(Color.parseColor("#CC999999"));
            localTextView2 = new TextView(this.mActivity);
            str1 = ((String)localHashMap2.get("title")).toString().replaceAll("\n", "").trim();
            if (str1.length() <= 20)
            {
              label897:
              if ((localHashMap2.get("rank") == null) || (((String)localHashMap2.get("rank")).equals(""))) {
                break label1472;
              }
              StringBuilder localStringBuilder = new StringBuilder(String.valueOf((String)localHashMap2.get("rank")));
              localTextView2.setText("." + str1);
              label972:
              localTextView2.setTextColor(-1);
              if ((this.mFloatViewMode == null) || (!this.mFloatViewMode.equals("A"))) {
                break label1482;
              }
              localTextView2.setTextSize(6.0F);
              localTextView2.setLines(2);
              setImageTask(arrayOfString[n], localImageButton);
              localImageButton.setTag(localHashMap2);
              localImageButton.setAdjustViewBounds(true);
              localImageButton.setBackgroundColor(0);
              localImageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
              localImageButton.setPadding(2, 2, 2, 2);
              View.OnClickListener local1 = new View.OnClickListener()
              {
                public void onClick(View paramAnonymousView)
                {
                  localFrameLayout.setBackgroundColor(Color.parseColor("#EAFFFA"));
                  localFrameLayout.invalidate();
                  new Thread(new Runnable()
                  {
                    public void run()
                    {
                      this.val$mHandler.post(new Runnable()
                      {
                        public void run()
                        {
                          try
                          {
                            Thread.sleep(500L);
                            label6:
                            this.val$fl.setBackgroundColor(Color.parseColor("#CC999999"));
                            this.val$fl.invalidate();
                            return;
                          }
                          catch (Exception localException)
                          {
                            break label6;
                          }
                        }
                      });
                    }
                  }).start();
                  AppHorizontalScrollView.this.mySdk.configure(AppHorizontalScrollView.this.mActivity.getApplicationContext());
                  if (!AppHorizontalScrollView.this.mySdk.isInitialized())
                  {
                    Intent localIntent = new Intent(AppHorizontalScrollView.this.mActivity, AppCAgreementActivity.class);
                    localIntent.putExtra("redirect_class", "");
                    AppHorizontalScrollView.this.mActivity.startActivity(localIntent);
                  }
                  for (;;)
                  {
                    return;
                    String str1 = (String)localHashMap2.get("package");
                    String str2 = (String)localHashMap2.get("ad_apps_id");
                    String str3 = (String)localHashMap2.get("redirect_url");
                    HashMap localHashMap = new HashMap();
                    localHashMap.put("target_package", str1);
                    localHashMap.put("ad_apps_id", str2);
                    localHashMap.put("redirect_url", str3);
                    if ((AppHorizontalScrollView.this.mFloatViewMode != null) && (AppHorizontalScrollView.this.mFloatViewMode.equals("A"))) {}
                    for (String str4 = "simple";; str4 = "tower")
                    {
                      if (!AppHorizontalScrollView.this.mMode.equals("PR")) {
                        break label272;
                      }
                      AppHorizontalScrollView.this.mySdk.registCPIMoveMarket(paramAnonymousView.getContext(), localHashMap, str4);
                      break;
                    }
                    label272:
                    if (AppHorizontalScrollView.this.mMode.equals("HotApps")) {
                      AppHorizontalScrollView.this.mySdk.registCPIMoveMarket(paramAnonymousView.getContext(), localHashMap, "appC_float_ha");
                    } else if (AppHorizontalScrollView.this.mMode.equals("Recommends")) {
                      AppHorizontalScrollView.this.mySdk.registCPIMoveMarket(paramAnonymousView.getContext(), localHashMap, "appC_float_rcm");
                    } else if (AppHorizontalScrollView.this.mMode.equals("StaffReview")) {
                      AppHorizontalScrollView.this.mySdk.registCPIMoveMarket(paramAnonymousView.getContext(), localHashMap, "appC_float_sr");
                    } else if (AppHorizontalScrollView.this.mMode.equals("GameRanking")) {
                      AppHorizontalScrollView.this.mySdk.registCPIMoveMarket(paramAnonymousView.getContext(), localHashMap, "appC_float_gm");
                    } else {
                      AppHorizontalScrollView.this.mySdk.registCPIMoveMarket(paramAnonymousView.getContext(), localHashMap, str4);
                    }
                  }
                }
              };
              localImageButton.setOnClickListener(local1);
            }
          }
          for (;;)
          {
            try
            {
              String str3 = (String)localHashMap2.get("package");
              this.mActivity.getPackageManager().getApplicationInfo(str3, 128);
              str2 = "1";
              FrameLayout.LayoutParams localLayoutParams2 = new FrameLayout.LayoutParams(this.BOX_LENGTH, this.BOX_LENGTH);
              localLayoutParams2.gravity = 16;
              localLayoutParams2.setMargins(0, 2, 0, 2);
              FrameLayout.LayoutParams localLayoutParams3 = new FrameLayout.LayoutParams(this.BOX_LENGTH, this.BOX_LENGTH);
              localLayoutParams3.height = (10 + this.BOX_LENGTH / 3);
              localLayoutParams3.gravity = 80;
              LinearLayout.LayoutParams localLayoutParams4 = new LinearLayout.LayoutParams(this.BOX_LENGTH, this.BOX_LENGTH);
              localLayoutParams4.width = (-6 + this.BOX_LENGTH);
              localLayoutParams4.gravity = 17;
              LinearLayout localLinearLayout2 = new LinearLayout(this.mActivity);
              localLinearLayout2.setOrientation(1);
              localLinearLayout2.setBackgroundColor(Color.parseColor("#66000000"));
              localLinearLayout2.addView(localTextView2, localLayoutParams4);
              LinearLayout localLinearLayout3 = new LinearLayout(this.mActivity);
              localLinearLayout3.setOrientation(1);
              localLinearLayout3.setBackgroundColor(Color.parseColor("#99000000"));
              TextView localTextView3 = new TextView(this.mActivity);
              localTextView3.setText(" インストール済");
              localTextView3.setTextSize(6.0F);
              localLinearLayout3.addView(localTextView3, new LinearLayout.LayoutParams(this.BOX_LENGTH, this.BOX_LENGTH));
              localFrameLayout.addView(localImageButton, localLayoutParams2);
              if ((str2.equals("1")) && (!this.mMode.equals("PR")) && ((!this.mMode.equals("Random")) || (this.titleResult == null) || (!this.titleResult.equals("PR")))) {
                localFrameLayout.addView(localLinearLayout3, new FrameLayout.LayoutParams(this.BOX_LENGTH, this.BOX_LENGTH));
              }
              localFrameLayout.addView(localLinearLayout2, localLayoutParams3);
              if (this.viewFlipper == null) {
                break label1528;
              }
              this.viewFlipper.addView(localFrameLayout, localLayoutParams1);
              break;
              str1 = str1.substring(0, 20);
              break label897;
              label1472:
              localTextView2.setText(str1);
              break label972;
              label1482:
              localTextView2.setTextSize(6.0F);
              localTextView2.setLines(2);
            }
            catch (PackageManager.NameNotFoundException localNameNotFoundException1)
            {
              String str2 = "0";
              continue;
            }
            localFrameLayout.setBackgroundColor(Color.parseColor("#00000000"));
            localFrameLayout.setVisibility(4);
          }
          label1528:
          localLinearLayout1.addView(localFrameLayout, localLayoutParams1);
        }
        label1540:
        addView(localLinearLayout1, -1, -2);
        break label336;
        label1553:
        localLinearLayout1.setOrientation(0);
        setHorizontalScrollBarEnabled(false);
        localLinearLayout1.setPadding(0, 5, 0, 5);
        TextView localTextView1 = new TextView(this.mActivity);
        localTextView1.setText("通信エラーが発生しました。\n※電波状態の良い場所で再度試してみてください。\n");
        localTextView1.setTextSize(6.0F);
        localTextView1.setGravity(48);
        Button localButton = new Button(this.mActivity);
        localButton.setText("再通信");
        localButton.setTextSize(12.0F);
        localButton.setGravity(16);
        View.OnClickListener local2 = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            AppHorizontalScrollView.this.inLoading = true;
            HandlerThread localHandlerThread = new HandlerThread("BGThread");
            localHandlerThread.start();
            new Handler(localHandlerThread.getLooper()).post(new Runnable()
            {
              public void run()
              {
                Object localObject = new ArrayList();
                HashMap localHashMap;
                if (AppHorizontalScrollView.this.mMode.equals("Random"))
                {
                  localObject = AppHorizontalScrollView.this.resultList0;
                  int i = ((List)localObject).size();
                  HttpData localHttpData = null;
                  if ((localObject == null) || (i == 0))
                  {
                    localHashMap = new HashMap();
                    if (!AppHorizontalScrollView.this.mMode.equals("Random")) {
                      break label449;
                    }
                    localHashMap.put("m", "rand");
                    label91:
                    new AppDB(AppHorizontalScrollView.this.mActivity).removeCPIListByOld();
                    localHttpData = AppHorizontalScrollView.this.mySdk.getCPIList(AppHorizontalScrollView.this.mActivity, localHashMap);
                    localObject = localHttpData.getAppsList();
                  }
                  if (!AppHorizontalScrollView.this.mMode.equals("Random")) {
                    break label604;
                  }
                  AppHorizontalScrollView.this.resultList0 = ((List)localObject);
                  AppHorizontalScrollView.this.lastLoadTime_resultList0 = System.currentTimeMillis();
                  AppHorizontalScrollView.this.titleResult = localHttpData.getValue("list_title");
                  if ((TextUtils.isEmpty(AppHorizontalScrollView.this.titleResult)) && (!TextUtils.isEmpty(localHttpData.getValue("ranking_name")))) {
                    AppHorizontalScrollView.this.titleResult = localHttpData.getValue("ranking_name");
                  }
                }
                for (;;)
                {
                  AppHorizontalScrollView.this.loadList = true;
                  AppHorizontalScrollView.this.inLoading = true;
                  new Handler(Looper.getMainLooper()).post(new Runnable()
                  {
                    public void run()
                    {
                      AppHorizontalScrollView.this.contentSetting();
                    }
                  });
                  return;
                  if (AppHorizontalScrollView.this.mMode.equals("PR"))
                  {
                    localObject = AppHorizontalScrollView.this.resultList1;
                    break;
                  }
                  if (AppHorizontalScrollView.this.mMode.equals("HotApps"))
                  {
                    localObject = AppHorizontalScrollView.this.resultList2;
                    break;
                  }
                  if (AppHorizontalScrollView.this.mMode.equals("Recommends"))
                  {
                    localObject = AppHorizontalScrollView.this.resultList3;
                    break;
                  }
                  if (AppHorizontalScrollView.this.mMode.equals("StaffReview"))
                  {
                    localObject = AppHorizontalScrollView.this.resultList4;
                    break;
                  }
                  if (!AppHorizontalScrollView.this.mMode.equals("GameRanking")) {
                    break;
                  }
                  localObject = AppHorizontalScrollView.this.resultList5;
                  break;
                  label449:
                  if (AppHorizontalScrollView.this.mMode.equals("PR"))
                  {
                    localHashMap.put("m", "pr");
                    break label91;
                  }
                  if (AppHorizontalScrollView.this.mMode.equals("HotApps"))
                  {
                    localHashMap.put("m", "ha");
                    break label91;
                  }
                  if (AppHorizontalScrollView.this.mMode.equals("Recommends"))
                  {
                    localHashMap.put("m", "rcm");
                    break label91;
                  }
                  if (AppHorizontalScrollView.this.mMode.equals("StaffReview"))
                  {
                    localHashMap.put("m", "sr");
                    break label91;
                  }
                  if (!AppHorizontalScrollView.this.mMode.equals("GameRanking")) {
                    break label91;
                  }
                  localHashMap.put("m", "gm");
                  break label91;
                  label604:
                  if (AppHorizontalScrollView.this.mMode.equals("PR"))
                  {
                    AppHorizontalScrollView.this.resultList1 = ((List)localObject);
                    AppHorizontalScrollView.this.lastLoadTime_resultList1 = System.currentTimeMillis();
                  }
                  else if (AppHorizontalScrollView.this.mMode.equals("HotApps"))
                  {
                    AppHorizontalScrollView.this.resultList2 = ((List)localObject);
                    AppHorizontalScrollView.this.lastLoadTime_resultList2 = System.currentTimeMillis();
                  }
                  else if (AppHorizontalScrollView.this.mMode.equals("Recommends"))
                  {
                    AppHorizontalScrollView.this.resultList3 = ((List)localObject);
                    AppHorizontalScrollView.this.lastLoadTime_resultList3 = System.currentTimeMillis();
                  }
                  else if (AppHorizontalScrollView.this.mMode.equals("StaffReview"))
                  {
                    AppHorizontalScrollView.this.resultList4 = ((List)localObject);
                    AppHorizontalScrollView.this.lastLoadTime_resultList4 = System.currentTimeMillis();
                  }
                  else if (AppHorizontalScrollView.this.mMode.equals("GameRanking"))
                  {
                    AppHorizontalScrollView.this.resultList5 = ((List)localObject);
                    AppHorizontalScrollView.this.lastLoadTime_resultList5 = System.currentTimeMillis();
                  }
                }
              }
            });
          }
        };
        localButton.setOnClickListener(local2);
        localLinearLayout1.addView(localTextView1);
        localLinearLayout1.addView(localButton);
        addView(localLinearLayout1, -1, this.BOX_LENGTH);
        this.nowLoading.setVisibility(4);
        this.leftArrow.setVisibility(4);
        this.rightArrow.setVisibility(4);
        this.loadList = true;
        this.inLoading = false;
        invalidate();
      }
      catch (Exception localException)
      {
        break label735;
      }
    }
  }
  
  private void scrollToNear()
  {
    LinearLayout localLinearLayout = (LinearLayout)getChildAt(0);
    int i = localLinearLayout.getChildCount();
    int j = computeHorizontalScrollOffset();
    int k = computeHorizontalScrollRange() - (j + getWidth());
    for (int m = 0;; m++)
    {
      if (m >= i) {}
      for (;;)
      {
        return;
        View localView1 = localLinearLayout.getChildAt(m);
        int n = localView1.getLeft();
        localView1.getTop();
        int i1 = localView1.getWidth();
        View localView2 = localLinearLayout.getChildAt(m + 1);
        if (localView2 != null) {}
        for (int i2 = localView2.getLeft();; i2 = 0)
        {
          if (j == 0) {
            break label131;
          }
          if ((localView2 == null) || (localView2.getVisibility() != 4) || (j == 0)) {
            break label133;
          }
          smoothScrollTo(n, getScrollY());
          break;
        }
        label131:
        continue;
        label133:
        if (j != n) {
          if ((j > n) && (j <= n + i1 / 2))
          {
            smoothScrollTo(n, getScrollY());
          }
          else
          {
            if ((j <= n + i1 / 2) || (i2 == 0) || (j >= i2)) {
              break;
            }
            if (k < this.BOX_LENGTH) {
              smoothScrollTo(n, getScrollY());
            } else {
              smoothScrollTo(i2, getScrollY());
            }
          }
        }
      }
    }
  }
  
  private void setting()
  {
    setFadingEdgeLength(0);
    this.gestureDetector = new GestureDetector(getContext(), this);
    setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        AppHorizontalScrollView.this.adjustFlag = false;
        boolean bool = AppHorizontalScrollView.this.gestureDetector.onTouchEvent(paramAnonymousMotionEvent);
        int i = (int)paramAnonymousMotionEvent.getRawX();
        paramAnonymousMotionEvent.getRawY();
        int j = AppHorizontalScrollView.this.offsetX - i;
        AppHorizontalScrollView.this.offsetX = i;
        if (paramAnonymousMotionEvent.getAction() != 2)
        {
          if (paramAnonymousMotionEvent.getAction() != 0) {
            break label75;
          }
          paramAnonymousMotionEvent.getRawX();
        }
        for (;;)
        {
          return bool;
          label75:
          if ((paramAnonymousMotionEvent.getAction() == 1) && (Math.abs(j) <= 10) && (!AppHorizontalScrollView.this.adjustFlag))
          {
            new CountDownTimer(3000L, 2L)
            {
              public void onFinish()
              {
                AppHorizontalScrollView.this.scrollToNear();
                AppHorizontalScrollView.this.adjustFlag = false;
              }
              
              public void onTick(long paramAnonymous2Long) {}
            }.start();
            AppHorizontalScrollView.this.adjustFlag = true;
          }
        }
      }
    });
  }
  
  private static void updateAlphaArrowView(Context paramContext, final View paramView, final int paramInt)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        AppHorizontalScrollView.this.post(new Runnable()
        {
          public void run()
          {
            if (this.val$visibility == 0)
            {
              this.val$imageView.startAnimation(AppImages.getAnimation(0.0F, 1.0F, 500));
              this.val$imageView.setVisibility(this.val$visibility);
            }
            for (;;)
            {
              return;
              this.val$imageView.startAnimation(AppImages.getAnimation(1.0F, 0.0F, 500));
              this.val$imageView.setVisibility(this.val$visibility);
            }
          }
        });
      }
    }).start();
  }
  
  private void updateImageView(final Context paramContext, final String paramString, final ImageView paramImageView)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          final Bitmap localBitmap = AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmapIcon(paramString, paramContext), 48, 48, 5);
          this.val$mHandler.post(new Runnable()
          {
            public void run()
            {
              this.val$imageView.startAnimation(AppImages.getIconAnimation());
              this.val$imageView.setImageBitmap(localBitmap);
            }
          });
          label41:
          return;
        }
        catch (Exception localException)
        {
          break label41;
        }
      }
    }).start();
  }
  
  public void goToLeftEdge()
  {
    if (this.viewFlipper == null)
    {
      computeHorizontalScrollOffset();
      computeHorizontalScrollRange();
      smoothScrollTo(0, getScrollY());
    }
    for (;;)
    {
      return;
      int i = this.viewFlipper.getNextFocusLeftId();
      updateImageView(getContext(), (String)((HashMap)this.targetLoadImage.get(i)).get("url"), (ImageView)((HashMap)this.targetLoadImage.get(i)).get("view"));
      this.viewFlipper.showPrevious();
    }
  }
  
  public void goToRightEdge()
  {
    LinearLayout localLinearLayout;
    int k;
    if (this.viewFlipper == null)
    {
      localLinearLayout = (LinearLayout)getChildAt(0);
      int j = localLinearLayout.getChildCount();
      computeHorizontalScrollOffset();
      computeHorizontalScrollRange();
      k = 0;
      if (k < j) {}
    }
    for (;;)
    {
      return;
      View localView1 = localLinearLayout.getChildAt(k);
      int m = localView1.getLeft();
      localView1.getTop();
      localView1.getWidth();
      View localView2 = localLinearLayout.getChildAt(k + 1);
      if ((localView2 != null) && (localView2.getVisibility() == 4))
      {
        smoothScrollTo(m, getScrollY());
      }
      else
      {
        k++;
        break;
        int i = this.viewFlipper.getNextFocusRightId();
        updateImageView(getContext(), (String)((HashMap)this.targetLoadImage.get(i)).get("url"), (ImageView)((HashMap)this.targetLoadImage.get(i)).get("view"));
        this.viewFlipper.showNext();
      }
    }
  }
  
  public void initResouce()
  {
    HandlerThread localHandlerThread = new HandlerThread("BGThread");
    localHandlerThread.start();
    new Handler(localHandlerThread.getLooper()).post(new Runnable()
    {
      public void run()
      {
        Object localObject = new ArrayList();
        HashMap localHashMap;
        if (AppHorizontalScrollView.this.mMode.equals("Random"))
        {
          localObject = AppHorizontalScrollView.this.resultList0;
          int i = ((List)localObject).size();
          HttpData localHttpData = null;
          if ((localObject == null) || (i == 0))
          {
            localHashMap = new HashMap();
            if (!AppHorizontalScrollView.this.mMode.equals("Random")) {
              break label399;
            }
            localHashMap.put("m", "rand");
            label82:
            System.currentTimeMillis();
            if ((AppHorizontalScrollView.this.mFloatViewMode == null) || (!AppHorizontalScrollView.this.mFloatViewMode.equals("A"))) {
              break label539;
            }
            localHashMap.put("linktag", "simple");
            label121:
            localHttpData = AppHorizontalScrollView.this.mySdk.getCPIList(AppHorizontalScrollView.this.mActivity, localHashMap);
            localObject = localHttpData.getAppsList();
          }
          if (!AppHorizontalScrollView.this.mMode.equals("Random")) {
            break label552;
          }
          AppHorizontalScrollView.this.resultList0 = ((List)localObject);
          AppHorizontalScrollView.this.lastLoadTime_resultList0 = System.currentTimeMillis();
          AppHorizontalScrollView.this.titleResult = localHttpData.getValue("list_title");
          if ((TextUtils.isEmpty(AppHorizontalScrollView.this.titleResult)) && (!TextUtils.isEmpty(localHttpData.getValue("ranking_name")))) {
            AppHorizontalScrollView.this.titleResult = localHttpData.getValue("ranking_name");
          }
        }
        for (;;)
        {
          AppHorizontalScrollView.this.loadList = true;
          AppHorizontalScrollView.this.inLoading = true;
          new Handler(Looper.getMainLooper()).post(new Runnable()
          {
            public void run()
            {
              AppHorizontalScrollView.this.contentSetting();
            }
          });
          return;
          if (AppHorizontalScrollView.this.mMode.equals("PR"))
          {
            localObject = AppHorizontalScrollView.this.resultList1;
            break;
          }
          if (AppHorizontalScrollView.this.mMode.equals("HotApps"))
          {
            localObject = AppHorizontalScrollView.this.resultList2;
            break;
          }
          if (AppHorizontalScrollView.this.mMode.equals("Recommends"))
          {
            localObject = AppHorizontalScrollView.this.resultList3;
            break;
          }
          if (AppHorizontalScrollView.this.mMode.equals("StaffReview"))
          {
            localObject = AppHorizontalScrollView.this.resultList4;
            break;
          }
          if (!AppHorizontalScrollView.this.mMode.equals("GameRanking")) {
            break;
          }
          localObject = AppHorizontalScrollView.this.resultList5;
          break;
          label399:
          if (AppHorizontalScrollView.this.mMode.equals("PR"))
          {
            localHashMap.put("m", "pr");
            break label82;
          }
          if (AppHorizontalScrollView.this.mMode.equals("HotApps"))
          {
            localHashMap.put("m", "ha");
            break label82;
          }
          if (AppHorizontalScrollView.this.mMode.equals("Recommends"))
          {
            localHashMap.put("m", "rcm");
            break label82;
          }
          if (AppHorizontalScrollView.this.mMode.equals("StaffReview"))
          {
            localHashMap.put("m", "sr");
            break label82;
          }
          if (!AppHorizontalScrollView.this.mMode.equals("GameRanking")) {
            break label82;
          }
          localHashMap.put("m", "gm");
          break label82;
          label539:
          localHashMap.put("linktag", "tower");
          break label121;
          label552:
          if (AppHorizontalScrollView.this.mMode.equals("PR"))
          {
            AppHorizontalScrollView.this.resultList1 = ((List)localObject);
            AppHorizontalScrollView.this.lastLoadTime_resultList1 = System.currentTimeMillis();
          }
          else if (AppHorizontalScrollView.this.mMode.equals("HotApps"))
          {
            AppHorizontalScrollView.this.resultList2 = ((List)localObject);
            AppHorizontalScrollView.this.lastLoadTime_resultList2 = System.currentTimeMillis();
          }
          else if (AppHorizontalScrollView.this.mMode.equals("Recommends"))
          {
            AppHorizontalScrollView.this.resultList3 = ((List)localObject);
            AppHorizontalScrollView.this.lastLoadTime_resultList3 = System.currentTimeMillis();
          }
          else if (AppHorizontalScrollView.this.mMode.equals("StaffReview"))
          {
            AppHorizontalScrollView.this.resultList4 = ((List)localObject);
            AppHorizontalScrollView.this.lastLoadTime_resultList4 = System.currentTimeMillis();
          }
          else if (AppHorizontalScrollView.this.mMode.equals("GameRanking"))
          {
            AppHorizontalScrollView.this.resultList5 = ((List)localObject);
            AppHorizontalScrollView.this.lastLoadTime_resultList5 = System.currentTimeMillis();
          }
        }
      }
    });
  }
  
  public boolean onDown(MotionEvent paramMotionEvent)
  {
    return false;
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    System.currentTimeMillis();
    System.currentTimeMillis();
    int i = computeHorizontalScrollOffset();
    int j = computeHorizontalScrollRange();
    int k = getWidth();
    getHeight();
    int m = j - (k + i);
    if ((!this.mFloatViewMode.equals("A")) && (!this.initResourceFlag))
    {
      this.initResourceFlag = true;
      initResouce();
    }
    int i2;
    if ((!this.inLoading) && (this.loadList) && (this.targetLoadImage.size() > 0))
    {
      int n = this.targetLoadImage.size();
      if (this.viewFlipper != null) {
        break label333;
      }
      i2 = 0;
      if (i2 < n) {}
    }
    else
    {
      label129:
      if (this.viewFlipper == null)
      {
        if (20 >= i) {
          break label461;
        }
        if (this.leftArrow.getVisibility() == 4) {
          updateAlphaArrowView(getContext(), this.leftArrow, 0);
        }
        label166:
        if (20 + 3 * this.BOX_LENGTH >= m) {
          break label486;
        }
        if (this.rightArrow.getVisibility() == 4) {
          updateAlphaArrowView(getContext(), this.rightArrow, 0);
        }
      }
    }
    for (;;)
    {
      System.currentTimeMillis();
      return;
      if ((m < j - i2 * this.BOX_LENGTH) && ((String)((HashMap)this.targetLoadImage.get(i2)).get("load") == null))
      {
        updateImageView(getContext(), (String)((HashMap)this.targetLoadImage.get(i2)).get("url"), (ImageView)((HashMap)this.targetLoadImage.get(i2)).get("view"));
        ((HashMap)this.targetLoadImage.get(i2)).put("load", "complete");
      }
      i2++;
      break;
      label333:
      int i1 = i / k;
      if ((this.targetLoadImage.get(i1) == null) || ((String)((HashMap)this.targetLoadImage.get(i1)).get("load") != null)) {
        break label129;
      }
      updateImageView(getContext(), (String)((HashMap)this.targetLoadImage.get(i1)).get("url"), (ImageView)((HashMap)this.targetLoadImage.get(i1)).get("view"));
      ((HashMap)this.targetLoadImage.get(i1)).put("load", "complete");
      break label129;
      label461:
      if (this.leftArrow.getVisibility() != 0) {
        break label166;
      }
      updateAlphaArrowView(getContext(), this.leftArrow, 4);
      break label166;
      label486:
      if (this.rightArrow.getVisibility() == 0) {
        updateAlphaArrowView(getContext(), this.rightArrow, 4);
      }
    }
  }
  
  public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    return false;
  }
  
  public void onLongPress(MotionEvent paramMotionEvent) {}
  
  public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2)
  {
    int i = (int)paramMotionEvent2.getRawX();
    paramMotionEvent2.getRawY();
    int j = this.offsetX - i;
    this.offsetX = i;
    if (j <= 0) {}
    return false;
  }
  
  public void onShowPress(MotionEvent paramMotionEvent) {}
  
  public boolean onSingleTapUp(MotionEvent paramMotionEvent)
  {
    return false;
  }
  
  public void setImageTask(String paramString, ImageView paramImageView)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("url", paramString);
    localHashMap.put("view", paramImageView);
    this.targetLoadImage.add(localHashMap);
  }
  
  public void setResource(String paramString1, String paramString2, Activity paramActivity, Button paramButton, TextView paramTextView, View paramView1, View paramView2, View paramView3, int paramInt)
  {
    this.rightArrow = paramView2;
    this.leftArrow = paramView1;
    this.nowLoading = paramView3;
    this.conroleButton = paramButton;
    paramView2.setVisibility(4);
    paramView1.setVisibility(4);
    this.nowLoading.setVisibility(0);
    this.mActivity = paramActivity;
    this.mMode = paramString2;
    this.mFloatViewMode = paramString1;
    this.stackLabel = paramTextView;
    this.BOX_LENGTH = (paramInt - this.BOX_LENGTH / 10);
    this.mySdk = AppController.createIncetance(this.mActivity);
    addView(this.nowLoading);
    if (paramString1.equals("A"))
    {
      this.initResourceFlag = true;
      initResouce();
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.view.AppHorizontalScrollView
 * JD-Core Version:    0.7.0.1
 */