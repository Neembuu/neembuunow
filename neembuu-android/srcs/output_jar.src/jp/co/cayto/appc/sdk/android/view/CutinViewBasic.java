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
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
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
import jp.co.cayto.appc.sdk.android.resources.Bitmaps;

public class CutinViewBasic
  implements AppCCutinView.ICutInView
{
  private static final int _FP = -1;
  private static final int _WC = -2;
  private int mButtonAreaHeight;
  private LinearLayout mButtonAreaLayout;
  private int mButtonAreaWidth;
  private int mButtonHeight;
  private int mButtonWidth;
  private HashMap<String, Integer> mImageNameMap;
  
  private HashMap<String, Integer> createImageNameMap(String paramString)
  {
    this.mImageNameMap = new HashMap();
    if ("finish".equals(paramString))
    {
      this.mImageNameMap.put("normal", Integer.valueOf(8));
      this.mImageNameMap.put("dl", Integer.valueOf(5));
      this.mImageNameMap.put("other", Integer.valueOf(9));
      this.mImageNameMap.put("exit", Integer.valueOf(6));
    }
    for (;;)
    {
      return this.mImageNameMap;
      this.mImageNameMap.put("normal", Integer.valueOf(2));
      this.mImageNameMap.put("dl", Integer.valueOf(1));
      this.mImageNameMap.put("other", Integer.valueOf(3));
    }
  }
  
  public void cancelButtonClick(View paramView)
  {
    Bitmap localBitmap = Bitmaps.getBitmap(((Integer)this.mImageNameMap.get("normal")).intValue());
    this.mButtonAreaLayout.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSize(localBitmap, this.mButtonAreaWidth, this.mButtonAreaHeight)));
  }
  
  public FrameLayout createLayout(Activity paramActivity, String paramString, ImageButton paramImageButton1, ImageButton paramImageButton2, ImageButton paramImageButton3)
  {
    int i = ((WindowManager)paramActivity.getSystemService("window")).getDefaultDisplay().getWidth();
    int j;
    float f2;
    label50:
    int k;
    double d2;
    label73:
    int m;
    int n;
    int i1;
    int i2;
    int i3;
    if (paramActivity.getResources().getConfiguration().orientation == 2)
    {
      j = 1;
      float f1 = i;
      if (j == 0) {
        break label1011;
      }
      f2 = 0.4F;
      k = (int)(f2 * f1);
      double d1 = k;
      if (j == 0) {
        break label1018;
      }
      d2 = 0.8571000099182129D;
      m = (int)(d2 * d1);
      this.mButtonAreaWidth = k;
      this.mButtonAreaHeight = ((int)(0.4542F * k));
      this.mButtonWidth = ((int)(0.65F * k));
      this.mButtonHeight = this.mButtonWidth;
      n = (int)(0.73F * this.mButtonAreaWidth);
      i1 = (int)(0.33F * this.mButtonAreaHeight);
      i2 = (int)(0.54F * n);
      if (!"finish".equals(paramString)) {
        break label1026;
      }
      i3 = i2;
    }
    for (int i4 = i1;; i4 = (int)(0.35D * this.mButtonAreaHeight))
    {
      int i5 = (int)(0.2D * k);
      int i6 = (int)(0.3D * k);
      createImageNameMap(paramString);
      FrameLayout localFrameLayout1 = new FrameLayout(paramActivity);
      Button localButton = new Button(paramActivity);
      localButton.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
      localButton.setBackgroundColor(Color.argb(128, 0, 0, 0));
      localFrameLayout1.addView(localButton);
      FrameLayout localFrameLayout2 = new FrameLayout(paramActivity);
      FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2);
      localLayoutParams.gravity = 17;
      localFrameLayout2.setLayoutParams(localLayoutParams);
      localFrameLayout1.addView(localFrameLayout2);
      LinearLayout localLinearLayout1 = new LinearLayout(paramActivity);
      localLinearLayout1.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
      localLinearLayout1.setOrientation(1);
      localFrameLayout2.addView(localLinearLayout1);
      LinearLayout localLinearLayout2 = new LinearLayout(paramActivity);
      localLinearLayout2.setOrientation(1);
      localLinearLayout2.setLayoutParams(new LinearLayout.LayoutParams(k, m));
      localLinearLayout2.setGravity(81);
      localLinearLayout2.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSize(Bitmaps.getBitmap(10), k, m)));
      localLinearLayout1.addView(localLinearLayout2);
      ImageView localImageView = new ImageView(paramActivity);
      localLinearLayout2.addView(localImageView);
      this.mButtonAreaLayout = new LinearLayout(paramActivity);
      this.mButtonAreaLayout.setOrientation(1);
      this.mButtonAreaLayout.setGravity(1);
      this.mButtonAreaLayout.setLayoutParams(new LinearLayout.LayoutParams(this.mButtonAreaWidth, this.mButtonAreaHeight));
      Bitmap localBitmap = Bitmaps.getBitmap(((Integer)this.mImageNameMap.get("normal")).intValue());
      this.mButtonAreaLayout.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSize(localBitmap, this.mButtonWidth, this.mButtonHeight)));
      ImageButton localImageButton = new ImageButton(paramActivity);
      localImageButton.setBackgroundColor(Color.argb(0, 0, 0, 0));
      localImageButton.setLayoutParams(new LinearLayout.LayoutParams(n, i1));
      this.mButtonAreaLayout.addView(localImageButton);
      LinearLayout localLinearLayout3 = new LinearLayout(paramActivity);
      localLinearLayout3.setOrientation(0);
      localLinearLayout3.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
      if (paramString.equals("finish"))
      {
        paramImageButton3.setBackgroundColor(Color.argb(0, 0, 128, 0));
        LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(i2, i1);
        localLayoutParams3.width = i2;
        localLayoutParams3.height = i1;
        paramImageButton3.setLayoutParams(localLayoutParams3);
        localLinearLayout3.addView(paramImageButton3);
      }
      LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(i3, i4);
      localLayoutParams1.width = i3;
      localLayoutParams1.height = i4;
      paramImageButton2.setLayoutParams(localLayoutParams1);
      paramImageButton2.setBackgroundColor(Color.argb(0, 128, 0, 0));
      localLinearLayout3.addView(paramImageButton2);
      this.mButtonAreaLayout.addView(localLinearLayout3);
      new HttpResponseTask(paramActivity, localImageView, this.mButtonWidth, this.mButtonHeight, localImageButton).execute(new Void[0]);
      localLinearLayout1.addView(this.mButtonAreaLayout);
      LinearLayout localLinearLayout4 = new LinearLayout(paramActivity);
      localLinearLayout4.setLayoutParams(new LinearLayout.LayoutParams(k, i5));
      localLinearLayout4.setGravity(5);
      localFrameLayout2.addView(localLinearLayout4);
      LinearLayout localLinearLayout5 = new LinearLayout(paramActivity);
      localLinearLayout5.setLayoutParams(new LinearLayout.LayoutParams(i5, i5));
      localLinearLayout5.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSize(Bitmaps.getBitmap(4), i5, i5)));
      localLinearLayout4.addView(localLinearLayout5);
      paramImageButton1.setBackgroundColor(Color.argb(0, 0, 0, 0));
      LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(i5, i5);
      localLayoutParams2.width = i5;
      localLayoutParams2.height = i5;
      paramImageButton1.setLayoutParams(localLayoutParams2);
      localLinearLayout5.addView(paramImageButton1);
      LinearLayout localLinearLayout6 = new LinearLayout(paramActivity);
      localLinearLayout6.setLayoutParams(new LinearLayout.LayoutParams(k, m));
      localLinearLayout6.setGravity(85);
      localFrameLayout2.addView(localLinearLayout6);
      LinearLayout localLinearLayout7 = new LinearLayout(paramActivity);
      localLinearLayout7.setLayoutParams(new LinearLayout.LayoutParams(i6, i6));
      localLinearLayout7.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSize(Bitmaps.getBitmap(7), i6, i6)));
      localLinearLayout6.addView(localLinearLayout7);
      return localFrameLayout1;
      j = 0;
      break;
      label1011:
      f2 = 0.9F;
      break label50;
      label1018:
      d2 = 0.8571D;
      break label73;
      label1026:
      i3 = (int)(0.78D * this.mButtonAreaWidth);
    }
  }
  
  public void exitButtonClick(View paramView)
  {
    Bitmap localBitmap = Bitmaps.getBitmap(((Integer)this.mImageNameMap.get("exit")).intValue());
    this.mButtonAreaLayout.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSize(localBitmap, this.mButtonWidth, this.mButtonHeight)));
  }
  
  public void installButtonClick(View paramView)
  {
    Bitmap localBitmap = Bitmaps.getBitmap(((Integer)this.mImageNameMap.get("dl")).intValue());
    this.mButtonAreaLayout.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSize(localBitmap, this.mButtonWidth, this.mButtonHeight)));
  }
  
  public void otherButtonClick(View paramView)
  {
    Bitmap localBitmap = Bitmaps.getBitmap(((Integer)this.mImageNameMap.get("other")).intValue());
    this.mButtonAreaLayout.setBackgroundDrawable(new BitmapDrawable(AppImages.resizeBitmapToSpecifiedSize(localBitmap, this.mButtonWidth, this.mButtonHeight)));
  }
  
  class HttpResponseTask
    extends AsyncTask<Void, Void, Boolean>
  {
    private AppController appController;
    private String campaignId = null;
    private String campaignUrl = null;
    private Context context;
    private int imageHeight;
    private String imageUrl;
    private ImageView imageView;
    private int imageWidth;
    private ImageButton installButton;
    private HashMap<String, String> targetUriParams;
    
    public HttpResponseTask(Context paramContext, ImageView paramImageView, int paramInt1, int paramInt2, ImageButton paramImageButton)
    {
      this.context = paramContext;
      this.imageView = paramImageView;
      this.imageWidth = paramInt1;
      this.imageHeight = paramInt2;
      this.installButton = paramImageButton;
      this.imageUrl = null;
      this.campaignId = null;
      this.campaignUrl = null;
      this.targetUriParams = new HashMap();
      this.appController = AppController.createIncetance(paramContext);
    }
    
    private void addClickImageView(View paramView, final String paramString, final HashMap<String, String> paramHashMap, final AppController paramAppController)
    {
      paramView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (!TextUtils.isEmpty(paramString))
          {
            Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
            CutinViewBasic.HttpResponseTask.this.context.startActivity(localIntent);
          }
          for (;;)
          {
            return;
            paramAppController.registCPIMoveMarket(CutinViewBasic.HttpResponseTask.this.context, paramHashMap, "back_btn");
          }
        }
      });
    }
    
    private void addClickInstallButton(View paramView, final String paramString, final HashMap<String, String> paramHashMap, final AppController paramAppController)
    {
      paramView.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          CutinViewBasic.this.installButtonClick(paramAnonymousView);
          if (!TextUtils.isEmpty(paramString))
          {
            Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
            CutinViewBasic.HttpResponseTask.this.context.startActivity(localIntent);
          }
          for (;;)
          {
            return;
            paramAppController.registCPIMoveMarket(CutinViewBasic.HttpResponseTask.this.context, paramHashMap, "back_btn");
          }
        }
      });
    }
    
    private void updateImageView(final Context paramContext, final String paramString, final ImageView paramImageView, final int paramInt1, final int paramInt2)
    {
      new Thread(new Runnable()
      {
        public void run()
        {
          try
          {
            final Bitmap localBitmap = AppImages.resizeBitmapToSpecifiedSize(AppImages.getBitmapIcon(paramString, paramContext), paramInt1, paramInt2);
            this.val$mHandler.post(new Runnable()
            {
              public void run()
              {
                this.val$imageView.startAnimation(AppImages.getIconAnimation());
                this.val$imageView.setImageBitmap(localBitmap);
              }
            });
            label44:
            return;
          }
          catch (Exception localException)
          {
            break label44;
          }
        }
      }).start();
    }
    
    protected Boolean doInBackground(Void... paramVarArgs)
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("m", "bb");
      localHashMap.put("linktag", "back_btn");
      ArrayList localArrayList1 = this.appController.getCPIList(this.context, localHashMap).getApps();
      Boolean localBoolean;
      if (localArrayList1.isEmpty()) {
        localBoolean = Boolean.valueOf(false);
      }
      for (;;)
      {
        return localBoolean;
        Collections.shuffle(localArrayList1);
        ArrayList localArrayList2 = new ArrayList();
        PackageManager localPackageManager = this.context.getPackageManager();
        Iterator localIterator = localArrayList1.iterator();
        for (;;)
        {
          if (!localIterator.hasNext())
          {
            int i = localArrayList2.size();
            if (i <= 0) {
              break label332;
            }
            int j = new Random(System.currentTimeMillis()).nextInt(i);
            localHttpApp2 = (HttpApp)localArrayList2.get(j);
            if ((j != 0) || (TextUtils.isEmpty(this.campaignId)) || (TextUtils.isEmpty(this.campaignUrl))) {
              break label343;
            }
            this.imageUrl = ("http://android.giveapp.jp/images/banner/appc/campaign/" + this.campaignId + ".gif");
            this.campaignUrl = (this.campaignUrl + "?uid=" + AppPreference.getGid(this.context) + "&cid=" + this.campaignId);
            localBoolean = Boolean.valueOf(true);
            break;
          }
          HttpApp localHttpApp1 = (HttpApp)localIterator.next();
          if (!TextUtils.isEmpty(localHttpApp1.getValue("campaign_id")))
          {
            this.campaignId = localHttpApp1.getValue("campaign_id");
            this.campaignUrl = localHttpApp1.getValue("campaign_url");
          }
          String str1 = localHttpApp1.getValue("package");
          try
          {
            localPackageManager.getApplicationInfo(str1, 0);
          }
          catch (PackageManager.NameNotFoundException localNameNotFoundException)
          {
            localArrayList2.add(localHttpApp1);
          }
        }
        label332:
        HttpApp localHttpApp2 = (HttpApp)localArrayList1.get(0);
        label343:
        String str2 = localHttpApp2.getValue("ad_apps_id");
        if (!TextUtils.isEmpty(str2)) {
          this.imageUrl = ("http://android.giveapp.jp/images/banner/appc/back_btn/" + str2 + ".gif");
        }
        this.targetUriParams = new HashMap();
        this.targetUriParams.put("target_package", localHttpApp2.getCnvValue("package"));
        this.targetUriParams.put("ad_apps_id", str2);
        this.targetUriParams.put("redirect_url", localHttpApp2.getCnvValue("redirect_url"));
        localBoolean = Boolean.valueOf(true);
      }
    }
    
    protected void onPostExecute(Boolean paramBoolean)
    {
      if (paramBoolean.booleanValue())
      {
        updateImageView(this.context, this.imageUrl, this.imageView, this.imageWidth, this.imageHeight);
        addClickImageView(this.imageView, this.campaignUrl, this.targetUriParams, this.appController);
        addClickInstallButton(this.installButton, this.campaignUrl, this.targetUriParams, this.appController);
      }
    }
  }
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.view.CutinViewBasic
 * JD-Core Version:    0.7.0.1
 */