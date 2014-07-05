package jp.co.cayto.appc.sdk.android.view;

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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import jp.co.cayto.appc.sdk.android.AppCCutinView.ICutInView;
import jp.co.cayto.appc.sdk.android.common.AppController;
import jp.co.cayto.appc.sdk.android.common.AppImages;
import jp.co.cayto.appc.sdk.android.common.AppPreference;
import jp.co.cayto.appc.sdk.android.entity.HttpApp;
import jp.co.cayto.appc.sdk.android.entity.HttpData;

public class CutinViewCube
  implements AppCCutinView.ICutInView
{
  private static final String _ICON_INFO_TYPE_CAMPAIGN = "campaign";
  private static final String _ICON_INFO_TYPE_CPI = "cpi";
  private static final String _PR_TYPE = "";
  
  private void addClickColor(View paramView)
  {
    new CountDownTimer(1000L, 200L)
    {
      public void onFinish()
      {
        this.val$v.getBackground().setColorFilter(null);
        this.val$v.invalidate();
      }
      
      public void onTick(long paramAnonymousLong)
      {
        this.val$v.getBackground().setColorFilter(-862348903, PorterDuff.Mode.LIGHTEN);
        this.val$v.invalidate();
      }
    }.start();
  }
  
  public void cancelButtonClick(View paramView) {}
  
  public FrameLayout createLayout(Activity paramActivity, String paramString, ImageButton paramImageButton1, ImageButton paramImageButton2, ImageButton paramImageButton3)
  {
    int i;
    boolean bool;
    int j;
    int k;
    int m;
    label86:
    float f2;
    label103:
    int n;
    int i1;
    float f3;
    label137:
    int i2;
    if (paramActivity.getResources().getConfiguration().orientation == 2)
    {
      i = 1;
      bool = "finish".equals(paramString);
      Display localDisplay = ((WindowManager)paramActivity.getSystemService("window")).getDefaultDisplay();
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      paramActivity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
      j = (int)(3.0F * localDisplayMetrics.scaledDensity);
      if (i == 0) {
        break label851;
      }
      k = 5;
      m = 2;
      float f1 = localDisplay.getWidth();
      if (!bool) {
        break label860;
      }
      f2 = 0.7F;
      n = (int)(f2 * f1 / k);
      i1 = (int)(0.5F * n);
      if (!bool) {
        break label867;
      }
      f3 = 0.4F * n;
      i2 = (int)f3;
      if (!bool) {
        break label878;
      }
    }
    LinearLayout localLinearLayout2;
    ArrayList localArrayList;
    int i9;
    label851:
    label860:
    label867:
    label878:
    for (int i3 = i2 * 2 + 2 * (j * 2);; i3 = i2 + j * 2)
    {
      int i4 = n * k + 2 * (2 * (j * k));
      int i5 = i3 + (n * m + 2 * (2 * (j * m)));
      int i6 = i4 + i1 / 2;
      int i7 = i5 + i1 / 2;
      int i8 = i5 - i3;
      FrameLayout localFrameLayout1 = new FrameLayout(paramActivity);
      Button localButton = new Button(paramActivity);
      localButton.setBackgroundColor(Color.argb(128, 0, 0, 0));
      localFrameLayout1.addView(localButton, new FrameLayout.LayoutParams(-1, -1));
      RelativeLayout localRelativeLayout = new RelativeLayout(paramActivity);
      FrameLayout.LayoutParams localLayoutParams1 = new FrameLayout.LayoutParams(i6, i7);
      localLayoutParams1.gravity = 17;
      localFrameLayout1.addView(localRelativeLayout, localLayoutParams1);
      LinearLayout localLinearLayout1 = new LinearLayout(paramActivity);
      localLinearLayout1.setOrientation(1);
      RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(i4, i5);
      localLayoutParams2.addRule(13);
      localRelativeLayout.addView(localLinearLayout1, localLayoutParams2);
      FrameLayout localFrameLayout2 = new FrameLayout(paramActivity);
      localFrameLayout2.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmap(12, false, paramActivity), i4, i8, 0)));
      LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(-1, -1);
      localLayoutParams3.weight = 1.0F;
      localLinearLayout1.addView(localFrameLayout2, localLayoutParams3);
      localLinearLayout2 = new LinearLayout(paramActivity);
      localLinearLayout2.setPadding(j, j, j, j);
      localLinearLayout2.setOrientation(1);
      LinearLayout.LayoutParams localLayoutParams4 = new LinearLayout.LayoutParams(-1, -1);
      localLayoutParams4.gravity = 17;
      localFrameLayout2.addView(localLinearLayout2, localLayoutParams4);
      localArrayList = new ArrayList();
      i9 = 0;
      if (i9 < m) {
        break label890;
      }
      EditIconInfoTask localEditIconInfoTask = new EditIconInfoTask(paramActivity, localArrayList, n);
      localEditIconInfoTask.execute(new Void[0]);
      paramImageButton1.setScaleType(ImageView.ScaleType.FIT_XY);
      paramImageButton1.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmap(4, false, paramActivity), i1, i1, 0)));
      RelativeLayout.LayoutParams localLayoutParams5 = new RelativeLayout.LayoutParams(i1, i1);
      localLayoutParams5.addRule(10);
      localLayoutParams5.addRule(11);
      localRelativeLayout.addView(paramImageButton1, localLayoutParams5);
      LinearLayout localLinearLayout3 = new LinearLayout(paramActivity);
      localLinearLayout3.setOrientation(1);
      localLinearLayout3.setPadding(j, j, j, j + j);
      localLinearLayout3.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmap(11, false, paramActivity), i4, i3, 0)));
      LinearLayout.LayoutParams localLayoutParams6 = new LinearLayout.LayoutParams(-1, i3);
      localLayoutParams6.weight = 0.0F;
      localLinearLayout1.addView(localLinearLayout3, localLayoutParams6);
      paramImageButton2.setScaleType(ImageView.ScaleType.FIT_XY);
      paramImageButton2.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmap(13, false, paramActivity), i4, i2, 0)));
      LinearLayout.LayoutParams localLayoutParams7 = new LinearLayout.LayoutParams(-1, -1);
      localLayoutParams7.weight = 1.0F;
      localLayoutParams7.setMargins(j * 2, j, j * 2, 0);
      localLinearLayout3.addView(paramImageButton2, localLayoutParams7);
      if (bool)
      {
        paramImageButton3.setScaleType(ImageView.ScaleType.FIT_XY);
        paramImageButton3.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmap(14, false, paramActivity), i4, i2, 0)));
        LinearLayout.LayoutParams localLayoutParams8 = new LinearLayout.LayoutParams(-1, -1);
        localLayoutParams8.weight = 1.0F;
        localLayoutParams8.setMargins(j * 2, j, j * 2, 0);
        localLinearLayout3.addView(paramImageButton3, localLayoutParams8);
      }
      return localFrameLayout1;
      i = 0;
      break;
      k = 3;
      m = 3;
      break label86;
      f2 = 0.75F;
      break label103;
      f3 = 0.5F * n;
      break label137;
    }
    label890:
    LinearLayout localLinearLayout4 = new LinearLayout(paramActivity);
    localLinearLayout4.setOrientation(0);
    localLinearLayout4.setGravity(17);
    for (int i10 = 0;; i10++)
    {
      if (i10 >= k)
      {
        LinearLayout.LayoutParams localLayoutParams9 = new LinearLayout.LayoutParams(-1, -1);
        localLayoutParams9.weight = 1.0F;
        localLinearLayout2.addView(localLinearLayout4, localLayoutParams9);
        i9++;
        break;
      }
      ImageButton localImageButton = new ImageButton(paramActivity);
      localImageButton.setScaleType(ImageView.ScaleType.FIT_XY);
      localImageButton.setPadding(j, j, j, j);
      localImageButton.setImageBitmap(AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmap(17, true, paramActivity), n, n, 15));
      localImageButton.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmap(15, true, paramActivity), n, n, 0)));
      localArrayList.add(localImageButton);
      FrameLayout localFrameLayout3 = new FrameLayout(paramActivity);
      localFrameLayout3.setPadding(j, j, j, j);
      FrameLayout.LayoutParams localLayoutParams10 = new FrameLayout.LayoutParams(-1, -1);
      localLayoutParams10.gravity = 17;
      localFrameLayout3.addView(localImageButton, localLayoutParams10);
      LinearLayout.LayoutParams localLayoutParams11 = new LinearLayout.LayoutParams(0, -1);
      localLayoutParams11.weight = 1.0F;
      localLinearLayout4.addView(localFrameLayout3, localLayoutParams11);
    }
  }
  
  public void exitButtonClick(View paramView)
  {
    addClickColor(paramView);
  }
  
  public void installButtonClick(View paramView) {}
  
  public void otherButtonClick(View paramView)
  {
    addClickColor(paramView);
  }
  
  class EditIconInfoTask
    extends AsyncTask<Void, Void, Void>
  {
    private String campaignId = null;
    private String campaignUrl = null;
    private Context context;
    private ArrayList<ImageButton> iconImageButtons;
    private int iconSize;
    
    public EditIconInfoTask(ArrayList<ImageButton> paramArrayList, int paramInt)
    {
      this.context = paramArrayList;
      this.iconImageButtons = paramInt;
      int i;
      this.iconSize = i;
      this.campaignId = null;
      this.campaignUrl = null;
    }
    
    private void addClick(final ImageButton paramImageButton)
    {
      paramImageButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          CutinViewCube.IconInfo localIconInfo = (CutinViewCube.IconInfo)paramImageButton.getTag();
          if (localIconInfo.emptyFlg) {}
          for (;;)
          {
            return;
            new CountDownTimer(1000L, 200L)
            {
              public void onFinish()
              {
                this.val$iconImageButton.getDrawable().setColorFilter(null);
                this.val$iconImageButton.invalidate();
              }
              
              public void onTick(long paramAnonymous2Long)
              {
                this.val$iconImageButton.getDrawable().setColorFilter(-862348903, PorterDuff.Mode.LIGHTEN);
                this.val$iconImageButton.invalidate();
              }
            }.start();
            if (localIconInfo.type.equals("campaign"))
            {
              Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(localIconInfo.campaignUrl));
              CutinViewCube.EditIconInfoTask.this.context.startActivity(localIntent);
            }
            else if (localIconInfo.type.equals("cpi"))
            {
              HashMap localHashMap = new HashMap();
              localHashMap.put("target_package", localIconInfo.pkg);
              localHashMap.put("ad_apps_id", localIconInfo.adAppsId);
              localHashMap.put("redirect_url", localIconInfo.redirectUrl);
              AppController.createIncetance(CutinViewCube.EditIconInfoTask.this.context).registCPIMoveMarket(CutinViewCube.EditIconInfoTask.this.context, localHashMap, "");
            }
          }
        }
      });
    }
    
    private void setIcon(final Context paramContext, final ImageButton paramImageButton, int paramInt)
    {
      new Thread(new Runnable()
      {
        public void run()
        {
          try
          {
            CutinViewCube.IconInfo localIconInfo = (CutinViewCube.IconInfo)paramImageButton.getTag();
            Object localObject;
            if (localIconInfo.emptyFlg) {
              localObject = AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmap(18, true, paramContext), CutinViewCube.EditIconInfoTask.this.iconSize, CutinViewCube.EditIconInfoTask.this.iconSize, 15);
            }
            for (;;)
            {
              this.val$mHandler.post(new Runnable()
              {
                public void run()
                {
                  this.val$icon.startAnimation(AppImages.getIconAnimation());
                  this.val$icon.setImageBitmap(this.val$animBmp);
                }
              });
              break;
              if (localIconInfo.type.equals("cpi"))
              {
                localObject = AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmapIcon(localIconInfo.imageUrl, paramContext), CutinViewCube.EditIconInfoTask.this.iconSize, CutinViewCube.EditIconInfoTask.this.iconSize, 15);
              }
              else if (localIconInfo.type.equals("campaign"))
              {
                localObject = AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmap(16, false, paramContext), CutinViewCube.EditIconInfoTask.this.iconSize, CutinViewCube.EditIconInfoTask.this.iconSize, 15);
              }
              else
              {
                Bitmap localBitmap = AppImages.resizeBitmapToSpecifiedSizeDrawable(AppImages.getBitmap(18, true, paramContext), CutinViewCube.EditIconInfoTask.this.iconSize, CutinViewCube.EditIconInfoTask.this.iconSize, 15);
                localObject = localBitmap;
              }
            }
            return;
          }
          catch (Exception localException) {}
        }
      }).start();
    }
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("m", "bb");
      localHashMap.put("linktag", "");
      ArrayList localArrayList1 = AppController.createIncetance(this.context).getCPIList(this.context, localHashMap).getApps();
      if (localArrayList1.isEmpty())
      {
        Iterator localIterator3 = this.iconImageButtons.iterator();
        for (;;)
        {
          if (!localIterator3.hasNext()) {
            return null;
          }
          ImageButton localImageButton4 = (ImageButton)localIterator3.next();
          CutinViewCube.IconInfo localIconInfo4 = new CutinViewCube.IconInfo(CutinViewCube.this, null);
          localIconInfo4.emptyFlg = true;
          localImageButton4.setTag(localIconInfo4);
        }
      }
      Collections.shuffle(localArrayList1);
      Collections.shuffle(this.iconImageButtons);
      ArrayList localArrayList2 = new ArrayList();
      PackageManager localPackageManager = this.context.getPackageManager();
      Iterator localIterator1 = localArrayList1.iterator();
      label154:
      int i;
      int j;
      int k;
      int m;
      Iterator localIterator2;
      if (!localIterator1.hasNext())
      {
        i = localArrayList2.size();
        j = this.iconImageButtons.size();
        k = 0;
        m = 0;
        if (m < j) {
          break label392;
        }
        if ((i > 0) && (new Random(System.currentTimeMillis()).nextInt(i) == 0) && (!TextUtils.isEmpty(this.campaignId)) && (!TextUtils.isEmpty(this.campaignUrl)))
        {
          this.campaignUrl = (this.campaignUrl + "?uid=" + AppPreference.getGid(this.context) + "&cid=" + this.campaignId);
          if (k == 0) {
            break label590;
          }
          localIterator2 = this.iconImageButtons.iterator();
          label298:
          if (localIterator2.hasNext()) {
            break label533;
          }
        }
      }
      for (;;)
      {
        for (;;)
        {
          break;
          HttpApp localHttpApp1 = (HttpApp)localIterator1.next();
          if (!TextUtils.isEmpty(localHttpApp1.getValue("campaign_id")))
          {
            this.campaignId = localHttpApp1.getValue("campaign_id");
            this.campaignUrl = localHttpApp1.getValue("campaign_url");
          }
          String str = localHttpApp1.getValue("package");
          try
          {
            localPackageManager.getApplicationInfo(str, 0);
          }
          catch (PackageManager.NameNotFoundException localNameNotFoundException)
          {
            localArrayList2.add(localHttpApp1);
          }
        }
        break label154;
        label392:
        ImageButton localImageButton1 = (ImageButton)this.iconImageButtons.get(m);
        CutinViewCube.IconInfo localIconInfo1 = new CutinViewCube.IconInfo(CutinViewCube.this, null);
        if (m < i)
        {
          HttpApp localHttpApp2 = (HttpApp)localArrayList2.get(m);
          localIconInfo1.emptyFlg = false;
          localIconInfo1.type = "cpi";
          localIconInfo1.pkg = localHttpApp2.getCnvValue("package");
          localIconInfo1.adAppsId = localHttpApp2.getValue("ad_apps_id");
          localIconInfo1.redirectUrl = localHttpApp2.getCnvValue("redirect_url");
          localIconInfo1.imageUrl = localHttpApp2.getValue("icon_url");
          localImageButton1.setTag(localIconInfo1);
        }
        for (;;)
        {
          m++;
          break;
          localIconInfo1.emptyFlg = true;
          localImageButton1.setTag(localIconInfo1);
          k = 1;
        }
        label533:
        ImageButton localImageButton3 = (ImageButton)localIterator2.next();
        CutinViewCube.IconInfo localIconInfo3 = (CutinViewCube.IconInfo)localImageButton3.getTag();
        if (!localIconInfo3.emptyFlg) {
          break label298;
        }
        localIconInfo3.type = "campaign";
        localIconInfo3.campaignUrl = this.campaignUrl;
        localImageButton3.setTag(localIconInfo3);
        continue;
        label590:
        ImageButton localImageButton2 = (ImageButton)this.iconImageButtons.get(new Random(System.currentTimeMillis()).nextInt(j));
        CutinViewCube.IconInfo localIconInfo2 = new CutinViewCube.IconInfo(CutinViewCube.this, null);
        localIconInfo2.type = "campaign";
        localIconInfo2.campaignUrl = this.campaignUrl;
        localImageButton2.setTag(localIconInfo2);
      }
    }
    
    protected void onPostExecute(Void paramVoid)
    {
      Iterator localIterator = this.iconImageButtons.iterator();
      for (;;)
      {
        if (!localIterator.hasNext()) {
          return;
        }
        ImageButton localImageButton = (ImageButton)localIterator.next();
        setIcon(this.context, localImageButton, this.iconSize);
        addClick(localImageButton);
      }
    }
  }
  
  private class IconInfo
  {
    public String adAppsId;
    public String campaignUrl;
    public boolean emptyFlg;
    public String imageUrl;
    public String pkg;
    public String redirectUrl;
    public String type;
    
    private IconInfo() {}
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.view.CutinViewCube
 * JD-Core Version:    0.7.0.1
 */