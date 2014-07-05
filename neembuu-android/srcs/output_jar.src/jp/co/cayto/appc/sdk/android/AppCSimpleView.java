package jp.co.cayto.appc.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.ParseException;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import java.util.HashMap;
import jp.co.cayto.appc.sdk.android.common.AppImages;
import jp.co.cayto.appc.sdk.android.resources.Texts;
import jp.co.cayto.appc.sdk.android.resources.Texts.ITexts;
import jp.co.cayto.appc.sdk.android.view.AppHorizontalScrollView;

public final class AppCSimpleView
  extends LinearLayout
{
  private static final String COLOR_APPC_BG = "color_appc_bg";
  private static final String COLOR_ARW_L = "color_arrow_l";
  private static final String COLOR_ARW_R = "color_arrow_r";
  private static final String COLOR_HEADER_BG = "color_header_bg";
  private static final String COLOR_LIST_BG = "color_list_bg";
  private static final String COLOR_TEXT = "color_text";
  private static final String COLOR_TITLE_BG = "color_title_bg";
  private static final int ICON_VIEW_COUNT = 6;
  private final int FP = -1;
  private final int WC = -2;
  private HashMap<String, Integer> colorMap = new HashMap();
  private boolean mCreatedFlag = false;
  private int mIconCount;
  private int mIconSize;
  
  public AppCSimpleView(Context paramContext)
  {
    super(paramContext);
  }
  
  public AppCSimpleView(Context paramContext, AttributeSet paramAttributeSet)
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
      label68:
      break label68;
    }
    createView(str1, localObject);
  }
  
  private void createScrollView(String paramString1, String paramString2, Button paramButton, ImageButton paramImageButton, LinearLayout paramLinearLayout)
  {
    Context localContext = getContext();
    TextView localTextView1 = new TextView(localContext);
    localTextView1.setBackgroundColor(0);
    localTextView1.setTextColor(((Integer)this.colorMap.get("color_text")).intValue());
    TableLayout localTableLayout1 = new TableLayout(localContext);
    final AppHorizontalScrollView localAppHorizontalScrollView = new AppHorizontalScrollView(localContext);
    ColorDrawable localColorDrawable = new ColorDrawable(((Integer)this.colorMap.get("color_header_bg")).intValue());
    ImageView localImageView1 = new ImageView(localContext);
    localImageView1.setImageBitmap(AppImages.createTriangle(20, "left", ((Integer)this.colorMap.get("color_arrow_l")).intValue()));
    localImageView1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        localAppHorizontalScrollView.goToLeftEdge();
      }
    });
    localImageView1.setOnLongClickListener(new View.OnLongClickListener()
    {
      public boolean onLongClick(View paramAnonymousView)
      {
        localAppHorizontalScrollView.goToLeftEdge();
        return false;
      }
    });
    ImageView localImageView2 = new ImageView(localContext);
    localImageView2.setImageBitmap(AppImages.createTriangle(20, "right", ((Integer)this.colorMap.get("color_arrow_r")).intValue()));
    localImageView2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        localAppHorizontalScrollView.goToRightEdge();
      }
    });
    localImageView2.setOnLongClickListener(new View.OnLongClickListener()
    {
      public boolean onLongClick(View paramAnonymousView)
      {
        localAppHorizontalScrollView.goToRightEdge();
        return false;
      }
    });
    localAppHorizontalScrollView.setLayoutParams(new LinearLayout.LayoutParams(-1, this.mIconSize));
    TextView localTextView2 = new TextView(localContext);
    localTextView2.setBackgroundColor(0);
    localTextView2.setText(new Texts(localContext.getApplicationContext()).get.広告_読み込み中());
    localTextView2.setTextSize(12.0F);
    localAppHorizontalScrollView.setResource(paramString1, paramString2, (Activity)localContext, paramButton, localTextView1, localImageView1, localImageView2, localTextView2, this.mIconSize - this.mIconSize / 6);
    TableRow localTableRow1 = new TableRow(localContext);
    TableLayout localTableLayout2 = new TableLayout(localContext);
    TableRow localTableRow2 = new TableRow(localContext);
    localTableRow2.addView(paramButton);
    localTableRow2.setBackgroundDrawable(localColorDrawable);
    TableRow localTableRow3 = new TableRow(localContext);
    localTableRow3.addView(localImageView1, new TableRow.LayoutParams(-1, -1));
    localTableRow3.addView(localAppHorizontalScrollView, new TableRow.LayoutParams(-1, -2));
    localTableRow3.addView(localImageView2, new TableRow.LayoutParams(-1, -1));
    localTableLayout2.addView(localTableRow2, new TableLayout.LayoutParams(-1, -2));
    localTableLayout2.addView(localTableRow3, new TableLayout.LayoutParams(-1, -2));
    localTableLayout2.setColumnShrinkable(1, true);
    TableRow.LayoutParams localLayoutParams1 = (TableRow.LayoutParams)paramButton.getLayoutParams();
    localLayoutParams1.weight = 1.0F;
    localLayoutParams1.span = 3;
    localLayoutParams1.column = 0;
    localLayoutParams1.gravity = 3;
    ((TableRow.LayoutParams)localImageView1.getLayoutParams()).gravity = 16;
    ((TableRow.LayoutParams)localAppHorizontalScrollView.getLayoutParams()).gravity = 16;
    ((TableRow.LayoutParams)localImageView2.getLayoutParams()).gravity = 16;
    localTableRow1.addView(localTableLayout2, new TableRow.LayoutParams(-1, -2));
    localTableRow1.addView(paramImageButton);
    TableRow.LayoutParams localLayoutParams2 = (TableRow.LayoutParams)paramImageButton.getLayoutParams();
    localLayoutParams2.gravity = 21;
    localLayoutParams2.width = this.mIconSize;
    localLayoutParams2.height = (8 + this.mIconSize);
    paramImageButton.setLayoutParams(localLayoutParams2);
    localLayoutParams1.height = (this.mIconSize / 4);
    localTableLayout1.addView(localTableRow1, new TableLayout.LayoutParams(-1, -2));
    localTableLayout1.setColumnStretchable(0, true);
    localTableLayout1.setColumnShrinkable(1, true);
    paramLinearLayout.addView(localTableLayout1, new LinearLayout.LayoutParams(-1, -2));
    paramLinearLayout.setPadding(0, 0, 0, 0);
  }
  
  private View getView(String paramString1, String paramString2, String paramString3)
  {
    final Context localContext = getContext();
    Object localObject;
    try
    {
      HashMap localHashMap = new HashMap();
      Display localDisplay = ((WindowManager)localContext.getSystemService("window")).getDefaultDisplay();
      if (localContext.getResources().getConfiguration().orientation == 2) {}
      for (this.mIconSize = ((int)(0.16F * localDisplay.getHeight()));; this.mIconSize = (localDisplay.getWidth() / 6))
      {
        localHashMap.put("logo_0", AppImages.getBitmap(24, false, localContext));
        if (!paramString2.equals("green")) {
          break;
        }
        this.colorMap.put("color_arrow_l", Integer.valueOf(Color.parseColor("#FFBCDA00")));
        this.colorMap.put("color_arrow_r", Integer.valueOf(Color.parseColor("#FFBCDA00")));
        this.colorMap.put("color_header_bg", Integer.valueOf(Color.parseColor("#FFBCDA1B")));
        this.colorMap.put("color_list_bg", Integer.valueOf(Color.parseColor("#99FFFFFF")));
        this.colorMap.put("color_title_bg", Integer.valueOf(Color.parseColor("#FFBCDA1B")));
        this.colorMap.put("color_appc_bg", Integer.valueOf(Color.parseColor("#FFBCDA1B")));
        this.colorMap.put("color_text", Integer.valueOf(AppImages.parseColor(paramString3, "#333333")));
        String str = new Texts(localContext).get.広告_タイトル();
        ColorDrawable localColorDrawable1 = new ColorDrawable(((Integer)this.colorMap.get("color_header_bg")).intValue());
        ColorDrawable localColorDrawable2 = new ColorDrawable(((Integer)this.colorMap.get("color_title_bg")).intValue());
        GradientDrawable.Orientation localOrientation = GradientDrawable.Orientation.TOP_BOTTOM;
        int[] arrayOfInt = new int[3];
        arrayOfInt[0] = ((Integer)this.colorMap.get("color_appc_bg")).intValue();
        arrayOfInt[1] = ((Integer)this.colorMap.get("color_appc_bg")).intValue();
        arrayOfInt[2] = ((Integer)this.colorMap.get("color_appc_bg")).intValue();
        final GradientDrawable localGradientDrawable = new GradientDrawable(localOrientation, arrayOfInt);
        ColorDrawable localColorDrawable3 = new ColorDrawable(((Integer)this.colorMap.get("color_list_bg")).intValue());
        localObject = new LinearLayout(localContext);
        ((LinearLayout)localObject).setOrientation(1);
        ((LinearLayout)localObject).setBackgroundDrawable(localColorDrawable1);
        LinearLayout localLinearLayout1 = new LinearLayout(localContext);
        localLinearLayout1.setOrientation(1);
        localLinearLayout1.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        final ImageButton localImageButton = new ImageButton(localContext);
        localImageButton.setBackgroundDrawable(localGradientDrawable);
        localImageButton.setImageBitmap(AppImages.resizeBitmapToSpecifiedSize((Bitmap)localHashMap.get("logo_0"), this.mIconSize, this.mIconSize));
        localImageButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            new CountDownTimer(1000L, 200L)
            {
              public void onFinish()
              {
                this.val$appCBGDrawable.setColorFilter(null);
                this.val$appCButton.invalidate();
              }
              
              public void onTick(long paramAnonymous2Long)
              {
                this.val$appCBGDrawable.setColorFilter(-872415079, PorterDuff.Mode.LIGHTEN);
                this.val$appCButton.invalidate();
              }
            }.start();
            Intent localIntent = new Intent(localContext, AppCWebActivity.class);
            localIntent.putExtra("type", "pr_list");
            localIntent.putExtra("pr_type", "simple_web");
            localContext.startActivity(localIntent);
            new Thread(new Runnable()
            {
              public void run()
              {
                try
                {
                  Thread.sleep(1000L);
                  label6:
                  this.val$mHandler.post(new Runnable()
                  {
                    public void run()
                    {
                      this.val$appCBGDrawable.setColorFilter(null);
                      this.val$appCButton.invalidate();
                    }
                  });
                  return;
                }
                catch (Exception localException)
                {
                  break label6;
                }
              }
            }).start();
          }
        });
        Button localButton = new Button(localContext);
        localButton.setBackgroundDrawable(localColorDrawable2);
        localButton.setText(str);
        localButton.setTextSize(10.0F);
        localButton.setPadding(5, 2, 2, 2);
        localButton.setIncludeFontPadding(false);
        localButton.setGravity(16);
        localButton.setTextColor(((Integer)this.colorMap.get("color_text")).intValue());
        TableLayout localTableLayout = new TableLayout(localContext);
        TableLayout.LayoutParams localLayoutParams = new TableLayout.LayoutParams(-1, -2);
        localTableLayout.setLayoutParams(localLayoutParams);
        new FrameLayout(localContext).setBackgroundColor(Color.parseColor("#FF000000"));
        LinearLayout localLinearLayout2 = new LinearLayout(localContext);
        localLinearLayout2.setOrientation(0);
        ScrollView localScrollView = new ScrollView(localContext);
        LinearLayout localLinearLayout3 = new LinearLayout(localContext);
        localLinearLayout3.setOrientation(1);
        createScrollView(paramString1, "Random", localButton, localImageButton, localLinearLayout1);
        localLinearLayout3.addView(localLinearLayout1, new LinearLayout.LayoutParams(-1, -2));
        LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-1, -2);
        ((LinearLayout)localObject).addView(localLinearLayout2, localLayoutParams1);
        LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-1, -2);
        ((LinearLayout)localObject).addView(localScrollView, localLayoutParams2);
        localScrollView.addView(localLinearLayout3);
        localScrollView.setBackgroundDrawable(localColorDrawable3);
        localScrollView.setPadding(0, 0, 0, 0);
        break label1108;
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        localException.printStackTrace();
        TextView localTextView = new TextView(localContext);
        localObject = localTextView;
        break;
        if (paramString2.equals("pink"))
        {
          this.colorMap.put("color_arrow_l", Integer.valueOf(Color.parseColor("#FFFFD1CC")));
          this.colorMap.put("color_arrow_r", Integer.valueOf(Color.parseColor("#FFFFD1CC")));
          this.colorMap.put("color_header_bg", Integer.valueOf(Color.parseColor("#FFFFD1D4")));
          this.colorMap.put("color_list_bg", Integer.valueOf(Color.parseColor("#99FFFFFF")));
          this.colorMap.put("color_title_bg", Integer.valueOf(Color.parseColor("#FFFFD1D4")));
          this.colorMap.put("color_appc_bg", Integer.valueOf(Color.parseColor("#FFFFD1D4")));
          this.colorMap.put("color_text", Integer.valueOf(AppImages.parseColor(paramString3, "#333333")));
        }
        else
        {
          int i = AppImages.parseColor(paramString2, "#333333");
          this.colorMap.put("color_arrow_l", Integer.valueOf(Color.parseColor("#00ff00")));
          this.colorMap.put("color_arrow_r", Integer.valueOf(Color.parseColor("#00ff00")));
          this.colorMap.put("color_header_bg", Integer.valueOf(i));
          this.colorMap.put("color_list_bg", Integer.valueOf(Color.parseColor("#CC000000")));
          this.colorMap.put("color_title_bg", Integer.valueOf(i));
          this.colorMap.put("color_appc_bg", Integer.valueOf(i));
          this.colorMap.put("color_text", Integer.valueOf(AppImages.parseColor(paramString3, "#FFFFFF")));
        }
      }
    }
    label1108:
    return localObject;
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
            AppCSimpleView.this.addView(this.val$view, new LinearLayout.LayoutParams(-1, -2));
            AppCSimpleView.this.setVisibility(0);
          }
        });
      }
    });
  }
  
  public AppCSimpleView createView(String paramString1, String paramString2)
  {
    if (this.mCreatedFlag) {}
    for (;;)
    {
      return this;
      this.mCreatedFlag = true;
      if (TextUtils.isEmpty(paramString1)) {
        paramString1 = "black";
      }
      setVisibility(4);
      setView(getView("A", paramString1, paramString2));
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
}


/* Location:           F:\neembuu\Research\android_apps\output_jar.jar
 * Qualified Name:     jp.co.cayto.appc.sdk.android.AppCSimpleView
 * JD-Core Version:    0.7.0.1
 */